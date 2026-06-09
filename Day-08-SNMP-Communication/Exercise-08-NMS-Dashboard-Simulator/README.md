# Exercise 08 - NMS Dashboard Simulator

This exercise aggregates device and alarm state into a console NMS screen.

## What You Learn
- Summary dashboards.
- Refresh cycles.
- Operational visibility for managers and telecom NOC teams.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out NMSDashboardSimulator
```

## Expected Output
- Devices online/offline.
- Alarm counts by severity.
- CPU and memory averages.
