### 1. **Create an EC2 instance using Ansible**
```yaml
- name: Create an EC2 instance
    hosts: localhost
    tasks:
        - name: Launch EC2 instance
            amazon.aws.ec2_instance:
                name: "my-ec2-instance"
                key_name: "my-key-pair"
                instance_type: "t2.micro"
                image_id: "ami-0c55b159cbfafe1f0"
                region: "us-east-1"
                vpc_subnet_id: "subnet-12345678"
                security_group: "sg-12345678"
```

### 2. **Create an RDS instance using Ansible**
```yaml
- name: Create an RDS instance
    hosts: localhost
    tasks:
        - name: Launch RDS instance
            amazon.aws.rds_instance:
                identifier: "my-rds-instance"
                engine: "mysql"
                instance_type: "db.t3.micro"
                username: "admin"
                password: "MySecurePassword123"
                allocated_storage: 20
                region: "us-east-1"
                vpc_security_group_ids: "sg-12345678"
                subnet_group: "my-subnet-group"
```

### 3. **Create an S3 bucket using Ansible**
```yaml
- name: Create an S3 bucket
    hosts: localhost
    tasks:
        - name: Create S3 bucket
            amazon.aws.s3_bucket:
                name: "my-unique-bucket-name"
                region: "us-east-1"
```

### 4. **Create a VPC using Ansible**
```yaml
- name: Create a VPC
    hosts: localhost
    tasks:
        - name: Create VPC
            amazon.aws.ec2_vpc_net:
                name: "my-vpc"
                cidr_block: "10.0.0.0/16"
                region: "us-east-1"
```

### 5. **Create a security group using Ansible**
```yaml
- name: Create a security group
    hosts: localhost
    tasks:
        - name: Create security group
            amazon.aws.ec2_security_group:
                name: "my-security-group"
                description: "My security group"
                vpc_id: "vpc-12345678"
                region: "us-east-1"
                rules:
                    - proto: tcp
                        ports: 22
                        cidr_ip: "0.0.0.0/0"
```

### 6. **Create an IAM role using Ansible**
```yaml
- name: Create an IAM role
    hosts: localhost
    tasks:
        - name: Create IAM role
            amazon.aws.iam_role:
                name: "my-iam-role"
                assume_role_policy_document: '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"ec2.amazonaws.com"},"Action":"sts:AssumeRole"}]}'
                managed_policy: "AmazonS3ReadOnlyAccess"
                region: "us-east-1"
```

### 7. **Create an EBS volume and attach it to an EC2 instance using Ansible**
```yaml
- name: Create and attach an EBS volume
    hosts: localhost
    tasks:
        - name: Create EBS volume
            amazon.aws.ec2_vol:
                volume_size: 10
                volume_type: "gp3"
                region: "us-east-1"
                availability_zone: "us-east-1a"
            register: volume
        - name: Attach EBS volume to EC2 instance
            amazon.aws.ec2_vol:
                instance: "i-1234567890abcdef0"
                id: "{{ volume.volume_id }}"
                device_name: "/dev/xvdf"
                region: "us-east-1"
```

### 8. **Create a Lambda function using Ansible**
```yaml
- name: Create a Lambda function
    hosts: localhost
    tasks:
        - name: Create Lambda function
            amazon.aws.lambda_function:
                name: "my-lambda-function"
                runtime: "nodejs18.x"
                role: "arn:aws:iam::123456789012:role/lambda-exec-role"
                handler: "index.handler"
                s3_bucket: "my-code-bucket"
                s3_key: "lambda-code.zip"
                region: "us-east-1"
```

### 9. **Create an SNS topic using Ansible**
```yaml
- name: Create an SNS topic
    hosts: localhost
    tasks:
        - name: Create SNS topic
            amazon.aws.sns_topic:
                name: "my-sns-topic"
                region: "us-east-1"
```

