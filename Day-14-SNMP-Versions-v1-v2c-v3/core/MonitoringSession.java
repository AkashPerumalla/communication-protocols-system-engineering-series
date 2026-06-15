public record MonitoringSession(
        String sessionId,
        String deviceName,
        SnmpVersion version,
        SecurityLevel securityLevel,
        String authentication,
        String encryption,
        String telemetrySummary,
        long responseTimeMicros) {
}
