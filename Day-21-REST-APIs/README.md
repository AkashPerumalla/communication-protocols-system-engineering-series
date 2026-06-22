# Day-21 REST APIs

A production-style Spring Boot device management API platform that models how NOC, telecom, SatCom, OSS/BSS, and enterprise backend teams design, validate, secure, monitor, audit, and consume REST APIs.

## REST API Fundamentals

REST is an architectural style for networked applications where resources are identified by URIs and manipulated through standard HTTP methods. In this module, devices, metrics, dashboard snapshots, and audit logs are treated as resources rather than remote procedure calls.

Core REST properties used here:
- Client-server separation
- Stateless request handling
- Uniform interface through HTTP
- Cache-aware response semantics
- Resource-oriented URI design
- Layered backend architecture

## HTTP Methods

- `GET`: Retrieve resource representations without mutating state.
- `POST`: Create new resources or trigger well-defined workflows.
- `PUT`: Replace or update a known resource.
- `DELETE`: Remove a resource and its dependent operational data.

## Status Codes

- `200 OK`: Successful retrieval, update, or deletion.
- `201 Created`: Successful resource creation.
- `400 Bad Request`: Validation or request-shape failure.
- `401 Unauthorized`: Missing or invalid credentials.
- `403 Forbidden`: Authenticated but not allowed for the operation.
- `404 Not Found`: Resource ID does not exist.
- `500 Internal Server Error`: Unexpected backend failure.

## Request/Response Lifecycle

1. Client sends an HTTP request with credentials and JSON payload if needed.
2. Spring Security authenticates the caller and enforces endpoint roles.
3. Bean Validation checks request fields before service execution.
4. Controllers delegate to the service layer.
5. Services apply inventory rules, persistence operations, and aggregation logic.
6. Repositories read or write state in H2 through Spring Data JPA.
7. Responses are wrapped in `ApiResponse<T>` for consistent markers and timestamps.
8. The audit interceptor records endpoint, method, response code, and execution time.

## Resource-Oriented Architecture

This module models the following primary resources:
- `Device`: Managed network, SatCom, and IoT inventory asset.
- `DeviceMetric`: Operational telemetry associated with a device.
- `ApiAuditLog`: Immutable record of API access and execution behavior.
- `Dashboard`: Aggregated operational view derived from devices and metrics.

## REST Best Practices Used

- Use plural nouns for resource collections.
- Use path parameters only for resource identity and categorical filters.
- Keep business logic out of controllers.
- Enforce validation close to the API boundary.
- Return stable response envelopes for both success and failure.
- Separate persistence entities from transport DTOs.
- Apply role-based access controls by HTTP method and endpoint sensitivity.
- Persist audit trails without polluting business controllers.
- Keep startup data deterministic for repeatable testing and teaching.

## API Versioning

This module does not expose `/v1` in the URI to keep the learning surface small, but the production recommendation is to version external APIs using one of these patterns:
- URI versioning: `/api/v1/devices`
- Header-based versioning for controlled clients
- Media-type versioning for large ecosystems

For enterprise systems, versioning should be planned before public client adoption because backward compatibility costs grow quickly once multiple consumers rely on the API.

## Validation

Validation is enforced with:
- `@NotBlank` for required text fields such as hostname and location
- `@NotNull` for enums such as device type and status
- `@Pattern` for IPv4 address validation
- `@Valid` on controller request bodies

The create and update flows intentionally surface `VALIDATION PASSED` in successful responses so the smoke harness can verify both the positive and negative validation paths.

## Error Handling

`GlobalExceptionHandler` normalizes API failures into structured JSON instead of Spring's default error payload. It handles:
- `ValidationException`
- `EntityNotFoundException`
- `MethodArgumentNotValidException`
- `IllegalArgumentException`
- fallback `Exception`

Each error response includes:
- `success=false`
- failure marker
- human-readable message
- structured error payload
- response timestamp

## Security Model

The platform uses Spring Security with HTTP Basic authentication and role-based authorization.

Roles:
- `VIEWER`: Read inventory, metrics, and dashboard endpoints.
- `OPERATOR`: Create and update devices.
- `ADMIN`: Delete devices and retrieve audit logs.

Default lab credentials:
- `viewer / viewer123`
- `operator / operator123`
- `admin / admin123`

