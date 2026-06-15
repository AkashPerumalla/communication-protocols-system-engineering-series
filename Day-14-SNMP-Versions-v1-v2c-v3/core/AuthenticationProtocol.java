public enum AuthenticationProtocol {
    NONE,
    MD5,
    SHA;

    public boolean requiresSecret() {
        return this != NONE;
    }
}
