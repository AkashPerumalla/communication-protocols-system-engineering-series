# Day 08 - SNMP Communication

This module turns SNMP into a portfolio-quality network management lab. It is built in plain Java 17, uses a simulator-first design, and mirrors how an NMS platform reasons about managers, agents, MIB trees, traps, alarms, telemetry, and dashboard views.

## Learning Objectives
- Understand the SNMP manager/agent model.
- Read and traverse MIB trees with GET and GETNEXT.
- Distinguish read-only and writable OIDs.
- Model traps, alarms, and asynchronous notifications.
- Simulate network device and telecom device polling.
- Build a console NMS dashboard with refresh cycles.

## SNMP Architecture
- The manager requests values by OID.
- The agent owns the MIB and responds with current values.
- GET returns a single variable.
- GETNEXT steps to the next lexicographic OID.
- SET validates access mode and value constraints.
- TRAPS are push-style notifications from the agent side.

## Manager vs Agent
- Manager: polls, aggregates, and presents status.
- Agent: stores variables and exposes operational state.
- The labs model both sides locally so the exercises run without hardware.

## MIB and OID
- The MIB is a hierarchical tree of managed objects.
- OIDs identify the exact node to read or write.
- The walk exercise shows how GETNEXT discovers the tree in order.

## SNMP Versions
- SNMPv1: basic GET/SET/WALK semantics.
- SNMPv2c: adds more efficient bulk retrieval and better error handling.
- SNMPv3: adds authentication, integrity, and privacy.

## Community Strings
- Community strings are shared secrets used by SNMPv1/v2c.
- They behave like simple access labels and should be treated carefully.
- In production, SNMPv3 is preferred for stronger security.

## SNMPv3 Security
- Authentication verifies who is talking to the agent.
- Privacy encrypts the payload.
- Integrity protects the data from tampering.

## GET / SET / WALK
- Exercise 01 demonstrates basic GET retrieval.
- Exercise 02 demonstrates GETNEXT-driven tree traversal.
- Exercise 03 demonstrates writable OIDs, validation, and rejection paths.

## Traps and Informs
- Traps are asynchronous notifications.
- Informs are acknowledged notifications.
- Exercise 04 models common operational alarms such as link down, power failure, and high temperature.

## Telecom Monitoring
- Exercise 06 simulates modem, BUC, LNB, HPA, demodulator, and router telemetry.
- The metrics mirror SatCom hub operational checks such as signal strength, Tx/Rx power, BER, lock state, and temperature.

## SatCom Monitoring
- SatCom environments depend on clean RF telemetry and stable lock conditions.
- The lab highlights why NMS tools watch both transport gear and RF chain health.

## NMS Dashboard
- Exercise 08 aggregates device health and alarm counts into a console dashboard.
- It refreshes automatically and behaves like a lightweight operations screen.

## Real-World Tools
- SolarWinds: device polling, topology, thresholds, and event correlation.
- PRTG: sensor-based monitoring and alerting.
- Zabbix: agent polling, templates, triggers, and dashboards.
- Nagios: host/service checks and notifications.
- Cisco Prime: Cisco-centric network management.
- Telecom NMS: service assurance, alarms, and performance monitoring.
- SatCom HUB Monitoring: RF chain telemetry, lock state, and station alarms.

## Expected Outputs
- GET returns Router-01, Cisco Router Simulator, uptime, and location values.
- WALK prints discovered OIDs and values in sequence.
- SET shows success, read-only rejection, and invalid-value rejection.
- TRAPS print source, severity, alarm, and detail blocks.
- Monitoring exercises print device health snapshots.
- Alarm management prints active and cleared alarm views.
- The dashboard shows online/offline devices, alarm counts, and averages.

## Troubleshooting Guide
- Use Java 17.
- Run `bash scripts/run_all_tests.sh` from this folder.
- If compilation fails, remove `out/` and compile again.
- If a capture log looks stale, rerun the aggregate script.

## Interview Preparation
- Read the interview questions file after reviewing the exercises.
- Focus on OID lookup, access control, traps, and SNMPv3 security.
- Be able to explain how the same core model applies to enterprise and telecom monitoring.

## Running the Labs
```bash
bash scripts/run_all_tests.sh
```

Each exercise can also be compiled and run directly with `javac --release 17` and `java -cp out <ClassName>`.
