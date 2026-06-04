# Exercise 02 - Arduino to Arduino UART

## Overview

This exercise demonstrates direct UART communication between two Arduino UNO boards. One sends numbered messages while the other receives, displays, and logs transmission statistics.

**Key Concepts**: Cross-wired TX/RX, Message Framing, Statistics Tracking, Transmission Rate

**Baud Rate**: 9600 baud (same as Exercise 01)

**Boards**: 2× Arduino UNO

## Hardware Requirements

| Component | Quantity | Purpose |
| --- | --- | --- |
| Arduino UNO | 2 | Microcontroller (one sender, one receiver) |
| USB Cable (Type-A to Type-B) | 2 | Programming and monitoring each board |
| Jumper Wires | 3 | TX→RX, RX→TX, GND→GND cross-wiring |
| Breadboard | 1 | (optional) Organizing connections |

## Pin Assignments

### Arduino A (Sender)
| Pin | Function | Direction |
| --- | --- | --- |
| Pin 1 (TX) | Transmit to Arduino B RX | Output |
| Pin 0 (RX) | (not used in sender-only mode) | - |
| GND | Common Ground | Reference |

### Arduino B (Receiver)
| Pin | Function | Direction |
| --- | --- | --- |
| Pin 0 (RX) | Receive from Arduino A TX | Input |
| Pin 1 (TX) | (not used in receiver-only mode) | - |
| GND | Common Ground | Reference |

## Wiring Diagram

```
Arduino A (Sender)          Arduino B (Receiver)
┌─────────────────┐        ┌─────────────────┐
│   Pin 1 (TX)    ├────────┤ Pin 0 (RX)      │
│   Pin 0 (RX)    │        │ Pin 1 (TX)      │
│   GND           ├────────┤ GND             │
└─────────────────┘        └─────────────────┘
        ↓                        ↓
   [USB Programmer]       [USB Serial Monitor]
```

