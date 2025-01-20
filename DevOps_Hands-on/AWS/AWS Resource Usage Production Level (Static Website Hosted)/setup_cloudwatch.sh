#!/bin/bash

region=$1
source resource_ids_centos.txt

# Create CloudWatch log group
log_group_name="my-log-group"
aws logs create-log-group --log-group-name ${log_group_name} --region ${region}

echo "CloudWatch log group ${log_group_name} created."

# Store CloudWatch log group name
echo "log_group_name=${log_group_name}" >> resource_ids_centos.txt

# Create SNS topic
sns_topic_name="MyTopic"
sns_topic_arn=$(aws sns create-topic --name ${sns_topic_name} --region ${region} --query 'TopicArn' --output text)

echo "SNS topic ${sns_topic_name} created with ARN ${sns_topic_arn}."

# Subscribe to SNS topic
email_address="jeevansaikanaparthi@gmail.com"  # Replace with your email address
aws sns subscribe --topic-arn ${sns_topic_arn} --protocol email --notification-endpoint ${email_address} --region ${region}

echo "Subscribed ${email_address} to SNS topic ${sns_topic_name}."

# Store SNS topic ARN
echo "sns_topic_arn=${sns_topic_arn}" >> resource_ids_centos.txt

# Create CloudWatch alarm for CPU utilization
alarm_name="HighCPUUtilization"
aws cloudwatch put-metric-alarm --alarm-name ${alarm_name} \
    --metric-name CPUUtilization --namespace AWS/EC2 \
    --statistic Average --period 300 --threshold 80 \
    --comparison-operator GreaterThanOrEqualToThreshold \
    --dimensions Name=InstanceId,Value=${instance_ids[0]} \
    --evaluation-periods 2 --alarm-actions ${sns_topic_arn} \
    --region ${region}

echo "CloudWatch alarm ${alarm_name} created."