```mermaid
flowchart LR
    HostA[Host A\n(AA:AA)] -->|frame| Switch
    HostB[Host B\n(BB:BB)] -->|frame| Switch
    Switch -->|learn AA->port1| MACTable
    Switch -->|learn BB->port2| MACTable
    Switch -->|forward to port2| HostB
    Switch -->|flood| Hosts[All other ports]
```

Description: MAC learning, forwarding, and flooding behavior.
