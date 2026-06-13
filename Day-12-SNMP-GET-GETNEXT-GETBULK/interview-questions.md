# Day 12 Interview Questions - SNMP GET vs GETNEXT vs GETBULK

## SNMP Fundamentals
1. Question: What is the core difference between SNMP GET, GETNEXT, and GETBULK?
   Answer: GET returns one exact object, GETNEXT returns the next lexicographic object, and GETBULK returns multiple successive objects in one request.
   Explanation: The three operations trade precision for traversal and polling efficiency.

2. Question: Why is a deterministic MIB repository useful in a learning lab?
   Answer: It makes traversal, polling, and expected output repeatable.
   Explanation: Real devices can vary, but a fixed repository lets engineers compare behavior reliably.

3. Question: What does an OID represent in SNMP?
   Answer: An OID is a dotted numeric path that identifies a managed object in the MIB tree.
   Explanation: Every SNMP lookup resolves to a specific object or subtree.

4. Question: Why do NMS platforms use GETNEXT and GETBULK instead of only GET?
   Answer: They need discovery and efficient batch polling, not just single-object reads.
   Explanation: Large fleets require traversal and reduced request volume.

## GET Operation
5. Question: What does GET return when the OID exists?
   Answer: It returns the exact object value for that OID.
   Explanation: GET does not advance to a sibling or child object.

6. Question: What should GET return when the OID does not exist?
   Answer: A no-such-object style error or equivalent failure response.
   Explanation: A GET request is not a traversal operation.

7. Question: Why is GET ideal for a single status check?
   Answer: It is precise and easy to reason about when only one object matters.
   Explanation: Examples include sysName or a single interface counter.

8. Question: In this lab, what marker identifies a GET response?
   Answer: The output includes `GET Response`.
   Explanation: The test harness uses that marker to validate the exercise.

## GETNEXT Operation
9. Question: What problem does GETNEXT solve?
   Answer: It finds the next object in lexicographic OID order.
   Explanation: That makes it useful for browsing and discovery.

10. Question: Why is GETNEXT used for MIB exploration?
    Answer: It reveals neighboring objects without knowing the exact OID beforehand.
    Explanation: Engineers can walk through a tree branch by branch.

11. Question: What happens when GETNEXT reaches the end of the MIB view?
    Answer: The agent signals that no next object exists.
    Explanation: That terminates a walk or discovery session.

12. Question: What marker does the Day 12 GETNEXT exercise emit?
    Answer: `GETNEXT Result`.
    Explanation: The lab uses that marker to prove traversal output was produced.

## GETBULK Operation
13. Question: Why was GETBULK added to SNMP?
    Answer: To reduce the number of requests required to retrieve many values.
    Explanation: It is the batch-oriented alternative to repeated GETNEXT calls.

14. Question: What do nonRepeaters and maxRepetitions control?
    Answer: nonRepeaters are fetched once, and maxRepetitions controls how many successor values are returned.
    Explanation: Together they shape how much repeated data comes back in a single response.

15. Question: Why is GETBULK efficient on large tables?
    Answer: It amortizes request overhead across many results.
    Explanation: Fewer network round-trips mean less latency and lower load.

16. Question: What marker identifies bulk retrieval in the lab output?
    Answer: `GETBULK Result`.
    Explanation: The harness validates that GETBULK retrieval actually ran.

## OID Traversal
17. Question: What is lexicographic OID traversal?
    Answer: It is the ordered movement from one OID to the next in numeric sequence.
    Explanation: SNMP uses that order for GETNEXT and walk-style discovery.

18. Question: Why is subtree traversal important for monitoring systems?
    Answer: It lets the manager pull all objects under a branch such as system or interfaces.
    Explanation: That is how agents expose groups of related telemetry.

19. Question: How does an OID discovery workflow start?
    Answer: It starts from an unknown or partially known OID and repeatedly applies GETNEXT.
    Explanation: Each step reveals the next object and gradually exposes the branch.

