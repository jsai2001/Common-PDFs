# Interview Questions on Python boto3 for AWS, Kubernetes, Terraform, Ansible, and GitHub Actions

# Conceptual Questions
1. What is the `boto3` Python module, and how does it interact with AWS services?
2. Explain the difference between `boto3.client` and `boto3.resource` in the context of AWS resource manipulation.
3. How does `boto3` authenticate with AWS? Describe the various authentication methods supported.
4. What are the key AWS services you can manage using `boto3`?
5. How does `boto3` handle pagination for large datasets returned by AWS APIs?
6. What is the role of the AWS SDK in `boto3`, and how does it differ from other AWS SDKs?
7. Explain how `boto3` can be used to manage AWS resources in a programmatic way.
8. What are the benefits of using `boto3` over the AWS Management Console or AWS CLI?
9. How does `boto3` integrate with other DevOps tools like Kubernetes, Terraform, Ansible, and GitHub Actions?
10. What is the significance of AWS IAM roles in securing `boto3` scripts?
11. How does `boto3` handle errors, and what are some best practices for error handling?
12. Explain the concept of AWS regions and how `boto3` interacts with region-specific resources.
13. What are the limitations of using `boto3` for AWS resource management?
14. How can you use `boto3` to interact with AWS services in a serverless architecture (e.g., Lambda)?
15. What is the purpose of the `botocore` library in relation to `boto3`?

## Technical Questions (boto3 and AWS)
16. Write a `boto3` script to list all S3 buckets in an AWS account.
17. How do you use `boto3` to create an EC2 instance with specific parameters (e.g., AMI, instance type)?
18. Write a `boto3` script to upload a file to an S3 bucket and make it publicly accessible.
19. How do you configure `boto3` to use a specific AWS profile from the `~/.aws/credentials` file?
20. Explain how to use `boto3` to start, stop, or terminate an EC2 instance.
21. Write a `boto3` script to create an SNS topic and publish a message to it.
22. How do you use `boto3` to query DynamoDB tables and handle conditional writes?
23. Write a `boto3` script to create a Lambda function and invoke it programmatically.
24. How do you use `boto3` to manage IAM roles and policies (e.g., attach a policy to a role)?
25. Explain how to use `boto3` to interact with AWS CloudFormation stacks (e.g., create, update, delete).
26. Write a `boto3` script to monitor CloudWatch metrics for an EC2 instance.
27. How do you handle throttling or rate-limiting errors in `boto3` scripts?
28. Write a `boto3` script to create an SQS queue and send a message to it.
29. How do you use `boto3` to manage VPC configurations, such as creating a subnet or security group?
30. Explain how to use `boto3` with AWS STS to assume a role for cross-account access.

## Technical Questions (boto3 with Kubernetes)
31. How can `boto3` be used to interact with an Amazon EKS cluster (e.g., retrieving cluster details)?
32. Write a `boto3` script to list all EKS clusters in a given region.
33. How do you use `boto3` to update the configuration of an EKS cluster (e.g., scaling node groups)?
34. Explain how `boto3` can be used to manage IAM roles for EKS service accounts.
35. Write a `boto3` script to create an EKS node group for an existing cluster.
36. How do you integrate `boto3` with Kubernetes Python libraries (e.g., `kubernetes` client) to manage EKS resources?
37. How can `boto3` be used to retrieve logs from an EKS cluster stored in CloudWatch?
38. Explain how to use `boto3` to configure autoscaling for an EKS cluster.
39. Write a `boto3` script to delete an EKS cluster and its associated resources.
40. How do you use `boto3` to manage EKS add-ons (e.g., VPC-CNI, CoreDNS)?

## Technical Questions (boto3 with Terraform)
41. How can `boto3` be used to validate or inspect resources created by Terraform in AWS?
42. Write a `boto3` script to list all resources in an AWS account created by a specific Terraform state file.
43. How do you use `boto3` to automate post-provisioning tasks for resources created by Terraform (e.g., configuring an EC2 instance)?
44. Explain how to use `boto3` to interact with Terraform-managed S3 buckets for state storage.
45. Write a `boto3` script to check the status of a Terraform-managed CloudFormation stack.
46. How can `boto3` be used to trigger Terraform operations programmatically (e.g., via AWS Lambda)?
47. Explain how to use `boto3` to clean up orphaned resources not managed by Terraform.
48. Write a `boto3` script to enable versioning on an S3 bucket used as a Terraform backend.
49. How do you integrate `boto3` with Terraform’s AWS provider to manage dynamic credentials?
50. Explain how to use `boto3` to monitor drift in Terraform-managed infrastructure.

## Technical Questions (boto3 with Ansible)
51. How can `boto3` be used to complement Ansible playbooks for AWS resource management?
52. Write a `boto3` script to generate an inventory of AWS resources for use in an Ansible playbook.
53. How do you use `boto3` to validate the state of AWS resources before running an Ansible playbook?
54. Explain how to use `boto3` to create AWS resources that Ansible will later configure.
55. Write a `boto3` script to tag EC2 instances for Ansible dynamic inventory.
56. How can `boto3` be used to automate the setup of Ansible’s AWS modules (e.g., ensuring `boto3` is installed)?
57. Explain how to use `boto3` to retrieve AWS resource details for Ansible’s `ec2_instance` module.
58. Write a `boto3` script to create an SNS topic that Ansible can use for notifications.
59. How do you integrate `boto3` with Ansible to handle AWS resource failures dynamically?
60. Explain how to use `boto3` to manage AWS credentials for Ansible’s AWS modules securely.

## Technical Questions (boto3 with GitHub Actions)
61. How can `boto3` be used in a GitHub Actions workflow to manage AWS resources?
62. Write a GitHub Actions workflow that uses `boto3` to deploy a Lambda function.
63. How do you configure AWS credentials in GitHub Actions to allow `boto3` scripts to run securely?
64. Explain how to use `boto3` in a GitHub Actions workflow to validate an S3 bucket’s contents.
65. Write a `boto3` script to be executed in a GitHub Actions workflow to scale an EKS cluster.
66. How do you use `boto3` in GitHub Actions to publish test results to an SNS topic?
67. Write a GitHub Actions workflow that uses `boto3` to create an S3 bucket and upload artifacts.
68. How can `boto3` be used in GitHub Actions to trigger a CloudFormation stack deployment?
69. Explain how to handle `boto3` errors in a GitHub Actions workflow to prevent pipeline failures.
70. Write a `boto3` script for a GitHub Actions workflow to clean up unused EC2 instances.

## Scenario-Based Questions
71. **Scenario**: Your team’s GitHub Actions workflow is failing because the `boto3` script cannot authenticate with AWS. How would you troubleshoot and resolve this issue?
72. **Scenario**: A `boto3` script is timing out when listing thousands of objects in an S3 bucket. Redacted. How would you optimize the script to handle large buckets efficiently?
73. **Scenario**: You need to automate the creation of an EKS cluster using `boto3` and then configure it with Ansible. Describe the steps to achieve this.
74. **Scenario**: A `boto3` script is failing with a “Rate Exceeded” error when creating multiple EC2 instances. How would you modify the script to handle this?
75. **Scenario**: Your Terraform-managed infrastructure is out of sync with the actual AWS resources. How would you use `boto3` to detect and report drift?
76. **Scenario**: A GitHub Actions workflow using `boto3` to deploy a Lambda function is exposing AWS credentials in the logs. How would you secure the workflow?
77. **Scenario**: You need to write a `boto3` script to monitor CloudWatch alarms and trigger an Ansible playbook if an alarm is triggered. How would you implement this?
78. **Scenario**: An EKS cluster created with `boto3` is not scaling as expected. How would you diagnose and fix the issue?
79. **Scenario**: Your team wants to automate the cleanup of unused S3 buckets using `boto3` in a GitHub Actions workflow. Describe the approach.
80. **Scenario**: A `boto3` script is failing because the IAM role lacks permissions to access a DynamoDB table. How would you troubleshoot and resolve this?
81. **Scenario**: You need to use `boto3` to create AWS resources and then use Terraform to manage them. How would you ensure a smooth handoff?
82. **Scenario**: A `boto3` script in a GitHub Actions workflow is taking too long to complete. How would you optimize its performance?
83. **Scenario**: Your Ansible playbook is failing because the AWS resources it expects (created by `boto3`) are not yet available. How would you handle this?
84. **Scenario**: You need to use `boto3` to extract logs from an EKS cluster and store them in S3 for analysis. Describe the implementation.
85. **Scenario**: A `boto3` script is inadvertently deleting critical AWS resources. How would you add safeguards to prevent this?

## Example Artifacts

### Example boto3 Script: List S3 Buckets
<xaiArtifact artifact_id="55fabff6-f705-4c72-bc62-6d055af37660" artifact_version_id="338def31-49fd-415c-8b98-d9125b2d91e9" title="list_s3_buckets.py" contentType="text/python">
import boto3

def list_s3_buckets():
    s3_client = boto3.client('s3')
    response = s3_client.list_buckets()
    for bucket in response['Buckets']:
        print(f"Bucket: {bucket['Name']}, Created: {bucket['CreationDate']}")

if __name__ == "__main__":
    list_s3_buckets()

