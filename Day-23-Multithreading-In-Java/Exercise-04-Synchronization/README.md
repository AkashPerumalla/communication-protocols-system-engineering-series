# Exercise-04-Synchronization

## Objective

Compare unsafe increments, synchronized methods, and AtomicInteger under concurrent contention.

## Architecture

- MonitoringStatistics holds shared counters.
- ThreadingDemoService runs parallel increment benchmarks.
- /api/threads/performance reports expected vs actual counts.

## Steps

1. Start application.
2. GET /api/threads/performance.
3. Compare strategy results.
4. Observe marker THREAD SYNCHRONIZED.

## Expected Output

- Unsafe strategy may lose increments.
- synchronized and AtomicInteger strategies remain accurate.

## Solution

Drive high-contention loops in multiple threads and measure correctness + duration for each strategy.

## Learning Outcome

You can identify and fix race conditions in shared-state monitoring components.
