terraform init
terraform plan
terraform apply -auto-approve
aws eks update-kubeconfig --name my-cluster --region ap-south-1
./kube_apply.sh