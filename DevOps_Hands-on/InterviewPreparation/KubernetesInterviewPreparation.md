# Kubernetes Interview Preparation

## Kubernetes Basics
- What is Kubernetes, and why is it used?
- What problems does Kubernetes solve in container orchestration?
- Explain the difference between Docker and Kubernetes.
- What is a container, and how does Kubernetes manage containers?
- What are the key components of Kubernetes architecture?
- What is a Pod in Kubernetes?
- How does Kubernetes differ from traditional virtualization?
- What is the role of a Kubernetes cluster?
- What is the difference between a node and a cluster in Kubernetes?
- What is a control plane in Kubernetes, and what does it consist of?

## Kubernetes Architecture
- Explain the role of the kube-apiserver.
- What does the kube-scheduler do?
- What is the purpose of the kube-controller-manager?
- What is etcd, and why is it important in Kubernetes?
- How does the kubelet work on a worker node?
- What is the role of kube-proxy in Kubernetes?
- Explain the difference between master nodes and worker nodes.
- What is the Cloud Controller Manager, and when is it used?
- How does Kubernetes ensure high availability of the control plane?
- What is a Namespace in Kubernetes, and how is it used?

## Pods and Workloads
- How do you create a Pod in Kubernetes?
- What is the difference between a Pod and a Deployment?
- What is a ReplicaSet, and how does it relate to a Deployment?
- Explain the lifecycle of a Pod.
- What are labels and selectors in Kubernetes, and how are they used?
- What is a multi-container Pod, and when would you use one?
- How do you restart a Pod without deleting it?
- What is a StatefulSet, and how does it differ from a Deployment?
- What is a DaemonSet, and in what scenarios is it useful?
- What is a Job and a CronJob in Kubernetes?

## Networking
- How does networking work in Kubernetes?
- What is a ClusterIP service, and how does it work?
- Explain the difference between ClusterIP, NodePort, and LoadBalancer services.
- What is an Ingress controller, and why is it used?
- How does Kubernetes handle DNS resolution within a cluster?
- What is a CNI (Container Network Interface), and why is it important?
- How do Pods communicate with each other in Kubernetes?
- What is Network Policy, and how can you enforce it?
- How does Kubernetes handle external traffic to services?
- What is the purpose of kube-proxy in networking?

## Storage
- What is a Persistent Volume (PV) in Kubernetes?
- What is a Persistent Volume Claim (PVC), and how does it relate to a PV?
- Explain the difference between static and dynamic provisioning of storage.
- What is a StorageClass, and how does it work?
- How do you attach storage to a Pod?
- What are the access modes for Persistent Volumes (e.g., RWO, RWX)?
- How does Kubernetes handle storage in a multi-node cluster?
- What is the role of the CSI (Container Storage Interface) in Kubernetes?
- How do you troubleshoot storage issues in Kubernetes?
- What happens to a PV when a PVC is deleted?

## Configuration and Secrets
- What is a ConfigMap, and how is it used?
- How do you inject environment variables into a Pod?
- What is a Secret in Kubernetes, and how does it differ from a ConfigMap?
- How do you create and manage Secrets in Kubernetes?
- How can you mount a ConfigMap or Secret as a volume in a Pod?
- What are the security best practices for managing Secrets in Kubernetes?
- How do you update a ConfigMap or Secret without restarting a Pod?
- What is the difference between a base64-encoded Secret and a plain-text ConfigMap?
- How do you pass sensitive data to an application in Kubernetes?
- What is the role of RBAC in securing ConfigMaps and Secrets?

## Scheduling and Resource Management
- How does the Kubernetes scheduler decide where to place a Pod?
- What are resource requests and limits in Kubernetes?
- What happens when a Pod exceeds its resource limits?
- What is a taint and toleration, and how are they used?
- What is a node selector, and how does it work?
- How do you prioritize Pods in Kubernetes?
- What is a PriorityClass, and how do you configure it?
- How does Kubernetes handle Pod eviction?
- What is a Quality of Service (QoS) class, and how is it assigned to Pods?
- How do you troubleshoot a Pod that is stuck in a Pending state?

## Scaling and High Availability
- What is Horizontal Pod Autoscaling (HPA), and how does it work?
- How do you configure HPA based on custom metrics?
- What is Vertical Pod Autoscaling (VPA), and when is it useful?
- How does Kubernetes ensure high availability of applications?
- What is a rolling update, and how do you perform one in Kubernetes?
- What is the difference between a rolling update and a recreate strategy?
- How does Kubernetes handle failover for Pods?
- What is the role of liveness and readiness probes?
- How do you configure liveness and readiness probes for a Pod?
- What happens if a readiness probe fails?

## Security
- What is Role-Based Access Control (RBAC) in Kubernetes?
- How do you create a Role and a RoleBinding?
- What is the difference between a ClusterRole and a Role?
- How do you secure the Kubernetes API server?
- What is a ServiceAccount, and how is it used?
- How does Kubernetes handle authentication and authorization?
- What are Pod Security Policies (PSPs), and how do they work?
- How do you restrict network traffic using Network Policies?
- What are some best practices for securing a Kubernetes cluster?
- How do you audit and monitor security events in Kubernetes?

## Monitoring and Troubleshooting
- How do you check the status of Pods in a cluster?
- What is the purpose of the kubectl describe command?
- How do you view logs of a Pod using kubectl?
- What does the kubectl get events command do?
- How do you debug a Pod that is crashing?
- What are some common reasons for a Pod to be in a CrashLoopBackOff state?
- How do you troubleshoot a service that is not responding?
- What is Prometheus, and how is it used with Kubernetes?
- How do you monitor resource usage in a Kubernetes cluster?
- What is the role of the Kubernetes Dashboard, and how do you access it?

## CI/CD and GitOps
- How do you integrate Kubernetes with a CI/CD pipeline?
- What is Helm, and how does it simplify Kubernetes deployments?
- What are Helm charts, and how do you create one?
- What is GitOps, and how does it work with Kubernetes?
- How do you use tools like ArgoCD or Flux with Kubernetes?
- How do you roll back a deployment in Kubernetes?
- What is the difference between kubectl apply and kubectl create?
- How do you manage multiple environments (e.g., dev, staging, prod) in Kubernetes?
- What is a Kubernetes operator, and how does it work?
- How do you automate deployments in Kubernetes?

## Advanced Topics
- What is the Custom Resource Definition (CRD) in Kubernetes?
- How do you extend Kubernetes with custom controllers?
- What is a sidecar container, and how is it used?
- How does Kubernetes handle multi-tenancy?
- What is Federation in Kubernetes, and when is it used?
- How do you upgrade a Kubernetes cluster without downtime?
- What is the difference between Kubernetes and OpenShift?
- How do you configure a Kubernetes cluster on a cloud provider (e.g., AWS, GCP, Azure)?
- What is KubeVirt, and how does it integrate VMs with Kubernetes?
- How does Kubernetes handle disaster recovery?

## Scenario-Based Questions
- A Pod is stuck in a Pending state. How do you troubleshoot it?
- A service is not accessible externally. What steps would you take to debug it?
- You notice high CPU usage in your cluster. How do you identify the cause?
- How would you handle a situation where a node in the cluster fails?
- A deployment fails after an update. How do you roll it back?
- How do you migrate an application from a single server to Kubernetes?
- A Secret is accidentally exposed. What steps do you take to mitigate it?
- How do you scale an application to handle sudden traffic spikes?
- A StatefulSet is not creating Pods as expected. What could be the issue?
- How do you recover a cluster if the etcd database is corrupted?

## Practical/Hands-On Questions
- Write a YAML file to create a Deployment with 3 replicas.
- How do you expose a Deployment as a LoadBalancer service?
- Write a Network Policy to allow traffic only from specific Pods.
- Create a CronJob that runs every 5 minutes.
- How do you set resource limits and requests for a Pod?
- Write a YAML file for a Persistent Volume and Persistent Volume Claim.
- How do you taint a node and apply a toleration to a Pod?
- Create a ConfigMap and inject it into a Pod as an environment variable.
- How do you configure an Ingress resource with TLS?
- Write a Helm chart for a simple web application.

1. **What is Kubernetes, and why is it used?**

    Kubernetes (often abbreviated as K8s) is an open-source platform designed to automate the deployment, scaling, and management of containerized applications. It was originally developed by Google and donated to the Cloud Native Computing Foundation (CNCF). Kubernetes provides a framework for running distributed systems resiliently, handling tasks like load balancing, scaling, self-healing, and resource allocation.

    **Why it’s used:**
    - **Automation:** Simplifies deploying and managing containers at scale.
    - **Scalability:** Automatically scales applications based on demand.
    - **Portability:** Works across on-premises, public cloud, and hybrid environments.
    - **Resilience:** Ensures applications recover from failures through self-healing mechanisms.
    - **Resource Efficiency:** Optimizes resource usage by scheduling containers efficiently.

2. **What problems does Kubernetes solve in container orchestration?**

    Container orchestration involves managing the lifecycle of containers in a distributed environment. Kubernetes addresses the following challenges:
    - **Deployment:** Automates the rollout of containerized applications across multiple nodes.
    - **Scaling:** Dynamically scales containers up or down based on load or predefined rules.
    - **Load Balancing:** Distributes traffic across containers to ensure no single instance is overwhelmed.
    - **Self-Healing:** Automatically restarts, replaces, or reschedules failed containers or nodes.
    - **Resource Management:** Allocates CPU, memory, and storage efficiently across containers.
    - **Service Discovery:** Provides built-in mechanisms for containers to find and communicate with each other.
    - **Configuration Management:** Manages application configuration and secrets without rebuilding images.
    - **Multi-Host Coordination:** Manages containers across multiple hosts, ensuring high availability and fault tolerance.

3. **Explain the difference between Docker and Kubernetes.**

    - **Docker:**
      - A containerization platform that packages applications and their dependencies into lightweight, portable containers.
      - Focuses on creating, running, and managing individual containers on a single host.
      - Operates at a lower level, dealing with container runtime and image management.
      - Example command: `docker run` to start a container.

    - **Kubernetes:**
      - A container orchestration platform that manages multiple containers across a cluster of machines.
      - Works on top of Docker (or other container runtimes) to automate deployment, scaling, and operations.
      - Handles higher-level concerns like scheduling, networking, and self-healing across multiple nodes.
      - Example command: `kubectl apply` to deploy an application.

    **Key Difference:** Docker is about building and running containers, while Kubernetes is about orchestrating and managing a fleet of containers at scale.

4. **What is a container, and how does Kubernetes manage containers?**

    - **What is a container:**
      - A container is a lightweight, standalone, executable package that includes an application, its dependencies, libraries, and configuration files. It uses the host operating system’s kernel but isolates the application from the rest of the system, ensuring consistency across different environments.

    - **How Kubernetes manages containers:**
      - **Pods:** Kubernetes groups containers into Pods, the smallest deployable unit. A Pod can contain one or more containers that share storage and networking.
      - **Scheduling:** The Kubernetes scheduler assigns Pods to nodes based on resource availability, constraints, and policies.
      - **Lifecycle Management:** Kubernetes starts, stops, and restarts containers as needed using controllers like Deployments or ReplicaSets.
      - **Networking:** Assigns each Pod a unique IP address and manages communication between Pods and services.
      - **Monitoring:** Uses health checks (liveness and readiness probes) to ensure containers are running correctly.
      - **Scaling:** Adds or removes container instances based on demand or predefined rules.

5. **What are the key components of Kubernetes architecture?**

    Kubernetes follows a master-worker architecture with the following key components:

    - **Control Plane (Master Components):**
      - **kube-apiserver:** The front-end of the control plane, exposing the Kubernetes API for communication.
      - **etcd:** A distributed key-value store that holds the cluster’s configuration and state.
      - **kube-scheduler:** Assigns Pods to nodes based on resource requirements and constraints.
      - **kube-controller-manager:** Runs controllers (e.g., Node Controller, ReplicaSet Controller) to maintain the desired state.

    - **Worker Node Components:**
      - **kubelet:** An agent on each node that ensures containers in Pods are running as expected.
      - **kube-proxy:** Manages network rules for communication between Pods and services.
      - **Container Runtime:** Software (e.g., Docker, containerd) that runs containers on the node.

    - **Add-ons (Optional):**
      - DNS, Dashboard, Ingress controllers, and monitoring tools like Prometheus.

6. **What is a Pod in Kubernetes?**

    A Pod is the smallest and most basic deployable unit in Kubernetes. It represents a single instance of a running process in a cluster and can contain one or more containers that share the same network namespace (e.g., IP address and ports) and storage volumes. Pods are ephemeral—they can be created, destroyed, and replaced as needed.

    **Key Features:**
    - Containers within a Pod share localhost networking and can communicate easily.
    - Pods are managed by higher-level controllers like Deployments or ReplicaSets.
    - Each Pod gets a unique IP address within the cluster.

    **Example Use Case:** A web server (Nginx) and a sidecar container (log collector) might run in the same Pod.

7. **How does Kubernetes differ from traditional virtualization?**

    - **Traditional Virtualization:**
      - Uses a hypervisor (e.g., VMware, Hyper-V) to create virtual machines (VMs).
      - Each VM includes a full guest OS, making it heavyweight and resource-intensive.
      - Isolation is at the hardware level, with separate OS instances for each VM.
      - Slower to start due to OS boot time.

    - **Kubernetes (Container-Based):**
      - Uses containers that share the host OS kernel, making them lightweight and fast.
      - Isolation is at the process level using namespaces and cgroups.
      - Managed by Kubernetes for orchestration, scaling, and self-healing.
      - Faster startup (seconds vs. minutes) and better resource utilization.

    **Key Difference:** Kubernetes with containers eliminates the overhead of a full OS per application, offering greater efficiency and scalability compared to traditional VMs.

8. **What is the role of a Kubernetes cluster?**

    A Kubernetes cluster is a set of machines (nodes) that work together to run containerized applications. Its role is to:
    - Provide a unified environment for deploying, managing, and scaling applications.
    - Ensure high availability by distributing workloads across nodes.
    - Facilitate resource allocation and scheduling of Pods.
    - Enable self-healing by automatically recovering from failures.
    - Support networking, storage, and service discovery for containerized workloads.

    A cluster consists of at least one master node (control plane) and multiple worker nodes.

9. **What is the difference between a node and a cluster in Kubernetes?**

    - **Node:**
      - A single machine (physical or virtual) in a Kubernetes cluster.
      - Can be a master node (runs control plane components) or a worker node (runs application workloads).
      - Hosts Pods and includes components like kubelet, kube-proxy, and a container runtime.

    - **Cluster:**
      - A collection of nodes working together as a single system.
      - Managed by the control plane to orchestrate containerized applications.
      - Provides redundancy, scalability, and fault tolerance across all nodes.

    **Key Difference:** A node is an individual component, while a cluster is the entire system of interconnected nodes.

10. **What is a control plane in Kubernetes, and what does it consist of?**

     The control plane (formerly called the master) is the brain of a Kubernetes cluster, responsible for managing and maintaining the desired state of the cluster. It makes global decisions like scheduling Pods and responding to cluster events.

     **Components of the Control Plane:**
     - **kube-apiserver:** The entry point for all cluster operations, exposing the Kubernetes API.
     - **etcd:** A highly available key-value store that holds the cluster’s configuration data and state.
     - **kube-scheduler:** Assigns Pods to nodes based on resource needs, policies, and constraints.
     - **kube-controller-manager:** Runs background controllers (e.g., Node Controller, Replication Controller) to enforce the desired state.

     **Role:** The control plane ensures the cluster operates as intended, managing worker nodes and application workloads.

