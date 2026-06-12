# Day 11 Interview Questions - OID (Object Identifier)

## OID Fundamentals
1. What is an OID?
Question: What does Object Identifier mean in SNMP?
Answer: It is a globally unique numeric path used to identify a managed object.
Explanation: Managers use OIDs to address exact data points in a MIB tree.

2. Why are OIDs hierarchical?
Question: Why do OIDs use parent-child numbering?
Answer: Hierarchy makes object grouping, traversal, and lookup deterministic.
Explanation: Each level narrows the object domain from standard to vendor-specific data.

3. What does a dotted OID represent?
Question: Why is an OID written as `1.3.6.1...`?
Answer: Each number is a node in a tree.
Explanation: The sequence is the full path from the root to the object.

4. What is the difference between an OID and a MIB?
Question: How does a MIB relate to an OID?
Answer: A MIB is the schema; an OID is the address of a specific object in that schema.
Explanation: MIB defines meaning, OID identifies location.

5. What is the root of the OID tree?
Question: Which branch starts the standard OID hierarchy?
Answer: `iso(1)`.
Explanation: It is the starting point for the standard internet OID tree.

6. What is `mib-2`?
Question: What is `1.3.6.1.2.1` in SNMP?
Answer: It is the MIB-II subtree.
Explanation: Common system and interface objects live under it.

## OID Hierarchy
7. What is the `system` group?
Question: Why is `1.3.6.1.2.1.1` important?
Answer: It contains core host identity and status objects.
Explanation: Examples include `sysDescr`, `sysName`, and `sysUpTime`.

8. What is the `interfaces` group?
Question: What kind of data is stored under `1.3.6.1.2.1.2`?
Answer: Interface inventory and traffic counters.
Explanation: It holds objects such as `ifSpeed`, `ifInOctets`, and `ifOperStatus`.

9. Why do enterprise OIDs exist?
Question: Why do vendors use `1.3.6.1.4.1`?
Answer: To allocate private branches for vendor-specific objects.
Explanation: Enterprises can model telecom, SatCom, and platform-specific telemetry.

10. What is OID depth?
Question: What does depth mean in a tree walk?
Answer: The number of edges from the root to the node.
Explanation: Deeper nodes are more specific.

11. What is a parent OID?
Question: How do you identify a node's parent?
Answer: Remove the last numeric arc from the OID.
Explanation: `1.3.6.1.2.1.1.5.0` has parent `1.3.6.1.2.1.1.5`.

12. What is a child OID?
Question: What makes one OID a child of another?
Answer: It extends the parent path with additional arcs.
Explanation: Children represent more specific objects or table instances.

## OID Resolution
13. How do managers resolve OIDs to names?
Question: How is `1.3.6.1.2.1.1.5.0` resolved?
Answer: Each prefix is matched to a named node in the hierarchy.
Explanation: The result becomes a canonical path such as `iso.org.dod.internet.mgmt.mib-2.system.sysName.0`.

14. Why is canonical naming useful?
Question: Why not use numbers only?
Answer: Names make dashboards and diagnostics easier to read.
Explanation: Operators can recognize objects faster.

15. What does partial lookup solve?
Question: Why search by keyword?
Answer: It helps operators find objects when they know the label but not the exact OID.
Explanation: This is useful in browsers and troubleshooting tools.

16. What happens when an OID is missing?
Question: How should a lookup service respond?
Answer: It should return a structured not-found result, not fail silently.
Explanation: Managers need clear operational feedback.

17. Why are some OIDs instances ending in `.0`?
Question: What does the trailing zero mean?
Answer: It usually represents a scalar instance.
Explanation: Many single-value system objects are modeled with instance `0`.

18. Why does OID ordering matter?
Question: Why is lexicographic order important?
Answer: It drives GETNEXT and WALK behavior.
Explanation: SNMP walks traverse the tree in ascending OID order.

## SNMP Operations
19. What does SNMP GET do?
Question: What is the purpose of GET?
Answer: It retrieves the value of a specific object.
Explanation: The request targets one exact OID.

20. What does SNMP GETNEXT do?
Question: How does GETNEXT differ from GET?
Answer: It returns the next OID in lexicographic order.
Explanation: This is the basis for SNMP walking.

