### Condensed SRE Troubleshooting Questions and Answers

#### 1. General Troubleshooting Approach

1. **Step-by-step process for production issue?**  
   Confirm issue, gather logs/metrics, hypothesize, test fixes (restart/rollback), implement, verify, document.

2. **Prioritize multiple failing systems?**  
   Focus on customer-facing, revenue-critical systems first, then internal tools, using severity (Sev1 vs. Sev3) and dependency graphs.

3. **Symptom vs. root cause?**  
   Symptom: Visible effect (500 errors). Root cause: Trigger (DB failure). Trace via logs/metrics/dependencies.

4. **Not enough info to diagnose?**  
   Add verbose logging, custom metrics (CloudWatch), reproduce in test env, check runbooks/team knowledge.

5. **Escalate vs. troubleshoot alone?**  
   Escalate for vendor issues, time-critical cases, or restricted access; otherwise, keep digging.

#### 2. Application and Service Issues

6. **App returns 500 errors?**  
   Check logs (`tail /var/log/app.log`), server health (`top`), endpoints (`curl -v`). Fix exceptions/DB timeouts, restart/rollback.

7. **Web service slow?**  
   Review latency (CloudWatch), CPU/memory (`top`), DB queries (`EXPLAIN`), trace (X-Ray) to find bottleneck.

8. **Intermittent app crashes?**  
   Analyze logs (`grep "error" log`), monitor resources (`vmstat`), use `strace`/debug to catch triggers (leaks/race conditions).

9. **Service unresponsive post-deploy?**  
   Compare configs (`diff`), check logs (`kubectl logs`), verify limits, rollback (`kubectl rollout undo`) or redeploy.

10. **App uses excessive CPU/memory?**  
    `top`/`htop` for process, `pmap` for memory, check logs for leaks, optimize code/limits (e.g., fewer threads).

#### 3. Cloud Infrastructure

11. **AWS EC2 unreachable?**  
    Check status (`aws ec2 describe-instances`), security groups, VPC routing, reboot (`aws ec2 reboot-instances`).
    ```bash
    aws ec2 describe-instances --instance-ids i-1234567890abcdef0
    ```

12. **Cloud app latency spikes?**  
    CloudWatch metrics (CPU/latency), ALB logs, network test (`ping`/`traceroute`), scale/optimize DB.

13. **Failed Terraform deployment?**  
    Run `terraform plan`, check logs for errors (IAM/syntax), validate (`terraform validate`), fix/import resources.
    ```bash
    terraform plan -out=tfplan
    ```

14. **RDS timing out?**  
    CloudWatch CPU/IOPS, `SHOW PROCESSLIST` for queries, upscale (`aws rds modify-db-instance`), tune `max_connections`.

15. **Load balancer misrouting?**  
    Check target health (`aws elbv2 describe-target-health`), ALB rules, listener ports (`curl`), ensure backend is active.
    ```bash
    aws elbv2 describe-target-health --target-group-arn arn:aws:elasticloadbalancing:...
    ```

#### 4. Containers and Kubernetes

16. **Pod stuck in Pending?**  
    `kubectl describe pod` for events (resources/scheduling), check nodes (`kubectl get nodes`), verify quotas.
    ```bash
    kubectl describe pod my-pod
    ```

17. **CrashLoopBackOff container?**  
    `kubectl logs`/`describe pod` for errors/exit codes, `exec` to inspect, fix image/config.

18. **ImagePullBackOff error?**  
    `kubectl describe pod` for image/auth issues, test registry (`docker pull`), update secrets/tags.

19. **High latency between microservices?**  
    `kubectl top` for resources, `exec` to test network (`ping`), check services (`kubectl describe svc`), add tracing (Jaeger).

20. **Node NotReady in cluster?**  
    `kubectl describe node` for conditions, kubelet logs (`journalctl -u kubelet`), verify network (`kubectl get pods -A`).
    ```bash
    kubectl describe node my-node
    ```

#### 5. CI/CD Pipeline Issues

21. **Pipeline fails midway?**  
    Check Jenkins/GitHub Actions logs for last step/error, inspect configs/scripts at failing stage (build/test).

22. **Jenkins job stuck in queue?**  
    Check executors (`Manage Jenkins > Nodes`), agent status, resources, restart agent, or add executors.

23. **Pipeline succeeds, app not updated?**  
    Verify target (`kubectl get pods`), check image tags, rollout status (`kubectl rollout status`), rollback (`kubectl rollout undo`).
    ```bash
    kubectl get pods -o wide
    ```

24. **Debug GitHub Actions failure?**  
    Check `Actions` logs, enable debug (`ACTIONS_RUNNER_DEBUG: true`), trace variables (`echo`), test locally (`act`).
    ```yaml
    - run: echo "DEBUG: ${{ github.sha }}"
    ```

