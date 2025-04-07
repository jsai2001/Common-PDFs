
---

## SRE Troubleshooting

### 1. General Troubleshooting Approach

- **What’s your step-by-step process for troubleshooting a production issue?**  
  1) Reproduce or confirm the issue, 2) Gather data (logs, metrics), 3) Hypothesize causes, 4) Test hypotheses (e.g., restart, rollback), 5) Implement fix, 6) Verify resolution, 7) Document findings.

- **How do you prioritize what to investigate when multiple systems are failing at once?**  
  I prioritize based on impact—customer-facing systems first, then revenue-critical, then internal tools—using severity levels (e.g., Sev1 vs. Sev3) and dependency graphs to focus on upstream failures.

- **How do you differentiate between a symptom and the root cause of an issue?**  
  Symptoms are visible effects (e.g., 500 errors); root causes are underlying triggers (e.g., DB failure). I trace back through logs, metrics, and dependencies to separate effect from origin.

- **What do you do when you don’t have enough information to diagnose a problem?**  
  I enable verbose logging, add temporary metrics (e.g., CloudWatch custom metrics), reproduce in a test environment, or consult runbooks/team for historical context.

- **How do you decide when to escalate an issue versus continuing to troubleshoot it yourself?**  
  I escalate if it’s outside my expertise (e.g., vendor issue), time-critical beyond my resolution speed, or requires higher access (e.g., AWS root account), otherwise I dig deeper.

---

### 2. Application and Service Issues

- **An application is returning 500 Internal Server Errors. How do you troubleshoot it?**  
  I check app logs (`tail -f /var/log/app.log`), verify server health (`top`, `netstat`), and test endpoints (`curl -v`). I look for exceptions, DB timeouts, or misconfigs, then restart or rollback if needed.

- **A web service is responding slowly. What steps do you take to identify the bottleneck?**  
  I check latency metrics (CloudWatch), profile CPU/memory (`top`), review DB query times (`EXPLAIN`), and trace requests (X-Ray) to pinpoint app, network, or DB slowdowns.

- **You notice an application is crashing intermittently. How do you pinpoint the cause?**  
  I analyze logs for patterns (`grep "error" log`), monitor resource usage (`vmstat`), and use `strace` or debug flags to catch crashes, isolating triggers like race conditions or memory leaks.

- **A service stops responding after a new deployment. What’s your troubleshooting approach?**  
  I compare configs pre/post-deploy (`diff`), check logs (`kubectl logs` or app logs), verify resource limits, and rollback with `kubectl rollout undo` or redeploy a known-good version.

- **How do you troubleshoot an application that’s consuming excessive CPU or memory?**  
  I use `top` or `htop` to identify the process, check `pmap` for memory maps, analyze app logs for loops/leaks, and adjust limits or optimize code (e.g., reduce threads).

---

### 3. Cloud Infrastructure (e.g., AWS, GCP, Azure)

- **An AWS EC2 instance is unreachable. What do you check first?**  
  I run `aws ec2 describe-instances` to check status, verify security groups (`aws ec2 describe-security-groups`), and inspect VPC routing, then reboot if stopped (`aws ec2 reboot-instances`).

  ```bash
  aws ec2 describe-instances --instance-ids i-1234567890abcdef0
  ```

- **A cloud-based application is experiencing latency spikes. How do you investigate?**  
  I check CloudWatch metrics (latency, CPU), review ALB logs, test network (`ping`, `traceroute`), and scale resources or optimize DB queries based on findings.

- **How do you troubleshoot a failed Terraform deployment in a cloud environment?**  
  I run `terraform plan` to spot diffs, check `terraform apply` logs for errors (e.g., IAM perms), validate syntax (`terraform validate`), and fix or import resources if needed.

  ```bash
  terraform plan -out=tfplan
  ```

- **An RDS database instance is timing out. What steps do you take to resolve it?**  
  I check CloudWatch for CPU/IOPS, run `SHOW PROCESSLIST` (MySQL) for slow queries, increase instance size (`aws rds modify-db-instance`), and tune params (e.g., `max_connections`).

- **A load balancer is not distributing traffic correctly. How do you diagnose the issue?**  
  I use `aws elbv2 describe-target-health` to check target status, verify ALB rules, test listener ports (`curl`), and ensure backend instances are in service.

  ```bash
  aws elbv2 describe-target-health --target-group-arn arn:aws:elasticloadbalancing:region:account-id:targetgroup/my-tg/12345
  ```

