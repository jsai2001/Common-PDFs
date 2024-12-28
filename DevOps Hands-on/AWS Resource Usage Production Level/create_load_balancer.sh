#!/bin/bash

region=$1
source resource_ids_centos.txt

# Create Load Balancer
load_balancer_arn=$(aws elbv2 create-load-balancer \
    --name my-load-balancer \
    --subnets ${public_subnet_id_1} ${public_subnet_id_2} \
    --security-groups ${app_security_group_id} \
    --query 'LoadBalancers[0].LoadBalancerArn' --output text --region ${region})

if [ -z "$load_balancer_arn" ]; then
    echo "Failed to create Load Balancer"
    exit 1
fi

echo "Created Load Balancer with ARN ${load_balancer_arn}"

# Create Target Group
target_group_arn=$(aws elbv2 create-target-group \
    --name my-target-group \
    --protocol HTTP \
    --port 80 \
    --vpc-id ${vpc_id} \
    --query 'TargetGroups[0].TargetGroupArn' --output text --region ${region})

if [ -z "$target_group_arn" ]; then
    echo "Failed to create Target Group"
    exit 1
fi

echo "Created Target Group with ARN ${target_group_arn}"

# Register Targets
aws elbv2 register-targets --target-group-arn ${target_group_arn} --targets Id=${instance_ids} --region ${region}
echo "Registered targets with Target Group"

# Create Listener
aws elbv2 create-listener \
    --load-balancer-arn ${load_balancer_arn} \
    --protocol HTTP \
    --port 80 \
    --default-actions Type=forward,TargetGroupArn=${target_group_arn} --region ${region}
echo "Created Listener for Load Balancer"

# Store Load Balancer and Target Group ARNs
echo "load_balancer_arn=${load_balancer_arn}" >> resource_ids_centos.txt
echo "target_group_arn=${target_group_arn}" >> resource_ids_centos.txt

sleep 30

# Retrieve and print the DNS name of the load balancer
load_balancer_dns=$(aws elbv2 describe-load-balancers --load-balancer-arns ${load_balancer_arn} --query 'LoadBalancers[0].DNSName' --output text --region ${region})
echo "Access your application at: http://${load_balancer_dns}"