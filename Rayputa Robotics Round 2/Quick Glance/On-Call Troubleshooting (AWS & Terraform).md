### Condensed AWS CLI and Terraform Questions and Answers

#### AWS CLI-Based Questions

1. **Find EC2 instances in a specific VPC?**  
   ```bash
   aws ec2 describe-instances --filters "Name=vpc-id,Values=vpc-12345678" --query "Reservations[].Instances[].InstanceId" --output text
   ```

2. **Stop all running EC2 instances in a region?**  
   ```bash
   aws ec2 stop-instances --instance-ids $(aws ec2 describe-instances --region us-east-1 --filters "Name=instance-state-name,Values=running" --query "Reservations[].Instances[].InstanceId" --output text)
   ```

3. **Check S3 bucket size?**  
   ```bash
   aws s3api list-objects-v2 --bucket my-bucket --query "[sum(Contents[].Size)]" --output text
   ```

4. **Create security group, allow port 80?**  
   ```bash
   aws ec2 create-security-group --group-name MySG --description "Allow HTTP" --vpc-id vpc-12345678
   aws ec2 authorize-security-group-ingress --group-name MySG --protocol tcp --port 80 --cidr 0.0.0.0/0
   ```

5. **List IAM users created last 30 days?**  
   ```bash
   aws iam list-users --query "Users[?CreateDate>=`2025-03-08`].UserName" --output text
   ```

6. **Download latest CloudWatch logs for Lambda?**  
   ```bash
   aws logs get-log-events --log-group-name /aws/lambda/my-function --log-stream-name $(aws logs describe-log-streams --log-group-name /aws/lambda/my-function --order-by LastEventTime --descending --limit 1 --query "logStreams[0].logStreamName" --output text) --output text > lambda_logs.txt
   ```

7. **Scale Auto Scaling group to 5 instances?**  
   ```bash
   aws autoscaling set-desired-capacity --auto-scaling-group-name my-asg --desired-capacity 5
   ```

8. **Check RDS status, reboot if not available?**  
   ```bash
   STATUS=$(aws rds describe-db-instances --db-instance-identifier my-rds --query "DBInstances[0].DBInstanceStatus" --output text); [ "$STATUS" != "available" ] && aws rds reboot-db-instance --db-instance-identifier my-rds
   ```

9. **Delete all objects in S3 bucket?**  
   ```bash
   aws s3 rm s3://my-bucket --recursive
   ```

10. **Attach EBS volume to EC2 instance?**  
    ```bash
    aws ec2 attach-volume --volume-id vol-12345678 --instance-id i-12345678 --device /dev/xvdf
    ```

11. **List CloudWatch alarms in ALARM state?**  
    ```bash
    aws cloudwatch describe-alarms --state-value ALARM
    ```

12. **Create snapshot of EBS volume?**  
    ```bash
    aws ec2 create-snapshot --volume-id <volume-id> --description "Snapshot"
    ```

13. **Tag untagged EC2 instances?**  
    ```bash
    aws ec2 describe-instances --query 'Reservations[*].Instances[?not_null(Tags)==`false`].[InstanceId]' --output text | xargs -I {} aws ec2 create-tags --resources {} --tags Key=Environment,Value=Dev
    ```

14. **Check ELB health, remove unhealthy instance?**  
    ```bash
    aws elb describe-instance-health --load-balancer-name <elb-name>
    aws elb deregister-instances-from-load-balancer --load-balancer-name <elb-name> --instances <instance-id>
    ```

15. **Enable termination protection on EC2?**  
    ```bash
    aws ec2 modify-instance-attribute --instance-id <instance-id> --disable-api-termination
    ```

16. **List S3 buckets with versioning disabled?**  
    ```bash
    aws s3api list-buckets --query 'Buckets[].Name' --output text | xargs -I {} sh -c 'aws s3api get-bucket-versioning --bucket {} | grep -q "Enabled" || echo {}'
    ```

17. **Rotate IAM access key for a user?**  
    ```bash
    aws iam create-access-key --user-name <username>
    aws iam delete-access-key --user-name <username> --access-key-id <old-key-id>
    ```

18. **Copy file between S3 buckets, different regions?**  
    ```bash
    aws s3 cp s3://source-bucket/file.txt s3://destination-bucket/file.txt --source-region us-east-1 --region us-west-2
    ```

