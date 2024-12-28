#!/bin/bash

region=$1
key_pair_name="Centos-Complex-Key-Pair-Auto"

# Create Key Pair if it doesn't exist
if ! aws ec2 describe-key-pairs --key-names ${key_pair_name} --region ${region} &>/dev/null; then
    aws ec2 create-key-pair --key-name ${key_pair_name} --query 'KeyMaterial' --output text --region ${region} > CentosComplexKeyPair.pem
    # Set permissions for the key pair file
    chmod 400 CentosComplexKeyPair.pem
    echo "Created Key Pair with name ${key_pair_name}"
else
    echo "Key pair ${key_pair_name} already exists."
fi

echo "key_pair_name=${key_pair_name}" >> resource_ids_centos.txt