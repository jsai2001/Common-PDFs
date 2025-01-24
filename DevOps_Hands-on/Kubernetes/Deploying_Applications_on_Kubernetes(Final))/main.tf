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
}

# Internet Gateway - Required for public subnet internet access
resource "aws_internet_gateway" "eks_igw" {
  vpc_id = aws_vpc.eks_vpc.id
}

# Add Elastic IP for NAT Gateway
resource "aws_eip" "nat" {
  domain = "vpc"
}

# Update NAT Gateway configuration
resource "aws_nat_gateway" "eks_nat" {
  subnet_id     = aws_subnet.eks_public_subnet[0].id
  allocation_id = aws_eip.nat.id

  depends_on = [aws_internet_gateway.eks_igw]
}

# Route Tables - Required for traffic routing
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.eks_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.eks_igw.id
  }
  tags = {
    Name = "public"
  }
}

# Add route table associations
resource "aws_route_table" "private" {
  vpc_id = aws_vpc.eks_vpc.id

  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.eks_nat.id
  }
  tags = {
    Name = "private"
  }
}

# Add public route table association
resource "aws_route_table_association" "public" {
  count          = 3
  subnet_id      = aws_subnet.eks_public_subnet[count.index].id
  route_table_id = aws_route_table.public.id
}

resource "aws_route_table_association" "private" {
  count          = 3
  subnet_id      = aws_subnet.eks_private_subnet[count.index].id
  route_table_id = aws_route_table.private.id
}

resource "aws_eks_cluster" "my_cluster" {
  name     = "my-eks-cluster"
  role_arn = aws_iam_role.eks_cluster_role.arn

  vpc_config {
    subnet_ids = concat(aws_subnet.eks_public_subnet[*].id, aws_subnet.eks_private_subnet[*].id)
  }
}

resource "aws_eks_node_group" "my_node_group" {
  cluster_name    = aws_eks_cluster.my_cluster.name
  node_group_name = "my-node-group"
  node_role_arn   = aws_iam_role.eks_node_role.arn
  subnet_ids      = aws_subnet.eks_private_subnet[*].id

  scaling_config {
    desired_size = 2
    max_size     = 5
    min_size     = 1
  }

  instance_types = ["t3.medium"]

  remote_access {
    ec2_ssh_key = "my-key"  # Ensure this key pair exists in your AWS account
  }
}

resource "aws_iam_role" "eks_cluster_role" {
  name = "eks-cluster-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "eks.amazonaws.com"
        }
      }
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
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
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

resource "aws_db_subnet_group" "mydb_subnet_group" {
  name       = "mydb-subnet-group"
  subnet_ids = aws_subnet.eks_private_subnet[*].id
}

resource "aws_db_instance" "mydb" {
  allocated_storage      = 20
  storage_type           = "gp2"
  engine                 = "mysql"
  engine_version         = "8.4.3"  # Use a supported engine version
  instance_class         = "db.t3.micro"  # Use a supported instance class
  db_name                = "mydatabase"
  username               = "admin"
  password               = var.mysql_root_password
  db_subnet_group_name   = aws_db_subnet_group.mydb_subnet_group.name
  vpc_security_group_ids = [aws_security_group.rds_security_group.id]
  skip_final_snapshot    = true
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

resource "kubernetes_secret" "mysql_secret" {
  metadata {
    name = "mysql-secret"
  }
  data = {
    MYSQL_ROOT_PASSWORD = var.mysql_root_password
  }
}

resource "kubernetes_persistent_volume_claim" "mysql_pvc" {
  metadata {
    name = "mysql-pvc"
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "20Gi"
      }
    }
    storage_class_name = "standard"  # Ensure it matches the created storage class
  }
}

resource "kubernetes_deployment" "backend" {
  metadata {
    name = "backend"
    annotations = {
      "prometheus.io/scrape" = "true"
      "prometheus.io/port"   = "3000"
      "prometheus.io/path"   = "/metrics"
    }
  }
  spec {
    replicas = 2
    selector {
      match_labels = {
        app = "backend"
      }
    }
    template {
      metadata {
        labels = {
          app = "backend"
        }
      }
      spec {
        container {
          name  = "backend"
          image = "jeevan2001/backend:latest"
          
          port {
            container_port = 3000
            name          = "http"
          }

          env {
            name  = "DB_HOST"
            value = aws_db_instance.mydb.address
          }

          env {
            name  = "DB_PORT"
            value = tostring(aws_db_instance.mydb.port)
          }

          env {
            name = "DB_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.mysql_secret.metadata[0].name
                key  = "MYSQL_ROOT_PASSWORD"
              }
            }
          }

          resources {
            limits = {
              cpu    = "500m"
              memory = "512Mi"
            }
            requests = {
              cpu    = "250m"
              memory = "256Mi"
            }
          }
        }
      }
    }
  }
}

# Prometheus Service
resource "kubernetes_service" "prometheus" {
  metadata {
    name = "prometheus"
  }
  spec {
    selector = {
      app = "prometheus"
    }
    port {
      port        = 9090
      target_port = 9090
    }
  }
}

# Prometheus ConfigMap
resource "kubernetes_config_map" "prometheus" {
  metadata {
    name = "prometheus-config"
  }

  data = {
    "prometheus.yml" = <<EOT
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'backend'
    kubernetes_sd_configs:
      - role: pod
    relabel_configs:
      - source_labels: [__meta_kubernetes_pod_annotation_prometheus_io_scrape]
        action: keep
        regex: true
      - source_labels: [__meta_kubernetes_pod_annotation_prometheus_io_path]
        action: replace
        target_label: __metrics_path__
        regex: (.+)
    EOT
  }
}

