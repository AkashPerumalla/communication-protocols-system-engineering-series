public final class Snmpv2cCommunicationDemo {
    private Snmpv2cCommunicationDemo() {
    }

    public static void main(String[] args) {
        SnmpAgent agent = SampleData.createAgent();
        SnmpManager manager = new SnmpManager("Day14-NOC-Manager");
        CommunityString community = SampleData.v2cCommunity();

        SnmpResponse getResponse = manager.sendRequest(agent, new SnmpRequest(
                SnmpVersion.SNMPv2c,
                SnmpRequest.Operation.GET,
                "1.3.6.1.2.1.1.3.0",
                community,
                null,
                "Uptime poll"));
        SnmpResponse nextResponse = manager.sendRequest(agent, new SnmpRequest(
                SnmpVersion.SNMPv2c,
                SnmpRequest.Operation.GETNEXT,
                "1.3.6.1.2.1.1.3.0",
                community,
                null,
                "Walk next object"));
        SnmpResponse bulkResponse = manager.sendRequest(agent, new SnmpRequest(
                SnmpVersion.SNMPv2c,
                SnmpRequest.Operation.GETBULK,
                "1.3.6.1.2.1.1.3.0",
                community,
                null,
                "Bulk poll"));

        System.out.println(ConsoleFormatter.title("SNMPv2c Communication Simulation"));
        System.out.println(ConsoleFormatter.keyValue("Community Validation", Boolean.toString(getResponse.success())));
        System.out.println(ConsoleFormatter.keyValue("GET Result", getResponse.value()));
        System.out.println(ConsoleFormatter.keyValue("GET Response Time (micros)", Long.toString(getResponse.responseTimeMicros())));
        System.out.println(ConsoleFormatter.keyValue("GETNEXT Result", nextResponse.value()));
        System.out.println(ConsoleFormatter.keyValue("GETNEXT Response Time (micros)", Long.toString(nextResponse.responseTimeMicros())));
        System.out.println(ConsoleFormatter.keyValue("GETBULK Result", bulkResponse.value()));
        System.out.println(ConsoleFormatter.keyValue("GETBULK Response Time (micros)", Long.toString(bulkResponse.responseTimeMicros())));
        System.out.println("SNMPv2c SUCCESS");
    }
}