11. **Explain the role of the kube-apiserver.**
    The kube-apiserver is the central component of the Kubernetes control plane and serves as the primary entry point for all administrative tasks in a Kubernetes cluster. It exposes the Kubernetes API, which allows users, external systems, and other Kubernetes components to interact with the cluster. Its key responsibilities include:
    - **Authentication and Authorization:** Validates and authorizes requests from users or components using mechanisms like RBAC (Role-Based Access Control), tokens, or certificates.
    - **Processing API Requests:** Handles CRUD (Create, Read, Update, Delete) operations for Kubernetes resources (e.g., Pods, Services, Deployments) by interpreting RESTful API calls.
    - **Interfacing with etcd:** Acts as the front-end for the cluster’s state, persisting all cluster data (e.g., configuration, object states) into the etcd database.
    - **Communication Hub:** Facilitates communication between the control plane components (e.g., scheduler, controller manager) and worker nodes.
    In short, the kube-apiserver is the "brain" of Kubernetes, ensuring all operations and state changes are managed effectively.

12. **What does the kube-scheduler do?**
    The kube-scheduler is responsible for assigning Pods to nodes in the Kubernetes cluster based on resource availability, constraints, and user-defined policies. Its primary role is to ensure efficient workload distribution. Here’s how it works:
    - **Pod Scheduling:** When a new Pod is created and not yet assigned to a node, the scheduler identifies a suitable node based on:
        - Resource requirements (CPU, memory requests/limits).
        - Node capacity and availability.
        - Scheduling policies like taints, tolerations, node selectors, and affinity/anti-affinity rules.
    - **Scoring and Ranking:** It uses a two-step process:
        - Filtering: Eliminates nodes that don’t meet the Pod’s requirements (e.g., insufficient resources).
        - Scoring: Ranks the remaining nodes based on heuristics (e.g., least resource usage) to select the best fit.
    - **Binding:** Once a node is chosen, the scheduler updates the Pod’s specification via the kube-apiserver to bind it to that node.
    The kube-scheduler ensures optimal resource utilization and adherence to application-specific requirements.

13. **What is the purpose of the kube-controller-manager?**
    The kube-controller-manager is a control plane component that runs a collection of controllers—background processes that monitor and maintain the desired state of the cluster. Its purpose is to ensure that the actual state of the cluster matches the desired state defined by users. Key functions include:
    - **Running Controllers:** Manages multiple controllers, such as:
        - Node Controller: Monitors node health and handles node failures.
        - Replication Controller: Ensures the correct number of Pod replicas are running.
        - Endpoints Controller: Manages endpoint objects for Services.
        - Deployment Controller: Oversees Deployment resources and their ReplicaSets.
    - **Reconciliation Loop:** Continuously watches the cluster state via the kube-apiserver, compares it to the desired state, and takes corrective actions (e.g., creating or deleting Pods).
    - **High Availability:** In HA setups, multiple instances of kube-controller-manager run, with leader election ensuring only one is active at a time.
    It automates cluster management tasks, reducing manual intervention.

14. **What is etcd, and why is it important in Kubernetes?**
    etcd is a distributed, highly available key-value store that serves as the persistent backing store for all cluster data in Kubernetes. It’s often called the "single source of truth" for the cluster. Its importance lies in:
    - **State Storage:** Stores all Kubernetes objects (e.g., Pods, Services, ConfigMaps) and their metadata, including current state and desired state.
    - **Consistency:** Uses the Raft consensus algorithm to ensure data consistency and fault tolerance across multiple nodes.
    - **Watch Mechanism:** Provides real-time updates to the control plane components (via the kube-apiserver) when cluster state changes.
    - **Critical Dependency:** The kube-apiserver relies on etcd to read and write cluster state, making it essential for cluster operations.
    Without etcd, Kubernetes cannot function, as it would lose track of the cluster’s configuration and state.

15. **How does the kubelet work on a worker node?**
    The kubelet is an agent that runs on every worker node in a Kubernetes cluster. Its primary role is to manage Pods and ensure they run as expected. Here’s how it works:
    - **Communication with Control Plane:** Registers the node with the kube-apiserver and listens for Pod specifications assigned to its node.
    - **Pod Management:** 
        - Reads Pod specs from the API server (or local manifests) and ensures the described containers are running.
        - Starts, stops, and restarts containers via the container runtime (e.g., Docker, containerd).
    - **Health Monitoring:** Performs liveness and readiness checks on Pods and reports their status back to the control plane.
    - **Node Resource Reporting:** Monitors and reports the node’s resource usage (CPU, memory, disk) to the kube-apiserver.
    The kubelet acts as the "node-level enforcer" of the cluster’s desired state.

16. **What is the role of kube-proxy in Kubernetes?**
    kube-proxy is a network proxy that runs on each node in the cluster and manages network rules to enable communication between Pods, Services, and external traffic. Its key responsibilities include:
    - **Service Abstraction:** Maintains network rules (e.g., iptables, IPVS) to implement Kubernetes Services, ensuring Pods can communicate via virtual IPs (ClusterIPs).
    - **Load Balancing:** Distributes traffic across multiple Pod replicas behind a Service.
    - **Network Connectivity:** Facilitates Pod-to-Pod, Pod-to-Service, and external-to-Service communication by managing NAT (Network Address Translation) and routing.
    - **Modes:** Operates in different modes like iptables (default), IPVS, or userspace (legacy), depending on performance needs.
    In essence, kube-proxy ensures seamless networking within the cluster.

17. **Explain the difference between master nodes and worker nodes.**
    - **Master Nodes:** 
        - Host the control plane components (e.g., kube-apiserver, kube-scheduler, kube-controller-manager, etcd).
        - Responsible for managing the cluster: scheduling Pods, maintaining state, and responding to API requests.
        - Typically do not run user workloads (though this can be overridden).
        - Require high reliability and are often deployed in HA configurations.
    - **Worker Nodes:** 
        - Run the actual application workloads (Pods) assigned by the scheduler.
        - Host the kubelet (to manage Pods) and kube-proxy (for networking).
        - Provide compute, memory, and storage resources for containers.
        - Report their status to the master nodes via the kubelet.
    In summary, master nodes control the cluster, while worker nodes execute the workloads.

18. **What is the Cloud Controller Manager, and when is it used?**
    The Cloud Controller Manager (CCM) integrates Kubernetes with cloud provider APIs, offloading cloud-specific control logic from the core Kubernetes components. It’s used in clusters running on cloud platforms (e.g., AWS, GCP, Azure). Its role includes:
    - **Node Management:** Syncs node information with cloud metadata (e.g., adding/removing nodes).
    - **Load Balancer Management:** Provisions cloud load balancers for Kubernetes Services (e.g., type LoadBalancer).
    - **Storage Integration:** Manages cloud storage resources like volumes (e.g., AWS EBS, GCE Persistent Disk).
    - **Separation of Concerns:** Allows Kubernetes to remain cloud-agnostic by delegating provider-specific tasks to the CCM.
    It’s used when deploying Kubernetes on a cloud provider to leverage native cloud features without modifying core Kubernetes code.

19. **How does Kubernetes ensure high availability of the control plane?**
    Kubernetes ensures high availability (HA) of the control plane by:
    - **Multiple Master Nodes:** Running multiple instances of control plane components (kube-apiserver, kube-controller-manager, kube-scheduler) across different nodes.
    - **etcd Distribution:** Deploying etcd as a distributed cluster (typically 3 or 5 nodes) with data replication for fault tolerance.
    - **Leader Election:** Using mechanisms like Raft (in etcd) and leader election in kube-controller-manager/scheduler to ensure only one instance is active, with failover to others if needed.
    - **Load Balancing:** Placing a load balancer (e.g., external or cloud-provided) in front of multiple kube-apiserver instances to distribute API requests.
    - **Redundancy:** Ensuring no single point of failure by replicating components and data across nodes/zones.
    This setup ensures the control plane remains operational even if individual components or nodes fail.

20. **What is a Namespace in Kubernetes, and how is it used?**
    A Namespace in Kubernetes is a logical partition within a cluster used to organize and isolate resources. It’s a way to divide cluster resources among multiple users, teams, or projects. Its uses include:
    - **Resource Isolation:** Groups related objects (e.g., Pods, Services) while preventing naming collisions (e.g., two teams can have a Service named "frontend").
    - **Access Control:** Combined with RBAC, restricts users to specific namespaces, enhancing security.
    - **Resource Quotas:** Limits resource usage (CPU, memory) per namespace to prevent overconsumption.
    - **Default Namespaces:** Kubernetes provides built-in namespaces like default (for objects without a namespace), kube-system (for system resources), and kube-public (for public access).
    For example, a dev team might use a "dev" namespace, while production runs in a "prod" namespace, keeping environments separate within the same cluster.

21. **How do you create a Pod in Kubernetes?**
    - A Pod is the smallest deployable unit in Kubernetes, and you can create it using either a YAML file or a kubectl command.
    - Using a YAML file: Define the Pod specification in a YAML file and apply it using kubectl.
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
      Then run:
      ```bash
      kubectl apply -f pod.yaml
      ```
    - Using kubectl command: You can create a Pod imperatively with:
      ```bash
      kubectl run my-pod --image=nginx --restart=Never
      ```
      The `--restart=Never` flag ensures a Pod is created instead of a Deployment.
    - Once created, you can verify it with:
      ```bash
      kubectl get pods
      ```

22. **What is the difference between a Pod and a Deployment?**
    - **Pod**: A Pod is the basic execution unit in Kubernetes that encapsulates one or more containers sharing the same network and storage resources. It’s typically created directly for one-off tasks or testing, but it doesn’t provide self-healing or scaling capabilities natively.
    - **Deployment**: A Deployment is a higher-level resource that manages Pods declaratively. It ensures a specified number of Pod replicas are running, provides rolling updates, rollbacks, and self-healing (e.g., restarting failed Pods). It uses a ReplicaSet under the hood to manage the Pods.
    - **Key Difference**: A Pod is a low-level primitive, while a Deployment is a controller that manages Pods for scalability and reliability.

23. **What is a ReplicaSet, and how does it relate to a Deployment?**
    - **ReplicaSet**: A ReplicaSet is a Kubernetes resource that ensures a specified number of Pod replicas are running at any given time. It replaces failed Pods and maintains the desired state based on a Pod template and a replica count.
    - **Relation to Deployment**: A Deployment creates and manages a ReplicaSet to handle the Pods. When you update a Deployment (e.g., change the image version), it creates a new ReplicaSet with the updated configuration and gradually phases out the old ReplicaSet. Thus, the Deployment provides orchestration features (like rolling updates) while delegating Pod replication to the ReplicaSet.
    - **Example**: When you create a Deployment with 3 replicas, it spawns a ReplicaSet, which in turn ensures 3 Pods are running.

24. **Explain the lifecycle of a Pod.**
    - The lifecycle of a Pod in Kubernetes consists of several phases:
      - **Pending**: The Pod has been accepted by the Kubernetes system but is not yet running. This could be due to scheduling, image pulling, or resource allocation delays.
      - **Running**: At least one container in the Pod is running, and the Pod is scheduled on a node.
      - **Succeeded**: All containers in the Pod have terminated successfully (exit code 0), and the Pod won’t restart (common for Jobs).
      - **Failed**: All containers in the Pod have terminated, with at least one having a non-zero exit code, and no restarts are scheduled.
      - **Unknown**: The state of the Pod cannot be determined (e.g., due to communication issues with the node).
    - **Additional Notes**:
      - During its lifecycle, a Pod may use init containers (run before app containers) and liveness/readiness probes to manage container health.
      - Pods can be deleted manually or by a controller (e.g., Deployment) when no longer needed.

25. **What are labels and selectors in Kubernetes, and how are they used?**
    - **Labels**: Key-value pairs attached to Kubernetes objects (e.g., Pods, Services) for identification and organization. Example: `app: nginx`, `env: production`.
    - **Selectors**: Mechanisms to filter or select objects based on their labels. There are two types:
      - **Equality-based selectors**: Match exact label values (e.g., `app=nginx`).
      - **Set-based selectors**: Use conditions like `in`, `notin`, or `exists` (e.g., `env in (production, staging)`).
    - **Usage**:
      - **Pods and Services**: A Service uses a selector to route traffic to Pods with matching labels.
      - **Deployments/ReplicaSets**: They use selectors to manage Pods with specific labels.
      - **Organization**: Labels help group resources for querying or management (e.g., `kubectl get pods -l app=nginx`).

26. **What is a multi-container Pod, and when would you use one?**
    - **Multi-container Pod**: A Pod that runs multiple containers sharing the same network namespace (same IP and port space) and storage volumes. Containers in a Pod can communicate via localhost.
    - **When to Use**:
      - **Sidecar Pattern**: One container enhances or supports another (e.g., a logging agent like Fluentd alongside an app container).
      - **Proxy Pattern**: A container acts as a reverse proxy (e.g., an ambassador container handling external communication).
      - **Tight Coupling**: When two processes need to share resources closely (e.g., a web server and a content generator).
    - **Example**: A Pod with an Nginx container (web server) and a Git-sync container (to pull content).

27. **How do you restart a Pod without deleting it?**
    - Kubernetes doesn’t provide a direct “restart” command for Pods, as Pods are ephemeral. However, you can restart a Pod’s containers in these ways:
      - Using kubectl to trigger a container restart:
        ```bash
        kubectl delete pod <pod-name> --grace-period=0 --force
        ```
        If managed by a Deployment, the ReplicaSet will recreate it instantly.
      - Edit the Pod/Deployment: Change a trivial spec (e.g., add a label or environment variable) to force a restart:
        ```bash
        kubectl edit pod <pod-name>
        ```
        Or for a Deployment:
        ```bash
        kubectl rollout restart deployment <deployment-name>
        ```
      - **Liveness Probe Failure**: If configured, a failing liveness probe will cause Kubernetes to restart the container automatically.
    - **Note**: For Pods managed by higher-level controllers (like Deployments), restarting the controller is preferred.

28. **What is a StatefulSet, and how does it differ from a Deployment?**
    - **StatefulSet**: A Kubernetes controller for managing stateful applications, ensuring Pods have stable identities (e.g., pod-name-0, pod-name-1) and persistent storage. It’s designed for apps like databases (e.g., MySQL, MongoDB).
    - **Differences from Deployment**:
      - **Identity**: StatefulSet Pods have predictable, ordered names and stable network identities (via headless services); Deployment Pods are interchangeable.
      - **Scaling**: StatefulSets scale up/down sequentially (e.g., Pod 0, then Pod 1); Deployments scale arbitrarily.
      - **Storage**: StatefulSets associate each Pod with a unique Persistent Volume; Deployments don’t inherently manage state.
    - **Use Case**: StatefulSets are for stateful apps; Deployments are for stateless apps (e.g., web servers).

29. **What is a DaemonSet, and in what scenarios is it useful?**
    - **DaemonSet**: A Kubernetes controller that ensures one Pod runs on every node in the cluster (or a subset of nodes based on node selectors/taints). If a new node is added, the DaemonSet automatically deploys a Pod there.
    - **Scenarios**:
      - **Monitoring**: Deploy monitoring agents (e.g., Prometheus Node Exporter) on every node.
      - **Logging**: Run log collectors (e.g., Fluentd, Filebeat) on all nodes.
      - **Networking**: Deploy network proxies or agents (e.g., kube-proxy, CNI plugins).
      - **Security**: Run security agents on every node.
    - **Example**: A DaemonSet for a logging agent ensures logs are collected from every node in the cluster.

30. **What is a Job and a CronJob in Kubernetes?**
    - **Job**: A Kubernetes resource that creates one or more Pods to run a task to completion. Once the task finishes successfully (exit code 0), the Pods terminate. Useful for batch processing, data migrations, or one-time tasks.
      - **Example**:
        ```yaml
        apiVersion: batch/v1
        kind: Job
        metadata:
          name: my-job
        spec:
          template:
            spec:
              containers:
              - name: my-task
                image: busybox
                command: ["echo", "Task completed"]
              restartPolicy: Never
        ```
    - **CronJob**: A controller that schedules Jobs to run at specific times or intervals, similar to a Unix cron job. It’s used for recurring tasks like backups or report generation.
      - **Example**:
        ```yaml
        apiVersion: batch/v1
        kind: CronJob
        metadata:
          name: my-cronjob
        spec:
          schedule: "*/5 * * * *" # Every 5 minutes
          jobTemplate:
            spec:
              template:
                spec:
                  containers:
                  - name: my-task
                    image: busybox
                    command: ["echo", "Scheduled task"]
                  restartPolicy: OnFailure
        ```
    - **Key Difference**: A Job runs once until completion; a CronJob runs Jobs on a schedule.

