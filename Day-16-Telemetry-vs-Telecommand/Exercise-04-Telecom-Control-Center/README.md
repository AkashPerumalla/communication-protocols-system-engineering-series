# Exercise 04 - Telecom Control Center

## Objective
Change RF frequency and carrier state for telecom devices.

## Marker
TELECOM CONTROL

## Steps
1. Call `POST /api/commands/change-frequency/1`.
2. Call `POST /api/commands/enable-carrier/1` or `POST /api/commands/disable-carrier/1`.
3. Inspect telemetry and command audit responses.

## Expected Output
- Telecom controls update the device state.
- Frequency changes are visible in the inventory snapshot.
- The response includes `TELECOM CONTROL`.
