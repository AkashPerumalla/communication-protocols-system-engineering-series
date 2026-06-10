import java.util.List;

public final class SnmpFixtures {
    private SnmpFixtures() {
    }

    public static SnmpAgentCore routerAgent() {
        return new SnmpAgentCore("Router-01", List.of(
                new MibObject("1.3.6.1.2.1.1.1.0", "sysDescr", "STRING", "Router-01 Integrated Services Router", false),
                new MibObject("1.3.6.1.2.1.1.3.0", "sysUpTime", "TimeTicks", "1284372", false),
                new MibObject("1.3.6.1.2.1.1.5.0", "sysName", "STRING", "Router-01", false),
                new MibObject("1.3.6.1.2.1.1.6.0", "sysLocation", "STRING", "Data Center A / Rack 14", false)
        ));
    }

    public static SnmpAgentCore multiOidAgent() {
        return new SnmpAgentCore("Router-01", List.of(
                new MibObject("1.3.6.1.4.1.9999.1.1", "cpuUsage", "Gauge32", "35%", false),
                new MibObject("1.3.6.1.4.1.9999.1.2", "memoryUsage", "Gauge32", "61%", false),
                new MibObject("1.3.6.1.4.1.9999.1.3", "temperature", "Gauge32", "41°C", false),
                new MibObject("1.3.6.1.4.1.9999.1.4", "interfaceStatus", "INTEGER", "UP", false),
                new MibObject("1.3.6.1.4.1.9999.1.5", "bandwidth", "Gauge32", "920 Mbps", false)
        ));
    }

    public static SnmpAgentCore mibBrowserAgent() {
        return new SnmpAgentCore("MIB-Browser-Agent", List.of(
                new MibObject("1.3.6.1.2.1.1.1.0", "sysDescr", "STRING", "Generic MIB-II Demo Device", false),
                new MibObject("1.3.6.1.2.1.1.3.0", "sysUpTime", "TimeTicks", "90012", false),
                new MibObject("1.3.6.1.2.1.2.2.1.8", "ifOperStatus", "INTEGER", "1", false)
        ));
    }

    public static List<SnmpAgentCore> multiDeviceAgents() {
        return List.of(
                new SnmpAgentCore("Router-01", List.of(new MibObject("1", "status", "STRING", "ONLINE", false))),
                new SnmpAgentCore("Switch-01", List.of(new MibObject("1", "status", "STRING", "ONLINE", false))),
                new SnmpAgentCore("Firewall-01", List.of(new MibObject("1", "status", "STRING", "ONLINE", false))),
                new SnmpAgentCore("Server-01", List.of(new MibObject("1", "status", "STRING", "ONLINE", false)))
        );
    }

    public static List<DeviceTelemetry> networkDevices() {
        return List.of(
                new DeviceTelemetry("Router-01", 35, 61, 41, "UP", 920),
                new DeviceTelemetry("Switch-01", 21, 44, 39, "UP", 600),
                new DeviceTelemetry("Firewall-01", 49, 57, 46, "UP", 420),
                new DeviceTelemetry("Server-01", 63, 68, 52, "UP", 1100)
        );
    }

    public static List<TelecomTelemetry> telecomDevices() {
        return List.of(
                new TelecomTelemetry("Modem-01", 38, -52, 89, "1.2e-07", "LOCKED", 34),
                new TelecomTelemetry("BUC-01", 42, -46, 92, "8.1e-08", "LOCKED", 38),
                new TelecomTelemetry("LNB-01", 0, -61, 87, "1.5e-07", "LOCKED", 33),
                new TelecomTelemetry("HPA-01", 42, -44, 91, "6.8e-08", "LOCKED", 38),
                new TelecomTelemetry("Demod-01", 0, -49, 88, "9.0e-08", "LOCKED", 35)
        );
    }

    public static List<TrapEvent> trapEvents() {
        return List.of(
                new TrapEvent("Router-01", "CRITICAL", "LINK_DOWN", "GigabitEthernet0/1 lost carrier"),
                new TrapEvent("Router-01", "INFO", "LINK_UP", "GigabitEthernet0/1 restored carrier"),
                new TrapEvent("HPA-01", "CRITICAL", "POWER_FAILURE", "Output stage lost supply"),
                new TrapEvent("BUC-01", "MAJOR", "HIGH_TEMPERATURE", "Temperature exceeded 70C"),
                new TrapEvent("Modem-01", "CRITICAL", "MODEM_UNLOCK", "Carrier lock dropped")
        );
    }

    public static List<ManagedDeviceView> failureDevices() {
        return List.of(
                new ManagedDeviceView("Router-01", true, 5),
                new ManagedDeviceView("Switch-01", true, 9),
                new ManagedDeviceView("Firewall-01", false, 15),
                new ManagedDeviceView("Server-01", true, 7)
        );
    }

    public static DashboardSnapshot dashboardSnapshot() {
        return new DashboardSnapshot(15, 2, 3, 4, 47, 56);
    }
}
