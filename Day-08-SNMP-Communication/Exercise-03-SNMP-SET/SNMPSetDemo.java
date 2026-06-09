public final class SNMPSetDemo {
    public static void main(String[] args) {
        SnmpMibAgent agent = DemoDataFactory.writableAgent();

        printSet(agent, "1.3.6.1.2.1.1.6.0", "Hyderabad", "SET sysLocation = Hyderabad");
        printSet(agent, "1.3.6.1.4.1.55555.1.1.0", "DOWN", "SET adminStatus = DOWN");
        printSet(agent, "1.3.6.1.2.1.1.1.0", "New Description", "SET sysDescr = New Description");
        printSet(agent, "1.3.6.1.4.1.55555.1.1.0", "PAUSED", "SET adminStatus = PAUSED");
    }

    private static void printSet(SnmpMibAgent agent, String oid, String value, String label) {
        System.out.println(label);
        System.out.println("Response = " + agent.set(oid, value).format());
        System.out.println();
    }
}
