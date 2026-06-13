import java.util.List;

public final class SnmpGetBulkSimulatorDemo {
    private SnmpGetBulkSimulatorDemo() {
    }

    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        SnmpManagerSimulator manager = new SnmpManagerSimulator(new SnmpAgentSimulator(repository));

        System.out.print(SnmpConsole.header("DAY 12 - EXERCISE 03 - SNMP GETBULK", "System group retrieval"));
        List<SnmpResponse> responses = manager.getBulk("1.3.6.1.2.1.1", 0, 7);
        System.out.print(SnmpConsole.formatResponses("GETBULK Result", responses));
        int bulkRequests = 1;
        int getRequests = responses.size();
        int reduction = Math.max(0, getRequests - bulkRequests);
        double gain = bulkRequests == 0 ? 0.0d : (double) getRequests / bulkRequests;
        System.out.println("Request Count Reduction: " + reduction);
        System.out.println("Network Efficiency Gain: " + String.format("%.2fx", gain));
    }
}
