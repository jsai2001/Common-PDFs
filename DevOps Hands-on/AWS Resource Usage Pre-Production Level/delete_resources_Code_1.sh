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

# Terminate EC2 instances if InstanceIds is not empty
for instance_id in "${instance_ids[@]}"; do
    if [ -n "${instance_id}" ]; then
        echo "Processing instance ID: $instance_id"
        delete_resource "EC2 Instance ${instance_id}" "aws ec2 terminate-instances --instance-ids ${instance_id} --region ap-south-1"
        aws ec2 wait instance-terminated --instance-ids ${instance_id} --region ap-south-1
        sleep 10
    else
        echo "No EC2 instances to terminate."
    fi
done

# Delete the key pair
delete_resource "Key Pair ${key_pair_name}" "aws ec2 delete-key-pair --key-name ${key_pair_name} --region ap-south-1"
rm -f CentosComplexKeyPair.pem

sleep 10

# Delete the IAM role and instance profile
aws iam remove-role-from-instance-profile --instance-profile-name ${instance_profile_name} --role-name ${role_name}
aws iam delete-instance-profile --instance-profile-name ${instance_profile_name}
aws iam detach-role-policy --role-name ${role_name} --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
delete_resource "IAM Role ${role_name}" "aws iam delete-role --role-name ${role_name}"

sleep 10

# Delete the placement group
delete_resource "Placement Group ${placement_group_name}" "aws ec2 delete-placement-group --group-name ${placement_group_name} --region ap-south-1"

sleep 10

# Delete the NAT gateway
delete_resource "NAT Gateway ${nat_gateway_id}" "aws ec2 delete-nat-gateway --nat-gateway-id ${nat_gateway_id} --region ap-south-1"

sleep 10

# Release the Elastic IP address
# delete_resource "Elastic IP ${eip_allocation_id}" "aws ec2 release-address --allocation-id ${eip_allocation_id} --region ap-south-1"

# sleep 10

# Detach and delete the internet gateway
aws ec2 detach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ap-south-1
delete_resource "Internet Gateway ${igw_id}" "aws ec2 delete-internet-gateway --internet-gateway-id ${igw_id} --region ap-south-1"

sleep 10

# Delete the private subnet
delete_resource "Private Subnet ${private_subnet_id}" "aws ec2 delete-subnet --subnet-id ${private_subnet_id} --region ap-south-1"

sleep 10

# Delete the public subnet
delete_resource "Public Subnet ${public_subnet_id}" "aws ec2 delete-subnet --subnet-id ${public_subnet_id} --region ap-south-1"

sleep 10

# Delete the public route table
delete_resource "Public Route Table ${public_route_table_id}" "aws ec2 delete-route-table --route-table-id ${public_route_table_id} --region ap-south-1"

sleep 10

# Delete the private route table
delete_resource "Private Route Table ${private_route_table_id}" "aws ec2 delete-route-table --route-table-id ${private_route_table_id} --region ap-south-1"

sleep 10

# Delete the security group
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