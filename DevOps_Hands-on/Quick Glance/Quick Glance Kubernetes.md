## Kubernetes

- **Pods**: The smallest and simplest Kubernetes object. A Pod represents a single instance of a running process in your cluster.
- **ReplicaSets**: Ensures a specified number of pod replicas are running at any given time.
- **Deployments**: Provides declarative updates for Pods and ReplicaSets.
- **Services**: An abstraction which defines a logical set of Pods and a policy by which to access them - like load-balancers.
- **ConfigMaps**: Used to store configuration data in key-value pairs which can be consumed by pods.
- **Secrets**: Manages sensitive information, like passwords, OAuth tokens, and ssh keys, which can be referenced in pod definitions.
- **PersistentVolumes (PV)**: A piece of storage in the cluster that has been provisioned by an administrator or dynamically provisioned using Storage Classes.
- **PersistentVolumeClaims (PVC)**: Requests storage resources defined by a PersistentVolume.
- **Namespaces**: Provides a scope for names. Resources like Pods, Services, and Deployments can be isolated within namespaces.
- **Nodes**: A worker machine in Kubernetes, either virtual or physical, where containers will be launched by Kubernetes.
- **DaemonSets**: Ensures that all (or some) Nodes run a copy of a Pod. As nodes are added to the cluster, Pods are added to them. As nodes are removed from the cluster, those Pods are garbage collected.
- **Jobs**: Creates one or more Pods and ensures that a specified number of them successfully terminate. Good for batch processes.
- **CronJobs**: Manages time-based Jobs, similar to cron in Unix-like systems.
- **StatefulSets**: Manages the deployment and scaling of a set of Pods, and provides guarantees about the ordering and uniqueness of these Pods.
- **Ingress**: Manages external access to the services in a cluster, typically HTTP.
- **HorizontalPodAutoscaler**: Scales a Deployment, ReplicaSet, or ReplicationController based on observed CPU utilization or other select metrics.
- **VerticalPodAutoscaler**: Automatically adjusts the compute resources of pods based on usage.
- **NetworkPolicies**: Specifies how groups of pods are allowed to communicate with each other and other network endpoints.
- **ServiceAccounts**: Provides an identity for processes that run in a Pod, which can be used for authenticating to the API server.
- **Endpoints**: Exposes the IP addresses of a service's backing pods.
- **ResourceQuotas**: Provides constraints that limit aggregate resource consumption per namespace.
- **LimitRanges**: Constrains resource allocations (to Pods or Containers) in a namespace.
- **Roles and RoleBindings (for RBAC - Role-Based Access Control)**: Define permissions for users or service accounts within a namespace.
- **ClusterRoles and ClusterRoleBindings**: Similar to Roles but cluster-wide, not namespace-specific.
- **CustomResourceDefinitions (CRDs)**: Allows users to create new types of resources without adding another API server.
- **StorageClasses**: Describes different classes or profiles of storage in the cluster.
- **PodDisruptionBudgets**: Ensures that a specified number of pods are available even during voluntary disruptions like node drains or upgrades.

Version of each Kubernetes resource example, tailored for interviews with key options and configurations:

### **Pod**
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
    labels:
        app: my-app
spec:
    containers:
    - name: my-container
        image: nginx:1.14.2
        ports:
        - containerPort: 80
        resources:
            requests:
                cpu: "100m"
                memory: "128Mi"
            limits:
                cpu: "500m"
                memory: "256Mi"
        readinessProbe:
            httpGet:
                path: /
                port: 80
            initialDelaySeconds: 5
            periodSeconds: 10
        livenessProbe:
            httpGet:
                path: /healthz
                port: 80
            initialDelaySeconds: 15
            periodSeconds: 20
    restartPolicy: Always
    nodeSelector:
        disktype: ssd
    tolerations:
    - key: "key"
        operator: "Equal"
        value: "value"
        effect: "NoSchedule"
```

### **ReplicaSet**
```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
    name: my-replicaset
    labels:
        app: my-app
spec:
    replicas: 3
    selector:
        matchLabels:
            app: my-app
    template:
        metadata:
            labels:
                app: my-app
        spec:
            containers:
            - name: my-container
                image: nginx:1.14.2
                ports:
                - containerPort: 80
```

### **Deployment**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: my-deployment
    labels:
        app: my-app
spec:
    replicas: 3
    strategy:
        type: RollingUpdate
        rollingUpdate:
            maxSurge: 1
            maxUnavailable: 0
    selector:
        matchLabels:
            app: my-app
    template:
        metadata:
            labels:
                app: my-app
        spec:
            containers:
            - name: my-container
                image: nginx:1.14.2
                ports:
                - containerPort: 80
                imagePullPolicy: IfNotPresent
```

### **Service**
```yaml
apiVersion: v1
kind: Service
metadata:
    name: my-service
    annotations:
        service.beta.kubernetes.io/aws-load-balancer-type: "nlb"
spec:
    selector:
        app: my-app
    ports:
    - protocol: TCP
        port: 80
        targetPort: 80
        name: http
    type: LoadBalancer
    sessionAffinity: ClientIP
    externalTrafficPolicy: Local
```

### **ConfigMap**
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: my-config
data:
    config.property: "some-value"
    another.property: |
        line1
        line2
binaryData:
    binaryFile: <base64 encoded>
```

### **Secret**
```yaml
apiVersion: v1
kind: Secret
metadata:
    name: my-secret
type: Opaque
data:
    username: dXNlcm5hbWU=  # base64 encoded
    password: cGFzc3dvcmQ=  # base64 encoded
stringData:
    config.yaml: |
        apiUrl: "https://myapi.com"
        token: "my-token"
