# Exercise 01 - TCP Reliable Messaging

This lab demonstrates how TCP supports reliable, ordered, and acknowledged communication in a practical client-server workflow.

## What You Will Learn

- How TCP keeps a persistent connection open between client and server.
- Why acknowledgements matter for reliable delivery.
- How multithreading helps one server handle many clients at the same time.

## How It Works

1. Start the server on port `9000`.
2. Launch one or more clients.
3. Each client enters a username.
4. The server logs the username, connection time, messages, and disconnection time.
5. Every message receives an acknowledgement.

## Run Instructions

Compile the files:

```bash
javac TCPServer.java TCPClient.java
```

Start the server:

```bash
java TCPServer
```

Start one or more clients from separate terminals:

```bash
java TCPClient
```

## Expected Output

Server console:

```text
[09:15:21] Akash connected
[09:15:30] Akash:
Hello Team
ACK sent
```

Client console:

```text
Enter username:
Akash
SERVER ACK: Username accepted
Akash: Hello Team
SERVER ACK: Message Delivered Successfully
```

## Educational Notes

- TCP is connection-oriented, so the server keeps state for each client.
- The thread pool lets the server process multiple clients at once.
- The `/exit` command closes a client session cleanly.
