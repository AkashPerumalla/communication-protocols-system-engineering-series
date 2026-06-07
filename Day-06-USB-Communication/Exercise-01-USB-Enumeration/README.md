# Exercise 01 — USB Enumeration

Objective: Understand how a host discovers USB devices and reads VID/PID, assigns address, and loads a driver.

Run:

1. Compile and start the simulator:

```bash
javac --release 17 -d out $(find Day-06-USB-Communication/Exercise-01-USB-Enumeration -name '*.java')
java -cp out Exercise01.USBEnumerator
```

2. In another terminal run the client:

```bash
java -cp out Exercise01.USBEnumeratorClient
```

Expected: The client prints enumeration lifecycle steps and device VID/PID/manufacturer/product.
