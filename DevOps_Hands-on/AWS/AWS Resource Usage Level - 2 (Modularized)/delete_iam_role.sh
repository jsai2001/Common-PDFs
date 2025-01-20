#!/bin/bash

source resource_ids_centos.txt

# Delete the IAM role and instance profile
aws iam remove-role-from-instance-profile --instance-profile-name ${InstanceProfileName} --role-name ${RoleName}
aws iam delete-instance-profile --instance-profile-name ${InstanceProfileName}
aws iam detach-role-policy --role-name ${RoleName} --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
aws iam delete-role --role-name ${RoleName}
echo "Deleted IAM Role and Instance Profile."