# Exercise 05 - UART Command Protocol

## Overview

This exercise teaches structured protocol design by implementing a packet-based command protocol. Instead of simple text messages, commands are wrapped in a structured format with clear delimiters and validation. This demonstrates how real embedded systems (CAN bus, Modbus, MQTT) implement robust communication.

**Key Concepts**: Packet Format, Delimiter-Based Parsing, Validation, Error Handling, Protocol Design

**Protocol Format**: `<START>|COMMAND|VALUE|<END>\n`

**Baud Rate**: 115200 baud

**Boards**: Arduino UNO or ESP32 (both supported)

## Protocol Specification

### Packet Structure

Every command packet has a fixed format:

```
<START>|COMMAND|VALUE|<END>\n
```

| Component | Purpose | Example |
| --- | --- | --- |
| `<START>` | Packet boundary marker (start) | Literal string `<START>` |
| `COMMAND` | Action to perform | LED, TEMP, RELAY, STATUS |
| `VALUE` | Parameter for command | ON, OFF, 25, TOGGLE |
| `<END>` | Packet boundary marker (end) | Literal string `<END>` |
| `\n` | Line terminator | Newline character (ASCII 10) |

### Valid Commands

| Command | Valid Values | Example | Purpose |
| --- | --- | --- | --- |
| LED | ON, OFF | `<START>|LED|ON|<END>` | Control LED |
| TEMP | 0-100 (numeric) | `<START>|TEMP|25|<END>` | Set temperature |
| RELAY | ON, OFF, TOGGLE | `<START>|RELAY|TOGGLE|<END>` | Control relay |
| STATUS | Any text | `<START>|STATUS|OK|<END>` | Request status |

### Response Format

**Valid Command**:
```
ACK|COMMAND|VALUE
```

**Invalid Packet**:
```
ERROR: <reason>
```

Example errors:
- `ERROR: Packet does not start with <START>`
- `ERROR: Unknown command: INVALID`
- `ERROR: Invalid value for TEMP: 150`

## Hardware Setup

### Arduino UNO
| Pin | Function |
| --- | --- |
| Pin 1 (TX) | Transmit |
| Pin 0 (RX) | Receive |
| GND | Ground |

### ESP32
| GPIO | Function |
| --- | --- |
| GPIO17 (TX2) | Transmit on Serial2 |
| GPIO16 (RX2) | Receive on Serial2 |
| GND | Ground |

## Wiring Diagram

```
Sender                    Receiver
┌──────────────┐         ┌──────────────┐
│  TX ────────┼────────┼─ RX           │
│  RX ←───────┼────────┼─ TX           │
│  GND ──────┼────────┼─ GND           │
└──────────────┘         └──────────────┘
```

## Running Instructions

### Program Sender

1. Open Arduino IDE
2. Open `Exercise-05-UART-Command-Protocol/Sender/CommandSender.ino`
3. Select Board: `Tools → Board → Arduino UNO` (or ESP32)
4. Select Port: Sender's USB port
5. **Important**: Line 36 must match board:
   - **Arduino**: `Serial.begin(115200);` (already set)
   - **ESP32**: Uncomment line 39 and comment line 36:
     ```cpp
     // Serial.begin(115200);
     Serial2.begin(115200, SERIAL_8N1, 16, 17);
     ```
6. Compile: `Sketch → Verify/Compile`
7. Upload: `Sketch → Upload`

### Program Receiver

1. Open `Exercise-05-UART-Command-Protocol/Receiver/CommandReceiver.ino`
2. Select Board and Port for Receiver
3. **Important**: Same board selection as sender (Arduino or ESP32)
4. If ESP32, uncomment Serial2 initialization (same as sender)
5. Compile and Upload

### Monitor Receiver

1. Open Serial Monitor: `Tools → Serial Monitor`
2. Select Baud Rate: **115200**
3. Observe ACK responses and statistics

## Expected Output

### Sender Serial Monitor

```
=== UART Command Protocol: SENDER ===
Protocol: <START>|COMMAND|VALUE|<END>
Baud rate: 115200
Sending commands...

[2500 ms] TX #1: <START>|LED|ON|<END>
[4500 ms] TX #2: <START>|LED|OFF|<END>
[6500 ms] TX #3: <START>|TEMP|27|<END>
[8500 ms] TX #4: <START>|RELAY|TOGGLE|<END>
[10500 ms] TX #5: <START>|LED|ON|<END>
[12500 ms] TX #6: <START>|LED|OFF|<END>
[14500 ms] TX #7: <START>|TEMP|23|<END>
[16500 ms] TX #8: <START>|RELAY|TOGGLE|<END>
[18500 ms] TX #9: <START>|LED|ON|<END>
[20500 ms] TX #10: <START>|LED|OFF|<END>

    ┌─ STATS: 10 packets | 10 valid | 0 invalid
    │  Success rate: 100.00%
    └─
```

