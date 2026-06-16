# Exercise 03 - Metric Collection

## Objective
Trace the metric lifecycle from simulator output to persisted monitoring samples.

## Architecture
- Simulated SNMP GET style reads
- Metric enrichment for telecom and SatCom devices
- JPA persistence for time-series snapshots

## Steps
1. Start the app.
2. Trigger one or more polling cycles.
3. Open `GET /api/metrics`.
4. Identify the generic and specialized telemetry fields.

## Expected Output
- Base CPU, memory, uptime, temperature, and status data
- Telecom fields for Hub-01
- SatCom fields for Modem-01

## Learning Outcome
You learn how monitoring systems store both generic infrastructure metrics and domain-specific telemetry in one operational model.
