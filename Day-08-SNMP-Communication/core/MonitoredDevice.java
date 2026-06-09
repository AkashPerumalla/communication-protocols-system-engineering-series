import java.util.LinkedHashMap;
import java.util.Map;

public final class MonitoredDevice {
    private final String name;
    private final String type;
    private final String ipAddress;
    private final LinkedHashMap<String, String> metrics = new LinkedHashMap<>();

    public MonitoredDevice(String name, String type, String ipAddress) {
        this.name = name;
        this.type = type;
        this.ipAddress = ipAddress;
    }

    public MonitoredDevice metric(String metricName, String value) {
        metrics.put(metricName, value);
        return this;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String ipAddress() {
        return ipAddress;
    }

    public Map<String, String> metrics() {
        return new LinkedHashMap<>(metrics);
    }
}
