# Telemetry And Alarm Pipeline

```mermaid
graph LR
    Scheduler[Telemetry Scheduler] --> Telemetry[TelemetryRecord]
    Telemetry --> Evaluator[Alarm Service]
    Evaluator --> Alarm[AlarmEvent]
    Alarm --> Notify[Notification Service]
    Notify --> Channels[Email SMS Webhook Console]
```
