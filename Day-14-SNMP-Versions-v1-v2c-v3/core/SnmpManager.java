public final class SnmpManager {
    private final String name;

    public SnmpManager(String name) {
        ValidationUtils.requireNonBlank(name, "name");
        this.name = name;
    }

    public String name() {
        return name;
    }

    public SnmpResponse sendRequest(SnmpAgent agent, SnmpRequest request) {
        ValidationUtils.requireNonNull(agent, "agent");
        ValidationUtils.requireNonNull(request, "request");
        return agent.handleRequest(request);
    }

    public long compareOperationalCost(SnmpVersion version, SnmpRequest.Operation operation) {
        long versionCost = switch (version) {
            case SNMPv1 -> 1L;
            case SNMPv2c -> 2L;
            case SNMPv3 -> 4L;
        };
        long operationCost = switch (operation) {
            case GET -> 1L;
            case GETNEXT -> 2L;
            case GETBULK -> 3L;
        };
        return versionCost * operationCost;
    }
}
