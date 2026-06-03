# Echo Server Architecture

```mermaid
graph LR
    Client -->|TCP connect| Server[Echo Server]
    Server -->|Accept/Read| Client
    Server -->|Write/Echo| Client
```