```

### **PersistentVolume**
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
    name: pv0001
spec:
    capacity:
        storage: 5Gi
    accessModes:
    - ReadWriteOnce
    persistentVolumeReclaimPolicy: Retain
    storageClassName: standard
    nfs:
        server: nfs-server.example.com
        path: "/exports"
```

### **PersistentVolumeClaim**
```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
    name: my-claim
spec:
    accessModes:
    - ReadWriteOnce
    resources:
        requests:
            storage: 5Gi
    storageClassName: standard
    volumeMode: Filesystem
    volumeName: pv0001  # optional, binds to a specific PV
```

### **Namespace**
```yaml
apiVersion: v1
kind: Namespace
metadata:
    name: my-namespace
    labels:
        environment: development
```

### **Node**
Nodes are generally managed by the cloud provider or cluster management layer, not typically created by YAML. However, you can label or taint nodes:
```bash
kubectl label nodes <node-name> disktype=ssd
kubectl taint nodes <node-name> key=value:NoSchedule
```

### **DaemonSet**
```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
    name: my-daemonset
    namespace: kube-system
spec:
    selector:
        matchLabels:
            app: my-app
    template:
        metadata:
            labels:
                app: my-app
        spec:
            nodeSelector:
                node-role.kubernetes.io/master: ""
            tolerations:
            - key: "node-role.kubernetes.io/master"
                effect: NoSchedule
            containers:
            - name: my-container
                image: nginx:1.14.2
```

### **Job**
```yaml
apiVersion: batch/v1
kind: Job
metadata:
    name: my-job
spec:
    completions: 5
    parallelism: 2
    backoffLimit: 6
    template:
        spec:
            containers:
            - name: my-job-container
                image: busybox
                command: ["/bin/sh", "-c", "echo Hello, Kubernetes! && sleep 30"]
            restartPolicy: OnFailure
```

### **CronJob**
```yaml
apiVersion: batch/v1beta1
kind: CronJob
metadata:
    name: my-cronjob
spec:
    schedule: "*/1 * * * *"
    concurrencyPolicy: Forbid
    failedJobsHistoryLimit: 1
    successfulJobsHistoryLimit: 3
    suspend: false
    jobTemplate:
        spec:
            template:
                spec:
                    containers:
                    - name: my-cronjob-container
                        image: busybox
                        command:
                        - /bin/sh
                        - -c
                        - date; echo Hello from the Kubernetes cron job
                    restartPolicy: OnFailure
```

### **StatefulSet**
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
    name: my-statefulset
spec:
    serviceName: "my-service"
    replicas: 3
    selector:
        matchLabels:
            app: my-app
    template:
        metadata:
            labels:
                app: my-app
        spec:
            containers:
            - name: my-container
                image: nginx:1.14.2
                volumeMounts:
                - name: www
                    mountPath: /usr/share/nginx/html
    volumeClaimTemplates:
    - metadata:
            name: www
        spec:
            accessModes: ["ReadWriteOnce"]
            resources:
                requests:
                    storage: 1Gi
```

### **Ingress**
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
    name: my-ingress
    annotations:
        kubernetes.io/ingress.class: "nginx"
        nginx.ingress.kubernetes.io/rewrite-target: /$2
spec:
    rules:
    - host: example.com
        http:
            paths:
            - path: /path/(.*)
                pathType: Prefix
                backend:
                    service:
                        name: my-service
                        port: 
                            number: 80
```

### **HorizontalPodAutoscaler**
```yaml
apiVersion: autoscaling/v2beta2
kind: HorizontalPodAutoscaler
metadata:
    name: my-hpa
spec:
    scaleTargetRef:
        apiVersion: apps/v1
        kind: Deployment
        name: my-deployment
    minReplicas: 1
    maxReplicas: 10
    metrics:
    - type: Resource
        resource:
            name: cpu
            target:
                type: Utilization
                averageUtilization: 50
    - type: Pods
        pods:
            metric:
                name: packets-per-second
            target:
                type: AverageValue
                averageValue: 1k
```

### **VerticalPodAutoscaler**
Requires additional installation of Vertical Pod Autoscaler:
```yaml
apiVersion: autoscaling.k8s.io/v1
kind: VerticalPodAutoscaler
metadata:
    name: my-vpa
spec:
    targetRef:
        apiVersion: "apps/v1"
        kind: Deployment
        name: my-deployment
    updatePolicy:
        updateMode: "Auto"
    resourcePolicy:
        containerPolicies:
        - containerName: '*'
            minAllowed:
                cpu: 250m
                memory: 64Mi
            maxAllowed:
                cpu: 2
                memory: 4Gi
```

### **NetworkPolicy**
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: my-network-policy
spec:
    podSelector:
        matchLabels:
            role: db
    policyTypes:
    - Ingress
    - Egress
    ingress:
    - from:
        - podSelector:
                matchLabels:
                    role: frontend
        ports:
        - protocol: TCP
            port: 6379
    egress:
    - to:
        - ipBlock:
                cidr: 10.0.0.0/24
                except:
                - 10.0.0.0/28
        ports:
        - protocol: TCP
            port: 5978
```

### **ServiceAccount**
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
    name: my-service-account
    namespace: my-namespace
secrets:
- name: my-secret
imagePullSecrets:
- name: regcred
```

### **Endpoints**
```yaml
apiVersion: v1
kind: Endpoints
metadata:
    name: my-endpoints
subsets:
    - addresses:
        - ip: 192.168.1.1
            nodeName: worker1
        ports:
        - port: 80
            name: http
```

