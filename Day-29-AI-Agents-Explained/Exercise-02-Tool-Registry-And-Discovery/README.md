# Exercise 02 - Planner and Tool Selection

## Objective

Execute an agent query and inspect deterministic planner output, selected tool, and trace markers.

## Steps

1. Ensure API is running.
2. Submit `POST /agent/chat` with query `show platform status`.
3. Inspect `plan.selected_tool` and `trace` fields.
4. Validate `PLAN_CREATED`, `TOOL_SELECTED`, and `TOOL_EXECUTED` markers.

## Solution

Run `run.sh` to send a deterministic chat request and assert planner markers.

## Outcome

You validate that the planner and tool selection pipeline is deterministic and traceable.
