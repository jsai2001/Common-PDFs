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

# Delete Auto Scaling Group
aws autoscaling update-auto-scaling-group --auto-scaling-group-name MyAutoScalingGroup --min-size 0 --max-size 0 --desired-capacity 0 --region ${region}
sleep 60
aws autoscaling delete-auto-scaling-group --auto-scaling-group-name MyAutoScalingGroup --force-delete --region ${region}
aws ec2 delete-launch-template --launch-template-name MyLaunchTemplate --region ${region}
echo "Deleted Auto Scaling Group and Launch Template."
sleep 30

# Delete Load Balancer
load_balancer_arn=$(aws elbv2 describe-load-balancers --names my-load-balancer --query 'LoadBalancers[0].LoadBalancerArn' --output text --region ${region})
aws elbv2 delete-load-balancer --load-balancer-arn ${load_balancer_arn} --region ${region}
sleep 60
echo "Deleted Load Balancer."

# Delete EC2 Instances
# instance_ids=$(aws ec2 describe-instances --filters "Name=tag:Name,Values=CentOSComplexInstance*" --query 'Reservations[*].Instances[*].InstanceId' --output text --region ${region})
# aws ec2 terminate-instances --instance-ids ${instance_ids} --region ${region}
# aws ec2 wait instance-terminated --instance-ids ${instance_ids} --region ${region}
# echo "Deleted EC2 Instances."
# sleep 30

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

# Delete CloudWatch Alarms
aws cloudwatch delete-alarms --alarm-names HighCPUUtilization LowCPUUtilization --region ${region}
echo "Deleted CloudWatch Alarms."
sleep 10

# Delete RDS Instance
aws rds delete-db-instance --db-instance-identifier mydbinstance --skip-final-snapshot --region ${region}
aws rds wait db-instance-deleted --db-instance-identifier mydbinstance --region ${region}
echo "Deleted RDS Instance."
sleep 60

# Delete S3 Bucket
bucket_name=$(grep bucket_name resource_ids_centos.txt | cut -d'=' -f2)
aws s3 rb s3://${bucket_name} --force --region ${region}
echo "Deleted S3 Bucket."
sleep 10

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
# listener_arns=$(aws elbv2 describe-listeners --load-balancer-arn ${load_balancer_arn} --query 'Listeners[*].ListenerArn' --output text --region ${region})
# for listener_arn in ${listener_arns}; do
#     delete_resource "Listener ${listener_arn}" "aws elbv2 delete-listener --listener-arn ${listener_arn} --region ${region}"
# done

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