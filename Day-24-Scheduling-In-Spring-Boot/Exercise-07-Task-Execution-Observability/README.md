# Exercise 07 - Task Execution Observability

## Objective
Track scheduler execution lifecycle and derive task reliability metrics.

## Architecture
Schedulers -> TaskExecutionService -> TaskExecutionLogRepository -> /api/tasks endpoints.

## Steps
1. Start the app and let jobs run for one minute.
2. Call GET /api/tasks/executions.
3. Call GET /api/tasks/statistics.
4. Inspect status distribution (RUNNING, SUCCESS, FAILED).

## Expected Output
- Marker: SCHEDULER ACTIVE
- Execution records include startTime, endTime, duration, status

## Solution
Wrap every scheduled task with executeTracked and use repository aggregations for statistics.

## Learning Outcome
You can provide production-grade scheduler observability for SRE and NOC teams.
