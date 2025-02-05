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

#### 1. Understanding the Purpose of the File
**Objective:** The `backend-deployment.tpl.yaml` file is a Kubernetes Deployment template used to deploy the backend application. It includes an init container to initialize the MySQL database and sets up the main application containers.

#### 2. Key Components of the YAML File
- **apiVersion:** Specifies the version of the Kubernetes API to use.
    ```yaml
    apiVersion: apps/v1
    ```
- **kind:** Specifies the type of Kubernetes resource.
    ```yaml
    kind: Deployment
    ```
- **metadata:** Contains metadata about the resource, such as name.
    ```yaml
    metadata:
        name: backend
    ```
- **spec:** Defines the desired state of the deployment.
    ```yaml
    spec:
        replicas: 2
        selector:
            matchLabels:
                app: backend
        template:
            metadata:
                labels:
                    app: backend
            spec:
                initContainers:
                - name: init-mysql
                    image: mysql:8.0
                    env:
                    - name: MYSQL_ROOT_PASSWORD
                        valueFrom:
                            secretKeyRef:
                                name: mysql-secret
                                key: MYSQL_ROOT_PASSWORD
                    - name: DB_HOST
                        value: "${db_host}"
                    - name: DB_PORT
                        value: "${db_port}"
                    volumeMounts:
                    - name: init-sql
                        mountPath: /docker-entrypoint-initdb.d
    ```

#### 3. Detailed Explanation of Key Fields
- **replicas:** Specifies the number of pod replicas to run.
    ```yaml
    replicas: 2
    ```
- **selector:** Defines how the Deployment finds which pods to manage.
    ```yaml
    selector:
        matchLabels:
            app: backend
    ```
- **template:** Specifies the pod template used to create new pods.
    ```yaml
    template:
        metadata:
            labels:
                app: backend
        spec:
            initContainers:
            - name: init-mysql
                image: mysql:8.0
                env:
                - name: MYSQL_ROOT_PASSWORD
                    valueFrom:
                        secretKeyRef:
                            name: mysql-secret
                            key: MYSQL_ROOT_PASSWORD
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_PORT
                    value: "${db_port}"
                volumeMounts:
                - name: init-sql
                    mountPath: /docker-entrypoint-initdb.d
    ```

#### 4. Init Containers
**initContainers:** Specifies the init containers that run before the main application containers. In this case, the `init-mysql` container initializes the MySQL database.
```yaml
initContainers:
- name: init-mysql
    image: mysql:8.0
    env:
    - name: MYSQL_ROOT_PASSWORD
        valueFrom:
            secretKeyRef:
                name: mysql-secret
                key: MYSQL_ROOT_PASSWORD
    - name: DB_HOST
        value: "${db_host}"
    - name: DB_PORT
        value: "${db_port}"
    volumeMounts:
    - name: init-sql
        mountPath: /docker-entrypoint-initdb.d
```

#### 5. Environment Variables
**env:** Specifies environment variables for the init container. The `MYSQL_ROOT_PASSWORD` is retrieved from a Kubernetes Secret, and `DB_HOST` and `DB_PORT` are template variables.
```yaml
env:
- name: MYSQL_ROOT_PASSWORD
    valueFrom:
        secretKeyRef:
            name: mysql-secret
            key: MYSQL_ROOT_PASSWORD
- name: DB_HOST
    value: "${db_host}"
- name: DB_PORT
    value: "${db_port}"
```

#### 6. Volume Mounts
**volumeMounts:** Specifies the volumes to mount into the init container. The `init-sql` volume is mounted at `/docker-entrypoint-initdb.d`.
```yaml
volumeMounts:
- name: init-sql
    mountPath: /docker-entrypoint-initdb.d
```

#### 7. Creating and Managing Deployments
- **Create Deployment from YAML File:**
    ```sh
    kubectl apply -f backend-deployment.yaml
    ```
    This command creates the deployment defined in the `backend-deployment.yaml` file.
- **View Deployments:**
    ```sh
    kubectl get deployments
    ```
    This command lists all deployments in the current namespace.
- **Describe Deployment:**
    ```sh
    kubectl describe deployment backend
    ```
    This command provides detailed information about the backend deployment.
