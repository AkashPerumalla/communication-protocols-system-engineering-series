# Exercise 06 — USB Packet Analyzer

Objective: Simulate USB packet timelines and analyze control/IN/OUT/SOF packets.

Run:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-06-USB-Packet-Analyzer -name '*.java')
java -cp out Exercise06.USBPacketSimulator
```

Then in another terminal:

```bash
java -cp out Exercise06.USBPacketAnalyzer
```

See instructions in the top-level README for generating Wireshark captures locally.
