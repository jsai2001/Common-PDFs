#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status.

# Load resource IDs from the file
source resource_ids_centos.txt

# Helper function to check and delete resources
delete_resource() {
    local resource_name=$1
    local delete_command=$2
    echo "Attempting to delete ${resource_name}..."
    if ${delete_command}; then
        echo "${resource_name} deleted successfully."
    else
        echo "Error deleting ${resource_name}. Please check manually."
        exit 1
    fi
}

# Detach and delete the internet gateway
aws ec2 detach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ap-south-1
delete_resource "Internet Gateway ${igw_id}" "aws ec2 delete-internet-gateway --internet-gateway-id ${igw_id} --region ap-south-1"

sleep 10

# Delete the private subnets
delete_resource "Private Subnet 1 ${private_subnet_id_1}" "aws ec2 delete-subnet --subnet-id ${private_subnet_id_1} --region ap-south-1"
delete_resource "Private Subnet 2 ${private_subnet_id_2}" "aws ec2 delete-subnet --subnet-id ${private_subnet_id_2} --region ap-south-1"

sleep 10

# Delete the public subnets
delete_resource "Public Subnet 1 ${public_subnet_id_1}" "aws ec2 delete-subnet --subnet-id ${public_subnet_id_1} --region ap-south-1"
delete_resource "Public Subnet 2 ${public_subnet_id_2}" "aws ec2 delete-subnet --subnet-id ${public_subnet_id_2} --region ap-south-1"

sleep 10

# Delete the public route tables
delete_resource "Public Route Table 1 ${public_route_table_id_1}" "aws ec2 delete-route-table --route-table-id ${public_route_table_id_1} --region ap-south-1"
delete_resource "Public Route Table 2 ${public_route_table_id_2}" "aws ec2 delete-route-table --route-table-id ${public_route_table_id_2} --region ap-south-1"

sleep 10

# Delete the private route tables
delete_resource "Private Route Table 1 ${private_route_table_id_1}" "aws ec2 delete-route-table --route-table-id ${private_route_table_id_1} --region ap-south-1"
delete_resource "Private Route Table 2 ${private_route_table_id_2}" "aws ec2 delete-route-table --route-table-id ${private_route_table_id_2} --region ap-south-1"

sleep 10

# Delete the security groups
delete_resource "Application Security Group ${app_security_group_id}" "aws ec2 delete-security-group --group-id ${app_security_group_id} --region ap-south-1"
delete_resource "Bastion Security Group ${bastion_security_group_id}" "aws ec2 delete-security-group --group-id ${bastion_security_group_id} --region ap-south-1"

sleep 10

# Delete the VPC
delete_resource "VPC ${vpc_id}" "aws ec2 delete-vpc --vpc-id ${vpc_id} --region ap-south-1"

sleep 10

# Cleanup local files
rm -f resource_ids_centos.txt trust-policy.json userDataCentOsComplex.sh
echo "Removed all local resource files."

echo "All resources and dependencies have been deleted successfully."