# Exercise 04 - Report Cron Operations

## Objective
Use cron-based scheduling for periodic operational reports.

## Architecture
ReportScheduler (cron) -> ReportService -> ReportRecordRepository.

## Steps
1. Review scheduler.report-daily-cron and scheduler.report-performance-cron.
2. Start the app and wait one cron cycle.
3. Call GET /api/reports.
4. Trigger POST /api/tasks/run/report for manual report generation.

## Expected Output
- Marker: REPORT GENERATED
- Report types include DAILY and PERFORMANCE with summaries

## Solution
Bind cron expressions from application.yml and route each to dedicated report methods.

## Learning Outcome
You can align scheduler jobs with calendar windows and enterprise reporting needs.
