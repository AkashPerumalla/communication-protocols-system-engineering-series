# Exercise 07 - Closed-Loop Automation

## Objective
See how telemetry automatically triggers a recovery command.

## Marker
AUTO CORRECTION

## Steps
1. Trigger a telemetry condition that raises an alarm.
2. Let the automation service execute the recovery action.
3. Verify the alarm is cleared only after the recovery telemetry is observed.

## Expected Output
- Alarm -> command -> verification -> clear.
- The loop is deterministic and audit-friendly.
- The response includes `AUTO CORRECTION`.