# Update Prometheus Deployment
resource "kubernetes_deployment" "prometheus" {
  metadata {
    name = "prometheus"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "prometheus"
      }
    }
    template {
      metadata {
        labels = {
          app = "prometheus"
        }
      }
      spec {
        container {
          name  = "prometheus"
          image = "prom/prometheus:v2.30.3"
          
          args = [
            "--config.file=/etc/prometheus/prometheus.yml",
            "--storage.tsdb.path=/prometheus",
            "--web.console.libraries=/usr/share/prometheus/console_libraries",
            "--web.console.templates=/usr/share/prometheus/consoles"
          ]

          volume_mount {
            name       = "config"
            mount_path = "/etc/prometheus"
          }
        }

        volume {
          name = "config"
          config_map {
            name = kubernetes_config_map.prometheus.metadata[0].name
          }
        }
      }
    }
  }
}

resource "kubernetes_deployment" "frontend" {
  metadata {
    name = "frontend"
  }
  spec {
    replicas = 2
    selector {
      match_labels = {
        app = "frontend"
      }
    }
    template {
      metadata {
        labels = {
          app = "frontend"
        }
      }
      spec {
        container {
          name  = "frontend"
          image = "jeevan2001/frontend:latest"
          env {
            name  = "REACT_APP_BACKEND_URL"
            value = "http://backend-service:3000"
          }
          port {
            container_port = 80
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "backend_service" {
  metadata {
    name = "backend-service"
  }
  spec {
    selector = {
      app = "backend"
    }
    port {
      port        = 3000
      target_port = 3000
    }
    type = "ClusterIP"
  }
}

resource "kubernetes_service" "frontend_service" {
  metadata {
    name = "frontend-service"
  }
  spec {
    selector = {
      app = "frontend"
    }
    port {
      port        = 80
      target_port = 80
    }
    type = "LoadBalancer"
  }
}

resource "kubernetes_horizontal_pod_autoscaler" "hpa_backend" {
  metadata {
    name = "hpa-backend"
  }
  spec {
    scale_target_ref {
      kind = "Deployment"
      name = kubernetes_deployment.backend.metadata[0].name
      api_version = "apps/v1"
    }
    min_replicas = 2
    max_replicas = 10
    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 80
        }
      }
    }
  }
}

resource "kubernetes_cluster_role" "example" {
  metadata {
    name = "example"
  }
  rule {
    api_groups = [""]
    resources  = ["pods"]
    verbs      = ["get", "list", "watch"]
  }
}

resource "kubernetes_role_binding" "example" {
  metadata {
    name = "example"
  }
  role_ref {
    api_group = "rbac.authorization.k8s.io"
    kind      = "ClusterRole"
    name      = kubernetes_cluster_role.example.metadata[0].name
  }
  subject {
    kind = "ServiceAccount"
    name = "default"
    namespace = "default"
  }
}

resource "kubernetes_network_policy" "frontend_to_backend" {
  metadata {
    name = "frontend-to-backend"
  }
  spec {
    pod_selector {
      match_labels = {
        app = "backend"
      }
    }
    ingress {
      from {
        pod_selector {
          match_labels = {
            app = "frontend"
          }
        }
      }
      ports {
        port     = 3000
        protocol = "TCP"
      }
    }
    policy_types = ["Ingress"]
  }
}

resource "kubernetes_network_policy" "backend_to_db" {
  metadata {
    name = "backend-to-db"
  }
  spec {
    pod_selector {
      match_labels = {
        app = "backend"
      }
    }
    egress {
      to {
        ip_block {
          cidr = aws_vpc.eks_vpc.cidr_block
        }
      }
      ports {
        port     = 3306
        protocol = "TCP"
      }
    }
    policy_types = ["Egress"]
  }
}

resource "kubernetes_network_policy" "prometheus_monitoring" {
  metadata {
    name = "prometheus-monitoring"
  }
  spec {
    pod_selector {
      match_labels = {
        app = "backend"
      }
    }
    ingress {
      from {
        pod_selector {
          match_labels = {
            app = "prometheus"
          }
        }
      }
      ports {
        port     = 9090
        protocol = "TCP"
      }
    }
    policy_types = ["Ingress"]
  }
}

# Update RBAC for monitoring
resource "kubernetes_cluster_role" "prometheus" {
  metadata {
    name = "prometheus"
  }
  rule {
    api_groups = [""]
    resources  = ["pods", "services", "endpoints"]
    verbs      = ["get", "list", "watch"]
  }
}

resource "kubernetes_role_binding" "prometheus" {
  metadata {
    name = "prometheus"
  }
  role_ref {
    api_group = "rbac.authorization.k8s.io"
    kind      = "ClusterRole"
    name      = kubernetes_cluster_role.prometheus.metadata[0].name
  }
  subject {
    kind      = "ServiceAccount"
    name      = "default"
    namespace = "default"
  }
}

resource "local_file" "website_content_configmap" {
  content  = data.template_file.website_content_configmap.rendered
  filename = "${path.module}/website-content-configmap.yaml"
}

data "template_file" "website_content_configmap" {
  template = file("${path.module}/website-content-configmap.tpl.yaml")
  vars = {
    db_host = aws_db_instance.mydb.endpoint
  }
}

resource "local_file" "backend_deployment" {
  content  = data.template_file.backend_deployment.rendered
  filename = "${path.module}/backend-deployment.yaml"
}

data "template_file" "backend_deployment" {
  template = file("${path.module}/backend-deployment.tpl.yaml")
  vars = {
    db_host = local.db_hostname
    db_port = local.db_port
    MYSQL_ROOT_PASSWORD = local.mysql_root_password
  }
}

output "db_endpoint" {
  value = aws_db_instance.mydb.endpoint
}