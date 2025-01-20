#!/bin/bash

source config.sh
source resource_ids_centos.txt

# Create Subnet if it doesn't exist
subnet_id=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${subnet_cidr}" --query 'Subnets[0].SubnetId' --output text --region ${region})
if [ "$subnet_id" == "None" ]; then
    subnet_id=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${subnet_cidr} --availability-zone ${available_zone} --query 'Subnet.SubnetId' --output text --region ${region})
    echo "Created Subnet with ID ${subnet_id}"
else
    echo "Subnet with CIDR ${subnet_cidr} already exists with ID ${subnet_id}"
fi

echo "SubnetId=${subnet_id}" >> resource_ids_centos.txt