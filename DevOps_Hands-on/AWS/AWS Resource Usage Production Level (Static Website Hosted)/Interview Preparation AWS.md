Here are the interview preparation notes based on the provided `main.sh` script, including the syntax of various commands used in the script. These notes will help you understand the key concepts and functionalities used in the script, ensuring you don't miss any important points during your interview.

## Interview Preparation Notes

### 1. Understanding the Script Purpose
**Objective:** The script checks the availability of a specific EC2 instance type (`t3.micro`) in different Availability Zones (AZs) within the `ap-south-1` region and then executes a series of modules to set up AWS resources.

### 2. Key Components of the Script
- **Shebang:** `#!/bin/bash`
    - Indicates that the script should be run using the Bash shell.
- **Error Handling:** `set -e`
    - Ensures the script exits immediately if any command exits with a non-zero status.

### 3. Variables
- **Region:** `region="ap-south-1"`
    - Specifies the AWS region to check for instance type availability.
- **Instance Type:** `instance_type="t3.micro"`
    - Specifies the EC2 instance type to check for availability.
- **Availability Zones:** `azs=("ap-south-1a" "ap-south-1b" "ap-south-1c")`
    - An array of Availability Zones within the specified region.

### 4. Functions
- **check_az_availability**
    - **Purpose:** Checks if the specified instance type is available in a given Availability Zone.
    - **Usage:** `check_az_availability $az`
    - **AWS CLI Command:** `aws ec2 describe-instance-type-offerings`
        - **Parameters:**
            - `--location-type availability-zone`: Specifies the location type as Availability Zone.
            - `--filters`: Filters the results based on instance type and location.
            - `--region $region`: Specifies the AWS region.
            - `--query`: Filters the output to show only the locations where the instance type is available.
            - `--output text`: Outputs the result in plain text format.

### 5. Loops and Conditionals
- **Loop:** `for az in "${azs[@]}"; do ... done`
    - Iterates over each Availability Zone in the `azs` array.
- **Conditional:** `if [[ $(check_az_availability $az) == "$az" ]]; then ... fi`
    - Checks if the instance type is available in the current Availability Zone.
    - Adds the Availability Zone to the `available_azs` array if available.

### 6. Array Handling
- **Array Initialization:** `available_azs=()`
    - Initializes an empty array to store available Availability Zones.
- **Array Length Check:** `if [ ${#available_azs[@]} -eq 0 ]; then ... fi`
    - Checks if the `available_azs` array is empty.

### 7. Output and Error Messages
- **Output Message:** `echo "No Availability Zone present for $instance_type in $region"`
    - Prints a message if no Availability Zones are available for the specified instance type.

### 8. File Operations
- **Writing to a File:**
    - `echo "region=${region}" > resource_ids_centos.txt`
    - `echo "available_zone_1=${available_azs[0]}" >> resource_ids_centos.txt`
    - `echo "available_zone_2=${available_azs[1]}" >> resource_ids_centos.txt`
    - The `>` operator overwrites the file, while the `>>` operator appends to the file.

### 9. Executing Other Scripts
- **Syntax:** `./script_name.sh ${region}`
    - Executes another script and passes the region as an argument.
- **Sleep Command:** `sleep 10`
    - Pauses the script for 10 seconds.

## Syntax of Various Commands

### AWS CLI Command
```bash
aws ec2 describe-instance-type-offerings \
        --location-type availability-zone \
        --filters "Name=instance-type,Values=$instance_type" "Name=location,Values=$1" \
        --region $region \
        --query 'InstanceTypeOfferings[?InstanceType==`'${instance_type}'`].Location' \
        --output text
```

### Loop and Conditional
```bash
for az in "${azs[@]}"; do
        if [[ $(check_az_availability $az) == "$az" ]]; then
                available_azs+=("$az")
        fi
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
```

### Example Questions
1. **What is the purpose of the `set -e` command in the script?**
    - It ensures the script exits immediately if any command exits with a non-zero status.
2. **How does the script check the availability of an EC2 instance type in different Availability Zones?**
    - It uses the `aws ec2 describe-instance-type-offerings` command with appropriate filters and queries.
3. **What is the significance of the `--query` parameter in the AWS CLI command?**
    - It filters the output to show only the locations where the specified instance type is available.
4. **How does the script handle the case where no Availability Zones are available for the specified instance type?**
    - It checks the length of the `available_azs` array and prints a message if the array is empty.
5. **How does the script write information to a file?**
    - It uses the `echo` command with the `>` and `>>` operators to write and append information to a file.
6. **What is the purpose of the `sleep` command in the script?**
    - It pauses the script for a specified number of seconds to ensure that previous operations have completed before proceeding.

By understanding these key points and practicing the example questions, you'll be well-prepared for interview questions related to this script and similar topics.

## AWS Resource Creation Process

### Create Key Pair
- **Command:** `./create_key_pair.sh ${region}`
- **Description:** This script creates an SSH key pair in the specified AWS region. The key pair is essential for securely accessing EC2 instances.

### Create VPC
- **Command:** `./create_vpc.sh ${region}`
- **Description:** This script creates a Virtual Private Cloud (VPC). A VPC is a logically isolated section of the AWS cloud where you can launch AWS resources in a virtual network.

### Create Internet Gateway
- **Command:** `./create_internet_gateway.sh ${region}`
- **Description:** This script creates an Internet Gateway and attaches it to the VPC. An Internet Gateway allows communication between the VPC and the internet.

### Create Subnets
- **Command:** `./create_subnets.sh ${region}`
- **Description:** This script creates subnets within the VPC. Subnets are segments of the VPC where you can group resources based on security and operational needs.

### Create Route Tables
- **Command:** `./create_route_tables.sh ${region}`
- **Description:** This script creates route tables and associates them with the subnets. Route tables determine how traffic is directed within the VPC.

### Create NAT Gateway
- **Command:** `./create_nat_gateway.sh ${region}`
- **Description:** This script creates a NAT Gateway. A NAT Gateway allows instances in a private subnet to connect to the internet or other AWS services, but prevents the internet from initiating connections with those instances.

### Create Security Groups
- **Command:** `./create_security_groups.sh ${region}`
- **Description:** This script creates security groups. Security groups act as virtual firewalls for your instances to control inbound and outbound traffic.

