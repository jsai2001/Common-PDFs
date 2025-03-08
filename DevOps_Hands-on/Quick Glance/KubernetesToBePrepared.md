# Priority 1: Must-Know Kubernetes Resources for Interviews
---
### Pod

The smallest and simplest
Kubernetes object. A Pod represents a
single instance of a running process
in your cluster.

#### Basic

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: simple-pod
  labels:
    app: my-app
spec:
  containers:
  - name: app-container
    image: nginx:latest
    ports:
    - containerPort: 80
    resources:
      requests:
        cpu: "100m"
        memory: "128Mi"
      limits:
        cpu: "500m"
        memory: "256Mi"
```

#### Advanced

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: devops-interview-pod
  namespace: production          # Shows understanding of namespace usage
  labels:
    app: web-service
    environment: prod
    tier: frontend
  annotations:                   # Useful for documentation and monitoring
    maintainer: "devops-team@example.com"
    version: "v1.2.3"
spec:
  # High availability consideration
  affinity:                      # Demonstrates pod placement knowledge
    podAntiAffinity:
      preferredDuringSchedulingIgnoredDuringExecution:
      - weight: 100
        podAffinityTerm:
          labelSelector:
            matchExpressions:
            - key: app
              operator: In
              values:
              - web-service
          topologyKey: "kubernetes.io/hostname"
  containers:
  - name: application-container
    image: custom-app:1.14.2     # Replace nginx with a custom app
    imagePullPolicy: IfNotPresent
    ports:
    - containerPort: 8080
      name: http
      protocol: TCP
    env:                         # Environment variable management
    - name: DATABASE_URL
      valueFrom:
        secretKeyRef:            # Shows secret management
          name: db-credentials
          key: url
    - name: LOG_LEVEL
      valueFrom:
        configMapKeyRef:        # Shows ConfigMap usage
          name: app-config
          key: log-level
    resources:
      requests:                 # Resource management knowledge
        cpu: "250m"
        memory: "256Mi"
      limits:
        cpu: "1"
        memory: "512Mi"
    readinessProbe:            # Health checking
      httpGet:
        path: /health
        port: 8080
      initialDelaySeconds: 10
      periodSeconds: 10
      failureThreshold: 3
    livenessProbe:
      httpGet:
        path: /healthz
        port: 8080
      initialDelaySeconds: 15
      periodSeconds: 30
      failureThreshold: 3
    volumeMounts:              # Persistent storage
    - name: app-logs
      mountPath: /var/log/app
  # Sidecar container for logging (common DevOps pattern)
  - name: log-collector
    image: fluentd:1.15
    resources:
      requests:
        cpu: "100m"
        memory: "128Mi"
      limits:
        cpu: "200m"
        memory: "256Mi"
    volumeMounts:
    - name: app-logs
      mountPath: /input-logs
  volumes:                     # Volume management
  - name: app-logs
    persistentVolumeClaim:
      claimName: app-logs-pvc
  # Security best practices
  securityContext:
    runAsUser: 1000
    runAsGroup: 3000
    fsGroup: 2000
  restartPolicy: Always
  nodeSelector:               # Infrastructure awareness
    environment: production
    disktype: ssd
  tolerations:                # Taint/toleration knowledge
  - key: "node-role.kubernetes.io/master"
    operator: "Exists"
    effect: "NoSchedule"
  imagePullSecrets:           # Private registry access
  - name: docker-registry-secret
  # Resource optimization
  priorityClassName: high-priority
```

---
### Deployment

Provides declarative
updates for Pods and ReplicaSets.

#### Basic

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
          resources:
            requests:
              memory: "256Mi"
              cpu: "200m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          livenessProbe:
            httpGet:
              path: /health
              port: 80
            initialDelaySeconds: 30
            periodSeconds: 10
          readinessProbe:
            httpGet:
              path: /ready
              port: 80
            initialDelaySeconds: 5
            periodSeconds: 5
          env:
            - name: ENVIRONMENT
              value: "production"
