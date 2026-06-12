public final class OidResponse {
    private final String requestOid;
    private final String resolvedOid;
    private final String name;
    private final String type;
    private final String value;
    private final String status;
    private final String message;

    public OidResponse(String requestOid, String resolvedOid, String name, String type, String value, String status, String message) {
        this.requestOid = requestOid;
        this.resolvedOid = resolvedOid;
        this.name = name;
        this.type = type;
        this.value = value;
        this.status = status;
        this.message = message;
    }

    public String requestOid() {
        return requestOid;
    }

    public String resolvedOid() {
        return resolvedOid;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
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

    public String format() {
        StringBuilder builder = new StringBuilder();
        builder.append("Request OID : ").append(requestOid).append(System.lineSeparator());
        builder.append("Resolved OID: ").append(resolvedOid).append(System.lineSeparator());
        builder.append("Name        : ").append(name).append(System.lineSeparator());
        builder.append("Type        : ").append(type).append(System.lineSeparator());
        builder.append("Value       : ").append(value).append(System.lineSeparator());
        builder.append("Status      : ").append(status).append(System.lineSeparator());
        if (message != null && !message.isBlank()) {
            builder.append("Message     : ").append(message).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