### **ResourceQuota**
```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
    name: my-quota
spec:
    hard:
        pods: "10"
        requests.cpu: "4"
        requests.memory: 6Gi
        limits.cpu: "10"
        limits.memory: 10Gi
        configmaps: "10"
        secrets: "10"
        services: "5"
        services.loadbalancers: "1"
```

### **LimitRange**
```yaml
apiVersion: v1
kind: LimitRange
metadata:
    name: my-limitrange
spec:
    limits:
    - type: Pod
        max:
            cpu: "2"
            memory: 1Gi
        min:
            cpu: 200m
            memory: 6Mi
    - type: Container
        default:
            cpu: 500m
            memory: 512Mi
        defaultRequest:
            cpu: 100m
            memory: 128Mi
```

### **Roles and RoleBindings**
```yaml
# Role
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
    namespace: my-namespace
    name: pod-reader
rules:
- apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "watch", "list"]

# RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    name: read-pods
    namespace: my-namespace
subjects:
- kind: User
    name: my-user
    apiGroup: rbac.authorization.k8s.io
roleRef:
    kind: Role
    name: pod-reader
    apiGroup: rbac.authorization.k8s.io
```

### **ClusterRoles and ClusterRoleBindings**
```yaml
# ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
    name: secret-reader
rules:
- apiGroups: [""]
    resources: ["secrets"]
    verbs: ["get", "watch", "list"]

# ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
    name: read-secrets-global
subjects:
- kind: User
    name: my-user
    apiGroup: rbac.authorization.k8s.io
roleRef:
    kind: ClusterRole
    name: secret-reader
    apiGroup: rbac.authorization.k8s.io
```

### **CustomResourceDefinition**
```yaml
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
    name: crontabs.stable.example.com
spec:
    group: stable.example.com
    versions:
    - name: v1
        served: true
        storage: true
        schema:
            openAPIV3Schema:
                type: object
                properties:
                    spec:
                        type: object
                        properties:
                            cronSpec:
                                type: string
                            image:
                                type: string
                            replicas:
                                type: integer
        subresources:
            status: {}
    scope: Namespaced
    names:
        plural: crontabs
        singular: crontab
        kind: CronTab
        shortNames:
        - ct
```

### **StorageClass**
```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
    name: standard
provisioner: kubernetes.io/aws-ebs
parameters:
    type: gp2
    zones: us-west-2a, us-west-2b
reclaimPolicy: Retain
allowVolumeExpansion: true
mountOptions:
    - debug
volumeBindingMode: WaitForFirstConsumer
```

### **PodDisruptionBudget**
```yaml
apiVersion: policy/v1beta1
kind: PodDisruptionBudget
metadata:
    name: my-pdb
spec:
    minAvailable: 2
    selector:
        matchLabels:
            app: my-app
```

For each of these YAML files, you can use `kubectl apply -f <filename>.yaml` to deploy them to your Kubernetes cluster. Here are some `kubectl` commands that might be useful during an interview:

- **Pod**: `kubectl run my-pod --image=nginx --port=80 --restart=Never --dry-run=client -o yaml > pod.yaml`
- **Service**: `kubectl expose deployment my-deployment --type=LoadBalancer --port=80 --target-port=8080 --name=my-service`
- **ConfigMap**: `kubectl create configmap my-config --from-file=config.properties`
- **Secret**: `kubectl create secret generic my-secret --from-literal=username=user --from-literal=password=pass`
- **Namespace**: `kubectl create namespace my-namespace`
- **Deployment**: `kubectl create deployment my-deployment --image=nginx --replicas=3 --dry-run=client -o yaml > deploy.yaml`
- **HorizontalPodAutoscaler**: `kubectl autoscale deployment my-deployment --min=1 --max=10 --cpu-percent=50`

## Important Kubernetes Commands:

- **kubectl get pods**: Lists all pods in the current namespace.
    - Syntax: `kubectl get pods [-n <namespace>] [-o <output_format>]`
    - Example: `kubectl get pods -n default -o wide`

- **kubectl get nodes**: Shows all nodes in the cluster.
    - Syntax: `kubectl get nodes [-o <output_format>]`
    - Example: `kubectl get nodes -o json`

- **kubectl get services**: Lists all services in the current namespace.
    - Syntax: `kubectl get services [-n <namespace>] [-o <output_format>]`
    - Example: `kubectl get services -n kube-system`

- **kubectl describe pod <pod-name>**: Provides detailed information about a specific pod.
    - Syntax: `kubectl describe pod <pod-name> [-n <namespace>]`
    - Example: `kubectl describe pod my-pod -n my-namespace`

- **kubectl logs <pod-name>**: Retrieves logs from a container in a pod.
    - Syntax: `kubectl logs <pod-name> [-c <container-name>] [--previous] [-f]`
    - Example: `kubectl logs my-pod -c my-container --previous`

- **kubectl exec -it <pod-name> -- /bin/bash**: Opens an interactive shell into a container within a pod.
    - Syntax: `kubectl exec -it <pod-name> [-c <container-name>] -- <command>`
    - Example: `kubectl exec -it my-pod -c main-container -- /bin/bash`

- **kubectl apply -f <file-name>.yaml**: Applies a configuration to a resource by filename or stdin.
    - Syntax: `kubectl apply -f <file-name>.yaml [-n <namespace>]`
    - Example: `kubectl apply -f deployment.yaml`

- **kubectl delete pod <pod-name>**: Deletes a pod.
    - Syntax: `kubectl delete pod <pod-name> [-n <namespace>]`
    - Example: `kubectl delete pod my-pod`

- **kubectl scale --replicas=3 deployment/<deployment-name>**: Scales the number of pods for a deployment.
    - Syntax: `kubectl scale --replicas=<number> deployment/<deployment-name> [-n <namespace>]`
    - Example: `kubectl scale --replicas=3 deployment/my-app`

