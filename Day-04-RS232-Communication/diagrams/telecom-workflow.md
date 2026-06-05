```mermaid
sequenceDiagram
    Technician->>Console: Connect
    Console->>TelecomDevice: SHOW CONFIG
    TelecomDevice-->>Console: CONFIG DATA
    Technician->>Console: SET FREQ 1450
    Console->>TelecomDevice: SET FREQ 1450
    TelecomDevice-->>Console: OK
    Technician->>Console: SAVE
    TelecomDevice-->>Console: SAVED
```
