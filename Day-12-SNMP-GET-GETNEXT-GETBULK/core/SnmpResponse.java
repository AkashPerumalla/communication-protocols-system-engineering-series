public final class SnmpResponse {
    private final String requestOid;
    private final String resolvedOid;
    private final String objectName;
    private final String dataType;
    private final String value;
    private final String status;
    private final String message;
    private final long elapsedNanos;

    public SnmpResponse(String requestOid, String resolvedOid, String objectName, String dataType, String value, String status, String message, long elapsedNanos) {
        this.requestOid = requestOid;
        this.resolvedOid = resolvedOid;
        this.objectName = objectName;
        this.dataType = dataType;
        this.value = value;
        this.status = status;
        this.message = message;
        this.elapsedNanos = elapsedNanos;
    }

    public String requestOid() {
        return requestOid;
    }

    public String resolvedOid() {
        return resolvedOid;
    }

    public String objectName() {
        return objectName;
    }

    public String dataType() {
        return dataType;
    }

    public String value() {
        return value;
    }

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }

    public long elapsedNanos() {
        return elapsedNanos;
    }

    public long elapsedMillis() {
        return Math.max(0L, elapsedNanos / 1_000_000L);
    }
}
