# Deployment Topology

```mermaid
flowchart TB
  subgraph Host
    API[day29-agent-app container]
    DB[(day29-agent-postgres container)]
  end
  User[Operator] --> API
  API --> DB
  API --> Vol[data/]
  Mon[Monitoring] --> API
```
