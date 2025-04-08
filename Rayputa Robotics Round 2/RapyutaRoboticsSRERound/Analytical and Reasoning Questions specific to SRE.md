
---

**1. How would you determine the root cause of a sudden spike in application latency across a distributed system?**  
- **Steps:**  
  1. Check monitoring dashboards (e.g., Prometheus, CloudWatch) for latency metrics across services, identifying the spike’s timing and affected components.  
  2. Correlate with logs (e.g., via ELK or Splunk) to spot errors or slow queries.  
  3. Trace requests using distributed tracing (e.g., Jaeger, Zipkin) to pinpoint bottlenecks (e.g., database, API, or network).  
  4. Review recent changes (deployments, config updates) via CI/CD logs or version control.  
  5. Test hypotheses: Check resource utilization (CPU, memory, I/O), network latency (e.g., ping, traceroute), or external dependencies (e.g., third-party APIs).  
- **Outcome:** Narrow down to a specific service, resource, or external factor, then validate with targeted tests.

---

**2. If a production deployment fails intermittently, what steps would you take to isolate the issue without rolling back immediately?**  
- **Steps:**  
  1. Reproduce the failure in a staging environment with similar load and configuration.  
  2. Enable verbose logging and metrics collection to capture failure conditions.  
  3. Use canary testing or feature flags to isolate the new deployment’s impact on a subset of traffic.  
  4. Compare successful vs. failed requests (e.g., headers, payloads) to identify patterns.  
  5. Check infrastructure health (e.g., instance status, network connectivity) during failures.  
- **Outcome:** Identify whether the issue is code-related, environmental, or load-dependent, allowing targeted fixes.

---

**3. How do you decide when a system needs to scale horizontally versus vertically, and what factors influence your choice?**  
- **Factors:**  
  - **Workload Type:** CPU-bound tasks (e.g., computation) favor vertical scaling; I/O-bound or stateless apps (e.g., web servers) suit horizontal scaling.  
  - **Resource Limits:** Vertical scaling is constrained by instance size limits (e.g., AWS max vCPUs); horizontal scaling depends on orchestration capacity (e.g., Kubernetes).  
  - **Cost:** Vertical scaling may be cheaper short-term; horizontal scaling optimizes long-term resilience.  
  - **Availability:** Horizontal scaling improves redundancy and fault tolerance.  
  - **Latency:** Vertical scaling reduces overhead; horizontal scaling may introduce network delays.  
- **Decision:** Scale vertically for quick fixes or single-threaded apps; scale horizontally for high availability and distributed loads.

---

**4. Given a system with 99.9% uptime, how would you calculate the acceptable downtime per month, and what does this mean for incident response?**  
- **Calculation:**  
  - Monthly minutes: 30 days * 24 hours * 60 minutes = 43,200 minutes.  
  - 99.9% uptime = 0.1% downtime = 0.001 * 43,200 = 43.2 minutes/month.  
- **Implications:**  
  - You have ~43 minutes of allowable downtime, so incidents must be resolved within this window to meet the SLO.  
  - Prioritize rapid detection (e.g., alerts within 5 minutes) and mitigation (e.g., <30-minute MTTR) to stay within budget.  
  - Frequent small incidents erode this buffer, necessitating proactive fixes.

---

**5. If two services depend on each other and both are failing, how would you determine which one is the source of the problem?**  
- **Steps:**  
  1. Check timelines in logs and metrics to see which service failed first.  
  2. Test each service independently (e.g., mock the other’s API) to isolate behavior.  
  3. Review dependency calls (e.g., HTTP errors, timeouts) to identify failure propagation.  
  4. Analyze resource usage (e.g., CPU, memory) for signs of overload in one service.  
  5. Simulate load on one service while keeping the other idle to observe cascading effects.  
- **Outcome:** The service failing first or independently is likely the root cause.

---

**6. How would you analyze whether a CI/CD pipeline slowdown is due to infrastructure, code, or external dependencies?**  
- **Steps:**  
  1. Compare historical pipeline runtimes (e.g., GitHub Actions logs) to pinpoint the slowdown’s onset.  
  2. Break down job durations (e.g., build, test) to isolate slow steps.  
  3. Check infrastructure metrics (e.g., runner CPU, network latency) for bottlenecks.  
  4. Review code changes (e.g., larger artifacts, new tests) in recent commits.  
  5. Test external calls (e.g., `curl` to APIs, package registries) for delays.  
