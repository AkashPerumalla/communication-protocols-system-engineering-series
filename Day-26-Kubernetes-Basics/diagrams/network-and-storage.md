# Network And Storage

```mermaid
graph LR
    AppPod[day26-platform Pod] -->|TCP 3306| MysqlPod[day26-mysql Pod]
    Policy[NetworkPolicy] -. allows .-> MysqlPod
    PVC[day26-mysql-pvc] --> MysqlPod
    PV[day26-mysql-pv] --> PVC
```
