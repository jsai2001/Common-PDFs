# AWS Prep Guide: 105 Questions Answered

## General Cloud Computing Concepts

### What’s cloud computing vs. on-premises?
- **Cloud**: Rent resources online, managed by provider—flexible, scalable.
- **On-premises**: Own and maintain hardware locally—costly, manual.

### Key benefits of AWS over others?
- Global reach, 200+ services, pay-as-you-go, strong security, big ecosystem.

### IaaS vs. PaaS vs. SaaS with AWS examples?
- **IaaS**: EC2, S3—manage OS/apps.
- **PaaS**: Elastic Beanstalk—deploy code, AWS handles rest.
- **SaaS**: WorkMail—just use it.

### What’s elasticity in AWS?
- Auto-scales resources to match demand—e.g., Auto Scaling adds EC2s on traffic spike, cuts them when low.

### Shared responsibility model in AWS?
- AWS secures infra (data centers, hardware). You secure your setup (data, IAM, configs).

### How AWS ensures high availability and fault tolerance?
- Multi-AZ/Region design, redundant systems—e.g., ELB across AZs, RDS failover.

### Scalability vs. elasticity?
- **Scalability**: Add capacity (manual/planned).
- **Elasticity**: Auto-adjust in real-time.

### Region, Availability Zone, Edge Location?
- **Region**: Geographic area (e.g., US-East-1).
- **AZ**: Isolated site in Region.
- **Edge**: CloudFront cache point near users.

### AWS pricing and cost optimization?
- Pay-per-use—EC2 hourly, S3 per GB.
- Optimize: Reserved/Spot Instances, right-sizing, Cost Explorer.

### AWS Well-Architected Framework and its pillars?
- Best practices guide.
- **Pillars**: Operational Excellence, Security, Reliability, Performance Efficiency, Cost Optimization.

## AWS Core Services

### Compute

#### What’s Amazon EC2 and its features?
- Virtual servers—flexible types, scalable, pay-as-you-go, integrates with S3/VPC, offers Elastic IPs.

#### EC2 instance types (t2.micro vs. m5.large)?
- **t2.micro**: 1 vCPU, 1 GB, burstable—small tasks.
- **m5.large**: 2 vCPUs, 8 GB, steady—web apps.

#### What are AMIs?
- Templates (OS+software) for EC2—stored in S3, used for consistency/scaling.

#### Auto Scaling Group?
- Manages EC2 fleet—scales based on demand, pairs with ELB.

#### Secure an EC2 instance?
- Security Groups, IAM roles, EBS encryption, private subnets, updates, monitoring.

#### AWS Lambda and when to use it?
- Serverless compute—runs code on events. Use over EC2 for short, event-driven tasks (e.g., file processing).

#### Elastic Beanstalk vs. EC2/Lambda?
- **Elastic Beanstalk**: PaaS—auto-manages deployment.
- **EC2**: Full control, manual.
- **Lambda**: Serverless, event-based.

#### ECS vs. EKS?
- **ECS**: AWS-native container orchestration—simple.
- **EKS**: Managed Kubernetes—flexible, portable.

#### AWS Fargate?
- Serverless containers for ECS/EKS—no EC2 management, just define resources.

#### Monitor EC2 performance?
- CloudWatch—CPU, memory, I/O metrics, alarms. Add SSM Agent, CloudTrail.

### Storage

#### S3 vs. EBS vs. EFS?
- **S3**: Object storage—backups, web.
- **EBS**: Block for EC2—databases.
- **EFS**: Shared file system—multi-instance apps.

#### S3 bucket and use cases?
- Object container—web hosting, backups, data lakes, content delivery.

#### S3 versioning?
- Keeps all object versions—retrieve older ones, costs more.

#### S3 storage classes?
- Standard (frequent), IA (less used), One Zone-IA (cheap, risky), Glacier (archive), Deep Archive (rare), Intelligent-Tiering (auto).

#### Secure S3 data?
- IAM/bucket policies, SSE-S3/KMS encryption, versioning, block public access, logs.

#### Amazon EBS and EC2 integration?
- Block storage—mounts to EC2 as root/data volume, snapshots for backup.

#### SSD vs. HDD in EBS?
- **SSD (gp3, io2)**: Fast, IOPS-heavy—DBs.
- **HDD (st1, sc1)**: Cheap, throughput-focused—big data, cold storage.

#### Amazon EFS and when over S3?
- Shared file system—mounts to multiple EC2s. Use for shared access vs. S3’s object storage.

