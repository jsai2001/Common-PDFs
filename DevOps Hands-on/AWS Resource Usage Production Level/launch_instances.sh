#!/bin/bash

region=$1
source resource_ids_centos.txt

set -e

# ln -s ./resource_ids_centos.txt /c/Users/Dell/Common-PDFs/resource_ids_centos.txt

# Create User Data Script
cat > userDataCentOsComplex.sh <<EOF
#!/bin/bash
# Install httpd and aws-cli
yum update -y
yum install -y httpd aws-cli

# Start httpd service
systemctl start httpd

# Enable httpd service to start on boot
systemctl enable httpd

# Create a sample log file
echo "This is a sample log file." > ./sample_log.txt

# Upload the log file to S3 bucket
bucket_name=$(grep bucket_name ./resource_ids_centos.txt | cut -d'=' -f2)
aws s3 cp ./sample_log.txt s3://${bucket_name}/sample_log.txt
EOF

image_id=ami-0fd05997b4dff7aac
instance_name="CentOSComplexInstance"

echo "image_id=${image_id}" >> resource_ids_centos.txt

# Launch EC2 Instances in Private Subnet 1
instance_count_1=1  # Number of instances to launch in subnet 1
instance_ids_1=$(aws ec2 run-instances \
    --image-id ${image_id} \
    --count ${instance_count_1} \
    --instance-type t2.micro \
    --key-name ${key_pair_name} \
    --security-group-ids ${app_security_group_id} \
    --subnet-id ${private_subnet_id_1} \
    --user-data file://userDataCentOsComplex.sh \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value='${instance_name}_1'}]' \
    --region ${region} \
    --monitoring "Enabled=false" \
    --iam-instance-profile Name=${instance_profile_name} \
    --block-device-mappings '[{"DeviceName":"/dev/sdh","Ebs":{"VolumeSize":8,"DeleteOnTermination":true}}]' \
    --placement "AvailabilityZone=${available_zone_1},GroupName=${placement_group_name}" \
    --instance-initiated-shutdown-behavior "terminate" \
    --query 'Instances[*].InstanceId' --output text)

# Launch EC2 Instances in Private Subnet 2
instance_count_2=1  # Number of instances to launch in subnet 2
instance_ids_2=$(aws ec2 run-instances \
    --image-id ${image_id} \
    --count ${instance_count_2} \
    --instance-type t2.micro \
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
    --query 'Instances[*].InstanceId' --output text)

# Combine instance IDs
instance_ids="${instance_ids_1} ${instance_ids_2}"

# Store instance IDs
echo "instance_ids=(${instance_ids})" >> resource_ids_centos.txt

# Wait for the instances to be in running state
aws ec2 wait instance-running --instance-ids ${instance_ids} --region ${region}

# Wait for the instance status checks to pass
aws ec2 wait instance-status-ok --instance-ids ${instance_ids} --region ${region}

echo "Launched EC2 instances in private subnets with IDs: ${instance_ids}"