### Create IAM Role
- **Command:** `./create_iam_role.sh ${region}`
- **Description:** This script creates an IAM role. IAM roles are used to grant permissions to AWS services to perform actions on your behalf.

### Create Placement Group
- **Command:** `./create_placement_group.sh ${region}`
- **Description:** This script creates a placement group. Placement groups influence the placement of instances to meet the needs of your workload.

### Create S3 Bucket
- **Command:** `./create_s3_bucket.sh ${region}`
- **Description:** This script creates an S3 bucket. S3 buckets are used for storing objects, such as data and backups.

### Create RDS Instance
- **Command:** `./create_rds_instance.sh ${region}`
- **Description:** This script creates an RDS (Relational Database Service) instance. RDS provides scalable database solutions in the cloud.

### Setup CloudWatch
- **Command:** `./setup_cloudwatch.sh ${region}`
- **Description:** This script sets up CloudWatch. CloudWatch is used for monitoring and managing various metrics and logs from AWS resources.

### Launch Instances
- **Command:** `./launch_instances.sh ${region}`
- **Description:** This script launches EC2 instances. EC2 instances are virtual servers in the cloud.

### Create Load Balancer
- **Command:** `./create_load_balancer.sh ${region}`
- **Description:** This script creates a load balancer. Load balancers distribute incoming application traffic across multiple targets, such as EC2 instances.

### Create Auto Scaling Group
- **Command:** `./create_auto_scaling_group.sh ${region}`
- **Description:** This script creates an auto-scaling group. Auto-scaling groups automatically adjust the number of EC2 instances in response to demand.

### Create Lambda Function
- **Command:** `./create_lambda_function.sh ${region}`
- **Description:** This script creates a Lambda function. Lambda functions allow you to run code without provisioning or managing servers.

Each resource is created in a specific order to ensure dependencies are met. For example, the VPC must be created before subnets, and the Internet Gateway must be attached to the VPC before instances can communicate with the internet. This sequence ensures that all resources are properly configured and can interact as needed in real-time scenarios.

To launch a website using AWS resources, you need to follow a specific sequence to ensure all dependencies are met. First, create an SSH key pair using the command `./create_key_pair.sh ${region}`. This key pair is essential for securely accessing EC2 instances. Next, create a Virtual Private Cloud (VPC) with `./create_vpc.sh ${region}`. A VPC is a logically isolated section of the AWS cloud where you can launch AWS resources in a virtual network.

After setting up the VPC, create an Internet Gateway using `./create_internet_gateway.sh ${region}` and attach it to the VPC. This gateway allows communication between the VPC and the internet. Then, create subnets within the VPC using `./create_subnets.sh ${region}`. Subnets are segments of the VPC where you can group resources based on security and operational needs.

Following this, create route tables with `./create_route_tables.sh ${region}` and associate them with the subnets. Route tables determine how traffic is directed within the VPC. Next, create a NAT Gateway using `./create_nat_gateway.sh ${region}`. A NAT Gateway allows instances in a private subnet to connect to the internet or other AWS services while preventing the internet from initiating connections with those instances.

Create security groups using `./create_security_groups.sh ${region}`. Security groups act as virtual firewalls for your instances to control inbound and outbound traffic. Then, create an IAM role with `./create_iam_role.sh ${region}`. IAM roles are used to grant permissions to AWS services to perform actions on your behalf.

Next, create a placement group using `./create_placement_group.sh ${region}`. Placement groups influence the placement of instances to meet the needs of your workload. Create an S3 bucket with `./create_s3_bucket.sh ${region}`. S3 buckets are used for storing objects, such as data and backups.

Create an RDS (Relational Database Service) instance using `./create_rds_instance.sh ${region}`. RDS provides scalable database solutions in the cloud. Set up CloudWatch with `./setup_cloudwatch.sh ${region}`. CloudWatch is used for monitoring and managing various metrics and logs from AWS resources.

Launch EC2 instances using `./launch_instances.sh ${region}`. EC2 instances are virtual servers in the cloud. Create a load balancer with `./create_load_balancer.sh ${region}`. Load balancers distribute incoming application traffic across multiple targets, such as EC2 instances.

Finally, create an auto-scaling group using `./create_auto_scaling_group.sh ${region}`. Auto-scaling groups automatically adjust the number of EC2 instances in response to demand. Optionally, create a Lambda function with `./create_lambda_function.sh ${region}`. Lambda functions allow you to run code without provisioning or managing servers.

By following this sequence, you ensure that all resources are properly configured and can interact as needed in real-time scenarios.

## Key-Pairs

### 1. Understanding the Script Purpose
**Objective:** The script creates an EC2 key pair in a specified AWS region if it doesn't already exist. The key pair is used for SSH access to EC2 instances.

### 2. Key Components of the Script
**Shebang:** `#!/bin/bash`  
Indicates that the script should be run using the Bash shell.

### 3. Variables
**Region:** `region=$1`  
The AWS region is passed as an argument to the script.

**Key Pair Name:** `key_pair_name="Centos-Complex-Key-Pair-Auto"`  
Specifies the name of the EC2 key pair to be created.

### 4. Conditional Check
**Check if Key Pair Exists:**
```bash
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
```
Uses the `aws ec2 describe-key-pairs` command to check if the key pair already exists in the specified region.  
The `&>/dev/null` part redirects both stdout and stderr to `/dev/null`, effectively silencing the command output.

### 5. Creating the Key Pair
**Create Key Pair:**
```bash
aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosComplexKeyPair.pem
```
Uses the `aws ec2 create-key-pair` command to create a new key pair.  
The `--query 'KeyMaterial'` parameter extracts the key material (private key) from the response.  
The `--output text` parameter outputs the key material in plain text format.  
The key material is saved to a file named `CentosComplexKeyPair.pem`.

### 6. Setting File Permissions
**Set Permissions:**
```bash
chmod 400 CentosComplexKeyPair.pem
```
Uses the `chmod` command to set the file permissions of the key pair file to `400` (read-only for the owner).

### 7. Output Messages
**Print Messages:**
```bash
echo "Created Key Pair with name ${key_pair_name}"
echo "Key pair ${key_pair_name} already exists."
```
Prints messages indicating whether the key pair was created or already exists.

### 8. Writing to a File
**Append to File:**
```bash
echo "key_pair_name=${key_pair_name}" >> resource_ids_centos.txt
```
Appends the key pair name to the `resource_ids_centos.txt` file.

