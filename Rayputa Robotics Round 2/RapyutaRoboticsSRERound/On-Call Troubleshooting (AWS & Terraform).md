## AWS CLI-Based Questions

**How would you use AWS CLI to find all EC2 instances in a specific VPC?**

```bash
aws ec2 describe-instances --filters "Name=vpc-id,Values=vpc-12345678" --query "Reservations[].Instances[].InstanceId" --output text
```

* Uses `describe-instances` with a filter for the VPC ID. Replace `vpc-12345678` with your VPC ID. Outputs instance IDs.

**Write a command to stop all running EC2 instances in a given region.**

```bash
aws ec2 stop-instances --instance-ids $(aws ec2 describe-instances --region us-east-1 --filters "Name=instance-state-name,Values=running" --query "Reservations[].Instances[].InstanceId" --output text)
```

* First queries running instances in the region (e.g., `us-east-1`), then passes those IDs to `stop-instances`. Adjust region as needed.

**How do you check the current size of an S3 bucket using AWS CLI?**

```bash
aws s3api list-objects-v2 --bucket my-bucket --query "[sum(Contents[].Size)]" --output text
```

* Lists all objects in `my-bucket` and sums their sizes in bytes. For a human-readable total, pipe to a tool like `awk` or use CloudWatch metrics for precision.

**Use AWS CLI to create a new security group and allow port 80 traffic.**

```bash
aws ec2 create-security-group --group-name MySG --description "Allow HTTP" --vpc-id vpc-12345678 && \
aws ec2 authorize-security-group-ingress --group-name MySG --protocol tcp --port 80 --cidr 0.0.0.0/0
```

* Creates a security group `MySG` in the specified VPC, then adds a rule for TCP port 80 from anywhere (`0.0.0.0/0`). Replace VPC ID.

**How would you list all IAM users created in the last 30 days?**

```bash
aws iam list-users --query "Users[?CreateDate>=`2025-03-08`].UserName" --output text
```

* Filters `list-users` by `CreateDate` (30 days back from April 07, 2025, is March 08). Adjust date if needed.

**Write a command to download the latest CloudWatch logs for a Lambda function.**

```bash
aws logs get-log-events --log-group-name /aws/lambda/my-function --log-stream-name $(aws logs describe-log-streams --log-group-name /aws/lambda/my-function --order-by LastEventTime --descending --limit 1 --query "logStreams[0].logStreamName" --output text) --output text > lambda_logs.txt
```

* Finds the latest log stream for `my-function` and downloads its events to `lambda_logs.txt`. Replace function name.

**How do you use AWS CLI to scale an Auto Scaling group to 5 instances?**

```bash
aws autoscaling set-desired-capacity --auto-scaling-group-name my-asg --desired-capacity 5
```

* Sets the desired capacity of `my-asg` to 5 instances. Replace ASG name. Add `--honor-cooldown` if needed.

**Check the status of an RDS instance and reboot it if it’s not available.**

```bash
STATUS=$(aws rds describe-db-instances --db-instance-identifier my-rds --query "DBInstances[0].DBInstanceStatus" --output text); \
if [ "$STATUS" != "available" ]; then aws rds reboot-db-instance --db-instance-identifier my-rds; fi
```

* Checks status of `my-rds`. If not available, reboots it. Replace instance ID.

**How would you delete all objects in an S3 bucket without deleting the bucket itself?**

```bash
aws s3 rm s3://my-bucket --recursive
```

* Recursively deletes all objects in `my-bucket`. Bucket stays intact. Add `--dryrun` to test first.

**Use AWS CLI to attach an EBS volume to an EC2 instance.**

```bash
aws ec2 attach-volume --volume-id vol-12345678 --instance-id i-12345678 --device /dev/xvdf
```

* Attaches volume `vol-12345678` to instance `i-12345678` as `/dev/xvdf`. Replace IDs and device name as needed.

**11. How do you list all CloudWatch alarms in an ‘ALARM’ state?**
Use the AWS CLI to filter CloudWatch alarms by state:
```bash
aws cloudwatch describe-alarms --state-value ALARM
```
This lists all alarms currently in the "ALARM" state across all regions unless you specify `--region`.

**12. Write a command to create a snapshot of an EBS volume.**
Replace `<volume-id>` with your EBS volume ID:
```bash
aws ec2 create-snapshot --volume-id <volume-id> --description "Snapshot of EBS volume"
```
Add `--region` if needed.

**13. How would you use AWS CLI to tag all untagged EC2 instances with a specific key-value pair?**
First, list untagged instances, then tag them. Here’s a script-like approach:
```bash
aws ec2 describe-instances --query 'Reservations[*].Instances[?not_null(Tags)==`false`].[InstanceId]' --output text | xargs -I {} aws ec2 create-tags --resources {} --tags Key=Environment,Value=Dev
```
This tags untagged instances with `Environment=Dev`. Adjust the key-value pair as needed.

**14. Check the health of an ELB (Elastic Load Balancer) and remove an unhealthy instance.**
Check health:
```bash
aws elb describe-instance-health --load-balancer-name <elb-name>
```
Remove an unhealthy instance (replace `<instance-id>`):
```bash
aws elb deregister-instances-from-load-balancer --load-balancer-name <elb-name> --instances <instance-id>
```
You’d identify unhealthy instances from the first command’s output (look for `OutOfService` state).

**15. How do you use AWS CLI to enable termination protection on an EC2 instance?**
Replace `<instance-id>` with your instance ID:
```bash
aws ec2 modify-instance-attribute --instance-id <instance-id> --disable-api-termination
```
This sets termination protection to `true`.

**16. List all S3 buckets with versioning disabled.**
Use a loop since `list-buckets` doesn’t show versioning directly:
```bash
aws s3api list-buckets --query 'Buckets[].Name' --output text | xargs -I {} sh -c 'aws s3api get-bucket-versioning --bucket {} | grep -q "Enabled" || echo {}'
```
This outputs bucket names where versioning isn’t enabled.

**17. How would you use AWS CLI to rotate an IAM access key for a user?**
Steps: Create a new key, test it, then delete the old one. Replace `<username>`:
Create new key:
```bash
aws iam create-access-key --user-name <username>
```
(Manually update apps with the new key from output.)
Delete old key (replace `<old-key-id>`):
```bash
aws iam delete-access-key --user-name <username> --access-key-id <old-key-id>
```

