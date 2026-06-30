# Exercise 01 - Agent Orchestrator Bootstrap

## Objective

Initialize the Day-29 platform and validate baseline agent readiness through health and tool discovery endpoints.

## Steps

1. Install dependencies (`uv sync` or `python -m pip install -e .`).
2. Start API server on port 8094.
3. Call `GET /health` and `GET /agent/tools`.
4. Verify `SYSTEM_HEALTHY` and `AGENT_READY` markers.

## Solution

Run `run.sh` in this exercise folder. The script performs health and tool discovery checks with marker assertions.

## Outcome

You confirm that the platform is bootstrapped and the agent runtime is ready before task execution.
