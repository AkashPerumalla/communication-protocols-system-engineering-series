# UART Advanced Topics

## Hardware UART vs Software UART

### Hardware UART
**Definition**: Uses dedicated on-chip UART controller

| Aspect | Benefit |
| --- | --- |
| **Speed** | Can handle 115200+ baud reliably |
| **Accuracy** | Built-in hardware timing, precise bit sampling |
| **CPU Load** | Minimal; hardware handles reception in background |
| **Buffer** | 64+ bytes on Arduino, 128+ on ESP32 |
| **Reliability** | Never misses data if buffer doesn't overflow |

**Example**: `Serial.begin(9600)` on Arduino uses hardware UART

### Software UART (SoftwareSerial)
**Definition**: Emulates UART using GPIO pins and software timing

| Aspect | Limitation |
| --- | --- |
| **Speed** | Limited to ~57600 baud reliably |
| **Accuracy** | Dependent on CPU timing; can have drift |
| **CPU Load** | High; blocks CPU during RX/TX operations |
| **Buffer** | Very small (16-32 bytes) |
| **Reliability** | Can lose data during heavy processing |

**Example**: `SoftwareSerial softSerial(10, 11)` on Arduino (slower, less reliable)

### Comparison Table

| Feature | Hardware | Software |
| --- | --- | --- |
| **Baud Rate** | Up to 115200+ | Up to 57600 |
| **CPU Usage** | <1% | 10-30% |
| **Buffer Size** | 64-128 bytes | 16-32 bytes |
| **Flexibility** | Limited pins (UART pins only) | Any GPIO pins |
| **Reliability** | High | Lower |
| **Learning Curve** | Simple | Simple |
| **Cost** | Free (built-in) | Free (library) |

**Recommendation**: Always use Hardware UART when possible. Use SoftwareSerial only if additional UART channels needed.

---

## Baud Rate Mismatch Effects

### What Happens When Baud Rates Don't Match?

**Scenario**: Sender @ 9600 baud, Receiver @ 19200 baud (2× mismatch)

```
Sender (9600):   Sends 'A' (01000001) over 10.4 ms
                 ├─ Start bit: 0 ms - 104 µs
                 ├─ Data bits:  104-1040 µs
                 └─ Stop bit: 1040-1104 µs

Receiver (19200): Samples every 52 µs (2× faster)
                 ├─ Sees start bit: OK
                 ├─ Samples data bits too quickly
                 │  └─ Misses correct bit timing
                 ├─ Reconstructs wrong bits
                 └─ Gets garbage instead of 'A'
```

### Visual Example

```
Sender (9600 baud):
Start │ B0 │ B1 │ B2 │ B3 │ B4 │ B5 │ B6 │ B7 │ Stop
  0   │ 1  │ 0  │ 0  │ 0  │ 0  │ 1  │ 0  │ 0  │ 1

Receiver (19200 baud):
Samples at 2× rate (every 52 µs instead of 104 µs)
Start│ X │ B0 │ X │ B1 │ X │ B2 │ X │ B3 │ X │...
  0  │ ? │ 1  │ ? │ 0  │ ? │ 0  │ ? │ 0  │ ? │...
       └─ Samples at wrong time, gets garbage
```

### Output Comparison

**Correct (9600 both sides)**:
```
Serial Monitor shows:
Hello UART
Hello UART
Hello UART
```

**Mismatch (TX=9600, RX=19200)**:
```
Serial Monitor shows:
☐□►¥ ◄■▲
☐□►¥ ◄■▲
☐□►¥ ◄■▲
(Random garbage characters)
```

### Tolerance Range

UART can tolerate timing error of **±5%**:

| Sender Baud | Tolerance Range | OK Baud Rates |
| --- | --- | --- |
| 9600 | 9120 - 10080 | 9600 ✓, 19200 ✗ |
| 115200 | 109440 - 121000 | 115200 ✓, 110000 ✓ |

**Conclusion**: Baud rates must be exactly the same (or extremely close).

---

## Noise and Error Handling

### Sources of Noise

