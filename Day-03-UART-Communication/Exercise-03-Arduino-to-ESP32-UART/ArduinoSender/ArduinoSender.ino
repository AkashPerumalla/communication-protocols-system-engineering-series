/**
 * Exercise 03 - Arduino to ESP32 UART: Arduino Sender
 *
 * This sketch sends sensor-style telemetry messages from Arduino UNO to ESP32.
 * Demonstrates interoperability between different microcontroller platforms.
 *
 * Hardware Setup:
 * - Arduino UNO: Sender (this sketch)
 * - ESP32: Receiver (running ESP32Receiver.ino)
 *
 * Wiring (IMPORTANT: Different baud rates and voltage levels):
 * Arduino TX (Pin 1, 5V output) ──→ ESP32 RX2 (Pin 16, 3.3V input)
 *   Note: Arduino outputs 5V, ESP32 expects 3.3V max
 *   Solution: USB isolation provides sufficient protection for initial projects
 *   Production: Use 10kΩ + 20kΩ voltage divider on RX2
 *
 * Arduino RX (Pin 0, 5V input) ←── ESP32 TX2 (Pin 17, 3.3V output)
 *   Note: ESP32 outputs 3.3V, Arduino can read 3.3V as HIGH (threshold ~2.0V)
 *   This direction is usually safe without level shifting
 *
 * Arduino GND ───────────────────→ ESP32 GND (common reference, CRITICAL)
 *
 * Baud Rate: 115200 (ESP32 standard, 12× faster than Exercise 02)
 * At 115200 baud, one bit = 8.68 microseconds
 * Character transmission time: ~87 microseconds (10 bits)
 *
 * Message Format (sensor telemetry style):
 * TEMP=<value>\n  (example: "TEMP=29\n")
 * HUM=<value>\n   (example: "HUM=65\n")
 *
 * This format is easily parseable:
 * - Key=Value format (common in IoT)
 * - Newline delimiter marks message boundary
 * - Fixed-length keys (TEMP, HUM) aid validation
 *
 * Educational Value:
 * - Demonstrates platform interoperability
 * - Shows baud rate synchronization importance
 * - Introduces sensor-style data format
 * - Foundation for real IoT devices (temperature sensors, humidity sensors)
 */

// Simulated sensor values
int temperature = 25;     // Starting temperature (°C)
int humidity = 50;        // Starting humidity (%)

void setup() {
  // Initialize Serial at 115200 baud (ESP32 standard)
  // This is 12× faster than 9600, appropriate for ESP32 performance
  Serial.begin(115200);

  // Send startup message
  Serial.println("\n=== Arduino to ESP32 UART: SENDER (Sensor Telemetry) ===");
  Serial.println("Transmitting sensor data at 115200 baud");
  Serial.println("Message format: KEY=VALUE");
  Serial.println("Waiting for ESP32 receiver...\n");

  // Small delay for ESP32 to be ready
  delay(2000);
}

void loop() {
  // Get current timestamp
  unsigned long currentTime = millis();

  // Simulate temperature sensor reading (vary between 20-30°C)
  temperature = 20 + (random(100) % 10);  // Random value 20-29

  // Simulate humidity sensor reading (vary between 40-70%)
  humidity = 40 + (random(100) % 30);     // Random value 40-69

  // Send temperature telemetry
  Serial.print("[");
  Serial.print(currentTime);
  Serial.print(" ms] TEMP=");
  Serial.println(temperature);

  // Small delay between measurements
  delay(500);

  // Send humidity telemetry
  Serial.print("[");
  Serial.print(currentTime);
  Serial.print(" ms] HUM=");
  Serial.println(humidity);

  // Wait 1.5 seconds before next sensor readings
  // Typical sensor polling interval in real applications
  delay(1500);

  // Optional: Send a status message every 10 readings
  static unsigned long readingCount = 0;
  readingCount++;

  if (readingCount % 10 == 0) {
    Serial.print("[");
    Serial.print(currentTime);
    Serial.print(" ms] STATUS=Telemetry");
    Serial.print(" ReadingCount=");
    Serial.println(readingCount);
  }
}