- **Outcome:** Pinpoint the slowest component and validate with targeted tests (e.g., scale runners, mock externals).

---

**7. If a Kubernetes cluster is running out of resources, how would you decide which workloads to prioritize or evict?**  
- **Steps:**  
  1. Check `kubectl top pods` and `kubectl describe nodes` for resource usage.  
  2. Review pod priorities (e.g., `priorityClassName`)—higher-priority pods stay.  
  3. Assess criticality: Keep production workloads over dev/test.  
  4. Evaluate resource requests/limits: Evict over-allocating or non-essential pods.  
  5. Use taints/taints or node draining if targeting specific nodes.  
- **Decision:** Prioritize business-critical pods; evict low-priority or resource-hogging ones.

---

**8. What metrics would you use to assess the health of a database under heavy load, and how would you interpret them?**  
- **Metrics:**  
  - **Query Latency:** High values indicate slow performance.  
  - **Connections:** Near max suggests saturation.  
  - **IOPS:** Low throughput vs. capacity hints at disk bottlenecks.  
  - **CPU Usage:** Spikes signal compute overload.  
  - **Memory Usage:** Swapping or OOM risks thrashing.  
  - **Error Rates:** Lock timeouts or deadlocks point to contention.  
- **Interpretation:** Cross-reference with baselines; e.g., high CPU + low IOPS = compute-bound, high latency + errors = contention.

---

**9. How would you reason through a situation where a system is meeting SLOs but users are still reporting issues?**  
- **Steps:**  
  1. Verify SLO metrics (e.g., latency, error rate) align with user experience—SLOs might miss edge cases.  
  2. Collect user feedback (e.g., specific errors, regions) to identify unreported failure modes.  
  3. Check client-side metrics (e.g., browser logs, CDN latency) for external factors.  
  4. Analyze outlier requests (e.g., 99th percentile latency) that SLOs might mask.  
  5. Test end-to-end flows to uncover unmonitored dependencies.  
- **Outcome:** Adjust SLOs or monitoring to capture user-impacting issues.

---

**10. If an AWS EC2 instance is unresponsive but shows as "running" in the console, what hypotheses would you form and test?**  
- **Hypotheses & Tests:**  
  1. **SSH Failure:** `ssh -v <ip>`—check timeouts or key issues.  
  2. **Resource Exhaustion:** AWS CloudWatch for CPU/memory/disk spikes.  
  3. **Network Issue:** `ping <ip>` or check VPC routing/security groups.  
  4. **OS Crash:** Reboot via `aws ec2 reboot-instances` and check logs post-restart.  
  5. **Application Hang:** Console screenshot or CloudWatch Logs for app errors.  
- **Outcome:** Narrow to network, resource, or app layer and escalate accordingly.

---

**11. How do you differentiate between a network issue and an application bug when a service stops responding?**  
- **Steps:**  
  1. Test network reachability: `ping`, `curl`, or `traceroute` to the service endpoint.  
  2. Check app logs for errors (e.g., segfaults, unhandled exceptions).  
  3. Monitor network metrics (e.g., packet loss, latency) vs. app metrics (e.g., request rate).  
  4. Simulate requests locally (e.g., `curl localhost`) to bypass network.  
  5. Review recent changes: Network config vs. code deploys.  
- **Outcome:** Network if external tests fail; app if local tests fail or logs show crashes.

---

**12. If a Terraform deployment fails halfway, how would you analyze the state file to recover without starting over?**  
- **Steps:**  
  1. Check `terraform state list` for successfully applied resources.  
  2. Review `terraform apply` logs for the failure point (e.g., resource creation error).  
  3. Use `terraform state show <resource>` to inspect partial state.  
  4. Fix the issue (e.g., adjust config, import existing resources with `terraform import`).  
  5. Run `terraform plan` to validate and `terraform apply` to resume.  
- **Outcome:** Resume deployment by reconciling state with reality.

---

**13. How would you determine if a spike in error rates is due to a new deployment or an external factor like a third-party API?**  
- **Steps:**  
  1. Correlate error spike timing with deployment logs (e.g., CI/CD timestamps).  
  2. Check error types in logs (e.g., 500s vs. 503s) for app vs. dependency clues.  
  3. Test external APIs (e.g., `curl <api-endpoint>`) for availability/latency.  
  4. Roll out a canary deployment to compare error rates.  
  5. Review metrics pre- and post-deploy for trends.  
