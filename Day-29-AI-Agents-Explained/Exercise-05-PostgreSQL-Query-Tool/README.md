# Exercise 05 - PostgreSQL Read-Only Query Safety

## Objective

Validate read-only SQL guard behavior and optional database fallback mode.

## Steps

1. Run unit tests for SQL guard.
2. Execute agent query that routes to `postgresql_query`.
3. Confirm write operations are blocked and fallback mode is deterministic.

## Solution

Run `run.sh` to execute SQL-guard validation tests.

## Outcome

You confirm the platform enforces safe, read-only query behavior.
