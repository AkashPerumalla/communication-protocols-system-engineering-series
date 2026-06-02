# TCP vs UDP Mermaid Comparison

```mermaid
flowchart LR
    TCP[TCP] --> T1[Connection-oriented]
    TCP --> T2[Three-way handshake]
    TCP --> T3[Ordered delivery]
    TCP --> T4[Reliability and retransmission]

    UDP[UDP] --> U1[Connectionless]
    UDP --> U2[No handshake]
    UDP --> U3[Datagram transmission]
    UDP --> U4[Lower overhead]
```

## Quick Takeaway

- Use TCP when delivery correctness matters more than raw speed.
- Use UDP when low latency and lightweight transmission matter more than guaranteed delivery.
