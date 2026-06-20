# Day 17 - Interview Questions

1. What is the difference between monitoring and alarming?
   - Monitoring observes state.
   - Alarming forces action.
   - Operators use alarms to restore service.

2. What triggers an alarm in a NOC?
   - A threshold violation.
   - A fault indication.
   - A correlation rule can also raise an incident.

3. Why are thresholds important?
   - They convert raw telemetry into actionable events.
   - They avoid operator overload.
   - They define when the system should page someone.

4. How do you assign alarm severity?
   - By business impact.
   - By service risk.
   - By how quickly the fault can damage operations.

5. What is the purpose of the `INFO` severity?
   - It records informational conditions.
   - It does not usually require urgent action.
   - It is useful for trend analysis.

6. When should a `WARNING` alarm be used?
   - When the issue needs observation.
   - When failure may follow soon.
   - When the operator should watch the asset closely.

7. What makes an alarm `MAJOR`?
   - Service degradation is likely.
   - Response is needed soon.
   - Escalation may follow if unresolved.

8. What makes an alarm `CRITICAL`?
   - Immediate service impact.
   - Clear outage risk.
   - Urgent operator action.

9. What is alarm acknowledgement?
   - An operator takes ownership.
   - The alarm is no longer anonymous.
   - It remains visible until resolved.

10. Why track acknowledgement timestamps?
    - To measure response time.
    - To audit operator actions.
    - To support SLA reporting.

11. What does alarm escalation mean?
    - The alarm is handed to a higher-priority responder.
    - The incident exceeded a time or severity policy.
    - The issue now needs broader attention.

12. How do escalation policies work?
    - They define time limits.
    - They map severity to teams.
    - They route unresolved alarms upward.

13. Why is alarm lifecycle management important?
    - It avoids inconsistent incident handling.
    - It makes status transitions auditable.
    - It keeps operations predictable.

14. What is the difference between `OPEN` and `ACKNOWLEDGED`?
    - Open means visible and unresolved.
    - Acknowledged means owned by an operator.
    - Both can still be active.

15. What is the difference between `RESOLVED` and `CLOSED`?
    - Resolved means the technical fault is fixed.
    - Closed means the incident is administratively complete.
    - Closure usually happens after review.

16. What is alarm correlation?
    - Grouping related alarms.
    - Reducing duplicate alerts.
    - Linking symptoms to one incident.

17. Why does a NOC need correlation?
    - To reduce alarm storms.
    - To focus operators on root events.
    - To prevent duplicate investigations.

18. What is an alarm storm?
    - A burst of alarms from one failure.
    - Often caused by a shared root cause.
    - It overwhelms the operations desk.

19. How do you control an alarm storm?
    - Correlate alarms.
    - Suppress duplicates.
    - Escalate only the incident.

20. What is root cause analysis in alarm management?
    - Finding the source fault.
    - Collapsing symptoms into one cause.
    - Helping operators avoid chasing noise.

21. Why is RCA hard in distributed systems?
    - One fault can create many symptoms.
    - Timing and propagation are complex.
    - Data may be incomplete.

22. How does a power failure appear in alarms?
    - Device unreachable.
    - Interface down.
    - Signal loss or service loss.

23. What is the role of notification channels?
    - Deliver alarm information.
    - Reach the right responder.
    - Preserve delivery history.

24. Which channels are common in production NOCs?
    - Email.
    - SMS.
    - Webhooks and ticketing systems.

25. Why simulate notification delivery?
    - To keep the module self-contained.
    - To test operational workflows.
    - To avoid external dependencies.

26. What is an alarm event?
    - A timestamped lifecycle record.
    - A note about a transition.
    - An audit trail entry.

27. Why keep audit history for alarms?
    - Compliance.
    - SLA analysis.
    - Root cause review.

28. How is a threshold different from a rule?
    - The threshold is the limit.
    - The rule is the full condition.
    - The rule includes severity and action.

29. What telemetry fields are useful for alarming?
    - CPU.
    - Temperature.
    - BER, EbNo, and signal strength.

30. What alarms are common in telecom NOCs?
    - Interface down.
    - Carrier unlock.
    - High BER.

31. What alarms are common in SatCom operations?
    - EbNo low.
    - BUC failure.
    - LNB failure.

32. What alarms are common in data centers?
    - Database down.
    - Application down.
    - Memory exhaustion.

33. What alarms appear in SCADA environments?
    - Sensor link loss.
    - Temperature violations.
    - Communication failure.

34. How does Spring Boot help build an alarm backend?
    - It handles REST APIs.
    - It simplifies persistence.
    - It supports clean service layering.

35. Why use Spring Data JPA here?
    - For concise repository access.
    - For query support.
    - For relational history storage.

36. Why use H2 in the module?
    - It keeps the lab self-contained.
    - It starts quickly.
    - It is suitable for deterministic examples.

37. Why is determinism important in learning modules?
    - Tests must be repeatable.
    - Scripts must be reliable.
    - Outputs should not fluctuate unexpectedly.

38. How do you make alarm generation deterministic?
    - Use seeded data.
    - Use controlled timestamps.
    - Avoid random external dependencies.

39. What is a correlated incident?
    - A grouped view of related alarms.
    - A single operational case.
    - A better operator experience than raw alerts.

40. Why is a dashboard still important if alarms exist?
    - It summarizes the operating picture.
    - It supports executive and NOC views.
    - It helps prioritize work.

41. What should a NOC dashboard show?
    - Open alarms.
    - Critical alarms.
    - Correlated incidents and RCA summary.

42. Why track timestamps for each lifecycle stage?
    - To calculate response and recovery times.
    - To identify bottlenecks.
    - To audit operator actions.

43. What is the first action after a critical alarm appears?
    - Acknowledge it.
    - Confirm the service impact.
    - Start incident handling.

44. How do you decide if an alarm should escalate?
    - Use the policy timer.
    - Check severity.
    - Verify whether it was resolved.

45. Why should correlated alarms not all page operators?
    - They create duplicate noise.
    - They hide the root event.
    - They waste response time.

46. What is the relationship between an alarm and a ticket?
    - The alarm is the technical signal.
    - The ticket is the operational work item.
    - They often share the same incident lifecycle.

47. How can a telecom alarm point to a power problem?
    - Loss of device reachability.
    - Multiple downstream link alarms.
    - A shared correlation key.

48. Why is RCA useful after the outage is restored?
    - It prevents repeat incidents.
    - It improves engineering follow-up.
    - It supports post-incident review.

49. What should be avoided in an alarm platform?
    - Blind alert flooding.
    - Unbounded escalation loops.
    - State transitions without audit.

50. What is the main teaching goal of Day 17?
    - To show that monitoring finds issues.
    - Alarm management ensures action.
    - Operators need lifecycle, priority, escalation, and RCA.
