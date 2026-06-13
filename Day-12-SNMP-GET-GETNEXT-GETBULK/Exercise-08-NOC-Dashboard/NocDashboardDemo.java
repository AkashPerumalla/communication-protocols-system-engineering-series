public final class NocDashboardDemo {
    private NocDashboardDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 08 - NOC DASHBOARD", "Operational summary"));
        System.out.print(manager.nocDashboard());

        for (DeviceProfile profile : SampleData.deviceProfiles()) {
            System.out.print(manager.captureCriticalSummary(profile));
        }
    }
}
