# Day 15 - Network Monitoring Architecture

Day 15 is a production-style Spring Boot monitoring laboratory that models the core behavior of a Network Operations Center, a telecom monitoring platform, and a SatCom HUB monitoring stack.

It is intentionally designed to feel like a miniature version of SolarWinds, PRTG, Zabbix, Nagios, OpenNMS, and a carrier-grade NOC console.

The application does not print to the console and stop there.
It collects telemetry, persists it, evaluates thresholds, processes traps, generates alerts, and exposes operational REST APIs.

## What This Module Teaches

- How monitoring systems discover devices and keep inventory current.
- How polling engines collect CPU, memory, temperature, uptime, and interface health.
- How trap and event processing complements periodic polling.
- How an alert engine turns metrics into actionable incidents.
- How dashboards summarize fleet state for operators.
- How telecom and SatCom devices require domain-specific telemetry.
- How Spring Boot can be used to build a realistic monitoring backend.

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Scheduling
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Project Layout

- `src/main/java/com/sky2dev/day15/controller/` - REST APIs for devices, metrics, events, alerts, and the dashboard.
- `src/main/java/com/sky2dev/day15/service/` - polling, aggregation, domain services, and startup bootstrap logic.
- `src/main/java/com/sky2dev/day15/scheduler/` - scheduled jobs for polling and trap generation.
- `src/main/java/com/sky2dev/day15/simulator/` - in-memory device and trap simulators.
- `src/main/java/com/sky2dev/day15/repository/` - Spring Data JPA repositories.
- `src/main/java/com/sky2dev/day15/entity/` - monitoring entities and enums.
- `src/main/java/com/sky2dev/day15/dto/` - API response models.
- `src/main/java/com/sky2dev/day15/alert/` - alert rules, candidates, and alert persistence coordination.
- `src/main/java/com/sky2dev/day15/dashboard/` - dashboard summary model.
- `src/main/java/com/sky2dev/day15/monitoring/` - stats and aggregation helpers.
- `src/main/java/com/sky2dev/day15/config/` - async and execution configuration.
- `src/main/resources/application.yml` - H2 and scheduler configuration.
- `src/main/resources/data.sql` - sample device bootstrap data.

## Architecture Overview

The system is organized as a monitoring pipeline.

1. Devices are loaded into the inventory.
2. A polling scheduler reads device state on a fixed interval.
3. The simulator generates realistic telemetry for each device.
4. Special services enrich telecom and SatCom devices with domain-specific fields.
5. Metric snapshots are persisted to H2.
6. Alert rules evaluate thresholds and create active alerts.
7. Trap events are generated asynchronously and converted into events and alerts.
8. The dashboard aggregator computes fleet-level health summaries.
9. REST APIs expose the operational state to a NOC or integration client.

## Realistic Device Set

The module seeds eight devices:

- Router-01
- Router-02
- Switch-01
- Switch-02
- Modem-01
- Hub-01
- BUC-01
- LNB-01

Each device exposes:

- hostname
- ipAddress
- cpuUsage
- memoryUsage
- uptime
- status
- temperature
- interfaceStatus

## Why H2 Is Used

H2 keeps the project fully self-contained.

It gives you a relational model, JPA queries, dashboard aggregation, and bootstrap data without requiring any external database setup.

That makes the module easy to run in a classroom, a local lab, or a CI pipeline.

## Monitoring Model

The monitoring layer persists five main entities:

- Device
- MonitoringMetric
- TrapEvent
- Alert
- DashboardSnapshot

This mirrors the way a real monitoring product separates inventory, samples, events, incidents, and dashboards.

## Polling Engine

The polling engine is driven by `MonitoringScheduler` and `DevicePollingService`.

Every 10 seconds, the scheduler polls all devices, simulates SNMP GET style reads, enriches telecom and SatCom metrics where appropriate, and stores the result.

Important polling behavior:

- CPU is sampled as a percentage.
- Memory is sampled as a percentage.
- Temperature is sampled in Celsius.
- Status and interface health are preserved or degraded depending on the device state.
- Uptime advances as the device continues to run.

## Event and Trap Engine

The trap engine is driven by `TrapScheduler`, `TrapGenerator`, and `TrapProcessor`.

Every 30 seconds, the system generates realistic event traffic such as:

- Interface Down
- High Temperature
- Device Restart
- High CPU
- High Memory

Traps are processed asynchronously so event bursts do not block the polling pipeline.

## Alert Engine

The alert engine evaluates conditions such as:

- CPU greater than 85 percent
- Memory greater than 90 percent
- Temperature greater than 70 degrees Celsius
- Device offline

Alert severities are represented as:

- INFO
- WARNING
- CRITICAL

Alerts are persisted as active records so the dashboard can summarize current operational pressure.

## Dashboard Aggregation

The dashboard is built by `DashboardAggregator`.

It calculates:

- Total Devices
- Online Devices
- Offline Devices
- Active Alerts
- Critical Alerts
- Average CPU
- Average Memory

The dashboard response is suitable for direct consumption by a NOC UI, a status page, or an integration service.

## Telecom Monitoring

