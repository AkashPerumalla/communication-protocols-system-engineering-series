# Exercise 04 - Metric Collection Agent

## Objective
Implement a realistic metric collection cycle with heartbeat-aware agents and dynamic metric values.

## Architecture
- Scheduled metric collector (5-second cadence)
- Agent health scheduler for ONLINE/OFFLINE status refresh
- Uptime and process trend simulation

## Implementation Steps
1. Register multiple agents.
2. Start scheduled collection and observe metric drift.
3. Pause heartbeat to observe status transition.
4. Recover with heartbeat and verify ONLINE state.

## Expected Output
- AGENT REGISTERED
- METRICS COLLECTED

## Solution Explanation
The collector uses deterministic formulas with bounded drifts to emulate production-like behavior while keeping test outputs repeatable.
