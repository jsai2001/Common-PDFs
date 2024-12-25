#!/bin/bash

region=$1
placement_group_name="centos-instances-placement-group"

# Create Placement Group if it doesn't exist
aws ec2 create-placement-group --group-name ${placement_group_name} --strategy spread --region ${region}
echo "Created Placement Group with name ${placement_group_name}"

echo "placement_group_name=${placement_group_name}" >> resource_ids_centos.txt