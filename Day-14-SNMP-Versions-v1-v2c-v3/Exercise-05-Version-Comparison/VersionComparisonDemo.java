import java.util.ArrayList;
import java.util.List;

public final class VersionComparisonDemo {
    private VersionComparisonDemo() {
    }

    public static void main(String[] args) {
        VersionComparisonService service = new VersionComparisonService();
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Version", "Authentication", "Encryption", "Security", "Performance", "Recommended Usage"});
        for (VersionComparisonService.ComparisonRow row : service.compare()) {
            rows.add(new String[]{row.version(), row.authentication(), row.encryption(), row.security(), row.performance(), row.recommendedUsage()});
        }

        System.out.println(ConsoleFormatter.title("SNMP Version Comparison"));
        System.out.println(ConsoleFormatter.table(rows, 10, 16, 12, 14, 18, 34));
        System.out.println("VERSION COMPARISON");
    }
}
