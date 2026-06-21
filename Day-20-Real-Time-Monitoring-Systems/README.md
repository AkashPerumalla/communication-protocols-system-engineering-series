# Day 20 - Real-Time Monitoring Systems

This project builds a realistic Spring Boot monitoring platform that behaves like a simplified Prometheus, Grafana, AlertManager, and NOC dashboard stack. It continuously collects telemetry, stores time-series-style metrics, evaluates alert thresholds, sends simulated notifications, and pushes live dashboard updates over WebSocket.

## Learning Objectives
- Understand real-time monitoring architecture in enterprise systems
- Learn agent-to-server telemetry collection workflows
- Model time-series style metrics and dashboard snapshots
- Implement threshold-based alerting with severity levels
- Simulate notification routing to multiple operational channels
- Push live monitoring data through WebSocket topics
- Understand SNMP monitoring concepts and OID-based collection
- Connect observability fundamentals to NOC monitoring workflows

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- Spring Scheduling
- Spring WebSocket
- H2 Database
- Lombok
- Jakarta Validation
- Maven

## Monitoring Architecture

Agent
-> Monitoring API
-> Metric Storage
-> Alert Engine
-> Dashboard Engine
-> WebSocket Gateway
-> Reporting Layer

The application implements this flow using deterministic seed data plus scheduled collection cycles every five seconds.

## Project Layout
- `src/main/java/com/sky2dev/day20/controller` - REST APIs for agents, metrics, alerts, notifications, dashboard, reports, stream status, and SNMP
- `src/main/java/com/sky2dev/day20/service` - business logic for collection, evaluation, aggregation, reporting, and streaming
- `src/main/java/com/sky2dev/day20/entity` - JPA entities and enums for the monitoring domain
- `src/main/java/com/sky2dev/day20/repository` - Spring Data JPA repositories
- `src/main/java/com/sky2dev/day20/config` - seed data and WebSocket broker configuration
- `src/main/java/com/sky2dev/day20/exception` - global exception handling
- `src/test/java/com/sky2dev/day20` - integration and determinism tests

## Monitoring Domain Model
- MonitoringAgent: agent identity, host metadata, status, and heartbeat time
- MetricRecord: CPU, memory, disk, network, processes, and uptime snapshots
- AlertRule: metric comparator, threshold, severity, and enablement
- AlertEvent: generated operational alerts with status and timestamps
- NotificationEvent: outbound operational notifications across channels
- DashboardSnapshot: aggregate dashboard state for NOC views

## Metrics Flow
1. Agents register with the monitoring API.
2. The scheduler simulates metric collection every five seconds.
3. MetricRecord entities are persisted in H2.
4. Latest metrics are aggregated for dashboards and SNMP views.
5. WebSocket topics publish metrics to subscribers.

## Alert Flow
1. Alert rules define thresholds such as CPU > 80 or Disk > 90.
2. The alert engine evaluates the latest metric per agent.
3. Active alerts are created with LOW, MEDIUM, HIGH, or CRITICAL severity.
4. Notifications can be sent through EMAIL, SMS, WEBHOOK, SLACK, or TEAMS.
5. Alert status is exposed to the NOC dashboard and WebSocket topics.

## Dashboard Workflow
1. Dashboard service calculates active agents, active alerts, critical alerts, average CPU, average memory, and average disk.
2. The latest dashboard snapshot is stored for deterministic retrieval.
3. Clients retrieve `/api/dashboard` or subscribe to `/topic/dashboard`.

## WebSocket Workflow
- Endpoint: `/ws-monitoring`
- Topics:
  - `/topic/metrics`
  - `/topic/alerts`
  - `/topic/dashboard`

The WebSocket streaming service broadcasts metrics, active alerts, and dashboard snapshots every few seconds.

## SNMP Workflow
- Simulated OIDs:
  - System Uptime
  - CPU Usage
  - Memory Usage
  - Interface In
  - Interface Out

The SNMP service translates the latest metric snapshot for each agent into OID-style outputs so learners can connect application monitoring to classical network monitoring approaches.

## Observability Concepts Covered
- Metrics vs state snapshots
- Time-series data handling basics
- Threshold-based alerting
- Notification fan-out
- NOC dashboard summarization
- Agent heartbeat and liveness
- Real-time push vs polling
- SNMP integration concepts

## Deterministic Seed Data
- 5 agents: `agent-01` through `agent-05`
- 50 metric records
- 10 alert events
- 20 notification events
- Healthy, warning, and critical patterns included

## REST APIs

GET endpoints:
- `/api/agents`
- `/api/metrics`
- `/api/alerts`
- `/api/alerts/active`
- `/api/notifications`
- `/api/dashboard`
- `/api/reports`
- `/api/snmp`
- `/api/stream/status`

POST endpoints:
- `/api/agents/register`
- `/api/agents/heartbeat`
- `/api/alerts/evaluate`
- `/api/notifications/send`
- `/api/reports/generate`

## Exercises
1. Exercise-01-Build-Realtime-Monitoring-System - Build the core monitoring platform and data flow.
2. Exercise-02-Implement-Alerting-System - Evaluate thresholds and create alert events.
3. Exercise-03-TimeSeries-Metrics-Storage - Persist and query metric records for operational visibility.
4. Exercise-04-Metric-Collection-Agent - Simulate agent registration, heartbeat, and collection cycles.
5. Exercise-05-Realtime-Dashboard - Aggregate NOC dashboard state and stream live updates.
6. Exercise-06-SNMP-Monitoring - Simulate SNMP OID views for infrastructure monitoring.

