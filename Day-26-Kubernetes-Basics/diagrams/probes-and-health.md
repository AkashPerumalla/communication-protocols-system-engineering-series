# Probes And Health

```mermaid
graph LR
    Startup[Startup Probe] --> Health[/actuator/health]
    Readiness[Readiness Probe] --> Ready[/actuator/health/readiness]
    Liveness[Liveness Probe] --> Live[/actuator/health/liveness]
    Ready --> Service[Service Routing]
    Live --> Kubelet[Restart Decision]
```