```

#### Advanced

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-deployment
  namespace: devops-interview  # Added namespace for environment separation
  labels:
    app: my-app
    environment: production
    version: v1
  annotations:
    description: "Core application deployment"
    maintainer: "devops-team@example.com"
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1           # Allows one extra pod during update
      maxUnavailable: 0     # Ensures no downtime
  revisionHistoryLimit: 5   # Keeps 5 old ReplicaSets for rollback
  selector:
    matchLabels:
      app: my-app
      tier: frontend
  template:
    metadata:
      labels:
        app: my-app
        tier: frontend
      annotations:
        prometheus.io/scrape: "true"  # For monitoring integration
        prometheus.io/port: "8080"
    spec:
      restartPolicy: Always
      containers:
        - name: my-container
          image: nginx:1.14.2
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 80
              name: http
              protocol: TCP
          resources:                    # Resource management
            requests:
              memory: "256Mi"
              cpu: "200m"              # 0.2 CPU cores
            limits:
              memory: "512Mi"
              cpu: "500m"              # 0.5 CPU cores
          livenessProbe:               # Health checking
            httpGet:
              path: /health
              port: 80
              scheme: HTTP
            initialDelaySeconds: 30
            periodSeconds: 10
            timeoutSeconds: 3
            failureThreshold: 3
          readinessProbe:
            httpGet:
              path: /ready
              port: 80
            initialDelaySeconds: 5
            periodSeconds: 5
          env:                        # Environment variables
            - name: ENVIRONMENT
              value: "production"
            - name: POD_NAME
              valueFrom:
                fieldRef:
                  fieldPath: metadata.name
          volumeMounts:              # Persistent storage
            - name: config-volume
              mountPath: "/etc/nginx/conf.d"
          securityContext:           # Security best practices
            runAsNonRoot: true
            readOnlyRootFilesystem: true
            allowPrivilegeEscalation: false
      volumes:
        - name: config-volume
          configMap:
            name: nginx-config
      affinity:                    # Pod scheduling
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
          - weight: 100
            podAffinityTerm:
              labelSelector:
                matchExpressions:
                - key: app
                  operator: In
                  values:
                  - my-app
              topologyKey: "kubernetes.io/hostname"
      tolerations:                # Handle tainted nodes
        - key: "node-role.kubernetes.io/master"
          operator: "Exists"
          effect: "NoSchedule"
      imagePullSecrets:          # Private registry access
        - name: docker-registry-secret
```

---
### Service

An abstraction which defines
a logical set of Pods and a policy by
which to access them - like loadbalancers.

#### Basic

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
spec:
  selector:
    app: my-app
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
      name: http
  type: LoadBalancer
```

#### Advanced

```yaml
apiVersion: v1
kind: Service
metadata:
  name: my-service
  namespace: devops-interview  # Added for environment separation
  labels:
    app: my-app
    environment: production
  annotations:
    service.beta.kubernetes.io/aws-loadbalancer-type: "nlb"  # AWS-specific LB type
    service.beta.kubernetes.io/aws-loadbalancer-healthcheck-path: "/health"  # Health check customization
    prometheus.io/scrape: "true"  # Monitoring integration
    prometheus.io/port: "80"
spec:
  selector:
    app: my-app
  ports:
    - name: http
      protocol: TCP
      port: 80          # Service port exposed externally
      targetPort: 8080  # Pod port (changed to show port mapping knowledge)
  type: LoadBalancer
  loadBalancerSourceRanges:  # Security: Restrict inbound traffic
    - "10.0.0.0/16"
    - "192.168.0.0/24"
  sessionAffinity: ClientIP   # Sticky sessions
  externalTrafficPolicy: Local  # Preserves client IP, affects health checks
```

---
### ConfigMap

Used to store
configuration data in key-value pairs
which can be consumed by pods.

#### Basic

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: my-config
data:
  app.env: "production"
  config.file: |
    key1=value1
    key2=value2
```

