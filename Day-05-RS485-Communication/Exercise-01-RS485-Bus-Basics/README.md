Exercise 01 — RS485 Bus Basics

Objective
- Demonstrate a basic RS485 master/slave exchange using simulated TCP sockets.

Files
- `MasterSimulator.java` — master (address 0) polls slave
- `SlaveSimulator.java` — slave (address 1) responds to `READ_STATUS`

Default settings
- Slave port: 8001
- Master connects to localhost:8001
- Messages are simple text frames with `ADDR` and `CMD` fields.

Run
1. Compile:
```
javac SlaveSimulator.java MasterSimulator.java
```
2. Run slave in one terminal:
```
java SlaveSimulator
```
3. Run master in another terminal:
```
java MasterSimulator
```

Expected output
- Master prints sent message and timestamp.
- Slave prints received command and responds with `STATUS=OK` including timestamp.

Notes
- This simulates half-duplex polling — master initiates and slave responds.
