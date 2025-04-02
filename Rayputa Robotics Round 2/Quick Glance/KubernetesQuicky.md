# Kubernetes Interview Preparation

## Kubernetes Basics

### What is Kubernetes, and why is it used?
Kubernetes (K8s) is an open-source platform for automating deployment, scaling, and management of containerized apps. It’s used for scalability, resilience, and portability—handling tasks like load balancing, self-healing, and resource optimization across any environment.

### What problems does Kubernetes solve in container orchestration?
It automates deployment, scaling, load balancing, and self-healing; manages resources, service discovery, and configs; and coordinates containers across multiple hosts for high availability.

### Explain the difference between Docker and Kubernetes.
Docker builds and runs containers on one host. Kubernetes orchestrates many containers across a cluster, adding scaling, scheduling, and networking.

### What is a container, and how does Kubernetes manage containers?
A container is a lightweight, isolated app package with dependencies. Kubernetes groups them into Pods, schedules them on nodes, manages their lifecycle, and handles networking and scaling.

### What are the key components of Kubernetes architecture?
- **Control Plane**: kube-apiserver, etcd, kube-scheduler, kube-controller-manager.
- **Worker Nodes**: kubelet, kube-proxy, container runtime (e.g., Docker).

### What is a Pod in Kubernetes?
The smallest unit—runs one or more containers sharing network (IP) and storage. Ephemeral and managed by controllers.

### How does Kubernetes differ from traditional virtualization?
Containers share the host OS, making them lighter and faster than VMs, which run full guest OSes. Kubernetes optimizes this for scale.

### What is the role of a Kubernetes cluster?
A group of nodes running containers, managed as one system for deployment, scaling, and fault tolerance.

### What is the difference between a node and a cluster in Kubernetes?
- **Node**: Single machine (master or worker).
- **Cluster**: All nodes working together under a control plane.

### What is a control plane in Kubernetes, and what does it consist of?
The brain of the cluster—manages state and scheduling. Includes kube-apiserver, etcd, kube-scheduler, and kube-controller-manager.

## Kubernetes Architecture

### Explain the role of the kube-apiserver.
Central hub—exposes the API, handles requests, authenticates/authorizes, and updates etcd.

### What does the kube-scheduler do?
Places Pods on nodes based on resources, policies, and constraints.

### What is the purpose of the kube-controller-manager?
Runs controllers to keep cluster state (e.g., replicas, nodes) as desired.

### What is etcd, and why is it important in Kubernetes?
Distributed store for cluster state—critical for consistency and control plane operations.

### How does the kubelet work on a worker node?
Node agent—starts/stops containers, ensures Pods run, and reports to the control plane.

### What is the role of kube-proxy in Kubernetes?
Manages networking—sets up rules for Service IPs and load balances traffic to Pods.

### Explain the difference between master nodes and worker nodes.
- **Master**: Runs control plane (manages cluster).
- **Worker**: Runs Pods (executes workloads).

### What is the Cloud Controller Manager, and when is it used?
Links Kubernetes to cloud APIs for nodes, load balancers, and storage—used in cloud-hosted clusters.

### How does Kubernetes ensure high availability of the control plane?
Multiple master nodes, replicated etcd, leader election, and load-balanced API servers.

### What is a Namespace in Kubernetes, and how is it used?
Logical partition—isolates resources, scopes access, and limits quotas for teams or projects.

## Pods and Workloads

