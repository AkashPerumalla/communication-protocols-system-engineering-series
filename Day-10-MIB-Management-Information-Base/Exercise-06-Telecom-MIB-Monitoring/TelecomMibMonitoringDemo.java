import java.util.List;

public final class TelecomMibMonitoringDemo {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 06 - TELECOM MIB MONITORING", "Telecom Terminal 01");
        printTelecomTerminal(repository.telecomObjects());
    }

    private static void printTelecomTerminal(List<MibObject> objects) {
        for (MibObject object : objects) {
            if ("ber".equalsIgnoreCase(object.name())) {
                System.out.println("BER: " + object.value());
            } else {
                MibConsole.printNameValue(object.name(), object.value());
            }
        }
    }
}