21. What does SNMP WALK do?
Question: Why is WALK used?
Answer: It reads an entire subtree.
Explanation: It is the standard way to enumerate MIB branches.

22. Why is WALK useful in operations?
Question: What do operators gain from WALK?
Answer: They can inventory objects, counters, and state across a branch.
Explanation: This is common in MIB browsers and NMS tooling.

23. What is a structured SNMP response?
Question: What should a good simulator return?
Answer: Request OID, resolved OID, name, type, value, and status.
Explanation: Structured responses are easier to test and consume.

24. Why simulate request-response flow?
Question: Why not just print the value?
Answer: Real managers care about protocol flow as much as the data.
Explanation: The workflow teaches operational SNMP behavior.

## Enterprise OIDs
25. What is an enterprise branch?
Question: What does `1.3.6.1.4.1` represent?
Answer: The enterprises subtree for vendor-specific objects.
Explanation: It is where private telemetry commonly lives.

26. Why use enterprise OIDs for telecom?
Question: Why not force everything into MIB-II?
Answer: Telecom and SatCom systems need domain-specific metrics.
Explanation: MIB-II is not enough for RF, carrier, or modem state.

27. What is a realistic telecom OID example?
Question: Which objects are common?
Answer: RF power, BER, carrier lock, frequency, and symbol rate.
Explanation: They describe link quality and modulation state.

28. What is a realistic SatCom OID example?
Question: Which objects matter for a satellite terminal?
Answer: Eb/No, terminal state, BUC status, LNB status, and modem status.
Explanation: These drive link health and support decisions.

## Monitoring and NOC Operations
29. Why monitor `sysName`?
Question: Why is hostname important?
Answer: It identifies the managed node unambiguously.
Explanation: Dashboards and alarms often start with the hostname.

30. Why monitor `sysUpTime`?
Question: What does uptime reveal?
Answer: Device stability and restart history.
Explanation: Sudden resets often indicate faults or maintenance.

31. Why monitor `ifOperStatus`?
Question: Why is interface status critical?
Answer: It shows whether the link is operational.
Explanation: A down interface often explains reachability problems.

32. Why monitor `ifInOctets` and `ifOutOctets`?
Question: What do octet counters help measure?
Answer: Traffic volume and throughput trends.
Explanation: They are basic telemetry for capacity and congestion analysis.

33. Why monitor BER?
Question: Why does bit error rate matter?
Answer: It shows link quality and error pressure.
Explanation: High BER can precede service degradation.

34. Why monitor Eb/No?
Question: Why is Eb/No a key SatCom metric?
Answer: It indicates carrier quality relative to noise.
Explanation: It is one of the best health indicators for satellite links.

35. Why monitor carrier lock?
Question: What does carrier lock tell you?
Answer: Whether the receiver is synchronized to the carrier.
Explanation: Loss of lock usually means an immediate service issue.

36. Why do NOC dashboards aggregate many devices?
Question: What is the operational value of aggregation?
Answer: It provides a single control view for many managed assets.
Explanation: Operators can spot outages and trends faster.

37. What should a NOC dashboard show first?
Question: Which fields matter most?
Answer: Hostname, status, CPU, memory, uptime, and critical OIDs.
Explanation: These are the fastest indicators of health.

38. Why include critical OIDs in dashboards?
Question: Why not only show status?
Answer: Status alone hides the reason behind the status.
Explanation: Critical OIDs expose the actual telemetry source.

## Troubleshooting
39. What does `NO SUCH OBJECT` mean?
Question: How should operators interpret an absent OID?
Answer: The object is not present in the agent's MIB view.
Explanation: It may be unsupported, misconfigured, or out of scope.

40. What does `NO NEXT OBJECT` mean?
Question: What happens at the end of a walk?
Answer: There is no lexicographically greater OID in the subtree.
Explanation: The walk has reached the end of the branch.

41. Why validate OID syntax?
Question: What should be rejected early?
Answer: Blank, malformed, and non-numeric dotted strings.
Explanation: Input validation prevents broken tree lookups.

42. Why keep outputs deterministic?
Question: Why is determinism important in a learning module?
Answer: It makes documentation, testing, and grading reliable.
Explanation: Operators and students can compare results consistently.
