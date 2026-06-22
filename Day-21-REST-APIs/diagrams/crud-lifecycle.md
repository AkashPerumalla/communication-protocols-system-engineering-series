# CRUD Lifecycle

```mermaid
flowchart TD
    Create[POST /api/devices] --> Stored[Device persisted]
    Stored --> ReadOne[GET /api/devices/{id}]
    Stored --> ReadMany[GET /api/devices]
    ReadOne --> Update[PUT /api/devices/{id}]
    Update --> Search[GET /api/devices/search]
    Search --> Filter[Status / Type / Location filters]
    Update --> Delete[DELETE /api/devices/{id}]
    Delete --> Audit[Audit log entry recorded]
```
