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
eksctl create cluster --name=my-cluster --region=ap-south-1 --node-type=t3.small --nodes=2 --nodes-min=1 --nodes-max=3 --managed --spot
```

**Using a YAML Configuration for More Control:**

Create a YAML file (`cluster-config.yaml`) with your cluster configuration:

```yaml
apiVersion: eksctl.io/v1alpha5
kind: ClusterConfig
metadata:
    name: my-cluster
    region: us-west-2
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
nginx-service   LoadBalancer   10.100.200.100   a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6-1234567890.us-west-2.elb.amazonaws.com   80:31234/TCP   5m
```

Open a web browser and navigate to the `EXTERNAL-IP` address to see the Nginx welcome page.

## Summary

By following these steps, you will:

- Set up an AWS environment and create an EKS cluster.
- Deploy an Nginx application on Kubernetes using Deployment and Service manifests.
- Expose the Nginx application to the internet using a LoadBalancer Service.