**18. Write a command to copy a file from one S3 bucket to another in a different region.**
Replace source/destination details:
```bash
aws s3 cp s3://source-bucket/file.txt s3://destination-bucket/file.txt --source-region us-east-1 --region us-west-2
```
Ensure the IAM role has cross-region permissions.

**19. How do you check the current IAM role attached to an EC2 instance?**
From the instance’s metadata (run on the EC2 itself):
```bash
curl [http://169.254.169.254/latest/meta-data/iam/info](http://169.254.169.254/latest/meta-data/iam/info)
```
Or via AWS CLI (replace `<instance-id>`):
```bash
aws ec2 describe-instances --instance-ids <instance-id> --query 'Reservations[0].Instances[0].IamInstanceProfile'
```
Outputs the ARN or name of the attached role.

**20. Use AWS CLI to increase the size of an EBS volume.**
Replace `<volume-id>` and specify new size in GiB:
```bash
aws ec2 modify-volume --volume-id <volume-id> --size 100
```
After, extend the file system on the instance (e.g., `resize2fs` for Linux).

**21. How would you list all running ECS tasks in a specific cluster?**
Use `aws ecs list-tasks` to get task ARNs, filtering by cluster and status:
```bash
aws ecs list-tasks --cluster <cluster-name> --desired-status RUNNING
```
To see detailed info, pipe into `describe-tasks`:
```bash
aws ecs describe-tasks --cluster <cluster-name> --tasks $(aws ecs list-tasks --cluster <cluster-name> --desired-status RUNNING --query 'taskArns' --output text)
```

**22. Write a command to invoke a Lambda function and capture its output.**
Invoke the function and save the response to a file:
```bash
aws lambda invoke --function-name <function-name> output.json
```
The output (e.g., JSON payload) is written to `output.json`. Check it with `cat output.json`.

**23. How do you use AWS CLI to check the status of an EKS cluster?**
Use `aws eks describe-cluster` to get cluster details, including status:
```bash
aws eks describe-cluster --name <cluster-name> --query 'cluster.status' --output text
```
This returns something like `ACTIVE` or `CREATING`. For full details, omit the `--query`.

**24. List all DynamoDB tables and their provisioned throughput settings.**
List tables first:
```bash
aws dynamodb list-tables
```
Then, for each table’s throughput, loop with `describe-table`:
```bash
for table in $(aws dynamodb list-tables --query 'TableNames' --output text); do aws dynamodb describe-table --table-name $table --query 'Table.{Name:TableName,ReadCapacity:ProvisionedThroughput.ReadCapacityUnits,WriteCapacity:ProvisionedThroughput.WriteCapacityUnits}' --output table; done
```
This shows table names with read/write capacity (if provisioned; serverless tables skip this).

**25. How would you use AWS CLI to enable encryption on an unencrypted S3 bucket?**
Apply a bucket policy with server-side encryption (e.g., AES256):
```bash
aws s3api put-bucket-encryption --bucket <bucket-name> --server-side-encryption-configuration '{"Rules":[{"ApplyServerSideEncryptionByDefault":{"SSEAlgorithm":"AES256"}}]}'
```
This sets default encryption for new objects. Existing objects need separate handling (e.g., `aws s3 cp` with `--sse`).

**26. Write a command to restore an RDS database from a specific snapshot.**
Restore from a snapshot to a new instance:
```bash
aws rds restore-db-instance-from-db-snapshot --db-instance-identifier <new-db-name> --db-snapshot-identifier <snapshot-id>
```
Replace `<new-db-name>` with the new instance name and `<snapshot-id>` with the snapshot ARN or ID.

**27. How do you check the CloudTrail events for a specific EC2 instance?**
Use `lookup-events` with a filter (e.g., instance ID as a resource):
```bash
aws cloudtrail lookup-events --lookup-attributes AttributeKey=ResourceName,AttributeValue=<instance-id> --query 'Events[*].{Time:EventTime,Event:EventName,User:Username}' --output table
```
Replace `<instance-id>` (e.g., `i-1234567890abcdef0`). Adjust time range with `--start-time` if needed.

**28. Use AWS CLI to deregister an old AMI and delete its associated snapshots.**
First, deregister the AMI:
```bash
aws ec2 deregister-image --image-id <ami-id>
```
Then, find and delete associated snapshots:
```bash
aws ec2 describe-snapshots --filters Name=description,Values="Created by*for <ami-id>*" --query 'Snapshots[*].SnapshotId' --output text | xargs -n1 aws ec2 delete-snapshot --snapshot-id
```
Replace `<ami-id>` (e.g., `ami-12345678`).

**29. How would you list all SNS topics and their subscriptions?**
List topics:
```bash
aws sns list-topics
```
For each topic’s subscriptions:
```bash
for topic in $(aws sns list-topics --query 'Topics[*].TopicArn' --output text); do aws sns list-subscriptions-by-topic --topic-arn $topic --query 'Subscriptions[*].{Endpoint:Endpoint,Protocol:Protocol}' --output table; done
```
This loops through topics and lists their subscriptions (e.g., email, SQS).

**30. Write a command to update the timeout of a Lambda function to 10 seconds.**
Update the function configuration:
```bash
aws lambda update-function-configuration --function-name <function-name> --timeout 10
```
Timeout is in seconds; 10 is valid (max is 900 as of now).

## Terraform-Based Questions

**31. Terraform configuration to create an S3 bucket with public read access**
```hcl
resource "aws_s3_bucket" "public_bucket" {
  bucket = "my-public-bucket-123"
}

resource "aws_s3_bucket_public_access_block" "public_bucket" {
  bucket                  = aws_s3_bucket.public_bucket.id
  block_public_acls       = false
  block_public_policy    = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "public_read" {
  bucket = aws_s3_bucket.public_bucket.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect    = "Allow"
        Principal = "*"
        Action    = "s3:GetObject"
        Resource  = "${aws_s3_bucket.public_bucket.arn}/*"
      }
    ]
  })
}
```

