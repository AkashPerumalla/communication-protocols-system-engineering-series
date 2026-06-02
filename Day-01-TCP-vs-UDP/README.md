# Day 01 - TCP vs UDP

Day 01 is a hands-on networking lab that compares TCP and UDP using practical Java socket programs, packet analysis, and latency benchmarks.

## Learning Objectives

- Understand the TCP three-way handshake and reliable delivery model.
- Understand UDP datagram delivery and connectionless communication.
- Build and run client-server socket applications in Java 17.
- Measure and compare protocol latency using round-trip timing.
- Interpret protocol behavior using Wireshark.

## Architecture Diagrams

The detailed architecture notes live in:

- [TCP Architecture](diagrams/tcp-architecture.md)
- [UDP Architecture](diagrams/udp-architecture.md)
- [TCP vs UDP Mermaid Comparison](diagrams/tcp-vs-udp-mermaid.md)

## TCP vs UDP Comparison

| Feature | TCP | UDP |
| --- | --- | --- |
| Connection model | Connection-oriented | Connectionless |
| Reliability | Reliable delivery with acknowledgements | Best-effort delivery |
| Ordering | Preserves order | No ordering guarantee |
| Overhead | Higher | Lower |
| Latency | Typically higher | Typically lower |
| Typical use cases | Web, email, file transfer | DNS, VoIP, gaming, live streaming |

## Real-World Use Cases

TCP fits systems where correctness matters more than speed, such as authentication, APIs, and file transfer. UDP fits systems where low latency matters most, such as real-time voice, game state updates, and telemetry.

## Exercises

### Exercise 01: TCP Reliable Messaging

Build a multi-client TCP messaging system that logs usernames, connection times, received messages, and acknowledgements.

### Exercise 02: UDP Packet Analyzer

Build a UDP monitoring tool that displays packet metadata, packet counts, and timeout behavior.

### Exercise 03: Latency Benchmark

Measure round-trip latency for TCP and UDP and compare average, minimum, and maximum values.

## Running Instructions

Each exercise is standalone. Open a terminal in the exercise folder and compile the source files with `javac`, then run the server before the client when applicable.

### TCP Reliable Messaging

```bash
cd Exercise-01-TCP-Reliable-Messaging
javac TCPServer.java TCPClient.java
java TCPServer
```

In a second terminal:

```bash
java TCPClient
```

### UDP Packet Analyzer

```bash
cd Exercise-02-UDP-Packet-Analyzer
javac UDPServer.java UDPClient.java
java UDPServer
```

In a second terminal:

```bash
java UDPClient
```

### Latency Benchmark

Run the TCP server and UDP server first, then launch the benchmark clients from the Exercise 03 folder.

```bash
cd Exercise-03-Latency-Benchmark
javac TCPBenchmarkClient.java UDPBenchmarkClient.java
java TCPBenchmarkClient
java UDPBenchmarkClient
```

## Expected Output

The TCP exercise should show usernames, timestamps, and acknowledgements. The UDP exercise should show source IP, source port, payload, packet size, and running totals. The benchmark exercise should print average, minimum, and maximum latency in milliseconds.

## Wireshark Analysis

The protocol walkthrough and capture steps are documented in the diagrams folder and the interview notes. TCP should reveal SYN, SYN-ACK, and ACK frames during connection setup. UDP should show independent datagrams without a handshake.

## Interview Preparation

Review the full question bank in [interview-questions.md](interview-questions.md).

## Key Takeaways

- TCP trades extra overhead for reliability and ordering.
- UDP trades reliability for simplicity and lower latency.
- Wireshark makes transport behavior visible and easier to explain.
- Practical labs build stronger understanding than theory alone.# Day 01 - TCP vs UDP

## Helper Scripts

Use the one-command launchers in [scripts/README.md](scripts/README.md) if you want a faster workflow on macOS, Linux, or Windows.

Each exercise is standalone. Open a terminal in the exercise folder and compile the source files with `javac`, then run the server before the client when applicable.

### TCP Reliable Messaging

```bash
cd Exercise-01-TCP-Reliable-Messaging
javac TCPServer.java TCPClient.java
java TCPServer
```

In a second terminal:

```bash
java TCPClient
```

### UDP Packet Analyzer

```bash
cd Exercise-02-UDP-Packet-Analyzer
javac UDPServer.java UDPClient.java
java UDPServer
```

In a second terminal:

```bash
java UDPClient
```

### Latency Benchmark

Run the TCP server and UDP server first, then launch the benchmark clients from the Exercise 03 folder.

```bash
cd Exercise-03-Latency-Benchmark
javac TCPBenchmarkClient.java UDPBenchmarkClient.java
java TCPBenchmarkClient
java UDPBenchmarkClient
```

## Expected Output

The TCP exercise should show usernames, timestamps, and acknowledgements. The UDP exercise should show source IP, source port, payload, packet size, and running totals. The benchmark exercise should print average, minimum, and maximum latency in milliseconds.

## Wireshark Analysis

The protocol walkthrough and capture steps are documented in [wireshark-analysis.md](wireshark-analysis.md). TCP should reveal SYN, SYN-ACK, and ACK frames during connection setup. UDP should show independent datagrams without a handshake.

## Interview Preparation

Review the full question bank in [interview-questions.md](interview-questions.md).

## Key Takeaways

- TCP trades extra overhead for reliability and ordering.
- UDP trades reliability for simplicity and lower latency.
- Wireshark makes transport behavior visible and easier to explain.
- Practical labs build stronger understanding than theory alone.
