public final class SnmpResponse {
    private final String operation;
    private final SnmpOid oid;
    private final boolean success;
    private final String value;
    private final String message;

    private SnmpResponse(String operation, SnmpOid oid, boolean success, String value, String message) {
        this.operation = operation;
        this.oid = oid;
        this.success = success;
        this.value = value;
        this.message = message;
    }

    public static SnmpResponse success(String operation, SnmpOid oid, String value, String message) {
        return new SnmpResponse(operation, oid, true, value, message);
    }

    public static SnmpResponse failure(String operation, SnmpOid oid, String message) {
        return new SnmpResponse(operation, oid, false, null, message);
    }

    public boolean success() {
        return success;
    }

    public String value() {
        return value;
    }

    public String message() {
        return message;
    }

    public String operation() {
        return operation;
    }

    public SnmpOid oid() {
        return oid;
    }

    public String format() {
        if (success && value != null) {
            return value;
        }
        return message;
    }
}
