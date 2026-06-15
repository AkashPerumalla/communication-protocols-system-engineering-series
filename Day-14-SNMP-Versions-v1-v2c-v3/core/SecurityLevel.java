public enum SecurityLevel {
    NO_AUTH_NO_PRIV("NoAuthNoPriv", false, false),
    AUTH_NO_PRIV("AuthNoPriv", true, false),
    AUTH_PRIV("AuthPriv", true, true);

    private final String displayName;
    private final boolean authenticated;
    private final boolean encrypted;

    SecurityLevel(String displayName, boolean authenticated, boolean encrypted) {
        this.displayName = displayName;
        this.authenticated = authenticated;
        this.encrypted = encrypted;
    }

    public String displayName() {
        return displayName;
    }

    public boolean authenticated() {
        return authenticated;
    }

    public boolean encrypted() {
        return encrypted;
    }
}