20. Question: What is the practical value of walking the MIB tree?
    Answer: It helps engineers map unknown devices, vendor extensions, and table structure.
    Explanation: Discovery is often the first step before building a polling policy.

## Performance Optimization
21. Question: Why does GETBULK usually outperform repeated GET requests?
    Answer: It reduces request count and protocol overhead.
    Explanation: One bulk request can return many values.

22. Question: Which metric best shows request efficiency?
    Answer: Request count reduction and efficiency ratio.
    Explanation: Lower request count for the same data is the practical win.

23. Question: Why should polling statistics track bytes transferred?
    Answer: Bytes show the wire cost of a monitoring strategy.
    Explanation: Efficiency is not only about request count; payload size matters too.

24. Question: What does a deterministic benchmark help compare?
    Answer: It helps compare GET, GETNEXT, and GETBULK on the same dataset.
    Explanation: Stable inputs make efficiency differences visible and repeatable.

## Telecom Monitoring
25. Question: Which telemetry values are common in telecom polling?
    Answer: RF power, BER, carrier lock, and frequency.
    Explanation: These metrics show link health and signal quality.

26. Question: Why is BER critical in telecom operations?
    Answer: It reveals how many bits are being corrupted on the link.
    Explanation: A rising BER can indicate alignment, attenuation, or interference issues.

27. Question: Why is carrier lock status important?
    Answer: It tells operators whether the modem has locked onto the carrier.
    Explanation: Loss of lock can break service even if other metrics look acceptable.

28. Question: Why does GETBULK help telecom polling?
    Answer: It can retrieve a set of telemetry objects in one batch.
    Explanation: Telecom polling often needs multiple adjacent values at the same time.

## NOC Monitoring
29. Question: What does a NOC dashboard aggregate?
    Answer: Device status, CPU, memory, uptime, and critical OIDs.
    Explanation: Operators need a single view of fleet health.

30. Question: Why are device profiles useful in a dashboard?
    Answer: They summarize the operational state of routers, switches, modems, and hubs.
    Explanation: A dashboard should show health at a glance, not just raw OIDs.

31. Question: What is the role of critical OIDs in NOC monitoring?
    Answer: They highlight the objects that matter most for incident detection.
    Explanation: Prioritizing key metrics reduces time to diagnosis.

32. Question: Why should a dashboard preserve deterministic ordering?
    Answer: It makes operational views easier to scan and test.
    Explanation: Stable output is also important for automated validation.

## Troubleshooting
33. Question: If GETNEXT repeats the same value, what should you inspect?
    Answer: Check whether the current OID is being advanced correctly.
    Explanation: A traversal bug often means the request pointer is not moving forward.

34. Question: What might cause a GET request to fail in the lab?
    Answer: An invalid or nonexistent OID.
    Explanation: Exact-match retrieval has no fallback to the next object.

35. Question: Why might a GETBULK response stop early?
    Answer: The subtree may end before maxRepetitions is exhausted.
    Explanation: The agent cannot return objects beyond the available view.

36. Question: What is the fastest way to validate a broken exercise output?
    Answer: Check the capture log for the required marker string.
    Explanation: The harness is designed around deterministic validation markers.

## Real-World Scenarios
37. Question: When would a telecom engineer choose GETBULK over GETNEXT?
    Answer: When polling many adjacent metrics from a modem or hub.
    Explanation: Bulk retrieval reduces polling overhead during steady-state monitoring.

38. Question: When would a network engineer prefer GET?
    Answer: When reading one specific value such as sysName or a single counter.
    Explanation: GET is the simplest and most direct operation.

39. Question: Why is GETNEXT still useful in modern NMS tools?
    Answer: It is valuable for discovery, MIB browsing, and table exploration.
    Explanation: Even if bulk polling is common, traversal still matters.

40. Question: What should an enterprise monitoring platform do with all three operations?
    Answer: Use GET for exact reads, GETNEXT for discovery, and GETBULK for efficient polling.
    Explanation: That combination mirrors how production SNMP systems actually operate.
