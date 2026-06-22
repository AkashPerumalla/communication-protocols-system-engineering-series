# Exercise 05: Metrics Consumption

Objective: Retrieve telemetry summaries for the platform and for a single managed device.

Architecture:
- MetricController exposes recent metrics and device-specific metrics.
- MetricService maps deterministic seed telemetry into response DTOs.
- Metrics remain separate from device inventory resources.

Steps:
1. Call `GET /api/metrics`.
2. Call `GET /api/metrics/device/5`.
3. Compare metric values for CPU, memory, temperature, and signal strength.
4. Identify which device profile appears healthiest.

Expected Output:
- `METRICS RETRIEVED`

Solution:
- Start with modem and hub device IDs to compare SatCom-oriented telemetry.

Learning Outcome:
- Understand time-series retrieval patterns and why telemetry is modeled separately from inventory.
