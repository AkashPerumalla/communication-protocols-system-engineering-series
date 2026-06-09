public final class SNMPManager {
    public static void main(String[] args) {
        SNMPAgentSimulator agent = new SNMPAgentSimulator();

        printGet(agent, "1.3.6.1.2.1.1.5.0", "sysName.0");
        printGet(agent, "1.3.6.1.2.1.1.1.0", "sysDescr.0");
        printGet(agent, "1.3.6.1.2.1.1.3.0", "sysUpTime.0");
        printGet(agent, "1.3.6.1.2.1.1.6.0", "sysLocation.0");
    }

    private static void printGet(SNMPAgentSimulator agent, String oid, String label) {
        System.out.println("GET " + label);
        System.out.println("Response = " + agent.get(oid).format());
        System.out.println();
    }
}
