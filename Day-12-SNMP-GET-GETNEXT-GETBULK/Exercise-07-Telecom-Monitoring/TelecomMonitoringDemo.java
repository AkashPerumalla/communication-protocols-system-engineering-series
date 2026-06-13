public final class TelecomMonitoringDemo {
    private TelecomMonitoringDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 07 - TELECOM MONITORING", "RF telemetry and polling efficiency"));
        System.out.print(manager.telecomSummary());
        System.out.println("GETBULK Result");
        System.out.print(SnmpConsole.formatResponses("Telecom Bulk Retrieval", manager.getBulk("1.3.6.1.4.1.55555", 0, 5)));
        System.out.print(manager.compareOperations("1.3.6.1.4.1.55555.1.1.0", 4));
    }
}
