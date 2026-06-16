# Exercise 08 - NOC Dashboard

## Objective
Understand the operational summary layer that presents device health, alert pressure, and performance trends to the NOC.

## Architecture
- `DashboardAggregator`
- `DashboardSnapshot`
- `GET /api/dashboard`
- Aggregate counts and averages for operational visibility

## Steps
1. Start the application.
2. Call `GET /api/dashboard`.
3. Compare online and offline device counts.
4. Review active alert totals and average CPU.

## Expected Output
- Total device count
- Online/offline breakdown
- Active and critical alerts
- Average CPU and memory values

## Learning Outcome
You learn how a dashboard condenses a large monitoring estate into a single operational decision view.
