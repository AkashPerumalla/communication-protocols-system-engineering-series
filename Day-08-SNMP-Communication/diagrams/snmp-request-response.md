# SNMP Request / Response

```mermaid
sequenceDiagram
    participant M as Manager
    participant A as Agent
    M->>A: GET sysName.0
    A-->>M: Router-01
    M->>A: SET sysLocation.0 = Hyderabad
    A-->>M: OK
```

This sequence shows the synchronous request/response path used by GET and SET.

## Notes
- The manager initiates every request.
- The agent replies with success or a validation error.
- SET must respect access mode and value constraints.
