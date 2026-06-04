/**
 * Exercise 02 - Arduino to Arduino UART: Receiver
 *
 * This sketch receives numbered messages from another Arduino.
 * Demonstrates receiving, parsing, and logging UART data.
 *
 * Hardware Setup:
 * - Arduino A: Sender (running ArduinoSender.ino)
 * - Arduino B (this sketch): Receiver
 *
 * Wiring (critical for UART to work):
 * Arduino A TX (Pin 1) ──────→ Arduino B RX (Pin 0)  [cross over]
 * Arduino A RX (Pin 0) ←────── Arduino B TX (Pin 1)  [cross over]
 * Arduino A GND ──────────────→ Arduino B GND         [common reference]
 *
 * Protocol:
 * - Expects messages in format: "Message #N\n"
 * - Message counter increments in receiver (independent of sender)
 * - Each received message logged with timestamp
 * - Transmission statistics updated and displayed
 *
 * Statistics Tracked:
 * - Total messages received
 * - Last message timestamp
 * - Time since last message (gap detection)
 * - Message rate (messages per second)
 *
 * Educational Value:
 * - Demonstrates Serial.available() and Serial.read()
 * - Shows line buffering with readStringUntil('\n')
 * - Introduces statistics/monitoring (real embedded systems need this)
 * - Helps debug communication issues (gaps, timeouts, overflow)
 */

unsigned long messageCount = 0;        // Count of received messages
unsigned long lastMessageTime = 0;     // Timestamp of last received message
unsigned long firstMessageTime = 0;    // Timestamp of first message (for rate calc)

void setup() {
  // Initialize Serial (UART on pins 0/1) at 9600 baud
  // MUST match sender's baud rate
  Serial.begin(9600);

  // Send startup message
  Serial.println("\n=== Arduino to Arduino UART: RECEIVER ===");
  Serial.println("Listening for messages at 9600 baud");
  Serial.println("Waiting for first message...\n");

  // Initialize timing variables
  lastMessageTime = millis();
  firstMessageTime = millis();
}

void loop() {
  // Check if data is available in the serial buffer
  // Serial.available() returns number of bytes waiting
  if (Serial.available() > 0) {
    // Read entire line from serial buffer until newline character
    // This handles multi-character messages like "Message #123\n"
    String receivedMessage = Serial.readStringUntil('\n');

    // Get current timestamp
    unsigned long currentTime = millis();
    unsigned long timeSinceLastMessage = currentTime - lastMessageTime;

    // Increment message counter
    messageCount++;

    // Update tracking timestamps
    if (messageCount == 1) {
      firstMessageTime = currentTime;
    }
    lastMessageTime = currentTime;

    // Display received message with metadata
    Serial.print("[");
    Serial.print(currentTime);
    Serial.print(" ms] Received: ");
    Serial.println(receivedMessage);

    // Display transmission statistics
    Serial.print("    └─ Message #");
    Serial.print(messageCount);
    Serial.print(" | Gap: ");
    Serial.print(timeSinceLastMessage);
    Serial.println(" ms");

    // Every 5 messages, display comprehensive statistics
    if (messageCount % 5 == 0) {
      Serial.println("    ┌─ === TRANSMISSION STATISTICS ===");
      Serial.print("    │  Total received: ");
      Serial.println(messageCount);

      unsigned long elapsedTime = currentTime - firstMessageTime;
      float messageRate = 0.0;
      if (elapsedTime > 0) {
        messageRate = (float)messageCount * 1000.0 / (float)elapsedTime;
      }
      Serial.print("    │  Elapsed time: ");
      Serial.print(elapsedTime);
      Serial.println(" ms");
      Serial.print("    │  Message rate: ");
      Serial.print(messageRate);
      Serial.println(" msg/sec");
      Serial.println("    └─ ============================\n");
    }
  }

  // Small delay prevents CPU spinning, keeps response fast
  delay(10);
}