19. **Check IAM role on EC2 instance?**  
    ```bash
    aws ec2 describe-instances --instance-ids <instance-id> --query 'Reservations[0].Instances[0].IamInstanceProfile'
    ```

20. **Increase EBS volume size?**  
    ```bash
    aws ec2 modify-volume --volume-id <volume-id> --size 100
    ```

21. **List running ECS tasks in cluster?**  
    ```bash
    aws ecs list-tasks --cluster <cluster-name> --desired-status RUNNING
    ```

22. **Invoke Lambda function, capture output?**  
    ```bash
    aws lambda invoke --function-name <function-name> output.json
    ```

23. **Check EKS cluster status?**  
    ```bash
    aws eks describe-cluster --name <cluster-name> --query 'cluster.status' --output text
    ```

24. **List DynamoDB tables with throughput?**  
    ```bash
    for table in $(aws dynamodb list-tables --query 'TableNames' --output text); do aws dynamodb describe-table --table-name $table --query 'Table.{Name:TableName,ReadCapacity:ProvisionedThroughput.ReadCapacityUnits,WriteCapacity:ProvisionedThroughput.WriteCapacityUnits}' --output table; done
    ```

25. **Enable encryption on S3 bucket?**  
    ```bash
    aws s3api put-bucket-encryption --bucket <bucket-name> --server-side-encryption-configuration '{"Rules":[{"ApplyServerSideEncryptionByDefault":{"SSEAlgorithm":"AES256"}}]}'
    ```

26. **Restore RDS from snapshot?**  
    ```bash
    aws rds restore-db-instance-from-db-snapshot --db-instance-identifier <new-db-name> --db-snapshot-identifier <snapshot-id>
    ```

27. **Check CloudTrail events for EC2 instance?**  
    ```bash
    aws cloudtrail lookup-events --lookup-attributes AttributeKey=ResourceName,AttributeValue=<instance-id> --query 'Events[*].{Time:EventTime,Event:EventName,User:Username}' --output table
    ```

28. **Deregister AMI, delete snapshots?**  
    ```bash
    aws ec2 deregister-image --image-id <ami-id>
    aws ec2 describe-snapshots --filters Name=description,Values="Created by*for <ami-id>*" --query 'Snapshots[*].SnapshotId' --output text | xargs -n1 aws ec2 delete-snapshot --snapshot-id
    ```

29. **List SNS topics and subscriptions?**  
    ```bash
    for topic in $(aws sns list-topics --query 'Topics[*].TopicArn' --output text); do aws sns list-subscriptions-by-topic --topic-arn $topic --query 'Subscriptions[*].{Endpoint:Endpoint,Protocol:Protocol}' --output table; done
    ```

30. **Update Lambda timeout to 10 seconds?**  
    ```bash
    aws lambda update-function-configuration --function-name <function-name> --timeout 10
    ```

#### Terraform-Based Questions

31. **S3 bucket with public read access?**  
    ```hcl
    resource "aws_s3_bucket" "public_bucket" { bucket = "my-public-bucket-123" }
    resource "aws_s3_bucket_public_access_block" "public_bucket" { bucket = aws_s3_bucket.public_bucket.id, block_public_acls = false, block_public_policy = false, ignore_public_acls = false, restrict_public_buckets = false }
    resource "aws_s3_bucket_policy" "public_read" { bucket = aws_s3_bucket.public_bucket.id, policy = jsonencode({ Version = "2012-10-17", Statement = [{ Effect = "Allow", Principal = "*", Action = "s3:GetObject", Resource = "${aws_s3_bucket.public_bucket.arn}/*" }] }) }
    ```

32. **EC2 instance with specific key pair?**  
    ```hcl
    resource "aws_instance" "example" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro", key_name = "my-key-pair", tags = { Name = "MyEC2Instance" } }
    ```

33. **VPC with two private subnets?**  
    ```hcl
    resource "aws_vpc" "main" { cidr_block = "10.0.0.0/16", tags = { Name = "MainVPC" } }
    resource "aws_subnet" "private1" { vpc_id = aws_vpc.main.id, cidr_block = "10.0.1.0/24", availability_zone = "us-east-1a", tags = { Name = "PrivateSubnet1" } }
    resource "aws_subnet" "private2" { vpc_id = aws_vpc.main.id, cidr_block = "10.0.2.0/24", availability_zone = "us-east-1b", tags = { Name = "PrivateSubnet2" } }
    ```

