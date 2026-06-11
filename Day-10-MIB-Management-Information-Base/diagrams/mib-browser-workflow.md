# MIB Browser Workflow

## Explanation
A browser lets an engineer search by OID or name, inspect parent and child nodes, and walk an entire branch.

## Mermaid
```mermaid
flowchart TD
    A[Enter OID or Name] --> B{Lookup in MIB Tree}
    B -->|Found| C[Show Object Metadata]
    C --> D[Show Parent]
    C --> E[List Children]
    C --> F[Walk Branch]
    B -->|Not Found| G[Display Not Found]
```

## Real-World Relevance
MIB browsers are used during device onboarding, troubleshooting, and vendor interoperability checks.

## Learning Outcomes
- Trace object discovery flow
- Explain hierarchical navigation
- Use browser output for troubleshooting
