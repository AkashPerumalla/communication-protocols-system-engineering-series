# Exercise 04 - SNMP TRAPS

This exercise demonstrates asynchronous trap generation and reception.

## What You Learn
- Trap architecture and alarm propagation.
- Severity mapping.
- Typical operational events.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out SNMPTrapDemo
```

## Expected Output
- Trap blocks for link down, link up, power failure, high temperature, and low signal.
