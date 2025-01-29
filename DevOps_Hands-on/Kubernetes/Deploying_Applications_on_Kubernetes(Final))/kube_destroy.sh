# Delete Prometheus and Grafana configurations
kubectl delete -f prometheus-deployment.yaml
kubectl delete -f prometheus-service.yaml
kubectl delete -f prometheus-clusterrolebinding.yaml
kubectl delete -f prometheus-clusterrole.yaml
kubectl delete -f prometheus-serviceaccount.yaml
kubectl delete -f prometheus-config.yaml

# Uncomment if Grafana was applied
# kubectl delete -f grafana-deployment.yaml
# kubectl delete -f grafana-datasource.yaml

# Delete Cluster Autoscaler configuration
kubectl delete -f cluster-autoscaler.yaml

# Delete HPA configuration
kubectl delete -f hpa-backend.yaml

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

# Delete Prometheus and Grafana configurations
# kubectl delete -f grafana-deployment.yaml
# kubectl delete -f grafana-datasource.yaml
# kubectl delete -f prometheus-deployment.yaml
# kubectl delete -f prometheus-config.yaml

# Delete EFK stack configurations
# kubectl delete -f kibana-deployment.yaml
# kubectl delete -f fluentd-daemonset.yaml
# kubectl delete -f fluentd-config.yaml
# kubectl delete -f elasticsearch-deployment.yaml

echo "All Kubernetes resources have been deleted."