# Exercise 04 — USB CDC Communication

Objective: Simulate a USB CDC virtual serial device and interact via a console.

Run simulator:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-04-USB-CDC-Communication -name '*.java')
java -cp out Exercise04.CDCDeviceSimulator
```

Run console client:

```bash
java -cp out Exercise04.CDCConsole
```

Commands: `STATUS`, `VERSION`, `HELP`, `RESET`.
