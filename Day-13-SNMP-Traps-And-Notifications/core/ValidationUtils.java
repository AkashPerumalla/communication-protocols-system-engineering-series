public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void requireNonNull(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
    }

    public static void requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
    }

    public static void ensure(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateTrapEvent(TrapEvent event) {
        requireNonNull(event, "trapEvent");
        requireNonNull(event.timestamp(), "timestamp");
        requireNonBlank(event.deviceName(), "deviceName");
        requireNonBlank(event.sourceIp(), "sourceIp");
        requireNonNull(event.trapOid(), "trapOid");
        requireNonNull(event.trapType(), "trapType");
        requireNonNull(event.severity(), "severity");
        requireNonBlank(event.description(), "description");
        requireNonBlank(event.eventId(), "eventId");
    }

    public static void validateAlarm(Alarm alarm) {
        requireNonNull(alarm, "alarm");
        requireNonBlank(alarm.alarmId(), "alarmId");
        requireNonNull(alarm.severity(), "severity");
        requireNonBlank(alarm.device(), "device");
        requireNonNull(alarm.status(), "status");
        requireNonBlank(alarm.description(), "description");
        requireNonNull(alarm.createdTime(), "createdTime");
        requireNonBlank(alarm.sourceEventId(), "sourceEventId");
        requireNonNull(alarm.trapType(), "trapType");
    }
}