## Running Instructions

```bash
cd Day-20-Real-Time-Monitoring-Systems
bash scripts/run_all_tests.sh
```

Default port: `8086`

## Expected Markers
- AGENT REGISTERED
- METRICS COLLECTED
- ALERT GENERATED
- NOTIFICATION SENT
- DASHBOARD UPDATED
- REALTIME STREAM ACTIVE
- SNMP MONITORING ACTIVE
- REPORT GENERATED

## Interview Preparation
See `interview-questions.md` for 50+ monitoring, alerting, SNMP, WebSocket, distributed monitoring, and scalability questions.

## Key Takeaways
- Real-time monitoring is a pipeline, not a CRUD workflow.
- Dashboard state is derived from time-series metrics and alert state.
- Alert noise control matters in production-style monitoring systems.
- WebSocket streaming complements REST APIs for operational visibility.
- SNMP concepts still matter when bridging app observability and network operations.# Day 20 - Real-Time Monitoring Systems

This module builds a production-style real-time monitoring platform using Spring Boot. It simulates an enterprise environment inspired by Prometheus, Grafana, AlertManager, and NOC dashboard workflows.

## Learning Objectives
- Understand real-time monitoring architecture and control planes.
- Implement agent-server communication and heartbeat workflows.
- Store and query time-series style metric data.
- Build deterministic threshold-based alerting.
- Design notification fan-out channels for NOC teams.
- Stream live updates through WebSocket topics.
- Simulate SNMP OID polling in monitoring pipelines.
- Generate operational reports for daily workflows.

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- Spring Scheduling
- Spring WebSocket (STOMP broker)
- H2 Database
- Lombok
- Jakarta Validation
- Maven

## Monitoring Architecture

Agent
→ Monitoring API
→ Metric Storage
→ Alert Engine / Dashboard Engine / WebSocket Gateway / Reporting Layer

See diagrams in diagrams/ for detailed flows.

## Project Layout
- src/main/java/com/sky2dev/day20/controller: REST APIs.
- src/main/java/com/sky2dev/day20/service: monitoring, alerting, reporting, streaming services.
- src/main/java/com/sky2dev/day20/entity: JPA entities and enums.
- src/main/java/com/sky2dev/day20/repository: persistence interfaces.
- src/main/java/com/sky2dev/day20/config: seed data and websocket config.
- src/main/java/com/sky2dev/day20/exception: global exception handling.
- src/main/java/com/sky2dev/day20/scheduler: scheduler components.
- src/test/java/com/sky2dev/day20: integration and deterministic seed tests.

## Metrics Flow
1. Agents register and send heartbeat.
2. MetricsCollectionService runs every 5 seconds.
3. Metric records are persisted in H2.
4. Latest metrics are aggregated for dashboard and SNMP views.

## Alert Flow
1. Alert rules define metric, comparator, threshold, and severity.
2. AlertEngine evaluates latest agent metrics periodically and on-demand.
3. Alert events are generated and deduplicated for active incidents.
4. Notification workflows can dispatch alert messages via multiple channels.

## Dashboard Workflow
1. DashboardService computes active agents and active/critical alerts.
2. Dashboard snapshot stores average CPU, memory, disk.
3. NOC clients query /api/dashboard for latest operational view.

## WebSocket Workflow
1. STOMP endpoint: /ws-monitoring
2. Topics:
   - /topic/metrics
   - /topic/alerts
   - /topic/dashboard
3. WebSocketStreamingService broadcasts updates every few seconds.

## SNMP Workflow
1. SNMPMonitoringService maps latest metrics to simulated OIDs.
2. OID outputs include system uptime, CPU, memory, interface in/out.
3. /api/snmp returns polling-style data for NMS/NOC integrations.

## Observability Concepts Covered
- Metric collection and trend visibility
- Alert thresholds and severity mapping
- Event correlation for NOC triage
- Real-time push channels for dashboard clients
- Deterministic simulation for repeatable testing

## REST APIs

GET:
- /api/agents
- /api/metrics
- /api/alerts
- /api/alerts/active
- /api/notifications
- /api/dashboard
- /api/reports
- /api/snmp

POST:
- /api/agents/register
- /api/agents/heartbeat
- /api/alerts/evaluate
- /api/notifications/send
- /api/reports/generate

Additional verification endpoint:
- /api/stream/status

## Exercises
- Exercise-01-Build-Realtime-Monitoring-System
- Exercise-02-Implement-Alerting-System
- Exercise-03-TimeSeries-Metrics-Storage
- Exercise-04-Metric-Collection-Agent
- Exercise-05-Realtime-Dashboard
- Exercise-06-SNMP-Monitoring

## Run Instructions

```bash
mvn clean test
bash scripts/run_all_tests.sh
```

Server:
- Default port: 8086
- H2 console: /h2-console

## Deterministic Markers
- AGENT REGISTERED
- METRICS COLLECTED
- ALERT GENERATED
- NOTIFICATION SENT
- DASHBOARD UPDATED
- REALTIME STREAM ACTIVE
- SNMP MONITORING ACTIVE
- REPORT GENERATED

## Interview Preparation
See interview-questions.md for 50+ beginner-to-advanced questions.

## Key Takeaways
- Real-time monitoring systems require both pull (REST) and push (WebSocket) patterns.
- Deterministic simulation is effective for testing monitoring pipelines.
- Alert deduplication and severity modeling are essential for NOC usability.
- Dashboard snapshots are useful for low-latency operational summaries.
