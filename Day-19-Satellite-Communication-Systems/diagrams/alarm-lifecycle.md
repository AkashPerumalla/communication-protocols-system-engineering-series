# Alarm Lifecycle

```mermaid
stateDiagram-v2
    [*] --> DETECTED
    DETECTED --> OPEN
    OPEN --> ACKNOWLEDGED
    ACKNOWLEDGED --> INVESTIGATING
    INVESTIGATING --> RESOLVED
    RESOLVED --> CLOSED

    OPEN --> ESCALATED
    ESCALATED --> INVESTIGATING
    INVESTIGATING --> OPEN: Recurred condition
```
