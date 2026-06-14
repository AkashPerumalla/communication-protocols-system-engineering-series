public record TrapOid(String value) {
    public TrapOid {
        ValidationUtils.requireNonBlank(value, "trapOid");
    }

    public static TrapOid of(String value) {
        return new TrapOid(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
