The RDS endpoint is for the database and does not serve any web content directly. To interact with the database, you need a backend application that connects to the RDS instance and a frontend application that interacts with the backend.

Here's a summary of how the components interact:

- **Frontend Application**: A web application (e.g., React) that users interact with.
- **Backend Application**: A server application (e.g., Node.js with Express) that handles requests from the frontend and interacts with the database.
- **Database**: An RDS instance (e.g., MySQL) that stores data.

## Steps to Set Up the Complete Application

1. **Deploy the Backend Application**: The backend connects to the RDS instance.
2. **Deploy the Frontend Application**: The frontend interacts with the backend.

### Example Backend Application

Let's assume you have a simple Node.js application that connects to the MySQL database and provides an API to fetch data.

#### Backend Deployment (`backend-deployment.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend-deployment
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
            containers:
            - name: backend
                image: your-backend-image:latest
                ports:
                - containerPort: 3000
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_USER
                    value: "admin"
                - name: DB_PASSWORD
                    value: "password"
                - name: DB_NAME
                    value: "mydatabase"
```

#### Backend Service (`backend-service.yaml`)

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
    type: ClusterIP
```

### Example Frontend Application

Let's assume you have a simple React application that fetches data from the backend.

#### Frontend Deployment (`frontend-deployment.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: frontend-deployment
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
                image: your-frontend-image:latest
                ports:
                - containerPort: 80
                env:
                - name: BACKEND_URL
                    value: "http://backend-service:3000"
```

#### Frontend Service (`frontend-service.yaml`)

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

## Update Terraform Configuration

Ensure the backend deployment template is correctly configured to use the RDS endpoint.

#### Backend Deployment Template (`backend-deployment.tpl.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend-deployment
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
            containers:
            - name: backend
                image: your-backend-image:latest
                ports:
                - containerPort: 3000
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_USER
                    value: "admin"
                - name: DB_PASSWORD
                    value: "password"
                - name: DB_NAME
                    value: "mydatabase"
```

### Update `main.tf` to Include Backend Deployment

```hcl
data "template_file" "backend_deployment" {
        template = file("${path.module}/backend-deployment.tpl.yaml")

        vars = {
                db_host = aws_db_instance.mydb.endpoint
        }
}

resource "local_file" "backend_deployment" {
        content  = data.template_file.backend_deployment.rendered
        filename = "${path.module}/backend-deployment.yaml"
}
```

### Apply the Terraform Configuration

Run the following commands to apply the Terraform configuration and generate the final YAML files:

```sh
terraform init
terraform apply
```

### Apply the Kubernetes Manifests

After running `terraform apply`, the `backend-deployment.yaml`, `website-content-configmap.yaml`, and `static-website-deployment.yaml` files will be generated with the correct RDS endpoint. Apply the Kubernetes manifests:

```sh
kubectl apply -f website-content-configmap.yaml
kubectl apply -f static-website-deployment.yaml
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

## Summary

By following these steps, you will have a complete setup where:

- The frontend application interacts with the backend.
- The backend application connects to the RDS instance.
- The frontend is accessible via a LoadBalancer, and the backend is accessible within the cluster.
- The RDS endpoint itself does not serve any web content, but the backend application will use it to interact with the database and provide data to the frontend.

### Backend Application

We'll use a simple Node.js application that connects to the MySQL database and provides an API to fetch data.

#### Backend Dockerfile

Create a `Dockerfile` for the backend:

```dockerfile
# Use the official Node.js image
FROM node:14

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy local code to the container image.
COPY . .

# Expose the port the app runs on
EXPOSE 3000

# Run the web service on container startup.
CMD [ "node", "server.js" ]
```

#### Backend `server.js`

Create a `server.js` file for the backend:

```javascript
const express = require('express');
const mysql = require('mysql');
const app = express();
const port = 3000;

const db = mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME
});

db.connect(err => {
    if (err) {
        console.error('Error connecting to the database:', err);
        return;
    }
    console.log('Connected to the database');
});

app.get('/data', (req, res) => {
    db.query('SELECT * FROM your_table', (err, results) => {
        if (err) {
            res.status(500).send('Error fetching data');
            return;
        }
        res.json(results);
    });
});

app.listen(port, () => {
    console.log(`Backend app listening at http://localhost:${port}`);
});
```

#### Backend `package.json`

Create a `package.json` file for the backend:

```json
{
    "name": "backend",
    "version": "1.0.0",
    "description": "Node.js backend for the demo application",
    "main": "server.js",
    "scripts": {
        "start": "node server.js"
    },
    "dependencies": {
        "express": "^4.17.1",
        "mysql": "^2.18.1"
    }
}
```

### Frontend Application

We'll use a simple React application that fetches data from the backend.

#### Frontend Dockerfile

Create a `Dockerfile` for the frontend:

```dockerfile
# Use the official Node.js image
FROM node:14