34. **RDS instance with MySQL engine?**  
    ```hcl
    resource "aws_db_instance" "mysql" { allocated_storage = 20, engine = "mysql", engine_version = "8.0", instance_class = "db.t3.micro", name = "mydb", username = "admin", password = "SecurePass123!", skip_final_snapshot = true, tags = { Name = "MyRDS" } }
    ```

35. **Auto Scaling group with launch template?**  
    ```hcl
    resource "aws_launch_template" "example" { name_prefix = "example-", image_id = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro" }
    resource "aws_autoscaling_group" "example" { desired_capacity = 2, max_size = 3, min_size = 1, vpc_zone_identifier = [aws_subnet.private1.id, aws_subnet.private2.id], launch_template = { id = aws_launch_template.example.id, version = "$Latest" } }
    ```

36. **Module for ALB with target group?**  
    ```hcl
    module "alb" { source = "./modules/alb", vpc_id = aws_vpc.main.id, subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id], alb_name = "my-alb", target_group_name = "my-target-group" }
    # Module (modules/alb/main.tf):
    resource "aws_lb" "main" { name = var.alb_name, internal = false, load_balancer_type = "application", subnets = var.subnet_ids }
    resource "aws_lb_target_group" "main" { name = var.target_group_name, port = 80, protocol = "HTTP", vpc_id = var.vpc_id }
    variable "vpc_id" {}
    variable "subnet_ids" { type = list(string) }
    variable "alb_name" {}
    variable "target_group_name" {}
    ```

37. **CloudWatch monitoring on EC2 instance?**  
    ```hcl
    resource "aws_instance" "monitored" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro", monitoring = true, tags = { Name = "MonitoredEC2" } }
    resource "aws_cloudwatch_metric_alarm" "cpu" { alarm_name = "high-cpu-usage", comparison_operator = "GreaterThanThreshold", evaluation_periods = 2, metric_name = "CPUUtilization", namespace = "AWS/EC2", period = 300, statistic = "Average", threshold = 80, dimensions = { InstanceId = aws_instance.monitored.id } }
    ```

38. **S3 bucket with Glacier lifecycle rule?**  
    ```hcl
    resource "aws_s3_bucket" "lifecycle_bucket" { bucket = "my-lifecycle-bucket-123" }
    resource "aws_s3_bucket_lifecycle_configuration" "lifecycle" { bucket = aws_s3_bucket.lifecycle_bucket.id, rule = { id = "archive-to-glacier", status = "Enabled", transition = { days = 30, storage_class = "GLACIER" } } }
    ```

39. **EKS cluster with single node group?**  
    ```hcl
    resource "aws_eks_cluster" "example" { name = "my-eks-cluster", role_arn = aws_iam_role.eks_role.arn, vpc_config = { subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id] } }
    resource "aws_eks_node_group" "example" { cluster_name = aws_eks_cluster.example.name, node_group_name = "my-node-group", node_role_arn = aws_iam_role.node_role.arn, subnet_ids = [aws_subnet.private1.id, aws_subnet.private2.id], scaling_config = { desired_size = 1, max_size = 2, min_size = 1 } }
    resource "aws_iam_role" "eks_role" { name = "eks-role", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "eks.amazonaws.com" } }]) }
    resource "aws_iam_role" "node_role" { name = "eks-node-role", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "ec2.amazonaws.com" } }]) }
    ```

40. **IAM role with S3 full access?**  
    ```hcl
    resource "aws_iam_role" "s3_full_access" { name = "s3-full-access-role", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "ec2.amazonaws.com" } }]) }
    resource "aws_iam_role_policy_attachment" "s3_full_access" { role = aws_iam_role.s3_full_access.name, policy_arn = "arn:aws:iam::aws:policy/AmazonS3FullAccess" }
    ```

41. **Security group with SSH/HTTP inbound rules?**  
    ```hcl
    resource "aws_security_group" "example" { name = "allow_ssh_http", vpc_id = aws_vpc.main.id, ingress = [{ from_port = 22, to_port = 22, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }, { from_port = 80, to_port = 80, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }], egress = [{ from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] }] }
    ```