31. **How does networking work in Kubernetes?**
    Kubernetes networking is designed to enable communication between Pods, Services, and external entities while ensuring scalability and flexibility. It follows these key principles:
    - **Pod-to-Pod Communication:** Every Pod in a Kubernetes cluster gets its own unique IP address, assigned from a cluster-wide IP range. Pods can communicate directly with each other across nodes without NAT (Network Address Translation).
    - **Flat Networking:** Kubernetes uses a flat network model, meaning there’s no need for complex routing or port mapping between Pods. This is achieved through an overlay network or underlay network, depending on the CNI plugin.
    - **Service Abstraction:** Services provide a stable IP and DNS name to access a group of Pods, abstracting away the dynamic nature of Pod IPs.
    - **External Access:** External traffic is routed into the cluster via Services (e.g., NodePort, LoadBalancer) or Ingress controllers.
    - **CNI Plugins:** Kubernetes relies on the Container Network Interface (CNI) to manage networking. Popular CNI plugins (e.g., Flannel, Calico, Cilium) handle IP allocation, routing, and network policies.
    - The networking stack involves kube-proxy (for service routing), the CNI plugin (for Pod networking), and DNS (via CoreDNS or kube-dns) for name resolution.

32. **What is a ClusterIP service, and how does it work?**
    - **Definition:** ClusterIP is the default Service type in Kubernetes. It provides a virtual IP address (called the Cluster IP) within the cluster to enable communication between Pods or between applications and a group of Pods.
    - **How It Works:**
        - When you create a ClusterIP Service, Kubernetes assigns a stable IP address from an internal range (configured in the cluster).
        - The Service uses labels and selectors to identify a set of Pods it routes traffic to.
        - kube-proxy, running on each node, programs iptables (or uses IPVS in advanced setups) to redirect traffic sent to the Cluster IP to one of the backend Pods (load balancing).
        - The Cluster IP is only accessible within the cluster and is not exposed externally unless paired with another mechanism (e.g., Ingress).
    - **Use Case:** Internal communication, such as a frontend application talking to a backend API within the cluster.

33. **Explain the difference between ClusterIP, NodePort, and LoadBalancer services.**
    - **ClusterIP:**
        - Default Service type.
        - Creates a virtual IP accessible only within the cluster.
        - Used for internal communication between Pods or applications.
        - Example: A database service accessed by an application Pod.
    - **NodePort:**
        - Exposes the Service on a specific port of each node in the cluster (in addition to the Cluster IP).
        - The port range is typically 30000–32767.
        - External traffic can reach the Service by hitting `<NodeIP>:<NodePort>`.
        - kube-proxy maps the NodePort to the Service’s Cluster IP, which then routes to Pods.
        - **Use Case:** Temporary external access or when a load balancer isn’t available.
    - **LoadBalancer:**
        - Exposes the Service externally using a cloud provider’s load balancer (e.g., AWS ELB, GCP Load Balancer).
        - Assigns a public IP or hostname to the Service, making it accessible from outside the cluster.
        - Internally, it still uses a Cluster IP and may use NodePorts as a fallback.
        - **Use Case:** Production-grade external access to an application (e.g., a web server).
    - **Key Difference:** ClusterIP is internal-only, NodePort exposes via node IPs and ports, and LoadBalancer integrates with cloud infrastructure for external access.

34. **What is an Ingress controller, and why is it used?**
    - **Definition:** An Ingress controller is a Kubernetes resource and accompanying software (e.g., NGINX, Traefik, HAProxy) that manages external HTTP/HTTPS traffic to Services, typically for load balancing, SSL termination, and routing based on hostnames or paths.
    - **How It Works:**
        - An Ingress resource defines rules (e.g., route `/api` to the API Service, `/web` to the web Service).
        - The Ingress controller watches these resources via the Kubernetes API and configures itself to enforce the rules.
        - It sits between external traffic and Services, often exposed via a LoadBalancer or NodePort Service.
    - **Why It’s Used:**
        - Simplifies external access by consolidating routing logic (no need for multiple LoadBalancer Services).
        - Supports advanced features like path-based routing, domain-based routing, and TLS termination.
        - Reduces costs (e.g., one load balancer for multiple Services instead of one per Service).
    - **Example:** Routing `example.com/api` to an API Service and `example.com/blog` to a blog Service using a single Ingress controller.

35. **How does Kubernetes handle DNS resolution within a cluster?**
    - Kubernetes provides built-in DNS resolution through a cluster-wide DNS server (CoreDNS or kube-dns in older versions).
    - **How It Works:**
        - Each Service gets a DNS name in the format `<service-name>.<namespace>.svc.cluster.local`.
        - Pods can resolve this name to the Service’s Cluster IP.
        - CoreDNS runs as a Deployment in the kube-system namespace and is configured by the kubelet to serve as the DNS server for Pods.
        - When a Pod is created, its `/etc/resolv.conf` is populated with the cluster DNS server’s IP.
        - For example, a Service named `my-service` in the default namespace can be accessed as `my-service.default.svc.cluster.local`.
    - **Features:**
        - Supports short names (e.g., `my-service`) within the same namespace.
        - Automatically updates DNS records when Services or Pods change.
    - **Use Case:** Pods use DNS to discover and communicate with Services dynamically.

36. **What is a CNI (Container Network Interface), and why is it important?**
    - **Definition:** The Container Network Interface (CNI) is a standard specification and set of plugins that Kubernetes uses to configure networking for Pods.
    - **How It Works:**
        - When a Pod is scheduled, the kubelet calls the CNI plugin to assign an IP address and set up networking.
        - The CNI plugin integrates with the underlying network infrastructure (e.g., overlay networks like VXLAN or underlay routing).
        - **Examples:** Flannel (simple overlay), Calico (advanced networking and policies), Cilium (eBPF-based).
    - **Why It’s Important:**
        - Ensures Pods get unique IP addresses and can communicate across nodes.
        - Provides flexibility to choose networking solutions based on needs (e.g., performance, security).
        - Enables advanced features like Network Policies for traffic control.
        - Abstracts networking complexity from Kubernetes, making it portable across environments.

37. **How do Pods communicate with each other in Kubernetes?**
    - **Direct Communication:** Pods communicate using their assigned IP addresses. Since every Pod gets a unique IP in a flat network, they can reach each other directly across nodes without NAT.
    - **Via Services:** Pods often communicate through a Service, which provides a stable IP (Cluster IP) and DNS name. The Service load-balances traffic to the target Pods.
    - **Networking Layer:** The CNI plugin ensures connectivity by setting up routing or an overlay network (e.g., VXLAN, IP-in-IP).
    - **DNS:** Pods can use DNS names (e.g., `my-service.default.svc.cluster.local`) to find and connect to other Pods via Services.
    - **Example:** Pod A at `10.244.1.2` can send a request to Pod B at `10.244.2.3` directly or use a Service’s Cluster IP like `10.96.0.10`.

38. **What is Network Policy, and how can you enforce it?**
    - **Definition:** A Network Policy is a Kubernetes resource that defines rules for controlling traffic to and from Pods based on labels, namespaces, IP ranges, or ports.
    - **How It Works:**
        - Policies are applied to Pods using label selectors.
        - Rules can allow or deny ingress (incoming) and egress (outgoing) traffic.
        - Requires a CNI plugin that supports Network Policies (e.g., Calico, Cilium; Flannel does not).
    - **Enforcement:**
        - Create a NetworkPolicy YAML file specifying podSelector, ingress, and egress rules.
        - Apply it with `kubectl apply -f policy.yaml`.
        - **Example:** Allow traffic only from Pods labeled `app=frontend` to Pods labeled `app=backend` on port 80.
    - **Use Case:** Restrict database Pods to only accept traffic from application Pods, enhancing security.

39. **How does Kubernetes handle external traffic to services?**
    - **NodePort:** Exposes a Service on a specific port of each node. External clients can access it via `<NodeIP>:<NodePort>`.
    - **LoadBalancer:** Integrates with a cloud provider’s load balancer to assign a public IP or hostname to the Service, routing traffic to Pods.
    - **Ingress:** Uses an Ingress controller to manage external HTTP/HTTPS traffic, providing advanced routing (e.g., based on URL paths or domains) and TLS termination.
    - **Process:** External traffic hits the exposed endpoint (NodePort, LoadBalancer IP, or Ingress), kube-proxy routes it to the Service’s Cluster IP, and the Service forwards it to the appropriate Pods.

40. **What is the purpose of kube-proxy in networking?**
    - **Definition:** kube-proxy is a network proxy that runs on each node in a Kubernetes cluster.
    - **Purpose:**
        - Manages Service networking by maintaining network rules to route traffic from a Service’s Cluster IP to its backend Pods.
        - Implements load balancing across Pods for a Service.
    - **How It Works:**
        - Watches the Kubernetes API for Service and Endpoint changes.
        - Configures iptables (or IPVS in advanced setups) to redirect traffic to the correct Pods.
        - For example, traffic to a Cluster IP like `10.96.0.10` is distributed to Pods like `10.244.1.2` and `10.244.1.3`.
    - **Modes:** Supports iptables (default), IPVS (for high performance), and userspace (legacy).
    - **Key Role:** Ensures Services work seamlessly by abstracting Pod IP changes and providing reliable connectivity.

41. **What is a Persistent Volume (PV) in Kubernetes?**
    A Persistent Volume (PV) is a cluster-wide resource in Kubernetes that represents a piece of storage provisioned by an administrator or dynamically by a storage system. It abstracts the underlying storage details (e.g., NFS, iSCSI, cloud storage like AWS EBS or GCE PD) and provides a way for applications to use storage without needing to know the specifics of how it’s implemented. PVs are independent of Pods and have a lifecycle separate from the Pods that use them, ensuring data persistence even if a Pod is deleted or rescheduled.

42. **What is a Persistent Volume Claim (PVC), and how does it relate to a PV?**
    A Persistent Volume Claim (PVC) is a request for storage by a user or application in Kubernetes. It specifies requirements such as storage size, access mode, and optionally a StorageClass. A PVC acts as a "claim" that binds to an available Persistent Volume (PV) that matches its requirements. Once bound, the PVC provides a handle for Pods to use the storage. Essentially, PVCs are the user-facing abstraction, while PVs are the underlying resource managed by the cluster. The relationship is one-to-one: a PVC binds to a single PV, and once bound, that PV is reserved exclusively for the PVC until released.

43. **Explain the difference between static and dynamic provisioning of storage.**
    - **Static Provisioning:** In static provisioning, an administrator manually creates Persistent Volumes (PVs) in advance and defines their properties (e.g., size, type, access mode). Users then create PVCs that bind to these pre-existing PVs based on matching criteria. This approach gives fine-grained control but requires manual intervention and doesn’t scale well for large or dynamic environments.
    - **Dynamic Provisioning:** In dynamic provisioning, PVs are automatically created by Kubernetes when a PVC is requested, provided a StorageClass is specified and a storage provisioner (e.g., for AWS EBS or GCE PD) is configured. This eliminates the need for pre-creating PVs, making it more scalable and efficient for cloud-native environments. The StorageClass defines the storage type and policies, enabling on-demand provisioning.

44. **What is a StorageClass, and how does it work?**
    A StorageClass is a Kubernetes resource that defines a "class" or type of storage available in a cluster (e.g., fast SSD, slow HDD, or cloud-specific options like aws-ebs). It allows administrators to abstract storage provisioning details and policies (e.g., replication, backup) into reusable templates. When a PVC references a StorageClass, Kubernetes uses the associated provisioner (e.g., a CSI driver or cloud provider plugin) to dynamically create a PV that meets the PVC’s requirements. For example:
    - A StorageClass named `fast` might use SSDs with high IOPS.
    - When a PVC specifies `storageClassName: fast`, a matching PV is provisioned automatically.

45. **How do you attach storage to a Pod?**
    To attach storage to a Pod, you:
    - Create a Persistent Volume (PV) (for static provisioning) or rely on dynamic provisioning via a StorageClass.
    - Create a Persistent Volume Claim (PVC) that requests the storage with the desired size and access mode.
    - Reference the PVC in the Pod specification using the `volumes` and `volumeMounts` fields in the Pod’s YAML. For example:
      ```yaml
      apiVersion: v1
      kind: Pod
      metadata:
        name: my-pod
      spec:
        containers:
        - name: my-container
          image: nginx
          volumeMounts:
          - mountPath: "/data"
            name: my-volume
        volumes:
        - name: my-volume
          persistentVolumeClaim:
            claimName: my-pvc
      ```
    Kubernetes binds the PVC to a PV, and the Pod can then use the storage mounted at the specified path (e.g., `/data`).

46. **What are the access modes for Persistent Volumes (e.g., RWO, RWX)?**
    Persistent Volumes support the following access modes, depending on the underlying storage capabilities:
    - **ReadWriteOnce (RWO):** The volume can be mounted as read-write by a single node. Multiple Pods on the same node can use it, but it’s restricted to one node at a time. Common for single-node databases (e.g., AWS EBS).
    - **ReadOnlyMany (ROX):** The volume can be mounted as read-only by multiple nodes simultaneously. Useful for serving static content.
    - **ReadWriteMany (RWX):** The volume can be mounted as read-write by multiple nodes at the same time. Supported by distributed file systems like NFS or GlusterFS, ideal for shared storage use cases.
    The access mode requested in a PVC must be supported by the PV it binds to.

47. **How does Kubernetes handle storage in a multi-node cluster?**
    In a multi-node cluster, Kubernetes manages storage through Persistent Volumes (PVs) and Persistent Volume Claims (PVCs), abstracting the underlying storage system. Key aspects include:
    - **Node Affinity:** For RWO volumes (e.g., AWS EBS), Kubernetes ensures the Pod using the volume is scheduled on the same node where the storage is accessible, using node affinity rules in the PV spec.
    - **Distributed Storage:** For RWX volumes (e.g., NFS), multiple nodes can access the same storage simultaneously via a shared file system.
    - **Dynamic Provisioning:** StorageClasses and provisioners handle the creation of PVs across nodes, integrating with cloud provider APIs or on-premises systems.
    - **CSI Drivers:** The Container Storage Interface (CSI) standardizes storage interactions, allowing seamless integration with various storage backends across nodes.
    - **Data Persistence:** PVs ensure data remains available even if Pods move between nodes, as long as the storage backend supports it.

48. **What is the role of the CSI (Container Storage Interface) in Kubernetes?**
    The Container Storage Interface (CSI) is a standardized API that allows Kubernetes to integrate with various storage systems in a vendor-agnostic way. Its role includes:
    - **Extensibility:** CSI enables third-party storage providers (e.g., Ceph, Portworx) to write drivers that work with Kubernetes without modifying core Kubernetes code.
    - **Dynamic Provisioning:** CSI drivers handle the creation, deletion, and management of PVs when PVCs are created with a StorageClass.
    - **Advanced Features:** CSI supports features like snapshots, cloning, and resizing of volumes, which older in-tree plugins couldn’t easily provide.
    - **Decoupling:** It replaces legacy in-tree storage plugins (e.g., for AWS EBS) with out-of-tree drivers, improving maintainability and flexibility.
    CSI drivers are deployed as containers in the cluster and interact with the Kubernetes control plane to manage storage operations.

