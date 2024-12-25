#!/bin/bash

source resource_ids_centos.txt

# Delete the security group
aws ec2 delete-security-group --group-id ${SecurityGroupId} --region ap-south-1
echo "Deleted Security Group with ID ${SecurityGroupId}."