## Conceptual Questions

## 1. **What is the** `boto3` **Python module, and how does it interact with AWS services?**\
    **Answer**: `boto3` is the official AWS SDK for Python, enabling developers to interact with AWS services programmatically. It provides a Pythonic interface to AWS APIs, allowing management of resources like EC2, S3, Lambda, and more. It interacts with AWS services via HTTP requests authenticated with AWS credentials, using `botocore` to handle low-level API calls.

## 2. **Explain the difference between** `boto3.client` **and** `boto3.resource` **in the context of AWS resource manipulation.**\
    **Answer**:

    - `boto3.client`: Provides a low-level interface to AWS APIs, returning raw JSON responses. It’s suitable for operations requiring precise control or when the resource abstraction isn’t available.
    - `boto3.resource`: Offers a higher-level, object-oriented interface, abstracting AWS resources (e.g., S3 buckets) as Python objects with methods and attributes. It simplifies common tasks but may not support all API operations.\
      **Example**: Listing S3 buckets:

    ```python
    import boto3
    
    # Using client
    client = boto3.client('s3')
    response = client.list_buckets()
    print(response['Buckets'])
    
    # Using resource
    resource = boto3.resource('s3')
    for bucket in resource.buckets.all():
        print(bucket.name)
    ```

## 3. **How does** `boto3` **authenticate with AWS? Describe the various authentication methods supported.**\
    **Answer**: `boto3` authenticates using AWS credentials, resolved in this order:

    - Environment variables (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_SESSION_TOKEN`).
    - AWS credentials file (`~/.aws/credentials`).
    - IAM roles for Amazon EC2 instances or ECS tasks.
    - AWS CLI SSO or temporary credentials via AWS STS.\
      Credentials can be specified explicitly in code or via profiles.

## 4. **What are the key AWS services you can manage using** `boto3`**?**\
    **Answer**: `boto3` supports most AWS services, including:

    - Compute: EC2, Lambda, ECS, EKS.
    - Storage: S3, EBS, Glacier.
    - Database: DynamoDB, RDS, Redshift.
    - Networking: VPC, Route 53, ELB.
    - Messaging: SNS, SQS, SES.
    - Monitoring: CloudWatch, CloudTrail.
    - Management: IAM, CloudFormation, SSM.

## 5. **How does** `boto3` **handle pagination for large datasets returned by AWS APIs?**\
    **Answer**: `boto3` handles pagination using paginators, which automatically iterate over paginated API responses. Paginators simplify retrieving large datasets by handling `NextToken` or similar markers.\
    **Example**:

    ```python
    import boto3
    
    s3 = boto3.client('s3')
    paginator = s3.get_paginator('list_objects_v2')
    for page in paginator.paginate(Bucket='my-bucket'):
        for obj in page.get('Contents', []):
            print(obj['Key'])
    ```

## 6. **What is the role of the AWS SDK in** `boto3`**, and how does it differ from other AWS SDKs?**\
    **Answer**: The AWS SDK for Python (`boto3`) provides a Python interface to AWS APIs, built on `botocore`. It abstracts low-level HTTP requests into Python methods. Other SDKs (e.g., AWS SDK for JavaScript, Java) serve similar purposes but are tailored to their respective languages, differing in syntax, conventions, and some feature implementations.

## 7. **Explain how** `boto3` **can be used to manage AWS resources in a programmatic way.**\
    **Answer**: `boto3` allows programmatic management of AWS resources via Python scripts, enabling automation of tasks like creating EC2 instances, uploading S3 files, or configuring IAM roles. It supports both low-level (`client`) and high-level (`resource`) interfaces, integrating with scripts, CI/CD pipelines, or serverless functions.

## 8. **What are the benefits of using** `boto3` **over the AWS Management Console or AWS CLI?**\
    **Answer**:

    - **Automation**: `boto3` enables scripted, repeatable tasks.
    - **Integration**: Seamlessly integrates with Python-based tools and workflows.
    - **Scalability**: Manages large-scale resources programmatically.
    - **Flexibility**: Allows complex logic and conditionals not possible in the console or CLI.
    - **Error Handling**: Provides programmatic error handling and retries.

## 9. **How does** `boto3` **integrate with other DevOps tools like Kubernetes, Terraform, Ansible, and GitHub Actions?**\
    **Answer**:

    - **Kubernetes**: Manages EKS clusters (e.g., creating node groups) and integrates with Kubernetes Python clients.
    - **Terraform**: Validates or modifies Terraform-managed AWS resources.
    - **Ansible**: Complements Ansible by generating resource inventories or pre-provisioning resources.
    - **GitHub Actions**: Runs `boto3` scripts in workflows to deploy or manage AWS resources.
## 10. **What is the significance of AWS IAM roles in securing** `boto3` **scripts?**\
    **Answer**: IAM roles provide temporary, scoped credentials for `boto3` scripts, reducing the risk of hardcoding keys. They enforce least privilege, ensuring scripts only access authorized resources, and are critical for secure execution in EC2, Lambda, or CI/CD environments.
## 11. **How does** `boto3` **handle errors, and what are some best practices for error handling?**\
    **Answer**: `boto3` raises exceptions (e.g., `botocore.exceptions.ClientError`) for API errors. Best practices:

    - Use try-except blocks to catch specific errors.
    - Implement exponential backoff for throttling errors.
    - Log errors for debugging.
    - Validate inputs to prevent client-side errors.\
      **Example**:

    ```python
    import boto3
    from botocore.exceptions import ClientError
    
    s3 = boto3.client('s3')
    try:
        s3.head_bucket(Bucket='my-bucket')
    except ClientError as e:
        print(f"Error: {e.response['Error']['Message']}")
    ```
## 12. **Explain the concept of AWS regions and how** `boto3` **interacts with region-specific resources.**\
    **Answer**: AWS regions are isolated geographic areas hosting AWS services. `boto3` interacts with region-specific resources by specifying the region in the client or resource constructor (e.g., `boto3.client('s3', region_name='us-east-1')`). If unspecified, it uses the default region from the AWS configuration.
## 13. **What are the limitations of using** `boto3` **for AWS resource management?**\
    **Answer**:

    - **Complexity**: Requires understanding AWS APIs and Python.
    - **Rate Limits**: API throttling can affect performance.
    - **Incomplete Resource Coverage**: Some services have limited `resource` support, requiring `client` usage.
    - **Error Handling**: Requires robust exception handling for reliability.
    - **Learning Curve**: Steep for beginners compared to the AWS Console.
## 14. **How can you use** `boto3` **to interact with AWS services in a serverless architecture (e.g., Lambda)?**\
    **Answer**: In a serverless architecture, `boto3` is used in Lambda functions to interact with AWS services (e.g., S3, DynamoDB, SNS). Lambda’s IAM role provides credentials, and `boto3` scripts handle event-driven tasks like processing S3 uploads or sending notifications.
## 15. **What is the purpose of the** `botocore` **library in relation to** `boto3`**?**\
    **Answer**: `botocore` is the low-level library underpinning `boto3`, handling HTTP requests, authentication, and API response parsing. `boto3` builds on `botocore` to provide a user-friendly, Pythonic interface.

## Technical Questions (boto3 and AWS)
## 16. **Write a** `boto3` **script to list all S3 buckets in an AWS account.**

    ```python
    import boto3
    
    def list_s3_buckets():
        s3 = boto3.client('s3')
        response = s3.list_buckets()
        for bucket in response['Buckets']:
            print(f"Bucket: {bucket['Name']}, Created: {bucket['CreationDate']}")
    
    if __name__ == "__main__":
        list_s3_buckets()
    ```
## 17. **How do you use** `boto3` **to create an EC2 instance with specific parameters (e.g., AMI, instance type)?**\
    **Answer**: Use the `run_instances` method of the EC2 client with parameters like `ImageId`, `InstanceType`, and `MinCount`/`MaxCount`.

    ```python
    import boto3
    
    def create_ec2_instance():
        ec2 = boto3.client('ec2')
        response = ec2.run_instances(
            ImageId='ami-0c55b159cbfafe1f0',  # Example AMI ID
            InstanceType='t2.micro',
            MinCount=1,
            MaxCount=1,
            KeyName='my-key-pair',
            SecurityGroupIds=['sg-12345678'],
            SubnetId='subnet-12345678'
        )
        instance_id = response['Instances'][0]['InstanceId']
        print(f"Created instance: {instance_id}")
    
    if __name__ == "__main__":
        create_ec2_instance()
    ```
## 18. **Write a** `boto3` **script to upload a file to an S3 bucket and make it publicly accessible.**

    ```python
    import boto3
    
    def upload_to_s3(file_path, bucket_name, object_key):
        s3 = boto3.client('s3')
        s3.upload_file(file_path, bucket_name, object_key, ExtraArgs={'ACL': 'public-read'})
        print(f"Uploaded {file_path} to s3://{bucket_name}/{object_key}")
    
    if __name__ == "__main__":
        upload_to_s3('local_file.txt', 'my-bucket', 'public_file.txt')
    ```
## 19. **How do you configure** `boto3` **to use a specific AWS profile from the** `~/.aws/credentials` **file?**\
    **Answer**: Use the `boto3.session.Session` with the `profile_name` parameter.

    ```python
    import boto3
    
    session = boto3.session.Session(profile_name='my-profile')
    s3 = session.client('s3')
    response = s3.list_buckets()
    print(response['Buckets'])
    ```
## 20. **Explain how to use** `boto3` **to start, stop, or terminate an EC2 instance.**\
    **Answer**: Use the EC2 client’s `start_instances`, `stop_instances`, or `terminate_instances` methods, passing the instance ID.

    ```python
    import boto3
    
    def manage_ec2_instance(instance_id, action):
        ec2 = boto3.client('ec2')
        if action == 'start':
            ec2.start_instances(InstanceIds=[instance_id])
            print(f"Starting instance {instance_id}")
        elif action == 'stop':
            ec2.stop_instances(InstanceIds=[instance_id])
            print(f"Stopping instance {instance_id}")
        elif action == 'terminate':
            ec2.terminate_instances(InstanceIds=[instance_id])
            print(f"Terminating instance {instance_id}")
    
    if __name__ == "__main__":
        manage_ec2_instance('i-1234567890abcdef0', 'start')
    ```
## 21. **Write a** `boto3` **script to create an SNS topic and publish a message to it.**

    ```python
    import boto3
    
    def create_and_publish_sns(topic_name, message):
        sns = boto3.client('sns')
        response = sns.create_topic(Name=topic_name)
        topic_arn = response['TopicArn']
        sns.publish(TopicArn=topic_arn, Message=message)
        print(f"Published message to {topic_arn}")
    
    if __name__ == "__main__":
        create_and_publish_sns('my-topic', 'Hello, SNS!')
    ```
## 22. **How do you use** `boto3` **to query DynamoDB tables and handle conditional writes?**\
    **Answer**: Use the DynamoDB resource or client to query tables and `put_item` with `ConditionExpression` for conditional writes.

    ```python
    import boto3
    from botocore.exceptions import ClientError
    
    def query_and_write_dynamodb(table_name, item_key, item_data):
        dynamodb = boto3.resource('dynamodb')
        table = dynamodb.Table(table_name)
        
        # Query
        response = table.query(KeyConditionExpression='id = :id', ExpressionAttributeValues={':id': item_key})
        print(f"Query results: {response['Items']}")
        
        # Conditional write
        try:
            table.put_item(Item={'id': item_key, 'data': item_data}, ConditionExpression='attribute_not_exists(id)')
            print("Item written successfully")
        except ClientError as e:
            print(f"Conditional write failed: {e}")
    
    if __name__ == "__main__":
        query_and_write_dynamodb('my-table', '123', 'example-data')
    ```
## 23. **Write a** `boto3` **script to create a Lambda function and invoke it programmatically.**

    ```python
    import boto3
    import zipfile
    import io
    
    def create_and_invoke_lambda(function_name):
        lambda_client = boto3.client('lambda')
        
        # Create ZIP file for Lambda code
        zip_buffer = io.BytesIO()
        with zipfile.ZipFile(zip_buffer, 'a', zipfile.ZIP_DEFLATED) as zf:
            zf.writestr('index.py', 'def handler(event, context):\n    return {"statusCode": 200, "body": "Hello from Lambda!"}')
        zip_buffer.seek(0)
        
        # Create Lambda function
        lambda_client.create_function(
            FunctionName=function_name,
            Runtime='python3.9',
            Role='arn:aws:iam::123456789012:role/lambda-role',
            Handler='index.handler',
            Code={'ZipFile': zip_buffer.read()}
        )
        
        # Invoke Lambda
        response = lambda_client.invoke(FunctionName=function_name)
        print(f"Lambda response: {response['Payload'].read().decode()}")
    
    if __name__ == "__main__":
        create_and_invoke_lambda('my-lambda')
    ```
## 24. **How do you use** `boto3` **to manage IAM roles and policies (e.g., attach a policy to a role)?**\
    **Answer**: Use the IAM client to create roles, policies, and attach policies using `attach_role_policy`.

    ```python
    import boto3
    
    def attach_policy_to_role(role_name, policy_arn):
        iam = boto3.client('iam')
        iam.attach_role_policy(RoleName=role_name, PolicyArn=policy_arn)
        print(f"Attached {policy_arn} to {role_name}")
    
    if __name__ == "__main__":
        attach_policy_to_role('my-role', 'arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess')
    ```
## 25. **Explain how to use** `boto3` **to interact with AWS CloudFormation stacks (e.g., create, update, delete).**\
    **Answer**: Use the CloudFormation client’s `create_stack`, `update_stack`, and `delete_stack` methods, passing stack templates and parameters.

    ```python
    import boto3
    
    def manage_cloudformation_stack(stack_name, template_body):
        cf = boto3.client('cloudformation')
        cf.create_stack(StackName=stack_name, TemplateBody=template_body)
        cf.get_waiter('stack_create_complete').wait(StackName=stack_name)
        print(f"Stack {stack_name} created")
    
    if __name__ == "__main__":
        template = '''
        Resources:
          MyBucket:
            Type: AWS::S3::Bucket
        '''
        manage_cloudformation_stack('my-stack', template)
    ```
## 26. **Write a** `boto3` **script to monitor CloudWatch metrics for an EC2 instance.**

    ```python
    import boto3
    from datetime import datetime, timedelta
    
    def monitor_ec2_metrics(instance_id):
        cloudwatch = boto3.client('cloudwatch')
        response = cloudwatch.get_metric_statistics(
            Namespace='AWS/EC2',
            MetricName='CPUUtilization',
            Dimensions=[{'Name': 'InstanceId', 'Value': instance_id}],
            StartTime=datetime.utcnow() - timedelta(minutes=60),
            EndTime=datetime.utcnow(),
            Period=300,
            Statistics=['Average']
        )
        for datapoint in response['Datapoints']:
            print(f"Time: {datapoint['Timestamp']}, CPU: {datapoint['Average']}%")
    
    if __name__ == "__main__":
        monitor_ec2_metrics('i-1234567890abcdef0')
    ```
## 27. **How do you handle throttling or rate-limiting errors in** `boto3` **scripts?**\
    **Answer**: Use exponential backoff with `boto3`’s retry configuration or manually implement retries.

    ```python
    import boto3
    from botocore.exceptions import ClientError
    import time
    
    def list_objects_with_retry(bucket_name):
        s3 = boto3.client('s3')
        retries = 3
        for attempt in range(retries):
            try:
                response = s3.list_objects_v2(Bucket=bucket_name)
                return response['Contents']
            except ClientError as e:
                if e.response['Error']['Code'] == 'Throttling' and attempt < retries - 1:
                    time.sleep(2 ** attempt)
                else:
                    raise
        return []
    
    if __name__ == "__main__":
        objects = list_objects_with_retry('my-bucket')
        print(objects)
    ```
## 28. **Write a** `boto3` **script to create an SQS queue and send a message to it.**

    ```python
    import boto3
    
    def create_and_send_sqs_message(queue_name, message):
        sqs = boto3.client('sqs')
        response = sqs.create_queue(QueueName=queue_name)
        queue_url = response['QueueUrl']
        sqs.send_message(QueueUrl=queue_url, MessageBody=message)
        print(f"Sent message to {queue_url}")
    
    if __name__ == "__main__":
        create_and_send_sqs_message('my-queue', 'Hello, SQS!')
    ```
## 29. **How do you use** `boto3` **to manage VPC configurations, such as creating a subnet or security group?**\
    **Answer**: Use the EC2 client to create VPC resources like subnets and security groups.

    ```python
    import boto3
    
    def create_vpc_resources(vpc_id):
        ec2 = boto3.client('ec2')
        subnet = ec2.create_subnet(VpcId=vpc_id, CidrBlock='10.0.1.0/24')
        sg = ec2.create_security_group(GroupName='my-sg', Description='My security group', VpcId=vpc_id)
        print(f"Created subnet: {subnet['Subnet']['SubnetId']}, SG: {sg['GroupId']}")
    
    if __name__ == "__main__":
        create_vpc_resources('vpc-12345678')
    ```
## 30. **Explain how to use** `boto3` **with AWS STS to assume a role for cross-account access.**\
    **Answer**: Use the STS client’s `assume_role` method to obtain temporary credentials for a role in another account.

    ```python
    import boto3
    
    def assume_role(role_arn, session_name):
        sts = boto3.client('sts')
        response = sts.assume_role(RoleArn=role_arn, RoleSessionName=session_name)
        credentials = response['Credentials']
        s3 = boto3.client(
            's3',
            aws_access_key_id=credentials['AccessKeyId'],
            aws_secret_access_key=credentials['SecretAccessKey'],
            aws_session_token=credentials['SessionToken']
        )
        buckets = s3.list_buckets()
        print(buckets['Buckets'])
    
    if __name__ == "__main__":
        assume_role('arn:aws:iam::123456789012:role/MyRole', 'MySession')
    ```



# Answers to boto3 Technical Questions for Kubernetes, Terraform, and Ansible

# Technical Questions (boto3 with Kubernetes)

## 31. How can `boto3` be used to interact with an Amazon EKS cluster (e.g., retrieving cluster details)?

**Answer**: `boto3` interacts with Amazon EKS via the `eks` client, allowing you to retrieve cluster details, manage node groups, and configure add-ons. For example, you can use the `describe_cluster` method to fetch details like the cluster’s status, endpoint, and version.

```python
import boto3

def get_eks_cluster_details(cluster_name, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.describe_cluster(name=cluster_name)
    return response['cluster']

# Example usage
cluster_details = get_eks_cluster_details('my-eks-cluster')
print(f"Cluster Name: {cluster_details['name']}, Status: {cluster_details['status']}")
```

## 32. Write a `boto3` script to list all EKS clusters in a given region.

**Answer**: The `list_clusters` method retrieves all EKS clusters in a specified region, handling pagination if necessary.

```python
import boto3

def list_eks_clusters(region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    clusters = []
    paginator = eks_client.get_paginator('list_clusters')
    for page in paginator.paginate():
        clusters.extend(page['clusters'])
    return clusters

# Example usage
clusters = list_eks_clusters()
for cluster in clusters:
    print(f"Cluster: {cluster}")
```

## 33. How do you use `boto3` to update the configuration of an EKS cluster (e.g., scaling node groups)?

**Answer**: To scale node groups, use the `update_nodegroup_config` method to modify the desired number of nodes. You need the cluster name and node group name.

```python
import boto3

def scale_eks_nodegroup(cluster_name, nodegroup_name, desired_size, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.update_nodegroup_config(
        clusterName=cluster_name,
        nodegroupName=nodegroup_name,
        scalingConfig={
            'minSize': desired_size,
            'maxSize': desired_size + 2,
            'desiredSize': desired_size
        }
    )
    return response

# Example usage
response = scale_eks_nodegroup('my-eks-cluster', 'my-nodegroup', 3)
print(f"Nodegroup update initiated: {response['update']['id']}")
```

## 34. Explain how `boto3` can be used to manage IAM roles for EKS service accounts.

**Answer**: `boto3` can manage IAM roles for EKS service accounts by creating roles, attaching policies, and associating them with Kubernetes service accounts via annotations. Use the `iam` client to create and configure roles, and ensure the role’s trust policy allows EKS to assume it.

```python
import boto3
import json

def create_eks_service_account_role(role_name, cluster_name, region='us-east-1'):
    iam_client = boto3.client('iam', region_name=region)
    trust_policy = {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": {"Federated": f"arn:aws:iam::123456789012:oidc-provider/oidc.eks.{region}.amazonaws.com/id/EXAMPLED539D4633E53DE1B716D3041E"},
                "Action": "sts:AssumeRoleWithWebIdentity",
                "Condition": {
                    "StringEquals": {
                        f"oidc.eks.{region}.amazonaws.com/id/EXAMPLED539D4633E53DE1B716D3041E:sub": "system:serviceaccount:default:my-sa"
                    }
                }
            }
        ]
    }
    response = iam_client.create_role(
        RoleName=role_name,
        AssumeRolePolicyDocument=json.dumps(trust_policy)
    )
    iam_client.attach_role_policy(
        RoleName=role_name,
        PolicyArn='arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess'
    )
    return response['Role']['Arn']

# Example usage
role_arn = create_eks_service_account_role('eks-sa-role', 'my-eks-cluster')
print(f"Role ARN: {role_arn}")
```

## 35. Write a `boto3` script to create an EKS node group for an existing cluster.

**Answer**: The `create_nodegroup` method creates a node group with specified instance types, scaling configurations, and IAM roles.

```python
import boto3

def create_eks_nodegroup(cluster_name, nodegroup_name, role_arn, subnets, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.create_nodegroup(
        clusterName=cluster_name,
        nodegroupName=nodegroup_name,
        scalingConfig={
            'minSize': 1,
            'maxSize': 3,
            'desiredSize': 2
        },
        instanceTypes=['t3.medium'],
        subnets=subnets,
        nodeRole=role_arn
    )
    return response

# Example usage
response = create_eks_nodegroup(
    'my-eks-cluster', 
    'my-nodegroup', 
    'arn:aws:iam::123456789012:role/EKSNodeRole', 
    ['subnet-12345678', 'subnet-87654321']
)
print(f"Nodegroup creation initiated: {response['nodegroup']['nodegroupArn']}")
```

## 36. How do you integrate `boto3` with Kubernetes Python libraries (e.g., `kubernetes` client) to manage EKS resources?

**Answer**: Use `boto3` to retrieve EKS cluster details (e.g., endpoint, certificate) and configure the `kubernetes` client to interact with the cluster. The `boto3` `eks` client provides the cluster’s authentication data, which is used to set up the Kubernetes API client.

```python
import boto3
from kubernetes import client, config
from kubernetes.client.rest import ApiException

def configure_k8s_client(cluster_name, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    cluster = eks_client.describe_cluster(name=cluster_name)['cluster']
    configuration = client.Configuration()
    configuration.host = cluster['endpoint']
    configuration.ssl_ca_cert = cluster['certificateAuthority']['data']
    configuration.api_key['authorization'] = boto3.client('sts').get_caller_identity()['Arn']
    k8s_client = client.ApiClient(configuration)
    return client.CoreV1Api(k8s_client)

# Example usage: List pods
k8s_api = configure_k8s_client('my-eks-cluster')
pods = k8s_api.list_pod_for_all_namespaces()
for pod in pods.items:
    print(f"Pod: {pod.metadata.name}")
```

## 37. How can `boto3` be used to retrieve logs from an EKS cluster stored in CloudWatch?

**Answer**: EKS cluster logs are stored in CloudWatch Logs. Use the `logs` client in `boto3` to query log groups and streams associated with the cluster.

```python
import boto3
from datetime import datetime, timedelta

def get_eks_logs(cluster_name, region='us-east-1'):
    logs_client = boto3.client('logs', region_name=region)
    log_group = f"/aws/eks/{cluster_name}/cluster"
    response = logs_client.filter_log_events(
        logGroupName=log_group,
        startTime=int((datetime.now() - timedelta(hours=1)).timestamp() * 1000),
        endTime=int(datetime.now().timestamp() * 1000)
    )
    return response['events']

# Example usage
logs = get_eks_logs('my-eks-cluster')
for log in logs:
    print(f"Log: {log['message']}")
```

## 38. Explain how to use `boto3` to configure autoscaling for an EKS cluster.

**Answer**: Autoscaling for EKS node groups is managed via the `update_nodegroup_config` method to set scaling policies. Additionally, the `autoscaling` client can be used to configure Auto Scaling groups for managed node groups.

```python
import boto3

def configure_eks_autoscaling(cluster_name, nodegroup_name, min_size, max_size, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.update_nodegroup_config(
        clusterName=cluster_name,
        nodegroupName=nodegroup_name,
        scalingConfig={
            'minSize': min_size,
            'maxSize': max_size,
            'desiredSize': min_size
        }
    )
    return response

# Example usage
response = configure_eks_autoscaling('my-eks-cluster', 'my-nodegroup', 2, 5)
print(f"Autoscaling configured: {response['update']['id']}")
```

## 39. Write a `boto3` script to delete an EKS cluster and its associated resources.

**Answer**: Deleting an EKS cluster involves removing node groups first, then the cluster itself using the `delete_nodegroup` and `delete_cluster` methods.

```python
import boto3
import time

def delete_eks_cluster(cluster_name, nodegroup_name, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    # Delete node group
    eks_client.delete_nodegroup(clusterName=cluster_name, nodegroupName=nodegroup_name)
    while eks_client.describe_nodegroup(clusterName=cluster_name, nodegroupName=nodegroup_name)['nodegroup']['status'] != 'DELETED':
        print("Waiting for nodegroup deletion...")
        time.sleep(30)
    # Delete cluster
    eks_client.delete_cluster(name=cluster_name)
    while eks_client.describe_cluster(name=cluster_name)['cluster']['status'] != 'DELETED':
        print("Waiting for cluster deletion...")
        time.sleep(30)
    print("Cluster and nodegroup deleted.")

# Example usage
delete_eks_cluster('my-eks-cluster', 'my-nodegroup')
```

## 40. How do you use `boto3` to manage EKS add-ons (e.g., VPC-CNI, CoreDNS)?

**Answer**: Use the `create_addon`, `describe_addon`, and `delete_addon` methods to manage EKS add-ons like VPC-CNI or CoreDNS.

```python
import boto3

def manage_eks_addon(cluster_name, addon_name, version, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.create_addon(
        clusterName=cluster_name,
        addonName=addon_name,
        addonVersion=version
    )
    return response

# Example usage
response = manage_eks_addon('my-eks-cluster', 'vpc-cni', 'v1.10.1-eksbuild.1')
print(f"Addon created: {response['addon']['addonArn']}")
```

# Technical Questions (boto3 with Terraform)

## 41. How can `boto3` be used to validate or inspect resources created by Terraform in AWS?

**Answer**: `boto3` can query AWS resources (e.g., EC2, S3) to validate their existence, configuration, or tags against Terraform’s expected state. Compare resource attributes with Terraform’s state file output.

```python
import boto3

def validate_terraform_resources(resource_type, expected_tags):
    client = boto3.client(resource_type)
    if resource_type == 'ec2':
        response = client.describe_instances()
        for reservation in response['Reservations']:
            for instance in reservation['Instances']:
                tags = {tag['Key']: tag['Value'] for tag in instance.get('Tags', [])}
                if all(tag in tags.items() for tag in expected_tags.items()):
                    print(f"Validated EC2 instance: {instance['InstanceId']}")

# Example usage
validate_terraform_resources('ec2', {'Terraform': 'true', 'Environment': 'dev'})
```

## 42. Write a `boto3` script to list all resources in an AWS account created by a specific Terraform state file.

**Answer**: Parse the Terraform state file to identify resources, then use `boto3` to verify their existence in AWS.

```python
import boto3
import json

def list_terraform_resources(state_file_path):
    with open(state_file_path, 'r') as f:
        state = json.load(f)
    resources = state['resources']
    ec2_client = boto3.client('ec2')
    for resource in resources:
        if resource['type'] == 'aws_instance':
            instance_id = resource['instances'][0]['attributes']['id']
            response = ec2_client.describe_instances(InstanceIds=[instance_id])
            print(f"Found EC2 instance: {instance_id}")

# Example usage
list_terraform_resources('terraform.tfstate')
```

## 43. How do you use `boto3` to automate post-provisioning tasks for resources created by Terraform (e.g., configuring an EC2 instance)?

**Answer**: Use `boto3` to perform tasks like tagging, starting/stopping instances, or configuring security groups after Terraform creates the resources.

```python
import boto3

def configure_ec2_instance(instance_id, region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    ec2_client.create_tags(
        Resources=[instance_id],
        Tags=[{'Key': 'PostConfigured', 'Value': 'true'}]
    )
    ec2_client.start_instances(InstanceIds=[instance_id])
    print(f"Configured and started EC2 instance: {instance_id}")

# Example usage
configure_ec2_instance('i-1234567890abcdef0')
```

## 44. Explain how to use `boto3` to interact with Terraform-managed S3 buckets for state storage.

**Answer**: Use the `s3` client to manage buckets used as Terraform backends, such as enabling versioning, encryption, or checking state file existence.

```python
import boto3

def manage_terraform_s3_backend(bucket_name, region='us-east-1'):
    s3_client = boto3.client('s3', region_name=region)
    s3_client.put_bucket_versioning(
        Bucket=bucket_name,
        VersioningConfiguration={'Status': 'Enabled'}
    )
    response = s3_client.list_objects_v2(Bucket=bucket_name, Prefix='terraform.tfstate')
    if 'Contents' in response:
        print(f"Terraform state file found in {bucket_name}")
    else:
        print(f"No state file found in {bucket_name}")

# Example usage
manage_terraform_s3_backend('my-terraform-backend')
```

## 45. Write a `boto3` script to check the status of a Terraform-managed CloudFormation stack.

**Answer**: Use the `cloudformation` client to query the stack’s status.

```python
import boto3

def check_cloudformation_stack(stack_name, region='us-east-1'):
    cf_client = boto3.client('cloudformation', region_name=region)
    response = cf_client.describe_stacks(StackName=stack_name)
    stack = response['Stacks'][0]
    print(f"Stack {stack_name} Status: {stack['StackStatus']}")
    return stack['StackStatus']

# Example usage
check_cloudformation_stack('my-terraform-stack')
```

## 46. How can `boto3` be used to trigger Terraform operations programmatically (e.g., via AWS Lambda)?

**Answer**: Deploy a Lambda function with `boto3` to invoke Terraform commands via an S3 trigger or API call. The function can run `terraform apply` or `plan` using a subprocess or AWS CodeBuild.

```python
import boto3
import subprocess

def lambda_handler(event, context):
    s3_client = boto3.client('s3')
    s3_client.download_file('my-terraform-bucket', 'terraform/main.tf', '/tmp/main.tf')
    result = subprocess.run(['terraform', 'apply', '-auto-approve'], cwd='/tmp', capture_output=True)
    return {
        'statusCode': 200,
        'body': result.stdout.decode()
    }
```

## 47. Explain how to use `boto3` to clean up orphaned resources not managed by Terraform.

**Answer**: Query AWS resources with `boto3` and compare them against Terraform’s state file. Delete resources not found in the state file.

```python
import boto3
import json

def clean_orphaned_resources(state_file_path, region='us-east-1'):
    with open(state_file_path, 'r') as f:
        state = json.load(f)
    managed_ids = [r['instances'][0]['attributes']['id'] for r in state['resources'] if r['type'] == 'aws_instance']
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.describe_instances()
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            if instance['InstanceId'] not in managed_ids:
                ec2_client.terminate_instances(InstanceIds=[instance['InstanceId']])
                print(f"Terminated orphaned instance: {instance['InstanceId']}")

# Example usage
clean_orphaned_resources('terraform.tfstate')
```

## 48. Write a `boto3` script to enable versioning on an S3 bucket used as a Terraform backend.

**Answer**: Use the `put_bucket_versioning` method to enable versioning.

```python
import boto3

def enable_s3_versioning(bucket_name, region='us-east-1'):
    s3_client = boto3.client('s3', region_name=region)
    response = s3_client.put_bucket_versioning(
        Bucket=bucket_name,
        VersioningConfiguration={'Status': 'Enabled'}
    )
    print(f"Versioning enabled on {bucket_name}")
    return response

# Example usage
enable_s3_versioning('my-terraform-backend')
```

## 49. How do you integrate `boto3` with Terraform’s AWS provider to manage dynamic credentials?

**Answer**: Use `boto3` to assume an IAM role or generate temporary credentials via STS, then pass these to Terraform’s AWS provider using environment variables or a credentials file.

```python
import boto3
import os

def set_terraform_credentials(role_arn, region='us-east-1'):
    sts_client = boto3.client('sts')
    response = sts_client.assume_role(RoleArn=role_arn, RoleSessionName='TerraformSession')
    credentials = response['Credentials']
    os.environ['AWS_ACCESS_KEY_ID'] = credentials['AccessKeyId']
    os.environ['AWS_SECRET_ACCESS_KEY'] = credentials['SecretAccessKey']
    os.environ['AWS_SESSION_TOKEN'] = credentials['SessionToken']
    print("Terraform credentials set.")

# Example usage
set_terraform_credentials('arn:aws:iam::123456789012:role/TerraformRole')
```

## 50. Explain how to use `boto3` to monitor drift in Terraform-managed infrastructure.

**Answer**: Compare the current state of AWS resources (via `boto3`) with Terraform’s state file to detect drift. Check attributes like tags, instance types, or security groups.

```python
import boto3
import json

def detect_drift(state_file_path, region='us-east-1'):
    with open(state_file_path, 'r') as f:
        state = json.load(f)
    ec2_client = boto3.client('ec2', region_name=region)
    for resource in state['resources']:
        if resource['type'] == 'aws_instance':
            instance_id = resource['instances'][0]['attributes']['id']
            response = ec2_client.describe_instances(InstanceIds=[instance_id])
            actual_tags = response['Reservations'][0]['Instances'][0].get('Tags', [])
            expected_tags = resource['instances'][0]['attributes']['tags']
            if actual_tags != expected_tags:
                print(f"Drift detected for instance {instance_id}: Tags differ")

# Example usage
detect_drift('terraform.tfstate')
```

# Technical Questions (boto3 with Ansible)

## 51. How can `boto3` be used to complement Ansible playbooks for AWS resource management?

**Answer**: `boto3` can create or query AWS resources before or after Ansible playbooks run, providing dynamic inventory, validating resource states, or performing tasks Ansible’s AWS modules don’t support.

```python
import boto3

def prepare_aws_resources():
    ec2_client = boto3.client('ec2')
    response = ec2_client.run_instances(
        ImageId='ami-12345678',
        InstanceType='t2.micro',
        MinCount=1,
        MaxCount=1
    )
    instance_id = response['Instances'][0]['InstanceId']
    print(f"Created instance: {instance_id}")
    return instance_id

# Example usage
instance_id = prepare_aws_resources()
```

## 52. Write a `boto3` script to generate an inventory of AWS resources for use in an Ansible playbook.

**Answer**: Query AWS resources and output them in a format compatible with Ansible’s dynamic inventory.

```python
import boto3
import json

def generate_ansible_inventory(region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.describe_instances()
    inventory = {'webservers': {'hosts': []}}
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            if instance['State']['Name'] == 'running':
                inventory['webservers']['hosts'].append(instance['PublicIpAddress'])
    with open('inventory.json', 'w') as f:
        json.dump(inventory, f)
    print("Ansible inventory generated.")

# Example usage
generate_ansible_inventory()
```

## 53. How do you use `boto3` to validate the state of AWS resources before running an Ansible playbook?

**Answer**: Use `boto3` to check resource attributes (e.g., instance status, tags) to ensure they meet playbook prerequisites.

```python
import boto3

def validate_aws_resources(instance_id, region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.describe_instances(InstanceIds=[instance_id])
    instance = response['Reservations'][0]['Instances'][0]
    if instance['State']['Name'] != 'running':
        raise Exception(f"Instance {instance_id} is not running")
    print(f"Instance {instance_id} is ready for Ansible playbook")
    return True

# Example usage
validate_aws_resources('i-1234567890abcdef0')
```

## 54. Explain how to use `boto3` to create AWS resources that Ansible will later configure.

**Answer**: Use `boto3` to provision resources like EC2 instances or S3 buckets, then pass their IDs or ARNs to Ansible for configuration (e.g., installing software).

```python
import boto3

def create_aws_resources(region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.run_instances(
        ImageId='ami-12345678',
        InstanceType='t2.micro',
        MinCount=1,
        MaxCount=1
    )
    instance_id = response['Instances'][0]['InstanceId']
    print(f"Created instance {instance_id} for Ansible configuration")
    return instance_id

# Example usage
instance_id = create_aws_resources()
```

## 55. Write a `boto3` script to tag EC2 instances for Ansible dynamic inventory.

**Answer**: Use the `create_tags` method to add tags that Ansible can use for grouping hosts.

```python
import boto3

def tag_ec2_instances(instance_ids, tags, region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    ec2_client.create_tags(
        Resources=instance_ids,
        Tags=[{'Key': k, 'Value': v} for k, v in tags.items()]
    )
    print(f"Tagged instances: {instance_ids}")

# Example usage
tag_ec2_instances(['i-1234567890abcdef0'], {'AnsibleGroup': 'webservers'})
```

## 56. How can `boto3` be used to automate the setup of Ansible’s AWS modules (e.g., ensuring `boto3` is installed)?

**Answer**: Use `boto3` to check for prerequisites (e.g., Python, `boto3`) on an EC2 instance via SSM or a script, then install them if missing.

```python
import boto3

def ensure_ansible_prerequisites(instance_id, region='us-east-1'):
    ssm_client = boto3.client('ssm', region_name=region)
    response = ssm_client.send_command(
        InstanceIds=[instance_id],
        DocumentName='AWS-RunShellScript',
        Parameters={'commands': ['pip install boto3']}
    )
    print(f"Ensured boto3 installation on {instance_id}")
    return response['Command']['CommandId']

# Example usage
ensure_ansible_prerequisites('i-1234567890abcdef0')
```

## 57. Explain how to use `boto3` to retrieve AWS resource details for Ansible’s `ec2_instance` module.

**Answer**: Query resource details (e.g., instance IDs, IPs) with `boto3` and pass them to Ansible via a JSON file or environment variables.

```python
import boto3

def get_ec2_details(instance_id, region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.describe_instances(InstanceIds=[instance_id])
    instance = response['Reservations'][0]['Instances'][0]
    details = {
        'instance_id': instance['InstanceId'],
        'public_ip': instance.get('PublicIpAddress', 'N/A')
    }
    with open('ec2_details.json', 'w') as f:
        json.dump(details, f)
    print(f"EC2 details saved for Ansible")
    return details

# Example usage
get_ec2_details('i-1234567890abcdef0')
```

## 58. Write a `boto3` script to create an SNS topic that Ansible can use for notifications.

**Answer**: Use the `sns` client to create a topic and return its ARN for Ansible.

```python
import boto3

def create_sns_topic(topic_name, region='us-east-1'):
    sns_client = boto3.client('sns', region_name=region)
    response = sns_client.create_topic(Name=topic_name)
    topic_arn = response['TopicArn']
    print(f"Created SNS topic: {topic_arn}")
    return topic_arn

# Example usage
create_sns_topic('ansible-notifications')
```

## 59. How do you integrate `boto3` with Ansible to handle AWS resource failures dynamically?

**Answer**: Use `boto3` to monitor resource health (e.g., via CloudWatch) and trigger Ansible playbooks to remediate failures, such as restarting instances or recreating resources.

```python
import boto3

def handle_ec2_failure(instance_id, region='us-east-1'):
    ec2_client = boto3.client('ec2', region_name=region)
    response = ec2_client.describe_instances(InstanceIds=[instance_id])
    if response['Reservations'][0]['Instances'][0]['State']['Name'] != 'running':
        ec2_client.start_instances(InstanceIds=[instance_id])
        print(f"Restarted failed instance: {instance_id}")
    return True

# Example usage
handle_ec2_failure('i-1234567890abcdef0')
```

## 60. Explain how to use `boto3` to manage AWS credentials for Ansible’s AWS modules securely.

**Answer**: Use `boto3` with STS to generate temporary credentials or assume roles, then pass them to Ansible via environment variables or a credentials file.

```python
import boto3
import os

def set_ansible_credentials(role_arn, region='us-east-1'):
    sts_client = boto3.client('sts')
    response = sts_client.assume_role(RoleArn=role_arn, RoleSessionName='AnsibleSession')
    credentials = response['Credentials']
    os.environ['AWS_ACCESS_KEY_ID'] = credentials['AccessKeyId']
    os.environ['AWS_SECRET_ACCESS_KEY'] = credentials['SecretAccessKey']
    os.environ['AWS_SESSION_TOKEN'] = credentials['SessionToken']
    print("Ansible credentials set securely.")

# Example usage
set_ansible_credentials('arn:aws:iam::123456789012:role/AnsibleRole')
```

## Technical Questions (boto3 with GitHub Actions)

### 61. How can `boto3` be used in a GitHub Actions workflow to manage AWS resources?

**Answer**: `boto3` can be used in GitHub Actions to automate AWS resource management by executing Python scripts within workflow steps. The workflow sets up a Python environment, installs `boto3`, configures AWS credentials, and runs scripts to create, update, or delete resources like S3 buckets, EC2 instances, or Lambda functions. Secure credential management (e.g., GitHub Secrets) and proper IAM roles are critical.

---

### 62. Write a GitHub Actions workflow that uses `boto3` to deploy a Lambda function.

**Answer**: Below is a workflow that deploys a Lambda function using `boto3`. It zips a Python script, uploads it to S3, and updates the Lambda function code.

```yaml
name: Deploy Lambda Function
on:
  push:
    branches: [main]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install boto3
    - name: Zip Lambda code
      run: zip -r lambda_function.zip lambda_function.py
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Deploy Lambda
      run: python deploy_lambda.py
```

```python
# deploy_lambda.py
import boto3

def deploy_lambda():
    s3_client = boto3.client('s3')
    lambda_client = boto3.client('lambda')
    bucket_name = 'my-lambda-bucket'
    function_name = 'MyLambdaFunction'
    
    # Upload zip to S3
    s3_client.upload_file('lambda_function.zip', bucket_name, 'lambda_function.zip')
    
    # Update Lambda function code
    lambda_client.update_function_code(
        FunctionName=function_name,
        S3Bucket=bucket_name,
        S3Key='lambda_function.zip'
    )
    print(f"Updated Lambda function: {function_name}")

if __name__ == "__main__":
    deploy_lambda()
```

---

### 63. How do you configure AWS credentials in GitHub Actions to allow `boto3` scripts to run securely?

**Answer**: To configure AWS credentials securely:
- Store credentials (e.g., `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`) in GitHub Secrets.
- Use the `aws-actions/configure-aws-credentials` action to set credentials in the workflow.
- Prefer IAM roles with temporary credentials via OIDC for better security.
- Avoid hardcoding credentials in scripts or workflow files.

```yaml
steps:
- name: Configure AWS Credentials
  uses: aws-actions/configure-aws-credentials@v2
  with:
    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
    aws-region: us-east-1
```

---

### 64. Explain how to use `boto3` in a GitHub Actions workflow to validate an S3 bucket’s contents.

**Answer**: A `boto3` script can list objects in an S3 bucket and check for expected files or properties (e.g., size, metadata). The workflow runs the script after configuring AWS credentials and fails if validation criteria are not met.

```yaml
name: Validate S3 Bucket
on: [push]
jobs:
  validate:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Validate S3
      run: python validate_s3.py
```

```python
# validate_s3.py
import boto3
import sys

def validate_s3_bucket(bucket_name):
    s3_client = boto3.client('s3')
    response = s3_client.list_objects_v2(Bucket=bucket_name)
    if 'Contents' not in response:
        print("Bucket is empty!")
        sys.exit(1)
    for obj in response['Contents']:
        print(f"Found: {obj['Key']}, Size: {obj['Size']}")
    print("Validation successful!")

if __name__ == "__main__":
    validate_s3_bucket('my-bucket')
```

---

### 65. Write a `boto3` script to be executed in a GitHub Actions workflow to scale an EKS cluster.

**Answer**: The script updates the desired capacity of an EKS node group to scale the cluster.

```python
# scale_eks.py
import boto3

def scale_eks_cluster(cluster_name, nodegroup_name, desired_capacity):
    eks_client = boto3.client('eks')
    eks_client.update_nodegroup_config(
        clusterName=cluster_name,
        nodegroupName=nodegroup_name,
        scalingConfig={'desiredSize': desired_capacity}
    )
    print(f"Scaled {nodegroup_name} to {desired_capacity} nodes")

if __name__ == "__main__":
    scale_eks_cluster('my-cluster', 'my-nodegroup', 3)
```

```yaml
name: Scale EKS Cluster
on: [workflow_dispatch]
jobs:
  scale:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Scale EKS
      run: python scale_eks.py
```

---

### 66. How do you use `boto3` in GitHub Actions to publish test results to an SNS topic?

**Answer**: A `boto3` script publishes test results as a message to an SNS topic. The workflow runs tests, captures results, and uses the script to send the message.

```yaml
name: Publish Test Results
on: [push]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install dependencies
      run: pip install boto3 pytest
    - name: Run tests
      run: pytest --junitxml=report.xml
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Publish to SNS
      run: python publish_sns.py
```

```python
# publish_sns.py
import boto3
import json

def publish_test_results(topic_arn, message):
    sns_client = boto3.client('sns')
    sns_client.publish(
        TopicArn=topic_arn,
        Message=json.dumps({'test_results': message}),
        Subject='Test Results'
    )
    print("Published to SNS")

if __name__ == "__main__":
    publish_test_results('arn:aws:sns:us-east-1:123456789012:TestTopic', 'Tests passed!')
```

---

### 67. Write a GitHub Actions workflow that uses `boto3` to create an S3 bucket and upload artifacts.

**Answer**: The workflow creates an S3 bucket and uploads a file using `boto3`.

```yaml
name: Create S3 Bucket and Upload
on: [push]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Create and Upload to S3
      run: python s3_upload.py
```

```python
# s3_upload.py
import boto3

def create_and_upload(bucket_name, file_path):
    s3_client = boto3.client('s3')
    try:
        s3_client.create_bucket(Bucket=bucket_name)
        print(f"Created bucket: {bucket_name}")
    except s3_client.exceptions.BucketAlreadyOwnedByYou:
        print(f"Bucket {bucket_name} already exists")
    s3_client.upload_file(file_path, bucket_name, file_path)
    print(f"Uploaded {file_path} to {bucket_name}")

if __name__ == "__main__":
    create_and_upload('my-unique-bucket-123', 'artifact.txt')
```

---

### 68. How can `boto3` be used in GitHub Actions to trigger a CloudFormation stack deployment?

**Answer**: A `boto3` script creates or updates a CloudFormation stack. The workflow runs the script with a template file.

```yaml
name: Deploy CloudFormation Stack
on: [push]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Deploy Stack
      run: python deploy_cloudformation.py
```

```python
# deploy_cloudformation.py
import boto3

def deploy_stack(stack_name, template_file):
    cf_client = boto3.client('cloudformation')
    with open(template_file, 'r') as f:
        template_body = f.read()
    try:
        cf_client.create_stack(
            StackName=stack_name,
            TemplateBody=template_body,
            Capabilities=['CAPABILITY_IAM']
        )
        cf_client.get_waiter('stack_create_complete').wait(StackName=stack_name)
        print(f"Stack {stack_name} created")
    except cf_client.exceptions.AlreadyExistsException:
        cf_client.update_stack(
            StackName=stack_name,
            TemplateBody=template_body,
            Capabilities=['CAPABILITY_IAM']
        )
        cf_client.get_waiter('stack_update_complete').wait(StackName=stack_name)
        print(f"Stack {stack_name} updated")

if __name__ == "__main__":
    deploy_stack('MyStack', 'template.yml')
```

---

### 69. Explain how to handle `boto3` errors in a GitHub Actions workflow to prevent pipeline failures.

**Answer**: To handle `boto3` errors:
- Use try-except blocks to catch specific AWS exceptions (e.g., `ClientError`).
- Log error details for debugging.
- Implement retry logic for transient errors (e.g., throttling).
- Use `continue-on-error` in workflow steps to prevent pipeline failure for non-critical errors.
- Exit with appropriate status codes to signal success or failure.

```python
# handle_errors.py
import boto3
from botocore.exceptions import ClientError
import sys

def safe_s3_operation(bucket_name):
    s3_client = boto3.client('s3')
    try:
        s3_client.head_bucket(Bucket=bucket_name)
        print(f"Bucket {bucket_name} exists")
    except ClientError as e:
        error_code = e.response['Error']['Code']
        if error_code == '404':
            print(f"Bucket {bucket_name} does not exist")
        else:
            print(f"Error: {e}")
            sys.exit(1)

if __name__ == "__main__":
    safe_s3_operation('my-bucket')
```

```yaml
name: Safe S3 Check
on: [push]
jobs:
  check:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Check S3 Bucket
      continue-on-error: true
      run: python handle_errors.py
```

---

### 70. Write a `boto3` script for a GitHub Actions workflow to clean up unused EC2 instances.

**Answer**: The script terminates EC2 instances with a specific tag if they are older than a threshold.

```python
# cleanup_ec2.py
import boto3
from datetime import datetime, timedelta, timezone

def cleanup_ec2_instances(tag_key, tag_value, max_age_days=7):
    ec2_client = boto3.client('ec2')
    response = ec2_client.describe_instances(
        Filters=[{'Name': f'tag:{tag_key}', 'Values': [tag_value]}]
    )
    cutoff = datetime.now(timezone.utc) - timedelta(days=max_age_days)
    
    for reservation in response['Reservations']:
        for instance in reservation['Instances']:
            launch_time = instance['LaunchTime']
            if launch_time < cutoff:
                instance_id = instance['InstanceId']
                ec2_client.terminate_instances(InstanceIds=[instance_id])
                print(f"Terminated instance: {instance_id}")

if __name__ == "__main__":
    cleanup_ec2_instances('Environment', 'Temp')
```

```yaml
name: Cleanup EC2 Instances
on: [schedule: { cron: '0 0 * * *' }]
jobs:
  cleanup:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    - name: Cleanup EC2
      run: python cleanup_ec2.py
```

---

## Scenario-Based Questions

### 71. Scenario: Your team’s GitHub Actions workflow is failing because the `boto3` script cannot authenticate with AWS. How would you troubleshoot and resolve this issue?

**Answer**:
- **Check Secrets**: Verify that `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` are correctly set in GitHub Secrets.
- **Validate IAM Permissions**: Ensure the IAM user/role has the necessary permissions for the AWS actions.
- **Inspect Workflow**: Confirm the `aws-actions/configure-aws-credentials` action is correctly configured.
- **Check Region**: Ensure the correct AWS region is specified.
- **Debug Logs**: Enable debug logging (`ACTIONS_STEP_DEBUG: true`) to inspect credential setup errors.
- **Use OIDC**: Switch to OIDC-based authentication with an IAM role for better security.

---

### 72. Scenario: A `boto3` script is timing out when listing thousands of objects in an S3 bucket. How would you optimize the script to handle large buckets efficiently?

**Answer**: To optimize:
- Use pagination with `list_objects_v2` and a `MaxKeys` limit.
- Process objects in batches to avoid memory issues.
- Implement retry logic for transient errors.
- Use boto3’s `paginator` to handle large result sets efficiently.

```python
import boto3
from botocore.exceptions import ClientError

def list_s3_objects(bucket_name):
    s3_client = boto3.client('s3')
    paginator = s3_client.get_paginator('list_objects_v2')
    try:
        for page in paginator.paginate(Bucket=bucket_name, PaginationConfig={'MaxItems': 1000}):
            if 'Contents' in page:
                for obj in page['Contents']:
                    print(f"Object: {obj['Key']}")
    except ClientError as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    list_s3_objects('my-large-bucket')
```

---

### 73. Scenario: You need to automate the creation of an EKS cluster using `boto3` and then configure it with Ansible. Describe the steps to achieve this.

**Answer**:
1. **Create EKS Cluster with boto3**:
   - Use `boto3` to create an EKS cluster and node group.
   - Wait for the cluster to become active.
2. **Generate Kubeconfig**:
   - Use `boto3` to retrieve cluster details and generate a kubeconfig file.
3. **Run Ansible Playbook**:
   - Configure Ansible to use the kubeconfig file.
   - Deploy Kubernetes resources (e.g., deployments, services) with Ansible’s `k8s` module.
4. **Integrate in GitHub Actions**:
   - Run the `boto3` script and Ansible playbook in a workflow.

```python
# create_eks.py
import boto3
import time

def create_eks_cluster(cluster_name, role_arn, subnets):
    eks_client = boto3.client('eks')
    eks_client.create_cluster(
        name=cluster_name,
        roleArn=role_arn,
        resourcesVpcConfig={'subnetIds': subnets}
    )
    while eks_client.describe_cluster(name=cluster_name)['cluster']['status'] != 'ACTIVE':
        print("Waiting for cluster...")
        time.sleep(30)
    print("Cluster created")
```

---

### 74. Scenario: A `boto3` script is failing with a “Rate Exceeded” error when creating multiple EC2 instances. How would you modify the script to handle this?

**Answer**:
- Implement exponential backoff and retry logic using `botocore`.
- Limit the number of concurrent API calls.
- Use batch operations where possible.

```python
import boto3
from botocore.exceptions import ClientError
import time

def create_ec2_instances(instance_count):
    ec2_client = boto3.client('ec2')
    for i in range(instance_count):
        try:
            ec2_client.run_instances(
                ImageId='ami-12345678',
                InstanceType='t2.micro',
                MinCount=1,
                MaxCount=1
            )
            print(f"Created instance {i+1}")
        except ClientError as e:
            if e.response['Error']['Code'] == 'RequestLimitExceeded':
                time.sleep(2 ** i)  # Exponential backoff
                continue
            raise e

if __name__ == "__main__":
    create_ec2_instances(5)
```

---

### 75. Scenario: Your Terraform-managed infrastructure is out of sync with the actual AWS resources. How would you use `boto3` to detect and report drift?

**Answer**:
- Use `boto3` to query AWS resources (e.g., EC2, S3).
- Compare resource attributes with Terraform state file data.
- Generate a report of discrepancies (e.g., missing resources, modified attributes).

```python
import boto3
import json

def detect_drift(bucket_name):
    s3_client = boto3.client('s3')
    try:
        response = s3_client.head_bucket(Bucket=bucket_name)
        print(f"Bucket {bucket_name} exists in AWS")
        # Compare with Terraform state (assumed to be parsed)
        terraform_state = {'versioning': 'enabled'}
        versioning = s3_client.get_bucket_versioning(Bucket=bucket_name).get('Status')
        if versioning != terraform_state['versioning']:
            print(f"Drift detected: Versioning is {versioning}, expected {terraform_state['versioning']}")
    except s3_client.exceptions.ClientError:
        print(f"Bucket {bucket_name} not found in AWS")

if __name__ == "__main__":
    detect_drift('my-bucket')
```

---

### 76. Scenario: A GitHub Actions workflow using `boto3` to deploy a Lambda function is exposing AWS credentials in the logs. How would you secure the workflow?

**Answer**:
- Use GitHub Secrets to store credentials.
- Use OIDC to assume an IAM role instead of long-term credentials.
- Mask sensitive outputs in logs using `::add-mask::`.
- Avoid logging sensitive data in scripts.

```yaml
name: Secure Lambda Deploy
on: [push]
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.10'
    - name: Install boto3
      run: pip install boto3
    - name: Configure AWS Credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        role-to-assume: arn:aws:iam::123456789012:role/GitHubActionsRole
        aws-region: us-east-1
    - name: Deploy Lambda
      run: python deploy_lambda.py
```

---

### 77. Scenario: You need to write a `boto3` script to monitor CloudWatch alarms and trigger an Ansible playbook if an alarm is triggered. How would you implement this?

**Answer**:
- Use `boto3` to query CloudWatch alarms.
- Check for `ALARM` state and trigger an Ansible playbook via a shell command.
- Run in a GitHub Actions workflow with a schedule.

```python
# monitor_alarms.py
import boto3
import subprocess

def monitor_alarms(alarm_name):
    cw_client = boto3.client('cloudwatch')
    response = cw_client.describe_alarms(AlarmNames=[alarm_name])
    for alarm in response['MetricAlarms']:
        if alarm['StateValue'] == 'ALARM':
            print(f"Alarm {alarm_name} triggered!")
            subprocess.run(['ansible-playbook', 'remediate.yml'], check=True)

if __name__ == "__main__":
    monitor_alarms('HighCPUAlarm')
```

---

### 78. Scenario: An EKS cluster created with `boto3` is not scaling as expected. How would you diagnose and fix the issue?

**Answer**:
- **Check Node Group Config**: Verify desired capacity and scaling policies using `boto3`.
- **Inspect Autoscaler**: Ensure the Cluster Autoscaler is deployed and configured.
- **Review Logs**: Check CloudWatch logs for errors.
- **Validate IAM**: Ensure the EKS role has scaling permissions.
- **Fix**: Update node group scaling config if needed.

```python
import boto3

def check_eks_scaling(cluster_name, nodegroup_name):
    eks_client = boto3.client('eks')
    response = eks_client.describe_nodegroup(clusterName=cluster_name, nodegroupName=nodegroup_name)
    scaling_config = response['nodegroup']['scalingConfig']
    print(f"Desired: {scaling_config['desiredSize']}, Min: {scaling_config['minSize']}, Max: {scaling_config['maxSize']}")
```

---

### 79. Scenario: Your team wants to automate the cleanup of unused S3 buckets using `boto3` in a GitHub Actions workflow. Describe the approach.

**Answer**:
- Write a `boto3` script to list buckets and delete those with a specific tag or age.
- Schedule the script in a GitHub Actions workflow.
- Add safeguards (e.g., dry-run mode, confirmation).

```python
# cleanup_s3.py
import boto3

def cleanup_s3_buckets(tag_key, tag_value):
    s3_client = boto3.client('s3')
    response = s3_client.list_buckets()
    for bucket in response['Buckets']:
        tags = s3_client.get_bucket_tagging(Bucket=bucket['Name']).get('TagSet', [])
        if any(tag['Key'] == tag_key and tag['Value'] == tag_value for tag in tags):
            s3_client.delete_bucket(Bucket=bucket['Name'])
            print(f"Deleted bucket: {bucket['Name']}")

if __name__ == "__main__":
    cleanup_s3_buckets('Environment', 'Temp')
```

---

### 80. Scenario: A `boto3` script is failing because the IAM role lacks permissions to access a DynamoDB table. How would you troubleshoot and resolve this?

**Answer**:
- **Check Error**: Inspect the `AccessDenied` error message.
- **Verify IAM Role**: Use `boto3` to describe the role’s policies.
- **Update Policy**: Attach a policy allowing `dynamodb:*` actions.
- **Test**: Rerun the script.

```python
import boto3
from botocore.exceptions import ClientError

def access_dynamodb(table_name):
    dynamodb = boto3.resource('dynamodb')
    try:
        table = dynamodb.Table(table_name)
        table.get_item(Key={'id': '1'})
        print("Access successful")
    except ClientError as e:
        if e.response['Error']['Code'] == 'AccessDeniedException':
            print("IAM role lacks DynamoDB permissions")
        raise e
```

---

### 81. Scenario: You need to use `boto3` to create AWS resources and then use Terraform to manage them. How would you ensure a smooth handoff?

**Answer**:
- Tag resources created by `boto3` for Terraform identification.
- Generate a Terraform configuration file with resource details.
- Import resources into Terraform state using `terraform import`.
- Validate the state matches AWS.

```python
import boto3

def create_and_tag_s3(bucket_name):
    s3_client = boto3.client('s3')
    s3_client.create_bucket(Bucket=bucket_name)
    s3_client.put_bucket_tagging(
        Bucket=bucket_name,
        Tagging={'TagSet': [{'Key': 'ManagedBy', 'Value': 'Terraform'}]}
    )
    print(f"Created bucket {bucket_name} for Terraform")
```

---

### 82. Scenario: A `boto3` script in a GitHub Actions workflow is taking too long to complete. How would you optimize its performance?

**Answer**:
- Use pagination for large API responses.
- Cache results to avoid redundant calls.
- Parallelize operations with `ThreadPoolExecutor`.
- Optimize API calls (e.g., batch operations).

```python
from concurrent.futures import ThreadPoolExecutor
import boto3

def list_s3_objects_parallel(bucket_name):
    s3_client = boto3.client('s3')
    paginator = s3_client.get_paginator('list_objects_v2')
    pages = paginator.paginate(Bucket=bucket_name)
    
    def process_page(page):
        return [obj['Key'] for obj in page.get('Contents', [])]
    
    with ThreadPoolExecutor(max_workers=4) as executor:
        results = executor.map(process_page, pages)
    for keys in results:
        print(keys)
```

---

### 83. Scenario: Your Ansible playbook is failing because the AWS resources it expects (created by `boto3`) are not yet available. How would you handle this?

**Answer**:
- Use `boto3` waiters to ensure resources are available.
- Add retry logic in the Ansible playbook.
- Run `boto3` and Ansible in separate workflow steps with dependencies.

```python
import boto3

def create_s3_with_waiter(bucket_name):
    s3_client = boto3.client('s3')
    s3_client.create_bucket(Bucket=bucket_name)
    waiter = s3_client.get_waiter('bucket_exists')
    waiter.wait(Bucket=bucket_name)
    print(f"Bucket {bucket_name} is ready")
```

---

### 84. Scenario: You need to use `boto3` to extract logs from an EKS cluster and store them in S3 for analysis. Describe the implementation.

**Answer**:
- Use `boto3` to query CloudWatch logs for the EKS cluster.
- Export logs to an S3 bucket.
- Schedule in a GitHub Actions workflow.

```python
import boto3
import time

def export_eks_logs(log_group, bucket_name, prefix):
    cw_client = boto3.client('logs')
    task = cw_client.create_export_task(
        logGroupName=log_group,
        fromTime=0,
        to=int(time.time() * 1000),
        destination=bucket_name,
        destinationPrefix=prefix
    )
    print(f"Export task created: {task['taskId']}")
```

---

### 85. Scenario: A `boto3` script is inadvertently deleting critical AWS resources. How would you add safeguards to prevent this?

**Answer**:
- Add confirmation prompts or dry-run mode.
- Use resource tags to filter critical resources.
- Restrict IAM permissions to read-only for sensitive resources.
- Implement logging and alerts.

```python
import boto3

def safe_delete_s3(bucket_name, protected_tag='Critical'):
    s3_client = boto3.client('s3')
    tags = s3_client.get_bucket_tagging(Bucket=bucket_name).get('TagSet', [])
    if any(tag['Key'] == protected_tag for tag in tags):
        print(f"Bucket {bucket_name} is protected")
        return
    s3_client.delete_bucket(Bucket=bucket_name)
    print(f"Deleted bucket: {bucket_name}")
```