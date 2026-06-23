# Exercise 05 - Dashboard Auto Refresh

## Objective
Build a dashboard aggregator that updates every 15 seconds and pushes refreshed KPIs to connected operators.

## Architecture
DashboardScheduler -> DashboardService aggregates repositories + connection metrics -> `/topic/dashboard`.

## Steps
1. Query `/api/dashboard` for baseline metrics.
2. Trigger telemetry and alarm generation.
3. Trigger dashboard refresh cycle.
4. Confirm dashboard topic receives updated aggregate payload.

## Expected Output
- Counts for online/offline devices and active alarms are updated.
- Average resource metrics reflect latest telemetry.
- Marker `DASHBOARD UPDATED` is present.

## Solution
Use repository aggregate methods and connection metric counters in a single dashboard DTO.

## Learning Outcome
You can implement composite real-time views that fuse state from multiple operational subsystems.