# Create and change to the app directory
WORKDIR /usr/src/app

# Copy application dependency manifests to the container image.
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy local code to the container image.
COPY . .

# Build the app
RUN npm run build

# Install serve to serve the build
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 5000

# Run the web service on container startup.
CMD [ "serve", "-s", "build" ]
```

#### Frontend `src/App.js`

Create an `App.js` file for the frontend:

```javascript
import React, { useEffect, useState } from 'react';
import './App.css';

function App() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetch('http://backend-service:3000/data')
            .then(response => response.json())
            .then(data => setData(data))
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

#### Frontend `package.json`

Create a `package.json` file for the frontend:

```json
{
    "name": "frontend",
    "version": "1.0.0",
    "description": "React frontend for the demo application",
    "main": "src/index.js",
    "scripts": {
        "start": "react-scripts start",
        "build": "react-scripts build",
        "test": "react-scripts test",
        "eject": "react-scripts eject"
    },
    "dependencies": {
        "react": "^17.0.2",
        "react-dom": "^17.0.2",
        "react-scripts": "4.0.3"
    }
}
```

### Build and Push Docker Images

Build and push the Docker images to a container registry (e.g., Docker Hub).

```sh
# Build and push the backend image
docker build -t your-dockerhub-username/backend:latest -f backend/Dockerfile .
docker push your-dockerhub-username/backend:latest

# Build and push the frontend image
docker build -t your-dockerhub-username/frontend:latest -f frontend/Dockerfile .
docker push your-dockerhub-username/frontend:latest
```

### Update Kubernetes Manifests

Update the Kubernetes manifests to use the Docker images.

#### Backend Deployment (`backend-deployment.tpl.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend-deployment
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
            containers:
            - name: backend
                image: your-dockerhub-username/backend:latest
                ports:
                - containerPort: 3000
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_USER
                    value: "admin"
                - name: DB_PASSWORD
                    value: "password"
                - name: DB_NAME
                    value: "mydatabase"
```

#### Frontend Deployment (`frontend-deployment.yaml`)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: frontend-deployment
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
                image: your-dockerhub-username/frontend:latest
                ports:
                - containerPort: 5000
                env:
                - name: BACKEND_URL
                    value: "http://backend-service:3000"
```

### Apply the Kubernetes Manifests

After running `terraform apply`, the `backend-deployment.yaml`, `website-content-configmap.yaml`, and `static-website-deployment.yaml` files will be generated with the correct RDS endpoint. Apply the Kubernetes manifests:

```sh
kubectl apply -f website-content-configmap.yaml
kubectl apply -f static-website-deployment.yaml
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

## Summary

By following these steps, you will have a complete setup where:

- The frontend application interacts with the backend.
- The backend application connects to the RDS instance.
- The frontend is accessible via a LoadBalancer, and the backend is accessible within the cluster.
- The RDS endpoint itself does not serve any web content, but the backend application will use it to interact with the database and provide data to the frontend.

You're correct. The new RDS instance will not have any tables created by default. We need to create a table and insert some sample data into it. We can do this by running a SQL script after the RDS instance is created.

### Step 1: Create a SQL Script
Create a SQL script to create a table and insert some sample data. Save this script as `init.sql`.

```sql
CREATE DATABASE IF NOT EXISTS mydatabase;

USE mydatabase;

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO items (name) VALUES ('Item 1'), ('Item 2'), ('Item 3');
```

### Step 2: Run the SQL Script
You can use the `mysql` command-line tool to run the SQL script. First, ensure you have the necessary MySQL client installed on your local machine or use an EC2 instance with the MySQL client installed.

```sh
mysql -h <rds-endpoint> -u admin -p < init.sql
```

Replace `<rds-endpoint>` with the endpoint of your RDS instance.

### Step 3: Update Backend Application
Update the backend application to query the `items` table.

**Backend `server.js`**

```javascript
const express = require('express');
const mysql = require('mysql');
const app = express();
const port = 3000;

const db = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME
});

db.connect(err => {
  if (err) {
    console.error('Error connecting to the database:', err);
    return;
  }
  console.log('Connected to the database');
});

