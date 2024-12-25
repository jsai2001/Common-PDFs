#!/bin/bash

source config.sh
source resource_ids_centos.txt

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

echo "RouteTableId=${route_table_id}" >> resource_ids_centos.txt