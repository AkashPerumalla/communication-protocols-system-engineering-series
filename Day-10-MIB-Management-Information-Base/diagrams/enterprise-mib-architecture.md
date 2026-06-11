# Enterprise MIB Architecture

## Explanation
Enterprise MIBs live under the private enterprise branch and model vendor-specific telemetry such as RF power, temperature, and alarms.

## Mermaid
```mermaid
graph TD
    A[internet 1.3.6.1] --> B[enterprises 1.3.6.1.4.1]
    B --> C[Avantel 99999]
    C --> D[deviceTemperature]
    C --> E[rfPower]
    C --> F[ber]
    C --> G[carrierStatus]
    C --> H[terminalStatus]
```

## Real-World Relevance
Vendors extend standard MIBs to expose platform-specific counters, alarms, and control points.

## Learning Outcomes
- Explain private enterprise numbering
- Distinguish standard and vendor objects
- Model telemetry under an enterprise branch
