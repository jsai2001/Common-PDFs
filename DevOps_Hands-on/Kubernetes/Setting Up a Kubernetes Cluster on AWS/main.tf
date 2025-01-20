provider "aws" {
    region = "ap-south-1"
}

# Network Configuration
resource "aws_vpc" "eks_vpc" {
    cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "eks_subnet" {
    count = 3
    vpc_id = aws_vpc.eks_vpc.id
    cidr_block = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
    availability_zone = element(data.aws_availability_zones.available.names, count.index)
}

data "aws_availability_zones" "available" {
    state = "available"
}

# IAM Roles and Policies for EKS
resource "aws_iam_role" "eks_cluster_role" {
    name = "eks-cluster-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Effect = "Allow"
                Principal = {
                    Service = "eks.amazonaws.com"
                }
                Action = "sts:AssumeRole"
            },
        ]
    })
}

resource "aws_iam_role_policy_attachment" "eks_cluster_policy" {
    role       = aws_iam_role.eks_cluster_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

resource "aws_iam_role" "eks_node_role" {
    name = "eks-node-role"

    assume_role_policy = jsonencode({
        Version = "2012-10-17"
        Statement = [
            {
                Effect = "Allow"
                Principal = {
                    Service = "ec2.amazonaws.com"
                }
                Action = "sts:AssumeRole"
            },
        ]
    })
}

resource "aws_iam_role_policy_attachment" "eks_node_policy" {
    role       = aws_iam_role.eks_node_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}

resource "aws_iam_role_policy_attachment" "eks_cni_policy" {
    role       = aws_iam_role.eks_node_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
}

# EKS Cluster
resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids = aws_subnet.eks_subnet[*].id
    }

    tags = {
        Name = "EKS-Cluster"
    }

    depends_on = [
        aws_vpc.eks_vpc,
        aws_subnet.eks_subnet
    ]
}

# Fetch the latest EKS optimized AMI
data "aws_ami" "eks_optimized" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amazon-eks-node-${aws_eks_cluster.my_cluster.version}-v*"]
  }
}

# Create a launch template with the custom AMI
resource "aws_launch_template" "eks_node_launch_template" {
  name_prefix   = "eks-node-lt-"
  image_id      = data.aws_ami.eks_optimized.id
  instance_type = "t3.medium"
}

# EKS Node Group using the Launch Template
resource "aws_eks_node_group" "my_node_group" {
    cluster_name    = aws_eks_cluster.my_cluster.name
    node_group_name = "my-node-group"
    node_role_arn   = aws_iam_role.eks_node_role.arn
    subnet_ids      = aws_subnet.eks_subnet[*].id

    launch_template {
        id      = aws_launch_template.eks_node_launch_template.id
        version = aws_launch_template.eks_node_launch_template.latest_version
    }

    scaling_config {
        desired_size = 2
        max_size     = 3
        min_size     = 1
    }

    # Removed 'instance_types' here since it's defined in the launch template
    capacity_type = "SPOT"

    labels = {
        "role" = "worker"
    }

    tags = {
        "Name" = "eks-node-group"
    }

    depends_on = [
        aws_eks_cluster.my_cluster,
        aws_iam_role_policy_attachment.eks_node_policy,
        aws_iam_role_policy_attachment.eks_cni_policy
    ]
}