---

### 4. Containers and Kubernetes

- **A Kubernetes pod is stuck in a Pending state. What do you check?**  
  I run `kubectl describe pod <name>` for events (e.g., insufficient resources), check node capacity (`kubectl get nodes`), and verify quotas or scheduling issues.

  ```bash
  kubectl describe pod my-pod
  ```

- **How do you troubleshoot a container that’s stuck in CrashLoopBackOff?**  
  I use `kubectl logs <pod>` for crash details, `kubectl describe pod <pod>` for exit codes, exec in (`kubectl exec -it`) if possible, and fix image/config errors.

- **A Kubernetes deployment is failing with an "ImagePullBackOff" error. What’s your next step?**  
  I run `kubectl describe pod <name>` to check image name/auth, verify registry access (`docker pull`), and update secrets or image tags in the YAML.

- **You notice high latency between microservices in a Kubernetes cluster. How do you investigate?**  
  I check `kubectl top` for resource usage, use `kubectl exec` to test network (`ping`), review service endpoints (`kubectl describe svc`), and enable tracing (e.g., Jaeger).

- **A node in your Kubernetes cluster is marked as NotReady. How do you troubleshoot it?**  
  I run `kubectl describe node <name>` for conditions (e.g., disk pressure), check kubelet logs (`journalctl -u kubelet`), and verify network (`kubectl get pods -A -o wide`).

  ```bash
  kubectl describe node my-node
  ```

---

---

### 5. CI/CD Pipeline Issues

- **A CI/CD pipeline fails midway. How do you identify where it broke?**  
  I check the pipeline logs in Jenkins or GitHub Actions for the last executed step and error messages. I use `Jenkins Console Output` or `Actions Logs` to pinpoint the failing stage (e.g., build, test), then inspect related configs or scripts.

- **Your Jenkins job is stuck in the queue. What do you check to resolve it?**  
  I verify executor availability (`Manage Jenkins > Nodes`), check if agents are offline, and review resource limits (e.g., CPU/memory). I restart the agent or increase executors via `Configure Node` if needed.

- **A deployment pipeline succeeds but the application doesn’t update. How do you troubleshoot?**  
  I confirm the deployment target (e.g., `kubectl get pods` for Kubernetes) matches the pipeline output, check image tags for mismatches, and review rollout status (`kubectl rollout status`). I rollback if needed (`kubectl rollout undo`).

  ```bash
  kubectl get pods -o wide
  ```

- **How do you debug a GitHub Actions workflow that’s failing unexpectedly?**  
  I inspect the workflow logs under the `Actions` tab, enable debug mode with `ACTIONS_RUNNER_DEBUG: true`, and add `echo` statements to trace variables. I test locally with `act` if possible.

  ```yaml
  - name: Debug
    run: echo "DEBUG: ${{ github.sha }}"
  ```

- **A build is taking longer than usual. What steps do you take to optimize or fix it?**  
  I analyze build logs for slow steps, parallelize tasks (e.g., in Jenkins `parallel` block), cache dependencies (`mvn dependency:resolve`), and reduce image sizes. I measure time pre- and post-optimization.

---

### 6. Networking and Connectivity

- **A service can’t connect to an external API. What do you investigate?**  
  I check firewall rules (e.g., security groups), test connectivity with `curl` or `ping`, verify DNS resolution (`nslookup`), and review API logs or status codes for rate limits or auth issues.

  ```bash
  curl -v https://api.example.com
  ```

- **How do you troubleshoot a "connection refused" error between two services?**  
  I confirm the target service is running (`netstat -tuln`), check port accessibility (`telnet host port`), validate security groups/NACLs, and ensure the correct IP/port is used in configs.

- **You’re seeing packet loss between two servers. What tools and steps do you use?**  
  I use `ping` to measure loss, `traceroute` to identify hops, and `mtr` for real-time diagnostics. I check network interfaces (`ifconfig`) and escalate to network team if beyond server scope.

  ```bash
  mtr --report host.example.com
  ```

- **A DNS resolution issue is affecting your application. How do you diagnose it?**  
  I run `nslookup` or `dig` to test DNS, check `/etc/resolv.conf` for nameservers, and verify network connectivity to the DNS server. I flush the cache (`systemd-resolve --flush-caches`) if stale.

  ```bash
  dig example.com
  ```

