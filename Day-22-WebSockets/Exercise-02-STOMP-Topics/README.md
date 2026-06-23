# Exercise 02 - STOMP Topics

## Objective
Implement and validate topic-based pub-sub for telemetry, alarms, dashboard updates, and client lifecycle events.

## Architecture
Client SUBSCRIBE -> `/topic/*` -> Broker -> Multiple operator subscribers.

## Steps
1. Connect two clients to `/ws-monitoring`.
2. Subscribe both to `/topic/telemetry`.
3. Subscribe one client to `/topic/alarms`.
4. Trigger telemetry and alarm broadcasts.
5. Observe topic-specific delivery behavior.

## Expected Output
- Both clients receive telemetry events.
- Only alarm subscriber receives alarm topic events.
- Marker values match topic semantics.

## Solution
Use `WebSocketBroadcastIntegrationTest` with queue-based frame handlers for each topic to assert marker and payload presence.

## Learning Outcome
You can design and validate scalable event fan-out patterns with destination-level routing.
