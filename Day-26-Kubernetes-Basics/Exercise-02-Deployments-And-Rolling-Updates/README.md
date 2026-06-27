# Exercise 02 - Deployments And Rolling Updates

## Objective
Practice rollout-aware workload management using the Day-26 deployment API and Kubernetes Deployment strategy.

## Architecture
The Spring Boot Deployment uses `RollingUpdate`, readiness probes, and stable image tags.

## Steps
1. Apply `k8s/deployment.yaml`.
2. Trigger `POST /api/deployment/rolling-update`.
3. Observe `updatedReplicas`, `availableReplicas`, and rollout markers.

## Expected Output
- The deployment starts in `ROLLING_UPDATE_IN_PROGRESS`.
- Scheduler advancement moves it back to `RUNNING`.

## Solution
Use `kubectl rollout status deployment/day26-platform -n day26-platform` and query `/api/deployments` during the rollout.

## Learning Outcome
You can connect application-level deployment simulation to Kubernetes rollout behaviour.
