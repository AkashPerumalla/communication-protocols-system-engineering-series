public final class PerformanceComparisonDemo {
    private PerformanceComparisonDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 05 - PERFORMANCE COMPARISON", "GET vs GETNEXT vs GETBULK"));
        System.out.print(manager.compareOperations("1.3.6.1.2.1.1.1.0", 6));
    }
}
