# Day 17 - Alarm Monitoring Systems

Day 17 teaches how real operations teams move from passive monitoring to active alarm management. A NOC, telecom operations center, satellite operations center, data center, SCADA room, or IoT monitoring team does not just collect metrics. It detects alarms, ranks them, routes them, acknowledges them, escalates them, correlates storms, and uses root cause analysis to collapse noise into one actionable incident.

This module is a production-style Spring Boot 3.3.x / Java 17 backend built with Maven, Spring Web, Spring Data JPA, and H2. It exposes REST APIs only. There is no frontend. The goal is to teach alarm lifecycle management the way real operations consoles behave.

## What This Module Teaches

- How alarm thresholds are defined and evaluated.
- How severity is assigned from telemetry conditions.
- How operators acknowledge, resolve, and close alarms.
- How escalations route unresolved incidents to the right team.
- How alarm storms are correlated into one incident.
- How root cause analysis reduces duplicate alerts.
- Why monitoring is not enough without alarm management.

## Monitoring vs Alarming

Monitoring answers: what is happening?

Alarming answers: what must the operator do now?

Monitoring can show CPU, temperature, BER, EbNo, interface state, and power levels. Alarm management turns those readings into an operational workflow with ownership, timing, priority, escalation, and closure.

## Technology Stack

- Java 17
- Spring Boot 3.3.x
- Spring Web
- Spring Data JPA
- H2 Database
- Maven
- No frontend

## Project Layout

- `src/main/java/com/sky2dev/day17/controller/` - REST APIs for devices, metrics, alarms, escalations, notifications, RCA, and dashboard.
- `src/main/java/com/sky2dev/day17/service/` - alarm rule evaluation, lifecycle management, notification delivery, escalation, RCA, correlation, and dashboard aggregation.
- `src/main/java/com/sky2dev/day17/model/` - alarm domain entities and enums.
- `src/main/java/com/sky2dev/day17/repository/` - Spring Data JPA repositories.
- `src/main/java/com/sky2dev/day17/dto/` - API response records.
- `src/main/java/com/sky2dev/day17/config/` - deterministic seed data.
- `src/main/java/com/sky2dev/day17/simulation/` - reserved for future deterministic storm generation and alarm load simulation.
- `scripts/run_all_tests.sh` - smoke harness that compiles and validates markers.

## Core Domain Model

The module centers on these entities:

- `ManagedDevice`
- `MetricRecord`
- `Alarm`
- `AlarmRule`
- `AlarmEvent`
- `AlarmAcknowledgement`
- `EscalationPolicy`
- `NotificationChannel`
- `AlarmCorrelation`
- `RootCause`
- `Operator`

## Alarm Severity Matrix

| Severity | Meaning | Typical Example |
|---|---|---|
| INFO | Informational only | Minor pre-failure condition |
| WARNING | Needs observation | Elevated CPU with no service impact |
| MINOR | Lower priority operational issue | Degraded link quality |
| MAJOR | Action needed soon | CPU above 80%, signal below threshold |
| CRITICAL | Immediate intervention | Power failure, BER above threshold, device unreachable |

## Alarm Lifecycle

1. `DETECTED` - a threshold is violated or a fault is observed.
2. `OPEN` - the alarm is active and visible to the NOC.
3. `ACKNOWLEDGED` - an operator has taken ownership.
4. `ESCALATED` - the alarm exceeded its target time or severity path.
5. `RESOLVED` - service is restored and the fault is no longer active.
6. `CLOSED` - the incident is administratively closed.

Each stage carries timestamps so operators can measure response times, escalation delays, and closure latency.

## Threshold Examples

- If CPU > 80% then raise a MAJOR alarm.
- If CPU > 95% then raise a CRITICAL alarm.
- If temperature > 70°C then raise a CRITICAL alarm.
- If signal strength < -80 dBm then raise a MAJOR alarm.
- If BER > threshold then raise a CRITICAL alarm.

## Alarm Correlation

Alarm correlation prevents duplicate operator pages. If one root cause produces many symptoms, the system groups them under one incident. This is critical in telecom outages, satellite downlinks, power events, data-center failures, and SCADA plant disturbances.

## Alarm Storm Handling

Alarm storms happen when one upstream fault generates dozens of downstream alarms. The system handles storms by:

- grouping alarms by correlation key,
- suppressing duplicate notifications,
- prioritizing the first root event,
- escalating only the meaningful incident,
- and preserving the full alarm history for analysis.

## Root Cause Analysis

The RCA engine identifies the source condition that best explains the alarm set.

Example: a power failure can produce device unreachable, interface down, and signal loss alarms. RCA should collapse those symptoms into the real root cause: Power Failure.

## NOC Workflow

1. A device produces telemetry or a fault indication.
2. A threshold rule detects a violation.
3. An alarm is generated and classified.
4. The alarm is acknowledged by an operator.
5. If it remains unresolved, it is escalated.
6. Correlated alarms are grouped into a single incident.
7. RCA identifies the likely root cause.
8. The alarm is resolved and closed.

## Real-World Scenarios

### Telecom NOC

- Interface down on a core router.
- High CPU on a packet gateway.
- Carrier unlock on a backhaul link.
- BER spike on a microwave hop.

### Satellite Operations Center

- EbNo drop during rain fade.
- BUC failure at the hub.
- LNB failure at a remote terminal.
- Device unreachable after a power event.

### Data Center

- Database down.
- Application down.
- Memory exhaustion.
- Cooling or power faults.

### Industrial SCADA

- Sensor link loss.
- PLC communication failure.
- Elevated temperature in a control cabinet.

## REST APIs

- `GET /api/devices`
- `GET /api/metrics`
- `GET /api/alarms`
- `GET /api/alarms/open`
- `GET /api/alarms/critical`
- `GET /api/alarms/unacknowledged`
- `POST /api/alarms/{id}/acknowledge`
- `POST /api/alarms/{id}/escalate`
- `POST /api/alarms/{id}/resolve`
- `POST /api/alarms/{id}/close`
- `GET /api/escalations`
- `GET /api/notifications`
- `GET /api/rca`
- `GET /api/dashboard`

## Example Smoke Checks

```bash
curl http://localhost:8083/api/metrics
curl http://localhost:8083/api/alarms
curl -X POST http://localhost:8083/api/alarms/1/acknowledge
curl http://localhost:8083/api/dashboard
```

## Expected Marker Strings

- `THRESHOLD DETECTED`
- `ALARM GENERATED`
- `SEVERITY ASSIGNED`
- `ALARM ACKNOWLEDGED`
- `ALARM ESCALATED`
- `ROOT CAUSE IDENTIFIED`
- `ALARM CORRELATED`
- `NOC ALARM DASHBOARD`

## Running Instructions

```bash
cd Day-17-Alarm-Monitoring-Systems
mvn test
scripts/run_all_tests.sh
```

## Learning Outcomes

- Recognize the difference between monitoring and alarming.
- Model an operational alarm lifecycle.
- Implement severity and escalation policies.
- Correlate noisy alarm storms into one incident.
- Design deterministic NOC-style REST APIs in Spring Boot.
