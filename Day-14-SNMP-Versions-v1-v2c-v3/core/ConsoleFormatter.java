import java.util.ArrayList;
import java.util.List;

public final class ConsoleFormatter {
    private ConsoleFormatter() {
    }

    public static String title(String text) {
        return "=== " + text + " ===";
    }

    public static String section(String text) {
        return "-- " + text + " --";
    }

    public static String keyValue(String key, String value) {
        return ValidationUtils.padRight(key + ":", 18) + value;
    }

    public static String table(List<String[]> rows, int... widths) {
        List<String> lines = new ArrayList<>();
        for (String[] row : rows) {
            List<String> cells = new ArrayList<>();
            for (int i = 0; i < row.length; i++) {
                int width = i < widths.length ? widths[i] : 20;
                cells.add(ValidationUtils.padRight(row[i], width));
            }
            lines.add(String.join(" | ", cells));
        }
        return String.join(System.lineSeparator(), lines);
    }
}
