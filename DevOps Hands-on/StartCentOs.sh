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
key_pair_name="Centos-Key-Pair-Auto"
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
    aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosKeyPair.pem
    # Set permissions for the key pair file
    chmod 400 CentosKeyPair.pem
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
security_group_name="CentosSecurityGroup"
security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$security_group_id" == "None" ]; then
    security_group_id=$(aws ec2 create-security-group --group-name ${security_group_name} --description "Centos security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
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

# Create User Data Script
cat > userDataCentOs.sh <<EOF
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
instance_name="CentOSInstance"

# Launch EC2 Instance
instance_id=$(aws ec2 run-instances \
    --image-id ${image_id} \
    --count 1 \
    --instance-type t2.micro \
    --key-name ${key_pair_name} \
    --security-group-ids ${security_group_id} \
    --subnet-id ${subnet_id} \
    --associate-public-ip-address \
    --user-data file://userDataCentOs.sh \
    --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value='${instance_name}'}]' \
    --region ${region} \
    --query 'Instances[0].InstanceId' --output text)

# Store resource IDs
echo "KeyPairName=${key_pair_name}" > resource_ids_centos.txt
echo "VpcId=${vpc_id}" >> resource_ids_centos.txt
echo "SubnetId=${subnet_id}" >> resource_ids_centos.txt
echo "SecurityGroupId=${security_group_id}" >> resource_ids_centos.txt
echo "InstanceId=${instance_id}" >> resource_ids_centos.txt
echo "IgwId=${igw_id}" >> resource_ids_centos.txt
echo "RouteTableId=${route_table_id}" >> resource_ids_centos.txt

# Wait for the instance to be in running state
aws ec2 wait instance-running --instance-ids ${instance_id} --region ${region}

# Wait for the instance status checks to pass
aws ec2 wait instance-status-ok --instance-ids ${instance_id} --region ${region}

# Retrieve and print the public IP address
public_ip=$(aws ec2 describe-instances --instance-ids ${instance_id} --query 'Reservations[0].Instances[0].PublicIpAddress' --output text --region ${region})
echo "Instance with ID ${instance_id} has been created with Public IP: http://${public_ip}"