42. **CloudFront distribution for S3 bucket?**  
    ```hcl
    resource "aws_s3_bucket" "bucket" { bucket = "my-static-website-bucket" }
    resource "aws_cloudfront_distribution" "s3_distribution" { origin = { domain_name = aws_s3_bucket.bucket.bucket_regional_domain_name, origin_id = "S3Origin" }, enabled = true, is_ipv6_enabled = true, default_root_object = "index.html", default_cache_behavior = { allowed_methods = ["GET", "HEAD"], cached_methods = ["GET", "HEAD"], target_origin_id = "S3Origin", forwarded_values = { query_string = false, cookies = { forward = "none" } }, viewer_protocol_policy = "redirect-to-https" }, restrictions = { geo_restriction = { restriction_type = "none" } }, viewer_certificate = { cloudfront_default_certificate = true } }
    ```

43. **Lambda function triggered by S3 event?**  
    ```hcl
    resource "aws_s3_bucket" "bucket" { bucket = "my-lambda-trigger-bucket" }
    resource "aws_iam_role" "lambda_role" { name = "lambda_execution_role", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "lambda.amazonaws.com" } }]) }
    resource "aws_iam_role_policy_attachment" "lambda_policy" { role = aws_iam_role.lambda_role.name, policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole" }
    resource "aws_lambda_function" "example" { filename = "lambda_function.zip", function_name = "s3_trigger_lambda", role = aws_iam_role.lambda_role.arn, handler = "index.handler", runtime = "nodejs18.x" }
    resource "aws_s3_bucket_notification" "bucket_notification" { bucket = aws_s3_bucket.bucket.id, lambda_function = { lambda_function_arn = aws_lambda_function.example.arn, events = ["s3:ObjectCreated:*"] } }
    resource "aws_lambda_permission" "allow_s3" { statement_id = "AllowS3Invoke", action = "lambda:InvokeFunction", function_name = aws_lambda_function.example.function_name, principal = "s3.amazonaws.com", source_arn = aws_s3_bucket.bucket.arn }
    ```

44. **DynamoDB table with auto-scaling?**  
    ```hcl
    resource "aws_dynamodb_table" "example" { name = "MyTable", billing_mode = "PROVISIONED", read_capacity = 5, write_capacity = 5, hash_key = "id", attribute = { name = "id", type = "S" } }
    resource "aws_appautoscaling_target" "read_target" { max_capacity = 20, min_capacity = 5, resource_id = "table/${aws_dynamodb_table.example.name}", scalable_dimension = "dynamodb:table:ReadCapacityUnits", service_namespace = "dynamodb" }
    resource "aws_appautoscaling_policy" "read_policy" { name = "scale-read", policy_type = "TargetTrackingScaling", resource_id = aws_appautoscaling_target.read_target.resource_id, scalable_dimension = aws_appautoscaling_target.read_target.scalable_dimension, service_namespace = aws_appautoscaling_target.read_target.service_namespace, target_tracking_scaling_policy_configuration = { predefined_metric_specification = { predefined_metric_type = "DynamoDBReadCapacityUtilization" }, target_value = 70.0 } }
    ```

45. **NAT Gateway in a VPC?**  
    ```hcl
    resource "aws_vpc" "main" { cidr_block = "10.0.0.0/16" }
    resource "aws_subnet" "public" { vpc_id = aws_vpc.main.id, cidr_block = "10.0.1.0/24" }
    resource "aws_internet_gateway" "igw" { vpc_id = aws_vpc.main.id }
    resource "aws_eip" "nat_eip" { vpc = true }
    resource "aws_nat_gateway" "nat" { allocation_id = aws_eip.nat_eip.id, subnet_id = aws_subnet.public.id, depends_on = [aws_internet_gateway.igw] }
    ```

