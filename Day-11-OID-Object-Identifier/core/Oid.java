import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Oid implements Comparable<Oid> {
    private final String oid;
    private final String name;
    private final String description;
    private final String dataType;
    private final String instanceSuffix;
    private String currentValue;
    private Oid parent;
    private final List<Oid> children = new ArrayList<>();

    public Oid(String oid, String name, String description, String dataType, String currentValue) {
        this(oid, name, description, dataType, currentValue, null);
    }

    public Oid(String oid, String name, String description, String dataType, String currentValue, String instanceSuffix) {
        this.oid = ValidationUtils.normalizeOid(oid);
        this.name = Objects.requireNonNull(name, "name");
        this.description = Objects.requireNonNull(description, "description");
        this.dataType = Objects.requireNonNull(dataType, "dataType");
        this.currentValue = currentValue;
        this.instanceSuffix = instanceSuffix;
    }

    public String oid() {
        return oid;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String dataType() {
        return dataType;
    }

    public String currentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String instanceSuffix() {
        return instanceSuffix;
    }

    public String displayName() {
        return instanceSuffix == null || instanceSuffix.isBlank() ? name : name + "." + instanceSuffix;
    }

    public Oid getParent() {
        return parent;
    }

    public List<Oid> getChildren() {
        List<Oid> sorted = new ArrayList<>(children);
        sorted.sort(Oid::compareTo);
        return Collections.unmodifiableList(sorted);
    }

    public void attachParent(Oid parent) {
        this.parent = parent;
    }

    public void addChild(Oid child) {
        if (!children.contains(child)) {
            children.add(child);
        }
    }

    public int depth() {
        return parent == null ? 0 : parent.depth() + 1;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public String canonicalPath() {
        return parent == null ? displayName() : parent.canonicalPath() + "." + displayName();
    }

    @Override
    public int compareTo(Oid other) {
        return ValidationUtils.compareOids(oid, other.oid);
    }

    @Override
    public String toString() {
        return canonicalPath() + " = " + ValidationUtils.safe(currentValue);
    }
}
