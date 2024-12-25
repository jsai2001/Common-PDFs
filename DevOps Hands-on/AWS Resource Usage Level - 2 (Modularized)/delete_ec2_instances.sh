#!/bin/bash

source resource_ids_centos.txt

# Terminate the EC2 instances if InstanceIds is not empty
for instance_id in ${InstanceIds}; do
    if [ -n "${instance_id}" ]; then
        echo "Processing instance ID: $instance_id"
        aws ec2 terminate-instances --instance-ids ${instance_id} --region ap-south-1
        echo "Terminating EC2 instance with ID ${instance_id}..."
        aws ec2 wait instance-terminated --instance-ids ${instance_id} --region ap-south-1
        echo "EC2 instance with ID ${instance_id} has been terminated."
    else
        echo "No EC2 instances to terminate."
    fi
done