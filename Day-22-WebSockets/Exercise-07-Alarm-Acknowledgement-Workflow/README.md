# Exercise 07 - Alarm Acknowledgement Workflow

## Objective
Implement acknowledgement flow so operators can clear ownership of active alarms via REST and WebSocket paths.

## Architecture
Operator command -> AlarmService acknowledge -> AlarmEvent update -> alarm topic broadcast.

## Steps
1. Retrieve current alarm list.
2. Acknowledge one alarm through `POST /api/alarms/{id}/acknowledge`.
3. Acknowledge another alarm through `/app/acknowledge`.
4. Verify `acknowledged=true` in alarm data.

## Expected Output
- Alarm acknowledgement updates persist correctly.
- Marker `ALARM ACKNOWLEDGED` appears for both pathways.
- Dashboard active alarm count decreases in subsequent refresh.

## Solution
Centralize acknowledgement logic in `AlarmService.acknowledge()` and reuse from REST and WebSocket controllers.

## Learning Outcome
You can implement operational state transitions that remain consistent across protocol interfaces.
