import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EventRepository {
    private final Map<String, TrapEvent> events = new LinkedHashMap<>();
    private final Map<String, Alarm> alarms = new LinkedHashMap<>();

    public void storeEvent(TrapEvent event) {
        ValidationUtils.validateTrapEvent(event);
        events.put(event.eventId(), event);
    }

    public void storeAlarm(Alarm alarm) {
        ValidationUtils.validateAlarm(alarm);
        alarms.put(alarm.alarmId(), alarm);
    }

    public List<TrapEvent> events() {
        return List.copyOf(events.values());
    }

    public List<Alarm> alarms() {
        return List.copyOf(alarms.values());
    }

    public Alarm alarmById(String alarmId) {
        return alarms.get(alarmId);
    }

    public void replaceAlarm(Alarm alarm) {
        storeAlarm(alarm);
    }

    public List<Alarm> openAlarms() {
        return alarms.values().stream()
                .filter(alarm -> alarm.status() == AlarmStatus.OPEN || alarm.status() == AlarmStatus.ACKNOWLEDGED)
                .toList();
    }

    public long countBySeverity(TrapSeverity severity) {
        return alarms.values().stream().filter(alarm -> alarm.severity() == severity).count();
    }

    public Set<String> impactedDevices() {
        Set<String> impactedDevices = new LinkedHashSet<>();
        for (Alarm alarm : alarms.values()) {
            impactedDevices.add(alarm.device());
        }
        return Collections.unmodifiableSet(impactedDevices);
    }

    public Map<TrapType, Long> trapCountsByType() {
        Map<TrapType, Long> counts = new LinkedHashMap<>();
        for (TrapEvent event : events.values()) {
            counts.merge(event.trapType(), 1L, Long::sum);
        }
        return Collections.unmodifiableMap(counts);
    }

    public List<TrapEvent> eventsForDevice(String deviceName) {
        return events.values().stream().filter(event -> event.deviceName().equals(deviceName)).toList();
    }

    public List<TrapEvent> correlatedEvents(TrapType... trapTypes) {
        List<TrapType> requestedTypes = List.of(trapTypes);
        List<TrapEvent> correlated = new ArrayList<>();
        for (TrapEvent event : events.values()) {
            if (requestedTypes.contains(event.trapType())) {
                correlated.add(event);
            }
        }
        return List.copyOf(correlated);
    }
}
