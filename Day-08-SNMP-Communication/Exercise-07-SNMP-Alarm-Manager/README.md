# Exercise 07 - SNMP Alarm Manager

This exercise tracks active and cleared alarms with severity levels.

## What You Learn
- Alarm state transitions.
- Severity-based operational triage.
- Active and cleared alarm views.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out SNMPAlarmManagerDemo
```

## Expected Output
- A list of active alarms.
- A cleared alarm history view.
