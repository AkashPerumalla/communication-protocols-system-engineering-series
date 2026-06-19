# Exercise 07: Automation and Recovery

Objective: Execute deterministic auto-recovery runbooks for major hub fault scenarios.

Steps:
1. Call `POST /api/automation/recover/{deviceId}`.
2. Validate generated action list for interface down, modem offline, high temperature, and low signal.
3. Verify persisted side effects through alarms and control command audit history.

Expected marker: AUTO RECOVERY COMPLETE
