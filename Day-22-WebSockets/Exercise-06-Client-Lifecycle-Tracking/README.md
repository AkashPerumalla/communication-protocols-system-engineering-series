# Exercise 06 - Client Lifecycle Tracking

## Objective
Track operator connect/disconnect state and expose active client sessions to dashboards and APIs.

## Architecture
Session events -> ConnectionMetricsService + ConnectedClientService -> `/topic/clients` + `/api/clients`.

## Steps
1. Connect client via `/app/connect` with username payload.
2. Check `/api/clients` to confirm active entry.
3. Disconnect client and verify state transition.
4. Validate client lifecycle markers on `/topic/clients`.

## Expected Output
- Client registration persists session metadata.
- Disconnect marks client inactive.
- Markers `CLIENT CONNECTED` and `CLIENT DISCONNECTED` are emitted.

## Solution
Use session listener events and repository updates keyed by STOMP session ID.

## Learning Outcome
You can model operator presence management for real operational control rooms.
