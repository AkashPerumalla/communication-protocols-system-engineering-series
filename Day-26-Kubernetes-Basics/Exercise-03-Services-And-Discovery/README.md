# Exercise 03 - Services And Discovery

## Objective
Expose the application and database through stable service endpoints.

## Architecture
A ClusterIP Service fronts MySQL and a NodePort Service fronts the Spring Boot application.

## Steps
1. Apply `k8s/service.yaml`.
2. Resolve the MySQL Service name from inside the app Pod.
3. Reach the app through NodePort or port-forwarding.

## Expected Output
- `day26-mysql` resolves inside the namespace.
- `day26-platform-service` routes traffic to healthy Pods.

## Solution
Use `kubectl get svc -n day26-platform` and `kubectl port-forward` for local testing.

## Learning Outcome
You understand stable discovery patterns for internal and external traffic.
