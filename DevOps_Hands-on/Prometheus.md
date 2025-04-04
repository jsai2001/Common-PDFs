# 1. Prometheus Fundamentals

## Basics

* What is Prometheus, and how does it differ from traditional monitoring tools?
* What are the key components of Prometheus (e.g., Prometheus server, time-series database, HTTP server, exporters)?
* What is a time-series database, and why does Prometheus use it?
* What are the four core metric types in Prometheus (Counter, Gauge, Histogram, Summary)?
* How does Prometheus handle data collection (pull vs. push model)?
* What is PromQL, and what are its basic syntax rules?
* What is the role of the Prometheus configuration file (prometheus.yml)?
* How does Prometheus scrape metrics from targets?

## Questions to Address

* How do you install and configure a basic Prometheus instance?
* What is the difference between a Counter and a Gauge? Provide examples.
* How would you write a simple PromQL query to retrieve CPU usage over time?
* What happens if a scrape target is down? How does Prometheus handle it?
* How can you visualize Prometheus data without additional tools?

## Code-Related Questions

* Write a basic prometheus.yml configuration file to scrape metrics from a local endpoint at localhost:8080.
* How would you modify prometheus.yml to increase the scrape interval to 30 seconds?
* Write a PromQL query to calculate the total number of HTTP requests using a Counter metric (e.g., http_requests_total).
* Create a sample metric exposition format for a Gauge metric tracking memory usage.
* How do you expose a custom metric (e.g., app_requests_total) from a Python application using the Prometheus client library?

# 2. Prometheus Architecture and Ecosystem

## Concepts

* What are exporters, and how do they expose metrics to Prometheus?
* What is the role of the Service Discovery mechanism in Prometheus?
* How does Prometheus integrate with Kubernetes Service Discovery?
* What is the Alertmanager, and how does it manage alerts?
* What are recording rules and alerting rules in Prometheus?
* How does Prometheus handle high availability and data federation?

## Questions to Address

* What exporters are commonly used with Kubernetes (e.g., Node Exporter, kube-state-metrics)?
* How do you configure Prometheus to discover Kubernetes pods and services dynamically?
* What is the difference between kube-state-metrics and metrics from the Kubernetes Metrics Server?
* How do you set up an alerting rule for high CPU usage in Prometheus?
* How would you configure Alertmanager to send notifications to Slack or email?
* What are the pros and cons of Prometheus’ pull-based model in a Kubernetes cluster?

## Code-Related Questions

* Write a prometheus.yml snippet to configure scraping for Node Exporter running on node-exporter:9100.
* How would you configure Kubernetes Service Discovery in prometheus.yml to scrape all pods with the label app=my-app?
* Create a recording rule to precompute the average CPU usage per node over 5 minutes.
* Write an Alertmanager configuration (alertmanager.yml) to route critical alerts to a Slack webhook.
* How do you define a simple alerting rule in rules.yml to trigger when memory usage exceeds 90%?
* Modify prometheus.yml to federate metrics from another Prometheus instance at remote-prom:9090.

# 3. Kubernetes Integration

## Basics

* How does Prometheus monitor Kubernetes clusters?
* What is the role of the Prometheus Operator in Kubernetes?
* What are Custom Resource Definitions (CRDs) like ServiceMonitor and PodMonitor?
* How does Prometheus scrape metrics from Kubernetes components (e.g., nodes, pods, services)?
* What are the default metrics exposed by Kubernetes that Prometheus can use?

## Questions to Address

* How do you deploy Prometheus in a Kubernetes cluster using Helm or manifests?
* What is the difference between manual Prometheus configuration and using the Prometheus Operator?
* How do you configure a ServiceMonitor to scrape application-specific metrics?
* How can you monitor the control plane components (e.g., API server, scheduler) with Prometheus?
* What Kubernetes-specific labels and annotations does Prometheus rely on for service discovery?
* How do you ensure Prometheus scales with a growing Kubernetes cluster?

## Code-Related Questions

* Write a Helm command to install Prometheus with the Prometheus Operator in the monitoring namespace.
* Create a ServiceMonitor YAML to scrape metrics from a pod with the label app=nginx on port metrics:8080.
* How would you define a PodMonitor YAML to monitor pods without a service in Kubernetes?
* Write a Kubernetes Role and RoleBinding YAML to grant Prometheus RBAC permissions to discover services.
* Modify a prometheus.yml file to scrape Kubernetes API server metrics at https://kubernetes.default.svc:443.
* How do you configure a Prometheus CRD to limit resource usage (e.g., 2GB memory, 1 CPU)?

# 4. PromQL (Prometheus Query Language)

## Concepts

* What are the basic building blocks of a PromQL query (e.g., selectors, functions, operators)?
* How do you use rate() and increase() functions with Counter metrics?
* What are aggregation operators (e.g., sum, avg, max) and how are they applied?
* How do you filter metrics by labels in PromQL?
* What is the difference between instant vectors and range vectors?

## Questions to Address

* Write a PromQL query to calculate the average CPU usage per pod over the last 5 minutes.
* How would you query the 95th percentile request latency using a Histogram metric?
* How do you use the “rate” function to measure network traffic per second?
* How can you join two metrics in PromQL using label matching?
* What happens if you query a metric that doesn’t exist? How do you troubleshoot it?
* How do you use the “topk” function to find the top 3 memory-consuming pods?

## Code-Related Questions

* Write a PromQL query: rate(node_cpu_seconds_total[5m]) and explain what it measures.
* Create a PromQL query to calculate the 99th percentile latency from http_request_duration_seconds_bucket.
* How would you write a query to sum the memory usage of all pods in the prod namespace?
* Write a PromQL query to join container_cpu_usage_seconds_total with pod_labels using the on clause.
* How do you write a query to find pods with memory usage above 500MB using container_memory_usage_bytes?
* Create a PromQL expression to calculate the error rate of HTTP requests (e.g., http_errors_total / http_requests_total).

# 5. Monitoring Kubernetes with Prometheus

## Concepts

* What are the key Kubernetes metrics to monitor (e.g., CPU, memory, disk, network)?
* How do you monitor pod health, restarts, and failures using Prometheus?
* What is the role of Node Exporter in collecting node-level metrics?
* How do you monitor resource utilization vs. resource requests/limits in Kubernetes?
* What are best practices for labeling metrics in a Kubernetes environment?

## Questions to Address

* How do you set up Prometheus to monitor node-level metrics like disk I/O or memory usage?
* What query would you use to detect pods that are consistently restarting?
* How can you monitor the resource usage of a specific namespace in Kubernetes?
* How do you identify over-provisioned or under-utilized pods with Prometheus?
* How would you detect a Kubernetes node running out of disk space using Prometheus?
* What metrics would you use to monitor the health of a Kubernetes ingress controller?

## Code-Related Questions

* Write a PromQL query to calculate total disk usage per node using node_filesystem_size_bytes.
* Create a query to count pod restarts using kube_pod_container_status_restarts_total.
* How would you write a PromQL query to compare container_cpu_usage_seconds_total with kube_pod_container_resource_requests?
* Write a query to find pods using more CPU than their limits (kube_pod_container_resource_limits).
* Define a PromQL expression to alert when node disk space (node_filesystem_free_bytes) falls below 10%.
* How do you query ingress request rates using nginx_ingress_controller_requests?

# 6. Alerting and Notification

## Concepts

* How do you define alerting rules in Prometheus?
* What are the key fields in an alerting rule (e.g., expr, for, labels, annotations)?
* How does Alertmanager route and group alerts?
* What is alert inhibition, and when would you use it?
* How do you test an alerting setup in Prometheus?

## Questions to Address

* Write an alerting rule to notify when a pod’s CPU usage exceeds 80% for 5 minutes.
* How do you configure Alertmanager to suppress low-priority alerts during maintenance?
* What’s the difference between “pending” and “firing” states in an alert?
* How would you set up a multi-channel notification system (e.g., Slack + email)?
* How do you handle alert fatigue in a large Kubernetes cluster?
* What query would you use to alert on a pod crash loop?

## Code-Related Questions

* Write a rules.yml file with an alert for CPU usage > 80% using container_cpu_usage_seconds_total.
* How do you define an Alertmanager route in alertmanager.yml to send critical alerts to a Slack channel?
* Create an inhibition rule in alertmanager.yml to suppress pod-level alerts if a node is down.
* Write an alerting rule to detect pod crash loops using kube_pod_container_status_restarts_total.
* How would you configure prometheus.yml to load the alerting rules from rules.yml?
* Define a multi-severity alerting rule (e.g., warning at 70% CPU, critical at 90%).

# 7. Advanced Topics

## Concepts

* How does Prometheus handle long-term storage (e.g., integration with Thanos or VictoriaMetrics)?
* What is the role of relabeling in Prometheus, and how is it used in Kubernetes?
* How do you secure Prometheus in a Kubernetes cluster (e.g., TLS, RBAC)?
* What are the limitations of Prometheus in large-scale Kubernetes deployments?
* How do you optimize Prometheus performance (e.g., scrape intervals, sharding)?

## Questions to Address

* How would you configure Prometheus to store metrics for 6 months using an external storage solution?
* What relabeling rules would you use to drop unnecessary metrics from Kubernetes?
* How do you restrict Prometheus access to specific namespaces using Kubernetes RBAC?
* What happens if Prometheus runs out of memory in a large cluster? How do you mitigate it?
* How would you shard Prometheus instances across a multi-cluster Kubernetes environment?
* How do you integrate Prometheus with Grafana for advanced dashboards?

## Code-Related Questions

