# Exercise 03 - TimeSeries Metrics Storage

## Objective
Design and query time-stamped metric records suitable for monitoring trend analysis.

## Architecture
- MetricRecord with timestamp and indexed agentId/timestamp
- Repository methods for latest and recent windows
- Dashboard aggregation using latest metrics

## Implementation Steps
1. Seed deterministic metrics with healthy/warning/critical samples.
2. Query latest metrics by agent.
3. Query recent metric windows.
4. Aggregate averages for dashboard summaries.

## Expected Output
- METRICS COLLECTED
- DASHBOARD UPDATED

## Solution Explanation
Although H2 is used for local simulation, the data model mirrors production time-series query patterns and supports deterministic validation.
