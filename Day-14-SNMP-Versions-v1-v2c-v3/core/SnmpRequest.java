public record SnmpRequest(
        SnmpVersion version,
        Operation operation,
        String oid,
        CommunityString communityString,
        SnmpUser user,
        String payload) {

    public enum Operation {
        GET,
        GETNEXT,
        GETBULK
    }

    public SnmpRequest {
        ValidationUtils.requireNonNull(version, "version");
        ValidationUtils.requireNonNull(operation, "operation");
        ValidationUtils.requireNonBlank(oid, "oid");
        if (version == SnmpVersion.SNMPv3) {
            ValidationUtils.requireNonNull(user, "user");
        } else {
            ValidationUtils.requireNonNull(communityString, "communityString");
        }
    }
}
