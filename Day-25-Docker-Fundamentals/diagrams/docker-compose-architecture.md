# Docker Compose Architecture

```mermaid
graph LR
    subgraph day25-net bridge
        APP[spring-app:8080]
        DB[mysql:3306]
    end

    Volume[(day25_mysql_data)] --> DB
    APP -->|depends_on: service_healthy| DB
    APP -->|healthcheck| Health[/actuator/health]
    User[Client/NOC Tool] -->|localhost:8080| APP
```

## Quick Explanation

Compose wires service dependencies, health checks, persistent volume storage, and bridge networking for local enterprise simulation.
