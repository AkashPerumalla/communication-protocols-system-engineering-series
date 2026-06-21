# SNMP Monitoring Flow

```mermaid
flowchart TD
    A[Latest MetricRecord per Agent] --> B[Map to OIDs]
    B --> C[sysUptime .1.3.6.1.2.1.1.3.0]
    B --> D[cpuUsage .1.3.6.1.4.1.2021.11.9.0]
    B --> E[memoryUsage .1.3.6.1.4.1.2021.4.6.0]
    B --> F[ifIn .1.3.6.1.2.1.2.2.1.10]
    B --> G[ifOut .1.3.6.1.2.1.2.2.1.16]
    C --> H[/api/snmp response]
    D --> H
    E --> H
    F --> H
    G --> H
```
