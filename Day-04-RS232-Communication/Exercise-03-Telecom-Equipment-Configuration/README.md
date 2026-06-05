# Exercise 03 — Telecom Equipment Configuration

Objective

Simulate a remote telecom unit and learn configuration workflows used in real telecom equipment.

Files

- `TelecomDeviceSimulator.java` — maintains a simple configuration (FREQ, POWER, STATUS) and responds to commands.
- `TelecomConsole.java` — console client for setting and querying configuration.

Run

```bash
cd Day-04-RS232-Communication/Exercise-03-Telecom-Equipment-Configuration
javac TelecomDeviceSimulator.java TelecomConsole.java
# Terminal A
java TelecomDeviceSimulator 7003
# Terminal B
java TelecomConsole localhost 7003
```

Example

```
cmd> SHOW STATUS
FREQ=1400 MHz
POWER=LOW
STATUS=INACTIVE

cmd> SET FREQ 1450
OK
cmd> SET POWER HIGH
OK
cmd> SHOW STATUS
FREQ=1450 MHz
POWER=HIGH
STATUS=ACTIVE
cmd> SAVE
SAVED
```

Educational notes

- This exercise models a common workflow: query current config, apply parameter changes, validate status, then save configuration to non-volatile storage.
- In real gear this would often be done over a console port (DB9) with a CLI and role-based access controls.
