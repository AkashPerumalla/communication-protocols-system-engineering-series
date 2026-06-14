import java.util.ArrayList;
import java.util.List;

public final class ConsoleFormatter {
    private ConsoleFormatter() {
    }

    public static String header(String title, String subtitle) {
        StringBuilder builder = new StringBuilder();
        builder.append("====================================================\n");
        builder.append(title).append('\n');
        builder.append(subtitle).append('\n');
        builder.append("====================================================\n\n");
        return builder.toString();
    }

    public static String section(String title) {
        return "-- " + title + " --\n";
    }

    public static String kv(String label, String value) {
        return String.format("%-18s : %s%n", label, value);
    }

    public static String blankLine() {
        return "\n";
    }

    public static String formatTrap(TrapEvent event) {
        StringBuilder builder = new StringBuilder();
        builder.append(section(event.trapType().code()));
        builder.append(kv("Timestamp", event.timestamp().toString()));
        builder.append(kv("Device", event.deviceName()));
        builder.append(kv("Source IP", event.sourceIp()));
        builder.append(kv("Trap OID", event.trapOid().toString()));
        builder.append(kv("Severity", event.severity().name()));
        builder.append(kv("Description", event.description()));
        builder.append(kv("Acknowledged", Boolean.toString(event.acknowledged())));
        builder.append(kv("Event ID", event.eventId()));
        if (!event.attributes().isEmpty()) {
            builder.append(kv("Attributes", formatAttributes(event.attributes())));
        }
        builder.append('\n');
        return builder.toString();
    }

    public static String formatAlarm(Alarm alarm) {
        StringBuilder builder = new StringBuilder();
        builder.append(kv("Alarm ID", alarm.alarmId()));
        builder.append(kv("Severity", alarm.severity().name()));
        builder.append(kv("Device", alarm.device()));
        builder.append(kv("Status", alarm.status().name()));
        builder.append(kv("Description", alarm.description()));
        builder.append(kv("Created Time", alarm.createdTime().toString()));
        builder.append(kv("Source Event", alarm.sourceEventId()));
        builder.append(kv("Trap Type", alarm.trapType().code()));
        return builder.toString();
    }

    public static String table(String title, List<String> headers, List<String[]> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append(section(title));
        int[] widths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        for (String[] row : rows) {
            for (int i = 0; i < headers.size() && i < row.length; i++) {
                widths[i] = Math.max(widths[i], row[i].length());
            }
        }
        builder.append(renderRow(headers.toArray(String[]::new), widths)).append('\n');
        builder.append(renderSeparator(widths)).append('\n');
        for (String[] row : rows) {
            builder.append(renderRow(row, widths)).append('\n');
        }
        builder.append('\n');
        return builder.toString();
    }

    public static String formatAttributes(java.util.Map<String, String> attributes) {
        List<String> parts = new ArrayList<>();
        for (java.util.Map.Entry<String, String> entry : attributes.entrySet()) {
            parts.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.join(", ", parts);
    }

    private static String renderRow(String[] values, int[] widths) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            String value = i < values.length ? values[i] : "";
            builder.append(String.format("%-" + widths[i] + "s", value));
            if (i < widths.length - 1) {
                builder.append(" | ");
            }
        }
        return builder.toString();
    }

    private static String renderSeparator(int[] widths) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < widths.length; i++) {
            builder.append("-".repeat(widths[i]));
            if (i < widths.length - 1) {
                builder.append("-+-");
            }
        }
        return builder.toString();
    }
}
