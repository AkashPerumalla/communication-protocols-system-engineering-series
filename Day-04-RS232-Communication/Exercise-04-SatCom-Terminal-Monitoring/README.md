# Exercise 04 — SatCom Terminal Monitoring

Objective

Simulate a SatCom terminal that periodically sends telemetry packets; build a monitoring console to view health and status.

Files

- `SatComTerminalSimulator.java` — streams periodic telemetry packets to connected monitoring clients.
- `MonitoringConsole.java` — connects and prints packets with timestamps and packet counters.

Run

```bash
cd Day-04-RS232-Communication/Exercise-04-SatCom-Terminal-Monitoring
javac SatComTerminalSimulator.java MonitoringConsole.java
# Terminal A
java SatComTerminalSimulator 7004
# Terminal B
java MonitoringConsole localhost 7004
```

Notes

- The simulator periodically generates fields: `TERMINAL_ID`, `TEMP`, `POWER`, `STATUS`, `ALARM`, `PKT`, `TS`.
- The console demonstrates packet counters, parsing, and simple anomaly observation.