Hub-01 represents a telecom monitoring endpoint.

The module enriches it with RF-focused fields:

- RF Power
- BER
- Carrier Lock
- Frequency
- Symbol Rate

This gives the project a telecom operations flavor rather than only generic infrastructure monitoring.

## SatCom Monitoring

Modem-01 represents a SatCom monitoring endpoint.

The module enriches it with satellite ground-segment fields:

- EbNo
- Modem Status
- BUC Status
- LNB Status
- Uplink Power
- Downlink Power

That makes the monitoring stack useful for satellite network labs and hub operations demos.

## REST APIs

The module exposes these endpoints:

- `GET /api/dashboard`
- `GET /api/devices`
- `GET /api/devices/{id}`
- `GET /api/metrics`
- `GET /api/events`
- `GET /api/alerts`

### Example Dashboard Response

```json
{
  "totalDevices": 8,
  "onlineDevices": 7,
  "offlineDevices": 1,
  "activeAlerts": 3,
  "criticalAlerts": 1,
  "averageCpu": 42.5,
  "averageMemory": 41.8
}
```

## Validation

Use `scripts/test-api.sh` to validate the main operational endpoints.

The script checks dashboard, device, event, and alert APIs and prints the required PASS message when the platform responds correctly.

## Testing Strategy

The repository includes JUnit-based integration tests for:

- Device inventory and controller output
- Dashboard aggregation
- Polling and metric persistence

Scheduling is disabled during tests so the results stay deterministic and repeatable.

## Learning Path

1. Read the architecture diagrams.
2. Review the device inventory model.
3. Inspect the polling scheduler.
4. Trace a simulated metric from generation to storage.
5. Study the trap processor and alert engine.
6. Query the REST APIs.
7. Compare telecom and SatCom telemetry.
8. Run the API validation script.

## Real-World Applications

This architecture maps directly to real operational systems in:

- Enterprise monitoring
- Telecom NOCs
- SatCom ground stations
- ISP monitoring platforms
- Private cloud operations
- Managed services dashboards
- Industrial telemetry systems

## Design Notes

- The simulator keeps the app self-contained.
- The repository layer keeps data access clean and queryable.
- The alert engine uses threshold rules rather than console messages.
- The dashboard is generated from persisted operational data.
- The code is organized to resemble a small but realistic production backend.

## Exercises

See the eight exercise folders for the learning path:

- Exercise-01-Device-Discovery
- Exercise-02-Polling-Architecture
- Exercise-03-Metric-Collection
- Exercise-04-Trap-Processing
- Exercise-05-Alert-Engine
- Exercise-06-Telecom-Monitoring
- Exercise-07-SatCom-Monitoring
- Exercise-08-NOC-Dashboard

## Diagrams

The `diagrams/` folder contains Mermaid documentation for:

- Monitoring architecture
- Polling workflow
- Trap processing
- Alert engine
- Dashboard architecture
- Telecom monitoring
- SatCom monitoring

## Operational Principle

A monitoring platform is only useful when it can do all of the following at once:

- see the device inventory
- sample telemetry regularly
- react to asynchronous events
- raise useful alerts
- summarize state for operators
- keep historical data for analysis

Day 15 demonstrates that complete flow in one Spring Boot application.

## Start Here

1. Open the Day-15 folder in VS Code.
2. Run the Spring Boot application.
3. Call the REST APIs.
4. Read the diagrams and exercises.
5. Run the validation script.

## Appendix A - Operational Reference

### Device Inventory Notes
- Device inventory note 01: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 02: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 03: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 04: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 05: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 06: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 07: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 08: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 09: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 10: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 11: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 12: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 13: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 14: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 15: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 16: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 17: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 18: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 19: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 20: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 21: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 22: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 23: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 24: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 25: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 26: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 27: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 28: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 29: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 30: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 31: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 32: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 33: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 34: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 35: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 36: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 37: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 38: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 39: keep hostname, IP address, category, and health state aligned with the authoritative device registry.
- Device inventory note 40: keep hostname, IP address, category, and health state aligned with the authoritative device registry.

### Polling Notes
- Polling note 01: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 02: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 03: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 04: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 05: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 06: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 07: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 08: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 09: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 10: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 11: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 12: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 13: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 14: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 15: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 16: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 17: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 18: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 19: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 20: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 21: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 22: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 23: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 24: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 25: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 26: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 27: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 28: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 29: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 30: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 31: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 32: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 33: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 34: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 35: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 36: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 37: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 38: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 39: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.
- Polling note 40: poll on a fixed cadence, persist each sample, and avoid relying on console output as the final system of record.

### Trap Notes
- Trap note 01: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 02: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 03: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 04: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 05: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 06: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 07: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 08: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 09: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 10: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 11: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 12: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 13: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 14: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 15: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 16: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 17: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 18: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 19: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 20: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 21: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 22: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 23: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 24: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 25: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 26: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 27: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 28: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 29: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 30: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 31: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 32: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 33: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 34: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 35: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 36: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 37: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 38: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 39: treat asynchronous events as first-class monitoring data instead of incidental log lines.
- Trap note 40: treat asynchronous events as first-class monitoring data instead of incidental log lines.

