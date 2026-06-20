# Alarm Lifecycle

```mermaid
stateDiagram-v2
    [*] --> DETECTED
    DETECTED --> OPEN
    OPEN --> ACKNOWLEDGED
    ACKNOWLEDGED --> ESCALATED
    ACKNOWLEDGED --> RESOLVED
    ESCALATED --> RESOLVED
    RESOLVED --> CLOSED
```
