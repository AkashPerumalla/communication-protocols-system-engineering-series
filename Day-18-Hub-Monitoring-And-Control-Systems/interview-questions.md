# Day-18 Interview Questions: Hub Monitoring and Control Systems

## Beginner Level

1. What is a HUB Monitoring and Control system in SatCom operations?
Answer: It is a centralized platform that monitors device health, raises alarms, performs control actions, and supports NOC decision-making.

2. Why is deterministic telemetry useful in test environments?
Answer: It guarantees reproducible behavior, stable assertions, and predictable marker validation.

3. What is the difference between monitoring and control?
Answer: Monitoring observes and reports status; control changes operational state through commands.

4. Why are device types modeled as enums?
Answer: Enums enforce valid categories and simplify rule mapping for alarms and automation.

5. Why is Bean Validation used on command requests?
Answer: It prevents malformed control operations and enforces operator input quality.

6. What role does JPA play in this module?
Answer: JPA persists devices, metrics, alarms, control history, notifications, and reports in a structured relational model.

7. Why should alarm severity be explicit?
Answer: Explicit severity enables differentiated response and escalation policies.

8. What is an audit trail in remote control workflows?
Answer: It is a tamper-evident record of who executed what command, when, and with what result.

9. Why expose dashboard data through a DTO?
Answer: DTOs decouple internal entities from API contracts and support aggregation-specific payloads.

10. What is the purpose of an API marker string?
Answer: It enables deterministic smoke testing for expected workflow execution.

## Intermediate Level

11. How does threshold-based alarming work in this implementation?
Answer: Latest metrics are evaluated against rule bands for signal, temperature, power, CPU, and interface state to produce severity-specific alarms.

12. Why is Interface DOWN modeled separately from device OFFLINE?
Answer: A device can remain reachable while one interface fails; these conditions imply different recovery actions.

13. How is device availability computed?
Answer: Availability percentage equals online device count divided by total device count, multiplied by 100.

14. Why is notification generation tied to critical alarms?
Answer: Critical events represent highest operational risk and require immediate multi-channel dissemination.

15. What is the operational value of report types such as daily and weekly summaries?
Answer: They provide shift-level and trend-level views for planning, RCA, and preventive operations.

16. Why should control failures create alarms?
Answer: Failed control actions may indicate deeper faults and must be visible to operators for escalation.

17. How does automation reduce mean time to recover?
Answer: It executes predefined corrective runbooks immediately after known fault patterns are detected.

18. Why keep command payload as data instead of hardcoding behavior?
Answer: It supports flexible execution context and preserves intent in the audit trail.

19. What is the significance of using Instant timestamps?
Answer: Instant provides unambiguous UTC timing suitable for distributed operations and correlation.

20. How is NOC health score useful?
Answer: It compresses multiple operational factors into a single readiness indicator for leadership dashboards.

21. What is the difference between MAJOR and CRITICAL alarms operationally?
Answer: MAJOR indicates severe degradation; CRITICAL indicates immediate service risk or outage.

22. Why should notification delivery status be persisted?
Answer: Persisted status supports compliance, troubleshooting, and retry policies.

23. How can top problem devices be identified from this model?
Answer: By combining alarm density, failed controls, and low health scores per device.

24. What benefit does a global exception handler add?
Answer: It standardizes API error responses and improves client-side integration predictability.

25. Why use repository query methods instead of manual filtering in controllers?
Answer: It keeps controller logic lean and places data-access concerns in repository/service layers.

## Advanced Level

26. How would you implement alarm deduplication?
Answer: Use a uniqueness key based on device, alarm type, and time window; update existing alarm state instead of inserting duplicates.

27. What is alarm correlation and why is it important?
Answer: Correlation links related alarms to a likely root cause, reducing operator noise and cognitive load.

28. How can you avoid alert fatigue in a large NOC?
Answer: Apply suppression, deduplication, severity gating, and context-aware escalation.

29. Why is deterministic seeding preferable over random simulation for CI?
Answer: Randomness introduces flaky assertions and unstable integration tests.

30. How would you model multi-tenant hub operations?
Answer: Add tenant boundaries to all domain entities, enforce scoped queries, and isolate automation policies per tenant.

31. How would you support asynchronous notification dispatch?
Answer: Use event outbox plus message queue consumers with retry, backoff, and dead-letter handling.

32. What would change to support real vendor adapters for control commands?
Answer: Introduce adapter interfaces per device family and route command execution through protocol-specific implementations.

33. How can command safety be improved for mission-critical systems?
Answer: Add RBAC, dual-approval workflows, dry-run simulation, and rollback plans.

34. How would you design SLA-aware alert escalation?
Answer: Track alarm age against policy, escalate channel and assignee tiers based on breach thresholds.

35. What is the impact of eventual consistency in distributed NOC architecture?
Answer: Dashboard views may lag writes; correlation and response logic must tolerate short staleness windows.

36. How would you implement performance trend analytics?
Answer: Store historical aggregates and compute moving windows for KPIs such as BER, EbNo, and uptime.

37. Why is separating command intent and command result important?
Answer: Intent captures requested action; result captures runtime outcome for forensic analysis.

38. How do you prevent automation loops?
Answer: Introduce idempotency keys, cooldown windows, and max-attempt counters.

39. How would you integrate ITSM ticketing robustly?
Answer: Add transactional outbox for ticket events, persist correlation IDs, and reconcile delivery acknowledgements.

40. What security controls are mandatory for remote hub control?
Answer: Strong authentication, least-privilege authorization, command signing, and immutable audit logs.

41. How can this model support blue/green firmware rollout?
Answer: Track firmware targets, staged cohorts, and post-change health validation before expanding rollout.

42. What database indexes are critical in this domain?
Answer: Index deviceId+timestamp for metrics, severity+status for alarms, and executionTime for controls.

43. How do you calculate a more accurate health score?
Answer: Use weighted KPIs, normalization by device type, and historical trend penalties rather than static thresholds.

44. How can you design for telemetry back-pressure?
Answer: Use ingestion buffering, bounded queues, and adaptive sampling under burst load.

45. What is the difference between synthetic and real telemetry in NOC training?
Answer: Synthetic telemetry is controlled and deterministic; real telemetry includes noise, jitter, and unknown anomalies.

46. How would you add root cause probability scoring?
Answer: Combine correlated alarm patterns, dependency graph signals, and Bayesian or rules-based scoring.

47. How do you enforce API contract stability for operations tools?
Answer: Version APIs, publish schema contracts, and run backward-compatibility tests.

48. Why should report generation be idempotent for scheduled runs?
Answer: Idempotency prevents duplicate records during retries and scheduler restarts.

49. How would you deploy this as a highly available service?
Answer: Run stateless app replicas behind load balancer, externalize DB, and use health probes with failover.

50. What metrics should be monitored for the monitoring platform itself?
Answer: API latency, failed control ratio, notification failure rate, alarm processing lag, and data freshness.
