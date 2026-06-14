import java.util.ArrayList;
import java.util.List;

public final class NocTrapDashboardDemo {
    private NocTrapDashboardDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 08 - NOC TRAP DASHBOARD", "Operational summary for trap-driven fault management"));
        EventRepository repository = new EventRepository();
        AlarmManager alarmManager = new AlarmManager(repository);
        List<TrapEvent> traps = SampleData.nocTraps();
        List<Alarm> alarms = new ArrayList<>();
        for (TrapEvent trap : traps) {
            repository.storeEvent(trap);
            alarms.add(alarmManager.createAlarm(trap));
        }

        System.out.print(renderDashboard(SampleData.deviceProfiles(), repository, alarms));
        System.out.println("NOC DASHBOARD");
    }

    private static String renderDashboard(List<DeviceProfile> devices, EventRepository repository, List<Alarm> alarms) {
        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.section("NOC Summary"));
        builder.append(ConsoleFormatter.kv("Open Alarms", Long.toString(repository.openAlarms().size())));
        builder.append(ConsoleFormatter.kv("Critical Alarms", Long.toString(repository.countBySeverity(TrapSeverity.CRITICAL))));
        builder.append(ConsoleFormatter.kv("Major Alarms", Long.toString(repository.countBySeverity(TrapSeverity.MAJOR))));
        builder.append(ConsoleFormatter.kv("Trap Count", Long.toString(repository.events().size())));
        builder.append(ConsoleFormatter.kv("Devices Impacted", String.join(", ", repository.impactedDevices())));
        builder.append(ConsoleFormatter.blankLine());
        builder.append(ConsoleFormatter.table(
                "Device Status Snapshot",
                List.of("Device", "IP", "Site", "Role"),
                devices.stream().map(device -> new String[] { device.name(), device.sourceIp(), device.site(), device.role() }).toList()));
        builder.append(ConsoleFormatter.section("Active Alarms"));
        for (Alarm alarm : alarms) {
            builder.append(ConsoleFormatter.kv(alarm.device(), alarm.severity().name() + " / " + alarm.status().name()));
        }
        return builder.toString();
    }
}
