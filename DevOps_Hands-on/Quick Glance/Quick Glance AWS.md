### AWS

#### Check Availability Zone Availability
```bash
aws ec2 describe-instance-type-offerings \
    --location-type availability-zone \
    --filters "Name=instance-type,Values=$instance_type" "Name=location,Values=$1" \
    --region $region \
    --query 'InstanceTypeOfferings[?InstanceType==`'${instance_type}'`].Location' \
    --output text
```

#### Key Pairs

**Check if Key Pair Exists:**
```bash
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
```
**Create Key Pair:**
```bash
aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosComplexKeyPair.pem
```
**Set Permissions:**
```bash
chmod 400 CentosComplexKeyPair.pem
```

#### VPC
**Describe VPCs:**
```bash
aws ec2 describe-vpcs --filters "Name=cidr,Values=${vpc_cidr}" --query 'Vpcs[0].VpcId' --output text --region ${region}
```
**Create VPC:**
```bash
aws ec2 create-vpc --cidr-block ${vpc_cidr} --query 'Vpc.VpcId' --output text --region ${region}
```

#### Internet Gateway
**Check if Internet Gateway Exists:**
```bash
igw_id=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${vpc_id}" --query 'InternetGateways[0].InternetGatewayId' --output text --region ${region})
if [ "$igw_id" == "None" ]; then
```
**Create Internet Gateway:**
```bash
igw_id=$(aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text --region ${region})
```
**Attach Internet Gateway:**
```bash
aws ec2 attach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ${region}
```

#### Subnets
**Check if Public Subnet 1 Exists:**
```bash
public_subnet_id_1=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_1}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$public_subnet_id_1" == "None" ]; then
```
**Create Public Subnet 1:**
```bash
public_subnet_id_1=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_1} --availability-zone ${available_zone_1} --query 'Subnet.SubnetId' --output text --region ${region})
```

#### Route Tables
**Check if Route Table for Public Subnet 1 Exists:**
```bash
public_route_table_id_1=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_1}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$public_route_table_id_1" == "None" ]; then
```
**Create Route Table for Public Subnet 1:**
```bash
public_route_table_id_1=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
```
**Associate Route Table with Public Subnet 1:**
```bash
aws ec2 associate-route-table --route-table-id ${public_route_table_id_1} --subnet-id ${public_subnet_id_1} --region ${region}
```
**Create Route in Route Table for Public Subnet 1:**
```bash
aws ec2 create-route --route-table-id ${public_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
```

#### NAT Gateway
**Allocate Elastic IP:**
```bash
eip_allocation_id_1=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
```
**Create NAT Gateway:**
```bash
nat_gateway_id_1=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_1} --allocation-id ${eip_allocation_id_1} --query 'NatGateway.NatGatewayId' --output text --region ${region})
```
**Update Private Route Table 1:**
```bash
aws ec2 create-route --route-table-id ${private_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_1} --region ${region}
echo "Updated Private Route Table 1 to use NAT Gateway 1"
```

#### Security Groups
**Check if Bastion Security Group Exists:**
```bash
bastion_security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${bastion_security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$bastion_security_group_id" == "None" ]; then
```
**Create Bastion Security Group:**
```bash
bastion_security_group_id=$(aws ec2 create-security-group --group-name ${bastion_security_group_name} --description "Bastion security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
```
**Add Inbound Rules to Bastion Security Group:**
```bash
aws ec2 authorize-security-group-ingress --group-id ${bastion_security_group_id} --protocol tcp --port 22 --cidr 0.0.0.0/0 --region ${region}
```
**Check if Application Security Group Exists:**
```bash
app_security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${app_security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$app_security_group_id" == "None" ]; then
```
**Create Application Security Group:**
```bash
app_security_group_id=$(aws ec2 create-security-group --group-name ${app_security_group_name} --description "Application security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
```
**Add Inbound Rules to Application Security Group:**
```bash
aws ec2 authorize-security-group-ingress --group-id ${app_security_group_id} --protocol tcp --port 22 --source-group ${bastion_security_group_id} --region ${region}
aws ec2 authorize-security-group-ingress --group-id ${app_security_group_id} --protocol tcp --port 80 --cidr 0.0.0.0/0 --region ${region}
```

