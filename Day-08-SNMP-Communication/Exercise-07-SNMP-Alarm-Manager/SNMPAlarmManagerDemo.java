import java.util.List;

public final class SNMPAlarmManagerDemo {
    public static void main(String[] args) {
        AlarmManager alarmManager = DemoDataFactory.seedAlarmManager();

        List<AlarmRecord> active = alarmManager.activeAlarms();
        if (!active.isEmpty()) {
            alarmManager.clear(active.get(0).id());
        }

        System.out.println("ACTIVE ALARMS");
        printRecords(alarmManager.activeAlarms());

        System.out.println("CLEARED ALARMS");
        printRecords(alarmManager.clearedAlarms());
    }

    private static void printRecords(List<AlarmRecord> alarms) {
        for (AlarmRecord alarm : alarms) {
            System.out.println("ID       : " + alarm.id());
            System.out.println("Severity : " + alarm.severity());
            System.out.println("Device   : " + alarm.device());
            System.out.println("Timestamp: " + alarm.timestampText());
            System.out.println("Status   : " + alarm.status());
            System.out.println("Summary  : " + alarm.summary());
            System.out.println();
        }
    }
}
