#!/bin/bash

region="ap-south-1"
key_pair_name="Centos-Complex-Key-Pair-Auto"
vpc_cidr="10.0.0.0/16"
subnet_cidr="10.0.1.0/24"
security_group_name="CentosComplexSecurityGroup"
role_name="CentOsRole"
instance_profile_name="CentOsProfile"
policy_arn="arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"
placement_group_name="centos-instances-placement-group"
image_id="ami-0fd05997b4dff7aac"
instance_name="CentOSComplexInstance"
instance_count=2