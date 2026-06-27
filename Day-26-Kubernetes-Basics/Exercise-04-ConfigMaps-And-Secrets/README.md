# Exercise 04 - ConfigMaps And Secrets

## Objective
Verify that runtime configuration and credentials are injected into the application at deploy time.

## Architecture
The Deployment uses `envFrom` to mount ConfigMap and Secret data into the container environment.

## Steps
1. Apply `k8s/configmap.yaml` and `k8s/secret.yaml`.
2. Describe the Pod environment wiring.
3. Confirm `/api/platform/status` reflects the `kubernetes` profile.

## Expected Output
- Profile is `kubernetes`.
- Database mode changes to `MYSQL`.

## Solution
Use `kubectl describe pod` and `curl /api/platform/status` through a port-forward.

## Learning Outcome
You see how secure and non-secure runtime settings flow into Spring Boot on Kubernetes.
