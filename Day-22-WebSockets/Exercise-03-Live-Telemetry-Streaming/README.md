# Exercise 03 - Live Telemetry Streaming

## Objective
Generate deterministic telemetry every 5 seconds, persist it, and broadcast live updates without polling.

## Architecture
TelemetryScheduler -> TelemetryService -> TelemetryMetricRepository -> `/topic/telemetry`.

## Steps
1. Run the app and confirm seed telemetry exists.
2. Trigger telemetry scheduler method or wait one cycle.
3. Query `/api/telemetry` and inspect latest records.
4. Subscribe to `/topic/telemetry` and verify updates arrive.

## Expected Output
- New telemetry rows are inserted periodically.
- Telemetry payload includes CPU, memory, temperature, and signal strength.
- Marker `TELEMETRY STREAM ACTIVE` is present.

## Solution
Use `generateTelemetryBatch()` for deterministic value generation and publish via `WebSocketBroadcastService`.

## Learning Outcome
You can implement low-latency telemetry push pipelines that support both storage and live dashboards.
