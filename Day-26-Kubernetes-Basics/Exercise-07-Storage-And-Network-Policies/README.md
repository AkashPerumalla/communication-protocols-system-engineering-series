# Exercise 07 - Storage And Network Policies

## Objective
Protect stateful data and narrow network access paths.

## Architecture
MySQL uses a PV/PVC pair and NetworkPolicies only allow app-to-database communication.

## Steps
1. Apply the PV, PVC, and NetworkPolicy manifests.
2. Verify PVC binding.
3. Confirm only the application Pods are allowed to reach MySQL.

## Expected Output
- PVC status is `Bound`.
- The MySQL Pod mounts `/var/lib/mysql` from the claim.
- NetworkPolicy restricts ingress to MySQL.

## Solution
Use `kubectl get pvc`, `kubectl describe pod`, and `kubectl describe networkpolicy`.

## Learning Outcome
You understand the persistence and least-privilege networking baseline for stateful workloads.