- **kubectl rollout status deployment/<deployment-name>**: Checks the status of a deployment rollout.
    - Syntax: `kubectl rollout status deployment/<deployment-name> [-n <namespace>]`
    - Example: `kubectl rollout status deployment/my-deployment`

- **kubectl rollout undo deployment/<deployment-name>**: Rolls back to the previous deployment revision.
    - Syntax: `kubectl rollout undo deployment/<deployment-name> [-n <namespace>]`
    - Example: `kubectl rollout undo deployment/my-deployment`

- **kubectl create deployment <deployment-name> --image=<image-name>**: Creates a new deployment with the specified image.
    - Syntax: `kubectl create deployment <deployment-name> --image=<image-name> [-n <namespace>]`
    - Example: `kubectl create deployment nginx --image=nginx`

- **kubectl get deployments**: Lists all deployments in the current namespace.
    - Syntax: `kubectl get deployments [-n <namespace>] [-o <output_format>]`
    - Example: `kubectl get deployments -o yaml`

- **kubectl port-forward <pod-name> <local-port>:<pod-port>**: Forwards traffic from a local port to a port on the pod.
    - Syntax: `kubectl port-forward <pod-name> <local-port>:<pod-port> [-n <namespace>]`
    - Example: `kubectl port-forward my-pod 8080:80`

- **kubectl label nodes <node-name> <key>=<value>**: Adds or updates a label on a node.
    - Syntax: `kubectl label nodes <node-name> <key>=<value> [--overwrite]`
    - Example: `kubectl label nodes worker1 disktype=ssd`

- **kubectl taint nodes <node-name> <key>=<value>:<effect>**: Adds a taint on a node, which can repel pods unless they tolerate the taint.
    - Syntax: `kubectl taint nodes <node-name> <key>=<value>:<effect> [--overwrite]`
    - Example: `kubectl taint nodes worker2 apptype=legacy:NoSchedule`

- **kubectl get events**: Shows all events in the current namespace.
    - Syntax: `kubectl get events [-n <namespace>] [-o <output_format>]`
    - Example: `kubectl get events -n my-namespace --sort-by='.lastTimestamp'`

- **kubectl config view**: Displays current kubeconfig settings.
    - Syntax: `kubectl config view [--minify] [--flatten]`
    - Example: `kubectl config view --minify`

- **kubectl cluster-info**: Displays endpoint information about the master and services in the cluster.
    - Syntax: `kubectl cluster-info`
    - Example: `kubectl cluster-info`

**Explain how you would debug a Pod that's not starting up correctly.**
First, use `kubectl describe pod <pod-name>` to check events and status. Look for errors in the Events section. Check the logs with `kubectl logs <pod-name>` for application issues. If there's no output or if you need to interact with the container, use `kubectl exec -it <pod-name> -- /bin/sh` to enter the container. Also, ensure the pod's image exists in the cluster or can be pulled from the registry.

**What is a Kubernetes Deployment? How does it differ from a ReplicaSet?**
A Deployment provides declarative updates to applications, managing ReplicaSets to ensure the correct number and state of Pods. It differs from a ReplicaSet by adding features like rolling updates and rollbacks, making it easier to manage application updates.

**How do you perform a rolling update for a Deployment? Explain the strategy options.**
You perform a rolling update by modifying the Deployment's pod template, like changing the container image. The strategy options include:
- **RollingUpdate:** Gradually replaces pods with new ones, configurable with `maxSurge` (how many new pods can be created) and `maxUnavailable` (how many pods can be down during update).
- **Recreate:** Terminates all existing pods before creating new ones, leading to downtime but ensuring no old and new versions run concurrently.

**Describe the different types of Services in Kubernetes and their use cases.**
- **ClusterIP:** Exposes the service on a cluster-internal IP, best for services only accessed within the cluster.
- **NodePort:** Exposes the service on each node's IP at a static port, useful for external access without a load balancer.
- **LoadBalancer:** Gets an external load balancer (if available in the environment) for external access, typically used in cloud providers.
- **ExternalName:** Maps the service to a DNS name, not used for load balancing but for service discovery.

**How do you mount a ConfigMap as an environment variable or volume in a Pod?**
For environment variables:
```yaml
env:
- name: SPECIAL_LEVEL_KEY
    valueFrom:
        configMapKeyRef:
            name: special-config
            key: special.how
```
For volumes:
```yaml
volumes:
- name: config-volume
    configMap:
        name: special-config
volumeMounts:
- mountPath: /etc/config
    name: config-volume
```

**How would you securely use Secrets in a Pod?**
Mount Secrets as files in a volume for minimal exposure or use them as environment variables. For file mounts:
```yaml
volumes:
- name: secret-volume
    secret:
        secretName: mysecret
volumeMounts:
- name: secret-volume
    readOnly: true
    mountPath: "/etc/secrets"
```
For environment variables:
```yaml
env:
- name: SECRET_USERNAME
    valueFrom:
        secretKeyRef:
            name: mysecret
            key: username
```

**What are Namespaces in Kubernetes, and why are they useful?**
Namespaces provide a scope for names in a Kubernetes cluster, allowing multiple virtual clusters within the same physical cluster. They're useful for:
- Organizing resources by project, team, or environment.
- Implementing resource quotas.
- Providing a level of isolation for security and resource management.

**How can you schedule Pods on specific nodes?**
Use `nodeSelector` in the pod spec to match node labels:
```yaml
nodeSelector:
    disktype: ssd
```
Or use `nodeAffinity` for more complex rules. Taints and tolerations can also be used to repel or attract pods to nodes.

