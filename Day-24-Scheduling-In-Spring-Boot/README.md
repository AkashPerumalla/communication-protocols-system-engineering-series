# Day-24 Scheduling In Spring Boot

This module simulates a production-style Telecom/NOC/SatCom Operations Automation Platform where recurring jobs collect telemetry, detect faults, generate alarms, notify operators, build reports, archive old data, and recover offline devices.

## Learning Objectives
- Understand @EnableScheduling and when to use it in enterprise systems
- Implement @Scheduled with fixedRate, fixedDelay, and cron
- Use ThreadPoolTaskScheduler for parallel job execution
- Externalize scheduling intervals in application.yml
- Track task execution lifecycle in persistent logs
- Handle scheduler failures and keep jobs resilient
- Build operations APIs for manual job triggering and observability
- Validate marker-driven behavior with automated smoke scripts

## Technology Stack
- Java 17
- Spring Boot 3.3.x
- Maven
- Spring Scheduler
- Spring Data JPA
- H2 Database
- Lombok
- Validation
- Spring Actuator

## Production Scheduling Fundamentals

### fixedRate
Use fixedRate when the next run should start based on interval cadence, independent of previous run completion.
- Best for telemetry polling, dashboard refresh, and health checks.
- Risk: overlap if task duration exceeds interval. Use thread pools and idempotent logic.

### fixedDelay
Use fixedDelay when each run must finish before waiting the delay and starting again.
- Best for maintenance, cleanup, and non-overlapping housekeeping.
- Good for workload that must avoid concurrent runs.

### cron
Use cron for calendar-aligned workloads.
- Best for report generation, archival windows, and policy-driven jobs.
- Keep cron in config to avoid redeploying for schedule changes.

## Thread Pool Scheduling
- Configured with ThreadPoolTaskScheduler in SchedulerConfig
- Pool size: 10
- Thread name prefix: Scheduler-Thread-
- Graceful shutdown enabled
- Await termination enabled
- Remove canceled tasks enabled

This prevents a single long-running task from starving all scheduler jobs.

## Project Layout
- src/main/java/com/sky2dev/day24/config: scheduler and seed config
- src/main/java/com/sky2dev/day24/entity: domain entities and enums
- src/main/java/com/sky2dev/day24/repository: JPA repositories
- src/main/java/com/sky2dev/day24/service: operations logic
- src/main/java/com/sky2dev/day24/scheduler: dedicated scheduler classes
- src/main/java/com/sky2dev/day24/controller: REST APIs and manual triggers
- src/main/java/com/sky2dev/day24/exception: global exception handling
- src/test/java/com/sky2dev/day24: Day24ApplicationTests
- scripts/run_all_tests.sh: automation validation script

## Scheduled Jobs in this Platform
- TelemetryScheduler: every 5 seconds
- HealthCheckScheduler: every 15 seconds
- AlarmScheduler: every 20 seconds
- DashboardScheduler: every 30 seconds
- NotificationScheduler: every 45 seconds
- RecoveryScheduler: every minute
- ReportScheduler: cron-based daily/performance reports
- ArchiveScheduler: cron-based archival
- MaintenanceScheduler: fixed-delay cleanup and DB maintenance

## Task Execution Monitoring
Every scheduled job is wrapped by TaskExecutionService:
- Creates TaskExecutionLog at start (RUNNING)
- Captures start/end timestamps
- Calculates executionDurationMs
- Marks SUCCESS or FAILED
- Saves failure message for troubleshooting

This pattern mirrors NOC operational auditability requirements.

## API Endpoints
- GET /api/devices
- GET /api/telemetry
- GET /api/alarms
- GET /api/reports
- GET /api/dashboard
- GET /api/tasks/executions
- GET /api/tasks/statistics
- POST /api/tasks/run/telemetry
- POST /api/tasks/run/alarm
- POST /api/tasks/run/report
- POST /api/tasks/run/archive
- POST /api/tasks/run/recovery
- GET /api/platform/status

## Marker Contracts
- SCHEDULER ACTIVE
- TELEMETRY COLLECTED
- DEVICE HEALTH CHECKED
- ALARM GENERATED
- NOTIFICATION SENT
- REPORT GENERATED
- DATA ARCHIVED
- MAINTENANCE COMPLETED
- AUTO RECOVERY COMPLETE
- DASHBOARD UPDATED

## Monitoring Scheduled Jobs in Production
- Persist execution logs and monitor FAILED counts
- Expose scheduler health in dashboard DTO
- Use actuator health and metrics
- Alert on repeated job failures
- Use fixedDelay for heavy maintenance workloads
- Keep job logic idempotent and retry-safe

## Troubleshooting Guide
- Jobs not running: verify app.scheduling.enabled=true
- Unexpected overlap: increase thread pool or switch to fixedDelay
- Marker mismatch in tests: verify controller marker constants
- Seed count drift during tests: disable scheduling in tests
- Slow startup: check H2 schema update and repository initialization

## Running Instructions

### Run unit and integration tests
```bash
mvn clean test
```

### Run app
```bash
mvn spring-boot:run
```

### Run full automation script
```bash
scripts/run_all_tests.sh
```

Expected script result:
- PASS: Day-24 Scheduling automation checks completed successfully

## Exercises
- Exercise-01-Telemetry-Scheduler-Basics
- Exercise-02-Health-Check-Automation
- Exercise-03-Alarm-Evaluation-Pipeline
- Exercise-04-Report-Cron-Operations
- Exercise-05-Archive-Strategy
- Exercise-06-Recovery-Workflow-Orchestration
- Exercise-07-Task-Execution-Observability
- Exercise-08-Maintenance-And-Cleanup

## Interview Preparation
See interview-questions.md for 50 role-focused scheduling and enterprise operations questions.

## Key Takeaways
- Scheduling in enterprise platforms requires execution tracking, not just timers.
- fixedRate/fixedDelay/cron must match workload behavior and risk profile.
- Thread-pool configuration is central to scheduler reliability.
- Marker-driven APIs and scripts make CI validation deterministic.
- Recovery and maintenance jobs are first-class NOC operations workflows.