* Write a prometheus.yml snippet to configure Thanos sidecar for long-term storage.
* Create a relabeling rule in prometheus.yml to drop metrics with the label env=dev.
* How would you configure TLS in prometheus.yml for secure scraping of a Kubernetes endpoint?
* Write a Kubernetes ConfigMap to shard Prometheus scrape targets across two instances.
* Define a ServiceMonitor with relabeling to rewrite pod labels (e.g., app to service).
* How do you configure Grafana’s datasources.yaml to connect to Prometheus at prometheus:9090?

# 8. Practical Scenarios and Troubleshooting

## Questions to Address

* How do you troubleshoot a Prometheus scrape failure in Kubernetes?
* What steps would you take if metrics are missing for certain pods?
* How do you debug an alerting rule that isn’t triggering as expected?
* What would you do if Prometheus is overloading the Kubernetes API server with scrape requests?
* How do you validate that a ServiceMonitor is correctly scraping application metrics?
* What are common pitfalls when setting up Prometheus in a multi-tenant Kubernetes cluster?

## Code-Related Questions

* Write a PromQL query to check scrape failures using up metric.
* How would you modify a ServiceMonitor to fix missing metrics from a pod?
* Create a debug query to verify an alerting rule’s expression (e.g., CPU > 80%).
* How do you adjust prometheus.yml to reduce API server load by increasing scrape intervals?
* Write a kubectl command to inspect Prometheus logs for scrape errors.
* Define a relabeling rule to exclude noisy metrics causing performance issues.

# 9. Integration with Grafana (Bonus, if Covered)

## Concepts

* How does Grafana connect to Prometheus as a data source?
* What are the benefits of using Grafana dashboards with Prometheus metrics?
* How do you create a custom Kubernetes dashboard in Grafana?

## Questions to Address

* How would you build a Grafana dashboard to visualize pod CPU and memory usage?
* What PromQL queries would you use to display cluster-wide resource utilization in Grafana?
* How do you set up Grafana alerts based on Prometheus metrics?

## Code-Related Questions

* Write a Grafana dashboard JSON snippet to display CPU usage using rate(container_cpu_usage_seconds_total[5m]).
* How do you configure a Grafana alert for disk space using node_filesystem_free_bytes?
* Create a PromQL query for Grafana to show pod memory usage as a time series.
* How would you define a Grafana variable to filter dashboards by namespace?

How to Use This List

* Before the Course: Review the questions to set learning goals.
* During the Course: Take notes under each category, answering both conceptual and code questions. Test code snippets in a local environment (e.g., Minikube).
* After the Course: Cross-check your notes against this list. Practice unanswered code questions using Prometheus docs or a sandbox cluster.
* Practice: Deploy Prometheus in Kubernetes and experiment with these configurations and queries.

---
## 1. Prometheus Fundamentals

### Basics

#### What is Prometheus, and how does it differ from traditional monitoring tools?
Prometheus is an open-source monitoring and alerting toolkit designed for reliability and scalability, particularly in dynamic, cloud-native environments. Unlike traditional monitoring tools (e.g., Nagios or Zabbix), which often rely on static configurations and agent-based push models, Prometheus uses a pull-based approach, scraping metrics over HTTP from configured targets. It emphasizes a multidimensional data model with time-series data identified by metric names and key-value pairs (labels), offering powerful querying via PromQL. Traditional tools typically focus on host-based monitoring with less flexibility for querying and scaling.

#### What are the key components of Prometheus?
- **Prometheus Server**: The core component that scrapes metrics, stores them, and evaluates rules for alerting or querying.
- **Time-Series Database (TSDB)**: A built-in, custom database optimized for storing and retrieving time-series data efficiently.
- **HTTP Server**: Exposes an API for querying metrics (via PromQL) and a basic UI for visualization.
- **Exporters**: Small programs that expose metrics from third-party systems (e.g., node_exporter for system metrics) in a Prometheus-compatible format.
- **Service Discovery**: Dynamically finds scrape targets (e.g., via DNS, Kubernetes, etc.).
- **Alertmanager**: A separate component (often bundled) that handles alerts generated by Prometheus rules.

#### What is a time-series database, and why does Prometheus use it?
A time-series database (TSDB) stores data as a sequence of timestamped values, optimized for time-based queries and analysis. Prometheus uses a TSDB because monitoring involves tracking metrics (e.g., CPU usage, request latency) over time, and a TSDB enables efficient storage, retrieval, and aggregation of this data. Its append-only design and compression techniques suit the continuous, high-volume data collection typical in monitoring.

#### What are the four core metric types in Prometheus?
1. **Counter**: A cumulative metric that only increases (e.g., request count). Resets to zero on restart.
   - Example: `http_requests_total`
2. **Gauge**: A metric that can go up or down (e.g., current memory usage).
   - Example: `node_memory_usage_bytes`
3. **Histogram**: Measures distributions (e.g., request durations) with buckets and a cumulative count.
   - Example: `http_request_duration_seconds`
4. **Summary**: Similar to Histogram but precomputes quantiles (e.g., 95th percentile latency).
   - Example: `rpc_duration_seconds`

#### How does Prometheus handle data collection (pull vs. push model)?
Prometheus uses a **pull model**, periodically scraping metrics from HTTP endpoints exposed by targets (e.g., `/metrics`). This contrasts with a **push model** (e.g., StatsD), where agents send data to a server. The pull model simplifies target management, enables health checks (if a target is down, scraping fails), and aligns with microservices architectures. However, Prometheus supports push via the Pushgateway for short-lived jobs.

#### What is PromQL, and what are its basic syntax rules?
PromQL (Prometheus Query Language) is a functional query language for selecting and aggregating time-series data. Basic syntax:
- **Metric selection**: `metric_name{label="value"}` (e.g., `http_requests_total{status="200"}`).
- **Time range**: `[5m]` for the last 5 minutes (e.g., `http_requests_total[5m]`).
- **Operators**: Arithmetic (`+`, `-`), comparisons (`>`, `==`), and aggregation (`sum`, `avg`).
- **Functions**: `rate()`, `increase()`, etc., for calculations.
Example: `rate(http_requests_total[5m])` computes the per-second increase in requests.

#### What is the role of the Prometheus configuration file (prometheus.yml)?
The `prometheus.yml` file defines how Prometheus operates:
- **Global settings**: Scrape intervals, timeouts.
- **Scrape targets**: Endpoints to monitor (static or via service discovery).
- **Rule files**: For recording rules and alerts.
- **Alertmanager**: Configuration for sending alerts.
Example:
```yaml
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'myapp'
    static_configs:
      - targets: ['localhost:9090']
```

#### How does Prometheus scrape metrics from targets?
Prometheus periodically sends HTTP GET requests to target endpoints (e.g., `http://target:port/metrics`), which return metrics in a text-based format (e.g., `metric_name{label="value"} 123.45`). It uses the configuration in `prometheus.yml` to determine targets and intervals. Exporters or instrumented apps expose these endpoints.

---

### Questions to Address

