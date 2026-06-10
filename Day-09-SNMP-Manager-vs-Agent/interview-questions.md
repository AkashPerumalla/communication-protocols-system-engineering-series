# Day 09 - SNMP Interview Questions

## SNMP Fundamentals

### 1. What problem does SNMP solve?
Question: What problem does SNMP solve in operations teams?
Answer: It standardizes how managers monitor and manage networked devices.
Explanation: SNMP gives operators a common way to query status, alarms, and counters.

### 2. What is the SNMP manager?
Question: What is the role of the SNMP manager?
Answer: It polls devices, aggregates results, and shows operational state.
Explanation: The manager is the control point of the monitoring workflow.

### 3. What is the SNMP agent?
Question: What is the role of the SNMP agent?
Answer: It exposes managed objects and responds to the manager.
Explanation: The agent sits on the managed device or simulator.

### 4. What is a MIB?
Question: What is a Management Information Base?
Answer: It is the tree of managed objects exposed by an agent.
Explanation: Each object has an OID, type, and value.

## OID and MIB

### 5. What is an OID?
Question: What is an object identifier?
Answer: It is a dotted numeric path that uniquely identifies a managed object.
Explanation: Managers use OIDs to address exact data points.

### 6. Why is OID ordering important?
Question: Why does SNMP care about OID order?
Answer: GETNEXT and walks depend on lexicographic traversal.
Explanation: Ordered traversal lets the manager discover objects in sequence.

### 7. What is MIB-II?
Question: What is MIB-II?
Answer: It is the standard collection of core management objects.
Explanation: It includes common values such as sysName, sysDescr, and ifOperStatus.

### 8. Why do managers browse the MIB?
Question: Why use a MIB browser?
Answer: It helps operators inspect objects, types, and values by OID.
Explanation: A browser is useful for discovery and troubleshooting.

## GET, GETNEXT, GETBULK, SET

### 9. What does GET do?
Question: What does a GET request return?
Answer: It returns the value of one specific object.
Explanation: GET is the simplest read operation in SNMP.

### 10. What does GETNEXT do?
Question: What does GETNEXT return?
Answer: It returns the next object after the requested OID.
Explanation: GETNEXT is the primitive behind SNMP walks.

### 11. What is GETBULK?
Question: What is GETBULK used for?
Answer: It retrieves multiple objects efficiently in fewer round trips.
Explanation: It reduces polling overhead in SNMPv2c and later.

### 12. What does SET do?
Question: What is SNMP SET used for?
Answer: It writes a new value to a writable object.
Explanation: SET is used for configuration or control.

### 13. Why are many OIDs read-only?
Question: Why are many SNMP objects read-only?
Answer: They represent live state rather than configuration.
Explanation: Agents should protect operational data from unsafe writes.

### 14. How should SET validate data?
Question: How should a SET request validate the value?
Answer: It should check access mode, type, and allowed ranges.
Explanation: Validation prevents invalid or dangerous configuration.

## Traps and Informs

### 15. What is an SNMP trap?
Question: What is an SNMP trap?
Answer: It is an unsolicited event sent by the agent.
Explanation: Traps report faults such as link down or high temperature.

### 16. What is an inform?
Question: What is an SNMP inform?
Answer: It is a notification that expects acknowledgement.
Explanation: Informs are more reliable than traps, but slightly heavier.

### 17. Why are traps important?
Question: Why do NMS platforms rely on traps?
Answer: They deliver urgent events immediately.
Explanation: Traps complement polling by pushing fault notifications.

### 18. Why are traps useful in telecom?
Question: Why are traps valuable in telecom networks?
Answer: They quickly surface RF and device alarms.
Explanation: Carrier loss, unlock, and power failures need immediate attention.

## Versions and Security

### 19. What does SNMPv1 provide?
Question: What does SNMPv1 support?
Answer: Core GET, GETNEXT, SET, and trap semantics.
Explanation: It is the simplest version for learning and legacy devices.