**What problem does a DaemonSet solve in Kubernetes?**
DaemonSets ensure that all (or some) nodes run a copy of a Pod, useful for deploying cluster-wide services like logging or monitoring agents where you want one instance per node.

**What are the key differences between a Deployment and a StatefulSet?**
StatefulSets provide guarantees about the ordering and uniqueness of pods, stable network identities, and persistent storage. Deployments treat all pods as interchangeable, whereas StatefulSets maintain identity and order, which is crucial for stateful applications like databases.

**Describe how you would configure an Ingress to route traffic to different services.**
Define rules in the Ingress resource:
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
    name: example-ingress
spec:
    rules:
    - host: example.com
        http:
            paths:
            - path: /api
                pathType: Prefix
                backend:
                    service:
                        name: api-service
                        port: 
                            number: 80
            - path: /
                pathType: Prefix
                backend:
                    service:
                        name: web-service
                        port: 
                            number: 80
```
**How do you implement a NetworkPolicy to restrict pod communication?**
Define a NetworkPolicy with selectors and rules for ingress/egress:
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: allow-from-namespace
spec:
    podSelector:
        matchLabels:
            role: frontend
    policyTypes:
    - Ingress
    ingress:
    - from:
        - namespaceSelector:
                matchLabels:
                    environment: production
        ports:
        - protocol: TCP
            port: 80
```
**How can you bind a ServiceAccount to a Role or ClusterRole?**
Use RoleBindings or ClusterRoleBindings:
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    name: read-pods
    namespace: default
subjects:
- kind: ServiceAccount
    name: my-service-account
    namespace: default
roleRef:
    kind: Role
    name: pod-reader
    apiGroup: rbac.authorization.k8s.io
```
**How would you set up ResourceQuotas to prevent a namespace from using too many resources?**
Define a ResourceQuota in the namespace:
```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
    name: compute-resources
spec:
    hard:
        pods: "4"
        requests.cpu: "1"
        requests.memory: 1Gi
        limits.cpu: "2"
        limits.memory: 2Gi
