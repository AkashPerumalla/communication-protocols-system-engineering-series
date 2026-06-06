Exercise 02 — Multi-Device RS485 Network

Objective
- Demonstrate multi-drop addressing and polling multiple devices on the same RS485 bus (simulated with TCP ports).

Files
- `NetworkMaster.java` — polls devices 01..03 by address
- `Device01.java` — address 01; responds TEMP=26
- `Device02.java` — address 02; responds TEMP=28
- `Device03.java` — address 03; responds TEMP=30

Default ports
- Device01: 8002
- Device02: 8003
- Device03: 8004

Run
1. Compile devices and master
```
javac Device01.java Device02.java Device03.java NetworkMaster.java
```
2. Start each device in separate terminals, then run `NetworkMaster`.
