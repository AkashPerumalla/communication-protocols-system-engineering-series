import java.util.ArrayList;
import java.util.List;

public final class NocDashboardService {
    public record DashboardRow(String deviceName, String version, String authentication, String encryption, String status) {
    }

    public List<DashboardRow> buildDashboard(List<MonitoringSession> sessions) {
        ValidationUtils.requireNonNull(sessions, "sessions");
        return sessions.stream()
                .map(session -> new DashboardRow(
                        session.deviceName(),
                        session.version().displayName(),
                        session.authentication(),
                        session.encryption(),
                        session.telemetrySummary()))
                .toList();
    }

    public String renderDashboard(List<DashboardRow> rows) {
        ValidationUtils.requireNonNull(rows, "rows");
        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.title("Secure NOC Monitoring Dashboard")).append(System.lineSeparator());
                List<String[]> tableRows = new ArrayList<>();
                tableRows.add(new String[]{"Device", "Version", "Authentication", "Encryption", "Status"});
                for (DashboardRow row : rows) {
                        tableRows.add(new String[]{row.deviceName(), row.version(), row.authentication(), row.encryption(), row.status()});
                }
                builder.append(ConsoleFormatter.table(tableRows, 18, 12, 16, 12, 28));
        return builder.toString();
    }
}
