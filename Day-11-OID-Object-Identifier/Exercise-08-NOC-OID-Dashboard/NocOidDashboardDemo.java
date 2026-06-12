import java.util.ArrayList;
import java.util.List;

public final class NocOidDashboardDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        OidConsole.printBanner("DAY 11 - EXERCISE 08 - NOC OID DASHBOARD", "NOC DASHBOARD");

        List<DeviceProfile> devices = repository.devices();
        List<String[]> rows = new ArrayList<>();
        int cpuTotal = 0;
        int memoryTotal = 0;
        for (DeviceProfile device : devices) {
            rows.add(new String[]{
                    device.hostname(),
                    device.status(),
                    String.valueOf(device.cpuPercent()),
                    String.valueOf(device.memoryPercent()),
                    device.uptime(),
                    String.join(", ", device.criticalOids())
            });
            cpuTotal += device.cpuPercent();
            memoryTotal += device.memoryPercent();
        }

        OidConsole.printTable("Hostname         | Status   | CPU  | Memory | Uptime     | Critical OIDs", rows);
        System.out.println("Operational Summary");
        System.out.println("Devices Online: " + devices.stream().filter(device -> "ONLINE".equals(device.status())).count());
        System.out.println("Devices Degraded: " + devices.stream().filter(device -> "DEGRADED".equals(device.status())).count());
        System.out.println("Average CPU: " + (cpuTotal / devices.size()) + "%");
        System.out.println("Average Memory: " + (memoryTotal / devices.size()) + "%");
    }
}
