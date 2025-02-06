## Terraform

### AWS Provider
```hcl
provider "aws" {
    region = "ap-south-1"
}
```
- **region**: Specifies the AWS region where resources will be provisioned.

### Kubernetes Provider
```hcl
provider "kubernetes" {
    host                   = aws_eks_cluster.my_cluster.endpoint
    cluster_ca_certificate = base64decode(aws_eks_cluster.my_cluster.certificate_authority[0].data)
    token                  = data.aws_eks_cluster_auth.my_cluster.token
}
```
- **host**: The endpoint of the EKS cluster.
- **cluster_ca_certificate**: Base64 decoded certificate authority data for authentication.
- **token**: Authentication token for the Kubernetes cluster.

### Data Sources
#### aws_eks_cluster_auth
```hcl
data "aws_eks_cluster_auth" "my_cluster" {
    name = aws_eks_cluster.my_cluster.name
}
```
- **name**: The name of the EKS cluster.

#### aws_availability_zones
```hcl
data "aws_availability_zones" "available" {}
```
No parameters needed, returns available zones in the region.

### Network Resources
#### aws_vpc
```hcl
resource "aws_vpc" "eks_vpc" {
    cidr_block = "10.0.0.0/16"
}
```
- **cidr_block**: Defines the range of IP addresses for this VPC.

#### aws_subnet
```hcl
resource "aws_subnet" "eks_public_subnet" {
    count                   = 3
    vpc_id                  = aws_vpc.eks_vpc.id
    cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
    availability_zone       = element(data.aws_availability_zones.available.names, count.index)
    map_public_ip_on_launch = true
}
```
- **cidr_block**: Uses cidrsubnet function to calculate CIDR based on VPC's CIDR, bit length, and index.
- **availability_zone**: Picks an availability zone from the list.
- **map_public_ip_on_launch**: Assigns a public IP to instances launched in this subnet.

#### aws_subnet (Private)
```hcl
resource "aws_subnet" "eks_private_subnet" {
    count                   = 3
    vpc_id                  = aws_vpc.eks_vpc.id
    cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index + 3)
    availability_zone       = element(data.aws_availability_zones.available.names, count.index)
    map_public_ip_on_launch = false
}
```
Similar to public subnet but with different CIDR blocks and no public IP assignment.

#### aws_internet_gateway
```hcl
resource "aws_internet_gateway" "eks_igw" {
    vpc_id = aws_vpc.eks_vpc.id
}
```
Attaches this gateway to the specified VPC for internet connectivity.

#### aws_route_table
```hcl
resource "aws_route_table" "eks_public_route_table" {
    vpc_id = aws_vpc.eks_vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.eks_igw.id
    }
}
```
- **route**: Defines a route for all traffic (0.0.0.0/0) to go through the Internet Gateway.

#### aws_route_table_association
```hcl
resource "aws_route_table_association" "eks_public_route_table_association" {
    count          = 3
    subnet_id      = element(aws_subnet.eks_public_subnet[*].id, count.index)
    route_table_id = aws_route_table.eks_public_route_table.id
}
```
- **subnet_id**: Associates a subnet with a route table.

#### aws_nat_gateway
```hcl
resource "aws_nat_gateway" "eks_nat_gateway" {
    count         = 3
    allocation_id = aws_eip.nat_eip[count.index].id
    subnet_id     = element(aws_subnet.eks_public_subnet[*].id, count.index)
}
```
- **allocation_id**: The Elastic IP allocated for this NAT Gateway.
- **subnet_id**: The public subnet in which to place the NAT Gateway.

#### aws_eip
```hcl
resource "aws_eip" "nat_eip" {
    count  = 3
    domain = "vpc"
}
```
- **domain**: Specifies that this EIP is for use within a VPC.

#### aws_route_table (Private)
```hcl
resource "aws_route_table" "eks_private_route_table" {
    vpc_id = aws_vpc.eks_vpc.id

    route {
        cidr_block     = "0.0.0.0/0"
        nat_gateway_id = element(aws_nat_gateway.eks_nat_gateway[*].id, 0)
    }
}
```
Routes all traffic through the first NAT Gateway for outbound internet access.

#### aws_route_table_association (Private)
```hcl
resource "aws_route_table_association" "eks_private_route_table_association" {
    count          = 3
    subnet_id      = element(aws_subnet.eks_private_subnet[*].id, count.index)
    route_table_id = aws_route_table.eks_private_route_table.id
}
```
Associates private subnets with the private route table.

### Security
#### aws_security_group
```hcl
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
```
- **egress**: Defines outbound rules. Here, all traffic is allowed out.
- **ingress**: Defines inbound rules. Here, it allows MySQL traffic from within the VPC.

### Database
#### aws_db_instance
```hcl
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
```
Various parameters for configuring the database instance.

#### aws_db_subnet_group
```hcl
resource "aws_db_subnet_group" "mydb_subnet_group" {
    name       = "mydb-subnet-group"
    subnet_ids = aws_subnet.eks_private_subnet[*].id
}
```
- **subnet_ids**: Specifies which subnets the DB can be placed in.

