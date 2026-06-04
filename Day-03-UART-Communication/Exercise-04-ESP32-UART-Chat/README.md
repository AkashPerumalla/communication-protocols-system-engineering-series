# Exercise 04 - ESP32 UART Chat

## Overview

This exercise demonstrates true bidirectional (full-duplex) UART communication between two ESP32 boards. Node A continuously sends PING messages while Node B listens, responds with ACK, and sends status updates. Unlike Exercise 02 (Arduino-to-Arduino), ESP32's multiple UART instances enable simultaneous transmission and reception on the same UART.

**Key Concepts**: Full-Duplex Communication, Message Protocol, Request-Response Pattern, Multi-UART Architecture

**Baud Rate**: 115200 baud

**Boards**: 2× ESP32

## Hardware Requirements

| Component | Quantity | Purpose |
| --- | --- | --- |
| ESP32 | 2 | Microcontroller (one Node A, one Node B) |
| Micro USB Cable | 2 | Programming and monitoring |
| Jumper Wires | 3 | TX2→RX2, RX2→TX2, GND→GND |
| Breadboard | 1 | (optional) Wire organization |

## Pin Assignments

### ESP32 Node A (Sender/Requester)
| Pin | Function | Purpose |
| --- | --- | --- |
| GPIO17 (TX2) | Transmit | Sends PING to Node B |
| GPIO16 (RX2) | Receive | Receives ACK and STATUS from Node B |
| GND | Ground | Common reference |

### ESP32 Node B (Responder)
| Pin | Function | Purpose |
| --- | --- | --- |
| GPIO17 (TX2) | Transmit | Sends ACK and STATUS to Node A |
| GPIO16 (RX2) | Receive | Receives PING from Node A |
| GND | Ground | Common reference |

## Wiring Diagram

```
ESP32 Node A (Requester)    ESP32 Node B (Responder)
┌──────────────────────┐    ┌──────────────────────┐
│ GPIO17 (TX2) ───────┼────┼─ GPIO16 (RX2)        │
│ GPIO16 (RX2) ←──────┼────┼─ GPIO17 (TX2)        │
│ GND ────────────────┼────┼─ GND                  │
└──────────────────────┘    └──────────────────────┘
        │                           │
   [Micro USB]                 [Micro USB]
   (Programmer &               (Programmer &
    Serial Monitor)            Serial Monitor)
```

## Message Protocol

### Message Types

| Message | Direction | Meaning | Response |
| --- | --- | --- | --- |
| PING | A → B | Request status | ACK + STATUS |
| ACK | B → A | Acknowledgement | (none) |
| STATUS | B → A | Status update | (none) |

### Message Sequence

```
Time    Node A          Node B
----    ------          ------
0 ms    [startup]       [startup]
                        Ready...
2 sec   PING ──────────→ Receive PING
                        ← ACK
                        ← STATUS: OK | Uptime=2s | PINGs=1
        Receive ACK
        Receive STATUS
4 sec   PING ──────────→ Receive PING
                        ← ACK
                        ← STATUS: OK | Uptime=4s | PINGs=2
        Receive ACK
        Receive STATUS
...
```

## Running Instructions

### Setup

1. **Plug two ESP32 boards into separate USB ports**
2. **Identify USB ports**:
   - Node A: `/dev/cu.SLAB_USBtoUART1` (example)
   - Node B: `/dev/cu.SLAB_USBtoUART2` (example)

### Program Node A (Requester)

1. Open Arduino IDE → New window (Window → New)
2. Open `Exercise-04-ESP32-UART-Chat/ESP32NodeA/NodeA.ino`
3. Select Board: `Tools → Board → ESP32 Dev Module`
4. Select Port: **Node A's USB port**
5. Compile: `Sketch → Verify/Compile` (Ctrl+R)
6. Upload: `Sketch → Upload` (Ctrl+U)

### Program Node B (Responder)

1. In original IDE window, open `Exercise-04-ESP32-UART-Chat/ESP32NodeB/NodeB.ino`
2. Select Board: `Tools → Board → ESP32 Dev Module`
3. Select Port: **Node B's USB port**
4. Compile and Upload

### Monitor Both Nodes

