import java.util.List;

public final class VersionMigrationSimulatorDemo {
    private VersionMigrationSimulatorDemo() {
    }

    public static void main(String[] args) {
        VersionComparisonService comparisonService = new VersionComparisonService();
        List<MonitoringSession> sessions = SampleData.migrationSessions();

        System.out.println(ConsoleFormatter.title("SNMP Version Migration Simulator"));
        for (MonitoringSession session : sessions) {
            System.out.println(ConsoleFormatter.keyValue(session.deviceName(), session.version().displayName() + " / " + session.securityLevel().displayName()));
            System.out.println(ConsoleFormatter.keyValue("Authentication", session.authentication()));
            System.out.println(ConsoleFormatter.keyValue("Encryption", session.encryption()));
            System.out.println(ConsoleFormatter.keyValue("Status", session.telemetrySummary()));
            System.out.println(ConsoleFormatter.keyValue("Response Time (micros)", Long.toString(session.responseTimeMicros())));
        }
        System.out.println(ConsoleFormatter.keyValue("Security Improvements", "Community-only access moves to authenticated and encrypted access"));
        System.out.println(ConsoleFormatter.keyValue("Risk Reduction", "Credential exposure and replay risk are reduced at each migration step"));
        System.out.println(ConsoleFormatter.keyValue("Operational Benefits", "SNMPv3 aligns with enterprise NMS and telecom control-plane hardening"));
        System.out.println(comparisonService.renderTable());
        System.out.println("MIGRATION COMPLETE");
    }
}