### IAM
#### aws_iam_role
```hcl
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
```
- **assume_role_policy**: JSON policy allowing EKS to assume this role.

#### aws_iam_role_policy_attachment
```hcl
resource "aws_iam_role_policy_attachment" "eks_cluster_role_attachment" {
    role       = aws_iam_role.eks_cluster_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}
```
Attaches a policy to a role.

### EKS
#### aws_eks_cluster
```hcl
resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids         = aws_subnet.eks_public_subnet[*].id
        security_group_ids = [aws_security_group.eks_security_group.id]
    }
}
```
- **vpc_config**: Configures network settings for the cluster.

#### aws_eks_node_group
```hcl
resource "aws_eks_node_group" "my_node_group" {
    cluster_name    = aws_eks_cluster.my_cluster.name
    node_group_name = "my-node-group"
    node_role_arn   = aws_iam_role.eks_node_role.arn
    subnet_ids      = aws_subnet.eks_private_subnet[*].id

    scaling_config {
        desired_size = 5
        max_size     = 7
        min_size     = 3
    }

    instance_types = ["t3.small"]

    remote_access {
        ec2_ssh_key = "my-key"
    }

    tags = {
        Name = "eks-node-group"
    }
}
```
- **scaling_config**: Configures auto-scaling for the node group.
- **instance_types**: Specifies the EC2 instance type for nodes.
- **remote_access**: Configures SSH access for the nodes.

### Local Resources and Data
#### local_file
```hcl
resource "local_file" "website_content_configmap" {
    content  = data.template_file.website_content_configmap.rendered
    filename = "${path.module}/website-content-configmap.yaml"
}
```
Creates a local file with rendered template content.

#### data.template_file
```hcl
data "template_file" "website_content_configmap" {
    template = file("${path.module}/website-content-configmap.tpl.yaml")
    vars = {
        db_host = aws_db_instance.mydb.endpoint
    }
}
```
Renders a template file with variables.

#### kubernetes_config_map
```hcl
resource "kubernetes_config_map" "init_sql_config" {
    metadata {
        name = "init-sql-config"
    }
    data = {
        "init.sql" = file("${path.module}/init.sql")
    }
}
```
Creates a Kubernetes ConfigMap from local file content.

### VPC

```hcl
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
```

### Security Groups
**AWS Security Group:**

```hcl
resource "aws_security_group" "eks_security_group" {
    vpc_id = aws_vpc.eks_vpc.id

    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}
```

**Kubernetes Network Policy:**

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: allow-web
  namespace: default
spec:
  podSelector:
    matchLabels:
      app: web
  ingress:
  - from:
    - podSelector:
        matchLabels:
          app: frontend
    ports:
    - protocol: TCP
      port: 80
```

### EKS Cluster

**EKS Cluster:**

```hcl
resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids = [aws_subnet.eks_public_subnet.*.id]
    }
}
```

**IAM Role for EKS Cluster:**

```hcl
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
```

### AWS & Kubernetes Integration with Terraform
```hcl
provider "aws" {
    region = "ap-south-1"
}

provider "kubernetes" {
    host                   = aws_eks_cluster.my_cluster.endpoint
    cluster_ca_certificate = base64decode(aws_eks_cluster.my_cluster.certificate_authority[0].data)
    token                  = data.aws_eks_cluster_auth.my_cluster.token
}

resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids = [aws_subnet.eks_public_subnet.*.id]
    }
}
```

### How to handle database configurations
- **ConfigMaps**: ConfigMaps are used to store non-sensitive configuration data in key-value pairs.
- **Secrets**: Secrets are used to store sensitive data, such as passwords, in a secure manner.

**Code Example:**

**ConfigMap:**

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: db-config
data:
  DB_HOST: mydb.example.com
  DB_PORT: "3306"
```

**Secret:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-secret
type: Opaque
data:
  DB_PASSWORD: cGFzc3dvcmQ=  # base64 encoded password
```

**Using ConfigMap and Secret in a Pod:**

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: my-app
spec:
  containers:
  - name: my-app-container
    image: my-app-image
    env:
    - name: DB_HOST
      valueFrom:
        configMapKeyRef:
          name: db-config
          key: DB_HOST
    - name: DB_PORT
      valueFrom:
        configMapKeyRef:
          name: db-config
          key: DB_PORT
    - name: DB_PASSWORD
      valueFrom:
        secretKeyRef:
          name: db-secret
          key: DB_PASSWORD
```

### Autoscaling using Kubernetes and AWS

**AWS Auto Scaling Group:**

```hcl
resource "aws_autoscaling_group" "example" {
    launch_configuration = aws_launch_configuration.example.id
    min_size             = 1
    max_size             = 5
    desired_capacity     = 2
    vpc_zone_identifier  = [aws_subnet.eks_public_subnet.*.id]
}
```

**Kubernetes HPA:**

```yaml
apiVersion: autoscaling/v1
kind: HorizontalPodAutoscaler
metadata:
  name: my-app-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-app
  minReplicas: 1
  maxReplicas: 10
  targetCPUUtilizationPercentage: 50
```