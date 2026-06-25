# Exercise 03 - Alarm Evaluation Pipeline

## Objective
Build a recurring alarm engine using telemetry threshold rules.

## Architecture
AlarmScheduler -> AlarmEvaluationService -> AlarmEventRepository.

## Steps
1. Start the app.
2. Trigger POST /api/tasks/run/alarm.
3. Call GET /api/alarms.
4. Validate warning/major/critical records are persisted.

## Expected Output
- Marker: ALARM GENERATED
- Alarms created for CPU > 80, Memory > 85, Temperature > 70, Signal < -80

## Solution
Implement threshold rule evaluation and severity mapping in determineSeverity.

## Learning Outcome
You can design alarm generation loops used by real NOC monitoring systems.
