public final class OidDiscoveryDemo {
    private OidDiscoveryDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 04 - OID DISCOVERY", "MIB exploration with GETNEXT"));
        String currentOid = "1.3.6.1.2.1.1.1.0";
        for (int index = 0; index < 5; index++) {
            SnmpResponse response = manager.getNext(currentOid);
            System.out.println("Current OID: " + currentOid);
            System.out.println("Next OID: " + response.resolvedOid());
            System.out.println("Object Name: " + response.objectName());
            System.out.println("Value: " + response.value());
            System.out.println("OID Discovery");
            System.out.println();
            currentOid = response.resolvedOid();
        }
    }
}
