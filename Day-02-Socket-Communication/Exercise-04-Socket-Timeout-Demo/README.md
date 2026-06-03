# Exercise 04 - Socket Timeout Demo

Overview
- Port: `9005`
- Demonstrates `socket.setSoTimeout(5000)` behavior and `SocketTimeoutException` handling.

Run
```bash
cd Day-02-Socket-Communication/Exercise-04-Socket-Timeout-Demo
javac TimeoutServer.java TimeoutClient.java
java TimeoutServer
```

In another terminal:

```bash
java TimeoutClient
```

If the client does not send data within 5 seconds, the server will log `Socket Timeout Occurred`.

Educational notes
- Blocking I/O call `readLine()` will block until data arrives or a timeout triggers.
- Servers should handle timeouts to avoid hanging threads and to provide clearer diagnostics.
