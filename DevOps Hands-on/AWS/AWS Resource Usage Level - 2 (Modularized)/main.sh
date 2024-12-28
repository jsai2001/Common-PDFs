#!/bin/bash

source config.sh

available_zone=$(aws ec2 describe-instance-type-offerings --location-type availability-zone --filters "Name=instance-type,Values=t2.micro" "Name=location,Values=ap-south-1a,ap-south-1b,ap-south-1c" --region ${region} --query 'InstanceTypeOfferings[0].Location' --output text)

if [ -z "$available_zone" ]; then
    echo "No availability zone found for t2.micro instance type in region ${region}"
    exit 1
fi

echo "Selected availability zone: ${available_zone}"

# Execute modules
./create_key_pair.sh
./create_vpc.sh
./create_internet_gateway.sh
./create_subnet.sh
./create_route_table.sh
./create_security_group.sh
./create_iam_role.sh
./create_placement_group.sh
./launch_instances.sh