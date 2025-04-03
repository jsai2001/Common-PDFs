# AWS Code-Related Interview Questions (Sorted by Priority)

## 1. Create an EC2 Instance Using the AWS CLI
```bash
aws ec2 run-instances \
    --image-id ami-0c55b159cbfafe1f0 \
    --instance-type t2.micro \
    --key-name my-key-pair \
    --security-group-ids sg-12345678 \
    --subnet-id subnet-12345678 \
    --region us-east-1
```

## 2. Create an RDS Instance Using the AWS CLI
```bash
aws rds create-db-instance \
    --db-instance-identifier my-rds-instance \
    --db-instance-class db.t3.micro \
    --engine mysql \
    --master-username admin \
    --master-user-password MySecurePassword123 \
    --allocated-storage 20 \
    --vpc-security-group-ids sg-12345678 \
    --db-subnet-group-name my-subnet-group \
    --region us-east-1
```

## 3. Create an S3 Bucket Using the AWS CLI
```bash
aws s3api create-bucket \
    --bucket my-unique-bucket-name \
    --region us-east-1
```

## 4. Create a Lambda Function Using AWS CloudFormation (YAML)
```yaml
Resources:
    MyLambdaFunction:
        Type: AWS::Lambda::Function
        Properties:
            FunctionName: my-lambda-function
            Handler: index.handler
            Runtime: nodejs18.x
            Role: arn:aws:iam::123456789012:role/lambda-exec-role
            Code:
                S3Bucket: my-code-bucket
                S3Key: lambda-code.zip
```

## 5. Create an IAM Role and Attach It to an EC2 Instance Using the AWS CLI
```bash
# Create IAM role
aws iam create-role \
    --role-name ec2-role \
    --assume-role-policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"ec2.amazonaws.com"},"Action":"sts:AssumeRole"}]}'

# Attach policy to role
aws iam attach-role-policy \
    --role-name ec2-role \
    --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess

# Create instance profile
aws iam create-instance-profile \
    --instance-profile-name ec2-instance-profile

# Add role to instance profile
aws iam add-role-to-instance-profile \
    --instance-profile-name ec2-instance-profile \
    --role-name ec2-role

# Attach to EC2 instance
aws ec2 associate-iam-instance-profile \
    --instance-id i-1234567890abcdef0 \
    --iam-instance-profile Name=ec2-instance-profile
```

## 6. Create an EBS Volume and Attach It to an EC2 Instance Using the AWS CLI
```bash
# Create EBS volume
aws ec2 create-volume \
    --availability-zone us-east-1a \
    --size 10 \
    --volume-type gp3 \
    --region us-east-1

# Attach volume to EC2 instance
aws ec2 attach-volume \
    --volume-id vol-1234567890abcdef0 \
    --instance-id i-1234567890abcdef0 \
    --device /dev/xvdf \
    --region us-east-1
```

## 7. Create a VPC with a Public Subnet Using the AWS CLI
```bash
# Create VPC
aws ec2 create-vpc \
    --cidr-block 10.0.0.0/16 \
    --region us-east-1

# Create subnet
aws ec2 create-subnet \
    --vpc-id vpc-12345678 \
    --cidr-block 10.0.1.0/24 \
    --availability-zone us-east-1a \
    --region us-east-1

# Create internet gateway
aws ec2 create-internet-gateway \
    --region us-east-1

# Attach internet gateway to VPC
aws ec2 attach-internet-gateway \
    --internet-gateway-id igw-12345678 \
    --vpc-id vpc-12345678 \
    --region us-east-1

# Create route table
aws ec2 create-route-table \
    --vpc-id vpc-12345678 \
    --region us-east-1

# Add route to internet gateway
aws ec2 create-route \
    --route-table-id rtb-12345678 \
    --destination-cidr-block 0.0.0.0/0 \
    --gateway-id igw-12345678 \
    --region us-east-1

# Associate route table with subnet
aws ec2 associate-route-table \
    --route-table-id rtb-12345678 \
    --subnet-id subnet-12345678 \
    --region us-east-1
```

## 8. Create a Security Group and Attach It to an EC2 Instance Using the AWS CLI
```bash
# Create security group
aws ec2 create-security-group \
    --group-name my-security-group \
    --description "My security group" \
    --vpc-id vpc-12345678 \
    --region us-east-1

# Add inbound rule
aws ec2 authorize-security-group-ingress \
    --group-id sg-12345678 \
    --protocol tcp \
    --port 22 \
    --cidr 0.0.0.0/0 \
    --region us-east-1

# Attach to EC2 instance
aws ec2 modify-instance-attribute \
    --instance-id i-1234567890abcdef0 \
    --groups sg-12345678 \
    --region us-east-1
```

