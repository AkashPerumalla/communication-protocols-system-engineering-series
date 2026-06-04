# Exercise 01 - Arduino UART Basics

## Overview

This exercise introduces UART (Universal Asynchronous Receiver/Transmitter) communication on Arduino UNO. You'll run two sketches to understand TX (transmission) and RX (reception) behavior.

**Key Concepts**: Baud Rate, UART Frame Structure, TX/RX pins, Serial Buffer

**Default Baud Rate**: 9600 baud

**Board**: Arduino UNO

## Hardware Requirements

| Component | Quantity | Purpose |
| --- | --- | --- |
| Arduino UNO | 1 | Microcontroller |
| USB Cable (Type-A to Type-B) | 1 | Power and Serial communication |

## Pins Used

| Arduino Pin | Function | Details |
| --- | --- | --- |
| Pin 0 (RXD) | RX (Receiver) | Receives serial data from USB |
| Pin 1 (TXD) | TX (Transmitter) | Transmits serial data to USB |
| 5V | Power | USB provides 5V |
| GND | Ground | Common reference |

## Sketch Descriptions

### ArduinoUARTTx.ino
- **Purpose**: Demonstrates UART transmission
- **Behavior**: Sends "Hello UART" message every 1 second with timestamp
- **Baud Rate**: 9600
- **Output**: Serial Monitor shows continuous messages

### ArduinoUARTRx.ino
- **Purpose**: Demonstrates UART reception and echo
- **Behavior**: Waits for character input from Serial Monitor, echoes it back with timestamp and ASCII code
- **Baud Rate**: 9600
- **Input**: Type characters in Serial Monitor
- **Output**: Echoed character with timestamp and ASCII code

## Running Instructions

### TX Demo (Transmitter)

1. Open Arduino IDE
2. Open `ArduinoUARTTx.ino`
3. Connect Arduino UNO via USB
4. Select Board: `Tools → Board → Arduino UNO`
5. Select Port: `Tools → Port → /dev/cu.usbmodem...` (or COM port on Windows)
6. Compile: `Sketch → Verify/Compile` (or Ctrl+R)
7. Upload: `Sketch → Upload` (or Ctrl+U)
8. Open Serial Monitor: `Tools → Serial Monitor`
9. Select Baud Rate: **9600** (bottom right of Serial Monitor)
10. Observe messages sent by Arduino

### RX Demo (Receiver)

1. Open `ArduinoUARTRx.ino` (same Arduino board)
2. Compile and upload same steps as TX demo
3. Open Serial Monitor at **9600 baud**
4. Type characters in the input field and press "Send" button
5. Watch echoed characters appear with timestamps

## Expected Output

### TX Demo
```
=== Arduino UART TX Demo ===
Transmitting messages at 9600 baud...
Message format: [TIMESTAMP] Hello UART

[1234 ms] Hello UART
[2234 ms] Hello UART
[3234 ms] Hello UART
[4234 ms] Hello UART
```

### RX Demo
```
=== Arduino UART RX Demo ===
Ready to receive data at 9600 baud
Type characters in Serial Monitor and press Send
---

[1250 ms] Received: 'A' (ASCII 65)
[1350 ms] Received: 'B' (ASCII 66)
[1450 ms] Received: 'C' (ASCII 67)
```

## Key Concepts Explained

### Baud Rate
- **Definition**: Speed of serial communication in bits per second (bps)
- **9600 baud**: Transmits 9600 bits/second = 960 characters/second
- **Timing**: Each character takes ~1.04 milliseconds to transmit (10 bits / 9600 bps)
- **Synchronization**: TX and RX must use the same baud rate, or data appears garbled

### UART Frame Structure
Each character transmitted as a single frame:

```
[Start Bit (0)] [Bit 0] [Bit 1] ... [Bit 7] [Stop Bit (1)]
       ↓         ↓                           ↓
     1 bit    8 data bits               1 bit
Total: 10 bits per character
```

Example: Transmitting character 'A' (ASCII 65 = binary 01000001)
```
Frame: [0][1][0][0][0][0][0][1][0][1]
       Start  Bit7..Bit0 (LSB first)  Stop
```

### TX (Transmitter)
- Arduino pin 1 (labeled TXD or TX)
- Sends data as voltage levels: HIGH=1, LOW=0
- Idle state: HIGH (5V on Arduino UNO)
- Start bit: LOW (0V) signals receiver to start listening
- Each subsequent bit: 1/9600 seconds = ~104 microseconds

### RX (Receiver)
- Arduino pin 0 (labeled RXD or RX)
- Receives voltage signals: HIGH=1, LOW=0
- Samples each bit at center of bit period (more reliable)
- UART hardware collects 10 bits and stores byte in buffer
- Arduino sketch reads buffer with `Serial.read()`

### Serial Buffer
- **Size**: 64 bytes for both RX and TX buffers
- **Blocking**: If buffer fills, further data is lost
- **Management**: Check `Serial.available()` before reading
- **Efficiency**: Faster sketch processing prevents buffer overflow

## Troubleshooting

| Problem | Cause | Solution |
| --- | --- | --- |
| No output in Serial Monitor | Wrong baud rate | Check Serial Monitor speed matches sketch (9600) |
| Garbled characters | Baud rate mismatch | Verify baud rate in both sketch and Serial Monitor |
| Characters missing | Buffer overflow | Reduce transmission speed or increase buffer handling |
| "Port not found" error | USB not connected or driver missing | Check USB cable; install CH340 driver if needed (clone boards) |
| TX sketch doesn't upload | Board/Port not selected | Verify Tools → Board and Tools → Port are correct |

## Educational Notes

1. **Arduino Serial Library**: `Serial.begin()`, `Serial.print()`, `Serial.println()`, `Serial.read()`, `Serial.available()` handle all UART complexity
2. **Hardware Abstraction**: Arduino IDE hides low-level UART register configuration
3. **Blocking vs Non-Blocking**: `Serial.available()` is non-blocking; check before `Serial.read()` to avoid waiting
4. **Timestamps**: `millis()` provides millisecond-precision timing for logging when messages arrive
5. **ASCII Values**: Each character has numeric ASCII code (A=65, B=66, etc.); useful for validation
6. **Next Step**: Move to Exercise 2 to add a second Arduino and implement bidirectional communication

## Real-World Connections

- **Firmware Debugging**: Developers use Serial.print() for diagnostics on embedded devices
- **Sensor Data**: Temperature sensors, GPS modules, and accelerometers communicate via UART
- **Terminal Communication**: Serial monitors in Arduino IDE, PuTTY, or minicom emulate old-school terminals
- **Baud Rate Standards**: 9600, 19200, 38400, 57600, 115200 are common in embedded systems

## See Also

- [UART Frame Structure Diagram](../diagrams/uart-frame-structure.md)
- [Arduino Serial Reference](https://www.arduino.cc/reference/en/language/functions/communication/serial/)
- [Arduino UNO Datasheet](https://www.arduino.cc/en/main/arduinoBoardUno)
