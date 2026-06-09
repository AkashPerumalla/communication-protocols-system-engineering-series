# Exercise 06 - Telecom Device Monitoring

This exercise simulates SatCom-style monitoring for modem-chain equipment.

## What You Learn
- Signal strength and BER interpretation.
- Tx/Rx power and lock-state monitoring.
- Why telecom NMS screens differ from generic IT dashboards.

## Run
```bash
javac --release 17 -d out $(find Day-08-SNMP-Communication -name '*.java')
java -cp out TelecomDeviceMonitoringDemo
```

## Expected Output
- Metrics for modem, BUC, LNB, HPA, demodulator, and router.
