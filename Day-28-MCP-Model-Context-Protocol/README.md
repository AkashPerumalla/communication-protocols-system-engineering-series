# Day 28 - MCP Model Context Protocol

This module delivers a production-style MCP platform designed for NOC, Telecom, SatCom, and IoT operations. It includes a deterministic tool execution layer, FastAPI operational APIs, stdio-based MCP server/client integration, optional PostgreSQL query support, and enterprise-grade testing and automation.

## Learning Objectives

- Build a layered MCP platform with constructor dependency injection.
- Expose and consume tool contracts using stdio transport.
- Implement deterministic output modes for repeatable testing.
- Design API response envelopes with marker-based observability.
- Apply global exception handling and validation in FastAPI.
- Integrate operational data tools for telemetry, alarms, and device status.
- Validate platform behavior with unit and integration tests.
- Package and run the platform using Docker and docker-compose.

## Technology Stack

- Python 3.11
- FastMCP
- FastAPI
- uv
- Pydantic v2
- PostgreSQL (optional)
- Docker + docker-compose
- pytest

## Project Layout

- `src/config` - settings and structured logging
- `src/api` - FastAPI dependencies, routes, exception handlers
- `src/mcp_server` - stdio MCP server
- `src/mcp_client` - stdio MCP client
- `src/tools` - MCP tool registration and handlers
- `src/services` - business services and orchestrators
- `src/models` - response envelope and marker constants
- `src/dto` - request/response DTO contracts
- `src/utils` - path safety and time helpers
- `src/exceptions` - custom exceptions
- `tests` - unit and integration test suites
- `scripts` - end-to-end validation script

## Implemented MCP Tools

1. System Info (CPU/RAM/Disk)
2. File Read/Search
3. Directory Listing
4. REST API Caller
5. PostgreSQL Query
6. Telemetry Lookup
7. Alarm Lookup
8. Device Status
9. Log Search
10. Platform Status

## API Contracts

- `GET /health`
- `GET /platform/status`
- `GET /tools`
- `POST /query`
- `POST /tool/{name}`

All API responses use a deterministic envelope:

```json
{
  "success": true,
  "marker": "API_SUCCESS",
  "message": "Platform status retrieved",
  "data": {},
  "timestamp": "2026-06-29T00:00:00+00:00"
}
```

## Marker Constants

- `MCP_READY`
- `TOOL_EXECUTED`
- `CLIENT_CONNECTED`
- `SERVER_RUNNING`
- `DATABASE_CONNECTED`
- `API_SUCCESS`
- `QUERY_COMPLETED`
- `SYSTEM_HEALTHY`

## Run Instructions

1. Sync dependencies:

```bash
uv sync
```

2. Start API server:

```bash
uv run uvicorn src.main:app --host 0.0.0.0 --port 8093
```

3. Run pytest:

```bash
uv run pytest
```

4. Run full enterprise validation:

```bash
./scripts/run_all_tests.sh
```

## Docker

Build and run app only:

```bash
docker compose up --build
```

Run with optional PostgreSQL profile:

```bash
docker compose --profile postgres up --build
```

## Expected Validation Output

```text
[1/7] Sync dependencies with uv
[2/7] Run pytest suite
[3/7] Start FastAPI service
[4/7] Wait for health endpoint
[5/7] Validate markers from REST APIs
[6/7] Validate MCP stdio server/client markers
[7/7] PASS: Day-28 MCP platform validations succeeded
```

## Exercises

- Exercise 01 - MCP Server Bootstrap
- Exercise 02 - Tool Registry And Discovery
- Exercise 03 - Stdio Client Server Integration
- Exercise 04 - FastAPI Operational Endpoints
- Exercise 05 - PostgreSQL Query Tool
- Exercise 06 - Telecom Observability Tools
- Exercise 07 - Deterministic Testing And Validation
- Exercise 08 - Dockerized Platform Operations

## Key Takeaways

- Determinism improves test reliability in distributed tool platforms.
- Marker constants provide machine-checkable operational status.
- Constructor DI preserves clear boundaries and testability.
- stdio MCP transport enables lightweight, secure local tool exchange.
- Optional database modes support portability across environments.