## 9. Create an SNS Topic and Subscribe an Email Endpoint Using the AWS CLI
```bash
# Create SNS topic
aws sns create-topic \
    --name my-topic \
    --region us-east-1

# Subscribe email endpoint
aws sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:123456789012:my-topic \
    --protocol email \
    --notification-endpoint user@example.com \
    --region us-east-1
```

## 10. Create an Auto Scaling Group and Attach It to an EC2 Launch Template Using the AWS CLI
```bash
# Create launch template
aws ec2 create-launch-template \
    --launch-template-name my-launch-template \
    --version-description "v1" \
    --launch-template-data '{"ImageId":"ami-0c55b159cbfafe1f0","InstanceType":"t2.micro","KeyName":"my-key-pair","SecurityGroupIds":["sg-12345678"]}' \
    --region us-east-1

# Create Auto Scaling group
aws autoscaling create-auto-scaling-group \
    --auto-scaling-group-name my-asg \
    --launch-template LaunchTemplateName=my-launch-template,Version=1 \
    --min-size 1 \
    --max-size 3 \
    --desired-capacity 2 \
    --vpc-zone-identifier subnet-12345678,subnet-87654321 \
    --region us-east-1
```

## 11. Create a CloudWatch Alarm for an EC2 Instance Using AWS CloudFormation (YAML)
```yaml
Resources:
    MyCloudWatchAlarm:
        Type: AWS::CloudWatch::Alarm
        Properties:
            AlarmName: ec2-cpu-alarm
            MetricName: CPUUtilization
            Namespace: AWS/EC2
            Statistic: Average
            Period: 300
            EvaluationPeriods: 2
            Threshold: 80
            ComparisonOperator: GreaterThanThreshold
            Dimensions:
                - Name: InstanceId
                    Value: i-1234567890abcdef0
            AlarmActions:
                - arn:aws:sns:us-east-1:123456789012:my-topic
```

## 12. Attach an Elastic IP to an EC2 Instance Using the AWS CLI
```bash
# Allocate Elastic IP
aws ec2 allocate-address \
    --domain vpc \
    --region us-east-1

# Associate Elastic IP with instance
aws ec2 associate-address \
    --instance-id i-1234567890abcdef0 \
    --allocation-id eipalloc-12345678 \
    --region us-east-1
```

## 13. Create an RDS Read Replica for an Existing RDS Instance Using the AWS CLI
```bash
aws rds create-db-instance-read-replica \
    --db-instance-identifier my-rds-replica \
    --source-db-instance-identifier my-rds-instance \
    --db-instance-class db.t3.micro \
    --region us-east-1
```

## 14. Attach a Security Group to an RDS Instance Using the AWS CLI
```bash
aws rds modify-db-instance \
    --db-instance-identifier my-rds-instance \
    --vpc-security-group-ids sg-12345678 \
    --apply-immediately \
    --region us-east-1
```

## 15. Create an API Gateway and Attach It to a Lambda Function Using AWS CloudFormation (YAML)
```yaml
Resources:
    MyApiGateway:
        Type: AWS::ApiGateway::RestApi
        Properties:
            Name: my-api
    MyResource:
        Type: AWS::ApiGateway::Resource
        Properties:
            RestApiId: !Ref MyApiGateway
            ParentId: !GetAtt MyApiGateway.RootResourceId
            PathPart: "hello"
    MyMethod:
        Type: AWS::ApiGateway::Method
        Properties:
            RestApiId: !Ref MyApiGateway
            ResourceId: !Ref MyResource
            HttpMethod: GET
            AuthorizationType: NONE
            Integration:
                Type: AWS
                IntegrationHttpMethod: POST
                Uri: !Sub arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/${MyLambdaFunction.Arn}/invocations
    MyLambdaFunction:
        Type: AWS::Lambda::Function
        Properties:
            FunctionName: my-lambda-function
            Handler: index.handler
            Runtime: nodejs18.x
            Role: arn:aws:iam::123456789012:role/lambda-exec-role
            Code:
                S3Bucket: my-code-bucket
                S3Key: lambda-code.zip
    LambdaPermission:
        Type: AWS::Lambda::Permission
        Properties:
            Action: lambda:InvokeFunction
            FunctionName: !Ref MyLambdaFunction
            Principal: apigateway.amazonaws.com
            SourceArn: !Sub arn:aws:execute-api:us-east-1:123456789012:${MyApiGateway}/*/*/*
```

## 16. Create a Step Functions State Machine Using AWS CloudFormation (YAML)
```yaml
Resources:
    MyStateMachine:
        Type: AWS::StepFunctions::StateMachine
        Properties:
            StateMachineName: my-state-machine
            RoleArn: arn:aws:iam::123456789012:role/step-functions-role
            DefinitionString: |
                {
                    "StartAt": "Hello",
                    "States": {
                        "Hello": {
                            "Type": "Pass",
                            "Result": "Hello World",
                            "End": true
                        }
                    }
                }
```

