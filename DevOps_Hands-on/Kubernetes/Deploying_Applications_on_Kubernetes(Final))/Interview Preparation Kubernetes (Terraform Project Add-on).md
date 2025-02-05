## Interview Preparation Kubernetes (Terraform Project Add-on)

### 1. Understanding the Script Purpose
**Objective:** The script automates the process of initializing Terraform, planning and applying the infrastructure changes, updating the kubeconfig for EKS, and applying Kubernetes configurations.

### 2. Key Components of the Script
- **Terraform Commands:** Initializes Terraform, plans the infrastructure changes, and applies them.
- **AWS CLI Command:** Updates the kubeconfig file to interact with the EKS cluster.
- **Custom Script:** Executes a custom script to apply Kubernetes configurations.

### 3. Terraform Commands
- **Initialize Terraform:**
    ```sh
    terraform init
    ```
    Initializes the Terraform configuration, downloads provider plugins, and prepares the working directory.

- **Plan Infrastructure Changes:**
    ```sh
    terraform plan
    ```
    Creates an execution plan, showing what actions Terraform will take to achieve the desired state.

- **Apply Infrastructure Changes:**
    ```sh
    terraform apply -auto-approve
    ```
    Applies the changes required to reach the desired state of the configuration. The `-auto-approve` flag automatically approves the plan without prompting for confirmation.

### 4. AWS CLI Command
- **Update kubeconfig for EKS:**
    ```sh
    aws eks update-kubeconfig --name my-cluster --region ap-south-1
    ```
    Updates the kubeconfig file to include the configuration for the specified EKS cluster. This allows `kubectl` to interact with the EKS cluster.

### 5. Custom Script Execution
- **Apply Kubernetes Configurations:**
    ```sh
    ./kube_apply.sh
    ```
    Executes the `kube_apply.sh` script, which is assumed to contain `kubectl` commands to apply Kubernetes configurations.

### Syntax of Various Commands
#### Terraform Commands
- **Initialize Terraform:**
    ```sh
    terraform init
    ```
    Initializes the Terraform configuration.

- **Plan Infrastructure Changes:**
    ```sh
    terraform plan
    ```
    Creates an execution plan.

- **Apply Infrastructure Changes:**
    ```sh
    terraform apply -auto-approve
    ```
    Applies the changes with automatic approval.

#### AWS CLI Command
- **Update kubeconfig for EKS:**
    ```sh
    aws eks update-kubeconfig --name my-cluster --region ap-south-1
    ```
    Updates the kubeconfig file for the specified EKS cluster.

#### Custom Script Execution
- **Execute Custom Script:**
    ```sh
    ./kube_apply.sh
    ```
    Executes the `kube_apply.sh` script.

### Example Questions
- **What is the purpose of the `terraform init` command in the script?**
    - It initializes the Terraform configuration, downloads provider plugins, and prepares the working directory.

- **How does the script plan the infrastructure changes using Terraform?**
    - It uses the `terraform plan` command to create an execution plan, showing what actions Terraform will take to achieve the desired state.

- **What does the `terraform apply -auto-approve` command do?**
    - It applies the changes required to reach the desired state of the configuration with automatic approval, without prompting for confirmation.

- **How does the script update the kubeconfig file for the EKS cluster?**
    - It uses the `aws eks update-kubeconfig --name my-cluster --region ap-south-1` command to update the kubeconfig file, allowing `kubectl` to interact with the EKS cluster.

- **What is the purpose of the `./kube_apply.sh` command in the script?**
    - It executes the `kube_apply.sh` script, which is assumed to contain `kubectl` commands to apply Kubernetes configurations.

The `kube_apply.sh` script is designed to apply Kubernetes configurations and update the frontend application with the backend service's LoadBalancer DNS. This script is executed after the AWS infrastructure has been provisioned using Terraform and the EKS cluster is set up.

### Workflow Steps:

#### Apply Kubernetes Configurations:
The script starts by applying Kubernetes configurations using `kubectl apply` commands for the MySQL secret, backend service, and backend deployment.
```sh
kubectl apply -f mysql-secret.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f backend-deployment.yaml
```

#### Set Current Directory:
The script stores the current working directory in the `CURR_DIR` variable.
```sh
CURR_DIR=$(pwd)
```

#### Retrieve Backend LoadBalancer DNS:
The script retrieves the DNS name of the backend service's LoadBalancer and exports it as an environment variable `BACKEND_LOADBALANCER_DNS`.
```sh
export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
echo "Backend LoadBalancer DNS: $BACKEND_LOADBALANCER_DNS"
```

#### Update Frontend Application:
The script uses the `sed` command to replace the placeholder `BACKEND_LOADBALANCER_DNS` in the `App.js.temp` file with the actual DNS name of the backend LoadBalancer. The updated file is saved as `App.js`.
```sh
sed "s|BACKEND_LOADBALANCER_DNS|$BACKEND_LOADBALANCER_DNS|g" "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js.temp" > "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js"
```

#### Change to Frontend Directory:
The script changes the working directory to the frontend directory.
```sh
cd "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend"
```

