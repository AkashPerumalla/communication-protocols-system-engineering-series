/**
 * Exercise 04 - ESP32 UART Chat: Node B
 *
 * This sketch implements two-way UART communication between two ESP32 boards.
 * Node B receives PING messages from Node A and responds with ACK+STATUS.
 * Demonstrates full-duplex communication (simultaneous TX/RX).
 *
 * Hardware Setup:
 * - ESP32 Node A (running NodeA.ino)
 * - ESP32 Node B (this sketch)
 *
 * Wiring (cross TX/RX):
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
 * Startup Behavior:
 * 1. Node B initializes and waits
 * 2. Node A sends first PING after 2 seconds
 * 3. Node B receives PING and immediately sends ACK
 * 4. Node B follows with STATUS message
 * 5. Loop: Repeat every 2 seconds
 *
 * Educational Value:
 * - Demonstrates request-response protocol
 * - Shows message parsing and routing
 * - Introduces state tracking (uptime, ping count)
 * - Foundation for networked systems
 */

// Message counters and state
unsigned long pingsReceived = 0;
unsigned long acksSent = 0;
unsigned long nodeStartTime = 0;

void setup() {
  // Initialize Serial (USB-UART for monitoring)
  Serial.begin(115200);

  // Initialize Serial2 (Hardware UART on pins 16/17)
  // RX: Pin 16, TX: Pin 17
  Serial2.begin(115200, SERIAL_8N1, 16, 17);

  // Record startup time
  nodeStartTime = millis();

  // Send startup message
  Serial.println("\n=== ESP32 UART Chat: Node B ===");
  Serial.println("Serial2 initialized at 115200 baud");
  Serial.println("Pins: RX2=16, TX2=17");
  Serial.println("Waiting for PING messages from Node A...\n");
}

void loop() {
  unsigned long currentTime = millis();

  // Check for incoming messages from Node A
  if (Serial2.available() > 0) {
    receiveMessage(currentTime);
  }

  // Small delay prevents CPU spinning
  delay(10);
}

/**
 * Receive message from Node A and respond
 */
void receiveMessage(unsigned long timestamp) {
  // Read complete line from Serial2
  String message = Serial2.readStringUntil('\n');
  message.trim();

  // Log received message
  Serial.print("[");
  Serial.print(timestamp);
  Serial.print(" ms] RX: \"");
  Serial.print(message);
  Serial.println("\"");

  // Process incoming message
  if (message == "PING") {
    pingsReceived++;
    Serial.println("       PING received!");

    // Send ACK immediately
    sendAck(timestamp);

    // Send STATUS after ACK
    sendStatus(timestamp);

    // Display statistics every 5 PINGs
    if (pingsReceived % 5 == 0) {
      displayStats(timestamp);
    }

  } else if (message == "HELLO") {
    Serial.println("       Received HELLO, acknowledging...");
    Serial2.println("ACK");
    acksSent++;

  } else if (message.length() > 0) {
    Serial.print("       Unknown message: ");
    Serial.println(message);
  }
}

/**
 * Send ACK response
 */
void sendAck(unsigned long timestamp) {
  acksSent++;

  Serial.print("[");
  Serial.print(timestamp);
  Serial.print(" ms] TX: ACK (");
  Serial.print(acksSent);
  Serial.println(" total)");;

  // Send ACK via Serial2
  Serial2.println("ACK");
}

/**
 * Send STATUS response
 */
void sendStatus(unsigned long timestamp) {
  // Calculate uptime
  unsigned long uptime = timestamp - nodeStartTime;
  unsigned long uptimeSeconds = uptime / 1000;

  Serial.print("[");
  Serial.print(timestamp);
  Serial.println(" ms] TX: STATUS");

  // Send STATUS message in format: "STATUS: OK"
  Serial2.print("STATUS: OK | Uptime=");
  Serial2.print(uptimeSeconds);
  Serial2.print("s | PINGs=");
  Serial2.println(pingsReceived);
}

/**
 * Display communication statistics
 */
void displayStats(unsigned long timestamp) {
  Serial.print("    ┌─ STATS: Received ");
  Serial.print(pingsReceived);
  Serial.print(" PINGs, Sent ");
  Serial.print(acksSent);
  Serial.println(" ACKs");
  Serial.println("    └─");
}
