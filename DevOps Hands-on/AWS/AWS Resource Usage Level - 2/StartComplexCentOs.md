# Script Overview

This Bash script automates the creation of a CentOS EC2 instance on AWS, including setting up networking resources like VPC, Subnet, Internet Gateway, Route Table, and Security Group. It also configures an IAM role for S3 read-only access, creates an instance profile, and finally launches EC2 instances with specific configurations.

## Parameters and Variables

- **Region**: 
    - `region="ap-south-1"` - Specifies the AWS region where resources will be created.
- **Availability Zone**: 
    - `available_zone=$(...)` - Dynamically checks for an available zone for t2.micro instances.
- **Key Pair**:
    - `key_pair_name="Centos-Complex-Key-Pair-Auto"` - Name for the SSH key pair.
- **VPC**:
    - `vpc_cidr="10.0.0.0/16"` - CIDR block for the VPC.
- **Internet Gateway, Subnet, Route Table**:
    - IDs are dynamically fetched or created.
- **Security Group**:
    - `security_group_name="CentosComplexSecurityGroup"` - Name for the security group.
- **IAM Role and Instance Profile**:
    - `role_name="CentOsRole"`
    - `instance_profile_name="CentOsProfile"`
    - `policy_arn="arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess"` - Policy for S3 read access.
- **Placement Group**:
    - `placement_group_name="centos-instances-placement-group"` - For improved network performance.
- **User Data Script**:
    - `userDataCentOsComplex.sh` - Script to install and start httpd on the instance.
- **AMI (Amazon Machine Image)**:
    - `image_id=ami-0fd05997b4dff7aac` - AMI ID for CentOS.
- **Instance Details**:
    - `instance_name="CentOSComplexInstance"`
    - `instance_count=2` - Number of instances to launch.

## Resource Creation Logic

- **Check and Create if Not Exists**: 
    - The script checks if resources like VPC, IGW, Subnet, etc., exist. If not, it creates them, ensuring idempotency.
- **Networking Setup**:
    - VPC creation, Internet Gateway attachment, Subnet creation within the VPC, and route table configuration for internet access.
- **Security**:
    - A security group is set up with rules for SSH (port 22), HTTP (port 80), and HTTPS (port 443) ingress from anywhere.
- **IAM Configuration**:
    - An IAM role with permissions to read from S3 is created, and an instance profile is associated with this role.
- **Instance Launch**:
    - Uses `aws ec2 run-instances` with parameters like:
        - Instance type (t2.micro), key pair, security group, subnet, public IP association, user data script, tagging, monitoring settings, IAM instance profile, block device mapping, and placement group specification.

## Order of Resource Declaration

While AWS doesn't strictly enforce an order for resource creation (apart from dependencies like VPC before subnet), this script follows a logical sequence:

1. **Check for instance type availability in zones** - This determines where resources can be placed.
2. **Create foundational network components** - VPC, Internet Gateway, Subnet.
3. **Setup routing** - Route Table, association with subnet.
4. **Security** - Security group creation and configuration.
5. **Identity and Access Management** - Role creation, profile setup.
6. **User Data Preparation** - Script for post-launch configuration.
7. **Instance Launch** - Finally, the EC2 instances are launched with all configurations.

## Additional Notes

- **Error Handling**: The script uses `if` conditions to check if resources exist, providing feedback or proceeding with creation if necessary.
- **Resource IDs**: All created resources' IDs are stored in `resource_ids_centos.txt` for reference or cleanup.
- **Cleanup**: This script doesn't include cleanup commands, but in practice, you'd want to remove these resources to avoid charges or clutter once done with them.

For an interview, understanding this script's flow, the purpose of each AWS component, how they interact, and the security implications of each setting (like public IP assignments or security group rules) would be crucial points to cover.