# Exercise-01-Create-Thread

## Objective

Demonstrate explicit thread creation and lifecycle tracking for a NOC task that runs independently from request-handling threads.

## Architecture

- Custom worker thread executes a bounded telemetry preparation job.
- Main controller tracks NEW, RUNNABLE, RUNNING, WAITING, TIMED_WAITING, TERMINATED observations.
- Results are exposed through /api/threads/status.

## Steps

1. Start the Day-23 application.
2. Invoke GET /api/threads/status.
3. Review observed states and marker THREAD CREATED.
4. Repeat to verify deterministic lifecycle sequence.

## Expected Output

- API returns marker THREAD CREATED.
- observedStates includes NEW, RUNNABLE, RUNNING, WAITING, TIMED_WAITING, TERMINATED.

## Solution

Use a dedicated Thread object with monitor wait/notify and timed sleep to force observable state transitions.

## Learning Outcome

You understand direct thread creation, lifecycle transitions, and why raw threads should be controlled and bounded.
