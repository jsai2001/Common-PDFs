provider "aws" {
  region = "ap-south-1"
}

provider "kubernetes" {
  host                   = aws_eks_cluster.my_cluster.endpoint
  cluster_ca_certificate = base64decode(aws_eks_cluster.my_cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.my_cluster.token
}

data "aws_eks_cluster_auth" "my_cluster" {
  name = aws_eks_cluster.my_cluster.name
}

data "aws_availability_zones" "available" {}

resource "aws_vpc" "eks_vpc" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "eks_public_subnet" {
  count                   = 3
  vpc_id                  = aws_vpc.eks_vpc.id
  cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
  availability_zone       = element(data.aws_availability_zones.available.names, count.index)
  map_public_ip_on_launch = true
}

resource "aws_subnet" "eks_private_subnet" {
  count                   = 3
  vpc_id                  = aws_vpc.eks_vpc.id
  cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index + 3)
  availability_zone       = element(data.aws_availability_zones.available.names, count.index)
  map_public_ip_on_launch = false
}

resource "aws_internet_gateway" "eks_igw" {
  vpc_id = aws_vpc.eks_vpc.id
}

resource "aws_route_table" "eks_public_route_table" {
  vpc_id = aws_vpc.eks_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.eks_igw.id
  }
}

resource "aws_route_table_association" "eks_public_route_table_association" {
  count          = 3
  subnet_id      = element(aws_subnet.eks_public_subnet[*].id, count.index)
  route_table_id = aws_route_table.eks_public_route_table.id
}

resource "aws_nat_gateway" "eks_nat_gateway" {
  count         = 3
  allocation_id = aws_eip.nat_eip[count.index].id
  subnet_id     = element(aws_subnet.eks_public_subnet[*].id, count.index)
}

resource "aws_eip" "nat_eip" {
  count  = 3
  domain = "vpc"
}

resource "aws_route_table" "eks_private_route_table" {
  vpc_id = aws_vpc.eks_vpc.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = element(aws_nat_gateway.eks_nat_gateway[*].id, 0)
  }
}

resource "aws_route_table_association" "eks_private_route_table_association" {
  count          = 3
  subnet_id      = element(aws_subnet.eks_private_subnet[*].id, count.index)
  route_table_id = aws_route_table.eks_private_route_table.id
}

resource "aws_security_group" "eks_security_group" {
  vpc_id = aws_vpc.eks_vpc.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }
}

resource "aws_security_group" "rds_security_group" {
  vpc_id = aws_vpc.eks_vpc.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["10.0.0.0/16"]
  }
}

resource "aws_db_instance" "mydb" {
  allocated_storage      = 20
  storage_type           = "gp2"
  engine                 = "mysql"
  engine_version         = "8.0"
  instance_class         = "db.t3.micro"
  db_name                = "mydatabase"
  username               = "admin"
  password               = "password"
  db_subnet_group_name   = aws_db_subnet_group.mydb_subnet_group.name
  vpc_security_group_ids = [aws_security_group.rds_security_group.id]
  skip_final_snapshot    = true
}

resource "aws_db_subnet_group" "mydb_subnet_group" {
  name       = "mydb-subnet-group"
  subnet_ids = aws_subnet.eks_private_subnet[*].id
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

resource "aws_iam_role_policy_attachment" "eks_cluster_role_attachment" {
  role       = aws_iam_role.eks_cluster_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"

  depends_on = [
    aws_iam_role.eks_cluster_role
  ]
}

resource "aws_eks_cluster" "my_cluster" {
  name     = "my-cluster"
  role_arn = aws_iam_role.eks_cluster_role.arn

  vpc_config {
    subnet_ids         = aws_subnet.eks_public_subnet[*].id
    security_group_ids = [aws_security_group.eks_security_group.id]
  }

  depends_on = [
    aws_vpc.eks_vpc,
    aws_subnet.eks_public_subnet,
    aws_internet_gateway.eks_igw,
    aws_route_table.eks_public_route_table,
    aws_iam_role_policy_attachment.eks_cluster_role_attachment
  ]
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

resource "aws_iam_role_policy_attachment" "eks_worker_node_policy" {
  role       = aws_iam_role.eks_node_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
}

resource "aws_iam_role_policy_attachment" "eks_cni_policy" {
  role       = aws_iam_role.eks_node_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
}

resource "aws_iam_role_policy_attachment" "eks_registry_policy" {
  role       = aws_iam_role.eks_node_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
}

resource "aws_eks_node_group" "my_node_group" {
  cluster_name    = aws_eks_cluster.my_cluster.name
  node_group_name = "my-node-group"
  node_role_arn   = aws_iam_role.eks_node_role.arn
  subnet_ids      = aws_subnet.eks_private_subnet[*].id

  scaling_config {
    desired_size = 1
    max_size     = 3
    min_size     = 1
  }

  instance_types = ["t3.small"]

  remote_access {
    ec2_ssh_key = "my-key"  # Ensure this key pair exists in your AWS account
  }

  tags = {
    Name = "eks-node-group"
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_node_policy,
    aws_iam_role_policy_attachment.eks_cni_policy,
    aws_iam_role_policy_attachment.eks_registry_policy
  ]
}

data "template_file" "website_content_configmap" {
  template = file("${path.module}/website-content-configmap.tpl.yaml")
  vars = {
    db_host = aws_db_instance.mydb.endpoint
  }
}

resource "local_file" "website_content_configmap" {
  content  = data.template_file.website_content_configmap.rendered
  filename = "${path.module}/website-content-configmap.yaml"
}

variable "mysql_root_password" {
  description = "The password for the MySQL root user"
  type        = string
  default     = "password"  # Assign a default value or provide it during terraform apply
}

locals {
  db_host     = aws_db_instance.mydb.endpoint
  db_hostname = regex("^(.*):", local.db_host)[0]
  db_port     = regex(":(.*)$", local.db_host)[0]
  mysql_root_password = "password"
}

data "template_file" "backend_deployment" {
  template = file("${path.module}/backend-deployment.tpl.yaml")
  vars = {
    db_host = local.db_hostname
    db_port = local.db_port
    MYSQL_ROOT_PASSWORD = local.mysql_root_password
  }
}

resource "local_file" "backend_deployment" {
  content  = data.template_file.backend_deployment.rendered
  filename = "${path.module}/backend-deployment.yaml"
}

output "db_endpoint" {
  value = aws_db_instance.mydb.endpoint
}

resource "kubernetes_config_map" "init_sql_config" {
  metadata {
    name = "init-sql-config"
  }
  data = {
    "init.sql" = file("${path.module}/init.sql")
  }
}