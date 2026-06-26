# Exercise 07 - Graceful Shutdown and Reliability

## Objective

Validate graceful shutdown behavior during controlled container termination.

## Architecture

- Spring lifecycle shutdown timeout
- Docker stop sending SIGTERM
- Compose restart policy for service reliability

## Steps

1. Start stack.
2. Generate API traffic.
3. Stop spring-app container.
4. Inspect shutdown logs and restart behavior.

## Expected Output

- App exits cleanly without abrupt termination errors.
- Logs indicate graceful shutdown path.

## Solution

```bash
docker compose up -d --build
curl -s http://localhost:8080/api/devices
docker stop day25-spring-app
docker logs day25-spring-app | tail -n 80
docker compose up -d spring-app
docker compose down
```

## Learning Outcome

You understand reliability behavior during stop/restart operations in containerized Java services.