### Syntax of Various Commands

**AWS CLI Command: Describe Key Pairs**
```bash
aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region}
```
**Parameters:**
- `--key-names ${key_pair_name}`: Specifies the name of the key pair to describe.
- `--region ${region}`: Specifies the AWS region.

**AWS CLI Command: Create Key Pair**
```bash
aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosComplexKeyPair.pem
```
**Parameters:**
- `--key-name ${key_pair_name}`: Specifies the name of the key pair to create.
- `--query 'KeyMaterial'`: Extracts the key material (private key) from the response.
- `--output text`: Outputs the key material in plain text format.
- `--region ${region}`: Specifies the AWS region.

**File Permissions Command**
```bash
chmod 400 CentosComplexKeyPair.pem
```
**Parameters:**
- `400`: Sets the file permissions to read-only for the owner.

**Conditional Check**
```bash
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
    # Commands to execute if the key pair does not exist
else
    # Commands to execute if the key pair already exists
fi
```

**Writing to a File**
```bash
echo "key_pair_name=${key_pair_name}" >> resource_ids_centos.txt
```
**Parameters:**
- `>>`: Appends the output to the specified file.

### Example Questions

**What is the purpose of the `&>/dev/null` part in the `aws ec2 describe-key-pairs` command?**

It redirects both stdout and stderr to `/dev/null`, effectively silencing the command output.

**How does the script check if an EC2 key pair already exists?**

It uses the `aws ec2 describe-key-pairs` command with the key pair name and region as parameters.

**What does the `--query 'KeyMaterial'` parameter do in the `aws ec2 create-key-pair` command?**

It extracts the key material (private key) from the response.

**Why is the `chmod 400 CentosComplexKeyPair.pem` command used in the script?**

It sets the file permissions of the key pair file to read-only for the owner.

**How does the script handle the case where the key pair already exists?**

It prints a message indicating that the key pair already exists and skips the creation step.

By understanding these key points and practicing the example questions, you'll be well-prepared for interview questions related to this script and similar topics.

## VPC

### 1. Understanding the Script Purpose
**Objective:** The script creates a Virtual Private Cloud (VPC) in a specified AWS region if it doesn't already exist. The VPC is a logically isolated network in the AWS cloud where you can launch AWS resources.

### 2. Key Components of the Script
**Shebang:** `#!/bin/bash`  
Indicates that the script should be run using the Bash shell.

### 3. Variables
**Region:** `region=$1`  
The AWS region is passed as an argument to the script.

**VPC CIDR Block:** `vpc_cidr="10.0.0.0/16"`  
Specifies the CIDR block for the VPC.

### 4. Conditional Check
**Check if VPC Exists:**
```bash
vpc_id=None
# vpc_id=$(aws ec2 describe-vpcs --filters "Name=cidr,Values=${vpc_cidr}" --query 'Vpcs[0].VpcId' --output text --region ${region})
if [ "$vpc_id" == "None" ]; then
```
Uses the `aws ec2 describe-vpcs` command to check if a VPC with the specified CIDR block already exists in the specified region.  
- The `--filters` parameter filters the VPCs based on the CIDR block.
- The `--query 'Vpcs[0].VpcId'` parameter extracts the VPC ID from the response.
- The `--output text` parameter outputs the VPC ID in plain text format.

### 5. Creating the VPC
**Create VPC:**
```bash
vpc_id=$(aws ec2 create-vpc --cidr-block ${vpc_cidr} --query 'Vpc.VpcId' --output text --region ${region})
```
Uses the `aws ec2 create-vpc` command to create a new VPC with the specified CIDR block.  
- The `--cidr-block ${vpc_cidr}` parameter specifies the CIDR block for the VPC.
- The `--query 'Vpc.VpcId'` parameter extracts the VPC ID from the response.
- The `--output text` parameter outputs the VPC ID in plain text format.

### 6. Output Messages
**Print Messages:**
```bash
echo "Created VPC with ID ${vpc_id}"
echo "VPC with CIDR ${vpc_cidr} already exists with ID ${vpc_id}"
```
Prints messages indicating whether the VPC was created or already exists.

### 7. Writing to a File
**Append to File:**
```bash
echo "vpc_id=${vpc_id}" >> resource_ids_centos.txt
```
Appends the VPC ID to the `resource_ids_centos.txt` file.

### Syntax of Various Commands
**AWS CLI Command: Describe VPCs**
```bash
aws ec2 describe-vpcs --filters "Name=cidr,Values=${vpc_cidr}" --query 'Vpcs[0].VpcId' --output text --region ${region}
```
**Parameters:**
- `--filters "Name=cidr,Values=${vpc_cidr}"`: Filters the VPCs based on the CIDR block.
- `--query 'Vpcs[0].VpcId'`: Extracts the VPC ID from the response.
- `--output text`: Outputs the VPC ID in plain text format.
- `--region ${region}`: Specifies the AWS region.

**AWS CLI Command: Create VPC**
```bash
aws ec2 create-vpc --cidr-block ${vpc_cidr} --query 'Vpc.VpcId' --output text --region ${region}
```
**Parameters:**
- `--cidr-block ${vpc_cidr}`: Specifies the CIDR block for the VPC.
- `--query 'Vpc.VpcId'`: Extracts the VPC ID from the response.
- `--output text`: Outputs the VPC ID in plain text format.
- `--region ${region}`: Specifies the AWS region.

### Conditional Check
```bash
if [ "$vpc_id" == "None" ]; then
    # Commands to execute if the VPC does not exist
else
    # Commands to execute if the VPC already exists
fi
```

### Writing to a File
```bash
echo "vpc_id=${vpc_id}" >> resource_ids_centos.txt
```
**Parameters:**
- `>>`: Appends the output to the specified file.

### Example Questions
**What is the purpose of the `aws ec2 describe-vpcs` command in the script?**

It checks if a VPC with the specified CIDR block already exists in the specified region.

**How does the script create a new VPC if it doesn't already exist?**

It uses the `aws ec2 create-vpc` command with the specified CIDR block and region.

**What does the `--query 'Vpc.VpcId'` parameter do in the `aws ec2 create-vpc` command?**

It extracts the VPC ID from the response.

**Why is the `--output text` parameter used in the AWS CLI commands?**

It outputs the result in plain text format.

