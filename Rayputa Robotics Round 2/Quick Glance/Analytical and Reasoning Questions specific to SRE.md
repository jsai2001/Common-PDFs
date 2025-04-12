### Condensed System Analysis and Troubleshooting Questions and Answers

1. **Root cause of application latency spike?**  
   Check dashboards, logs, traces; correlate with changes, test resources/network.  
   **Outcome:** Pinpoint service or external bottleneck.

2. **Isolate intermittent deployment failure?**  
   Reproduce in staging, log verbosely, use canary, compare requests, check infra.  
   **Outcome:** Identify code, env, or load issue.

3. **Horizontal vs. vertical scaling?**  
   CPU-bound = vertical; I/O-bound = horizontal. Consider limits, cost, availability.  
   **Decision:** Vertical for quick fixes; horizontal for resilience.

4. **99.9% uptime downtime calculation?**  
   43,200 min/month * 0.001 = 43.2 min downtime.  
   **Implication:** Resolve incidents fast to meet SLO.

5. **Two services failing, find source?**  
   Check failure timeline, test independently, analyze calls/resources.  
   **Outcome:** First or standalone failure is root.

6. **CI/CD pipeline slowdown cause?**  
   Compare runtimes, isolate steps, check infra, code, externals.  
   **Outcome:** Pinpoint slow component.

7. **Prioritize Kubernetes workloads?**  
   Check resource usage, priorities, criticality; evict low-priority pods.  
   **Decision:** Keep critical pods, remove excess.

8. **Database health metrics under load?**  
   Latency, connections, IOPS, CPU, memory, errors.  
   **Interpretation:** High CPU/low IOPS = compute; high latency/errors = contention.

9. **SLOs met but users report issues?**  
   Verify SLOs, collect feedback, check client metrics, analyze outliers.  
   **Outcome:** Adjust monitoring for user issues.

10. **EC2 unresponsive but running?**  
   Test SSH, check CloudWatch, ping network, reboot, view logs.  
   **Outcome:** Network, resource, or app issue.

11. **Network issue vs. app bug?**  
   Test reachability, check logs, compare metrics, simulate locally.  
   **Outcome:** Network if external fails; app if local errors.

12. **Recover Terraform partial failure?**  
   List state, review logs, fix issue, import resources, resume apply.  
   **Outcome:** Complete deployment safely.

13. **Error spike: deployment or external?**  
   Correlate timing, check error types, test APIs, use canary.  
   **Outcome:** Deployment if rollout-related; external if API fails.

14. **High CPU, low memory workload?**  
   Compute-intensive tasks, likely single-threaded.  
   **Next:** Profile processes.

15. **Optimize rising costs, no performance gain?**  
   Analyze costs vs. metrics, check utilization, scaling, app efficiency.  
   **Outcome:** Optimize resources or code.

16. **Kubernetes pod stuck in Pending?**  
   Describe pod, check nodes, resources, scheduler, validate spec.  
   **Outcome:** Fix resource or config issue.

17. **Incident cause: human, config, or software?**  
   Review changes, configs, logs; reproduce to confirm.  
   **Outcome:** Trace to earliest error source.

18. **Latency up after more servers?**  
   Check LB, DB contention, network.  
   **Outcome:** Fix bottleneck.

19. **Managed vs. self-hosted service?**  
   Compare reliability, control, cost, scale, security.  
   **Decision:** Managed for simplicity; self-hosted for customization.

20. **GitHub Actions sporadic failure?**  
   Check logs, rerun, test runners, debug script, simulate locally.  
   **Outcome:** Runner if env varies; script if consistent.

21. **Error budget for variable traffic?**  
   Analyze traffic, set dynamic SLOs, allocate budget by impact.  
   **Outcome:** Weighted budget for peaks.

22. **100% availability, poor performance?**  
   Argue latency impacts users, risks outages.  
   **Outcome:** Prioritize performance fixes.

23. **Hotfix vs. RCA trade-offs?**  
   Assess severity, budget, fix confidence.  
   **Decision:** Hotfix for critical; RCA for clarity.

24. **Ansible playbook fails on some hosts?**  
   Check errors, test ping, permissions, host vars.  
   **Outcome:** Network, permissions, or logic issue.

25. **False positive vs. real alert?**  
   Correlate metrics, check trends, validate logs, test manually.  
   **Outcome:** False if no impact; real if confirmed.

26. **Meets latency but fails throughput SLO?**  
   Low concurrency, resource saturation.  
   **Next:** Profile under load.

27. **Load balancer vs. service mesh?**  
   LB for simple routing; mesh for microservices control.  
   **Decision:** LB for external; mesh for internal.

28. **Kubernetes node NotReady?**  
   Describe node, check system pods, SSH for kubelet, disk, network.  
   **Outcome:** Resource, network, or kubelet failure.

