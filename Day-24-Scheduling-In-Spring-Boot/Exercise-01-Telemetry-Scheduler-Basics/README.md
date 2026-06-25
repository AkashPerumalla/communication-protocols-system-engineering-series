# Exercise 01 - Telemetry Scheduler Basics

## Objective
Understand how fixedRate scheduling continuously collects deterministic telemetry for all managed devices.

## Architecture
TelemetryScheduler -> TelemetryCollectionService -> TelemetryRecordRepository -> DeviceRepository.

## Steps
1. Start the application.
2. Wait 10-15 seconds.
3. Call GET /api/telemetry and observe records.
4. Call GET /api/tasks/executions and find TelemetryScheduler entries.

## Expected Output
- Marker: TELEMETRY COLLECTED
- Task log status transitions RUNNING -> SUCCESS
- CPU values appear as 35, 75, 95 profiles

## Solution
Use scheduler.telemetry-rate in application.yml and verify TelemetryScheduler invokes collectMetrics via TaskExecutionService.

## Learning Outcome
You can implement cadence-based telemetry polling with observable execution history.
