# Alarm Management Flow

```mermaid
flowchart LR
    Trap[Trap / Poll Result] --> Correlator[Alarm Correlator]
    Correlator --> Active[Active Alarms]
    Correlator --> Cleared[Cleared Alarms]
    Active --> Dashboard[NMS Dashboard]
    Cleared --> History[Alarm History]
```

The alarm manager correlates events, tracks active and cleared alarms, and feeds the dashboard.

## Notes
- Alarm severity helps prioritize response.
- Active and cleared views are both important for operations and postmortem analysis.
