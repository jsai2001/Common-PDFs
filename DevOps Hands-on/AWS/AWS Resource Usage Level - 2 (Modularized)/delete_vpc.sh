#!/bin/bash

source resource_ids_centos.txt

# Delete the VPC
aws ec2 delete-vpc --vpc-id ${VpcId} --region ap-south-1
echo "Deleted VPC with ID ${VpcId}."