49. **How do you troubleshoot storage issues in Kubernetes?**
    To troubleshoot storage issues in Kubernetes:
    - **Check Pod Status:** Use `kubectl get pods` and `kubectl describe pod <pod-name>` to see if the Pod is stuck (e.g., in Pending or CrashLoopBackOff) due to storage issues.
    - **Inspect PVC and PV:** Run `kubectl get pvc` and `kubectl get pv` to verify binding status. Use `kubectl describe pvc <pvc-name>` or `kubectl describe pv <pv-name>` to check events for errors (e.g., "failed to provision volume").
    - **Verify StorageClass:** Ensure the StorageClass exists (`kubectl get storageclass`) and matches the PVC’s requirements.
    - **Check Node Availability:** For RWO volumes, confirm the target node is healthy (`kubectl get nodes`) and matches the PV’s node affinity.
    - **Examine Logs:** Check logs of the Pod (`kubectl logs <pod-name>`), kubelet, or CSI driver Pods for errors related to mounting.
    - **Test Access:** Exec into the Pod (`kubectl exec -it <pod-name> -- sh`) and verify the volume is mounted and accessible.
    - **Cluster Events:** Use `kubectl get events` to identify storage-related failures (e.g., quota issues, provisioner errors).
    - **CSI Driver Issues:** If using CSI, ensure the driver Pods are running (`kubectl get pods -n kube-system`) and check their logs.

50. **What happens to a PV when a PVC is deleted?**
    When a PVC is deleted, the fate of the associated Persistent Volume (PV) depends on its reclaim policy:
    - **Retain:** The PV is not deleted or reused. It remains in the cluster with its data intact, marked as "Released," and must be manually reclaimed or deleted by an administrator. Useful for preserving data.
    - **Delete:** The PV is automatically deleted, and the underlying storage (e.g., AWS EBS volume) is destroyed, assuming the provisioner supports it. Common in dynamic provisioning scenarios.
    - **Recycle (Deprecated):** The PV’s data is wiped, and it’s made available for a new PVC. This is rarely used today due to security concerns and has been replaced by dynamic provisioning.
    You can check or set the reclaim policy in the PV spec under `persistentVolumeReclaimPolicy`. For example:
    ```yaml
    persistentVolumeReclaimPolicy: Retain
    ```

**51. What is a ConfigMap, and how is it used?**
A ConfigMap is a Kubernetes resource used to store non-sensitive configuration data in key-value pairs. It allows you to decouple configuration details from application code, making it easier to manage and update settings without rebuilding container images.
Usage: ConfigMaps can be used to store configuration files, environment variables, or command-line arguments for Pods. For example, you might use a ConfigMap to define database connection strings, API endpoints, or feature flags.
How it’s used: You can inject ConfigMap data into a Pod as environment variables, command-line arguments, or mounted as files in a volume.
Example:
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: app-config
data:
    database_url: "mysql://localhost:3306"
    log_level: "debug"
```

**52. How do you inject environment variables into a Pod?**
You can inject environment variables into a Pod in two primary ways:
Directly in the Pod spec:
Define environment variables explicitly in the Pod’s container specification.
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        env:
        - name: DB_URL
            value: "mysql://localhost:3306"
        - name: LOG_LEVEL
            value: "debug"
```
Using a ConfigMap:
Reference a ConfigMap to populate environment variables.
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        env:
        - name: DB_URL
            valueFrom:
                configMapKeyRef:
                    name: app-config
                    key: database_url
        - name: LOG_LEVEL
            valueFrom:
                configMapKeyRef:
                    name: app-config
                    key: log_level
```
You can also use `envFrom` to inject all key-value pairs from a ConfigMap as environment variables automatically.

**53. What is a Secret in Kubernetes, and how does it differ from a ConfigMap?**
A Secret is a Kubernetes resource designed to store sensitive data, such as passwords, API keys, or certificates, in a secure manner. It is similar to a ConfigMap but is specifically intended for confidential information.
Differences:
- Purpose: ConfigMaps store non-sensitive configuration data, while Secrets store sensitive data.
- Encoding: Secrets are base64-encoded by default (though not encrypted at rest unless additional measures are taken), while ConfigMaps store plain text.
- Security: Secrets have stricter access controls and can be encrypted at rest (with proper cluster configuration), while ConfigMaps do not.
- Size: Secrets are intended for small amounts of data (up to 1MB), whereas ConfigMaps can handle larger configurations.
Example of a Secret:
```yaml
apiVersion: v1
kind: Secret
metadata:
    name: my-secret
type: Opaque
data:
    password: cGFzc3dvcmQ=  # base64-encoded "password"
```

**54. How do you create and manage Secrets in Kubernetes?**
Creating a Secret:
Using a YAML file:
```yaml
apiVersion: v1
kind: Secret
metadata:
    name: my-secret
type: Opaque
data:
    username: YWRtaW4=  # base64-encoded "admin"
    password: cGFzc3dvcmQ=  # base64-encoded "password"
```
Apply it with: `kubectl apply -f secret.yaml`.
Using kubectl command:
```bash
kubectl create secret generic my-secret --from-literal=username=admin --from-literal=password=password
```
Managing Secrets:
- View Secrets: `kubectl get secrets` or `kubectl describe secret my-secret` (values are not shown in plain text).
- Edit Secrets: `kubectl edit secret my-secret` (edit the base64-encoded values).
- Delete Secrets: `kubectl delete secret my-secret`.
Secrets can also be managed imperatively with files:
```bash
kubectl create secret generic my-secret --from-file=secret-file.txt
```

**55. How can you mount a ConfigMap or Secret as a volume in a Pod?**
You can mount a ConfigMap or Secret as a volume to make its data available as files in a Pod.
Mounting a ConfigMap:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        volumeMounts:
        - name: config-volume
            mountPath: "/etc/config"
    volumes:
    - name: config-volume
        configMap:
            name: app-config
```
This mounts the ConfigMap’s key-value pairs as files under `/etc/config`.
Mounting a Secret:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        volumeMounts:
        - name: secret-volume
            mountPath: "/etc/secret"
    volumes:
    - name: secret-volume
        secret:
            secretName: my-secret
```
The Secret’s data is mounted as files (e.g., `/etc/secret/username`).

**56. What are the security best practices for managing Secrets in Kubernetes?**
- Enable Encryption at Rest: Configure the Kubernetes API server to encrypt Secrets in etcd using a provider like aesgcm or an external KMS (Key Management Service).
- Use RBAC: Restrict access to Secrets with Role-Based Access Control (RBAC) to only authorized users and ServiceAccounts.
- Avoid Hardcoding: Never store Secrets in source code or ConfigMaps; use Secrets instead.
- Limit Scope: Use namespaces to isolate Secrets and minimize their exposure.
- Rotate Secrets: Regularly update and rotate sensitive data to reduce the risk of leaks.
- Use External Secret Managers: Integrate with tools like HashiCorp Vault, AWS Secrets Manager, or Azure Key Vault for better security.
- Audit Access: Enable audit logging to monitor who accesses Secrets.
- Least Privilege: Grant Pods only the minimum access required to Secrets via ServiceAccounts.

**57. How do you update a ConfigMap or Secret without restarting a Pod?**
For ConfigMaps:
- If mounted as a volume, updates to the ConfigMap are automatically reflected in the Pod (with a slight delay due to sync intervals, typically ~10 seconds).
- If injected as environment variables, the Pod must be restarted (e.g., by deleting it or updating the Deployment) to pick up changes.
For Secrets:
- Similar to ConfigMaps, Secrets mounted as volumes are updated automatically.
- Secrets used as environment variables require a Pod restart.
To update:
- Edit the resource: `kubectl edit configmap app-config` or `kubectl edit secret my-secret`.
- Apply a new version: `kubectl apply -f updated-configmap.yaml`.
For volumes, no action is needed; for env vars, restart the Pod:
```bash
kubectl rollout restart deployment my-deployment
```

**58. What is the difference between a base64-encoded Secret and a plain-text ConfigMap?**
Base64-Encoded Secret:
- Secrets store data in base64-encoded format to handle binary or sensitive data.
- It’s not encryption—just an encoding mechanism—so it’s not secure by itself unless encrypted at rest.
- Example: "password" becomes `cGFzc3dvcmQ=`.
Plain-Text ConfigMap:
- ConfigMaps store data as plain text, making it human-readable and suitable for non-sensitive configuration.
- No encoding or obfuscation is applied.
- Example: `log_level: debug` is stored as-is.
Key Difference: Base64 encoding in Secrets provides a minimal layer of obfuscation, but the primary distinction is their intended use (sensitive vs. non-sensitive data).

**59. How do you pass sensitive data to an application in Kubernetes?**
Sensitive data can be passed to an application via Secrets in two ways:
As Environment Variables:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        env:
        - name: PASSWORD
            valueFrom:
                secretKeyRef:
                    name: my-secret
                    key: password
```
As a Mounted Volume:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        volumeMounts:
        - name: secret-volume
            mountPath: "/etc/secret"
    volumes:
    - name: secret-volume
        secret:
            secretName: my-secret
```
The application reads the sensitive data (e.g., password) from `/etc/secret/password`.

**60. What is the role of RBAC in securing ConfigMaps and Secrets?**
Role-Based Access Control (RBAC) in Kubernetes defines permissions for users, ServiceAccounts, or groups to access resources like ConfigMaps and Secrets.
Role in Security:
- RBAC ensures that only authorized entities can create, read, update, or delete ConfigMaps and Secrets.
- It prevents unauthorized Pods or users from accessing sensitive data.
Example RBAC Configuration:
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
    namespace: default
    name: secret-reader
rules:
- apiGroups: [""]
    resources: ["secrets", "configmaps"]
    verbs: ["get", "list"]
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    name: read-secrets
    namespace: default
subjects:
- kind: ServiceAccount
    name: my-sa
    namespace: default
roleRef:
    kind: Role
    name: secret-reader
    apiGroup: rbac.authorization.k8s.io
```
This allows the `my-sa` ServiceAccount to read Secrets and ConfigMaps in the default namespace.
Benefits: RBAC enforces the principle of least privilege, reducing the risk of data leaks or misuse.

**61. How does the Kubernetes scheduler decide where to place a Pod?**

The Kubernetes scheduler is responsible for assigning Pods to nodes in a cluster based on a multi-step process:

- **Filtering:** The scheduler filters out nodes that don’t meet the Pod’s requirements, such as:
    - Sufficient CPU, memory, and other resource availability (based on requests).
    - Matching node selectors, taints/tolerations, and affinity/anti-affinity rules.
    - Hardware or software constraints (e.g., GPU availability).
- **Scoring:** The scheduler assigns a score (0-10) to each remaining node based on predefined or custom scheduling policies, such as:
    - Resource utilization (e.g., least requested resources).
    - Node affinity or anti-affinity preferences.
    - Pod inter-affinity (e.g., placing Pods near others).
    - Taint/toleration alignment.
- **Selection:** The node with the highest score is chosen. If multiple nodes tie, one is selected randomly.
- **Binding:** The scheduler updates the Pod’s spec with the selected node’s name via the Kubernetes API server.

The scheduler can be customized with custom scheduling policies or replaced entirely with a custom scheduler.

**62. What are resource requests and limits in Kubernetes?**

- **Resource Requests:** These specify the minimum amount of CPU and memory a Pod needs to run. Kubernetes uses requests to determine where to schedule a Pod by ensuring the node has enough available resources. For example:
    ```yaml
    resources:
        requests:
            memory: "256Mi"
            cpu: "200m" # 200 milliCPU (0.2 CPU cores)
    ```
- **Resource Limits:** These define the maximum amount of CPU and memory a Pod can use. If a Pod exceeds its limits, it may be throttled (CPU) or terminated (memory). For example:
    ```yaml
    resources:
        limits:
            memory: "512Mi"
            cpu: "500m"
    ```

**Key Difference:** Requests are about scheduling (guaranteed minimum), while limits are about enforcement (maximum allowed).

**63. What happens when a Pod exceeds its resource limits?**

- **CPU:** If a Pod exceeds its CPU limit, it is throttled. The container’s CPU usage is capped, slowing down its execution, but it continues running.
- **Memory:** If a Pod exceeds its memory limit, it is terminated by the kubelet with an "OOM" (Out of Memory) error. The Pod may restart depending on its restart policy (e.g., in a Deployment, it will be rescheduled).
- **Eviction:** If the node itself runs out of resources due to Pods exceeding limits, Kubernetes may evict lower-priority Pods to free up resources.

**64. What is a taint and toleration, and how are they used?**

- **Taint:** A taint is a key-value pair with an effect applied to a node to repel Pods from being scheduled on it unless they explicitly tolerate the taint. Effects include:
    - **NoSchedule:** Pods won’t be scheduled unless they tolerate the taint.
    - **PreferNoSchedule:** Kubernetes avoids scheduling Pods, but it’s not strict.
    - **NoExecute:** Existing Pods are evicted if they don’t tolerate the taint.
    Example: `kubectl taint nodes node1 key1=value1:NoSchedule`
- **Toleration:** A toleration is a Pod specification that allows it to be scheduled on a tainted node. It matches the taint’s key, value, and effect.
    ```yaml
    tolerations:
    - key: "key1"
        operator: "Equal"
        value: "value1"
        effect: "NoSchedule"
    ```

**Use Case:** Taints and tolerations are used to reserve nodes for specific workloads (e.g., dedicated nodes for GPU-intensive tasks) or to mark nodes as unavailable for general use.

**65. What is a node selector, and how does it work?**

- **Node Selector:** A node selector is a simple key-value pair in a Pod’s spec that directs the scheduler to place the Pod on a node with matching labels.
- **How It Works:** Nodes are labeled (e.g., `kubectl label nodes node1 disktype=ssd`), and the Pod specifies the required labels:
    ```yaml
    spec:
        nodeSelector:
            disktype: ssd
    ```

**Limitation:** Node selectors are basic and don’t support complex logic (e.g., “not” or “or” conditions). For advanced scheduling, use affinity/anti-affinity rules.

**66. How do you prioritize Pods in Kubernetes?**

Kubernetes prioritizes Pods using PriorityClasses. Higher-priority Pods are scheduled before lower-priority ones and can evict lower-priority Pods if resources are scarce.

**Steps:**

1. Define a PriorityClass with a priority value (higher value = higher priority).
2. Assign the PriorityClass to a Pod.

**Example:**
```yaml
apiVersion: scheduling.k8s.io/v1
kind: PriorityClass
metadata:
    name: high-priority
value: 1000000
---
apiVersion: v1
kind: Pod
metadata:
    name: important-pod
spec:
    priorityClassName: high-priority
    containers:
    - name: app
        image: nginx
```

**Use Case:** Ensure critical workloads (e.g., production apps) take precedence over non-critical ones (e.g., batch jobs).

**67. What is a PriorityClass, and how do you configure it?**

- **PriorityClass:** A PriorityClass is a Kubernetes resource that assigns a priority value to Pods, influencing scheduling and eviction decisions.

**Configuration:**

1. Create a PriorityClass YAML:
    ```yaml
    apiVersion: scheduling.k8s.io/v1
    kind: PriorityClass
    metadata:
        name: high-priority
    value: 1000000 # Integer value; higher means higher priority
    globalDefault: false # Optional: Makes it the default for Pods without a PriorityClass
    description: "High-priority workloads"
    ```
2. Apply it: `kubectl apply -f priorityclass.yaml`
3. Reference it in a Pod spec using `priorityClassName`.

**Notes:** System-critical Pods (e.g., kube-system) often use high default priorities (e.g., 2 billion).

**68. How does Kubernetes handle Pod eviction?**

Kubernetes evicts Pods when a node runs low on resources (e.g., memory, disk):

- **Node Pressure:** The kubelet monitors resource usage. If thresholds are exceeded (e.g., memory.available < 100Mi), it triggers eviction.
- **Priority-Based Eviction:** Pods are ranked by:
    - Priority (from PriorityClass; lower priority evicted first).
    - QoS class (BestEffort < Burstable < Guaranteed).
    - Resource usage relative to requests (Pods exceeding requests evicted first).

**Process:** The kubelet terminates the Pod, and the scheduler reschedules it if managed by a controller (e.g., Deployment).

**Use Case:** Prevents node failure by freeing resources for critical workloads.

**69. What is a Quality of Service (QoS) class, and how is it assigned to Pods?**

- **QoS Classes:** Kubernetes assigns one of three QoS classes to Pods based on resource requests and limits:
    - **Guaranteed:** Requests equal limits for all containers (highest priority).
        ```yaml
        resources:
            requests:
                cpu: "200m"
                memory: "256Mi"
            limits:
                cpu: "200m"
                memory: "256Mi"
        ```
    - **Burstable:** Requests are set but less than limits (medium priority).
        ```yaml
        resources:
            requests:
                cpu: "200m"
            limits:
                cpu: "500m"
        ```
    - **BestEffort:** No requests or limits (lowest priority).
        ```yaml
        resources: {}
        ```