- **How do you troubleshoot a load balancer that’s dropping requests?**  
  I check health checks (`aws elb describe-target-health`), review LB logs, verify backend instance status, and adjust timeouts or idle settings in the LB config (e.g., ALB attributes).

---

### 7. Monitoring and Observability

- **Alerts are firing, but the system seems fine. How do you determine if it’s a false positive?**  
  I compare alert thresholds with actual metrics (e.g., CloudWatch), check for transient spikes, and validate data sources. I adjust thresholds or add debounce logic if confirmed false.

- **Logs are missing for a critical service. How do you track down the issue?**  
  I verify logging config (e.g., `/etc/logstash.conf`), check disk space (`df -h`), confirm log agent status (`systemctl status`), and test log generation manually.

- **How do you troubleshoot a spike in error rates reported by your monitoring system?**  
  I correlate error spikes with logs (`grep "ERROR" log`), check resource usage (`top`), and review recent changes (`git log`). I isolate the cause (e.g., bad deploy) and rollback if needed.

- **A metric in CloudWatch (or similar) is showing anomalies. What’s your process to investigate?**  
  I drill into the metric’s time range, compare with baselines, check related metrics (e.g., CPU if latency spikes), and cross-reference logs or traces to find the trigger.

- **How do you use distributed tracing to find the source of a performance issue?**  
  I use tools like AWS X-Ray, filter traces by latency, identify slow services or calls, and correlate with logs/metrics to pinpoint bottlenecks (e.g., a DB query).

---

### 8. Database Issues

- **A database query is running slower than expected. How do you troubleshoot it?**  
  I run `EXPLAIN` to analyze the query plan, check indexes, and monitor I/O or CPU usage (`top`). I optimize by adding indexes or rewriting joins.

  ```sql
  EXPLAIN SELECT * FROM orders WHERE customer_id = 123;
  ```

- **How do you diagnose a deadlock in a relational database?**  
  I enable deadlock logging (`innodb_print_all_deadlocks=1` in MySQL), check logs for conflicting transactions, and use `SHOW ENGINE INNODB STATUS` to identify locked rows.

- **A database is rejecting connections. What do you check first?**  
  I verify connection limits (`max_connections`), check `netstat -tuln` for open ports, test with `mysql -h host -u user -p`, and review security groups or firewall rules.

- **You notice high I/O wait times on a database server. How do you resolve it?**  
  I use `iostat` to confirm I/O, optimize queries with indexes, increase disk throughput (e.g., EBS IOPS), or offload reads to a replica.

  ```bash
  iostat -x 1
  ```

- **How do you troubleshoot replication lag in a distributed database setup?**  
  I check replica status (`SHOW SLAVE STATUS` in MySQL), monitor lag metrics, verify network latency (`ping`), and adjust write load or replication threads if needed.

---

---

### 9. Operating System and Server Issues

- **A Linux server is running out of disk space. How do you identify and fix the cause?**  
  I use `df -h` to check disk usage, then `du -sh /*` to pinpoint large directories. If logs (e.g., `/var/log`) are the culprit, I clear old files with `find /var/log -type f -mtime +30 -exec rm {} \;` and set up log rotation with `logrotate`.

  ```bash
  df -h
  du -sh /var/log/*
  ```

- **How do you troubleshoot a process that’s consuming 100% CPU on a server?**  
  I run `top` or `htop` to identify the PID, then `ps aux | grep <PID>` for details. I use `strace -p <PID>` to trace system calls or `perf` for deeper analysis, killing or optimizing the process (e.g., `kill -9 <PID>`) as needed.

  ```bash
  top
  strace -p 12345
  ```

- **A server is unresponsive over SSH. What steps do you take to regain access?**  
  I check AWS EC2 console for status, reboot if hung (`aws ec2 reboot-instances`), or use SSM Session Manager if configured. If hardware-related, I escalate to infra support after confirming network/SSH daemon issues.

- **You see high load averages on a Linux system, but CPU usage is low. What’s your approach?**  
  I run `uptime` to confirm load, then `iostat` or `vmstat` to check I/O wait. High I/O suggests disk bottlenecks, so I use `iotop` to find culprits and mitigate by reducing I/O load or resizing disks.

  ```bash
  uptime
  iostat -x 1 5
  ```

