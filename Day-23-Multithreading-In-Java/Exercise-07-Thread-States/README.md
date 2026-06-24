# Exercise-07-Thread-States

## Objective

Inspect practical thread-state transitions using a controlled demo and API output.

## Architecture

- One demo thread transitions through key lifecycle states.
- Controller reports observed states with deterministic marker.

## Steps

1. Call GET /api/threads/status.
2. Confirm all required lifecycle states are reported.
3. Repeat and compare consistency.

## Expected Output

- Marker THREAD CREATED.
- States list includes NEW, RUNNABLE, RUNNING, WAITING, TIMED_WAITING, TERMINATED.

## Solution

Use latch coordination, monitor wait/notify, and timed sleep to enforce state sequence.

## Learning Outcome

You gain practical understanding of lifecycle transitions used in thread debugging.
