### Condensed SRE-Based Questions and Answers

#### 1. SRE Fundamentals

1. **Define SLIs, SLOs, SLAs? Example?**  
   SLI: Measurable metric (latency). SLO: Target (99% requests <200ms). SLA: Contract (99.9% uptime or refund). E.g., SLI = success rate, SLO = 99.5%, SLA = 99.5% or 5% credit.

2. **Role of error budgets? Enforce?**  
   Allow limited downtime (0.1%/quarter) to balance innovation/reliability. Monitor SLIs (CloudWatch), pause releases if exceeded, adjust priorities with team.

3. **Prioritize technical debt vs. features?**  
   High-risk debt (outages) first; else, 20% sprint time for debt. Track with tickets, weigh impact vs. feature value.

4. **Proactive vs. reactive maintenance? Preference?**  
   Proactive: Anticipates issues (capacity planning). Reactive: Post-issue fixes (patches). Prefer proactive to reduce incidents, though reactive needed for surprises.

5. **Measure SRE team success?**  
   Uptime (SLOs), toil reduction (automation), MTTR, team morale. E.g., 99.9% uptime, 50% less toil.

#### 2. System Design and Architecture

6. **Design globally distributed system for millions of requests?**  
   AWS multi-region, Route 53 routing, CloudFront caching, EKS clusters, DynamoDB global tables, auto-scaling.

7. **Fault-tolerant system considerations?**  
   Redundancy (multi-AZ), failover (RDS), retries, circuit breakers, monitoring (CloudWatch).

8. **Ensure data consistency in distributed DB?**  
   Eventual consistency (DynamoDB), strong reads if needed, quorum writes (RDS), timestamp/versioning for conflicts.

9. **CAP theorem’s influence?**  
   Pick 2 of Consistency/Availability/Partition tolerance. Favor AP for user apps (S3), CP for critical data (banking).

10. **Microservices for high availability?**  
    EKS across AZs, ALB, health checks, RDS Multi-AZ, circuit breakers, retries, CloudWatch monitoring.

#### 3. Incident Management

11. **Post-mortem process after outage?**  
    Gather timeline (logs/metrics), find root cause (e.g., resource issue), list factors, propose fixes (auto-scaling), assign tasks, share report in 48hrs.

12. **Root cause with incomplete logs?**  
    Correlate logs/metrics (CloudWatch), check state (`top`), hypothesize (spike timing), test fixes, recreate in staging.

13. **Manage cascading failure?**  
    Isolate services (scale down pods), throttle traffic (ALB), rollback (`kubectl rollout undo`), add capacity, monitor recovery.

14. **Communicate during major incident?**  
    Frequent Slack/email updates: impact, actions, ETA. Post-incident: Summarize cause, next steps, keep non-technical.

15. **Prevent incident escalation?**  
    Saw memory leak (CloudWatch), scaled Auto Scaling group, applied hotfix, avoided outage (<5min downtime).

#### 4. Automation and Tooling

16. **Automate vs. manual processes?**  
    Automate repetitive tasks (deployments) with high ROI; keep complex diagnostics manual. Guided by frequency/time cost.

17. **Idempotent automation scripts?**  
    Check state first (`if ! exists`), use Ansible’s idempotency, test multiple runs.
    ```bash
    if ! grep -q "setting" /etc/config; then echo "setting=true" >> /etc/config; fi
    ```

18. **Automate memory leak detection/remediation?**  
    CloudWatch metric for memory (>90%), Lambda restarts instance/pod, validate with logs.
    ```bash
    aws cloudwatch put-metric-data --metric-name MemoryUsage --namespace App --value 95
    ```

19. **Complex automation task?**  
    Multi-region EKS rollback with Terraform (infra), Shell (`kubectl` checks), Ansible (config revert), tested in staging.

20. **Test/validate automation scripts?**  
    Unit tests (`bats`), staging runs, check idempotency, use dry-run (`ansible-playbook --check`) before prod.

#### 5. Cloud and Infrastructure

21. **Multi-region failover in cloud?**  
    Replicate RDS/S3, use Route 53 failover routing, test with chaos tools (Gremlin) for <min recovery.

22. **Manage cloud costs?**  
    Auto-scale, tag resources, schedule shutdowns, monitor with Cost Explorer, remove unused EBS, maintain SLAs.

23. **IaC in team setting?**  
    Terraform modules, Git PRs, shared S3 state with locking, README docs.
    ```hcl
    terraform { backend "s3" { bucket = "my-tf-state"; key = "state/terraform.tfstate"; region = "us-east-1" } }
    ```

24. **Managed vs. self-hosted services?**  
    Managed (RDS): Easy, less control, costly. Self-hosted (MySQL): Flexible, high effort. Choose by workload/team capacity.

