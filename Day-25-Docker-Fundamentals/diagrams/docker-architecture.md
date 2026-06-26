# Docker Architecture

```mermaid
graph TD
    Dev[Developer] --> Dockerfile[Dockerfile Multi-Stage Build]
    Dockerfile --> Image[day25-device-monitoring image]
    Image --> AppContainer[spring-app container]
    Compose[Docker Compose] --> AppContainer
    Compose --> DbContainer[mysql container]
    AppContainer -->|JDBC| DbContainer
    AppContainer --> Actuator[/actuator/health]
    Ops[NOC Operator] -->|API Calls| AppContainer
    Logs[Container Logs] --> Observability[Monitoring/Log Pipeline]
```

## Quick Explanation

The backend is built into a production-focused image and orchestrated with MySQL using Docker Compose on an isolated bridge network.
