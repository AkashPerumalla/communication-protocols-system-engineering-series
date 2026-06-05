# Exercise 05 — RS232 Diagnostics Tool

Objective

Build a troubleshooting utility that checks connectivity, measures response times, detects timeouts, and reports statistics.

Files

- `DiagnosticsServer.java` — simple responder supporting `PING`, `ECHO <text>`, `DELAY <ms>`.
- `DiagnosticsClient.java` — runs an automated diagnostics sequence and prints a summary.

Run

```bash
cd Day-04-RS232-Communication/Exercise-05-RS232-Diagnostics-Tool
javac DiagnosticsServer.java DiagnosticsClient.java
# Terminal A
java DiagnosticsServer 7005
# Terminal B
java DiagnosticsClient localhost 7005
```

Expected output

```
PING -> PONG (RTT=1ms)
ECHO -> HelloDiagnostics (RTT=2ms)
DELAY -> DELAYED 200 (RTT=205ms)

--- Diagnostics Summary ---
Packets Sent: 7
Packets Received: 7
Success Rate: 100%
Average RTT (ms): 30
```

Notes

- This tool demonstrates how to automate basic serial/console diagnostics frequently used by field engineers and maintenance teams.
- For real serial ports, adapt the client to use a serial API and measure timestamps around write/read operations.
