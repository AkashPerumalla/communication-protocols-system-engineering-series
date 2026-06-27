# Exercise 01 - Kubernetes Namespaces And Config

## Objective
Learn how the Day-26 platform separates workload concerns with a namespace, ConfigMap, and Secret.

## Architecture
Namespace `day26-platform` contains the application, MySQL, and all governance resources.

## Steps
1. Apply `k8s/namespace.yaml`.
2. Apply `k8s/configmap.yaml` and `k8s/secret.yaml`.
3. Inspect the generated resources with `kubectl get` and `kubectl describe`.

## Expected Output
- Namespace exists.
- ConfigMap keys expose profile, namespace, image tag, and database host.
- Secret exposes the MySQL and datasource credentials.

## Solution
Use `kubectl apply -f k8s/namespace.yaml -f k8s/configmap.yaml -f k8s/secret.yaml`.

## Learning Outcome
You understand how Day-26 externalizes runtime configuration without rebuilding the image.
