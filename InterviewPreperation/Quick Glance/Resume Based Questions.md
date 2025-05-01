### Condensed Resume-Based Questions and Answers

#### 1. General DevOps and SRE Concepts

1. **DevOps vs. SRE? Role alignment?**  
   DevOps: Collaboration for fast delivery. SRE: DevOps with software engineering for reliability (SLIs/SLOs). My role aligns by automating for reliability and reducing toil, supporting rapid development.

2. **Define system reliability? Metrics?**  
   Reliability: Consistent function under load. Metrics: Availability (99.9%), latency (<200ms), error rate. Tracked via CloudWatch/Prometheus.

3. **Toil and reduction?**  
   Toil: Manual, repetitive tasks. Reduced via Jenkins CI/CD, Shell scripting, Terraform, saving hours weekly.

4. **Balance velocity/reliability?**  
   Use error budgets (e.g., 0.1% downtime), monitor with alerts, automate rollbacks to maintain stability while speeding development.

5. **On-call and incident response?**  
   Monitor via CloudWatch/PagerDuty. Process: Acknowledge, triage, diagnose (logs/metrics), mitigate (scale/rollback), document. Communicate clearly.

#### 2. Cloud (AWS)

6. **Highly available AWS architecture?**  
   EC2 Auto Scaling across AZs, RDS Multi-AZ, S3 for storage, EKS for Kubernetes, CloudWatch for monitoring.

7. **Secure AWS VPC?**  
   Private/public subnets, NAT Gateways, security groups (e.g., port 22 only), VPC Flow Logs, IAM roles, NACLs.

8. **Terraform for AWS? Case?**  
   Define infra as code (EC2/S3/VPC). Automated EKS cluster setup, cut provisioning from hours to minutes.
   ```hcl
   resource "aws_eks_cluster" "example" { name = "my-cluster"; ... }
   ```

9. **EKS experience? Scaling?**  
   Deployed EKS with `kubectl`/Terraform. Scaled via HPA (CPU/memory) and Auto Scaling node groups, monitored with CloudWatch.

10. **CloudWatch custom metrics/alarms?**  
    Push metrics (e.g., `ContainerRestarts`) via CLI, set alarms (e.g., >5 restarts/10min) via console/Terraform.
    ```bash
    aws cloudwatch put-metric-data --metric-name ContainerRestarts --namespace MyApp --value 3
    ```

#### 3. CI/CD Pipelines

11. **CI/CD pipeline for 25% faster deployment?**  
    Jenkins pipeline with parallel stages, cached dependencies, optimized Docker builds, GitHub Actions for PRs, cut time from 20 to 15 minutes.

12. **Zero downtime deployments?**  
    Blue-green via AWS ALB, Kubernetes rolling updates (`maxUnavailable=0`) to keep pods live.

13. **Jenkins scripted vs. declarative pipelines?**  
    Scripted: Groovy, flexible, complex. Declarative: Structured, readable. Prefer declarative for clarity, scripted for custom logic.

14. **Handle pipeline failures/rollbacks?**  
    Jenkins: `try-catch` or `post` for rollbacks (redeploy old image). GitHub Actions: Rollback job with `if: failure()`.

15. **Security checks in CI/CD?**  
    SAST (SonarQube) in build, DAST (OWASP ZAP) in test. Jenkins: Add steps; GitHub Actions: Use marketplace actions.

#### 4. Containerization (Docker and Kubernetes)

16. **Docker containerization at NielsenIQ? Challenges?**  
    Converted apps to Docker, built images, used registry. Fixed dependency conflicts with multi-stage builds, trained team to overcome resistance.

17. **Optimize Docker images?**  
    Multi-stage builds, early caching, `.dockerignore`, trusted base images for lean, fast images.

18. **Manage Kubernetes deployments? Scaling?**  
    Use YAML, `kubectl apply`. Scale with HPA (CPU/memory), set limits/requests (500m CPU), monitor with `kubectl top`.

19. **Troubleshoot crashing Kubernetes pod?**  
    `kubectl describe pod` for events, `logs` for errors, `exec` for process checks, review resource limits, cluster logs.

20. **Doctor-Patient System with Docker Compose?**  
    Orchestrated PHP app, MySQL DB, frontend with `docker-compose.yml`, linked via network, set DB dependency.
    ```yaml
    services:
      app: { image: php:7.4-apache, ports: ["8080:80"], depends_on: [db] }
      db: { image: mysql:5.7, environment: { MYSQL_ROOT_PASSWORD: example } }
    ```

#### 5. Automation and Scripting

21. **Python/Shell script for container management?**  
    Shell script cleaned stopped containers/dangling images, freed disk/memory.
    ```bash
    docker ps -a -q --filter "status=exited" | xargs -r docker rm
    docker images -q --filter "dangling=true" | xargs -r docker rmi
    ```

22. **Python vs. Shell scripting?**  
    Shell: Quick system tasks (files/commands). Python: Complex logic, data processing, libraries (e.g., boto3).

23. **30% performance improvement via automation?**  
    Slow container restarts (10min). Python script monitored/restarted pods, cut to 7min. Measured via response times.

24. **Efficient algorithms for automation?**  
    Use hashes for lookups, avoid nested loops, cache results, profile with `time`/cProfile, test with real data.

25. **Ansible vs. Terraform?**  
    Ansible: Config management (packages), agentless. Terraform: Infra provisioning (AWS), declarative. Used Ansible for server setup, Terraform for EKS.

#### 6. Databases (Snowflake SQL)

26. **Real-time data aggregation challenges?**  
    High latency, data staleness. Fixed with table partitioning, optimized joins, micro-batches (under 5min delay).

