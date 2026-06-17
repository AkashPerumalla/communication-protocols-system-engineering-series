# Exercise 06 - Remote Recovery Actions

## Objective
Detect a fault and recover the device remotely.

## Marker
REMOTE RECOVERY

## Steps
1. Inspect active alarms with `GET /api/alarms`.
2. Call `POST /api/commands/clear-alarm/{alarmId}` or a backup-link action.
3. Confirm the alarm is cleared after recovery.

## Expected Output
- Recovery actions are executed without manual device access.
- Alarms are cleared only after remediation.
- The response includes `REMOTE RECOVERY`.
