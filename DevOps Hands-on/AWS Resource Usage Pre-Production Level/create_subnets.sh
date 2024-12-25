#!/bin/bash

region=$1
public_subnet_id=None
private_subnet_id=None
source resource_ids_centos.txt

# Create Public Subnet if it doesn't exist
public_subnet_cidr="10.0.1.0/24"
# public_subnet_id=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$public_subnet_id" == "None" ]; then
    public_subnet_id=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr} --availability-zone ${available_zone} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Public Subnet with ID ${public_subnet_id}"
else
    echo "Public Subnet with CIDR ${public_subnet_cidr} already exists with ID ${public_subnet_id}"
fi

# Create Private Subnet if it doesn't exist
private_subnet_cidr="10.0.2.0/24"
# private_subnet_id=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${private_subnet_cidr}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$private_subnet_id" == "None" ]; then
    private_subnet_id=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${private_subnet_cidr} --availability-zone ${available_zone} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Private Subnet with ID ${private_subnet_id}"
else
    echo "Private Subnet with CIDR ${private_subnet_cidr} already exists with ID ${private_subnet_id}"
fi

echo "public_subnet_id=${public_subnet_id}" >> resource_ids_centos.txt
echo "private_subnet_id=${private_subnet_id}" >> resource_ids_centos.txt