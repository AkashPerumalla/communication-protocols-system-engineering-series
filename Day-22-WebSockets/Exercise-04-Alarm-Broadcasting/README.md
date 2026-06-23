# Exercise 04 - Alarm Broadcasting

## Objective
Evaluate threshold rules and emit warning/major/critical alarms to operations subscribers.

## Architecture
AlarmScheduler -> AlarmService rule engine -> AlarmEventRepository -> `/topic/alarms`.

## Steps
1. Ensure telemetry contains threshold breaches.
2. Trigger alarm evaluation cycle.
3. Inspect `/api/alarms` for generated events.
4. Subscribe to `/topic/alarms` and confirm alarm broadcasts.

## Expected Output
- Alarm events are created with proper severity.
- Offline devices generate critical alarms.
- Marker `ALARM BROADCASTED` appears in pushed payloads.

## Solution
Implement multi-tier thresholds in `AlarmService` and broadcast only when event list is non-empty.

## Learning Outcome
You can convert noisy raw telemetry into operator-grade actionable alarm events.