**How does the script handle the case where the VPC already exists?**

It prints a message indicating that the VPC already exists and skips the creation step.

**How does the script write the VPC ID to a file?**

It uses the `echo` command with the `>>` operator to append the VPC ID to the `resource_ids_centos.txt` file.

## Internet Gateway

### 1. Understanding the Script Purpose
**Objective:** The script creates an Internet Gateway (IGW) in a specified AWS region if it doesn't already exist and attaches it to a VPC. An Internet Gateway allows communication between instances in the VPC and the internet.

### 2. Key Components of the Script
- **Shebang:** `#!/bin/bash`
    - Indicates that the script should be run using the Bash shell.

### 3. Variables
- **Region:** `region=$1`
    - The AWS region is passed as an argument to the script.
- **Internet Gateway ID:** `igw_id=None`
    - Initializes the Internet Gateway ID variable.
- **Source File:** `source resource_ids_centos.txt`
    - Sources the `resource_ids_centos.txt` file to import variables defined in it (e.g., `vpc_id`).

### 4. Conditional Check
- **Check if Internet Gateway Exists:**
    ```bash
    igw_id=$(aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${vpc_id}" --query 'InternetGateways[0].InternetGatewayId' --output text --region ${region})
    if [ "$igw_id" == "None" ]; then
    ```
    - Uses the `aws ec2 describe-internet-gateways` command to check if an Internet Gateway is already attached to the specified VPC.
    - The `--filters` parameter filters the Internet Gateways based on the VPC ID.
    - The `--query 'InternetGateways[0].InternetGatewayId'` parameter extracts the Internet Gateway ID from the response.
    - The `--output text` parameter outputs the Internet Gateway ID in plain text format.

### 5. Creating the Internet Gateway
- **Create Internet Gateway:**
    ```bash
    igw_id=$(aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text --region ${region})
    ```
    - Uses the `aws ec2 create-internet-gateway` command to create a new Internet Gateway.
    - The `--query 'InternetGateway.InternetGatewayId'` parameter extracts the Internet Gateway ID from the response.
    - The `--output text` parameter outputs the Internet Gateway ID in plain text format.

### 6. Attaching the Internet Gateway
- **Attach Internet Gateway:**
    ```bash
    aws ec2 attach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ${region}
    ```
    - Uses the `aws ec2 attach-internet-gateway` command to attach the Internet Gateway to the specified VPC.
    - The `--internet-gateway-id ${igw_id}` parameter specifies the Internet Gateway ID.
    - The `--vpc-id ${vpc_id}` parameter specifies the VPC ID.

### 7. Output Messages
- **Print Messages:**
    ```bash
    echo "Created and attached Internet Gateway with ID ${igw_id}"
    echo "Internet Gateway already exists with ID ${igw_id}"
    ```
    - Prints messages indicating whether the Internet Gateway was created and attached or already exists.

### 8. Writing to a File
- **Append to File:**
    ```bash
    echo "igw_id=${igw_id}" >> resource_ids_centos.txt
    ```
    - Appends the Internet Gateway ID to the `resource_ids_centos.txt` file.

### Syntax of Various Commands

#### AWS CLI Command: Describe Internet Gateways
```bash
aws ec2 describe-internet-gateways --filters "Name=attachment.vpc-id,Values=${vpc_id}" --query 'InternetGateways[0].InternetGatewayId' --output text --region ${region}
```
- **Parameters:**
    - `--filters "Name=attachment.vpc-id,Values=${vpc_id}"`: Filters the Internet Gateways based on the VPC ID.
    - `--query 'InternetGateways[0].InternetGatewayId'`: Extracts the Internet Gateway ID from the response.
    - `--output text`: Outputs the Internet Gateway ID in plain text format.
    - `--region ${region}`: Specifies the AWS region.

#### AWS CLI Command: Create Internet Gateway
```bash
aws ec2 create-internet-gateway --query 'InternetGateway.InternetGatewayId' --output text --region ${region}
```
- **Parameters:**
    - `--query 'InternetGateway.InternetGatewayId'`: Extracts the Internet Gateway ID from the response.
    - `--output text`: Outputs the Internet Gateway ID in plain text format.
    - `--region ${region}`: Specifies the AWS region.

#### AWS CLI Command: Attach Internet Gateway
```bash
aws ec2 attach-internet-gateway --internet-gateway-id ${igw_id} --vpc-id ${vpc_id} --region ${region}
```
- **Parameters:**
    - `--internet-gateway-id ${igw_id}`: Specifies the Internet Gateway ID.
    - `--vpc-id ${vpc_id}`: Specifies the VPC ID.
    - `--region ${region}`: Specifies the AWS region.

### Conditional Check
```bash
if [ "$igw_id" == "None" ]; then
        # Commands to execute if the Internet Gateway does not exist
else
        # Commands to execute if the Internet Gateway already exists
fi
```

### Writing to a File
```bash
echo "igw_id=${igw_id}" >> resource_ids_centos.txt
```
- **Parameters:**
    - `>>`: Appends the output to the specified file.

### Example Questions
- **What is the purpose of the `aws ec2 describe-internet-gateways` command in the script?**
    - It checks if an Internet Gateway is already attached to the specified VPC.
- **How does the script create a new Internet Gateway if it doesn't already exist?**
    - It uses the `aws ec2 create-internet-gateway` command to create a new Internet Gateway.
- **What does the `--query 'InternetGateway.InternetGatewayId'` parameter do in the `aws ec2 create-internet-gateway` command?**
    - It extracts the Internet Gateway ID from the response.
- **Why is the `--output text` parameter used in the AWS CLI commands?**
    - It outputs the result in plain text format.
- **How does the script attach the Internet Gateway to the specified VPC?**
    - It uses the `aws ec2 attach-internet-gateway` command with the Internet Gateway ID and VPC ID as parameters.
- **How does the script handle the case where the Internet Gateway already exists?**
    - It prints a message indicating that the Internet Gateway already exists and skips the creation and attachment steps.
- **How does the script write the Internet Gateway ID to a file?**
    - It uses the `echo` command with the `>>` operator to append the Internet Gateway ID to the `resource_ids_centos.txt` file.

## Subnets

### 1. Understanding the Script Purpose
**Objective:** The script creates public and private subnets in a specified AWS region if they don't already exist. Subnets are segments of a VPC's IP address range where you can place groups of isolated resources.

