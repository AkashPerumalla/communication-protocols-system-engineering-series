public final class SatComTrapsDemo {
    private SatComTrapsDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 06 - SATCOM TRAPS", "Satellite monitoring and alarm handling"));
        SatComAlarmModel model = SampleData.satComAlarmModel();
        TrapEvent event = SampleData.satComTrap();
        System.out.print(model.describe());
        System.out.print(ConsoleFormatter.formatTrap(event));
        System.out.println("SATCOM ALARM");
    }
}
