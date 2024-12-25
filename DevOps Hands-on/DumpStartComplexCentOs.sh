# Steps to delete the resources created by StartComplexCentOs.sh

source resource_ids_centos.txt

# Terminate the EC2 instances if InstanceIds is not empty
for instance_id in "${InstanceIds[@]}"; do
    if [ -n "${instance_id}" ]; then
        echo "Processing instance ID: $instance_id"
        aws ec2 terminate-instances --instance-ids ${instance_id} --region ap-south-1
        echo "Terminating EC2 instances with IDs ${instance_id}..."
        aws ec2 wait instance-terminated --instance-ids ${instance_id} --region ap-south-1
        echo "EC2 instances with IDs ${instance_id} have been terminated."
    else
        echo "No EC2 instances to terminate."
    fi
done

# Delete the security group
aws ec2 delete-security-group --group-id ${SecurityGroupId} --region ap-south-1
echo "Deleted Security Group with ID ${SecurityGroupId}."

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

# Delete the route table
# aws ec2 delete-route-table --route-table-id ${RouteTableId} --region ap-south-1
# echo "Deleted Route Table with ID ${RouteTableId}."

# Delete the IAM role and instance profile
aws iam remove-role-from-instance-profile --instance-profile-name ${InstanceProfileName} --role-name ${RoleName}
aws iam delete-instance-profile --instance-profile-name ${InstanceProfileName}
aws iam detach-role-policy --role-name ${RoleName} --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess
aws iam delete-role --role-name ${RoleName}
echo "Deleted IAM Role and Instance Profile."

# Delete the placement group
aws ec2 delete-placement-group --group-name ${PlacementGroupName} --region ap-south-1
echo "Deleted Placement Group with name ${PlacementGroupName}."

# Delete the key pair
aws ec2 delete-key-pair --key-name ${KeyPairName} --region ap-south-1
echo "Deleted Key Pair with name ${KeyPairName}."

# Remove the key pair file
rm -f CentosComplexKeyPair.pem
echo "Removed key pair file CentosComplexKeyPair.pem."

# Remove the resource IDs file
rm -f resource_ids_centos.txt
echo "Removed resource IDs file resource_ids_centos.txt."

# Remove trust policy file
rm -f trust-policy.json
echo "Removed trust policy file trust-policy.json."

# Remove the user data file
rm -f userDataCentOs.sh
echo "Removed user data file userDataCentOs.sh."

echo "All resources have been deleted successfully."