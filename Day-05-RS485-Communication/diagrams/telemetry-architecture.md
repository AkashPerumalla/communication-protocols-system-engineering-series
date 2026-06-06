```mermaid
graph TD
    Sensors --> RTU
    RTU --> Gateway
    Gateway --> Cloud[Telemetry Collector]
    Gateway --- RS485[RS485 Field Bus]
```