- **Delete Deployment:**
    ```sh
    kubectl delete -f backend-deployment.yaml
    ```
    This command deletes the deployment defined in the `backend-deployment.yaml` file.

#### Example Questions
- **What is the purpose of the `backend-deployment.tpl.yaml` file?**
    The `backend-deployment.tpl.yaml` file is a Kubernetes Deployment template used to deploy the backend application, including an init container to initialize the MySQL database.
- **What does the `replicas` field specify in the deployment definition?**
    The `replicas` field specifies the number of pod replicas to run.
- **What is the role of the `initContainers` section in the deployment?**
    The `initContainers` section specifies the init containers that run before the main application containers. In this case, the `init-mysql` container initializes the MySQL database.
- **How do you create the deployment defined in the `backend-deployment.tpl.yaml` file?**
    You can create the deployment by running the command `kubectl apply -f backend-deployment.yaml`.
- **How can you view the details of the backend deployment?**
    You can view the details of the deployment by running the command `kubectl describe deployment backend`.

### Update frontend-service with backend load balancer link

1. **Understanding the Purpose of the File**
    - **Objective**: The `kube_apply.sh` script automates the process of applying Kubernetes configurations, retrieving the backend service's LoadBalancer DNS, updating the frontend application with this DNS, and committing the changes to a Git repository.

2. **Key Components of the Script**
    - **Current Directory**: Stores the current working directory in a variable.
      ```sh
      CURR_DIR=$(pwd)
      ```
    - **Retrieve Backend LoadBalancer DNS**: Retrieves the DNS name of the backend service's LoadBalancer and exports it as an environment variable.
      ```sh
      export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
      echo "Backend LoadBalancer DNS: $BACKEND_LOADBALANCER_DNS"
      ```
    - **Update Frontend Application**: Uses the `sed` command to replace a placeholder in the `App.js.temp` file with the actual DNS name of the backend LoadBalancer, and saves the updated file as `App.js`.
      ```sh
      sed "s|BACKEND_LOADBALANCER_DNS|$BACKEND_LOADBALANCER_DNS|g" "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js.temp" > "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js"
      ```
    - **Change to Frontend Directory**: Changes the working directory to the frontend directory.
      ```sh
      cd "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend"
      ```
    - **Commit and Push Changes**: Commits the changes to the `App.js` file and pushes them to the main branch of the Git repository.
      ```sh
      git add src/App.js
      git commit -m "Update App.js with Backend LoadBalancer DNS"
      git push origin main
      ```
    - **Wait for Changes to Propagate**: Waits for 240 seconds (4 minutes) to allow the changes to propagate.
      ```sh
      sleep 240
      ```
    - **Return to Original Directory**: Changes back to the original working directory stored in `CURR_DIR`.
      ```sh
      cd $CURR_DIR
      ```

3. **Detailed Explanation of Key Commands**
    - **Retrieve Backend LoadBalancer DNS**:
      ```sh
      kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'
      ```
      Retrieves the DNS name of the backend service's LoadBalancer.
    - **Update Frontend Application**:
      ```sh
      sed "s|BACKEND_LOADBALANCER_DNS|$BACKEND_LOADBALANCER_DNS|g" "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js.temp" > "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js"
      ```
      Replaces the placeholder `BACKEND_LOADBALANCER_DNS` in the `App.js.temp` file with the actual DNS name and saves the updated file as `App.js`.
    - **Commit and Push Changes**:
      ```sh
      git add src/App.js
      git commit -m "Update App.js with Backend LoadBalancer DNS"
      git push origin main
      ```
      Adds the `App.js` file to the staging area, commits the changes with a message, and pushes the changes to the main branch of the Git repository.

### Example Questions

- **What is the purpose of the `kube_apply.sh` script?**
  - The `kube_apply.sh` script automates the process of applying Kubernetes configurations, retrieving the backend service's LoadBalancer DNS, updating the frontend application with this DNS, and committing the changes to a Git repository.

- **How does the script retrieve the backend service's LoadBalancer DNS?**
  - The script uses the command `kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'` to retrieve the DNS name of the backend service's LoadBalancer.

- **What does the `sed` command do in the script?**
  - The `sed` command replaces the placeholder `BACKEND_LOADBALANCER_DNS` in the `App.js.temp` file with the actual DNS name of the backend LoadBalancer and saves the updated file as `App.js`.

