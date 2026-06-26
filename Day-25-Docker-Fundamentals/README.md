# Day-25 Docker Fundamentals - Enterprise Device Monitoring Platform

This module builds a production-style Dockerized Spring Boot backend for Telecom, NOC, SatCom, and enterprise operations.

The platform manages device inventory, telemetry, alarms, notifications, dashboards, and reports while demonstrating real-world Docker practices such as multi-stage image builds, profile-driven configuration, health checks, and service orchestration with Docker Compose.

## Learning Objectives

- Dockerize a layered Spring Boot application for production-style deployment.
- Use multi-stage Docker builds for smaller runtime images.
- Orchestrate Spring Boot and MySQL with Docker Compose bridge networking.
- Configure environment-variable-driven runtime settings.
- Implement container health checks and startup dependency ordering.
- Expose operational endpoints through Spring Actuator.
- Validate logging, graceful shutdown, and smoke-test automation.

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Maven
- Docker
- Docker Compose
- H2 (development profile)
- MySQL 8 (docker profile)
- Spring Data JPA
- Spring Validation
- Spring Actuator
- Lombok

## Application Domain

Telecom and NOC device monitoring simulation with deterministic startup data and standardized API responses.

## Layered Architecture

- src/main/java/com/sky2dev/day25/controller - REST API endpoints
- src/main/java/com/sky2dev/day25/service - business logic
- src/main/java/com/sky2dev/day25/repository - JPA repositories
- src/main/java/com/sky2dev/day25/entity - domain entities
- src/main/java/com/sky2dev/day25/dto - request/response contracts
- src/main/java/com/sky2dev/day25/config - seed data and app config
- src/main/java/com/sky2dev/day25/exception - global exception handling
- src/main/java/com/sky2dev/day25/util - marker constants

## Domain Model

- Device
- TelemetryRecord
- AlarmEvent
- NotificationEvent
- ReportRecord

## Deterministic Seed Data

Startup seed data is deterministic and provides:

- 10 Devices
- 100 Telemetry Records
- 20 Alarm Events
- 20 Notifications
- 10 Reports

Device names:

- Router-01
- Router-02
- Switch-01
- Switch-02
- Satellite-Modem-01
- Satellite-Modem-02
- Hub-01
- BUC-01
- LNB-01
- Sensor-01

## API Endpoints

- GET /api/devices
- GET /api/telemetry
- GET /api/alarms
- GET /api/dashboard
- GET /api/reports
- GET /api/notifications
- POST /api/devices
- PUT /api/devices/{id}
- DELETE /api/devices/{id}
- GET /api/platform/status

## Standard API Response

All APIs return ApiResponse<T> with:

- success
- marker
- message
- data
- timestamp

Markers used in this project:

- APPLICATION RUNNING
- DEVICE CREATED
- TELEMETRY COLLECTED
- ALARM GENERATED
- NOTIFICATION SENT
- REPORT GENERATED
- DASHBOARD UPDATED
- DOCKER READY

## Spring Profiles and Configuration

### application.yml (default profile)

- H2 in-memory datasource
- Actuator health/info exposure
- console-only timestamped logging
- graceful shutdown timeout

### application-docker.yml (docker profile)

- MySQL datasource
- MySQL dialect
- H2 console disabled

## Actuator and Health

Actuator endpoints:

- /actuator/health
- /actuator/info

Docker Compose health checks:

- MySQL health via mysqladmin ping
- Spring app health via /actuator/health

Startup ordering:

- spring-app depends_on mysql with condition: service_healthy

## Dockerfile Strategy

The Dockerfile uses a multi-stage build:

1. Builder stage: eclipse-temurin:17-jdk
1. Runtime stage: eclipse-temurin:17-jre

Production optimizations included:

- dependency caching via separate pom.xml copy and go-offline step
- cached Maven repository mount
- runtime image contains only app.jar and JRE

Runtime:

- EXPOSE 8080
- ENTRYPOINT java -jar app.jar

## .dockerignore

Excludes non-runtime build context:

- target
- .git
- .idea
- .vscode
- logs

## Docker Compose Architecture

Services:

- mysql
- spring-app

Networking:

- bridge network day25-net

Persistence:

- named volume day25_mysql_data

Environment variables externalized:

- MYSQL_DATABASE
- MYSQL_ROOT_PASSWORD
- MYSQL_USER
- MYSQL_PASSWORD
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SERVER_PORT
- SPRING_PROFILES_ACTIVE

## Build and Run

### Local development (H2)

```bash
mvn clean test
mvn spring-boot:run
```

### Docker image build

```bash
docker build -t day25-device-monitoring:latest .
```

### Docker Compose stack

```bash
docker compose up -d --build
curl -s http://localhost:8080/actuator/health
curl -s http://localhost:8080/api/platform/status
```

### Shutdown

```bash
docker compose down
```

## Command Guide

### Docker Core Commands

```bash
docker build -t day25-device-monitoring:latest .
docker images | grep day25-device-monitoring
docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=default day25-device-monitoring:latest
docker ps
docker logs day25-spring-app
docker exec -it day25-spring-app sh
docker stop day25-spring-app
docker rm day25-spring-app
docker rmi day25-device-monitoring:latest
```

### Docker Compose Commands

```bash
docker compose up -d --build
docker compose ps
docker compose logs -f
docker compose logs -f spring-app
docker compose down
docker compose down -v
```

Legacy command equivalents:

```bash
docker-compose up -d
docker-compose logs
docker-compose down
```

## Logging

- Console-only logging configured with timestamps.
- No file appender configured.
- Container logs are accessible through docker logs and docker compose logs.

## Graceful Shutdown

- spring.lifecycle.timeout-per-shutdown-phase set to 30s.
- container stop propagates SIGTERM allowing in-flight work to complete cleanly.

## Testing and Automation

Integration tests validate:

- context loads
- deterministic seed counts
- REST marker contracts
- platform status endpoint

Automation script:

```bash
./scripts/run_all_tests.sh
```

Script flow:

1. mvn clean test
1. docker build
1. docker compose up
1. wait for health
1. run API smoke tests
1. validate markers
1. docker compose down

## Volumes, Networks, and Containers Explained

- Images are immutable artifacts built from Dockerfile.
- Containers are runtime instances of images.
- Volumes provide persistent storage (MySQL data survives restarts).
- Networks provide isolated service communication (spring-app reaches mysql by service name).

## Production Best Practices Demonstrated

- Multi-stage image builds
- Runtime-only base image
- Environment variable externalization
- Profile-based database switching
- Container health checks
- Service dependency gating
- Deterministic startup seed data for repeatable validation
- Console-first structured logging
- Automation script with cleanup trap
- Named volume for stateful database persistence

## Practical Exercises

- Exercise-01-Containerized-Spring-Boot-Basics
- Exercise-02-Multi-Stage-Build-Optimization
- Exercise-03-Docker-Compose-MySQL-Integration
- Exercise-04-Environment-Variable-Management
- Exercise-05-Health-Check-Orchestration
- Exercise-06-Container-Logging-And-Observability
- Exercise-07-Graceful-Shutdown-And-Reliability
- Exercise-08-Production-Readiness-Validation

## Diagrams

See diagrams folder:

- docker-architecture.md
- docker-build-flow.md
- container-lifecycle.md
- docker-compose-architecture.md
- springboot-container-flow.md
- health-check-workflow.md
- enterprise-deployment-architecture.md

## Quick Validation

```bash
mvn clean test
./scripts/run_all_tests.sh
```

Expected final marker:

- PASS: Day-25 Docker platform validation completed successfully
