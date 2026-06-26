# Exercise 01 - Containerized Spring Boot Basics

## Objective

Build and run the Day-25 Spring Boot application as a Docker container with the default profile.

## Architecture

- Single spring-app container
- Embedded H2 profile for fast validation
- API and actuator exposed on port 8080

## Steps

1. Build the image.
2. Run the container with port mapping.
3. Call platform and health endpoints.
4. Inspect logs.

## Expected Output

- Container starts successfully.
- /api/platform/status returns markers DOCKER READY and APPLICATION RUNNING.
- /actuator/health returns status UP.

## Solution

```bash
docker build -t day25-device-monitoring:latest .
docker run --rm -p 8080:8080 -e SPRING_PROFILES_ACTIVE=default day25-device-monitoring:latest
curl -s http://localhost:8080/api/platform/status
curl -s http://localhost:8080/actuator/health
docker logs $(docker ps -q --filter ancestor=day25-device-monitoring:latest)
```

## Learning Outcome

You understand how a Spring Boot JAR is packaged and executed as a containerized service.
