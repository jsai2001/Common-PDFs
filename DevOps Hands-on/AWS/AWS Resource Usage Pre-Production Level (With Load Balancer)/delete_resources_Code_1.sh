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