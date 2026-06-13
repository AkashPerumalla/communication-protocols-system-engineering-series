# Exercise 07 - Telecom Monitoring

Poll RF power, BER, carrier lock, and frequency like a telecom NMS would when checking link quality.

Run:
```bash
javac --release 17 -d out $(find ../core -name '*.java') TelecomMonitoringDemo.java
java -cp out TelecomMonitoringDemo
```
