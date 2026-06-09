import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class AlarmManager {
    private final List<AlarmRecord> activeAlarms = new ArrayList<>();
    private final List<AlarmRecord> history = new ArrayList<>();

    public AlarmRecord raise(AlarmSeverity severity, String device, String summary) {
        AlarmRecord record = new AlarmRecord(severity, device, summary);
        activeAlarms.add(record);
        history.add(record);
        return record;
    }

    public boolean clear(String alarmId) {
        Optional<AlarmRecord> match = activeAlarms.stream().filter(record -> record.id().equals(alarmId)).findFirst();
        if (match.isEmpty()) {
            return false;
        }
        match.get().clear();
        activeAlarms.remove(match.get());
        return true;
    }

    public List<AlarmRecord> activeAlarms() {
        return new ArrayList<>(activeAlarms);
    }

    public List<AlarmRecord> clearedAlarms() {
        return history.stream().filter(record -> "CLEARED".equals(record.status())).toList();
    }

    public List<AlarmRecord> history() {
        return new ArrayList<>(history);
    }
}
