# Exercise 04 - Environment Variable Management

## Objective

Practice externalized runtime configuration for profile, credentials, datasource URL, and server port.

## Architecture

- Compose-level environment variable injection
- Spring property binding from env
- Same image, different runtime behavior

## Steps

1. Override compose variables in shell.
2. Start the stack.
3. Validate app behavior using overridden values.
4. Inspect container env values.

## Expected Output

- App runs on configured port.
- Active profile and datasource values reflect env overrides.

## Solution

```bash
export SERVER_PORT=8080
export SPRING_PROFILES_ACTIVE=docker
export MYSQL_DATABASE=device_monitoring
export MYSQL_USER=monitor_user
export MYSQL_PASSWORD=monitor_pass
export SPRING_DATASOURCE_USERNAME=monitor_user
export SPRING_DATASOURCE_PASSWORD=monitor_pass
docker compose up -d
curl -s http://localhost:8080/api/platform/status
docker exec -it day25-spring-app env | grep SPRING_
docker compose down
```

## Learning Outcome

You can operate containerized backends using environment-driven configuration without rebuilding images.
