# Step-by-Step Process for Kubernetes Hands-On Exercises
## Introduction to Kubernetes

- Understand the basics of Kubernetes, its architecture, and key components (Pods, Nodes, Services, Deployments, etc.).
- Set up a local Kubernetes cluster using Minikube or Kind (Kubernetes in Docker).

## Setting Up a Kubernetes Cluster on AWS

- Use Amazon EKS (Elastic Kubernetes Service) to set up a managed Kubernetes cluster.
- Install and configure kubectl to interact with your EKS cluster.
- Create an EKS cluster using the AWS Management Console, AWS CLI, or Infrastructure as Code (IaC) tools like Terraform.

## Deploying Applications on Kubernetes

- Create and deploy a simple application (e.g., a static website or a web server) using Kubernetes manifests (YAML files).
- Understand and use key Kubernetes objects: Pods, ReplicaSets, Deployments, and Services.
- Deploy your current project (e.g., the static website hosted on EC2) to the Kubernetes cluster.

## Configuring Networking in Kubernetes

- Set up Services to expose your application (ClusterIP, NodePort, LoadBalancer).
- Use Ingress controllers to manage external access to your services.
- Configure DNS within the cluster using CoreDNS.

## Managing Storage in Kubernetes

- Understand Persistent Volumes (PVs) and Persistent Volume Claims (PVCs).
- Use AWS EBS (Elastic Block Store) for persistent storage in your EKS cluster.
- Deploy a stateful application (e.g., a database) that requires persistent storage.

## Scaling Applications in Kubernetes

- Use Horizontal Pod Autoscaler (HPA) to scale your application based on CPU/memory usage.
- Configure Cluster Autoscaler to automatically adjust the number of nodes in your cluster.

## Monitoring and Logging in Kubernetes

- Set up monitoring using Prometheus and Grafana.
- Configure logging using Fluentd, Elasticsearch, and Kibana (EFK stack).
- Use CloudWatch for monitoring and logging in AWS.

## Securing Kubernetes

- Implement Role-Based Access Control (RBAC) to manage permissions.
- Use Network Policies to control traffic between Pods.
- Secure your cluster with best practices (e.g., using Secrets, configuring Pod Security Policies).

## CI/CD with Kubernetes

- Integrate your Kubernetes deployments with a CI/CD pipeline using tools like Jenkins, GitLab CI, or GitHub Actions.
- Automate the deployment of your application to the Kubernetes cluster.

## Advanced Topics

- Understand and use Helm for package management in Kubernetes.
- Explore Kubernetes Operators for managing complex applications.
- Learn about Service Mesh (e.g., Istio) for advanced networking and observability.

# Hands-On Exercises

## Set Up a Local Kubernetes Cluster

- Install Minikube or Kind.
- Deploy a sample application.

## Create an EKS Cluster

- Use the AWS Management Console or AWS CLI to create an EKS cluster.
- Configure kubectl to connect to your EKS cluster.

## Deploy Your Application

- Create Kubernetes manifests for your application (e.g., static website).
- Deploy the application to your EKS cluster.

## Expose Your Application

- Create a Service to expose your application.
- Set up an Ingress controller and create an Ingress resource.

## Set Up Persistent Storage

- Create a Persistent Volume and Persistent Volume Claim.
- Deploy a stateful application that uses the persistent storage.

## Implement Autoscaling

- Configure Horizontal Pod Autoscaler (HPA) for your application.
- Set up Cluster Autoscaler for your EKS cluster.

## Set Up Monitoring and Logging

- Deploy Prometheus and Grafana for monitoring.
- Set up the EFK stack for logging.

## Secure Your Cluster

- Implement RBAC and Network Policies.
- Use Secrets to manage sensitive information.

## Integrate CI/CD

- Set up a CI/CD pipeline to automate the deployment of your application to the Kubernetes cluster.

## Explore Advanced Topics

- Use Helm to manage your application deployments.
- Learn about Kubernetes Operators and Service Mesh.

# Resources

- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Amazon EKS Documentation](https://docs.aws.amazon.com/eks/)
- [Kubernetes Hands-On Labs](https://www.katacoda.com/courses/kubernetes)
- [Kubernetes the Hard Way](https://github.com/kelseyhightower/kubernetes-the-hard-way)

## Day 1: Introduction to Kubernetes and Setting Up a Local Cluster

**Objective:** Understand Kubernetes basics and set up a local Kubernetes cluster.

**Tasks:**
- Read about Kubernetes architecture and key components (Pods, Nodes, Services, Deployments).
- Install Minikube or Kind on your local machine.
- Deploy a simple application (e.g., Nginx) using Kubernetes manifests (YAML files).
- Verify the deployment and access the application.

## Day 2: Setting Up a Kubernetes Cluster on AWS (EKS)

**Objective:** Set up a managed Kubernetes cluster on AWS using EKS.

**Tasks:**
- Read about Amazon EKS and its benefits.
- Create an EKS cluster using the AWS Management Console or AWS CLI.
- Install and configure kubectl to interact with your EKS cluster.
- Deploy a sample application to the EKS cluster.

## Day 3: Deploying Applications on Kubernetes

**Objective:** Deploy your current project (e.g., static website) to the Kubernetes cluster.

**Tasks:**
- Create Kubernetes manifests for your application (Deployment, Service).
- Deploy the application to your EKS cluster.
- Verify the deployment and access the application.

## Day 4: Configuring Networking in Kubernetes

**Objective:** Set up Services and Ingress to expose your application.

**Tasks:**
- Create a Service to expose your application (ClusterIP, NodePort, LoadBalancer).
- Set up an Ingress controller (e.g., Nginx Ingress) and create an Ingress resource.
- Configure DNS within the cluster using CoreDNS.

## Day 5: Managing Storage in Kubernetes

**Objective:** Set up persistent storage for stateful applications.

**Tasks:**
- Read about Persistent Volumes (PVs) and Persistent Volume Claims (PVCs).
- Create a PV and PVC using AWS EBS (Elastic Block Store).
- Deploy a stateful application (e.g., MySQL) that uses the persistent storage.

## Day 6: Scaling Applications in Kubernetes

**Objective:** Implement autoscaling for your application.

**Tasks:**
- Read about Horizontal Pod Autoscaler (HPA) and Cluster Autoscaler.
- Configure HPA to scale your application based on CPU/memory usage.
- Set up Cluster Autoscaler to automatically adjust the number of nodes in your cluster.

## Day 7: Setting Up Monitoring and Logging

**Objective:** Monitor and log your Kubernetes cluster and applications.

**Tasks:**
- Deploy Prometheus and Grafana for monitoring.
- Set up the EFK stack (Elasticsearch, Fluentd, Kibana) for logging.
- Create dashboards and alerts in Grafana.

## Day 8: Securing Your Cluster

**Objective:** Implement security best practices in your Kubernetes cluster.

**Tasks:**
- Read about Role-Based Access Control (RBAC) and Network Policies.
- Implement RBAC to manage permissions.
- Use Network Policies to control traffic between Pods.
- Secure sensitive information using Secrets.

## Day 9: Integrating CI/CD

**Objective:** Automate the deployment of your application using a CI/CD pipeline.

**Tasks:**
- Set up a CI/CD pipeline using Jenkins, GitLab CI, or GitHub Actions.
- Automate the build, test, and deployment process for your application.
- Deploy the application to your Kubernetes cluster as part of the CI/CD pipeline.

## Day 10: Exploring Advanced Topics

**Objective:** Learn about advanced Kubernetes topics and tools.

**Tasks:**
- Read about Helm and its benefits for package management.
- Use Helm to manage your application deployments.
- Learn about Kubernetes Operators and their use cases.
- Explore Service Mesh (e.g., Istio) for advanced networking and observability.

# Resources

- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Amazon EKS Documentation](https://docs.aws.amazon.com/eks/)
- [Kubernetes Hands-On Labs](https://www.katacoda.com/courses/kubernetes)
- [Kubernetes the Hard Way](https://github.com/kelseyhightower/kubernetes-the-hard-way)

# Day 1: Introduction to Kubernetes and Setting Up a Local Cluster
Objective: Understand Kubernetes basics and set up a local Kubernetes cluster.

**Notes and Hands-On Guide**

1. Read about Kubernetes Architecture and Key Components

**Kubernetes Architecture:**

- **Master Node:** Manages the Kubernetes cluster. It includes components like the API server, scheduler, controller manager, and etcd (key-value store).
- **Worker Nodes:** Run the application workloads. Each node includes components like the kubelet, kube-proxy, and container runtime (e.g., Docker).

**Key Components:**

- **Pods:** The smallest deployable units in Kubernetes, which can contain one or more containers.
- **Nodes:** Machines (virtual or physical) that run the Kubernetes cluster.
- **Services:** Abstracts and exposes a set of Pods as a network service.
- **Deployments:** Manages the deployment and scaling of Pods.

2. Install Minikube or Kind on Your Local Machine

**Minikube Installation:**

1. **Install Minikube:**
  - Follow the installation guide for your operating system: https://minikube.sigs.k8s.io/docs/start/
  - Example for Windows using Chocolatey:

  ```sh
  choco install minikube
  ```

2. **Start Minikube:**

  ```sh
  minikube start
  ```

**Kind Installation:**

1. **Install Kind:**
  - Follow the installation guide: https://kind.sigs.k8s.io/docs/user/quick-start/
  - Example for Windows using Chocolatey:

  ```sh
  choco install kind
  ```

2. **Create a Kind Cluster:**

  ```sh
  kind create cluster
  ```

3. **Deploy a Simple Application** (e.g., Nginx) Using Kubernetes Manifests (YAML Files)

**Create Kubernetes Manifests:**

1. Create a Deployment YAML file (nginx-deployment.yaml):

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

2. Create a Service YAML file (nginx-service.yaml):

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
    type: NodePort
  ```

4. Verify the Deployment and Access the Application

Deploy the Application:

1. Apply the Deployment and Service Manifests:

  ```sh
  kubectl apply -f nginx-deployment.yaml
  kubectl apply -f nginx-service.yaml
  ```

2. Verify the Deployment:

  ```sh
  kubectl get deployments
  kubectl get pods
  kubectl get services
  ```

3. Access the Application:
  - For Minikube:

    ```sh
    minikube service nginx-service
    ```

  - For Kind:
    Get the NodePort assigned to the service:

    ```sh
    kubectl get service nginx-service
    ```

    Access the application using the Node's IP address and the NodePort.

Summary

By the end of Day 1, you should have:

- A basic understanding of Kubernetes architecture and key components.
- Installed Minikube or Kind on your local machine.
- Deployed a simple Nginx application using Kubernetes manifests.
- Verified the deployment and accessed the application.

This hands-on exercise will give you a solid foundation in Kubernetes and prepare you for more advanced topics in the following days.

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

## References:
- [eksctl Installation Guide](https://eksctl.io/introduction/#installation)
- [Amazon EKS Cluster Setup using eksctl | by Subbarami Reddy | Medium](https://medium.com/@subbarami.reddy/amazon-eks-cluster-setup-using-eksctl-1a2b3c4d5e6f)

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

## References:
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
eksctl create cluster --name=my-cluster --region=us-west-2
```
**Specify Node Type and Count:**
```bash
eksctl create cluster --name=my-cluster --region=us-west-2 --node-type=t3.medium --nodes=3 --nodes-min=2 --nodes-max=4
```
**Using YAML Configuration:**
Create a YAML file (e.g., `cluster.yaml`) with your cluster configuration:
```yaml
apiVersion: eksctl.io/v1alpha5
kind: ClusterConfig
metadata:
    name: my-cluster
    region: us-west-2
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
eksctl delete cluster --name=my-cluster --region=us-west-2
```

## References:
- [Get started with Amazon EKS – eksctl - Amazon EKS](https://docs.aws.amazon.com/eks/latest/userguide/getting-started-eksctl.html)
- [How To Create AWS EKS Cluster Using Eksctl - devopscube.com](https://devopscube.com/create-eks-cluster-eksctl/)
- [Create an Amazon EKS cluster - Amazon EKS](https://docs.aws.amazon.com/eks/latest/userguide/create-cluster.html)

These steps should guide you through installing eksctl and awscli, configuring your AWS environment, and creating an EKS cluster using eksctl. Always refer to the latest documentation for the most current instructions and best practices.