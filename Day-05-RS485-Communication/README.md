Day 05 — RS485 Communication (Industrial Lab)

This module provides a professional, simulated RS485 lab for Embedded, Telecom, Industrial Automation, SCADA, and IoT engineers. It uses Java 17 and TCP loopback sockets to simulate RS485 physical characteristics (half-duplex multi-drop bus, addressing, master/slave polling and Modbus RTU frames).

Contents
- Exercise-01-RS485-Bus-Basics — Master/Slave basics (half-duplex)
- Exercise-02-MultiDevice-Network — Multi-drop addressing and polling
- Exercise-03-Modbus-RTU-Simulator — Function code 03, CRC16, register decoding
- Exercise-04-Industrial-Telemetry-Network — Telemetry collection and aggregation
- Exercise-05-RS485-Diagnostics-Tool — Discovery, ping, latency, packet stats
- Exercise-06-SCADA-Monitoring-Simulator — SCADA-style polling and dashboard
- diagrams/ — Mermaid diagrams for architectures and flows
- captures/ — Example logs and simulated traces
- interview-questions.md — 40+ interview Q&A

Run notes
- Java 17 required. Compile and run per exercise README files.
- Port range: default exercises use ports 8001–8015 on localhost.
- Designed for learning RS485 concepts; hardware integration can replace socket endpoints with serial libraries.

Key learning outcomes
- Multi-drop bus topology and addressing
- Master-slave polling and half-duplex behavior
- Modbus RTU framing and CRC
- Telemetry aggregation, diagnostics and SCADA concepts

Overview & Theory

What is RS485?
- RS485 is a balanced differential serial physical layer capable of multi-drop connections, long distances, and robust noise immunity. It uses a pair (A/B) to carry inverted signals; receivers sample the difference.

RS232 vs RS485
- RS232: single-ended, point-to-point, short distance.
- RS485: differential, multi-drop, suitable for industrial environments.

Differential Signaling
- Signals are carried as voltage differences between `A(+)` and `B(-)`. This reduces susceptibility to common-mode noise.

Bus Topology and Termination
- Use a daisy-chain (multi-drop) topology. Terminate both physical ends with ~120Ω and implement bias resistors for idle state.

Addressing
- Devices use unique addresses. Masters poll addressed slaves; broadcasts (address 0) may be used for commands that do not require replies.

Modbus RTU Overview
- Frame: `[ADDR][FUNC][DATA][CRC_LO][CRC_HI]`. Function 03 reads holding registers. CRC16 (0xA001 polynomial) is used to detect errors.

Exercises
- The module includes six exercises that progressively teach bus basics, multi-device networks, Modbus RTU, telemetry aggregation, diagnostics, SCADA simulation, and an advanced multi-master arbitration example.

Running Instructions
- Java 17 is required.
- Compile all Day-05 sources:
```
find Day-05-RS485-Communication -name "*.java" > /tmp/day05_java_files.txt
javac @/tmp/day05_java_files.txt
```
- Run the smoke test:
```
sh Day-05-RS485-Communication/scripts/run_all_tests.sh
```
- Run multi-master test:
```
sh Day-05-RS485-Communication/scripts/run_multi_master_test.sh
```

Expected Outputs
- Exercise-01: Master prints `STATUS=OK` from slave.
- Exercise-02: Master polls devices and prints `TEMP` for each address.
- Exercise-03: Master decodes Modbus registers `R0=100, R1=50` from slave.
- Exercise-05: Diagnostics show devices found and average latency in microseconds.

Troubleshooting
- If a socket fails to bind, ensure no other process uses the port and you have permission.
- Use the `captures/` logs for examples produced by the test scripts.

Industrial Use Cases
- PLC interlocks, building management, power systems telemetry, telecom equipment monitoring, SatCom hub telemetry, SCADA RTUs, and factory sensors.

Interview Preparation
- See `interview-questions.md` for 40+ curated questions with answers and explanations covering RS485 fundamentals, Modbus RTU, troubleshooting, and SCADA concepts.

Key Takeaways
- RS485 is the backbone of many industrial and telecom networks for its robustness and simplicity. Understanding physical wiring, protocol framing, addressing and diagnostics is critical for reliable deployed systems.

