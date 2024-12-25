#!/bin/bash

region=$1
public_route_table_id=None
private_route_table_id=None
source resource_ids_centos.txt

# Create Route Table for Public Subnet if it doesn't exist
# public_route_table_id=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$public_route_table_id" == "None" ]; then
    public_route_table_id=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${public_route_table_id} --subnet-id ${public_subnet_id} --region ${region}
    aws ec2 create-route --route-table-id ${public_route_table_id} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
    echo "Created Route Table for Public Subnet with ID ${public_route_table_id}"
else
    echo "Route Table for Public Subnet already exists with ID ${public_route_table_id}"
fi

# Create Route Table for Private Subnet if it doesn't exist
# private_route_table_id=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${private_subnet_id}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$private_route_table_id" == "None" ]; then
    private_route_table_id=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${private_route_table_id} --subnet-id ${private_subnet_id} --region ${region}
    echo "Created Route Table for Private Subnet with ID ${private_route_table_id}"
fi

echo "public_route_table_id=${public_route_table_id}" >> resource_ids_centos.txt
echo "private_route_table_id=${private_route_table_id}" >> resource_ids_centos.txt