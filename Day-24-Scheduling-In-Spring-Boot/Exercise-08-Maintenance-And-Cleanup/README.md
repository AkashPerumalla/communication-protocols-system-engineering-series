# Exercise 08 - Maintenance And Cleanup

## Objective
Implement fixedDelay-based maintenance to clean execution logs and run DB housekeeping.

## Architecture
MaintenanceScheduler (fixedDelay) -> MaintenanceService -> TaskExecutionLogRepository.

## Steps
1. Start the app.
2. Wait for maintenance cycle based on scheduler.maintenance-delay.
3. Query GET /api/tasks/executions before and after cycle.
4. Validate maintenance run log entries and durations.

## Expected Output
- Marker: MAINTENANCE COMPLETED in workflow context
- Old execution logs are periodically purged

## Solution
Use deleteByEndTimeBefore threshold logic and keep fixedDelay to avoid overlap.

## Learning Outcome
You can design safe recurring maintenance for enterprise scheduler platforms.
