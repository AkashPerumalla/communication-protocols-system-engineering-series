import java.util.List;

public final class AlarmManagementDemo {
    private AlarmManagementDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 04 - ALARM MANAGEMENT", "Trap to alarm lifecycle"));
        EventRepository repository = new EventRepository();
        AlarmManager alarmManager = new AlarmManager(repository);
        List<TrapEvent> traps = SampleData.alarmTraps();
        for (TrapEvent trap : traps) {
            Alarm alarm = alarmManager.createAlarm(trap);
            System.out.print(ConsoleFormatter.section("Created Alarm"));
            System.out.print(ConsoleFormatter.formatAlarm(alarm));
        }
        System.out.println("ALARM CREATED");
    }
}
