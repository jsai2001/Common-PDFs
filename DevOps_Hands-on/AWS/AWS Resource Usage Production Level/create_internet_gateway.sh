#!/bin/bash

region=$1
igw_id=None
source resource_ids_centos.txt

# Create Internet Gateway if it doesn't exist
# igw_id=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${vpc_id}" --query 'InternetGateways[0].InternetGatewayId' --output text --region ${region})
if [ "$igw_id" == "None" ]; then
    igw_id=$(aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text --region ${region})
    aws ec2 attach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ${region}
    echo "Created and attached Internet Gateway with ID ${igw_id}"
else
    echo "Internet Gateway already exists with ID ${igw_id}"
fi

echo "igw_id=${igw_id}" >> resource_ids_centos.txt