import java.util.List;

public final class VersionComparisonService {
    public record ComparisonRow(
            String version,
            String authentication,
            String encryption,
            String security,
            String performance,
            String recommendedUsage) {
    }

    public List<ComparisonRow> compare() {
        return List.of(
                new ComparisonRow("SNMPv1", "None", "None", "Weak community-only access", "Fastest / least overhead", SnmpVersion.SNMPv1.recommendedUse()),
                new ComparisonRow("SNMPv2c", "None", "None", "Community-based with same exposure class as v1", "Fast / slightly better operations", SnmpVersion.SNMPv2c.recommendedUse()),
                new ComparisonRow("SNMPv3", "MD5 or SHA", "DES or AES", "Strong user-based security", "Highest overhead / strongest control", SnmpVersion.SNMPv3.recommendedUse())
        );
    }

    public String renderTable() {
        return ConsoleFormatter.table(
                List.of(
                        new String[]{"Version", "Authentication", "Encryption", "Security", "Performance", "Recommended Usage"},
                        new String[]{"SNMPv1", "None", "None", "Weak", "Fastest", SnmpVersion.SNMPv1.recommendedUse()},
                        new String[]{"SNMPv2c", "Community", "None", "Moderate", "Fast", SnmpVersion.SNMPv2c.recommendedUse()},
                        new String[]{"SNMPv3", "MD5/SHA", "DES/AES", "Strong", "Controlled", SnmpVersion.SNMPv3.recommendedUse()}
                ),
                10, 16, 12, 14, 12, 34
        );
    }
}
