#### 1. **Create an EC2 instance using Terraform**
    ```hcl
    resource "aws_instance" "my_ec2" {
      ami           = "ami-0c55b159cbfafe1f0"
      instance_type = "t2.micro"
      key_name      = "my-key-pair"
      subnet_id     = "subnet-12345678"
      security_groups = ["sg-12345678"]
    }
    ```

#### 2. **Create an RDS instance using Terraform**
    ```hcl
    resource "aws_db_instance" "my_rds" {
      identifier          = "my-rds-instance"
      engine              = "mysql"
      instance_class      = "db.t3.micro"
      allocated_storage   = 20
      username            = "admin"
      password            = "MySecurePassword123"
      vpc_security_group_ids = ["sg-12345678"]
      db_subnet_group_name = "my-subnet-group"
    }
    ```

#### 3. **Create an S3 bucket using Terraform**
    ```hcl
    resource "aws_s3_bucket" "my_bucket" {
      bucket = "my-unique-bucket-name"
    }
    ```

#### 4. **Create a VPC using Terraform**
    ```hcl
    resource "aws_vpc" "my_vpc" {
      cidr_block = "10.0.0.0/16"
      tags = {
         Name = "my-vpc"
      }
    }
    ```

#### 5. **Create a security group using Terraform**
    ```hcl
    resource "aws_security_group" "my_sg" {
      name        = "my-security-group"
      description = "My security group"
      vpc_id      = "vpc-12345678"

      ingress {
         from_port   = 22
         to_port     = 22
         protocol    = "tcp"
         cidr_blocks = ["0.0.0.0/0"]
      }
    }
    ```

#### 6. **Create an IAM role using Terraform**
    ```hcl
    resource "aws_iam_role" "my_role" {
      name = "my-iam-role"
      assume_role_policy = jsonencode({
         Version = "2012-10-17"
         Statement = [{
            Action = "sts:AssumeRole"
            Effect = "Allow"
            Principal = {
              Service = "ec2.amazonaws.com"
            }
         }]
      })
    }

    resource "aws_iam_role_policy_attachment" "my_role_policy" {
      role       = aws_iam_role.my_role.name
      policy_arn = "arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"
    }
    ```

#### 7. **Create an EBS volume and attach it to an EC2 instance using Terraform**
    ```hcl
    resource "aws_ebs_volume" "my_volume" {
      availability_zone = "us-east-1a"
      size              = 10
      type              = "gp3"
    }

    resource "aws_volume_attachment" "my_attachment" {
      device_name = "/dev/xvdf"
      volume_id   = aws_ebs_volume.my_volume.id
      instance_id = aws_instance.my_ec2.id
    }
    ```

#### 8. **Create a Lambda function using Terraform**
    ```hcl
    resource "aws_lambda_function" "my_lambda" {
      function_name = "my-lambda-function"
      handler       = "index.handler"
      runtime       = "nodejs18.x"
      role          = "arn:aws:iam::123456789012:role/lambda-exec-role"
      s3_bucket     = "my-code-bucket"
      s3_key        = "lambda-code.zip"
    }
    ```

#### 9. **Create an SNS topic using Terraform**
    ```hcl
    resource "aws_sns_topic" "my_topic" {
      name = "my-sns-topic"
    }
    ```

#### 10. **Create an Auto Scaling group using Terraform**
     ```hcl
     resource "aws_launch_template" "my_lt" {
        name_prefix   = "my-lt"
        image_id      = "ami-0c55b159cbfafe1f0"
        instance_type = "t2.micro"
     }

     resource "aws_autoscaling_group" "my_asg" {
        name                = "my-asg"
        min_size            = 1
        max_size            = 3
        desired_capacity    = 2
        vpc_zone_identifier = ["subnet-12345678", "subnet-87654321"]
        launch_template {
          id      = aws_launch_template.my_lt.id
          version = "$Latest"
        }
     }
     ```

#### 11. **Attach a security group to an EC2 instance using Terraform**
     ```hcl
     resource "aws_instance" "my_ec2_with_sg" {
        ami           = "ami-0c55b159cbfafe1f0"
        instance_type = "t2.micro"
        subnet_id     = "subnet-12345678"
        security_groups = [aws_security_group.my_sg.name]
     }
     ```

#### 12. **Attach an IAM role to an EC2 instance using Terraform**
     ```hcl
     resource "aws_iam_instance_profile" "my_profile" {
        name = "my-instance-profile"
        role = aws_iam_role.my_role.name
     }

     resource "aws_instance" "my_ec2_with_role" {
        ami                  = "ami-0c55b159cbfafe1f0"
        instance_type        = "t2.micro"
        subnet_id            = "subnet-12345678"
        iam_instance_profile = aws_iam_instance_profile.my_profile.name
     }
     ```

