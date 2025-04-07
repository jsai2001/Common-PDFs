## SRE Based Questions

### 1. SRE Fundamentals

- **How do you define Service Level Indicators (SLIs), Service Level Objectives (SLOs), and Service Level Agreements (SLAs)? Can you provide an example?**  
  SLIs are measurable metrics of system performance (e.g., latency). SLOs are target values for SLIs (e.g., 99% of requests <200ms). SLAs are contractual commitments based on SLOs with consequences (e.g., 99.9% uptime or refunds). Example: SLI = request success rate, SLO = 99.5% success, SLA = 99.5% or 5% credit.

- **What’s the role of error budgets in SRE, and how do you enforce them?**  
  Error budgets quantify acceptable downtime (e.g., 0.1% per quarter) to balance reliability and innovation. I enforce them by monitoring SLIs via CloudWatch, halting feature releases if the budget is exceeded, and reviewing with the team to adjust priorities.

- **How do you prioritize fixing technical debt versus delivering new features?**  
  I assess debt’s impact on reliability (e.g., outages, toil) versus feature value (e.g., revenue). High-risk debt (e.g., single-point failures) gets priority; otherwise, I allocate 20% of sprint time to debt, tracking progress with tickets.

- **What’s the difference between proactive and reactive system maintenance? Which do you prefer and why?**  
  Proactive maintenance anticipates issues (e.g., capacity planning), while reactive fixes them after they occur (e.g., post-outage patches). I prefer proactive—it reduces incidents and toil, though reactive is inevitable for unknowns.

- **How do you measure the success of an SRE team?**  
  Success is measured by uptime (SLO adherence), reduced toil (automation metrics), incident response time (MTTR), and team morale (feedback). Example: 99.9% uptime and 50% toil reduction in a quarter.

---

### 2. System Design and Architecture

- **How would you design a globally distributed system to handle millions of requests per second?**  
  I’d use AWS multi-region setup with Route 53 latency-based routing, CloudFront for caching, and EKS clusters per region. Load balancers distribute traffic, and DynamoDB global tables sync data, with auto-scaling for spikes.

- **What considerations do you take into account when designing a fault-tolerant system?**  
  I consider redundancy (multi-AZ), failover (RDS Multi-AZ), retries for transient failures, circuit breakers for downstream issues, and monitoring (CloudWatch) to detect faults early.

- **How do you ensure data consistency in a distributed database setup?**  
  I use eventual consistency with DynamoDB (strong reads when needed), quorum-based writes in RDS, and conflict resolution via timestamps or versioning, depending on the use case.

- **Can you explain the CAP theorem and how it influences your design decisions?**  
  CAP theorem states a system can only guarantee two of Consistency, Availability, and Partition tolerance. I favor AP (availability, partition tolerance) for user-facing apps (e.g., eventual consistency in S3) and CP (consistency, partition tolerance) for critical data (e.g., banking).

- **How would you architect a microservices-based application for high availability?**  
  I’d deploy services on EKS across AZs, use an ALB for traffic, implement health checks, and replicate data with RDS Multi-AZ. Circuit breakers and retries handle failures, monitored via CloudWatch.

---

### 3. Incident Management

- **Walk me through your process for conducting a post-mortem after an outage.**  
  I gather timeline data (logs, metrics), identify root cause (e.g., resource exhaustion), list contributing factors, propose fixes (e.g., auto-scaling), assign action items, and share a report with the team within 48 hours.

- **How do you determine the root cause of an incident when logs are incomplete?**  
  I correlate available logs with metrics (e.g., CloudWatch), check system state (e.g., `top`), hypothesize based on patterns (e.g., spike timing), and test fixes iteratively, recreating the issue in staging if needed.

- **What’s your strategy for managing a cascading failure in a production environment?**  
  I isolate affected services (e.g., scale down pods), throttle traffic (e.g., ALB rules), rollback recent changes with `kubectl rollout undo`, and increase capacity (e.g., EC2 instances), monitoring recovery in real-time.

