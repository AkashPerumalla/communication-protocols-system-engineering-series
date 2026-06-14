import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TrapAgent {
    private final DeviceProfile deviceProfile;

    public TrapAgent(DeviceProfile deviceProfile) {
        ValidationUtils.requireNonNull(deviceProfile, "deviceProfile");
        this.deviceProfile = deviceProfile;
    }

    public TrapEvent generate(TrapType trapType, Instant timestamp, String description, Map<String, String> attributes) {
        ValidationUtils.requireNonNull(trapType, "trapType");
        ValidationUtils.requireNonNull(timestamp, "timestamp");
        ValidationUtils.requireNonBlank(description, "description");
        Map<String, String> safeAttributes = attributes == null ? Map.of() : new LinkedHashMap<>(attributes);
        String eventId = buildEventId(trapType, timestamp);
        return new TrapEvent(
                timestamp,
                deviceProfile.name(),
                deviceProfile.sourceIp(),
                trapType.trapOid(),
                trapType,
                trapType.defaultSeverity(),
                description,
                false,
                eventId,
                safeAttributes);
    }

    private String buildEventId(TrapType trapType, Instant timestamp) {
        return deviceProfile.name() + "-" + trapType.code() + "-" + timestamp.toEpochMilli();
    }
}
