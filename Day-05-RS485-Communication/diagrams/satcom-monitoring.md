```mermaid
graph LR
    HUB[SatCom Hub Controller] --- Remote1[UPC]
    HUB --- Remote2[HPA]
    HUB --- Remote3[LNB]
    Remote1 -->|Telemetry| HUB
    Remote2 -->|Telemetry| HUB
```
