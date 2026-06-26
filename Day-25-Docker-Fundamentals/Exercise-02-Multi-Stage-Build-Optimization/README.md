# Exercise 02 - Multi-Stage Build Optimization

## Objective

Validate how multi-stage Docker builds improve image efficiency and build repeatability.

## Architecture

- Builder stage with JDK + Maven
- Runtime stage with JRE only
- Layer cache for dependencies

## Steps

1. Build image twice.
2. Compare build durations.
3. Inspect image history.
4. Verify runtime layer size impact.

## Expected Output

- Second build is faster due to cache reuse.
- Runtime image excludes Maven and source files.

## Solution

```bash
time docker build -t day25-device-monitoring:latest .
time docker build -t day25-device-monitoring:latest .
docker history day25-device-monitoring:latest
docker images | grep day25-device-monitoring
```

## Learning Outcome

You can explain Docker layer caching and why multi-stage builds are mandatory for production-style Java images.
