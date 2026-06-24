# Exercise-05-Monitoring-Simulator

## Objective

Run a realistic concurrent monitoring simulation for telemetry ingestion, alarm processing, notifications, and dashboard refresh.

## Architecture

- Producer-consumer pipeline for telemetry and alarms.
- Notification task fan-out on alarm creation.
- Scheduled tasks for dashboard, reports, and health checks.

## Steps

1. POST /api/threads/start-simulation.
2. Poll /api/telemetry, /api/alarms, /api/dashboard.
3. Observe task logs in database.
4. POST /api/threads/stop-simulation.

## Expected Output

- Markers: TASK EXECUTED, TELEMETRY COLLECTED, ALARM GENERATED, MULTITHREADING ACTIVE.

## Solution

Coordinate worker and scheduled tasks through MonitoringSimulationService with graceful lifecycle control.

## Learning Outcome

You can orchestrate multiple concurrent workloads in one enterprise service.