46. **ECS cluster with Fargate task definition?**  
    ```hcl
    resource "aws_ecs_cluster" "example" { name = "my-ecs-cluster" }
    resource "aws_iam_role" "ecs_task_execution_role" { name = "ecsTaskExecutionRole", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "ecs-tasks.amazonaws.com" } }]) }
    resource "aws_iam_role_policy_attachment" "ecs_task_execution_policy" { role = aws_iam_role.ecs_task_execution_role.name, policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy" }
    resource "aws_ecs_task_definition" "example" { family = "my-fargate-task", network_mode = "awsvpc", requires_compatibilities = ["FARGATE"], cpu = "256", memory = "512", execution_role_arn = aws_iam_role.ecs_task_execution_role.arn, container_definitions = jsonencode([{ name = "my-container", image = "amazon/amazon-ecs-sample", portMappings = [{ containerPort = 80, hostPort = 80 }]}]) }
    resource "aws_ecs_service" "example" { name = "my-service", cluster = aws_ecs_cluster.example.id, task_definition = aws_ecs_task_definition.example.arn, launch_type = "FARGATE", desired_count = 1, network_configuration = { subnets = [aws_subnet.public.id], security_groups = [aws_security_group.example.id] } }
    ```

47. **Import existing S3 bucket into Terraform state?**  
    ```hcl
    resource "aws_s3_bucket" "existing" { bucket = "my-existing-bucket" }
    ```
    ```bash
    terraform import aws_s3_bucket.existing my-existing-bucket
    ```

48. **SNS topic with email subscription?**  
    ```hcl
    resource "aws_sns_topic" "example" { name = "my-sns-topic" }
    resource "aws_sns_topic_subscription" "email" { topic_arn = aws_sns_topic.example.arn, protocol = "email", endpoint = "user@example.com" }
    ```

49. **EC2 instance behind a load balancer?**  
    ```hcl
    resource "aws_vpc" "main" { cidr_block = "10.0.0.0/16" }
    resource "aws_subnet" "public" { vpc_id = aws_vpc.main.id, cidr_block = "10.0.1.0/24" }
    resource "aws_security_group" "alb_sg" { vpc_id = aws_vpc.main.id, ingress = { from_port = 80, to_port = 80, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"] }, egress = { from_port = 0, to_port = 0, protocol = "-1", cidr_blocks = ["0.0.0.0/0"] } }
    resource "aws_lb" "example" { name = "my-alb", internal = false, load_balancer_type = "application", security_groups = [aws_security_group.alb_sg.id], subnets = [aws_subnet.public.id] }
    resource "aws_lb_target_group" "example" { name = "my-tg", port = 80, protocol = "HTTP", vpc_id = aws_vpc.main.id }
    resource "aws_lb_listener" "example" { load_balancer_arn = aws_lb.example.arn, port = 80, protocol = "HTTP", default_action = { type = "forward", target_group_arn = aws_lb_target_group.example.arn } }
    resource "aws_instance" "example" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro", subnet_id = aws_subnet.public.id, security_groups = [aws_security_group.alb_sg.name] }
    resource "aws_lb_target_group_attachment" "example" { target_group_arn = aws_lb_target_group.example.arn, target_id = aws_instance.example.id, port = 80 }
    ```

50. **Route 53 record for ALB?**  
    ```hcl
    resource "aws_route53_zone" "example" { name = "example.com" }
    resource "aws_route53_record" "www" { zone_id = aws_route53_zone.example.zone_id, name = "www.example.com", type = "A", alias = { name = aws_lb.example.dns_name, zone_id = aws_lb.example.zone_id, evaluate_target_health = true } }
    ```

51. **VPC peering connection between two VPCs?**  
    ```hcl
    resource "aws_vpc" "vpc_a" { cidr_block = "10.0.0.0/16" }
    resource "aws_vpc" "vpc_b" { cidr_block = "10.1.0.0/16" }
    resource "aws_vpc_peering_connection" "peer" { vpc_id = aws_vpc.vpc_a.id, peer_vpc_id = aws_vpc.vpc_b.id, auto_accept = true }
    resource "aws_route" "vpc_a_route" { route_table_id = aws_vpc.vpc_a.main_route_table_id, destination_cidr_block = aws_vpc.vpc_b.cidr_block, vpc_peering_connection_id = aws_vpc_peering_connection.peer.id }
    resource "aws_route" "vpc_b_route" { route_table_id = aws_vpc.vpc_b.main_route_table_id, destination_cidr_block = aws_vpc.vpc_a.cidr_block, vpc_peering_connection_id = aws_vpc_peering_connection.peer.id }
    ```

