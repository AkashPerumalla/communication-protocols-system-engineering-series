import java.util.Objects;

public final class TrapProcessor {
    private final TrapReceiver trapReceiver;
    private final EventRepository repository;
    private final NotificationService notificationService;

    public TrapProcessor(TrapReceiver trapReceiver, EventRepository repository, NotificationService notificationService) {
        this.trapReceiver = Objects.requireNonNull(trapReceiver, "trapReceiver");
        this.repository = Objects.requireNonNull(repository, "repository");
        this.notificationService = Objects.requireNonNull(notificationService, "notificationService");
    }

    public String process(TrapEvent event) {
        ValidationUtils.validateTrapEvent(event);
        TrapEvent received = trapReceiver.receive(event);
        repository.storeEvent(received);
        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.section("Trap Processing Pipeline"));
        builder.append(ConsoleFormatter.kv("Step 1", "Receive"));
        builder.append(ConsoleFormatter.kv("Step 2", "Validate"));
        builder.append(ConsoleFormatter.kv("Step 3", "Decode"));
        builder.append(ConsoleFormatter.kv("Step 4", "Store"));
        builder.append(ConsoleFormatter.kv("Step 5", "Forward"));
        builder.append(ConsoleFormatter.blankLine());
        builder.append(ConsoleFormatter.section("Decoded Trap"));
        builder.append(ConsoleFormatter.kv("Device", received.deviceName()));
        builder.append(ConsoleFormatter.kv("OID", received.trapOid().toString()));
        builder.append(ConsoleFormatter.kv("Trap Type", received.trapType().code()));
        builder.append(ConsoleFormatter.kv("Severity", received.severity().name()));
        builder.append(ConsoleFormatter.kv("Description", received.description()));
        builder.append(ConsoleFormatter.blankLine());
        builder.append(notificationService.forward(received));
        builder.append("TRAP PROCESSED\n");
        return builder.toString();
    }
}
