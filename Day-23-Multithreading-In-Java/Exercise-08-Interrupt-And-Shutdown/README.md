# Exercise-08-Interrupt-And-Shutdown

## Objective

Practice interrupt handling and graceful shutdown for long-running concurrent monitoring workloads.

## Architecture

- MonitoringSimulationService maintains active futures and running flags.
- stopSimulation() cancels workers and scheduled tasks, clears queues.
- @PreDestroy ensures cleanup on application shutdown.

## Steps

1. Start simulation with POST /api/threads/start-simulation.
2. Wait for workload activity.
3. Stop simulation with POST /api/threads/stop-simulation.
4. Verify no lingering worker activity.

## Expected Output

- Marker NOTIFICATION SENT from stop workflow.
- Workers are interrupted and queue is drained.

## Solution

Implement cooperative interruption checks in tasks and invoke Future.cancel(true) during shutdown.

## Learning Outcome

You can implement safe shutdown behavior required for production releases and maintenance windows.
