# Exercise 05 - Realtime Dashboard

## Objective
Build a low-latency dashboard workflow using REST snapshots and WebSocket streaming topics.

## Architecture
- DashboardService snapshot aggregation
- STOMP endpoint /ws-monitoring
- Topics: /topic/metrics, /topic/alerts, /topic/dashboard

## Implementation Steps
1. Call /api/dashboard and inspect aggregates.
2. Subscribe to WebSocket topics.
3. Observe periodic metric and alert updates.
4. Validate stream status via /api/stream/status.

## Expected Output
- DASHBOARD UPDATED
- REALTIME STREAM ACTIVE

## Solution Explanation
This hybrid design supports both pull-based API clients and push-based NOC dashboards, which is common in enterprise monitoring portals.
