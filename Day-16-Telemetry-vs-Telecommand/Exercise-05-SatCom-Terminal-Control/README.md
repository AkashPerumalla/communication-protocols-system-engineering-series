# Exercise 05 - SatCom Terminal Control

## Objective
Reset the modem and recover the SatCom link.

## Marker
SATCOM COMMAND

## Steps
1. Call `POST /api/commands/reset-modem/4`.
2. Inspect the device state and telemetry after recovery.
3. Confirm the audit trail captured the command.

## Expected Output
- The modem transitions through a recovery state.
- The next telemetry reflects improved link health.
- The response includes `SATCOM COMMAND`.