25. **Slow build?**  
    Log slow steps, parallelize tasks (Jenkins `parallel`), cache dependencies (`mvn`), shrink images, measure time.

#### 6. Networking and Connectivity

26. **Service can’t reach external API?**  
    Check firewall (security groups), test (`curl`/`ping`), verify DNS (`nslookup`), review API limits/auth.
    ```bash
    curl -v https://api.example.com
    ```

27. **Connection refused between services?**  
    Confirm service runs (`netstat -tuln`), test port (`telnet`), check security groups/NACLs, verify IP/port.

28. **Packet loss between servers?**  
    Measure with `ping`, trace with `traceroute`/`mtr`, check interfaces (`ifconfig`), escalate if network issue.
    ```bash
    mtr --report host.example.com
    ```

29. **DNS resolution issue?**  
    Test with `nslookup`/`dig`, check `/etc/resolv.conf`, verify DNS server, flush cache (`systemd-resolve --flush-caches`).
    ```bash
    dig example.com
    ```

30. **Load balancer dropping requests?**  
    Check health checks (`aws elb describe-target-health`), LB logs, backend status, adjust timeouts/settings.

#### 7. Monitoring and Observability

31. **False positive alerts?**  
    Compare thresholds with metrics (CloudWatch), check spikes, adjust or add debounce if false.

32. **Missing logs for service?**  
    Verify config (`/etc/logstash.conf`), disk space (`df -h`), agent status (`systemctl status`), test logging.

33. **Spike in error rates?**  
    Correlate logs (`grep "ERROR" log`), resources (`top`), changes (`git log`), rollback if deploy-related.

34. **CloudWatch metric anomaly?**  
    Drill into time range, compare baselines, check related metrics, cross-reference logs/traces for trigger.

35. **Distributed tracing for performance?**  
    Use X-Ray, filter high-latency traces, identify slow services/calls, correlate logs/metrics.

#### 8. Database Issues

36. **Slow database query?**  
    `EXPLAIN` for plan, check indexes, monitor I/O (`top`), add indexes/rewrite joins.
    ```sql
    EXPLAIN SELECT * FROM orders WHERE customer_id = 123;
    ```

37. **Diagnose DB deadlock?**  
    Enable logging (`innodb_print_all_deadlocks`), check logs, use `SHOW ENGINE INNODB STATUS` for locked rows.

38. **DB rejecting connections?**  
    Check `max_connections`, ports (`netstat -tuln`), test (`mysql -h host`), verify security groups.

39. **High DB I/O wait times?**  
    Confirm with `iostat`, optimize queries/indexes, increase disk (EBS IOPS), offload to replica.
    ```bash
    iostat -x 1
    ```

40. **Replication lag in distributed DB?**  
    Check status (`SHOW SLAVE STATUS`), lag metrics, network (`ping`), adjust write load/threads.

#### 9. Operating System and Server Issues

41. **Linux server low disk space?**  
    `df -h`, `du -sh /*` to find large files, clear logs (`find /var/log -mtime +30 -exec rm {} \;`), set `logrotate`.
    ```bash
    df -h
    du -sh /var/log/*
    ```

42. **Process at 100% CPU?**  
    `top`/`htop` for PID, `ps aux | grep <PID>`, `strace -p <PID>` or `perf`, kill/optimize (`kill -9 <PID>`).
    ```bash
    top
    strace -p 12345
    ```

43. **Server unresponsive over SSH?**  
    Check EC2 console, reboot (`aws ec2 reboot-instances`), use SSM, escalate if hardware/network issue.

44. **High load, low CPU?**  
    Confirm load (`uptime`), check I/O (`iostat`/`vmstat`), find culprits (`iotop`), reduce I/O or resize disk.
    ```bash
    uptime
    iostat -x 1 5
    ```

45. **Memory leak on server?**  
    `free -m`, `top` for processes, `pmap -x <PID>`, confirm with `valgrind`, restart/patch, add swap.
    ```bash
    free -m
    pmap -x 12345
    ```

#### 10. Security and Access Issues

46. **Users can’t log in?**  
    Check logs for auth errors, verify DB creds, test auth service (LDAP), reset credentials if needed.

47. **Service "permission denied"?**  
    Verify permissions (`ls -l`), user (`whoami`), logs, check IAM (`aws iam get-role-policy`), adjust policy.
    ```bash
    aws iam get-role-policy --role-name my-role --policy-name my-policy
    ```

48. **Potential cloud security breach?**  
    Review CloudTrail for rogue API calls, VPC Flow Logs for traffic, scan EC2 (`ps`/`netstat`), isolate resources.
    ```bash
    aws cloudtrail lookup-events --start-time $(date -u -d "1 hour ago" +%s)
    ```

