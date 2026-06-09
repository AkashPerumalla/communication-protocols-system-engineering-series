# SNMP Trap Flow

```mermaid
flowchart TD
    Event[Device Event] --> Trap[Trap Generated]
    Trap --> NMS[NMS / Trap Receiver]
    NMS --> Alarm[Alarm Correlation]
    Alarm --> Dashboard[Dashboard Update]
```

Traps allow the agent to notify the manager without waiting for a poll cycle.

## Notes
- High temperature, link down, and power failure are common trap sources.
- Correlation turns raw traps into actionable alarms.