app.get('/data', (req, res) => {
  db.query('SELECT * FROM items', (err, results) => {
    if (err) {
      res.status(500).send('Error fetching data');
      return;
    }
    res.json(results);
  });
});

app.listen(port, () => {
  console.log(`Backend app listening at http://localhost:${port}`);
});
```

### Step 4: Build and Push Docker Images
Build and push the Docker images to a container registry (e.g., Docker Hub).

```sh
# Build and push the backend image
docker build -t your-dockerhub-username/backend:latest -f backend/Dockerfile .
docker push your-dockerhub-username/backend:latest

# Build and push the frontend image
docker build -t your-dockerhub-username/frontend:latest -f frontend/Dockerfile .
docker push your-dockerhub-username/frontend:latest
```

### Step 5: Update Kubernetes Manifests
Update the Kubernetes manifests to use the Docker images.

**Backend Deployment (`backend-deployment.tpl.yaml`)**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend-deployment
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
      containers:
      - name: backend
        image: your-dockerhub-username/backend:latest
        ports:
        - containerPort: 3000
        env:
        - name: DB_HOST
          value: "${db_host}"
        - name: DB_USER
          value: "admin"
        - name: DB_PASSWORD
          value: "password"
        - name: DB_NAME
          value: "mydatabase"
```

### Step 6: Apply the Kubernetes Manifests
After running `terraform apply`, the `backend-deployment.yaml`, `website-content-configmap.yaml`, and `static-website-deployment.yaml` files will be generated with the correct RDS endpoint. Apply the Kubernetes manifests:

```sh
kubectl apply -f website-content-configmap.yaml
kubectl apply -f static-website-deployment.yaml
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

### Summary
By following these steps, you will have a complete setup where:

- The frontend application interacts with the backend.
- The backend application connects to the RDS instance and queries the `items` table.
- The frontend is accessible via a LoadBalancer, and the backend is accessible within the cluster.
- The RDS endpoint itself does not serve any web content, but the backend application will use it to interact with the database and provide data to the frontend. The SQL script ensures that the necessary table and sample data are created in the database.

To automate the creation of the table and insertion of sample data as part of the workflow, we can use an initContainer in the Kubernetes deployment. This initContainer will run the SQL script before the main application starts.

### Step 1: Create a ConfigMap for the SQL Script
Create a ConfigMap to store the SQL script.

**init-sql-configmap.yaml**
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

### Step 2: Update Backend Deployment to Use initContainer
Update the backend deployment to include an initContainer that runs the SQL script.

**Backend Deployment Template (backend-deployment.tpl.yaml)**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend-deployment
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
                    value: "password"
                - name: DB_HOST
                    value: "${db_host}"
                volumeMounts:
                - name: init-sql
                    mountPath: /docker-entrypoint-initdb.d
                command: [ "sh", "-c", "mysql -h ${DB_HOST} -u admin -p${MYSQL_ROOT_PASSWORD} < /docker-entrypoint-initdb.d/init.sql" ]
            containers:
            - name: backend
                image: your-dockerhub-username/backend:latest
                ports:
                - containerPort: 3000
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_USER
                    value: "admin"
                - name: DB_PASSWORD
                    value: "password"
                - name: DB_NAME
                    value: "mydatabase"
            volumes:
            - name: init-sql
                configMap:
                    name: init-sql-configmap
                    items:
                    - key: init.sql
                        path: init.sql
```

### Step 3: Update Terraform Configuration
Ensure the backend deployment template is correctly configured to use the RDS endpoint.

Update **main.tf** to include backend deployment and ConfigMap
```hcl
data "template_file" "backend_deployment" {
        template = file("${path.module}/backend-deployment.tpl.yaml")

        vars = {
                db_host = aws_db_instance.mydb.endpoint
        }
}

resource "local_file" "backend_deployment" {
        content  = data.template_file.backend_deployment.rendered
        filename = "${path.module}/backend-deployment.yaml"
}
```

### Step 4: Apply the Terraform Configuration
Run the following commands to apply the Terraform configuration and generate the final YAML files:
```sh
terraform init
terraform apply
```

