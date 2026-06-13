import java.util.List;
import java.util.Map;

public final class SnmpManagerSimulator {
    private final SnmpAgentSimulator agent;
    private final BulkRetrievalService bulkRetrievalService;
    private final DashboardService dashboardService;

    public SnmpManagerSimulator(SnmpAgentSimulator agent) {
        this.agent = agent;
        this.bulkRetrievalService = new BulkRetrievalService(agent.repository());
        this.dashboardService = new DashboardService(bulkRetrievalService);
    }

    public SnmpResponse get(String oid) {
        return agent.get(oid);
    }

    public SnmpResponse getNext(String oid) {
        return agent.getNext(oid);
    }

    public List<SnmpResponse> getBulk(String oid, int nonRepeaters, int maxRepetitions) {
        return agent.getBulk(oid, nonRepeaters, maxRepetitions);
    }

    public List<Oid> walk(String oid) {
        return agent.walk(oid);
    }

    public List<Oid> discover(String oid, int steps) {
        return agent.discover(oid, steps);
    }

    public String comparePolling(String oid, int repetitions) {
        PollingStatistics getStats = new PollingStatistics();
        PollingStatistics bulkStats = new PollingStatistics();

        for (int index = 0; index < repetitions; index++) {
            getStats.recordRequest();
            getStats.recordResponse(agent.get(oid));
        }

        bulkStats.recordRequest();
        bulkStats.recordResponses(agent.getBulk(oid, 0, repetitions));

        StringBuilder builder = new StringBuilder();
        builder.append("Performance Comparison\n");
        builder.append("GET | ").append(getStats.summaryLine()).append('\n');
        builder.append("GETBULK | ").append(bulkStats.summaryLine()).append('\n');
        builder.append("Efficiency Gain: ").append(String.format("%.2f", bulkStats.efficiencyRatio() - getStats.efficiencyRatio())).append('\n');
        return builder.toString();
    }

    public String compareOperations(String oid, int repetitions) {
        PollingStatistics getStats = new PollingStatistics();
        PollingStatistics nextStats = new PollingStatistics();
        PollingStatistics bulkStats = new PollingStatistics();
        String currentNextOid = oid;

        for (int index = 0; index < repetitions; index++) {
            getStats.recordRequest();
            getStats.recordResponse(agent.get(oid));

            nextStats.recordRequest();
            SnmpResponse nextResponse = agent.getNext(currentNextOid);
            nextStats.recordResponse(nextResponse);
            if ("OK".equals(nextResponse.status())) {
                currentNextOid = nextResponse.resolvedOid();
            }
        }

        bulkStats.recordRequest();
        bulkStats.recordResponses(agent.getBulk(oid, 0, repetitions));

        StringBuilder builder = new StringBuilder();
        builder.append("Performance Comparison\n");
        builder.append("GET | ").append(getStats.summaryLine()).append('\n');
        builder.append("GETNEXT | ").append(nextStats.summaryLine()).append('\n');
        builder.append("GETBULK | ").append(bulkStats.summaryLine()).append('\n');
        builder.append("Expected Winner: GETBULK\n");
        return builder.toString();
    }

    public String monitoringSummary(DeviceProfile profile, Map<String, String> requestedValues) {
        StringBuilder builder = new StringBuilder();
        builder.append(profile.deviceName()).append('\n');
        builder.append("Status: ").append(profile.status()).append('\n');
        builder.append("CPU: ").append(profile.cpuPercent()).append("%\n");
        builder.append("Memory: ").append(profile.memoryPercent()).append("%\n");
        builder.append("Uptime: ").append(profile.uptime()).append('\n');
        requestedValues.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> builder.append(entry.getKey()).append(": ").append(entry.getValue()).append('\n'));
        return builder.toString();
    }

    public String telecomSummary() {
        DeviceProfile profile = SampleData.deviceProfiles().get(2);
        return monitoringSummary(profile, Map.of(
                "RF Power", agent.get("1.3.6.1.4.1.55555.1.1.0").value(),
                "BER", agent.get("1.3.6.1.4.1.55555.1.2.0").value(),
                "Carrier Lock", agent.get("1.3.6.1.4.1.55555.1.3.0").value(),
                "Frequency", agent.get("1.3.6.1.4.1.55555.1.4.0").value()));
    }

    public String nocDashboard() {
        return dashboardService.buildDashboard(SampleData.deviceProfiles(), SampleData.dashboardHighlights());
    }

    public String captureCriticalSummary(DeviceProfile profile) {
        return dashboardService.criticalOidsSummary(profile);
    }
}