#### AWS Snowball for migration?
- Physical device (80TB)—ships data to S3 for big, slow transfers.

#### Enable encryption for S3, EBS, EFS?
- **S3**: SSE-S3/KMS via settings.
- **EBS**: KMS at creation.
- **EFS**: KMS on setup.

### Networking

#### What’s a VPC and setup?
- Private cloud—set CIDR (e.g., 10.0.0.0/16), subnets, Internet Gateway, route tables.

#### Public vs. private subnet?
- **Public**: Routes to Internet Gateway—web-facing.
- **Private**: No direct internet—backend.

#### Route Table in VPC?
- Directs traffic—e.g., 0.0.0.0/0 to Internet Gateway for public subnets.

#### NAT Gateway?
- Outbound internet for private subnets—sits in public subnet with Elastic IP.

#### Internet Gateway vs. NAT Gateway?
- **Internet Gateway**: Two-way public access.
- **NAT**: One-way outbound for private.

#### AWS Direct Connect?
- Private link to AWS—use for high-speed, secure hybrid transfers.

#### Elastic Load Balancer (ELB) and types?
- Distributes traffic—ALB (HTTP), NLB (TCP/UDP), CLB (legacy).

#### Amazon Route 53?
- DNS service—resolves domains, offers failover, latency routing.

#### Security Group vs. Network ACL?
- **Security Group**: Stateful, instance-level.
- **NACL**: Stateless, subnet-level.

#### Troubleshoot VPC connectivity?
- Check routes, Security Groups, NACLs, Flow Logs, IP assignment.

## Databases

#### Amazon RDS and supported DBs?
- Managed relational DB—MySQL, PostgreSQL, MariaDB, Oracle, SQL Server, Aurora.

#### Multi-AZ in RDS?
- Enable Multi-AZ—syncs primary to standby in another AZ for failover.

#### Amazon Aurora vs. RDS?
- **Aurora**: Faster, scalable, 6-copy storage.
- **RDS**: Standard engines, simpler.

#### Amazon DynamoDB and when over RDS?
- NoSQL—scales fast, low latency. Use for unstructured, high-traffic vs. RDS’s relational.

#### DynamoDB scaling/partitioning?
- On-demand/provisioned scaling, partitions via key—auto-splits for load.

#### Amazon Redshift and use cases?
- Data warehouse—analytics, BI on big datasets.

#### Migrate on-prem DB to AWS?
- Use DMS—bulk move, then sync changes, switch app.

#### SQL vs. NoSQL in AWS?
- **SQL (RDS)**: Structured, joins.
- **NoSQL (DynamoDB)**: Flexible, scales.

#### Secure an AWS database?
- IAM, encryption (KMS/SSL), private subnet, logs, backups.

#### Amazon ElastiCache?
- In-memory cache (Redis/Memcached)—speeds apps by reducing DB load.

## Security and Identity

#### AWS IAM and permissions?
- Controls access—users, roles, policies define actions.

#### IAM policies, roles, users?
- **Policies**: Rules (JSON).
- **Users**: Individuals.
- **Roles**: Temp access for services.

#### Least privilege in AWS?
- Limit permissions to essentials—specific policies, review with Access Analyzer.

#### MFA in AWS?
- Adds 2nd factor—enable via IAM, QR code in app.

#### AWS KMS?
- Manages encryption keys—encrypts data in S3, RDS, etc.

#### Rotate keys in AWS?
- KMS auto-rotates yearly, or manual—new key, update apps.

#### AWS Shield?
- DDoS protection—Standard (free), Advanced (paid, robust).

#### AWS WAF vs. traditional firewall?
- **WAF**: Layer 7 (HTTP) filtering.
- **Traditional**: Network-level control.

#### Audit/monitor security?
- CloudTrail (API logs), CloudWatch (alerts), Config (compliance).

#### AWS Secrets Manager vs. KMS?
- **Secrets Manager**: Stores/rotates secrets.
- **KMS**: Manages keys.

## Monitoring, Logging, Management

#### Amazon CloudWatch?
- Monitors metrics/logs—EC2, RDS, Lambda, etc.

#### Set up CloudWatch alarms?
- Pick metric (e.g., CPU), set threshold, action (e.g., SNS).

#### CloudTrail vs. CloudWatch?
- **CloudTrail**: API activity logs.
- **CloudWatch**: Performance metrics.

#### AWS Systems Manager for EC2?
- SSM Agent—runs commands, patches, sessions remotely.

