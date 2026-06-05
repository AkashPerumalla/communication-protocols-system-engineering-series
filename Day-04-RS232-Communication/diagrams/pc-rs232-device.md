```mermaid
sequenceDiagram
    PC->>RS232Device: Serial Frame (9600 8N1)
    RS232Device-->>PC: ACK / Response
    Note right of RS232Device: Timestamp, Port, Baud
```
