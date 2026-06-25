# Interview Questions - Day-24 Scheduling In Spring Boot

## Beginner
1. Question: What problem does Spring scheduling solve in enterprise systems?
   Answer: It automates recurring operational workflows.
   Explanation: NOC platforms rely on unattended execution for polling, alarms, and reports.

2. Question: What is the purpose of @EnableScheduling?
   Answer: It activates processing of @Scheduled methods.
   Explanation: Without it, scheduler annotations are ignored at runtime.

3. Question: What is the difference between fixedRate and fixedDelay?
   Answer: fixedRate is cadence-based; fixedDelay waits after completion.
   Explanation: fixedRate can overlap, fixedDelay naturally serializes task runs.

4. Question: When should cron be preferred over fixedRate?
   Answer: For calendar-based jobs.
   Explanation: Reports and archive windows usually align with business time.

5. Question: Why externalize schedule values in application.yml?
   Answer: To adjust timings without code change.
   Explanation: Operations teams can tune schedules per environment.

6. Question: What is a ThreadPoolTaskScheduler?
   Answer: A configurable scheduler with a worker thread pool.
   Explanation: It allows concurrent task execution and avoids single-thread bottlenecks.

7. Question: Why is marker-based API validation useful?
   Answer: It makes behavior assertions deterministic.
   Explanation: CI scripts can verify workflow completion via marker strings.

8. Question: Why log scheduler task execution to database?
   Answer: For auditability and troubleshooting.
   Explanation: Start/end/duration/status data supports root-cause analysis.

9. Question: What should be included in TaskExecutionLog?
   Answer: Task name, timing, status, duration, and message.
   Explanation: This gives full visibility into each run.

10. Question: Why use H2 in learning projects?
    Answer: It provides fast in-memory persistence.
    Explanation: It supports realistic JPA workflows with zero external setup.

11. Question: What is a good use case for fixedDelay scheduling?
    Answer: Cleanup and maintenance operations.
    Explanation: These jobs often should not overlap.

12. Question: What is a good use case for fixedRate scheduling?
    Answer: Telemetry collection.
    Explanation: Monitoring requires regular cadence updates.

13. Question: Why should alarm evaluation be scheduled?
    Answer: Threshold breaches can happen continuously.
    Explanation: Scheduled evaluation ensures consistent fault detection.

14. Question: What does actuator health provide?
    Answer: Runtime health endpoint.
    Explanation: Automation scripts can poll readiness and operational status.

15. Question: Why disable scheduling in tests?
    Answer: To keep tests deterministic.
    Explanation: Background jobs can mutate data and cause flaky assertions.

## Intermediate
16. Question: How do you prevent scheduler tasks from blocking each other?
    Answer: Configure an adequate scheduler thread pool.
    Explanation: Separate threads allow independent task progress.

17. Question: How can scheduler jobs be made idempotent?
    Answer: Design operations safe for repeated execution.
    Explanation: Idempotency prevents duplicate side effects during retries.

18. Question: How should scheduler exceptions be handled?
    Answer: Catch and persist failure status with context.
    Explanation: Silent failures are operationally dangerous.

19. Question: What is the role of TaskExecutionService wrapper?
    Answer: Centralized lifecycle tracking around job logic.
    Explanation: It enforces consistent logging and error behavior.

20. Question: Why separate scheduler classes by domain workflow?
    Answer: To keep responsibilities clear.
    Explanation: Telemetry, alarms, reports, and maintenance have different cadence and failure semantics.

21. Question: How would you detect scheduler degradation?
    Answer: Monitor failed execution count trends.
    Explanation: Rising failures indicate outages, dependency issues, or overloaded threads.

22. Question: Why use different schedules for health and notifications?
    Answer: They have different urgency and cost.
    Explanation: Health checks are frequent; notifications can be slightly less frequent.

23. Question: How do you model alarm severity?
    Answer: Use enum levels like WARNING, MAJOR, CRITICAL.
    Explanation: Severity drives escalation and notification priority.

24. Question: What threshold rules are common in NOC telemetry?
    Answer: CPU, memory, temperature, and signal quality.
    Explanation: These represent platform performance and link stability.

25. Question: Why maintain archived record metadata?
    Answer: To prove data retention actions occurred.
    Explanation: Auditors need archive time and record counts.

