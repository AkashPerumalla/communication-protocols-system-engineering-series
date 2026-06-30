# Exercise 03 - MCP Stdio Tool Bridge

## Objective

Validate MCP stdio compatibility for readiness and tool execution calls.

## Steps

1. Run MCP readiness command via stdio.
2. Run MCP tool command for `system_info`.
3. Confirm `AGENT_READY` and `TOOL_EXECUTED` markers.

## Solution

Run `run.sh` to execute stdio payloads against `src.mcp_server.stdio_entry`.

## Outcome

You confirm model-facing tool execution works over MCP stdio transport.