52. **CloudWatch alarm for EC2 CPU utilization?**  
    ```hcl
    resource "aws_instance" "example" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro" }
    resource "aws_cloudwatch_metric_alarm" "cpu_alarm" { alarm_name = "ec2-cpu-utilization", comparison_operator = "GreaterThanThreshold", evaluation_periods = "2", metric_name = "CPUUtilization", namespace = "AWS/EC2", period = "300", statistic = "Average", threshold = "80", dimensions = { InstanceId = aws_instance.example.id } }
    ```

53. **Manage existing RDS instance without recreating?**  
    ```hcl
    resource "aws_db_instance" "existing_rds" { identifier = "my-existing-db", engine = "mysql", instance_class = "db.t3.micro", allocated_storage = 20, username = "admin", password = "existingpassword", skip_final_snapshot = true }
    ```
    ```bash
    terraform import aws_db_instance.existing_rds my-existing-db
    ```

54. **Elastic Beanstalk application?**  
    ```hcl
    resource "aws_elastic_beanstalk_application" "app" { name = "my-eb-app" }
    resource "aws_elastic_beanstalk_environment" "env" { name = "my-eb-env", application = aws_elastic_beanstalk_application.app.name, solution_stack_name = "64bit Amazon Linux 2 v3.3.13 running Python 3.8", setting = { namespace = "aws:autoscaling:launchconfiguration", name = "InstanceType", value = "t2.micro" } }
    resource "aws_s3_bucket" "app_bucket" { bucket = "my-eb-app-bucket" }
    resource "aws_s3_object" "app_version" { bucket = aws_s3_bucket.app_bucket.bucket, key = "app.zip", source = "path/to/your/app.zip" }
    resource "aws_elastic_beanstalk_application_version" "version" { name = "v1", application = aws_elastic_beanstalk_application.app.name, bucket = aws_s3_bucket.app_bucket.bucket, key = aws_s3_object.app_version.key }
    ```

55. **S3 bucket with server-side encryption?**  
    ```hcl
    resource "aws_s3_bucket" "bucket" { bucket = "my-encrypted-bucket" }
    resource "aws_s3_bucket_server_side_encryption_configuration" "encryption" { bucket = aws_s3_bucket.bucket.bucket, rule = { apply_server_side_encryption_by_default = { sse_algorithm = "AES256" } } }
    resource "aws_s3_bucket_versioning" "versioning" { bucket = aws_s3_bucket.bucket.bucket, versioning_configuration = { status = "Enabled" } }
    resource "aws_s3_bucket_public_access_block" "block" { bucket = aws_s3_bucket.bucket.bucket, block_public_acls = true, block_public_policy = true, ignore_public_acls = true, restrict_public_buckets = true }
    ```

56. **Check EC2 status, reboot if unresponsive?**  
    ```bash
    aws ec2 describe-instances --instance-ids <instance-id> --query "Reservations[*].Instances[*].State.Name" --output text
    aws ec2 reboot-instances --instance-ids <instance-id>
    ```

57. **Identify, terminate EC2 instances older than 30 days?**  
    ```bash
    aws ec2 describe-instances --query "Reservations[*].Instances[?LaunchTime<'$(date -u -d '30 days ago' +%Y-%m-%dT%H:%M:%SZ')'].InstanceId" --output text | xargs -r aws ec2 terminate-instances --instance-ids
    ```

58. **Move old S3 files to Glacier?**  
    ```bash
    aws s3api list-objects-v2 --bucket <bucket-name> --query "Contents[?LastModified<'$(date -u -d '30 days ago' +%Y-%m-%d')'].Key" --output text | xargs -I {} aws s3api put-object --bucket <bucket-name> --key {} --storage-class GLACIER
    ```

59. **Troubleshoot Auto Scaling group not launching?**  
    ```bash
    aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names <asg-name>
    aws autoscaling describe-scaling-activities --auto-scaling-group-name <asg-name> --max-items 10
    ```

60. **Fetch last 10 minutes of ECS task logs?**  
    ```bash
    aws logs get-log-events --log-group-name <log-group-name> --log-stream-name <log-stream-name> --start-time $(date -d '10 minutes ago' +%s000) --end-time $(date +%s000) --output text
    ```

61. **Check RDS status, initiate failover?**  
    ```bash
    aws rds describe-db-instances --db-instance-identifier <db-instance-id> --query "DBInstances[*].DBInstanceStatus" --output text
    aws rds failover-db-cluster --db-cluster-identifier <db-cluster-id>
    ```

