# Kubernetes Code-Related Interview Questions

## 1. Create a Pod using kubectl
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: nginx
        image: nginx
```

## 2. Create a Deployment with 3 replicas using kubectl
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: my-deployment
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
                image: nginx
```

## 3. Create a Service to expose a Deployment using kubectl
```yaml
apiVersion: v1
kind: Service
metadata:
    name: my-service
spec:
    selector:
        app: nginx
    ports:
    - protocol: TCP
        port: 80
        targetPort: 80
    type: ClusterIP
```

## 4. Create a ConfigMap using kubectl
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: my-configmap
data:
    key1: value1
    key2: value2
```

## 5. Create a Secret using kubectl
```yaml
apiVersion: v1
kind: Secret
metadata:
    name: my-secret
type: Opaque
data:
    username: YWRtaW4=  # base64 for "admin"
    password: cGFzc3dvcmQ=  # base64 for "password"
```

## 6. Create a PersistentVolume (PV) using kubectl
```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
    name: my-pv
spec:
    capacity:
        storage: 1Gi
    accessModes:
    - ReadWriteOnce
    hostPath:
        path: /mnt/data
```

## 7. Create a PersistentVolumeClaim (PVC) and attach it to a Pod using kubectl
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
            storage: 1Gi
---
apiVersion: v1
kind: Pod
metadata:
    name: my-pod-with-pvc
spec:
    containers:
    - name: nginx
        image: nginx
        volumeMounts:
        - mountPath: /data
            name: my-volume
    volumes:
    - name: my-volume
        persistentVolumeClaim:
            claimName: my-pvc
```

## 8. Create a Namespace using kubectl
```bash
kubectl create namespace my-namespace
```

## 9. Create a ReplicaSet with 2 replicas using kubectl
```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
    name: my-replicaset
spec:
    replicas: 2
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
                image: nginx
```

## 10. Create an Ingress resource using kubectl
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
    name: my-ingress
spec:
    rules:
    - host: example.com
        http:
            paths:
            - path: /
                pathType: Prefix
                backend:
                    service:
                        name: my-service
                        port:
                            number: 80
```

## 11. Create a Job using kubectl
```yaml
apiVersion: batch/v1
kind: Job
metadata:
    name: my-job
spec:
    template:
        spec:
            containers:
            - name: pi
                image: perl
                command: ["perl", "-Mbignum=bpi", "-wle", "print bpi(2000)"]
            restartPolicy: Never
```

## 12. Create a CronJob using kubectl
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
    name: my-cronjob
spec:
    schedule: "*/1 * * * *"
    jobTemplate:
        spec:
            template:
                spec:
                    containers:
                    - name: hello
                        image: busybox
                        args:
                        - /bin/sh
                        - -c
                        - echo Hello from CronJob
                    restartPolicy: OnFailure
```

## 13. Create a StatefulSet with 2 replicas using kubectl
```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
    name: my-statefulset
spec:
    serviceName: "nginx"
    replicas: 2
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
                image: nginx
```

## 14. Create a DaemonSet using kubectl
```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
    name: my-daemonset
spec:
    selector:
        matchLabels:
            app: fluentd
    template:
        metadata:
            labels:
                app: fluentd
        spec:
            containers:
            - name: fluentd
                image: fluentd
```

## 15. Attach a ConfigMap to a Pod using kubectl
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod-with-configmap
spec:
    containers:
    - name: nginx
        image: nginx
        env:
        - name: KEY1
            valueFrom:
                configMapKeyRef:
                    name: my-configmap
                    key: key1
```

## 16. Attach a Secret to a Pod using kubectl
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod-with-secret
spec:
    containers:
    - name: nginx
        image: nginx
        env:
        - name: USERNAME
            valueFrom:
                secretKeyRef:
                    name: my-secret
                    key: username
```

## 17. Attach a Service to a Pod using kubectl
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
        targetPort: 80
---
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
    labels:
        app: my-app
spec:
    containers:
    - name: nginx
        image: nginx
```

## 18. Create a HorizontalPodAutoscaler (HPA) for a Deployment using kubectl
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
    minReplicas: 1
    maxReplicas: 5
    metrics:
    - type: Resource
        resource:
            name: cpu
            target:
                type: Utilization
                averageUtilization: 50
```

## 19. Create a NetworkPolicy to restrict Pod traffic using kubectl
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: my-network-policy
spec:
    podSelector:
        matchLabels:
            app: nginx
    policyTypes:
    - Ingress
    ingress:
    - from:
        - podSelector:
                matchLabels:
                    app: frontend
        ports:
        - protocol: TCP
            port: 80
```

## 20. Create a Role and attach it to a ServiceAccount using kubectl
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
    name: my-role
    namespace: default
rules:
- apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list"]
---
apiVersion: v1
kind: ServiceAccount
metadata:
    name: my-serviceaccount
    namespace: default
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    name: my-role-binding
    namespace: default
subjects:
- kind: ServiceAccount
    name: my-serviceaccount
    namespace: default
roleRef:
    kind: Role
    name: my-role
    apiGroup: rbac.authorization.k8s.io
```

## 21. Create a ClusterRole and attach it to a ServiceAccount using kubectl
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
    name: my-clusterrole
rules:
- apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list"]
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
    name: my-clusterrole-binding
subjects:
- kind: ServiceAccount
    name: my-serviceaccount
    namespace: default
roleRef:
    kind: ClusterRole
    name: my-clusterrole
    apiGroup: rbac.authorization.k8s.io
```

## 22. Create a ResourceQuota in a Namespace using kubectl
```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
    name: my-resourcequota
    namespace: my-namespace
spec:
    hard:
        pods: "10"
        requests.cpu: "2"
        requests.memory: 4Gi
```

## 23. Create a LimitRange in a Namespace using kubectl
```yaml
apiVersion: v1
kind: LimitRange
metadata:
    name: my-limitrange
    namespace: my-namespace
spec:
    limits:
    - type: Container
        default:
            cpu: "500m"
            memory: "512Mi"
        defaultRequest:
            cpu: "200m"
            memory: "256Mi"
```

## 24. Attach a PersistentVolumeClaim (PVC) to a Deployment using kubectl
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: my-deployment-with-pvc
spec:
    replicas: 1
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
                image: nginx
                volumeMounts:
                - mountPath: /data
                    name: my-volume
            volumes:
            - name: my-volume
                persistentVolumeClaim:
                    claimName: my-pvc
```

## 25. Create a ServiceAccount and attach it to a Pod using kubectl
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
    name: my-serviceaccount
---
apiVersion: v1
kind: Pod
metadata:
    name: my-pod-with-sa
spec:
    serviceAccountName: my-serviceaccount
    containers:
    - name: nginx
        image: nginx
```