#### Advanced

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: my-config
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
data:
  # Key-value pairs for environment variables
  app.env: "production"
  log.level: "info"
  # Configuration file content
  nginx.conf: |
    server {
        listen 80;
        server_name localhost;
        location / {
            root /usr/share/nginx/html;
            index index.html;
        }
    }
binaryData:  # For binary files (e.g., certificates)
  secret.key: VGhpcyBpcyBhIHNhbXBsZSBiaW5hcnkgdmFsdWU=  # "This is a sample binary value" in base64
```

---
### Secret

Manages sensitive
information, like passwords, OAuth
tokens, and ssh keys, which can be
referenced in pod definitions.

#### Basic

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
type: Opaque
data:
  username: YWRtaW4=  # "admin"
  password: UEA1NXcwcmQ=  # "P@55w0rd"
```

#### Advanced

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: my-secret
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
type: Opaque  # Generic secret type
data:  # Base64-encoded values
  db-username: YWRtaW4=  # "admin"
  db-password: UEA1NXcwcmQ=  # "P@55w0rd"
stringData:  # Plain text (auto-encoded by Kubernetes)
  config.yaml: |
    apiUrl: "https://myapi.com"
    dbHost: "mysql.prod.svc.cluster.local"
    token: "xyz123-secret-token"
```

---
### PersistentVolume

A piece of
storage in the cluster that has been
provisioned by an administrator or
dynamically provisioned using Storage
Classes.

#### Basic

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

#### Advanced

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv0001
  labels:
    app: my-app
    environment: production
spec:
  capacity:
    storage: 5Gi
  accessModes:
    - ReadWriteOnce  # Single node read-write
    - ReadOnlyMany   # Multiple nodes read-only
  persistentVolumeReclaimPolicy: Retain  # Keeps data after PVC deletion
  storageClassName: standard  # Ties to dynamic provisioning
  mountOptions:  # NFS-specific options
    - nfsvers=4.1
    - nconnect=4
  nfs:
    server: nfs-server.example.com
    path: "/exports/data"
```
---
### PersistentVolumeClaim

Requests
storage resources defined by a
PersistentVolume.

#### Basic

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
```

#### Advanced

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-claim
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  accessModes:
    - ReadWriteOnce  # Single node read-write
  resources:
    requests:
      storage: 5Gi
  storageClassName: standard  # Ties to dynamic provisioning
  volumeMode: Filesystem  # Default, but explicit for clarity
  selector:  # Optional: Matches specific PVs
    matchLabels:
      app: my-app
  volumeName: pv0001  # Optional: Binds to a specific PV
```

---
### Namespace

Provides a scope for
names. Resources like Pods, Services,
and Deployments can be isolated within
namespaces.

#### Basic

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: my-namespace
  labels:
    environment: production
```

#### Advanced

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: my-namespace
  labels:
    app: my-app
    environment: production  # Changed to production for broader applicability
    team: devops
  annotations:
    purpose: "Application runtime environment"
    owner: "devops-team@example.com"
    scheduler.alpha.kubernetes.io/defaultTolerations: '[{"key":"dedicated","operator":"Equal","value":"app-team"}]'
```
---
### StatefulSet

Manages the deployment
and scaling of a set of Pods, and
provides guarantees about the ordering
and uniqueness of these Pods.

#### Basic

```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: my-statefulset
spec:
  serviceName: my-service
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
              mountPath: "/usr/share/nginx/html"
  volumeClaimTemplates:
    - metadata:
        name: www
      spec:
        accessModes:
          - ReadWriteOnce
        resources:
          requests:
            storage: 1Gi
```

#### Advanced

