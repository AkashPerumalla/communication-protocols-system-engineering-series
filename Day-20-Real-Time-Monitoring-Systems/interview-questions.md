# Interview Questions - Real-Time Monitoring Systems

## Beginner

1. Question: What is a real-time monitoring system?
   Answer: It continuously collects, analyzes, and visualizes system metrics/events.
   Explanation: Unlike periodic reporting, real-time monitoring prioritizes low-latency telemetry and quick incident response.

2. Question: Why do monitoring agents exist?
   Answer: Agents collect host/application metrics and send them to a central platform.
   Explanation: Agents reduce coupling by abstracting OS/service specifics from the monitoring backend.

3. Question: What is a metric in observability?
   Answer: A numeric measurement sampled over time.
   Explanation: CPU usage, memory usage, and request latency are common examples of time-series metrics.

4. Question: What is an alert rule?
   Answer: A condition that triggers when metric values cross a threshold.
   Explanation: Rules define metric, comparator, threshold, severity, and enabled/disabled states.

5. Question: Why is timestamp important in monitoring data?
   Answer: It preserves event order and trend analysis.
   Explanation: Time is the primary dimension for anomaly detection and incident timelines.

6. Question: What is the difference between an alert rule and an alert event?
   Answer: Rule is configuration; event is runtime incident.
   Explanation: A rule can generate many alert events over time when conditions are met.

7. Question: What is heartbeat in agent communication?
   Answer: A periodic signal indicating the agent is alive.
   Explanation: Missing heartbeats can classify nodes as offline/degraded.

8. Question: Why use severity levels like LOW, HIGH, CRITICAL?
   Answer: To prioritize operational response.
   Explanation: Severity helps on-call engineers allocate effort during multiple simultaneous incidents.

9. Question: What does dashboard aggregation provide?
   Answer: A summarized operational view.
   Explanation: Aggregates such as average CPU and active alerts reduce cognitive load in NOC operations.

10. Question: Why use validation annotations in request DTOs?
    Answer: To reject malformed input early.
    Explanation: Validation improves API robustness and prevents bad states from entering business logic.

11. Question: What is H2 used for in training projects?
    Answer: Lightweight in-memory relational database.
    Explanation: It accelerates local development while preserving SQL/JPA behavior.

12. Question: What is the role of Spring Data JPA?
    Answer: Simplifies persistence and repository access.
    Explanation: It provides repository abstractions, derived queries, and transaction integration.

13. Question: What is WebSocket in monitoring dashboards?
    Answer: A persistent duplex connection for live updates.
    Explanation: It avoids polling overhead by pushing metric/alert updates to clients.

14. Question: What is STOMP in Spring WebSocket apps?
    Answer: Messaging protocol on top of WebSocket.
    Explanation: STOMP defines destinations/topics and subscription semantics used by dashboards.

15. Question: Why use deterministic seed data in teaching systems?
    Answer: To make tests and demos reproducible.
    Explanation: Deterministic scenarios prevent flaky validations and simplify debugging.

16. Question: What does a global exception handler solve?
    Answer: Standardized error responses.
    Explanation: It centralizes error mapping and avoids duplicated try/catch in controllers.

## Intermediate

17. Question: How does scheduled metric collection work in Spring?
    Answer: Using @Scheduled methods with fixed delay/rate.
    Explanation: Scheduler threads periodically execute collection logic independent of request traffic.

18. Question: What is time-series data handling in relational stores?
    Answer: Store measurements with timestamps and index by time/entity.
    Explanation: Query patterns commonly include latest record per source and range-based scans.

19. Question: Why index agentId and timestamp together?
    Answer: To optimize latest-per-agent and historical lookup queries.
    Explanation: Composite indexes reduce scan costs for frequent monitoring access patterns.

20. Question: What is alert deduplication?
    Answer: Suppressing repeated alerts for an already-active issue.
    Explanation: It reduces noise and prevents pager fatigue in NOC teams.

21. Question: Why separate NotificationService from AlertEngine?
    Answer: Separation of concerns and extensibility.
    Explanation: Alert generation and delivery channels evolve independently in enterprise systems.

22. Question: What are common notification channels?
    Answer: Email, SMS, webhook, Slack, Teams.
    Explanation: Multi-channel support aligns with incident criticality and organizational workflows.

23. Question: How can you model SNMP data in a modern monitoring platform?
    Answer: Represent OID values as metrics with metadata.
    Explanation: SNMP outputs can be normalized into common time-series pipelines.

24. Question: What is the advantage of API response envelopes?
    Answer: Consistent marker, message, and payload structure.
    Explanation: It simplifies scripting, UI handling, and cross-endpoint observability.

25. Question: Why keep request and response DTOs separate from entities?
    Answer: Prevents persistence model leakage.
    Explanation: DTO boundaries support API evolution and security hardening.

26. Question: How do you compute a dashboard snapshot?
    Answer: Aggregate current agent state, alerts, and metric averages.
    Explanation: Snapshot records can be cached and streamed to clients for low-latency UIs.