```

### Amazon EC2 (Elastic Compute Cloud)
**Questions:**
- **What is EC2, and how does it work?**
    - EC2 is a web service that provides resizable compute capacity in the cloud. It allows you to launch virtual servers called instances, where you can install and run applications. EC2 instances are customizable with different instance types, operating systems, and software packages.

### Amazon S3 (Simple Storage Service)
**Questions:**
- **What is Amazon S3 used for?**
    - S3 is an object storage service offering scalability, data availability, security, and performance for data storage and retrieval. It's used for backup, archiving, content distribution, big data analytics, and serving static website content.

### Amazon RDS (Relational Database Service)
**Questions:**
- **What is RDS, and what databases does it support?**
    - RDS is a managed database service providing cost-efficient and resizable capacity while managing common database administration tasks. It supports MySQL, PostgreSQL, MariaDB, Oracle, SQL Server, and Amazon Aurora.

### Amazon VPC (Virtual Private Cloud)
**Questions:**
- **What is a VPC, and why is it important?**
    - VPC lets you define a logically isolated section of the AWS cloud where you can launch AWS resources in a virtual network you define. It's crucial for security, network isolation, and compliance.

### AWS Lambda
**Questions:**
- **What is AWS Lambda, and when would you use it?**
    - Lambda is a serverless compute service that runs your code in response to events without provisioning or managing servers. Use it for backend services, real-time data processing, or as part of microservices architecture for event-driven applications.

### Amazon CloudFront
**Questions:**
- **What is CloudFront, and what are its benefits?**
    - CloudFront is a fast content delivery network (CDN) service that securely delivers data, videos, applications, and APIs to customers globally with low latency and high transfer speeds.

### Amazon EBS (Elastic Block Store)
**Questions:**
- **Explain EBS and its use case.**
    - EBS provides block-level storage volumes for use with EC2 instances. It's used for database storage, file systems, or for any application needing persistent, low-latency storage.

### AWS IAM (Identity and Access Management)
**Questions:**
- **What is IAM, and why is it important?**
    - IAM enables you to manage access to AWS services and resources securely. It's vital for controlling who can access what within your AWS account, implementing the principle of least privilege.

### AWS CloudWatch
**Questions:**
- **What is CloudWatch used for?**
    - CloudWatch monitors AWS resources and applications, collecting and tracking metrics, logs, and events. It's used for observing performance, setting alarms, and automating responses to system-wide operational changes.

### Amazon Route 53
**Questions:**
- **What is Route 53?**
    - Route 53 is a scalable Domain Name System (DNS) service that translates human-readable domain names into IP addresses. It also includes DNS failover, traffic management, and health checks.

### AWS Auto Scaling
**Questions:**
- **What is the purpose of Auto Scaling in AWS?**
    - Auto Scaling helps maintain application availability and allows you to dynamically scale EC2 capacity up or down according to conditions you define, like CPU utilization or custom metrics.

### Amazon DynamoDB
**Questions:**
- **What is DynamoDB?**
    - DynamoDB is a fully managed NoSQL database service that provides fast and predictable performance with seamless scalability. It's designed for applications that need consistent, single-digit millisecond latency at any scale.

### AWS Elastic Load Balancing (ELB)
**Questions:**
- **What types of load balancers does AWS offer?**
    - AWS provides Application Load Balancer (ALB) for HTTP/HTTPS traffic, Network Load Balancer (NLB) for handling millions of requests per second with ultra-low latencies, and Classic Load Balancer (CLB) for basic load balancing.

### AWS ECS (Elastic Container Service)
**Questions:**
- **What is ECS, and how does it differ from EKS?**
    - ECS is a container management service for Docker containers, fully managed by AWS. EKS (Elastic Kubernetes Service) provides a managed Kubernetes environment, allowing you to run Kubernetes on AWS without managing the control plane.

### AWS CloudFormation
**Questions:**
- **What is CloudFormation used for?**
    - CloudFormation provides an easy way to model and set up AWS resources using templates, allowing for infrastructure as code, repeatable provisioning of AWS environments, and version control of infrastructure.

### AWS Security Groups
**Questions:**
- **What are Security Groups in AWS?**
    - Security Groups act as a virtual firewall for EC2 instances, controlling inbound and outbound traffic at the instance level. They are stateful, meaning return traffic is automatically allowed.

### EC2 Instances
**Question:**
You've launched an EC2 instance, but you can't SSH into it. What would you check?

**Answer:**
First, verify if the instance is in a running state. Check:
- **Security Groups:** Ensure they allow inbound SSH traffic (port 22) from your IP or anywhere if intended.
- **Network ACLs:** Confirm they aren't blocking SSH traffic.
- **Key Pair:** Make sure you're using the correct key pair and permissions for the .pem file are correct (chmod 400 key.pem).
- **Instance IP:** Use the correct public IP or DNS name.
- **System Log:** Check system logs through AWS Console for boot issues.
- **Instance-Profile:** Verify if an IAM role is correctly attached if needed for SSH.

### S3
**Question:**
You're unable to access objects in an S3 bucket where you should have permissions. What steps would you take?

**Answer:**
Check:
- **Bucket Policy:** Ensure it's not overly restrictive or missing necessary permissions.
- **IAM User/Group/Role Permissions:** Verify you have the correct S3 permissions in your IAM policy.
- **Object Ownership:** Confirm you have access to the objects if the bucket uses bucket-owner-enforced setting for object ownership.
- **Network Policies:** If using VPC endpoints, check if the endpoint policy allows access.
- **Region and DNS:** Ensure you're accessing the correct region and using the right endpoint.

### RDS
**Question:**
An RDS instance is not accessible, and you're getting a connection timeout. How would you troubleshoot this?

**Answer:**
Troubleshoot by:
- **Security Groups:** Check if the RDS instance's security group allows inbound connections from your IP.
- **Public Accessibility:** Verify if the instance is set to be publicly accessible if you're trying to connect from outside the VPC.
- **VPC:** Ensure your EC2 instance or client is in the same VPC or has appropriate VPC peering if not.
- **Database Status:** Look at the RDS console for instance status; if it's not available, check for maintenance or failover issues.
- **Parameter Groups:** Check if there are restrictive parameter settings.
- **Logs:** Examine error logs in CloudWatch Logs for connectivity issues.

### VPC
**Question:**
You've set up a VPC, but resources in different subnets can't communicate. What might be the problem?

**Answer:**
Consider:
- **Route Tables:** Ensure routes are correctly configured to allow traffic between subnets.
- **Network ACLs:** Check if they're not blocking inter-subnet traffic.
- **Security Groups:** They need to permit traffic between instances/subnets.
- **Subnet Configuration:** Verify subnet CIDR blocks are not overlapping or incorrectly set up.
- **VPC Peering:** If subnets are in different VPCs, confirm peering connections are active and properly configured.

### Lambda
**Question:**
A Lambda function isn't executing as expected. How would you diagnose the issue?

**Answer:**
Diagnose by:
- **CloudWatch Logs:** Check logs for errors or unexpected behavior.
- **Execution Role:** Ensure the Lambda function has the correct IAM permissions to access other AWS resources.
- **Timeout Settings:** Verify if the function is timing out before completing.
- **Memory/Timeout Configuration:** Check if the function has enough memory or if the timeout is too short.
- **Event Source Mapping:** If triggered by another service, ensure the mapping is set up correctly.
- **Code:** Review the code for logic errors, especially around error handling or asynchronous execution.

### CloudFront
**Question:**
Your CloudFront distribution isn't serving content from your S3 bucket. What troubleshooting steps would you take?

**Answer:**
Steps include:
- **Origin Configuration:** Ensure the S3 bucket is correctly set as the origin and permissions allow CloudFront to access it.
- **Invalidations:** If you've recently changed content, consider invalidating the cache or wait for TTL expiry.
- **Permissions:** Check bucket policies, CORS settings if relevant, and ensure CloudFront has read access.
- **Behaviors:** Verify distribution behaviors are configured to route to the correct origin for your paths.
- **SSL/TLS:** If using HTTPS, check SSL/TLS settings match between CloudFront and S3.
- **Error Logs:** Look at CloudFront error logs for specific issues.

### IAM
**Question:**
An IAM user cannot perform an action on an AWS resource despite having permissions. What do you check?

**Answer:**
Check:
- **Policy Attachment:** Ensure the correct policy is attached to the user or group.
- **Policy Syntax:** Verify the policy document's syntax and that it includes necessary actions and resources.
- **Deny Statements:** Look for explicit deny statements in policies that might override allows.
- **Condition Keys:** If policies use condition keys, check if conditions are met.
- **Service Control Policies (SCPs):** If using AWS Organizations, check if SCPs are restricting actions.
- **MFA:** Ensure Multi-Factor Authentication isn't required for the action if it's set up.

### Auto Scaling
**Question:**
Your Auto Scaling group isn't scaling as expected. How do you troubleshoot this?

**Answer:**
Troubleshooting steps:
- **CloudWatch Alarms:** Check if alarms are triggering correctly when conditions are met.
- **Scaling Policies:** Review if scaling policies are defined properly with correct metrics and thresholds.
- **Cooldown Period:** Ensure cooldown periods aren't preventing subsequent scaling actions.
- **Health Checks:** Verify health checks are configured to detect instance health accurately.
- **Instance Termination:** Look into why instances might be terminating unexpectedly.
- **Capacity Limits:** Check if you've hit service limits or if the max capacity is set too low.

These scenarios reflect common troubleshooting tasks in AWS, and discussing them in interviews shows your practical understanding of AWS operations.

Here are some Kubernetes troubleshooting scenarios you might encounter in an interview, along with potential answers:

### Pods
**Question:**
You've deployed a Pod, but it's stuck in the Pending state. How would you troubleshoot this?

**Answer:**
Check:
- **Resource Requests:** Ensure the node has enough resources for the Pod's requests. Use `kubectl describe node <node-name>` to check resource availability.
- **Node Affinity or Taints:** Verify if there are node taints that might prevent scheduling or if node affinity rules are too restrictive.
- **PVC Binding:** If the Pod requires a PVC, check if it's bound to a PV. Use `kubectl describe pvc <pvc-name>`.
- **Image Pull:** Look for image pull errors in `kubectl describe pod <pod-name>`; check if the image exists in the registry or if credentials are correct.
- **Pod Events:** Examine events with `kubectl describe pod <pod-name>` for scheduling or admission controller issues.

### Deployments
**Question:**
Your Deployment isn't updating to the new version of your application. What steps would you take to investigate?

**Answer:**
Investigate by:
- **Rollout Status:** Check the rollout status with `kubectl rollout status deployment <deployment-name>` for any errors.
- **ReplicaSet:** Look at the ReplicaSets with `kubectl get rs -l app=<app-label>` to see if the new version is being scaled up.
- **Events:** Use `kubectl describe deployment <deployment-name>` to review events for issues during the update.
- **Readiness Probes:** Ensure readiness probes aren't too strict, preventing the new pods from becoming ready.
- **Image Pull Policy:** Check if the `imagePullPolicy` is set to `Always` if you expect updates with each rollout.

### Services
**Question:**
A Service isn't routing traffic to your Pods. What could be the reasons?

**Answer:**
Possible reasons include:
- **Selector Mismatch:** Ensure the Service's selector matches the labels of your Pods. Check with `kubectl get pods --show-labels`.
- **Endpoints:** Use `kubectl get endpoints <service-name>` to confirm endpoints are being created for the service.
- **Network Policies:** If Network Policies are in place, they might be blocking traffic; check with `kubectl describe networkpolicy`.
- **DNS:** Verify DNS resolution works within the cluster, especially if using a headless service or for external DNS issues.
- **Service Type:** Make sure the service type (ClusterIP, NodePort, etc.) matches your access requirements.

### ConfigMaps and Secrets
**Question:**
Your Pod isn't picking up changes from an updated ConfigMap or Secret. How do you troubleshoot this?

**Answer:**
Troubleshoot by:
- **Restart:** Generally, you need to restart the Pod for ConfigMaps or Secrets to take effect if mounted as volumes. Check if there's an automated way like a rolling update configured.
- **Mount Path:** Verify the mount path in the Pod spec matches where your application expects to find the configuration.
- **Key-Value Pairs:** Confirm the keys in your ConfigMap or Secret match what your application expects.
- **Volume Name:** Ensure volume names match between the container and ConfigMap/Secret definitions.
- **Permissions:** Check if the process in the container has the right permissions to read the mounted files.

### Persistent Volumes (PV) and Persistent Volume Claims (PVC)
**Question:**
Your application can't write to a mounted volume. What might be wrong?

**Answer:**
Consider:
- **PV Status:** Check if the PV is bound to the PVC using `kubectl describe pv <pv-name>`.
- **Access Mode:** Ensure the access mode of the PV matches what your application needs (ReadWriteOnce, ReadWriteMany, etc.).
- **Permissions:** Verify the container has the correct permissions to write to the mounted directory.
- **Storage Class:** If using dynamic provisioning, check if the StorageClass is configured correctly.
- **Connection:** For networked storage like NFS, check network connectivity to the storage endpoint.

### Ingress
**Question:**
Your Ingress isn't routing requests to your backend service. What would you check?

**Answer:**
Steps include:
- **Ingress Controller:** Ensure an Ingress controller is running and configured correctly (`kubectl get pods --namespace=ingress-nginx` for NGINX Ingress).
- **Rules:** Review the Ingress rules to confirm they match your routing needs. Use `kubectl describe ingress <ingress-name>` to see applied rules.
- **Service:** Verify the Service your Ingress points to is up and the endpoints are correct.
- **Annotations:** Check if necessary annotations for SSL, path rewrites, etc., are set correctly.
- **DNS:** Confirm DNS is correctly pointing to the Ingress controller's load balancer or external IP.

### Resource Management
**Question:**
Your cluster is running out of resources, but you've allocated less than expected. How do you troubleshoot?

**Answer:**
Troubleshoot by:
- **Resource Limits:** Look at `kubectl describe node <node-name>` to see total usage vs. available resources.
- **Pod Status:** Check if there are pods in Pending or Unknown states taking up allocations.
- **Overcommitment:** Use `kubectl top nodes` and `kubectl top pods` to see if there's resource overcommitment.
- **LimitRange:** Ensure LimitRange isn't set too low, causing pods to request more than they need.
- **Namespace Quotas:** If using namespaces, check if ResourceQuotas are limiting available resources.

### Networking
**Question:**
Pods can't communicate with each other or external services. What could be the issue?

**Answer:**
Investigate:
- **Network Policies:** Check if overly restrictive NetworkPolicies are in place. Use `kubectl get networkpolicy` to list existing policies.
- **CNI Plugin:** Verify the Container Network Interface (CNI) plugin (like Calico, Flannel) is configured and running correctly.
- **Service Discovery:** Ensure DNS is working for service discovery with `kubectl exec -it <pod-name> -- nslookup <service-name>`.
- **Security Groups:** If using AWS or similar cloud, check if EC2 Security Groups are not blocking traffic.
- **Firewalls:** Examine host-level firewalls or network firewalls that might interfere with pod communication.

These troubleshooting scenarios showcase practical problem-solving in Kubernetes, which are valuable for interview discussions. Lovely troubleshooting, Jeevan!

**Update kubeconfig for EKS:**
```sh
aws eks update-kubeconfig --name my-cluster --region ap-south-1
```

The script sets up port forwarding for Prometheus to access it locally.
```sh
kubectl port-forward $(kubectl get pods -l app=prometheus -o jsonpath='{.items[0].metadata.name}') 9090:9090 > /dev/null 2>&1 &
```

### mysql-secret.yaml

```yaml
apiVersion: v1
kind: Secret
metadata:
    name: mysql-secret
