# Day 15 Interview Questions and Answers

## Monitoring Architecture

1. Question: What is network monitoring architecture?
   Answer: It is the end-to-end design used to collect device telemetry, store it, evaluate health, and present operational visibility.
   Explanation: The architecture typically includes device discovery, polling, event handling, alerting, storage, and dashboards.

2. Question: Why do NOCs rely on both polling and event monitoring?
   Answer: Polling gives continuous state snapshots, while events provide immediate incident signals.
   Explanation: Using both methods reduces blind spots and gives operators both trend data and real-time notifications.

3. Question: What role does the monitoring database play?
   Answer: It stores devices, metrics, traps, alerts, and dashboard snapshots for historical and operational analysis.
   Explanation: A monitoring platform is only useful when the collected data is queryable and auditable over time.

4. Question: Why is a dashboard snapshot entity useful?
   Answer: It preserves a point-in-time operational summary that can be queried without recalculating every metric.
   Explanation: This helps reporting, trend analysis, and performance optimization.

5. Question: What is the difference between a metric and an alert?
   Answer: A metric is raw or processed telemetry, while an alert is an actionable incident generated from rules.
   Explanation: Metrics inform analysis; alerts drive operator response.

## Polling

6. Question: Why is the polling interval important?
   Answer: It balances freshness of data against load on devices and the monitoring platform.
   Explanation: Very short intervals increase noise and resource use, while long intervals delay detection.

7. Question: What does the polling engine do in this module?
   Answer: It simulates SNMP GET style collection and persists CPU, memory, temperature, uptime, and status values.
   Explanation: This mirrors how real monitoring tools periodically ask devices for health information.

8. Question: Why store polling results in a relational database?
   Answer: Relational storage makes aggregate queries, filtering, and dashboard calculations simple and reliable.
   Explanation: For learning and enterprise-style reporting, H2 and JPA are sufficient and easy to inspect.

9. Question: Why is a simulator needed in a monitoring demo?
   Answer: It allows the architecture to behave like a live system without requiring real network devices.
   Explanation: The simulator provides deterministic, testable telemetry patterns.

10. Question: What makes polling deterministic in this project?
    Answer: Seeded or repeatable simulation logic ensures stable behavior across tests and startup runs.
    Explanation: Determinism is important for reproducible validation and predictable dashboards.

## Traps and Events

11. Question: What is a trap in network monitoring?
    Answer: A trap is an unsolicited event from a device indicating a fault or operational change.
    Explanation: It is the event-driven counterpart to periodic polling.

12. Question: Why process traps asynchronously?
    Answer: Asynchronous processing prevents event bursts from blocking the main request or scheduler thread.
    Explanation: This improves responsiveness and models realistic NOC back-pressure handling.

13. Question: What trap types are modeled here?
    Answer: Interface down, high temperature, device restart, high CPU, and high memory.
    Explanation: These represent common operational conditions that require monitoring attention.

14. Question: Why do some traps become alerts and others do not?
    Answer: Only traps with operational impact should escalate to alerts; informational traps can remain events.
    Explanation: This keeps the alert channel focused on actionable incidents.

15. Question: What is the difference between trap severity and alert severity?
    Answer: Trap severity describes the event itself, while alert severity describes the generated operator action.
    Explanation: In many platforms the values align, but the lifecycle is still different.

## Alerting

16. Question: What does the alert engine evaluate?
    Answer: It checks CPU, memory, temperature, device online state, and trap-derived incidents.
    Explanation: Thresholds turn raw telemetry into actionable situations.

17. Question: Why are threshold rules essential in a NOC?
    Answer: They create consistency, reduce operator ambiguity, and automate incident creation.
    Explanation: A NOC cannot scale if humans must inspect every metric manually.

18. Question: Why use INFO, WARNING, and CRITICAL severities?
    Answer: They communicate operational urgency and help prioritize response.
    Explanation: Severity levels support triage, escalation, and reporting.

19. Question: Why is deduplication important in alert handling?
    Answer: Without deduplication, repeated polls can create duplicate alerts for the same condition.
    Explanation: Real monitoring systems update or correlate alerts instead of generating endless copies.

20. Question: What is the lifecycle of an alert?
    Answer: A condition is detected, an alert is created or refreshed, and the alert remains active until recovery or closure.
    Explanation: Lifecycle control is what turns raw detections into manageable incidents.

