#!/bin/bash

source config.sh

# Create trust policy JSON file
cat > trust-policy.json <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "ec2.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF

# Create IAM Role
aws iam create-role --role-name ${role_name} --assume-role-policy-document file://trust-policy.json --region ${region}
aws iam attach-role-policy --role-name ${role_name} --policy-arn ${policy_arn} --region ${region}

# Create Instance Profile
aws iam create-instance-profile --instance-profile-name ${instance_profile_name} --region ${region}
aws iam add-role-to-instance-profile --instance-profile-name ${instance_profile_name} --role-name ${role_name} --region ${region}

# Retrieve Instance Profile ARN
instance_profile_arn=$(aws iam get-instance-profile --instance-profile-name ${instance_profile_name} --query 'InstanceProfile.Arn' --output text --region ${region})
echo "Instance Profile ARN: ${instance_profile_arn}"

echo "RoleName=${role_name}" >> resource_ids_centos.txt
echo "InstanceProfileName=${instance_profile_name}" >> resource_ids_centos.txt