#### Commit and Push Changes:
The script commits the changes to the `App.js` file and pushes them to the main branch of the Git repository.
```sh
git add src/App.js
git commit -m "Update App.js with Backend LoadBalancer DNS"
git push origin main
```

#### Wait for Changes to Propagate:
The script waits for 240 seconds (4 minutes) to allow the changes to propagate.
```sh
sleep 240
```

#### Return to Original Directory:
The script changes back to the original working directory stored in `CURR_DIR`.
```sh
cd $CURR_DIR
```

#### Apply Frontend Configurations:
```sh
kubectl apply -f frontend-service.yaml
kubectl apply -f frontend-deployment.yaml
```

#### Apply Horizontal Pod Autoscaler (HPA) Configuration:
The script applies the HPA configuration for the backend, which automatically scales the number of pods based on CPU utilization or other metrics.
```sh
kubectl apply -f hpa-backend.yaml
```

#### Apply Cluster Autoscaler Configuration:
The script applies the configuration for the Cluster Autoscaler, which automatically adjusts the number of nodes in the cluster based on the resource requirements of the pods.
```sh
kubectl apply -f cluster-autoscaler.yaml
```

#### Apply Prometheus and Grafana Configurations:
The script applies the configurations for Prometheus, a monitoring and alerting toolkit.
```sh
kubectl apply -f prometheus-config.yaml
kubectl apply -f prometheus-serviceaccount.yaml
kubectl apply -f prometheus-clusterrole.yaml
kubectl apply -f prometheus-clusterrolebinding.yaml
kubectl apply -f prometheus-deployment.yaml
kubectl apply -f prometheus-service.yaml
```
The script sets up port forwarding for Prometheus to access it locally.
```sh
kubectl port-forward $(kubectl get pods -l app=prometheus -o jsonpath='{.items[0].metadata.name}') 9090:9090 > /dev/null 2>&1 &
```

#### Apply Grafana Configurations:
The script applies the configurations for Grafana, a data visualization and monitoring tool.
```sh
kubectl apply -f grafana-datasource.yaml
kubectl apply -f grafana-deployment.yaml
kubectl apply -f grafana-service.yaml
```
The script sets up port forwarding for Grafana to access it locally.
```sh
kubectl port-forward $(kubectl get pods -l app=grafana -o jsonpath='{.items[0].metadata.name}') 3000:3000 > /dev/null 2>&1 &
```

#### Apply EFK Stack Configurations:
The script applies the configurations for the EFK stack (Elasticsearch, Fluentd, Kibana) for logging and log management.
```sh
kubectl apply -f elasticsearch-deployment.yaml
kubectl apply -f elasticsearch-service.yaml
kubectl apply -f fluentd-configmap.yaml
kubectl apply -f fluentd-daemonset.yaml
kubectl apply -f kibana-deployment.yaml
kubectl apply -f kibana-service.yaml
```
The script sets up port forwarding for Kibana to access it locally.
```sh
kubectl port-forward $(kubectl get pods -l app=kibana -o jsonpath='{.items[0].metadata.name}') 5601:5601 > /dev/null 2>&1 &
```

### Summary of Extended Workflow:
- **Apply Frontend Configurations:** Deploys the frontend service and deployment.
- **Apply HPA Configuration:** Sets up the Horizontal Pod Autoscaler for the backend.
- **Apply Cluster Autoscaler Configuration:** Configures the Cluster Autoscaler.
- **Apply Prometheus Configurations:** Deploys Prometheus for monitoring and sets up port forwarding.
- **Apply Grafana Configurations:** Deploys Grafana for data visualization and sets up port forwarding.
- **Apply EFK Stack Configurations:** Deploys Elasticsearch, Fluentd, and Kibana for logging and sets up port forwarding for Kibana.

### Summary of Workflow:
- **Apply Kubernetes Configurations:** Deploys the MySQL secret, backend service, and backend deployment.
- **Retrieve Backend LoadBalancer DNS:** Gets the DNS name of the backend service's LoadBalancer.
- **Update Frontend Application:** Replaces the placeholder in the frontend application with the actual DNS name.
- **Commit and Push Changes:** Commits and pushes the updated frontend application to the Git repository.
- **Wait for Propagation:** Waits for the changes to propagate.
- **Apply Frontend Configurations:** Applies the frontend configurations (implied step).

## Step By Step applying Kubernetes Configurations

### mysql-secret.yaml

#### 1. Understanding the Purpose of the File
**Objective:** The `mysql-secret.yaml` file is used to create a Kubernetes Secret that securely stores the MySQL root password. Secrets are used to manage sensitive information such as passwords, OAuth tokens, and SSH keys.

#### 2. Key Components of the YAML File
- **apiVersion:** Specifies the version of the Kubernetes API to use.
    ```yaml
    apiVersion: v1
    ```
- **kind:** Specifies the type of Kubernetes resource.
    ```yaml
    kind: Secret
    ```
- **metadata:** Contains metadata about the resource, such as name.
    ```yaml
    metadata:
        name: mysql-secret
    ```
