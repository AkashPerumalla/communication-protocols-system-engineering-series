# Autoscaling And Resource Controls

```mermaid
graph TB
    Metrics[CPU and Memory Metrics] --> HPA[HorizontalPodAutoscaler]
    HPA --> Deployment[day26-platform Deployment]
    Quota[ResourceQuota] --> Namespace[day26-platform Namespace]
    LimitRange --> Namespace
    Namespace --> Deployment
```
