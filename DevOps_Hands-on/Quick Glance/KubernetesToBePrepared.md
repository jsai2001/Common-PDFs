Priority 1: Must-Know Kubernetes Resources for Interviews
---
### Pod

The smallest and simplest
Kubernetes object. A Pod represents a
single instance of a running process
in your cluster.

---
### Deployment

Provides declarative
updates for Pods and ReplicaSets.

---
### Service

An abstraction which defines
a logical set of Pods and a policy by
which to access them - like loadbalancers.

---
### ConfigMap

Used to store
configuration data in key-value pairs
which can be consumed by pods.

---
### Secret

Manages sensitive
information, like passwords, OAuth
tokens, and ssh keys, which can be
referenced in pod definitions.

---
### PersistentVolume

A piece of
storage in the cluster that has been
provisioned by an administrator or
dynamically provisioned using Storage
Classes.

---
### PersistentVolumeClaim

Requests
storage resources defined by a
PersistentVolume.

---
### Namespace

Provides a scope for
names. Resources like Pods, Services,
and Deployments can be isolated within
namespaces.

---
### StatefulSet

Manages the deployment
and scaling of a set of Pods, and
provides guarantees about the ordering
and uniqueness of these Pods.

---
### Ingress

Manages external access to
the services in a cluster, typically
HTTP.

---
### HorizontalPodAutoscaler

Scales a
Deployment, ReplicaSet, or
ReplicationController based on
observed CPU utilization or other
select metrics.

---