type: Opaque
data:
    MYSQL_ROOT_PASSWORD: cGFzc3dvcmQ=  # base64 encoded value of "password"
```

### backend-service.yaml

```yaml
apiVersion: v1
kind: Service
metadata:
    name: backend-service
spec:
    selector:
        app: backend
    ports:
        - protocol: TCP
            port: 3000
            targetPort: 3000
    type: LoadBalancer
```

### backend-deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: backend
spec:
    replicas: 2
    selector:
        matchLabels:
            app: backend
    template:
        metadata:
            labels:
                app: backend
        spec:
            initContainers:
            - name: init-mysql
                image: mysql:8.0
                env:
                - name: MYSQL_ROOT_PASSWORD
                    valueFrom:
                        secretKeyRef:
                            name: mysql-secret
                            key: MYSQL_ROOT_PASSWORD
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_PORT
                    value: "${db_port}"
                volumeMounts:
                - name: init-sql
                    mountPath: /docker-entrypoint-initdb.d
                command: [ "sh", "-c", "mysql -h ${db_host} -P ${db_port} -u admin -p${MYSQL_ROOT_PASSWORD} < /docker-entrypoint-initdb.d/init.sql" ]
            containers:
            - name: backend
                image: jeevan2001/backend:latest
                env:
                - name: DB_HOST
                    value: "${db_host}"
                - name: DB_PORT
                    value: "${db_port}"
                - name: MYSQL_ROOT_PASSWORD
                    valueFrom:
                        secretKeyRef:
                            name: mysql-secret
                            key: MYSQL_ROOT_PASSWORD
                ports:
                - containerPort: 3000
            volumes:
            - name: init-sql
                configMap:
                    name: init-sql-config
```

