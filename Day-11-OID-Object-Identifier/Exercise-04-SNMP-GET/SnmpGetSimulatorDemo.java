public final class SnmpGetSimulatorDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        OidConsole.printBanner("DAY 11 - EXERCISE 04 - SNMP GET", "SNMP GET Simulator");
        System.out.println(manager.getFlow("1.3.6.1.2.1.1.5.0"));
    }
}