**32. How to use Terraform to deploy an EC2 instance with a specific key pair**
You’d define an EC2 instance resource and reference an existing key pair by name. The key pair must already exist in AWS.
```hcl
resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0" # Example AMI (Amazon Linux 2)
  instance_type = "t2.micro"
  key_name      = "my-key-pair"           # Replace with your key pair name
  tags = {
    Name = "MyEC2Instance"
  }
}
```
**Note:** Use `aws ec2 create-key-pair` or the AWS console to create the key pair first if it doesn’t exist.

**33. Terraform script to set up a VPC with two private subnets**
```hcl
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "MainVPC"
  }
}

resource "aws_subnet" "private1" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.1.0/24"
  availability_zone = "us-east-1a"
  tags = {
    Name = "PrivateSubnet1"
  }
}

resource "aws_subnet" "private2" {
  vpc_id            = aws_vpc.main.id
  cidr_block        = "10.0.2.0/24"
  availability_zone = "us-east-1b"
  tags = {
    Name = "PrivateSubnet2"
  }
}
```
**Note:** No internet gateway or NAT gateway here, keeping it private. Add those if you need outbound access.

**34. Terraform configuration to provision an RDS instance with a MySQL engine**
```hcl
resource "aws_db_instance" "mysql" {
  allocated_storage    = 20
  engine              = "mysql"
  engine_version      = "8.0"
  instance_class      = "db.t3.micro"
  name                = "mydb"
  username            = "admin"
  password            = "SecurePass123!" # Use variables or secrets management in practice
  skip_final_snapshot = true
  tags = {
    Name = "MyRDS"
  }
}
```

**35. How to use Terraform to create an Auto Scaling group with a launch template**
```hcl
resource "aws_launch_template" "example" {
  name_prefix   = "example-"
  image_id      = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
}

resource "aws_autoscaling_group" "example" {
  desired_capacity = 2
  max_size         = 3
  min_size         = 1
  vpc_zone_identifier = [aws_subnet.private1.id, aws_subnet.private2.id] # From Q33
  launch_template {
    id      = aws_launch_template.example.id
    version = "$Latest"
  }
}
```
**Note:** Assumes subnets exist (e.g., from Q33). Adjust AMI and instance type as needed.

**36. Terraform module to deploy an ALB with a target group**
```hcl
module "alb" {
  source = "./modules/alb"

  vpc_id          = aws_vpc.main.id          # From Q33
  subnet_ids      = [aws_subnet.private1.id, aws_subnet.private2.id]
  alb_name        = "my-alb"
  target_group_name = "my-target-group"
}
```
**Module (in `modules/alb/main.tf`):**
```hcl
resource "aws_lb" "main" {
  name               = var.alb_name
  internal           = false
  load_balancer_type = "application"
  subnets            = var.subnet_ids
  tags = {
    Name = var.alb_name
  }
}

resource "aws_lb_target_group" "main" {
  name     = var.target_group_name
  port     = 80
  protocol = "HTTP"
  vpc_id   = var.vpc_id
}

variable "vpc_id" {}
variable "subnet_ids" { type = list(string) }
variable "alb_name" {}
variable "target_group_name" {}
```

**37. Terraform script to enable CloudWatch monitoring on an EC2 instance**
```hcl
resource "aws_instance" "monitored" {
  ami           = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
  monitoring    = true # Enables detailed CloudWatch monitoring
  tags = {
    Name = "MonitoredEC2"
  }
}

resource "aws_cloudwatch_metric_alarm" "cpu" {
  alarm_name          = "high-cpu-usage"
  comparison_operator = "GreaterThanThreshold"
  evaluation_periods  = 2
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = 300
  statistic           = "Average"
  threshold           = 80
  alarm_description   = "Triggers if CPU exceeds 80%"
  dimensions = {
    InstanceId = aws_instance.monitored.id
  }
}
```

**38. How to use Terraform to set up an S3 bucket with a lifecycle rule to archive to Glacier**
```hcl
resource "aws_s3_bucket" "lifecycle_bucket" {
  bucket = "my-lifecycle-bucket-123"
}

resource "aws_s3_bucket_lifecycle_configuration" "lifecycle" {
  bucket = aws_s3_bucket.lifecycle_bucket.id
  rule {
    id     = "archive-to-glacier"
    status = "Enabled"
    transition {
      days          = 30
      storage_class = "GLACIER"
    }
  }
}
```

**39. Terraform configuration to deploy an EKS cluster with a single node group**
```hcl
resource "aws_eks_cluster" "example" {
  name     = "my-eks-cluster"
  role_arn = aws_iam_role.eks_role.arn
  vpc_config {
    subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id] # From Q33
  }
}

resource "aws_eks_node_group" "example" {
  cluster_name    = aws_eks_cluster.example.name
  node_group_name = "my-node-group"
  node_role_arn   = aws_iam_role.node_role.arn
  subnet_ids      = [aws_subnet.private1.id, aws_subnet.private2.id]
  scaling_config {
    desired_size = 1
    max_size     = 2
    min_size     = 1
  }
}

# IAM roles (simplified)
resource "aws_iam_role" "eks_role" {
  name = "eks-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "eks.amazonaws.com" }
    }]
  })
}

resource "aws_iam_role" "node_role" {
  name = "eks-node-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "ec2.amazonaws.com" }
    }]
  })
}
```
**Note:** Attach necessary policies (e.g., `AmazonEKSClusterPolicy`) to roles in practice.

**40. Terraform script to create an IAM role with S3 full access permissions**
```hcl
resource "aws_iam_role" "s3_full_access" {
  name = "s3-full-access-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "ec2.amazonaws.com" } # Example principal
    }]
  })
}

resource "aws_iam_role_policy_attachment" "s3_full_access" {
  role       = aws_iam_role.s3_full_access.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonS3FullAccess"
}
```

**41. How do you use Terraform to configure a security group with inbound rules for SSH and HTTP?**
You define an `aws_security_group` resource in Terraform, specifying inbound rules (`ingress`) for SSH (port 22) and HTTP (port 80). Here’s an example:

```hcl
resource "aws_security_group" "example" {
  name        = "allow_ssh_http"
  description = "Allow SSH and HTTP traffic"
  vpc_id      = aws_vpc.main.id

  ingress {
    description = "SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
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
```

* `ingress` blocks define allowed inbound traffic.
* `egress` allows all outbound traffic (common practice).
* Adjust `cidr_blocks` to restrict sources (e.g., your IP) for security.

