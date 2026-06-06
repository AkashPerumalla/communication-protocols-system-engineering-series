```mermaid
sequenceDiagram
    Master->>Slave: [ADDR][FUNC=03][START][QTY][CRC]
    Slave-->>Master: [ADDR][FUNC=03][BYTECNT][DATA..][CRC]
```
