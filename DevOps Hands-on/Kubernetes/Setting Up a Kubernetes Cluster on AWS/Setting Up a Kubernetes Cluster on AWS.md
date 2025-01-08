# Hands-On Exercise: Setting Up a Kubernetes Cluster on AWS

## Objective
Use Amazon EKS (Elastic Kubernetes Service) to set up a managed Kubernetes cluster. Install and configure `kubectl` to interact with your EKS cluster. Create an EKS cluster using the AWS Management Console, AWS CLI, or Infrastructure as Code (IaC) tools like Terraform.

## Step-by-Step Guide

### 1. Use Amazon EKS to Set Up a Managed Kubernetes Cluster

#### Option 1: Using the AWS Management Console

**Create an EKS Cluster:**

1. Open the Amazon EKS Console.
2. Click on "Create cluster".
3. Configure the cluster name, Kubernetes version, and role.
4. Configure the networking settings (VPC, subnets, security groups).
5. Click "Create" to create the cluster.

**Create Node Group:**

1. After the cluster is created, navigate to the "Node Groups" section.
2. Click on "Add node group".
3. Configure the node group name, instance type, and scaling configuration.
4. Click "Create" to create the node group.

#### Option 2: Using the AWS CLI

**Install AWS CLI:**

Follow the instructions to install the AWS CLI for your operating system: [AWS CLI Installation Guide](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html).

**Configure AWS CLI:**

```sh
aws configure
```

Enter your AWS Access Key ID, Secret Access Key, default region, and output format.

**Install `eksctl`:**

Follow the instructions to install `eksctl` for your operating system: [eksctl Installation Guide](https://eksctl.io/introduction/#installation).

**Create an EKS Cluster:**

```sh
eksctl create cluster --name=my-cluster --region=us-west-2 --node-type=t3.medium --nodes=3 --nodes-min=2 --nodes-max=4
```

#### Option 3: Using Terraform

**Install Terraform:**

Follow the instructions to install Terraform for your operating system: [Terraform Installation Guide](https://learn.hashicorp.com/tutorials/terraform/install-cli).

**Create a Terraform Configuration File (`main.tf`):**

```hcl
provider "aws" {
    region = "us-west-2"
}

resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids = aws_subnet.eks_subnet[*].id
    }
}

resource "aws_eks_node_group" "my_node_group" {
    cluster_name    = aws_eks_cluster.my_cluster.name
    node_group_name = "my-node-group"
    node_role_arn   = aws_iam_role.eks_node_role.arn
    subnet_ids      = aws_subnet.eks_subnet[*].id

    scaling_config {
        desired_size = 3
        max_size     = 4
        min_size     = 2
    }

    instance_types = ["t3.medium"]
}

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
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKSCNIPolicy"
}

resource "aws_subnet" "eks_subnet" {
    count = 3
    vpc_id = aws_vpc.eks_vpc.id
    cidr_block = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
    availability_zone = element(data.aws_availability_zones.available.names, count.index)
}

resource "aws_vpc" "eks_vpc" {
    cidr_block = "10.0.0.0/16"
}

data "aws_availability_zones" "available" {}
```

**Initialize and Apply the Terraform Configuration:**

```sh
terraform init
terraform apply
```

### 2. Install and Configure `kubectl` to Interact with Your EKS Cluster

**Install `kubectl`:**

Follow the instructions to install `kubectl` for your operating system: [Install and Set Up kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/).

**Update kubeconfig:**

Use `eksctl` to update your kubeconfig file:

```sh
eksctl utils write-kubeconfig --cluster=my-cluster --region=us-west-2
```

Or use the AWS CLI:

```sh
aws eks update-kubeconfig --name my-cluster --region us-west-2
```

**Verify the Configuration:**

```sh
kubectl get nodes
```

## Summary
By the end of this exercise, you should have:

- Set up an Amazon EKS cluster using the AWS Management Console, AWS CLI, or Terraform.
- Installed and configured `kubectl` to interact with your EKS cluster.
- Verified the cluster setup by listing the nodes.

This hands-on exercise will give you practical experience in setting up and managing a Kubernetes cluster on AWS, preparing you for more advanced topics and real-world scenarios.