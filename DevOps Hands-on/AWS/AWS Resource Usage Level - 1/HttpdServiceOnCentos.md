Structured Notes for CentOS Instance Setup on AWS
Instance Specifications:
Instance Type: t2.micro
VPC: Default VPC
Subnet: Default Subnet
AMI: Amazon Linux (Note: You specified CentOS, but Amazon Linux is what you mentioned initially. For CentOS, you would need to find an appropriate CentOS AMI from the AWS Marketplace or Community AMIs)
Storage: 8GB Default Volume Storage

Security Group Configuration:
Inbound Rules:
Port 80 (HTTP) - TCP - 0.0.0.0/0
Port 443 (HTTPS) - TCP - 0.0.0.0/0
Port 22 (SSH) - TCP - 0.0.0.0/0
Outbound Rules: All traffic - All protocols - 0.0.0.0/0

Steps to Configure the Instance:

Launch Instance: Use the AWS Management Console or CLI to launch a new EC2 instance with the specified settings.
User Data Script: Use the following script in the user-data field during instance launch to automate the setup:

bash
#!/bin/bash

# Update system
yum update -y

# Install httpd
yum install -y httpd

# Start httpd service
systemctl start httpd

# Enable httpd to start on boot
systemctl enable httpd

# Create a simple index.html for testing
echo "<html><body><h1>Welcome to My CentOS Server on AWS!</h1></body></html>" > /var/www/html/index.html

# Ensure correct permissions for the web content
chown -R apache:apache /var/www/html
chmod -R 755 /var/www/html

# Firewall configuration (since CentOS uses firewalld by default)
firewall-cmd --permanent --add-service=http
firewall-cmd --reload

# Note: Use http://public-ip instead of https://public-ip
# Using https://public-ip results in a page load error due to SSL/TLS certificate issues.
# You can access the server using http://<public-ip> or the public DNS name provided by AWS.

# Optionally, you can add logging or additional configurations here

Additional Notes:
User Data: This script runs with root privileges when the instance boots for the first time. It updates the system, installs Apache (httpd), starts the service, enables it to start on boot, creates a simple index.html for verification, sets correct permissions, and configures the firewall to allow HTTP traffic.
Security Group: Ensure that the security group associated with this instance has the inbound rules as shown in the images you provided, allowing HTTP (80), HTTPS (443), and SSH (22) from any IP address (0.0.0.0/0). The outbound rules are set to allow all traffic.
AMI Selection: You mentioned Amazon Linux initially, but for CentOS, you'll need to select a CentOS AMI. Make sure to choose one that matches your desired version of CentOS from the AWS Marketplace or Community AMIs.
Accessing the Server: After the instance is launched and the script runs, you should access your webpage by visiting http://<public-ip> or the public DNS name of your instance in a web browser. Do not use https as it results in a page load error due to SSL/TLS certificate issues.
Troubleshooting: If you encounter issues, check the instance's system log in the EC2 console for any errors during the user-data script execution or check the Apache logs on the instance for service-related issues.

Remember to adjust the AMI selection for CentOS when launching your instance, as initially mentioned, you referred to Amazon Linux.