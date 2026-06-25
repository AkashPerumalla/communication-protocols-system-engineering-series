# Exercise 06 - Recovery Workflow Orchestration

## Objective
Automate closed-loop remediation for offline devices.

## Architecture
RecoveryScheduler -> RecoveryService -> DeviceRepository + NotificationEventRepository.

## Steps
1. Set at least one device to OFFLINE using database or service hook.
2. Trigger POST /api/tasks/run/recovery.
3. Call GET /api/devices and verify recovered status.
4. Confirm recovery notification creation.

## Expected Output
- Marker: AUTO RECOVERY COMPLETE
- OFFLINE devices transition to ONLINE

## Solution
Simulate restart, reconnect, status update, and notification in autoRecoverDevices.

## Learning Outcome
You can model operational self-healing workflows used in telecom operations centers.
