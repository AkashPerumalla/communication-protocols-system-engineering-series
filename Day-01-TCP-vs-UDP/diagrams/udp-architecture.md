# UDP Architecture

## Flow

```mermaid
sequenceDiagram
    participant C as Client
    participant S as UDP Server

    C->>S: Datagram
    C->>S: Datagram
    C->>S: Datagram
    S-->>C: Optional response
```

## Key Properties

- Connectionless communication
- No handshake
- Datagram-based transmission
- Best-effort delivery without retransmission guarantees

## Lab Mapping

- The UDP analyzer receives each packet independently.
- Packet metadata is displayed for monitoring and troubleshooting.
