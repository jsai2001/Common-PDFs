Here are the code-only answers for the requested Docker interview questions. Each snippet is a raw Docker command, Dockerfile, or configuration file. Replace placeholders (e.g., `<RDS_ENDPOINT>`, `<AWS_ACCESS_KEY_ID>`) with actual values specific to your environment.

#### 1. **Create a Docker container running Nginx**
    ```bash
    docker run -d -p 80:80 nginx
    ```

#### 2. **Create a Docker container with a custom image from a Dockerfile**
    ```dockerfile
    # Dockerfile
    FROM nginx:latest
    COPY index.html /usr/share/nginx/html/
    ```
    ```bash
    docker build -t my-nginx .
    docker run -d -p 80:80 my-nginx
    ```

#### 3. **Create a Docker network for two containers**
    ```bash
    docker network create my-network
    docker run -d --name container1 --network my-network nginx
    docker run -d --name container2 --network my-network busybox
    ```

#### 4. **Create a Docker volume and attach it to a container**
    ```bash
    docker volume create my-volume
    docker run -d -v my-volume:/data nginx
    ```

#### 5. **Create a Docker container and connect it to an RDS instance**
    ```bash
    docker run -d \
      -e MYSQL_HOST=<RDS_ENDPOINT> \
      -e MYSQL_USER=admin \
      -e MYSQL_PASSWORD=MySecurePassword123 \
      -e MYSQL_DATABASE=mydb \
      mysql:5.7
    ```

#### 6. **Create a Docker container on an EC2 instance**
    ```bash
    # Run on EC2 instance after SSH
    docker run -d -p 80:80 nginx
    ```

#### 7. **Create a Docker container and attach it to an S3 bucket**
    ```bash
    docker run -d \
      -e AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID> \
      -e AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY> \
      -e AWS_REGION=us-east-1 \
      amazon/aws-cli s3 ls s3://my-bucket
    ```

#### 8. **Create a Docker compose file with two services**
    ```yaml
    # docker-compose.yml
    version: "3"
    services:
      web:
         image: nginx
         ports:
            - "80:80"
      app:
         image: busybox
         command: sleep 3600
    ```

#### 9. **Create a Docker container and expose it on port 8080**
    ```bash
    docker run -d -p 8080:80 nginx
    ```

#### 10. **Create a Docker container and attach it to a custom network**
     ```bash
     docker network create custom-network
     docker run -d --network custom-network nginx
     ```

#### 11. **Create a Docker container with an environment variable**
     ```bash
     docker run -d -e MY_VAR="hello" nginx
     ```

#### 12. **Create a Docker container and mount a local directory**
     ```bash
     docker run -d -v /local/path:/usr/share/nginx/html nginx
     ```

#### 13. **Create a Docker container and link it to another container**
     ```bash
     docker run -d --name db mysql:5.7
     docker run -d --link db:database nginx
     ```

#### 14. **Create a Docker container and attach it to an ECS cluster**
     ```bash
     # Assumes ECS task definition is created via AWS CLI or console
     docker build -t my-app .
     docker tag my-app <ECR_REGISTRY>/my-app:latest
     docker push <ECR_REGISTRY>/my-app:latest
     # Use AWS CLI to run task on ECS
     aws ecs run-task --cluster my-ecs-cluster --task-definition my-task --launch-type FARGATE --network-configuration "awsvpcConfiguration={subnets=[subnet-12345678],securityGroups=[sg-12345678]}"
     ```

#### 15. **Create a Docker image and push it to Amazon ECR**
```bash
docker build -t my-app .
docker tag my-app <ECR_REGISTRY>/my-app:latest
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <ECR_REGISTRY>
docker push <ECR_REGISTRY>/my-app:latest
```

#### 16. **Create a Docker container and attach it to a load balancer**
```bash
# Assumes ALB is set up with a target group in AWS
docker run -d -p 80:80 --name nginx nginx
# Register container's EC2 instance with ALB target group via AWS CLI
aws elbv2 register-targets --target-group-arn <TARGET_GROUP_ARN> --targets Id=<EC2_INSTANCE_ID>,Port=80
```

#### 17. **Create a Docker container and connect it to a DynamoDB table**
```bash
docker run -d \
    -e AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID> \
    -e AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY> \
    -e AWS_REGION=us-east-1 \
    amazon/aws-cli dynamodb list-tables
```

#### 18. **Create a Docker container and attach it to a CloudWatch log group**
```bash
docker run -d \
    --log-driver=awslogs \
    --log-opt awslogs-region=us-east-1 \
    --log-opt awslogs-group=my-log-group \
    --log-opt awslogs-create-group=true \
    nginx
```

#### 19. **Create a Docker container and run it in detached mode**
```bash
docker run -d nginx
```

#### 20. **Create a Docker container and attach it to an EBS volume on EC2**
```bash
# Run on EC2 instance with EBS volume mounted at /mnt/ebs
docker run -d -v /mnt/ebs:/data nginx
```

#### 21. **Create a Docker container and connect it to an SNS topic**
```bash
docker run -d \
    -e AWS_ACCESS_KEY_ID=<AWS_ACCESS_KEY_ID> \
    -e AWS_SECRET_ACCESS_KEY=<AWS_SECRET_ACCESS_KEY> \
    -e AWS_REGION=us-east-1 \
    amazon/aws-cli sns publish --topic-arn <SNS_TOPIC_ARN> --message "Hello from Docker"
```

#### 22. **Create a Docker container and attach it to a Route 53 domain**
```bash
docker run -d -p 80:80 --name nginx nginx
# Update Route 53 record via AWS CLI (run separately)
aws route53 change-resource-record-sets --hosted-zone-id <ZONE_ID> --change-batch '{"Changes":[{"Action":"UPSERT","ResourceRecordSet":{"Name":"nginx.example.com","Type":"A","TTL":300,"ResourceRecords":[{"Value":"<EC2_PUBLIC_IP>"}]}}]}'
```

#### 23. **Create a Docker container and run it with a specific CPU limit**
```bash
docker run -d --cpus="0.5" nginx
```

#### 24. **Create a Docker container and attach it to a Lambda function trigger**
```bash
# Lambda trigger typically set up via AWS console/CLI; container runs app
docker run -d -p 8080:8080 my-app
# Configure Lambda to invoke via API Gateway or event source separately
```

#### 25. **Create a Docker container and connect it to a Redis instance**
```bash
docker run -d \
    -e REDIS_HOST=<REDIS_ENDPOINT> \
    -e REDIS_PORT=6379 \
    redis redis-cli -h <REDIS_ENDPOINT> PING
```