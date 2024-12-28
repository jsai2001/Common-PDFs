# Steps to create a CentOS Instance with all necessary dependencies and launch the httpd service and access that through our browser

#Region under Availability Zone
region="ap-south-1"

# Check availability of t2.micro instance type in each availability zone
available_zone=$(aws ec2 describe-instance-type-offerings --location-type availability-zone --filters "Name=instance-type,Values=t2.micro" "Name=location,Values=ap-south-1a,ap-south-1b,ap-south-1c" --region ${region} --query 'InstanceTypeOfferings[0].Location' --output text)

if [ -z "$available_zone" ]; then
    echo "No availability zone found for t2.micro instance type in region ${region}"
    exit 1
fi

echo "Selected availability zone: ${available_zone}"

# Create Key Pair if it doesn't exist
key_pair_name="Centos-Complex-Key-Pair-Auto"
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
    aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosComplexKeyPair.pem
    # Set permissions for the key pair file
    chmod 400 CentosComplexKeyPair.pem
else
    echo "Key pair ${key_pair_name} already exists."
fi

# Create VPC if it doesn't exist
vpc_cidr="10.0.0.0/16"
vpc_id=$(aws ec2 describe-vpcs --filters "Name=cidr,Values=${vpc_cidr}" --query 'Vpcs[0].VpcId' --output text --region ${region})
if [ "$vpc_id" == "None" ]; then
    vpc_id=$(aws ec2 create-vpc --cidr-block ${vpc_cidr} --query 'Vpc.VpcId' --output text --region ${region})
    echo "Created VPC with ID ${vpc_id}"
else
    echo "VPC with CIDR ${vpc_cidr} already exists with ID ${vpc_id}"
fi

# Create Internet Gateway if it doesn't exist
igw_id=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${vpc_id}" --query 'InternetGateways[0].InternetGatewayId' --output text --region ${region})
if [ "$igw_id" == "None" ]; then
    igw_id=$(aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text --region ${region})
    aws ec2 attach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ${region}
    echo "Created and attached Internet Gateway with ID ${igw_id}"
else
    echo "Internet Gateway already exists with ID ${igw_id}"
fi

# Create Subnet if it doesn't exist
subnet_cidr="10.0.1.0/24"
subnet_id=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${subnet_cidr}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$subnet_id" == "None" ]; then
    subnet_id=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${subnet_cidr} --availability-zone ${available_zone} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Subnet with ID ${subnet_id}"
else
    echo "Subnet with CIDR ${subnet_cidr} already exists with ID ${subnet_id}"
fi

# Create Route Table if it doesn't exist
route_table_id=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$route_table_id" == "None" ]; then
    route_table_id=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    echo "Created Route Table with ID ${route_table_id}"
else
    echo "Route Table already exists with ID ${route_table_id}"
fi

# Associate Route Table with Subnet
aws ec2 associate-route-table --route-table-id ${route_table_id} --subnet-id ${subnet_id} --region ${region}

# Create Route to Internet Gateway
aws ec2 create-route --route-table-id ${route_table_id} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}

# Create Security Group if it doesn't exist
security_group_name="CentosComplexSecurityGroup"
security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$security_group_id" == "None" ]; then
    security_group_id=$(aws ec2 create-security-group --group-name ${security_group_name} --description "Centos Complex security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
    echo "Created Security Group with ID ${security_group_id}"

    # Add inbound rules to the security group
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 22 --cidr 0.0.0.0/0 --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 80 --cidr 0.0.0.0/0 --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 443 --cidr 0.0.0.0/0 --region ${region}

    # Add outbound rules to the security group (all requests allowed to outside)
    # aws ec2 authorize-security-group-egress --group-id ${security_group_id} --protocol -1 --port all --cidr 0.0.0.0/0 --region ${region}
else
    echo "Security Group ${security_group_name} already exists with ID ${security_group_id}"
fi

# Create IAM Role and Instance Profile
role_name="CentOsRole"
instance_profile_name="CentOsProfile"
policy_arn="arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"

# Create trust policy JSON file
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
    }
  ]
}
EOF

# Create IAM Role
aws iam create-role --role-name ${role_name} --assume-role-policy-document file://trust-policy.json --region ${region}
aws iam attach-role-policy --role-name ${role_name} --policy-arn ${policy_arn} --region ${region}

# Create Instance Profile
aws iam create-instance-profile --instance-profile-name ${instance_profile_name} --region ${region}
aws iam add-role-to-instance-profile --instance-profile-name ${instance_profile_name} --role-name ${role_name} --region ${region}

# Retrieve Instance Profile ARN
instance_profile_arn=$(aws iam get-instance-profile --instance-profile-name ${instance_profile_name} --query 'InstanceProfile.Arn' --output text --region ${region})
echo "Instance Profile ARN: ${instance_profile_arn}"

# Create Placement Group if it doesn't exist
placement_group_name="centos-instances-placement-group"
aws ec2 create-placement-group --group-name ${placement_group_name} --strategy spread --region ${region}
echo "Created Placement Group with name ${placement_group_name}"

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

image_id=ami-0fd05997b4dff7aac
instance_name="CentOSComplexInstance"

# Launch EC2 Instances
instance_count=2  # Number of instances to launch
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
    --placement "AvailabilityZone=${available_zone},GroupName=${placement_group_name}" \
    --instance-initiated-shutdown-behavior "terminate" \
    --query 'Instances[*].InstanceId' --output text)

# --tenancy "default" \ attribute needed for defining the place to launch the instance , either on shared hardware or dedicated hardware

# Store resource IDs
echo "KeyPairName=${key_pair_name}" > resource_ids_centos.txt
echo "VpcId=${vpc_id}" >> resource_ids_centos.txt
echo "SubnetId=${subnet_id}" >> resource_ids_centos.txt
echo "SecurityGroupId=${security_group_id}" >> resource_ids_centos.txt
echo "InstanceIds=(${instance_ids})" >> resource_ids_centos.txt
echo "IgwId=${igw_id}" >> resource_ids_centos.txt
echo "RouteTableId=${route_table_id}" >> resource_ids_centos.txt
echo "RoleName=${role_name}" >> resource_ids_centos.txt
echo "InstanceProfileName=${instance_profile_name}" >> resource_ids_centos.txt
echo "PlacementGroupName=${placement_group_name}" >> resource_ids_centos.txt

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