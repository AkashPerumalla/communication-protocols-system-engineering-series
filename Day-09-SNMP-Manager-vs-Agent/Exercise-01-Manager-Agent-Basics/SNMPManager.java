public class SNMPManager {
    public static void main(String[] args) {
        SNMPAgent agent = new SNMPAgent();

        printGet(agent, "1.3.6.1.2.1.1.5.0", "sysName");
        printGet(agent, "1.3.6.1.2.1.1.1.0", "sysDescr");
        printGet(agent, "1.3.6.1.2.1.1.6.0", "sysLocation");
        printGet(agent, "1.3.6.1.2.1.1.3.0", "sysUpTime");

        System.out.println("Teach:");
        System.out.println("- Manager Role");
        System.out.println("- Agent Role");
        System.out.println("- MIB Lookup");
        System.out.println("- Request Response Flow");
    }

    private static void printGet(SNMPAgent agent, String oid, String label) {
        System.out.println("Manager:");
        System.out.println("GET " + label);
        System.out.println("Agent:");
        MibObject object = agent.get(oid);
        System.out.println(object == null ? "NO SUCH OBJECT" : object.value());
        System.out.println();
    }
}