## 17. Create an ECS Cluster and Deploy a Task Definition Using the AWS CLI
```bash
# Create ECS cluster
aws ecs create-cluster \
    --cluster-name my-ecs-cluster \
    --region us-east-1

# Register task definition
aws ecs register-task-definition \
    --family my-task \
    --network-mode awsvpc \
    --requires-compatibilities FARGATE \
    --cpu "256" \
    --memory "512" \
    --execution-role-arn arn:aws:iam::123456789012:role/ecs-task-execution-role \
    --container-definitions '[{"name":"my-container","image":"amazon/amazon-ecs-sample","essential":true,"portMappings":[{"containerPort":80,"hostPort":80}]}]' \
    --region us-east-1

# Run task
aws ecs run-task \
    --cluster my-ecs-cluster \
    --task-definition my-task \
    --launch-type FARGATE \
    --network-configuration "awsvpcConfiguration={subnets=[subnet-12345678],securityGroups=[sg-12345678],assignPublicIp=ENABLED}" \
    --region us-east-1
```
## 18. Create EC2 instances with AutoScaling group

Here's the text formatted in Markdown:

### Here’s the clean AWS CLI code to set up an Auto Scaling group with CPU and memory utilization scaling:

1. **Create Launch Template (with CloudWatch Agent)**  
   ```bash
   aws ec2 create-launch-template \
     --launch-template-name MyWebServerTemplate \
     --version-description "v1" \
     --launch-template-data '{
       "ImageId": "<ami-id>",
       "InstanceType": "t2.micro",
       "KeyName": "<key-pair-name>",
       "NetworkInterfaces": [{"AssociatePublicIpAddress": true, "DeviceIndex": 0, "SubnetId": "<subnet-id>", "Groups": ["<security-group-id>"]}],
       "UserData": "'$(echo -e "#!/bin/bash\nyum install -y amazon-cloudwatch-agent\n/opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-config-wizard" | base64)'"
     }'
   ```

2. **Create Auto Scaling Group**  
   ```bash
   aws autoscaling create-auto-scaling-group \
     --auto-scaling-group-name MyWebServerASG \
     --launch-template "LaunchTemplateName=MyWebServerTemplate,Version=1" \
     --min-size 1 \
     --max-size 4 \
     --desired-capacity 2 \
     --vpc-zone-identifier "<subnet-id-1>,<subnet-id-2>"
   ```

3. **Create Step Scaling Policy**  
   Save to `step-scaling-policy.json`:  
   ```json
   {
     "AdjustmentType": "ChangeInCapacity",
     "StepAdjustments": [{"MetricIntervalLowerBound": 0, "ScalingAdjustment": 1}],
     "Cooldown": 300
   }
   ```  
   Run:  
   ```bash
   aws autoscaling put-scaling-policy \
     --auto-scaling-group-name MyWebServerASG \
     --policy-name MyStepScalingPolicy \
     --policy-type StepScaling \
     --step-scaling-policy-configuration file://step-scaling-policy.json
   ```  
   Note the `PolicyARN` from the output.

4. **Create CloudWatch Alarms**  
   **CPU Alarm (50% Threshold)**  
   ```bash
   aws cloudwatch put-metric-alarm \
     --alarm-name HighCPUAlarm \
     --metric-name CPUUtilization \
     --namespace AWS/EC2 \
     --statistic Average \
     --period 300 \
     --evaluation-periods 2 \
     --threshold 50 \
     --comparison-operator GreaterThanThreshold \
     --dimensions Name=AutoScalingGroupName,Value=MyWebServerASG \
     --alarm-actions <scaling-policy-arn>
   ```  
   **Memory Alarm (70% Threshold)**  
   ```bash
   aws cloudwatch put-metric-alarm \
     --alarm-name HighMemoryAlarm \
     --metric-name MemoryUtilization \
     --namespace CWAgent \
     --statistic Average \
     --period 300 \
     --evaluation-periods 2 \
     --threshold 70 \
     --comparison-operator GreaterThanThreshold \
     --dimensions Name=AutoScalingGroupName,Value=MyWebServerASG \
     --alarm-actions <scaling-policy-arn>
   ```

5. **Verify**  
   ```bash
   aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names MyWebServerASG
   aws cloudwatch describe-alarms --alarm-names HighCPUAlarm HighMemoryAlarm
   ```

Replace `<ami-id>`, `<key-pair-name>`, `<subnet-id>`, `<security-group-id>`, and `<scaling-policy-arn>` with your values. Ensure the CloudWatch agent is configured to push memory metrics.
```