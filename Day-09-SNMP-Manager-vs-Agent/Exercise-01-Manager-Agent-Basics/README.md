# Exercise 01 - Manager-Agent Basics

This exercise introduces the SNMP manager and agent roles with basic GET requests.

## Learn
- Manager role
- Agent role
- MIB lookup
- Request/response flow

## Run
```bash
javac --release 17 -d out/day09 $(find Day-09-SNMP-Manager-vs-Agent -name '*.java')
java -cp out/day09 SNMPManager
```

## Expected Output
- sysName
- sysDescr
- sysLocation
- sysUpTime
