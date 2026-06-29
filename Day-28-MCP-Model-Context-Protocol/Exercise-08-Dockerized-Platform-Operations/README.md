# Exercise 08 - Dockerized Platform Operations

## Objective

Run the MCP platform in containers with optional PostgreSQL profile.

## Steps

1. Start application: `docker compose up --build`
2. Validate health endpoint from host.
3. Start optional database: `docker compose --profile postgres up --build`

## Solution

The Dockerfile builds a Python 3.11 runtime with uv-managed dependencies and health checks.

## Outcome

Platform becomes portable across developer machines and CI/CD environments.
