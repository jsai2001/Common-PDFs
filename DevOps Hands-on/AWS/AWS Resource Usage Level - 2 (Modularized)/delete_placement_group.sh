#!/bin/bash

source resource_ids_centos.txt

# Delete the placement group
aws ec2 delete-placement-group --group-name ${PlacementGroupName} --region ap-south-1
echo "Deleted Placement Group with name ${PlacementGroupName}."