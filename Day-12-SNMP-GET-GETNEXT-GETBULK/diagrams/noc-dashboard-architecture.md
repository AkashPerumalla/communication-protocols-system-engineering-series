# NOC Dashboard Architecture

```mermaid
graph TD
    Manager[SNMP Manager] --> Agent[SNMP Agent Simulator]
    Agent --> Repository[MIB Repository]
    Manager --> Dashboard[Dashboard Service]
    Dashboard --> Router[Router-01]
    Dashboard --> Switch[Switch-01]
    Dashboard --> Modem[Modem-01]
    Dashboard --> Hub[Hub-01]
```