#### IAM Role
**Trust Policy:**
```bash
cat > trust-policy.json <<EOF
{
   "Version": "2012-10-17",
   "Statement": [
      {
    "Effect": "Allow",
    "Principal": {
       "Service": "ec2.amazonaws.com"
    },
    "Action": "sts:AssumeRole"
      },
      {
    "Effect": "Allow",
    "Principal": {
       "Service": "lambda.amazonaws.com"
    },
    "Action": "sts:AssumeRole"
      }
   ]
}
EOF
```
**Create Role:**
```bash
aws iam create-role --role-name ${role_name} --assume-role-policy-document file://trust-policy.json --region ${region}
```
**Attach Policy:**
```bash
aws iam attach-role-policy --role-name ${role_name} --policy-arn ${policy_arn} --region ${region}
```
**Create Instance Profile:**
```bash
aws iam create-instance-profile --instance-profile-name ${instance_profile_name} --region ${region}
```
**Add Role to Instance Profile:**
```bash
aws iam add-role-to-instance-profile --instance-profile-name ${instance_profile_name} --role-name ${role_name} --region ${region}
```
**Launch EC2 Instance with Instance Profile:**
```bash
aws ec2 run-instances --image-id ami-0abcdef1234567890 --count 1 --instance-type t2.micro --iam-instance-profile Name=MyInstanceProfile --region us-west-2
```

#### Placement Group

**Create Placement Group:**
```bash
aws ec2 create-placement-group --group-name ${placement_group_name} --strategy spread --region ${region}
```
- `--group-name ${placement_group_name}`: Specifies the name of the placement group.
- `--strategy spread`: Specifies the placement strategy (spread in this case).
- `--region ${region}`: Specifies the AWS region.

**Placement Group Strategies:**
- **Cluster:** Packs instances close together inside an Availability Zone. This strategy enables workloads to achieve the low-latency network performance necessary for tightly-coupled node-to-node communication.
- **Spread:** Strictly places a small group of instances across distinct underlying hardware to reduce correlated failures.
- **Partition:** Divides each group of instances into logical segments called partitions. Each partition has its own set of racks, and instances in one partition do not share racks with instances in other partitions.

**Cluster Placement Group**
**Use Case:** High-performance computing (HPC) applications, big data workloads, and applications that require high network throughput.
```bash
aws ec2 create-placement-group --group-name my-cluster-group --strategy cluster --region us-west-2
```

**Spread Placement Group**
**Use Case:** Applications that require high availability and need to be isolated from failures, such as critical applications.
```bash
aws ec2 create-placement-group --group-name my-spread-group --strategy spread --region us-west-2
```

**Partition Placement Group**
**Use Case:** Large distributed and replicated workloads, such as Hadoop, Cassandra, and Kafka.
```bash
aws ec2 create-placement-group --group-name my-partition-group --strategy partition --partition-count 3 --region us-west-2
```
This command creates a partition placement group named my-partition-group with 3 partitions in the us-west-2 region.

**Launch Instances in the Partition Placement Group:**
```bash
aws ec2 run-instances --image-id ami-0abcdef1234567890 --count 3 --instance-type t2.micro --placement "GroupName=my-partition-group,PartitionNumber=0" --region us-west-2
aws ec2 run-instances --image-id ami-0abcdef1234567890 --count 3 --instance-type t2.micro --placement "GroupName=my-partition-group,PartitionNumber=1" --region us-west-2
aws ec2 run-instances --image-id ami-0abcdef1234567890 --count 3 --instance-type t2.micro --placement "GroupName=my-partition-group,PartitionNumber=2" --region us-west-2
```

