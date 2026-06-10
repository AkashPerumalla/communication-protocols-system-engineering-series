# Day 09 - SNMP Manager vs Agent

This day turns SNMP from a single GET example into a compact Network Management System lab. The exercises stay simulator-first, use plain Java 17, compile with direct `javac`, and mirror how real NMS platforms reason about managers, agents, MIB data, traps, telemetry, and dashboards.

## Learning Objectives
- Understand the SNMP manager/agent relationship.
- Explore MIB objects, OIDs, and request/response flow.
- Practice multi-OID polling and centralized device visibility.
- Model telecom monitoring, traps, failure detection, and dashboard aggregation.
- Learn how SNMP maps to enterprise and SatCom operations.

## Manager vs Agent
- The manager polls devices, aggregates state, and presents operators with visibility.
- The agent owns the managed objects and returns current values from its MIB.
- In this lab both roles are simulated locally so the exercises run without hardware.

## MIB Architecture
- The MIB is represented as a sorted tree of managed objects.
- Each object has an OID, name, type, value, and access mode.
- GET returns a single object, GETNEXT advances through the tree, and traps push alarms asynchronously.

## OID Hierarchy
- Common MIB-II OIDs appear in the browser and manager exercises.
- The lab highlights how managers address exact objects such as sysName, sysDescr, sysUpTime, and ifOperStatus.
- Multi-OID polling shows how NMS platforms query multiple values per device.

## Polling Mechanism
- Polling is manager-driven and happens on a schedule.
- The manager in these exercises reads CPU, memory, temperature, bandwidth, and interface state.
- The same model scales to routers, switches, firewalls, servers, and telecom equipment.

## Trap Architecture
- Traps are agent-driven event notifications.
- The trap-enabled exercise emits events such as LINK_DOWN, POWER_FAILURE, HIGH_TEMPERATURE, and MODEM_UNLOCK.
- This mirrors how real NMS tools receive fault alarms without waiting for the next poll cycle.

## Device Monitoring
- Exercise 04 shows centralized monitoring across multiple devices.
- Exercise 07 simulates reachability checks, last-seen timestamps, and DEVICE_UNREACHABLE alarms.
- The dashboard exercise summarizes fleet health in a console NOC view.

## Telecom Monitoring
- Exercise 05 models modem-chain telemetry used in telecom and SatCom environments.
- It exposes Tx power, Rx power, signal strength, BER, lock status, and temperature.
- These are the same metrics operations teams watch on real RF chains.

## SatCom Monitoring
- SatCom hubs rely on carrier lock, power stability, and clean RF paths.
- The lab makes those values visible in a format that looks like an operations screen.
- It helps explain why telecom and SatCom monitoring often blends RF and IP telemetry.

## NOC Dashboard
- Exercise 08 prints a periodic console dashboard with device counts, alarm counts, and averages.
- The snapshot is intentionally compact so it resembles a live NOC summary.
- This is the capstone exercise for the day.

## Exercises
1. Manager-Agent Basics
2. Multi-OID Polling
3. MIB Browser
4. Multi-Device Monitoring
5. Telecom Agent Monitoring
6. Trap-Enabled Agent
7. Agent Failure Detection
8. NOC Dashboard

## Expected Outputs
- Exercise 01 prints GET requests and agent responses for sysName, sysDescr, sysLocation, and sysUpTime.
- Exercise 02 prints CPU, memory, temperature, interface status, and bandwidth.
- Exercise 03 prints OID, name, type, and value rows.
- Exercise 04 prints a device status dashboard.
- Exercise 05 prints telecom telemetry for modem, BUC, LNB, HPA, and demodulator.
- Exercise 06 prints TRAP RECEIVED blocks.
- Exercise 07 prints offline detection with DEVICE_UNREACHABLE.
- Exercise 08 prints a NOC summary with online/offline and alarm totals.

## Real-World Uses
- SolarWinds: polling, thresholding, alarms, and topology visibility.
- PRTG: sensor-based monitoring across network and infrastructure devices.
- Zabbix: templates, triggers, agent polling, and dashboards.
- Nagios: host/service checks and operational alerts.
- Cisco Prime: Cisco-centric device and policy management.
- Telecom NMS: service assurance, RF telemetry, and alarm correlation.
- SatCom hubs: carrier lock, BER, power, and temperature monitoring.
- Data centers: router, switch, firewall, and server visibility.
- ISP networks: large-scale availability and performance monitoring.

## Troubleshooting Guide
- Use Java 17.
- Run `bash scripts/run_all_tests.sh` from the Day-09 folder.
- If compilation fails, remove `out/day09` and re-run the script.
- If a capture log looks stale, re-run the orchestrator to regenerate it.

## Interview Preparation
- Focus on manager vs agent roles, MIB lookup, GET/GETNEXT semantics, traps, and SNMPv3 security.
- Be ready to explain why telecom monitoring cares about BER, lock state, and RF power.
- Be able to compare polling and traps as complementary monitoring patterns.

## Running the Lab
```bash
bash scripts/run_all_tests.sh
```

Each exercise also runs directly with `java -cp out/day09 <ClassName>` after compiling.
