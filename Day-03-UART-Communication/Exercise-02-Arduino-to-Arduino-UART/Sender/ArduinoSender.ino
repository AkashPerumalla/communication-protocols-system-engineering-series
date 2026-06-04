/**
 * Exercise 02 - Arduino to Arduino UART: Sender
 *
 * This sketch sends numbered messages from one Arduino to another.
 * Demonstrates basic Arduino-to-Arduino communication.
 *
 * Hardware Setup:
 * - Arduino A (this sketch): Sender
 * - Arduino B: Receiver (running ArduinoReceiver.ino)
 *
 * Wiring (critical for UART to work):
 * Arduino A TX (Pin 1) ──────→ Arduino B RX (Pin 0)
 * Arduino A RX (Pin 0) ←────── Arduino B TX (Pin 1)
 * Arduino A GND ──────────────→ Arduino B GND (common reference)
 *
 * Notes:
 * - Must cross TX to RX (not parallel TX to TX)
 * - Must share common ground (GND)
 * - Cannot use Serial.begin() for both TX and RX on pins 0/1
 *   (pins 0/1 are the only UART pins on Arduino UNO)
 * - If you need simultaneous bidirectional communication,
 *   use Exercise 04 with ESP32 (has 3 UART instances)
 *   or Exercise 02's Receiver sketch on Arduino B
 *
 * Protocol:
 * - Sender transmits: "Message #1\n", "Message #2\n", etc.
 * - Sender waits 1 second between messages (makes output readable)
 * - Receiver displays received messages on its Serial Monitor
 *
 * Educational Value:
 * - Demonstrates hardware TX/RX crossover requirement
 * - Shows message format with delimiter (newline '\n')
 * - Introduces message numbering/sequencing
 * - Builds foundation for real protocols (Exercise 05)
 */

unsigned long messageCount = 0;  // Track number of messages sent

void setup() {
  // Initialize Serial (UART on pins 0/1) at 9600 baud
  // This is the standard rate for Arduino-to-Arduino communication
  Serial.begin(9600);

  // Send startup message (only visible in Sender's Serial Monitor)
  Serial.println("\n=== Arduino to Arduino UART: SENDER ===");
  Serial.println("Sending numbered messages at 9600 baud");
  Serial.println("Waiting for receiver...\n");

  // Wait for receiver to initialize (optional, for clarity)
  delay(2000);
}

void loop() {
  // Increment message counter for each transmission
  messageCount++;

  // Get current timestamp for logging
  unsigned long currentTime = millis();

  // Send formatted message with counter and timestamp
  Serial.print("[");
  Serial.print(currentTime);
  Serial.print(" ms] Message #");
  Serial.println(messageCount);

  // Delay before next message (1 second)
  // This makes the output readable and prevents overwhelming the receiver
  delay(1000);

  // Optional: Log total messages sent periodically (every 10 messages)
  if (messageCount % 10 == 0) {
    Serial.print("--- Total messages sent: ");
    Serial.println(messageCount);
  }
}
