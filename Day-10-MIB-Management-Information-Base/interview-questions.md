# Day 10 - MIB Interview Questions

## MIB Fundamentals

### 1. What is a MIB?
Question: What is a Management Information Base?
Answer: It is the structured tree of managed objects that an SNMP agent exposes.
Explanation: The MIB defines what can be monitored or controlled on a device.

### 2. Why does MIB exist?
Question: Why do network devices need a MIB?
Answer: It standardizes how operational data is named, organized, and queried.
Explanation: A common structure lets many tools monitor many vendors consistently.

### 3. What is a managed object?
Question: What is a managed object in SNMP terms?
Answer: It is a named data element with an OID, type, access mode, and value.
Explanation: Examples include sysName, ifOperStatus, and vendor counters.

### 4. What is a scalar object?
Question: What is a scalar object?
Answer: It is a single-instance object, typically addressed with an instance suffix such as .0.
Explanation: System values like sysDescr and sysUpTime are scalars.

### 5. What is a table object?
Question: What is a table object in a MIB?
Answer: It is a collection of related rows and columns modeled under a table branch.
Explanation: Interface statistics are commonly represented as table objects.

## OID Structure

### 6. What is an OID?
Question: What is an object identifier?
Answer: It is a dotted numeric path that uniquely identifies a managed object.
Explanation: OIDs let SNMP clients address exact data points without ambiguity.

### 7. How is an OID hierarchy organized?
Question: How do you read an OID hierarchy?
Answer: Each numeric arc narrows the path from a broad branch to a specific object.
Explanation: For example, 1.3.6.1.2.1 leads to mib-2.

### 8. Why is OID ordering important?
Question: Why does ordering matter in SNMP?
Answer: GETNEXT and walks depend on lexicographic order through the tree.
Explanation: Ordered traversal is how managers discover neighboring objects.

### 9. What is the difference between an OID and a name?
Question: Why do we need both OIDs and names?
Answer: The OID is the machine address; the name is the human-readable label.
Explanation: Browsers display both so operators can work efficiently.

### 10. What is an instance OID?
Question: What is an instance OID?
Answer: It is the address of a specific instance of an object, often the scalar suffix .0.
Explanation: sysName.0 identifies the actual value instance.

## MIB-II

### 11. What is MIB-II?
Question: What is MIB-II?
Answer: It is the standard set of core managed objects defined for common devices.
Explanation: It covers system, interfaces, IP, ICMP, TCP, UDP, and more.

### 12. Why is MIB-II important?
Question: Why do engineers still learn MIB-II?
Answer: Because it remains the baseline for cross-vendor monitoring and troubleshooting.
Explanation: sysDescr, sysName, and ifOperStatus are widely supported.

### 13. What is the system group?
Question: What is the system group in MIB-II?
Answer: It is the set of objects that describe the device identity and operating context.
Explanation: The Day-10 Exercise 01 demo focuses on this group.

### 14. Which system objects matter most?
Question: Which MIB-II system objects are used most often?
Answer: sysDescr, sysObjectID, sysUpTime, sysContact, sysName, sysLocation, and sysServices.
Explanation: These values help identify the device and its operational role.

### 15. Why is sysUpTime useful?
Question: Why is sysUpTime important during operations?
Answer: It indicates how long the device has been running since the last reboot.
Explanation: A reset uptime can confirm a restart or crash recovery.

## Enterprise MIBs

### 16. What is an enterprise MIB?
Question: What is an enterprise MIB?
Answer: It is a vendor-specific MIB branch that extends the standard tree.
Explanation: Vendors publish additional counters, alarms, and configuration points.

### 17. What is a private enterprise number?
Question: What is a private enterprise number used for?
Answer: It identifies a vendor branch under 1.3.6.1.4.1.
Explanation: The Day-10 demo uses a fictional Avantel branch.

### 18. Why do vendors create enterprise MIBs?
Question: Why not use only standard MIBs?
Answer: Standard MIBs do not cover all hardware-specific telemetry.
Explanation: RF power, temperature, and modem state often require vendor objects.

### 19. What is the risk of relying only on vendor MIBs?
Question: What is a downside of enterprise-only monitoring?
Answer: It can reduce portability across vendors and device families.
Explanation: Standard objects are easier to reuse in mixed environments.

### 20. How should enterprise OIDs be designed?
Question: What makes a good enterprise MIB design?
Answer: It should be stable, hierarchical, readable, and consistent with operational needs.
Explanation: Good design reduces confusion for NOC and integration teams.

## SNMP and MIB Relationship

