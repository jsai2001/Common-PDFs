Here are the code-only answers for the requested GitHub Actions interview questions. Each snippet is a raw GitHub Actions workflow (YAML) that you can save in `.github/workflows/` (e.g., `deploy.yml`) and run in a GitHub repository. These workflows use AWS CLI commands and assume AWS credentials are configured as GitHub Secrets (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_REGION`). Replace placeholders (e.g., `ami-0c55b159cbfafe1f0`, `my-key-pair`) with actual values.

### 1. Create an EC2 instance using GitHub Actions
```yaml
name: Create EC2 Instance
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Launch EC2 Instance
                run: |
                    aws ec2 run-instances \
                        --image-id ami-0c55b159cbfafe1f0 \
                        --instance-type t2.micro \
                        --key-name my-key-pair \
                        --security-group-ids sg-12345678 \
                        --subnet-id subnet-12345678
```

### 2. Create an RDS instance using GitHub Actions
```yaml
name: Create RDS Instance
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Launch RDS Instance
                run: |
                    aws rds create-db-instance \
                        --db-instance-identifier my-rds-instance \
                        --db-instance-class db.t3.micro \
                        --engine mysql \
                        --master-username admin \
                        --master-user-password MySecurePassword123 \
                        --allocated-storage 20 \
                        --vpc-security-group-ids sg-12345678 \
                        --db-subnet-group-name my-subnet-group
```

### 3. Create an S3 bucket using GitHub Actions
```yaml
name: Create S3 Bucket
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create S3 Bucket
                run: |
                    aws s3api create-bucket \
                        --bucket my-unique-bucket-name
```

### 4. Create a VPC using GitHub Actions
```yaml
name: Create VPC
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create VPC
                run: |
                    aws ec2 create-vpc \
                        --cidr-block 10.0.0.0/16
```

### 5. Create a security group using GitHub Actions
```yaml
name: Create Security Group
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Security Group
                run: |
                    aws ec2 create-security-group \
                        --group-name my-security-group \
                        --description "My security group" \
                        --vpc-id vpc-12345678
                    aws ec2 authorize-security-group-ingress \
                        --group-name my-security-group \
                        --protocol tcp \
                        --port 22 \
                        --cidr 0.0.0.0/0
```

### 6. Create an IAM role using GitHub Actions
```yaml
name: Create IAM Role
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create IAM Role
                run: |
                    aws iam create-role \
                        --role-name my-iam-role \
                        --assume-role-policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"ec2.amazonaws.com"},"Action":"sts:AssumeRole"}]}'
                    aws iam attach-role-policy \
                        --role-name my-iam-role \
                        --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
```

### 7. Create an EBS volume and attach it to an EC2 instance using GitHub Actions
```yaml
name: Create and Attach EBS Volume
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create and Attach EBS Volume
                run: |
                    VOLUME_ID=$(aws ec2 create-volume \
                        --availability-zone us-east-1a \
                        --size 10 \
                        --volume-type gp3 \
                        --query VolumeId --output text)
                    aws ec2 attach-volume \
                        --volume-id $VOLUME_ID \
                        --instance-id i-1234567890abcdef0 \
                        --device /dev/xvdf
```

### 8. Create a Lambda function using GitHub Actions
```yaml
name: Create Lambda Function
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Lambda Function
                run: |
                    aws lambda create-function \
                        --function-name my-lambda-function \
                        --runtime nodejs18.x \
                        --role arn:aws:iam::123456789012:role/lambda-exec-role \
                        --handler index.handler \
                        --zip-file fileb://lambda-code.zip
```

### 9. Create an SNS topic using GitHub Actions
```yaml
name: Create SNS Topic
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create SNS Topic
                run: |
                    aws sns create-topic \
                        --name my-sns-topic
```

### 10. Create an Auto Scaling group using GitHub Actions
```yaml
name: Create Auto Scaling Group
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Auto Scaling Group
                run: |
                    aws autoscaling create-auto-scaling-group \
                        --auto-scaling-group-name my-asg \
                        --launch-template LaunchTemplateName=my-launch-template,Version=1 \
                        --min-size 1 \
                        --max-size 3 \
                        --desired-capacity 2 \
                        --vpc-zone-identifier subnet-12345678,subnet-87654321
```

### 11. Attach a security group to an EC2 instance using GitHub Actions
```yaml
name: Attach Security Group to EC2
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach Security Group
                run: |
                    aws ec2 modify-instance-attribute \
                        --instance-id i-1234567890abcdef0 \
                        --groups sg-12345678
```

### 12. Attach an IAM role to an EC2 instance using GitHub Actions
```yaml
name: Attach IAM Role to EC2
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach IAM Role
                run: |
                    aws iam create-instance-profile \
                        --instance-profile-name my-instance-profile
                    aws iam add-role-to-instance-profile \
                        --instance-profile-name my-instance-profile \
                        --role-name my-iam-role
                    aws ec2 associate-iam-instance-profile \
                        --instance-id i-1234567890abcdef0 \
                        --iam-instance-profile Name=my-instance-profile
```

### 13. Attach a subnet to a VPC using GitHub Actions
```yaml
name: Attach Subnet to VPC
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Subnet
                run: |
                    aws ec2 create-subnet \
                        --vpc-id vpc-12345678 \
                        --cidr-block 10.0.1.0/24 \
                        --availability-zone us-east-1a
```

### 14. Attach an internet gateway to a VPC using GitHub Actions
```yaml
name: Attach Internet Gateway to VPC
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach Internet Gateway
                run: |
                    IGW_ID=$(aws ec2 create-internet-gateway --query InternetGateway.InternetGatewayId --output text)
                    aws ec2 attach-internet-gateway \
                        --internet-gateway-id $IGW_ID \
                        --vpc-id vpc-12345678
