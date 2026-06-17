# Exercise 01 - Telemetry Collection

## Objective
Observe metrics from all devices and understand how telemetry flows from device to control center.

## Marker
TELEMETRY RECEIVED

## Steps
1. Start the Spring Boot application.
2. Call `GET /api/telemetry`.
3. Inspect CPU, memory, temperature, power, interface state, and telecom or SatCom-specific fields.

## Expected Output
- Device telemetry records are returned.
- Telecom and SatCom devices expose domain-specific fields.
- The response includes the marker `TELEMETRY RECEIVED`.
