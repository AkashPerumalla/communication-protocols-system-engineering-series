# Day-18 Hub Monitoring and Control Systems

A realistic HUB Monitoring and Control (HMC) simulation platform for SatCom hubs, teleport operations, VSAT backbones, telecom NOCs, and mission-critical communication systems.

## Architecture

The module follows an enterprise Spring Boot layered architecture:

1. Controller Layer: REST API endpoints for monitoring, control, alarms, automation, reporting, and NOC views.
2. Service Layer: Operational intelligence and workflows.
3. Repository Layer: Spring Data JPA persistence for deterministic operational state.
4. Domain Layer: Hub entities, enums, and lifecycle models.
5. Seed Layer: Deterministic, realistic SatCom baseline dataset.

Core services:
- HubMonitoringService
- AlarmEngine
- DeviceControlService
- DashboardService
- NotificationService
- ReportingService
- AutomationService
- NocControlCenterService

## Technology Stack

- Java 17
- Spring Boot 3.3.x
- Spring Data JPA
- H2 Database
- Maven
- Lombok
- Bean Validation
- REST APIs

## Data Model

### HubDevice
- id
- hostname
- deviceType
- ipAddress
- status
- location
- vendor
- firmwareVersion
- lastSeen

### DeviceMetric
- id
- deviceId
- cpuUsage
- memoryUsage
- temperature
- signalStrength
- powerLevel
- interfaceStatus
- timestamp
- SatCom and power extensions: EbNo, BER, carrierLock, frequency, symbolRate, BUC/LNB/UPS/Power-meter fields

### Alarm
- id
- deviceId
- severity
- alarmType
- message
- status
- acknowledged
- createdAt

### ControlCommand
- id
- deviceId
- commandType
- commandPayload
- executionStatus
- executionTime
- executedBy

### NotificationEvent
- id
- alarmId
- channel
- deliveryStatus
- sentTime

### Report
- id
- reportType
- generatedBy
- generatedTime
- reportSummary

## Seed Dataset (Deterministic)

- 10 devices
- 30 metrics
- 12 alarms
- 20 notifications
- 10 control commands
- 5 reports

Seeded devices include:
- Router-01
- Router-02
- Switch-01
- Satellite-Modem-01
- Satellite-Modem-02
- BUC-01
- LNB-01
- UPS-01
- Temperature-Sensor-01
- Power-Meter-01

## Monitoring Flow

1. Device discovery collects managed HUB inventory.
2. Telemetry collection ingests latest operational metrics.
3. Health scoring computes per-device serviceability score.
4. Threshold engine checks signal, power, CPU, interface, and temperature.
5. Alarms are created with WARNING, MAJOR, or CRITICAL severity.

Marker: HUB MONITORING ACTIVE

## Control Flow

Supported command set:
- REBOOT_DEVICE
- RESET_INTERFACE
- ENABLE_PORT
- DISABLE_PORT
- CHANGE_CONFIGURATION

Each command updates device state and writes immutable audit history to ControlCommand.

Marker: CONTROL EXECUTED

## Dashboard Flow

Dashboard aggregates:
- Total Devices
- Online Devices
- Offline Devices
- Critical Alarms
- Major Alarms
- Warnings
- Recent Commands
- Device Availability

Marker: DASHBOARD UPDATED

## Alarm Management

Alarm categories:
- LOW_SIGNAL
- HIGH_TEMPERATURE
- POWER_ANOMALY
- HIGH_CPU
- INTERFACE_DOWN
- DEVICE_OFFLINE
- CONTROL_FAILURE
- PERFORMANCE_DEGRADATION

Marker: ALARM GENERATED

## Notification Workflow

Channels:
- EMAIL
- SMS
- WEBHOOK
- SLACK
- TEAMS
- ITSM

Automatic notifications are emitted for critical alarms and failures.

Marker: NOTIFICATION SENT

## Reporting Workflow

Generated report types:
- Daily Report
- Weekly Report
- Alarm Summary
- Availability Report
- Performance Report

Marker: REPORT GENERATED

## Automation and Recovery

Recovery scenarios:
- Interface Down -> Auto Reset Interface
- Modem Offline -> Auto Restart Modem
- High Temperature -> Generate Critical Alarm
- Low Signal -> Trigger Recovery Action

Marker: AUTO RECOVERY COMPLETE

## NOC Control Center

Operational command-center view includes:
- Network Overview
- Alarm Overview
- Control Overview
- Notification Overview
- Report Overview
- Hub Availability
- NOC Health Score

Marker: NOC CONTROL CENTER

## REST APIs

GET endpoints:
- /api/devices
- /api/devices/{id}
- /api/devices/health
- /api/metrics
- /api/alarms
- /api/dashboard
- /api/reports
- /api/notifications
- /api/noc

POST endpoints:
- /api/control/reboot/{id}
- /api/control/reset-interface/{id}
- /api/control/enable-port/{id}
- /api/control/disable-port/{id}
- /api/control/change-config/{id}
- /api/alarms/{id}/acknowledge
- /api/automation/recover/{deviceId}

## Running

From Day-18 root:

```bash
mvn clean test
mvn spring-boot:run
./scripts/run_all_tests.sh
```

## Diagrams

- diagrams/hub-monitoring-architecture.mmd
- diagrams/device-control-flow.mmd
- diagrams/alarm-lifecycle.mmd
- diagrams/notification-workflow.mmd
- diagrams/dashboard-architecture.mmd
- diagrams/reporting-workflow.mmd
- diagrams/hub-operation-flow.mmd
