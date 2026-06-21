# Exercise 01 - Build Realtime Monitoring System

## Objective
Build the end-to-end monitoring platform baseline including agents, metrics ingestion, and storage.

## Architecture
- MonitoringAgent registration and heartbeat API
- MetricRecord persistence via JPA/H2
- Layered controller-service-repository flow

## Implementation Steps
1. Register agents through /api/agents/register.
2. Send heartbeats through /api/agents/heartbeat.
3. Verify agent discovery via /api/agents.
4. Validate stored metrics with /api/metrics.

## Expected Output
- AGENT REGISTERED
- METRICS COLLECTED

## Solution Explanation
The solution uses deterministic seed data and scheduled collection to simulate real infrastructure nodes, then exposes a stable API for NOC workflows.