- **Outcome:** Deployment if errors align with rollout; external if API tests fail.

---

**14. Given a system with high CPU usage but low memory usage, what conclusions would you draw about its workload?**  
- **Conclusions:**  
  - Likely CPU-bound: Tasks are compute-intensive (e.g., data processing, encryption).  
  - Not memory-intensive: Minimal large datasets or caching.  
  - Potential single-threaded bottleneck if one core is maxed out.  
- **Next Steps:** Profile app (e.g., `top`, `perf`) to confirm compute-heavy processes.

---

**15. How would you reason through optimizing a system where costs are rising but performance isn’t improving?**  
- **Steps:**  
  1. Analyze cost drivers (e.g., AWS Cost Explorer) vs. performance metrics (e.g., latency).  
  2. Check resource utilization: Over-provisioned instances or unused capacity?  
  3. Review scaling policies: Are they triggering unnecessarily?  
  4. Audit app efficiency: Inefficient queries, loops, or I/O?  
  5. Test cost-effective alternatives (e.g., spot instances, reserved capacity).  
- **Outcome:** Optimize resource allocation or code to align cost with performance.

---

**16. If a Kubernetes pod is stuck in a `Pending` state, what logical steps would you take to identify the bottleneck?**  
- **Steps:**  
  1. `kubectl describe pod <pod-name>`—check events (e.g., "Insufficient CPU", "No nodes").  
  2. `kubectl get nodes`—verify node availability and capacity.  
  3. `kubectl top nodes`—assess resource exhaustion.  
  4. Check scheduler logs (`kubectl logs -n kube-system`) for scheduling failures.  
  5. Validate pod spec: Requests/limits, node selectors, or taints mismatches?  
- **Outcome:** Resolve resource, scheduling, or config issues.

---

**17. How do you analyze whether an incident was caused by human error, misconfiguration, or software failure?**  
- **Steps:**  
  1. Review change logs (e.g., CI/CD, manual commands) for human actions.  
  2. Compare configs (e.g., Terraform state, YAML) against expected state.  
  3. Check app logs/stack traces for software bugs (e.g., null pointers).  
  4. Correlate timing: Incident vs. last change or deploy.  
  5. Reproduce in a test environment to confirm.  
- **Outcome:** Trace back to the earliest deviation (human, config, or code).

---

**18. If an application’s response time increases after adding more servers, what might be the underlying issue, and how would you investigate?**  
- **Possible Issues:**  
  - Load balancer misconfiguration (e.g., uneven distribution).  
  - Database contention (e.g., lock waits).  
  - Network latency between nodes.  
- **Investigation:**  
  1. Check LB metrics (e.g., target group health).  
  2. Profile DB queries (e.g., slow query log).  
  3. Measure inter-node latency (e.g., `ping`).  
- **Outcome:** Fix the bottleneck (LB, DB, or network).

---

**19. How would you decide between using a managed service versus a self-hosted solution for a critical workload?**  
- **Factors:**  
  - **Reliability:** Managed services offer SLAs (e.g., 99.99% uptime).  
  - **Control:** Self-hosted allows customization but requires expertise.  
  - **Cost:** Managed may be pricier but reduces ops overhead.  
  - **Scale:** Managed scales effortlessly; self-hosted needs manual tuning.  
  - **Security:** Managed shifts compliance burden; self-hosted needs hardening.  
- **Decision:** Use managed for simplicity and uptime; self-hosted for specific needs or cost savings.

---

**20. If a GitHub Actions workflow fails sporadically, how would you narrow down whether it’s a runner issue or a script problem?**  
- **Steps:**  
  1. Check workflow logs for consistent error patterns (e.g., timeouts vs. script errors).  
  2. Rerun failed jobs (`Re-run jobs`) to test reproducibility.  
  3. Test on different runners (e.g., `ubuntu-latest` vs. `macos-latest`).  
  4. Add debug steps (e.g., `echo` outputs, `set -x`) to script.  
  5. Simulate locally to rule out script logic.  
- **Outcome:** Runner if failures vary by environment; script if errors are consistent.

---

---

