# Exercise 01 - WebSocket Handshake

## Objective
Understand the HTTP upgrade process and validate successful STOMP connection establishment for the monitoring endpoint.

## Architecture
Operator Client -> `/ws-monitoring` -> Spring WebSocket endpoint -> STOMP CONNECT/CONNECTED lifecycle.

## Steps
1. Start the application.
2. Open a STOMP client and connect to `/ws-monitoring`.
3. Confirm `101 Switching Protocols` during handshake.
4. Send STOMP CONNECT frame.
5. Verify CONNECTED response from broker.

## Expected Output
- Handshake completes with HTTP 101.
- Session appears as active in connection metrics.
- No transport or protocol errors.

## Solution
Use WebSocketConfigurationIntegrationTest pattern to connect using `WebSocketStompClient` and validate `session.isConnected()`.

## Learning Outcome
You can explain and verify the end-to-end handshake path from HTTP upgrade to active STOMP session.