**42. Create a Terraform configuration to set up a CloudFront distribution for an S3 bucket.**
Here’s a Terraform config for a CloudFront distribution serving an S3 bucket:

```hcl
resource "aws_s3_bucket" "bucket" {
  bucket = "my-static-website-bucket"
}

resource "aws_cloudfront_distribution" "s3_distribution" {
  origin {
    domain_name = aws_s3_bucket.bucket.bucket_regional_domain_name
    origin_id   = "S3Origin"
  }

  enabled             = true
  is_ipv6_enabled     = true
  default_root_object = "index.html"

  default_cache_behavior {
    allowed_methods  = ["GET", "HEAD"]
    cached_methods   = ["GET", "HEAD"]
    target_origin_id = "S3Origin"
    forwarded_values {
      query_string = false
      cookies {
        forward = "none"
      }
    }
    viewer_protocol_policy = "redirect-to-https"
  }

  restrictions {
    geo_restriction {
      restriction_type = "none"
    }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }
}
```

* Links CloudFront to the S3 bucket via `origin`.
* Configures basic caching and HTTPS redirection.
* Assumes a public S3 bucket (add bucket policy separately if needed).

**43. Write a Terraform script to deploy a Lambda function triggered by an S3 event.**
Here’s a script for a Lambda function triggered by S3 uploads:

```hcl
resource "aws_s3_bucket" "bucket" {
  bucket = "my-lambda-trigger-bucket"
}

resource "aws_iam_role" "lambda_role" {
  name = "lambda_execution_role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "lambda.amazonaws.com" }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_policy" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_lambda_function" "example" {
  filename      = "lambda_function.zip" # Your zipped Lambda code
  function_name = "s3_trigger_lambda"
  role          = aws_iam_role.lambda_role.arn
  handler       = "index.handler"
  runtime       = "nodejs18.x"
}

resource "aws_s3_bucket_notification" "bucket_notification" {
  bucket = aws_s3_bucket.bucket.id

  lambda_function {
    lambda_function_arn = aws_lambda_function.example.arn
    events              = ["s3:ObjectCreated:*"]
  }
}

resource "aws_lambda_permission" "allow_s3" {
  statement_id  = "AllowS3Invoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.example.function_name
  principal     = "s3.amazonaws.com"
  source_arn    = aws_s3_bucket.bucket.arn
}
```

* Creates an S3 bucket, Lambda function, and IAM role.
* Ties them together with a bucket notification and permission.

**44. How would you use Terraform to create a DynamoDB table with auto-scaling enabled?**
You use `aws_dynamodb_table` for the table and `aws_appautoscaling_target`/`policy` for auto-scaling:

```hcl
resource "aws_dynamodb_table" "example" {
  name           = "MyTable"
  billing_mode   = "PROVISIONED"
  read_capacity  = 5
  write_capacity = 5
  hash_key       = "id"

  attribute {
    name = "id"
    type = "S"
  }
}

resource "aws_appautoscaling_target" "read_target" {
  max_capacity       = 20
  min_capacity       = 5
  resource_id        = "table/${aws_dynamodb_table.example.name}"
  scalable_dimension = "dynamodb:table:ReadCapacityUnits"
  service_namespace  = "dynamodb"
}

resource "aws_appautoscaling_policy" "read_policy" {
  name               = "scale-read"
  policy_type        = "TargetTrackingScaling"
  resource_id        = aws_appautoscaling_target.read_target.resource_id
  scalable_dimension = aws_appautoscaling_target.read_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.read_target.service_namespace

  target_tracking_scaling_policy_configuration {
    predefined_metric_specification {
      predefined_metric_type = "DynamoDBReadCapacityUtilization"
    }
    target_value = 70.0
  }
}

# Repeat for write capacity (similar config, change to WriteCapacityUnits)
```

* Sets initial capacity and scales based on utilization (e.g., 70%).

**45. Create a Terraform configuration to set up a NAT Gateway in a VPC.**
Here’s a config for a NAT Gateway in a VPC:

```hcl
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "public" {
  vpc_id     = aws_vpc.main.id
  cidr_block = "10.0.1.0/24"
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.main.id
}

resource "aws_eip" "nat_eip" {
  vpc = true
}

resource "aws_nat_gateway" "nat" {
  allocation_id = aws_eip.nat_eip.id
  subnet_id     = aws_subnet.public.id
  depends_on    = [aws_internet_gateway.igw]
}
```

* Places the NAT Gateway in a public subnet with an Elastic IP.
* Requires an Internet Gateway for outbound traffic.

**46. Write a Terraform script to deploy an ECS cluster with a Fargate task definition.**
Here’s a script for an ECS cluster with Fargate:

```hcl
resource "aws_ecs_cluster" "example" {
  name = "my-ecs-cluster"
}

resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecsTaskExecutionRole"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "ecs-tasks.amazonaws.com" }
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_policy" {
  role       = aws_iam_role.ecs_task_execution_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

resource "aws_ecs_task_definition" "example" {
  family                   = "my-fargate-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([{
    name  = "my-container"
    image = "amazon/amazon-ecs-sample"
    portMappings = [{ containerPort = 80, hostPort = 80 }]
  }])
}

resource "aws_ecs_service" "example" {
  name            = "my-service"
  cluster         = aws_ecs_cluster.example.id
  task_definition = aws_ecs_task_definition.example.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets         = [aws_subnet.public.id]
    security_groups = [aws_security_group.example.id]
  }
}
```

* Defines a Fargate-compatible task and service in an ECS cluster.
* Assumes a VPC/subnet/security group exists.

**47. How do you use Terraform to import an existing S3 bucket into your state file?**
You use the `terraform import` command. Steps:

1.  Define the resource in your config:
    ```hcl
    resource "aws_s3_bucket" "existing" {
      bucket = "my-existing-bucket"
    }
    ```
2.  Run:
    ```bash
    terraform import aws_s3_bucket.existing my-existing-bucket
    ```
    The bucket name is the identifier.
3.  After import, Terraform manages it in your state file.

**48. Create a Terraform configuration to provision an SNS topic with an email subscription.**
Here’s the config:

```hcl
resource "aws_sns_topic" "example" {
  name = "my-sns-topic"
}

resource "aws_sns_topic_subscription" "email" {
  topic_arn = aws_sns_topic.example.arn
  protocol  = "email"
  endpoint  = "user@example.com"
}
```

