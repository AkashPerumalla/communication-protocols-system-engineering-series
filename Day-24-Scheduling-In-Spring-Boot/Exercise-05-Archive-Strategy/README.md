# Exercise 05 - Archive Strategy

## Objective
Archive historical telemetry data and maintain archive metadata.

## Architecture
ArchiveScheduler -> ArchiveService -> TelemetryRecordRepository + ArchivedRecordRepository.

## Steps
1. Start the app.
2. Trigger POST /api/tasks/run/archive.
3. Call GET /api/dashboard and inspect archivedRecords.
4. Review task execution logs for archive task entry.

## Expected Output
- Marker: DATA ARCHIVED
- ArchivedRecord created with sourceType and recordCount

## Solution
Select old telemetry by timestamp threshold, delete archived rows, persist archive summary row.

## Learning Outcome
You can implement retention and archival automation for long-running monitoring platforms.