- **How do you communicate with stakeholders during a major incident?**  
  I send brief, frequent updates via Slack/email: initial impact, ongoing actions, and resolution ETA. Post-incident, I share a summary with root cause and next steps, keeping it clear and non-technical for execs.

- **Can you describe a time when you prevented an incident from escalating? What did you do?**  
  I noticed a memory leak via CloudWatch alerts on an EC2 instance. I quickly scaled the Auto Scaling group, applied a hotfix to limit memory usage, and avoided a full outage, keeping downtime under 5 minutes.

---

### 4. Automation and Tooling

- **How do you decide which processes to automate versus handle manually?**  
  I automate repetitive, error-prone tasks (e.g., deployments) with high ROI, leaving one-off or complex diagnostics (e.g., rare bugs) manual. Frequency and time cost guide the decision.

- **What’s your approach to writing idempotent automation scripts?**  
  I check state before acting (e.g., `if ! exists; then create`), use tools like Ansible with built-in idempotency, and test multiple runs to ensure no duplicate effects.

  ```bash
  # Shell example
  if ! grep -q "setting" /etc/config; then
    echo "setting=true" >> /etc/config
  fi
  ```

- **How would you automate the detection and remediation of a memory leak?**  
  I’d script a CloudWatch metric push for memory usage, set an alarm for >90%, and trigger a Lambda to restart the instance or pod. I’d validate with logs post-fix.

  ```bash
  # Push metric
  aws cloudwatch put-metric-data --metric-name MemoryUsage --namespace App --value 95
  ```

- **What’s the most complex automation task you’ve tackled, and how did you approach it?**  
  I automated a multi-region EKS deployment rollback. I used Terraform to define infra, a Shell script to detect failures via `kubectl`, and Ansible to revert configs, testing each step in staging first.

- **How do you test and validate automation scripts before deploying them to production?**  
  I run unit tests (e.g., `bats` for Shell), simulate in a staging environment, check idempotency with multiple runs, and monitor outcomes with dry-run flags (e.g., `ansible-playbook --check`) before prod deployment.

---

---

### 5. Cloud and Infrastructure

- **How do you handle multi-region failover in a cloud environment?**  
  I configure multi-region replication (e.g., RDS read replicas, S3 cross-region replication) and use Route 53 with latency-based routing or failover policies to redirect traffic. I test failover with chaos engineering tools like Gremlin, ensuring recovery within minutes.

- **What’s your strategy for managing cloud costs without compromising reliability?**  
  I use auto-scaling to match demand, tag resources for cost allocation, and schedule non-critical workloads (e.g., dev environments) to shut down off-hours. I monitor with AWS Cost Explorer, trimming unused resources like unattached EBS volumes while maintaining SLAs.

- **How do you approach infrastructure as code (IaC) in a team setting?**  
  I use Terraform with a modular structure, storing code in a Git repo with PR reviews for collaboration. I enforce state management in a shared backend (e.g., S3 with locking) and document variables in READMEs to ensure team alignment.

  ```hcl
  terraform {
    backend "s3" {
      bucket = "my-tf-state"
      key    = "state/terraform.tfstate"
      region = "us-east-1"
    }
  }
  ```

- **What are the trade-offs between using managed services versus self-hosted solutions?**  
  Managed services (e.g., RDS) offer ease of use, auto-scaling, and less maintenance but cost more and limit customization. Self-hosted solutions (e.g., MySQL on EC2) provide control and flexibility but require more effort for patching, scaling, and HA. I choose based on workload needs and team capacity.

- **How do you ensure compliance and governance in a cloud infrastructure?**  
  I enforce IAM policies, enable AWS Config for auditing, and use CloudTrail for activity logs. I automate compliance checks with tools like AWS Security Hub and apply tags for resource governance, ensuring adherence to standards like SOC 2 or HIPAA.

---

### 6. Containers and Orchestration

