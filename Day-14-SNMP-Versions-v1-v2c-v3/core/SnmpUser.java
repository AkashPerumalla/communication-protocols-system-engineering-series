public record SnmpUser(
        String username,
        AuthenticationProtocol authenticationProtocol,
        PrivacyProtocol privacyProtocol,
        SecurityLevel securityLevel,
        String authenticationSecret,
        String privacySecret) {

    public SnmpUser {
        ValidationUtils.requireNonBlank(username, "username");
        ValidationUtils.requireNonNull(authenticationProtocol, "authenticationProtocol");
        ValidationUtils.requireNonNull(privacyProtocol, "privacyProtocol");
        ValidationUtils.requireNonNull(securityLevel, "securityLevel");
        if (authenticationProtocol.requiresSecret()) {
            ValidationUtils.requireNonBlank(authenticationSecret, "authenticationSecret");
        }
        if (privacyProtocol.requiresSecret()) {
            ValidationUtils.requireNonBlank(privacySecret, "privacySecret");
        }
        if (securityLevel == SecurityLevel.NO_AUTH_NO_PRIV && authenticationProtocol != AuthenticationProtocol.NONE) {
            throw new IllegalArgumentException("NO_AUTH_NO_PRIV requires AuthenticationProtocol.NONE");
        }
        if (securityLevel == SecurityLevel.AUTH_NO_PRIV && privacyProtocol != PrivacyProtocol.NONE) {
            throw new IllegalArgumentException("AUTH_NO_PRIV requires PrivacyProtocol.NONE");
        }
        if (securityLevel == SecurityLevel.AUTH_PRIV && privacyProtocol == PrivacyProtocol.NONE) {
            throw new IllegalArgumentException("AUTH_PRIV requires an encryption protocol");
        }
    }
}
