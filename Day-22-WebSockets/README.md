# Day 22 - WebSockets in Enterprise Real-Time Monitoring

This module implements a production-style WebSocket platform for real-time network monitoring operations. Instead of a chat demo, it models how NOCs, telecom operations, satellite control rooms, IoT command centers, and trading-style dashboards deliver low-latency telemetry, alarm fan-out, and live operational state to many operators simultaneously.

## Learning Objectives
- Understand WebSocket protocol and HTTP upgrade handshake mechanics.
- Build STOMP-based publish/subscribe pipelines with Spring WebSocket.
- Implement SockJS fallback for client compatibility in restricted networks.
- Model real-world telemetry, alarming, and operator client sessions.
- Compare pull-based REST polling vs push-based WebSocket streaming.
- Integrate observability counters for session/message/broadcast tracking.
- Validate real-time flows with integration tests and automation scripts.

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Web
- Spring WebSocket
- STOMP
- SockJS
- Spring Data JPA
- H2 Database
- Jakarta Validation
- Lombok
- Spring Boot Actuator
- JUnit 5
- MockMvc / TestRestTemplate style integration testing

## Why WebSocket for NOC Platforms?
In operational systems, waiting for periodic polling creates blind spots. WebSockets eliminate request churn and enable push-first behavior:
- Telemetry packets stream continuously.
- Alarm conditions are broadcast instantly.
- Dashboards refresh in-place without full page reloads.
- Operator presence is tracked in near real time.

## HTTP vs WebSocket
| Property | HTTP | WebSocket |
| --- | --- | --- |
| Communication model | Request/response | Full-duplex persistent channel |
| Connection lifecycle | Short lived per request | Long-lived upgraded connection |
| Update strategy | Polling or long-polling | Server push + client push |
| Latency for new event | Depends on polling interval | Near-immediate |
| Bandwidth overhead | Repeated headers | Minimal framing after handshake |

## What is STOMP?
STOMP (Simple Text Oriented Messaging Protocol) is an application-level messaging protocol layered over WebSocket. It provides destination semantics:
- Clients publish to application destinations such as `/app/connect`.
- Server broadcasts to broker topics such as `/topic/alarms`.
- Subscriptions are decoupled from producers, enabling scalable fan-out.

## Why SockJS?
SockJS provides fallback transports when direct WebSocket is blocked by old proxies, strict enterprise gateways, or browser limitations. This project exposes native endpoint and SockJS endpoint for compatibility:
- Native: `/ws-monitoring`
- SockJS fallback: `/ws-monitoring-sockjs`

## Handshake Process Summary
1. Client sends HTTP request with WebSocket upgrade headers.
2. Server responds with `101 Switching Protocols`.
3. Connection becomes persistent and bidirectional.
4. STOMP `CONNECT`, `SUBSCRIBE`, and `SEND` frames manage messaging semantics.
5. Session disconnect events update client lifecycle metrics.

## Architecture Overview
- Telemetry producer pipeline generates periodic metrics and persists them.
- Alarm engine evaluates thresholds and generates operational alarms.
- Dashboard aggregator computes real-time summary KPIs.
- WebSocket broker broadcasts telemetry, alarms, dashboard, and client session events.
- REST APIs expose query/state/acknowledgement operations.
- Connection metrics service tracks active sessions, total connections, sent/received messages, and broadcast volume.

## Broadcast vs Unicast in Operations
- Broadcast (Topic): Used for alarms, telemetry streams, and dashboard snapshots for all subscribed operators.
- Unicast (User queue): Used in secure systems for operator-specific instructions, role-based visibility, and private acknowledgements.

This module focuses on broadcast-centric NOC workflows while maintaining a design that can be extended to user-scoped destinations.

## Project Structure
- `src/main/java/com/sky2dev/day22/controller` - REST API controllers
- `src/main/java/com/sky2dev/day22/websocket` - STOMP controllers and session listeners
- `src/main/java/com/sky2dev/day22/service` - business logic services
- `src/main/java/com/sky2dev/day22/repository` - JPA repositories
- `src/main/java/com/sky2dev/day22/entity` - JPA entities and enums
- `src/main/java/com/sky2dev/day22/dto` - API and messaging DTOs
- `src/main/java/com/sky2dev/day22/config` - seed data and websocket config
- `src/main/java/com/sky2dev/day22/exception` - global exception handling
- `src/main/java/com/sky2dev/day22/scheduler` - telemetry/alarm/dashboard jobs
- `src/main/java/com/sky2dev/day22/monitoring` - connection metrics service

