# Exercise 03 - Arduino to ESP32 UART

## Overview

This exercise demonstrates cross-platform UART communication between Arduino UNO (5V) and ESP32 (3.3V). You'll learn about different baud rates, pin configurations, data validation, and handling interoperability challenges.

**Key Concepts**: Platform Interoperability, Baud Rate, Voltage Levels, Data Parsing & Validation, Telemetry Format

**Baud Rate**: 115200 baud (ESP32 standard, 12× faster than Exercise 02)

**Boards**: Arduino UNO (Sender) + ESP32 (Receiver)

## Hardware Requirements

| Component | Quantity | Purpose |
| --- | --- | --- |
| Arduino UNO | 1 | Sender (5V microcontroller) |
| ESP32 | 1 | Receiver (3.3V microcontroller) |
| USB Cable (Type-A to Type-B) | 1 | Arduino programming |
| Micro USB Cable | 1 | ESP32 programming and monitoring |
| Jumper Wires | 3 | TX→RX2, RX→TX2, GND→GND |
| Breadboard | 1 | (optional) Wire organization |
| Voltage Divider (optional) | 1 set | Optional: Protect ESP32 RX2 from 5V |

## Pin Assignments

### Arduino UNO (Sender)
| Pin | Function | Voltage | Purpose |
| --- | --- | --- | --- |
| Pin 1 (TX) | Transmit | 0-5V | Sends data to ESP32 RX2 |
| GND | Ground | 0V | Common reference with ESP32 |

### ESP32 (Receiver)
| Pin | Function | Voltage | Purpose |
| --- | --- | --- | --- |
| Pin 16 (RX2) | Receive | 0-3.3V | Receives data from Arduino TX |
| Pin 17 (TX2) | Transmit | 0-3.3V | (unused in this exercise) |
| GND | Ground | 0V | Common reference with Arduino |

## Voltage Level Considerations

| Direction | From | To | Voltage | Safe? | Notes |
| --- | --- | --- | --- | --- | --- |
| TX→RX2 | Arduino TX (5V) | ESP32 RX2 (3.3V max) | 5V | ⚠️ Borderline | USB isolation provides some protection; resistor divider recommended for production |
| RX→TX2 | ESP32 TX2 (3.3V) | Arduino RX (5V tolerant) | 3.3V | ✓ Yes | Safe; Arduino input threshold ~2.0V |

### Optional Voltage Divider for RX2 Protection

If you want to safely reduce 5V to 3.3V:

```
Arduino TX (5V) ──[10kΩ]──┬──→ ESP32 RX2 (3.3V)
                           │
                         [20kΩ]
                           │
                          GND

Voltage at RX2 = 5V × (20k/(10k+20k)) = 5V × (2/3) ≈ 3.33V (safe!)
```

For initial testing with USB isolation, this is optional.

## Wiring Diagram

```
Arduino UNO (5V)              ESP32 (3.3V)
┌─────────────────┐          ┌──────────────────┐
│                 │          │                  │
│  Pin 1 (TX) ───┼──────────┼─ Pin 16 (RX2)    │
│  Pin 0 (RX)  │  (unused)  │  Pin 17 (TX2)    │
│  GND ────────┼──────────┼─ GND               │
│                 │          │                  │
└─────────────────┘          └──────────────────┘
        │                            │
   [USB Serial]               [USB Serial]
   (Programmer)               (Programmer)
```

## Message Format

The Arduino sender transmits sensor telemetry in Key=Value format:

```
TEMP=25
HUM=65
STATUS=Telemetry ReadingCount=5
```

**Format Rules**:
- Each message on separate line (ends with `\n`)
- Key and value separated by `=`
- Value is numeric for TEMP and HUM
- Valid keys: TEMP, HUM, STATUS

## Running Instructions

### Program Arduino Sender

1. Open Arduino IDE
2. Open `Exercise-03-Arduino-to-ESP32-UART/ArduinoSender/ArduinoSender.ino`
3. Select Board: `Tools → Board → Arduino UNO`
4. Select Port: Arduino's USB port
5. Compile: `Sketch → Verify/Compile` (Ctrl+R)
6. Upload: `Sketch → Upload` (Ctrl+U)

