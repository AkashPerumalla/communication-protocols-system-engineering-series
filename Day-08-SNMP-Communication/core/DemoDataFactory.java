import java.util.List;

public final class DemoDataFactory {
    private DemoDataFactory() {
    }

    public static SnmpMibAgent systemAgent() {
        return new SnmpMibAgent()
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.1.0"), "sysDescr.0", "Cisco Router Simulator", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.2.0"), "sysObjectID.0", "1.3.6.1.4.1.9.1.1", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.3.0"), "sysUpTime.0", "1856321", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.4.0"), "sysContact.0", "noc@example.com", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.5.0"), "sysName.0", "Router-01", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.6.0"), "sysLocation.0", "Hyderabad NOC", SnmpAccessMode.READ_WRITE, value -> !value.isBlank()))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.7.0"), "sysServices.0", "72", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.1.1"), "ifIndex.1", "1", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.2.1"), "ifDescr.1", "GigabitEthernet0/0", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.7.1"), "ifAdminStatus.1", "UP", SnmpAccessMode.READ_WRITE, value -> value.equals("UP") || value.equals("DOWN")))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.4.1.55555.1.1.0"), "adminStatus.0", "UP", SnmpAccessMode.READ_WRITE, value -> value.equals("UP") || value.equals("DOWN")));
    }

    public static SnmpMibAgent walkAgent() {
        return new SnmpMibAgent()
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.1.0"), "sysDescr.0", "Network Core Router", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.2.0"), "sysObjectID.0", "1.3.6.1.4.1.9.1.1", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.3.0"), "sysUpTime.0", "3748291", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.4.0"), "sysContact.0", "noc@example.com", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.5.0"), "sysName.0", "Core-Router-01", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.1.6.0"), "sysLocation.0", "DC-01, Hyderabad", SnmpAccessMode.READ_WRITE, value -> !value.isBlank()))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.1.1"), "ifIndex.1", "1", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.2.1"), "ifDescr.1", "GigabitEthernet0/0", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.3.1"), "ifType.1", "ethernetCsmacd", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.8.1"), "ifOperStatus.1", "up", SnmpAccessMode.READ_ONLY))
                .register(new SnmpVariable(SnmpOid.of("1.3.6.1.2.1.2.2.1.16.1"), "ifOutOctets.1", "439887332", SnmpAccessMode.READ_ONLY));
    }

    public static SnmpMibAgent writableAgent() {
        return systemAgent();
    }

    public static List<SnmpTrap> trapSequence() {
        return List.of(
                new SnmpTrap("192.168.1.10", SnmpTrapSeverity.CRITICAL, "LINK_DOWN", "Interface Gi0/1 is down"),
                new SnmpTrap("192.168.1.10", SnmpTrapSeverity.INFO, "LINK_UP", "Interface Gi0/1 is up"),
                new SnmpTrap("192.168.1.20", SnmpTrapSeverity.CRITICAL, "POWER_FAILURE", "Primary PSU reported failure"),
                new SnmpTrap("192.168.1.30", SnmpTrapSeverity.MAJOR, "HIGH_TEMPERATURE", "Device temperature crossed threshold"),
                new SnmpTrap("192.168.1.40", SnmpTrapSeverity.MINOR, "LOW_SIGNAL", "Carrier signal margin is low")
        );
    }

    public static List<MonitoredDevice> networkDevices() {
        return List.of(
                new MonitoredDevice("Router-01", "Router", "10.10.1.1")
                        .metric("Status", "ONLINE")
                        .metric("CPU Usage", "32%")
                        .metric("Memory Usage", "61%")
                        .metric("Temperature", "41°C")
                        .metric("Bandwidth", "820 Mbps")
                        .metric("Interface Status", "UP"),
                new MonitoredDevice("Switch-01", "Switch", "10.10.1.2")
                        .metric("Status", "ONLINE")
                        .metric("CPU Usage", "21%")
                        .metric("Memory Usage", "48%")
                        .metric("Temperature", "37°C")
                        .metric("Bandwidth", "640 Mbps")
                        .metric("Interface Status", "UP"),
                new MonitoredDevice("Firewall-01", "Firewall", "10.10.1.3")
                        .metric("Status", "ONLINE")
                        .metric("CPU Usage", "54%")
                        .metric("Memory Usage", "66%")
                        .metric("Temperature", "45°C")
                        .metric("Bandwidth", "510 Mbps")
                        .metric("Interface Status", "UP"),
                new MonitoredDevice("Server-01", "Server", "10.10.1.10")
                        .metric("Status", "OFFLINE")
                        .metric("CPU Usage", "0%")
                        .metric("Memory Usage", "0%")
                        .metric("Temperature", "0°C")
                        .metric("Bandwidth", "0 Mbps")
                        .metric("Interface Status", "DOWN")
        );
    }

    public static List<MonitoredDevice> telecomDevices() {
        return List.of(
                new MonitoredDevice("Modem-01", "Modem", "172.16.10.11")
                        .metric("Signal Strength", "-53 dBm")
                        .metric("Tx Power", "42 dBm")
                        .metric("Rx Power", "-18 dBm")
                        .metric("BER", "0.000001")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "47°C"),
                new MonitoredDevice("BUC-01", "BUC", "172.16.10.12")
                        .metric("Signal Strength", "-49 dBm")
                        .metric("Tx Power", "44 dBm")
                        .metric("Rx Power", "-17 dBm")
                        .metric("BER", "0.000002")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "51°C"),
                new MonitoredDevice("LNB-01", "LNB", "172.16.10.13")
                        .metric("Signal Strength", "-61 dBm")
                        .metric("Tx Power", "N/A")
                        .metric("Rx Power", "-21 dBm")
                        .metric("BER", "0.000003")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "39°C"),
                new MonitoredDevice("HPA-01", "HPA", "172.16.10.14")
                        .metric("Signal Strength", "-47 dBm")
                        .metric("Tx Power", "48 dBm")
                        .metric("Rx Power", "-16 dBm")
                        .metric("BER", "0.000001")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "58°C"),
                new MonitoredDevice("Demod-01", "Demodulator", "172.16.10.15")
                        .metric("Signal Strength", "-52 dBm")
                        .metric("Tx Power", "N/A")
                        .metric("Rx Power", "-19 dBm")
                        .metric("BER", "0.0000009")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "43°C"),
                new MonitoredDevice("Router-02", "Router", "172.16.10.16")
                        .metric("Signal Strength", "-50 dBm")
                        .metric("Tx Power", "40 dBm")
                        .metric("Rx Power", "-20 dBm")
                        .metric("BER", "0.0000015")
                        .metric("Lock Status", "LOCKED")
                        .metric("Temperature", "44°C")
        );
    }

    public static AlarmManager seedAlarmManager() {
        AlarmManager alarmManager = new AlarmManager();
        alarmManager.raise(AlarmSeverity.CRITICAL, "Router-01", "High Temperature");
        alarmManager.raise(AlarmSeverity.MAJOR, "Firewall-01", "Power Failure");
        alarmManager.raise(AlarmSeverity.MINOR, "Modem-01", "Low Signal Strength");
        alarmManager.raise(AlarmSeverity.WARNING, "HPA-01", "Modem Unlock");
        return alarmManager;
    }

    public static List<DashboardSnapshot> dashboardSnapshots() {
        return List.of(
                new DashboardSnapshot(12, 1, 2, 3, 1, 0, 45, 52),
                new DashboardSnapshot(12, 1, 1, 2, 1, 1, 43, 50),
                new DashboardSnapshot(12, 1, 1, 2, 0, 1, 44, 51)
        );
    }
}
