import java.time.Instant;

public record Alarm(
        String alarmId,
        TrapSeverity severity,
        String device,
        AlarmStatus status,
        String description,
        Instant createdTime,
        String sourceEventId,
        TrapType trapType) {

    public Alarm {
        ValidationUtils.requireNonBlank(alarmId, "alarmId");
        ValidationUtils.requireNonNull(severity, "severity");
        ValidationUtils.requireNonBlank(device, "device");
        ValidationUtils.requireNonNull(status, "status");
        ValidationUtils.requireNonBlank(description, "description");
        ValidationUtils.requireNonNull(createdTime, "createdTime");
        ValidationUtils.requireNonBlank(sourceEventId, "sourceEventId");
        ValidationUtils.requireNonNull(trapType, "trapType");
    }

    public Alarm withStatus(AlarmStatus newStatus) {
        return new Alarm(alarmId, severity, device, newStatus, description, createdTime, sourceEventId, trapType);
    }
}
