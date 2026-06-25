# Exercise 02 - Health Check Automation

## Objective
Implement device health evaluation from telemetry and lastSeen recency.

## Architecture
HealthCheckScheduler -> DeviceHealthService -> DeviceRepository + TelemetryRecordRepository.

## Steps
1. Start the app.
2. Let telemetry and health schedulers run.
3. Call GET /api/devices and inspect status changes.
4. Trigger POST /api/tasks/run/telemetry and observe follow-up health state.

## Expected Output
- Marker: DEVICE HEALTH CHECKED in scheduler logs
- Device statuses include ONLINE and WARNING depending on thresholds

## Solution
Use threshold checks and stale timestamp logic in evaluateDeviceHealth.

## Learning Outcome
You can automate health-state classification in an operations environment.
