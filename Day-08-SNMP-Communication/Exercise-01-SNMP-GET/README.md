# Exercise 01 - SNMP GET Operations

This exercise demonstrates the basic request/response path for SNMP GET.

## What You Learn
- Manager to agent communication.
- OID lookup for system variables.
- Reading sysName, sysDescr, sysUpTime, and sysLocation.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out SNMPManager
```

## Expected Output
- `GET sysName.0` returns `Router-01`.
- `GET sysDescr.0` returns `Cisco Router Simulator`.
- `GET sysUpTime.0` returns the uptime value.
- `GET sysLocation.0` returns `Hyderabad NOC`.
