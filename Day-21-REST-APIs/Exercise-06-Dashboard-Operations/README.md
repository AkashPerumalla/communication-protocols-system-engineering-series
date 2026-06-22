# Exercise 06: Dashboard Operations

Objective: Generate an operational summary similar to a compact NOC dashboard service.

Architecture:
- DashboardService aggregates device counts and average telemetry values.
- DeviceRepository supplies inventory counts.
- MetricRepository supplies average CPU and memory utilization.

Steps:
1. Call `GET /api/dashboard`.
2. Record the total devices and online/offline split.
3. Compare device-type counts with the seeded inventory.
4. Interpret the average CPU and memory usage values.

Expected Output:
- `DASHBOARD GENERATED`

Solution:
- Use the dashboard to explain the current network posture in one short operational summary.

Learning Outcome:
- Learn how dashboards differ from raw resource APIs by returning aggregated operational intelligence.
