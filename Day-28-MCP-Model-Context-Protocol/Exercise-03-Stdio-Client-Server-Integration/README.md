# Exercise 03 - Stdio Client Server Integration

## Objective

Execute an MCP round-trip flow where client connects and invokes a server tool through stdio.

## Steps

1. In project root, run: `uv run pytest tests/integration/test_mcp_stdio.py`
2. Observe connect and execute assertions.

## Solution

`MCPClient` uses subprocess stdio exchange with the server entry point and validates markers.

## Outcome

Client returns `CLIENT_CONNECTED`; tool call returns `TOOL_EXECUTED`.
