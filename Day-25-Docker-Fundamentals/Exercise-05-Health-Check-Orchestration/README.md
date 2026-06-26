# Exercise 05 - Health Check Orchestration

## Objective

Observe container health transitions and dependency gating during startup.

## Architecture

- MySQL healthcheck using mysqladmin ping
- Spring healthcheck using actuator endpoint
- Compose dependency condition on healthy DB

## Steps

1. Start stack and tail compose status.
2. Observe health status transitions.
3. Confirm app starts only after DB health.
4. Trigger API smoke call after healthy state.

## Expected Output

- mysql transitions to healthy before spring-app becomes healthy.
- API calls succeed only after healthy state.

## Solution

```bash
docker compose up -d --build
watch -n 2 docker compose ps
docker inspect --format='{{.State.Health.Status}}' day25-mysql
docker inspect --format='{{.State.Health.Status}}' day25-spring-app
curl -s http://localhost:8080/actuator/health
docker compose down
```

## Learning Outcome

You understand readiness orchestration and how health checks prevent race-condition failures.
