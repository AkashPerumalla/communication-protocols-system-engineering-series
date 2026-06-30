# Day-29 - AI Agents Explained

This module implements a production-style AI Agent platform for NOC, Telecom, SatCom, and IoT operations. The platform is not a chatbot demo: it performs deterministic planning, tool selection, tool execution, memory updates, and grounded response synthesis with marker-driven observability.

## Learning Objectives

- Build an enterprise AI agent orchestration pipeline in Python.
- Implement a deterministic planner and tool selector.
- Design session memory for operational agent workflows.
- Expose production-style FastAPI endpoints for control and history.
- Integrate MCP stdio tool execution with FastMCP.
- Enforce read-only SQL safety and file path guards.
- Apply structured logging, global exception handling, and typed DTO validation.
- Deliver complete QA automation with unit and integration coverage.

## Technology Stack

- Python 3.11+
- FastAPI
- Pydantic v2
- OpenAI-compatible SDK (`openai`, mock provider used in this module)
- FastMCP
- PostgreSQL (optional, read-only tool path)
- Docker + docker-compose
- pytest + pytest-cov

## Enterprise Architecture

The project follows modular SOLID layering under `src`:

- `src/config` - settings, logging configuration
- `src/api` - HTTP routes, middleware, exception handlers, dependencies
- `src/agent` - orchestrator for reason-plan-execute-memory lifecycle
- `src/planner` - deterministic planning and tool selection
- `src/memory` - session conversation memory store
- `src/tools` - tool registry and 10 enterprise tools
- `src/services` - domain/platform/system/REST/Postgres/LLM services
- `src/models` - response envelope and marker constants
- `src/dto` - validated API payload contracts
- `src/utils` - time, SQL guard, file safety
- `src/exceptions` - custom exception hierarchy
- `tests` - unit and integration suites

## Agent Workflow

1. Agent receives task/query.
2. Planner creates execution plan and selects a tool.
3. Tool registry executes selected tool with validated args.
4. Orchestrator updates memory with user + assistant events.
5. Mock LLM synthesizes grounded response from tool evidence.
6. API returns deterministic envelope with trace markers.

## Marker Constants

- `AGENT_READY`
- `TOOL_SELECTED`
- `TOOL_EXECUTED`
- `PLAN_CREATED`
- `MEMORY_UPDATED`
- `TASK_COMPLETED`
- `API_SUCCESS`
- `SYSTEM_HEALTHY`

## Implemented Agent Tools

1. `system_info`
2. `file_search_read`
3. `rest_api_client`
4. `postgresql_query` (read-only)
5. `device_status`
6. `telemetry_lookup`
7. `alarm_lookup`
8. `log_search`
9. `knowledge_search`
10. `platform_status`

## REST API Contracts

- `GET /health`
- `GET /platform/status`
- `GET /agent/tools`
- `POST /agent/chat`
- `POST /agent/run`
- `GET /agent/history`

All responses use the response envelope:

```json
{
  "success": true,
  "marker": "TASK_COMPLETED",
  "message": "Agent task executed",
  "data": {},
  "timestamp": "2026-06-30T00:00:00+00:00"
}
```

## Deterministic Mode

Deterministic mode is enabled by default and guarantees:

- fixed timestamps
- stable planner choices for identical inputs
- reproducible system/REST fallback outputs
- reliable CI assertions on markers and response shape

## Quick Start

### Option A: uv workflow

```bash
uv sync
uv run uvicorn src.main:app --host 0.0.0.0 --port 8094
```

### Option B: pip workflow

```bash
python -m pip install -e . pytest pytest-cov
python -m uvicorn src.main:app --host 0.0.0.0 --port 8094
```

## Run Tests

```bash
python -m pytest -q
```

## Run Full Enterprise Validation

```bash
./scripts/run_all_tests.sh
```

Expected final output:

```text
PASS: All tests passed
```

## Docker

Start application container:

```bash
docker compose up --build
```

Start application + optional PostgreSQL profile:

```bash
docker compose --profile postgres up --build
```

## Practical Exercises

- Exercise 01 - Agent Orchestrator Bootstrap
- Exercise 02 - Planner and Tool Selection
- Exercise 03 - MCP Stdio Tool Bridge
- Exercise 04 - FastAPI Agent API Contracts
- Exercise 05 - PostgreSQL Read-Only Query Safety
- Exercise 06 - Telecom Observability Tooling
- Exercise 07 - Deterministic Testing and Validation
- Exercise 08 - Dockerized Platform Operations

## Key Takeaways

- Enterprise agents require deterministic orchestration, not chat-only outputs.
- Tool grounding is essential for trustworthy operations responses.
- Marker-based contracts make API and workflow validation machine-verifiable.
- SOLID modular architecture improves maintainability and testability.
- Optional dependencies (PostgreSQL) should degrade gracefully without service outage.
