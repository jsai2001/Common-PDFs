Let's walk through the entire project under the "Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))" folder, explaining each component and how they work together to deploy a Node.js backend, React frontend, and MySQL database on Kubernetes.

## Project Structure
The project structure might look something like this:

```
Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))/
├── backend/
│   ├── Dockerfile
│   ├── src/
│   │   └── server.js
│   └── package.json
├── frontend/
│   ├── Dockerfile
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── App.js.temp
│   │   ├── App.css
│   │   ├── index.css
│   │   ├── index.js
│   │   └── App.js
│   └── package.json
│── mysql-secret.yaml
│── backend-deployment.yaml
│── backend-service.yaml
│── build_infra.sh
│── buildspec.yaml
│── destroy_infra.sh
│── init-sql-configmap.yaml
│── init.sql
│── restart_backend_deployment.yaml
│── restart_frontend_deployment.yaml
│── backend-service.yaml
│── frontend-deployment.yaml
│── frontend-service.yaml
├── kube_apply.sh
├── kube_destroy.sh
└── main.tf
```

## Backend
### Dockerfile
The Dockerfile for the backend defines how to build the Docker image for the Node.js backend application.

```dockerfile
# Use the official Node.js image
FROM node:14

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Clear npm cache and install dependencies
RUN npm cache clean --force && npm install 

RUN npm ci && npm audit fix

# Copy local code to the container image.
COPY . .

# Expose the port the app runs on
EXPOSE 3000

# Run the web service on container startup.
CMD [ "node", "src/server.js" ]
```

### server.js
The server.js file is the main entry point for the Node.js backend application. It sets up an Express server and connects to a MySQL database.

```javascript
const express = require('express');
const mysql = require('mysql');
const cors = require('cors');
const app = express();
const port = 3000;

// Disable CORS by allowing all origins (development only!)
app.use(cors({
    origin: '*',
    optionsSuccessStatus: 200 // some legacy browsers (IE11, various SmartTVs) choke on 204
}));

// Middleware to log requests for debugging
app.use((req, res, next) => {
    console.log('Middleware hit:', req.method, req.path);
    next();
});

const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER || 'admin',
    password: process.env.DB_PASSWORD || process.env.MYSQL_ROOT_PASSWORD,
    database: process.env.DB_NAME || 'mydatabase'
});

db.connect(err => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to the database');
});

app.get('/items', (req, res) => {
    db.query('SELECT * FROM items', (err, results) => {
        if (err) {
            console.error('Error fetching items:', err);
            res.status(500).send('Error fetching items');
            return;
        }
        res.json(results);
    });
});

app.get('/', (req, res) => {
    res.send('Hello, World!');
});

// Handle errors globally
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something broke!');
});

app.listen(port, () => {
    console.log(`Backend app listening at http://localhost:${port}`);
});
```

## Kubernetes Manifests
- `mysql-secret.yaml`: Defines a Kubernetes secret to store the MySQL root password.
- `backend-deployment.yaml`: Defines the deployment for the backend application, including an init container to initialize the MySQL database.
- `backend-service.yaml`: Defines a service to expose the backend application.

## Frontend
### Dockerfile
The Dockerfile for the frontend defines how to build the Docker image for the React frontend application.

```dockerfile
# Use the official Node.js image
FROM node:14 as build

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Clear npm cache and install dependencies
RUN npm cache clean --force && npm install

RUN npm ci && npm audit fix

# Copy local code to the container image.
COPY . .

# Build the app
RUN npm run build

# Stage 2: Serve the built application using Nginx
FROM nginx:alpine

# Copy the build output from the previous stage to the Nginx html directory
COPY --from=build /usr/src/app/build /usr/share/nginx/html

# Expose the port Nginx will serve on
EXPOSE 80

# Run Nginx
CMD ["nginx", "-g", "daemon off;"]
```

### App.js.temp
The App.js.temp file is a template for the main React component. It includes a placeholder for the backend LoadBalancer DNS.

```javascript
import React, { useEffect, useState } from 'react';
import './App.css';

