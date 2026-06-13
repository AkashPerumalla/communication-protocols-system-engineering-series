import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class NetworkMonitoringDemo {
    private NetworkMonitoringDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 06 - NETWORK MONITORING", "Router, switch, and firewall polling"));

        for (DeviceProfile profile : SampleData.deviceProfiles().stream()
                .filter(device -> device.deviceName().equals("Router-01") || device.deviceName().equals("Switch-01") || device.deviceName().equals("Firewall-01"))
                .collect(Collectors.toList())) {
            Map<String, String> telemetry = new LinkedHashMap<>();
            telemetry.put("Interfaces", String.valueOf(profile.criticalOids().size()));
            telemetry.put("Traffic Counters", trafficCounterFor(profile.deviceName(), manager));
            String summary = manager.monitoringSummary(profile, telemetry);
            System.out.print(summary);
            System.out.println("GET vs GETBULK");
            System.out.print(SnmpConsole.formatResponses("GETBULK Result", manager.getBulk(bulkBaseFor(profile.deviceName()), 0, profile.criticalOids().size())));
            System.out.println();
        }
    }

    private static String bulkBaseFor(String deviceName) {
        return switch (deviceName) {
            case "Switch-01" -> "1.3.6.1.2.1.2";
            case "Router-01", "Firewall-01" -> "1.3.6.1.2.1.1";
            default -> "1.3.6.1.2.1.1";
        };
    }

    private static String trafficCounterFor(String deviceName, SnmpManagerSimulator manager) {
        return switch (deviceName) {
            case "Router-01" -> manager.get("1.3.6.1.2.1.2.2.1.16.1").value();
            case "Switch-01" -> manager.get("1.3.6.1.2.1.2.2.1.10.1").value();
            case "Firewall-01" -> manager.get("1.3.6.1.2.1.2.2.1.16.1").value();
            default -> manager.get("1.3.6.1.2.1.2.2.1.10.1").value();
        };
    }
}