### 21. How are SNMP and MIB related?
Question: How do SNMP and the MIB work together?
Answer: SNMP is the protocol; the MIB is the data model it queries.
Explanation: SNMP requests values from the MIB tree.

### 22. What does GET do in MIB terms?
Question: What does GET retrieve?
Answer: It returns the value of one exact object instance.
Explanation: This is the most common read operation.

### 23. What does GETNEXT do?
Question: What does GETNEXT return?
Answer: It returns the next lexicographic object after a requested OID.
Explanation: GETNEXT is the foundation of SNMP walking.

### 24. What is a MIB browser?
Question: What is the purpose of a MIB browser?
Answer: It helps engineers inspect object names, OIDs, parents, children, and values.
Explanation: It is useful for discovery, validation, and troubleshooting.

### 25. What is MIB walking?
Question: What does walking a MIB branch mean?
Answer: It means traversing successive objects in a subtree, usually with GETNEXT.
Explanation: Walks reveal how much data a device publishes.

## Telecom Monitoring

### 26. Why is BER important in telecom?
Question: Why do telecom teams care about BER?
Answer: BER measures error rate and signal quality on the RF path.
Explanation: A rising BER often indicates degradation before service loss.

### 27. Why monitor carrier status?
Question: Why is carrier status monitored?
Answer: It shows whether the modem chain has valid lock.
Explanation: Carrier loss usually means immediate service impact.

### 28. Why monitor RF power?
Question: Why are Tx and Rx power values useful?
Answer: They help diagnose alignment, attenuation, and equipment faults.
Explanation: RF power anomalies often precede outages.

### 29. Why monitor alarm state?
Question: Why does alarm state matter in telecom operations?
Answer: It tells the operator whether the terminal needs attention.
Explanation: Alarm state drives triage and escalation workflows.

### 30. What is the value of telecom MIBs in NOCs?
Question: How do telecom MIBs help a NOC?
Answer: They expose the exact metrics the team uses to keep service healthy.
Explanation: This includes frequency, power, lock state, and BER.

## SatCom Monitoring

### 31. Why monitor BUC status?
Question: Why is BUC status important?
Answer: The BUC drives the uplink chain, so its health affects transmit capability.
Explanation: A BUC fault can stop service even if IP connectivity is fine.

### 32. Why monitor LNB status?
Question: Why is LNB status important?
Answer: The LNB affects receive path quality and downlink visibility.
Explanation: Receive issues can look like a carrier or antenna problem.

### 33. What does Eb/No indicate?
Question: What does Eb/No measure?
Answer: It measures signal quality relative to noise in the satellite link.
Explanation: It is a key indicator of terminal and path health.

### 34. Why monitor terminal state?
Question: Why is the terminal state monitored?
Answer: It summarizes whether the remote terminal is online and serviceable.
Explanation: Operators need a quick operational verdict.

### 35. Why is SatCom monitoring different from generic IP monitoring?
Question: What makes SatCom monitoring unique?
Answer: It blends RF, environmental, and terminal-state metrics.
Explanation: Traditional network counters alone are not enough.

## NOC Monitoring

### 36. What does a NOC dashboard do?
Question: What is the role of a NOC dashboard?
Answer: It aggregates status from many managed systems into one operator view.
Explanation: It reduces time to detect and triage incidents.

### 37. Why display CPU and memory in a dashboard?
Question: Why are CPU and memory shown in NOC views?
Answer: They expose resource pressure and capacity issues.
Explanation: These metrics help predict performance degradation.

### 38. Why display temperature in a dashboard?
Question: Why is temperature a useful NOC metric?
Answer: It often indicates hardware stress or cooling problems.
Explanation: Thermal issues can lead to failures if ignored.

### 39. What is the difference between polling and aggregation?
Question: How do polling and aggregation differ?
Answer: Polling collects data; aggregation turns it into an operational summary.
Explanation: Dashboards depend on both.

### 40. Why centralize monitoring?
Question: Why is centralized monitoring preferred?
Answer: It gives operators one place to assess the fleet and respond faster.
Explanation: Centralization improves consistency and incident response.

## Troubleshooting and Advanced Concepts

### 41. What should you check if an OID is not found?
Question: What do you do when a MIB browser cannot find an OID?
Answer: Verify the branch, the instance suffix, and whether the agent supports the object.
Explanation: A typo or unsupported vendor branch is often the cause.

### 42. Why is SNMPv3 preferred in production?
Question: Why is SNMPv3 used in real networks?
Answer: It adds authentication, integrity, and privacy.
Explanation: Production systems need stronger security than community strings.
