public record SnmpResponse(
        SnmpVersion version,
        boolean success,
        String message,
        String oid,
        String value,
        long responseTimeMicros,
        SecurityLevel securityLevel,
        boolean encrypted,
        String payload) {
}