27. **Optimize Snowflake SQL? Example?**  
    Cluster tables, use materialized views, avoid full scans. E.g., daily sales query:
    ```sql
    SELECT DATE_TRUNC('day', order_date) AS day, SUM(order_amount) AS total_sales
    FROM orders WHERE order_date >= '2023-01-01' GROUP BY day;
    ```

28. **Window functions for 45% accuracy?**  
    Calculate across rows (e.g., `RANK()`). Replaced subqueries for deduplication/ranking, accuracy from 55% to 100%.

29. **Handle large Snowflake datasets? Cost?**  
    Partition on time/keys, cluster, auto-scale. Cost: Monitor resources, suspend warehouses, prune columns.

30. **Freemarker Templates use case?**  
    Generated dynamic SQL for reports (e.g., sales by region), parameterized filters, cut query maintenance.

#### 7. Monitoring and Observability

31. **CloudWatch for reliability? Custom solution?**  
    Track CPU/latency, set alarms. Built EKS pod restart monitor, pushed `PodRestarts`, alerted on >threshold.

32. **Alerting thresholds? Avoid fatigue?**  
    Set via baselines (95th percentile), test loads, prioritize critical alerts, use escalation, suppress noise.

33. **Debug distributed system performance?**  
    Check metrics (CPU/latency), logs, trace with X-Ray, isolate (e.g., DB), test fixes, verify with metrics.

34. **Other monitoring tools vs. CloudWatch?**  
    Prometheus: Rich queries, Kubernetes. Grafana: Better visuals. CloudWatch: Simpler, costlier, less flexible.

35. **Correlate logs/metrics/traces?**  
    Align timestamps, use CloudWatch Insights/ELK, find patterns, test hypotheses with experiments.

#### 8. Linux Administration

36. **Tune Linux performance? Example?**  
    Adjust kernel (`vm.swappiness`), I/O, CPU governors. E.g., boosted web server throughput with `tcp_max_syn_backlog`.

37. **Troubleshoot high CPU/memory?**  
    Use `top`/`htop`, `vmstat`, `strace`/`perf`, kill/limit processes, tweak configs.

38. **Secure Linux environment?**  
    Minimal packages, SSH keys, `ufw`/`iptables`, updates, AppArmor/SELinux.

39. **Systemd/cron use case?**  
    Systemd: Managed monitoring service. Cron: Scheduled backups (`0 0 * * * tar -czf /backup/data.tar.gz /data`).

40. **Manage disk space?**  
    Monitor (`df`/`du`), alert at 80%, clean logs (`find`/`rm`), expand with `resize2fs`.

#### 9. Team Collaboration and Mentorship

41. **Mentored juniors? Topics/progress?**  
    Taught CI/CD, Docker, AWS. Progress: Independent deployments, fewer errors (5 to 1/week), completed Dockerfiles.

42. **Handle dev/ops conflicts?**  
    Discuss openly, use data (error rates), propose compromises (staged rollouts), align via shared metrics.

43. **Document for knowledge sharing?**  
    Wikis (Confluence), repo READMEs, post-mortems with pipelines/diagrams/troubleshooting guides.

44. **Cloud-native with Release Management?**  
    Migrated app to EKS, used Terraform/Jenkins for releases, cut cycles from days to hours, confirmed by feedback.

#### 10. Problem-Solving and Scenarios

45. **Fix misconfigured Kubernetes pod?**  
    `kubectl describe`/`logs`, edit YAML (`kubectl edit deployment`) or rollback (`kubectl rollout undo`), verify pods.

46. **Latency spikes in app?**  
    Check CloudWatch, logs, `kubectl top`, trace with X-Ray, scale/optimize DB/code, confirm via latency.

47. **Optimize slow CI/CD pipeline?**  
    Parallelize tasks, cache dependencies, use small Docker images, aim for 20-30% faster runs.

48. **EC2 memory crash?**  
    `top`/`free -m`, check `dmesg`, logs, upscale instance (`aws ec2 modify`), add swap, verify uptime.

49. **AWS disaster recovery plan?**  
    Multi-region RDS/S3, EBS backups, Route 53 failover. Test RTO/RPO (<1hr downtime).

#### 11. Projects and Achievements

50. **Real-time data aggregation? 20% efficiency?**  
    Snowflake SQL, partitioned tables, tasks. Cut batch time from 10 to 8min with optimized queries/cache.

51. **30% deployment misalignment reduction?**  
    Tracked Jenkins failures, synced images with Terraform, dropped errors from 10 to 7/100 deployments.

52. **Doctor-Patient System security/scalability?**  
    HTTPS, MySQL auth, input validation. Docker Compose, tested 100 users, kept <500ms response.

53. **Maven workflow challenge?**  
    Dependency conflicts. Centralized versions in parent POM, cut failures from 15% to 2%, aligned team.

54. **Automation time savings?**  
    Terraform for AWS, cut setup from 2hr to 20min/env, saved 10+hr/week, verified by logs.

#### 12. Certifications and Learning

55. **DevOps Beginners to Advanced cert impact?**  
    Improved CI/CD, container, cloud skills, enabled confident Jenkins/Docker deployments.

56. **Jenkins, From Zero to Hero learnings?**  
    Parallel stages, shared libraries. Split Jenkinsfile into modules, sped edits by 50%.
    ```groovy
    @Library('my-lib') _
    pipeline { agent any; stages { stage('Build') { steps { buildApp() } } } }
    ```

57. **Stay updated on DevOps trends?**  
    Read DevOps.com, watch webinars, test GitHub tools, take Udemy courses, join r/devops for trends like GitOps.