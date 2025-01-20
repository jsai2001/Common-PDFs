#!/bin/bash

set -e

region="ap-south-1"
instance_type="t2.micro"
azs=("ap-south-1a" "ap-south-1b" "ap-south-1c")

# Function to check if an AZ is available for the instance type
check_az_availability() {
    aws ec2 describe-instance-type-offerings \
        --location-type availability-zone \
        --filters "Name=instance-type,Values=$instance_type" "Name=location,Values=$1" \
        --region $region \
        --query 'InstanceTypeOfferings[?InstanceType==`'${instance_type}'`].Location' \
        --output text
}

available_azs=()

# Check each AZ
for az in "${azs[@]}"; do
    if [[ $(check_az_availability $az) == "$az" ]]; then
        available_azs+=("$az")
    fi
done

# Check if at least one AZ is available
if [ ${#available_azs[@]} -eq 0 ]; then
    echo "No Availability Zone present for $instance_type in $region"
    exit 1
fi

# If only one AZ is available, exit with an error message
if [ ${#available_azs[@]} -eq 1 ]; then
    echo "Only one Availability Zone present for $instance_type in $region: ${available_azs[0]}"
    exit 1
fi

# If two or more AZs are available, print only two of them
echo "Selected availability zone: ${available_azs[0]} ${available_azs[1]}"

echo "region=${region}" > resource_ids_centos.txt
echo "available_zone_1=${available_azs[0]}" >> resource_ids_centos.txt
echo "available_zone_2=${available_azs[1]}" >> resource_ids_centos.txt

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
./create_s3_bucket.sh ${region}
sleep 30
./create_rds_instance.sh ${region}
sleep 60
./setup_cloudwatch.sh ${region}
sleep 10
./launch_instances.sh ${region}
sleep 10
./create_load_balancer.sh ${region}
sleep 60
./create_auto_scaling_group.sh ${region}