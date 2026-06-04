/**
 * Exercise 04 - ESP32 UART Chat: Node A
 *
 * This sketch implements two-way UART communication between two ESP32 boards.
 * Node A sends PING messages and receives ACK+STATUS responses from Node B.
 * Demonstrates full-duplex communication (simultaneous TX/RX).
 *
 * Hardware Setup:
 * - ESP32 Node A (this sketch)
 * - ESP32 Node B (running NodeB.ino)
 *
 * Wiring (cross TX/RX like Exercise 02):
 * Node A TX2 (Pin 17) ──→ Node B RX2 (Pin 16)
 * Node A RX2 (Pin 16) ←── Node B TX2 (Pin 17)
 * Node A GND ──────────→ Node B GND
 *
 * Baud Rate: 115200 (ESP32 standard)
 *
 * Communication Protocol:
 * Node A → Node B: "PING\n"
 * Node B → Node A: "ACK\nSTATUS: OK\n"
 *
 * Message Types:
 * - PING: Node A requests status from Node B
 * - ACK: Node B acknowledges receipt
 * - STATUS: Node B sends current state
 *
 * Advantages over Exercise 02 (Arduino-to-Arduino):
 * - ESP32 has 3 separate UART instances (Serial, Serial1, Serial2)
 * - Can simultaneously send and receive on same UART
 * - No need for request-response timing constraints
 * - Faster baud rate (115200 vs 9600)
 * - More processing power for complex protocols
 *
 * Educational Value:
 * - Demonstrates full-duplex communication
 * - Shows independent TX and RX processing
 * - Introduces message protocol (PING/ACK/STATUS)
 * - Foundation for networked embedded systems
 */

// Message counters
unsigned long pingsSent = 0;
unsigned long acksReceived = 0;
unsigned long lastPingTime = 0;

void setup() {
  // Initialize Serial (USB-UART for monitoring)
  Serial.begin(115200);

  // Initialize Serial2 (Hardware UART on pins 16/17)
  // RX: Pin 16, TX: Pin 17
  Serial2.begin(115200, SERIAL_8N1, 16, 17);

  // Send startup message
  Serial.println("\n=== ESP32 UART Chat: Node A ===");
  Serial.println("Serial2 initialized at 115200 baud");
  Serial.println("Pins: RX2=16, TX2=17");
  Serial.println("Sending PING messages...\n");

  lastPingTime = millis();
}

void loop() {
  unsigned long currentTime = millis();

  // Send PING every 2 seconds
  if ((currentTime - lastPingTime) >= 2000) {
    sendPing(currentTime);
    lastPingTime = currentTime;
  }

  // Check for incoming messages from Node B
  if (Serial2.available() > 0) {
    receiveMessage(currentTime);
  }

  // Small delay prevents CPU spinning
  delay(10);
}

/**
 * Send PING message to Node B
 */
void sendPing(unsigned long timestamp) {
  pingsSent++;

  // Format: [timestamp ms] PING
  Serial.print("[");
  Serial.print(timestamp);
  Serial.print(" ms] PING sent (attempt #");
  Serial.println(pingsSent);

  // Send PING message to Node B via Serial2
  Serial2.println("PING");
}

/**
 * Receive and parse incoming message from Node B
 */
void receiveMessage(unsigned long timestamp) {
  // Read complete line from Serial2
  String message = Serial2.readStringUntil('\n');
  message.trim();

  // Log raw received message
  Serial.print("[");
  Serial.print(timestamp);
  Serial.print(" ms] RX: \"");
  Serial.print(message);
  Serial.println("\"");

  // Parse and respond to message
  if (message == "ACK") {
    acksReceived++;
    Serial.print("       ACK received! (");
    Serial.print(acksReceived);
    Serial.println(" total)");

  } else if (message.startsWith("STATUS:")) {
    // Extract status message
    String statusValue = message.substring(7);  // Skip "STATUS:"
    statusValue.trim();

    Serial.print("       STATUS: ");
    Serial.println(statusValue);

  } else if (message == "HELLO") {
    Serial.println("       Received HELLO from Node B");

  } else if (message.length() > 0) {
    Serial.print("       Unknown message type: ");
    Serial.println(message);
  }

  // Display statistics every 5 ACKs received
  if (acksReceived % 5 == 0) {
    displayStats();
  }
}

/**
 * Display communication statistics
 */
void displayStats() {
  float successRate = 0.0;
  if (pingsSent > 0) {
    successRate = (float)acksReceived * 100.0 / (float)pingsSent;
  }

  Serial.print("    ┌─ STATS: Sent ");
  Serial.print(pingsSent);
  Serial.print(" PINGs, Received ");
  Serial.print(acksReceived);
  Serial.print(" ACKs (");
  Serial.print(successRate);
  Serial.println("% success)");
  Serial.println("    └─");
}
