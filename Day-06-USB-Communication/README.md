# Day 06 — USB Communication (Engineering Labs)

This folder contains a portfolio-quality set of USB engineering labs designed for embedded, Linux, Android and firmware engineers.

Work is implemented in plain Java (Java 17) using small TCP-based simulators so the labs are runnable cross-platform without hardware.

Overview:
- Exercise-01: USB Enumeration
- Exercise-02: USB Descriptor Analyzer
- Exercise-03: USB HID Monitor
- Exercise-04: USB CDC Communication
- Exercise-05: USB Mass Storage Inspector
- Exercise-06: USB Packet Analyzer
- Exercise-07: Android USB Host (host-side examples)
- Exercise-08: USB Diagnostics Tool

Running the labs (basic):

Compile all Java sources:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication -name '*.java')
```

Run an exercise simulator (example — enumeration simulator):

```bash
java -cp out Exercise01.USBEnumerator
```

Then in another terminal run the client:

```bash
java -cp out Exercise01.USBEnumeratorClient
```

See each exercise README for specifics and expected outputs.

Notes:
- The exercises use ports 8001–8008 (one port per exercise simulator).
- Exercise-07 contains host-side Android examples and does not require the Android SDK to read; it is explanatory and safe to compile.
- Exercise-06 includes instructions to generate Wireshark captures locally rather than including large .pcap files in the repo.

Contact: This lab was generated as part of the Communication Protocols System Engineering Series.