| Source | Effect | Mitigation |
| --- | --- | --- |
| **EMI (Electromagnetic Interference)** | Random bit flips | Shielded cables, ferrite cores |
| **Long cables** (>5 meters) | Signal degradation | Use RS-485 with differential signaling |
| **Power supply ripple** | Voltage fluctuations | Better power supply, capacitive filtering |
| **Cross-talk** | Interference from adjacent wires | Twisted pair, separate grounds |

### Bit Error Example

```
Correct bit:    1 1 1 0 0 0 1 0
With noise:     1 1 0 0 0 0 1 0
                    ↑ flipped
Result: Wrong data value reconstructed
```

### Error Detection Methods

**1. Parity Bit**
```cpp
// Check if odd number of 1s in data
Transmitted: 0 1 0 0 0 0 1 0 | Parity=1
Received:    0 1 0 0 0 0 1 0 | Parity=0  ← Mismatch!
             └──────────────┘
                  6 ones (even)
Conclusion: Error detected! (but can't correct)
```

**2. Checksum**
```cpp
// Sum of all bytes modulo 256
Data: "TEMP=25\n"
Checksum = ('T' + 'E' + 'M' + 'P' + '=' + '2' + '5' + '\n') % 256
Transmitted: <START>|TEMP|25|<CHECKSUM>|<END>

Receiver recalculates checksum and compares
```

**3. CRC (Cyclic Redundancy Check)**
```cpp
// Advanced polynomial-based check
More reliable than checksum
Can detect burst errors
Used in Modbus and CAN protocols
```

### Noise-Hardened Protocol Example

```
Normal protocol:     <START>|COMMAND|VALUE|<END>\n

Robust protocol:     <START>|ID|COMMAND|VALUE|CRC|<END>\n
                              │                  │
                              └─ Message counter  └─ Checksum
                                 (detect loss)       (detect errors)
```

---

## Hardware UART vs Software UART Comparison

### Arduino UNO Resources

```
Pin 0 (RX) ──→ ┌─────────────────────────┐
Pin 1 (TX) ←── │  Hardware UART           │
               │  (ATmega328P USART)      │
               │  • 64-byte buffer        │
               │  • Up to 115200 baud     │
               │  • Automatic timing      │
               └─────────────────────────┘

GPIO 10 ──→ ┌─────────────────────────┐
GPIO 11 ←── │  Software UART (SoftSerial)
            │  • 16-byte buffer        │
            │  • Up to 57600 baud      │
            │  • CPU-dependent timing  │
            └─────────────────────────┘
```

### When to Use Each

**Use Hardware UART (default)**:
- Primary communication (debugging, data logging)
- High baud rates (>19200)
- Time-critical applications
- Continuous data stream

**Use SoftwareSerial Only If**:
- Need multiple serial connections
- Hardware UART already in use
- Lower baud rates acceptable (<19200)
- Occasional data transmission

---

## Logic Analyzer Interpretation

### Waveform for "A" (0x41 = 01000001)

```
Time (µs)
0    104   208   312   416   520   624   728   832   936  1040
│    │     │     │     │     │     │     │     │     │     │
└────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘
 START  1    0     0     0     0     1     0     0   STOP
  ↑                                                    ↑
Logic High (idle)                      Logic Low (active)
5V on Arduino / 3.3V on ESP32


           ┌─ Start Bit (always 0)
           │  ┌─ Data Bits (01000001 = 'A')
           │  │     ┌─ Stop Bit (always 1)
           │  │     │
    HIGH  │└─────────┐
5V ────────┘         └──────┐
           │                 │
           │<─ ~104µs/bit ─>│
0V ─────────────────────────┘
```

### Reading with Logic Analyzer

**Setup**:
1. Connect logic analyzer to TX pin (Channel 0)
2. Set sample rate: **100 kHz minimum** (preferably 500 kHz+)
3. Trigger on falling edge (start of start bit)
4. Capture 20-50 frames

**Measurement**:
```
Bit period should measure:
At 9600 baud:   ~104 microseconds
At 115200 baud: ~8.7 microseconds

If measured differently:
→ Baud rate mismatch confirmed
→ Check sender and receiver configuration
```

**Export**:
Save as PNG screenshot or CSV data for analysis

---

## UART Debugging Techniques

### 1. Loopback Test (Hardware Health Check)

