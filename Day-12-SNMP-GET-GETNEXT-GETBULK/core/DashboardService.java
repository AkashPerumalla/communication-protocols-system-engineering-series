import java.util.List;
import java.util.Map;

public final class DashboardService {
    private final BulkRetrievalService retrievalService;

    public DashboardService(BulkRetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    public String buildDashboard(List<DeviceProfile> devices, Map<String, String> highlights) {
        StringBuilder builder = new StringBuilder();
        builder.append("NOC DASHBOARD\n");
        builder.append("Devices Monitored: ").append(devices.size()).append('\n');
        for (DeviceProfile profile : devices) {
            builder.append(profile.deviceName()).append(" | ")
                    .append(profile.deviceType()).append(" | ")
                    .append(profile.status()).append(" | CPU ").append(profile.cpuPercent()).append("% | Memory ").append(profile.memoryPercent()).append("% | Uptime ").append(profile.uptime()).append('\n');
        }
        for (Map.Entry<String, String> entry : highlights.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
        }
        return builder.toString();
    }

    public String criticalOidsSummary(DeviceProfile profile) {
        StringBuilder builder = new StringBuilder();
        builder.append(profile.deviceName()).append(" Critical OIDs\n");
        for (String oid : profile.criticalOids()) {
            List<SnmpResponse> responses = retrievalService.retrieve(oid, 1);
            if (!responses.isEmpty()) {
                SnmpResponse response = responses.get(0);
                builder.append(response.objectName()).append(" = ").append(response.value()).append('\n');
            }
        }
        return builder.toString();
    }
}
