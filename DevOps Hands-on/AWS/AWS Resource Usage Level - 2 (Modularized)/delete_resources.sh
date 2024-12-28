#!/bin/bash

# Execute deletion modules
./delete_ec2_instances.sh
./delete_security_group.sh
./delete_internet_gateway.sh
./delete_subnet.sh
./delete_vpc.sh
./delete_iam_role.sh
./delete_placement_group.sh
./delete_key_pair.sh
./cleanup_files.sh