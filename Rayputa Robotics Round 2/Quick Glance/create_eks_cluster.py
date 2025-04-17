import boto3
import time

def create_eks_cluster(cluster_name, role_arn, subnets, region='us-east-1'):
    eks_client = boto3.client('eks', region_name=region)
    response = eks_client.create_cluster(
        name=cluster_name,
        roleArn=role_arn,
        resourcesVpcConfig={
            'subnetIds': subnets,
            'securityGroupIds': []
        },
        version='1.21'
    )
    print(f"Creating EKS cluster: {cluster_name}")
    while True:
        cluster_status = eks_client.describe_cluster(name=cluster_name)['cluster']['status']
        if cluster_status == 'ACTIVE':
            print("Cluster is active!")
            break
        print("Waiting for cluster to become active...")
        time.sleep(30)

if __name__ == "__main__":
    create_eks_cluster(
        cluster_name='my-eks-cluster',
        role_arn='arn:aws:iam::123456789012:role/EKSRole',
        subnets=['subnet-12345678', 'subnet-87654321']
    )