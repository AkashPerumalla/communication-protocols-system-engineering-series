import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class SampleData {
    private SampleData() {
    }

    public static SnmpAgent createAgent() {
        return new SnmpAgent("Core-NMS-Agent", mibData());
    }

    public static CommunityString v1Community() {
        return new CommunityString("telecom-read-only");
    }

    public static CommunityString v2cCommunity() {
        return new CommunityString("noc-monitor");
    }

    public static SnmpUser shaUser() {
        return new SnmpUser(
                "noc-sha-operator",
                AuthenticationProtocol.SHA,
                PrivacyProtocol.AES,
                SecurityLevel.AUTH_PRIV,
                "sha-auth-secret-14",
                "aes-privacy-secret-14");
    }

    public static SnmpUser md5User() {
        return new SnmpUser(
                "noc-md5-operator",
                AuthenticationProtocol.MD5,
                PrivacyProtocol.NONE,
                SecurityLevel.AUTH_NO_PRIV,
                "md5-auth-secret-14",
                null);
    }

    public static TelecomDeviceProfile telecomProfile() {
        return new TelecomDeviceProfile("SatHub-14", "-42 dBm", "1.2E-6", "LOCKED", "14.250 GHz", "45 Msps");
    }

    public static SatComDeviceProfile satComProfile() {
        return new SatComDeviceProfile("Gateway-14", "9.4 dB", "ONLINE", "ONLINE", "ONLINE", "OPERATIONAL");
    }

    public static List<MonitoringSession> secureNocSessions() {
        return List.of(
                new MonitoringSession("S-001", "Router-01", SnmpVersion.SNMPv2c, SecurityLevel.NO_AUTH_NO_PRIV, "Community: noc-monitor", "None", "GREEN", 580L),
                new MonitoringSession("S-002", "Switch-01", SnmpVersion.SNMPv3, SecurityLevel.AUTH_NO_PRIV, "SHA", "None", "GREEN", 1220L),
                new MonitoringSession("S-003", "Firewall-01", SnmpVersion.SNMPv3, SecurityLevel.AUTH_PRIV, "SHA", "AES", "GREEN", 1510L),
                new MonitoringSession("S-004", "Modem-01", SnmpVersion.SNMPv2c, SecurityLevel.NO_AUTH_NO_PRIV, "Community: telecom-read-only", "None", "AMBER", 610L),
                new MonitoringSession("S-005", "Hub-01", SnmpVersion.SNMPv1, SecurityLevel.NO_AUTH_NO_PRIV, "Community: legacy-hub", "None", "GREEN", 890L)
        );
    }

    public static List<MonitoringSession> migrationSessions() {
        return List.of(
                new MonitoringSession("M-1", "Migration-Step-1", SnmpVersion.SNMPv1, SecurityLevel.NO_AUTH_NO_PRIV, "Community: public", "None", "Legacy exposure", 860L),
                new MonitoringSession("M-2", "Migration-Step-2", SnmpVersion.SNMPv2c, SecurityLevel.NO_AUTH_NO_PRIV, "Community: noc-monitor", "None", "Controlled exposure", 560L),
                new MonitoringSession("M-3", "Migration-Step-3", SnmpVersion.SNMPv3, SecurityLevel.AUTH_PRIV, "SHA", "AES", "Protected production path", 1490L)
        );
    }

    public static Map<String, String> mibData() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("1.3.6.1.2.1.1.1.0", "Satellite Transport NMS Agent");
        data.put("1.3.6.1.2.1.1.3.0", "34567890");
        data.put("1.3.6.1.2.1.2.2.1.10.1", "1144223344");
        data.put("1.3.6.1.2.1.2.2.1.16.1", "992233441");
        data.put("1.3.6.1.4.1.55555.1.1.0", "-42 dBm");
        data.put("1.3.6.1.4.1.55555.1.2.0", "1.2E-6");
        data.put("1.3.6.1.4.1.55555.1.3.0", "LOCKED");
        data.put("1.3.6.1.4.1.55555.1.4.0", "14.250 GHz");
        data.put("1.3.6.1.4.1.55555.1.5.0", "45 Msps");
        data.put("1.3.6.1.4.1.55555.2.1.0", "9.4 dB");
        data.put("1.3.6.1.4.1.55555.2.2.0", "ONLINE");
        data.put("1.3.6.1.4.1.55555.2.3.0", "ONLINE");
        data.put("1.3.6.1.4.1.55555.2.4.0", "ONLINE");
        data.put("1.3.6.1.4.1.55555.2.5.0", "OPERATIONAL");
        data.put("1.3.6.1.4.1.55555.3.1.0", "ROUTER-01");
        data.put("1.3.6.1.4.1.55555.3.2.0", "SWITCH-01");
        data.put("1.3.6.1.4.1.55555.3.3.0", "FIREWALL-01");
        data.put("1.3.6.1.4.1.55555.3.4.0", "MODEM-01");
        data.put("1.3.6.1.4.1.55555.3.5.0", "HUB-01");
        return data;
    }
}
