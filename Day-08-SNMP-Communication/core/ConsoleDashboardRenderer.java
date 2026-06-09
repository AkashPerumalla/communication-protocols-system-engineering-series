public final class ConsoleDashboardRenderer {
    public String render(DashboardSnapshot snapshot) {
        String line = "=================================";
        return String.join(System.lineSeparator(),
                line,
                "NETWORK MANAGEMENT DASHBOARD",
                line,
                "",
                String.format("Devices Online : %d", snapshot.devicesOnline()),
                String.format("Devices Offline: %d", snapshot.devicesOffline()),
                "",
                String.format("Critical Alarms : %d", snapshot.criticalAlarms()),
                String.format("Major Alarms    : %d", snapshot.majorAlarms()),
                String.format("Minor Alarms    : %d", snapshot.minorAlarms()),
                String.format("Warning Alarms  : %d", snapshot.warningAlarms()),
                "",
                String.format("CPU Average     : %d%%", snapshot.averageCpu()),
                String.format("Memory Average  : %d%%", snapshot.averageMemory()),
                "",
                line);
    }
}