- **type:** Specifies the type of secret. In this case, it is an Opaque secret, which means it contains arbitrary user-defined data.
    ```yaml
    type: Opaque
    ```
- **data:** Contains the secret data in base64-encoded format.
    ```yaml
    data:
        MYSQL_ROOT_PASSWORD: cGFzc3dvcmQ=  # base64 encoded value of "password"
    ```

#### 3. Base64 Encoding
**Base64 Encoding:** Kubernetes requires secret data to be base64-encoded. For example, the base64-encoded value of "password" is `cGFzc3dvcmQ=`.

#### 4. Creating and Managing Secrets
- **Create Secret from YAML File:**
    ```sh
    kubectl apply -f mysql-secret.yaml
    ```
    This command creates the secret defined in the `mysql-secret.yaml` file.

- **View Secrets:**
    ```sh
    kubectl get secrets
    ```
    This command lists all secrets in the current namespace.

- **Describe Secret:**
    ```sh
    kubectl describe secret mysql-secret
    ```
    This command provides detailed information about the `mysql-secret`.

- **Decode Secret Data:**
    ```sh
    echo 'cGFzc3dvcmQ=' | base64 --decode
    ```
    This command decodes the base64-encoded secret data.

#### Example Questions
- **What is the purpose of the `mysql-secret.yaml` file?**
    The `mysql-secret.yaml` file is used to create a Kubernetes Secret that securely stores the MySQL root password.

- **What does the `type: Opaque` field specify in the secret definition?**
    The `type: Opaque` field specifies that the secret contains arbitrary user-defined data.

- **Why is the MySQL root password base64-encoded in the secret?**
    Kubernetes requires secret data to be base64-encoded to ensure it is stored in a secure and standardized format.

- **How do you create the secret defined in the `mysql-secret.yaml` file?**
    You can create the secret by running the command `kubectl apply -f mysql-secret.yaml`.

- **How can you view the details of the `mysql-secret`?**
    You can view the details of the secret by running the command `kubectl describe secret mysql-secret`.

### backend-service.yaml

#### 1. Understanding the Purpose of the File
**Objective:** The `backend-service.yaml` file is used to create a Kubernetes Service that exposes the backend application to external traffic. The service type is `LoadBalancer`, which provisions an external load balancer to route traffic to the backend pods.

#### 2. Key Components of the YAML File
- **apiVersion:** Specifies the version of the Kubernetes API to use.
    ```yaml
    apiVersion: v1
    ```
- **kind:** Specifies the type of Kubernetes resource.
    ```yaml
    kind: Service
    ```
- **metadata:** Contains metadata about the resource, such as name.
    ```yaml
    metadata:
        name: backend-service
    ```
- **spec:** Defines the desired state of the service.
    ```yaml
    spec:
        selector:
            app: backend
        ports:
            - protocol: TCP
                port: 3000
                targetPort: 3000
        type: LoadBalancer
    ```

#### 3. Detailed Explanation of Key Fields
- **selector:** Specifies the label selector used to identify the pods that the service targets. In this case, it targets pods with the label `app: backend`.
    ```yaml
    selector:
        app: backend
    ```
- **ports:** Defines the ports that the service exposes.
    - **protocol:** Specifies the protocol used by the service (e.g., TCP).
    - **port:** Specifies the port on which the service is exposed.
    - **targetPort:** Specifies the port on the backend pods to which traffic is forwarded.
    ```yaml
    ports:
        - protocol: TCP
            port: 3000
            targetPort: 3000
    ```
- **type:** Specifies the type of service. The `LoadBalancer` type provisions an external load balancer to route traffic to the backend pods.
    ```yaml
    type: LoadBalancer
    ```

#### 4. Creating and Managing Services
- **Create Service from YAML File:**
    ```sh
    kubectl apply -f backend-service.yaml
    ```
    This command creates the service defined in the `backend-service.yaml` file.
- **View Services:**
    ```sh
    kubectl get services
    ```
    This command lists all services in the current namespace.
- **Describe Service:**
    ```sh
    kubectl describe service backend-service
    ```
    This command provides detailed information about the `backend-service`.
- **Delete Service:**
    ```sh
    kubectl delete -f backend-service.yaml
    ```
    This command deletes the service defined in the `backend-service.yaml` file.

#### Example Questions
- **What is the purpose of the `backend-service.yaml` file?**
    - The `backend-service.yaml` file is used to create a Kubernetes Service that exposes the backend application to external traffic using a load balancer.
- **What does the `selector` field specify in the service definition?**
    - The `selector` field specifies the label selector used to identify the pods that the service targets. In this case, it targets pods with the label `app: backend`.
- **What is the significance of the `type: LoadBalancer` field in the service definition?**
    - The `type: LoadBalancer` field provisions an external load balancer to route traffic to the backend pods, making the service accessible from outside the cluster.
- **How do you create the service defined in the `backend-service.yaml` file?**
    - You can create the service by running the command `kubectl apply -f backend-service.yaml`.
- **How can you view the details of the `backend-service`?**
    - You can view the details of the service by running the command `kubectl describe service backend-service`.

### backend-deployment.yaml
