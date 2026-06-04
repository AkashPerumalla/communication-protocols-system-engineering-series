/**
 * Exercise 01 - Arduino UART Basics: RX (Receiver)
 *
 * This sketch demonstrates basic UART reception on Arduino UNO.
 * Receives characters from Serial Monitor and echoes them back with timestamp.
 *
 * Hardware:
 * - Arduino UNO
 * - USB cable (for Serial communication)
 *
 * Concepts:
 * - RX (Receiver): Arduino digital pin 0 (RXD)
 *   Receives serial data from external source
 *
 * - Serial.available(): Returns number of bytes received in the serial buffer
 *   Non-blocking check: returns immediately with count (0 if no data)
 *
 * - Serial.read(): Reads one byte from the serial buffer
 *   Removes the byte from buffer after reading
 *   Returns -1 if no data available
 *
 * - Serial.print() vs Serial.println()
 *   print(): Sends data without newline (no line terminator)
 *   println(): Sends data + '\n' newline character
 *
 * - Character reception flow:
 *   1. User types character in Serial Monitor
 *   2. Character transmitted via USB → Arduino RX pin
 *   3. Arduino UART hardware receives start bit, 8 data bits, stop bit
 *   4. Byte placed in RX buffer (capacity: 64 bytes)
 *   5. loop() checks Serial.available() and reads byte
 *   6. Arduino sends back (echo) to Serial Monitor
 *
 * Expected Output (type "ABC" in Serial Monitor):
 * > ABC (user input)
 * [12345 ms] Received: A
 * [12350 ms] Received: B
 * [12355 ms] Received: C
 */

void setup() {
  // Initialize serial communication at 9600 baud (same as TX demo)
  Serial.begin(9600);

  // Send startup message
  Serial.println("\n=== Arduino UART RX Demo ===");
  Serial.println("Ready to receive data at 9600 baud");
  Serial.println("Type characters in Serial Monitor and press Send");
  Serial.println("---\n");
}

void loop() {
  // Check if data is available in the serial buffer
  // Serial.available() returns the number of bytes ready to read
  // This is non-blocking - returns immediately without waiting
  if (Serial.available() > 0) {
    // Read one byte from the serial buffer
    char receivedChar = Serial.read();

    // Get current timestamp
    unsigned long currentTime = millis();

    // Echo back the received character with timestamp
    // Format: [timestamp] Received: 'character' (ASCII code)
    Serial.print("[");
    Serial.print(currentTime);
    Serial.print(" ms] Received: '");
    Serial.print(receivedChar);
    Serial.print("' (ASCII ");
    Serial.print((int)receivedChar);
    Serial.println(")");

    // Note: At 9600 baud, each character takes ~1.04 ms to receive
    // If multiple characters are sent quickly (like "ABC" pasted),
    // the UART buffer fills and loop() services each character
  }

  // Small delay prevents busy-waiting but keeps response time fast
  delay(10);
}
