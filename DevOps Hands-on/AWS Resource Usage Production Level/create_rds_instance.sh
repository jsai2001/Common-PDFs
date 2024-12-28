#!/bin/bash

region=$1
source resource_ids_centos.txt

set -e

db_instance_identifier="mydbinstance"
db_instance_class="db.t3.micro"
engine="mysql"
# engine_version="8.0.23"
master_username="admin"
master_user_password="yourpassword"
db_name="mydatabase"
db_subnet_group_name="mydbsubnetgroup"

# Create DB Subnet Group
aws rds create-db-subnet-group \
    --db-subnet-group-name ${db_subnet_group_name} \
    --db-subnet-group-description "My DB Subnet Group" \
    --subnet-ids ${private_subnet_id_1} ${private_subnet_id_2} \
    --region ${region}

echo "DB Subnet Group ${db_subnet_group_name} created."

# Create RDS instance with Multi-AZ deployment and private accessibility
aws rds create-db-instance \
    --db-instance-identifier ${db_instance_identifier} \
    --db-instance-class ${db_instance_class} \
    --engine ${engine} \
    --master-username ${master_username} \
    --master-user-password ${master_user_password} \
    --allocated-storage 20 \
    --db-name ${db_name} \
    --vpc-security-group-ids ${app_security_group_id} \
    --db-subnet-group-name ${db_subnet_group_name} \
    --multi-az \
    --no-publicly-accessible \
    --region ${region}

echo "RDS instance ${db_instance_identifier} is being created with Multi-AZ deployment and private accessibility. This may take a few minutes."

# Wait for the RDS instance to be available
aws rds wait db-instance-available --db-instance-identifier ${db_instance_identifier} --region ${region}

# Get the RDS endpoint
db_endpoint=$(aws rds describe-db-instances --db-instance-identifier ${db_instance_identifier} --query 'DBInstances[0].Endpoint.Address' --output text --region ${region})
echo "RDS instance endpoint: ${db_endpoint}"

# Store RDS instance details
echo "db_instance_identifier=${db_instance_identifier}" >> resource_ids_centos.txt
echo "db_endpoint=${db_endpoint}" >> resource_ids_centos.txt