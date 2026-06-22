# Layered Spring Boot Architecture

```mermaid
graph LR
    A[Controller Layer] --> B[DTO + Mapper Layer]
    B --> C[Service Layer]
    C --> D[Repository Layer]
    D --> E[Entity Layer]
    A --> F[Exception Layer]
    A --> G[Validation Layer]
    A --> H[Audit Layer]
    A --> I[Config Layer]
```
