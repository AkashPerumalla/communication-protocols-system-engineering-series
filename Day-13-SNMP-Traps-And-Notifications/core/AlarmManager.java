public final class AlarmManager {
    private final EventRepository repository;

    public AlarmManager(EventRepository repository) {
        this.repository = java.util.Objects.requireNonNull(repository, "repository");
    }

    public Alarm createAlarm(TrapEvent event) {
        ValidationUtils.validateTrapEvent(event);
        Alarm alarm = new Alarm(
                buildAlarmId(event),
                event.severity(),
                event.deviceName(),
                AlarmStatus.OPEN,
                event.description(),
                event.timestamp(),
                event.eventId(),
                event.trapType());
        repository.storeAlarm(alarm);
        return alarm;
    }

    public Alarm acknowledge(String alarmId) {
        Alarm alarm = repository.alarmById(alarmId);
        ValidationUtils.ensure(alarm != null, "Unknown alarm: " + alarmId);
        Alarm updated = alarm.withStatus(AlarmStatus.ACKNOWLEDGED);
        repository.replaceAlarm(updated);
        return updated;
    }

    public Alarm clear(String alarmId) {
        Alarm alarm = repository.alarmById(alarmId);
        ValidationUtils.ensure(alarm != null, "Unknown alarm: " + alarmId);
        Alarm updated = alarm.withStatus(AlarmStatus.CLEARED);
        repository.replaceAlarm(updated);
        return updated;
    }

    private String buildAlarmId(TrapEvent event) {
        return "ALM-" + event.eventId();
    }
}