### How do you create a Pod in Kubernetes?
**YAML**:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: nginx
```
Apply: `kubectl apply -f pod.yaml`  
Or command: `kubectl run my-pod --image=nginx --restart=Never`

### What is the difference between a Pod and a Deployment?
- **Pod**: Single instance of containers.
- **Deployment**: Manages Pods with replicas, updates, and self-healing.

### What is a ReplicaSet, and how does it relate to a Deployment?
Ensures a set number of Pods run. Deployment creates and updates ReplicaSets for rollouts.

### Explain the lifecycle of a Pod.
`Pending → Running → Succeeded/Failed → (Unknown if lost)`. Managed by init containers and probes.

### What are labels and selectors in Kubernetes, and how are they used?
- **Labels**: Key-value tags (e.g., app=nginx).
- **Selectors**: Filter objects by labels for routing or management.

### What is a multi-container Pod, and when would you use one?
Multiple containers in one Pod—used for sidecars (e.g., logging) or tightly coupled apps.

### How do you restart a Pod without deleting it?
- For Deployment: `kubectl rollout restart deployment <name>`
- Direct: Edit spec (e.g., add label) or let probes fail.

### What is a StatefulSet, and how does it differ from a Deployment?
Manages stateful apps with stable names and storage. Unlike Deployment, it’s ordered and persistent.

### What is a DaemonSet, and in what scenarios is it useful?
Runs one Pod per node—great for monitoring, logging, or network agents.

### What is a Job and a CronJob in Kubernetes?
- **Job**: One-time task to completion.
- **CronJob**: Scheduled Jobs (e.g., backups).

## Networking

### How does networking work in Kubernetes?
Pods get unique IPs, communicate via flat network (CNI), and use Services for stable access.

### What is a ClusterIP service, and how does it work?
Default Service—virtual IP for internal Pod access, load-balanced by kube-proxy.

### Explain the difference between ClusterIP, NodePort, and LoadBalancer services.
- **ClusterIP**: Internal only.
- **NodePort**: Exposes on node ports (30000-32767).
- **LoadBalancer**: Cloud external IP.

### What is an Ingress controller, and why is it used?
Manages HTTP traffic—routes by host/path, handles TLS, and cuts LoadBalancer costs.

### How does Kubernetes handle DNS resolution within a cluster?
CoreDNS assigns names like `<service>.<namespace>.svc.cluster.local` for Pod/Service discovery.

### What is a CNI (Container Network Interface), and why is it important?
Standard for Pod networking—assigns IPs and routes traffic (e.g., Flannel, Calico).

### How do Pods communicate with each other in Kubernetes?
Direct IP or via Service ClusterIP—enabled by CNI and DNS.

### What is Network Policy, and how can you enforce it?
Rules to control Pod traffic—enforced with CNI plugins like Calico.

### How does Kubernetes handle external traffic to services?
Via NodePort (node IP:port), LoadBalancer (cloud IP), or Ingress (HTTP routing).

### What is the purpose of kube-proxy in networking?
Sets up Service IPs and load balances traffic to Pods using iptables or IPVS.

## Storage

### What is a Persistent Volume (PV) in Kubernetes?
Cluster storage resource—abstracts disks (e.g., NFS, cloud) for Pods.

### What is a Persistent Volume Claim (PVC), and how does it relate to a PV?
User request for storage—binds to a matching PV for Pod use.

### Explain the difference between static and dynamic provisioning of storage.
- **Static**: Admin pre-creates PVs.
- **Dynamic**: Auto-creates PVs via StorageClass on demand.

### What is a StorageClass, and how does it work?
Defines storage types (e.g., SSD) for dynamic PV provisioning.

### How do you attach storage to a Pod?
```yaml
volumes:
- name: my-volume
    persistentVolumeClaim:
        claimName: my-pvc
```
Mount via PVC in Pod spec.

### What are the access modes for Persistent Volumes (e.g., RWO, RWX)?
- **RWO**: Read-write, one node.
- **ROX**: Read-only, many nodes.
- **RWX**: Read-write, many nodes.

### How does Kubernetes handle storage in a multi-node cluster?
Uses PV/PVC with node affinity (RWO) or shared systems (RWX) via CSI.

### What is the role of the CSI (Container Storage Interface) in Kubernetes?
Standardizes storage drivers—enables dynamic provisioning and advanced features.

### How do you troubleshoot storage issues in Kubernetes?
Check Pod events (`kubectl describe`), PVC/PV binding, and StorageClass config.

### What happens to a PV when a PVC is deleted?
Depends on reclaim policy: Retain (keeps PV), Delete (removes PV), or Recycle (wipes data).

## Configuration and Secrets

### What is a ConfigMap, and how is it used?
Stores non-sensitive config data—used as env vars or files in Pods.

### How do you inject environment variables into a Pod?
```yaml
env:
- name: ENV_VAR
    value: "value"
