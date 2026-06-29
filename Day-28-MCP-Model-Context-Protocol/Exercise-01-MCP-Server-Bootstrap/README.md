# Exercise 01 - MCP Server Bootstrap

## Objective

Bootstrap an MCP server that exposes deterministic readiness and tool execution behavior over stdio.

## Steps

1. Sync dependencies: `uv sync`
2. Start stdio server command: `uv run python -m src.mcp_server.stdio_entry`
3. Send readiness payload: `{"command":"ready"}`

## Solution

The server reads JSON lines from stdin, routes command/tool requests, and emits marker-based JSON responses.

## Outcome

You will observe `MCP_READY` and `SERVER_RUNNING` in the readiness response.
