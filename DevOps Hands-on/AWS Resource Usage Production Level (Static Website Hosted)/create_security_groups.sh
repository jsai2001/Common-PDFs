#!/bin/bash

region=$1
bastion_security_group_id=None
app_security_group_id=None
source resource_ids_centos.txt

# Create Security Group for Bastion Host
bastion_security_group_name="BastionSecurityGroup"
# bastion_security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${bastion_security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$bastion_security_group_id" == "None" ]; then
    bastion_security_group_id=$(aws ec2 create-security-group --group-name ${bastion_security_group_name} --description "Bastion security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
    echo "Created Bastion Security Group with ID ${bastion_security_group_id}"

    # Add inbound rules to the bastion security group
    aws ec2 authorize-security-group-ingress --group-id ${bastion_security_group_id} --protocol tcp --port 22 --cidr 0.0.0.0/0 --region ${region}
else
    echo "Bastion Security Group ${bastion_security_group_name} already exists with ID ${bastion_security_group_id}"
fi

# Create Security Group for Application Instances
app_security_group_name="AppSecurityGroup"
# app_security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${app_security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$app_security_group_id" == "None" ]; then
    app_security_group_id=$(aws ec2 create-security-group --group-name ${app_security_group_name} --description "Application security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
    echo "Created Application Security Group with ID ${app_security_group_id}"

    # Add inbound rules to the application security group
    aws ec2 authorize-security-group-ingress --group-id ${app_security_group_id} --protocol tcp --port 22 --source-group ${bastion_security_group_id} --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${app_security_group_id} --protocol tcp --port 80 --cidr 0.0.0.0/0 --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${app_security_group_id} --protocol tcp --port 443 --cidr 0.0.0.0/0 --region ${region}
else
    echo "Application Security Group ${app_security_group_name} already exists with ID ${app_security_group_id}"
fi

echo "bastion_security_group_id=${bastion_security_group_id}" >> resource_ids_centos.txt
echo "app_security_group_id=${app_security_group_id}" >> resource_ids_centos.txt