**Critical**: Cross TX to RX, not parallel connection (TX→TX and RX→RX won't work).

## Running Instructions

### Setup

1. **Plug two Arduino UNOs into separate USB ports** (or use USB hub)
2. **Identify the USB ports**:
   - `Tools → Port` shows available ports
   - Arduino A: `/dev/cu.usbmodem14201` (example)
   - Arduino B: `/dev/cu.usbmodem14101` (example)

### Program Sender (Arduino A)

1. Open Arduino IDE → New window (Window → New)
2. Open `Exercise-02-Arduino-to-Arduino-UART/Sender/ArduinoSender.ino`
3. Select Board: `Tools → Board → Arduino UNO`
4. Select Port: **Arduino A's port** (`/dev/cu.usbmodem14201`)
5. Compile: `Sketch → Verify/Compile` (Ctrl+R)
6. Upload: `Sketch → Upload` (Ctrl+U)

### Program Receiver (Arduino B)

1. In original IDE window, open `Exercise-02-Arduino-to-Arduino-UART/Receiver/ArduinoReceiver.ino`
2. Select Board: `Tools → Board → Arduino UNO`
3. Select Port: **Arduino B's port** (`/dev/cu.usbmodem14101`)
4. Compile and Upload same as sender

### Monitor Receiver

1. Click window with Receiver sketch active
2. Open Serial Monitor: `Tools → Serial Monitor` (or Ctrl+Shift+M)
3. Select Baud Rate: **9600** (bottom right)
4. Observe incoming messages

### (Optional) Monitor Sender

1. Click window with Sender sketch active
2. Open Serial Monitor: `Tools → Serial Monitor`
3. Select Baud Rate: **9600**
4. Observe outgoing messages (for debugging)

## Expected Output

### Receiver Serial Monitor (Arduino B)

```
=== Arduino to Arduino UART: RECEIVER ===
Listening for messages at 9600 baud
Waiting for first message...

[2100 ms] Received: Message #1
    └─ Message #1 | Gap: 2100 ms
[3100 ms] Received: Message #2
    └─ Message #2 | Gap: 1000 ms
[4100 ms] Received: Message #3
    └─ Message #3 | Gap: 1000 ms
[5100 ms] Received: Message #4
    └─ Message #4 | Gap: 1000 ms
[6100 ms] Received: Message #5
    └─ Message #5 | Gap: 1000 ms
    ┌─ === TRANSMISSION STATISTICS ===
    │  Total received: 5
    │  Elapsed time: 4000 ms
    │  Message rate: 1.25 msg/sec
    └─ ============================

[7100 ms] Received: Message #6
    └─ Message #6 | Gap: 1000 ms
...
```

### Sender Serial Monitor (Arduino A)

```
=== Arduino to Arduino UART: SENDER ===
Sending numbered messages at 9600 baud
Waiting for receiver...

[2000 ms] Message #1
[3000 ms] Message #2
[4000 ms] Message #3
[5000 ms] Message #4
[6000 ms] Message #5
--- Total messages sent: 5
[7000 ms] Message #6
...
```

## Key Concepts Explained

### Cross-Wired TX/RX
- **Critical Rule**: Transmitter's TX pin must connect to Receiver's RX pin
- **Why**: TX sends data; RX receives data from external source
- **Common Mistake**: Connecting TX→TX and RX→RX results in no communication
- **Analogy**: One person speaks (TX), other listens (RX); they face each other

### Message Framing
- **Format**: `"Message #N\n"` where N is the message number
- **Delimiter**: Newline character `'\n'` (ASCII 10) marks end of message
- **Why Needed**: Without delimiter, receiver doesn't know when message ends
- **Parsing**: `Serial.readStringUntil('\n')` waits for newline, then returns complete string

### Statistics Tracking
- **Message Count**: Total messages received (independent of sender's counter)
- **Timestamp**: When each message arrived (milliseconds since Arduino startup)
- **Gap Calculation**: Time between consecutive messages (should be ~1000 ms)
- **Message Rate**: Messages per second (useful for monitoring bandwidth)

### Single UART Port Limitation
- Arduino UNO has only **one hardware UART** (pins 0/1)
- Cannot simultaneously send and receive on same UART with this exercise design
- Solutions:
  1. Use software UART library (slower, less reliable)
  2. Use boards with multiple UARTs (ESP32 Exercise 04)
  3. Implement request-response protocol with timing constraints

## Troubleshooting

| Problem | Cause | Solution |
| --- | --- | --- |
| Receiver shows no output | Wiring incorrect | Verify TX→RX, RX→TX, GND→GND connections |
| Garbled messages | Baud rate mismatch | Check both sketches use 9600 baud; verify Serial Monitor is 9600 |
| Messages appear slowly | Baud rate too slow | Try 19200 or 38400 for faster communication |
| "Port already in use" error | COM port already open | Close other Serial Monitor windows |
| Gap increases over time | Buffer overflow | Receiver too slow; add processing speed or increase buffer |
| Large first gap (>2000 ms) | Initialization delay | Normal; firmware upload and startup takes time |

## Message Rate Analysis

At 9600 baud with 10 bits/character and 1-second send delay:

```
Message: "Message #5\n" = 11 characters
TX time: 11 chars × 10 bits/char ÷ 9600 baud ≈ 11.5 ms
Send delay: 1000 ms
Total per message: ~1011 ms (TX time negligible vs delay)
Expected rate: ~0.99 messages/second (close to 1.0)
```

With 5 messages in 4 seconds: `5 / 4 = 1.25 msg/sec` (looks like startup delay)

## Educational Notes

1. **Hardware Communication**: This exercise uses actual hardware UART, not USB emulation
2. **Cross-Wiring Requirement**: Different from computer serial cables (which are straight-through)
3. **Asynchronous Protocol**: No clock signal; timing controlled by baud rate alone
4. **Buffer Management**: Arduino handles buffering transparently; 64-byte limit for overflow scenarios
5. **Scalability**: For multi-device networks, consider CAN bus or I2C (different protocols)
6. **Debugging**: Timestamps and statistics help diagnose lost messages or slow networks

## Real-World Applications

- **Sensor Networks**: Arduino collects data from one sensor, sends to main controller
- **Telemetry**: Embedded device reports status to central logger
- **Configuration**: Device receives setup commands from host computer
- **Multi-Board Robots**: Arduino boards coordinate motor control and sensing

## Next Steps

- **Exercise 03**: Upgrade from Arduino-to-Arduino to Arduino-to-ESP32 (different voltage levels, faster baud rate)
- **Exercise 04**: Use ESP32's multiple UART instances for true bidirectional communication
- **Exercise 05**: Implement structured packet protocol with validation and ACK/NACK responses

## See Also

- [Exercise 01 - Arduino UART Basics](../Exercise-01-Arduino-UART-Basics/)
- [Exercise 03 - Arduino to ESP32 UART](../Exercise-03-Arduino-to-ESP32-UART/)
- [Arduino-to-Arduino Wiring Diagram](../diagrams/arduino-to-arduino-uart.md)
- [UART Frame Structure](../diagrams/uart-frame-structure.md)
