#!/bin/bash

region=$1
source resource_ids_centos.txt

set -e

launch_template_name="MyLaunchTemplate"
auto_scaling_group_name="MyAutoScalingGroup"
min_size=2
max_size=3
desired_capacity=2

# Create Launch Template
launch_template_id=$(aws ec2 create-launch-template \
    --launch-template-name ${launch_template_name} \
    --version-description "v1" \
    --launch-template-data '{
        "ImageId": "'${image_id}'",
        "InstanceType": "t3.micro",
        "KeyName": "'${key_pair_name}'",
        "SecurityGroupIds": ["'${app_security_group_id}'"],
        "IamInstanceProfile": {"Name": "'${instance_profile_name}'"},
        "UserData": "'$(base64 -w 0 ./userDataCentOsComplex.sh)'",
        "BlockDeviceMappings": [{
            "DeviceName": "/dev/sdh",
            "Ebs": {
                "VolumeSize": 8,
                "DeleteOnTermination": true
            }
        }]
    }' \
    --query 'LaunchTemplate.LaunchTemplateId' --output text --region ${region})

echo "Launch template ${launch_template_name} created with ID ${launch_template_id}."

# Create Auto Scaling Group
aws autoscaling create-auto-scaling-group \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --launch-template "LaunchTemplateId=${launch_template_id},Version=1" \
    --min-size ${min_size} \
    --max-size ${max_size} \
    --desired-capacity ${desired_capacity} \
    --vpc-zone-identifier "${private_subnet_id_1},${private_subnet_id_2}" \
    --region ${region}

echo "Auto Scaling group ${auto_scaling_group_name} created."

# Create Scaling Policies
scale_up_policy_arn=$(aws autoscaling put-scaling-policy \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --policy-name ScaleUpPolicy \
    --scaling-adjustment 1 \
    --adjustment-type ChangeInCapacity \
    --region ${region} \
    --query 'PolicyARN' --output text)

scale_down_policy_arn=$(aws autoscaling put-scaling-policy \
    --auto-scaling-group-name ${auto_scaling_group_name} \
    --policy-name ScaleDownPolicy \
    --scaling-adjustment -1 \
    --adjustment-type ChangeInCapacity \
    --region ${region} \
    --query 'PolicyARN' --output text)

echo "Scaling policies created: ScaleUpPolicy (${scale_up_policy_arn}), ScaleDownPolicy (${scale_down_policy_arn})"

# Create CloudWatch Alarms for Scaling Policies
aws cloudwatch put-metric-alarm \
    --alarm-name HighCPUUtilization \
    --metric-name CPUUtilization \
    --namespace AWS/EC2 \
    --statistic Average \
    --period 300 \
    --threshold 80 \
    --comparison-operator GreaterThanOrEqualToThreshold \
    --dimensions Name=AutoScalingGroupName,Value=${auto_scaling_group_name} \
    --evaluation-periods 2 \
    --alarm-actions ${scale_up_policy_arn} \
    --region ${region}

aws cloudwatch put-metric-alarm \
    --alarm-name LowCPUUtilization \
    --metric-name CPUUtilization \
    --namespace AWS/EC2 \
    --statistic Average \
    --period 300 \
    --threshold 20 \
    --comparison-operator LessThanOrEqualToThreshold \
    --dimensions Name=AutoScalingGroupName,Value=${auto_scaling_group_name} \
    --evaluation-periods 2 \
    --alarm-actions ${scale_down_policy_arn} \
    --region ${region}

echo "CloudWatch alarms created for scaling policies."