```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: my-statefulset
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  serviceName: my-service  # Headless service for stable DNS
  replicas: 3
  podManagementPolicy: OrderedReady  # Default, but explicit for clarity
  updateStrategy:
    type: RollingUpdate
    rollingUpdate:
      partition: 0  # Controls update rollout
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
              name: http
          volumeMounts:
            - name: www
              mountPath: "/usr/share/nginx/html"
          resources:
            requests:
              cpu: "200m"
              memory: "256Mi"
            limits:
              cpu: "500m"
              memory: "512Mi"
          livenessProbe:
            httpGet:
              path: "/health"
              port: 80
            initialDelaySeconds: 30
            periodSeconds: 10
  volumeClaimTemplates:
    - metadata:
        name: www
      spec:
        accessModes:
          - ReadWriteOnce
        storageClassName: standard
        resources:
          requests:
            storage: 1Gi
```
---
### Ingress

Manages external access to
the services in a cluster, typically
HTTP.

#### Basic

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: my-ingress
  annotations:
    kubernetes.io/ingress.class: "nginx"
spec:
  rules:
    - host: example.com
      http:
        paths:
          - path: /app
            pathType: Prefix
            backend:
              service:
                name: my-service
                port:
                  number: 80
```

#### Advanced

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: my-ingress
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
  annotations:
    kubernetes.io/ingress.class: "nginx"  # Specifies ingress controller
    nginx.ingress.kubernetes.io/rewrite-target: /$2  # Rewrites captured path
    nginx.ingress.kubernetes.io/ssl-redirect: "true"  # Enforces HTTPS
    cert-manager.io/cluster-issuer: "letsencrypt-prod"  # Auto TLS certs
spec:
  tls:  # TLS configuration
    - hosts:
        - example.com
      secretName: my-tls-secret
  rules:
    - host: example.com
      http:
        paths:
          - path: /app/(.*)  # Captures app-specific paths
            pathType: Prefix
            backend:
              service:
                name: my-service
                port:
                  number: 80
          - path: /api/(.*)  # Multiple paths for different services
            pathType: Prefix
            backend:
              service:
                name: api-service
                port:
                  number: 8080
```

---
### HorizontalPodAutoscaler

Scales a
Deployment, ReplicaSet, or
ReplicationController based on
observed CPU utilization or other
select metrics.

#### Basic

```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: my-hpa
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-deployment
  minReplicas: 2
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
```

#### Advanced

```yaml
apiVersion: autoscaling/v2  # Updated to stable v2 (preferred over v2beta2)
kind: HorizontalPodAutoscaler
metadata:
  name: my-hpa
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: my-deployment
  minReplicas: 2  # Avoid single point of failure
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70  # More realistic threshold
    - type: Resource
      resource:
        name: memory
        target:
          type: Utilization
          averageUtilization: 80
  behavior:  # Custom scaling behavior
    scaleUp:
      stabilizationWindowSeconds: 30  # Faster scaling
      policies:
        - type: Pods
          value: 2
          periodSeconds: 60
    scaleDown:
      stabilizationWindowSeconds: 300  # Slower scale-down
```

---
# Priority 2: Nice-to-Know Resources (Learn if You Have Time)
---
### Replicaset

Ensures a specified
number of pod replicas are running at
any given time.

#### Basic

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

#### Advanced

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: my-replicaset
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
    tier: frontend
spec:
  replicas: 3
  selector:
    matchLabels:
      app: my-app
      tier: frontend  # Consistent with pod labels
  template:
    metadata:
      labels:
        app: my-app
        tier: frontend
    spec:
      containers:
        - name: my-container
          image: nginx:1.14.2
          ports:
            - containerPort: 80
              name: http
          resources:
            requests:
              cpu: "200m"
              memory: "256Mi"
            limits:
              cpu: "500m"
              memory: "512Mi"
          livenessProbe:
            httpGet:
              path: "/health"
              port: 80
            initialDelaySeconds: 30
            periodSeconds: 10
```
---
### DaemonSet

Ensures that all (or some)
Nodes run a copy of a Pod. As nodes
are added to the cluster, Pods are
added to them. As nodes are removed
from the cluster, those Pods are
garbage collected.

#### Basic

```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: my-daemonset
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
        kubernetes.io/role: worker
      tolerations:
        - key: "node-role.kubernetes.io/control-plane"
          effect: "NoSchedule"
      containers:
        - name: my-container
          image: nginx:1.14.2
