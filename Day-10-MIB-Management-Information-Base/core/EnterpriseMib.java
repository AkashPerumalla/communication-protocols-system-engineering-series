import java.util.List;

public final class EnterpriseMib {
    public static final String AVANTEL_ENTERPRISE_OID = "1.3.6.1.4.1.99999";

    private EnterpriseMib() {
    }

    public static List<MibObject> avantelObjects() {
        return List.of(
                new MibObject("1.3.6.1.4.1.99999.1.1", "deviceTemperature", "Gauge32", "48 C", false),
                new MibObject("1.3.6.1.4.1.99999.1.2", "rfPower", "Gauge32", "-42 dBm", false),
                new MibObject("1.3.6.1.4.1.99999.1.3", "ber", "Counter64", "1.0E-5", false),
                new MibObject("1.3.6.1.4.1.99999.1.4", "carrierStatus", "INTEGER", "LOCKED", false),
                new MibObject("1.3.6.1.4.1.99999.1.5", "terminalStatus", "INTEGER", "ONLINE", false)
        );
    }
}
