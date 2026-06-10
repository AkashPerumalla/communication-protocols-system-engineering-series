public class MibBrowserDemo {
    public static void main(String[] args) {
        SnmpAgentCore agent = SnmpFixtures.mibBrowserAgent();

        printRow(agent, "1.3.6.1.2.1.1.1.0");
        printRow(agent, "1.3.6.1.2.1.1.3.0");
        printRow(agent, "1.3.6.1.2.1.2.2.1.8");

        System.out.println("Teach:");
        System.out.println("- OID Navigation");
        System.out.println("- MIB Structure");
        System.out.println("- MIB-II Objects");
    }

    private static void printRow(SnmpAgentCore agent, String oid) {
        MibObject object = agent.get(oid);
        System.out.println("OID: " + oid);
        System.out.println("Name: " + object.name());
        System.out.println("Type: " + object.type());
        System.out.println("Value: " + object.value());
        System.out.println();
    }
}
