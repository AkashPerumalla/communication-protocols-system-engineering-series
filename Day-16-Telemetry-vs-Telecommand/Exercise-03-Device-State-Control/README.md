# Exercise 03 - Device State Control

## Objective
Enable and disable interfaces and observe how state transitions are persisted.

## Marker
STATE UPDATED

## Steps
1. Call `POST /api/commands/disable-interface/2`.
2. Call `POST /api/commands/enable-interface/2`.
3. Inspect the device state before and after the commands.

## Expected Output
- The interface state changes deterministically.
- The command audit trail records both operations.
- The response includes `STATE UPDATED`.
