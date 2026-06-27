# Exercise 05 - Probes And Cluster Health

## Objective
Connect Actuator health endpoints to Kubernetes probes and the project’s cluster-health endpoint.

## Architecture
Startup, readiness, and liveness probes call Actuator while the app also computes cluster state internally.

## Steps
1. Apply the application Deployment.
2. Inspect probe configuration in the Pod spec.
3. Query `/actuator/health`, `/actuator/health/readiness`, and `/api/cluster/health`.

## Expected Output
- Actuator endpoints return `UP`.
- Cluster health shows node, pod, and storage summaries.

## Solution
Use `kubectl describe pod` plus curl against the forwarded service.

## Learning Outcome
You understand the difference between process health and operational readiness.
