# Exercise-03-ExecutorService

## Objective

Use ExecutorService with dedicated pools to isolate telemetry, alarm, notification, and scheduled workloads.

## Architecture

- monitoringExecutor fixed pool (5)
- alarmExecutor fixed pool (3)
- notificationExecutor fixed pool (3)
- scheduledExecutor scheduled pool (2)

## Steps

1. Start application.
2. Trigger simulation via POST /api/threads/start-simulation.
3. Check /api/devices and /api/threads/statistics.
4. Stop simulation.

## Expected Output

- Marker THREAD POOL ACTIVE in responses.
- Task logs include deterministic thread names (Telemetry-Thread-n, Alarm-Thread-n, Dashboard-Thread-n, Notification-Thread-n).

## Solution

Create named ExecutorService beans with custom ThreadFactory and route each workload to the correct pool.

## Learning Outcome

You can partition concurrency resources to prevent workload interference.
