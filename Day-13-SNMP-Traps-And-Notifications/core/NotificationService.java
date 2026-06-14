import java.util.List;
import java.util.stream.Collectors;

public final class NotificationService {
    public String forward(TrapEvent event) {
        String route = routeForSeverity(event.severity());
        return ConsoleFormatter.section("Notification Forwarding")
                + ConsoleFormatter.kv("Route", route)
                + ConsoleFormatter.kv("Device", event.deviceName())
                + ConsoleFormatter.kv("Severity", event.severity().name())
                + ConsoleFormatter.kv("Event ID", event.eventId());
    }

    public String forward(Alarm alarm) {
        String route = routeForSeverity(alarm.severity());
        return ConsoleFormatter.section("Alarm Notification")
                + ConsoleFormatter.kv("Route", route)
                + ConsoleFormatter.kv("Device", alarm.device())
                + ConsoleFormatter.kv("Severity", alarm.severity().name())
                + ConsoleFormatter.kv("Alarm ID", alarm.alarmId());
    }

    public String summarizeAlarms(List<Alarm> alarms) {
        return alarms.stream()
                .map(alarm -> alarm.alarmId() + " | " + alarm.device() + " | " + alarm.severity().name() + " | " + alarm.status().name())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String routeForSeverity(TrapSeverity severity) {
        return switch (severity) {
            case CRITICAL, MAJOR -> "NOC paging and incident workflow";
            case MINOR, WARNING -> "Operations queue and ticket enrichment";
            case INFO -> "Event archive and audit trail";
        };
    }
}
