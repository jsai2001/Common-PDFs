#!/bin/bash

region="ap-south-1"
available_zone=$(aws ec2 describe-instance-type-offerings --location-type availability-zone --filters "Name=instance-type,Values=t2.micro" "Name=location,Values=ap-south-1a,ap-south-1b,ap-south-1c" --region ${region} --query 'InstanceTypeOfferings[0].Location' --output text)

if [ -z "$available_zone" ]; then
    echo "No availability zone found for t2.micro instance type in region ${region}"
    exit 1
fi

echo "Selected availability zone: ${available_zone}"

echo "region=${region}" > resource_ids_centos.txt
echo "available_zone=${available_zone}" >> resource_ids_centos.txt

# Execute modules
./create_key_pair.sh ${region}
sleep 10
./create_vpc.sh ${region}
sleep 10
./create_internet_gateway.sh ${region}
sleep 10
./create_subnets.sh ${region}
sleep 10
./create_route_tables.sh ${region}
sleep 10
./create_nat_gateway.sh ${region}
sleep 10
./create_security_groups.sh ${region}
sleep 10
./create_iam_role.sh ${region}
sleep 10
./create_placement_group.sh ${region}
sleep 10
./launch_instances.sh ${region}