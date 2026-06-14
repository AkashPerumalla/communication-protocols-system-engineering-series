# Day 13 - SNMP Traps and Notifications

Day 13 turns asynchronous SNMP notifications into a realistic fault-management lab. The module simulates a production-style NMS pipeline: agents generate traps, a receiver accepts them, the processor validates and stores them, alarms are created from events, telecom and SatCom scenarios are modeled explicitly, and correlation identifies a likely root cause before the NOC dashboard summarizes the fleet.

## What You Will Learn
- How SNMP traps differ from polling-based monitoring.
- How a trap receiver normalizes and validates asynchronous notifications.
- How traps become alarms and move through an operational workflow.
- How telecom and SatCom monitoring use domain-specific thresholds and state.
- How event correlation reduces alert storms and highlights root cause.
- How an NOC dashboard aggregates devices, traps, and alarms for operators.

## Architecture
- `core/` contains immutable trap and alarm models plus reusable services.
- `TrapAgent` creates deterministic trap events from simulated device conditions.
- `TrapReceiver` is the ingestion boundary for asynchronous notifications.
- `TrapProcessor` performs receive, validate, decode, store, and forward steps.
- `AlarmManager` turns events into alarm lifecycle objects.
- `EventCorrelationEngine` identifies a downstream symptom versus a likely source event.
- `ConsoleFormatter` keeps the operator output consistent and readable.

## Exercises
| Exercise | Topic | Marker |
| --- | --- | --- |
| 01 | Trap Basics | `TRAP GENERATED` |
| 02 | Trap Receiver | `TRAP RECEIVED` |
| 03 | Trap Processing | `TRAP PROCESSED` |
| 04 | Alarm Management | `ALARM CREATED` |
| 05 | Telecom Traps | `BER ALARM` |
| 06 | SatCom Traps | `SATCOM ALARM` |
| 07 | Event Correlation | `ROOT CAUSE` |
| 08 | NOC Trap Dashboard | `NOC DASHBOARD` |

## Quick Start
```bash
cd Day-13-SNMP-Traps-And-Notifications
bash scripts/run_all_tests.sh
```

## Notes
- Java 17 only.
- No external dependencies.
- No Maven or Gradle.
- All outputs are deterministic so the capture logs can be validated automatically.
- The traps are simulated, but the workflow mirrors how a real NMS or telecom fault-management platform behaves.
