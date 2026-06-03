# Exercise 01 - TCP Echo Server

Overview
- Port: `9002`
- Demonstrates basic TCP echo behavior and proper socket handling with try-with-resources.

Run
```bash
cd Day-02-Socket-Communication/Exercise-01-TCP-Echo-Server
javac EchoServer.java EchoClient.java
java EchoServer
```

Then in another terminal:

```bash
java EchoClient
```

Expected behavior
- The server logs client IP, port, and timestamp for each received message.
- The client sends text lines and prints the echoed response.

Educational notes
- The server uses `try-with-resources` for `ServerSocket` and per-client `Socket` cleanup.
- Logging shows when clients connect and disconnect, demonstrating the socket lifecycle.