### Step 5: Apply the Kubernetes Manifests
After running `terraform apply`, the `backend-deployment.yaml`, `website-content-configmap.yaml`, and `static-website-deployment.yaml` files will be generated with the correct RDS endpoint. Apply the Kubernetes manifests:
```sh
kubectl apply -f init-sql-configmap.yaml
kubectl apply -f website-content-configmap.yaml
kubectl apply -f static-website-deployment.yaml
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

### Summary
By following these steps, you will have a complete setup where:
- The frontend application interacts with the backend.
- The backend application connects to the RDS instance and queries the items table.
- The frontend is accessible via a LoadBalancer, and the backend is accessible within the cluster.
- The initContainer in the backend deployment ensures that the necessary table and sample data are created in the database automatically.

This approach ensures that the database initialization is part of the deployment workflow and does not require manual intervention.

Here is the complete `main.tf` file that sets up the necessary AWS infrastructure, including VPC, subnets, Internet Gateway, NAT Gateway, route tables, security groups, IAM roles, EKS cluster, node group, and RDS instance. It also includes the configuration to generate the Kubernetes deployment YAML files with the correct RDS endpoint.

```hcl
provider "aws" {
    region = "ap-south-1"
}

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

resource "aws_nat_gateway" "eks_nat_gateway" {
    allocation_id = aws_eip.nat_eip.id
    subnet_id     = element(aws_subnet.eks_public_subnet[*].id, 0)
}

resource "aws_eip" "nat_eip" {
    vpc = true
}

resource "aws_route_table" "eks_public_route_table" {
    vpc_id = aws_vpc.eks_vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.eks_igw.id
    }
}

resource "aws_route_table" "eks_private_route_table" {
    vpc_id = aws_vpc.eks_vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        nat_gateway_id = aws_nat_gateway.eks_nat_gateway.id
    }
}

resource "aws_route_table_association" "eks_public_route_table_association" {
    count          = 3
    subnet_id      = element(aws_subnet.eks_public_subnet[*].id, count.index)
    route_table_id = aws_route_table.eks_public_route_table.id
}

resource "aws_route_table_association" "eks_private_route_table_association" {
    count          = 3
    subnet_id      = element(aws_subnet.eks_private_subnet[*].id, count.index)
    route_table_id = aws_route_table.eks_private_route_table.id
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
        aws_route_table_association.eks_public_route_table_association
    ]
}