- **How do you handle secrets management in a Kubernetes cluster?**  
  I use Kubernetes Secrets for sensitive data, encoded in base64, and store them securely with RBAC restrictions. For production, I integrate with AWS Secrets Manager or HashiCorp Vault, injecting secrets via environment variables or volumes.

  ```yaml
  apiVersion: v1
  kind: Secret
  metadata:
    name: my-secret
  type: Opaque
  data:
    password: cGFzc3dvcmQ= # base64-encoded "password"
  ```

- **What’s your process for upgrading a Kubernetes cluster with zero downtime?**  
  I use `kubectl get nodes` to check versions, then upgrade the control plane with `eksctl upgrade cluster` (for EKS) or `kubeadm`. I roll out worker nodes gradually, draining them (`kubectl drain`) and verifying pod health with `kubectl get pods` to ensure no disruptions.

- **How do you debug a container that’s stuck in a CrashLoopBackOff state?**  
  I run `kubectl describe pod <name>` to check events, then `kubectl logs <name>` for crash details. I exec into the container (`kubectl exec -it`) if possible, check resource limits, and fix config errors (e.g., wrong image) with `kubectl edit`.

- **What are the pros and cons of using Kubernetes versus a serverless architecture?**  
  Kubernetes pros: fine-grained control, portability, and robust scaling. Cons: complexity and overhead. Serverless (e.g., AWS Lambda) pros: simplicity, auto-scaling, and no server management. Cons: cold starts, limited execution time. I pick based on workload persistence and cost.

- **How do you ensure resource isolation between workloads in a shared cluster?**  
  I use namespaces for logical separation, set resource quotas (`ResourceQuota`) and limits (`LimitRange`) per namespace, and apply NetworkPolicies to restrict pod communication, ensuring no workload starves others.

  ```yaml
  apiVersion: v1
  kind: ResourceQuota
  metadata:
    name: compute-quota
    namespace: dev
  spec:
    hard:
      requests.cpu: "2"
      requests.memory: "4Gi"
  ```

---

### 7. Monitoring and Observability

- **What’s the difference between monitoring and observability? Why does it matter?**  
  Monitoring tracks predefined metrics (e.g., CPU) to alert on issues; observability provides deeper insight into system behavior via logs, metrics, and traces to diagnose unknowns. It matters because observability helps resolve complex, unpredictable failures faster.

- **How do you design a monitoring system for a distributed application?**  
  I deploy Prometheus for metrics, Loki for logs, and Jaeger for tracing, scraping endpoints across services. I set dashboards in Grafana for real-time views and configure alerts based on SLIs (e.g., latency > 500ms), ensuring coverage of all components.

- **What’s your approach to reducing noise in alerting systems?**  
  I set thresholds based on historical data, use anomaly detection over static limits, and group related alerts into single incidents. I also implement alert suppression during maintenance and escalate only critical issues to reduce fatigue.

- **How do you use tracing to identify bottlenecks in a microservices architecture?**  
  I instrument services with OpenTelemetry, sending traces to Jaeger or AWS X-Ray. I analyze trace spans to spot high-latency calls (e.g., slow DB queries), then optimize the culprit service or adjust resources.

- **What metrics would you monitor for a database under heavy load?**  
  I monitor CPU usage, memory utilization, disk I/O (read/write ops), query latency, connection count, and cache hit ratio. These indicate performance bottlenecks or resource exhaustion.

---

### 8. Security and Reliability

- **How do you harden a system against DDoS attacks?**  
  I use AWS WAF with rate-limiting rules, enable CloudFront for edge caching, and configure Auto Scaling to absorb traffic spikes. I also set up IP whitelisting and monitor with CloudWatch for unusual patterns.

  ```json
  {
    "Name": "RateLimit",
    "Action": "BLOCK",
    "Statement": {
      "RateBasedStatement": {
        "Limit": 2000,
        "AggregateKeyType": "IP"
      }
    }
  }
  ```

