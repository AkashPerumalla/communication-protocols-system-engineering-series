public enum PrivacyProtocol {
    NONE,
    DES,
    AES;

    public boolean requiresSecret() {
        return this != NONE;
    }
}
