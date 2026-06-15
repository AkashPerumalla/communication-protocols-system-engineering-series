public enum SnmpVersion {
    SNMPv1("SNMPv1", false, false, false, "Legacy lab or isolated test networks"),
    SNMPv2c("SNMPv2c", false, false, false, "Operationally common with controlled community access"),
    SNMPv3("SNMPv3", true, true, true, "Production NMS and regulated environments");

    private final String displayName;
    private final boolean supportsAuthentication;
    private final boolean supportsPrivacy;
    private final boolean recommendedForProduction;

    SnmpVersion(String displayName, boolean supportsAuthentication, boolean supportsPrivacy, boolean recommendedForProduction, String recommendedUse) {
        this.displayName = displayName;
        this.supportsAuthentication = supportsAuthentication;
        this.supportsPrivacy = supportsPrivacy;
        this.recommendedForProduction = recommendedForProduction;
    }

    public String displayName() {
        return displayName;
    }

    public boolean supportsAuthentication() {
        return supportsAuthentication;
    }

    public boolean supportsPrivacy() {
        return supportsPrivacy;
    }

    public boolean recommendedForProduction() {
        return recommendedForProduction;
    }

    public String recommendedUse() {
        return switch (this) {
            case SNMPv1 -> "Legacy lab or isolated test networks";
            case SNMPv2c -> "Operationally common with controlled community access";
            case SNMPv3 -> "Production NMS and regulated environments";
        };
    }
}