### 10. **Create an Auto Scaling group using Ansible**
```yaml
- name: Create an Auto Scaling group
    hosts: localhost
    tasks:
        - name: Create Auto Scaling group
            amazon.aws.autoscaling_group:
                name: "my-asg"
                launch_template:
                    name: "my-launch-template"
                    version: "$Latest"
                min_size: 1
                max_size: 3
                desired_capacity: 2
                vpc_zone_identifier: ["subnet-12345678", "subnet-87654321"]
                region: "us-east-1"
```

### 11. **Attach a security group to an EC2 instance using Ansible**
```yaml
- name: Attach a security group to an EC2 instance
    hosts: localhost
    tasks:
        - name: Modify EC2 instance security group
            amazon.aws.ec2_instance:
                instance_ids: "i-1234567890abcdef0"
                security_groups: "sg-12345678"
                region: "us-east-1"
                state: present
```

### 12. **Attach an IAM role to an EC2 instance using Ansible**
```yaml
- name: Attach an IAM role to an EC2 instance
    hosts: localhost
    tasks:
        - name: Create instance profile and attach role
            amazon.aws.iam_instance_profile:
                name: "my-instance-profile"
                roles: "my-iam-role"
                region: "us-east-1"
        - name: Attach instance profile to EC2
            amazon.aws.ec2_instance:
                instance_ids: "i-1234567890abcdef0"
                iam_instance_profile: "my-instance-profile"
                region: "us-east-1"
                state: present
```

### 13. **Attach a subnet to a VPC using Ansible**
```yaml
- name: Attach a subnet to a VPC
    hosts: localhost
    tasks:
        - name: Create subnet in VPC
            amazon.aws.ec2_vpc_subnet:
                vpc_id: "vpc-12345678"
                cidr: "10.0.1.0/24"
                az: "us-east-1a"
                region: "us-east-1"
```

### 14. **Attach an internet gateway to a VPC using Ansible**
```yaml
- name: Attach an internet gateway to a VPC
    hosts: localhost
    tasks:
        - name: Create internet gateway
            amazon.aws.ec2_vpc_igw:
                vpc_id: "vpc-12345678"
                region: "us-east-1"
                state: present
```

### 15. **Attach a route table to a subnet using Ansible**
```yaml
- name: Attach a route table to a subnet
    hosts: localhost
    tasks:
        - name: Create route table
            amazon.aws.ec2_vpc_route_table:
                vpc_id: "vpc-12345678"
                region: "us-east-1"
                routes:
                    - dest: "0.0.0.0/0"
                        gateway_id: "igw-12345678"
            register: route_table
        - name: Associate route table with subnet
            amazon.aws.ec2_vpc_route_table_association:
                route_table_id: "{{ route_table.route_table_id }}"
                subnet_id: "subnet-12345678"
                region: "us-east-1"
```

### 16. **Create a CloudWatch alarm for an EC2 instance using Ansible**
```yaml
- name: Create a CloudWatch alarm for an EC2 instance
    hosts: localhost
    tasks:
        - name: Create CloudWatch alarm
            amazon.aws.cloudwatch_metric_alarm:
                alarm_name: "ec2-cpu-alarm"
                metric_name: "CPUUtilization"
                namespace: "AWS/EC2"
                statistic: "Average"
                period: 300
                evaluation_periods: 2
                threshold: 80
                comparison_operator: "GreaterThanThreshold"
                dimensions:
                    InstanceId: "i-1234567890abcdef0"
                alarm_actions: ["arn:aws:sns:us-east-1:123456789012:my-topic"]
                region: "us-east-1"
```

### 17. **Create a DynamoDB table using Ansible**

```yaml
- name: Create a DynamoDB table
    hosts: localhost
    tasks:
        - name: Create DynamoDB table
            amazon.aws.dynamodb_table:
                name: "my-dynamodb-table"
                hash_key_name: "id"
                hash_key_type: "S"
                read_capacity: 5
                write_capacity: 5
                region: "us-east-1"
```

