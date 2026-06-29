# Exercise 05 - PostgreSQL Query Tool

## Objective

Run SQL query tool in deterministic mock mode and optional PostgreSQL mode.

## Steps

1. Default mode: ensure `POSTGRES_ENABLED=false`.
2. Invoke tool: `curl -s -X POST http://localhost:8093/tool/postgresql_query -H 'Content-Type: application/json' -d '{"arguments":{"sql":"SELECT 1 AS ok"}}'`
3. Optional DB mode: `docker compose --profile postgres up --build` and set `POSTGRES_ENABLED=true`.

## Solution

`PostgresService` routes to mock data when DB is disabled and executes real SQL when enabled.

## Outcome

Mock mode stays deterministic while real mode validates runtime DB integration.