#### 13. **Attach a subnet to a VPC using Terraform**
     ```hcl
     resource "aws_subnet" "my_subnet" {
        vpc_id     = aws_vpc.my_vpc.id
        cidr_block = "10.0.1.0/24"
        availability_zone = "us-east-1a"
     }
     ```

#### 14. **Attach an Internet Gateway to a VPC using Terraform**
```hcl
resource "aws_internet_gateway" "my_igw" {
    vpc_id = "vpc-12345678"
}
```

#### 15. **Attach a Route Table to a Subnet using Terraform**
```hcl
resource "aws_route_table" "my_route_table" {
    vpc_id = "vpc-12345678"
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.my_igw.id
    }
}

resource "aws_route_table_association" "my_route_table_assoc" {
    subnet_id      = "subnet-12345678"
    route_table_id = aws_route_table.my_route_table.id
}
```

#### 16. **Create a CloudWatch Alarm for an EC2 Instance using Terraform**
```hcl
resource "aws_cloudwatch_metric_alarm" "ec2_cpu_alarm" {
    alarm_name          = "ec2-cpu-alarm"
    comparison_operator = "GreaterThanThreshold"
    evaluation_periods  = 2
    metric_name         = "CPUUtilization"
    namespace           = "AWS/EC2"
    period              = 300
    statistic           = "Average"
    threshold           = 80
    alarm_actions       = ["arn:aws:sns:us-east-1:123456789012:my-topic"]
    dimensions = {
        InstanceId = "i-1234567890abcdef0"
    }
}
```

#### 17. **Create a DynamoDB Table using Terraform**
```hcl
resource "aws_dynamodb_table" "my_dynamodb_table" {
    name           = "my-dynamodb-table"
    billing_mode   = "PAY_PER_REQUEST"
    hash_key       = "id"
    attribute {
        name = "id"
        type = "S"
    }
}
```

#### 18. **Create an Elastic IP and Attach it to an EC2 Instance using Terraform**
```hcl
resource "aws_eip" "my_eip" {
    vpc = true
}

resource "aws_eip_association" "eip_assoc" {
    instance_id   = "i-1234567890abcdef0"
    allocation_id = aws_eip.my_eip.id
}
```

#### 19. **Create a Load Balancer using Terraform**
```hcl
resource "aws_lb" "my_alb" {
    name               = "my-alb"
    internal           = false
    load_balancer_type = "application"
    security_groups    = ["sg-12345678"]
    subnets            = ["subnet-12345678", "subnet-87654321"]
}
```

#### 20. **Attach a Security Group to an RDS Instance using Terraform**
```hcl
resource "aws_db_instance" "my_rds" {
    identifier           = "my-rds-instance"
    engine               = "mysql"
    instance_class       = "db.t3.micro"
    allocated_storage    = 20
    username             = "admin"
    password             = "MySecurePassword123"
    vpc_security_group_ids = ["sg-12345678"]
    db_subnet_group_name = "my-subnet-group"
}
```

#### 21. **Create an RDS Snapshot using Terraform**
```hcl
resource "aws_db_snapshot" "my_rds_snapshot" {
    db_instance_identifier = "my-rds-instance"
    db_snapshot_identifier = "my-rds-snapshot"
}
```

#### 22. **Create a Route 53 Record using Terraform**
```hcl
resource "aws_route53_record" "my_record" {
    zone_id = "Z1234567890ABCDEF"
    name    = "www.example.com"
    type    = "A"
    ttl     = 300
    records = ["192.0.2.1"]
}
```

#### 23. **Attach a Lambda Function to an SNS Topic using Terraform**
```hcl
resource "aws_sns_topic" "my_sns_topic" {
    name = "my-sns-topic"
}

resource "aws_lambda_function" "my_lambda" {
    function_name = "my-lambda-function"
    handler       = "index.handler"
    runtime       = "nodejs18.x"
    role          = "arn:aws:iam::123456789012:role/lambda-exec-role"
    filename      = "lambda-code.zip"
}

resource "aws_sns_topic_subscription" "lambda_sns" {
    topic_arn = aws_sns_topic.my_sns_topic.arn
    protocol  = "lambda"
    endpoint  = aws_lambda_function.my_lambda.arn
}

resource "aws_lambda_permission" "sns_invoke" {
    statement_id  = "AllowExecutionFromSNS"
    action        = "lambda:InvokeFunction"
    function_name = aws_lambda_function.my_lambda.function_name
    principal     = "sns.amazonaws.com"
    source_arn    = aws_sns_topic.my_sns_topic.arn
}
```