### 2. Key Components of the Script
- **Shebang:** `#!/bin/bash`
    - Indicates that the script should be run using the Bash shell.

### 3. Variables
- **Region:** `region=$1`
    - The AWS region is passed as an argument to the script.
- **Subnet IDs:**
    - `public_subnet_id_1=None`
    - `private_subnet_id_1=None`
    - `public_subnet_id_2=None`
    - `private_subnet_id_2=None`
    - Initializes the subnet ID variables.
- **Source File:** `source resource_ids_centos.txt`
    - Sources the `resource_ids_centos.txt` file to import variables defined in it (e.g., `vpc_id`, `available_zone_1`, `available_zone_2`).

### 4. Creating Public Subnet 1
- **Public Subnet CIDR Block:** `public_subnet_cidr_1="10.0.1.0/24"`
    - Specifies the CIDR block for Public Subnet 1.
- **Check if Public Subnet 1 Exists:**
    ```bash
    public_subnet_id_1=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_1}" --query 'Subnets[0].SubnetId' --output text --region ${region})
    if [ "$public_subnet_id_1" == "None" ]; then
    ```
    - Uses the `aws ec2 describe-subnets` command to check if Public Subnet 1 already exists in the specified VPC.
    - The `--filters` parameter filters the subnets based on the VPC ID and CIDR block.
    - The `--query 'Subnets[0].SubnetId'` parameter extracts the Subnet ID from the response.
    - The `--output text` parameter outputs the Subnet ID in plain text format.
- **Create Public Subnet 1:**
    ```bash
    public_subnet_id_1=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_1} --availability-zone ${available_zone_1} --query 'Subnet.SubnetId' --output text --region ${region})
    ```
    - Uses the `aws ec2 create-subnet` command to create Public Subnet 1.
    - The `--vpc-id ${vpc_id}` parameter specifies the VPC ID.
    - The `--cidr-block ${public_subnet_cidr_1}` parameter specifies the CIDR block for the subnet.
    - The `--availability-zone ${available_zone_1}` parameter specifies the Availability Zone.
    - The `--query 'Subnet.SubnetId'` parameter extracts the Subnet ID from the response.
    - The `--output text` parameter outputs the Subnet ID in plain text format.
- **Output Messages:**
    ```bash
    echo "Created Public Subnet 1 with ID ${public_subnet_id_1}"
    echo "Public Subnet 1 with CIDR ${public_subnet_cidr_1} already exists with ID ${public_subnet_id_1}"
    ```
    - Prints messages indicating whether Public Subnet 1 was created or already exists.

### 5. Creating Public Subnet 2
- **Public Subnet CIDR Block:** `public_subnet_cidr_2="10.0.2.0/24"`
    - Specifies the CIDR block for Public Subnet 2.
- **Check if Public Subnet 2 Exists:**
    ```bash
    public_subnet_id_2=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_2}" --query 'Subnets[0].SubnetId' --output text --region ${region})
    if [ "$public_subnet_id_2" == "None" ]; then
    ```
    - Uses the `aws ec2 describe-subnets` command to check if Public Subnet 2 already exists in the specified VPC.
    - The `--filters` parameter filters the subnets based on the VPC ID and CIDR block.
    - The `--query 'Subnets[0].SubnetId'` parameter extracts the Subnet ID from the response.
    - The `--output text` parameter outputs the Subnet ID in plain text format.
- **Create Public Subnet 2:**
    ```bash
    public_subnet_id_2=$(aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_2} --availability-zone ${available_zone_2} --query 'Subnet.SubnetId' --output text --region ${region})
    if [ "$public_subnet_id_2" == "None" ]; then
    ```
    - Uses the `aws ec2 create-subnet` command to create Public Subnet 2.
    - The `--vpc-id ${vpc_id}` parameter specifies the VPC ID.
    - The `--cidr-block ${public_subnet_cidr_2}` parameter specifies the CIDR block for the subnet.
    - The `--availability-zone ${available_zone_2}` parameter specifies the Availability Zone.
    - The `--query 'Subnet.SubnetId'` parameter extracts the Subnet ID from the response.
    - The `--output text` parameter outputs the Subnet ID in plain text format.
- **Output Messages:**
    ```bash
    echo "Created Public Subnet 2 with ID ${public_subnet_id_2}"
    echo "Public Subnet 2 with CIDR ${public_subnet_cidr_2} already exists with ID ${public_subnet_id_2}"
    ```
    - Prints messages indicating whether Public Subnet 2 was created or already exists.

### Syntax of Various Commands
- **AWS CLI Command: Describe Subnets**
    ```bash
    aws ec2 describe-subnets --filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_1}" --query 'Subnets[0].SubnetId' --output text --region ${region}
    ```
    - **Parameters:**
        - `--filters "Name=vpc-id,Values=${vpc_id}" "Name=cidr-block,Values=${public_subnet_cidr_1}"`: Filters the subnets based on the VPC ID and CIDR block.
        - `--query 'Subnets[0].SubnetId'`: Extracts the Subnet ID from the response.
        - `--output text`: Outputs the Subnet ID in plain text format.
        - `--region ${region}`: Specifies the AWS region.

- **AWS CLI Command: Create Subnet**
    ```bash
    aws ec2 create-subnet --vpc-id ${vpc_id} --cidr-block ${public_subnet_cidr_1} --availability-zone ${available_zone_1} --query 'Subnet.SubnetId' --output text --region ${region}
    ```
    - **Parameters:**
        - `--vpc-id ${vpc_id}`: Specifies the VPC ID.
        - `--cidr-block ${public_subnet_cidr_1}`: Specifies the CIDR block for the subnet.
        - `--availability-zone ${available_zone_1}`: Specifies the Availability Zone.
        - `--query 'Subnet.SubnetId'`: Extracts the Subnet ID from the response.
        - `--output text`: Outputs the Subnet ID in plain text format.
        - `--region ${region}`: Specifies the AWS region.

### Conditional Check
```bash
if [ "$public_subnet_id_1" == "None" ]; then
        # Commands to execute if the Public Subnet 1 does not exist
else
        # Commands to execute if the Public Subnet 1 already exists
fi
```

### Writing to a File
```bash
echo "public_subnet_id_1=${public_subnet_id_1}" >> resource_ids_centos.txt
```
- **Parameters:**
    - `>>`: Appends the output to the specified file.

