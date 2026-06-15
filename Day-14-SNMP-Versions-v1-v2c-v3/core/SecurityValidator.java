public final class SecurityValidator {
    public boolean validateCommunity(CommunityString communityString, SnmpVersion version) {
        ValidationUtils.requireNonNull(version, "version");
        if (version == SnmpVersion.SNMPv3) {
            return communityString == null;
        }
        return communityString != null && !communityString.value().isBlank();
    }

    public boolean validateUser(SnmpUser user) {
        ValidationUtils.requireNonNull(user, "user");
        return switch (user.securityLevel()) {
            case NO_AUTH_NO_PRIV -> user.authenticationProtocol() == AuthenticationProtocol.NONE
                    && user.privacyProtocol() == PrivacyProtocol.NONE;
            case AUTH_NO_PRIV -> user.authenticationProtocol() != AuthenticationProtocol.NONE
                    && user.privacyProtocol() == PrivacyProtocol.NONE;
            case AUTH_PRIV -> user.authenticationProtocol() != AuthenticationProtocol.NONE
                    && user.privacyProtocol() != PrivacyProtocol.NONE;
        };
    }

    public String describeSecurity(SnmpVersion version, SecurityLevel securityLevel) {
        if (version == SnmpVersion.SNMPv1 || version == SnmpVersion.SNMPv2c) {
            return "Community-based access control";
        }
        return securityLevel.displayName() + " with USM protection";
    }
}