**Node A Monitor**:
1. Click NodeA IDE window
2. Open Serial Monitor: `Tools → Serial Monitor`
3. Select Baud: **115200**

**Node B Monitor** (optional):
1. Click NodeB IDE window
2. Open Serial Monitor: `Tools → Serial Monitor`
3. Select Baud: **115200**

## Expected Output

### Node A Serial Monitor (Requester)

```
=== ESP32 UART Chat: Node A ===
Serial2 initialized at 115200 baud
Pins: RX2=16, TX2=17
Sending PING messages...

[2500 ms] PING sent (attempt #1)
[2520 ms] RX: "ACK"
       ACK received! (1 total)
[2530 ms] RX: "STATUS: OK | Uptime=2s | PINGs=1"
       STATUS: OK | Uptime=2s | PINGs=1
[4500 ms] PING sent (attempt #2)
[4520 ms] RX: "ACK"
       ACK received! (2 total)
[4530 ms] RX: "STATUS: OK | Uptime=4s | PINGs=2"
       STATUS: OK | Uptime=4s | PINGs=2
[6500 ms] PING sent (attempt #3)
[6520 ms] RX: "ACK"
       ACK received! (3 total)
[6530 ms] RX: "STATUS: OK | Uptime=6s | PINGs=3"
       STATUS: OK | Uptime=6s | PINGs=3
[8500 ms] PING sent (attempt #4)
[8520 ms] RX: "ACK"
       ACK received! (4 total)
[8530 ms] RX: "STATUS: OK | Uptime=8s | PINGs=4"
       STATUS: OK | Uptime=8s | PINGs=4
[10500 ms] PING sent (attempt #5)
[10520 ms] RX: "ACK"
       ACK received! (5 total)
[10530 ms] RX: "STATUS: OK | Uptime=10s | PINGs=5"
       STATUS: OK | Uptime=10s | PINGs=5
    ┌─ STATS: Sent 5 PINGs, Received 5 ACKs (100% success)
    └─
```

### Node B Serial Monitor (Responder)

```
=== ESP32 UART Chat: Node B ===
Serial2 initialized at 115200 baud
Pins: RX2=16, TX2=17
Waiting for PING messages from Node A...

[2520 ms] RX: "PING"
       PING received!
[2520 ms] TX: ACK (1 total)
[2525 ms] TX: STATUS
[4520 ms] RX: "PING"
       PING received!
[4520 ms] TX: ACK (2 total)
[4525 ms] TX: STATUS
[6520 ms] RX: "PING"
       PING received!
[6520 ms] TX: ACK (3 total)
[6525 ms] TX: STATUS
[8520 ms] RX: "PING"
       PING received!
[8520 ms] TX: ACK (4 total)
[8525 ms] TX: STATUS
[10520 ms] RX: "PING"
       PING received!
[10520 ms] TX: ACK (5 total)
[10525 ms] TX: STATUS
    ┌─ STATS: Received 5 PINGs, Sent 5 ACKs
    └─
```

## Key Concepts Explained

### Full-Duplex Communication

**Definition**: Both parties can transmit and receive simultaneously on separate data paths

| Aspect | Half-Duplex (Ex. 02) | Full-Duplex (Ex. 04) |
| --- | --- | --- |
| Boards | Arduino UNO (1 UART) | ESP32 (3 UARTs) |
| Communication | Request → Response | Simultaneous TX & RX |
| Timing | Sequential (sender waits for receiver to respond) | Independent (both active at same time) |
| Efficiency | Lower (idle waiting time) | Higher (no idle time) |
| Complexity | Simpler | More complex (race conditions possible) |

**Advantage**: Node A doesn't need to wait for Node B to respond; it can send another PING immediately while receiving the previous ACK.

### Multi-UART Architecture on ESP32

ESP32 has **3 independent hardware UART instances**:

| UART | RX Pin | TX Pin | Purpose |
| --- | --- | --- | --- |
| Serial (UART0) | GPIO3 | GPIO1 | USB communication (programming/monitoring) |
| Serial1 (UART1) | GPIO9 | GPIO10 | Usually reserved for PSRAM; can repurpose |
| Serial2 (UART2) | GPIO16 | GPIO17 | General purpose UART (used in this exercise) |