- **How do you diagnose and fix a memory leak on a production server?**  
  I use `free -m` to check memory, `top` to spot processes, and `pmap -x <PID>` to map memory usage. I confirm leaks with `valgrind` if possible, then restart the app or patch the code, adding swap as a temporary fix.

  ```bash
  free -m
  pmap -x 12345
  ```

---

### 10. Security and Access Issues

- **Users report they can’t log in to an application. How do you troubleshoot the authentication failure?**  
  I check app logs for auth errors, verify user creds in the DB, and test connectivity to the auth service (e.g., LDAP). I ensure IAM roles or API keys are valid, resetting credentials if needed.

- **A service is getting "permission denied" errors. What do you check?**  
  I verify file permissions with `ls -l`, check user context with `whoami`, and review service logs. For AWS, I inspect IAM policies with `aws iam get-role-policy` and adjust if restrictive.

- **How do you investigate a potential security breach in a cloud environment?**  
  I review CloudTrail logs for unauthorized API calls, check VPC Flow Logs for unusual traffic, and scan EC2 instances with `ps` or `netstat` for rogue processes, isolating affected resources immediately.

  ```bash
  aws cloudtrail lookup-events --start-time $(date -u -d "1 hour ago" +%s)
  ```

- **An IAM policy change breaks access to an AWS resource. How do you debug it?**  
  I use `aws iam simulate-principal-policy` to test the policy, compare with previous versions in CloudTrail, and revert via CLI (`aws iam update-role-policy`) if needed, validating access post-fix.

- **How do you troubleshoot a certificate expiration issue affecting HTTPS traffic?**  
  I check cert validity with `openssl s_client -connect <domain>:443`, review load balancer configs, and replace the expired cert in ACM or upload a new one, testing with `curl` afterward.

  ```bash
  openssl s_client -connect example.com:443 -servername example.com
  ```

---

### 11. Performance and Scalability

- **An application becomes unresponsive under heavy load. How do you diagnose and mitigate it?**  
  I check CPU/memory with `top`, review logs for errors, and scale up pods/instances via `kubectl scale` or AWS ASG. I tune resource limits and test with load tools like `ab` to confirm stability.

- **How do you troubleshoot a system that’s hitting its connection limit?**  
  I use `netstat -an | grep ESTABLISHED | wc -l` to count connections, check `ulimit` and `sshd_config`, and increase limits (`sysctl -w net.core.somaxconn=1024`) or add load balancing.

  ```bash
  netstat -an | grep ESTABLISHED
  ```

- **A containerized app is throttling CPU usage. What do you investigate?**  
  I run `kubectl top pod` to verify throttling, check resource limits in the pod spec, and adjust `requests/limits` in YAML (e.g., `cpu: "500m"`) to match workload, redeploying to test.

- **How do you identify and resolve a bottleneck in a distributed system?**  
  I use metrics (e.g., latency) to isolate components, trace with X-Ray, and profile DB queries or API calls. I resolve by scaling the bottleneck (e.g., RDS instance) or optimizing code.

- **A service scales up but performance doesn’t improve. What’s your troubleshooting process?**  
  I verify scaling with `kubectl get pods` or AWS console, check downstream dependencies (e.g., DB), and test for contention (e.g., locks). I adjust configs or shard data if needed.

---

### 12. Real-World Scenarios

- **A critical service goes down during a traffic spike. Walk me through your troubleshooting steps.**  
  I check alerts (CloudWatch), scale resources (`kubectl scale deployment`), review logs for errors, and rollback if recent changes caused it (`kubectl rollout undo`), verifying uptime post-fix.

- **You deploy a change, and customers report errors. How do you roll back and investigate?**  
  I rollback with `kubectl rollout undo` or Jenkins revert, check deploy diffs in Git, and analyze logs/metrics to pinpoint the issue (e.g., bad config), documenting findings.

- **A third-party dependency fails, impacting your application. How do you troubleshoot and recover?**  
  I test the dependency with `curl`, mock it locally if possible, and switch to a fallback (e.g., cached data). I monitor recovery and plan redundancy (e.g., multi-provider setup).

- **An automated backup process stops working. How do you find and fix the issue?**  
  I check logs (`/var/log/backup.log`), test the script manually, and verify permissions/cron with `crontab -l`. I fix by correcting paths or creds and retest.

  ```bash
  crontab -l
  /path/to/backup.sh
  ```

