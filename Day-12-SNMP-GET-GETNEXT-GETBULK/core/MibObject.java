import java.util.Objects;

public final class MibObject {
    private final Oid oid;
    private final String objectClass;
    private final String access;

    public MibObject(Oid oid, String objectClass, String access) {
        this.oid = Objects.requireNonNull(oid, "oid");
        this.objectClass = Objects.requireNonNull(objectClass, "objectClass");
        this.access = Objects.requireNonNull(access, "access");
    }

    public Oid oid() {
        return oid;
    }

    public String objectClass() {
        return objectClass;
    }

    public String access() {
        return access;
    }
}