**Assignment:** Automatically determined by Kubernetes based on the Pod’s resource spec. QoS affects eviction order during resource pressure.

**70. How do you troubleshoot a Pod that is stuck in a Pending state?**

A Pod in a Pending state hasn’t been scheduled. Steps to troubleshoot:

1. **Check Pod Status:** `kubectl describe pod <pod-name>`
     - Look for events (e.g., "Insufficient CPU," "FailedScheduling").
2. **Resource Availability:** Verify nodes have enough CPU/memory: `kubectl describe nodes`.
3. **Scheduling Constraints:**
     - Check for mismatched node selectors, taints, or affinity rules.
     - Example: `kubectl get nodes -o wide` and compare with Pod spec.
4. **Taints/Tolerations:** Ensure the Pod tolerates node taints: `kubectl describe node <node-name>`.
5. **Cluster Issues:** Check scheduler health: `kubectl get pods -n kube-system`.

**Fix:** Adjust resources, add tolerations, or scale the cluster (e.g., add nodes).

**Common Causes:** Insufficient resources, taints, or misconfigured node selectors.

**71. What is Horizontal Pod Autoscaling (HPA), and how does it work?**
Answer:
Horizontal Pod Autoscaling (HPA) is a Kubernetes feature that automatically scales the number of Pod replicas in a Deployment, ReplicaSet, or StatefulSet based on observed metrics, such as CPU or memory utilization. It helps ensure that an application can handle varying workloads by increasing or decreasing the number of Pods dynamically.

How it works:
- HPA relies on the Metrics Server (or a custom metrics adapter) to collect resource usage data from Pods.
- It compares the current metric values (e.g., CPU usage) against a target threshold defined in the HPA configuration.
- If the metric exceeds the target (e.g., CPU usage > 80%), HPA increases the number of Pods. If it falls below the target, HPA decreases the number of Pods, within the defined minReplicas and maxReplicas limits.
- The scaling decision is made periodically (typically every 15-30 seconds, depending on the cluster configuration).

Example:
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
                averageUtilization: 80
```
In this example, HPA scales the `my-deployment` Deployment between 2 and 10 replicas based on 80% CPU utilization.

**72. How do you configure HPA based on custom metrics?**
Answer:
To configure HPA based on custom metrics (e.g., requests per second, queue length), you need to integrate a custom metrics provider (like Prometheus with the Prometheus Adapter) with Kubernetes. This allows HPA to scale based on application-specific metrics instead of just CPU or memory.

Steps to configure HPA with custom metrics:
1. Deploy a Metrics Source: Install and configure a monitoring system like Prometheus to collect custom metrics from your application.
2. Install a Custom Metrics Adapter: Deploy a custom metrics adapter (e.g., Prometheus Adapter) to expose these metrics to the Kubernetes API.
3. Define the Custom Metric in HPA: Update the HPA configuration to reference the custom metric.

Example:
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
    name: custom-hpa
spec:
    scaleTargetRef:
        apiVersion: apps/v1
        kind: Deployment
        name: my-app
    minReplicas: 1
    maxReplicas: 10
    metrics:
    - type: Pods
        pods:
            metric:
                name: http_requests_per_second
            target:
                type: AverageValue
                averageValue: "100"
```
Here, HPA scales the `my-app` Deployment based on the custom metric `http_requests_per_second`, targeting an average of 100 requests per second per Pod.

Requirements:
- The cluster must have the `custom.metrics.k8s.io` API enabled.
- Your application must expose the custom metric in a format the metrics adapter can scrape.

**73. What is Vertical Pod Autoscaling (VPA), and when is it useful?**
Answer:
Vertical Pod Autoscaling (VPA) adjusts the resource requests and limits (CPU and memory) of individual Pods rather than changing the number of Pods. It analyzes the historical and current resource usage of a Pod and recommends or applies optimal resource settings.

How it works:
- VPA runs as a custom controller in the cluster.
- It monitors Pod resource usage and compares it to the configured requests/limits.
- It can operate in three modes:
    - Off: Provides recommendations only (no changes applied).
    - Auto: Automatically updates Pod resource requests/limits and restarts Pods if needed.
    - Initial: Sets resource requests at Pod creation time.

When it’s useful:
- When you’re unsure of the optimal resource requests/limits for your application.
- For workloads with stable traffic but variable resource needs (e.g., memory-intensive batch jobs).
- In scenarios where horizontal scaling isn’t feasible (e.g., stateful applications that don’t scale well with more replicas).
- To optimize resource utilization and reduce over-provisioning.

Note: VPA is not part of core Kubernetes and requires the VPA custom resource and controller to be installed.

**74. How does Kubernetes ensure high availability of applications?**
Answer:
Kubernetes ensures high availability (HA) of applications through several mechanisms:
- Replication: Deployments and ReplicaSets maintain a desired number of Pod replicas. If a Pod fails, Kubernetes automatically recreates it.
- Self-Healing: The kubelet and controllers monitor Pod health and restart or reschedule Pods on failure.
- Node Failure Handling: If a node fails, the scheduler moves Pods to healthy nodes.
- Load Balancing: Services distribute traffic across multiple Pods using kube-proxy or external load balancers.
- Health Checks: Liveness and readiness probes ensure only healthy Pods serve traffic.
- Multi-Node Control Plane: In HA clusters, multiple master nodes (with replicated etcd, API server, etc.) prevent single points of failure.
- Pod Anti-Affinity: Ensures Pods are spread across nodes to avoid concentration on a single point of failure.

Example: A Deployment with 3 replicas spread across nodes, combined with a LoadBalancer service, ensures HA by tolerating node or Pod failures.

**75. What is a rolling update, and how do you perform one in Kubernetes?**
Answer:
A rolling update is a strategy in Kubernetes to update an application (e.g., new container image or configuration) with zero downtime by incrementally replacing old Pods with new ones.

How it works:
- Kubernetes updates Pods in a controlled manner, ensuring a minimum number of Pods remain available during the update.
- It uses the `maxUnavailable` and `maxSurge` settings to control the process.

How to perform a rolling update:
1. Update the Deployment’s container image or configuration in the YAML file or via `kubectl`.
2. Apply the changes:
     ```bash
     kubectl apply -f deployment.yaml
     ```
     OR
     ```bash
     kubectl set image deployment/my-app my-container=new-image:tag
     ```
3. Monitor the rollout:
     ```bash
     kubectl rollout status deployment/my-app
     ```

Example Deployment with Rolling Update:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: my-app
spec:
    replicas: 3
    strategy:
        type: RollingUpdate
        rollingUpdate:
            maxUnavailable: 1
            maxSurge: 1
    template:
        spec:
            containers:
            - name: my-container
                image: my-image:v2
```
- `maxUnavailable: 1` ensures at least 2 Pods are available.
- `maxSurge: 1` allows 1 extra Pod during the update.

**76. What is the difference between a rolling update and a recreate strategy?**
Answer:
Rolling Update:
- Updates Pods incrementally, maintaining availability (zero downtime).
- Old Pods are terminated and replaced with new ones gradually.
- Controlled by `maxUnavailable` and `maxSurge`.
- Default strategy for Deployments.
- Suitable for stateless applications requiring continuous availability.

Recreate Strategy:
- Terminates all existing Pods before creating new ones, causing downtime.
- Defined with `strategy.type: Recreate` in the Deployment.
- Simpler but less flexible; no overlap between old and new Pods.
- Useful for applications that cannot run multiple versions simultaneously (e.g., database migrations).

Example (Recreate):
```yaml
spec:
    strategy:
        type: Recreate
```

**77. How does Kubernetes handle failover for Pods?**
Answer:
Kubernetes handles Pod failover through:
- Replication Controller/ReplicaSet: Ensures the desired number of Pods is always running. If a Pod fails, a new one is created.
- Node Failure Detection: The kubelet on each node reports health to the control plane. If a node becomes unreachable, the scheduler reschedules affected Pods to healthy nodes.
- Liveness Probes: Detects unhealthy Pods and restarts them automatically.
- Pod Disruption Budget (PDB): Ensures a minimum number of Pods remain available during voluntary disruptions (e.g., node draining).
- Anti-Affinity Rules: Spreads Pods across nodes to minimize the impact of a single node failure.

Example: If a node crashes, the ReplicaSet controller detects the missing Pods and schedules replacements on other nodes.

**78. What is the role of liveness and readiness probes?**
Answer:
Liveness Probe:
- Determines if a Pod is healthy and running correctly.
- If the probe fails, Kubernetes restarts the Pod.
- Use case: Detect crashes, deadlocks, or unresponsive applications.

Readiness Probe:
- Determines if a Pod is ready to serve traffic.
- If the probe fails, the Pod is removed from the Service’s endpoints (traffic stops routing to it).
- Use case: Ensure Pods are fully initialized (e.g., app startup, database connection).

Key Difference: Liveness affects Pod restarts; readiness affects traffic routing.

**79. How do you configure liveness and readiness probes for a Pod?**
Answer:
Probes are defined in the Pod spec using HTTP, TCP, or command-based checks.

Example:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: my-pod
spec:
    containers:
    - name: my-container
        image: my-image
        livenessProbe:
            httpGet:
                path: /healthz
                port: 8080
            initialDelaySeconds: 5
            periodSeconds: 10
        readinessProbe:
            tcpSocket:
                port: 8080
            initialDelaySeconds: 5
            periodSeconds: 5
```
- Liveness Probe: Checks `/healthz` endpoint every 10 seconds, starting after 5 seconds.
- Readiness Probe: Checks TCP port 8080 every 5 seconds, starting after 5 seconds.

Probe Types:
- `httpGet`: HTTP request to an endpoint.
- `tcpSocket`: Tests if a port is open.
- `exec`: Runs a command inside the container (success if exit code is 0).

**80. What happens if a readiness probe fails?**
Answer:
If a readiness probe fails:
- The Pod is marked as "Not Ready."
- Kubernetes removes the Pod from the Service’s endpoints list, stopping traffic to it.
- The Pod continues running (unlike a liveness probe failure, which triggers a restart).
- Once the probe succeeds again, the Pod is marked "Ready" and rejoins the Service endpoints.

Use Case: Prevents traffic from reaching a Pod that’s still starting up or temporarily unhealthy (e.g., during a slow database connection).

81. **What is Role-Based Access Control (RBAC) in Kubernetes?**

Role-Based Access Control (RBAC) is a method of regulating access to Kubernetes resources based on the roles assigned to users, groups, or service accounts within a cluster. RBAC allows administrators to define permissions (e.g., read, write, delete) for specific resources (e.g., Pods, Deployments, ConfigMaps) and scope them to a namespace or the entire cluster. It ensures that only authorized entities can perform specific actions, enhancing security by adhering to the principle of least privilege.

RBAC in Kubernetes uses four key API objects:
- **Role:** Defines permissions within a specific namespace.
- **ClusterRole:** Defines permissions cluster-wide.
- **RoleBinding:** Assigns a Role to a user, group, or service account within a namespace.
- **ClusterRoleBinding:** Assigns a ClusterRole to a user, group, or service account across the entire cluster.

82. **How do you create a Role and a RoleBinding?**

To create a Role and a RoleBinding, you define them using YAML manifests and apply them with kubectl.

**Example: Creating a Role**

This Role allows a user to get and list Pods in the development namespace:

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
    namespace: development
    name: pod-reader
rules:
- apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list"]
```

**Example: Creating a RoleBinding**

This RoleBinding binds the pod-reader Role to a user named jane in the development namespace:

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    namespace: development
    name: pod-reader-binding
subjects:
- kind: User
    name: jane
    apiGroup: rbac.authorization.k8s.io
roleRef:
    kind: Role
    name: pod-reader
    apiGroup: rbac.authorization.k8s.io
```

**Applying the Configuration**

Run the following commands to apply these manifests:

```bash
kubectl apply -f role.yaml
kubectl apply -f rolebinding.yaml
```

Now, jane can only get and list Pods in the development namespace.

83. **What is the difference between a ClusterRole and a Role?**

- **Role:**
    - Scoped to a specific namespace.
    - Defines permissions for resources within that namespace only.
    - Used for fine-grained access control in a single namespace.
    - Example: A Role might allow managing Pods in the dev namespace but not in prod.

- **ClusterRole:**
    - Scoped to the entire cluster (cluster-wide).
    - Defines permissions for resources across all namespaces or for cluster-scoped resources (e.g., Nodes, PersistentVolumes).
    - Used for broader administrative tasks or when namespace-specific scoping isn’t required.
    - Example: A ClusterRole might allow viewing all Nodes in the cluster.

In short, a Role is namespace-specific, while a ClusterRole applies globally. A ClusterRole can also be bound to a namespace using a RoleBinding if needed.

84. **How do you secure the Kubernetes API server?**

Securing the Kubernetes API server is critical since it’s the central control point of the cluster. Here are key steps:

- **Enable Authentication:**
    - Use strong authentication mechanisms like client certificates, OpenID Connect (OIDC), or tokens.
    - Avoid using anonymous access (`--anonymous-auth=false`).

- **Enable Authorization:**
    - Use RBAC to control access to the API server.
    - Define granular Roles and ClusterRoles to limit permissions.

- **Enable TLS:**
    - Ensure all API server communications use HTTPS with valid certificates (`--tls-cert-file` and `--tls-private-key-file`).
    - Avoid insecure ports (`--insecure-port=0`).

- **Restrict Access:**
    - Limit network access to the API server using firewalls or network policies.
    - Use `--bind-address` to bind the API server to a specific IP (e.g., not `0.0.0.0`).

- **Disable Unnecessary Features:**
    - Turn off unused admission controllers or features that could be exploited.

- **Audit Logging:**
    - Enable audit logs (`--audit-log-path`) to monitor API server activity.

- **Use kubeconfig Securely:**
    - Protect kubeconfig files with proper permissions and avoid sharing credentials.

**Example: Start the API server with secure flags:**

```bash
kube-apiserver --tls-cert-file=/path/to/cert --tls-private-key-file=/path/to/key --authorization-mode=RBAC
```

85. **What is a ServiceAccount, and how is it used?**

A ServiceAccount is a Kubernetes object that provides an identity for processes running in Pods to interact with the Kubernetes API. Unlike regular user accounts (for humans), ServiceAccounts are designed for applications or workloads.

**How It’s Used:**
- When a Pod is created, it can be assigned a ServiceAccount (via the `serviceAccountName` field in the Pod spec).
- The ServiceAccount provides a token (stored as a Secret) that the Pod uses to authenticate to the API server.
- RBAC policies (Roles/ClusterRoles) can be bound to a ServiceAccount to define what actions it can perform.

**Example:**

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
    name: my-service-account
    namespace: default
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
    name: my-role-binding
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

In this example, the `my-service-account` ServiceAccount is granted permissions via the `pod-reader` Role.

86. **How does Kubernetes handle authentication and authorization?**

- **Authentication:**
    - Determines who is accessing the cluster.
    - Kubernetes supports multiple methods:
        - **Client Certificates:** Uses X.509 certificates for identity.
        - **Bearer Tokens:** Tokens (e.g., from ServiceAccounts or external providers).
        - **OpenID Connect (OIDC):** Integrates with external identity providers (e.g., Google, Azure AD).
        - **Webhooks:** Delegates authentication to an external service.
    - Configured via API server flags like `--client-ca-file` or `--oidc-issuer-url`.

- **Authorization:**
    - Determines what an authenticated user can do.
    - Kubernetes supports multiple authorization modes (configured via `--authorization-mode`):
        - **RBAC:** Most common; uses Roles and ClusterRoles.
        - **ABAC:** Attribute-based access control (less common, file-based).
        - **Webhook:** Delegates authorization to an external service.
        - **Node:** Special mode for kubelet authorization.
    - RBAC is the default and recommended mode.

