# Exercise 08 - Production Readiness Validation

## Objective

Execute the end-to-end automated verification pipeline and confirm all production-readiness checks.

## Architecture

- Maven integration tests
- Docker image build
- Compose startup and health checks
- API smoke tests with marker assertions

## Steps

1. Run automation script.
2. Observe numbered pipeline stages.
3. Verify marker validations.
4. Confirm cleanup on completion.

## Expected Output

- PASS message after all checks.
- No orphaned containers remain.

## Solution

```bash
chmod +x scripts/run_all_tests.sh
./scripts/run_all_tests.sh
docker compose ps
```

## Learning Outcome

You can automate quality gates for containerized backend delivery using a deterministic smoke pipeline.
