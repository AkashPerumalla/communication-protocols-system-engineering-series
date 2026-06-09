# Day 08 - SNMP Interview Questions

## SNMP Fundamentals

### 1. What problem does SNMP solve?
Question: What problem does SNMP solve in network operations?
Answer: It provides a standard way to monitor and manage networked devices.
Explanation: SNMP lets a manager poll agents for status and configuration data.

### 2. What is the SNMP manager?
Question: What is the role of the SNMP manager?
Answer: It polls devices, collects metrics, and presents alarms and status.
Explanation: The manager is the control point of the NMS workflow.

### 3. What is the SNMP agent?
Question: What is the role of the SNMP agent?
Answer: It exposes device data through managed objects in the MIB.
Explanation: The agent lives on the device being monitored or simulated.

### 4. What is a MIB?
Question: What is a Management Information Base?
Answer: It is the hierarchical database of managed objects.
Explanation: Each object is addressed by an OID and can be read or written depending on access.

## MIB and OID

### 5. What is an OID?
Question: What is an object identifier?
Answer: It is a dotted numeric path that uniquely identifies a managed object.
Explanation: OIDs are used by the manager to address exact values in the MIB.

### 6. How does an OID hierarchy work?
Question: How is the OID hierarchy structured?
Answer: It follows a tree where each node refines the managed object namespace.
Explanation: Higher-level branches represent families of objects and lower nodes represent specific variables.

### 7. Why is lexicographic ordering important?
Question: Why does SNMP care about lexicographic order?
Answer: GETNEXT and walk operations depend on ordered traversal.
Explanation: The manager discovers the next object by comparing OIDs in sequence.

### 8. What is a subtree walk?
Question: What is a subtree walk in SNMP?
Answer: It is the process of traversing a portion of the MIB until the subtree ends.
Explanation: It is commonly used for inventory and discovery.

## GET / GETNEXT / GETBULK

### 9. What does GET do?
Question: What does a GET request return?
Answer: It returns the value of a specific OID.
Explanation: The manager asks for one managed object and the agent responds with the current value.

### 10. What does GETNEXT do?
Question: What does a GETNEXT request return?
Answer: It returns the next object after the requested OID.
Explanation: It is the basic primitive behind SNMP walking.

### 11. What is GETBULK?
Question: What is GETBULK used for?
Answer: It fetches many objects efficiently in fewer round trips.
Explanation: It is useful for large tables and reduces poll overhead in SNMPv2c and later.

### 12. When would you use GETNEXT instead of GET?
Question: When is GETNEXT more appropriate than GET?
Answer: When you need to discover or enumerate a subtree.
Explanation: GET is for one known value, while GETNEXT is for traversal.

## SET Operations

### 13. What does SET do?
Question: What is the purpose of SNMP SET?
Answer: It changes a writable managed object.
Explanation: SET is used for configuration, control, and operational updates.

### 14. Why are many OIDs read-only?
Question: Why are many SNMP values read-only?
Answer: Because they represent live state rather than configuration.
Explanation: Devices expose status safely, but not every state should be writable.

### 15. How should SET values be validated?
Question: How should a SET request validate its value?
Answer: It should check access mode, data type, and allowed value range.
Explanation: Validation prevents invalid configuration and unsafe state changes.

### 16. Why did the lab reject sysDescr?
Question: Why was sysDescr rejected in the SET lab?
Answer: Because sysDescr is treated as read-only.
Explanation: This demonstrates access control and configuration boundaries.

## Traps vs Informs

### 17. What is an SNMP trap?
Question: What is an SNMP trap?
Answer: It is an unsolicited notification sent by an agent.
Explanation: Traps report events like link down, power failure, or temperature alarms.

### 18. What is an inform?
Question: What is an SNMP inform?
Answer: It is a notification that expects acknowledgement.
Explanation: Informs are more reliable than traps but also slightly heavier.

### 19. When should traps be used?
Question: When are traps a good fit?
Answer: For asynchronous operational events that should reach the NMS immediately.
Explanation: They complement polling by pushing important state changes.

### 20. Why are traps valuable in telecom?
Question: Why are traps useful in telecom and SatCom environments?
Answer: They surface critical link, lock, and RF alarms quickly.
Explanation: Telecom systems often need immediate attention for signal and hardware events.

## SNMP Versions

### 21. What does SNMPv1 provide?
Question: What are the basics of SNMPv1?
Answer: It provides core GET, GETNEXT, SET, and trap semantics.
Explanation: It is the earliest widely adopted version and still easy to study.

