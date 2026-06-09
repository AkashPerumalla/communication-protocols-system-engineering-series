# Exercise 02 - SNMP WALK

This exercise demonstrates GETNEXT traversal across a realistic MIB subtree.

## What You Learn
- MIB hierarchy and lexicographic order.
- GETNEXT internals.
- How a manager discovers objects in sequence.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out SNMPWalkDemo
```

## Expected Output
- A sequence of discovered OIDs and values.
- A GETNEXT sample line showing the next object after the start OID.