```

### 15. Attach a route table to a subnet using GitHub Actions

```yaml
name: Attach Route Table to Subnet
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach Route Table
                run: |
                    RTB_ID=$(aws ec2 create-route-table --vpc-id vpc-12345678 --query RouteTable.RouteTableId --output text)
                    aws ec2 create-route \
                        --route-table-id $RTB_ID \
                        --destination-cidr-block 0.0.0.0/0 \
                        --gateway-id igw-12345678
                    aws ec2 associate-route-table \
                        --route-table-id $RTB_ID \
                        --subnet-id subnet-12345678
```

### 16. Create a CloudWatch alarm for an EC2 instance using GitHub Actions

```yaml
name: Create CloudWatch Alarm
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create CloudWatch Alarm
                run: |
                    aws cloudwatch put-metric-alarm \
                        --alarm-name ec2-cpu-alarm \
                        --metric-name CPUUtilization \
                        --namespace AWS/EC2 \
                        --statistic Average \
                        --period 300 \
                        --evaluation-periods 2 \
                        --threshold 80 \
                        --comparison-operator GreaterThanThreshold \
                        --dimensions Name=InstanceId,Value=i-1234567890abcdef0 \
                        --alarm-actions arn:aws:sns:us-east-1:123456789012:my-topic
```

### 17. Create a DynamoDB table using GitHub Actions

```yaml
name: Create DynamoDB Table
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create DynamoDB Table
                run: |
                    aws dynamodb create-table \
                        --table-name my-dynamodb-table \
                        --attribute-definitions AttributeName=id,AttributeType=S \
                        --key-schema AttributeName=id,KeyType=HASH \
                        --billing-mode PAY_PER_REQUEST
```

### 18. Create an Elastic IP and attach it to an EC2 instance using GitHub Actions

```yaml
name: Create and Attach Elastic IP
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create and Attach Elastic IP
                run: |
                    EIP_ALLOC_ID=$(aws ec2 allocate-address --domain vpc --query AllocationId --output text)
                    aws ec2 associate-address \
                        --instance-id i-1234567890abcdef0 \
                        --allocation-id $EIP_ALLOC_ID
```

### 19. Create a load balancer using GitHub Actions

```yaml
name: Create Load Balancer
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Load Balancer
                run: |
                    aws elbv2 create-load-balancer \
                        --name my-alb \
                        --subnets subnet-12345678 subnet-87654321 \
                        --security-groups sg-12345678 \
                        --scheme internet-facing \
                        --type application
```

### 20. Attach a security group to an RDS instance using GitHub Actions

```yaml
name: Attach Security Group to RDS
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach Security Group
                run: |
                    aws rds modify-db-instance \
                        --db-instance-identifier my-rds-instance \
                        --vpc-security-group-ids sg-12345678 \
                        --apply-immediately
```

### 21. Create an RDS snapshot using GitHub Actions

```yaml
name: Create RDS Snapshot
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create RDS Snapshot
                run: |
                    aws rds create-db-snapshot \
                        --db-instance-identifier my-rds-instance \
                        --db-snapshot-identifier my-rds-snapshot
```

### 22. Create a Route 53 record using GitHub Actions

```yaml
name: Create Route 53 Record
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Route 53 Record
                run: |
                    aws route53 change-resource-record-sets \
                        --hosted-zone-id Z1234567890ABCDEF \
                        --change-batch '{"Changes":[{"Action":"CREATE","ResourceRecordSet":{"Name":"www.example.com","Type":"A","TTL":300,"ResourceRecords":[{"Value":"192.0.2.1"}]}}]}'
```

### 23. Attach a Lambda function to an SNS topic using GitHub Actions

```yaml
name: Attach Lambda to SNS Topic
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Attach Lambda to SNS
                run: |
                    aws sns subscribe \
                        --topic-arn arn:aws:sns:us-east-1:123456789012:my-sns-topic \
                        --protocol lambda \
                        --notification-endpoint arn:aws:lambda:us-east-1:123456789012:function:my-lambda-function
```

### 24. Create an ECS cluster using GitHub Actions

```yaml
name: Create ECS Cluster
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create ECS Cluster
                run: |
                    aws ecs create-cluster \
                        --cluster-name my-ecs-cluster
```

### 25. Create a task definition and attach it to an ECS cluster using GitHub Actions

```yaml
name: Create Task Definition and Attach to ECS
on: [push]
jobs:
    deploy:
        runs-on: ubuntu-latest
        steps:
            - name: Configure AWS Credentials
                uses: aws-actions/configure-aws-credentials@v1
                with:
                    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
                    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
                    aws-region: ${{ secrets.AWS_REGION }}
            - name: Create Task Definition and Run Task
                run: |
                    aws ecs register-task-definition \
                        --family my-task \
                        --network-mode awsvpc \
                        --requires-compatibilities FARGATE \
                        --cpu "256" \
                        --memory "512" \
                        --execution-role-arn arn:aws:iam::123456789012:role/ecs-task-execution-role \
                        --container-definitions '[{"name":"my-container","image":"amazon/amazon-ecs-sample","essential":true,"portMappings":[{"containerPort":80,"hostPort":80}]}]'
                    aws ecs run-task \
                        --cluster my-ecs-cluster \
                        --task-definition my-task \
                        --launch-type FARGATE \
                        --network-configuration "awsvpcConfiguration={subnets=[subnet-12345678],securityGroups=[sg-12345678],assignPublicIp=ENABLED}"
```