**21. How do you reason through setting an error budget for a service with varying traffic patterns?**  
- **Approach:**  
  1. Analyze historical traffic data (e.g., peak vs. off-peak) to define acceptable failure windows.  
  2. Set SLOs based on user impact (e.g., 99.9% uptime during peaks, 99.5% off-peak).  
  3. Calculate error budget as a percentage of total requests: `(1 - SLO) * total requests/month`.  
  4. Adjust for variability: Allocate more budget to peak times if failures there are costlier.  
  5. Validate with stakeholders: Ensure budget aligns with business needs (e.g., revenue loss).  
- **Outcome:** A dynamic budget (e.g., 43 minutes/month at 99.9%) weighted toward critical periods.

---

**22. If a system has 100% availability but poor performance, how would you argue for prioritizing performance improvements?**  
- **Argument:**  
  1. Availability alone doesn’t guarantee user satisfaction—slow responses degrade experience.  
  2. Cite metrics: High latency (e.g., P99 > 2s) increases bounce rates or lost revenue.  
  3. Highlight risks: Poor performance may mask underlying issues (e.g., resource exhaustion) that could lead to outages.  
  4. Propose trade-off: Small, controlled performance fixes now prevent larger reliability hits later.  
- **Outcome:** Shift focus to performance as a reliability pillar, backed by data.

---

**23. How would you analyze the trade-offs between deploying a hotfix immediately versus waiting for a full root cause analysis?**  
- **Trade-offs:**  
  - **Hotfix Now:** Faster recovery (lower MTTR), but risks incomplete fixes or new bugs.  
  - **Wait for RCA:** Better long-term stability, but prolongs downtime and user impact.  
- **Analysis:**  
  1. Assess severity: Critical outage (e.g., 100% failure) favors hotfix; partial (e.g., 10% errors) allows RCA.  
  2. Check error budget: If exhausted, hotfix; if spare, investigate.  
  3. Evaluate fix confidence: Known issue with tested patch vs. unknown cause.  
- **Decision:** Hotfix for urgent, high-impact issues; RCA for manageable, unclear cases.

---

**24. If an Ansible playbook fails on half the hosts, how would you deduce whether it’s a network, permissions, or logic issue?**  
- **Steps:**  
  1. Check error messages (`ansible-playbook -v`): "Permission denied" = permissions, "unreachable" = network, task-specific = logic.  
  2. Test connectivity: `ansible all -m ping`—failures indicate network.  
  3. Verify permissions: Run a simple task (e.g., `whoami`) with `--become`.  
  4. Compare host vars/facts: Logic errors may tie to differences (e.g., OS version).  
- **Outcome:** Isolate to network (unreachable), permissions (auth fails), or logic (task-specific).

---

**25. How do you determine if a monitoring alert is a false positive or an early warning of a real problem?**  
- **Steps:**  
  1. Correlate with other metrics: Alert on latency but CPU/memory normal = possible false.  
  2. Check historical trends: Frequent, fleeting alerts = noise; new pattern = warning.  
  3. Validate with logs: Errors or user impact confirm a real issue.  
  4. Test manually: Reproduce the condition (e.g., load test) to verify.  
- **Outcome:** False if isolated/no impact; real if corroborated by data.

---

**26. If a service meets its latency SLO but fails its throughput SLO, what might this indicate about its design?**  
- **Indications:**  
  - Optimized for low concurrency: Handles individual requests fast but chokes under load.  
  - Resource saturation: Limited threads or connections cap throughput.  
  - Inefficient scaling: Vertical limits or poor parallelization.  
- **Next Steps:** Profile under load (e.g., `ab`, `wrk`) to confirm concurrency bottlenecks.

---

**27. How would you reason through whether to use a load balancer or a service mesh for traffic management in a microservices architecture?**  
- **Factors:**  
  - **Complexity:** Load balancer (LB) for simple routing; service mesh (e.g., Istio) for fine-grained control (e.g., retries, tracing).  
  - **Scale:** LB scales externally; mesh handles inter-service traffic.  
  - **Observability:** Mesh offers built-in metrics; LB needs external tools.  
  - **Overhead:** LB is lighter; mesh adds latency/complexity.  
- **Decision:** LB for basic, external traffic; mesh for microservices with heavy internal communication.

---

