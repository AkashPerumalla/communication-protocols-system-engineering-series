# Exercise 02 - Telecommand Execution

## Objective
Send a restart command and observe the command audit trail.

## Marker
COMMAND EXECUTED

## Steps
1. Start the application.
2. Call `POST /api/commands/restart/1`.
3. Inspect the command result and resulting state.

## Expected Output
- The device enters a restart workflow.
- A command result is stored.
- The response includes `COMMAND EXECUTED`.
