import java.util.List;

public final class SatComMibMonitoringDemo {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 07 - SATCOM MIB MONITORING", "Terminal: VSAT-01");
        printSatcomTerminal(repository.satcomObjects());
    }

    private static void printSatcomTerminal(List<MibObject> objects) {
        for (MibObject object : objects) {
            if ("ebNo".equalsIgnoreCase(object.name())) {
                System.out.println("Eb/No: " + object.value());
            } else {
                MibConsole.printNameValue(object.name(), object.value());
            }
        }
        System.out.println();
        System.out.println("Signal: GOOD");
    }
}
