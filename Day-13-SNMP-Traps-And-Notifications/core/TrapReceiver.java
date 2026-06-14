public final class TrapReceiver {
    public TrapEvent receive(TrapEvent event) {
        ValidationUtils.validateTrapEvent(event);
        return event;
    }

    public String receipt(TrapEvent event) {
        TrapEvent received = receive(event);
        return ConsoleFormatter.section("Trap Receiver Receipt")
                + ConsoleFormatter.kv("Source", received.sourceIp())
                + ConsoleFormatter.kv("Device", received.deviceName())
                + ConsoleFormatter.kv("OID", received.trapOid().toString())
                + ConsoleFormatter.kv("Severity", received.severity().name())
                + ConsoleFormatter.kv("Event ID", received.eventId());
    }
}