### 20. What improved in SNMPv2c?
Question: What did SNMPv2c improve?
Answer: It added GETBULK and better efficiency.
Explanation: It is common in labs and legacy monitoring setups.

### 21. Why use SNMPv3?
Question: Why is SNMPv3 preferred in production?
Answer: It adds authentication, integrity, and privacy.
Explanation: That makes the protocol safer for operational environments.

### 22. What is a community string?
Question: What is a community string?
Answer: It is a shared access token used by SNMPv1 and SNMPv2c.
Explanation: It identifies access but is not strong cryptographic security.

### 23. What does authentication protect?
Question: What does SNMP authentication protect?
Answer: It verifies who is talking to the agent.
Explanation: Authentication helps block unauthorized access.

### 24. What does privacy protect?
Question: What does SNMP privacy protect?
Answer: It encrypts the payload.
Explanation: Privacy keeps sensitive monitoring data confidential.

### 25. What does integrity protect?
Question: What does SNMP integrity protect?
Answer: It detects tampering or corruption in transit.
Explanation: Integrity helps ensure the data has not been altered.

## Manager vs Agent

### 26. How do manager and agent differ?
Question: How do the manager and agent differ?
Answer: The manager polls and presents data, while the agent serves objects.
Explanation: They are the two sides of the SNMP control loop.

### 27. Why simulate both roles locally?
Question: Why simulate manager and agent in the lab?
Answer: It keeps the exercises portable and easy to run.
Explanation: The user can learn the architecture without hardware access.

### 28. How do managers scale?
Question: How do managers scale across many devices?
Answer: They poll fleets, aggregate status, and apply thresholds.
Explanation: NMS tools use centralized monitoring to reduce complexity.

## Monitoring Use Cases

### 29. Why poll CPU and memory?
Question: Why monitor CPU and memory?
Answer: They indicate load and capacity pressure.
Explanation: Sustained growth often predicts performance issues.

### 30. Why monitor temperature?
Question: Why is temperature monitored?
Answer: It helps detect hardware stress and cooling problems.
Explanation: Rising temperature often precedes failures.

### 31. Why monitor interface status?
Question: Why track interface status?
Answer: It shows whether a link is up or down.
Explanation: Interfaces are core indicators of connectivity health.

### 32. Why monitor bandwidth?
Question: Why monitor bandwidth usage?
Answer: It reveals how heavily a link is being used.
Explanation: High utilization can lead to congestion.

### 33. Why monitor BER?
Question: Why is BER important in telecom?
Answer: It measures signal quality and error rate.
Explanation: A worsening BER often indicates RF degradation.

### 34. Why monitor lock status?
Question: Why is lock status important?
Answer: It shows whether the modem chain has carrier lock.
Explanation: Loss of lock is a service-impacting event.

### 35. Why monitor Tx and Rx power?
Question: Why track Tx and Rx power?
Answer: They show RF chain strength and alignment.
Explanation: Power anomalies can indicate faulty equipment or misalignment.

## NMS Architecture

### 36. What does an NMS do?
Question: What is the purpose of an NMS?
Answer: It centralizes visibility, alarms, and device control.
Explanation: NMS platforms reduce operational complexity.

### 37. What is alarm correlation?
Question: What is alarm correlation?
Answer: It groups related events into one incident.
Explanation: Correlation prevents alert storms.

### 38. Why do dashboards matter?
Question: Why are dashboards important?
Answer: They show the current operational picture quickly.
Explanation: Operators need an immediate summary during incidents.

### 39. What is the difference between polling and traps?
Question: What is the difference between polling and traps?
Answer: Polling is manager-driven; traps are agent-driven.
Explanation: Polling gives snapshots; traps deliver immediate events.

### 40. How do SNMP tools map to the real world?
Question: How do SNMP tools map to enterprise environments?
Answer: They power monitoring, alerting, and operational troubleshooting.
Explanation: SolarWinds, PRTG, Zabbix, Nagios, and telecom NMS tools all use the same pattern.
