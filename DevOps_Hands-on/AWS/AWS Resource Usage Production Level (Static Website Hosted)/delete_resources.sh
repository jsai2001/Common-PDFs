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

# Delete the load balancer listeners
listener_arns=$(aws elbv2 describe-listeners --load-balancer-arn ${load_balancer_arn} --query 'Listeners[*].ListenerArn' --output text --region ${region})
for listener_arn in ${listener_arns}; do
    delete_resource "Listener ${listener_arn}" "aws elbv2 delete-listener --listener-arn ${listener_arn} --region ${region}"
done

sleep 10

# Delete the load balancer
delete_resource "Load Balancer ${load_balancer_arn}" "aws elbv2 delete-load-balancer --load-balancer-arn ${load_balancer_arn} --region ap-south-1"

sleep 10

# Delete the target group
delete_resource "Target Group ${target_group_arn}" "aws elbv2 delete-target-group --target-group-arn ${target_group_arn} --region ap-south-1"

sleep 10

# Delete the NAT gateways
delete_resource "NAT Gateway 1 ${nat_gateway_id_1}" "aws ec2 delete-nat-gateway --nat-gateway-id ${nat_gateway_id_1} --region ap-south-1"
delete_resource "NAT Gateway 2 ${nat_gateway_id_2}" "aws ec2 delete-nat-gateway --nat-gateway-id ${nat_gateway_id_2} --region ap-south-1"

sleep 10

# Detach and delete the internet gateway
aws ec2 detach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ap-south-1
delete_resource "Internet Gateway ${igw_id}" "aws ec2 delete-internet-gateway --internet-gateway-id ${igw_id} --region ap-south-1"

sleep 10

# Release the Elastic IP addresses
delete_resource "Elastic IP 1 ${eip_allocation_id_1}" "aws ec2 release-address --allocation-id ${eip_allocation_id_1} --region ap-south-1"
delete_resource "Elastic IP 2 ${eip_allocation_id_2}" "aws ec2 release-address --allocation-id ${eip_allocation_id_2} --region ap-south-1"

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