### Example Questions
- **What is the purpose of the `aws ec2 describe-subnets` command in the script?**
    - It checks if a subnet with the specified CIDR block already exists in the specified VPC.
- **How does the script create a new subnet if it doesn't already exist?**
    - It uses the `aws ec2 create-subnet` command with the specified VPC ID, CIDR block, and Availability Zone.
- **What does the `--query 'Subnet.SubnetId'` parameter do in the `aws ec2 create-subnet` command?**
    - It extracts the Subnet ID from the response.
- **Why is the `--output text` parameter used in the AWS CLI commands?**
    - It outputs the result in plain text format.
- **How does the script handle the case where the subnet already exists?**
    - It prints a message indicating that the subnet already exists and skips the creation step.
- **How does the script write the Subnet ID to a file?**
    - It uses the `echo` command with the `>>` operator to append the Subnet ID to the `resource_ids_centos.txt` file.

## Route tables

1. **Understanding the Script Purpose**
    - **Objective**: The script creates route tables for public and private subnets in a specified AWS region if they don't already exist. Route tables are used to determine where network traffic is directed.

2. **Key Components of the Script**
    - **Shebang**: `#!/bin/bash`
      - Indicates that the script should be run using the Bash shell.

3. **Variables**
    - **Region**: `region=$1`
      - The AWS region is passed as an argument to the script.
    - **Route Table IDs**:
      - `public_route_table_id_1=None`
      - `public_route_table_id_2=None`
      - `private_route_table_id_1=None`
      - `private_route_table_id_2=None`
      - Initializes the route table ID variables.
    - **Source File**: `source resource_ids_centos.txt`
      - Sources the `resource_ids_centos.txt` file to import variables defined in it (e.g., `vpc_id`, `public_subnet_id_1`, `public_subnet_id_2`, `igw_id`).

4. **Creating Route Table for Public Subnet 1**
    - **Check if Route Table for Public Subnet 1 Exists**:
      ```bash
      public_route_table_id_1=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_1}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
      if [ "$public_route_table_id_1" == "None" ]; then
      ```
      - Uses the `aws ec2 describe-route-tables` command to check if a route table for Public Subnet 1 already exists in the specified VPC.
      - The `--filters` parameter filters the route tables based on the VPC ID and subnet ID.
      - The `--query 'RouteTables[0].RouteTableId'` parameter extracts the Route Table ID from the response.
      - The `--output text` parameter outputs the Route Table ID in plain text format.
    - **Create Route Table for Public Subnet 1**:
      ```bash
      public_route_table_id_1=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
      ```
      - Uses the `aws ec2 create-route-table` command to create a route table for Public Subnet 1.
      - The `--vpc-id ${vpc_id}` parameter specifies the VPC ID.
      - The `--query 'RouteTable.RouteTableId'` parameter extracts the Route Table ID from the response.
      - The `--output text` parameter outputs the Route Table ID in plain text format.
    - **Associate Route Table with Public Subnet 1**:
      ```bash
      aws ec2 associate-route-table --route-table-id ${public_route_table_id_1} --subnet-id ${public_subnet_id_1} --region ${region}
      ```
      - Uses the `aws ec2 associate-route-table` command to associate the route table with Public Subnet 1.
      - The `--route-table-id ${public_route_table_id_1}` parameter specifies the Route Table ID.
      - The `--subnet-id ${public_subnet_id_1}` parameter specifies the Subnet ID.
    - **Create Route in Route Table for Public Subnet 1**:
      ```bash
      aws ec2 create-route --route-table-id ${public_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
      ```
      - Uses the `aws ec2 create-route` command to create a route in the route table for Public Subnet 1.
      - The `--route-table-id ${public_route_table_id_1}` parameter specifies the Route Table ID.
      - The `--destination-cidr-block 0.0.0.0/0` parameter specifies the destination CIDR block (default route).
      - The `--gateway-id ${igw_id}` parameter specifies the Internet Gateway ID.
    - **Output Messages**:
      ```bash
      echo "Created Route Table for Public Subnet 1 with ID ${public_route_table_id_1}"
      echo "Route Table for Public Subnet 1 already exists with ID ${public_route_table_id_1}"
      ```
      - Prints messages indicating whether the route table for Public Subnet 1 was created or already exists.

5. **Creating Route Table for Public Subnet 2**
    - **Check if Route Table for Public Subnet 2 Exists**:
      ```bash
      public_route_table_id_2=$(aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_2}" --query 'RouteTables[0].RouteTableId' --output text --region ${region})
      if [ "$public_route_table_id_2" == "None" ]; then
      ```
      - Uses the `aws ec2 describe-route-tables` command to check if a route table for Public Subnet 2 already exists in the specified VPC.
      - The `--filters` parameter filters the route tables based on the VPC ID and subnet ID.
      - The `--query 'RouteTables[0].RouteTableId'` parameter extracts the Route Table ID from the response.
      - The `--output text` parameter outputs the Route Table ID in plain text format.
    - **Create Route Table for Public Subnet 2**:
      ```bash
      public_route_table_id_2=$(aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region})
      ```
      - Uses the `aws ec2 create-route-table` command to create a route table for Public Subnet 2.
      - The `--vpc-id ${vpc_id}` parameter specifies the VPC ID.
      - The `--query 'RouteTable.RouteTableId'` parameter extracts the Route Table ID from the response.
      - The `--output text` parameter outputs the Route Table ID in plain text format.
    - **Associate Route Table with Public Subnet 2**:
      ```bash
      aws ec2 associate-route-table --route-table-id ${public_route_table_id_2} --subnet-id ${public_subnet_id_2} --region ${region}
      ```
      - Uses the `aws ec2 associate-route-table` command to associate the route table with Public Subnet 2.
      - The `--route-table-id ${public_route_table_id_2}` parameter specifies the Route Table ID.
      - The `--subnet-id ${public_subnet_id_2}` parameter specifies the Subnet ID.
    - **Create Route in Route Table for Public Subnet 2**:
      ```bash
      aws ec2 create-route --route-table-id ${public_route_table_id_2} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
      ```
      - Uses the `aws ec2 create-route` command to create a route in the route table for Public Subnet 2.
      - The `--route-table-id ${public_route_table_id_2}` parameter specifies the Route Table ID.
      - The `--destination-cidr-block 0.0.0.0/0` parameter specifies the destination CIDR block (default route).
      - The `--gateway-id ${igw_id}` parameter specifies the Internet Gateway ID.
    - **Output Messages**:
      ```bash
      echo "Created Route Table for Public Subnet 2 with ID ${public_route_table_id_2}"
      echo "Route Table for Public Subnet 2 already exists with ID ${public_route_table_id_2}"
      ```
      - Prints messages indicating whether the route table for Public Subnet 2 was created or already exists.