### Program ESP32 Receiver

1. Open Arduino IDE → `Sketch → Include Library → Manage Libraries`
   - Search for "ESP32" and install "ESP32" by Espressif Systems (if not already installed)
2. Open `Exercise-03-Arduino-to-ESP32-UART/ESP32Receiver/ESP32Receiver.ino`
3. Select Board: `Tools → Board → ESP32 Dev Module` (or your specific ESP32 board)
4. Select Port: ESP32's USB port
5. Compile: `Sketch → Verify/Compile` (Ctrl+R)
6. Upload: `Sketch → Upload` (Ctrl+U)

### Monitor ESP32 Receiver

1. Open Serial Monitor: `Tools → Serial Monitor`
2. Select Baud Rate: **115200** (must match both sketches)
3. Observe parsed telemetry messages

## Expected Output

### ESP32 Serial Monitor (Receiver)

```
=== Arduino to ESP32 UART: RECEIVER (ESP32) ===
Listening on Serial2 (pins 16/17) at 115200 baud
Expected message format: KEY=VALUE
Valid keys: TEMP, HUM, STATUS
---

[2150 ms] Received (raw): "TEMP=28"
       PARSED: Temperature = 28°C
[2675 ms] Received (raw): "HUM=62"
       PARSED: Humidity = 62%
[4200 ms] Received (raw): "TEMP=25"
       PARSED: Temperature = 25°C
[4725 ms] Received (raw): "HUM=58"
       PARSED: Humidity = 58%
[6250 ms] Received (raw): "STATUS=Telemetry ReadingCount=10"
       PARSED: Status = Telemetry ReadingCount=10

       === Statistics: 10 messages received ===

[8300 ms] Received (raw): "TEMP=29"
       PARSED: Temperature = 29°C
```

### Arduino Serial Monitor (Sender)

```
=== Arduino to ESP32 UART: SENDER (Sensor Telemetry) ===
Transmitting sensor data at 115200 baud
Message format: KEY=VALUE
Waiting for ESP32 receiver...

[2100 ms] TEMP=28
[2600 ms] HUM=62
[4100 ms] TEMP=25
[4600 ms] HUM=58
[6100 ms] STATUS=Telemetry ReadingCount=10
[8100 ms] TEMP=29
[8600 ms] HUM=65
```

## Parsing & Validation

### Validation Steps

1. **Format Check**: Message must contain `=` separator
   - Invalid: `TEMP 25` (no equals)
   - Valid: `TEMP=25`

2. **Key Validation**: Key must be recognized
   - Valid: TEMP, HUM, STATUS
   - Invalid: PRESSURE, ALTITUDE (not handled)

3. **Value Validation**:
   - TEMP/HUM values must be numeric (all digits)
   - TEMP range: 0-100°C (warning if outside)
   - HUM range: 0-100% (warning if outside)
   - STATUS accepts any text

4. **Error Handling**:
   - Invalid format → Log error, skip message
   - Unknown key → Log warning, skip message
   - Out-of-range value → Log warning, still process
   - Non-numeric value for numeric key → Log error, skip

### Error Messages

```
[2150 ms] Received (raw): "TEMP=xyz"
       ERROR: TEMP value is not numeric
       Failed message: "TEMP=xyz"

[2300 ms] Received (raw): "PRESSURE=1013"
       ERROR: Unknown key: PRESSURE
       Failed message: "PRESSURE=1013"

[2450 ms] Received (raw): "TEMP150"
       ERROR: Invalid format (no '=' separator)

[2600 ms] Received (raw): "HUM=150"
       WARNING: HUM out of range: 150
       PARSED: Humidity = 150%
```

## Key Concepts Explained

### Baud Rate Selection

| Rate | Bits/sec | Character Time | Platform | Use Case |
| --- | --- | --- | --- | --- |
| 9600 | 9600 | 1.04 ms | Arduino | Beginners, long-distance |
| 19200 | 19200 | 0.52 ms | Arduino | Standard embedded |
| 115200 | 115200 | 0.087 ms | ESP32, modern MCUs | High-speed debugging, IoT |

