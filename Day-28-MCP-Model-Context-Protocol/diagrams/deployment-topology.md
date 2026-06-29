# Deployment Topology

```mermaid
flowchart LR
  Dev[Developer Laptop] --> Compose[docker-compose]
  Compose --> App[day28-mcp-app]
  Compose -->|profile: postgres| DB[(PostgreSQL)]
  App --> Health[/health]
  App --> API[/platform/status,/tools,/query,/tool/{name}]
  App --> Stdio[MCP stdio server]
```

The platform supports app-only mode and optional PostgreSQL profile mode under docker-compose.
