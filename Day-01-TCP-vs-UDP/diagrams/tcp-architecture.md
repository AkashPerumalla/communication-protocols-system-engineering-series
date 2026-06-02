# TCP Architecture

## Flow

```mermaid
sequenceDiagram
    participant C as Client
    participant S as TCP Server

    C->>S: SYN
    S->>C: SYN-ACK
    C->>S: ACK
    C->>S: Reliable message
    S->>C: ACK / response
```

## Key Properties

- Connection-oriented communication
- Ordered delivery
- Retransmission on packet loss
- Flow control and acknowledgement tracking

## Lab Mapping

- The TCP messaging exercise uses one persistent socket per client.
- The server logs username, connection time, message body, and disconnection time.
