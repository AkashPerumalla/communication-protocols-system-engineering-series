public final class SnmpWalkSimulatorDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        OidConsole.printBanner("DAY 11 - EXERCISE 05 - SNMP WALK", "SNMP WALK Simulator");
        System.out.println("WALK Result");
        System.out.print(manager.walkFlow("1.3.6.1.2.1.1"));
    }
}
