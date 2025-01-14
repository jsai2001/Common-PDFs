Creating a node group in an EKS cluster can sometimes take a significant amount of time due to various factors such as instance availability, network configuration, and AWS service load. While creating a node group is mandatory for running workloads on an EKS cluster, there are a few strategies you can use to potentially reduce the time it takes:

### Strategies to Reduce Node Group Creation Time

- **Use On-Demand Instances**: Spot instances can sometimes take longer to provision due to availability and pricing fluctuations. Using on-demand instances can help reduce provisioning time.
- **Choose Common Instance Types**: Using more commonly available instance types like `t3.medium` can help reduce provisioning time.
- **Pre-existing Node Groups**: If you already have a node group, you can reuse it instead of creating a new one each time.

### Typical Time Frame

- **Normal Range**: 5 to 15 minutes
- **Possible Delays**: Up to 20 minutes or more in some cases

## Hands-On Practice: Deploying a Static Website on Kubernetes Using Terraform and Kubernetes Manifests

### Objective

Deploy a static website on a Kubernetes cluster using Terraform for infrastructure setup and Kubernetes YAML files for application deployment. Understand and use key Kubernetes objects: Pods, ReplicaSets, Deployments, and Services.

### Step-by-Step Guide

#### 1. Set Up Your Kubernetes Cluster Using Terraform

**a. Create a Terraform Configuration File (`main.tf`)**

This configuration will set up an EKS cluster with the default node group.

```hcl
provider provider "aws" {
    region = "ap-south-1"
}

resource "aws_vpc" "eks_vpc" {
    cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "eks_subnet" {
    count                   = 3
    vpc_id                  = aws_vpc.eks_vpc.id
    cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
    availability_zone       = element(data.aws_availability_zones.available.names, count.index)
    map_public_ip_on_launch = true
}

resource "aws_internet_gateway" "eks_igw" {
    vpc_id = aws_vpc.eks_vpc.id
}

resource "aws_route_table" "eks_route_table" {
    vpc_id = aws_vpc.eks_vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.eks_igw.id
    }
}

resource "aws_route_table_association" "eks_route_table_association" {
    count          = 3
    subnet_id      = element(aws_subnet.eks_subnet[*].id, count.index)
    route_table_id = aws_route_table.eks_route_table.id
}

data "aws_availability_zones" "available" {
    state = "available"
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
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKS_CNI_Policy"
}

resource "aws_iam_role_policy_attachment" "eks_vpc_resource_controller_policy" {
    role       = aws_iam_role.eks_node_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
}

resource "aws_iam_role_policy_attachment" "eks_ecr_read_policy" {
    role       = aws_iam_role.eks_node_role.name
    policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
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
        from_port   = 0
        to_port     = 65535
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        from_port   = 0
        to_port     = 0
        protocol    = "icmp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        from_port   = 1025
        to_port     = 65535
        protocol    = "udp"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

resource "aws_eks_cluster" "my_cluster" {
    name     = "my-cluster"
    role_arn = aws_iam_role.eks_cluster_role.arn

    vpc_config {
        subnet_ids         = aws_subnet.eks_subnet[*].id
        security_group_ids = [aws_security_group.eks_security_group.id]
    }

    depends_on = [
        aws_vpc.eks_vpc,
        aws_subnet.eks_subnet,
        aws_internet_gateway.eks_igw,
        aws_route_table.eks_route_table,
        aws_route_table_association.eks_route_table_association
    ]
}

resource "aws_eks_node_group" "my_node_group" {
    cluster_name    = aws_eks_cluster.my_cluster.name
    node_group_name = "my-node-group"
    node_role_arn   = aws_iam_role.eks_node_role.arn
    subnet_ids      = aws_subnet.eks_subnet[*].id

    scaling_config {
        desired_size = 2
        max_size     = 3
        min_size     = 1
    }

    instance_types = ["t3.medium"]

    capacity_type = "ON_DEMAND"

    depends_on = [
        aws_eks_cluster.my_cluster,
        aws_iam_role_policy_attachment.eks_node_policy,
        aws_iam_role_policy_attachment.eks_cni_policy,
        aws_iam_role_policy_attachment.eks_vpc_resource_controller_policy,
        aws_iam_role_policy_attachment.eks_ecr_read_policy
    ]
}
```

