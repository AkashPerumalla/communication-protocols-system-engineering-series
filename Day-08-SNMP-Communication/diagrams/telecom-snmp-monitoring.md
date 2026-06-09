# Telecom SNMP Monitoring

```mermaid
flowchart LR
    Hub[SatCom HUB] --> Modem[Modem]
    Hub --> BUC[BUC]
    Hub --> LNB[LNB]
    Hub --> HPA[HPA]
    Modem --> Metrics[Signal, BER, Lock, Temp]
    BUC --> Metrics
    LNB --> Metrics
    HPA --> Metrics
```

This diagram shows how SNMP-style polling can be used to monitor telecom and SatCom ground equipment.

## Notes
- Signal strength and BER are critical operational indicators.
- Temperature and lock status are common escalation triggers.