## Dashboard and Aggregation

21. Question: What does the dashboard aggregator compute?
    Answer: It calculates total devices, online devices, offline devices, active alerts, critical alerts, and average metrics.
    Explanation: This provides the NOC with a compact operational summary.

22. Question: Why do dashboards often use averages instead of raw values?
    Answer: Averages give a fleet-level view that is faster to interpret during incident response.
    Explanation: Detailed raw metrics still exist for drill-down analysis.

23. Question: Why store dashboard snapshots?
    Answer: Snapshots provide a historical record of what the NOC looked like at a specific time.
    Explanation: This is useful for audits, change reviews, and capacity analysis.

24. Question: Why is the dashboard endpoint useful in a demo project?
    Answer: It demonstrates how multiple monitoring subsystems converge into a single operational API.
    Explanation: Real NOC tools need one place where operators can assess health quickly.

25. Question: How does the app keep dashboard values realistic?
    Answer: It reads the latest metrics, counts alert states, and rounds calculated averages.
    Explanation: This produces stable, understandable numbers for the operator view.

## Telecom Monitoring

26. Question: What makes telecom monitoring different from general IT monitoring?
    Answer: Telecom monitoring includes RF and carrier metrics that are not relevant in ordinary server monitoring.
    Explanation: Examples include power, BER, carrier lock, frequency, and symbol rate.

27. Question: Why is Hub-01 treated specially?
    Answer: Hub-01 represents a telecom aggregation node where RF visibility matters.
    Explanation: Specialized services enrich the shared metric model with telecom-specific values.

28. Question: What is BER?
    Answer: BER means bit error rate, a measure of how often bits are received incorrectly.
    Explanation: Lower BER usually indicates a cleaner and more stable signal path.

29. Question: Why is carrier lock important?
    Answer: Carrier lock indicates whether the receiver has locked onto the expected carrier signal.
    Explanation: Loss of lock is often a critical telecom incident.

30. Question: Why is frequency tracked in GHz in this module?
    Answer: Satellite and telecom links are commonly measured in RF bands where GHz is a meaningful unit.
    Explanation: It helps operators verify channel configuration and link health.

## SatCom Monitoring

31. Question: What is EbNo?
    Answer: EbNo is the ratio of energy per bit to noise density, used to assess communication quality.
    Explanation: Higher EbNo usually means better link margin.

32. Question: Why does the app monitor both BUC and LNB status?
    Answer: These are critical satellite ground-segment components and their failure affects link availability.
    Explanation: Monitoring them gives operators visibility into the complete RF path.

33. Question: Why is Modem-01 specialized separately from Hub-01?
    Answer: The modem represents a distinct SatCom endpoint with different metrics and operational concerns.
    Explanation: Different device classes require different telemetry enrichment rules.

34. Question: What is uplink power used for?
    Answer: Uplink power shows the transmit side of the satellite link and helps diagnose signal margin issues.
    Explanation: Abnormal uplink power can indicate configuration or hardware problems.

35. Question: What is downlink power used for?
    Answer: Downlink power indicates the received signal strength from the satellite path.
    Explanation: It helps detect fade conditions and alignment issues.

## Spring Boot Design

36. Question: Why is Spring Boot a good fit for this module?
    Answer: It gives a production-style foundation for REST, scheduling, persistence, and configuration with minimal boilerplate.
    Explanation: The architecture becomes realistic while remaining easy to run and understand.

37. Question: Why use Spring Data JPA here?
    Answer: It provides repository abstractions for device, metric, trap, alert, and snapshot persistence.
    Explanation: The monitoring logic can focus on domain behavior instead of SQL plumbing.

38. Question: Why is scheduling important in monitoring applications?
    Answer: Scheduling drives periodic collection and event generation without manual intervention.
    Explanation: Monitoring systems must operate continuously and predictably.

39. Question: Why is Lombok used?
    Answer: Lombok reduces boilerplate in entities and service constructors.
    Explanation: This keeps the code easier to scan while preserving a clean architecture style.

40. Question: What is the main architectural lesson from this project?
    Answer: A realistic monitoring platform is a pipeline of discovery, telemetry, analysis, alerting, and visualization.
    Explanation: The project demonstrates how those layers fit together in a Spring Boot system.
