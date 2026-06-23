# Diagram 4 - Alarm Broadcast Flow

```mermaid
flowchart LR
    A[AlarmScheduler 10s] --> B[AlarmService Evaluate Rules]
    B --> C{Threshold Breach?}
    C -- No --> D[No New Alarm]
    C -- Yes --> E[Create AlarmEvent]
    E --> F[(AlarmEvent Repository)]
    F --> G[WebSocketBroadcastService]
    G --> H[/topic/alarms]
    H --> I[NOC Alarm Consoles]

    J[Operator ACK via /app/acknowledge] --> K[AlarmService acknowledge]
    K --> L[(Update acknowledged=true)]
    L --> G
```