```

#### Advanced

```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: my-daemonset
  namespace: kube-system  # Common for system-level DaemonSets
  labels:
    app: my-app
    purpose: monitoring
spec:
  selector:
    matchLabels:
      app: my-app
  updateStrategy:
    type: RollingUpdate  # Default, but explicit for clarity
    rollingUpdate:
      maxUnavailable: 1  # Ensures minimal disruption
  template:
    metadata:
      labels:
        app: my-app
    spec:
      nodeSelector:  # Target specific nodes
        kubernetes.io/role: worker  # Changed to worker nodes for broader use
      tolerations:  # Allow running on tainted nodes
        - key: "node-role.kubernetes.io/control-plane"  # Updated to modern control-plane taint
          operator: "Exists"
          effect: "NoSchedule"
      containers:
        - name: my-container
          image: nginx:1.14.2
          ports:
            - containerPort: 80
              name: http
          resources:
            requests:
              cpu: "100m"
              memory: "128Mi"
            limits:
              cpu: "200m"
              memory: "256Mi"
          livenessProbe:
            httpGet:
              path: "/health"
              port: 80
            initialDelaySeconds: 30
            periodSeconds: 10
```
---
### Job

Creates one or more Pods and
ensures that a specified number of
them successfully terminate. Good for
batch processes.

#### Basic

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: my-job
spec:
  completions: 5
  parallelism: 2
  backoffLimit: 4
  template:
    spec:
      containers:
        - name: my-job-container
          image: busybox
          command: ["/bin/sh", "-c", "echo Hello, Kubernetes!"]
      restartPolicy: OnFailure
```

#### Advanced

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: my-job
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    purpose: batch-processing
spec:
  completions: 5  # Total successful tasks
  parallelism: 2  # Concurrent pods
  backoffLimit: 4  # Retry attempts before failure
  activeDeadlineSeconds: 600  # Timeout for the entire job
  ttlSecondsAfterFinished: 3600  # Cleanup after 1 hour
  template:
    spec:
      containers:
        - name: my-job-container
          image: busybox
          command: ["/bin/sh", "-c", "echo Hello, Kubernetes! && sleep 30"]
          resources:
            requests:
              cpu: "100m"
              memory: "128Mi"
            limits:
              cpu: "200m"
              memory: "256Mi"
      restartPolicy: OnFailure  # Retry failed containers
```
---
### CronJob

Manages time-based Jobs,
similar to cron in Unix-like systems.

#### Basic

```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
  name: my-cronjob
spec:
  schedule: "0 */1 * * *"  # Every hour
  concurrencyPolicy: Forbid
  jobTemplate:
    spec:
      template:
        spec:
          containers:
            - name: my-cronjob-container
              image: busybox
              command: ["/bin/sh", "-c", "echo Hello"]
          restartPolicy: OnFailure
```

#### Advanced

```yaml
apiVersion: batch/v1  # Updated to stable version
kind: CronJob
metadata:
  name: my-cronjob
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    purpose: scheduled-task
spec:
  schedule: "0 */1 * * *"  # Every hour (more realistic than every minute)
  concurrencyPolicy: Forbid  # Prevents overlapping runs
  failedJobsHistoryLimit: 2  # Retains some failure history
  successfulJobsHistoryLimit: 3  # Retains recent successes
  startingDeadlineSeconds: 300  # Fails if not started within 5 minutes
  jobTemplate:
    spec:
      backoffLimit: 4  # Retry attempts for the Job
      ttlSecondsAfterFinished: 3600  # Cleanup after 1 hour
      template:
        spec:
          containers:
            - name: my-cronjob-container
              image: busybox
              command: ["/bin/sh", "-c", "date; echo Hello from Kubernetes"]
              resources:
                requests:
                  cpu: "100m"
                  memory: "128Mi"
                limits:
                  cpu: "200m"
                  memory: "256Mi"
          restartPolicy: OnFailure  # Retries failed containers
