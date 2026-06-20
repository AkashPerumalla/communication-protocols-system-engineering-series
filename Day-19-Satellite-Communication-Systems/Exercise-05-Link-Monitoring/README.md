# Exercise 05 - Link Monitoring

## Objective
Analyze SatCom KPIs and identify degradations before full outage conditions occur.

## Implementation Steps

1. Call GET /api/metrics and inspect C/N, Eb/No, BER, Rx/Tx power, lock state.
2. Call GET /api/monitoring/findings.
3. Identify links flagged for C/N degradation and BER increase.
4. Identify links with lock loss and severe attenuation.

## Expected Output

- Marker LINK MONITORING ACTIVE is returned.
- Findings list categorizes issues by C/N, BER, Eb/No, Rx power, and lock.
- Critical link profile includes C/N 7 dB, Eb/No 4.8 dB, BER 3.2E-4.

## Solution Walkthrough

LinkMonitoringService applies threshold rules to each metric snapshot and emits categorized findings with severity hints. This captures how NOC systems perform health scoring and incident prequalification before alarm escalation.
