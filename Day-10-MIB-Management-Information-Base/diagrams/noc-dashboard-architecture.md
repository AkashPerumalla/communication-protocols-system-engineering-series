# NOC Dashboard Architecture

## Explanation
The NOC dashboard aggregates MIB data from routers, switches, servers, telecom nodes, and satellite terminals into one operational view.

## Mermaid
```mermaid
graph LR
    A[Router] --> D[NOC Dashboard]
    B[Switch] --> D
    C[Server] --> D
    E[Telecom Terminal] --> D
    F[SatCom Terminal] --> D
    D --> G[Status / CPU / Memory / Temperature / Alarms]
```

## Real-World Relevance
A centralized dashboard helps operators triage incidents quickly and compare fleets at a glance.

## Learning Outcomes
- Explain centralized monitoring
- Summarize multi-device visibility
- Connect MIB data to operations dashboards
