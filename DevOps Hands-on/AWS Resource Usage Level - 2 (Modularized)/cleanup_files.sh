#!/bin/bash

# Remove the resource IDs file
rm -f resource_ids_centos.txt
echo "Removed resource IDs file resource_ids_centos.txt."

# Remove trust policy file
rm -f trust-policy.json
echo "Removed trust policy file trust-policy.json."

# Remove the user data file
rm -f userDataCentOsComplex.sh
echo "Removed user data file userDataCentOsComplex.sh."

echo "All resources have been deleted successfully."