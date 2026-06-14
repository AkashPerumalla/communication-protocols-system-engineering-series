# Day 13 Interview Questions

1. Question: What is an SNMP trap?
   Answer: An SNMP trap is an asynchronous notification sent from an agent to a management system.
   Explanation: Unlike polling, traps are pushed when an event occurs.

2. Question: Why are traps important in fault management?
   Answer: They shorten detection time for critical conditions.
   Explanation: Operators do not need to wait for the next polling cycle.

3. Question: What is the difference between a trap and a notification?
   Answer: A trap is the event payload, while notification is the broader operational delivery concept.
   Explanation: In practice, platforms route and enrich traps as notifications.

4. Question: What does a trap receiver do?
   Answer: It accepts incoming events and normalizes them for the processing pipeline.
   Explanation: This is the first boundary in the NMS event flow.

5. Question: Why should trap processing be separated into stages?
   Answer: It makes validation, storage, and forwarding maintainable and testable.
   Explanation: Each stage can evolve independently.

6. Question: What fields should every trap carry?
   Answer: Timestamp, device name, source IP, trap OID, type, severity, description, acknowledgment state, and event ID.
   Explanation: These fields make the event useful for operations and correlation.

7. Question: Why is deterministic event ID generation helpful in a simulator?
   Answer: It keeps outputs stable across runs.
   Explanation: Deterministic logs are easier to verify and explain.

8. Question: Why is trap severity important?
   Answer: It drives alert routing and response priority.
   Explanation: A critical alarm should not follow the same workflow as an informational trap.

9. Question: What are the common severity levels in this lab?
   Answer: INFO, WARNING, MINOR, MAJOR, and CRITICAL.
   Explanation: This matches a realistic fault-management scale.

10. Question: How does an alarm differ from a trap?
    Answer: An alarm is the operational state created from one or more events.
    Explanation: Alarms can be acknowledged or cleared, while traps are event records.

11. Question: Why do NMS platforms convert traps into alarms?
    Answer: To manage operator workflow and lifecycle state.
    Explanation: Traps alone are too raw for incident handling.

12. Question: What does OPEN mean for an alarm?
    Answer: The alarm is active and still requires attention.
    Explanation: It has not been cleared or fully resolved.

13. Question: What does ACKNOWLEDGED mean for an alarm?
    Answer: An operator has seen the issue and started handling it.
    Explanation: The alarm remains open until the condition clears.

14. Question: What does CLEARED mean for an alarm?
    Answer: The underlying fault is no longer active.
    Explanation: The alarm can be closed out in the workflow.

15. Question: Why is validation necessary before storing a trap?
    Answer: It prevents malformed events from polluting the repository.
    Explanation: Bad data becomes expensive in an operations platform.

16. Question: What is the role of the event repository?
    Answer: It holds in-memory trap and alarm history for the lab.
    Explanation: This allows repeatable processing and correlation.

17. Question: How does the receiver help with asynchronous notifications?
    Answer: It acts as the ingestion boundary between the agent and the NMS.
    Explanation: The receiver can normalize and validate before forwarding.

18. Question: What does the processor’s forward step represent?
    Answer: It represents handing the event to downstream notification or alarm workflows.
    Explanation: In production, this might mean ticketing, paging, or SIEM routing.

19. Question: What is root cause correlation?
    Answer: It is the process of identifying the original fault behind multiple symptoms.
    Explanation: This reduces alert storms and improves incident quality.

20. Question: Why correlate power failure and device unreachable together?
    Answer: Because the unreachable event is often the symptom, not the source.
    Explanation: Power loss often explains subsequent loss of contact.

21. Question: What should a correlation engine avoid?
    Answer: Treating every alert as an independent incident.
    Explanation: That causes noisy and duplicated ticketing.

22. Question: How is telecom monitoring different from generic IT monitoring?
    Answer: Telecom monitoring cares about RF values like BER, carrier lock, and RF power.
    Explanation: Service health depends on signal quality as well as reachability.

23. Question: Why is BER important in telecom operations?
    Answer: High BER indicates a deteriorating signal path.
    Explanation: It often precedes service impact.

24. Question: Why is RF power monitored?
    Answer: It indicates whether the signal is arriving within acceptable bounds.
    Explanation: Low or unstable power can cause link degradation.

25. Question: What does carrier lock tell an operator?
    Answer: Whether the modem has successfully locked to the carrier.
    Explanation: Loss of lock is a strong fault indicator.

26. Question: What is SatCom fault management focused on?
    Answer: Power units, demodulation state, and terminal stability.
    Explanation: Satellite systems are highly sensitive to RF chain health.

27. Question: Why are BUC and LNB important in SatCom?
    Answer: They are core RF components for uplink and downlink paths.
    Explanation: Failures in either can break service.

28. Question: What does Eb/No represent?
    Answer: Energy per bit over noise density.
    Explanation: It is a signal quality metric commonly used in satellite systems.

29. Question: Why does the NOC dashboard aggregate by device and severity?
    Answer: Operators need a fast operational summary.
    Explanation: Severity and impacted devices are the first things to triage.

30. Question: What should a NOC dashboard show first?
    Answer: Open alarms and critical alarms.
    Explanation: Those determine immediate operational priority.

31. Question: Why is deterministic ordering useful in dashboards?
    Answer: It keeps outputs easy to compare and verify.
    Explanation: Stable ordering is important in automated tests and walkthroughs.

32. Question: Why are immutable records useful in this lab?
    Answer: They make model objects concise and safe to share.
    Explanation: Immutable state reduces accidental mutation.

33. Question: Why keep exercises thin?
    Answer: Thin exercises keep business logic in reusable core services.
    Explanation: That is closer to how production systems are organized.

34. Question: What design principle is reinforced by the core layer?
    Answer: Single responsibility and separation of concerns.
    Explanation: Each class has one clear role in the pipeline.

35. Question: Why avoid external dependencies here?
    Answer: The module should compile and run with plain Java 17.
    Explanation: Self-contained code is easier to teach and validate.

36. Question: What is the value of a notification service?
    Answer: It centralizes routing and operator-facing message formatting.
    Explanation: Different severities can be handled differently.

37. Question: Why are timestamps important in traps?
    Answer: They help reconstruct event order and incident timelines.
    Explanation: Correlation depends on knowing when events occurred.

38. Question: What makes a trap meaningful to an operator?
    Answer: Context such as device, severity, description, and related values.
    Explanation: Raw trap data without context is difficult to act on.

39. Question: How does this lab reflect real NMS workflows?
    Answer: It simulates agent-to-receiver ingestion, alarm creation, routing, and dashboarding.
    Explanation: Those are the same conceptual steps used by operational platforms.

40. Question: What should you check first when troubleshooting trap flow?
    Answer: Whether the trap was generated, received, validated, and stored correctly.
    Explanation: The fastest way to isolate the problem is to inspect each pipeline stage.

41. Question: Why might a trap be received but not become an alarm?
    Answer: The conversion rule may not consider it actionable.
    Explanation: Not every event should create an alert.

42. Question: Why is a trap repository useful for correlation?
    Answer: It gives the correlation engine a scoped event history.
    Explanation: Root cause detection depends on seeing related events together.

43. Question: What is the operational difference between major and critical alarms?
    Answer: Critical alarms usually require immediate paging and escalation.
    Explanation: Major alarms are urgent, but may not be as severe as critical faults.

44. Question: Why should a simulator use realistic values like BER 1.2E-6 or Eb/No 4.2 dB?
    Answer: Realistic values make the learning module feel like an actual operations platform.
    Explanation: Engineers learn faster from believable telemetry.
