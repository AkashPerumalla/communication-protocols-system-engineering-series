# Exercise 06 - Container Logging and Observability

## Objective

Use Docker log streams and actuator endpoints for runtime observability.

## Architecture

- Console-only structured timestamp logging
- Actuator health/info endpoints
- Service-scoped log inspection via Compose

## Steps

1. Start stack.
2. Stream spring-app logs.
3. Call APIs to generate traffic.
4. Validate logs and actuator metadata.

## Expected Output

- Timestamped logs in docker compose logs output.
- Health and info endpoints return expected metadata.

## Solution

```bash
docker compose up -d --build
docker compose logs -f spring-app
curl -s http://localhost:8080/api/dashboard
curl -s http://localhost:8080/actuator/health
curl -s http://localhost:8080/actuator/info
docker compose down
```

## Learning Outcome

You can use container-native logging and actuator telemetry for operational diagnostics.