## Domain Model
- Device: identity, network address, type, and status
- TelemetryMetric: CPU, memory, temperature, signal strength, timestamp
- AlarmEvent: severity, message, acknowledgement state, timestamp
- ConnectedClient: username, session ID, connected time, active state

## Device Types
- ROUTER
- SWITCH
- MODEM
- HUB
- BUC
- LNB
- SERVER
- SENSOR

## WebSocket Contract
### STOMP Endpoint
- `/ws-monitoring`

### Broker Topics
- `/topic/telemetry`
- `/topic/alarms`
- `/topic/dashboard`
- `/topic/clients`

### Application Destinations
- `/app/connect`
- `/app/acknowledge`
- `/app/request-dashboard`

## Real-Time Features
1. Live telemetry streaming (CPU, memory, temperature, signal strength)
2. Live alarm broadcasting (warning, major, critical, offline)
3. Dashboard auto-refresh without page reload
4. Connected client session tracking and lifecycle events

## Alarm Rules
- CPU > 80%
- Memory > 85%
- Temperature > 70 C
- Signal strength < -80 dBm
- Device OFFLINE

Severity levels:
- WARNING
- MAJOR
- CRITICAL

## REST APIs
- `GET /api/platform`
- `GET /api/devices`
- `GET /api/telemetry`
- `GET /api/alarms`
- `GET /api/dashboard`
- `GET /api/clients`
- `POST /api/alarms/{id}/acknowledge`

## Response Wrapper
All REST and WebSocket payloads use `ApiResponse<T>` with:
- success
- marker
- message
- data
- timestamp

## Required Markers
- WEBSOCKET CONNECTED
- TELEMETRY STREAM ACTIVE
- ALARM BROADCASTED
- DASHBOARD UPDATED
- CLIENT CONNECTED
- CLIENT DISCONNECTED
- ALARM ACKNOWLEDGED
- REALTIME MONITORING ACTIVE

## Deterministic Seed Data
At startup, the project loads:
- 10 devices
- 100 telemetry records
- 10 alarm events
- 5 connected clients

## Observability
`ConnectionMetricsService` tracks:
- active sessions
- total connections
- messages sent
- messages received
- broadcast count

Dashboard API includes these counters in the returned payload.

## Testing Strategy
- Seed determinism test verifies exact startup data counts.
- REST integration test verifies endpoint markers and alarm acknowledgement.
- WebSocket handshake test verifies STOMP connection path.
- WebSocket broadcast test verifies topic subscriptions and telemetry/alarm pushes.

## Run Commands
```bash
cd Day-22-WebSockets
mvn clean test
```

Run automation:
```bash
bash scripts/run_all_tests.sh
```

## Scaling WebSockets in Enterprise Systems
For large fleets and multi-region operations, production deployments typically add:
- External broker relay (RabbitMQ/Kafka/STOMP broker) instead of in-memory simple broker
- Horizontal pods with sticky sessions or distributed session routing
- Backpressure and message sampling for burst control
- Topic partitioning by region/domain/team
- AuthN/AuthZ with fine-grained subscription policies
- TLS termination and gateway-based connection management

## NOC, Telecom, and SatCom Use Cases
- NOC wallboard with instant critical alarm overlays
- Teleport earth-station modem and RF chain health tracking
- Hub and gateway network incident operations
- Remote site telemetry fan-in for VSAT and edge systems
- Multi-tenant monitoring for service provider operations centers
- Real-time incident bridges for cross-team response coordination

## Diagrams
See the `diagrams/` folder for 7 Mermaid architecture diagrams:
1. WebSocket Handshake
2. STOMP Architecture
3. Telemetry Streaming Flow
4. Alarm Broadcast Flow
5. Dashboard Update Flow
6. Connected Client Lifecycle
7. Real-Time Monitoring Architecture

## Exercises
- Exercise-01-WebSocket-Handshake
- Exercise-02-STOMP-Topics
- Exercise-03-Live-Telemetry-Streaming
- Exercise-04-Alarm-Broadcasting
- Exercise-05-Dashboard-Auto-Refresh
- Exercise-06-Client-Lifecycle-Tracking
- Exercise-07-Alarm-Acknowledgement-Workflow
- Exercise-08-Scaling-And-Resilience
