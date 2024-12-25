#!/bin/bash

source resource_ids_centos.txt

# Detach and delete the internet gateway
aws ec2 detach-internet-gateway --internet-gateway-id ${IgwId} --vpc-id ${VpcId} --region ap-south-1
echo "Detached Internet Gateway with ID ${IgwId} from VPC ${VpcId}."
aws ec2 delete-internet-gateway --internet-gateway-id ${IgwId} --region ap-south-1
echo "Deleted Internet Gateway with ID ${IgwId}."