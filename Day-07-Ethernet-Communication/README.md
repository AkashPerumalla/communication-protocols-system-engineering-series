
# Day 07 - Ethernet Communication

Overview
--------
This module teaches Ethernet from an engineering perspective with hands-on Java simulators and practical labs covering frames, MAC addressing, ARP, switching, VLANs, packet analysis, industrial Ethernet, and a SatCom ground network.

Structure
---------
- Exercises: `Exercise-01` through `Exercise-08` provide focused simulators and labs.
- Diagrams: `diagrams/` contains Mermaid diagrams for core flows.
- Packet captures: `packet-captures/` contains sample captures and annotated analysis.
- Scripts: `scripts/` contains per-exercise run scripts and `run_all_day07.sh`.

Requirements
------------
- Java 17 (compile with `javac --release 17`)
- POSIX shell for helper scripts

Running the labs
----------------
Compile all Java sources and run a single exercise (example):

```bash
./scripts/run-ex01.sh
```

To compile all exercises and run demos sequentially:

```bash
./scripts/run_all_day07.sh
```

Contents (high-level)
---------------------
1. What is Ethernet? — history and physical/logical overview.
2. Ethernet Frame Structure — preamble, SFD, destination, source, EtherType, payload, CRC.
3. MAC Addresses — unicast, multicast, broadcast, OUI structure.
4. ARP — neighbor resolution and caching.
5. Switching — MAC learning, forwarding, flooding, unknown unicast.
6. VLANs — segmentation, tagging, broadcast domains.
7. Packet Analysis & Wireshark — common filters and field meanings.
8. Industrial Ethernet — PLC/HMI telemetry, QoS, determinism.
9. SatCom Ground Networks — hub controllers, remote terminals, telemetry.

Support & Troubleshooting
-------------------------
- Ensure `JAVA_HOME`/`java` points to Java 17.
- If compilation fails, run:

```bash
javac --release 17 -d out $(find Day-07-Ethernet-Communication -name '*.java')
```

Contact
-------
For issues, open an issue in the repository or contact the author.

