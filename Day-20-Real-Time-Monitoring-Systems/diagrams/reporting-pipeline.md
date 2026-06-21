# Reporting Pipeline

```mermaid
flowchart LR
    A[Metrics + Alerts + Dashboard Snapshot] --> B[ReportingService]
    B --> C[Daily Monitoring Report]
    B --> D[Alert Summary Report]
    B --> E[Resource Utilization Report]
    C --> F[/api/reports]
    D --> F
    E --> F
    B --> G[/api/reports/generate]
```
