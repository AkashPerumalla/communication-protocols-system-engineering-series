/**
 * Exercise 03 - Arduino to ESP32 UART: ESP32 Receiver
 *
 * This sketch receives and parses sensor telemetry from an Arduino UNO.
 * Demonstrates data validation, parsing, and error handling on ESP32.
 *
 * Hardware Setup:
 * - Arduino UNO: Sender (running ArduinoSender.ino)
 * - ESP32 (this sketch): Receiver
 *
 * Wiring:
 * Arduino TX (Pin 1) ──→ ESP32 RX2 (Pin 16) [via optional voltage divider]
 * Arduino RX (Pin 0) ←── ESP32 TX2 (Pin 17)
 * Arduino GND ───────→ ESP32 GND (CRITICAL common reference)
 *
 * ESP32 UART Configuration:
 * - Serial2: Hardware UART on pins 16 (RX2) and 17 (TX2)
 * - Default baud rate: 115200 (ESP32 standard)
 * - Alternative: Serial1 (pins 9/10, used for PSRAM on some boards)
 * - Serial: Pins 1/3 (USB-UART, used for programming)
 *
 * Message Format (from Arduino):
 * TEMP=<value>\n
 * HUM=<value>\n
 * STATUS=<status>\n
 *
 * Parsing Strategy:
 * 1. Read line until '\n' newline character
 * 2. Split on '=' to get key and value
 * 3. Validate key (must be TEMP, HUM, or STATUS)
 * 4. Validate value (must be numeric, within expected range)
 * 5. Update internal state and log
 *
 * Validation:
 * - Key validation: Ensure key is expected value
 * - Value range: Temp 0-100°C, Humidity 0-100%
 * - Format check: Must have '=' separator
 * - Type check: Value must be numeric (all digits)
 *
 * Educational Value:
 * - Demonstrates ESP32 UART configuration
 * - Shows data parsing and validation (real embedded work)
 * - Introduces error handling (invalid packets)
 * - Foundation for IoT data reception
 */

// ESP32 uses Serial2 for hardware UART on pins 16/17
// Note: Different from Arduino UNO (only Serial available)

unsigned long lastDataTime = 0;
unsigned long messageCount = 0;

void setup() {
  // Initialize Serial (USB-UART for monitoring, 115200 baud)
  Serial.begin(115200);

  // Initialize Serial2 (Hardware UART on pins 16/17, 115200 baud)
  // Parameters: Serial2.begin(baud, config, rxPin, txPin)
  Serial2.begin(115200, SERIAL_8N1, 16, 17);
  // SERIAL_8N1: 8 data bits, No parity, 1 stop bit (standard UART frame)

  // Send startup message on USB Serial (Serial Monitor)
  Serial.println("\n=== Arduino to ESP32 UART: RECEIVER (ESP32) ===");
  Serial.println("Listening on Serial2 (pins 16/17) at 115200 baud");
  Serial.println("Expected message format: KEY=VALUE");
  Serial.println("Valid keys: TEMP, HUM, STATUS");
  Serial.println("---\n");

  // Initialize timing
  lastDataTime = millis();
}

void loop() {
  // Check if data available on Serial2 (hardware UART from Arduino)
  if (Serial2.available() > 0) {
    // Read complete line until newline character
    String receivedLine = Serial2.readStringUntil('\n');

    // Remove trailing whitespace (some systems send \r\n)
    receivedLine.trim();

    // Get current timestamp
    unsigned long currentTime = millis();

    // Increment message counter
    messageCount++;

    // Log raw received message
    Serial.print("[");
    Serial.print(currentTime);
    Serial.print(" ms] Received (raw): \"");
    Serial.print(receivedLine);
    Serial.println("\"");

    // Parse message: format is "KEY=VALUE"
    int equalsIndex = receivedLine.indexOf('=');

    if (equalsIndex == -1) {
      // Invalid format: no '=' found
      Serial.println("       ERROR: Invalid format (no '=' separator)");
      logParseError(receivedLine);
    } else {
      // Extract key and value
      String key = receivedLine.substring(0, equalsIndex);
      String value = receivedLine.substring(equalsIndex + 1);

      // Validate and process based on key
      parseAndLogMessage(key, value, currentTime, messageCount);
    }

    // Update timestamp
    lastDataTime = currentTime;

    // Log statistics every 10 messages
    if (messageCount % 10 == 0) {
      Serial.print("\n       === Statistics: ");
      Serial.print(messageCount);
      Serial.println(" messages received ===\n");
    }
  }

  // Check for timeout (no data received for 5 seconds)
  unsigned long currentTime = millis();
  if ((currentTime - lastDataTime) > 5000 && messageCount > 0) {
    // Print timeout warning once, then reset timer
    if ((currentTime - lastDataTime) == 5001) {  // Trigger only once
      Serial.println("WARNING: No data received for 5 seconds");
      Serial.println("Check wiring: Arduino TX → ESP32 RX2 (pin 16)\n");
    }
  }

  // Small delay prevents CPU spinning
  delay(10);
}

/**
 * Parse and validate sensor telemetry message
 *
 * Expected formats:
 * - "TEMP=25" → Temperature 25°C
 * - "HUM=65" → Humidity 65%
 * - "STATUS=Telemetry" → Status update
 */
void parseAndLogMessage(String key, String value, unsigned long timestamp, unsigned long msgNum) {
  // Validate that value is numeric (for TEMP and HUM)
  bool valueIsNumeric = isNumeric(value);

  if (key == "TEMP") {
    if (!valueIsNumeric) {
      Serial.println("       ERROR: TEMP value is not numeric");
      logParseError("TEMP=" + value);
      return;
    }

    int tempValue = value.toInt();

    // Validate temperature range (0-100°C is reasonable)
    if (tempValue < 0 || tempValue > 100) {
      Serial.print("       WARNING: TEMP out of range: ");
      Serial.println(tempValue);
    }

    Serial.print("       PARSED: Temperature = ");
    Serial.print(tempValue);
    Serial.println("°C");

  } else if (key == "HUM") {
    if (!valueIsNumeric) {
      Serial.println("       ERROR: HUM value is not numeric");
      logParseError("HUM=" + value);
      return;
    }

    int humValue = value.toInt();

    // Validate humidity range (0-100% is required)
    if (humValue < 0 || humValue > 100) {
      Serial.print("       WARNING: HUM out of range: ");
      Serial.println(humValue);
    }

    Serial.print("       PARSED: Humidity = ");
    Serial.print(humValue);
    Serial.println("%");

  } else if (key == "STATUS") {
    // Status is free-form text, no numeric validation
    Serial.print("       PARSED: Status = ");
    Serial.println(value);

  } else {
    // Unknown key
    Serial.print("       ERROR: Unknown key: ");
    Serial.println(key);
    logParseError(key + "=" + value);
  }
}

/**
 * Check if string contains only digits (0-9)
 * Returns true if numeric, false otherwise
 */
bool isNumeric(String str) {
  if (str.length() == 0) return false;

  for (int i = 0; i < str.length(); i++) {
    if (!isDigit(str[i])) {
      return false;
    }
  }
  return true;
}

/**
 * Log invalid/error messages for debugging
 */
void logParseError(String failedMessage) {
  Serial.print("       Failed message: \"");
  Serial.print(failedMessage);
  Serial.println("\"");
}