62. **Delete unused Elastic IPs?**  
    ```bash
    aws ec2 describe-addresses --query "Addresses[?AssociationId==null].AllocationId" --output text | xargs -r aws ec2 release-address --allocation-id
    ```

63. **Check Lambda function failure logs?**  
    ```bash
    aws logs filter-log-events --log-group-name /aws/lambda/<function-name> --filter-pattern "ERROR" --limit 10
    ```

64. **Investigate ELB unhealthy instances, deregister?**  
    ```bash
    aws elbv2 describe-target-health --target-group-arn <target-group-arn> --query "TargetHealthDescriptions[?TargetHealth.State=='unhealthy'].Target.Id" --output text
    aws elbv2 deregister-targets --target-group-arn <target-group-arn> --targets Id=<instance-id>
    ```

65. **Update EC2 security group for HTTPS?**  
    ```bash
    aws ec2 authorize-security-group-ingress --group-id <security-group-id> --protocol tcp --port 443 --cidr 0.0.0.0/0
    ```

66. **Check S3 bucket replication rule status?**  
    ```bash
    aws s3api get-bucket-replication --bucket <source-bucket-name>
    ```

67. **Troubleshoot EKS cluster not responding?**  
    ```bash
    aws eks describe-cluster --name <cluster-name> --query "cluster.status"
    aws eks list-nodegroups --cluster-name <cluster-name>
    aws eks describe-nodegroup --cluster-name <cluster-name> --nodegroup-name <nodegroup-name>
    ```

68. **Restore S3 object from specific version?**  
    ```bash
    aws s3api copy-object --bucket <bucket-name> --key <object-key> --copy-source <bucket-name>/<object-key>?versionId=<version-id>
    ```

69. **Troubleshoot CloudWatch alarm not triggering?**  
    ```bash
    aws cloudwatch describe-alarms --alarm-names <alarm-name>
    aws cloudwatch describe-alarm-history --alarm-name <alarm-name> --history-item-type StateUpdate
    ```

70. **Resize RDS instance for load?**  
    ```bash
    aws rds modify-db-instance --db-instance-identifier <db-instance-id> --db-instance-class <new-instance-class> --apply-immediately
    ```

71. **Debug Terraform EC2 instance not accessible?**  
    ```hcl
    resource "aws_instance" "example" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro", subnet_id = aws_subnet.public.id, security_groups = [aws_security_group.allow_ssh.id], associate_public_ip_address = true }
    ```
    ```bash
    terraform apply
    aws ec2 describe-instances --instance-ids <instance-id>
    ```

72. **Enable versioning on existing S3 bucket?**  
    ```hcl
    resource "aws_s3_bucket" "existing_bucket" { bucket = "<bucket-name>" }
    resource "aws_s3_bucket_versioning" "versioning" { bucket = aws_s3_bucket.existing_bucket.id, versioning_configuration = { status = "Enabled" } }
    ```

73. **Resolve Terraform ‘resource already exists’ error?**  
    ```hcl
    resource "aws_s3_bucket" "my_bucket" { bucket = "<bucket-name>" }
    ```
    ```bash
    terraform import aws_s3_bucket.my_bucket <bucket-name>
    ```

74. **Add new subnet to existing VPC?**  
    ```hcl
    resource "aws_vpc" "existing_vpc" { cidr_block = "10.0.0.0/16" }
    resource "aws_subnet" "new_subnet" { vpc_id = aws_vpc.existing_vpc.id, cidr_block = "10.0.3.0/24", availability_zone = "us-east-1c" }
    ```

75. **Update RDS instance storage capacity?**  
    ```hcl
    resource "aws_db_instance" "example" { identifier = "my-rds-instance", allocated_storage = 50, instance_class = "db.t3.medium", engine = "mysql", username = "admin", password = "password", apply_immediately = true }
    ```

76. **Fix Terraform ALB not routing traffic?**  
    ```hcl
    resource "aws_lb" "example" { name = "my-alb", internal = false, load_balancer_type = "application", security_groups = [aws_security_group.alb.id], subnets = [aws_subnet.public1.id, aws_subnet.public2.id] }
    resource "aws_lb_target_group" "tg" { name = "my-tg", port = 80, protocol = "HTTP", vpc_id = aws_vpc.main.id }
    resource "aws_lb_listener" "listener" { load_balancer_arn = aws_lb.example.arn, port = 80, protocol = "HTTP", default_action = { type = "forward", target_group_arn = aws_lb_target_group.tg.arn } }
    ```

