# Exercise 05 - Network Device Monitoring

This exercise simulates polling routers, switches, firewalls, and servers.

## What You Learn
- Polling loops.
- Health and performance metrics.
- Operational visibility for network devices.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out NetworkDeviceMonitoringDemo
```

## Expected Output
- Per-device CPU, memory, temperature, bandwidth, and interface status blocks.
