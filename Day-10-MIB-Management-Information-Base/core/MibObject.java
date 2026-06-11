public record MibObject(String oid, String name, String type, String value, boolean writable) {
    public String access() {
        return writable ? "read-write" : "read-only";
    }

    public boolean isScalar() {
        return oid.endsWith(".0");
    }
}
