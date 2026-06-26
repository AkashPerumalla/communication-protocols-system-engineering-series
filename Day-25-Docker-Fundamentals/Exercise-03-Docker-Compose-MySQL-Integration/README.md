# Exercise 03 - Docker Compose MySQL Integration

## Objective

Run the complete stack (spring-app + mysql) with docker profile and verify DB-backed execution.

## Architecture

- mysql service with named volume
- spring-app service with depends_on health gating
- bridge network for service name resolution

## Steps

1. Start compose stack.
2. Wait for both services healthy.
3. Validate platform status database mode.
4. List devices and reports.

## Expected Output

- mysql and spring-app are healthy.
- Platform status reports databaseMode as MYSQL.
- API responses include required markers.

## Solution

```bash
docker compose up -d --build
docker compose ps
curl -s http://localhost:8080/api/platform/status
curl -s http://localhost:8080/api/devices
curl -s http://localhost:8080/api/reports
docker compose down
```

## Learning Outcome

You can orchestrate stateful and stateless services with Compose and validate cross-container connectivity.
