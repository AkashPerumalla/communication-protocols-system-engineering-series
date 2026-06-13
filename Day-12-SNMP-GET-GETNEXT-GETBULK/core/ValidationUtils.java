import java.util.ArrayList;
import java.util.List;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static String normalizeOid(String oid) {
        if (oid == null) {
            throw new IllegalArgumentException("OID cannot be null");
        }
        String trimmed = oid.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("OID cannot be blank");
        }
        if (trimmed.startsWith(".")) {
            trimmed = trimmed.substring(1);
        }
        if (trimmed.endsWith(".")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        if (!trimmed.matches("\\d+(?:\\.\\d+)*")) {
            throw new IllegalArgumentException("Invalid dotted OID: " + oid);
        }
        return trimmed;
    }

    public static List<Integer> parseOid(String oid) {
        String normalized = normalizeOid(oid);
        String[] parts = normalized.split("\\.");
        List<Integer> values = new ArrayList<>(parts.length);
        for (String part : parts) {
            values.add(Integer.parseInt(part));
        }
        return values;
    }

    public static int compareOids(String left, String right) {
        List<Integer> leftParts = parseOid(left);
        List<Integer> rightParts = parseOid(right);
        int max = Math.min(leftParts.size(), rightParts.size());
        for (int index = 0; index < max; index++) {
            int comparison = Integer.compare(leftParts.get(index), rightParts.get(index));
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(leftParts.size(), rightParts.size());
    }

    public static boolean isPrefixOf(String prefix, String oid) {
        List<Integer> prefixParts = parseOid(prefix);
        List<Integer> oidParts = parseOid(oid);
        if (prefixParts.size() > oidParts.size()) {
            return false;
        }
        for (int index = 0; index < prefixParts.size(); index++) {
            if (!prefixParts.get(index).equals(oidParts.get(index))) {
                return false;
            }
        }
        return true;
    }

    public static String safe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }
}