### Receiver Serial Monitor

```
=== UART Command Protocol: RECEIVER ===
Protocol: <START>|COMMAND|VALUE|<END>
Baud rate: 115200
Listening for commands...

[2520 ms] Packet #1: "<START>|LED|ON|<END>"
       SUCCESS: Command=LED Value=ON
       TX: ACK|LED|ON
[4520 ms] Packet #2: "<START>|LED|OFF|<END>"
       SUCCESS: Command=LED Value=OFF
       TX: ACK|LED|OFF
[6520 ms] Packet #3: "<START>|TEMP|27|<END>"
       SUCCESS: Command=TEMP Value=27
       TX: ACK|TEMP|27
[8520 ms] Packet #4: "<START>|RELAY|TOGGLE|<END>"
       SUCCESS: Command=RELAY Value=TOGGLE
       TX: ACK|RELAY|TOGGLE
[10520 ms] Packet #5: "<START>|LED|ON|<END>"
       SUCCESS: Command=LED Value=ON
       TX: ACK|LED|ON
[12520 ms] Packet #6: "<START>|LED|OFF|<END>"
       SUCCESS: Command=LED Value=OFF
       TX: ACK|LED|OFF
[14520 ms] Packet #7: "<START>|TEMP|23|<END>"
       SUCCESS: Command=TEMP Value=23
       TX: ACK|TEMP|23
[16520 ms] Packet #8: "<START>|RELAY|TOGGLE|<END>"
       SUCCESS: Command=RELAY Value=TOGGLE
       TX: ACK|RELAY|TOGGLE
[18520 ms] Packet #9: "<START>|LED|ON|<END>"
       SUCCESS: Command=LED Value=ON
       TX: ACK|LED|ON
[20520 ms] Packet #10: "<START>|LED|OFF|<END>"
       SUCCESS: Command=LED Value=OFF
       TX: ACK|LED|OFF

    ┌─ STATS: 10 packets | 10 valid | 0 invalid
    │  Success rate: 100.00%
    └─
```

### Error Examples

Intentionally send malformed packets to see error handling:

```
[2520 ms] Packet #1: "<START>LED|ON|<END>"
       ERROR: Packet does not start with <START>

[2540 ms] Packet #2: "<START>|LED|ON<END>"
       ERROR: Packet does not end with <END>

[2560 ms] Packet #3: "<START>|LED|MAYBE|<END>"
       ERROR: Invalid value for LED: MAYBE

[2580 ms] Packet #4: "<START>|TEMPERATURE|25|<END>"
       ERROR: Unknown command: TEMPERATURE

[2600 ms] Packet #5: "<START>|TEMP|150|<END>"
       ERROR: Invalid value for TEMP: 150

    ┌─ STATS: 5 packets | 0 valid | 5 invalid
    │  Success rate: 0.00%
    └─
```

## Parsing Algorithm Explained

### Step 1: Check Start Marker
```cpp
if (!packet.startsWith("<START>")) {
  // Invalid: missing start marker
}
```

### Step 2: Check End Marker
```cpp
if (!packet.endsWith("<END>")) {
  // Invalid: missing end marker
}
```

### Step 3: Extract Content
```cpp
String content = packet.substring(7);              // Skip "<START>"
content = content.substring(0, content.length() - 5);  // Remove "<END>"
```

### Step 4: Split on Delimiters
```cpp
int firstPipe = content.indexOf('|');
int secondPipe = content.indexOf('|', firstPipe + 1);
String command = content.substring(0, firstPipe);
String value = content.substring(firstPipe + 1, secondPipe);
```

### Step 5: Validate Command
```cpp
CommandType cmdType = parseCommand(command);
if (cmdType == CMD_UNKNOWN) {
  // Invalid: unknown command
}
```

### Step 6: Validate Value
```cpp
if (!validateValue(cmdType, value)) {
  // Invalid: value doesn't match command type
}
```

## Validation Rules

### LED Command
```cpp
case CMD_LED:
  return (value == "ON" || value == "OFF");
```
- **Valid**: `ON`, `OFF`
- **Invalid**: `TRUE`, `1`, `YES`

### TEMP Command
```cpp
case CMD_TEMP:
  if (!isNumeric(value)) return false;
  int tempVal = value.toInt();
  return (tempVal >= 0 && tempVal <= 100);
```
- **Valid**: `0`, `25`, `100`
- **Invalid**: `150`, `-10`, `ABC`, `25.5`

### RELAY Command
```cpp
case CMD_RELAY:
  return (value == "ON" || value == "OFF" || value == "TOGGLE");
```
- **Valid**: `ON`, `OFF`, `TOGGLE`
- **Invalid**: `SWITCH`, `1`, `2`