25. **Cloud compliance/governance?**  
    IAM policies, AWS Config audits, CloudTrail logs, Security Hub checks, tag for SOC 2/HIPAA adherence.

#### 6. Containers and Orchestration

26. **Secrets management in Kubernetes?**  
    Kubernetes Secrets (base64), RBAC, AWS Secrets Manager/Vault integration, inject via env/volumes.
    ```yaml
    apiVersion: v1
    kind: Secret
    metadata: { name: my-secret }
    data: { password: cGFzc3dvcmQ= }
    ```

27. **Zero-downtime Kubernetes upgrade?**  
    Upgrade control plane (`eksctl`), drain nodes (`kubectl drain`), roll workers gradually, verify pods (`kubectl get pods`).

28. **Debug CrashLoopBackOff?**  
    `kubectl describe`/`logs`, `exec` for checks, fix config (image/limits) with `kubectl edit`.

29. **Kubernetes vs. serverless?**  
    Kubernetes: Control, portable, complex. Serverless: Simple, auto-scales, cold starts. Pick by workload persistence/cost.

30. **Resource isolation in shared cluster?**  
    Namespaces, `ResourceQuota`/`LimitRange`, NetworkPolicies for pod separation.
    ```yaml
    apiVersion: v1
    kind: ResourceQuota
    metadata: { name: compute-quota, namespace: dev }
    spec: { hard: { requests.cpu: "2", requests.memory: "4Gi" } }
    ```

#### 7. Monitoring and Observability

31. **Monitoring vs. observability? Why matters?**  
    Monitoring: Predefined metrics. Observability: Logs/metrics/traces for unknown issues. Key for fast, complex failure diagnosis.

32. **Design monitoring for distributed app?**  
    Prometheus (metrics), Loki (logs), Jaeger (traces), Grafana dashboards, alerts on SLIs (latency >500ms).

33. **Reduce alerting noise?**  
    Set dynamic thresholds, group alerts, suppress during maintenance, escalate critical issues only.

34. **Tracing for microservices bottlenecks?**  
    Use OpenTelemetry, Jaeger/X-Ray, analyze spans for slow calls (e.g., DB), optimize service/resources.

35. **Database metrics under load?**  
    CPU, memory, disk I/O, query latency, connections, cache hit ratio.

#### 8. Security and Reliability

36. **Harden against DDoS?**  
    AWS WAF rate-limiting, CloudFront caching, Auto Scaling, IP whitelisting, CloudWatch monitoring.
    ```json
    { "Name": "RateLimit", "Action": "BLOCK", "Statement": { "RateBasedStatement": { "Limit": 2000, "AggregateKeyType": "IP" } } }
    ```

37. **Manage certificates?**  
    AWS ACM for HTTPS, ALB integration, HSTS, CloudWatch for expiry, auto-rotate.

38. **Security patch performance regression?**  
    Rollback, measure impact (latency up), analyze logs, tweak configs with vendors, keep security.

39. **Prevent privilege escalation?**  
    Least privilege IAM, MFA, disable root, audit with Config, isolate VPCs, monitor CloudTrail.

40. **Balance security/efficiency?**  
    Automate CI/CD checks, use managed services, prioritize high risks, maintain operational speed.

#### 9. Performance Optimization

41. **Resolve web app bottleneck?**  
    Check `nginx`/CloudWatch, profile with `curl`/New Relic, cache (Redis), optimize queries, scale instances.

42. **Profile app for latency?**  
    Metrics (Prometheus), `strace`/`perf`, `cProfile`, optimize I/O or slow code, retest.

43. **Throughput vs. latency optimization?**  
    Throughput: Parallelism, batching. Latency: Fewer hops, caching. Balance by app needs.

44. **Benchmark tools?**  
    `ab` (HTTP), `sysbench` (CPU/memory), `fio` (disk), `wrk` (concurrency), compare to baselines.

45. **Unexpected resource limits?**  
    `top`/`vmstat`, logs, scale (`kubectl scale`/ASG), fix root cause (queries), adjust limits.

#### 10. Coding and Scripting

46. **Script to monitor disk usage?**  
    ```bash
    THRESHOLD=80
    DISK_USAGE=$(df -h / | awk 'NR==2 {print $5}' | cut -d'%' -f1)
    [ "$DISK_USAGE" -gt "$THRESHOLD" ] && echo "Alert: Disk at ${DISK_USAGE}%"
    ```

47. **Python retry with exponential backoff?**  
    ```python
    import time
    def retry_operation(max_attempts=3):
        for attempt in range(1, max_attempts + 1):
            try:
                print(f"Attempt {attempt}"); raise Exception("Failed")
            except Exception as e:
                if attempt == max_attempts: print("Max attempts"); break
                time.sleep(2 ** attempt); print(f"Retrying: {e}")
    ```

