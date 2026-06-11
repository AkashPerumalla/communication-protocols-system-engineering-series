# Telecom Monitoring Flow

## Explanation
Telecom monitoring combines RF metrics, lock state, BER, and alarm state to show whether a terminal can carry service.

## Mermaid
```mermaid
sequenceDiagram
    participant NOC
    participant Manager
    participant Agent
    NOC->>Manager: Request telemetry view
    Manager->>Agent: GET txPower, rxPower, frequency, ber
    Agent-->>Manager: Return RF values and alarms
    Manager-->>NOC: Render telecom status
```

## Real-World Relevance
This mirrors hub and terminal monitoring in microwave, satellite, and backhaul operations.

## Learning Outcomes
- Trace RF monitoring data flow
- Link BER to service quality
- Describe alarm-driven visibility
