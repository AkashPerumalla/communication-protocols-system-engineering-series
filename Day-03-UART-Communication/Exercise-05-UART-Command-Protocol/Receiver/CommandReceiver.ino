/**
 * Exercise 05 - UART Command Protocol: Receiver
 *
 * This sketch receives and parses structured command packets:
 * <START>|COMMAND|VALUE|<END>\n
 *
 * Parsing Strategy:
 * 1. Read line until '\n'
 * 2. Check for <START> at beginning
 * 3. Split on '|' delimiter
 * 4. Validate command name
 * 5. Validate value format/range
 * 6. Check for <END> at end
 * 7. Respond with ACK or INVALID PACKET
 *
 * Validation Rules:
 * - Must start with "<START>"
 * - Must have exactly 3 pipes (|)
 * - Command must be recognized (LED, TEMP, RELAY, STATUS)
 * - Value must match expected type (string or numeric)
 * - Must end with "<END>"
 *
 * Response Format:
 * ACK <COMMAND> <VALUE>
 * or
 * INVALID PACKET: <reason>
 *
 * Hardware Setup:
 * - Sender: Arduino UNO or ESP32 (running CommandSender.ino)
 * - Receiver (this sketch): Arduino UNO or ESP32
 *
 * Baud Rate: 115200
 *
 * Educational Value:
 * - Demonstrates packet parsing
 * - Shows validation techniques
 * - Introduces error handling in protocols
 * - Foundation for professional embedded systems
 */

// Command definitions
enum CommandType {
  CMD_LED,
  CMD_TEMP,
  CMD_RELAY,
  CMD_STATUS,
  CMD_UNKNOWN
};

// Statistics
unsigned long totalReceived = 0;
unsigned long validCommands = 0;
unsigned long invalidCommands = 0;

void setup() {
  // Arduino: Initialize Serial at 115200
  Serial.begin(115200);

  // ESP32: Uncomment and use Serial2 instead
  // Serial2.begin(115200, SERIAL_8N1, 16, 17);

  Serial.println("\n=== UART Command Protocol: RECEIVER ===");
  Serial.println("Protocol: <START>|COMMAND|VALUE|<END>");
  Serial.println("Baud rate: 115200");
  Serial.println("Listening for commands...\n");
}

void loop() {
  // Check if data available on serial
  if (Serial.available() > 0) {
    // Read complete line
    String receivedPacket = Serial.readStringUntil('\n');
    receivedPacket.trim();

    unsigned long timestamp = millis();
    totalReceived++;

    // Log raw packet
    Serial.print("[");
    Serial.print(timestamp);
    Serial.print(" ms] Packet #");
    Serial.print(totalReceived);
    Serial.print(": \"");
    Serial.print(receivedPacket);
    Serial.println("\"");

    // Parse and validate packet
    parsePacket(receivedPacket, timestamp);

    // Display statistics every 10 packets
    if (totalReceived % 10 == 0) {
      displayStats();
    }
  }

  delay(10);
}

/**
 * Parse received packet and validate
 * Format: <START>|COMMAND|VALUE|<END>
 */
void parsePacket(String packet, unsigned long timestamp) {
  // Check if packet starts with <START>
  if (!packet.startsWith("<START>")) {
    invalidCommands++;
    Serial.println("       ERROR: Packet does not start with <START>");
    return;
  }

  // Check if packet ends with <END>
  if (!packet.endsWith("<END>")) {
    invalidCommands++;
    Serial.println("       ERROR: Packet does not end with <END>");
    return;
  }

  // Remove <START> and <END> markers
  String content = packet.substring(7);                    // Skip "<START>"
  content = content.substring(0, content.length() - 5);  // Remove "<END>"

  // Split on | delimiter
  // Expected format: COMMAND|VALUE
  int firstPipe = content.indexOf('|');
  int secondPipe = content.indexOf('|', firstPipe + 1);

  if (firstPipe == -1 || secondPipe == -1) {
    invalidCommands++;
    Serial.println("       ERROR: Invalid format (missing | delimiters)");
    return;
  }

  // Extract command and value
  String command = content.substring(0, firstPipe);
  String value = content.substring(firstPipe + 1, secondPipe);

  // Parse command type
  CommandType cmdType = parseCommand(command);

  // Validate command
  if (cmdType == CMD_UNKNOWN) {
    invalidCommands++;
    Serial.print("       ERROR: Unknown command: ");
    Serial.println(command);
    return;
  }

  // Validate value based on command type
  if (!validateValue(cmdType, value)) {
    invalidCommands++;
    Serial.print("       ERROR: Invalid value for ");
    Serial.print(command);
    Serial.print(": ");
    Serial.println(value);
    return;
  }

  // All validation passed
  validCommands++;
  Serial.print("       SUCCESS: Command=");
  Serial.print(command);
  Serial.print(" Value=");
  Serial.println(value);

  // Send ACK response
  sendAck(command, value);
}

/**
 * Parse command string and return command type
 */
CommandType parseCommand(String cmd) {
  if (cmd == "LED") {
    return CMD_LED;
  } else if (cmd == "TEMP") {
    return CMD_TEMP;
  } else if (cmd == "RELAY") {
    return CMD_RELAY;
  } else if (cmd == "STATUS") {
    return CMD_STATUS;
  } else {
    return CMD_UNKNOWN;
  }
}

/**
 * Validate value format based on command type
 */
bool validateValue(CommandType cmd, String value) {
  switch (cmd) {
    case CMD_LED:
      // LED expects "ON" or "OFF"
      return (value == "ON" || value == "OFF");

    case CMD_TEMP:
      // TEMP expects numeric value 0-100
      if (!isNumeric(value)) return false;
      int tempVal = value.toInt();
      return (tempVal >= 0 && tempVal <= 100);

    case CMD_RELAY:
      // RELAY expects "ON", "OFF", or "TOGGLE"
      return (value == "ON" || value == "OFF" || value == "TOGGLE");

    case CMD_STATUS:
      // STATUS accepts any value
      return true;

    default:
      return false;
  }
}

/**
 * Check if string is numeric
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
 * Send ACK response
 */
void sendAck(String command, String value) {
  // Format: ACK|COMMAND|VALUE|<END>
  Serial.print("       TX: ACK|");
  Serial.print(command);
  Serial.print("|");
  Serial.println(value);
}

/**
 * Display statistics
 */
void displayStats() {
  Serial.print("\n    ┌─ STATS: ");
  Serial.print(totalReceived);
  Serial.print(" packets | ");
  Serial.print(validCommands);
  Serial.print(" valid | ");
  Serial.print(invalidCommands);
  Serial.println(" invalid");

  if (totalReceived > 0) {
    float validPercent = (float)validCommands * 100.0 / (float)totalReceived;
    Serial.print("    │  Success rate: ");
    Serial.print(validPercent);
    Serial.println("%");
  }

  Serial.println("    └─\n");
}
