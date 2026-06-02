# Exercise 02 - UDP Packet Analyzer

This lab shows how UDP transmits independent datagrams without establishing a persistent connection.

## What You Will Learn

- How UDP receives packets one by one.
- How to inspect source IP, source port, payload, and packet size.
- Why packet counts and timeout handling matter in monitoring tools.

## How It Works

1. Start the server on port `9001`.
2. Send packets from the client such as `Packet-1`, `Packet-2`, and `Packet-3`.
3. The server prints packet metadata and maintains a running count.
4. If no packets arrive for five seconds, the server reports a timeout and the current total.

## Run Instructions

Compile the files:

```bash
javac UDPServer.java UDPClient.java
```

Start the server:

```bash
java UDPServer
```

Start the client:

```bash
java UDPClient
```

## Expected Output

Server console:

```text
Packet Received
Source:
127.0.0.1

Port:
52344

Payload:
Packet-1

Packet Size:
8 bytes

Total Packets:
1
```

## Educational Notes

- UDP does not maintain a connection state.
- Each packet stands alone as a datagram.
- A timeout helps demonstrate that the server can keep monitoring even when traffic pauses.
