# Exercise 02 - Polling Architecture

## Objective
Understand how the scheduler drives periodic device polling and why polling is the foundation of most NOC dashboards.

## Architecture
- `MonitoringScheduler`
- `DevicePollingService`
- `DeviceSimulatorService`
- H2 metric persistence

## Steps
1. Launch the application.
2. Wait for the polling cycle to run.
3. Review the latest rows in `GET /api/metrics`.
4. Compare current device state with the previous sample.

## Expected Output
- A fresh metric row per device on each cycle
- CPU, memory, temperature, status, and interface state stored in the database

## Learning Outcome
You learn how timed polling converts live device state into historical telemetry for analysis and reporting.
