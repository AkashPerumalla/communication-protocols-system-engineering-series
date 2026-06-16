# Exercise 05 - Alert Engine

## Objective
Explore threshold-based alerting for CPU, memory, temperature, and offline device conditions.

## Architecture
- `AlertRuleEngine`
- `AlertService`
- `AlertRepository`
- Severity mapping: INFO, WARNING, CRITICAL

## Steps
1. Start the application.
2. Inspect the dashboard and alert list.
3. Review which thresholds are exceeded.
4. Compare alert counts against device health.

## Expected Output
- Active alerts created from polling data and trap events
- Critical alert for the offline device

## Learning Outcome
You learn how real monitoring platforms convert raw telemetry into actionable incidents with severity and lifecycle control.