* Creates an SNS topic and subscribes an email (requires manual confirmation).

**49. Write a Terraform script to deploy an EC2 instance behind a load balancer.**
Here’s a script:

```hcl
resource "aws_vpc" "main" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "public" {
  vpc_id     = aws_vpc.main.id
  cidr_block = "10.0.1.0/24"
}

resource "aws_security_group" "alb_sg" {
  vpc_id = aws_vpc.main.id
  ingress {
    from_port   = 80
    to_port     = 80
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

resource "aws_lb" "example" {
  name               = "my-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb_sg.id]
  subnets            = [aws_subnet.public.id]
}

resource "aws_lb_target_group" "example" {
  name     = "my-tg"
  port     = 80
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id
}

resource "aws_lb_listener" "example" {
  load_balancer_arn = aws_lb.example.arn
  port              = 80
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.example.arn
  }
}

resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0" # Update to valid AMI
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.public.id
  security_groups = [aws_security_group.alb_sg.name]
}

resource "aws_lb_target_group_attachment" "example" {
  target_group_arn = aws_lb_target_group.example.arn
  target_id        = aws_instance.example.id
  port             = 80
}
```

* Sets up an ALB forwarding to an EC2 instance.

**50. How would you use Terraform to configure a Route 53 record pointing to an ALB?**
You use `aws_route53_record` linked to the ALB’s DNS:

```hcl
resource "aws_route53_zone" "example" {
  name = "example.com"
}

resource "aws_route53_record" "www" {
  zone_id = aws_route53_zone.example.zone_id
  name    = "[www.example.com](https://www.example.com)"
  type    = "A"

  alias {
    name                   = aws_lb.example.dns_name
    zone_id                = aws_lb.example.zone_id
    evaluate_target_health = true
  }
}
```

* Creates an A record alias to the ALB’s DNS name.

**51. Create a Terraform configuration to set up a VPC peering connection between two VPCs**

Here’s a Terraform config to peer two VPCs (e.g., VPC A and VPC B):

```hcl
provider "aws" {
  region = "us-east-1"
}

# VPC A (Requester)
resource "aws_vpc" "vpc_a" {
  cidr_block = "10.0.0.0/16"
  tags = { Name = "VPC-A" }
}

# VPC B (Accepter)
resource "aws_vpc" "vpc_b" {
  cidr_block = "10.1.0.0/16"
  tags = { Name = "VPC-B" }
}

# VPC Peering Connection
resource "aws_vpc_peering_connection" "peer" {
  vpc_id        = aws_vpc.vpc_a.id        # Requester VPC
  peer_vpc_id   = aws_vpc.vpc_b.id        # Accepter VPC
  auto_accept   = true                    # Auto-accept if in same account
  tags = { Name = "VPC-A-to-VPC-B" }
}

# Update route tables (example for VPC A)
resource "aws_route" "vpc_a_route" {
  route_table_id         = aws_vpc.vpc_a.main_route_table_id
  destination_cidr_block = aws_vpc.vpc_b.cidr_block
  vpc_peering_connection_id = aws_vpc_peering_connection.peer.id
}

# Update route tables (example for VPC B)
resource "aws_route" "vpc_b_route" {
  route_table_id         = aws_vpc.vpc_b.main_route_table_id
  destination_cidr_block = aws_vpc.vpc_a.cidr_block
  vpc_peering_connection_id = aws_vpc_peering_connection.peer.id
}
```

**Notes:** This assumes both VPCs are in the same region and account (`auto_accept = true`). For cross-account or cross-region peering, you’d need to manage the accepter side separately and set `auto_accept = false`.

**52. Write a Terraform script to deploy a CloudWatch alarm for EC2 CPU utilization**

Here’s a script to monitor CPU utilization on an EC2 instance:

```hcl
provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "example" {
  ami           = "ami-0c55b159cbfafe1f0" # Example AMI (Amazon Linux 2)
  instance_type = "t2.micro"
  tags = { Name = "Example-EC2" }
}

resource "aws_cloudwatch_metric_alarm" "cpu_alarm" {
  alarm_name          = "ec2-cpu-utilization"
  comparison_operator = "GreaterThanThreshold"
  evaluation_periods  = "2"               # Trigger after 2 periods
  metric_name         = "CPUUtilization"
  namespace           = "AWS/EC2"
  period              = "300"             # 5 minutes
  statistic           = "Average"
  threshold           = "80"              # Trigger if CPU > 80%
  alarm_description   = "Triggers when EC2 CPU exceeds 80%"
  alarm_actions       = []                # Add SNS topic ARN here if needed
  dimensions = {
    InstanceId = aws_instance.example.id
  }
}
```

**Notes:** Attach an SNS topic to `alarm_actions` if you want notifications. Adjust `period` and `threshold` based on your needs.

**53. How do you use Terraform to manage an existing RDS instance without recreating it?**

To manage an existing RDS instance, use Terraform’s **import** command and define the resource with matching attributes. Here’s the process:

1.  **Define the resource in your config (example):**

    ```hcl
    provider "aws" {
      region = "us-east-1"
    }

    resource "aws_db_instance" "existing_rds" {
      identifier          = "my-existing-db"  # Must match existing DB identifier
      engine              = "mysql"
      instance_class      = "db.t3.micro"
      allocated_storage   = 20
      username            = "admin"
      password            = "existingpassword" # Use existing credentials
      skip_final_snapshot = true
    }
    ```

2.  **Import the existing RDS instance:**

    ```bash
    terraform import aws_db_instance.existing_rds my-existing-db
    ```

3.  **Run `terraform plan` to ensure no changes are proposed** (adjust config if diffs appear).

**Notes:** Use `terraform state show aws_db_instance.existing_rds` after import to verify attributes. Avoid changing immutable fields (e.g., `engine`) unless you want Terraform to replace the instance. Sensitive fields like `password` might need a lifecycle ignore rule if managed outside Terraform:

```hcl
lifecycle {
  ignore_changes = [password]
}
```

**54. Create a Terraform configuration to deploy an Elastic Beanstalk application**

Here’s a config to deploy a simple Elastic Beanstalk app:

