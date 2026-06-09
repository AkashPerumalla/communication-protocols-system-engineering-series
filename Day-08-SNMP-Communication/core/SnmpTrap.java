import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class SnmpTrap {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String sourceIp;
    private final SnmpTrapSeverity severity;
    private final String alarm;
    private final String details;
    private final LocalDateTime timestamp;

    public SnmpTrap(String sourceIp, SnmpTrapSeverity severity, String alarm, String details) {
        this.sourceIp = sourceIp;
        this.severity = severity;
        this.alarm = alarm;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String formatBlock() {
        return String.join(System.lineSeparator(),
                "[TRAP RECEIVED]",
                "",
                "Source:",
                sourceIp,
                "",
                "Severity:",
                severity.name(),
                "",
                "Alarm:",
                alarm,
                "",
                "Timestamp:",
                FORMATTER.format(timestamp),
                "",
                "Details:",
                details);
    }
}
