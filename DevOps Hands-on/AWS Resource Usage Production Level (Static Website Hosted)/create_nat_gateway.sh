#!/bin/bash

region=$1
source resource_ids_centos.txt

# Create NAT Gateway for Public Subnet 1
eip_allocation_id_1=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
nat_gateway_id_1=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_1} --allocation-id ${eip_allocation_id_1} --query 'NatGateway.NatGatewayId' --output text --region ${region})
echo "Created NAT Gateway 1 with ID ${nat_gateway_id_1}"

# Wait for NAT Gateway 1 to become available
echo "Waiting for NAT Gateway 1 to become available..."
sleep 20

# Create NAT Gateway for Public Subnet 2
eip_allocation_id_2=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
nat_gateway_id_2=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_2} --allocation-id ${eip_allocation_id_2} --query 'NatGateway.NatGatewayId' --output text --region ${region})
echo "Created NAT Gateway 2 with ID ${nat_gateway_id_2}"

# Wait for NAT Gateway 2 to become available
echo "Waiting for NAT Gateway 2 to become available..."
sleep 20

# Update Private Route Table 1 to use NAT Gateway 1
aws ec2 create-route --route-table-id ${private_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_1} --region ${region}
echo "Updated Private Route Table 1 to use NAT Gateway 1"

# Update Private Route Table 2 to use NAT Gateway 2
aws ec2 create-route --route-table-id ${private_route_table_id_2} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_2} --region ${region}
echo "Updated Private Route Table 2 to use NAT Gateway 2"

echo "nat_gateway_id_1=${nat_gateway_id_1}" >> resource_ids_centos.txt
echo "eip_allocation_id_1=${eip_allocation_id_1}" >> resource_ids_centos.txt
echo "nat_gateway_id_2=${nat_gateway_id_2}" >> resource_ids_centos.txt
echo "eip_allocation_id_2=${eip_allocation_id_2}" >> resource_ids_centos.txt