import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class EventCorrelationEngine {
    public String correlate(Collection<TrapEvent> events) {
        Objects.requireNonNull(events, "events");
        List<TrapEvent> correlatedEvents = new ArrayList<>(events);
        Optional<TrapEvent> powerFailure = correlatedEvents.stream()
                .filter(event -> event.trapType() == TrapType.POWER_FAILURE)
                .findFirst();
        Optional<TrapEvent> unreachable = correlatedEvents.stream()
                .filter(event -> event.trapType() == TrapType.DEVICE_UNREACHABLE)
                .findFirst();

        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.section("Event Correlation"));
        builder.append(ConsoleFormatter.kv("Correlated Events", Integer.toString(correlatedEvents.size())));

        if (powerFailure.isPresent() && unreachable.isPresent() && powerFailure.get().deviceName().equals(unreachable.get().deviceName())) {
            builder.append(ConsoleFormatter.kv("Root Cause", "Power failure on " + powerFailure.get().deviceName()));
            builder.append(ConsoleFormatter.kv("Impact", "Device unreachable alarm is a downstream symptom"));
            builder.append("ROOT CAUSE\n");
            return builder.toString();
        }

        builder.append(ConsoleFormatter.kv("Root Cause", "No correlated root cause detected"));
        builder.append("ROOT CAUSE\n");
        return builder.toString();
    }
}