#### S3 Bucket
**Create S3 Bucket:**
```bash
aws s3api create-bucket --bucket ${bucket_name} --region ${region} --create-bucket-configuration LocationConstraint=${region}
```
**Create Sample File:**
```bash
echo "This is a sample file for S3 bucket." > sample_file.txt
```
**Upload Sample File:**
```bash
aws s3 cp sample_file.txt s3://${bucket_name}/sample_file.txt --region ${region}
```
#### RDS
**Create RDS Instance:**
```bash
aws rds create-db-instance \
    --db-instance-identifier ${db_instance_identifier} \
    --db-instance-class ${db_instance_class} \
    --engine ${engine} \
    --master-username ${master_username} \
    --master-user-password ${master_user_password} \
    --allocated-storage 20 \
    --db-name ${db_name} \
    --vpc-security-group-ids ${app_security_group_id} \
    --db-subnet-group-name ${db_subnet_group_name} \
    --multi-az \
    --no-publicly-accessible \
    --region ${region}
```
**Wait for Availability:**
```bash
aws rds wait db-instance-available --db-instance-identifier ${db_instance_identifier} --region ${region}
```
**Get RDS Endpoint:**
```bash
db_endpoint=$(aws rds describe-db-instances --db-instance-identifier ${db_instance_identifier} --query 'DBInstances[0].Endpoint.Address' --output text --region ${region})
echo "RDS instance endpoint: ${db_endpoint}"
```

###### Create DB Subnet Group:
```sh
aws rds create-db-subnet-group \
    --db-subnet-group-name ${db_subnet_group_name} \
    --db-subnet-group-description "My DB Subnet Group" \
    --subnet-ids ${private_subnet_id_1} ${private_subnet_id_2} \
    --region ${region}
```

#### AWS CloudWatch
**Create CloudWatch Alarm:**
```bash
aws cloudwatch put-metric-alarm --alarm-name ${alarm_name} \
    --metric-name CPUUtilization --namespace AWS/EC2 \
    --statistic Average --period 300 --threshold 80 \
    --comparison-operator GreaterThanOrEqualToThreshold \
    --dimensions Name=InstanceId,Value=${instance_ids[0]} \
    --evaluation-periods 2 --alarm-actions ${sns_topic_arn} \
    --region ${region}
```

#### Launch Instances
**User Data Script:**
```bash
cat > userDataCentOsComplex.sh <<EOF
#!/bin/bash
# Install httpd, unzip, and aws-cli
yum update -y
yum install -y httpd unzip aws-cli

# Start httpd service
systemctl start httpd

# Enable httpd service to start on boot
systemctl enable httpd

# Create a sample log file
echo "This is a sample log file." > ./sample_log.txt

# Upload the log file to S3 bucket
bucket_name=$(grep bucket_name ./resource_ids_centos.txt | cut -d'=' -f2)
aws s3 cp ./sample_log.txt s3://${bucket_name}/sample_log.txt

# Download and unzip the website files
cd /var/www/html
wget https://www.tooplate.com/download/2137_barista_cafe -O barista_cafe.zip
EOF
```
**Launch Instances:**
```bash
aws ec2 run-instances \
    --image-id ami-0abcdef1234567890 \
    --count 2 \
    --instance-type t3.micro \
    --key-name ${key_pair_name} \
    --security-group-ids ${app_security_group_id} \
    --subnet-id ${private_subnet_id_2} \
    --user-data file://userDataCentOsComplex.sh \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value='${instance_name}_2'}]' \
    --region ${region} \
    --monitoring "Enabled=false" \
    --iam-instance-profile Name=${instance_profile_name} \
    --block-device-mappings '[{"DeviceName":"/dev/sdh","Ebs":{"VolumeSize":8,"DeleteOnTermination":true}}]' \
    --placement "AvailabilityZone=${available_zone_2},GroupName=${placement_group_name}" \
    --instance-initiated-shutdown-behavior "terminate" \
    --query 'Instances[*].InstanceId' --output text
```
**Wait for Running State:**
```bash
aws ec2 wait instance-running --instance-ids ${instance_ids} --region ${region}
```
**Wait for Status Checks to Pass:**
```bash
aws ec2 wait instance-status-ok --instance-ids ${instance_ids} --region ${region}
```