**Process:**
1. A request hits the API server.
2. Authentication verifies the user’s identity (e.g., via token or certificate).
3. Authorization checks if the user has permission for the requested action (e.g., via RBAC rules).
4. If both pass, the request is processed.

87. **What are Pod Security Policies (PSPs), and how do they work?**

Pod Security Policies (PSPs) are a cluster-level resource (deprecated in Kubernetes 1.21, replaced by Pod Security Standards in 1.25) that define security constraints for Pods. PSPs enforce rules on what Pods can do, such as restricting privileged containers or root access.

**How They Work:**
- A PSP is applied to a cluster and enforced by the admission controller.
- When a Pod is created, the PSP validates it against defined rules (e.g., no privileged mode, specific volume types).
- If the Pod violates the policy, it’s rejected.

**Example:**

```yaml
apiVersion: policy/v1beta1
kind: PodSecurityPolicy
metadata:
    name: restricted-psp
spec:
    privileged: false
    runAsUser:
        rule: MustRunAsNonRoot
    seLinux:
        rule: RunAsAny
    fsGroup:
        rule: RunAsAny
    supplementalGroups:
        rule: RunAsAny
    volumes:
    - 'configMap'
    - 'secret'
```

This PSP prevents privileged Pods and enforces running as a non-root user.

**Note:** PSPs are replaced by Pod Security Admission (PSA) in newer versions.

88. **How do you restrict network traffic using Network Policies?**

Network Policies in Kubernetes control traffic flow between Pods by defining rules for ingress (incoming) and egress (outgoing) traffic. They require a CNI plugin that supports Network Policies (e.g., Calico, Cilium).

**Example:**

This Network Policy allows only Pods with the label `app: frontend` to send traffic to Pods with `app: backend` in the default namespace:

```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: backend-policy
    namespace: default
spec:
    podSelector:
        matchLabels:
            app: backend
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

**Key Components:**
- **podSelector:** Targets Pods to apply the policy to.
- **ingress/egress:** Defines allowed incoming/outgoing traffic.
- **from/to:** Specifies sources/destinations (e.g., Pod labels, namespaces, IP ranges).

89. **What are some best practices for securing a Kubernetes cluster?**

- **Use RBAC:** Grant least-privilege access with Roles and ClusterRoles.
- **Enable TLS:** Secure all communications with certificates.
- **Restrict API Access:** Limit who can reach the API server (e.g., via network policies or firewalls).
- **Use Namespaces:** Isolate workloads and apply scoped policies.
- **Secure Secrets:** Use Secrets instead of plaintext, and encrypt them at rest (`--encryption-provider-config`).
- **Limit Privileged Containers:** Avoid `privileged: true` and enforce with PSPs or PSA.
- **Enable Audit Logging:** Monitor and review cluster activity.
- **Keep Cluster Updated:** Apply security patches and upgrades promptly.
- **Use Network Policies:** Restrict Pod-to-Pod communication.
- **Validate Images:** Use trusted registries and scan container images for vulnerabilities.

90. **How do you audit and monitor security events in Kubernetes?**

- **Enable Audit Logging:**
    - Configure the API server with `--audit-log-path` and `--audit-policy-file`.
    - **Example audit policy:**

        ```yaml
        apiVersion: audit.k8s.io/v1
        kind: Policy
        rules:
        - level: Metadata
            resources:
            - group: ""
                resources: ["pods"]
        ```

    - Logs are written to the specified file and can be ingested into tools like ELK or Fluentd.

- **Monitoring Tools:**
    - **Prometheus:** Collects metrics (e.g., via kube-state-metrics) to monitor cluster health and anomalies.
    - **Grafana:** Visualizes metrics and alerts on suspicious activity.
    - **Falco:** Runtime security tool that detects abnormal behavior (e.g., unauthorized container actions).
    - **Kube-bench:** Checks cluster security against CIS benchmarks.

**Steps:**
1. Enable audit logging on the API server.
2. Set up a monitoring stack (e.g., Prometheus + Grafana).
3. Configure alerts for security events (e.g., unauthorized access attempts).
4. Regularly review logs and metrics for anomalies.

91. **How do you check the status of Pods in a cluster?**

To check the status of Pods in a Kubernetes cluster, you use the `kubectl` command-line tool. The most common command is:

```bash
kubectl get pods
```

**Details:** This command lists all Pods in the default namespace, showing their names, status (e.g., Running, Pending, CrashLoopBackOff), restarts, and age.

**With Namespace:** To check Pods in a specific namespace, add the `-n` flag:

```bash
kubectl get pods -n <namespace>
```

**Cluster-Wide:** To see Pods across all namespaces, use:

```bash
kubectl get pods --all-namespaces
```

**Additional Info:** For more details (e.g., IP, node), use the `-o wide` option:

```bash
kubectl get pods -o wide
```

This gives you a quick overview of the health and status of Pods in the cluster.

92. **What is the purpose of the kubectl describe command?**

The `kubectl describe` command provides detailed information about a specific Kubernetes resource, such as Pods, Nodes, Services, or Deployments. Its purpose is to help you inspect the configuration, status, and events associated with a resource for troubleshooting or verification.

**Syntax:**

```bash
kubectl describe <resource-type> <resource-name>
```

**Example:** `kubectl describe pod my-pod`

**Output:** It includes:
- Metadata (name, namespace, labels, annotations).
- Status (e.g., Running, Pending, CrashLoopBackOff).
- Conditions (e.g., PodScheduled, Ready).
- Events (e.g., "Failed to pull image," "Pod started").
- Resource-specific details (e.g., container specs for a Pod).

**Use Case:** It’s particularly useful for debugging issues by showing why a resource is in a certain state (e.g., why a Pod isn’t running).

93. **How do you view logs of a Pod using kubectl?**

To view logs of a Pod, use the `kubectl logs` command:

```bash
kubectl logs <pod-name>
```

**Details:** This retrieves logs from the container(s) in the specified Pod. If the Pod has only one container, it fetches that container’s logs automatically.

**With Namespace:** Specify the namespace if needed:

```bash
kubectl logs <pod-name> -n <namespace>
```

**Multi-Container Pods:** If the Pod has multiple containers, specify the container name:

```bash
kubectl logs <pod-name> -c <container-name>
```

**Live Logs:** To stream logs in real-time (like `tail -f`), use:

```bash
kubectl logs -f <pod-name>
```

**Previous Logs:** To see logs from a crashed container, use:

```bash
kubectl logs <pod-name> --previous
```

This is a key tool for debugging application issues within Pods.

94. **What does the kubectl get events command do?**

The `kubectl get events` command lists events in a Kubernetes cluster, providing a chronological log of activities related to resources (e.g., Pods, Nodes, Services).

**Syntax:**

```bash
kubectl get events
```

**Output:** It shows:
- Timestamp of the event.
- Type (Normal or Warning).
- Reason (e.g., "Scheduled," "FailedMount").
- Object (e.g., Pod/my-pod).
- Message (e.g., "Successfully pulled image").

**With Namespace:** By default, it shows events in the current namespace. Use `-n <namespace>` for a specific namespace or `--all-namespaces` for all:

```bash
kubectl get events --all-namespaces
```

**Purpose:** It’s useful for troubleshooting issues like scheduling failures, resource errors, or warnings in the cluster.

95. **How do you debug a Pod that is crashing?**

Debugging a crashing Pod involves several steps to identify and resolve the issue:

**Check Pod Status:**

```bash
kubectl get pods
```

Look for statuses like CrashLoopBackOff or Error.

**Describe the Pod:**

```bash
kubectl describe pod <pod-name>
```

Check the "Events" section for clues (e.g., image pull errors, resource limits exceeded).

**View Logs:**

```bash
kubectl logs <pod-name>
```

If the Pod has crashed, use `--previous` to see logs from the last run:

```bash
kubectl logs <pod-name> --previous
```

**Inspect Configuration:**

Verify the Pod’s YAML file for misconfigurations (e.g., wrong image name, port conflicts). Use `kubectl get pod <pod-name> -o yaml` to export and review.

**Exec into the Pod (if it’s running briefly):**

```bash
kubectl exec -it <pod-name> -- /bin/sh
```

Run commands inside to check the application.

**Check Resource Limits:**

Ensure CPU/memory requests and limits are appropriate.

**Look at Cluster Health:**

Check node status: `kubectl get nodes`. Verify resource availability: `kubectl describe node <node-name>`.

**Fix and Test:**

Update the Pod spec (e.g., fix image, adjust resources) and redeploy.

96. **What are some common reasons for a Pod to be in a CrashLoopBackOff state?**

CrashLoopBackOff means a container in the Pod is repeatedly crashing and Kubernetes is backing off before restarting it. Common reasons include:

- **Application Errors:** The application exits with a non-zero exit code due to a bug or misconfiguration. Check logs with `kubectl logs <pod-name>`.
- **Misconfigured Image:** Wrong image name, tag, or registry in the Pod spec. Image pull failures (e.g., authentication issues).
- **Resource Limits:** Container exceeds CPU/memory limits and gets killed by Kubernetes.
- **Missing Dependencies:** Application requires a file, service, or environment variable that’s unavailable.
- **Port Conflicts:** Container tries to bind to a port already in use on the node.
- **Liveness Probe Failure:** A misconfigured liveness probe causes Kubernetes to kill and restart the container.
- **Startup Issues:** Command or arguments in the Pod spec are incorrect, causing the container to fail immediately.

97. **How do you troubleshoot a service that is not responding?**

If a Kubernetes Service isn’t responding, follow these steps:

**Verify Service Status:**

```bash
kubectl get service <service-name>
```

Check the External IP (for LoadBalancer) or ClusterIP.

**Check Endpoints:**

```bash
kubectl get endpoints <service-name>
```

Ensure there are valid Pod IPs listed. If empty, the Service isn’t targeting any Pods.

**Inspect Pod Health:**

```bash
kubectl get pods -l <label-selector>
```

Verify the Pods backing the Service are Running and Ready.

**Describe the Service:**

```bash
kubectl describe service <service-name>
```

Look for misconfigured ports or selectors.

**Test Connectivity:**

Use `kubectl port-forward` to test locally:

```bash
kubectl port-forward service/<service-name> 8080:80
```

Exec into another Pod and use `curl` to test the Service’s ClusterIP.

**Check Networking:**

Ensure kube-proxy is running: `kubectl get pods -n kube-system`. Verify Network Policies aren’t blocking traffic.

**Review Logs:**

Check Pod logs (`kubectl logs <pod-name>`) for application errors.

**DNS Issues (if applicable):**

Test DNS resolution: `kubectl exec -it <pod-name> -- nslookup <service-name>`.

98. **What is Prometheus, and how is it used with Kubernetes?**

Prometheus is an open-source monitoring and alerting toolkit designed for reliability and scalability. It’s widely used with Kubernetes to monitor cluster and application metrics.

**How It Works:**
- Prometheus scrapes metrics from configured endpoints (e.g., Kubernetes API, Pods) at regular intervals.
- It stores time-series data and provides a query language (PromQL) to analyze it.

**Use in Kubernetes:**
- **Cluster Monitoring:** Tracks resource usage (CPU, memory), node health, and Pod status.
- **Application Monitoring:** Exposes custom metrics from apps via annotations or service monitors.
- **Integration:** Deployed with tools like the Prometheus Operator or Helm charts (e.g., kube-prometheus-stack).
- **Alerts:** Configured with Alertmanager to notify on issues (e.g., high latency, Pod failures).

**Key Components:**
- Prometheus Server (scrapes and stores data).
- Exporters (e.g., Node Exporter for node metrics).
- Grafana (for visualization).

99. **How do you monitor resource usage in a Kubernetes cluster?**

To monitor resource usage in Kubernetes:

**kubectl Commands:**
- `kubectl top nodes`: Shows CPU/memory usage per node.
- `kubectl top pods`: Displays resource usage per Pod (requires metrics server).

**Metrics Server:**

Deploy the Metrics Server to collect resource metrics:

```bash
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml
```

Enables `kubectl top` functionality.

**Prometheus and Grafana:**

Deploy Prometheus to scrape metrics (e.g., CPU, memory, network). Use Grafana dashboards to visualize cluster and Pod resource usage.

**Custom Metrics:**

Configure HPA or VPA with custom metrics via Prometheus Adapter.

**Kubernetes Dashboard:**

Provides a UI to view resource usage per Pod, Node, or Namespace.

**Third-Party Tools:**

Datadog, New Relic, or Sysdig for advanced monitoring.

100. **What is the role of the Kubernetes Dashboard, and how do you access it?**

**Role:** The Kubernetes Dashboard is a web-based UI for managing and monitoring a Kubernetes cluster. It allows you to:
- View Pods, Services, Deployments, and other resources.
- Check resource usage (CPU, memory).
- Create, edit, or delete resources.
- View logs and events.

**Access:**

**Deploy the Dashboard:**

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.7.0/aio/deploy/recommended.yaml
```

**Start Proxy:**

```bash
kubectl proxy
```

**Open in Browser:**

Access it at: `http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/`.

**Authentication:**

Create a ServiceAccount and ClusterRoleBinding for access. Get the token:

```bash
kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernetes-dashboard get secret | grep admin-user | awk '{print $1}')
```

Use the token to log in.

**Note:** It’s optional and often supplemented by tools like Grafana for advanced monitoring.

101. **How do you integrate Kubernetes with a CI/CD pipeline?**
Kubernetes integrates with CI/CD pipelines by automating the build, test, and deployment of applications into clusters. Here’s how it works:
- **Source Code Management:** Use a version control system (e.g., Git) to store application code and Kubernetes manifests.
- **CI Stage:** Use a CI tool (e.g., Jenkins, GitHub Actions, CircleCI) to:
    - Build the application (e.g., compile code, create Docker images).
    - Run tests (unit, integration).
    - Push the Docker image to a container registry (e.g., Docker Hub, ECR, GCR).
- **CD Stage:** Use a CD tool or script to:
    - Update Kubernetes manifests (e.g., change image tags in a Deployment YAML).
    - Apply the manifests to the Kubernetes cluster using `kubectl apply` or a GitOps tool.
    - Validate deployment success (e.g., check Pod status, run health checks).
- **Tools:** Common tools include Jenkins (with Kubernetes plugin), GitLab CI/CD, or ArgoCD for continuous deployment.
- **Best Practices:** Use rolling updates for zero-downtime deployments, incorporate approval gates for production, and monitor with tools like Prometheus.
- **Example:** A GitHub Action builds an image, pushes it to a registry, and uses `kubectl` to deploy it to a Kubernetes cluster.

102. **What is Helm, and how does it simplify Kubernetes deployments?**
Helm is a package manager for Kubernetes that simplifies the deployment and management of applications.
- **Definition:** Helm allows you to define, install, and upgrade complex Kubernetes applications using pre-configured packages called "charts."
- **Simplification:**
    - **Templating:** Helm uses templates to generate Kubernetes manifests dynamically, reducing manual YAML editing.
    - **Reusability:** Charts can be reused across environments or projects with customizable values.
    - **Dependency Management:** Helm manages dependencies between components (e.g., an app needing a database).
    - **Versioning:** It tracks releases, making rollbacks and upgrades easier.
- **Components:**
    - **Helm CLI:** The command-line tool to interact with charts.
    - **Charts:** Bundles of Kubernetes resources (e.g., Deployments, Services) with a `values.yaml` file for configuration.
    - **Tiller (older versions, pre-Helm 3):** Server-side component (removed in Helm 3 for better security).
- **Example:** Instead of managing 10 YAML files for an app, you install it with `helm install my-app ./chart`.

103. **What are Helm charts, and how do you create one?**
Helm charts are packages that contain all the Kubernetes resource definitions needed to run an application, along with metadata and templates.
- **Structure:**
    - `Chart.yaml`: Metadata (name, version, description).
    - `values.yaml`: Default configuration values (e.g., replica count, image tag).
    - `templates/`: Directory with templated Kubernetes manifests (e.g., deployment.yaml).
    - `charts/`: Optional subdirectory for dependent charts.
