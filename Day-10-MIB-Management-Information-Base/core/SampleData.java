import java.util.List;

public final class SampleData {
    private SampleData() {
    }

    public static List<MibObject> systemGroupObjects() {
        return List.of(
                new MibObject("1.3.6.1.2.1.1.1.0", "sysDescr", "STRING", "Linux Server - Ubuntu 22.04", false),
                new MibObject("1.3.6.1.2.1.1.2.0", "sysObjectID", "OBJECT IDENTIFIER", "1.3.6.1.4.1.99999.1", false),
                new MibObject("1.3.6.1.2.1.1.3.0", "sysUpTime", "TimeTicks", "1284372", false),
                new MibObject("1.3.6.1.2.1.1.4.0", "sysContact", "STRING", "noc@avantel.example", false),
                new MibObject("1.3.6.1.2.1.1.5.0", "sysName", "STRING", "AVANTEL-NODE-01", false),
                new MibObject("1.3.6.1.2.1.1.6.0", "sysLocation", "STRING", "NOC-1 / Rack 14 / Slot 02", false),
                new MibObject("1.3.6.1.2.1.1.7.0", "sysServices", "INTEGER", "72", false)
        );
    }

    public static List<MibObject> interfaceObjects() {
        return List.of(
                new MibObject("1.3.6.1.2.1.2.1.0", "ifNumber", "INTEGER", "4", false),
                new MibObject("1.3.6.1.2.1.2.2.1.2.1", "ifDescr", "STRING", "eth0", false),
                new MibObject("1.3.6.1.2.1.2.2.1.5.1", "ifSpeed", "Gauge32", "1000 Mbps", false),
                new MibObject("1.3.6.1.2.1.2.2.1.10.1", "ifInOctets", "Counter32", "124587326", false),
                new MibObject("1.3.6.1.2.1.2.2.1.16.1", "ifOutOctets", "Counter32", "81293477", false),
                new MibObject("1.3.6.1.2.1.2.2.1.8.1", "ifOperStatus", "INTEGER", "UP", false)
        );
    }

    public static List<MibObject> telecomObjects() {
        return List.of(
                new MibObject("1.3.6.1.4.1.99999.2.1", "txPower", "Gauge32", "14.2 dBm", false),
                new MibObject("1.3.6.1.4.1.99999.2.2", "rxPower", "Gauge32", "-51.6 dBm", false),
                new MibObject("1.3.6.1.4.1.99999.2.3", "frequency", "Gauge32", "14.25 GHz", false),
                new MibObject("1.3.6.1.4.1.99999.2.4", "ber", "Counter64", "1.0E-5", false),
                new MibObject("1.3.6.1.4.1.99999.2.5", "carrierStatus", "INTEGER", "LOCKED", false),
                new MibObject("1.3.6.1.4.1.99999.2.6", "alarmState", "INTEGER", "CLEAR", false)
        );
    }

    public static List<MibObject> satcomObjects() {
        return List.of(
                new MibObject("1.3.6.1.4.1.99999.3.1", "bucStatus", "INTEGER", "READY", false),
                new MibObject("1.3.6.1.4.1.99999.3.2", "lnbStatus", "INTEGER", "READY", false),
                new MibObject("1.3.6.1.4.1.99999.3.3", "signalLevel", "Gauge32", "GOOD", false),
                new MibObject("1.3.6.1.4.1.99999.3.4", "ebNo", "Gauge32", "11.2 dB", false),
                new MibObject("1.3.6.1.4.1.99999.3.5", "frequency", "Gauge32", "14.25 GHz", false),
                new MibObject("1.3.6.1.4.1.99999.3.6", "terminalState", "INTEGER", "ONLINE", false)
        );
    }

    public static List<String[]> dashboardRows() {
        return List.of(
                new String[]{"Router-01", "ONLINE", "34", "61", "41 C", "CLEAR"},
                new String[]{"Switch-01", "ONLINE", "22", "45", "38 C", "CLEAR"},
                new String[]{"Server-01", "ONLINE", "58", "72", "49 C", "CLEAR"},
                new String[]{"Telecom-Terminal-01", "ONLINE", "29", "54", "46 C", "CLEAR"},
                new String[]{"SatCom-Terminal-01", "ONLINE", "31", "50", "44 C", "CLEAR"}
        );
    }

    public static List<MibObject> allObjects() {
        return List.of(
                systemGroupObjects(),
                interfaceObjects(),
                EnterpriseMib.avantelObjects(),
                telecomObjects(),
                satcomObjects()
        ).stream().flatMap(List::stream).toList();
    }
}
