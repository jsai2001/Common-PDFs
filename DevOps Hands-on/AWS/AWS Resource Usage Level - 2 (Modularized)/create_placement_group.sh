#!/bin/bash

source config.sh

# Create Placement Group if it doesn't exist
aws ec2 create-placement-group --group-name ${placement_group_name} --strategy spread --region ${region}
echo "Created Placement Group with name ${placement_group_name}"

echo "PlacementGroupName=${placement_group_name}" >> resource_ids_centos.txt