**28. If a Kubernetes node is marked as `NotReady`, what analytical steps would you take to pinpoint the failure?**  
- **Steps:**  
  1. `kubectl describe node <node-name>`—check conditions (e.g., DiskPressure, NetworkUnavailable).  
  2. `kubectl get pods -n kube-system`—look for failed system pods (e.g., CNI, kubelet).  
  3. SSH to node: Check `systemctl status kubelet`, disk space (`df -h`), or network (`ping`).  
  4. Review logs: `journalctl -u kubelet` for errors.  
- **Outcome:** Identify if it’s resource, network, or kubelet failure.

---

**29. How do you evaluate whether an outage was preventable, and what data would you collect to support your conclusion?**  
- **Evaluation:**  
  1. Review change history: Was it tied to a deploy/config update?  
  2. Check monitoring gaps: Did alerts miss early signs?  
  3. Assess process: Were best practices (e.g., testing, canaries) followed?  
- **Data:** Logs, metrics, CI/CD records, post-mortem notes.  
- **Conclusion:** Preventable if tied to known risks with inadequate safeguards.

---

**30. If an AWS RDS instance is hitting connection limits, how would you reason through whether it’s a client issue or a database configuration problem?**  
- **Steps:**  
  1. Check `max_connections` (RDS parameter group)—too low?  
  2. Monitor CloudWatch: High `DatabaseConnections` vs. client load patterns.  
  3. Audit client apps: Connection leaks (e.g., no pooling) or spikes in requests?  
  4. Test with fewer clients: Persists = config; resolves = client.  
- **Outcome:** Config if limit is low; client if usage exceeds reasonable bounds.

---

**31. How would you analyze the impact of a third-party service outage on your system’s reliability?**  
- **Steps:**  
  1. Measure downtime overlap: Compare third-party outage timeline with your error rates.  
  2. Quantify dependency: % of requests hitting the service (e.g., via tracing).  
  3. Check mitigation: Circuit breakers or fallbacks in place?  
  4. Assess SLO burn: How much error budget was consumed?  
- **Outcome:** Impact = dependency depth * mitigation gaps.

---

**32. If a deployment pipeline takes twice as long after adding a new step, how would you identify the bottleneck?**  
- **Steps:**  
  1. Compare runtimes: Old vs. new pipeline logs (e.g., GitHub Actions).  
  2. Time each step: Add `time` commands or check job durations.  
  3. Profile new step: Resource usage (CPU, I/O) or external calls?  
  4. Test isolation: Run new step alone vs. full pipeline.  
- **Outcome:** Pinpoint slow step and optimize (e.g., cache, parallelize).

---

**33. How do you reason through balancing security patches with system stability in a production environment?**  
- **Factors:**  
  - **Severity:** Critical vuln (e.g., RCE) = immediate patch; low risk = schedule.  
  - **Impact:** Test patch in staging—breaks app = delay; seamless = proceed.  
  - **Downtime:** Can it be rolling or requires outage?  
  - **Error Budget:** Spare budget allows risk; tight budget favors stability.  
- **Decision:** Patch now for high risk; stage for stability concerns.

---

**34. If a system’s error rate doubles after a configuration change, how would you determine which change caused it?**  
- **Steps:**  
  1. Roll back incrementally: Revert changes one-by-one, monitoring errors.  
  2. Diff configs: Compare old vs. new (e.g., `git diff`) for suspects.  
  3. Correlate timing: Error spike vs. change deployment logs.  
  4. Test hypotheses: Reapply each change in isolation in staging.  
- **Outcome:** Identify the specific config triggering errors.

---

**35. How would you analyze whether a Kubernetes cluster’s resource quotas are too restrictive or too generous?**  
- **Steps:**  
  1. Check pod states: Many `Pending` = too restrictive.  
  2. Monitor usage: `kubectl top`—unused capacity = too generous.  
  3. Review quotas: `kubectl describe quota` vs. workload needs.  
  4. Simulate load: Test pod scheduling under current limits.  
- **Outcome:** Adjust quotas to balance utilization and availability.

---

**36. If an AWS Lambda function is timing out, how would you deduce whether it’s due to code, resources, or downstream dependencies?**  
- **Steps:**  
  1. Check logs (CloudWatch): Code errors or long-running tasks?  
  2. Review timeout setting: Too low for workload?  
  3. Monitor memory/CPU: Under-provisioned resources?  
  4. Test dependencies: Call downstream services manually.  