- **What’s your approach to managing certificates and ensuring secure communication in production?**  
  I use AWS ACM to issue and renew certificates, attach them to ALBs, and enforce HTTPS with HSTS headers. I monitor expiry with CloudWatch Events and automate rotation to avoid outages.

- **How do you handle a situation where a security patch causes a performance regression?**  
  I roll back the patch with a tested previous version, measure the regression (e.g., latency increase from 200ms to 500ms), and analyze logs/metrics to isolate the issue. I then work with vendors or tweak configs to mitigate while keeping security.

- **What steps do you take to prevent privilege escalation in a cloud environment?**  
  I enforce least privilege with IAM policies, use MFA, disable root accounts, and audit permissions with AWS Config. I also isolate workloads with VPCs and monitor for anomalies via CloudTrail.

- **How do you balance security requirements with operational efficiency?**  
  I automate security checks (e.g., in CI/CD) to reduce manual overhead, use managed services for compliance, and prioritize high-impact risks (e.g., public exposure) over low-risk controls, ensuring efficiency without compromising safety.

---

---

### 9. Performance Optimization

- **How do you identify and resolve a bottleneck in a web application?**  
  I use tools like `nginx` logs or CloudWatch to spot slow endpoints, then profile with `curl` or APM (e.g., New Relic) to pinpoint code or DB issues. Resolution might involve caching (Redis), query optimization, or scaling instances.

- **What’s your process for profiling an application to improve its latency?**  
  I start with metrics (e.g., response time via Prometheus), use `strace` or `perf` for system calls, and analyze code with a profiler (e.g., Python’s `cProfile`). I then optimize slow paths, like reducing I/O waits, and retest.

- **How do you optimize a system for throughput versus latency?**  
  For throughput, I increase parallelism (e.g., more threads, pods) and batch processing. For latency, I minimize hops (e.g., direct DB access) and cache frequently accessed data, balancing based on app needs.

- **What tools do you use to benchmark system performance?**  
  I use `ab` (Apache Benchmark) for HTTP load, `sysbench` for CPU/memory, `fio` for disk I/O, and `wrk` for high-concurrency tests, comparing results against baselines.

- **How do you handle a situation where a system is hitting its resource limits unexpectedly?**  
  I check `top`/`vmstat` for usage spikes, review logs for anomalies, and scale resources (e.g., `kubectl scale` or AWS ASG). I then analyze root cause (e.g., unoptimized queries) and adjust limits or code.

---

### 10. Coding and Scripting

- **Can you write a script to monitor disk usage and alert when it exceeds a threshold?**  
  Here’s a Bash script to check disk usage and echo an alert:

  ```bash
  #!/bin/bash
  THRESHOLD=80
  DISK_USAGE=$(df -h / | awk 'NR==2 {print $5}' | cut -d'%' -f1)
  if [ "$DISK_USAGE" -gt "$THRESHOLD" ]; then
    echo "Alert: Disk usage at ${DISK_USAGE}% exceeds threshold of ${THRESHOLD}%"
  fi
  ```

- **How would you implement a retry mechanism with exponential backoff in Python?**  
  Here’s a Python example (simplified, without Boto3 per your request):

  ```python
  import time
  def retry_operation(max_attempts=3):
      attempt = 1
      while attempt <= max_attempts:
          try:
              # Simulate an operation
              print(f"Attempt {attempt}")
              raise Exception("Failed")
          except Exception as e:
              if attempt == max_attempts:
                  print("Max attempts reached")
                  break
              wait_time = 2 ** attempt  # Exponential backoff
              print(f"Retrying in {wait_time}s: {e}")
              time.sleep(wait_time)
              attempt += 1
  retry_operation()
  ```

- **What’s your approach to handling race conditions in a multi-threaded script?**  
  I use locks (e.g., `threading.Lock` in Python) to synchronize access to shared resources, test with stress loads, and avoid global state where possible to minimize contention.

- **How do you ensure your code is maintainable and readable for other engineers?**  
  I follow PEP 8, use descriptive variable names, add docstrings/comments, and modularize code into functions. I also version control with Git and include unit tests.

