/**
 * Exercise 05 - UART Command Protocol: Sender
 *
 * This sketch implements a structured command protocol with packet format:
 * <START>|COMMAND|VALUE|<END>\n
 *
 * Example packets:
 * <START>|LED|ON|<END>
 * <START>|LED|OFF|<END>
 * <START>|TEMP|25|<END>
 * <START>|RELAY|ON|<END>
 *
 * Hardware Setup:
 * - Sender (this sketch): Arduino UNO or ESP32
 * - Receiver: Arduino UNO or ESP32 (running CommandReceiver.ino)
 *
 * Wiring:
 * TX (Pin 1 on Arduino / Pin 17 on ESP32) → RX on Receiver
 * RX (Pin 0 on Arduino / Pin 16 on ESP32) ← TX on Receiver
 * GND → GND (common reference)
 *
 * Baud Rate: 115200 (suitable for both Arduino and ESP32)
 *
 * Protocol Benefits:
 * - Fixed structure makes parsing predictable
 * - Delimiters (<START>, |, <END>) make boundaries clear
 * - Supports multiple command types (LED, TEMP, RELAY)
 * - Extensible: easy to add new commands
 * - Robust: receiver can validate all components
 *
 * Educational Value:
 * - Demonstrates protocol design principles
 * - Shows structured data transmission
 * - Introduces concept of packet format
 * - Foundation for CAN, Modbus, MQTT protocols
 */

// Command definitions
enum Command {
  CMD_LED,
  CMD_TEMP,
  CMD_RELAY,
  CMD_STATUS
};

// Simulated state
bool ledState = false;
bool relayState = false;
int temperatureSetpoint = 20;

void setup() {
  // Arduino: Initialize Serial at 115200
  Serial.begin(115200);

  // ESP32: Uncomment and use Serial2 instead
  // Serial2.begin(115200, SERIAL_8N1, 16, 17);

  Serial.println("\n=== UART Command Protocol: SENDER ===");
  Serial.println("Protocol: <START>|COMMAND|VALUE|<END>");
  Serial.println("Baud rate: 115200");
  Serial.println("Sending commands...\n");

  delay(2000);
}

void loop() {
  static unsigned long sendCount = 0;
  unsigned long timestamp = millis();

  // Send different command types in sequence
  sendCount++;

  if (sendCount % 4 == 1) {
    // Send LED ON command
    sendCommand(timestamp, "LED", "ON", sendCount);
  } else if (sendCount % 4 == 2) {
    // Send LED OFF command
    sendCommand(timestamp, "LED", "OFF", sendCount);
  } else if (sendCount % 4 == 3) {
    // Send TEMP command with value
    int tempValue = 20 + (random(100) % 10);
    sendCommand(timestamp, "TEMP", String(tempValue), sendCount);
  } else {
    // Send RELAY command
    sendCommand(timestamp, "RELAY", "TOGGLE", sendCount);
  }

  // Wait before next command (2 seconds)
  delay(2000);
}

/**
 * Send a command in protocol format: <START>|COMMAND|VALUE|<END>\n
 */
void sendCommand(unsigned long timestamp, String cmd, String value, unsigned long cmdCount) {
  // Build protocol packet
  String packet = "<START>|";
  packet += cmd;
  packet += "|";
  packet += value;
  packet += "|<END>";

  // Log transmission
  Serial.print("[");
  Serial.print(timestamp);
  Serial.print(" ms] TX #");
  Serial.print(cmdCount);
  Serial.print(": ");
  Serial.println(packet);

  // Send packet (including newline as end-of-message delimiter)
  Serial.println(packet);
}