**115200 chosen for this exercise** because:
- ESP32 default baud rate (faster processors)
- 12× faster than 9600 (data arrives quicker)
- Still reliable over short USB distances
- Standard for modern firmware debugging

### Platform Interoperability

| Aspect | Arduino UNO | ESP32 | Handling |
| --- | --- | --- | --- |
| Voltage | 5V | 3.3V | Divider or USB isolation |
| Clock Speed | 16 MHz | 80-240 MHz | No impact on UART timing |
| UART Instances | 1 (Serial only) | 3 (Serial, Serial1, Serial2) | Use Serial2 for this exercise |
| Baud Rate Support | Up to 115200 | Up to 115200+ | Both support 115200 |

### Data Validation Importance

Real embedded systems must validate input because:
- **Transmission errors** can corrupt data
- **Timing issues** can cause incomplete messages
- **Buffer overflow** can cause memory issues
- **Invalid commands** could crash device or cause harm

This exercise shows defensive programming: assume input is bad, validate before using.

## Troubleshooting

| Problem | Cause | Solution |
| --- | --- | --- |
| ESP32 shows no output | Wiring incorrect or baud mismatch | Verify pins 16/17, check both at 115200 baud |
| "Com port not found" on ESP32 | USB driver missing | Install CH340 or CP2102 driver (depends on ESP32 board) |
| Garbled messages | Baud rate mismatch | Both must be exactly 115200; check Serial.begin() and Serial2.begin() |
| Messages appear very slowly | TX/RX reversed | Verify Arduino TX (pin 1) → ESP32 RX2 (pin 16) |
| Validation errors (numeric check fails) | Sensor returning non-numeric data | Check ArduinoSender sketch; ensure random() values convert correctly |
| Large gaps between messages | Delayed processing on ESP32 | Normal; serial parsing and validation take time |
| GND not connected | Power reference not shared | ESP32 will show random garbage; always connect GND |

## Cross-Platform Protocol Design

When building protocols for different platforms:

1. **Agree on Baud Rate**: Choose rate both platforms support
2. **Agree on Format**: Clear delimiter and structure
3. **Agree on Character Encoding**: ASCII is universal
4. **Agree on Timeout**: How long to wait before assuming broken connection
5. **Add Validation**: Never trust input, always validate
6. **Document Everything**: Future developers (including you) will need it

## Educational Notes

1. **Voltage Levels**: Embedded systems have different power supplies; cross-platform communication requires care
2. **Baud Rate Synchronization**: Asynchronous UART relies on both sides agreeing on timing
3. **Hardware Abstraction**: Both Arduino and ESP32 hide register-level UART complexity
4. **Data Integrity**: Validation is critical in real systems; this exercise introduces that mindset
5. **Multi-UART Architecture**: ESP32's 3 UART instances enable more complex scenarios (Exercise 04)

## Real-World Applications

- **Sensor Networks**: Arduino collects local data, sends to central ESP32 data logger
- **Telemetry**: Embedded device reports sensor values to cloud gateway
- **Debugging**: Firmware logs sent via UART to central monitoring station
- **Cross-Platform Integration**: Legacy systems communicating with modern IoT devices
- **Redundancy**: Multiple platforms for reliability (if one fails, other continues)

## Next Steps

- **Exercise 04**: Use two ESP32 boards for full-duplex bidirectional communication
- **Exercise 05**: Add error detection with packet checksums and ACK/NACK responses
- **Advanced**: Implement time synchronization, request-response protocols, or data streaming

## See Also

- [Exercise 02 - Arduino to Arduino UART](../Exercise-02-Arduino-to-Arduino-UART/)
- [Exercise 04 - ESP32 Bidirectional Chat](../Exercise-04-ESP32-UART-Chat/)
- [Arduino-to-ESP32 Wiring Diagram](../diagrams/arduino-to-esp32-uart.md)
- [UART Frame Structure](../diagrams/uart-frame-structure.md)
- [Advanced Topics](../diagrams/uart-advanced-topics.md)