49. **IAM policy breaks access?**  
    Test with `aws iam simulate-principal-policy`, check CloudTrail, revert (`aws iam update-role-policy`), validate.

50. **Certificate expiration issue?**  
    Check cert (`openssl s_client -connect <domain>:443`), replace in ACM, test (`curl`).
    ```bash
    openssl s_client -connect example.com:443
    ```

#### 11. Performance and Scalability

51. **App unresponsive under load?**  
    `top` for CPU/memory, check logs, scale (`kubectl scale`/ASG), tune limits, test with `ab`.

52. **System hits connection limit?**  
    Count connections (`netstat -an | grep ESTABLISHED`), check `ulimit`, increase (`sysctl net.core.somaxconn`), add LB.
    ```bash
    netstat -an | grep ESTABLISHED
    ```

53. **Container CPU throttling?**  
    `kubectl top pod`, check limits, adjust `cpu: "500m"` in YAML, redeploy.
    ```yaml
    resources: { limits: { cpu: "500m" } }
    ```

54. **Distributed system bottleneck?**  
    Metrics for component, trace (X-Ray), profile queries/APIs, scale/optimize (e.g., RDS).

55. **Scaled service, no performance gain?**  
    Verify scaling (`kubectl get pods`), check dependencies (DB), test contention (locks), shard/adjust.

#### 12. Real-World Scenarios

56. **Critical service down during spike?**  
    Check CloudWatch, scale (`kubectl scale`), review logs, rollback (`kubectl rollout undo`), verify uptime.

57. **Errors post-change deploy?**  
    Rollback (`kubectl rollout undo`), check Git diffs, analyze logs/metrics, document cause.

58. **Third-party failure impact?**  
    Test with `curl`, mock locally, use fallback (cache), plan redundancy, monitor recovery.

59. **Automated backup fails?**  
    Check logs (`/var/log/backup.log`), test script, verify perms/cron (`crontab -l`), fix paths/creds.
    ```bash
    crontab -l
    ```

60. **Silent cron job failure?**  
    Check `crontab -l`, log output (`>> /log 2>&1`), trace with `journalctl`/`tail`, fix script/env.
    ```bash
    * * * * * /script.sh >> /var/log/cron.log 2>&1
    tail -f /var/log/cron.log
    ```

#### 13. Tool-Specific Troubleshooting

61. **Debug failed Ansible playbook?**  
    Run with `-vvv`, check failed task, inspect facts (`ansible -m setup`), validate syntax (`--syntax-check`).
    ```bash
    ansible-playbook playbook.yml -vvv
    ```

62. **Prometheus query missing data?**  
    Test query in UI, check scrape (`/targets`), verify metric (`curl /api/v1/label/__name__/values`), fix `prometheus.yml`.
    ```bash
    curl http://localhost:9090/api/v1/query?query=up
    ```

63. **Docker container exits immediately?**  
    Check status (`docker ps -a`), exit code (`docker inspect`), logs (`docker logs`), test entrypoint manually.
    ```bash
    docker inspect my-container --format='{{.State.ExitCode}}'
    ```

64. **Helm chart vague failure?**  
    Simulate (`helm install --dry-run --debug`), check manifest (`helm get manifest`), `kubectl describe` failing objects.
    ```bash
    helm install my-app ./chart --dry-run --debug
    ```

65. **Nginx 502 errors?**  
    Check logs (`tail /var/log/nginx/error.log`), test backend (`curl`), verify `proxy_pass`, reload (`nginx -t`).
    ```bash
    tail -f /var/log/nginx/error.log
    ```

#### 14. Proactive Troubleshooting

66. **Spot issues pre-outage?**  
    Monitor metrics (CloudWatch/Prometheus), set predictive alerts (80%), run health checks, review trends weekly.

67. **Analyze logs for failure patterns?**  
    Aggregate (ELK/CloudWatch Insights), filter errors (`grep "ERROR"`), query occurrence counts for spikes.
    ```bash
    aws logs filter-log-events --log-group-name my-app --filter-pattern "ERROR"
    ```

68. **Chaos engineering for weaknesses?**  
    Inject failures (Chaos Monkey), monitor recovery (`kubectl`/CloudWatch), fix issues (e.g., redundancy).

69. **System degrading slowly?**  
    Trend metrics (CloudWatch), check logs for leaks, profile (`top`/`strace`), test fixes, validate performance.

70. **Prevent recurring issues?**  
    Post-mortem, automate fixes, add alerts (CloudWatch), update playbooks, share lessons.
    ```hcl
    resource "aws_cloudwatch_metric_alarm" "high_cpu" {
      alarm_name = "high-cpu-usage"
      comparison_operator = "GreaterThanThreshold"
      threshold = "80"
      metric_name = "CPUUtilization"
    }
    ```