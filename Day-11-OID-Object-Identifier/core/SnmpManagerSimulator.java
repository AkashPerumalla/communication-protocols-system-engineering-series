import java.util.List;

public final class SnmpManagerSimulator {
    private final SnmpAgentSimulator agent;

    public SnmpManagerSimulator(SnmpAgentSimulator agent) {
        this.agent = agent;
    }

    public String getFlow(String oid) {
        OidResponse response = agent.get(oid);
        return String.join(System.lineSeparator(),
                "MANAGER -> AGENT",
                "GET " + oid,
                "AGENT -> MANAGER",
                response.format());
    }

    public String getNextFlow(String oid) {
        OidResponse response = agent.getNext(oid);
        return String.join(System.lineSeparator(),
                "MANAGER -> AGENT",
                "GETNEXT " + oid,
                "AGENT -> MANAGER",
                response.format());
    }

    public String walkFlow(String oid) {
        List<OidResponse> responses = agent.walk(oid);
        StringBuilder builder = new StringBuilder();
        builder.append("MANAGER -> AGENT").append(System.lineSeparator());
        builder.append("WALK ").append(oid).append(System.lineSeparator());
        for (OidResponse response : responses) {
            builder.append(response.format()).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
