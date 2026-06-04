/**
 * Exercise 01 - Arduino UART Basics: TX (Transmitter)
 *
 * This sketch demonstrates basic UART transmission on Arduino UNO.
 * Sends "Hello UART" message every 1 second to the Serial Monitor.
 *
 * Hardware:
 * - Arduino UNO
 * - USB cable (for Serial communication)
 *
 * Concepts:
 * - Baud Rate: Speed of serial communication in bits per second (bps)
 *   9600 baud = 9600 bits/second ≈ 960 characters/second
 *
 * - TX (Transmitter): Arduino digital pin 1 (also called TXD or TX)
 *   Sends serial data out to the receiver
 *
 * - RX (Receiver): Arduino digital pin 0 (also called RXD or RX)
 *   Receives serial data (not used in this TX-only demo)
 *
 * - UART Frame Structure:
 *   [Start Bit (0)] [8 Data Bits] [Stop Bit (1)]
 *   Example: Sending character 'A' (ASCII 65 = 0x41 = 01000001)
 *   Total: 10 bits per character at 9600 baud ≈ 1.04 ms per char
 *
 * - Serial.begin(9600): Initializes UART communication at 9600 baud
 * - Serial.println(): Sends string with newline character
 * - delay(1000): Waits 1000 milliseconds before next transmission
 *
 * Expected Output (Serial Monitor at 9600 baud):
 * Hello UART
 * Hello UART
 * Hello UART
 * ...
 */

void setup() {
  // Initialize serial communication at 9600 baud
  // This sets up the hardware UART on Arduino pin 1 (TX)
  Serial.begin(9600);

  // Optional: Send startup message
  Serial.println("\n=== Arduino UART TX Demo ===");
  Serial.println("Transmitting messages at 9600 baud...");
  Serial.println("Message format: [TIMESTAMP] Hello UART\n");
}

void loop() {
  // Get current time in milliseconds since startup
  unsigned long currentTime = millis();

  // Format and send message with timestamp
  Serial.print("[");
  Serial.print(currentTime);
  Serial.print(" ms] Hello UART\n");

  // Wait 1 second before next transmission
  // This makes the output readable and demonstrates continuous TX
  delay(1000);
}
