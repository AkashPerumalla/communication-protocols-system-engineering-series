# Day-23-Multithreading-In-Java

Day 23 delivers a production-style Spring Boot 3.3.x + Java 17 simulation of concurrent workloads inside a Network Operations Center (NOC), telecom monitoring platform, SatCom hub, and real-time monitoring system.

## What Is Multithreading

Multithreading is the ability of a process to execute multiple threads concurrently. In monitoring platforms, this is essential because telemetry collection, alarm detection, notification dispatch, dashboard refreshes, reporting, and maintenance tasks must progress in parallel.

## Thread vs Process

- Process: Independent program with isolated memory.
- Thread: Lightweight execution unit within a process sharing memory.
- NOC implication: One service process can run many threads for lower latency and better resource usage.

## Runnable vs Callable

- Runnable: No return value, no checked exception contract.
- Callable: Returns result and supports checked exceptions.
- This module uses Runnable for continuous workloads and Callable for expensive link-budget computation.

## ExecutorService

ExecutorService decouples task submission from task execution and provides managed thread pools:

- Monitoring fixed thread pool: 5
- Alarm executor: 3
- Notification executor: 3
- Scheduled executor: 2

Thread names are deterministic and enterprise-friendly:

- Telemetry-Thread-n
- Alarm-Thread-n
- Dashboard-Thread-n
- Notification-Thread-n

## Synchronization

The module demonstrates three synchronization strategies via shared MonitoringStatistics:

- Unsafe increment: race condition prone
- synchronized methods: correctness with monitor lock
- AtomicInteger: lock-free correctness

## CompletableFuture

The endpoint /api/threads/completable-future-demo executes parallel data fetches for:

- Device status
- Active alarms
- Dashboard metrics

It combines results through CompletableFuture.allOf() and returns NocDashboardResponse.

## Producer Consumer

A BlockingQueue-based producer-consumer pipeline drives realistic load:

- TelemetryCollectorTask produces telemetry samples
- AlarmProcessorTask consumes samples
- Pipeline persists telemetry, generates alarms, and submits notification tasks

## Best Practices

- Prefer thread pools over raw thread explosion
- Name threads deterministically
- Separate workload types into dedicated executors
- Keep shared state minimal and explicit
- Guard lifecycle with graceful startup/shutdown controls
- Record task execution logs for observability
- Validate async behavior through deterministic markers

## Common Mistakes

- Updating shared counters without synchronization
- Blocking critical thread pools with long operations
- Forgetting cancellation and shutdown hooks
- Using random, non-repeatable test outputs
- Mixing business logic and orchestration layers

## Real-World Monitoring Scenario

The server concurrently handles:

1. Telemetry Collection
2. Alarm Processing
3. Database Logging
4. Dashboard Updates
5. Notification Delivery
6. Report Generation
7. Device Health Monitoring
8. Background Maintenance Jobs

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Scheduling
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Spring Validation
- Maven

## Package Structure

- src/main/java/com/sky2dev/day23/controller
- src/main/java/com/sky2dev/day23/service
- src/main/java/com/sky2dev/day23/repository
- src/main/java/com/sky2dev/day23/entity
- src/main/java/com/sky2dev/day23/dto
- src/main/java/com/sky2dev/day23/config
- src/main/java/com/sky2dev/day23/scheduler
- src/main/java/com/sky2dev/day23/threading
- src/main/java/com/sky2dev/day23/simulation
- src/main/java/com/sky2dev/day23/exception
- src/main/java/com/sky2dev/day23/util

## REST APIs

- GET /api/devices
- GET /api/telemetry
- GET /api/alarms
- GET /api/dashboard
- GET /api/threads/status
- GET /api/threads/statistics
- GET /api/threads/performance
- GET /api/threads/callable-demo
- GET /api/threads/completable-future-demo
- POST /api/threads/start-simulation
- POST /api/threads/stop-simulation

## Deterministic Markers

- THREAD CREATED
- TASK EXECUTED
- TELEMETRY COLLECTED
- ALARM GENERATED
- NOTIFICATION SENT
- THREAD SYNCHRONIZED
- CALLABLE EXECUTED
- COMPLETABLE FUTURE COMPLETED
- THREAD POOL ACTIVE
- MULTITHREADING ACTIVE

## Seed Data

- 10 Devices
- 100 Telemetry Records
- 20 Alarm Events
- 30 Notifications
- 50 Task Execution Logs

## Run

```bash
mvn clean test
mvn -DskipTests spring-boot:run
./scripts/run_all_tests.sh
```

## Exercises

- Exercise-01-Create-Thread
- Exercise-02-Runnable
- Exercise-03-ExecutorService
- Exercise-04-Synchronization
- Exercise-05-Monitoring-Simulator
- Exercise-06-Performance-Comparison
- Exercise-07-Thread-States
- Exercise-08-Interrupt-And-Shutdown

## Diagrams

See the diagrams folder for:

- Thread Lifecycle
- ExecutorService Architecture
- Producer Consumer Flow
- Monitoring System Workflow
- Alarm Processing Flow
- CompletableFuture Flow
- NOC Concurrent Processing Architecture
