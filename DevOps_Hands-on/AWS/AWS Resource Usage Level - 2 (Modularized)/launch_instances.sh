#!/bin/bash

source config.sh
source resource_ids_centos.txt

# Create User Data Script
cat > userDataCentOsComplex.sh <<EOF
#!/bin/bash
# Install httpd
yum update -y
yum install -y httpd

# Start httpd service
systemctl start httpd

# Enable httpd service to start on boot
systemctl enable httpd
EOF

# Launch EC2 Instances
instance_ids=$(aws ec2 run-instances \
    --image-id ${image_id} \
    --count ${instance_count} \
    --instance-type t2.micro \
    --key-name ${key_pair_name} \
    --security-group-ids ${security_group_id} \
    --subnet-id ${subnet_id} \
    --associate-public-ip-address \
    --user-data file://userDataCentOsComplex.sh \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value='${instance_name}'}]' \
    --region ${region} \
    --monitoring "Enabled=false" \
    --iam-instance-profile Name=${instance_profile_name} \
    --block-device-mappings '[{"DeviceName":"/dev/sdh","Ebs":{"VolumeSize":8,"DeleteOnTermination":true}}]' \
    --placement "GroupName=${placement_group_name}" \
    --instance-initiated-shutdown-behavior "terminate" \
    --query 'Instances[*].InstanceId' --output text)

# Store instance IDs
echo "InstanceIds=${instance_ids}" >> resource_ids_centos.txt

# Wait for the instances to be in running state
aws ec2 wait instance-running --instance-ids ${instance_ids} --region ${region}

# Wait for the instance status checks to pass
aws ec2 wait instance-status-ok --instance-ids ${instance_ids} --region ${region}

# Retrieve and print the public IP addresses
public_ips=$(aws ec2 describe-instances --instance-ids ${instance_ids} --query 'Reservations[*].Instances[*].PublicIpAddress' --output text --region ${region})
# Loop through each public IP and print the HTTP URL
for public_ip in ${public_ips}; do
    echo "http://${public_ip}"
done