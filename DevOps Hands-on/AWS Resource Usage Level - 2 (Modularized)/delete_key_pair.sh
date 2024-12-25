#!/bin/bash

source resource_ids_centos.txt

# Delete the key pair
aws ec2 delete-key-pair --key-name ${KeyPairName} --region ap-south-1
echo "Deleted Key Pair with name ${KeyPairName}."

# Remove the key pair file
rm -f CentosComplexKeyPair.pem
echo "Removed key pair file CentosComplexKeyPair.pem."