# Exercise-02-Runnable

## Objective

Apply Runnable-based tasks to model continuous telemetry and alarm workers that can be submitted to thread pools.

## Architecture

- TelemetryCollectorTask implements Runnable and produces telemetry samples.
- AlarmProcessorTask implements Runnable and consumes samples from BlockingQueue.
- MonitoringSimulationService starts and stops these workers.

## Steps

1. Start application.
2. POST /api/threads/start-simulation.
3. GET /api/telemetry and GET /api/alarms.
4. POST /api/threads/stop-simulation.

## Expected Output

- Marker TASK EXECUTED when simulation starts.
- Marker TELEMETRY COLLECTED from telemetry API.
- Marker ALARM GENERATED from alarms API.

## Solution

Represent long-running background jobs as Runnable implementations and submit them to dedicated executors.

## Learning Outcome

You can design reusable Runnable tasks for enterprise monitoring pipelines.