### 22. What improved in SNMPv2c?
Question: What improved in SNMPv2c?
Answer: It added GETBULK and better performance-oriented operations.
Explanation: SNMPv2c is commonly used in lab and legacy environments.

### 23. What is the main benefit of SNMPv3?
Question: Why is SNMPv3 preferred in production?
Answer: It adds authentication, integrity, and encryption.
Explanation: That makes it suitable for secure operational environments.

### 24. Why is SNMPv1/v2c weaker than v3?
Question: Why are SNMPv1 and SNMPv2c considered weaker?
Answer: They rely on community strings without strong security.
Explanation: Community strings behave like shared secrets but do not provide full cryptographic protection.

## Community Strings and Security

### 25. What is a community string?
Question: What is a community string?
Answer: It is a shared access token used by SNMPv1 and SNMPv2c.
Explanation: It identifies the access group, but it is not a strong security boundary.

### 26. What is SNMP authentication?
Question: What does SNMP authentication verify?
Answer: It verifies the identity of the manager or user.
Explanation: In SNMPv3, authentication prevents unauthorized polling and control.

### 27. What is SNMP privacy?
Question: What does SNMP privacy do?
Answer: It encrypts the SNMP payload.
Explanation: Privacy protects sensitive operational data from being read in transit.

### 28. What is SNMP integrity?
Question: What does integrity protection provide?
Answer: It ensures the data has not been altered in transit.
Explanation: Integrity helps detect tampering or corruption.

## Telecom Monitoring

### 29. Why use SNMP in telecom networks?
Question: Why is SNMP common in telecom operations?
Answer: It provides a standard way to track device health and service state.
Explanation: Telecom NMS platforms often monitor large fleets of managed equipment.

### 30. What is SatCom HUB monitoring?
Question: What does SatCom HUB monitoring focus on?
Answer: It focuses on RF chain health, lock status, and equipment telemetry.
Explanation: Signal quality and temperature are as important as standard IP metrics.

### 31. Why monitor BER?
Question: Why is BER important in telecom?
Answer: BER measures communication quality and error frequency.
Explanation: A rising BER often indicates signal or equipment degradation.

### 32. Why monitor lock status?
Question: Why is lock status important?
Answer: It shows whether the modem or demodulator has a valid carrier lock.
Explanation: Loss of lock is a direct service-impacting condition.

## NMS Systems

### 33. What does an NMS do?
Question: What is the purpose of an NMS?
Answer: It centralizes visibility, alarms, metrics, and device control.
Explanation: NMS platforms reduce operational complexity across many devices.

### 34. What is alarm correlation?
Question: What is alarm correlation?
Answer: It groups related events into a meaningful operational incident.
Explanation: Correlation prevents alert storms and highlights root causes.

### 35. Why are dashboards important?
Question: Why do operators rely on dashboards?
Answer: They provide instant operational status and trends.
Explanation: Dashboards help NOC staff prioritize response quickly.

### 36. What is the difference between polling and traps?
Question: What is the difference between polling and traps?
Answer: Polling is manager-driven, while traps are agent-driven.
Explanation: Polling gives periodic snapshots; traps report events as they happen.

## Troubleshooting

### 37. Why might a GET return no such object?
Question: Why would a GET request return no such object?
Answer: The OID may not exist in the agent’s MIB.
Explanation: It can also mean the manager targeted the wrong branch.

### 38. Why might SET be rejected?
Question: Why can a SET request be rejected?
Answer: The OID may be read-only, or the value may fail validation.
Explanation: Agents should protect their state against unsafe writes.

### 39. Why did the walk stop early?
Question: Why might an SNMP walk stop before the full tree is discovered?
Answer: The subtree may have ended or the agent may not expose more nodes.
Explanation: Walks stop when there is no lexicographically next object in the requested subtree.

### 40. How do you troubleshoot missing traps?
Question: How do you troubleshoot missing SNMP traps?
Answer: Check agent configuration, receiver availability, network reachability, and severity filters.
Explanation: Traps can be lost if the receiver is down or blocked by policy.

### 41. What should you check when a dashboard looks stale?
Question: What should you check when a dashboard is stale?
Answer: Verify polling loops, timing, device reachability, and alarm refresh logic.
Explanation: A stale dashboard usually means the refresh path is not executing or data is not changing.

### 42. How does the lab map to production tools?
Question: How does this lab map to production tools like SolarWinds or Zabbix?
Answer: It models the same poll, trap, alarm, and dashboard lifecycle in a simplified local simulator.
Explanation: The implementation is smaller, but the operator workflow is the same.
