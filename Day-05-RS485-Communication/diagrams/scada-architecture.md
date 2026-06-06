```mermaid
graph LR
    PLCs[Field PLCs] --> RTU[RTU Concentrator]
    RTU --> SCADA[SCADA Server]
    SCADA --> HMI[Operator HMI]
    RTU --- RS485[RS485 Bus]
    PLCs --- RS485
```