```cpp
void setup() {
  Serial.begin(9600);
}

void loop() {
  if (Serial.available() > 0) {
    char c = Serial.read();
    Serial.write(c);  // Echo back
  }
}
```

**Procedure**:
1. Connect TX pin to RX pin (Pin 1 to Pin 0 on Arduino UNO)
2. Upload sketch
3. Open Serial Monitor
4. Type "HELLO"
5. If you see "HELLO", UART hardware is working ✓

### 2. Visual Inspection

| Symptom | Likely Cause |
| --- | --- |
| No output at all | Check baud rate, check wiring |
| Garbled characters | Baud rate mismatch |
| Occasional garbage | Noise or intermittent connection |
| Slow output | Processing bottleneck, not UART |

### 3. Baud Rate Verification Script

```cpp
void setup() {
  Serial.begin(9600);
  Serial.print("Baud=");
  Serial.println(9600);  // Should print clean
}

// If output is garbled, baud rate mismatch
// If clean, baud rate is correct
```

### 4. Timing Analysis

```cpp
unsigned long lastTime = 0;

void loop() {
  if (Serial.available() > 0) {
    unsigned long currentTime = millis();
    unsigned long gap = currentTime - lastTime;

    char c = Serial.read();

    Serial.print("Gap: ");
    Serial.print(gap);
    Serial.print(" ms, Char: ");
    Serial.println(c);

    lastTime = currentTime;
  }
}

// Analyze timing between messages
// If irregular, may indicate buffer overflow or sender issues
```

### 5. Packet Integrity Check

```cpp
unsigned int crcCalc = 0;

void setup() {
  Serial.begin(115200);
}

void loop() {
  if (Serial.available() > 0) {
    String packet = Serial.readStringUntil('\n');

    // Calculate CRC
    crcCalc = calculateCRC(packet);

    // Compare with transmitted CRC
    if (crcCalc == receivedCRC) {
      Serial.println("Packet OK");
    } else {
      Serial.println("ERROR: CRC mismatch");
    }
  }
}

unsigned int calculateCRC(String data) {
  unsigned int crc = 0;
  for (int i = 0; i < data.length(); i++) {
    crc ^= data[i];
  }
  return crc;
}
```

---

## Common Baud Rates

| Rate | Use Case | Max Distance | Comment |
| --- | --- | --- | --- |
| **300** | Very old devices | 100+ m | Almost obsolete |
| **1200** | Legacy equipment | 50 m | Rarely used |
| **2400** | Slow devices | 30 m | Backup baud |
| **4800** | Minimal rate | 20 m | Unusual |
| **9600** | **Arduino default** | 15 m | Most common for testing |
| **19200** | Standard | 10 m | Common alternative |
| **38400** | Moderate speed | 5 m | Less common |
| **57600** | Higher speed | 3 m | Software UART limit |
| **115200** | **ESP32 default** | 2 m | Modern standard |
| **230400** | High speed | 1 m | May require USB isolation |
| **460800** | Very high | <1 m | Short distances only |

---

## Troubleshooting Decision Tree

```
Problem: No output in Serial Monitor
│
├─ Check USB connection
│  ├─ Not connected → Connect USB cable
│  └─ Connected → Continue
│
├─ Check Serial Monitor speed
│  ├─ Speed wrong → Set to 9600 (or correct rate)
│  └─ Speed correct → Continue
│
├─ Check Serial.begin() in sketch
│  ├─ Not called → Add Serial.begin(9600)
│  └─ Called → Continue
│
├─ Check if sketch is uploaded
│  ├─ Not uploaded → Upload sketch
│  └─ Uploaded → Continue
│
├─ Do loopback test
│  ├─ Loopback fails → UART hardware broken (rare)
│  └─ Loopback works → Wiring issue
│
└─ Check wiring to external device
   ├─ Cross-check TX/RX direction
   ├─ Verify GND connection
   └─ Use multimeter to check voltages
```

## See Also

- [UART Frame Structure](uart-frame-structure.md)
- [Baud Rate Reference](uart-frame-structure.md)
- [Exercise 03 - Validation](../../Exercise-03-Arduino-to-ESP32-UART/)
- [Exercise 05 - Error Handling](../../Exercise-05-UART-Command-Protocol/)