```
---
### NetworkPolicy

Specifies how groups
of pods are allowed to communicate
with each other and other network
endpoints.

#### Basic

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
      ports:
        - protocol: TCP
          port: 3306
```

#### Advanced

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: my-network-policy
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  podSelector:
    matchLabels:
      role: db  # Targets database pods
  policyTypes:
    - Ingress
    - Egress
  ingress:
    - from:
        - podSelector:  # Allow frontend pods
            matchLabels:
              role: frontend
        - namespaceSelector:  # Allow from monitoring namespace
            matchLabels:
              purpose: monitoring
      ports:
        - protocol: TCP
          port: 6379  # Redis port (common DB example)
  egress:
    - to:
        - ipBlock:  # Allow external DB backup service
            cidr: 10.0.0.0/24
            except:
              - 10.0.0.0/28  # Exclude a specific range
        - podSelector:  # Allow internal service
            matchLabels:
              role: backup
      ports:
        - protocol: TCP
          port: 3306  # MySQL port (common egress example)
```
---
### ServiceAccount

Provides an identity
for processes that run in a Pod, which
can be used for authenticating to the
API server.

#### Basic

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: my-service-account
  namespace: devops-interview
imagePullSecrets:
  - name: regcred
```

#### Advanced

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: my-service-account
  namespace: devops-interview  # Updated for consistency
  labels:
    app: my-app
    environment: production
  annotations:
    description: "Service account for application pods"
imagePullSecrets:
  - name: regcred  # For private registry access
```
---
### ResourceQuota

Provides constraints
that limit aggregate resource
consumption per namespace.

#### Basic

```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: my-quota
  namespace: devops-interview
spec:
  hard:
    pods: "10"
    requests.cpu: "4"
    requests.memory: "6Gi"
    limits.cpu: "10"
    limits.memory: "10Gi"
```

#### Advanced

```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: my-quota
  namespace: devops-interview  # Scoped to a specific namespace
  labels:
    environment: production
    purpose: resource-control
  annotations:
    description: "Limits resources for production workloads"
spec:
  hard:
    pods: "10"               # Max running pods
    requests.cpu: "4"        # Total CPU requests
    requests.memory: "6Gi"   # Total memory requests
    limits.cpu: "10"         # Total CPU limits
    limits.memory: "10Gi"    # Total memory limits
    configmaps: "10"         # Max ConfigMaps
    secrets: "10"            # Max Secrets
    services: "5"            # Max Services
    services.loadbalancers: "2"  # Max LoadBalancer Services
```
---
### LimitRange

Constrains resource
allocations (to Pods or Containers) in
a namespace.

#### Basic

```yaml
apiVersion: v1
kind: LimitRange
metadata:
  name: my-limitrange
  namespace: devops-interview
spec:
  limits:
    - type: Container
      max:
        cpu: "1"
        memory: "512Mi"
      min:
        cpu: "100m"
        memory: "64Mi"
      default:
        cpu: "500m"
        memory: "512Mi"
      defaultRequest:
        cpu: "200m"
        memory: "256Mi"
```

#### Advanced

```yaml
apiVersion: v1
kind: LimitRange
metadata:
  name: my-limitrange
  namespace: devops-interview  # Scoped to a specific namespace
  labels:
    environment: production
    purpose: resource-limits
spec:
  limits:
    - type: Pod
      max:  # Upper bounds per pod
        cpu: "2"
        memory: "1Gi"
      min:  # Lower bounds per pod
        cpu: "200m"
        memory: "6Mi"
    - type: Container
      max:  # Upper bounds per container
        cpu: "1"
        memory: "512Mi"
      min:  # Lower bounds per container
        cpu: "100m"
        memory: "64Mi"
      default:  # Applied if limits omitted
        cpu: "500m"
        memory: "512Mi"
      defaultRequest:  # Applied if requests omitted
        cpu: "200m"
        memory: "256Mi"
