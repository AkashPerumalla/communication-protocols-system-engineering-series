```mermaid
graph LR
    PC((PC DB9)) --- NM[Null Modem Cable] --- Device((Device DB9))
    NM ---|RX/TX swapped| NM2
    classDef conn fill:#f9f,stroke:#333
```
