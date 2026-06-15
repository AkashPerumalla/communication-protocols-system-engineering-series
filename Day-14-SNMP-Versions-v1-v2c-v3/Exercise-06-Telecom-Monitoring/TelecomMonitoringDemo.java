public final class TelecomMonitoringDemo {
    private TelecomMonitoringDemo() {
    }

    public static void main(String[] args) {
        SnmpAgent agent = SampleData.createAgent();
        SnmpManager manager = new SnmpManager("Day14-Telecom-Manager");
        TelecomDeviceProfile profile = SampleData.telecomProfile();

        SnmpResponse v2cResponse = manager.sendRequest(agent, new SnmpRequest(
                SnmpVersion.SNMPv2c,
                SnmpRequest.Operation.GET,
                "1.3.6.1.4.1.55555.1.1.0",
                SampleData.v2cCommunity(),
                null,
                "Telecom RF poll"));
        SnmpResponse v3Response = manager.sendRequest(agent, new SnmpRequest(
                SnmpVersion.SNMPv3,
                SnmpRequest.Operation.GET,
                "1.3.6.1.4.1.55555.1.1.0",
                null,
                SampleData.shaUser(),
                "Telecom RF secure poll"));

        System.out.println(ConsoleFormatter.title("Telecom Monitoring Simulation"));
        System.out.println(ConsoleFormatter.keyValue("Device", profile.deviceName()));
        System.out.println(ConsoleFormatter.keyValue("RF Power", profile.rfPowerDbm()));
        System.out.println(ConsoleFormatter.keyValue("BER", profile.ber()));
        System.out.println(ConsoleFormatter.keyValue("Carrier Lock", profile.carrierLock()));
        System.out.println(ConsoleFormatter.keyValue("Frequency", profile.frequencyGhz()));
        System.out.println(ConsoleFormatter.keyValue("Symbol Rate", profile.symbolRateMsps()));
        System.out.println(ConsoleFormatter.keyValue("SNMPv2c Response Time (micros)", Long.toString(v2cResponse.responseTimeMicros())));
        System.out.println(ConsoleFormatter.keyValue("SNMPv3 Response Time (micros)", Long.toString(v3Response.responseTimeMicros())));
        System.out.println(ConsoleFormatter.keyValue("Operational Impact", "SNMPv3 adds authentication and privacy overhead for secure monitoring"));
        System.out.println("TELECOM MONITORING");
    }
}
