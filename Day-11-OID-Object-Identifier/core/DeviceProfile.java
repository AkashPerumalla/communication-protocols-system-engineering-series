import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DeviceProfile {
    private final String hostName;
    private final String deviceType;
    private final String status;
    private final int cpuPercent;
    private final int memoryPercent;
    private final String uptime;
    private final List<String> criticalOids;

    public DeviceProfile(String hostName, String deviceType, String status, int cpuPercent, int memoryPercent, String uptime, List<String> criticalOids) {
        this.hostName = hostName;
        this.deviceType = deviceType;
        this.status = status;
        this.cpuPercent = cpuPercent;
        this.memoryPercent = memoryPercent;
        this.uptime = uptime;
        this.criticalOids = new ArrayList<>(criticalOids);
    }

    public String hostName() {
        return hostName;
    }

    public String hostname() {
        return hostName;
    }

    public String deviceType() {
        return deviceType;
    }

    public String status() {
        return status;
    }

    public int cpuPercent() {
        return cpuPercent;
    }

    public int memoryPercent() {
        return memoryPercent;
    }

    public String uptime() {
        return uptime;
    }

    public List<String> criticalOids() {
        return Collections.unmodifiableList(criticalOids);
    }
}
