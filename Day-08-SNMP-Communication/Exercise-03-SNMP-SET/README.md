# Exercise 03 - SNMP SET Operations

This exercise demonstrates writable OIDs, validation, and rejection paths.

## What You Learn
- Read-only vs read-write access.
- Value validation for controlled configuration.
- Configuration management using SET.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out SNMPSetDemo
```

## Expected Output
- `sysLocation` updates successfully.
- `adminStatus` accepts valid values.
- `sysDescr` is rejected as read-only.
- Invalid values are rejected.
