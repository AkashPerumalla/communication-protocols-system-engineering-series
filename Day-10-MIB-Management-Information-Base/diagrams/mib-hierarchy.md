# MIB Hierarchy

## Explanation
This diagram shows the OID path from ISO to the MIB-II branch and then into standard and enterprise-managed objects.

## Mermaid
```mermaid
graph LR
    A[iso 1] --> B[org 3]
    B --> C[dod 6]
    C --> D[internet 1]
    D --> E[mgmt 2]
    E --> F[mib-2 1]
    F --> G[system]
    F --> H[interfaces]
    D --> I[enterprises]
    I --> J[Avantel 99999]
```

## Real-World Relevance
Operators navigate this hierarchy to discover object ownership, vendor extensions, and monitoring scope.

## Learning Outcomes
- Understand dotted OID structure
- Recognize standard and enterprise branches
- Explain how MIB trees support SNMP navigation