```hcl
provider "aws" {
  region = "us-east-1"
}

# Elastic Beanstalk Application
resource "aws_elastic_beanstalk_application" "app" {
  name        = "my-eb-app"
  description = "Example Elastic Beanstalk App"
}

# Elastic Beanstalk Environment
resource "aws_elastic_beanstalk_environment" "env" {
  name                = "my-eb-env"
  application         = aws_elastic_beanstalk_application.app.name
  solution_stack_name = "64bit Amazon Linux 2 v3.3.13 running Python 3.8"
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "InstanceType"
    value     = "t2.micro"
  }
}

# Optional: Upload app version (e.g., a ZIP file in S3)
resource "aws_s3_bucket" "app_bucket" {
  bucket = "my-eb-app-bucket"
}

resource "aws_s3_object" "app_version" {
  bucket = aws_s3_bucket.app_bucket.bucket
  key    = "app.zip"
  source = "path/to/your/app.zip"  # Local path to your app ZIP
}

resource "aws_elastic_beanstalk_application_version" "version" {
  name        = "v1"
  application = aws_elastic_beanstalk_application.app.name
  bucket      = aws_s3_bucket.app_bucket.bucket
  key         = aws_s3_object.app_version.key
}
```

**Notes:** Replace `path/to/your/app.zip` with your actual app bundle. Update `solution_stack_name` based on your app’s runtime (check AWS for valid stacks).

**55. Write a Terraform script to set up an S3 bucket with server-side encryption enabled**

Here’s a config for an S3 bucket with SSE:

```hcl
provider "aws" {
  region = "us-east-1"
}

resource "aws_s3_bucket" "bucket" {
  bucket = "my-encrypted-bucket"
  tags   = { Name = "Encrypted-Bucket" }
}

resource "aws_s3_bucket_server_side_encryption_configuration" "encryption" {
  bucket = aws_s3_bucket.bucket.bucket
  rule {
    apply_server_side_encryption_by_default {
      sse_algorithm = "AES256"  # Or "aws:kms" for KMS
    }
  }
}

resource "aws_s3_bucket_versioning" "versioning" {
  bucket = aws_s3_bucket.bucket.bucket
  versioning_configuration {
    status = "Enabled"  # Optional, but recommended with encryption
  }
}

resource "aws_s3_bucket_public_access_block" "block" {
  bucket                  = aws_s3_bucket.bucket.bucket
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}
```

**Notes:** Uses `AES256` by default. For KMS, set `sse_algorithm = "aws:kms"` and add a `kms_key_id`. The `public_access_block` ensures security.

---

**56. An EC2 instance is unresponsive. Use AWS CLI to check its status and reboot it.**  
To check the status:  
```bash
aws ec2 describe-instances --instance-ids <instance-id> --query "Reservations[*].Instances[*].State.Name" --output text
```
To reboot the instance:  
```bash
aws ec2 reboot-instances --instance-ids <instance-id>
```

---

**57. How would you use AWS CLI to identify and terminate EC2 instances older than 30 days?**  
List instances with their launch time, filter those older than 30 days, and terminate:  
```bash
aws ec2 describe-instances --query "Reservations[*].Instances[?LaunchTime<'$(date -u -d '30 days ago' +%Y-%m-%dT%H:%M:%SZ')'].InstanceId" --output text | xargs -r aws ec2 terminate-instances --instance-ids
```
Note: Use with caution—test the query first to avoid unintended terminations.

---

**58. An S3 bucket is nearing its storage limit. Write a command to move old files to Glacier.**  
Move objects older than 30 days to Glacier:  
```bash
aws s3api list-objects-v2 --bucket <bucket-name> --query "Contents[?LastModified<'$(date -u -d '30 days ago' +%Y-%m-%d')'].Key" --output text | xargs -I {} aws s3api put-object --bucket <bucket-name> --key {} --storage-class GLACIER
```
Note: Ensure lifecycle rules are considered for automation.

---

**59. Use AWS CLI to troubleshoot why an Auto Scaling group isn’t launching new instances.**  
Check the Auto Scaling group’s status and activities:  
```bash
aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names <asg-name>
aws autoscaling describe-scaling-activities --auto-scaling-group-name <asg-name> --max-items 10
```
Look at `DesiredCapacity`, `MinSize`, `MaxSize`, and activity details for errors (e.g., launch template issues or quota limits).

---

**60. How do you use AWS CLI to fetch the last 10 minutes of logs for an ECS task?**  
Get logs from CloudWatch Logs for the task:  
```bash
aws logs get-log-events --log-group-name <log-group-name> --log-stream-name <log-stream-name> --start-time $(date -d '10 minutes ago' +%s000) --end-time $(date +%s000) --output text
```
Find the log group and stream via the ECS task definition or console.

---

**61. An RDS instance is down. Write a command to check its status and initiate a failover.**  
Check status:  
```bash
aws rds describe-db-instances --db-instance-identifier <db-instance-id> --query "DBInstances[*].DBInstanceStatus" --output text
```
Initiate failover (for Multi-AZ setups):  
```bash
aws rds failover-db-cluster --db-cluster-identifier <db-cluster-id>
```

---

**62. Use AWS CLI to identify and delete unused Elastic IPs in a region.**  
List unused Elastic IPs (not associated with instances):  
```bash
aws ec2 describe-addresses --query "Addresses[?AssociationId==null].AllocationId" --output text | xargs -r aws ec2 release-address --allocation-id
```

---

**63. How would you use AWS CLI to check why a Lambda function failed its last execution?**  
Fetch the latest execution logs from CloudWatch:  
```bash
aws logs filter-log-events --log-group-name /aws/lambda/<function-name> --filter-pattern "ERROR" --limit 10
```
Check the `message` field for failure details.

---

**64. An ELB is reporting unhealthy instances. Write a command to investigate and deregister them.**  
List unhealthy instances:  
```bash
aws elbv2 describe-target-health --target-group-arn <target-group-arn> --query "TargetHealthDescriptions[?TargetHealth.State=='unhealthy'].Target.Id" --output text
```
Deregister them:  
```bash
aws elbv2 deregister-targets --target-group-arn <target-group-arn> --targets Id=<instance-id>
```

---

**65. Use AWS CLI to update the security group of an EC2 instance to allow HTTPS traffic.**  
Add HTTPS (port 443) to the security group:  
```bash
aws ec2 authorize-security-group-ingress --group-id <security-group-id> --protocol tcp --port 443 --cidr 0.0.0.0/0
```
Verify the instance uses this security group via `describe-instances`.

