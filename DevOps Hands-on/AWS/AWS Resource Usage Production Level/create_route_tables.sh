#!/bin/bash

region=$1
public_route_table_id_1=None
public_route_table_id_2=None
private_route_table_id_1=None
private_route_table_id_2=None
source resource_ids_centos.txt

# Create Route Table for Public Subnet 1 if it doesn't exist
# public_route_table_id_1=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_1}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$public_route_table_id_1" == "None" ]; then
    public_route_table_id_1=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${public_route_table_id_1} --subnet-id ${public_subnet_id_1} --region ${region}
    aws ec2 create-route --route-table-id ${public_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
    echo "Created Route Table for Public Subnet 1 with ID ${public_route_table_id_1}"
else
    echo "Route Table for Public Subnet 1 already exists with ID ${public_route_table_id_1}"
fi

# Create Route Table for Public Subnet 2 if it doesn't exist
# public_route_table_id_2=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_2}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$public_route_table_id_2" == "None" ]; then
    public_route_table_id_2=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${public_route_table_id_2} --subnet-id ${public_subnet_id_2} --region ${region}
    aws ec2 create-route --route-table-id ${public_route_table_id_2} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
    echo "Created Route Table for Public Subnet 2 with ID ${public_route_table_id_2}"
else
    echo "Route Table for Public Subnet 2 already exists with ID ${public_route_table_id_2}"
fi

# Create Route Table for Private Subnet 1 if it doesn't exist
# private_route_table_id_1=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${private_subnet_id_1}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$private_route_table_id_1" == "None" ]; then
    private_route_table_id_1=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${private_route_table_id_1} --subnet-id ${private_subnet_id_1} --region ${region}
    echo "Created Route Table for Private Subnet 1 with ID ${private_route_table_id_1}"
else
    echo "Route Table for Private Subnet 1 already exists with ID ${private_route_table_id_1}"
fi

# Create Route Table for Private Subnet 2 if it doesn't exist
# private_route_table_id_2=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${private_subnet_id_2}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
if [ "$private_route_table_id_2" == "None" ]; then
    private_route_table_id_2=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
    aws ec2 associate-route-table --route-table-id ${private_route_table_id_2} --subnet-id ${private_subnet_id_2} --region ${region}
    echo "Created Route Table for Private Subnet 2 with ID ${private_route_table_id_2}"
else
    echo "Route Table for Private Subnet 2 already exists with ID ${private_route_table_id_2}"
fi

echo "public_route_table_id_1=${public_route_table_id_1}" >> resource_ids_centos.txt
echo "public_route_table_id_2=${public_route_table_id_2}" >> resource_ids_centos.txt
echo "private_route_table_id_1=${private_route_table_id_1}" >> resource_ids_centos.txt
echo "private_route_table_id_2=${private_route_table_id_2}" >> resource_ids_centos.txt