import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SampleData {
    private SampleData() {
    }

    public static List<Oid> allOids() {
        List<Oid> oids = new ArrayList<>();

        Oid iso = branch("1", "iso", "International Organization for Standardization", "NODE");
        Oid org = branch("1.3", "org", "Organizations", "NODE");
        Oid dod = branch("1.3.6", "dod", "US Department of Defense", "NODE");
        Oid internet = branch("1.3.6.1", "internet", "Internet", "NODE");
        Oid mgmt = branch("1.3.6.1.2", "mgmt", "Management", "NODE");
        Oid mib2 = branch("1.3.6.1.2.1", "mib-2", "MIB-II", "NODE");
        Oid system = branch("1.3.6.1.2.1.1", "system", "System Group", "NODE");
        Oid interfaces = branch("1.3.6.1.2.1.2", "interfaces", "Interfaces Group", "NODE");
        Oid enterprises = branch("1.3.6.1.4.1", "enterprises", "Private Enterprises", "NODE");
        Oid telecom = branch("1.3.6.1.4.1.55555", "telecom", "Telecom Monitoring", "NODE");
        Oid satcom = branch("1.3.6.1.4.1.66666", "satcom", "SatCom Monitoring", "NODE");

        oids.add(iso);

        link(iso, org, oids);
        link(org, dod, oids);
        link(dod, internet, oids);
        link(internet, mgmt, oids);
        link(mgmt, mib2, oids);
        link(mib2, system, oids);
        link(mib2, interfaces, oids);
        link(internet, enterprises, oids);
        link(enterprises, telecom, oids);
        link(enterprises, satcom, oids);

        addLeaf(oids, system, "1.3.6.1.2.1.1.1.0", "sysDescr", "System description", "STRING", "Router-01 Integrated Services Router", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.2.0", "sysObjectID", "Object identifier", "OBJECT IDENTIFIER", "1.3.6.1.4.1.9.1.1", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.3.0", "sysUpTime", "System uptime", "TimeTicks", "1856321", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.4.0", "sysContact", "Administrative contact", "STRING", "noc@example.com", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.5.0", "sysName", "System hostname", "STRING", "Router-01", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.6.0", "sysLocation", "Physical location", "STRING", "Hyderabad NOC", "0");
        addLeaf(oids, system, "1.3.6.1.2.1.1.7.0", "sysServices", "Layer services", "INTEGER", "72", "0");

        addLeaf(oids, interfaces, "1.3.6.1.2.1.2.2.1.1.1", "ifIndex", "Interface index", "INTEGER", "1", "1");
        addLeaf(oids, interfaces, "1.3.6.1.2.1.2.2.1.5.1", "ifSpeed", "Interface speed", "Gauge32", "1000 Mbps", "1");
        addLeaf(oids, interfaces, "1.3.6.1.2.1.2.2.1.8.1", "ifOperStatus", "Interface operational status", "INTEGER", "UP", "1");
        addLeaf(oids, interfaces, "1.3.6.1.2.1.2.2.1.10.1", "ifInOctets", "Inbound octets", "Counter32", "124587326", "1");
        addLeaf(oids, interfaces, "1.3.6.1.2.1.2.2.1.16.1", "ifOutOctets", "Outbound octets", "Counter32", "81293477", "1");

        addLeaf(oids, telecom, "1.3.6.1.4.1.55555.1.1.0", "rfPower", "Receive RF power", "Gauge32", "-18 dBm", "0");
        addLeaf(oids, telecom, "1.3.6.1.4.1.55555.1.2.0", "ber", "Bit error rate", "Counter64", "0.000001", "0");
        addLeaf(oids, telecom, "1.3.6.1.4.1.55555.1.3.0", "carrierLock", "Carrier lock status", "INTEGER", "TRUE", "0");
        addLeaf(oids, telecom, "1.3.6.1.4.1.55555.1.4.0", "frequency", "Carrier frequency", "Gauge32", "14.250 GHz", "0");
        addLeaf(oids, telecom, "1.3.6.1.4.1.55555.1.5.0", "symbolRate", "Carrier symbol rate", "Gauge32", "30.000 Msps", "0");

        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.1.0", "terminalState", "Terminal state", "INTEGER", "ONLINE", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.2.0", "ebNo", "Eb/No", "Gauge32", "11.2 dB", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.3.0", "uplinkPower", "Uplink power", "Gauge32", "42 dBm", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.4.0", "downlinkPower", "Downlink power", "Gauge32", "-19 dBm", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.5.0", "modemStatus", "Modem status", "INTEGER", "LOCKED", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.6.0", "bucStatus", "BUC status", "INTEGER", "READY", "0");
        addLeaf(oids, satcom, "1.3.6.1.4.1.66666.1.7.0", "lnbStatus", "LNB status", "INTEGER", "READY", "0");

        return oids;
    }

    public static List<DeviceProfile> deviceProfiles() {
        return List.of(
                new DeviceProfile("Router-01", "Router", "ONLINE", 32, 61, "5d 02h 18m", List.of(
                        "1.3.6.1.2.1.1.5.0",
                        "1.3.6.1.2.1.2.2.1.10.1",
                        "1.3.6.1.2.1.2.2.1.16.1")),
                new DeviceProfile("Switch-01", "Switch", "ONLINE", 21, 48, "11d 09h 41m", List.of(
                        "1.3.6.1.2.1.1.5.0",
                        "1.3.6.1.2.1.2.2.1.8.1")),
                new DeviceProfile("Modem-01", "SatCom Modem", "ONLINE", 18, 44, "42d 16h 07m", List.of(
                        "1.3.6.1.4.1.55555.1.1.0",
                        "1.3.6.1.4.1.55555.1.2.0",
                        "1.3.6.1.4.1.66666.1.2.0")),
                new DeviceProfile("Firewall-01", "Firewall", "ONLINE", 39, 52, "24d 10h 22m", List.of(
                    "1.3.6.1.2.1.1.5.0",
                    "1.3.6.1.2.1.1.3.0",
                    "1.3.6.1.2.1.2.2.1.10.2")),
                new DeviceProfile("Hub-01", "Satellite Hub", "DEGRADED", 55, 67, "18d 03h 55m", List.of(
                        "1.3.6.1.4.1.66666.1.1.0",
                        "1.3.6.1.4.1.66666.1.5.0",
                        "1.3.6.1.4.1.66666.1.6.0"))
        );
    }

    public static Map<String, String> dashboardHighlights() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("alerts", "2");
        values.put("critical", "1");
        values.put("warning", "1");
        values.put("pollingInterval", "5s");
        return values;
    }

    private static Oid branch(String oid, String name, String description, String dataType) {
        return new Oid(oid, name, description, dataType, null);
    }

    private static void link(Oid parent, Oid child, List<Oid> sink) {
        child.attachParent(parent);
        parent.addChild(child);
        sink.add(child);
    }

    private static void addLeaf(List<Oid> sink, Oid parent, String oid, String name, String description, String dataType, String value, String instanceSuffix) {
        Oid leaf = new Oid(oid, name, description, dataType, value, instanceSuffix);
        leaf.attachParent(parent);
        parent.addChild(leaf);
        sink.add(leaf);
    }
}
