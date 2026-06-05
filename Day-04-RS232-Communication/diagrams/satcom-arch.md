```mermaid
graph TD
    Hub[Monitoring Hub] -->|Telemetry| SatTerm[SatCom Terminal]
    SatTerm -->|Telemetry| Hub
    SatTerm ---|RS232/Serial| Device
    Hub --> Dashboard
```