- **Creating a Chart:**
    - Run `helm create my-chart` to generate a skeleton chart.
    - Edit `values.yaml` to define customizable parameters (e.g., `replicaCount: 2`).
    - Add Kubernetes manifests in `templates/` (e.g., Deployment, Service) using Helm’s Go templating syntax (e.g., `{{ .Values.replicaCount }}`).
    - Test with `helm lint my-chart` to validate syntax.
    - Package it with `helm package my-chart` and deploy with `helm install my-release my-chart`.
- **Example:** A chart for a web app might include a Deployment, Service, and Ingress, parameterized by `values.yaml`.

104. **What is GitOps, and how does it work with Kubernetes?**
GitOps is a methodology for managing infrastructure and applications using Git as the single source of truth.
- **Definition:** In GitOps, all configuration (Kubernetes manifests, Helm charts) is stored in a Git repository, and changes are applied to the cluster via automated tools.
- **How It Works with Kubernetes:**
    - **Declarative Configuration:** Define the desired state of the cluster (e.g., Deployments, Services) in Git.
    - **Automation:** A GitOps tool (e.g., ArgoCD, Flux) continuously monitors the Git repo and syncs it with the cluster.
    - **Reconciliation:** If the cluster state drifts (e.g., a Pod is deleted), the tool re-applies the desired state from Git.
- **Benefits:** Version control, auditability, and consistency across environments.
- **Workflow:** Push a change to Git (e.g., update an image tag), and the GitOps tool applies it to Kubernetes via `kubectl apply`.
- **Example:** ArgoCD watches a Git repo and deploys a new app version when a commit is detected.

105. **How do you use tools like ArgoCD or Flux with Kubernetes?**
ArgoCD and Flux are popular GitOps tools for Kubernetes. Here’s how they work:
- **ArgoCD:**
    - **Setup:** Install ArgoCD in the cluster and connect it to a Git repo.
    - **Configuration:** Define an Application resource pointing to the repo and target namespace.
    - **Syncing:** ArgoCD compares the Git state with the cluster and applies changes (manual or auto-sync).
    - **Features:** UI dashboard, rollback support, multi-cluster management.
    - **Command:** `argocd app create my-app --repo <git-url> --path ./k8s --dest-namespace default`.
- **Flux:**
    - **Setup:** Install Flux in the cluster and configure it with a Git repo.
    - **Configuration:** Flux scans the repo for manifests or Helm charts and applies them.
    - **Syncing:** Automatically reconciles the cluster state with Git (e.g., via `kubectl apply`).
    - **Features:** Lightweight, integrates with Helm, supports image automation.
    - **Command:** `flux bootstrap git --url=<git-url> --branch=main`.
- **Difference:** ArgoCD offers a richer UI and application-centric model, while Flux is simpler and more lightweight.

106. **How do you roll back a deployment in Kubernetes?**
Kubernetes supports rollbacks for Deployments to revert to a previous state.
- **Steps:**
    - Check deployment history: `kubectl rollout history deployment <deployment-name>`.
    - Roll back to the previous revision: `kubectl rollout undo deployment <deployment-name>`.
    - Roll back to a specific revision: `kubectl rollout undo deployment <deployment-name> --to-revision=<number>`.
- **How It Works:** Kubernetes stores a history of ReplicaSet revisions (controlled by `revisionHistoryLimit` in the Deployment spec). Rolling back reverts to the previous ReplicaSet.
- **Verification:** Use `kubectl get pods` to confirm the old version is running.
- **Best Practice:** Test rollbacks in staging and monitor with `kubectl rollout status`.
- **Example:** After a bad update, `kubectl rollout undo deployment my-app` restores the prior version.

107. **What is the difference between kubectl apply and kubectl create?**
- **kubectl create:**
    - Creates a resource from a file or command-line input.
    - Fails if the resource already exists (idempotency not guaranteed).
    - **Example:** `kubectl create -f pod.yaml` or `kubectl create deployment my-app --image=nginx`.
- **kubectl apply:**
    - Applies a configuration to a resource, creating it if it doesn’t exist or updating it if it does.
    - Idempotent: Can be run multiple times without side effects.
    - Tracks changes declaratively using annotations (e.g., `kubectl.kubernetes.io/last-applied-configuration`).
    - **Example:** `kubectl apply -f pod.yaml`.
- **Key Difference:** `create` is imperative (do this once), while `apply` is declarative (ensure this state).

108. **How do you manage multiple environments (e.g., dev, staging, prod) in Kubernetes?**
Managing multiple environments requires separating configurations and resources:
- **Namespaces:** Use separate namespaces (e.g., dev, staging, prod) for isolation.
- **Configuration:**
    - **ConfigMaps/Secrets:** Store environment-specific settings (e.g., database URLs).
    - **Helm:** Use different `values.yaml` files (e.g., `values-dev.yaml`, `values-prod.yaml`).
    - **Kustomize:** Create base manifests and overlays for each environment (e.g., `overlays/dev/`).
    - **Resource Limits:** Set different CPU/memory limits per environment.
    - **RBAC:** Restrict access per environment (e.g., developers can’t modify prod).
- **CI/CD:** Deploy to specific clusters or namespaces based on the branch (e.g., main → prod).
- **Example:** `kubectl apply -f dev.yaml --namespace=dev` vs. `helm install my-app ./chart -f values-prod.yaml -n prod`.

109. **What is a Kubernetes operator, and how does it work?**
A Kubernetes Operator is a custom controller that extends Kubernetes to manage complex, stateful applications.
- **Definition:** Operators automate tasks (e.g., backups, scaling) using custom resources (CRDs) and logic written in a programming language (e.g., Go).
- **How It Works:**
    - Define a CRD (e.g., MyApp resource).
    - Write a controller that watches for MyApp instances and reconciles their state (e.g., creates Pods, updates configs).
    - Deploy the operator in the cluster.
- **Example:** The Prometheus Operator manages Prometheus instances via a Prometheus CRD.
- **Tools:** Operator SDK, Kubebuilder.
- **Use Case:** Automate database provisioning (e.g., PostgreSQL) with lifecycle management.

110. **How do you automate deployments in Kubernetes?**
Automation in Kubernetes involves integrating tools and processes:
- **CI/CD Pipelines:** Use Jenkins, GitLab CI, or GitHub Actions to build, test, and push images.
- **GitOps:** Use ArgoCD or Flux to sync manifests from Git to the cluster.
- **Helm:** Package apps as charts and deploy with `helm upgrade --install`.
- **kubectl Automation:** Script `kubectl apply` commands in a pipeline.
- **Operators:** Deploy custom operators for app-specific automation.
- **Triggers:** Automate based on events (e.g., new image tags, Git commits).
- **Example:** A GitHub Action builds an image, updates a Deployment YAML, and runs `kubectl apply -f deployment.yaml`.

111. **What is the Custom Resource Definition (CRD) in Kubernetes?**
A Custom Resource Definition (CRD) is a Kubernetes feature that allows users to extend the Kubernetes API by defining their own custom resources. These resources behave like native Kubernetes objects (e.g., Pods, Deployments) but are tailored to specific use cases. A CRD defines the schema and structure of the custom resource, enabling users to create, manage, and interact with it using kubectl or the Kubernetes API.

**Key Points:**
- CRDs do not require recompiling the Kubernetes codebase, making them a lightweight way to extend functionality.
- Once a CRD is created, you can instantiate custom objects based on it (e.g., myresource.example.com).
- Often paired with custom controllers to manage the lifecycle and behavior of these resources.
- Example Use Case: A CRD for a Database resource to manage database instances in a cluster.

112. **How do you extend Kubernetes with custom controllers?**
Custom controllers extend Kubernetes by implementing logic to manage custom resources defined by CRDs. A controller watches the state of the cluster (via the Kubernetes API) and ensures the desired state (defined by the custom resource) matches the actual state.

**Steps to Create a Custom Controller:**
1. Define a CRD: Create a YAML file specifying the custom resource (e.g., Database).
2. Write the Controller Logic: Use a programming language like Go with the Kubernetes client library (e.g., client-go) to watch for changes to the custom resource.
3. Reconcile Loop: Implement a reconciliation loop that compares the desired state (from the CRD instance) with the actual state and takes actions (e.g., provisioning resources).
4. Deploy the Controller: Package it as a container and run it as a Pod in the cluster.

**Tools:** Operator SDK, Kubebuilder, or raw client-go are commonly used to simplify development.

**Example:** A controller for a Database CRD might automatically provision a MySQL instance when a Database resource is created.

113. **What is a sidecar container, and how is it used?**
A sidecar container is an additional container running alongside the main application container within the same Pod. It enhances or extends the functionality of the main container without altering its code.

**How It’s Used:**
- Shares the same network and storage namespace as the main container, enabling tight integration.
- Commonly used for logging (e.g., Fluentd), monitoring (e.g., Prometheus exporters), proxying (e.g., Envoy in Istio), or initialization tasks.

**Example:** A web server Pod with a sidecar container running a log collector that forwards logs to a central system.

**Benefits:** Separation of concerns, reusability, and modularity.

114. **How does Kubernetes handle multi-tenancy?**
Kubernetes supports multi-tenancy—running multiple isolated users or teams on a shared cluster—through a combination of features, though it’s not fully multi-tenant out of the box.

**Mechanisms:**
- Namespaces: Logical isolation for resources (Pods, Services, etc.) per tenant.
- RBAC (Role-Based Access Control): Restricts access to resources based on user roles within namespaces.
- Resource Quotas and Limits: Ensures fair resource allocation (CPU, memory) among tenants.
- Network Policies: Controls network traffic between tenants’ workloads.
- Pod Security Policies (or alternatives): Enforces security constraints per tenant.

**Challenges:** Kubernetes lacks strict isolation (e.g., kernel-level separation), so noisy neighbors or security breaches can affect tenants unless additional tools (e.g., virtual clusters) are used.

**Advanced Solutions:** Tools like vCluster or Capsule can create virtual Kubernetes clusters for stronger isolation.

115. **What is Federation in Kubernetes, and when is it used?**
Kubernetes Federation (also known as KubeFed) enables the management of multiple Kubernetes clusters as a single entity. It synchronizes resources across clusters and provides a unified control plane.

**Key Features:**
- Deploy and manage applications across multiple clusters in different regions or clouds.
- Ensures high availability and disaster recovery by distributing workloads.
- Supports global load balancing and cross-cluster service discovery.

**When to Use:**
- Multi-region deployments for low latency (e.g., serving users globally).
- Hybrid or multi-cloud setups to avoid vendor lock-in.
- Disaster recovery scenarios requiring redundancy across clusters.

**Status:** Federation v2 (KubeFed) is deprecated; alternatives like Karmada or Clusterpedia are emerging.

116. **How do you upgrade a Kubernetes cluster without downtime?**
Upgrading a Kubernetes cluster without downtime requires careful planning to ensure workloads remain available.

**Steps:**
1. Check Compatibility: Verify the new version’s compatibility with your workloads and tools.
2. Upgrade Control Plane:
    - Use a managed service (e.g., EKS, GKE) or manually upgrade kube-apiserver, kube-controller-manager, and kube-scheduler one at a time in a highly available setup.
    - Ensure etcd is backed up and upgraded if needed.
3. Upgrade Worker Nodes:
    - Drain a node (`kubectl drain <node-name>`), evicting Pods gracefully.
    - Upgrade the kubelet and container runtime on the node.
    - Uncordon the node (`kubectl uncordon <node-name>`) to allow scheduling again.
    - Repeat for each node, ensuring enough capacity remains for workloads.
4. Rolling Updates for Workloads: Use Deployments with rolling update strategies to update application Pods seamlessly.
5. Validate: Test cluster functionality (e.g., `kubectl get nodes`) and application health post-upgrade.

**Tools:** kubeadm for self-managed clusters or cloud provider tools for managed clusters.

117. **What is the difference between Kubernetes and OpenShift?**
Kubernetes is an open-source container orchestration platform, while OpenShift (by Red Hat) is a Kubernetes-based platform with additional enterprise features.

**Differences:**
- Base Platform: Kubernetes is the core; OpenShift builds on it.
- Ease of Use: OpenShift provides a more user-friendly UI, CLI (oc), and developer tools out of the box.
- Security: OpenShift has stricter defaults (e.g., SCCs instead of PSPs) and integrated RBAC.
- Deployment: OpenShift includes built-in CI/CD (via Jenkins), image registry, and source-to-image (S2I) capabilities.
- Management: OpenShift is a commercial product with support; Kubernetes is DIY unless using a managed service.
- Networking: OpenShift uses SDN by default; Kubernetes requires a CNI plugin.
- Updates: OpenShift manages upgrades; Kubernetes upgrades are manual or provider-dependent.

118. **How do you configure a Kubernetes cluster on a cloud provider (e.g., AWS, GCP, Azure)?**
Configuring a Kubernetes cluster on a cloud provider typically involves using managed services for simplicity.

**AWS (EKS - Elastic Kubernetes Service):**
1. Install AWS CLI and eksctl.
2. Run: `eksctl create cluster --name my-cluster --region us-west-2 --nodegroup-name workers --nodes 3`.
3. Configure kubectl with the generated kubeconfig.

**GCP (GKE - Google Kubernetes Engine):**
1. Install gcloud SDK.
2. Run: `gcloud container clusters create my-cluster --machine-type=e2-standard-2 --num-nodes=3`.
3. Get credentials: `gcloud container clusters get-credentials my-cluster`.

**Azure (AKS - Azure Kubernetes Service):**
1. Install Azure CLI.
2. Run: `az aks create --resource-group myGroup --name myCluster --node-count 3`.
3. Get credentials: `az aks get-credentials --name myCluster --resource-group myGroup`.

**Common Steps:**
- Configure IAM roles, VPC/networking, and security groups.
- Optionally, enable auto-scaling, logging, and monitoring.

119. **What is KubeVirt, and how does it integrate VMs with Kubernetes?**
KubeVirt is an open-source project that extends Kubernetes to manage virtual machines (VMs) alongside containers.

**How It Works:**
- Adds CRDs (e.g., VirtualMachine, VirtualMachineInstance) to define and run VMs.
- Uses libvirt and QEMU/KVM under the hood to emulate VMs as Pods.
- Integrates with Kubernetes networking (CNI) and storage (CSI).

**Integration:**
- Deploy KubeVirt operator and components in the cluster.
- Create VM definitions in YAML (e.g., disk images, CPU/memory specs).
- Manage VMs with kubectl like Pods (e.g., start, stop, migrate).

**Use Case:** Run legacy apps requiring VMs alongside containerized workloads.

120. **How does Kubernetes handle disaster recovery?**
Kubernetes itself doesn’t provide built-in disaster recovery (DR), but you can implement it with best practices.

**Strategies:**
- Backups: Regularly back up etcd (e.g., using `etcdctl snapshot save`) and store it securely (e.g., S3).
- Multi-Cluster: Use federation or replicate clusters across regions for redundancy.
- Stateful Data: Use external persistent storage (e.g., cloud block storage) with replication.

**Recovery:**
- Restore etcd from backup and redeploy control plane components.
- Reconcile workloads using declarative manifests (GitOps).

**Tools:** Velero for backup/restore of cluster resources and volumes.

**Testing:** Simulate failures to validate DR plan.

121. **A Pod is stuck in a Pending state. How do you troubleshoot it?**
When a Pod is stuck in a Pending state, it means it hasn’t been scheduled to a node yet. Here’s how to troubleshoot:
- **Check Pod Status and Events:**
    - Run `kubectl describe pod <pod-name>`.
    - Look at the Events section for clues (e.g., insufficient resources, no matching nodes, or scheduling issues).
- **Verify Resource Availability:**
    - Check if the cluster has enough CPU and memory: `kubectl get nodes` and `kubectl describe node <node-name>`.
    - Ensure the Pod’s resource requests (requests.cpu, requests.memory) don’t exceed available capacity.
- **Inspect Scheduling Constraints:**
    - Check for taints and tolerations: `kubectl describe node <node-name>` (look for Taints).
    - Verify if the Pod has appropriate tolerations or if node selectors/labels match.
