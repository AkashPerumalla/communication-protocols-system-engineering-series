# Exercise 01: Hub Monitoring Lab Setup

Objective: Bring up HUB inventory and monitoring collectors used in SatCom NOC operations.

Steps:
1. Start the application with `mvn spring-boot:run`.
2. Call `GET /api/devices` to discover all HUB devices.
3. Call `GET /api/metrics` to ingest operational telemetry.
4. Call `GET /api/devices/health` for health scoring.

Expected marker: HUB MONITORING ACTIVE
