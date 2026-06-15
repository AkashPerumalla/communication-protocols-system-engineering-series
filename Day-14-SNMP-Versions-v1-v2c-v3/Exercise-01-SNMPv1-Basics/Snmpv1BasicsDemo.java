public final class Snmpv1BasicsDemo {
    private Snmpv1BasicsDemo() {
    }

    public static void main(String[] args) {
        SnmpAgent agent = SampleData.createAgent();
        SnmpManager manager = new SnmpManager("Day14-NOC-Manager");
        SnmpRequest request = new SnmpRequest(
                SnmpVersion.SNMPv1,
                SnmpRequest.Operation.GET,
                "1.3.6.1.2.1.1.1.0",
                SampleData.v1Community(),
                null,
                "System description poll");
        SnmpResponse response = manager.sendRequest(agent, request);

        System.out.println(ConsoleFormatter.title("SNMPv1 Basics Simulation"));
        System.out.println(ConsoleFormatter.keyValue("Manager", manager.name()));
        System.out.println(ConsoleFormatter.keyValue("Agent", agent.name()));
        System.out.println(ConsoleFormatter.keyValue("Version", response.version().displayName()));
        System.out.println(ConsoleFormatter.keyValue("Community", request.communityString().value()));
        System.out.println(ConsoleFormatter.keyValue("OID", response.oid()));
        System.out.println(ConsoleFormatter.keyValue("Returned Value", response.value()));
        System.out.println(ConsoleFormatter.keyValue("Response Time (micros)", Long.toString(response.responseTimeMicros())));
        System.out.println("SNMPv1 SUCCESS");
    }
}