---

**66. How do you use AWS CLI to check the status of an S3 bucket replication rule?**  
Check replication configuration and status:  
```bash
aws s3api get-bucket-replication --bucket <source-bucket-name>
```
Look at `Status` under `Rule` to confirm it’s enabled and working.

---

**67. An EKS cluster isn’t responding. Write a command to check its health and node status.**  
Check cluster status:  
```bash
aws eks describe-cluster --name <cluster-name> --query "cluster.status"
```
Check node status:  
```bash
aws eks list-nodegroups --cluster-name <cluster-name> && aws eks describe-nodegroup --cluster-name <cluster-name> --nodegroup-name <nodegroup-name>
```

---

**68. Use AWS CLI to restore an S3 object from a specific version.**  
Copy a specific version to the original key:  
```bash
aws s3api copy-object --bucket <bucket-name> --key <object-key> --copy-source <bucket-name>/<object-key>?versionId=<version-id>
```

---

**69. How would you use AWS CLI to troubleshoot a CloudWatch alarm that isn’t triggering?**  
Check alarm status and history:  
```bash
aws cloudwatch describe-alarms --alarm-names <alarm-name>
aws cloudwatch describe-alarm-history --alarm-name <alarm-name> --history-item-type StateUpdate
```
Verify the metric (e.g., via `get-metric-statistics`) and threshold settings.

---

**70. Write a command to resize an RDS instance to handle increased load.**  
Modify the instance class:  
```bash
aws rds modify-db-instance --db-instance-identifier <db-instance-id> --db-instance-class <new-instance-class> --apply-immediately
```
Example: Change to `db.m5.large`.

---

---

**71. An EC2 instance deployed with Terraform isn’t accessible. Debug the configuration live.**  
To debug:  
1. Check the Terraform configuration (`main.tf`):  
   - Ensure the `subnet_id` is in a public subnet with an internet gateway.
   - Verify the `security_groups` allow SSH (port 22) from your IP.
   - Confirm `associate_public_ip_address = true`.
   Example:  
   ```hcl
   resource "aws_instance" "example" {
     ami           = "ami-12345678"
     instance_type = "t2.micro"
     subnet_id     = aws_subnet.public.id
     security_groups = [aws_security_group.ssh.id]
     associate_public_ip_address = true
   }
   ```
2. Run `terraform plan` to validate changes.
3. Use `terraform apply` and check the output for the public IP.
4. If still inaccessible, run `aws ec2 describe-instances --instance-ids <instance-id>` to verify status and network settings.

---

**72. How would you use Terraform to update an existing S3 bucket to enable versioning?**  
Update the bucket resource:  
```hcl
resource "aws_s3_bucket" "existing_bucket" {
  bucket = "<bucket-name>"
}

resource "aws_s3_bucket_versioning" "versioning" {
  bucket = aws_s3_bucket.existing_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}
```
Run `terraform apply` to enable versioning.

---

**73. A Terraform apply fails with a ‘resource already exists’ error. Resolve it live.**  
This happens when a resource exists in AWS but not in Terraform state.  
1. Import the resource into the state:  
   ```bash
   terraform import aws_s3_bucket.my_bucket <bucket-name>
   ```
2. Add the resource to your configuration:  
   ```hcl
   resource "aws_s3_bucket" "my_bucket" {
     bucket = "<bucket-name>"
   }
   ```
3. Run `terraform plan` to confirm alignment, then `terraform apply`.

---

**74. Write a Terraform script to add a new subnet to an existing VPC.**  
```hcl
resource "aws_vpc" "existing_vpc" {
  cidr_block = "10.0.0.0/16"
  # Assume this exists
}

resource "aws_subnet" "new_subnet" {
  vpc_id            = aws_vpc.existing_vpc.id
  cidr_block        = "10.0.3.0/24"
  availability_zone = "us-east-1c"
  tags = {
    Name = "new-subnet"
  }
}
```
Run `terraform apply` to create the subnet.

---

**75. How do you use Terraform to update an RDS instance’s storage capacity?**  
Modify the `aws_db_instance` resource:  
```hcl
resource "aws_db_instance" "example" {
  identifier          = "my-rds-instance"
  allocated_storage   = 50  # Increase from, e.g., 20 GB
  instance_class      = "db.t3.medium"
  engine              = "mysql"
  username            = "admin"
  password            = "password"
  apply_immediately   = true
}
```
Run `terraform apply` to update storage.

---

**76. A Terraform configuration for an ALB isn’t routing traffic. Troubleshoot and fix it.**  
1. Check the ALB configuration:  
   ```hcl
   resource "aws_lb" "example" {
     name               = "my-alb"
     internal           = false
     load_balancer_type = "application"
     security_groups    = [aws_security_group.alb.id]
     subnets            = [aws_subnet.public1.id, aws_subnet.public2.id]
   }

   resource "aws_lb_target_group" "tg" {
     name     = "my-tg"
     port     = 80
     protocol = "HTTP"
     vpc_id   = aws_vpc.main.id
   }

   resource "aws_lb_listener" "listener" {
     load_balancer_arn = aws_lb.example.arn
     port              = 80
     protocol          = "HTTP"
     default_action {
       type             = "forward"
       target_group_arn = aws_lb_target_group.tg.arn
     }
   }
   ```
2. Troubleshoot:  
   - Ensure subnets are public (route table has an internet gateway).
   - Verify security group allows port 80 inbound.
   - Check target group health (instances registered and healthy).
3. Fix by adjusting subnets or security groups, then run `terraform apply`.

---

**77. Write a Terraform script to add a new IAM policy to an existing role.**  
```hcl
resource "aws_iam_role" "existing_role" {
  name = "my-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = { Service = "ec2.amazonaws.com" }
    }]
  })
}

resource "aws_iam_policy" "new_policy" {
  name   = "new-policy"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action   = "s3:GetObject"
      Effect   = "Allow"
      Resource = "arn:aws:s3:::my-bucket/*"
    }]
  })
}

resource "aws_iam_role_policy_attachment" "attach" {
  role       = aws_iam_role.existing_role.name
  policy_arn = aws_iam_policy.new_policy.arn
}
```
Run `terraform apply` to attach the policy.

