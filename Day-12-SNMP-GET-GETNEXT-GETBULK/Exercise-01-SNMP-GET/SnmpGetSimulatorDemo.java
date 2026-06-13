public final class SnmpGetSimulatorDemo {
    private SnmpGetSimulatorDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 01 - SNMP GET", "Single OID retrieval"));
        SnmpResponse response = manager.get("1.3.6.1.2.1.1.5.0");
        System.out.print(SnmpConsole.formatResponse("GET Response", response));
    }
}