- **Outcome:** Code if errors; resources if maxed out; dependencies if slow responses.

---

**37. How do you reason through choosing between blue-green deployments and canary releases for a high-traffic service?**  
- **Factors:**  
  - **Risk Tolerance:** Blue-green = zero-downtime switch; canary = gradual exposure.  
  - **Rollback Speed:** Blue-green = instant; canary = phased.  
  - **Cost:** Blue-green doubles infra; canary uses less.  
  - **Feedback:** Canary allows real-user testing; blue-green needs pre-validation.  
- **Decision:** Blue-green for critical, high-traffic; canary for iterative testing.

---

**38. If a monitoring system shows conflicting data (e.g., high latency but normal CPU), how would you reconcile the discrepancy?**  
- **Steps:**  
  1. Validate metrics: Check collection intervals and sources (e.g., app vs. infra).  
  2. Cross-check: Look at I/O, network, or DB metrics for hidden bottlenecks.  
  3. Trace requests: Use tracing to find latency source.  
  4. Test load: Reproduce to align data.  
- **Outcome:** Identify unmonitored bottleneck (e.g., disk, network).

---

**39. How would you determine if a system’s failure is due to insufficient capacity or a software bug?**  
- **Steps:**  
  1. Check resource usage: CPU/memory/disk maxed = capacity; idle = bug.  
  2. Review logs: Stack traces = bug; resource errors = capacity.  
  3. Scale up: Failure persists = bug; resolves = capacity.  
  4. Profile app: Pinpoint code vs. resource limits.  
- **Outcome:** Capacity if tied to load; bug if code-specific.

---

**40. If an application scales up but latency increases, what hypotheses would you form about its architecture?**  
- **Hypotheses:**  
  - **Contention:** Shared resource (e.g., DB) overwhelmed by more instances.  
  - **Coordination Overhead:** Increased inter-node communication (e.g., locks, sync).  
  - **Load Balancer Issue:** Uneven distribution or misconfig.  
- **Next Steps:** Profile DB, network, and LB metrics to confirm.

---

---

**41. How do you analyze whether a CI/CD pipeline failure is worth pausing deployments to fix immediately?**  
- **Steps:**  
  1. Assess impact: Does it block all deploys or just a subset (e.g., tests vs. prod)?  
  2. Check frequency: One-off vs. recurring failure.  
  3. Evaluate urgency: Critical bug fixes delayed vs. routine updates.  
  4. Measure risk: Can manual deploys or workarounds suffice?  
  5. Estimate fix time: Quick resolution vs. deep investigation.  
- **Decision:** Pause if it blocks critical deploys or risks prod stability; otherwise, fix in parallel.

---

**42. If a Kubernetes pod is evicted repeatedly, how would you reason through whether it’s a resource or configuration issue?**  
- **Steps:**  
  1. Check events: `kubectl describe pod <pod-name>`—look for "Evicted" reasons (e.g., "MemoryPressure").  
  2. Assess node resources: `kubectl top node`—high usage = resource issue.  
  3. Review pod spec: Missing/inadequate requests/limits = config issue.  
  4. Check quotas: `kubectl describe quota`—exceeded limits = config.  
  5. Test with higher limits: Persists = resources; resolves = config.  
- **Outcome:** Resources if nodes are maxed; config if spec or quotas misaligned.

---

**43. How would you evaluate the trade-offs of automating a manual process versus keeping it manual for rare tasks?**  
- **Trade-offs:**  
  - **Automation:** Upfront effort, reduces errors, but overkill for rare use.  
  - **Manual:** Low setup cost, prone to human error, slower execution.  
- **Factors:** Frequency (e.g., monthly vs. yearly), complexity (error risk), and team capacity.  
- **Decision:** Automate if frequent or error-prone; keep manual if rare and simple.

---

**44. If an AWS S3 bucket is inaccessible, how would you deduce whether it’s an IAM, network, or bucket policy issue?**  
- **Steps:**  
  1. Test access: `aws s3 ls s3://bucket`—403 = IAM/policy, timeout = network.  
  2. Check IAM: `aws iam get-role-policy`—missing perms = IAM.  
  3. Review bucket policy: `aws s3api get-bucket-policy`—deny rules = policy.  
  4. Verify network: Ping VPC endpoints or check security groups = network.  