```

### What is a Secret in Kubernetes, and how does it differ from a ConfigMap?
Stores sensitive data (e.g., passwords)—base64-encoded, unlike plain-text ConfigMap.

### How do you create and manage Secrets in Kubernetes?
`kubectl create secret generic my-secret --from-literal=key=value`

### How can you mount a ConfigMap or Secret as a volume in a Pod?
```yaml
volumes:
- name: config-vol
    configMap:
        name: my-config
```

### What are the security best practices for managing Secrets in Kubernetes?
Encrypt at rest, use RBAC, avoid logging, and rotate regularly.

### How do you update a ConfigMap or Secret without restarting a Pod?
Mount as volume—Pod sees updates automatically (with some delay).

### What is the difference between a base64-encoded Secret and a plain-text ConfigMap?
- **Secret**: Encoded for obscurity.
- **ConfigMap**: Raw text for configs.

### How do you pass sensitive data to an application in Kubernetes?
Use Secrets as env vars or mounted files.

### What is the role of RBAC in securing ConfigMaps and Secrets?
Restricts who can read/write them via Roles and Bindings.

## Scheduling and Resource Management

### How does the Kubernetes scheduler decide where to place a Pod?
Filters nodes (resources, taints), then scores based on policies (e.g., least usage).

### What are resource requests and limits in Kubernetes?
- **Requests**: Minimum needed.
- **Limits**: Max allowed (CPU/memory).

### What happens when a Pod exceeds its resource limits?
CPU throttled, memory OOM-killed.

### What is a taint and toleration, and how are they used?
- **Taint**: Repels Pods from nodes.
- **Toleration**: Allows Pods to ignore taints.

### What is a node selector, and how does it work?
Matches Pods to nodes by labels (e.g., `nodeSelector: disk=ssd`).

### How do you prioritize Pods in Kubernetes?
Use PriorityClass—higher priority Pods get scheduled first.

### What is a PriorityClass, and how do you configure it?
```yaml
apiVersion: scheduling.k8s.io/v1
kind: PriorityClass
metadata:
    name: high-priority
value: 1000
```

### How does Kubernetes handle Pod eviction?
Evicts low-priority or over-limit Pods when resources are tight.

### What is a Quality of Service (QoS) class, and how is it assigned to Pods?
- **Guaranteed**: limits=requests.
- **Burstable**: requests<limits.
- **BestEffort**: none—auto-assigned.

### How do you troubleshoot a Pod that is stuck in a Pending state?
Check events (`kubectl describe`), resources, taints, and PVCs.

## Scaling and High Availability

### What is Horizontal Pod Autoscaling (HPA), and how does it work?
Scales Pod replicas based on CPU/memory—needs metrics server.

### How do you configure HPA based on custom metrics?
Use Prometheus Adapter—set custom metric target in HPA spec.

### What is Vertical Pod Autoscaling (VPA), and when is it useful?
Adjusts Pod resource limits—useful for optimizing fixed workloads.

### How does Kubernetes ensure high availability of applications?
Replicas, multi-node scheduling, and self-healing via controllers.

### What is a rolling update, and how do you perform one in Kubernetes?
Gradual Pod replacement: `kubectl set image deployment/<name> container=new-image`.

### What is the difference between a rolling update and a recreate strategy?
- **Rolling**: Zero downtime, gradual.
- **Recreate**: Kills all, then restarts.

### How does Kubernetes handle failover for Pods?
Controllers (e.g., Deployment) reschedule failed Pods to healthy nodes.

### What is the role of liveness and readiness probes?
- **Liveness**: Restarts unhealthy Pods.
- **Readiness**: Controls traffic readiness.

### How do you configure liveness and readiness probes for a Pod?
```yaml
livenessProbe:
    httpGet:
        path: /health
        port: 80
    initialDelaySeconds: 5