In real NOC environments, these credentials would be externalized to identity infrastructure such as LDAP, SSO, IAM, or OAuth2/OIDC.

## Observability And Monitoring

Operational visibility is exposed through:
- `Spring Boot Actuator` health and info endpoints
- persisted `ApiAuditLog` entries
- structured SLF4J logging
- Swagger/OpenAPI documentation for consumer visibility

## Technology Stack

- Java 17
- Spring Boot 3.3.x
- Maven
- Spring Web
- Spring Data JPA
- H2 Database
- Spring Validation
- Lombok
- Spring Actuator
- Spring Security
- OpenAPI / Swagger
- JUnit 5
- MockMvc
- Jackson
- SLF4J Logging

## Project Layout

- `src/main/java/com/sky2dev/day21/controller`: REST entry points
- `src/main/java/com/sky2dev/day21/service`: business workflows and aggregation logic
- `src/main/java/com/sky2dev/day21/repository`: JPA persistence interfaces
- `src/main/java/com/sky2dev/day21/entity`: JPA entities and enums
- `src/main/java/com/sky2dev/day21/dto`: request and response contracts
- `src/main/java/com/sky2dev/day21/mapper`: entity to DTO conversions
- `src/main/java/com/sky2dev/day21/config`: security, seed, MVC, and OpenAPI config
- `src/main/java/com/sky2dev/day21/exception`: global exception handling
- `src/main/java/com/sky2dev/day21/validation`: reusable validation constants
- `src/main/java/com/sky2dev/day21/audit`: API audit interceptor
- `src/test/java/com/sky2dev/day21`: MockMvc integration tests
- `scripts/run_all_tests.sh`: smoke validation harness

## Domain Model

### Device
- `id`
- `hostname`
- `ipAddress`
- `deviceType`
- `vendor`
- `model`
- `firmwareVersion`
- `location`
- `status`
- `createdAt`
- `updatedAt`

### DeviceMetric
- `id`
- `deviceId`
- `cpuUsage`
- `memoryUsage`
- `temperature`
- `signalStrength`
- `timestamp`

### ApiAuditLog
- `id`
- `endpoint`
- `method`
- `responseCode`
- `executionTime`
- `timestamp`

## Deterministic Seed Data

The application starts with exactly:
- 10 devices
- 50 metrics

Seeded devices:
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

## REST APIs

### Platform
- `GET /api/platform`

### Devices
- `GET /api/devices`
- `GET /api/devices/{id}`
- `POST /api/devices`
- `PUT /api/devices/{id}`
- `DELETE /api/devices/{id}`
- `GET /api/devices/search`
- `GET /api/devices/status/{status}`
- `GET /api/devices/type/{type}`
- `GET /api/devices/location/{location}`

### Metrics
- `GET /api/metrics`
- `GET /api/metrics/device/{id}`

### Dashboard
- `GET /api/dashboard`

### Audit
- `GET /api/audit`

## Markers

Required operational markers:
- `DEVICE CREATED`
- `DEVICE UPDATED`
- `DEVICE DELETED`
- `DEVICE RETRIEVED`
- `DEVICE SEARCH COMPLETE`
- `METRICS RETRIEVED`
- `DASHBOARD GENERATED`
- `AUDIT LOG GENERATED`
- `VALIDATION PASSED`
- `API PLATFORM ACTIVE`

## Swagger And API Documentation

Swagger UI:
- `/swagger-ui.html`

OpenAPI JSON:
- `/v3/api-docs`

## Running The Project

Compile and test:
```bash
mvn -q clean test
```

Start the app:
```bash
mvn spring-boot:run
```

Run the full smoke harness:
```bash
scripts/run_all_tests.sh
```

## Practical Exercises

- Exercise-01 Device Inventory Overview
- Exercise-02 Secure Device Creation
- Exercise-03 Device Update And Deletion
- Exercise-04 Search And Filtering
- Exercise-05 Metrics Consumption
- Exercise-06 Dashboard Operations
- Exercise-07 Audit Verification
- Exercise-08 Validation And Error Handling

## Enterprise Takeaways

This module shows that a realistic REST backend is more than CRUD. It includes:
- contract design
- validation
- access control
- operational markers
- deterministic testability
- auditability
- observability
- documentation for API consumers
