#!/bin/bash

region=$1
public_subnet_id_1=None
private_subnet_id_1=None
public_subnet_id_2=None
private_subnet_id_2=None
source resource_ids_centos.txt

# Create Public Subnet 1 if it doesn't exist
public_subnet_cidr_1="10.0.1.0/24"
# public_subnet_id_1=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_1}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$public_subnet_id_1" == "None" ]; then
    public_subnet_id_1=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_1} --availability-zone ${available_zone_1} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Public Subnet 1 with ID ${public_subnet_id_1}"
else
    echo "Public Subnet 1 with CIDR ${public_subnet_cidr_1} already exists with ID ${public_subnet_id_1}"
fi

# Create Public Subnet 2 if it doesn't exist
public_subnet_cidr_2="10.0.2.0/24"
# public_subnet_id_2=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_2}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$public_subnet_id_2" == "None" ]; then
    public_subnet_id_2=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_2} --availability-zone ${available_zone_2} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Public Subnet 2 with ID ${public_subnet_id_2}"
else
    echo "Public Subnet 2 with CIDR ${public_subnet_cidr_2} already exists with ID ${public_subnet_id_2}"
fi

# Create Private Subnet 1 if it doesn't exist
private_subnet_cidr_1="10.0.3.0/24"
# private_subnet_id_1=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${private_subnet_cidr_1}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$private_subnet_id_1" == "None" ]; then
    private_subnet_id_1=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${private_subnet_cidr_1} --availability-zone ${available_zone_1} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Private Subnet 1 with ID ${private_subnet_id_1}"
else
    echo "Private Subnet 1 with CIDR ${private_subnet_cidr_1} already exists with ID ${private_subnet_id_1}"
fi

# Create Private Subnet 2 if it doesn't exist
private_subnet_cidr_2="10.0.4.0/24"
# private_subnet_id_2=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${private_subnet_cidr_2}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$private_subnet_id_2" == "None" ]; then
    private_subnet_id_2=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${private_subnet_cidr_2} --availability-zone ${available_zone_2} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Private Subnet 2 with ID ${private_subnet_id_2}"
else
    echo "Private Subnet 2 with CIDR ${private_subnet_cidr_2} already exists with ID ${private_subnet_id_2}"
fi

echo "public_subnet_id_1=${public_subnet_id_1}" >> resource_ids_centos.txt
echo "public_subnet_id_2=${public_subnet_id_2}" >> resource_ids_centos.txt
echo "private_subnet_id_1=${private_subnet_id_1}" >> resource_ids_centos.txt
echo "private_subnet_id_2=${private_subnet_id_2}" >> resource_ids_centos.txt