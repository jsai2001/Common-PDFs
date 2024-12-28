#!/bin/bash

region=$1
source resource_ids_centos.txt

bucket_name="my-unique-bucket-name-$(date +%s)"

# Create S3 bucket
aws s3api create-bucket --bucket ${bucket_name} --region ${region} --create-bucket-configuration LocationConstraint=${region}

echo "S3 bucket ${bucket_name} created."

# Store S3 bucket name
echo "bucket_name=${bucket_name}" >> resource_ids_centos.txt

# Upload a sample file to the S3 bucket
echo "This is a sample file for S3 bucket." > sample_file.txt
aws s3 cp sample_file.txt s3://${bucket_name}/sample_file.txt --region ${region}

echo "Sample file uploaded to S3 bucket ${bucket_name}."