- **Can you explain a time when you optimized an algorithm for better performance?**  
  I optimized a log-parsing script that looped over files linearly (O(n²)). By using a hash table to index entries, I reduced runtime from 10 minutes to 2 minutes for 100k lines, verified with `time`.

---

### 11. Collaboration and Processes

- **How do you work with developers to improve the reliability of their code?**  
  I pair on code reviews to add logging and error handling, introduce chaos testing (e.g., Chaos Monkey), and set up CI checks for reliability metrics like crash rates.

- **What’s your approach to introducing SRE practices to a team new to the concept?**  
  I start with a workshop on SLIs/SLOs, implement a simple monitoring setup (e.g., Prometheus), and pilot an error budget, gradually expanding with team buy-in.

- **How do you handle disagreements with a product manager over system reliability needs?**  
  I present data (e.g., downtime impact, user complaints) to justify reliability needs, propose phased approaches (e.g., 99.9% now, 99.99% later), and align on shared goals.

- **What’s your process for reviewing infrastructure code changes from peers?**  
  I check for idempotency, security (e.g., no hard-coded creds), and best practices (e.g., Terraform variables). I run `terraform plan` locally and test in a sandbox.

- **How do you ensure knowledge transfer when a key team member leaves?**  
  I enforce documentation (wikis, runbooks), pair program for hands-on learning, and record key processes in Git repos, ensuring critical info isn’t siloed.

---

### 12. Scenario-Based Questions

- **A critical service goes down during peak traffic. What’s your first step?**  
  I check the alerting system (e.g., CloudWatch) for error details, then verify service status with `kubectl get pods` or AWS console to assess impact and prioritize fixes.

- **You notice a slow memory leak in production. How do you investigate and mitigate it?**  
  I use `top`/`free` to confirm memory growth, check `dmesg` for OOM, and profile with `valgrind` or logs. I mitigate by restarting the service, then patch the leak long-term.

- **A new feature deployment causes intermittent outages. How do you respond?**  
  I rollback with `kubectl rollout undo` or Jenkins revert, check logs for errors, and isolate the change (e.g., via git diff) to fix and retest in staging.

- **Your alerting system is sending false positives. How do you fix it without missing real issues?**  
  I adjust thresholds based on historical data (e.g., 95th percentile), add hysteresis (delay before alerting), and test with synthetic failures to ensure accuracy.

- **A third-party service you depend on fails. How do you ensure your system remains operational?**  
  I implement a fallback (e.g., cached data), queue requests with SQS, and monitor with CloudWatch, updating the app to retry or degrade gracefully until recovery.

---

---

### 13. Tool-Specific Questions

- **How do you configure a Prometheus exporter for custom application metrics?**  
  I write a custom exporter in a language like Go or Python to expose metrics (e.g., request count) via an HTTP endpoint (e.g., `/metrics`). I configure Prometheus to scrape it by adding a job to `prometheus.yml`. For example, I set up an exporter for API hits, scraped every 15 seconds.

  ```yaml
  scrape_configs:
    - job_name: 'custom-app'
      static_configs:
        - targets: ['app:8080']
      scrape_interval: 15s
  ```

- **What’s your experience with chaos engineering tools like Chaos Monkey?**  
  I’ve used Chaos Monkey to test resilience in AWS-based Kubernetes clusters by randomly terminating EC2 instances. This helped identify weak auto-scaling configs, leading to a 10% uptime improvement. I paired it with monitoring to observe recovery behavior.

- **How do you use Helm to manage Kubernetes deployments?**  
  I use Helm to package and deploy Kubernetes apps via charts. I run `helm install` to deploy, `helm upgrade` for updates, and `helm rollback` for reversions. For example, I managed a web app chart with custom values for replicas and image tags.

  ```bash
  helm install my-app ./chart --set replicas=3,image.tag=v1.2
  ```