48. **Handle race conditions?**  
    Use `threading.Lock`, test under load, avoid global state to minimize contention.

49. **Maintainable/readable code?**  
    PEP 8, clear names, docstrings, modular functions, Git versioning, unit tests.

50. **Optimized algorithm?**  
    Log-parsing script from O(n²) to O(n) with hash table, cut runtime from 10 to 2min for 100k lines (`time`).

#### 11. Collaboration and Processes

51. **Improve dev code reliability?**  
    Review for logging/errors, add chaos tests, CI reliability checks (crash rates).

52. **Introduce SRE to new team?**  
    Workshop on SLIs/SLOs, simple Prometheus setup, pilot error budget, expand with buy-in.

53. **Disagree with PM on reliability?**  
    Show data (downtime impact), propose phased reliability goals, align on shared metrics.

54. **Review infra code?**  
    Check idempotency, security, Terraform best practices, run `terraform plan`, test in sandbox.

55. **Knowledge transfer on team exit?**  
    Wikis, runbooks, pair programming, Git-recorded processes to avoid silos.

#### 12. Scenario-Based Questions

56. **Critical service down at peak?**  
    Check CloudWatch, verify status (`kubectl get pods`), prioritize fixes by impact.

57. **Investigate memory leak?**  
    `top`/`free`, `dmesg`, profile (`valgrind`), restart service, patch leak long-term.

58. **Feature causing outages?**  
    Rollback (`kubectl rollout undo`), check logs, isolate change (`git diff`), fix/retest.

59. **False positive alerts?**  
    Adjust thresholds (95th percentile), add hysteresis, test with synthetic failures.

60. **Third-party service failure?**  
    Use fallback (cache), queue (SQS), monitor (CloudWatch), retry/degrade until recovery.

#### 13. Tool-Specific Questions

61. **Prometheus exporter for custom metrics?**  
    Python/Go exporter for `/metrics`, scrape via `prometheus.yml`.
    ```yaml
    scrape_configs:
      - job_name: 'custom-app'
        static_configs:
          - targets: ['app:8080']
        scrape_interval: 15s
    ```

62. **Chaos Monkey experience?**  
    Terminated EC2 in Kubernetes, fixed auto-scaling, improved uptime 10%, monitored recovery.

63. **Helm for Kubernetes?**  
    Package charts, `helm install`/`upgrade`/`rollback`. E.g., deploy app with custom replicas.
    ```bash
    helm install my-app ./chart --set replicas=3
    ```

64. **Ansible roles vs. playbooks?**  
    Playbooks: Task scripts. Roles: Reusable task sets. Playbooks for one-offs, roles for modular configs.

65. **Troubleshoot failed Terraform apply?**  
    Check error, `terraform plan`, verify state (`terraform state list`), fix dependencies, debug (`TF_LOG=DEBUG`).

#### 14. Advanced Topics

66. **Blue-green deployments?**  
    Two envs (blue/green), update green, test, switch traffic (ALB/Ingress), decommission blue.
    ```yaml
    apiVersion: networking.k8s.io/v1
    kind: Ingress
    spec:
      rules:
      - host: app.example.com
        http:
          paths:
          - backend: { service: { name: green-service, port: { number: 80 } } }
    ```

67. **Capacity planning?**  
    Forecast via metrics (CloudWatch), load test, set 20-30% headroom, use auto-scaling, review quarterly.

68. **Chaos engineering for resilience?**  
    Inject failures (Chaos Mesh), monitor (Prometheus), fix weaknesses, e.g., EC2 test cut recovery 15%.

69. **Service meshes for reliability?**  
    Manage communication (Istio/Linkerd), add tracing/retries/mTLS, e.g., Istio cut retries 10%.

70. **Stateful workloads in containers?**  
    StatefulSets, PersistentVolumes, Velero backups, leader-election, multi-AZ failover.

#### 15. Behavioral Questions

71. **Production mistake?**  
    Deployed old image (30min outage), rolled back (`kubectl rollout undo`), added image checks.

72. **Stay calm during outage?**  
    Triage, follow checklist, gather data, communicate clearly to avoid panic.

73. **Improve broken process?**  
    Manual patching to Ansible, cut time to 30min, errors by 90%.
    ```yaml
    - name: Update servers
      yum: { name: '*', state: latest }
    ```

74. **Challenging technical problem?**  
    Kubernetes crashes, found leak (`kubectl top`), scaled nodes (Terraform), stable in 2hrs.

75. **Keep DevOps skills sharp?**  
    Projects, Udemy, AWS blogs, Stack Overflow, meetups on GitOps/chaos engineering.