readinessProbe:
    httpGet:
        path: /ready
        port: 80
```

### What happens if a readiness probe fails?
Pod marked NotReady—traffic stops until it passes.

## Security

### What is Role-Based Access Control (RBAC) in Kubernetes?
Controls permissions—links Roles/ClusterRoles to users via Bindings.
```

```markdown
# Kubernetes Interview Questions and Answers

## RBAC and Security

### How do you create a Role and a RoleBinding?
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
    name: pod-reader
rules:
- apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list"]
---
kind: RoleBinding
metadata:
    name: read-pods
subjects:
- kind: User
    name: "user1"
roleRef:
    kind: Role
    name: pod-reader
```

### What is the difference between a ClusterRole and a Role?
- **ClusterRole**: Cluster-wide permissions.
- **Role**: Namespace-specific permissions.

### How do you secure the Kubernetes API server?
- Use TLS, RBAC, disable anonymous access, and restrict network access.

### What is a ServiceAccount, and how is it used?
- Identity for Pods—provides API token for app access.

### How does Kubernetes handle authentication and authorization?
- **Authentication**: Certs, tokens, OIDC.
- **Authorization**: RBAC, ABAC, or webhooks.

### What are Pod Security Policies (PSPs), and how do they work?
- Enforce Pod security rules (e.g., no root access)—deprecated for Pod Security Admission (PSA).

### How do you restrict network traffic using Network Policies?
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: restrict
spec:
    podSelector:
        matchLabels:
            app: backend
    ingress:
    - from:
        - podSelector:
                matchLabels:
                    app: frontend