#### AWS Trusted Advisor?
- Recommends cost, performance, security fixes.

#### Automate with AWS CLI/SDKs?
- Script tasks—e.g., `aws ec2 start-instances`, Boto3 code.

#### AWS Config?
- Tracks resource configs—flags compliance drift.

#### Troubleshoot failed deployment?
- Check CloudWatch logs, CloudTrail, rollback via tools.

#### Metric vs. log in CloudWatch?
- **Metric**: Numbers (CPU).
- **Log**: Text (errors).

#### Centralized logging?
- CloudWatch Logs group, stream via agent, S3/Kinesis for scale.

## Architecture and Design

#### Highly available app on AWS?
- Multi-AZ ELB, Auto Scaling, RDS Multi-AZ, Route 53.

#### Horizontal vs. vertical scaling?
- **Horizontal**: More instances.
- **Vertical**: Bigger instance.

#### Cost-effective architecture?
- Right-size, Reserved/Spot, serverless, tiered storage.

#### Microservices on AWS?
- ECS/EKS, Lambda, API Gateway, SQS—scalable, independent.

#### Migrate monolith to AWS?
- Lift-and-shift to EC2, refactor to microservices (ECS, Lambda).

#### AWS architecture anti-patterns?
- Overprovisioning, no elasticity, weak security, no IaC.

#### Disaster recovery in AWS?
- Multi-region, S3 replication, RDS replicas, Route 53 failover.

#### Stateless vs. stateful apps?
- **Stateless**: No memory (Lambda).
- **Stateful**: Retains data (RDS).

#### AWS Global Accelerator?
- Routes to nearest edge—low latency, failover.

#### Serverless architecture?
- Lambda, API Gateway, DynamoDB, S3, Step Functions.

## DevOps and CI/CD

#### AWS CodePipeline?
- CI/CD service—integrates CodeCommit, CodeBuild, CodeDeploy.

#### CI/CD with CodeBuild/CodeDeploy?
- **Pipeline**: Source (CodeCommit), Build (CodeBuild), Deploy (CodeDeploy).

#### Blue/green vs. rolling deployment?
- **Blue/green**: Full switch, no downtime.
- **Rolling**: Gradual, partial risk.

#### CloudFormation for IaC?
- Templates define resources—provision, update, rollback.

#### Benefits of AWS OpsWorks?
- Managed Chef/Puppet—automates deployment, scaling.

#### Roll back failed deployment?
- **CodeDeploy**: Switch back (blue/green).
- **CloudFormation**: Revert stack.

#### Docker in AWS and ECS?
- Containers for apps—ECS runs them, scales via ECR images.

#### Automate infra provisioning?
- CloudFormation/Terraform, CLI scripts, Auto Scaling.

#### AWS CodeStar?
- Unifies pipeline setup—CodeCommit, CodeBuild, hosting.

#### Secrets in CI/CD?
- Secrets Manager, IAM roles, KMS encryption.

## Scenario-Based Questions

#### Migrate 10TB DB with minimal downtime?
- Snowball for bulk, DMS for sync, switch in window.

#### Web app for 1M users daily?
- ELB, Auto Scaling EC2, RDS Multi-AZ, ElastiCache, S3/CloudFront.

#### Troubleshoot unreachable EC2?
- Status, Security Groups, routes, IP, logs—restart if needed.

#### Fix slow app performance?
- CloudWatch, X-Ray—scale EC2, cache, optimize DB.

#### Secure S3+RDS app?
- IAM, encryption, private subnets, restricted access.

#### S3 bucket made public?
- Remove public access, block settings, audit with CloudTrail.

#### Handle traffic spike?
- Auto Scaling, RDS replicas, CloudFront, Lambda.

#### Backup/recovery strategy?
- EBS snapshots, RDS backups, S3 versioning, Glacier.

#### Reduce overprovisioned costs?
- Cost Explorer, right-size, Spot, stop idle, tier S3.

#### Multi-region DR?
- Route 53 failover, S3 replication, RDS replicas, AMIs.

## Behavioral Questions

#### Took ownership of a project?
- Led app migration—planned, executed, beat deadline.

#### Dove deep into a problem?
- Fixed crashes—traced CPU spike, optimized query.

#### Handled disagreement?
- Debated EC2 vs. Lambda, committed, optimized—aligned later.

#### Delivered fast solution?
- Built dashboard in 5 days vs. 2 weeks—Glue, Athena, QuickSight.

#### Ensured customer satisfaction?
- Migrated app—zero downtime, cost savings, constant updates.