```
---
# Priority 3: Skip for Now (Unless Specialized)
---

### VerticalPodAutoscaler

Automatically
adjusts the compute resources of pods
based on usage.

#### Basic

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
      - containerName: "*"
        minAllowed:
          cpu: "250m"
          memory: "128Mi"
        maxAllowed:
          cpu: "2"
          memory: "4Gi"
```

#### Advanced

```yaml
apiVersion: autoscaling.k8s.io/v1
kind: VerticalPodAutoscaler
metadata:
  name: my-vpa
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  targetRef:
    apiVersion: "apps/v1"
    kind: Deployment
    name: my-deployment
  updatePolicy:
    updateMode: "Auto"  # Automatically adjusts resources
  resourcePolicy:
    containerPolicies:
      - containerName: "*"  # Applies to all containers
        minAllowed:
          cpu: "250m"
          memory: "128Mi"  # Slightly higher for realism
        maxAllowed:
          cpu: "2"
          memory: "4Gi"
        controlledResources:  # Explicitly specify
          - cpu
          - memory
```
---

### PodDisruptionBudget

Ensures that a
specified number of pods are available
even during voluntary disruptions like
node drains or upgrades.

#### Basic

```yaml
apiVersion: policy/v1
kind: PodDisruptionBudget
metadata:
  name: my-pdb
spec:
  minAvailable: 2
  selector:
    matchLabels:
      app: my-app
```

#### Advanced

```yaml
apiVersion: policy/v1  # Updated to stable version
kind: PodDisruptionBudget
metadata:
  name: my-pdb
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  minAvailable: 2  # Ensures at least 2 pods remain available
  # Alternative: maxUnavailable: 1  # Uncomment to show flexibility
  selector:
    matchLabels:
      app: my-app
      tier: backend  # More specific selector
```
---

### CustomResourceDefinition

Allows users to create new types of
resources without adding another API
server.

#### Basic

```yaml
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: crontabs.stable.example.com
spec:
  group: stable.example.com
  scope: Namespaced
  names:
    plural: crontabs
    singular: crontab
    kind: CronTab
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
```

#### Advanced

```yaml
apiVersion: apiextensions.k8s.io/v1
kind: CustomResourceDefinition
metadata:
  name: crontabs.stable.example.com
  labels:
    purpose: scheduling
    environment: production
spec:
  group: stable.example.com  # Custom API group
  scope: Namespaced  # Scoped to namespaces
  names:
    plural: crontabs
    singular: crontab
    kind: CronTab
    shortNames:
      - ct
  versions:
    - name: v1
      served: true  # Available via API
      storage: true  # Persists this version
      schema:
        openAPIV3Schema:
          type: object
          properties:
            spec:
              type: object
              required: ["cronSpec", "image"]  # Enforce mandatory fields
              properties:
                cronSpec:
                  type: string
                  pattern: '^(\d+|\*)\s+(\d+|\*)\s+(\d+|\*)\s+(\d+|\*)\s+(\d+|\*)$'  # Basic cron validation
                image:
                  type: string
                replicas:
                  type: integer
                  minimum: 1
                  maximum: 10
            status:  # Status subresource
              type: object
              properties:
                lastRun:
                  type: string
      subresources:
        status: {}  # Enables status updates
```
---

### StorageClass

Describes different
classes or profiles of storage in the
cluster.

#### Basic

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: standard
provisioner: kubernetes.io/aws-ebs
parameters:
  type: gp2
reclaimPolicy: Retain
allowVolumeExpansion: true
volumeBindingMode: WaitForFirstConsumer
```

#### Advanced

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: standard
  labels:
    environment: production
    purpose: general-storage
  annotations:
    storageclass.kubernetes.io/is-default-class: "true"  # Marks as default
provisioner: kubernetes.io/aws-ebs  # AWS EBS provisioner
parameters:
  type: gp2  # General-purpose SSD
  fsType: ext4  # Filesystem type
  encrypted: "true"  # Security feature
  zones: us-west-2a, us-west-2b  # Multi-zone support
reclaimPolicy: Retain  # Keeps PVs after PVC deletion
allowVolumeExpansion: true  # Supports resizing
volumeBindingMode: WaitForFirstConsumer  # Delays binding until pod scheduling
```
---