**b. Initialize and Apply the Terraform Configuration**

Initialize Terraform:

```sh
terraform init
```

Apply the Configuration:

```sh
terraform apply
```

#### 2. Deploy the Static Website Using Kubernetes Manifests

**a. Create a Deployment YAML file (`static-website-deployment.yaml`)**

This deployment will create a ReplicaSet with two replicas of an Nginx web server serving a static website.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: static-website-deployment
spec:
  replicas: 2
  selector:
    matchLabels:
      app: static-website
  template:
    metadata:
      labels:
        app: static-website
    spec:
      containers:
      - name: nginx
        image: nginx:latest
        ports:
        - containerPort: 80
        volumeMounts:
        - name: website-content
          mountPath: /usr/share/nginx/html
      volumes:
      - name: website-content
        configMap:
          name: website-content
```

**b. Create a ConfigMap YAML file (`website-content-configmap.yaml`)**

This ConfigMap will store the content of the static website.

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: website-content
data:
  index.html: |
    <!DOCTYPE html>
    <html>
    <head>
      <title>Static Website</title>
    </head>
    <body>
      <h1>Welcome to the Static Website</h1>
      <p>This website is served by an Nginx server running in a Kubernetes cluster.</p>
    </body>
    </html>
```

**c. Create a Service YAML file (`static-website-service.yaml`)**

This service will expose the static website deployment using a LoadBalancer.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: static-website-service
spec:
  selector:
    app: static-website
  ports:
  - protocol: TCP
    port: 80
    targetPort: 80
  type: LoadBalancer
