# Delete frontend configurations
kubectl delete -f frontend-deployment.yaml
kubectl delete -f frontend-service.yaml
kubectl delete -f website-content-configmap.yaml

# Delete backend configurations
kubectl delete -f backend-deployment.yaml
kubectl delete -f backend-service.yaml

# Delete MySQL secret
kubectl delete -f mysql-secret.yaml

# Optionally, delete any remaining resources or namespaces if needed
# kubectl delete namespace <namespace-name>