- **What’s the difference between Ansible roles and playbooks? When do you use each?**  
  Playbooks are YAML files defining tasks for execution, while roles are reusable, structured collections of tasks, templates, and vars. I use playbooks for one-off tasks (e.g., install a package) and roles for modular, reusable configs (e.g., setting up a web server).

- **How do you troubleshoot a failed Terraform apply operation?**  
  I check the error message in the console, run `terraform plan` to inspect changes, and verify state with `terraform state list`. Common fixes include correcting resource dependencies or refreshing state (`terraform refresh`). I enable debug logs with `TF_LOG=DEBUG` if needed.

---

### 14. Advanced Topics

- **How do you implement blue-green deployments in a cloud-native environment?**  
  I set up two identical environments (blue and green) in Kubernetes or AWS ECS. I deploy updates to the green environment, test it, then switch traffic using an ALB or Ingress. After validation, I decommission blue, ensuring zero downtime.

  ```yaml
  # Example Ingress for traffic switch
  apiVersion: networking.k8s.io/v1
  kind: Ingress
  metadata:
    name: app
  spec:
    rules:
    - host: app.example.com
      http:
        paths:
        - path: /
          pathType: Prefix
          backend:
            service:
              name: green-service
              port:
                number: 80
  ```

- **What’s your approach to capacity planning for a growing application?**  
  I analyze historical metrics (CPU, memory, traffic) to forecast demand, set baselines with CloudWatch, and simulate load tests. I plan for 20-30% headroom, using auto-scaling groups in AWS or HPA in Kubernetes, and review quarterly to adjust.

- **How do you use chaos engineering to test system resilience?**  
  I inject failures (e.g., pod terminations, network delays) using tools like Chaos Mesh in Kubernetes, monitor recovery with Prometheus, and document weaknesses. For example, I simulated EC2 failures to test auto-scaling, improving recovery time by 15%.

- **What’s the role of service meshes (e.g., Istio, Linkerd) in reliability?**  
  Service meshes manage inter-service communication, providing observability (tracing), traffic control (retries, timeouts), and security (mTLS). They enhance reliability by isolating failures and balancing loads, like when I used Istio to reduce retry failures by 10%.

- **How do you handle stateful workloads in a containerized environment?**  
  I use StatefulSets in Kubernetes for stable pod identities, PersistentVolumes for data storage, and backup tools like Velero. I configure leader-election for DBs and ensure failover with multi-AZ setups in AWS, minimizing data loss.

---

### 15. Behavioral Questions

- **Tell me about a time you made a mistake in production. How did you handle it?**  
  I once misconfigured a Jenkins pipeline, deploying an outdated image, causing a 30-minute outage. I rolled back using `kubectl rollout undo`, communicated impact to stakeholders, and added a pre-deployment image check, preventing recurrence.

- **How do you stay calm under pressure during a major outage?**  
  I focus on triage: acknowledge the issue, gather data (logs, metrics), and prioritize fixes. I communicate clearly with the team and avoid panic by following a structured incident response checklist, keeping stress in check.

- **Can you describe a situation where you improved a process that wasn’t working well?**  
  Our manual server patching took hours weekly. I automated it with Ansible playbooks to apply updates across EC2 instances, reducing time to 30 minutes and errors by 90%, measured by patch compliance reports.

  ```yaml
  - name: Update servers
    hosts: all
    tasks:
      - name: Apply updates
        yum:
          name: '*'
          state: latest
  ```

- **What’s the most challenging technical problem you’ve solved, and how did you do it?**  
  A Kubernetes cluster was crashing under load. I used `kubectl top` and Prometheus to find memory leaks, adjusted pod limits, and scaled nodes with Terraform, restoring stability in 2 hours, confirmed by zero crashes post-fix.

- **How do you keep your skills sharp in a fast-evolving field like DevOps?**  
  I experiment with new tools on personal projects, take Udemy courses, read blogs like AWS News, and participate in forums like Stack Overflow. I also attend meetups to discuss trends like GitOps or chaos engineering.

---