### Endpoints

Exposes the IP addresses of
a service's backing pods.

#### Basic

```yaml
apiVersion: v1
kind: Endpoints
metadata:
  name: my-endpoints
spec:
  subsets:
    - addresses:
        - ip: 192.168.1.1
      ports:
        - port: 80
          name: http
```

#### Advanced

```yaml
apiVersion: v1
kind: Endpoints
metadata:
  name: my-endpoints
  namespace: devops-interview  # Environment separation
  labels:
    app: my-app
    environment: production
spec:
  subsets:
    - addresses:
        - ip: 192.168.1.1  # External IP 1
          nodeName: worker1  # Optional: ties to a node
        - ip: 192.168.1.2  # External IP 2
      ports:
        - port: 80
          name: http
          protocol: TCP  # Explicit protocol
        - port: 443
          name: https
          protocol: TCP
```
---

### Roles

Define
permissions for users or service
accounts within a namespace.

#### Basic

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: pod-reader
  namespace: devops-interview
rules:
  - apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list", "watch"]
```

#### Advanced

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: pod-reader
  namespace: devops-interview  # Updated for consistency
  labels:
    app: my-app
    environment: production
  annotations:
    description: "Grants read-only access to pods"
rules:
  - apiGroups: [""]  # Core API group
    resources: ["pods"]
    verbs: ["get", "list", "watch"]  # Read-only actions
  - apiGroups: [""]  # Additional rule for logs
    resources: ["pods/log"]
    verbs: ["get"]
```
---

### RoleBindings

Define
permissions for users or service
accounts within a namespace.

#### Basic

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: read-pods
  namespace: devops-interview
subjects:
  - kind: User
    name: my-user
    apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role
  name: pod-reader
  apiGroup: rbac.authorization.k8s.io
```

#### Advanced

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: read-pods
  namespace: devops-interview  # Updated for consistency
  labels:
    app: my-app
    environment: production
  annotations:
    description: "Binds pod read access to users and service accounts"
subjects:
  - kind: User
    name: my-user  # Human user
    apiGroup: rbac.authorization.k8s.io
  - kind: ServiceAccount
    name: my-service-account  # Pod identity
    namespace: devops-interview
roleRef:
  kind: Role
  name: pod-reader  # References a Role
  apiGroup: rbac.authorization.k8s.io
```
---

### ClusterRoles

Similar to Roles but cluster-wide, not
namespace-specific.

#### Basic

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: secret-reader
rules:
  - apiGroups: [""]
    resources: ["secrets"]
    verbs: ["get", "list", "watch"]
```

#### Advanced

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: secret-reader
  labels:
    app: my-app
    environment: production
  annotations:
    description: "Grants read-only access to secrets cluster-wide"
rules:
  - apiGroups: [""]  # Core API group
    resources: ["secrets"]
    verbs: ["get", "list", "watch"]  # Read-only actions
  - apiGroups: [""]  # Additional rule for configmaps
    resources: ["configmaps"]
    verbs: ["get", "list"]
```
---

### ClusterRoleBindings

Similar to Roles but cluster-wide, not
namespace-specific

#### Basic

```yaml
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

#### Advanced

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: read-secrets-global
  labels:
    app: my-app
    environment: production
  annotations:
    description: "Grants cluster-wide secret read access to users and service accounts"
subjects:
  - kind: User
    name: my-user  # Human user
    apiGroup: rbac.authorization.k8s.io
  - kind: ServiceAccount
    name: my-service-account  # Pod identity
    namespace: devops-interview
roleRef:
  kind: ClusterRole
  name: secret-reader  # References a ClusterRole
  apiGroup: rbac.authorization.k8s.io
```
---