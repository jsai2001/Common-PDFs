#!/bin/bash

region=$1
source resource_ids_centos.txt

# Create NAT Gateway
eip_allocation_id=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
nat_gateway_id=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id} --allocation-id ${eip_allocation_id} --query 'NatGateway.NatGatewayId' --output text --region ${region})
echo "Created NAT Gateway with ID ${nat_gateway_id}"

# Wait for NAT Gateway to become available
echo "Waiting for NAT Gateway to become available..."
sleep 20

# Update Private Route Table to use NAT Gateway
aws ec2 create-route --route-table-id ${private_route_table_id} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id} --region ${region}
echo "Updated Private Route Table to use NAT Gateway"

echo "nat_gateway_id=${nat_gateway_id}" >> resource_ids_centos.txt
echo "eip_allocation_id=${eip_allocation_id}" >> resource_ids_centos.txt