```

### What are some best practices for securing a Kubernetes cluster?
- Use RBAC, TLS, encrypt Secrets, limit privileges, and enable audit logs.

### How do you audit and monitor security events in Kubernetes?
- Enable API audit logs and use tools like Prometheus or Falco for monitoring.

## Monitoring and Troubleshooting

### How do you check the status of Pods in a cluster?
- `kubectl get pods --all-namespaces -o wide`

### What is the purpose of the `kubectl describe` command?
- Shows detailed resource information—useful for debugging events.

### How do you view logs of a Pod using `kubectl`?
- `kubectl logs <pod-name> -c <container>`

### What does the `kubectl get events` command do?
- Lists cluster events—helps identify issues.

### How do you debug a Pod that is crashing?
- Check logs (`kubectl logs --previous`), events, and Pod spec.

### What are some common reasons for a Pod to be in a `CrashLoopBackOff` state?
- Application errors, bad image, resource limits, or probe failures.

### How do you troubleshoot a service that is not responding?
- Check endpoints, Pods, networking, and test with `kubectl port-forward`.

### What is Prometheus, and how is it used with Kubernetes?
- Monitors metrics—scrapes cluster/app data for alerts and visualization.

### How do you monitor resource usage in a Kubernetes cluster?
- Use `kubectl top`, Metrics Server, or Prometheus/Grafana.

### What is the role of the Kubernetes Dashboard, and how do you access it?
- UI for cluster management—access via `kubectl proxy` and a token.

## CI/CD and GitOps

### How do you integrate Kubernetes with a CI/CD pipeline?
- Build/test in CI, deploy via `kubectl apply` or Helm in CD.

### What is Helm, and how does it simplify Kubernetes deployments?
- Package manager—uses charts to templatize and deploy applications.

### What are Helm charts, and how do you create one?
- Bundled manifests—use `helm create <name>` and edit `values.yaml`.

### What is GitOps, and how does it work with Kubernetes?
- Git as the source of truth—tools sync manifests to the cluster automatically.

### How do you use tools like ArgoCD or Flux with Kubernetes?
- **ArgoCD**: UI-driven sync.
- **Flux**: Lightweight Git watcher.

### How do you roll back a deployment in Kubernetes?
- `kubectl rollout undo deployment/<name>`

### What is the difference between `kubectl apply` and `kubectl create`?
- **Apply**: Declarative, updates existing resources.
- **Create**: Imperative, creates resources one-time.

### How do you manage multiple environments (e.g., dev, staging, prod) in Kubernetes?
- Use Namespaces, ConfigMaps, Helm values, or Kustomize overlays.

### What is a Kubernetes operator, and how does it work?
- Custom controller—manages application lifecycle via CRDs.

### How do you automate deployments in Kubernetes?
- Use CI/CD pipelines, GitOps tools, or scripted `kubectl` commands.

## Advanced Topics

### What is the Custom Resource Definition (CRD) in Kubernetes?
- Extends the API with custom resources (e.g., MyApp).

### How do you extend Kubernetes with custom controllers?
- Write logic (e.g., in Go) to manage CRD instances.

### What is a sidecar container, and how is it used?
- Helper container in a Pod—adds logging, proxying, etc.

### How does Kubernetes handle multi-tenancy?
- Use Namespaces, RBAC, and quotas—limited isolation without additional tools.

### What is Federation in Kubernetes, and when is it used?
- Manages multiple clusters—used for multi-region or disaster recovery.

### How do you upgrade a Kubernetes cluster without downtime?
- Perform rolling upgrades for the control plane and nodes—drain, update, uncordon.

### What is the difference between Kubernetes and OpenShift?
- **OpenShift**: Kubernetes + enterprise features (UI, CI/CD, security).

### How do you configure a Kubernetes cluster on a cloud provider (e.g., AWS, GCP, Azure)?
- **EKS**: `eksctl create`.
- **GKE**: `gcloud container`.
- **AKS**: `az aks`.

### What is KubeVirt, and how does it integrate VMs with Kubernetes?
- Runs VMs as Pods—uses CRDs and QEMU.

### How does Kubernetes handle disaster recovery?
- Back up etcd, replicate clusters, and use external storage.

## Scenario-Based Questions

### A Pod is stuck in a `Pending` state. How do you troubleshoot it?
- Check resources, taints, and PVCs via `kubectl describe`.

### A service is not accessible externally. What steps would you take to debug it?
- Verify service type, endpoints, Pods, and network rules.

### You notice high CPU usage in your cluster. How do you identify the cause?
- Use `kubectl top`, logs, and Prometheus to find the culprits.

### How would you handle a situation where a node in the cluster fails?
- Drain the node, replace it, reschedule Pods, and scale if needed.

### A deployment fails after an update. How do you roll it back?
- `kubectl rollout undo deployment/<name>`

### How do you migrate an application from a single server to Kubernetes?
- Containerize, define Deployment/Service, move data, and cut over.

### A Secret is accidentally exposed. What steps do you take to mitigate it?
- Rotate the Secret, delete the old one, audit access, and encrypt etcd.

### How do you scale an application to handle sudden traffic spikes?
- Use HPA or manually scale with `kubectl scale`, plus cluster autoscaler.

### A StatefulSet is not creating Pods as expected. What could be the issue?
- Check PVC binding, resource limits, or headless Service misconfiguration.

### How do you recover a cluster if the etcd database is corrupted?
- Restore from backup, restart the control plane, and verify.

## Practical/Hands-On Questions

### Write a YAML file to create a Deployment with 3 replicas.
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: nginx-deployment
spec:
    replicas: 3
    selector:
        matchLabels:
            app: nginx
    template:
        metadata:
            labels:
                app: nginx
        spec:
            containers:
            - name: nginx
                image: nginx:latest
                ports:
                - containerPort: 80
```

