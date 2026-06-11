import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class MibNode {
    private final String oid;
    private final String name;
    private final MibObject object;
    private final MibNode parent;
    private final List<MibNode> children = new ArrayList<>();

    public MibNode(String oid, String name, MibObject object, MibNode parent) {
        this.oid = oid;
        this.name = name;
        this.object = object;
        this.parent = parent;
    }

    public String oid() {
        return oid;
    }

    public String name() {
        return name;
    }

    public MibObject object() {
        return object;
    }

    public MibNode parent() {
        return parent;
    }

    public List<MibNode> children() {
        List<MibNode> sorted = new ArrayList<>(children);
        sorted.sort(Comparator.comparing(MibNode::oid, OidParser::compare));
        return Collections.unmodifiableList(sorted);
    }

    public void addChild(MibNode child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public boolean isObjectNode() {
        return object != null;
    }

    public String displayType() {
        return object == null ? "NODE" : object.type();
    }

    public String displayValue() {
        return object == null ? "<branch>" : object.value();
    }

    public String parentOid() {
        return parent == null ? "" : parent.oid();
    }
}
