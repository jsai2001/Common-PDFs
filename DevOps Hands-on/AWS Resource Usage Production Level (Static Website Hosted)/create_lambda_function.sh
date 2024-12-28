#!/bin/bash

region=$1
source resource_ids_centos.txt

set -e

lambda_role_name="LambdaExecutionRole"
lambda_function_name="MyLambdaFunction"
bucket_name=$(grep bucket_name resource_ids_centos.txt | cut -d'=' -f2)

# Create IAM Role for Lambda
aws iam create-role --role-name ${lambda_role_name} --assume-role-policy-document file://trust-policy.json --region ${region}
aws iam attach-role-policy --role-name ${lambda_role_name} --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole --region ${region}

# Create Lambda Function
zip function.zip lambda_function.py
aws lambda create-function --function-name ${lambda_function_name} \
    --zip-file fileb://function.zip --handler lambda_function.lambda_handler --runtime python3.8 \
    --role arn:aws:iam::$(aws sts get-caller-identity --query Account --output text):role/${lambda_role_name} \
    --region ${region}

# Add S3 trigger to Lambda Function
aws lambda add-permission --function-name ${lambda_function_name} --principal s3.amazonaws.com \
    --statement-id s3invoke --action "lambda:InvokeFunction" \
    --source-arn arn:aws:s3:::${bucket_name} --region ${region}

aws s3api put-bucket-notification-configuration --bucket ${bucket_name} --notification-configuration '{
    "LambdaFunctionConfigurations": [
        {
            "LambdaFunctionArn": "arn:aws:lambda:'${region}':'$(aws sts get-caller-identity --query Account --output text)':function:'${lambda_function_name}'",
            "Events": ["s3:ObjectCreated:*"]
        }
    ]
}' --region ${region}

echo "lambda_role_name=${lambda_role_name}" >> resource_ids_centos.txt
echo "lambda_function_name=${lambda_function_name}" >> resource_ids_centos.txt
echo "Lambda function ${lambda_function_name} created and configured with S3 trigger."