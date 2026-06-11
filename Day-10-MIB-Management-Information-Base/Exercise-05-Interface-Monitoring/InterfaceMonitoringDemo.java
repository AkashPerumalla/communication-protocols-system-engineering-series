import java.util.List;

public final class InterfaceMonitoringDemo {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 05 - INTERFACE MONITORING", "IF-MIB Interface Monitoring Demo");
        printInterfaces(repository.interfaceObjects());
    }

    private static void printInterfaces(List<MibObject> objects) {
        for (MibObject object : objects) {
            MibConsole.printNameValue(object.name(), object.value() + " (" + object.oid() + ")");
        }
        System.out.println();
        System.out.println("Interface Summary");
        System.out.println("eth0 | 1000 Mbps | UP");
    }
}
