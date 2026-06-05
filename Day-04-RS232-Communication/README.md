# Day 04 — RS232 Communication

This day teaches RS232 from a practical engineering perspective aimed at embedded, telecom, SatCom, and systems engineers.

What you'll find here:

- Practical, portfolio-quality labs simulating RS232-based equipment and consoles.
- Exercises covering terminal basics, modem AT commands, telecom equipment configuration, SatCom monitoring, and diagnostics tools.
- Educational documentation covering RS232 electricals, connectors, flow control, and wiring.
- Mermaid diagrams and serial-capture examples.

Running the labs

Each exercise includes a small Java server (simulator) and a Java client (console). These use plain TCP sockets to simulate RS232 links for portability and reproducibility. The code targets Java 17 and uses only the standard library.

Generic build/run example (from an exercise directory):

```bash
javac *.java
# Start simulator in one terminal
java RS232DeviceSimulator
# Start client in another terminal
java RS232TerminalClient
```

Notes on hardware

These labs simulate RS232 over TCP for convenience. Later you can adapt the client to a real serial port using libraries like `jSerialComm` or `RXTX` and map the same commands to physical hardware.

See each exercise README for details, ports, example sessions, and troubleshooting.
