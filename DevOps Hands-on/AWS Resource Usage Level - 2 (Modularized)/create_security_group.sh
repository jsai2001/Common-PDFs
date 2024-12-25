#!/bin/bash

source config.sh
source resource_ids_centos.txt

# Create Security Group if it doesn't exist
security_group_id=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=${vpc_id}" "Name=group-name,Values=${security_group_name}" --query 'SecurityGroups[0].GroupId' --output text --region ${region})
if [ "$security_group_id" == "None" ]; then
    security_group_id=$(aws ec2 create-security-group --group-name ${security_group_name} --description "Centos Complex security group" --vpc-id ${vpc_id} --query 'GroupId' --output text --region ${region})
    echo "Created Security Group with ID ${security_group_id}"

    # Add inbound rules to the security group
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 22 --cidr 0.0.0.0/0 --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 80 --cidr 0.0.0.0/0 --region ${region}
    aws ec2 authorize-security-group-ingress --group-id ${security_group_id} --protocol tcp --port 443 --cidr 0.0.0.0/0 --region ${region}

    # Add outbound rules to the security group (all requests allowed to outside)
    aws ec2 authorize-security-group-egress --group-id ${security_group_id} --protocol -1 --port all --cidr 0.0.0.0/0 --region ${region}
else
    echo "Security Group ${security_group_name} already exists with ID ${security_group_id}"
fi

echo "SecurityGroupId=${security_group_id}" >> resource_ids_centos.txt