### 18. **Create an Elastic IP and attach it to an EC2 instance using Ansible**

```yaml
- name: Create and attach an Elastic IP
    hosts: localhost
    tasks:
        - name: Allocate Elastic IP
            amazon.aws.ec2_eip:
                region: "us-east-1"
                in_vpc: true
            register: eip
        - name: Associate Elastic IP with EC2 instance
            amazon.aws.ec2_eip:
                instance_id: "i-1234567890abcdef0"
                public_ip: "{{ eip.public_ip }}"
                region: "us-east-1"
```

### 19. **Create a load balancer using Ansible**

```yaml
- name: Create a load balancer
    hosts: localhost
    tasks:
        - name: Create Application Load Balancer
            amazon.aws.elb_application_lb:
                name: "my-alb"
                subnets: ["subnet-12345678", "subnet-87654321"]
                security_groups: ["sg-12345678"]
                scheme: "internet-facing"
                region: "us-east-1"
```

### 20. **Attach a security group to an RDS instance using Ansible**

```yaml
- name: Attach a security group to an RDS instance
    hosts: localhost
    tasks:
        - name: Modify RDS instance security group
            amazon.aws.rds_instance:
                identifier: "my-rds-instance"
                vpc_security_group_ids: ["sg-12345678"]
                region: "us-east-1"
                apply_immediately: true
```

### 21. **Create an RDS snapshot using Ansible**

```yaml
- name: Create an RDS snapshot
    hosts: localhost
    tasks:
        - name: Create RDS snapshot
            amazon.aws.rds_snapshot:
                db_instance_identifier: "my-rds-instance"
                db_snapshot_identifier: "my-rds-snapshot"
                region: "us-east-1"
```

### 22. **Create a Route 53 record using Ansible**

```yaml
- name: Create a Route 53 record
    hosts: localhost
    tasks:
        - name: Create Route 53 record
            amazon.aws.route53:
                state: present
                zone: "example.com"
                record: "www.example.com"
                type: "A"
                value: "192.0.2.1"
                region: "us-east-1"
```

### 23. **Attach a Lambda function to an SNS topic using Ansible**

```yaml
- name: Attach a Lambda function to an SNS topic
    hosts: localhost
    tasks:
        - name: Subscribe Lambda to SNS topic
            amazon.aws.sns_topic_subscription:
                topic_arn: "arn:aws:sns:us-east-1:123456789012:my-sns-topic"
                protocol: "lambda"
                endpoint: "arn:aws:lambda:us-east-1:123456789012:function:my-lambda-function"
                region: "us-east-1"
```

### 24. **Create an ECS cluster using Ansible**

```yaml
- name: Create an ECS cluster
    hosts: localhost
    tasks:
        - name: Create ECS cluster
            amazon.aws.ecs_cluster:
                name: "my-ecs-cluster"
                region: "us-east-1"
```

### 25. **Create a task definition and attach it to an ECS cluster using Ansible**

```yaml
- name: Create a task definition and attach it to an ECS cluster
    hosts: localhost
    tasks:
        - name: Create ECS task definition
            amazon.aws.ecs_taskdefinition:
                family: "my-task"
                network_mode: "awsvpc"
                cpu: "256"
                memory: "512"
                execution_role_arn: "arn:aws:iam::123456789012:role/ecs-task-execution-role"
                containers:
                    - name: "my-container"
                        image: "amazon/amazon-ecs-sample"
                        essential: true
                        portMappings:
                            - containerPort: 80
                                hostPort: 80
                region: "us-east-1"
        - name: Run task in ECS cluster
            amazon.aws.ecs_task:
                cluster: "my-ecs-cluster"
                task_definition: "my-task"
                launch_type: "FARGATE"
                network_configuration:
                    awsvpc_configuration:
                        subnets: ["subnet-12345678"]
                        security_groups: ["sg-12345678"]
                        assign_public_ip: "ENABLED"
                region: "us-east-1"
```