resource "aws_eks_node_group" "my_node_group" {
    cluster_name    = aws_eks_cluster.my_cluster.name
    node_group_name = "my-node-group"
    node_role_arn   = aws_iam_role.eks_node_role.arn
    subnet_ids      = aws_subnet.eks_public_subnet[*].id

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

resource "aws_db_instance" "mydb" {
    allocated_storage    = 20
    storage_type         = "gp2"
    engine               = "mysql"
    engine_version       = "8.0"
    instance_class       = "db.t3.micro"
    name                 = "mydatabase"
    username             = "admin"
    password             = "password"
    db_subnet_group_name = aws_db_subnet_group.mydb_subnet_group.name
    vpc_security_group_ids = [aws_security_group.rds_security_group.id]
    skip_final_snapshot  = true
}

resource "aws_db_subnet_group" "mydb_subnet_group" {
    name       = "mydb-subnet-group"
    subnet_ids = aws_subnet.eks_private_subnet[*].id
}

data "template_file" "static_website_deployment" {
    template = file("${path.module}/static-website-deployment.tpl.yaml")

    vars = {
        db_host = aws_db_instance.mydb.endpoint
    }
}

resource "local_file" "static_website_deployment" {
    content  = data.template_file.static_website_deployment.rendered
    filename = "${path.module}/static-website-deployment.yaml"
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

data "template_file" "backend_deployment" {
    template = file("${path.module}/backend-deployment.tpl.yaml")

    vars = {
        db_host = aws_db_instance.mydb.endpoint
    }
}

resource "local_file" "backend_deployment" {
    content  = data.template_file.backend_deployment.rendered
    filename = "${path.module}/backend-deployment.yaml"
}
```

### Summary
This `main.tf` file sets up the necessary AWS infrastructure, including:

- **VPC and Subnets**: Creates public and private subnets.
- **Internet Gateway and NAT Gateway**: Ensures instances in private subnets can access the internet.
- **Route Tables and Associations**: Configures routing for public and private subnets.
- **IAM Roles and Policies**: Creates IAM roles and attaches necessary policies.
- **Security Groups**: Creates security groups for the EKS cluster and the RDS instance.
- **EKS Cluster and Node Group**: Creates the EKS cluster and node group.
- **RDS Instance**: Creates an RDS instance for the MySQL database.
- **Template Files**: Generates Kubernetes deployment YAML files with the correct RDS endpoint.

By following these steps, you will have a complete setup where the frontend application interacts with the backend, the backend connects to the RDS instance, and the necessary table and sample data are created in the database automatically.

Here is a step-by-step guide to set up AWS CodeBuild for your project, including how to configure it, create the necessary files, and ensure everything works correctly.

## Step 1: Ensure Correct `package.json` Files

Ensure that your `package.json` files for both the frontend and backend are correctly set up.

### Backend `package.json`

```json
{
    "name": "backend",
    "version": "1.0.0",
    "description": "Node.js backend for the demo application",
    "main": "src/server.js",
    "scripts": {
      "start": "node src/server.js"
    },
    "dependencies": {
      "express": "^4.17.1",
      "mysql": "^2.18.1"
    }
  }
```

### Frontend `package.json`

```json
{
    "name": "frontend",
    "version": "1.0.0",
    "description": "React frontend for the demo application",
    "main": "src/index.js",
    "scripts": {
      "start": "react-scripts start",
      "build": "react-scripts build",
      "test": "react-scripts test",
      "eject": "react-scripts eject"
    },
    "dependencies": {
      "react": "^17.0.2",
      "react-dom": "^17.0.2",
      "react-scripts": "^5.0.1"
    }
  }
```

If you are not running `npm install` locally and want to handle everything within the Dockerfile during the AWS CodeBuild process, you need to ensure that the Dockerfile is correctly set up to handle the installation of dependencies and the generation of the `package-lock.json` file.

## Updated Dockerfile for Backend

Here is the updated Dockerfile for the backend application:

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

## Updated Dockerfile for Frontend

Here is the updated Dockerfile for the frontend application:

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

# Build the app
RUN npm run build

# Install serve to serve the build
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 5000

# Run the web service on container startup.
CMD [ "serve", "-s", "build" ]
```

## Example `buildspec.yml`

Ensure your `buildspec.yml` file is correctly set up to handle the build process.

```yaml
version: 0.2

phases:
  install:
    commands:
      - echo Logging in to Docker Hub...
      - echo "Kanna@123" | docker login -u "jeevan2001" --password-stdin
  pre_build:
    commands:
      - echo Build started on `date`
      - echo Building the Docker images...
  build:
    commands:
      - cd "DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Nodejs_React_MySQL))/backend"
      - docker build -t jeevan2001/backend:latest .
      - cd ../frontend
      - docker build -t jeevan2001/frontend:latest .
  post_build:
    commands:
      - echo Build completed on `date`
      - echo Pushing the Docker images...
      - docker push jeevan2001/backend:latest
      - docker push jeevan2001/frontend:latest
artifacts:
  files:
    - '**/*'
```

## Setting Environment Variables in AWS CodeBuild

### Create or Edit the CodeBuild Project:

1. Go to the AWS Management Console.
2. Navigate to CodeBuild.
3. If you haven't created a project yet, click on "Create build project". If you already have a project, click on the project name to edit it.

### Configure Environment Variables:

1. In the "Environment" section of the project configuration, find the "Environment variables" subsection.
2. Add the following environment variables:
     - `DOCKER_USERNAME`: Your Docker Hub username.
     - `DOCKER_PASSWORD`: Your Docker Hub password.

### Example Configuration

#### Create a CodeBuild Project:

- **Project Name**: Enter a name for your project.
- **Source**: Choose the source provider (e.g., GitHub) and connect your repository.
- **Environment**:
    - **Environment Image**: Use a managed image.
    - **Operating System**: Ubuntu.
    - **Runtime**: Standard.
    - **Image**: `aws/codebuild/standard:5.0`.
    - **Service Role**: Create a new service role or use an existing one.
- **Buildspec**:
    - Choose "Use a buildspec file".
    - Ensure the `buildspec.yml` file is in the root of your repository.
- **Environment Variables**:
    - Add environment variables for Docker Hub credentials:
        - `DOCKER_USERNAME`: Your Docker Hub username.
        - `DOCKER_PASSWORD`: Your Docker Hub password.

#### Example:

- `DOCKER_USERNAME`: your-dockerhub-username
- `DOCKER_PASSWORD`: your-dockerhub-password

### Create the Project:

Click on "Create build project".

## Summary

By ensuring that the Dockerfiles are correctly set up to handle the installation of dependencies and the generation of the `package-lock.json` file, you can manage the entire build process within AWS CodeBuild. The `buildspec.yml` file defines the build commands and environment for CodeBuild. Once the images are built and pushed to Docker Hub, you can update your Kubernetes manifests to use these images and deploy the applications to your Kubernetes cluster.