77. **Add IAM policy to existing role?**  
    ```hcl
    resource "aws_iam_role" "existing_role" { name = "my-role", assume_role_policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "sts:AssumeRole", Effect = "Allow", Principal = { Service = "ec2.amazonaws.com" } }]) }
    resource "aws_iam_policy" "new_policy" { name = "new-policy", policy = jsonencode({ Version = "2012-10-17", Statement = [{ Action = "s3:GetObject", Effect = "Allow", Resource = "arn:aws:s3:::my-bucket/*" }]) }
    resource "aws_iam_role_policy_attachment" "attach" { role = aws_iam_role.existing_role.name, policy_arn = aws_iam_policy.new_policy.arn }
    ```

78. **Scale EKS node group to 3 nodes?**  
    ```hcl
    resource "aws_eks_node_group" "example" { cluster_name = aws_eks_cluster.example.name, node_group_name = "my-node-group", node_role_arn = aws_iam_role.node.arn, subnet_ids = aws_subnet.example[*].id, scaling_config = { desired_size = 3, max_size = 5, min_size = 1 } }
    ```

79. **Debug Terraform Lambda function failure?**  
    ```hcl
    resource "aws_lambda_function" "example" { function_name = "my-lambda", handler = "index.handler", runtime = "nodejs16.x", role = aws_iam_role.lambda.arn, filename = "lambda.zip" }
    ```

80. **Update security group with new inbound rule?**  
    ```hcl
    resource "aws_security_group" "example" { name = "my-sg", vpc_id = aws_vpc.main.id }
    resource "aws_security_group_rule" "new_rule" { type = "ingress", from_port = 443, to_port = 443, protocol = "tcp", cidr_blocks = ["0.0.0.0/0"], security_group_id = aws_security_group.example.id }
    ```

81. **Fix Terraform EC2 launch issue, verify with CLI?**  
    ```hcl
    resource "aws_instance" "example" { ami = "ami-0c55b159cbfafe1f0", instance_type = "t2.micro", subnet_id = aws_subnet.public.id, security_groups = [aws_security_group.allow_ssh.id], associate_public_ip_address = true }
    ```
    ```bash
    terraform apply
    aws ec2 describe-instances --instance-ids <instance-id> --query "Reservations[*].Instances[*].[InstanceId, State.Name]"
    ```

82. **Check missing Terraform-created S3 bucket?**  
    ```bash
    aws s3 ls s3://<bucket-name>
    terraform state list | grep aws_s3_bucket
    terraform import aws_s3_bucket.my_bucket <bucket-name>
    ```

83. **Update RDS instance, confirm with CLI?**  
    ```hcl
    resource "aws_db_instance" "example" { identifier = "my-rds-instance", allocated_storage = 30, instance_class = "db.t3.medium", engine = "postgres", username = "admin", password = "password", apply_immediately = true }
    ```
    ```bash
    terraform apply
    aws rds describe-db-instances --db-instance-identifier my-rds-instance --query "DBInstances[*].[DBInstanceStatus, AllocatedStorage, DBInstanceClass]"
    ```

84. **Debug Terraform Auto Scaling group not scaling?**  
    ```hcl
    resource "aws_autoscaling_group" "example" { name = "my-asg", min_size = 1, max_size = 3, desired_capacity = 2, vpc_zone_identifier = [aws_subnet.subnet1.id, aws_subnet.subnet2.id], launch_template = { id = aws_launch_template.example.id, version = "$Latest" } }
    ```
    ```bash
    aws autoscaling describe-auto-scaling-groups --auto-scaling-group-names my-asg
    aws autoscaling describe-scaling-activities --auto-scaling-group-name my-asg --max-items 10
    aws ec2 describe-instances --instance-ids <instance-ids-from-asg>
    ```

85. **Create SNS topic with Terraform, subscribe email with CLI?**  
    ```hcl
    resource "aws_sns_topic" "example" { name = "my-sns-topic" }
    output "sns_topic_arn" { value = aws_sns_topic.example.arn }
    ```
    ```bash
    terraform apply
    aws sns subscribe --topic-arn <sns-topic-arn> --protocol email --notification-endpoint user@example.com
    ```