#### How do you install and configure a basic Prometheus instance?
1. **Installation**:
   - Download the latest Prometheus binary from [prometheus.io](https://prometheus.io/download/).
   - Extract and run: `./prometheus --config.file=prometheus.yml`.
   - Example (Linux):
     ```bash
     wget https://github.com/prometheus/prometheus/releases/download/v2.51.0/prometheus-2.51.0.linux-amd64.tar.gz
     tar xvfz prometheus-2.51.0.linux-amd64.tar.gz
     cd prometheus-2.51.0.linux-amd64
     ./prometheus --config.file=prometheus.yml
     ```
2. **Basic Configuration** (`prometheus.yml`):
   ```yaml
   global:
     scrape_interval: 15s
   scrape_configs:
     - job_name: 'prometheus'
       static_configs:
         - targets: ['localhost:9090'] # Prometheus self-monitoring
   ```
3. **Run**: Access the UI at `http://localhost:9090`.

#### What is the difference between a Counter and a Gauge? Provide examples.
- **Counter**: Only increases, used for cumulative totals.
  - Example: `http_requests_total{status="200"}` tracks total successful requests.
  - Use: `rate(http_requests_total[5m])` to get requests per second.
- **Gauge**: Can increase or decrease, used for current values.
  - Example: `node_cpu_usage_percent` shows current CPU usage.
  - Use: `avg(node_cpu_usage_percent)` to average across nodes.

#### How would you write a simple PromQL query to retrieve CPU usage over time?
For a metric like `node_cpu_seconds_total` (from node_exporter):
```promql
rate(node_cpu_seconds_total{mode="user"}[5m]) * 100
```
- `rate()`: Calculates per-second increase over 5 minutes.
- `mode="user"`: Filters for user-mode CPU time.
- `* 100`: Converts to percentage.

#### What happens if a scrape target is down? How does Prometheus handle it?
If a target is down, Prometheus marks it as “unhealthy” in the UI (`/targets` endpoint) and records no new data for that target. Existing data remains in the TSDB. Prometheus continues scraping other targets and can trigger alerts (via Alertmanager) based on rules like `up == 0` (where `up` is a built-in metric indicating target availability).

#### How can you visualize Prometheus data without additional tools?
Prometheus includes a basic web UI at `http://localhost:9090/graph`:
1. Enter a PromQL query (e.g., `rate(http_requests_total[5m])`).
2. Switch to the “Graph” tab to see a line chart over time.
3. Adjust the time range (e.g., last 1 hour).
It’s limited compared to tools like Grafana but sufficient for quick checks.

### Code-Related Questions

#### Write a basic prometheus.yml configuration file to scrape metrics from a local endpoint at localhost:8080.
Here’s a minimal `prometheus.yml` file to scrape metrics from `localhost:8080`:
```yaml
global:
  scrape_interval: 15s  # Default scrape interval
scrape_configs:
  - job_name: 'myapp'  # Unique name for this scrape job
    static_configs:
      - targets: ['localhost:8080']  # Endpoint to scrape
```
- `job_name`: Identifies the scrape job.
- `targets`: Specifies the endpoint(s) exposing metrics at `/metrics` by default.

#### How would you modify prometheus.yml to increase the scrape interval to 30 seconds?
To change the scrape interval to 30 seconds, update the `scrape_interval` under the `global` section or override it in the specific `scrape_configs` section. Here’s the modified version:
```yaml
global:
  scrape_interval: 30s  # Increased to 30 seconds globally
scrape_configs:
  - job_name: 'myapp'
    static_configs:
      - targets: ['localhost:8080']
```
Alternatively, for just this job:
```yaml
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'myapp'
    scrape_interval: 30s  # Overrides global setting for this job
    static_configs:
      - targets: ['localhost:8080']
```

#### Write a PromQL query to calculate the total number of HTTP requests using a Counter metric (e.g., http_requests_total).
Since `http_requests_total` is a Counter (cumulative and ever-increasing), you can query its current value directly or calculate its rate of increase. For the total number of requests at a given moment:
```promql
http_requests_total
```
To sum across all instances (e.g., if labeled by status or host):
```promql
sum(http_requests_total)
```
For the rate of requests per second over the last 5 minutes:
```promql
rate(http_requests_total[5m])
```
- `sum()` aggregates across all time series with the same metric name.
- `rate()` computes the per-second increase, useful for trends.

#### Create a sample metric exposition format for a Gauge metric tracking memory usage.
Prometheus metrics are exposed in a text-based format. Here’s an example for a Gauge metric tracking memory usage in bytes:
```
# HELP memory_usage_bytes Current memory usage of the application in bytes
# TYPE memory_usage_bytes gauge
memory_usage_bytes{app="myapp", env="prod"} 134217728  # 128 MB
memory_usage_bytes{app="myapp", env="dev"} 67108864   # 64 MB
```
- `# HELP`: Describes the metric.
- `# TYPE`: Declares it as a `gauge`.
- Labels (`app`, `env`) provide dimensionality.
- Value (e.g., `134217728`) is the current memory usage.

#### How do you expose a custom metric (e.g., app_requests_total) from a Python application using the Prometheus client library?
Using the `prometheus_client` library in Python, you can define and expose a custom Counter metric like `app_requests_total`. Here’s a complete example:
```python
from prometheus_client import Counter, start_http_server
import time

# Define the custom metric
APP_REQUESTS = Counter('app_requests_total', 'Total number of application requests', ['method', 'endpoint'])

# Start an HTTP server to expose metrics on port 8000
start_http_server(8000)

# Simulate request handling
def handle_request(method, endpoint):
    # Increment the counter with labels
    APP_REQUESTS.labels(method=method, endpoint=endpoint).inc()
    print(f"Handled {method} request to {endpoint}")

# Example usage
if __name__ == "__main__":
    while True:
        handle_request("GET", "/api/users")
        handle_request("POST", "/api/login")
        time.sleep(1)  # Simulate work
```
1. **Install the library**: `pip install prometheus-client`.
2. **Define the metric**: `Counter` for `app_requests_total` with labels `method` and `endpoint`.
3. **Expose metrics**: `start_http_server(8000)` runs a server at `localhost:8000/metrics`.
4. **Increment the metric**: `.labels().inc()` increases the counter for specific label values.

When Prometheus scrapes `localhost:8000/metrics`, it’ll see something like:
```
# HELP app_requests_total Total number of application requests
# TYPE app_requests_total counter
app_requests_total{method="GET",endpoint="/api/users"} 5.0
app_requests_total{method="POST",endpoint="/api/login"} 3.0
```

---
## 2. Prometheus Architecture and Ecosystem

### Concepts

#### What are exporters, and how do they expose metrics to Prometheus?
Exporters are standalone programs that collect metrics from systems or applications not natively instrumented for Prometheus and expose them in a Prometheus-compatible format (text-based, typically at `/metrics`). They act as translators, polling data from a target (e.g., a database or OS) and serving it via an HTTP endpoint. Prometheus scrapes these endpoints periodically. For example, Node Exporter exposes system metrics like CPU and memory usage.

#### What is the role of the Service Discovery mechanism in Prometheus?
Service Discovery (SD) allows Prometheus to dynamically discover scrape targets instead of relying on static configurations. It’s crucial in dynamic environments like Kubernetes, where targets (e.g., pods) change frequently. SD mechanisms include DNS, file-based discovery, and cloud provider integrations (e.g., AWS, GCP), enabling Prometheus to adapt to infrastructure changes without manual updates.

#### How does Prometheus integrate with Kubernetes Service Discovery?
Prometheus uses Kubernetes SD to discover targets via the Kubernetes API. It queries for resources like pods, services, endpoints, and nodes, filtering them by labels or annotations. Configuration in `prometheus.yml` specifies roles (e.g., `pod`, `service`) and namespaces, allowing Prometheus to scrape metrics from dynamically created Kubernetes objects.

#### What is the Alertmanager, and how does it manage alerts?
Alertmanager is a separate component that handles alerts generated by Prometheus. It receives alerts via Prometheus’ HTTP API, then groups, deduplicates, and routes them to notification channels (e.g., Slack, email) based on rules. It also supports silencing and inhibition to reduce noise, ensuring actionable notifications.

#### What are recording rules and alerting rules in Prometheus?
- **Recording Rules**: Precompute and store the results of expensive PromQL queries as new time series for faster access. Used for optimization.
- **Alerting Rules**: Define conditions (PromQL expressions) that, when met, trigger alerts sent to Alertmanager. Include severity and annotations for context.

#### How does Prometheus handle high availability and data federation?
- **High Availability (HA)**: Run multiple identical Prometheus instances scraping the same targets. They store data independently, and Alertmanager deduplicates alerts. External tools (e.g., Thanos) can unify queries.
- **Data Federation**: A Prometheus instance scrapes aggregated metrics from other Prometheus instances (via `/federate` endpoint), enabling hierarchical monitoring across clusters or regions.

---

### Questions to Address

#### What exporters are commonly used with Kubernetes?
- **Node Exporter**: Exposes hardware and OS metrics (e.g., CPU, memory) from Kubernetes nodes. Runs on each node.
- **kube-state-metrics**: Exports cluster-level metrics about Kubernetes objects (e.g., pod status, deployment replicas), not resource usage.
- Others: cAdvisor (container metrics, often integrated), Blackbox Exporter (probing), and application-specific exporters.

#### How do you configure Prometheus to discover Kubernetes pods and services dynamically?
Use Kubernetes SD in `prometheus.yml` with roles like `pod` or `service`. Example below in the code section.

#### What is the difference between kube-state-metrics and metrics from the Kubernetes Metrics Server?
- **kube-state-metrics**: Provides metrics about Kubernetes object states (e.g., pod phase, replica counts). Focuses on metadata, not resource usage.
- **Kubernetes Metrics Server**: Supplies resource usage metrics (e.g., CPU, memory) for pods and nodes via the Metrics API. Lightweight, replaces Heapster.

#### How do you set up an alerting rule for high CPU usage in Prometheus?
See the code section for an example in `rules.yml`.

#### How would you configure Alertmanager to send notifications to Slack or email?
See the code section for an `alertmanager.yml` example.

#### What are the pros and cons of Prometheus’ pull-based model in a Kubernetes cluster?
- **Pros**:
  - Simplifies target health checks (failed scrapes indicate issues).
  - Scales well with dynamic targets via Service Discovery.
  - No need for targets to push data, reducing complexity.
- **Cons**:
  - Requires targets to expose HTTP endpoints, which may not suit all apps.
  - Network latency or firewalls can disrupt scraping.
  - Pushgateway needed for short-lived jobs, adding overhead.

---

### Code-Related Questions

#### Write a prometheus.yml snippet to configure scraping for Node Exporter running on node-exporter:9100.
```yaml
scrape_configs:
  - job_name: 'node-exporter'
    static_configs:
      - targets: ['node-exporter:9100']
```
- Assumes Node Exporter runs at `node-exporter:9100` (e.g., in a Kubernetes cluster).

#### How would you configure Kubernetes Service Discovery in prometheus.yml to scrape all pods with the label app=my-app?
```yaml
scrape_configs:
  - job_name: 'kubernetes-pods'
    kubernetes_sd_configs:
      - role: pod
    relabel_configs:
      - source_labels: [__meta_kubernetes_pod_label_app]
        action: keep
        regex: my-app
      - source_labels: [__meta_kubernetes_pod_annotation_prometheus_io_scrape]
        action: keep
        regex: true  # Optional: only scrape pods with this annotation
      - source_labels: [__address__, __meta_kubernetes_pod_annotation_prometheus_io_port]
        action: replace
        regex: (.+?)(?::\d+)?;(\d+)
        replacement: $1:$2
        target_label: __address__
```
- `role: pod`: Discovers all pods.
- `relabel_configs`: Filters for `app=my-app` and adjusts the scrape address.

#### Create a recording rule to precompute the average CPU usage per node over 5 minutes.
In `rules.yml`:
```yaml
groups:
  - name: cpu-usage
    rules:
      - record: node_cpu_usage_avg_5m
        expr: avg(rate(node_cpu_seconds_total[5m])) by (instance)
```
- `record`: Names the new time series.
- `expr`: Averages CPU usage rate per node (`instance`).

#### Write an Alertmanager configuration (alertmanager.yml) to route critical alerts to a Slack webhook.
```yaml
global:
  slack_api_url: 'https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX'
route:
  receiver: 'slack-critical'
  match:
    severity: critical
receivers:
  - name: 'slack-critical'
    slack_configs:
      - channel: '#alerts'
        text: 'Alert: {{ .CommonAnnotations.summary }} - {{ .CommonAnnotations.description }}'
```
- Routes alerts with `severity: critical` to Slack.

#### How do you define a simple alerting rule in rules.yml to trigger when memory usage exceeds 90%?
```yaml
groups:
  - name: memory-alerts
    rules:
      - alert: HighMemoryUsage
        expr: (node_memory_MemTotal_bytes - node_memory_MemAvailable_bytes) / node_memory_MemTotal_bytes * 100 > 90
        for: 5m  # Sustained for 5 minutes
        labels:
          severity: critical
        annotations:
          summary: 'High memory usage on {{ $labels.instance }}'
          description: 'Memory usage exceeds 90% for 5 minutes.'
```
- `expr`: Calculates memory usage percentage.
- `for`: Ensures the condition persists before firing.

#### Modify prometheus.yml to federate metrics from another Prometheus instance at remote-prom:9090.
```yaml
scrape_configs:
  - job_name: 'federation'
    honor_labels: true
    metrics_path: /federate
    params:
      'match[]': ['{job=~".+"}']  # Scrape all metrics
    static_configs:
      - targets: ['remote-prom:9090']
```
- `metrics_path: /federate`: Uses the federation endpoint.
- `match[]`: Specifies which metrics to scrape.

---

## Kubernetes Integration

### Basics

#### How does Prometheus monitor Kubernetes clusters?
Prometheus monitors Kubernetes clusters by scraping metrics from various components (nodes, pods, services, etc.) using its pull-based model. It leverages Kubernetes Service Discovery to dynamically find targets via the Kubernetes API, querying for resources like pods and services. Exporters (e.g., Node Exporter) and native metrics endpoints (e.g., `/metrics` on the API server) provide the data, which Prometheus stores in its time-series database for querying and alerting.

#### What is the role of the Prometheus Operator in Kubernetes?
The Prometheus Operator simplifies deploying and managing Prometheus in Kubernetes by introducing Custom Resource Definitions (CRDs) like `Prometheus`, `ServiceMonitor`, and `PodMonitor`. It automates tasks such as configuration generation, scaling, and service discovery, replacing manual `prometheus.yml` edits with declarative Kubernetes manifests. The Operator ensures Prometheus instances align with cluster state dynamically.

#### What are Custom Resource Definitions (CRDs) like ServiceMonitor and PodMonitor?
- **ServiceMonitor**: A CRD that defines how Prometheus scrapes metrics from services, based on labels and port configurations. It ties to Kubernetes Services for stable endpoints.
- **PodMonitor**: A CRD for scraping metrics directly from pods (without a Service), useful for apps not exposed via Services. Both CRDs allow dynamic target discovery and configuration.

#### How does Prometheus scrape metrics from Kubernetes components (e.g., nodes, pods, services)?
Prometheus uses Kubernetes SD to discover targets via the API, scraping endpoints like:
- **Nodes**: Via Node Exporter (e.g., `node-exporter:9100`).
- **Pods**: Direct pod IPs or Service endpoints (e.g., `/metrics` on port 8080).
- **Services**: ClusterIP or external endpoints defined in `Service` objects.
It relies on annotations (e.g., `prometheus.io/scrape=true`) and labels for filtering.

#### What are the default metrics exposed by Kubernetes that Prometheus can use?
Kubernetes exposes metrics via:
- **API Server**: `/metrics` (e.g., request counts, latency).
- **Kubelet**: `/metrics` and `/metrics/cadvisor` (node and container metrics).
- **Controller Manager & Scheduler**: `/metrics` (e.g., scheduling latency).
- **Metrics Server**: Aggregated pod/node resource usage (CPU, memory).

---

### Questions to Address

#### How do you deploy Prometheus in a Kubernetes cluster using Helm or manifests?
- **Helm**: Use the `kube-prometheus-stack` chart from the Prometheus Community Helm Charts.
- **Manifests**: Apply YAML files (e.g., from the Operator repo), defining Prometheus, RBAC, and CRDs manually.

#### What is the difference between manual Prometheus configuration and using the Prometheus Operator?
- **Manual**: Edit `prometheus.yml` directly, manage deployments, and handle scaling yourself. Static and error-prone in dynamic environments.
- **Operator**: Uses CRDs for declarative configuration, automates service discovery, and adapts to cluster changes, reducing manual effort.

#### How do you configure a ServiceMonitor to scrape application-specific metrics?
See the code section for a `ServiceMonitor` example.

#### How can you monitor the control plane components (e.g., API server, scheduler) with Prometheus?
Scrape their `/metrics` endpoints (e.g., `https://kubernetes.default.svc:443` for the API server) using Kubernetes SD and proper RBAC. The Operator often includes predefined ServiceMonitors for these.

#### What Kubernetes-specific labels and annotations does Prometheus rely on for service discovery?
- **Labels**: Filter targets (e.g., `app=nginx`).
- **Annotations**:
  - `prometheus.io/scrape=true`: Enable scraping.
  - `prometheus.io/port`: Specify the port.
  - `prometheus.io/path`: Override `/metrics`.

#### How do you ensure Prometheus scales with a growing Kubernetes cluster?
- Use the Operator for dynamic scaling.
- Shard Prometheus instances by workload or namespace.
- Leverage federation or Thanos for long-term storage and global queries.
- Adjust resource limits and retention policies.

---

### Code-Related Questions

#### Write a Helm command to install Prometheus with the Prometheus Operator in the monitoring namespace.
```bash
helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --create-namespace
```
- Installs Prometheus, Alertmanager, and Grafana in the `monitoring` namespace.

#### Create a ServiceMonitor YAML to scrape metrics from a pod with the label app=nginx on port metrics:8080.
```yaml
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: nginx-monitor
  namespace: monitoring
spec:
  selector:
    matchLabels:
      app: nginx
  endpoints:
  - port: metrics
    path: /metrics
    interval: 30s
```
- Matches Services with `app: nginx`.
- Scrapes the `metrics` port (named in the Service) at `/metrics`.

#### How would you define a PodMonitor YAML to monitor pods without a service in Kubernetes?
```yaml
apiVersion: monitoring.coreos.com/v1
kind: PodMonitor
metadata:
  name: pod-monitor
  namespace: monitoring
spec:
  selector:
    matchLabels:
      app: nginx
  podMetricsEndpoints:
  - port: metrics
    path: /metrics
    interval: 30s
```
- Targets pods directly by label, bypassing Services.

#### Write a Kubernetes Role and RoleBinding YAML to grant Prometheus RBAC permissions to discover services.
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: prometheus-role
  namespace: monitoring
rules:
- apiGroups: [""]
  resources: ["services", "endpoints", "pods"]
  verbs: ["get", "list", "watch"]
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: prometheus-rolebinding
  namespace: monitoring
subjects:
- kind: ServiceAccount
  name: prometheus
  namespace: monitoring
roleRef:
  kind: Role
  name: prometheus-role
  apiGroup: rbac.authorization.k8s.io
```
- Grants Prometheus access to discover services, endpoints, and pods.

#### Modify a prometheus.yml file to scrape Kubernetes API server metrics at https://kubernetes.default.svc:443.
```yaml
scrape_configs:
  - job_name: 'kubernetes-apiserver'
    scheme: https
    tls_config:
      ca_file: /var/run/secrets/kubernetes.io/serviceaccount/ca.crt
      insecure_skip_verify: true  # Optional, for self-signed certs
    bearer_token_file: /var/run/secrets/kubernetes.io/serviceaccount/token
    static_configs:
      - targets: ['kubernetes.default.svc:443']
```
- Uses ServiceAccount token and CA for authentication.

#### How do you configure a Prometheus CRD to limit resource usage (e.g., 2GB memory, 1 CPU)?
```yaml
apiVersion: monitoring.coreos.com/v1
kind: Prometheus
metadata:
  name: prometheus
  namespace: monitoring
spec:
  replicas: 1
  resources:
    requests:
      memory: "2Gi"
      cpu: "1"
    limits:
      memory: "2Gi"
      cpu: "1"
  serviceMonitorSelector:
    matchLabels:
      app: prometheus
```
- Sets resource requests and limits for the Prometheus pod.

---

## PromQL (Prometheus Query Language)

### Concepts

#### What are the basic building blocks of a PromQL query (e.g., selectors, functions, operators)?
PromQL queries are built from:
- **Selectors**: Identify time series by metric name and labels (e.g., `http_requests_total{status="200"}`).
- **Functions**: Transform or compute data (e.g., `rate()`, `avg_over_time()`).
- **Operators**: Perform arithmetic (`+`, `-`), comparisons (`>`, `==`), or logical operations (`and`, `or`).
- **Aggregators**: Reduce data across dimensions (e.g., `sum`, `avg`).
Example: `sum(rate(http_requests_total[5m]))` combines a selector, function, and aggregator.

#### How do you use rate() and increase() functions with Counter metrics?
- **rate()**: Calculates the per-second increase of a Counter over a time range, ideal for rates (e.g., requests/sec). Ignores resets.
  - Syntax: `rate(counter_metric[time])`
- **increase()**: Computes the total increase of a Counter over a time range, useful for absolute growth (e.g., total requests in 5m).
  - Syntax: `increase(counter_metric[time])`
Example: For `http_requests_total`, `rate(http_requests_total[5m])` gives requests per second, while `increase(http_requests_total[5m])` gives the total over 5 minutes.

#### What are aggregation operators (e.g., sum, avg, max) and how are they applied?
Aggregation operators reduce multiple time series into a single series:
- `sum`: Adds values (e.g., total requests across instances).
- `avg`: Averages values.
- `max`: Takes the maximum value.
Applied with `by` (group by labels) or `without` (exclude labels). Example: `sum(rate(http_requests_total[5m])) by (job)` sums rates per job.

#### How do you filter metrics by labels in PromQL?
Use curly braces `{}` with label matchers:
- Equality: `metric{label="value"}`
- Regex: `metric{label=~"value|other"}`
- Negation: `metric{label!="value"}`
Example: `http_requests_total{status="200", env=~"prod|staging"}` filters for 200 status in prod or staging.

#### What is the difference between instant vectors and range vectors?
- **Instant Vector**: A set of time series with one value per series at a specific timestamp (e.g., `http_requests_total`).
- **Range Vector**: A set of time series with a range of values over time (e.g., `http_requests_total[5m]`). Used with functions like `rate()` or `avg_over_time()`.

---

### Questions to Address

#### Write a PromQL query to calculate the average CPU usage per pod over the last 5 minutes.
```promql
avg(rate(container_cpu_usage_seconds_total[5m])) by (pod)
```
- `rate()`: Per-second CPU usage.
- `avg()`: Averages over 5 minutes.
- `by (pod)`: Groups by pod name.

#### How would you query the 95th percentile request latency using a Histogram metric?
For a Histogram like `http_request_duration_seconds`:
```promql
histogram_quantile(0.95, sum(rate(http_request_duration_seconds_bucket[5m])) by (le))
```
- `rate()`: Normalizes bucket rates.
- `sum by (le)`: Aggregates across instances, preserving bucket labels.
- `histogram_quantile(0.95, ...)`: Computes the 95th percentile.

#### How do you use the “rate” function to measure network traffic per second?
For a Counter like `node_network_receive_bytes_total`:
```promql
rate(node_network_receive_bytes_total[5m])
```
- Measures bytes received per second over 5 minutes per instance.

#### How can you join two metrics in PromQL using label matching?
Use operators like `on` or `group_left`:
```promql
metric_a{label="value"} * on (common_label) group_left metric_b
```
See the code section for a concrete example.

#### What happens if you query a metric that doesn’t exist? How do you troubleshoot it?
- **Result**: Returns an empty result (no data points).
- **Troubleshooting**:
  1. Check metric name spelling in the Prometheus UI (`/graph`).
  2. Verify the target is scraped (`/targets`).
  3. Inspect the `/metrics` endpoint of the target.
  4. Ensure labels match (e.g., `metric{label="value"}`).

#### How do you use the “topk” function to find the top 3 memory-consuming pods?
```promql
topk(3, container_memory_usage_bytes)
```
- `topk(3, ...)`: Returns the 3 highest values with their labels (e.g., pod names).

---

### Code-Related Questions

#### Write a PromQL query: rate(node_cpu_seconds_total[5m]) and explain what it measures.
```promql
rate(node_cpu_seconds_total[5m])
```
- **What it measures**: The per-second rate of CPU usage (in seconds) per node and mode (e.g., `user`, `system`) over the last 5 minutes. It’s derived from a Counter tracking cumulative CPU time.

#### Create a PromQL query to calculate the 99th percentile latency from http_request_duration_seconds_bucket.
```promql
histogram_quantile(0.99, sum(rate(http_request_duration_seconds_bucket[5m])) by (le))
```
- Computes the 99th percentile latency across all instances, using bucket rates over 5 minutes.

#### How would you write a query to sum the memory usage of all pods in the prod namespace?
```promql
sum(container_memory_usage_bytes{namespace="prod"})
```
- Sums memory usage (in bytes) for all containers in the `prod` namespace.

#### Write a PromQL query to join container_cpu_usage_seconds_total with pod_labels using the on clause.
Assuming `pod_labels` has pod metadata:
```promql
rate(container_cpu_usage_seconds_total[5m]) * on (pod) group_left (app) pod_labels
```
- Joins CPU usage with pod labels (e.g., `app`) using the `pod` label, including `app` in the result.

#### How do you write a query to find pods with memory usage above 500MB using container_memory_usage_bytes?
```promql
container_memory_usage_bytes > 500 * 1024 * 1024
```
- `500 * 1024 * 1024`: Converts 500MB to bytes.
- Returns time series where memory exceeds this threshold.

#### Create a PromQL expression to calculate the error rate of HTTP requests (e.g., http_errors_total / http_requests_total).
```promql
rate(http_errors_total[5m]) / rate(http_requests_total[5m])
```
- Divides the rate of errors by the rate of total requests, giving the error rate per second over 5 minutes.

---

## Monitoring Kubernetes with Prometheus

### Concepts

#### What are the key Kubernetes metrics to monitor (e.g., CPU, memory, disk, network)?
Key metrics to monitor in Kubernetes include:
- **CPU**: Usage (`container_cpu_usage_seconds_total`), requests, and limits.
- **Memory**: Usage (`container_memory_usage_bytes`), requests, and limits.
- **Disk**: Filesystem usage (`node_filesystem_size_bytes`, `node_filesystem_free_bytes`) and I/O (`node_disk_io_time_seconds_total`).
- **Network**: Bytes sent/received (`node_network_receive_bytes_total`, `node_network_transmit_bytes_total`) and errors.
These metrics help track resource utilization, performance, and potential bottlenecks across nodes, pods, and containers.

#### How do you monitor pod health, restarts, and failures using Prometheus?
- **Pod Health**: Use `kube_pod_status_phase` (e.g., `Pending`, `Running`, `Failed`) to check pod states.
- **Restarts**: Track `kube_pod_container_status_restarts_total` to count container restarts.
- **Failures**: Monitor `kube_pod_container_status_terminated_reason` or `kube_pod_status_phase{phase="Failed"}` for failed pods.
Prometheus scrapes these from kube-state-metrics and alerts on anomalies.

#### What is the role of Node Exporter in collecting node-level metrics?
Node Exporter runs on each Kubernetes node, exposing hardware and OS-level metrics (e.g., CPU, memory, disk, network) via an HTTP endpoint (default: `:9100/metrics`). Prometheus scrapes these metrics, providing visibility into node health and resource usage, complementing pod/container metrics.

#### How do you monitor resource utilization vs. resource requests/limits in Kubernetes?
- **Utilization**: Metrics like `container_cpu_usage_seconds_total` and `container_memory_usage_bytes` show actual usage.
- **Requests/Limits**: `kube_pod_container_resource_requests` and `kube_pod_container_resource_limits` (from kube-state-metrics) provide configured values.
Compare these using PromQL (e.g., `usage / requests`) to assess efficiency or overcommitment.

#### What are best practices for labeling metrics in a Kubernetes environment?
- Use consistent, meaningful labels (e.g., `namespace`, `pod`, `app`, `env`).
- Leverage Kubernetes metadata (e.g., `__meta_kubernetes_pod_label_*`) via relabeling.
- Keep label cardinality low to avoid performance issues (avoid highly unique values like timestamps).
- Align with Prometheus naming conventions (e.g., `http_requests_total`).

---

### Questions to Address

#### How do you set up Prometheus to monitor node-level metrics like disk I/O or memory usage?
Deploy Node Exporter on each node (e.g., as a DaemonSet) and configure Prometheus to scrape it:
```yaml
scrape_configs:
  - job_name: 'node-exporter'
    kubernetes_sd_configs:
      - role: node
    relabel_configs:
      - source_labels: [__address__]
        action: replace
        target_label: __address__
        regex: (.+)(?::\d+)?
        replacement: $1:9100  # Node Exporter port
```
This uses Kubernetes SD to discover nodes and scrape metrics like `node_memory_MemAvailable_bytes` or `node_disk_io_time_seconds_total`.

#### What query would you use to detect pods that are consistently restarting?
```promql
increase(kube_pod_container_status_restarts_total[1h]) > 5
```
- `increase()`: Calculates restart count growth over 1 hour.
- `> 5`: Flags pods restarting more than 5 times, indicating instability.

#### How can you monitor the resource usage of a specific namespace in Kubernetes?
For CPU usage in a namespace (e.g., `my-namespace`):
```promql
sum(rate(container_cpu_usage_seconds_total{namespace="my-namespace"}[5m]))
```
- Filters by `namespace` label and sums CPU usage across all containers.

#### How do you identify over-provisioned or under-utilized pods with Prometheus?
Compare usage to requests:
```promql
sum(rate(container_cpu_usage_seconds_total[5m])) by (pod) / sum(kube_pod_container_resource_requests{resource="cpu"}) by (pod) < 0.5
```
- `< 0.5`: Pods using less than 50% of requested CPU (under-utilized).
For over-provisioned (exceeding limits):
```promql
sum(rate(container_cpu_usage_seconds_total[5m])) by (pod) > sum(kube_pod_container_resource_limits{resource="cpu"}) by (pod)
```

#### How would you detect a Kubernetes node running out of disk space using Prometheus?
See the code section for a disk space alert query.

#### What metrics would you use to monitor the health of a Kubernetes ingress controller?
For an NGINX Ingress Controller:
- `nginx_ingress_controller_requests`: Request rate.
- `nginx_ingress_controller_success`: Success rate of requests.
- `nginx_ingress_controller_request_duration_seconds`: Latency distribution.
- `nginx_ingress_controller_upstream_server_response_time`: Backend response time.

---

### Code-Related Questions

#### Write a PromQL query to calculate total disk usage per node using node_filesystem_size_bytes.
```promql
sum(node_filesystem_size_bytes - node_filesystem_free_bytes) by (instance) / 1024^3
```
- Subtracts free space from total size, sums per node (`instance`), and converts to GB.

#### Create a query to count pod restarts using kube_pod_container_status_restarts_total.
```promql
sum(increase(kube_pod_container_status_restarts_total[24h])) by (pod, namespace)
```
- `increase()`: Counts restarts over 24 hours.
- `sum() by (pod, namespace)`: Aggregates per pod and namespace.

#### How would you write a PromQL query to compare container_cpu_usage_seconds_total with kube_pod_container_resource_requests?
```promql
sum(rate(container_cpu_usage_seconds_total[5m])) by (pod, container) / sum(kube_pod_container_resource_requests{resource="cpu"}) by (pod, container)
```
- Ratio > 1 means usage exceeds requests; < 1 means under-utilization.

#### Write a query to find pods using more CPU than their limits (kube_pod_container_resource_limits).
```promql
sum(rate(container_cpu_usage_seconds_total[5m])) by (pod, container) > sum(kube_pod_container_resource_limits{resource="cpu"}) by (pod, container)
```
- Identifies pods exceeding CPU limits.

#### Define a PromQL expression to alert when node disk space (node_filesystem_free_bytes) falls below 10%.
In `rules.yml`:
```yaml
groups:
  - name: node-alerts
    rules:
      - alert: LowDiskSpace
        expr: (node_filesystem_free_bytes / node_filesystem_size_bytes * 100) < 10
        for: 10m
        labels:
          severity: critical
        annotations:
          summary: "Low disk space on {{ $labels.instance }}"
          description: "Free disk space is below 10% for 10 minutes."
```
- Checks if free space is <10% of total size.

#### How do you query ingress request rates using nginx_ingress_controller_requests?
```promql
rate(nginx_ingress_controller_requests[5m])
```
- Calculates requests per second over 5 minutes.
For a specific ingress:
```promql
rate(nginx_ingress_controller_requests{ingress="my-ingress"}[5m])
```

---

## Alerting and Notification

### Concepts

#### How do you define alerting rules in Prometheus?
Alerting rules in Prometheus are defined in a YAML file (e.g., `rules.yml`) and loaded via the `prometheus.yml` configuration. They specify conditions using PromQL expressions that, when true, trigger alerts sent to Alertmanager. Rules are grouped under a `group` name and evaluated periodically.

#### What are the key fields in an alerting rule (e.g., expr, for, labels, annotations)?
- **`expr`**: The PromQL expression that defines the alert condition (e.g., `cpu_usage > 80`).
- **`for`**: Duration the condition must hold before the alert fires (e.g., `5m` for 5 minutes).
- **`labels`**: Key-value pairs added to the alert for routing and identification (e.g., `severity: critical`).
- **`annotations`**: Descriptive metadata for notifications (e.g., `summary`, `description`).

#### How does Alertmanager route and group alerts?
Alertmanager routes alerts based on a routing tree defined in `alertmanager.yml`. The `route` section matches alerts by labels (e.g., `severity`) and directs them to receivers (e.g., Slack, email). Grouping combines similar alerts (by labels like `alertname` or `instance`) into a single notification to reduce noise, controlled by `group_by`.

#### What is alert inhibition, and when would you use it?
Inhibition prevents certain alerts from firing if a related, more severe alert is active. For example, suppress pod-level alerts if a node-level alert (e.g., node down) is firing, as the root cause is already identified. It’s defined in `alertmanager.yml` using label matchers.

#### How do you test an alerting setup in Prometheus?
- Use the Prometheus UI (`/rules`) to verify rule syntax and evaluation.
- Simulate conditions (e.g., increase CPU load) and check `/alerts` for pending/firing states.
- Test Alertmanager routing by sending sample alerts via `amtool` (e.g., `amtool alert add`) and confirming notifications.

---

### Questions to Address

#### Write an alerting rule to notify when a pod’s CPU usage exceeds 80% for 5 minutes.
See the code section for an example in `rules.yml`.

#### How do you configure Alertmanager to suppress low-priority alerts during maintenance?
Use a silence in Alertmanager:
- Define a silence via the UI (`/silences`) or `amtool`:
  ```bash
  amtool silence add --duration=2h severity=low
  ```
- This suppresses alerts with `severity: low` for 2 hours.

#### What’s the difference between “pending” and “firing” states in an alert?
- **Pending**: The `expr` condition is true, but the `for` duration hasn’t elapsed yet. No notification is sent.
- **Firing**: The condition has been true for the `for` duration, and the alert is sent to Alertmanager.

#### How would you set up a multi-channel notification system (e.g., Slack + email)?
Configure multiple receivers in `alertmanager.yml` and route alerts based on severity. See the code section for an example.

#### How do you handle alert fatigue in a large Kubernetes cluster?
- **Grouping**: Use `group_by` in Alertmanager to bundle similar alerts.
- **Inhibition**: Suppress redundant alerts (e.g., pod issues if node is down).
- **Threshold Tuning**: Set meaningful thresholds and `for` durations.
- **Prioritization**: Route critical alerts to urgent channels, others to logs or low-priority channels.

#### What query would you use to alert on a pod crash loop?
Use `kube_pod_container_status_restarts_total` to detect restart spikes. See the code section for an example.

---

### Code-Related Questions

#### Write a rules.yml file with an alert for CPU usage > 80% using container_cpu_usage_seconds_total.
```yaml
groups:
  - name: pod-alerts
    rules:
      - alert: HighPodCPUUsage
        expr: rate(container_cpu_usage_seconds_total[5m]) * 100 > 80
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "High CPU usage on pod {{ $labels.pod }}"
          description: "Pod {{ $labels.pod }} has CPU usage > 80% for 5 minutes."
```
- `rate()`: Calculates CPU usage rate over 5 minutes.
- `* 100`: Converts to percentage.

#### How do you define an Alertmanager route in alertmanager.yml to send critical alerts to a Slack channel?
```yaml
global:
  slack_api_url: 'https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX'
route:
  receiver: 'default'
  routes:
    - match:
        severity: critical
      receiver: 'slack-critical'
receivers:
  - name: 'default'
    # Fallback receiver (e.g., log)
  - name: 'slack-critical'
    slack_configs:
      - channel: '#alerts'
        text: '{{ .CommonAnnotations.summary }}: {{ .CommonAnnotations.description }}'
```
- `match`: Routes `severity: critical` to Slack.

#### Create an inhibition rule in alertmanager.yml to suppress pod-level alerts if a node is down.
```yaml
inhibit_rules:
  - source_match:
      alertname: 'NodeDown'
    target_match_re:
      alertname: 'HighPodCPUUsage|PodCrashLoop'
    equal: ['instance']
```
- Suppresses pod alerts if a `NodeDown` alert is active for the same `instance`.

#### Write an alerting rule to detect pod crash loops using kube_pod_container_status_restarts_total.
```yaml
groups:
  - name: pod-alerts
    rules:
      - alert: PodCrashLoop
        expr: rate(kube_pod_container_status_restarts_total[5m]) > 0
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "Pod {{ $labels.pod }} in crash loop"
          description: "Pod {{ $labels.pod }} has restarted multiple times in 5 minutes."
```
- `rate()`: Detects restart frequency.

#### How would you configure prometheus.yml to load the alerting rules from rules.yml?
```yaml
rule_files:
  - 'rules.yml'
scrape_configs:
  # Existing scrape configs...
```
- `rule_files`: Specifies the rules file to load.

#### Define a multi-severity alerting rule (e.g., warning at 70% CPU, critical at 90%).
```yaml
groups:
  - name: cpu-alerts
    rules:
      - alert: HighCPUUsage
        expr: rate(container_cpu_usage_seconds_total[5m]) * 100 > 70
        for: 5m
        labels:
          severity: warning
        annotations:
          summary: "CPU usage warning on pod {{ $labels.pod }}"
          description: "CPU usage > 70% for 5 minutes."
      - alert: HighCPUUsage
        expr: rate(container_cpu_usage_seconds_total[5m]) * 100 > 90
        for: 5m
        labels:
          severity: critical
        annotations:
          summary: "Critical CPU usage on pod {{ $labels.pod }}"
          description: "CPU usage > 90% for 5 minutes."
```
- Same `alertname` with different `severity` labels for routing.

---

## Advanced Topics

### Concepts

#### How does Prometheus handle long-term storage (e.g., integration with Thanos or VictoriaMetrics)?
Prometheus’ built-in TSDB is optimized for short-term storage (default retention: 15 days), but for long-term storage, it integrates with external solutions like **Thanos** or **VictoriaMetrics**. Thanos extends Prometheus with a sidecar that uploads TSDB blocks to object storage (e.g., S3), enabling infinite retention and global querying via a querier component. VictoriaMetrics offers a single-node or clustered solution that replaces Prometheus’ TSDB, providing efficient storage and querying for years of data. Both require additional configuration and infrastructure.

#### What is the role of relabeling in Prometheus, and how is it used in Kubernetes?
Relabeling allows Prometheus to rewrite or filter metric labels and targets during scraping. It’s critical for managing metadata, dropping irrelevant metrics, or mapping dynamic environments like Kubernetes. In Kubernetes, relabeling uses `__meta_kubernetes_*` labels (e.g., `__meta_kubernetes_pod_label_app`) from Service Discovery to filter pods, rewrite addresses, or add custom labels, ensuring only relevant metrics are collected.

#### How do you secure Prometheus in a Kubernetes cluster (e.g., TLS, RBAC)?
- **TLS**: Configure Prometheus to use HTTPS for scraping and API access with certificates. Targets must expose secure endpoints, and Prometheus needs client certificates or CA validation.
- **RBAC**: Use Kubernetes Role-Based Access Control to restrict Prometheus’ access to the API (e.g., specific namespaces). A ServiceAccount, Role, and RoleBinding limit what resources Prometheus can query via Service Discovery.
- Additional measures: Enable authentication (e.g., basic auth) and deploy behind an ingress with TLS.

#### What are the limitations of Prometheus in large-scale Kubernetes deployments?
- **Storage**: Local TSDB struggles with high cardinality or long retention in large clusters.
- **Scalability**: Single-instance Prometheus can’t handle massive metric volumes without sharding or federation.
- **Cardinality**: High label counts (e.g., per-pod metrics) increase memory usage and query latency.
- **HA**: Native HA requires external deduplication (e.g., Alertmanager) and doesn’t unify data.

#### How do you optimize Prometheus performance (e.g., scrape intervals, sharding)?
- **Scrape Intervals**: Increase intervals (e.g., 30s to 1m) for less critical targets to reduce load.
- **Sharding**: Split scrape targets across multiple Prometheus instances by job or cluster.
- **Recording Rules**: Precompute expensive queries to lower query-time overhead.
- **Cardinality Control**: Limit labels and use relabeling to drop unnecessary metrics.
- **External Storage**: Offload data to Thanos or VictoriaMetrics for scalability.

---

### Questions to Address

#### How would you configure Prometheus to store metrics for 6 months using an external storage solution?
Using Thanos, deploy a sidecar with Prometheus to upload TSDB blocks to object storage (e.g., S3). Retention is managed by the object store’s lifecycle policies. See the code section for a `prometheus.yml` snippet.

#### What relabeling rules would you use to drop unnecessary metrics from Kubernetes?
See the code section for an example dropping `env=dev` metrics.

#### How do you restrict Prometheus access to specific namespaces using Kubernetes RBAC?
Create a ServiceAccount, Role, and RoleBinding to limit API access. Example:
```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: prometheus
  namespace: monitoring
---
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  namespace: app-namespace
  name: prometheus-role
rules:
- apiGroups: [""]
  resources: ["pods", "services"]
  verbs: ["get", "list", "watch"]
---
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: prometheus-binding
  namespace: app-namespace
subjects:
- kind: ServiceAccount
  name: prometheus
  namespace: monitoring
roleRef:
  kind: Role
  name: prometheus-role
  apiGroup: rbac.authorization.k8s.io
```
- Restricts Prometheus to `app-namespace`.

#### What happens if Prometheus runs out of memory in a large cluster? How do you mitigate it?
Prometheus crashes or slows down if it exceeds memory limits due to high cardinality or large TSDB. Mitigation:
- Increase memory limits in the Kubernetes deployment.
- Reduce cardinality with relabeling.
- Shard instances across clusters.
- Use external storage (e.g., Thanos) to offload data.

#### How would you shard Prometheus instances across a multi-cluster Kubernetes environment?
Deploy multiple Prometheus instances, each scraping a subset of targets (e.g., by namespace or cluster). Use a ConfigMap to define targets and federate metrics to a central instance. See the code section for an example.

#### How do you integrate Prometheus with Grafana for advanced dashboards?
Deploy Grafana in the cluster, configure Prometheus as a data source (see code section), and create dashboards using Grafana’s UI or import prebuilt ones (e.g., from grafana.com).

---

### Code-Related Questions

#### Write a prometheus.yml snippet to configure Thanos sidecar for long-term storage.
```yaml
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']
# Thanos sidecar configuration (assumes sidecar runs with Prometheus)
command: "--storage.tsdb.path=/prometheus --storage.tsdb.retention.time=15d --web.enable-lifecycle"
# Sidecar uploads to S3 (configured separately in Thanos args)
```
- Thanos sidecar needs additional args (e.g., `--objstore.config-file`) for S3.

#### Create a relabeling rule in prometheus.yml to drop metrics with the label env=dev.
```yaml
scrape_configs:
  - job_name: 'kubernetes-pods'
    kubernetes_sd_configs:
      - role: pod
    relabel_configs:
      - source_labels: [__meta_kubernetes_pod_label_env]
        action: drop
        regex: dev
```
- `drop`: Excludes pods with `env=dev`.

#### How would you configure TLS in prometheus.yml for secure scraping of a Kubernetes endpoint?
```yaml
scrape_configs:
  - job_name: 'secure-endpoint'
    scheme: https
    tls_config:
      ca_file: /etc/prometheus/ca.crt
      cert_file: /etc/prometheus/client.crt
      key_file: /etc/prometheus/client.key
      insecure_skip_verify: false  # Set to true to skip verification (not recommended)
    static_configs:
      - targets: ['secure-endpoint:443']
```
- Mount certificates into the Prometheus pod via a Secret.

#### Write a Kubernetes ConfigMap to shard Prometheus scrape targets across two instances.
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: prometheus-config
  namespace: monitoring
data:
  prometheus-1.yml: |
    scrape_configs:
      - job_name: 'shard-1'
        kubernetes_sd_configs:
          - role: pod
        relabel_configs:
          - source_labels: [__meta_kubernetes_namespace]
            action: keep
            regex: (app1|app2)  # Shard 1: namespaces app1, app2
  prometheus-2.yml: |
    scrape_configs:
      - job_name: 'shard-2'
        kubernetes_sd_configs:
          - role: pod
        relabel_configs:
          - source_labels: [__meta_kubernetes_namespace]
            action: keep
            regex: (app3|app4)  # Shard 2: namespaces app3, app4
```
- Deploy two Prometheus instances, each using one config.

#### Define a ServiceMonitor with relabeling to rewrite pod labels (e.g., app to service).
```yaml
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: my-app-monitor
  namespace: monitoring
spec:
  selector:
    matchLabels:
      app: my-app
  endpoints:
  - port: metrics
    relabelings:
      - source_labels: [__meta_kubernetes_pod_label_app]
        target_label: service
        action: replace
```
- Rewrites `app` label to `service`.

#### How do you configure Grafana’s datasources.yaml to connect to Prometheus at prometheus:9090?
```yaml
apiVersion: 1
datasources:
  - name: Prometheus
    type: prometheus
    url: http://prometheus:9090
    access: proxy
    isDefault: true
```
- Mount this as a ConfigMap in Grafana’s deployment.

---

## Practical Scenarios and Troubleshooting

Let’s explore these practical Prometheus scenarios and troubleshooting steps, providing actionable advice and code where necessary.

---

### Questions to Address

#### How do you troubleshoot a Prometheus scrape failure in Kubernetes?
1. **Check the Targets Page**: Visit `http://<prometheus>:9090/targets` to see the status of scrape targets. Look for "DOWN" or errors (e.g., "connection refused").
2. **Verify Pod Accessibility**: Ensure the target pod is running (`kubectl get pods`) and its metrics endpoint (e.g., `/metrics`) is reachable.
3. **Inspect Logs**: Check Prometheus logs for errors like timeouts or TLS issues (`kubectl logs <prometheus-pod>`).
4. **Test Manually**: Use `curl` or `wget` from within the cluster (e.g., via a debug pod) to hit the target’s metrics endpoint.
5. **Review Config**: Validate `prometheus.yml` or ServiceMonitor for correct endpoints, ports, and labels.

#### What steps would you take if metrics are missing for certain pods?
1. **Confirm Pod Labels**: Ensure pods have the expected labels or annotations (e.g., `prometheus.io/scrape: "true"`) matching SD config.
2. **Check Service Discovery**: Verify Kubernetes SD is picking up the pods (`/targets` in Prometheus UI).
3. **Inspect Metrics Endpoint**: Ensure the pod exposes metrics at the configured path/port and in Prometheus format.
4. **Review Relabeling**: Check if relabel rules are dropping the pod’s metrics unintentionally.
5. **Pod Status**: Confirm the pod is running and not crashing (`kubectl describe pod <name>`).

#### How do you debug an alerting rule that isn’t triggering as expected?
1. **Evaluate the Expression**: Run the PromQL expression in the Prometheus UI (`/graph`) to see its current value.
2. **Check Time Range**: Ensure the `for` duration (e.g., `5m`) isn’t too long for the condition to persist.
3. **Inspect Labels**: Verify the metric labels match the rule’s expectations (e.g., `instance`, `job`).
4. **Look at Alerts Page**: Check `http://<prometheus>:9090/alerts` for pending/firing alerts and their state.
5. **Test with Simpler Query**: Simplify the expression to isolate the issue (e.g., remove filters).

#### What would you do if Prometheus is overloading the Kubernetes API server with scrape requests?
1. **Increase Scrape Intervals**: Adjust `scrape_interval` in `prometheus.yml` (e.g., from `15s` to `60s`).
2. **Limit Targets**: Use relabeling to reduce the number of scraped objects (e.g., filter by namespace or label).
3. **Throttle Queries**: Add `scrape_timeout` and rate limits in the config.
4. **Use Metrics Server**: Offload resource metrics to the Kubernetes Metrics Server instead of direct API calls.
5. **Scale API Server**: If unavoidable, increase API server capacity or enable caching (e.g., etcd optimizations).

#### How do you validate that a ServiceMonitor is correctly scraping application metrics?
1. **Check ServiceMonitor Status**: Ensure it’s applied (`kubectl get servicemonitor -n <namespace>`).
2. **Verify Labels**: Confirm the ServiceMonitor’s `selector` matches the target service’s labels.
3. **Inspect Targets**: Look at Prometheus UI (`/targets`) for the ServiceMonitor’s endpoints and their status.
4. **Query Metrics**: Run a PromQL query (e.g., `my_app_metric`) to see if data appears.
5. **Check Pod Annotations**: Ensure pods expose metrics and match the ServiceMonitor’s `endpoints` config (e.g., port, path).

#### What are common pitfalls when setting up Prometheus in a multi-tenant Kubernetes cluster?
- **Label Collisions**: Tenants using identical metric names/labels can overwrite data. Use unique `job` or `namespace` labels.
- **Resource Overuse**: Excessive scraping can overload Prometheus or the API server. Limit scrape targets and intervals.
- **Security**: Exposing metrics endpoints without authentication risks data leakage. Use RBAC and network policies.
- **Storage**: Default local storage fills up quickly in large clusters. Plan for retention or remote storage (e.g., Thanos).
- **Alert Noise**: Poorly defined rules lead to alert fatigue. Use inhibition and grouping in Alertmanager.

---

### Code-Related Questions

#### Write a PromQL query to check scrape failures using up metric.
The `up` metric is `1` when a target is scraped successfully, `0` when it fails:
```promql
up == 0
```
To filter by job:
```promql
up{job="myapp"} == 0
```
This shows all targets in the `myapp` job that are currently failing to scrape.

#### How would you modify a ServiceMonitor to fix missing metrics from a pod?
If metrics are missing, ensure the ServiceMonitor targets the correct service and port. Example fix:
```yaml
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: my-app-monitor
  namespace: my-namespace
spec:
  selector:
    matchLabels:
      app: my-app  # Must match service labels
  endpoints:
    - port: metrics  # Pod’s metrics port name (from service)
      path: /metrics  # Correct path if not default
      interval: 30s   # Adjust if needed
```
- Ensure the `port` matches the service’s port name exposing metrics.
- Add `path` if the app uses a non-standard endpoint.

#### Create a debug query to verify an alerting rule’s expression (e.g., CPU > 80%).
For an alerting rule like “CPU usage > 80%”:
```promql
rate(node_cpu_seconds_total{mode="user"}[5m]) * 100 > 80
```
Run this in the Prometheus UI:
1. Check if values exceed 80.
2. Add filters (e.g., `{instance="node1"}`) to isolate specific nodes.
3. Adjust `[5m]` to match the rule’s time range.

#### How do you adjust prometheus.yml to reduce API server load by increasing scrape intervals?
Increase `scrape_interval` globally or per job:
```yaml
global:
  scrape_interval: 60s  # From 15s to 60s
scrape_configs:
  - job_name: 'kubernetes-apiservers'
    kubernetes_sd_configs:
      - role: endpoints
    scrape_interval: 120s  # Override for this job
```
- `60s` globally reduces frequency.
- `120s` for API server targets further lightens the load.

#### Write a kubectl command to inspect Prometheus logs for scrape errors.
Assuming Prometheus runs in a pod named `prometheus-0` in namespace `monitoring`:
```bash
kubectl logs -n monitoring prometheus-0 | grep -i "scrape.*error"
```
- Filters logs for scrape-related errors (e.g., "timeout", "connection refused").

#### Define a relabeling rule to exclude noisy metrics causing performance issues.
To drop noisy metrics (e.g., `http_requests_total` with `status="404"`):
```yaml
scrape_configs:
  - job_name: 'myapp'
    static_configs:
      - targets: ['localhost:8080']
    relabel_configs:
      - source_labels: [__name__, status]
        regex: http_requests_total;404
        action: drop
```
- `drop`: Excludes matching time series.
- `regex`: Matches metric name and label value to filter out.

---

## Integration with Grafana

Let’s explore how Grafana integrates with Prometheus, enhancing visualization and alerting capabilities. I’ll provide detailed answers and code snippets where needed.

---

### Concepts

#### How does Grafana connect to Prometheus as a data source?
Grafana connects to Prometheus by configuring it as a data source via the Grafana UI or configuration files. You provide the Prometheus server’s HTTP endpoint (e.g., `http://localhost:9090`), authentication details (if any), and optional settings like scrape interval alignment. Grafana then queries Prometheus using PromQL over this connection, pulling time-series data for visualization.

#### What are the benefits of using Grafana dashboards with Prometheus metrics?
- **Rich Visualizations**: Grafana offers advanced charting (graphs, heatmaps, gauges) beyond Prometheus’ basic UI.
- **Customization**: Create tailored dashboards with variables, annotations, and layouts.
- **Multi-Source Integration**: Combine Prometheus data with other sources (e.g., MySQL, Loki) in one view.
- **Alerting**: Define and manage alerts with a user-friendly interface, routed via channels like Slack or email.
- **Sharing**: Easily share dashboards with teams or export them as JSON.

#### How do you create a custom Kubernetes dashboard in Grafana?
1. **Add Prometheus Data Source**: Configure Prometheus in Grafana pointing to your cluster’s Prometheus instance.
2. **Create Dashboard**: Use the Grafana UI to add panels, each with a PromQL query targeting Kubernetes metrics (e.g., from kube-state-metrics or node_exporter).
3. **Customize**: Add variables (e.g., namespace, pod) for filtering, set time ranges, and choose visualization types (e.g., time series, table).
4. **Save/Share**: Save the dashboard and export it as JSON for version control or sharing.

---

### Questions to Address

#### How would you build a Grafana dashboard to visualize pod CPU and memory usage?
1. **Add Panels**: Create two panels—one for CPU, one for memory.
2. **PromQL Queries**:
   - CPU: `rate(container_cpu_usage_seconds_total{namespace="$namespace", pod=~"$pod"}[5m]) * 100` (percentage).
   - Memory: `container_memory_working_set_bytes{namespace="$namespace", pod=~"$pod"} / 1024 / 1024` (MiB).
3. **Variables**: Define `$namespace` and `$pod` for filtering (see variable section below).
4. **Visualization**: Use a time-series graph for each, with legends showing pod names.

#### What PromQL queries would you use to display cluster-wide resource utilization in Grafana?
- **Cluster CPU Usage (%)**:
  ```promql
  sum(rate(container_cpu_usage_seconds_total[5m])) / sum(kube_node_status_capacity_cpu_cores) * 100
  ```
  - Sums CPU usage across all containers, divided by total CPU cores.
- **Cluster Memory Usage (%)**:
  ```promql
  sum(container_memory_working_set_bytes) / sum(kube_node_status_capacity_memory_bytes) * 100
  ```
  - Total memory used vs. total capacity.
- **Node Count**:
  ```promql
  count(kube_node_info)
  ```
  - Simple count of nodes for context.

#### How do you set up Grafana alerts based on Prometheus metrics?
1. **Create Panel**: Add a panel with a PromQL query (e.g., CPU usage).
2. **Alert Tab**: In the panel editor, go to “Alert” and define:
   - **Condition**: When the query exceeds a threshold (e.g., `avg() > 90`).
   - **Evaluation**: Set time range (e.g., last 5m) and frequency (e.g., every 1m).
   - **Notifications**: Link to a notification channel (e.g., Slack).
3. **Save**: Enable the alert and test it.

---

### Code-Related Questions

#### Write a Grafana dashboard JSON snippet to display CPU usage using rate(container_cpu_usage_seconds_total[5m]).
Here’s a simplified JSON snippet for a single panel:
```json
{
  "title": "Pod CPU Usage",
  "type": "timeseries",
  "targets": [
    {
      "expr": "rate(container_cpu_usage_seconds_total{namespace=\"$namespace\", pod=~\"$pod\"}[5m]) * 100",
      "legendFormat": "{{pod}}",
      "refId": "A"
    }
  ],
  "fieldConfig": {
    "defaults": {
      "unit": "percent",
      "min": 0,
      "max": 100
    }
  },
  "gridPos": { "h": 8, "w": 12, "x": 0, "y": 0 }
}
```
- `expr`: Calculates CPU usage percentage.
- `legendFormat`: Labels each line with the pod name.
- `unit`: Displays as a percentage.

#### How do you configure a Grafana alert for disk space using node_filesystem_free_bytes?
1. **Query**: Use `node_filesystem_free_bytes` to monitor free disk space.
2. **Alert Setup** in the panel’s “Alert” tab:
   ```promql
   node_filesystem_free_bytes{mountpoint="/", instance=~"$node"} / 1024 / 1024 / 1024 < 10
   ```
   - Triggers when free space drops below 10 GiB.
3. **Conditions**:
   - `WHEN last() of query(A, 5m, now) IS BELOW 10`
   - Evaluate every 1m.
4. **Notification**: Configure a Slack or email channel in Grafana’s “Notification Channels” settings.

#### Create a PromQL query for Grafana to show pod memory usage as a time series.
```promql
container_memory_working_set_bytes{namespace="$namespace", pod=~"$pod"} / 1024 / 1024
```
- `container_memory_working_set_bytes`: Memory used by the pod.
- `/ 1024 / 1024`: Converts bytes to MiB for readability.
- Variables `$namespace` and `$pod` allow filtering in Grafana.

#### How would you define a Grafana variable to filter dashboards by namespace?
In the Grafana dashboard settings under “Variables”:
1. **Add Variable**:
   - Name: `namespace`
   - Type: Query
   - Data Source: Prometheus
   - Query: `label_values(kube_pod_info, namespace)`
   - Refresh: On Dashboard Load
   - Multi-value: Optional (if you want to select multiple namespaces)
   - Include All Option: Optional
2. **Usage**: Reference as `$namespace` in PromQL queries (e.g., `namespace="$namespace"`).
This pulls unique namespace values from `kube_pod_info` metrics.

---