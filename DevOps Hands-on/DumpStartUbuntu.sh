source resource_ids_ubuntu.txt

# Terminate the EC2 instance
aws ec2 terminate-instances --instance-ids ${InstanceId} --region ap-south-1
echo "Terminating EC2 instance with ID ${InstanceId}..."
aws ec2 wait instance-terminated --instance-ids ${InstanceId} --region ap-south-1
echo "EC2 instance with ID ${InstanceId} has been terminated."

# Delete the security group
aws ec2 delete-security-group --group-id ${SecurityGroupId} --region ap-south-1
echo "Deleted Security Group with ID ${SecurityGroupId}."

# Delete the route table
aws ec2 delete-route-table --route-table-id ${RouteTableId} --region ap-south-1
echo "Deleted Route Table with ID ${RouteTableId}."

# Detach and delete the internet gateway
aws ec2 detach-internet-gateway --internet-gateway-id ${IgwId} --vpc-id ${VpcId} --region ap-south-1
echo "Detached Internet Gateway with ID ${IgwId} from VPC ${VpcId}."
aws ec2 delete-internet-gateway --internet-gateway-id ${IgwId} --region ap-south-1
echo "Deleted Internet Gateway with ID ${IgwId}."

# Delete the subnet
aws ec2 delete-subnet --subnet-id ${SubnetId} --region ap-south-1
echo "Deleted Subnet with ID ${SubnetId}."

# Delete the VPC
aws ec2 delete-vpc --vpc-id ${VpcId} --region ap-south-1
echo "Deleted VPC with ID ${VpcId}."

# Delete the key pair
aws ec2 delete-key-pair --key-name ${KeyPairName} --region ap-south-1
echo "Deleted Key Pair with name ${KeyPairName}."

# Remove the key pair file
rm -f UbuntuKeyPair.pem
echo "Removed key pair file UbuntuKeyPair.pem."

# Remove the resource IDs file
rm -f resource_ids_ubuntu.txt
echo "Removed resource IDs file resource_ids_ubuntu.txt."