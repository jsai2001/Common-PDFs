#!/bin/bash

source resource_ids_centos.txt

# Delete the subnet
aws ec2 delete-subnet --subnet-id ${SubnetId} --region ap-south-1
echo "Deleted Subnet with ID ${SubnetId}."