import java.util.ArrayList;
import java.util.List;

public final class OidParser {
    private OidParser() {
    }

    public static String normalize(String oid) {
        if (oid == null) {
            throw new IllegalArgumentException("OID must not be null");
        }
        String trimmed = oid.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("OID must not be empty");
        }
        while (trimmed.endsWith(".")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        return trimmed;
    }

    public static List<Integer> parse(String oid) {
        String normalized = normalize(oid);
        String[] parts = normalized.split("\\.");
        List<Integer> values = new ArrayList<>(parts.length);
        for (String part : parts) {
            values.add(Integer.parseInt(part));
        }
        return values;
    }

    public static int compare(String left, String right) {
        List<Integer> leftValues = parse(left);
        List<Integer> rightValues = parse(right);
        int limit = Math.min(leftValues.size(), rightValues.size());
        for (int index = 0; index < limit; index++) {
            int comparison = Integer.compare(leftValues.get(index), rightValues.get(index));
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(leftValues.size(), rightValues.size());
    }

    public static String parent(String oid) {
        List<Integer> values = parse(oid);
        if (values.size() <= 1) {
            return "";
        }
        return join(values.subList(0, values.size() - 1));
    }

    public static boolean isAncestor(String ancestorOid, String descendantOid) {
        List<Integer> ancestor = parse(ancestorOid);
        List<Integer> descendant = parse(descendantOid);
        if (ancestor.size() >= descendant.size()) {
            return false;
        }
        for (int index = 0; index < ancestor.size(); index++) {
            if (!ancestor.get(index).equals(descendant.get(index))) {
                return false;
            }
        }
        return true;
    }

    public static String join(List<Integer> parts) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < parts.size(); index++) {
            if (index > 0) {
                builder.append('.');
            }
            builder.append(parts.get(index));
        }
        return builder.toString();
    }
}
