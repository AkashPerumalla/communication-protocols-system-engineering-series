# Exercise-06-Performance-Comparison

## Objective

Evaluate concurrency strategy trade-offs between throughput and correctness in high-frequency counter updates.

## Architecture

- Benchmark harness runs three modes: unsafe, synchronized, atomic.
- Results include expected count, actual count, duration, and accuracy flag.

## Steps

1. GET /api/threads/performance.
2. Compare durationMs across strategies.
3. Identify correctness failures.

## Expected Output

- Unsafe mode is fastest in some runs but may be incorrect.
- synchronized and atomic maintain correctness.

## Solution

Use dedicated benchmark loops and controlled thread count to compare synchronization techniques.

## Learning Outcome

You can justify synchronization strategy choices with evidence.
