# Exercise 04 - FastAPI Operational Endpoints

## Objective

Validate operational APIs for health, platform status, tool discovery, and query execution.

## Steps

1. Start API server on port 8093.
2. Execute:
   - `curl -s http://localhost:8093/health`
   - `curl -s http://localhost:8093/platform/status`
   - `curl -s http://localhost:8093/tools`
3. Run a query and tool call via POST endpoints.

## Solution

Routes in `src/api/routes.py` call service layer dependencies and wrap outputs in `ResponseEnvelope`.

## Outcome

Endpoint responses include `SYSTEM_HEALTHY`, `API_SUCCESS`, `QUERY_COMPLETED`, and `TOOL_EXECUTED`.