26. Question: What is a practical auto-recovery sequence?
    Answer: Restart service, reconnect, update status, notify.
    Explanation: This simulates closed-loop remediation.

27. Question: How do manual trigger APIs help operations?
    Answer: They allow on-demand execution.
    Explanation: Engineers can force telemetry, reports, or recovery during incidents.

28. Question: Why track executionDurationMs?
    Answer: To identify slow or regressing jobs.
    Explanation: Duration trends are key performance indicators.

29. Question: How should report generation be scheduled in production?
    Answer: With cron aligned to business windows.
    Explanation: Daily and performance reports often have fixed business cutoffs.

30. Question: Why include schedulerHealth in dashboard DTO?
    Answer: It gives operators quick status context.
    Explanation: Dashboard consumers immediately see healthy/degraded scheduler state.

31. Question: How can stale lastSeen data affect health evaluation?
    Answer: Devices may be classified offline.
    Explanation: Recency checks are required beyond threshold values.

32. Question: Why should maintenance be isolated from telemetry jobs?
    Answer: Different risk and execution patterns.
    Explanation: Maintenance can be heavy and should not starve monitoring loops.

33. Question: Why is deterministic seeding important?
    Answer: It guarantees reproducible behavior.
    Explanation: Tests and demos depend on stable initial counts and values.

34. Question: How do you validate scheduler endpoints in CI?
    Answer: Use curl plus marker assertions.
    Explanation: Response content validation is stronger than status-only checks.

35. Question: What does graceful scheduler shutdown provide?
    Answer: In-flight task completion during termination.
    Explanation: It avoids abrupt state loss and half-written logs.

## Advanced
36. Question: How do you avoid duplicated alarms for repeating threshold breaches?
    Answer: Use deduplication windows or suppression rules.
    Explanation: Excessive duplicate alarms can create alert fatigue.

37. Question: How can you prioritize critical scheduling tasks?
    Answer: Use separate pools or weighted execution design.
    Explanation: High-severity workflows should not wait behind low-priority maintenance.

38. Question: What is scheduler back-pressure in monitoring platforms?
    Answer: Task production exceeds execution capacity.
    Explanation: It leads to delayed runs and stale operational state.

39. Question: How would you measure scheduling SLA compliance?
    Answer: Compare expected trigger times vs actual start times.
    Explanation: Drift quantifies scheduler timeliness.

40. Question: Why use persistent execution history instead of logs only?
    Answer: Queryable analytics and dashboards require structured storage.
    Explanation: Text logs are hard to aggregate for trend analysis.

41. Question: How do you secure manual task trigger endpoints in enterprise systems?
    Answer: Apply role-based authorization and audit logs.
    Explanation: Unauthorized triggering can disrupt operations.

42. Question: How can retries be implemented safely in scheduler workflows?
    Answer: Add bounded retries with idempotent operations.
    Explanation: Infinite retries can amplify failures and load.

43. Question: What is the risk of high-frequency fixedRate with long tasks?
    Answer: Overlap and thread pool exhaustion.
    Explanation: Jobs stack up and overall latency rises.

44. Question: How can you support dynamic schedule changes at runtime?
    Answer: Store schedules in config source and reconfigure scheduler beans.
    Explanation: Operational tuning should not require redeployment.

45. Question: How do you correlate alarm bursts to root cause?
    Answer: Aggregate by device, metric type, and time window.
    Explanation: Correlation helps distinguish symptoms from primary faults.

46. Question: What are best practices for cron expression management?
    Answer: Externalize, validate, and version control expressions.
    Explanation: Invalid cron values can break critical jobs.

47. Question: Why include automation scripts with marker checks?
    Answer: They provide repeatable operational verification.
    Explanation: Scripts simulate NOC runbooks and CI smoke tests.

48. Question: How do you design scheduler observability for SRE teams?
    Answer: Expose metrics, health, failure counts, and durations.
    Explanation: SREs need actionable telemetry, not only logs.

49. Question: How can scheduler jobs support incident response?
    Answer: Provide manual trigger APIs and real-time status endpoints.
    Explanation: Operators can force specific workflows during outage management.

50. Question: What makes this Day-24 project production-style instead of a toy demo?
    Answer: Multi-workflow scheduling, thread pools, persistence, markers, recovery, archival, and automated validation.
    Explanation: It mirrors real operational concerns in telecom and monitoring platforms.
