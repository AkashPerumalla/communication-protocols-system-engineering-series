# Exercise 08 - End To End Platform Validation

## Objective
Run the full Day-26 build, Docker, and Kubernetes verification pipeline.

## Architecture
The automation script runs Maven tests, builds the image, validates manifests, deploys the namespace, waits for rollouts, and checks markers.

## Steps
1. Ensure Docker Desktop Kubernetes and kubectl are available.
2. Run `./scripts/run_all_tests.sh`.
3. Review rollout status, service exposure, and API marker validation.

## Expected Output
- Maven tests pass.
- Kubernetes resources are created successfully.
- The script ends with `PASS`.

## Solution
Use the provided script exactly, then inspect namespace resources if a step fails.

## Learning Outcome
You can validate a realistic local Kubernetes delivery flow end to end.