27. Question: What is the role of CommandLineRunner in Spring Boot?
    Answer: Execute startup logic after context initialization.
    Explanation: It is frequently used for deterministic seed data and bootstrap checks.

28. Question: Why disable scheduling in integration tests?
    Answer: To avoid nondeterministic background mutations.
    Explanation: Controlled test environments improve reliability and assertion precision.

29. Question: What does random-port testing provide?
    Answer: Isolated web integration tests without fixed-port conflicts.
    Explanation: It allows parallel and local CI-friendly execution.

30. Question: How do you evaluate rule comparators generically?
    Answer: Encapsulate comparator logic in enum/function strategy.
    Explanation: This removes repetitive condition blocks and centralizes rule semantics.

31. Question: Why do monitoring systems need both pull and push interfaces?
    Answer: Pull supports API queries; push supports real-time dashboards.
    Explanation: Operational tooling and UI workloads benefit from different interaction patterns.

32. Question: How can processCount be useful in monitoring?
    Answer: It indicates process leaks, crash loops, or workload spikes.
    Explanation: Sudden drift from baseline can reveal platform instability.

33. Question: What is the difference between fixedRate and fixedDelay scheduling?
    Answer: fixedRate measures from start time; fixedDelay from completion time.
    Explanation: fixedDelay is often safer when job duration can vary.

34. Question: How do NOC dashboards help incident response?
    Answer: They unify telemetry, alerts, and service health in one view.
    Explanation: Centralized context reduces mean time to detect and triage issues.

35. Question: Why include uptimeSeconds as a metric?
    Answer: It helps detect restarts and stability regressions.
    Explanation: Uptime trends reveal reboot storms or unstable deployments.

## Advanced

36. Question: How would you scale this architecture for thousands of agents?
    Answer: Introduce ingestion queues, horizontal collectors, and partitioned storage.
    Explanation: Decoupled pipelines prevent API nodes from becoming ingestion bottlenecks.

37. Question: What are tradeoffs of in-memory H2 for monitoring?
    Answer: Fast local dev but no durable historical retention.
    Explanation: Production systems generally use durable TSDB or relational clusters.

38. Question: How would you add retention policies?
    Answer: Schedule archival/deletion based on age and granularity tiers.
    Explanation: Retention controls storage cost and query performance over long timelines.

39. Question: How can alert fatigue be reduced?
    Answer: Deduplication, grouping, suppression windows, and smarter thresholds.
    Explanation: Quality alerting focuses operator attention on actionable incidents.

40. Question: How does this map to Prometheus + Alertmanager + Grafana?
    Answer: Metrics collection/storage, alert evaluation/notification, and visualization.
    Explanation: The Day-20 design mirrors these core responsibilities in simplified form.

41. Question: What is cardinality risk in metric systems?
    Answer: Excessive unique label combinations exploding storage/query costs.
    Explanation: Label design must balance observability granularity and system scalability.

42. Question: How would you harden agent registration APIs?
    Answer: Add authN/authZ, mTLS, rate limits, and input sanitization.
    Explanation: Monitoring control planes are high-value attack surfaces.

43. Question: Why is observability more than monitoring?
    Answer: It includes logs, traces, and context-rich debugging signals.
    Explanation: Monitoring detects known failure patterns; observability helps explain unknown ones.

44. Question: How can WebSocket backpressure affect dashboards?
    Answer: Slow consumers can lag, buffer overflow, or drop messages.
    Explanation: Systems need throttling, sampling, and client-side reconciliation.

45. Question: What consistency model is acceptable for real-time dashboards?
    Answer: Eventual consistency with bounded staleness.
    Explanation: Operational dashboards prioritize freshness and continuity over strict transactionality.

46. Question: How would you test alert engine correctness?
    Answer: Rule-level unit tests plus integration tests with deterministic metric fixtures.
    Explanation: Include comparator edge cases and duplicate suppression behavior.

47. Question: What is the role of synthetic monitoring in this architecture?
    Answer: Generates controlled probes to validate monitoring pipeline health.
    Explanation: Synthetic checks detect blind spots in telemetry ingestion and alerting.

48. Question: How can SNMP be integrated with modern telemetry standards?
    Answer: Map OID data to common metric schemas and export pipelines.
    Explanation: Translation layers allow coexistence of legacy and cloud-native systems.

49. Question: How can reporting be made useful for operations leadership?
    Answer: Include trend deltas, SLA impact, and recurring failure patterns.
    Explanation: Reports should support decision-making, not just raw incident counts.

50. Question: How do you design for multi-tenant monitoring platforms?
    Answer: Isolate data, rules, auth scopes, and notification channels per tenant.
    Explanation: Tenant-aware isolation is necessary for security and scale in shared platforms.

51. Question: What should be tracked to improve MTTR?
    Answer: Detection latency, acknowledgment time, resolution time, and escalation hops.
    Explanation: These metrics expose operational bottlenecks in incident workflows.

52. Question: How would you migrate from simulation to production ingestion?
    Answer: Replace synthetic collectors with real agent/SNMP collectors and durable storage.
    Explanation: Keep service contracts stable so the integration layer can evolve incrementally.
