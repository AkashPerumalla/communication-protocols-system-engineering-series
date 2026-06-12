import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OidNode {
    private final Oid oid;
    private final OidNode parent;
    private final List<OidNode> children = new ArrayList<>();

    public OidNode(Oid oid, OidNode parent) {
        this.oid = oid;
        this.parent = parent;
    }

    public Oid oidObject() {
        return oid;
    }

    public String oid() {
        return oid.oid();
    }

    public String name() {
        return oid.displayName();
    }

    public String description() {
        return oid.description();
    }

    public String dataType() {
        return oid.dataType();
    }

    public String currentValue() {
        return oid.currentValue();
    }

    public OidNode parent() {
        return parent;
    }

    public List<OidNode> children() {
        List<OidNode> sorted = new ArrayList<>(children);
        sorted.sort((left, right) -> ValidationUtils.compareOids(left.oid(), right.oid()));
        return Collections.unmodifiableList(sorted);
    }

    public void addChild(OidNode child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public boolean isRoot() {
        return parent == null;
    }

    public int depth() {
        return parent == null ? 0 : parent.depth() + 1;
    }

    public String canonicalPath() {
        return parent == null ? name() : parent.canonicalPath() + "." + name();
    }

    public String parentOid() {
        return parent == null ? "" : parent.oid();
    }
}
