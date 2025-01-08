## Host an nginx webpage using Kubernetes and AWS Resources

To host an Nginx webpage using Kubernetes and AWS resources, you can follow these steps:

1. **Set Up Your AWS Environment:**
    - Install and configure the AWS CLI.
    - Create an EKS cluster using `eksctl`.

2. **Deploy Nginx on Kubernetes:**
    - Create Kubernetes manifests for the Nginx Deployment and Service.
    - Apply the manifests to your EKS cluster.

3. **Expose the Nginx Service:**
    - Use a LoadBalancer Service to expose the Nginx application to the internet.

## Step-by-Step Guide

### 1. Set Up Your AWS Environment

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
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=3 --nodes-min=2 --nodes-max=4
```

### Create an EKS Cluster with Cost-Effective Settings

**Create a Cluster Using Cost-Effective Instance Types and Spot Instances:**

```sh
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=2 --nodes-min=1 --nodes-max=3 --managed --spot --asg-access
```

* `--asg-access` allows for better integration with Auto Scaling groups for fine-tuned resource management.

**Using a YAML Configuration for More Control:**

Create a YAML file (`cluster-config.yaml`) with your cluster configuration:

```yaml
apiVersion: eksctl.io/v1alpha5
kind: ClusterConfig
metadata:
  name: my-cluster
  region: ap-south-1
nodeGroups:
  - name: ng-1
    instanceType: mixed
    desiredCapacity: 2
    minSize: 1
    maxSize: 3
    instancesDistribution:
      maxPrice: 0.04
      instanceTypes: ["t3.small", "t3.medium"]
      onDemandBaseCapacity: 0
      onDemandPercentageAboveBaseCapacity: 0
      spotInstancePools: 2
    labels: { role: worker }
    tags:
      nodegroup-role: worker
    iam:
      withAddonPolicies:
        autoScaler: true
```

**Apply the Configuration:**

```sh
eksctl create cluster -f cluster-config.yaml
```

### 2. Deploy Nginx on Kubernetes

**Create Kubernetes Manifests:**

Create a Deployment YAML file (`nginx-deployment.yaml`):

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

Create a Service YAML file (`nginx-service.yaml`):

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

**Apply the Manifests:**

```sh
kubectl apply -f nginx-deployment.yaml
kubectl apply -f nginx-service.yaml
```

### 3. Expose the Nginx Service

**Verify the Deployment and Service:**

```sh
kubectl get deployments
kubectl get pods
kubectl get services
```

**Access the Nginx Application:**

The `nginx-service` will be exposed using an AWS LoadBalancer. You can get the external IP address of the LoadBalancer using:

```sh
kubectl get service nginx-service
```

Look for the `EXTERNAL-IP` field in the output. This is the public IP address of your Nginx application.

**Example Output:**

```sh
$ kubectl get service nginx-service
NAME            TYPE           CLUSTER-IP       EXTERNAL-IP                                                              PORT(S)        AGE
nginx-service   LoadBalancer   10.100.200.100   a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6-1234567890.ap-south-1.elb.amazonaws.com   80:31234/TCP   5m
```

Open a web browser and navigate to the `EXTERNAL-IP` address to see the Nginx welcome page.

## Summary

By following these steps, you will:

- Set up an AWS environment and create an EKS cluster.
- Deploy an Nginx application on Kubernetes using Deployment and Service manifests.
- Expose the Nginx application to the internet using a LoadBalancer Service.

# Installing eksctl

## For Windows (Using Chocolatey):

**Prerequisite:** Ensure Chocolatey is installed on your system. If not, install it by following the official guide or using PowerShell as an administrator:

```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```

**Install eksctl:**

```powershell
choco install eksctl
```

## For macOS (Using Homebrew):

**Prerequisite:** Ensure Homebrew is installed. If not, install it by following the Homebrew installation guide.

**Install eksctl:**

```bash
brew tap weaveworks/tap
brew install weaveworks/tap/eksctl
```

## For Linux:

**Direct Download:**

```bash
curl --silent --location "https://github.com/eksctl-io/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```

**Verify Installation:**

```bash
eksctl version
```

**References:**