---

**78. How would you use Terraform to scale an EKS node group to 3 nodes?**  
Update the `aws_eks_node_group` resource:  
```hcl
resource "aws_eks_node_group" "example" {
  cluster_name    = aws_eks_cluster.example.name
  node_group_name = "my-node-group"
  node_role_arn   = aws_iam_role.node.arn
  subnet_ids      = aws_subnet.example[*].id
  scaling_config {
    desired_size = 3  # Scale to 3 nodes
    max_size     = 5
    min_size     = 1
  }
}
```
Run `terraform apply` to scale the node group.

---

**79. A Terraform deployment for a Lambda function fails. Debug the configuration live.**  
1. Check the configuration:  
   ```hcl
   resource "aws_lambda_function" "example" {
     function_name = "my-lambda"
     handler       = "index.handler"
     runtime       = "nodejs16.x"
     role          = aws_iam_role.lambda.arn
     filename      = "lambda.zip"
   }
   ```
2. Common issues:  
   - `filename` doesn’t exist or isn’t uploaded.
   - IAM role lacks `AWSLambdaBasicExecutionRole`.
   - Syntax error in the handler or runtime.
3. Fix by ensuring the ZIP file exists and role permissions are correct, then run `terraform apply`.

---

**80. Write a Terraform script to update a security group with a new inbound rule.**  
```hcl
resource "aws_security_group" "example" {
  name        = "my-sg"
  vpc_id      = aws_vpc.main.id
}

resource "aws_security_group_rule" "new_rule" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.example.id
}
```
Run `terraform apply` to add the HTTPS rule.

---

---

**81. An EC2 instance isn’t launching due to a Terraform misconfiguration. Fix it and verify with AWS CLI.**  
**Debug and Fix with Terraform:**  
1. Check the Terraform configuration:  
   ```hcl
   resource "aws_instance" "example" {
     ami           = "ami-12345678"  # Invalid AMI could cause failure
     instance_type = "t2.micro"
     subnet_id     = aws_subnet.public.id
     security_groups = [aws_security_group.allow_ssh.id]
   }
   ```
   Common issues:  
   - Invalid `ami` for the region.
   - `subnet_id` not in a valid VPC or missing route to an internet gateway.
   - `security_groups` misconfigured or missing.
2. Fix: Update the AMI to a valid one (e.g., `ami-0c55b159cbfafe1f0` for Amazon Linux 2 in us-east-1), ensure subnet and security group exist, then run:  
   ```bash
   terraform apply
   ```
**Verify with AWS CLI:**  
```bash
aws ec2 describe-instances --instance-ids <instance-id> --query "Reservations[*].Instances[*].[InstanceId, State.Name]" --output text
```
Check if the state is `running`.

---

**82. Use AWS CLI to check the status of an S3 bucket created by Terraform that’s missing.**  
**Steps:**  
1. Check if the bucket exists:  
   ```bash
   aws s3 ls s3://<bucket-name>
   ```
   If it fails with "NoSuchBucket," the bucket wasn’t created.
2. Verify Terraform state:  
   ```bash
   terraform state list | grep aws_s3_bucket
   ```
   If the bucket resource (e.g., `aws_s3_bucket.my_bucket`) isn’t listed, Terraform didn’t create it.
3. Check Terraform logs or run `terraform apply` again to see errors (e.g., permission issues or naming conflicts).
4. If it exists but isn’t in state, import it:  
   ```bash
   terraform import aws_s3_bucket.my_bucket <bucket-name>
   ```

---

**83. How would you use Terraform to update an RDS instance and confirm the change with AWS CLI?**  
**Terraform Update:**  
Update storage or instance class:  
```hcl
resource "aws_db_instance" "example" {
  identifier          = "my-rds-instance"
  allocated_storage   = 30  # Increase from 20 GB
  instance_class      = "db.t3.medium"
  engine              = "postgres"
  username            = "admin"
  password            = "password"
  apply_immediately   = true
}
```
Run:  
```bash
terraform apply
```
**Confirm with AWS CLI:**  
```bash
aws rds describe-db-instances --db-instance-identifier my-rds-instance --query "DBInstances[*].[DBInstanceStatus, AllocatedStorage, DBInstanceClass]" --output text
```
Verify `available` status, `30` GB storage, and `db.t3.medium` class.

---

**84. A Terraform-deployed Auto Scaling group isn’t scaling. Debug with AWS CLI commands.**  
**Terraform Config Check:**  
```hcl
resource "aws_autoscaling_group" "example" {
  name                = "my-asg"
  min_size            = 1
  max_size            = 3
  desired_capacity    = 2
  vpc_zone_identifier = [aws_subnet.subnet1.id, aws_subnet.subnet2.id]
  launch_template {
    id      = aws_launch_template.example.id
    version = "$Latest"
  }
}
```
**Debug with AWS CLI:**  
1. Check ASG status:  
   ```bash
   aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names my-asg --query "AutoScalingGroups[*].[MinSize, MaxSize, DesiredCapacity, Instances[*].InstanceId]"
   ```
   Ensure `DesiredCapacity` matches running instances.
2. View scaling activities:  
   ```bash
   aws autoscaling describe-scaling-activities --auto-scaling-group-name my-asg --max-items 10
   ```
   Look for errors (e.g., "Launch template not found" or "Quota exceeded").
3. Check instance health:  
   ```bash
   aws ec2 describe-instances --instance-ids <instance-ids-from-asg> --query "Reservations[*].Instances[*].State.Name"
   ```
4. Fix: Adjust `max_size`, quotas, or launch template in Terraform, then `terraform apply`.

---

**85. Write a Terraform script to create an SNS topic and use AWS CLI to subscribe an email.**  
**Terraform Script:**  
```hcl
resource "aws_sns_topic" "example" {
  name = "my-sns-topic"
}
```
Run:  
```bash
terraform apply
```
Output the ARN:  
```hcl
output "sns_topic_arn" {
  value = aws_sns_topic.example.arn
}
```
**AWS CLI Subscription:**  
Subscribe an email:  
```bash
aws sns subscribe --topic-arn <sns-topic-arn-from-output> --protocol email --notification-endpoint user@example.com
```
The user will receive a confirmation email to accept the subscription.

---