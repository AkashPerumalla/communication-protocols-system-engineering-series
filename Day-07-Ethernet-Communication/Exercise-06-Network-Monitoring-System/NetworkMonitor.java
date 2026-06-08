import java.util.ArrayList;
import java.util.List;

public class NetworkMonitor {
    private final List<DeviceSimulator> devices = new ArrayList<>();

    public void add(DeviceSimulator d) { devices.add(d); }

    public void pollAndPrint() {
        System.out.println("--- Network Monitor Dashboard ---");
        for (DeviceSimulator d : devices) {
            DeviceSimulator.DeviceMetrics m = d.sample();
            System.out.printf("%s (%s): bw=%dkbps pkts=%d errors=%d status=%s\n", m.name, m.ip, m.bandwidthKbps, m.packetCount, m.errors, m.status);
        }
    }

    public static void main(String[] args) throws Exception {
        NetworkMonitor nm = new NetworkMonitor();
        nm.add(new DeviceSimulator("Router-1","10.0.0.254"));
        nm.add(new DeviceSimulator("Switch-1","10.0.0.1"));
        nm.add(new DeviceSimulator("Server-1","10.0.0.10"));

        nm.pollAndPrint();
    }
}
