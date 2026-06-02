# Exercise 03 - TCP vs UDP Latency Benchmark

This lab measures round-trip latency for TCP and UDP using repeated requests and `System.nanoTime()`.

## What You Will Learn

- How to record round-trip time in nanoseconds.
- How to convert timing samples to milliseconds.
- How to compare latency characteristics between TCP and UDP.

## Run Instructions

Start the TCP server from Exercise 01 and the UDP server from Exercise 02 in separate terminals. Then compile and run the benchmark clients.

```bash
javac TCPBenchmarkClient.java UDPBenchmarkClient.java
java TCPBenchmarkClient
java UDPBenchmarkClient
```

## Expected Output

```text
TCP Results
Average: 12 ms
Min: 10 ms
Max: 15 ms

UDP Results
Average: 3 ms
Min: 1 ms
Max: 5 ms
```

## Educational Notes

- TCP may show higher latency because it maintains a reliable session.
- UDP is often faster because it avoids handshake and retransmission overhead.
- Benchmark results vary by hardware, OS, and network conditions.