- **How does the script commit and push changes to the Git repository?**
  - The script uses the commands `git add src/App.js`, `git commit -m "Update App.js with Backend LoadBalancer DNS"`, and `git push origin main` to commit the changes to the `App.js` file and push them to the main branch of the Git repository.

- **Why does the script wait for 240 seconds?**
  - The script waits for 240 seconds (4 minutes) to allow the changes to propagate and ensure that the updated frontend application is properly deployed.

  ### frontend-service.yaml

#### 1. Understanding the Purpose of the File
**Objective:** The `frontend-service.yaml` file is used to create a Kubernetes Service that exposes the frontend application to external traffic. The service type is `LoadBalancer`, which provisions an external load balancer to route traffic to the frontend pods.

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
        name: frontend-service
    ```
- **spec:** Defines the desired state of the service.
    ```yaml
    spec:
        selector:
            app: frontend
        ports:
            - protocol: TCP
                port: 80
                targetPort: 80
        type: LoadBalancer
    ```

#### 3. Detailed Explanation of Key Fields
- **selector:** Specifies the label selector used to identify the pods that the service targets. In this case, it targets pods with the label `app: frontend`.
    ```yaml
    selector:
        app: frontend
    ```
- **ports:** Defines the ports that the service exposes.
    - **protocol:** Specifies the protocol used by the service (e.g., TCP).
    - **port:** Specifies the port on which the service is exposed.
    - **targetPort:** Specifies the port on the frontend pods to which traffic is forwarded.
    ```yaml
    ports:
        - protocol: TCP
            port: 80
            targetPort: 80
    ```
- **type:** Specifies the type of service. The `LoadBalancer` type provisions an external load balancer to route traffic to the frontend pods.
    ```yaml
    type: LoadBalancer
    ```

#### 4. Creating and Managing Services
- **Create Service from YAML File:**
    ```sh
    kubectl apply -f frontend-service.yaml
    ```
    This command creates the service defined in the `frontend-service.yaml` file.
- **View Services:**
    ```sh
    kubectl get services
    ```
    This command lists all services in the current namespace.
- **Describe Service:**
    ```sh
    kubectl describe service frontend-service
    ```
    This command provides detailed information about the `frontend-service`.
- **Delete Service:**
    ```sh
    kubectl delete -f frontend-service.yaml
    ```
    This command deletes the service defined in the `frontend-service.yaml` file.

#### Example Questions
- **What is the purpose of the `frontend-service.yaml` file?**

    The `frontend-service.yaml` file is used to create a Kubernetes Service that exposes the frontend application to external traffic using a load balancer.

- **What does the `selector` field specify in the service definition?**

    The `selector` field specifies the label selector used to identify the pods that the service targets. In this case, it targets pods with the label `app: frontend`.

- **What is the significance of the `type: LoadBalancer` field in the service definition?**

    The `type: LoadBalancer` field provisions an external load balancer to route traffic to the frontend pods, making the service accessible from outside the cluster.

- **How do you create the service defined in the `frontend-service.yaml` file?**

    You can create the service by running the command:
    ```sh
    kubectl apply -f frontend-service.yaml
    ```

- **How can you view the details of the `frontend-service`?**

    You can view the details of the service by running the command:
    ```sh
    kubectl describe service frontend-service
    ```

### frontend-deployment.yaml

#### 1. Understanding the Purpose of the File
**Objective:** The `frontend-deployment.yaml` file is used to create a Kubernetes Deployment that manages the deployment and scaling of the frontend application. It ensures that the specified number of pod replicas are running at all times.

#### 2. Key Components of the YAML File
- **apiVersion:** Specifies the version of the Kubernetes API to use.
    ```yaml
    apiVersion: apps/v1
    ```
- **kind:** Specifies the type of Kubernetes resource.
    ```yaml
    kind: Deployment
    ```
- **metadata:** Contains metadata about the resource, such as name.
    ```yaml
    metadata:
        name: frontend
    ```
- **spec:** Defines the desired state of the deployment.
    ```yaml
    spec:
        replicas: 2
        selector:
            matchLabels:
                app: frontend
        template:
            metadata:
                labels:
                    app: frontend
            spec:
                containers:
                - name: frontend
                    image: jeevan2001/frontend:latest
                    ports:
                    - containerPort: 80
                    imagePullPolicy: Always
    ```

#### 3. Detailed Explanation of Key Fields
- **replicas:** Specifies the number of pod replicas to run.
    ```yaml
    replicas: 2
    ```
- **selector:** Defines how the Deployment finds which pods to manage.
    ```yaml
    selector:
        matchLabels:
            app: frontend
    ```
- **template:** Specifies the pod template used to create new pods.
    ```yaml
    template:
        metadata:
            labels:
                app: frontend
        spec:
            containers:
            - name: frontend
                image: jeevan2001/frontend:latest
                ports:
                - containerPort: 80
                imagePullPolicy: Always
    ```

#### 4. Containers
- **containers:** Specifies the containers that run in the pod.
    - **name:** The name of the container.
        ```yaml
        name: frontend
        ```
    - **image:** The container image to use.
        ```yaml
        image: jeevan2001/frontend:latest
        ```
    - **ports:** The ports that the container exposes.
        ```yaml
        ports:
        - containerPort: 80
        ```
    - **imagePullPolicy:** Specifies when to pull the container image. `Always` means the image is pulled every time the pod is started.
        ```yaml
        imagePullPolicy: Always
        ```

#### 5. Creating and Managing Deployments
- **Create Deployment from YAML File:**
    ```sh
    kubectl apply -f frontend-deployment.yaml
    ```
    This command creates the deployment defined in the `frontend-deployment.yaml` file.
- **View Deployments:**
    ```sh
    kubectl get deployments
    ```
    This command lists all deployments in the current namespace.
- **Describe Deployment:**
    ```sh
    kubectl describe deployment frontend
    ```
    This command provides detailed information about the frontend deployment.
- **View Pods:**
    ```sh
    kubectl get pods
    ```
    This command lists all pods in the current namespace.
- **Delete Deployment:**
    ```sh
    kubectl delete -f frontend-deployment.yaml
    ```
    This command deletes the deployment defined in the `frontend-deployment.yaml` file.

#### Example Questions
- **What is the purpose of the `frontend-deployment.yaml` file?**
    The `frontend-deployment.yaml` file is used to create a Kubernetes Deployment that manages the deployment and scaling of the frontend application.
- **What does the `replicas` field specify in the deployment definition?**
    The `replicas` field specifies the number of pod replicas to run.
- **What is the role of the `selector` field in the deployment?**
    The `selector` field defines how the Deployment finds which pods to manage. It matches pods with the label `app: frontend`.
- **How do you create the deployment defined in the `frontend-deployment.yaml` file?**
    You can create the deployment by running the command `kubectl apply -f frontend-deployment.yaml`.
- **How can you view the details of the frontend deployment?**
    You can view the details of the deployment by running the command `kubectl describe deployment frontend`.

### hpa-backend.yaml

#### 1. Understanding the Purpose of the File
**Objective:** The `hpa-backend.yaml` file is used to create a Kubernetes Horizontal Pod Autoscaler (HPA) that automatically scales the number of pod replicas for the backend deployment based on CPU utilization.

#### 2. Key Components of the YAML File
- **apiVersion:** Specifies the version of the Kubernetes API to use.
    ```yaml
    apiVersion: autoscaling/v1
    ```
- **kind:** Specifies the type of Kubernetes resource.
    ```yaml
    kind: HorizontalPodAutoscaler
    ```
- **metadata:** Contains metadata about the resource, such as name.
    ```yaml
    metadata:
        name: hpa-backend
    ```
- **spec:** Defines the desired state of the HPA.
    ```yaml
    spec:
        scaleTargetRef:
            apiVersion: apps/v1
            kind: Deployment
            name: backend-deployment
        minReplicas: 1
        maxReplicas: 10
        targetCPUUtilizationPercentage: 50
    ```

#### 3. Detailed Explanation of Key Fields
- **scaleTargetRef:** Specifies the target resource to scale. In this case, it targets the `backend-deployment` deployment.
    ```yaml
    scaleTargetRef:
        apiVersion: apps/v1
        kind: Deployment
        name: backend-deployment
    ```
- **minReplicas:** Specifies the minimum number of pod replicas.
    ```yaml
    minReplicas: 1
    ```
- **maxReplicas:** Specifies the maximum number of pod replicas.
    ```yaml
    maxReplicas: 10
    ```
- **targetCPUUtilizationPercentage:** Specifies the target average CPU utilization (as a percentage) across all pods. The HPA will adjust the number of replicas to maintain this target.
    ```yaml
    targetCPUUtilizationPercentage: 50
    ```

#### 4. Creating and Managing Horizontal Pod Autoscalers
- **Create HPA from YAML File:**
    ```sh
    kubectl apply -f hpa-backend.yaml
    ```
    This command creates the HPA defined in the `hpa-backend.yaml` file.
- **View HPAs:**
    ```sh
    kubectl get hpa
    ```
    This command lists all HPAs in the current namespace.
- **Describe HPA:**
    ```sh
    kubectl describe hpa hpa-backend
    ```
    This command provides detailed information about the `hpa-backend` HPA.
- **Delete HPA:**
    ```sh
    kubectl delete -f hpa-backend.yaml
    ```
    This command deletes the HPA defined in the `hpa-backend.yaml` file.

#### Example Questions
- **What is the purpose of the `hpa-backend.yaml` file?**
    The `hpa-backend.yaml` file is used to create a Kubernetes Horizontal Pod Autoscaler (HPA) that automatically scales the number of pod replicas for the backend deployment based on CPU utilization.
- **What does the `scaleTargetRef` field specify in the HPA definition?**
    The `scaleTargetRef` field specifies the target resource to scale. In this case, it targets the `backend-deployment` deployment.
- **What is the significance of the `targetCPUUtilizationPercentage` field in the HPA definition?**
    The `targetCPUUtilizationPercentage` field specifies the target average CPU utilization (as a percentage) across all pods. The HPA will adjust the number of replicas to maintain this target.
- **How do you create the HPA defined in the `hpa-backend.yaml` file?**
    You can create the HPA by running the command `kubectl apply -f hpa-backend.yaml`.
- **How can you view the details of the `hpa-backend` HPA?**
    You can view the details of the HPA by running the command `kubectl describe hpa hpa-backend`.

### cluster-autoscaler.yaml

1. **Understanding the Purpose of the File**
    - **Objective**: The `cluster-autoscaler.yaml` file is used to create a Kubernetes Deployment for the Cluster Autoscaler. The Cluster Autoscaler automatically adjusts the number of nodes in a cluster based on the resource requirements of the pods.

2. **Key Components of the YAML File**
    - **apiVersion**: Specifies the version of the Kubernetes API to use.
      ```yaml
      apiVersion: apps/v1
      ```
    - **kind**: Specifies the type of Kubernetes resource.
      ```yaml
      kind: Deployment
      ```
    - **metadata**: Contains metadata about the resource, such as name and namespace.
      ```yaml
      metadata:
         name: cluster-autoscaler
         namespace: kube-system
         labels:
            app: cluster-autoscaler
      ```
    - **spec**: Defines the desired state of the deployment.
      ```yaml
      spec:
         replicas: 1
         selector:
            matchLabels:
              app: cluster-autoscaler
         template:
            metadata:
              labels:
                 app: cluster-autoscaler
            spec:
              containers:
              - name: cluster-autoscaler
                 image: k8s.gcr.io/autoscaling/cluster-autoscaler:v1.20.0
                 command:
                 - ./cluster-autoscaler
                 - --v=4
                 - --stderrthreshold=info
                 - --cloud-provider=aws
                 - --skip-nodes-with-local-storage=false
                 - --expander=least-waste
                 - --nodes=1:10:my-node-group
                 env:
                 - name: AWS_REGION
      ```

3. **Detailed Explanation of Key Fields**
    - **metadata**: Specifies the name and namespace of the deployment. The `kube-system` namespace is used for system components.
      ```yaml
      metadata:
         name: cluster-autoscaler
         namespace: kube-system
         labels:
            app: cluster-autoscaler
      ```
    - **replicas**: Specifies the number of pod replicas to run. For the Cluster Autoscaler, typically only one replica is needed.
      ```yaml
      replicas: 1
      ```
    - **selector**: Defines how the Deployment finds which pods to manage.
      ```yaml
      selector:
         matchLabels:
            app: cluster-autoscaler
      ```
    - **template**: Specifies the pod template used to create new pods.
      ```yaml
      template:
         metadata:
            labels:
              app: cluster-autoscaler
         spec:
            containers:
            - name: cluster-autoscaler
              image: k8s.gcr.io/autoscaling/cluster-autoscaler:v1.20.0
              command:
              - ./cluster-autoscaler
              - --v=4
              - --stderrthreshold=info
              - --cloud-provider=aws
              - --skip-nodes-with-local-storage=false
              - --expander=least-waste
              - --nodes=1:10:my-node-group
              env:
              - name: AWS_REGION
      ```

4. **Container Configuration**
    - **name**: The name of the container.
      ```yaml
      name: cluster-autoscaler
      ```
    - **image**: The container image to use.
      ```yaml
      image: k8s.gcr.io/autoscaling/cluster-autoscaler:v1.20.0
      ```
    - **command**: The command to run in the container.
      ```yaml
      command:
      - ./cluster-autoscaler
      - --v=4
      - --stderrthreshold=info
      - --cloud-provider=aws
      - --skip-nodes-with-local-storage=false
      - --expander=least-waste
      - --nodes=1:10:my-node-group
      ```
    - **env**: Specifies environment variables for the container.
      ```yaml
      env:
      - name: AWS_REGION
      ```

5. **Creating and Managing Deployments**
    - **Create Deployment from YAML File**:
      ```sh
      kubectl apply -f cluster-autoscaler.yaml
      ```
      This command creates the deployment defined in the `cluster-autoscaler.yaml` file.
    - **View Deployments**:
      ```sh
      kubectl get deployments -n kube-system
      ```
      This command lists all deployments in the `kube-system` namespace.
    - **Describe Deployment**:
      ```sh
      kubectl describe deployment cluster-autoscaler -n kube-system
      ```
      This command provides detailed information about the `cluster-autoscaler` deployment.
    - **View Pods**:
      ```sh
      kubectl get pods -n kube-system
      ```
      This command lists all pods in the `kube-system` namespace.
    - **Delete Deployment**:
      ```sh
      kubectl delete -f cluster-autoscaler.yaml
      ```
      This command deletes the deployment defined in the `cluster-autoscaler.yaml` file.

### Example Questions

- **What is the purpose of the `cluster-autoscaler.yaml` file?**
  - The `cluster-autoscaler.yaml` file is used to create a Kubernetes Deployment for the Cluster Autoscaler, which automatically adjusts the number of nodes in a cluster based on the resource requirements of the pods.

- **What does the `replicas` field specify in the deployment definition?**
  - The `replicas` field specifies the number of pod replicas to run. For the Cluster Autoscaler, typically only one replica is needed.

- **What is the role of the `command` field in the container configuration?**
  - The `command` field specifies the command to run in the container. It includes various flags to configure the Cluster Autoscaler, such as the cloud provider, verbosity level, and node group settings.

- **How do you create the deployment defined in the `cluster-autoscaler.yaml` file?**
  - You can create the deployment by running the command `kubectl apply -f cluster-autoscaler.yaml`.

- **How can you view the details of the `cluster-autoscaler` deployment?**
  - You can view the details of the deployment by running the command `kubectl describe deployment cluster-autoscaler -n kube-system`.

  ### cluster-autoscaler-policy.json

#### 1. Understanding the Purpose of the File
**Objective:** The `cluster-autoscaler-policy.json` file defines an IAM policy that grants the necessary permissions for the Kubernetes Cluster Autoscaler to manage AWS resources. This policy is attached to an IAM role that the Cluster Autoscaler uses to interact with AWS services.

#### 2. Key Components of the JSON File
- **Version:** Specifies the version of the policy language.
    ```json
    "Version": "2012-10-17"
    ```
- **Statement:** Contains one or more individual statements that define the permissions.
    ```json
    "Statement": [
        {
            "Action": [
                "autoscaling:DescribeAutoScalingGroups",
                "autoscaling:DescribeAutoScalingInstances",
                "autoscaling:DescribeLaunchConfigurations",
                "autoscaling:DescribeTags",
                "autoscaling:SetDesiredCapacity",
                "autoscaling:TerminateInstanceInAutoScalingGroup",
                "ec2:DescribeLaunchTemplateVersions"
            ],
            "Resource": "*",
            "Effect": "Allow"
        }
    ]
    ```

#### 3. Detailed Explanation of Key Fields
- **Action:** Specifies the list of actions that are allowed by this policy. These actions are necessary for the Cluster Autoscaler to manage Auto Scaling groups and EC2 instances.
    ```json
    "Action": [
        "autoscaling:DescribeAutoScalingGroups",
        "autoscaling:DescribeAutoScalingInstances",
        "autoscaling:DescribeLaunchConfigurations",
        "autoscaling:DescribeTags",
        "autoscaling:SetDesiredCapacity",
        "autoscaling:TerminateInstanceInAutoScalingGroup",
        "ec2:DescribeLaunchTemplateVersions"
    ]
    ```
    - `autoscaling:DescribeAutoScalingGroups`: Allows the Cluster Autoscaler to describe Auto Scaling groups.
    - `autoscaling:DescribeAutoScalingInstances`: Allows the Cluster Autoscaler to describe Auto Scaling instances.
    - `autoscaling:DescribeLaunchConfigurations`: Allows the Cluster Autoscaler to describe launch configurations.
    - `autoscaling:DescribeTags`: Allows the Cluster Autoscaler to describe tags.
    - `autoscaling:SetDesiredCapacity`: Allows the Cluster Autoscaler to set the desired capacity of an Auto Scaling group.
    - `autoscaling:TerminateInstanceInAutoScalingGroup`: Allows the Cluster Autoscaler to terminate instances in an Auto Scaling group.
    - `ec2:DescribeLaunchTemplateVersions`: Allows the Cluster Autoscaler to describe EC2 launch template versions.
- **Resource:** Specifies the resources to which the actions apply. The wildcard `*` means the actions apply to all resources.
    ```json
    "Resource": "*"
    ```
- **Effect:** Specifies whether the action is allowed or denied. In this case, it is set to `Allow`.
    ```json
    "Effect": "Allow"
    ```

#### 4. Creating and Managing IAM Policies
- **Create IAM Policy:**
    ```sh
    aws iam create-policy --policy-name cluster-autoscaler-policy --policy-document file://cluster-autoscaler-policy.json
    ```
    This command creates an IAM policy using the definition in the `cluster-autoscaler-policy.json` file.
- **Attach IAM Policy to Role:**
    ```sh
    aws iam attach-role-policy --role-name <role-name> --policy-arn <policy-arn>
    ```
    This command attaches the created IAM policy to an IAM role.
- **View IAM Policies:**
    ```sh
    aws iam list-policies
    ```
    This command lists all IAM policies in the AWS account.
- **Describe IAM Policy:**
    ```sh
    aws iam get-policy --policy-arn <policy-arn>
    ```
    This command provides detailed information about a specific IAM policy.
- **Delete IAM Policy:**
    ```sh
    aws iam delete-policy --policy-arn <policy-arn>
    ```
    This command deletes a specific IAM policy.

#### Example Questions
- **What is the purpose of the `cluster-autoscaler-policy.json` file?**
    The `cluster-autoscaler-policy.json` file defines an IAM policy that grants the necessary permissions for the Kubernetes Cluster Autoscaler to manage AWS resources.
- **What actions are allowed by the IAM policy defined in the `cluster-autoscaler-policy.json` file?**
    The policy allows actions such as describing Auto Scaling groups, describing Auto Scaling instances, describing launch configurations, describing tags, setting desired capacity, terminating instances in an Auto Scaling group, and describing EC2 launch template versions.
- **What does the Resource field specify in the IAM policy?**
    The Resource field specifies the resources to which the actions apply. The wildcard `*` means the actions apply to all resources.
- **How do you create the IAM policy defined in the `cluster-autoscaler-policy.json` file?**
    You can create the IAM policy by running the command:
    ```sh
    aws iam create-policy --policy-name cluster-autoscaler-policy --policy-document file://cluster-autoscaler-policy.json
    ```
- **How can you attach the created IAM policy to an IAM role?**
    You can attach the IAM policy to an IAM role by running the command:
    ```sh
    aws iam attach-role-policy --role-name <role-name> --policy-arn <policy-arn>
    ```