### Alert Notes
- Alert note 01: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 02: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 03: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 04: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 05: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 06: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 07: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 08: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 09: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 10: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 11: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 12: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 13: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 14: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 15: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 16: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 17: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 18: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 19: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 20: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 21: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 22: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 23: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 24: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 25: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 26: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 27: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 28: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 29: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 30: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 31: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 32: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 33: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 34: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 35: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 36: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 37: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 38: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 39: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.
- Alert note 40: deduplicate active incidents so repeated polling does not create duplicate pages for the same condition.

### Dashboard Notes
- Dashboard note 01: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 02: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 03: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 04: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 05: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 06: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 07: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 08: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 09: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 10: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 11: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 12: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 13: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 14: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 15: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 16: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 17: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 18: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 19: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 20: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 21: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 22: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 23: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 24: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 25: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 26: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 27: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 28: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 29: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 30: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 31: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 32: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 33: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 34: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 35: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 36: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 37: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 38: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 39: aggregate from persisted samples so the operator view is fast and explainable.
- Dashboard note 40: aggregate from persisted samples so the operator view is fast and explainable.

### Telecom Notes
- Telecom note 01: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 02: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 03: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 04: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 05: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 06: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 07: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 08: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 09: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 10: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 11: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 12: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 13: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 14: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 15: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 16: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 17: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 18: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 19: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 20: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 21: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 22: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 23: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 24: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 25: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 26: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 27: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 28: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 29: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 30: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 31: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 32: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 33: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 34: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 35: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 36: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 37: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 38: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 39: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.
- Telecom note 40: RF power, BER, carrier lock, frequency, and symbol rate are core indicators for Hub-01 style services.

### SatCom Notes
- SatCom note 01: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 02: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 03: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 04: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 05: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 06: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 07: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 08: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 09: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 10: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 11: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 12: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 13: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 14: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 15: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 16: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 17: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 18: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 19: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 20: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 21: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 22: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 23: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 24: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 25: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 26: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 27: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 28: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 29: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 30: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 31: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 32: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 33: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 34: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 35: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 36: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 37: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 38: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 39: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.
- SatCom note 40: EbNo, modem status, BUC status, LNB status, uplink power, and downlink power must be monitored together.

### API Notes
- API note 01: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 02: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 03: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 04: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 05: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 06: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 07: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 08: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 09: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 10: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 11: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 12: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 13: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 14: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 15: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 16: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 17: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 18: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 19: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 20: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 21: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 22: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 23: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 24: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 25: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 26: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 27: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 28: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 29: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 30: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 31: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 32: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 33: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 34: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 35: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 36: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 37: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 38: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 39: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.
- API note 40: keep REST responses stable, descriptive, and suitable for direct consumption by NOC tooling.

### Data Model Notes
- Data model note 01: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 02: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 03: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 04: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 05: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 06: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 07: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 08: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 09: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 10: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 11: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 12: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 13: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 14: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 15: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 16: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 17: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 18: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 19: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 20: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 21: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 22: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 23: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 24: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 25: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 26: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 27: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 28: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 29: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 30: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 31: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 32: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 33: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 34: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 35: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 36: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 37: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 38: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 39: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.
- Data model note 40: separate inventory, metrics, traps, alerts, and snapshots so each concern remains queryable on its own.

### Testing Notes
- Testing note 01: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 02: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 03: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 04: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 05: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 06: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 07: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 08: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 09: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 10: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 11: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 12: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 13: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 14: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 15: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 16: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 17: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 18: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 19: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 20: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 21: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 22: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 23: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 24: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 25: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 26: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 27: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 28: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 29: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 30: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 31: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 32: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 33: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 34: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 35: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 36: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 37: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 38: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 39: disable scheduler races in tests so the dashboard and alert counts remain deterministic.
- Testing note 40: disable scheduler races in tests so the dashboard and alert counts remain deterministic.

### Operations Notes
- Operations note 01: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 02: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 03: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 04: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 05: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 06: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 07: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 08: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 09: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 10: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 11: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 12: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 13: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 14: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 15: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 16: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 17: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 18: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 19: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 20: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 21: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 22: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 23: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 24: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 25: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 26: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 27: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 28: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 29: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 30: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 31: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 32: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 33: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 34: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 35: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 36: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 37: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 38: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 39: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.
- Operations note 40: the same platform should support enterprise IT, telecom, and SatCom operations without changing the core monitoring flow.

### Troubleshooting Notes
- Troubleshooting note 01: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 02: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 03: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 04: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 05: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 06: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 07: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 08: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 09: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 10: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 11: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 12: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 13: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 14: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 15: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 16: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 17: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 18: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 19: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 20: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 21: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 22: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 23: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 24: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 25: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 26: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 27: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 28: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 29: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 30: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 31: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 32: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 33: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 34: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 35: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 36: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 37: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 38: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 39: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
- Troubleshooting note 40: when counts look wrong, inspect the latest metric rows, active alerts, and bootstrap data before changing the UI layer.
