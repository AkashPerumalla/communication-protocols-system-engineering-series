import java.util.Objects;

public final class SnmpRequest {
    private final String operation;
    private final String oid;
    private final int nonRepeaters;
    private final int maxRepetitions;

    public SnmpRequest(String operation, String oid) {
        this(operation, oid, 0, 0);
    }

    public SnmpRequest(String operation, String oid, int nonRepeaters, int maxRepetitions) {
        this.operation = Objects.requireNonNull(operation, "operation");
        this.oid = ValidationUtils.normalizeOid(Objects.requireNonNull(oid, "oid"));
        this.nonRepeaters = nonRepeaters;
        this.maxRepetitions = maxRepetitions;
    }

    public String operation() {
        return operation;
    }

    public String oid() {
        return oid;
    }

    public int nonRepeaters() {
        return nonRepeaters;
    }

    public int maxRepetitions() {
        return maxRepetitions;
    }
}
