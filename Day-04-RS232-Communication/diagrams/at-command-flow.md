```mermaid
flowchart TD
    User-->ATClient
    ATClient-->Modem[ATCommandSimulator]
    Modem-->|OK|ATClient
    Modem-->|URC|ATClient
```
