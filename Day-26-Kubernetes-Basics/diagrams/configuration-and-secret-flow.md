# Configuration And Secret Flow

```mermaid
graph TB
    ConfigMap -->|SPRING_PROFILES_ACTIVE| Pod[Application Pod]
    ConfigMap -->|PLATFORM_NAMESPACE| Pod
    Secret -->|DB Credentials| Pod
    Pod -->|JDBC| MySQL[(MySQL Service)]
```
