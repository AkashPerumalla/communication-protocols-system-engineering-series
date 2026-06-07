# Exercise 08 — USB Diagnostics Tool

Objective: Simple diagnostics that discover device reachability, driver status heuristics, and transfer statistics.

Run:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-08-USB-Diagnostics-Tool -name '*.java')
java -cp out Exercise08.USBDiagnostics
```

Expected: Reports which exercise simulators are reachable on localhost and a summary line.
