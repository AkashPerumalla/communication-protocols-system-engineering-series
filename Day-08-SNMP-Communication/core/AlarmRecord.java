import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public final class AlarmRecord {
    private static final AtomicLong SEQUENCE = new AtomicLong(1000);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final String id;
    private final AlarmSeverity severity;
    private final String device;
    private final String summary;
    private final LocalDateTime timestamp;
    private String status;

    public AlarmRecord(AlarmSeverity severity, String device, String summary) {
        this.id = "ALM-" + SEQUENCE.incrementAndGet();
        this.severity = severity;
        this.device = device;
        this.summary = summary;
        this.timestamp = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public String id() {
        return id;
    }

    public AlarmSeverity severity() {
        return severity;
    }

    public String device() {
        return device;
    }

    public String summary() {
        return summary;
    }

    public String status() {
        return status;
    }

    public String timestampText() {
        return FORMATTER.format(timestamp);
    }

    public void clear() {
        status = "CLEARED";
    }
}