### Syntax of Various Commands

### AWS CLI Command: Describe Route Tables
```bash
aws ec2 describe-route-tables --filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_1}" --query 'RouteTables[0].RouteTableId' --output text --region ${region}
```
- **Parameters**:
  - `--filters "Name=vpc-id,Values=${vpc_id}" "Name=association.subnet-id,Values=${public_subnet_id_1}"`: Filters the route tables based on the VPC ID and subnet ID.
  - `--query 'RouteTables[0].RouteTableId'`: Extracts the Route Table ID from the response.
  - `--output text`: Outputs the Route Table ID in plain text format.
  - `--region ${region}`: Specifies the AWS region.

### AWS CLI Command: Create Route Table
```bash
aws ec2 create-route-table --vpc-id ${vpc_id} --query 'RouteTable.RouteTableId' --output text --region ${region}
```
- **Parameters**:
  - `--vpc-id ${vpc_id}`: Specifies the VPC ID.
  - `--query 'RouteTable.RouteTableId'`: Extracts the Route Table ID from the response.
  - `--output text`: Outputs the Route Table ID in plain text format.
  - `--region ${region}`: Specifies the AWS region.

### AWS CLI Command: Associate Route Table
```bash
aws ec2 associate-route-table --route-table-id ${public_route_table_id_1} --subnet-id ${public_subnet_id_1} --region ${region}
```
- **Parameters**:
  - `--route-table-id ${public_route_table_id_1}`: Specifies the Route Table ID.
  - `--subnet-id ${public_subnet_id_1}`: Specifies the Subnet ID.
  - `--region ${region}`: Specifies the AWS region.

### AWS CLI Command: Create Route
```bash
aws ec2 create-route --route-table-id ${public_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --gateway-id ${igw_id} --region ${region}
```
- **Parameters**:
  - `--route-table-id ${public_route_table_id_1}`: Specifies the Route Table ID.
  - `--destination-cidr-block 0.0.0.0/0`: Specifies the destination CIDR block (default route).
  - `--gateway-id ${igw_id}`: Specifies the Internet Gateway ID.
  - `--region ${region}`: Specifies the AWS region.

### Conditional Check
```bash
if [ "$public_route_table_id_1" == "None" ]; then
     # Commands to execute if the Route Table for Public Subnet 1 does not exist
else
     # Commands to execute if the Route Table for Public Subnet 1 already exists
fi
```

### Writing to a File
```bash
echo "public_route_table_id_1=${public_route_table_id_1}" >> resource_ids_centos.txt
```
- **Parameters**:
  - `>>`: Appends the output to the specified file.

### Example Questions

1. **What is the purpose of the `aws ec2 describe-route-tables` command in the script?**
    - It checks if a route table for the specified subnet already exists in the specified VPC.

2. **How does the script create a new route table if it doesn't already exist?**
    - It uses the `aws ec2 create-route-table` command with the specified VPC ID.

3. **What does the `--query 'RouteTable.RouteTableId'` parameter do in the `aws ec2 create-route-table` command?**
    - It extracts the Route Table ID from the response.

4. **Why is the `--output text` parameter used in the AWS CLI commands?**
    - It outputs the result in plain text format.

5. **How does the script associate the route table with the specified subnet?**
    - It uses the `aws ec2 associate-route-table` command with the Route Table ID and Subnet ID as parameters.

6. **How does the script create a route in the route table?**
    - It uses the `aws ec2 create-route` command with the Route Table ID, destination CIDR block, and Internet Gateway ID as parameters.

7. **How does the script handle the case where the route table already exists?**
    - It prints a message indicating that the route table already exists and skips the creation and association steps.

8. **How does the script write the Route Table ID to a file?**
    - It uses the `echo` command with the `>>` operator to append the Route Table ID to the `resource_ids_centos.txt` file.

By understanding these key points and practicing the example questions, you'll be well-prepared for interview questions related to this script and similar topics.

## NAT Gateway

### 1. Understanding the Script Purpose
**Objective:** The script creates NAT Gateways for public subnets in a specified AWS region and updates private route tables to use these NAT Gateways. NAT Gateways allow instances in private subnets to connect to the internet while preventing inbound traffic from the internet.

### 2. Key Components of the Script
**Shebang:** `#!/bin/bash`  
Indicates that the script should be run using the Bash shell.

### 3. Variables
**Region:** `region=$1`  
The AWS region is passed as an argument to the script.  
**Source File:** `source resource_ids_centos.txt`  
Sources the `resource_ids_centos.txt` file to import variables defined in it (e.g., `public_subnet_id_1`, `public_subnet_id_2`, `private_route_table_id_1`, `private_route_table_id_2`).

### 4. Creating NAT Gateway for Public Subnet 1
**Allocate Elastic IP:**
```bash
eip_allocation_id_1=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
```
Uses the `aws ec2 allocate-address` command to allocate an Elastic IP address for the NAT Gateway.  
The `--domain vpc` parameter specifies that the Elastic IP address is for use in a VPC.  
The `--query 'AllocationId'` parameter extracts the Allocation ID from the response.  
The `--output text` parameter outputs the Allocation ID in plain text format.

**Create NAT Gateway:**
```bash
nat_gateway_id_1=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_1} --allocation-id ${eip_allocation_id_1} --query 'NatGateway.NatGatewayId' --output text --region ${region})
```
Uses the `aws ec2 create-nat-gateway` command to create a NAT Gateway in Public Subnet 1.  
The `--subnet-id ${public_subnet_id_1}` parameter specifies the Subnet ID.  
The `--allocation-id ${eip_allocation_id_1}` parameter specifies the Elastic IP Allocation ID.  
The `--query 'NatGateway.NatGatewayId'` parameter extracts the NAT Gateway ID from the response.  
The `--output text` parameter outputs the NAT Gateway ID in plain text format.

