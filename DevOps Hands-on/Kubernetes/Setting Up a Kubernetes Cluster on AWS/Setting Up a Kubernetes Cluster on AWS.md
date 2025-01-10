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

To install Terraform on Windows using the command line, you can use package managers like Chocolatey or Scoop, or manually download and set up the executable. Here are the steps for each method:

### Using Chocolatey

1. **Install Chocolatey**: If you haven't installed Chocolatey yet, open PowerShell as an administrator and run:
    ```powershell
    Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
    ```
2. **Install Terraform**: Once Chocolatey is installed, you can install Terraform with:
    ```powershell
    choco install terraform
    ```
3. **Verify Installation**: Open a new Command Prompt or PowerShell window and run:
    ```powershell
    terraform -version
    ```
    This should display the version of Terraform you just installed.

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

## Deploy a Test Application

### Create a Deployment YAML file (nginx-deployment.yaml):

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: nginx-deployment
spec:
    replicas: 2
    selector:
        matchLabels:
            app: nginx
    template:
        metadata:
            labels:
                app: nginx
        spec:
            containers:
            - name: nginx
                image: nginx:latest
                ports:
                - containerPort: 80
```

### Create a Service YAML file (nginx-service.yaml):

```yaml
apiVersion: v1
kind: Service
metadata:
    name: nginx-service
spec:
    selector:
        app: nginx
    ports:
        - protocol: TCP
            port: 80
            targetPort: 80
    type: LoadBalancer
```

### Apply the manifests:

```sh
kubectl apply -f nginx-deployment.yaml
kubectl apply -f nginx-service.yaml
```

### Verify the Deployment:

```sh
kubectl get deployments
kubectl get services
```

Let's go through the Terraform configuration step by step to understand what each part does.

1. **Provider Configuration**

    ```hcl
    provider "aws" {
        region = "ap-south-1"
    }
    ```

    - `provider "aws"`: Specifies that we are using the AWS provider.
    - `region`: Sets the AWS region to ap-south-1 (Mumbai).

2. **EKS Cluster Resource**

    ```hcl
    resource "aws_eks_cluster" "my_cluster" {
        name     = "my-cluster"
        role_arn = aws_iam_role.eks_cluster_role.arn

        vpc_config {
            subnet_ids = aws_subnet.eks_subnet[*].id
        }
    }
    ```

    - `resource "aws_eks_cluster" "my_cluster"`: Defines an EKS cluster resource named `my_cluster`.
    - `name`: The name of the EKS cluster.
    - `role_arn`: The ARN of the IAM role that provides permissions for the EKS control plane to manage AWS resources.
    - `vpc_config`: Configuration block for the VPC settings.
    - `subnet_ids`: Specifies the subnets to use for the EKS cluster. It references the IDs of the subnets created later in the configuration.

3. **EKS Node Group Resource**

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

        instance_types = ["t2.micro"]

        capacity_type = "ON_DEMAND"  # Use on-demand instances to stay within the Free Tier

        labels = {
            "role" = "worker"
        }

        tags = {
            "Name" = "eks-node-group"
        }
    }
    ```

    - `resource "aws_eks_node_group" "my_node_group"`: Defines an EKS node group resource named `my_node_group`.
    - `cluster_name`: The name of the EKS cluster to which the node group belongs.
    - `node_group_name`: The name of the node group.
    - `node_role_arn`: The ARN of the IAM role that provides permissions for the nodes to interact with AWS services.
    - `subnet_ids`: Specifies the subnets to use for the node group. It references the IDs of the subnets created later in the configuration.
    - `scaling_config`: Configuration block for the scaling settings.
        - `desired_size`: The desired number of nodes.
        - `max_size`: The maximum number of nodes.
        - `min_size`: The minimum number of nodes.
    - `instance_types`: Specifies the instance types to use for the nodes. Here, `t2.micro` instances are used to stay within the AWS Free Tier.
    - `capacity_type`: Specifies the capacity type for the instances. `ON_DEMAND` is used to stay within the Free Tier.
    - `labels`: Adds labels to the nodes.
    - `tags`: Adds tags to the node group.

4. **IAM Role for EKS Cluster**

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

    - `resource "aws_iam_role" "eks_cluster_role"`: Defines an IAM role named `eks_cluster_role`.
    - `name`: The name of the IAM role.
    - `assume_role_policy`: The policy that grants the EKS service permission to assume this role.

5. **IAM Policy Attachment for EKS Cluster Role**

    ```hcl
    resource "aws_iam_role_policy_attachment" "eks_cluster_policy" {
        role       = aws_iam_role.eks_cluster_role.name
        policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
    }
    ```

    - `resource "aws_iam_role_policy_attachment" "eks_cluster_policy"`: Attaches the `AmazonEKSClusterPolicy` to the `eks_cluster_role`.