#### Load Balancers
**Create Load Balancer:**
```bash
load_balancer_arn=$(aws elbv2 create-load-balancer \
    --name my-load-balancer \
    --subnets ${public_subnet_id_1} ${public_subnet_id_2} \
    --security-groups ${app_security_group_id} \
    --query 'LoadBalancers[0].LoadBalancerArn' --output text --region ${region})
```
**Create Target Group:**
```bash
target_group_arn=$(aws elbv2 create-target-group \
    --name my-target-group \
    --protocol HTTP \
    --port 80 \
    --vpc-id ${vpc_id} \
    --query 'TargetGroups[0].TargetGroupArn' --output text --region ${region})
```

#### AutoScaling Group
**Create Launch Template:**
```bash
launch_template_id=$(aws ec2 create-launch-template \
    --launch-template-name ${launch_template_name} \
    --version-description "v1" \
    --launch-template-data '{
        "ImageId": "'${image_id}'",
        "InstanceType": "t3.micro",
        "KeyName": "'${key_pair_name}'",
        "SecurityGroupIds": ["'${app_security_group_id}'"],
        "IamInstanceProfile": {"Name": "'${instance_profile_name}'"},
        "UserData": "'$(base64 -w 0 ./userDataCentOsComplex.sh)'",
        "BlockDeviceMappings": [{
            "DeviceName": "/dev/sdh",
            "Ebs": {
                "VolumeSize": 8,
                "DeleteOnTermination": true
            }
        }]
    }' --query 'LaunchTemplate.LaunchTemplateId' --output text --region ${region})
```
**Create Auto Scaling Group:**
```bash
aws autoscaling create-auto-scaling-group \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --launch-template "LaunchTemplateId=${launch_template_id},Version=1" \
    --min-size ${min_size} \
    --max-size ${max_size} \
    --desired-capacity ${desired_capacity} \
    --vpc-zone-identifier "${subnet_ids}" \
    --region ${region}
```
**Scale Up Policy:**
```bash
scale_up_policy_arn=$(aws autoscaling put-scaling-policy \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --policy-name ScaleUpPolicy \
    --scaling-adjustment 1 \
    --adjustment-type ChangeInCapacity \
    --region ${region} \
    --query 'PolicyARN' --output text)
```
**Scale Down Policy:**
```bash
scale_down_policy_arn=$(aws autoscaling put-scaling-policy \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --policy-name ScaleDownPolicy \
    --scaling-adjustment -1 \
    --adjustment-type ChangeInCapacity \
    --region ${region} \
    --query 'PolicyARN' --output text)
```
**High CPU Utilization Alarm:**
```bash
aws cloudwatch put-metric-alarm \
    --alarm-name HighCPUUtilization \
    --metric-name CPUUtilization \
    --namespace AWS/EC2 \
    --statistic Average \
    --period 300 \
    --threshold 80 \
    --comparison-operator GreaterThanOrEqualToThreshold \
    --dimensions Name=AutoScalingGroupName,Value=${auto_scaling_group_name} \
    --evaluation-periods 2 \
    --alarm-actions ${scale_up_policy_arn} \
    --region ${region}
```
**Low CPU Utilization Alarm:**
```bash
aws cloudwatch put-metric-alarm \
    --alarm-name LowCPUUtilization \
    --metric-name CPUUtilization \
    --namespace AWS/EC2 \
    --statistic Average \
    --period 300 \
    --threshold 20 \
    --comparison-operator LessThanOrEqualToThreshold \
    --dimensions Name=AutoScalingGroupName,Value=${auto_scaling_group_name} \
    --evaluation-periods 2 \
    --alarm-actions ${scale_down_policy_arn} \
    --region ${region}
```