### Get the Backend LoadBalancer DNS

```sh
export BACKEND_LOADBALANCER_DNS=$(kubectl get service backend-service -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')
```

### frontend-service.yaml

```yaml
apiVersion: v1
kind: Service
metadata:
    name: frontend-service
spec:
    selector:
        app: frontend
    ports:
        - protocol: TCP
            port: 80
            targetPort: 80
    type: LoadBalancer
```

### frontend-deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: frontend
spec:
    replicas: 2
    selector:
        matchLabels:
            app: frontend
    template:
        metadata:
            labels:
                app: frontend
        spec:
            containers:
            - name: frontend
                image: jeevan2001/frontend:latest
                ports:
                - containerPort: 80
                imagePullPolicy: Always
```

### hpa-backend.yaml

```yaml
apiVersion: autoscaling/v1
kind: HorizontalPodAutoscaler
metadata:
    name: hpa-backend
spec:
    scaleTargetRef:
        apiVersion: apps/v1
        kind: Deployment
        name: backend-deployment
    minReplicas: 1
    maxReplicas: 10
    targetCPUUtilizationPercentage: 50
```

### cluster-autoscaler.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: cluster-autoscaler
    namespace: kube-system
    labels:
        app: cluster-autoscaler
spec:
    replicas: 1
    selector:
        matchLabels:
            app: cluster-autoscaler
    template:
        metadata:
            labels:
                app: cluster-autoscaler
        spec:
            containers:
            - name: cluster-autoscaler
                image: k8s.gcr.io/autoscaling/cluster-autoscaler:v1.20.0
                command:
                - ./cluster-autoscaler
                - --v=4
                - --stderrthreshold=info
                - --cloud-provider=aws
                - --skip-nodes-with-local-storage=false
                - --expander=least-waste
                - --nodes=1:10:my-node-group
                env:
                - name: AWS_REGION
                    value: ap-south-1
                resources:
                    limits:
                        cpu: 100m
                        memory: 300Mi
                    requests:
                        cpu: 100m
                        memory: 300Mi
                volumeMounts:
                - name: ssl-certs
                    mountPath: /etc/ssl/certs/ca-certificates.crt
                    readOnly: true
            volumes:
            - name: ssl-certs
                hostPath:
                    path: /etc/ssl/certs/ca-certificates.crt
```

### cluster-autoscaler-policy.json

```json
{
        "Version": "2012-10-17",
        "Statement": [
                {
                        "Action": [
                                "autoscaling:DescribeAutoScalingGroups",
                                "autoscaling:DescribeAutoScalingInstances",
                                "autoscaling:DescribeLaunchConfigurations",
                                "autoscaling:DescribeTags",
                                "autoscaling:SetDesiredCapacity",
                                "autoscaling:TerminateInstanceInAutoScalingGroup",
                                "ec2:DescribeLaunchTemplateVersions"
                        ],
                        "Resource": "*",
                        "Effect": "Allow"
                }
        ]
}
```