#### 24. **Create an ECS Cluster using Terraform**
```hcl
resource "aws_ecs_cluster" "my_ecs_cluster" {
    name = "my-ecs-cluster"
}
```

#### 25. **Create a Task Definition and Attach it to an ECS Cluster using Terraform**
```hcl
resource "aws_ecs_task_definition" "my_task" {
    family                   = "my-task"
    network_mode             = "awsvpc"
    requires_compatibilities = ["FARGATE"]
    cpu                      = "256"
    memory                   = "512"
    execution_role_arn       = "arn:aws:iam::123456789012:role/ecs-task-execution-role"
    container_definitions    = jsonencode([{
        name  = "my-container"
        image = "amazon/amazon-ecs-sample"
        essential = true
        portMappings = [{
            containerPort = 80
            hostPort      = 80
        }]
    }])
}

resource "aws_ecs_service" "my_service" {
    name            = "my-ecs-service"
    cluster         = aws_ecs_cluster.my_ecs_cluster.id
    task_definition = aws_ecs_task_definition.my_task.arn
    launch_type     = "FARGATE"
    desired_count   = 1
    network_configuration {
        subnets          = ["subnet-12345678"]
        security_groups  = ["sg-12345678"]
        assign_public_ip = true
    }
}
```

#### **Updated Terraform Code for Launching an Nginx Application on EC2 (Ubuntu 20.04 LTS)**

Below is the Terraform code to deploy an Nginx application on an EC2 instance using an Ubuntu AMI. The configuration includes all necessary resources such as VPC, subnet, security group, internet gateway, and route table. Nginx is installed via user data tailored for Ubuntu.

```hcl
# VPC
resource "aws_vpc" "nginx_vpc" {
    cidr_block = "10.0.0.0/16"
}

# Subnet
resource "aws_subnet" "nginx_subnet" {
    vpc_id     = aws_vpc.nginx_vpc.id
    cidr_block = "10.0.1.0/24"
}

# Internet Gateway
resource "aws_internet_gateway" "nginx_igw" {
    vpc_id = aws_vpc.nginx_vpc.id
}

# Route Table
resource "aws_route_table" "nginx_route_table" {
    vpc_id = aws_vpc.nginx_vpc.id
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.nginx_igw.id
    }
}

# Route Table Association
resource "aws_route_table_association" "nginx_route_assoc" {
    subnet_id      = aws_subnet.nginx_subnet.id
    route_table_id = aws_route_table.nginx_route_table.id
}

# Security Group
resource "aws_security_group" "nginx_sg" {
    vpc_id = aws_vpc.nginx_vpc.id
    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

# EC2 Instance with Nginx (Ubuntu 20.04)
resource "aws_instance" "nginx_ec2" {
    ami           = "ami-0e86e20dae9224db8"  # Ubuntu 20.04 LTS AMI (us-east-1)
    instance_type = "t2.micro"
    subnet_id     = aws_subnet.nginx_subnet.id
    security_groups = [aws_security_group.nginx_sg.name]
    key_name      = "my-key-pair"
    user_data     = <<-EOF
                                    #!/bin/bash
                                    apt-get update -y
                                    apt-get install -y nginx
                                    systemctl start nginx
                                    systemctl enable nginx
                                    EOF
    associate_public_ip_address = true
}
```

#### Notes:
- **AMI**: The AMI `ami-0e86e20dae9224db8` corresponds to Ubuntu 20.04 LTS in the `us-east-1` region. For other regions or CentOS, replace the AMI ID accordingly (e.g., CentOS 7 AMI: `ami-0b2c7a2f6f2d513d4` in `us-east-1`).
- **User Data**: The script uses `apt-get` for Ubuntu. For CentOS, replace it with `yum install -y nginx`.
- **SSH Key**: Replace `my-key-pair` with your actual SSH key pair name.
- **Access**: After running `terraform apply`, access the Nginx welcome page at `http://<public-ip>`.

Let me know if you need further adjustments!



#### **Terraform Code to Launch Nginx on EC2 (CentOS 7)**

Below is the Terraform configuration to deploy an Nginx application on an EC2 instance using a CentOS 7 AMI. This setup includes all necessary resources such as VPC, subnet, security group, internet gateway, and route table.

**Terraform Code**