```

**d. Apply the Manifests**

Apply the ConfigMap:

```sh
kubectl apply -f website-content-configmap.yaml
```

Apply the Deployment:

```sh
kubectl apply -f static-website-deployment.yaml
```

Apply the Service:

```sh
kubectl apply -f static-website-service.yaml
```

#### 3. Verify the Deployment

**a. Check the Deployment**

```sh
kubectl get deployments
```

**b. Check the Pods**

```sh
kubectl get pods
```

**c. Check the Service**

```sh
kubectl get services
```

#### 4. Access the Static Website

For Minikube:

```sh
minikube service static-website-service
```

For EKS or other cloud providers:

Get the external IP of the LoadBalancer:

```sh
kubectl get service static-website-service
```

Access the website using the external IP.

### Summary

By following this hands-on practice, you will:

- Set up an EKS cluster using Terraform.
- Create and deploy a static website using Kubernetes manifests.
- Understand and use key Kubernetes objects: Pods, ReplicaSets, Deployments, and Services.
- Verify the deployment and access the static website.

This practice will give you a solid foundation in deploying applications on Kubernetes and managing them using Kubernetes objects and Terraform for infrastructure setup.

It seems like the EKS cluster's API server endpoint is not reachable, which could be due to several reasons such as the cluster not being properly set up, DNS issues, or network connectivity problems.

Let's go through the steps to ensure that your EKS cluster is correctly set up and that you can reach the API server.

## Steps to Troubleshoot and Resolve the Issue

### Verify Cluster Status
Ensure that your EKS cluster is active and running.

```sh
aws eks describe-cluster --name my-cluster --region ap-south-1 --query "cluster.status"
```

The output should be `ACTIVE`. If the cluster is not active, you need to resolve any issues with the cluster setup.

### Update kubeconfig
Ensure that your kubeconfig file is correctly configured and points to the correct EKS cluster.

```sh
aws eks update-kubeconfig --name my-cluster --region ap-south-1
```

### Check Cluster Endpoint
Verify the cluster endpoint URL from the EKS cluster description.

```sh
aws eks describe-cluster --name my-cluster --region ap-south-1 --query "cluster.endpoint"
```

Ensure that the endpoint URL matches the one in your kubeconfig file.

### Check Network Connectivity
Ensure that your local machine can reach the EKS cluster's API server. You can use tools like `curl` to test connectivity.

```sh
curl -v https://<your-cluster-endpoint>
```

### DNS Resolution
Ensure that DNS resolution is working correctly on your local machine. You can use tools like `nslookup` or `dig` to test DNS resolution.

```sh
nslookup <your-cluster-endpoint>
```

### Check Security Groups
Ensure that the security groups associated with your EKS cluster allow inbound and outbound traffic on the necessary ports.

## Summary
By following these steps, you should be able to troubleshoot and resolve the issue with `kubectl` not being able to reach the Kubernetes API server. Ensure that your kubeconfig is correctly configured, the cluster is active, and network connectivity is working. If DNS resolution is failing, check your local DNS settings and ensure that the EKS cluster is properly set up.

To delete the Kubernetes resources that you created using YAML files, you can use the `kubectl delete` command. This command allows you to delete resources defined in the YAML files.

## Steps to Delete Kubernetes Resources

### Delete the ConfigMap:

```sh
kubectl delete -f website-content-configmap.yaml
```

### Delete the Deployment:

```sh
kubectl delete -f static-website-deployment.yaml
```

### Delete the Service:

```sh
kubectl delete -f static-website-service.yaml
```

## Example Commands

Assuming you have the following YAML files:

- `website-content-configmap.yaml`
- `static-website-deployment.yaml`
- `static-website-service.yaml`

You can delete the resources using the following commands:

```sh
kubectl delete -f website-content-configmap.yaml
kubectl delete -f static-website-deployment.yaml
kubectl delete -f static-website-service.yaml
```

## Summary

By running these commands, you will delete the ConfigMap, Deployment, and Service that you created using the YAML files. This will effectively remove the Kubernetes resources from your cluster. Make sure to run these commands in the same directory where your YAML files are located or provide the correct path to the files.

## Explanation of the Infrastructure

The provided Terraform configuration sets up an Amazon EKS (Elastic Kubernetes Service) cluster along with the necessary networking and IAM roles. Here's a step-by-step explanation of how the infrastructure is built using AWS resources, how the resources are connected, and the configuration done for each resource.

### Step-by-Step Explanation

1. **Provider Configuration**

    ```hcl
    provider "aws" {
        region = "ap-south-1"
    }
    ```

    **Provider**: Specifies the AWS provider and the region (ap-south-1) where the resources will be created.

2. **VPC Configuration**

    ```hcl
    resource "aws_vpc" "eks_vpc" {
        cidr_block = "10.0.0.0/16"
    }
    ```

    **VPC**: Creates a Virtual Private Cloud (VPC) with a CIDR block of 10.0.0.0/16.

3. **Subnet Configuration**

    ```hcl
    resource "aws_subnet" "eks_subnet" {
        count                   = 3
        vpc_id                  = aws_vpc.eks_vpc.id
        cidr_block              = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
        availability_zone       = element(data.aws_availability_zones.available.names, count.index)
        map_public_ip_on_launch = true
    }
    ```

    **Subnets**: Creates three subnets within the VPC, each in a different availability zone.
    - `map_public_ip_on_launch`: Ensures that instances launched in these subnets are assigned public IP addresses, which is important for internet connectivity.

4. **Internet Gateway**

    ```hcl
    resource "aws_internet_gateway" "eks_igw" {
        vpc_id = aws_vpc.eks_vpc.id
    }
    ```

    **Internet Gateway**: Creates an Internet Gateway and attaches it to the VPC, allowing internet access.

5. **Route Table and Associations**

    ```hcl
    resource "aws_route_table" "eks_route_table" {
        vpc_id = aws_vpc.eks_vpc.id

        route {
            cidr_block = "0.0.0.0/0"
            gateway_id = aws_internet_gateway.eks_igw.id
        }
    }

    resource "aws_route_table_association" "eks_route_table_association" {
        count          = 3
        subnet_id      = element(aws_subnet.eks_subnet[*].id, count.index)
        route_table_id = aws_route_table.eks_route_table.id
    }
    ```

    **Route Table**: Creates a route table with a route to the internet via the Internet Gateway.
    - **Route Table Associations**: Associates the route table with the three subnets.

6. **IAM Roles and Policies**

    **Cluster Role**

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

    **Cluster Role**: Creates an IAM role for the EKS cluster with a trust relationship allowing EKS to assume the role.
    - **Cluster Policy**: Attaches the AmazonEKSClusterPolicy to the cluster role.

    **Node Role**

    ```hcl
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

    resource "aws_iam_role_policy_attachment" "eks_vpc_resource_controller_policy" {
        role       = aws_iam_role.eks_node_role.name
        policy_arn = "arn:aws:iam::aws:policy/AmazonEKSVPCResourceController"
    }

    resource "aws_iam_role_policy_attachment" "eks_ecr_read_policy" {
        role       = aws_iam_role.eks_node_role.name
        policy_arn = "arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryReadOnly"
    }
    ```

    **Node Role**: Creates an IAM role for the EKS worker nodes with a trust relationship allowing EC2 to assume the role.
    - **Node Policies**: Attaches necessary policies to the node role:
        - AmazonEKSWorkerNodePolicy
        - AmazonEKS_CNI_Policy
        - AmazonEKSVPCResourceController
        - AmazonEC2ContainerRegistryReadOnly

7. **Security Group**

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
            from_port   = 0
            to_port     = 65535
            protocol    = "tcp"
            cidr_blocks = ["0.0.0.0/0"]
        }

        ingress {
            from_port   = 0
            to_port     = 0
            protocol    = "icmp"
            cidr_blocks = ["0.0.0.0/0"]
        }

        ingress {
            from_port   = 1025
            to_port     = 65535
            protocol    = "udp"
            cidr_blocks = ["0.0.0.0/0"]
        }
    }
    ```

    **Security Group**: Creates a security group with open ingress and egress rules to allow traffic to and from the nodes.

