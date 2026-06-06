```mermaid
graph LR
    Master[Master]
    Slave1[Slave 1]
    Slave2[Slave 2]
    Slave3[Slave 3]
    Master ---|polls| Slave1
    Master ---|polls| Slave2
    Master ---|polls| Slave3
    note right of Master: Master initiates all transactions
```
