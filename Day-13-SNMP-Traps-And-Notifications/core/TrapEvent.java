import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record TrapEvent(
        Instant timestamp,
        String deviceName,
        String sourceIp,
        TrapOid trapOid,
        TrapType trapType,
        TrapSeverity severity,
        String description,
        boolean acknowledged,
        String eventId,
        Map<String, String> attributes) {

    public TrapEvent {
        ValidationUtils.requireNonNull(timestamp, "timestamp");
        ValidationUtils.requireNonBlank(deviceName, "deviceName");
        ValidationUtils.requireNonBlank(sourceIp, "sourceIp");
        ValidationUtils.requireNonNull(trapOid, "trapOid");
        ValidationUtils.requireNonNull(trapType, "trapType");
        ValidationUtils.requireNonNull(severity, "severity");
        ValidationUtils.requireNonBlank(description, "description");
        ValidationUtils.requireNonBlank(eventId, "eventId");
        Map<String, String> safeAttributes = attributes == null ? Map.of() : Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        attributes = safeAttributes;
    }
}