```hcl
# VPC
resource "aws_vpc" "nginx_vpc" {
    cidr_block = "10.0.0.0/16"
}

# Subnet
resource "aws_subnet" "nginx_subnet" {
    vpc_id     = aws_vpc.nginx_vpc.id
    cidr_block = "10.0.1.0/24"
}

# Internet Gateway
resource "aws_internet_gateway" "nginx_igw" {
    vpc_id = aws_vpc.nginx_vpc.id
}

# Route Table
resource "aws_route_table" "nginx_route_table" {
    vpc_id = aws_vpc.nginx_vpc.id
    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.nginx_igw.id
    }
}

# Route Table Association
resource "aws_route_table_association" "nginx_route_assoc" {
    subnet_id      = aws_subnet.nginx_subnet.id
    route_table_id = aws_route_table.nginx_route_table.id
}

# Security Group
resource "aws_security_group" "nginx_sg" {
    vpc_id = aws_vpc.nginx_vpc.id
    ingress {
        from_port   = 80
        to_port     = 80
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    ingress {
        from_port   = 22
        to_port     = 22
        protocol    = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

# EC2 Instance with Nginx (CentOS 7)
resource "aws_instance" "nginx_ec2" {
    ami           = "ami-0b2c7a2f6f2d513d4"  # CentOS 7 AMI (us-east-1)
    instance_type = "t2.micro"
    subnet_id     = aws_subnet.nginx_subnet.id
    security_groups = [aws_security_group.nginx_sg.name]
    key_name      = "my-key-pair"
    user_data     = <<-EOF
                                    #!/bin/bash
                                    yum update -y
                                    yum install -y epel-release
                                    yum install -y nginx
                                    systemctl start nginx
                                    systemctl enable nginx
                                    EOF
    associate_public_ip_address = true
}
```

#### Notes

- **AMI**: The AMI ID `ami-0b2c7a2f6f2d513d4` corresponds to CentOS 7 in the `us-east-1` region. Update the AMI ID if deploying in a different region.
- **User Data**: The script installs and starts Nginx on CentOS 7. It includes the `epel-release` package to access the EPEL repository, which provides Nginx.
- **Key Pair**: Replace `my-key-pair` with the name of your SSH key pair.

After running `terraform apply`, you can access the Nginx welcome page at `http://<public-ip>`.
```

#### **Connecting an RDS Instance to an EC2 Instance Using Terraform**

Below is the Terraform code to connect an RDS instance to an EC2 instance. This setup assumes the EC2 instance will access the RDS instance over the network using a security group and VPC configuration.

```hcl
# VPC
resource "aws_vpc" "my_vpc" {
    cidr_block = "10.0.0.0/16"
}

# Subnet
resource "aws_subnet" "my_subnet" {
    vpc_id     = aws_vpc.my_vpc.id
    cidr_block = "10.0.1.0/24"
}

# Security Group for EC2
resource "aws_security_group" "ec2_sg" {
    vpc_id = aws_vpc.my_vpc.id
    egress {
        from_port   = 0
        to_port     = 0
        protocol    = "-1"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

# Security Group for RDS
resource "aws_security_group" "rds_sg" {
    vpc_id = aws_vpc.my_vpc.id
    ingress {
        from_port       = 3306
        to_port         = 3306
        protocol        = "tcp"
        security_groups = [aws_security_group.ec2_sg.id]
    }
}

# RDS Subnet Group
resource "aws_db_subnet_group" "my_subnet_group" {
    name       = "my-subnet-group"
    subnet_ids = [aws_subnet.my_subnet.id]
}

# RDS Instance
resource "aws_db_instance" "my_rds" {
    identifier           = "my-rds-instance"
    engine               = "mysql"
    instance_class       = "db.t3.micro"
    allocated_storage    = 20
    username             = "admin"
    password             = "MySecurePassword123"
    vpc_security_group_ids = [aws_security_group.rds_sg.id]
    db_subnet_group_name = aws_db_subnet_group.my_subnet_group.name
    skip_final_snapshot  = true
}

# EC2 Instance
resource "aws_instance" "my_ec2" {
    ami           = "ami-0c55b159cbfafe1f0"
    instance_type = "t2.micro"
    subnet_id     = aws_subnet.my_subnet.id
    security_groups = [aws_security_group.ec2_sg.name]
    key_name      = "my-key-pair"
}
```

#### Explanation

1. **VPC and Subnet**: Creates a VPC and a subnet for the resources.
2. **Security Groups**:
     - EC2 security group allows outbound traffic.
     - RDS security group allows MySQL traffic (port 3306) from the EC2 security group.
3. **RDS Subnet Group**: Configures the RDS instance to use the specified subnet.
4. **RDS Instance**: Creates a MySQL RDS instance with the specified configuration.
5. **EC2 Instance**: Creates an EC2 instance in the same subnet with its security group.

You can connect to the RDS instance from the EC2 instance using the RDS endpoint (e.g., `my-rds-instance.abcdef123456.us-east-1.rds.amazonaws.com`) and port `3306` with the provided username and password.