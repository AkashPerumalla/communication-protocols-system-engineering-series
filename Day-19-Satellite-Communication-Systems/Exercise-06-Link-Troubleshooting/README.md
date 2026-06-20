# Exercise 06 - Link Troubleshooting

## Objective
Perform RCA from impaired link telemetry and generate deterministic trouble ticket actions.

## Implementation Steps

1. Trigger alarms using POST /api/alarms/simulate for a degraded link.
2. Call GET /api/alarms to inspect generated alarm classes.
3. Submit POST /api/troubleshooting/analyze with matching impairment profile.
4. Inspect rootCause and correctiveAction in generated trouble ticket.

## Expected Output

- Marker SATCOM ALARM GENERATED for alarm simulation.
- Marker ROOT CAUSE IDENTIFIED for troubleshooting analysis.
- Root cause maps to rain fade, misalignment, or lock-loss chain checks.

## Solution Walkthrough

TroubleshootingService uses deterministic rule mapping:

- low Rx + high BER -> possible antenna misalignment
- low C/N + low Eb/No + high BER -> rain fade
- lock lost -> inspect LNB/BUC/modem path

The service binds analysis to an existing alarm source and persists an investigation ticket.