6. **IAM Role for EKS Node Group**

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
    ```

    - `resource "aws_iam_role" "eks_node_role"`: Defines an IAM role named `eks_node_role`.
    - `name`: The name of the IAM role.
    - `assume_role_policy`: The policy that grants EC2 instances permission to assume this role.

7. **IAM Policy Attachments for EKS Node Role**

    ```hcl
    resource "aws_iam_role_policy_attachment" "eks_node_policy" {
        role       = aws_iam_role.eks_node_role.name
        policy_arn = "arn:aws:iam::aws:policy/AmazonEKSWorkerNodePolicy"
    }

    resource "aws_iam_role_policy_attachment" "eks_cni_policy" {
        role       = aws_iam_role.eks_node_role.name
        policy_arn = "arn:aws:iam::aws:policy/AmazonEKSCNIPolicy"
    }
    ```

    - `resource "aws_iam_role_policy_attachment" "eks_node_policy"`: Attaches the `AmazonEKSWorkerNodePolicy` to the `eks_node_role`.
    - `resource "aws_iam_role_policy_attachment" "eks_cni_policy"`: Attaches the `AmazonEKSCNIPolicy` to the `eks_node_role`.

8. **Subnets**

    ```hcl
    resource "aws_subnet" "eks_subnet" {
        count = 3
        vpc_id = aws_vpc.eks_vpc.id
        cidr_block = cidrsubnet(aws_vpc.eks_vpc.cidr_block, 8, count.index)
        availability_zone = element(data.aws_availability_zones.available.names, count.index)
    }
    ```

    - `resource "aws_subnet" "eks_subnet"`: Defines three subnets for the EKS cluster.
    - `count`: Specifies that three subnets should be created.
    - `vpc_id`: The ID of the VPC in which to create the subnets.
    - `cidr_block`: The CIDR block for each subnet.
    - `availability_zone`: The availability zone for each subnet.

9. **VPC**

    ```hcl
    resource "aws_vpc" "eks_vpc" {
        cidr_block = "10.0.0.0/16"
    }
    ```

    - `resource "aws_vpc" "eks_vpc"`: Defines a VPC for the EKS cluster.
    - `cidr_block`: The CIDR block for the VPC.

10. **Availability Zones**

    ```hcl
    data "aws_availability_zones" "available" {
        state = "available"
    }
    ```

    - `data "aws_availability_zones" "available"`: Retrieves the available availability zones in the specified region.

**Summary**

This Terraform configuration sets up an EKS cluster in the `ap-south-1` region with the following components:

- An EKS cluster with a specified IAM role.
- A node group with `t2.micro` instances (eligible for AWS Free Tier) and a specified IAM role.
- A VPC with three subnets, each in a different availability zone.
- IAM roles and policies for the EKS cluster and node group.

This setup ensures that the EKS cluster is properly configured and can be managed using the specified IAM roles and policies.

Here are some of the most important Terraform commands you should know when working with Terraform:

## Basic Commands

- **terraform init**: Initializes a new or existing Terraform working directory by downloading provider plugins and modules.
    ```bash
    terraform init
    ```
- **terraform plan**: Creates an execution plan which describes what Terraform will do to reach the desired state based on your configuration files. This is a dry run and does not make any changes to your infrastructure.
    ```bash
    terraform plan
    ```
- **terraform apply**: Applies the changes required to reach the desired state of the configuration. This command will execute the plan and modify your infrastructure.
    ```bash
    terraform apply
    ```
- **terraform destroy**: Destroys the Terraform-managed infrastructure. Use with caution as this will delete all resources managed by your Terraform configuration.
    ```bash
    terraform destroy
    ```

## State Management

- **terraform state list**: Lists all resources in the state file.
    ```bash
    terraform state list
    ```
- **terraform state show [resource]**: Shows the attributes of a single resource in the state file.
    ```bash
    terraform state show aws_instance.example
    ```
- **terraform state mv [source] [destination]**: Moves an item in the state, useful for renaming resources in your configuration.
    ```bash
    terraform state mv aws_instance.old_name aws_instance.new_name
    ```
- **terraform state rm [resource]**: Removes items from the state file, effectively telling Terraform to forget about managing those resources.
    ```bash
    terraform state rm aws_instance.to_remove
    ```

## Output and Variables

- **terraform output**: Displays the output values from your Terraform state. Useful for retrieving information about your infrastructure.
    ```bash
    terraform output
    ```
- **terraform refresh**: Updates the state file to match the real-world resources, without making any changes.
    ```bash
    terraform refresh
    ```
- **terraform validate**: Checks if the configuration files are syntactically valid and internally consistent.
    ```bash
    terraform validate
    ```

## Module Management

- **terraform get**: Downloads and installs modules needed by your configuration. This is typically done automatically by `terraform init`.
    ```bash
    terraform get
    ```

## Workspace Management

- **terraform workspace list**: Lists all workspaces in the current configuration.
    ```bash
    terraform workspace list
    ```
- **terraform workspace new [name]**: Creates a new workspace.
    ```bash
    terraform workspace new staging
    ```
- **terraform workspace select [name]**: Switches to the specified workspace.
    ```bash
    terraform workspace select staging
    ```
- **terraform workspace delete [name]**: Deletes a workspace.
    ```bash
    terraform workspace delete staging
    ```

## Miscellaneous

- **terraform fmt**: Rewrites Terraform configuration files to a canonical format and style.
    ```bash
    terraform fmt
    ```
- **terraform version**: Displays the version of Terraform you are using.
    ```bash
    terraform version
    ```
- **terraform show**: Provides human-readable output from a state or plan file.
    ```bash
    terraform show
    ```

These commands cover a wide range of operations you would typically perform with Terraform. Remember, when using these commands, especially `terraform apply` and `terraform destroy`, to review what changes will be made by first running `terraform plan`. Also, always ensure you're working in the correct workspace if you're using multiple environments. If you need more detailed information on any command, you can always use `terraform [command] -help` for more options and usage examples.