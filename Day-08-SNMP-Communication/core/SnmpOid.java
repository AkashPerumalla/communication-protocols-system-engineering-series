import java.util.Arrays;
import java.util.Objects;

public final class SnmpOid implements Comparable<SnmpOid> {
    private final int[] arcs;

    private SnmpOid(int[] arcs) {
        this.arcs = arcs;
    }

    public static SnmpOid of(String dottedDecimal) {
        Objects.requireNonNull(dottedDecimal, "dottedDecimal");
        String[] tokens = dottedDecimal.trim().split("\\.");
        int[] parsed = new int[tokens.length];
        for (int index = 0; index < tokens.length; index++) {
            parsed[index] = Integer.parseInt(tokens[index].trim());
        }
        return new SnmpOid(parsed);
    }

    public static SnmpOid of(int... arcs) {
        return new SnmpOid(Arrays.copyOf(arcs, arcs.length));
    }

    public boolean startsWith(SnmpOid prefix) {
        if (prefix.arcs.length > arcs.length) {
            return false;
        }
        for (int index = 0; index < prefix.arcs.length; index++) {
            if (arcs[index] != prefix.arcs[index]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(SnmpOid other) {
        int limit = Math.min(arcs.length, other.arcs.length);
        for (int index = 0; index < limit; index++) {
            int comparison = Integer.compare(arcs[index], other.arcs[index]);
            if (comparison != 0) {
                return comparison;
            }
        }
        return Integer.compare(arcs.length, other.arcs.length);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SnmpOid oid)) {
            return false;
        }
        return Arrays.equals(arcs, oid.arcs);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arcs);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < arcs.length; index++) {
            if (index > 0) {
                builder.append('.');
            }
            builder.append(arcs[index]);
        }
        return builder.toString();
    }
}
