# Exercise 03 - Socket Lifecycle Monitor

Overview
- Port: `9004`
- Demonstrates CREATE, CONNECT, COMMUNICATE, CLOSE stages with timestamps and connection duration summary.

Run
```bash
cd Day-02-Socket-Communication/Exercise-03-Socket-Lifecycle-Monitor
javac SocketStateServer.java SocketStateClient.java
java SocketStateServer
```

In another terminal:

```bash
java SocketStateClient
```

Educational notes
- Server logs each lifecycle stage for every client connection.
- Client prints its own lifecycle timestamps and a short summary when it closes.