- [eksctl Installation Guide](https://eksctl.io/introduction/#installation)
- [Amazon EKS Cluster Setup using eksctl | by Subbarami Reddy | Medium](https://medium.com/@subbarami.reddy/amazon-eks-cluster-setup-using-eksctl-5d4b5b2b8f8b)

# Installing AWS CLI

## For Windows:

**Download Binary:** Visit [AWS CLI Installation Page](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-windows.html) and download the latest AWS CLI MSI installer.

**Install:** Run the installer and follow the prompts to install it.

**Verify Installation:**

```cmd
aws --version
```

## For macOS (Using Homebrew):

**Install AWS CLI:**

```bash
brew install awscli
```

**Verify Installation:**

```bash
aws --version
```

## For Linux:

**Download and Install AWS CLI:**

```bash
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

**Verify Installation:**

```bash
aws --version
```

**Configure AWS CLI:**

After installation, configure AWS CLI with your credentials:

```bash
aws configure
```

You'll need to enter your AWS Access Key ID, Secret Access Key, default region, and output format.

**References:**

- [Set up AWS CLI - Amazon EKS](https://docs.aws.amazon.com/eks/latest/userguide/getting-started-eksctl.html)
- [Install AWS CLI, eksctl CLI, kubectl for AWS EKS - STACKSIMPLIFY](https://stacksimplify.com/aws-eks/install-aws-cli-eksctl-kubectl/)

# Creating an EKS Cluster with eksctl

## Basic Cluster Creation:

Use the following command to create a basic EKS cluster with default settings:

```bash
eksctl create cluster
```

This will create a cluster in your default region with default parameters like two m5.large worker nodes.

## Custom Cluster Configuration:

**Specify Name and Region:**

```bash
eksctl create cluster --name=my-cluster --region=ap-south-1
```

**Specify Node Type and Count:**

```bash
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.medium --nodes=3 --nodes-min=2 --nodes-max=4
```

**Using YAML Configuration:**

Create a YAML file (e.g., `cluster.yaml`) with your cluster configuration:

```yaml
apiVersion: eksctl.io/v1alpha5
kind: ClusterConfig
metadata:
  name: my-cluster
  region: ap-south-1
nodeGroups:
  - name: ng-1
    instanceType: m5.large
    desiredCapacity: 2
```

**Apply the configuration:**

```bash
eksctl create cluster -f cluster.yaml
```

## Post-Creation Steps:

**Update Kubeconfig:** After cluster creation, eksctl will update your `~/.kube/config` file. Verify by:

```bash
kubectl get nodes
```

**Delete Cluster:** If you need to delete the cluster later:

```bash
eksctl delete cluster --name=my-cluster --region=ap-south-1
```

**References:**

- [Get started with Amazon EKS – eksctl - Amazon EKS](https://docs.aws.amazon.com/eks/latest/userguide/getting-started-eksctl.html)
- [How To Create AWS EKS Cluster Using Eksctl - devopscube.com](https://devopscube.com/create-eks-cluster-eksctl/)
- [Create an Amazon EKS cluster - Amazon EKS](https://docs.aws.amazon.com/eks/latest/userguide/create-cluster.html)

These steps should guide you through installing eksctl and AWS CLI, configuring your AWS environment, and creating an EKS cluster using eksctl. Always refer to the latest documentation for the most current instructions and best practices.

### Amazon EKS Cluster Creation Process Using eksctl

Here’s a step-by-step explanation of what's happening in the process of creating and configuring an Amazon EKS (Elastic Kubernetes Service) cluster using eksctl, a command-line tool for managing EKS clusters:

#### Cluster Creation Command
The command `eksctl create cluster` is used to initiate the creation of an EKS cluster with specific parameters:
- `--name=kube-cluster-demo`: Names the cluster `kube-cluster-demo`.
- `--region=ap-south-1`: Specifies the region `ap-south-1` for the cluster.
- `--node-type=t3.small`: Specifies the EC2 instance type for the nodes.
- `--nodes=3`: Requests 3 nodes initially.
- `--nodes-min=2 --nodes-max=4`: Sets the auto-scaling limits for the number of nodes.
- `--managed`: Indicates that this is a managed node group.
- `--spot`: Uses spot instances for cost optimization.
- `--asg-access`: Grants ASG (Auto Scaling Group) access.

#### Setting Up VPC and Subnets
The command checks and sets up the VPC and subnets in the specified region (`ap-south-1`). It lists public and private subnets within this region.

#### Creating Nodegroup
A nodegroup named `ng-0e94f6e8` is created in the region `ap-south-1` using the instance type `AmazonLinux2/7.1.0`.

#### CloudFormation Stack Creation
The process involves creating a CloudFormation stack named `eksctl-kube-cluster-demo-cluster` for the cluster setup. CloudFormation is AWS's service for provisioning and managing stacks of AWS resources.

#### Waiting for Stack Operations
There are several steps where the process waits for the CloudFormation stack creation to complete. This includes waiting for the stack to be in a `CREATE_COMPLETE` state.

#### Kubernetes Configuration
Once the cluster is created, the process updates the `kubeconfig` file which is used to configure `kubectl`, the Kubernetes command-line tool, to interact with the newly created cluster.

#### Enabling Addons
The command attempts to enable addons like `vpc-cni` for networking, but notes that OIDC (OpenID Connect) is disabled, which prevents eksctl from configuring permissions directly. It suggests adding policies after addon creation via the configuration file or pod identity associations.

#### Nodegroup Creation
A separate CloudFormation stack `eksctl-kube-cluster-demo-nodegroup-ng-0e94f6e8` is created for managing the nodegroup. This involves similar waiting steps for the stack operations.

#### Final Configuration and Verification
After all operations, the `kubeconfig` file is saved, allowing interaction with the cluster. The status of the nodes is checked, confirming that 3 nodes have been created and are in a `Ready` state within the nodegroup `ng-0e94f6e8`.

#### Completion
The process concludes with a message indicating the EKS cluster `kube-cluster-demo` has been created, and suggests using `kubectl` commands to interact with the cluster.

This detailed process showcases the complexity of setting up a Kubernetes cluster on AWS using eksctl, involving AWS services like EC2, VPC, CloudFormation, and Kubernetes components. Each step ensures the cluster is properly configured with the right networking, scaling, and node management settings.

When you run the `eksctl create cluster` command, `eksctl` automates the creation of several AWS resources necessary for a fully functional EKS cluster. Here is a comprehensive list of the resources typically created by `eksctl`:

### 1. VPC (Virtual Private Cloud)
- **VPC**: A new VPC is created to host the EKS cluster.

### 2. Subnets
- **Public Subnets**: Typically, three public subnets are created, one in each of three different availability zones (if available).
- **Private Subnets**: Similarly, three private subnets are created, one in each of three different availability zones (if available).

### 3. Internet Gateway
- **Internet Gateway**: An Internet Gateway is attached to the VPC to allow internet access for resources in the public subnets.

### 4. NAT Gateway
- **NAT Gateway**: One or more NAT Gateways are created in the public subnets to allow instances in the private subnets to access the internet.

### 5. Route Tables
- **Public Route Table**: Routes traffic to the Internet Gateway.
- **Private Route Table**: Routes traffic to the NAT Gateway.

### 6. Elastic IPs
- **Elastic IPs**: Elastic IPs are allocated for the NAT Gateways.

### 7. Security Groups
- **Security Groups**: Security Groups are created to control inbound and outbound traffic for the EKS cluster and its nodes.

### 8. IAM Roles and Policies
- **IAM Roles**: IAM roles are created for the EKS cluster control plane and worker nodes.
- **IAM Policies**: Necessary IAM policies are attached to the IAM roles to grant required permissions.

### 9. EKS Cluster
- **EKS Cluster**: The EKS cluster itself is created, which includes the control plane.

### 10. Node Groups
- **Managed Node Groups**: One or more managed node groups are created, which include the EC2 instances (worker nodes) that run the Kubernetes workloads.

### 11. AutoScaling Groups
- **AutoScaling Groups**: AutoScaling Groups are created to manage the EC2 instances in the node groups, ensuring the desired number of instances are running and can scale up or down based on demand.

### 12. Load Balancers
- **Load Balancers**: When you create a Kubernetes Service of type `LoadBalancer`, AWS automatically provisions an Elastic Load Balancer (ELB) to distribute traffic to your application.

### 13. EKS Add-ons
- **EKS Add-ons**: Depending on the configuration, `eksctl` may also install and configure EKS add-ons such as the VPC CNI plugin, CoreDNS, and kube-proxy.

### Summary of Resources Created by `eksctl create cluster`

- **VPC**
- **Public Subnets** (typically 3)
- **Private Subnets** (typically 3)
- **Internet Gateway**
- **NAT Gateway**
- **Route Tables** (public and private)
- **Elastic IPs** (for NAT Gateways)
- **Security Groups**
- **IAM Roles and Policies**
- **EKS Cluster**
- **Managed Node Groups**
- **AutoScaling Groups**
- **Load Balancers** (when creating a Service of type `LoadBalancer`)
- **EKS Add-ons** (e.g., VPC CNI plugin, CoreDNS, kube-proxy)

These resources ensure that your EKS cluster is properly networked, secured, and scalable, providing a robust environment for running your Kubernetes workloads on AWS.

To create an EKS cluster with `eksctl` and verify all the created resources using AWS CLI, follow these steps:

### Step 1: Create the EKS Cluster
Use the `eksctl create cluster` command to create the EKS cluster with the desired configuration:

```sh
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=2 --nodes-min=1 --nodes-max=3 --managed --spot --asg-access
```

### Step 2: Verify the Created Resources
After the cluster is created, you can use various `aws ec2 describe` commands to verify the resources.

1. **Describe VPCs**

    ```sh
    aws ec2 describe-vpcs --region ap-south-1
    ```

2. **Describe Subnets**
    First, get the VPC ID associated with your cluster:

    ```sh
    VPC_ID=$(aws ec2 describe-vpcs --region ap-south-1 --query 'Vpcs[?Tags[?Key==`alpha.eksctl.io/cluster-name` && Value==`my-cluster`]].VpcId' --output text)
    ```

    Then, describe the subnets in the VPC:

    ```sh
    aws ec2 describe-subnets --filters "Name=vpc-id,Values=$VPC_ID" --region ap-south-1
    ```

3. **Describe Internet Gateways**

    ```sh
    aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=$VPC_ID" --region ap-south-1
    ```

4. **Describe NAT Gateways**

    ```sh
    aws ec2 describe-nat-gateways --filter "Name=vpc-id,Values=$VPC_ID" --region ap-south-1
    ```

5. **Describe Route Tables**

    ```sh
    aws ec2 describe-route-tables --filters "Name=vpc-id,Values=$VPC_ID" --region ap-south-1
    ```

6. **Describe Elastic IPs**

    ```sh
    aws ec2 describe-addresses --region ap-south-1
    ```

7. **Describe Security Groups**

    ```sh
    aws ec2 describe-security-groups --filters "Name=vpc-id,Values=$VPC_ID" --region ap-south-1
    ```

8. **Describe AutoScaling Groups**

    ```sh
    aws autoscaling describe-auto-scaling-groups --region ap-south-1
    ```

9. **Describe Load Balancers**
    For Classic Load Balancers (ELB):

    ```sh
    aws elb describe-load-balancers --region ap-south-1
    ```

    For Application Load Balancers (ALB) and Network Load Balancers (NLB):

    ```sh
    aws elbv2 describe-load-balancers --region ap-south-1
    ```

### Summary
By running the `eksctl create cluster` command, you will create an EKS cluster with the specified configuration. You can then use the AWS CLI commands to verify the creation of various resources such as VPCs, subnets, Internet Gateways, NAT Gateways, Route Tables, Elastic IPs, Security Groups, AutoScaling Groups, and Load Balancers.

This approach ensures that you have a comprehensive view of all the resources created by `eksctl` for your EKS cluster.

When you create an EKS cluster using `eksctl` without specifying the AMI (Amazon Machine Image) type, `eksctl` defaults to using the Amazon EKS-optimized Amazon Linux 2 AMI for the node group. This is the recommended AMI for EKS nodes as it is optimized for performance and security.

## Creating an EKS Cluster with Default Node Group

Here is the command to create an EKS cluster with a node group using the default Amazon Linux 2 AMI:

```sh
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=2 --nodes-min=1 --nodes-max=3 --managed --spot --asg-access
```

### Explanation of the Command

- `--name=my-cluster`: Specifies the name of the EKS cluster.
- `--region=ap-south-1`: Specifies the AWS region where the cluster will be created.
- `--node-type=t3.small`: Specifies the EC2 instance type for the nodes.
- `--nodes=2`: Specifies the desired number of nodes in the node group.
- `--nodes-min=1`: Specifies the minimum number of nodes in the node group.
- `--nodes-max=3`: Specifies the maximum number of nodes in the node group.
- `--managed`: Specifies that the node group should be a managed node group.
- `--spot`: Specifies that the node group should use spot instances.
- `--asg-access`: Grants the necessary IAM permissions for the Auto Scaling Group.

## Verifying the Node Group

After creating the cluster, you can verify the node group using the AWS CLI:

```sh
aws eks describe-nodegroup --cluster-name my-cluster --nodegroup-name ng-1 --region ap-south-1
```

### Example Output

The output will show details about the node group, including the AMI type:

```json
{
    "nodegroup": {
        "nodegroupName": "ng-1",
        "nodegroupArn": "arn:aws:eks:ap-south-1:123456789012:nodegroup/my-cluster/ng-1/abcd1234-5678-90ef-ghij-klmnopqrstuv",
        "clusterName": "my-cluster",
        "version": "1.21",
        "releaseVersion": "1.21.2-20210901",
        "createdAt": "2021-09-01T12:34:56.789Z",
        "status": "ACTIVE",
        "capacityType": "SPOT",
        "scalingConfig": {
            "minSize": 1,
            "maxSize": 3,
            "desiredSize": 2
        },
        "instanceTypes": [
            "t3.small"
        ],
        "subnets": [
            "subnet-12345678",
            "subnet-23456789",
            "subnet-34567890"
        ],
        "amiType": "AL2_x86_64",  // Amazon Linux 2 AMI
        "nodeRole": "arn:aws:iam::123456789012:role/eksctl-my-cluster-nodegroup-ng-1-NodeInstanceRole-1A2B3C4D5E6F",
        "labels": {
            "alpha.eksctl.io/cluster-name": "my-cluster",
            "alpha.eksctl.io/nodegroup-name": "ng-1"
        },
        "tags": {
            "eksctl.cluster.k8s.io/v1alpha1/cluster-name": "my-cluster",
            "eksctl.io/v1alpha2/nodegroup-name": "ng-1"
        }
    }
}
```

## Summary

When you create an EKS cluster using `eksctl` without specifying the AMI type, it defaults to using the Amazon EKS-optimized Amazon Linux 2 AMI for the node group. This ensures that the nodes are optimized for performance and security. You can verify the node group and its configuration using the AWS CLI.

When you create an EKS cluster using `eksctl`, it uses AWS CloudFormation under the hood to provision and manage the necessary AWS resources. `eksctl` generates CloudFormation templates and deploys them to create and configure the EKS cluster and its associated resources.

## How CloudFormation Stacks Are Created and Triggered by `eksctl`

1. **Generate CloudFormation Templates**: `eksctl` generates CloudFormation templates based on the configuration specified in the `eksctl create cluster` command or configuration file.
2. **Deploy CloudFormation Stacks**: `eksctl` deploys these CloudFormation templates to create the required AWS resources.
3. **Manage Resources**: The CloudFormation stacks manage the lifecycle of the resources, ensuring they are created, updated, and deleted as needed.

## Steps to Create an EKS Cluster and View CloudFormation Stacks

### 1. Create an EKS Cluster Using `eksctl`

Run the following command to create an EKS cluster:

```sh
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=2 --nodes-min=1 --nodes-max=3 --managed --spot --asg-access
```

### 2. View CloudFormation Stacks in the AWS Management Console

Open the CloudFormation Console:

- Go to the AWS CloudFormation Console.

#### View Stacks:

You will see multiple stacks created by `eksctl`. These stacks typically include:

- A stack for the EKS cluster control plane.
- A stack for the VPC and networking resources.
- A stack for the node group(s).

#### Stack Names:

The stack names usually follow a pattern like `eksctl-my-cluster-cluster`, `eksctl-my-cluster-nodegroup-ng-1`, etc.

### 3. Describe CloudFormation Stacks Using AWS CLI

You can also use the AWS CLI to describe the CloudFormation stacks:

```sh
# List all CloudFormation stacks
aws cloudformation list-stacks --region ap-south-1

# Describe a specific stack
aws cloudformation describe-stacks --stack-name eksctl-my-cluster-cluster --region ap-south-1
```

#### Example Output

The output will show details about the CloudFormation stack, including the resources it manages:

```json
{
    "Stacks": [
        {
            "StackId": "arn:aws:cloudformation:ap-south-1:123456789012:stack/eksctl-my-cluster-cluster/abcd1234-5678-90ef-ghij-klmnopqrstuv",
            "StackName": "eksctl-my-cluster-cluster",
            "Description": "EKS cluster stack",
            "Parameters": [
                {
                    "ParameterKey": "ClusterName",
                    "ParameterValue": "my-cluster"
                },
                {
                    "ParameterKey": "VpcId",
                    "ParameterValue": "vpc-12345678"
                }
            ],
            "CreationTime": "2021-09-01T12:34:56.789Z",
            "StackStatus": "CREATE_COMPLETE",
            "StackResources": [
                {
                    "LogicalResourceId": "ControlPlane",
                    "PhysicalResourceId": "arn:aws:eks:ap-south-1:123456789012:cluster/my-cluster",
                    "ResourceType": "AWS::EKS::Cluster",
                    "Timestamp": "2021-09-01T12:34:56.789Z",
                    "ResourceStatus": "CREATE_COMPLETE"
                },
                {
                    "LogicalResourceId": "Vpc",
                    "PhysicalResourceId": "vpc-12345678",
                    "ResourceType": "AWS::EC2::VPC",
                    "Timestamp": "2021-09-01T12:34:56.789Z",
                    "ResourceStatus": "CREATE_COMPLETE"
                }
            ]
        }
    ]
}
```

## Summary

When you create an EKS cluster using `eksctl`, it generates and deploys CloudFormation templates to provision and manage the necessary AWS resources. You can view and manage these CloudFormation stacks using the AWS Management Console or AWS CLI. This approach ensures that all resources are created and managed in a consistent and automated manner.