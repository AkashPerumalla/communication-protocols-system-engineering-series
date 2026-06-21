# Exercise 02 - Implement Alerting System

## Objective
Implement threshold-based alerting that transforms metric anomalies into actionable alert events.

## Architecture
- AlertRule entity with comparator and threshold
- AlertEngine evaluation loop
- AlertEvent persistence and active alert listing

## Implementation Steps
1. Configure rules for CPU, memory, disk, and networkIn.
2. Trigger evaluation using /api/alerts/evaluate.
3. List alerts with /api/alerts and /api/alerts/active.
4. Confirm duplicate suppression behavior for active incidents.

## Expected Output
- ALERT GENERATED

## Solution Explanation
Rules are evaluated against latest per-agent metrics. On threshold breaches, non-duplicate ACTIVE events are persisted and exposed to dashboard and notification layers.
