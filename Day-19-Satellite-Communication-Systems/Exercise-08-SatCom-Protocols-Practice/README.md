# Exercise 08 - SatCom Protocols Practice

## Objective
Practice operations-oriented interpretation of SatCom protocol and modem state indicators through NOC APIs.

## Implementation Steps

1. Call GET /api/metrics and inspect modulation, symbolRate, lockStatus, throughput.
2. Correlate lock and BER behavior with alarm output in GET /api/alarms.
3. Use POST /api/alarms/simulate to emulate protocol/state failures.
4. Validate final operational state through GET /api/dashboard.

## Expected Output

- LINK MONITORING ACTIVE, SATCOM ALARM GENERATED, and SATCOM NOC DASHBOARD markers appear.
- Modulation and lock-state changes correlate with quality degradation.
- Dashboard reflects the impact on availability and alarm counts.

## Solution Walkthrough

Although protocol stacks are abstracted, this lab simulates NOC-level interpretation of modem telemetry and control-plane indicators. Operators observe lock state transitions, BER trends, and modulation profile impacts, then drive alarming and troubleshooting decisions from those indicators.