function App() {
    const [data, setData] = useState([]);

    useEffect(() => {
        console.log('Fetching data from backend...');
        fetch('http://BACKEND_LOADBALANCER_DNS:3000/items')  // Ensure this URL is correct
            .then(response => {
                console.log('Received response:', response);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Fetched data:', data);  // Log fetched data
                setData(data);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <h1>Welcome to the Static Website</h1>
                <p>This website is served by an Nginx server running in a Kubernetes cluster.</p>
                <h2>Data from the Database:</h2>
                <ul>
                    {data.map((item, index) => (
                        <li key={index}>{item.name}</li>
                    ))}
                </ul>
            </header>
        </div>
    );
}

export default App;
```

## Kubernetes Manifests

- **frontend-deployment.yaml**: Defines the deployment for the frontend application.
- **frontend-service.yaml**: Defines a service to expose the frontend application.
- **website-content-configmap.yaml**: Defines a ConfigMap to store website content.

## Terraform Configuration

The `main.tf` file defines the Terraform configuration to provision the MySQL RDS instance and generate Kubernetes manifests.

```hcl
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
        desired_size = 2
        max_size     = 3
        min_size     = 1
    }

    instance_types = ["t3.medium"]

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
```

## Shell Scripts

### kube_apply.sh

The `kube_apply.sh` script applies the Kubernetes configurations, updates the frontend with the backend LoadBalancer DNS, and commits the changes to the repository.

```sh
# Apply Kubernetes configurations
kubectl apply -f mysql-secret.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f backend-deployment.yaml

CURR_DIR=$(pwd)

# Get the Backend LoadBalancer DNS
export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
echo "Backend LoadBalancer DNS: $BACKEND_LOADBALANCER_DNS"

# Replace placeholder in App.js.temp and create App.js
sed "s|BACKEND_LOADBALANCER_DNS|$BACKEND_LOADBALANCER_DNS|g" "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))/frontend/src/App.js.temp" > "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))/frontend/src/App.js"

# Change to the frontend directory
cd "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))/frontend"

# Commit and push the changes
git add src/App.js
git commit -m "Update App.js with Backend LoadBalancer DNS"
git push origin main

sleep 240

cd $CURR_DIR

# Apply frontend configurations
kubectl apply -f frontend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f website-content-configmap.yaml
```

### kube_destroy.sh

The `kube_destroy.sh` script deletes the Kubernetes resources.

```sh
# Delete frontend configurations
kubectl delete -f frontend-deployment.yaml
kubectl delete -f frontend-service.yaml
kubectl delete -f website-content-configmap.yaml

# Delete backend configurations
kubectl delete -f backend-deployment.yaml
kubectl delete -f backend-service.yaml

# Delete MySQL secret
kubectl delete -f mysql-secret.yaml

# Optionally, delete any remaining resources or namespaces if needed
# kubectl delete namespace <namespace-name>
```

## Summary

This project demonstrates how to deploy a Node.js backend, React frontend, and MySQL database on Kubernetes using Docker, Kubernetes manifests, and Terraform. The backend connects to a MySQL database, and the frontend fetches data from the backend. The `kube_apply.sh` script automates the deployment process, while the `kube_destroy.sh` script cleans up the resources.

Let's go through each of these files and explain their purpose in the project, along with the code.

### `mysql-secret.yaml`
This file defines a Kubernetes secret to store the MySQL root password securely.

```yaml
apiVersion: v1
kind: Secret
metadata:
    name: mysql-secret
type: Opaque
data:
    MYSQL_ROOT_PASSWORD: cGFzc3dvcmQ=  # base64 encoded value of "password"
```

**Explanation:**
- `apiVersion`: The version of the Kubernetes API to use.
- `kind`: The type of Kubernetes resource (Secret).
- `metadata`: Metadata about the secret, including its name.
- `type`: The type of secret (Opaque).
- `data`: The actual secret data, with the password base64 encoded.

### `backend-deployment.tpl.yaml`
This file defines the deployment for the backend application, including an init container to initialize the MySQL database.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend
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
                command: [ "sh", "-c", "mysql -h ${db_host} -P ${db_port} -u admin -p${MYSQL_ROOT_PASSWORD} < /docker-entrypoint-initdb.d/init.sql" ]
            containers:
            - name: backend
                image: jeevan2001/backend:latest
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_PORT
                    value: "${db_port}"
                - name: MYSQL_ROOT_PASSWORD
                    valueFrom:
                        secretKeyRef:
                            name: mysql-secret
                            key: MYSQL_ROOT_PASSWORD
                ports:
                - containerPort: 3000
            volumes:
            - name: init-sql
                configMap:
                    name: init-sql-config
```

**Explanation:**
- `initContainers`: An init container to initialize the MySQL database.
- `containers`: The main container running the backend application.
- `env`: Environment variables for the containers.
- `volumeMounts`: Mounting the init SQL script.
- `volumes`: Defining the volume for the init SQL script.

### `backend-service.yaml`
This file defines a service to expose the backend application.

```yaml
apiVersion: v1
kind: Service
metadata:
    name: backend-service
spec:
    selector:
        app: backend
    ports:
        - protocol: TCP
            port: 3000
            targetPort: 3000
    type: LoadBalancer
```

**Explanation:**
- `selector`: Selects the pods with the label `app: backend`.
- `ports`: Defines the port mapping.
- `type`: Specifies the service type as LoadBalancer to expose it externally.

### `frontend-deployment.yaml`
This file defines the deployment for the frontend application.

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: frontend
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

**Explanation:**
- `containers`: The main container running the frontend application.
- `ports`: Defines the port mapping for the container.

### `frontend-service.yaml`
This file defines a service to expose the frontend application.

```yaml
apiVersion: v1
kind: Service
metadata:
    name: frontend-service
spec:
    selector:
        app: frontend
    ports:
        - protocol: TCP
            port: 80
            targetPort: 80
    type: LoadBalancer
```

**Explanation:**
- `selector`: Selects the pods with the label `app: frontend`.
- `ports`: Defines the port mapping.
- `type`: Specifies the service type as LoadBalancer to expose it externally.

### `website-content-configmap.yaml`
Since you mentioned that this file is not needed, it can be removed from the project.

### Summary
This project demonstrates how to deploy a Node.js backend, React frontend, and MySQL database on Kubernetes using Docker, Kubernetes manifests, and Terraform. The backend connects to a MySQL database, and the frontend fetches data from the backend. The `kube_apply.sh` script automates the deployment process, while the `kube_destroy.sh` script cleans up the resources.

Let's go through the `init-sql-configmap.yaml` file and the `init.sql` script it contains.

### `init-sql-configmap.yaml`
The `init-sql-configmap.yaml` file is a Kubernetes ConfigMap that stores the SQL initialization script. This script is used to set up the initial state of the MySQL database.

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: init-sql-configmap
data:
    init.sql: |
        CREATE DATABASE IF NOT EXISTS mydatabase;

        USE mydatabase;

        CREATE TABLE IF NOT EXISTS items (
            id INT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(255) NOT NULL
        );

        INSERT INTO items (name) VALUES ('Item 1'), ('Item 2'), ('Item 3');
```

#### Explanation
- **apiVersion**: Specifies the version of the Kubernetes API to use. In this case, it's `v1`.
- **kind**: Specifies the type of Kubernetes resource. Here, it's a `ConfigMap`.
- **metadata**: Contains metadata about the ConfigMap, including its name (`init-sql-configmap`).
- **data**: Contains the actual data stored in the ConfigMap. In this case, it's an SQL script (`init.sql`).

### `init.sql`
The `init.sql` script is embedded within the ConfigMap and contains SQL commands to initialize the MySQL database.

```sql
CREATE DATABASE IF NOT EXISTS mydatabase;

USE mydatabase;

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO items (name) VALUES ('Item 1'), ('Item 2'), ('Item 3');
```

#### Explanation
- `CREATE DATABASE IF NOT EXISTS mydatabase;`: Creates a database named `mydatabase` if it does not already exist.
- `USE mydatabase;`: Selects the `mydatabase` database for subsequent operations.
- `CREATE TABLE IF NOT EXISTS items (...);`: Creates a table named `items` with the specified columns if it does not already exist.
    - `id INT AUTO_INCREMENT PRIMARY KEY`: An integer column named `id` that auto-increments and serves as the primary key.
    - `name VARCHAR(255) NOT NULL`: A varchar column named `name` with a maximum length of 255 characters that cannot be null.
- `INSERT INTO items (name) VALUES ('Item 1'), ('Item 2'), ('Item 3');`: Inserts three rows into the `items` table with the values 'Item 1', 'Item 2', and 'Item 3'.

### Usage
This ConfigMap can be used in a Kubernetes deployment to initialize the MySQL database. The init container in the backend deployment will mount this ConfigMap and execute the SQL script to set up the database.

### Example Usage in a Deployment
Here is an example of how this ConfigMap might be used in a Kubernetes deployment:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend
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
                    value: "terraform-20250121081643879900000008.c5o4csuyg3zb.ap-south-1.rds.amazonaws.com"
                - name: DB_PORT
                    value: "3306"
                volumeMounts:
                - name: init-sql
                    mountPath: /docker-entrypoint-initdb.d
                command: [ "sh", "-c", "mysql -h terraform-20250121081643879900000008.c5o4csuyg3zb.ap-south-1.rds.amazonaws.com -P 3306 -u admin -p$MYSQL_ROOT_PASSWORD < /docker-entrypoint-initdb.d/init.sql || (echo 'MySQL init failed' && exit 1)" ]
            containers:
            - name: backend
                image: jeevan2001/backend:latest
                env:
                - name: DB_HOST
                    value: "terraform-20250121081643879900000008.c5o4csuyg3zb.ap-south-1.rds.amazonaws.com"
                - name: DB_PORT
                    value: "3306"
                - name: MYSQL_ROOT_PASSWORD
                    valueFrom:
                        secretKeyRef:
                            name: mysql-secret
                            key: MYSQL_ROOT_PASSWORD
                ports:
                - containerPort: 3000
            volumes:
            - name: init-sql
                configMap:
                    name: init-sql-configmap
```

#### Explanation
- **initContainers**: An init container to initialize the MySQL database.
- **volumeMounts**: Mounts the `init-sql-configmap` ConfigMap at `/docker-entrypoint-initdb.d`.
- **command**: Executes the SQL script to initialize the database.
- **volumes**: Defines the volume that sources its data from the `init-sql-configmap` ConfigMap.

### Summary
The `init-sql-configmap.yaml` file is a Kubernetes ConfigMap that stores an SQL initialization script. This script is used to set up the initial state of the MySQL database, including creating the database, creating a table, and inserting initial data. The ConfigMap can be used in a Kubernetes deployment to initialize the database using an init container.