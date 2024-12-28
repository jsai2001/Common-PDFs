#!/bin/bash

region=$1
vpc_cidr="10.0.0.0/16"

# Create VPC if it doesn't exist
vpc_id=None
# vpc_id=$(aws ec2 describe-vpcs --filters "Name=cidr,Values=${vpc_cidr}" --query 'Vpcs[0].VpcId' --output text --region ${region})
if [ "$vpc_id" == "None" ]; then
    vpc_id=$(aws ec2 create-vpc --cidr-block ${vpc_cidr} --query 'Vpc.VpcId' --output text --region ${region})
    echo "Created VPC with ID ${vpc_id}"
else
    echo "VPC with CIDR ${vpc_cidr} already exists with ID ${vpc_id}"
fi

echo "vpc_id=${vpc_id}" >> resource_ids_centos.txt