# Apply Kubernetes configurations
kubectl apply -f mysql-secret.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f backend-deployment.yaml

CURR_DIR=$(pwd)

# Get the Backend LoadBalancer DNS
export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
echo "Backend LoadBalancer DNS: $BACKEND_LOADBALANCER_DNS"

# export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.spec.clusterIP}')
# echo "Backend LoadBalancer DNS: $BACKEND_LOADBALANCER_DNS"

# Replace placeholder in App.js.temp and create App.js
sed "s|BACKEND_LOADBALANCER_DNS|$BACKEND_LOADBALANCER_DNS|g" "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js.temp" > "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend/src/App.js"

# Change to the frontend directory
cd "C:/Users/Dell/Common-PDFs/DevOps_Hands-on/Kubernetes/Deploying_Applications_on_Kubernetes(Final))/frontend"

# Commit and push the changes
git add src/App.js
git commit -m "Update App.js with Backend LoadBalancer DNS"
git push origin main

sleep 240

cd $CURR_DIR

# Apply frontend configurations
kubectl apply -f frontend-service.yaml
kubectl apply -f frontend-deployment.yaml
# kubectl apply -f website-content-configmap.yaml

# Apply HPA configuration
kubectl apply -f hpa-backend.yaml

# Apply Cluster Autoscaler configuration
kubectl apply -f cluster-autoscaler.yaml