**Output Message:**
```bash
echo "Created NAT Gateway 1 with ID ${nat_gateway_id_1}"
```
Prints a message indicating that NAT Gateway 1 was created.

### 5. Waiting for NAT Gateway 1 to Become Available
**Sleep Command:**
```bash
echo "Waiting for NAT Gateway 1 to become available..."
sleep 20
```
Pauses the script for 20 seconds to allow NAT Gateway 1 to become available.

### 6. Creating NAT Gateway for Public Subnet 2
**Allocate Elastic IP:**
```bash
eip_allocation_id_2=$(aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region})
```
Uses the `aws ec2 allocate-address` command to allocate an Elastic IP address for the NAT Gateway.  
The `--domain vpc` parameter specifies that the Elastic IP address is for use in a VPC.  
The `--query 'AllocationId'` parameter extracts the Allocation ID from the response.  
The `--output text` parameter outputs the Allocation ID in plain text format.

**Create NAT Gateway:**
```bash
nat_gateway_id_2=$(aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_2} --allocation-id ${eip_allocation_id_2} --query 'NatGateway.NatGatewayId' --output text --region ${region})
```
Uses the `aws ec2 create-nat-gateway` command to create a NAT Gateway in Public Subnet 2.  
The `--subnet-id ${public_subnet_id_2}` parameter specifies the Subnet ID.  
The `--allocation-id ${eip_allocation_id_2}` parameter specifies the Elastic IP Allocation ID.  
The `--query 'NatGateway.NatGatewayId'` parameter extracts the NAT Gateway ID from the response.  
The `--output text` parameter outputs the NAT Gateway ID in plain text format.

**Output Message:**
```bash
echo "Created NAT Gateway 2 with ID ${nat_gateway_id_2}"
```
Prints a message indicating that NAT Gateway 2 was created.

### 7. Waiting for NAT Gateway 2 to Become Available
**Sleep Command:**
```bash
echo "Waiting for NAT Gateway 2 to become available..."
sleep 20
```
Pauses the script for 20 seconds to allow NAT Gateway 2 to become available.

### 8. Updating Private Route Tables to Use NAT Gateways
**Update Private Route Table 1:**
```bash
aws ec2 create-route --route-table-id ${private_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_1} --region ${region}
echo "Updated Private Route Table 1 to use NAT Gateway 1"
```
Uses the `aws ec2 create-route` command to create a route in Private Route Table 1 to use NAT Gateway 1.  
The `--route-table-id ${private_route_table_id_1}` parameter specifies the Route Table ID.  
The `--destination-cidr-block 0.0.0.0/0` parameter specifies the destination CIDR block (default route).  
The `--nat-gateway-id ${nat_gateway_id_1}` parameter specifies the NAT Gateway ID.  
Prints a message indicating that Private Route Table 1 was updated to use NAT Gateway 1.

**Update Private Route Table 2:**
```bash
aws ec2 create-route --route-table-id ${private_route_table_id_2} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_2} --region ${region}
echo "Updated Private Route Table 2 to use NAT Gateway 2"
```
Uses the `aws ec2 create-route` command to create a route in Private Route Table 2 to use NAT Gateway 2.  
The `--route-table-id ${private_route_table_id_2}` parameter specifies the Route Table ID.  
The `--destination-cidr-block 0.0.0.0/0` parameter specifies the destination CIDR block (default route).  
The `--nat-gateway-id ${nat_gateway_id_2}` parameter specifies the NAT Gateway ID.  
Prints a message indicating that Private Route Table 2 was updated to use NAT Gateway 2.

### Syntax of Various Commands
**AWS CLI Command: Allocate Elastic IP**
```bash
aws ec2 allocate-address --domain vpc --query 'AllocationId' --output text --region ${region}
```
**Parameters:**
- `--domain vpc`: Specifies that the Elastic IP address is for use in a VPC.
- `--query 'AllocationId'`: Extracts the Allocation ID from the response.
- `--output text`: Outputs the Allocation ID in plain text format.
- `--region ${region}`: Specifies the AWS region.

**AWS CLI Command: Create NAT Gateway**
```bash
aws ec2 create-nat-gateway --subnet-id ${public_subnet_id_1} --allocation-id ${eip_allocation_id_1} --query 'NatGateway.NatGatewayId' --output text --region ${region}
```
**Parameters:**
- `--subnet-id ${public_subnet_id_1}`: Specifies the Subnet ID.
- `--allocation-id ${eip_allocation_id_1}`: Specifies the Elastic IP Allocation ID.
- `--query 'NatGateway.NatGatewayId'`: Extracts the NAT Gateway ID from the response.
- `--output text`: Outputs the NAT Gateway ID in plain text format.
- `--region ${region}`: Specifies the AWS region.

**AWS CLI Command: Create Route**
```bash
aws ec2 create-route --route-table-id ${private_route_table_id_1} --destination-cidr-block 0.0.0.0/0 --nat-gateway-id ${nat_gateway_id_1} --region ${region}
```
**Parameters:**
- `--route-table-id ${private_route_table_id_1}`: Specifies the Route Table ID.
- `--destination-cidr-block 0.0.0.0/0`: Specifies the destination CIDR block (default route).
- `--nat-gateway-id ${nat_gateway_id_1}`: Specifies the NAT Gateway ID.
- `--region ${region}`: Specifies the AWS region.

**Sleep Command**
```bash
sleep 20
```
**Parameters:**
- `20`: Pauses the script for 20 seconds.

### Example Questions
- What is the purpose of the `aws ec2 allocate-address` command in the script?
    - It allocates an Elastic IP address for the NAT Gateway.
- How does the script create a new NAT Gateway?
    - It uses the `aws ec2 create-nat-gateway` command with the specified Subnet ID and Elastic IP Allocation ID.
- What does the `--query 'NatGateway.NatGatewayId'` parameter do in the `aws ec2 create-nat-gateway` command?
    - It extracts the NAT Gateway ID from the response.
- Why is the `--output text` parameter used in the AWS CLI commands?
    - It outputs the result in plain text format.
- How does the script update the private route tables to use the NAT Gateways?
    - It uses the `aws ec2 create-route` command with the Route Table ID, destination CIDR block, and NAT Gateway ID as parameters.
- Why does the script use the `sleep` command after creating each NAT Gateway?
    - It pauses the script to allow time for the NAT Gateway to become available.