# Exercise 02 - Tool Registry And Discovery

## Objective

Register all platform tools in a single registry and retrieve deterministic discovery output.

## Steps

1. Run API: `uv run uvicorn src.main:app --host 0.0.0.0 --port 8093`
2. Query tools endpoint: `curl -s http://localhost:8093/tools`
3. Verify tool names and descriptions are stable.

## Solution

`ToolRegistryService` stores handlers and metadata, sorted by tool name for deterministic listing.

## Outcome

Discovery returns an ordered tool list under marker `API_SUCCESS`.