8. **EKS Cluster**

    ```hcl
    resource "aws_eks_cluster" "my_cluster" {
        name     = "my-cluster"
        role_arn = aws_iam_role.eks_cluster_role.arn

        vpc_config {
            subnet_ids         = aws_subnet.eks_subnet[*].id
            security_group_ids = [aws_security_group.eks_security_group.id]
        }

        depends_on = [
            aws_vpc.eks_vpc,
            aws_subnet.eks_subnet,
            aws_internet_gateway.eks_igw,
            aws_route_table.eks_route_table,
            aws_route_table_association.eks_route_table_association
        ]
    }
    ```

    **EKS Cluster**: Creates the EKS cluster with the specified IAM role, subnets, and security group.

9. **EKS Node Group**

    ```hcl
    resource "aws_eks_node_group" "my_node_group" {
        cluster_name    = aws_eks_cluster.my_cluster.name
        node_group_name = "my-node-group"
        node_role_arn   = aws_iam_role.eks_node_role.arn
        subnet_ids      = aws_subnet.eks_subnet[*].id

        scaling_config {
            desired_size = 2
            max_size     = 3
            min_size     = 1
        }

        instance_types = ["t3.medium"]

        capacity_type = "ON_DEMAND"

        depends_on = [
            aws_eks_cluster.my_cluster,
            aws_iam_role_policy_attachment.eks_node_policy,
            aws_iam_role_policy_attachment.eks_cni_policy,
            aws_iam_role_policy_attachment.eks_vpc_resource_controller_policy,
            aws_iam_role_policy_attachment.eks_ecr_read_policy
        ]
    }
    ```

    **EKS Node Group**: Creates a node group for the EKS cluster with the specified IAM role, subnets, and instance types.
    - `scaling_config`: Configures the desired, minimum, and maximum number of nodes.
    - `instance_types`: Specifies the instance types to be used for the nodes.
    - `capacity_type`: Specifies the capacity type (on-demand).

### Summary

This Terraform configuration sets up an EKS cluster with the necessary networking, IAM roles, and security groups. The resources are interconnected as follows:

- The VPC provides the network environment.
- Subnets are created within the VPC and are associated with a route table that routes traffic to the internet via an Internet Gateway.
- IAM roles and policies are created for the EKS cluster and worker nodes to allow necessary permissions.
- A security group is created to allow traffic to and from the nodes.
- The EKS cluster is created with the specified IAM role, subnets, and security group.
- A node group is created for the EKS cluster with the specified IAM role, subnets, and instance types.

The `map_public_ip_on_launch` attribute in the subnet configuration ensures that instances launched in these subnets are assigned public IP addresses, which is important for internet connectivity. This allows the nodes to communicate with the EKS control plane and other internet resources.