- **A cron job fails silently. What tools and steps do you use to debug it?**  
  I check `crontab -l` for syntax, redirect output to a log (`* * * * * /script.sh >> /log 2>&1`), and use `journalctl` or `tail` to trace execution, fixing script errors or env issues.

  ```bash
  * * * * * /script.sh >> /var/log/cron.log 2>&1
  tail -f /var/log/cron.log
  ```

---

---

### 13. Tool-Specific Troubleshooting

- **How do you debug a failed Ansible playbook execution?**  
  I run the playbook with `-vvv` for verbose output to see detailed errors, check the task that failed, and inspect host facts with `ansible -m setup <host>`. I also validate syntax using `ansible-playbook --syntax-check playbook.yml` and rerun with `--start-at-task` to isolate the issue.

  ```bash
  ansible-playbook playbook.yml -vvv
  ```

- **A Prometheus query isn’t returning expected data. How do you troubleshoot it?**  
  I verify the query syntax in the Prometheus UI, check the target’s scrape status (`/targets`), and ensure the metric exists with `curl <prometheus>:9090/api/v1/label/__name__/values`. I also inspect service discovery and relabel configs in `prometheus.yml` for misconfigurations.

  ```bash
  curl http://localhost:9090/api/v1/query?query=up
  ```

- **How do you investigate a Docker container that exits immediately after starting?**  
  I use `docker ps -a` to confirm the container’s status, check exit codes with `docker inspect <container_id> --format='{{.State.ExitCode}}'`, and review logs with `docker logs <container_id>`. I then test the entrypoint/command manually to identify failures.

  ```bash
  docker inspect my-container --format='{{.State.ExitCode}}'
  ```

- **A Helm chart deployment fails with a vague error. What’s your approach?**  
  I run `helm install --dry-run --debug <release> <chart>` to simulate and see detailed output, check `helm get manifest <release>` for applied resources, and use `kubectl describe` on failing objects (e.g., pods) to pinpoint issues like missing secrets or resource limits.

  ```bash
  helm install my-app ./chart --dry-run --debug
  ```

- **How do you troubleshoot a misconfigured Nginx reverse proxy causing 502 errors?**  
  I check Nginx logs (`tail -f /var/log/nginx/error.log`) for upstream errors, test the backend with `curl <backend_url>` from the proxy host, and verify `proxy_pass` in the config (`/etc/nginx/sites-enabled/`). I reload with `nginx -t` and `systemctl reload nginx` after fixes.

  ```bash
  tail -f /var/log/nginx/error.log
  ```

---

### 14. Proactive Troubleshooting

- **How do you identify potential issues before they cause an outage?**  
  I monitor key metrics (e.g., CPU, latency) with CloudWatch/Prometheus, set predictive alerts (e.g., 80% threshold), and run health checks on critical services. I also review trends weekly to catch anomalies early.

- **What’s your process for analyzing logs to spot patterns of failure?**  
  I aggregate logs with tools like ELK or CloudWatch Logs Insights, filter for errors (`grep "ERROR" log.txt`), and use queries to count occurrences over time. I look for spikes or recurring messages indicating root causes.

  ```bash
  aws logs filter-log-events --log-group-name my-app --filter-pattern "ERROR"
  ```

- **How do you use chaos engineering to test and troubleshoot system weaknesses?**  
  I inject failures with tools like Chaos Monkey, targeting pods or AWS instances (e.g., terminate an EC2), then monitor recovery with `kubectl` or CloudWatch. I analyze logs and metrics post-test to fix weaknesses, like adding redundancy.

- **How do you troubleshoot a system that’s degrading slowly over time?**  
  I trend metrics (e.g., memory usage) over weeks with CloudWatch, check logs for resource leaks, and profile processes with `top` or `strace`. I hypothesize causes (e.g., cache growth), test fixes, and validate with performance data.

- **What steps do you take to prevent recurring issues after fixing them once?**  
  I document root causes in a post-mortem, automate fixes (e.g., script cleanup), add monitoring (e.g., CloudWatch alarm), and update playbooks or configs. I also share lessons with the team to enforce best practices.

  ```hcl
  # Terraform example: Add alarm to prevent recurrence
  resource "aws_cloudwatch_metric_alarm" "high_cpu" {
    alarm_name          = "high-cpu-usage"
    comparison_operator = "GreaterThanThreshold"
    threshold           = "80"
    metric_name         = "CPUUtilization"
    namespace           = "AWS/EC2"
  }
  ```

---