- **Outcome:** IAM if perms missing, policy if denied, network if unreachable.

---

**45. How do you reason through setting alerting thresholds to avoid noise while catching critical issues?**  
- **Steps:**  
  1. Analyze historical data: Set baseline (e.g., P95 latency) + buffer (e.g., 20%).  
  2. Define impact: Thresholds tied to user experience (e.g., 500ms latency).  
  3. Test sensitivity: Too low = noise, too high = misses issues.  
  4. Use multi-signal alerts: Combine metrics (e.g., latency + errors) to reduce false positives.  
  5. Iterate: Adjust based on alert fatigue vs. missed incidents.  
- **Outcome:** Balance specificity and sensitivity with data-driven thresholds.

---

**46. If a service’s error rate spikes during peak traffic, how would you determine if it’s a capacity or concurrency problem?**  
- **Steps:**  
  1. Check resource usage: `top` or CloudWatch—maxed CPU/memory = capacity.  
  2. Monitor threads/connections: High contention (e.g., DB locks) = concurrency.  
  3. Scale test: Add instances—errors drop = capacity; persist = concurrency.  
  4. Profile app: Look for bottlenecks (e.g., mutexes, single-threaded code).  
- **Outcome:** Capacity if resources exhaust; concurrency if scaling doesn’t help.

---

**47. How would you analyze the risk of deploying a change to a system with no recent incidents?**  
- **Steps:**  
  1. Assess change scope: Small tweak vs. major refactor.  
  2. Review testing: Unit/integration coverage vs. untested edge cases.  
  3. Check stability: No incidents may hide fragility (e.g., under-tested).  
  4. Plan mitigation: Canary or blue-green to limit blast radius.  
  5. Weigh urgency: Critical fix vs. optional update.  
- **Outcome:** Low risk with strong testing/mitigation; higher if untested or complex.

---

**48. If an Ansible task fails with a timeout, how would you deduce whether it’s a host, network, or playbook issue?**  
- **Steps:**  
  1. Check logs: `ansible-playbook -vvv`—SSH timeout = network/host, task hang = playbook.  
  2. Test host: `ansible <host> -m ping`—fails = host/network.  
  3. Review task: Long-running command (e.g., `sleep 300`) = playbook logic.  
  4. Test network: `ping <host>` or SSH manually = network.  
- **Outcome:** Host if unreachable, network if slow, playbook if task-specific.

---

**49. How do you reason through whether a system’s downtime was due to a single point of failure or a cascading issue?**  
- **Steps:**  
  1. Trace timeline: Logs/metrics—single component fails first = SPOF; multiple simultaneous = cascade.  
  2. Check dependencies: Failure propagates (e.g., DB → app) = cascade.  
  3. Test isolation: Restart one component—resolves = SPOF; persists = cascade.  
  4. Review architecture: SPOF if no redundancy; cascade if tight coupling.  
- **Outcome:** SPOF if one root cause; cascade if chain reaction.

---

**50. If a GitHub Actions workflow succeeds but the application isn’t updated, how would you investigate the disconnect?**  
- **Steps:**  
  1. Verify workflow: Check logs—did deploy step run (e.g., `kubectl apply`)?  
  2. Check target: `kubectl get pods`—new image deployed?  
  3. Validate config: Wrong env (e.g., staging vs. prod) or creds?  
  4. Test manually: Run deploy command locally—works = workflow issue.  
  5. Review caching: Stale artifacts or skipped steps?  
- **Outcome:** Workflow misconfig, env mismatch, or deployment failure.

---

**51. How would you determine if a Kubernetes cluster’s performance degradation is due to networking or node health?**  
- **Steps:**  
  1. Check pod latency: `kubectl exec`—slow responses = network.  
  2. Monitor nodes: `kubectl top node`—high usage = node health.  
  3. Test network: `ping` between pods or `iperf`—packet loss = network.  
  4. Review CNI: `kubectl logs -n kube-system`—CNI errors = network.  
- **Outcome:** Network if inter-pod issues; node if resource-bound.

---

