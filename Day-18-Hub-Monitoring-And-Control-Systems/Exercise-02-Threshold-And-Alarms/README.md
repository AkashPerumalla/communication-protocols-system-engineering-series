# Exercise 02: Threshold and Alarm Processing

Objective: Evaluate telemetry thresholds and generate realistic severity-based HUB alarms.

Steps:
1. Call `GET /api/alarms`.
2. Observe WARNING, MAJOR, and CRITICAL alarms.
3. Acknowledge one alarm using `POST /api/alarms/{id}/acknowledge`.

Expected marker: ALARM GENERATED