- **Check Persistent Volume Claims (PVCs):**
    - If the Pod uses a PVC, ensure the PVC is bound: `kubectl get pvc`.
    - If unbound, check Persistent Volume (PV) availability or StorageClass configuration.
- **Examine Logs:**
    - Check the scheduler logs if accessible (e.g., on the control plane) to see why it’s not scheduling.
- **Resolution:**
    - Add resources, adjust taints/tolerations, fix node labels, or resolve storage issues based on findings.

122. **A service is not accessible externally. What steps would you take to debug it?**
If a service isn’t accessible externally, follow these steps:
- **Verify Service Configuration:**
    - Run `kubectl get svc <service-name>` and check the Type (e.g., ClusterIP, NodePort, LoadBalancer).
    - For NodePort, ensure the port is open on the node. For LoadBalancer, confirm the external IP is assigned.
- **Check Endpoints:**
    - Run `kubectl get endpoints <service-name>`.
    - If no endpoints are listed, the service isn’t linked to any Pods (e.g., selector mismatch).
- **Validate Pod Status:**
    - Ensure Pods are running: `kubectl get pods -l <label-selector>`.
    - Check Pod logs (`kubectl logs <pod-name>`) for application issues.
- **Test Internal Connectivity:**
    - Use `kubectl exec` into another Pod and test the service’s ClusterIP: `curl <cluster-ip>:<port>`.
- **Inspect Networking:**
    - Verify kube-proxy is running: `kubectl get pods -n kube-system | grep kube-proxy`.
    - Check CNI plugin status (e.g., Flannel, Calico) for network issues.
- **External Access:**
    - For NodePort, test `<node-ip>:<node-port>` externally.
    - For LoadBalancer, ensure the cloud provider’s load balancer is provisioned correctly.
- **Resolution:**
    - Fix selector labels, open firewall rules, or troubleshoot the application/network layer.

123. **You notice high CPU usage in your cluster. How do you identify the cause?**
To pinpoint the cause of high CPU usage:
- **Check Node-Level Usage:**
    - Run `kubectl top nodes` to identify which node has high CPU usage.
- **Identify Pod-Level Usage:**
    - Run `kubectl top pods --all-namespaces` to see which Pods are consuming CPU.
    - Filter by namespace if needed: `kubectl top pods -n <namespace>`.
- **Inspect Resource Limits:**
    - Check Pod specs (`kubectl describe pod <pod-name>`) for CPU requests/limits.
    - Pods without limits might be overconsuming resources.
- **Analyze Logs and Metrics:**
    - Check Pod logs (`kubectl logs <pod-name>`) for abnormal activity.
    - Use a monitoring tool like Prometheus/Grafana to visualize CPU trends.
- **Examine Workloads:**
    - Look at Deployments/StatefulSets: `kubectl get deployments -o wide`.
    - Identify if a recent update or scaling caused the spike.
- **Resolution:**
    - Set resource limits, scale Pods/nodes, or optimize the application based on findings.

124. **How would you handle a situation where a node in the cluster fails?**
If a node fails:
- **Confirm Node Failure:**
    - Run `kubectl get nodes` and look for NotReady status.
- **Check Pod Impact:**
    - Identify affected Pods: `kubectl get pods --all-namespaces -o wide | grep <failed-node>`.
    - Kubernetes will attempt to reschedule Pods if they’re managed by a controller (e.g., Deployment).
- **Drain the Node (if recoverable):**
    - Run `kubectl drain <node-name> --ignore-daemonsets` to evict Pods gracefully.
    - Mark it unschedulable: `kubectl cordon <node-name>`.
- **Replace or Repair:**
    - If hardware-related, replace the node or fix it (e.g., restart kubelet).
    - Rejoin the cluster: `kubeadm join` or cloud provider-specific steps.
- **Scale if Needed:**
    - Add a new node if the cluster is under-resourced: `kubectl scale` or update node pool.
- **Monitor Recovery:**
    - Ensure Pods are rescheduled: `kubectl get pods -o wide`.

125. **A deployment fails after an update. How do you roll it back?**
To roll back a failed deployment:
- **Check Deployment Status:**
    - Run `kubectl rollout status deployment/<deployment-name>` to confirm the failure.
- **View History:**
    - Run `kubectl rollout history deployment/<deployment-name>` to see previous revisions.
- **Roll Back:**
    - Revert to the last working revision: `kubectl rollout undo deployment/<deployment-name>`.
    - Or specify a revision: `kubectl rollout undo deployment/<deployment-name> --to-revision=<number>`.
- **Verify Rollback:**
    - Check Pods: `kubectl get pods -l <label-selector>`.
    - Confirm the application is stable.
- **Investigate Failure:**
    - Review events (`kubectl describe deployment`) and logs to prevent recurrence.

126. **How do you migrate an application from a single server to Kubernetes?**
Migration steps:
- **Containerize the Application:**
    - Write a Dockerfile for the app, build an image, and push it to a registry (e.g., Docker Hub).
- **Define Kubernetes Resources:**
    - Create a Deployment YAML with replicas, container image, and resource limits.
    - Expose it via a Service (e.g., ClusterIP or LoadBalancer).
- **Handle Storage:**
    - Migrate persistent data to a PV/PVC or cloud storage (e.g., AWS S3).
- **Configure Environment:**
    - Use ConfigMaps for configuration and Secrets for sensitive data.
- **Test Locally:**
    - Use Minikube or Kind to test the app in a Kubernetes environment.
- **Deploy to Cluster:**
    - Apply manifests: `kubectl apply -f <file>.yaml`.
    - Verify Pods and Services: `kubectl get all`.
- **Update DNS and Cut Over:**
    - Point traffic to the Kubernetes Service (e.g., via Ingress or LoadBalancer).
- **Monitor and Scale:**
    - Set up monitoring (e.g., Prometheus) and HPA for scaling.

127. **A Secret is accidentally exposed. What steps do you take to mitigate it?**
Mitigation steps:
- **Rotate the Secret:**
    - Generate a new secret: `kubectl create secret generic <name> --from-literal=key=newvalue`.
    - Update affected applications to use the new secret.
- **Delete the Exposed Secret:**
    - Remove the old secret: `kubectl delete secret <secret-name>`.
- **Audit Access:**
    - Check RBAC policies: `kubectl get rolebindings,clusterrolebindings -A`.
    - Restrict access to Secrets.
- **Review Logs:**
    - Audit logs (if enabled) to see who accessed the Secret.
- **Secure Storage:**
    - Enable encryption at rest for Secrets in etcd (update API server config).
- **Notify and Remediate:**
    - Inform stakeholders and revoke any compromised credentials (e.g., API keys).

128. **How do you scale an application to handle sudden traffic spikes?**
Scaling steps:
- **Manual Scaling:**
    - Increase replicas: `kubectl scale deployment <deployment-name> --replicas=<number>`.
- **Horizontal Pod Autoscaling (HPA):**
    - Set up HPA: `kubectl autoscale deployment <deployment-name> --min=2 --max=10 --cpu-percent=80`.
    - Ensure metrics server is installed for CPU/memory metrics.
- **Cluster Autoscaling:**
    - Enable cluster autoscaler to add nodes dynamically (cloud provider-specific).
- **Load Balancing:**
    - Use a LoadBalancer Service or Ingress to distribute traffic.
- **Monitor:**
    - Use Prometheus/Grafana to track traffic and resource usage.
- **Optimize:**
    - Adjust resource limits/requests to avoid over-provisioning.

129. **A StatefulSet is not creating Pods as expected. What could be the issue?**
Potential issues:
- **PVC Issues:**
    - Check if PVCs are bound: `kubectl get pvc`.
    - Verify StorageClass or PV availability.
- **Resource Constraints:**
    - Ensure sufficient CPU/memory: `kubectl describe statefulset <name>`.
- **Pod Naming or Ordering:**
    - StatefulSets create Pods sequentially. Check events for failures in earlier Pods.
- **Headless Service:**
    - Ensure the associated Service exists and is correctly configured (ClusterIP: None).
- **Taints/Tolerations:**
    - Verify nodes aren’t tainted without matching tolerations.
- **Troubleshooting:**
    - Run `kubectl describe statefulset <name>` and check events/logs.

130. **How do you recover a cluster if the etcd database is corrupted?**
Recovery steps:
- **Stop Kubernetes Services:**
    - Pause kube-apiserver on all control plane nodes to prevent further corruption.
- **Backup Check:**
    - Restore from a recent etcd backup (e.g., taken via `etcdctl snapshot save`).
- **Restore etcd:**
    - Stop etcd, replace the corrupted data directory with the backup, and restart: `etcdctl snapshot restore <backup-file>`.
- **Restart Control Plane:**
    - Start kube-apiserver, controller-manager, and scheduler.
- **Verify Cluster:**
    - Run `kubectl get nodes` and `kubectl get pods --all-namespaces` to ensure recovery.
- **Prevention:**
    - Set up regular etcd backups and monitor etcd health.

131. **Write a YAML file to create a Deployment with 3 replicas.**
Here’s a YAML file for a Deployment with 3 replicas running an Nginx container:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
    name: nginx-deployment
    namespace: default
    labels:
        app: nginx
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
Explanation: 
- `replicas: 3` ensures 3 Pods are running.
- `selector` matches Pods with the label `app: nginx`.
- The `template` defines the Pod spec, including the Nginx container.

132. **How do you expose a Deployment as a LoadBalancer service?**
To expose a Deployment as a LoadBalancer service, create a Service resource with `type: LoadBalancer`. Here’s how:
YAML Example:
```yaml
apiVersion: v1
kind: Service
metadata:
    name: nginx-service
    namespace: default
spec:
    selector:
        app: nginx
    ports:
    - protocol: TCP
        port: 80
        targetPort: 80
    type: LoadBalancer
```
Command:
```bash
kubectl expose deployment nginx-deployment --type=LoadBalancer --port=80 --name=nginx-service
```
Explanation: 
- The `selector` matches the Deployment’s Pods (label `app: nginx`).
- `type: LoadBalancer` exposes the service externally via a cloud provider’s load balancer.
- Traffic to port 80 is forwarded to the Pods’ port 80.

133. **Write a Network Policy to allow traffic only from specific Pods.**
Here’s a Network Policy that allows ingress traffic only from Pods with the label `role: frontend`:
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
    name: allow-specific-pods
    namespace: default
spec:
    podSelector:
        matchLabels:
            app: backend
    policyTypes:
    - Ingress
    ingress:
    - from:
        - podSelector:
                matchLabels:
                    role: frontend
        ports:
        - protocol: TCP
            port: 80
```
Explanation: 
- `podSelector` applies the policy to Pods labeled `app: backend`.
- `ingress` allows traffic only from Pods with `role: frontend` on port 80.
- Requires a CNI plugin supporting Network Policies (e.g., Calico).

134. **Create a CronJob that runs every 5 minutes.**
Here’s a YAML for a CronJob that runs every 5 minutes:
```yaml
apiVersion: batch/v1
kind: CronJob
metadata:
    name: hello-cronjob
    namespace: default
spec:
    schedule: "*/5 * * * *"
    jobTemplate:
        spec:
            template:
                spec:
                    containers:
                    - name: hello
                        image: busybox
                        command:
                        - /bin/sh
                        - -c
                        - echo "Hello from CronJob!"
                    restartPolicy: OnFailure
```
Explanation: 
- `schedule: "*/5 * * * *"` uses cron syntax to run every 5 minutes.
- The `busybox` image runs a simple echo command.
- `restartPolicy: OnFailure` ensures the Job retries if it fails.

135. **How do you set resource limits and requests for a Pod?**
Resource limits and requests are set in the Pod’s container spec under `resources`. Here’s an example:
YAML Example:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: resource-pod
spec:
    containers:
    - name: nginx
        image: nginx:latest
        resources:
            requests:
                memory: "64Mi"
                cpu: "250m" # 250 milliCPU (1/4 CPU)
            limits:
                memory: "128Mi"
                cpu: "500m" # 500 milliCPU (1/2 CPU)
```
Explanation: 
- `requests`: Minimum resources the Pod needs (e.g., 64Mi memory, 1/4 CPU).
- `limits`: Maximum resources the Pod can use (e.g., 128Mi memory, 1/2 CPU).
- CPU is measured in cores or millicores (m), and memory in Mi (mebibytes) or Gi (gibibytes).

136. **Write a YAML file for a Persistent Volume and Persistent Volume Claim.**
Here’s an example of a Persistent Volume (PV) and Persistent Volume Claim (PVC):
Persistent Volume (PV):
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
    persistentVolumeReclaimPolicy: Retain
    hostPath:
        path: "/mnt/data"
```
Persistent Volume Claim (PVC):
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
Explanation: 
- PV defines 10Gi of storage with a `hostPath` (for local testing).
- PVC requests 10Gi and binds to the PV with matching `accessModes`.
- `ReadWriteOnce` means it can be mounted as read-write by one node.

137. **How do you taint a node and apply a toleration to a Pod?**
Taint a Node:
```bash
kubectl taint nodes <node-name> key=value:NoSchedule
```
Example:
```bash
kubectl taint nodes node1 app=backend:NoSchedule
```
`NoSchedule` prevents Pods without matching tolerations from scheduling on `node1`.
Apply a Toleration to a Pod:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: toleration-pod
spec:
    containers:
    - name: nginx
        image: nginx:latest
    tolerations:
    - key: "app"
        operator: "Equal"
        value: "backend"
        effect: "NoSchedule"
```
Explanation: 
- The toleration allows the Pod to be scheduled on nodes with the `app=backend:NoSchedule` taint.

138. **Create a ConfigMap and inject it into a Pod as an environment variable.**
ConfigMap:
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
    name: app-config
data:
    env: "production"
    log_level: "info"
```
Pod with ConfigMap Injected:
```yaml
apiVersion: v1
kind: Pod
metadata:
    name: config-pod
spec:
    containers:
    - name: nginx
        image: nginx:latest
        env:
        - name: ENVIRONMENT
            valueFrom:
                configMapKeyRef:
                    name: app-config
                    key: env
        - name: LOG_LEVEL
            valueFrom:
                configMapKeyRef:
                    name: app-config
                    key: log_level
```
Explanation: 
- The ConfigMap `app-config` stores key-value pairs.
- The Pod injects `env` and `log_level` as environment variables `ENVIRONMENT` and `LOG_LEVEL`.

139. **How do you configure an Ingress resource with TLS?**
Here’s an Ingress resource with TLS:
YAML Example:
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
    name: ingress-tls
    annotations:
        nginx.ingress.kubernetes.io/rewrite-target: /
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
Create TLS Secret:
```bash
kubectl create secret tls tls-secret --key tls.key --cert tls.crt
```
Explanation: 
- `tls` specifies the host (`example.com`) and references a Secret (`tls-secret`) containing the TLS key and certificate.
- The `rules` route traffic to the `nginx-service` on port 80.

140. **Write a Helm chart for a simple web application.**
A Helm chart includes a `Chart.yaml`, `values.yaml`, and templates. Here’s a basic structure for an Nginx web app:
Directory Structure:
```
nginx-chart/
├── Chart.yaml
├── values.yaml
├── templates/
│   ├── deployment.yaml
│   └── service.yaml
```
Chart.yaml:
```yaml
apiVersion: v2
name: nginx-chart
description: A Helm chart for a simple Nginx web app
version: 0.1.0
```
values.yaml:
```yaml
replicaCount: 3
image:
    repository: nginx
    tag: latest
service:
    type: LoadBalancer
    port: 80
```
templates/deployment.yaml:
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
templates/service.yaml:
```yaml
apiVersion: v1
kind: Service
metadata:
    name: {{ .Release.Name }}-nginx-service
spec:
    selector:
        app: nginx
    ports:
    - protocol: TCP
        port: {{ .Values.service.port }}
        targetPort: 80
    type: {{ .Values.service.type }}
```
Install the Chart:
```bash
helm install my-nginx ./nginx-chart
```
Explanation: 
- `Chart.yaml` defines metadata.
- `values.yaml` provides configurable values.
- Templates use Helm templating (`{{ .Values }}`) to generate Kubernetes manifests.