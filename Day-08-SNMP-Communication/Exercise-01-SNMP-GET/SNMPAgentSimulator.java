public final class SNMPAgentSimulator {
    private final SnmpMibAgent agent = DemoDataFactory.systemAgent();

    public SnmpResponse get(String oidText) {
        return agent.get(oidText);
    }
}