### STATUS Command
```cpp
case CMD_STATUS:
  return true;  // Accept any value
```
- **Valid**: Any text
- Used for flexible status requests

## Key Concepts Explained

### Packet Framing

**Problem**: How does receiver know where one message ends and next begins?

**Solutions**:
1. **Fixed Length**: Always 10 bytes (inflexible for variable-length data)
2. **Delimiter**: Special characters mark boundaries (used here: `<START>` and `<END>`)
3. **Length Prefix**: First byte is length, then data (used in some protocols)

**This exercise uses delimiters** because they're human-readable and flexible.

### Protocol Robustness

Real protocols add more safeguards:

| Feature | Purpose | Cost | Example |
| --- | --- | --- | --- |
| Start/End Markers | Frame boundaries | 14 bytes per packet | `<START>`, `<END>` |
| Checksum/CRC | Detect corruption | 2-4 bytes | Sum of all bytes % 256 |
| Length Field | Variable packets | 1-4 bytes | First byte = payload length |
| Message ID | Sequence tracking | 1-2 bytes | Message counter |
| Encryption | Confidentiality | 16+ bytes | AES-128 |

**This exercise focuses on framing** to keep complexity manageable.

### Extensibility

Easy to add new commands:

```cpp
// Add to enum:
enum CommandType {
  CMD_LED,
  CMD_TEMP,
  CMD_RELAY,
  CMD_STATUS,
  CMD_BUZZER,    // NEW
  CMD_PUMP,      // NEW
  CMD_UNKNOWN
};

// Add to parseCommand():
else if (cmd == "BUZZER") {
  return CMD_BUZZER;
}
else if (cmd == "PUMP") {
  return CMD_PUMP;
}

// Add to validateValue():
case CMD_BUZZER:
  return (value == "BEEP" || value == "OFF");

case CMD_PUMP:
  return (value >= 0 && value <= 100);  // Speed 0-100%
```

## Troubleshooting

| Problem | Cause | Solution |
| --- | --- | --- |
| No ACK received | Baud mismatch | Verify both at 115200 |
| Parsing errors but no output | Serial not initialized | Check Serial.begin() or Serial2.begin() |
| All packets marked invalid | Wrong board selected | Verify Tools → Board matches board type |
| Mixed valid/invalid | Timing issue or corruption | Check USB cable quality, reduce baud rate |
| Receiver output truncated | Serial buffer full | Reduce transmission rate or add buffer flush |

## Real-World Protocol Examples

| Protocol | Format | Use Case |
| --- | --- | --- |
| **UART ASCII** (this exercise) | `<START>|CMD|VAL|<END>` | Simple devices, debugging |
| **Modbus** | Binary with CRC | Industrial sensors |
| **CAN** | 8 bytes + ID | Automotive, robotics |
| **MQTT** | JSON over TCP | IoT, cloud |
| **HTTP** | Text headers + body | Web APIs |

## Educational Notes

1. **Protocol Design**: Balance simplicity, reliability, and efficiency
2. **Defensive Programming**: Always validate input; assume worst-case
3. **Extensibility**: Design protocols to support future commands
4. **Documentation**: Clear spec prevents implementation errors
5. **Testing**: Test edge cases (malformed packets, invalid values)
6. **Real-World Skills**: Industry uses similar structured protocols (Modbus, CAN, OPC-UA)

## Advanced Variations

### Variation 1: Add Checksum
```cpp
<START>|LED|ON|<CHECKSUM>|<END>
```
Where checksum = (L + E + D + O + N) % 256

### Variation 2: Message Counter
```cpp
<START>|ID=1|LED|ON|<END>
```
Detects lost messages if counter jumps.

### Variation 3: Binary Protocol
```
[HEADER=2 bytes][CMD=1 byte][VALUE=1 byte][CHECKSUM=1 byte]
```
More compact, harder to debug.

### Variation 4: Bidirectional Commands
Receiver sends commands back to sender:
```
<START>|LOG|Temperature=25|<END>
```

## Next Steps

- **Advanced**: Add CRC32 checksum for error detection
- **Real-World**: Implement Modbus RTU protocol (industrial standard)
- **IoT**: Migrate to MQTT over WiFi (ESP32 capability)

## See Also

- [Exercise 03 - Arduino to ESP32 UART](../Exercise-03-Arduino-to-ESP32-UART/)
- [Exercise 04 - ESP32 Chat](../Exercise-04-ESP32-UART-Chat/)
- [UART Command Protocol Diagram](../diagrams/uart-command-protocol.md)
- [Modbus Specification](https://modbus.org/)
- [CAN Protocol Reference](https://en.wikipedia.org/wiki/CAN_bus)