29. **Was outage preventable?**  
   Check changes, monitoring, processes.  
   **Conclusion:** Preventable if risks ignored.

30. **RDS hitting connection limits?**  
   Check max_connections, monitor usage, audit clients.  
   **Outcome:** Config if low limit; client if leaks.

31. **Third-party outage impact?**  
   Compare timelines, quantify dependency, check mitigations.  
   **Outcome:** Impact tied to dependency strength.

32. **Pipeline step doubles runtime?**  
   Compare logs, time steps, profile new step, test isolation.  
   **Outcome:** Optimize slow step.

33. **Security patches vs. stability?**  
   Weigh severity, impact, downtime, budget.  
   **Decision:** Patch critical now; stage others.

34. **Error rate doubles post-config?**  
   Roll back incrementally, diff configs, test changes.  
   **Outcome:** Identify triggering config.

35. **Kubernetes quotas too restrictive?**  
   Check pod states, usage, quotas; simulate load.  
   **Outcome:** Adjust for balance.

36. **AWS Lambda timeout cause?**  
   Check logs, timeout setting, resources, dependencies.  
   **Outcome:** Code, resources, or downstream issue.

37. **Blue-green vs. canary deployment?**  
   Blue-green for instant switch; canary for gradual test.  
   **Decision:** Blue-green for critical; canary for feedback.

38. **Conflicting monitoring data?**  
   Validate metrics, check I/O, trace requests, test load.  
   **Outcome:** Find hidden bottleneck.

39. **Failure: capacity or bug?**  
   Check usage, logs; scale up to test.  
   **Outcome:** Capacity if load-related; bug if code-specific.

40. **Latency up after scaling?**  
   Contention, coordination, or LB issues.  
   **Next:** Profile DB, network, LB.

41. **Pause CI/CD for pipeline failure?**  
   Assess impact, frequency, urgency, risk, fix time.  
   **Decision:** Pause if critical; fix in parallel otherwise.

42. **Kubernetes pod evicted repeatedly?**  
   Check events, node resources, pod spec, quotas.  
   **Outcome:** Resources if maxed; config if misaligned.

43. **Automate vs. keep manual process?**  
   Weigh frequency, complexity, errors.  
   **Decision:** Automate if frequent; manual if rare.

44. **AWS S3 bucket inaccessible?**  
   Test access, check IAM, policy, network.  
   **Outcome:** IAM, policy, or network issue.

45. **Set alerting thresholds?**  
   Use historical data, tie to impact, test sensitivity, combine signals.  
   **Outcome:** Balanced, low-noise alerts.

46. **Error spike at peak traffic?**  
   Check resources, threads; scale test, profile app.  
   **Outcome:** Capacity if exhausted; concurrency if persistent.

47. **Risk of change in stable system?**  
   Assess scope, testing, stability, mitigation.  
   **Outcome:** Low risk if tested; high if untested.

48. **Ansible task timeout cause?**  
   Check logs, test host, review task, network.  
   **Outcome:** Host, network, or playbook issue.

49. **SPOF vs. cascading failure?**  
   Trace timeline, test isolation, review architecture.  
   **Outcome:** SPOF if single cause; cascade if chain.

50. **GitHub Actions succeeds, app not updated?**  
   Verify logs, check target, config, test manually, review caching.  
   **Outcome:** Workflow, env, or deployment issue.

51. **Kubernetes performance: network or node?**  
   Test pod latency, monitor nodes, check CNI.  
   **Outcome:** Network if inter-pod; node if resource-bound.

52. **EKS cluster rejects pod schedules?**  
   Describe pod, check taints, quotas, resources.  
   **Outcome:** Taint, quota, or resource issue.

53. **Incident response vs. reliability?**  
   Prioritize response for outages; improvements for recurrence.  
   **Decision:** Response for crises; long-term for stability.

54. **Recover corrupted Terraform state?**  
   Backup state, check drift, import resources, validate, test apply.  
   **Outcome:** Rebuild state safely.

55. **High availability worth complexity?**  
   Compare uptime gain, cost, failure risks, team readiness.  
   **Outcome:** HA if cost-effective; simpler if not.

56. **No errors in logs, users report failures?**  
   Logs miss events, metrics gap, or performance issue.  
   **Next:** Add tracing, client logs.

57. **Fix performance issue for 1% users?**  
   Assess user value, trends, fix cost, SLOs.  
   **Decision:** Fix if high-value; defer if minor.

58. **AWS ALB dropping requests?**  
   Check metrics, test health, review targets, rules.  
   **Outcome:** Target, health, or routing issue.

59. **Failed deployment impact on downstream?**  
   Map dependencies, check metrics, test isolation, quantify.  
   **Outcome:** Impact by dependency depth.

60. **Kubernetes ingress not routing?**  
   Check ingress, test service, verify DNS, controller logs.  
   **Outcome:** Controller, DNS, or service issue.