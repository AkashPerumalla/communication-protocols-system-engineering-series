# Exercise 06 - Autoscaling And Resources

## Objective
Review how HPAs, quotas, and limits protect the cluster while enabling scale.

## Architecture
The namespace includes a ResourceQuota, a LimitRange, and an HPA targeting the Day-26 Deployment.

## Steps
1. Apply `k8s/resource-quota.yaml`, `k8s/limit-range.yaml`, and `k8s/hpa.yaml`.
2. Inspect the generated HPA.
3. Compare default requests and limits with the Deployment values.

## Expected Output
- The HPA tracks CPU and memory targets.
- Namespace policies cap total Pod and resource consumption.

## Solution
Use `kubectl get hpa` and `kubectl describe quota,limitrange -n day26-platform`.

## Learning Outcome
You can explain how scaling and resource governance work together.
