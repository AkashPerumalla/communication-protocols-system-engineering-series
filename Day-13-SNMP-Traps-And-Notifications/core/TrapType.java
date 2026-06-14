import java.util.Arrays;

public enum TrapType {
    COLD_START("coldStart", "1.3.6.1.6.3.1.1.5.1", TrapSeverity.INFO, "Agent restart notification"),
    WARM_START("warmStart", "1.3.6.1.6.3.1.1.5.2", TrapSeverity.INFO, "Warm restart notification"),
    LINK_DOWN("linkDown", "1.3.6.1.6.3.1.1.5.3", TrapSeverity.MAJOR, "Interface down event"),
    LINK_UP("linkUp", "1.3.6.1.6.3.1.1.5.4", TrapSeverity.INFO, "Interface restored event"),
    AUTHENTICATION_FAILURE("authenticationFailure", "1.3.6.1.6.3.1.1.5.5", TrapSeverity.CRITICAL, "Security authentication failure"),
    POWER_FAILURE("powerFailure", "1.3.6.1.4.1.9999.1.0.1", TrapSeverity.CRITICAL, "Power subsystem failure"),
    HIGH_TEMPERATURE("highTemperature", "1.3.6.1.4.1.9999.1.0.2", TrapSeverity.MAJOR, "Temperature threshold exceeded"),
    HIGH_CPU_UTILIZATION("highCpuUtilization", "1.3.6.1.4.1.9999.1.0.3", TrapSeverity.MINOR, "CPU utilization threshold exceeded"),
    HIGH_MEMORY_UTILIZATION("highMemoryUtilization", "1.3.6.1.4.1.9999.1.0.4", TrapSeverity.MINOR, "Memory utilization threshold exceeded"),
    DEVICE_UNREACHABLE("deviceUnreachable", "1.3.6.1.4.1.9999.1.0.5", TrapSeverity.CRITICAL, "Managed device unreachable"),
    CARRIER_LOSS("carrierLoss", "1.3.6.1.4.1.9999.2.0.1", TrapSeverity.MAJOR, "RF carrier lost"),
    BER_THRESHOLD_EXCEEDED("berThresholdExceeded", "1.3.6.1.4.1.9999.2.0.2", TrapSeverity.MAJOR, "BER threshold exceeded"),
    BUC_FAILURE("bucFailure", "1.3.6.1.4.1.9999.2.0.3", TrapSeverity.CRITICAL, "BUC failure detected"),
    LNB_FAILURE("lnbFailure", "1.3.6.1.4.1.9999.2.0.4", TrapSeverity.CRITICAL, "LNB failure detected"),
    MODEM_FAILURE("modemFailure", "1.3.6.1.4.1.9999.2.0.5", TrapSeverity.CRITICAL, "Modem failure detected");

    private final String code;
    private final String oidValue;
    private final TrapSeverity defaultSeverity;
    private final String description;

    TrapType(String code, String oidValue, TrapSeverity defaultSeverity, String description) {
        this.code = code;
        this.oidValue = oidValue;
        this.defaultSeverity = defaultSeverity;
        this.description = description;
    }

    public String code() {
        return code;
    }

    public TrapOid trapOid() {
        return TrapOid.of(oidValue);
    }

    public TrapSeverity defaultSeverity() {
        return defaultSeverity;
    }

    public String description() {
        return description;
    }

    public static TrapType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown trap type: " + code));
    }
}
