# Exercise 02 - Multi-Client Echo Server

Overview
- Port: `9003`
- Demonstrates thread-per-client concurrency using `ExecutorService` and `ClientHandler`.

Run
```bash
cd Day-02-Socket-Communication/Exercise-02-Multi-Client-Echo-Server
javac MultiClientEchoServer.java ClientHandler.java EchoClient.java
java MultiClientEchoServer
```

Open multiple terminals and run `java EchoClient` to connect multiple clients concurrently.

Educational notes
- Each client gets a unique `Client-N` id for clear logs.
- `ExecutorService` decouples connection acceptance from client handling.
- `ClientHandler` demonstrates resource cleanup, per-client timing, and thread-safe logging.