**Advantage over Arduino**: Can use Serial for debugging while Serial2 handles device communication.

### Request-Response Protocol

Pattern where requester expects acknowledgement:

1. **Requester** (Node A) sends PING
2. **Responder** (Node B) receives PING
3. **Responder** sends ACK (confirmation)
4. **Responder** sends STATUS (additional data)
5. **Requester** receives both ACK and STATUS
6. Repeat at fixed interval (2 seconds)

**Benefits**:
- Requester knows request was received (ACK)
- Responder can send status without being asked
- Can detect lost messages (missing ACK)
- Foundation for real protocols (HTTP, MQTT, gRPC)

### Message Rate & Timing

At 115200 baud with 1-second send interval:

```
"PING\n" = 5 characters = 50 bits
Time to transmit PING: 50 bits ÷ 115200 bps ≈ 0.43 ms
"ACK\n" = 4 characters = 40 bits
Time to transmit ACK: 40 bits ÷ 115200 bps ≈ 0.35 ms
"STATUS: OK | Uptime=2s | PINGs=1\n" ≈ 35 chars = 350 bits ≈ 3.04 ms

Total TX time: ~4 ms
Interval between PINGs: 2000 ms
Overhead: 4 ms / 2000 ms = 0.2% (very efficient)
```

## Troubleshooting

| Problem | Cause | Solution |
| --- | --- | --- |
| Node B shows no output | Wiring incorrect | Verify TX2→RX2 and RX2→TX2 cross-connection |
| Mixed message fragments | Race condition on serial buffer | Add small delay before reading (already in code) |
| Missing ACKs | Baud rate mismatch | Verify both sketches use 115200 baud |
| Latency increasing over time | Memory leak or buffer filling | Check for infinite loops or large string allocations |
| One direction works, other doesn't | TX/RX reversed | Verify wiring matches pin diagram |
| GND not connected | No common reference | ACKs appear garbled; always connect GND |

## Advanced Variations

### Variation 1: Keep-Alive Timeout
Detect when Node B goes offline by tracking time since last ACK:

```cpp
if ((millis() - lastAckTime) > 5000) {
  Serial.println("Node B offline!");
}
```

### Variation 2: Message Counter in Protocol
Add message ID to detect lost packets:

```
PING ID=5
ACK ID=5
```

### Variation 3: Bidirectional Chat
Make both nodes capable of sending and receiving:

```
Node A: "Hello Node B"
Node B: "Hello Node A"
```

### Variation 4: Broadcast Status
Node B sends status periodically without waiting for PING:

```cpp
if ((millis() - lastStatusTime) > 5000) {
  sendStatusBroadcast();
  lastStatusTime = millis();
}
```

## Educational Notes

1. **Simultaneous TX/RX**: ISR (Interrupt Service Routine) handles received data while loop() sends; no explicit synchronization needed
2. **Protocol Design**: Successful systems define clear message format and response rules
3. **Timing Synchronization**: No clock signal; UART timing is asynchronous but coordinated by baud rate
4. **Real-World Analogy**: Request-response like human conversation (question → answer)
5. **Scalability**: With 3 UARTs, ESP32 can coordinate multiple devices on different channels

## Real-World Applications

- **Master-Slave Sensor Networks**: Central controller polls multiple sensor nodes
- **Heartbeat Monitoring**: Nodes send "alive" signals to detect offline devices
- **Device Configuration**: Host sends configuration commands, device responds with status
- **Telemetry Collection**: Device sends periodic status, host logs all nodes centrally
- **Networked Robots**: Multiple robots coordinate via UART bridges

## Next Steps

- **Exercise 05**: Add packet structure, checksums, and error detection to protocol
- **Advanced**: Implement multi-node network with arbitration or bus protocol
- **Real-World**: Migrate to wireless (NRF24L01) or cellular (GSM) for remote communication

## See Also

- [Exercise 02 - Arduino-to-Arduino UART](../Exercise-02-Arduino-to-Arduino-UART/)
- [Exercise 05 - UART Command Protocol](../Exercise-05-UART-Command-Protocol/)
- [ESP32 Bidirectional Wiring Diagram](../diagrams/esp32-bidirectional-uart.md)
- [UART Frame Structure](../diagrams/uart-frame-structure.md)
