# Exercise 01 — RS232 Terminal Basics

Objective

Understand RS232-like terminal communication parameters and basic command-response flows.

What this exercise provides

- `RS232DeviceSimulator.java` — a server that responds to `STATUS`, `HELP`, `VERSION`, and `REBOOT`.
- `RS232TerminalClient.java` — a console client that connects, sends commands, and logs timestamp, port, baud rate, and responses.

Default settings

- TCP port: `7001` (used to simulate a serial link)
- Baud: `9600 8N1` (display only — simulation uses TCP)

Run

```bash
cd Day-04-RS232-Communication/Exercise-01-RS232-Terminal-Basics
javac RS232DeviceSimulator.java RS232TerminalClient.java
# Terminal A: start device simulator
java RS232DeviceSimulator 7001
# Terminal B: start client
java RS232TerminalClient localhost 7001 "9600 8N1"
```

Example session

PC: `STATUS`

Device: `DEVICE READY`

Educational notes

- `9600 8N1` means 9600 baud, 8 data bits, No parity, 1 stop bit.
- This exercise focuses on command/response patterns, timestamps, and logging rather than electrical signaling.

Adapt to real serial ports

To use actual serial ports replace the socket code with a serial library (`jSerialComm`) and open `/dev/ttyS*` or `COM*` devices. The command protocol and logging remain identical.