**52. If an AWS EKS cluster is rejecting pod schedules, how would you analyze whether it’s a taint, quota, or resource issue?**  
- **Steps:**  
  1. `kubectl describe pod`—Events: "taint" = taint, "quota" = quota, "insufficient" = resources.  
  2. Check taints: `kubectl get nodes -o yaml`—mismatched tolerations?  
  3. Review quotas: `kubectl describe quota`—exceeded?  
  4. Assess resources: `kubectl top nodes`—no capacity?  
- **Outcome:** Taint if toleration missing, quota if limits hit, resources if nodes full.

---

**53. How do you reason through prioritizing incident response versus long-term reliability improvements?**  
- **Factors:**  
  - **Severity:** Ongoing outage = response; latent issue = long-term.  
  - **Error Budget:** Exhausted = response; spare = improvements.  
  - **Impact:** User-facing = response; internal = long-term.  
  - **Recurrence:** Frequent incidents = long-term fix.  
- **Decision:** Response for active crises; improvements for systemic stability.

---

**54. If a Terraform state file is corrupted, how would you deduce the safest way to recover without losing resources?**  
- **Steps:**  
  1. Backup state: Copy corrupted file (e.g., `terraform.tfstate.backup`).  
  2. Check drift: `terraform plan`—compare intended vs. actual.  
  3. Rebuild state: `terraform import` for existing resources.  
  4. Validate: `terraform state list`—ensure all resources accounted.  
  5. Test apply: Small change to confirm integrity.  
- **Outcome:** Recover by importing live resources into a new state.

---

**55. How would you analyze whether a system’s high availability is worth the operational complexity it introduces?**  
- **Analysis:**  
  1. Measure uptime gain: HA’s 99.99% vs. simpler 99.9% = 42 min/month diff.  
  2. Assess cost: Extra infra + ops time vs. downtime losses.  
  3. Evaluate failures: HA mitigates SPOFs—worth it for critical apps?  
  4. Check team readiness: Complexity manageable with automation/skills?  
- **Outcome:** HA if downtime cost exceeds ops burden; simpler if not.

---

**56. If a service’s logs show no errors but users report failures, what conclusions would you draw about observability?**  
- **Conclusions:**  
  - Logs miss key events: Insufficient granularity or scope.  
  - Metrics gap: Unmonitored failure modes (e.g., client-side errors).  
  - User perception: Performance issues not logged as errors.  
- **Next Steps:** Add tracing, client logs, or broader metrics.

---

**57. How do you reason through whether a performance issue is worth fixing if it only affects 1% of users?**  
- **Steps:**  
  1. Assess impact: 1% of high-value users (e.g., paying) = higher priority.  
  2. Check trends: Growing issue or one-off?  
  3. Estimate fix cost: Quick tweak vs. major refactor.  
  4. Weigh SLOs: Within budget = lower priority; breached = fix.  
- **Decision:** Fix if high-value or escalating; defer if minor.

---

**58. If an AWS ALB is dropping requests, how would you determine if it’s a target group, health check, or routing issue?**  
- **Steps:**  
  1. Check metrics: CloudWatch `RequestCount` vs. `TargetResponseTime`—drops = routing/health.  
  2. Test health: `curl <target>`—fails = health check; works = routing.  
  3. Review target group: `aws elbv2 describe-target-health`—unhealthy = target issue.  
  4. Inspect rules: ALB listener rules misrouted?  
- **Outcome:** Target if unhealthy, health if misconfigured, routing if rules wrong.

---

**59. How would you analyze the impact of a failed deployment on downstream services?**  
- **Steps:**  
  1. Map dependencies: Trace calls (e.g., API logs, tracing).  
  2. Check downstream metrics: Error rates/latency spikes post-deploy.  
  3. Test isolation: Mock failed service—downstream fails?  
  4. Quantify: % of requests affected or SLO burn.  
- **Outcome:** Impact proportional to dependency depth and failure scope.

---

**60. If a Kubernetes ingress isn’t routing traffic, how would you deduce whether it’s a controller, DNS, or service issue?**  
- **Steps:**  
  1. Check ingress: `kubectl describe ingress`—events show controller errors?  
  2. Test service: `kubectl port-forward`—works = ingress issue.  
  3. Verify DNS: `nslookup <domain>`—resolves incorrectly = DNS.  
  4. Check controller: `kubectl logs -n ingress-nginx`—crash = controller.  
- **Outcome:** Controller if logs fail, DNS if unresolved, service if backend down.

---