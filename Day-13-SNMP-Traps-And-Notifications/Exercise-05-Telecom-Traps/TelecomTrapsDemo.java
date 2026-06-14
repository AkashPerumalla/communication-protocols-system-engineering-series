public final class TelecomTrapsDemo {
    private TelecomTrapsDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 05 - TELECOM TRAPS", "RF alarm handling in a telecom NMS"));
        TelecomAlarmModel model = SampleData.telecomAlarmModel();
        TrapEvent event = SampleData.telecomTrap();
        System.out.print(model.describe());
        System.out.print(ConsoleFormatter.formatTrap(event));
        System.out.println("BER ALARM");
    }
}