### How do you expose a Deployment as a LoadBalancer service?
```yaml
apiVersion: v1
kind: Service
metadata:
    name: nginx-service
spec:
    selector:
        app: nginx
    ports:
        - port: 80
            targetPort: 80
    type: LoadBalancer
```

### Write a Network Policy to allow traffic only from specific Pods.
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: allow-specific
spec:
    podSelector:
        matchLabels:
            app: backend
    ingress:
    - from:
        - podSelector:
                matchLabels:
                    app: frontend
        ports:
        - protocol: TCP
            port: 80
```
```

```markdown
# Kubernetes Quick Reference Guide

## Create a CronJob that runs every 5 minutes
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
    name: hello-cron
spec:
    schedule: "*/5 * * * *"
    jobTemplate:
        spec:
            template:
                spec:
                    containers:
                    - name: hello
                        image: busybox
                        command: ["echo", "Hello"]
                    restartPolicy: OnFailure
```

## Set Resource Limits and Requests for a Pod
```yaml
spec:
    containers:
    - name: nginx
        image: nginx
        resources:
            requests:
                cpu: "250m"
                memory: "64Mi"
            limits:
                cpu: "500m"
                memory: "128Mi"
```

## Persistent Volume and Persistent Volume Claim
### Persistent Volume (PV)
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
    name: my-pv
spec:
    capacity:
        storage: 10Gi
    accessModes:
        - ReadWriteOnce
    hostPath:
        path: "/mnt/data"
```

### Persistent Volume Claim (PVC)
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
    name: my-pvc
spec:
    accessModes:
        - ReadWriteOnce
    resources:
        requests:
            storage: 10Gi
```

## Taint a Node and Apply a Toleration to a Pod
### Taint
```bash
kubectl taint nodes <node> app=backend:NoSchedule
```

### Toleration
```yaml
tolerations:
- key: "app"
    operator: "Equal"
    value: "backend"
    effect: "NoSchedule"
```

## Create a ConfigMap and Inject it into a Pod as an Environment Variable
### ConfigMap
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: app-config
data:
    env: "prod"
```

### Pod
```yaml
spec:
    containers:
    - name: nginx
        image: nginx
        env:
        - name: ENV
            valueFrom:
                configMapKeyRef:
                    name: app-config
                    key: env
```

## Configure an Ingress Resource with TLS
### Ingress
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
    name: ingress-tls
spec:
    tls:
    - hosts:
            - example.com
        secretName: tls-secret
    rules:
    - host: example.com
        http:
            paths:
            - path: /
                pathType: Prefix
                backend:
                    service:
                        name: nginx-service
                        port:
                            number: 80
```

### Secret
```bash
kubectl create secret tls tls-secret --key tls.key --cert tls.crt
```

## Helm Chart for a Simple Web Application
### Chart.yaml
```yaml
apiVersion: v2
name: nginx-chart
version: 0.1.0
```

### values.yaml
```yaml
replicaCount: 3
image:
    repository: nginx
    tag: latest
service:
    type: LoadBalancer
    port: 80
```

### templates/deployment.yaml
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: {{ .Release.Name }}-nginx
spec:
    replicas: {{ .Values.replicaCount }}
    selector:
        matchLabels:
            app: nginx
    template:
        metadata:
            labels:
                app: nginx
        spec:
            containers:
            - name: nginx
                image: {{ .Values.image.repository }}:{{ .Values.image.tag }}
                ports:
                - containerPort: 80
```

### templates/service.yaml
```yaml
apiVersion: v1
kind: Service
metadata:
    name: {{ .Release.Name }}-nginx-service
spec:
    selector:
        app: nginx
    ports:
    - port: {{ .Values.service.port }}
        targetPort: 80
    type: {{ .Values.service.type }}
```
```