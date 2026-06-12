import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class OidTree {
    private final Map<String, OidNode> index = new LinkedHashMap<>();

    public OidNode insert(Oid oid) {
        OidNode existing = index.get(oid.oid());
        if (existing != null) {
            return existing;
        }
        Oid parentOid = oid.getParent();
        OidNode parentNode = parentOid == null ? null : index.get(parentOid.oid());
        OidNode node = new OidNode(oid, parentNode);
        index.put(oid.oid(), node);
        if (parentOid != null) {
            parentOid.addChild(oid);
            oid.attachParent(parentOid);
        }
        if (parentNode != null) {
            parentNode.addChild(node);
        }
        return node;
    }

    public Optional<OidNode> lookup(String oid) {
        return Optional.ofNullable(index.get(ValidationUtils.normalizeOid(oid)));
    }

    public List<OidNode> childrenOf(String oid) {
        return lookup(oid).map(OidNode::children).orElseGet(List::of);
    }

    public Optional<OidNode> parentOf(String oid) {
        return lookup(oid).map(OidNode::parent);
    }

    public List<OidNode> walk(String oid) {
        Optional<OidNode> start = lookup(oid);
        if (start.isEmpty()) {
            return List.of();
        }
        List<OidNode> results = new ArrayList<>();
        Deque<OidNode> stack = new ArrayDeque<>(start.get().children());
        while (!stack.isEmpty()) {
            OidNode node = stack.removeFirst();
            results.add(node);
            List<OidNode> children = new ArrayList<>(node.children());
            Collections.reverse(children);
            for (OidNode child : children) {
                stack.addFirst(child);
            }
        }
        return results;
    }

    public List<OidNode> allNodes() {
        List<OidNode> nodes = new ArrayList<>(index.values());
        nodes.sort((left, right) -> ValidationUtils.compareOids(left.oid(), right.oid()));
        return nodes;
    }

    public String printTree() {
        StringBuilder builder = new StringBuilder();
        List<OidNode> roots = new ArrayList<>();
        for (OidNode node : allNodes()) {
            if (node.isRoot()) {
                roots.add(node);
            }
        }
        for (int index = 0; index < roots.size(); index++) {
            renderNode(builder, roots.get(index), 0);
            if (index + 1 < roots.size()) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    public String printTree(String oid) {
        return lookup(oid).map(this::renderNode).orElse("<no matching OID>");
    }

    private String renderNode(OidNode node) {
        StringBuilder builder = new StringBuilder();
        renderNode(builder, node, 0);
        return builder.toString();
    }

    private void renderNode(StringBuilder builder, OidNode node, int depth) {
        builder.append("  ".repeat(Math.max(0, depth)))
                .append(node.name())
                .append(" (")
                .append(node.oid())
                .append(")");
        if (node.currentValue() != null) {
            builder.append(" = ").append(node.currentValue());
        }
        builder.append(System.lineSeparator());
        for (OidNode child : node.children()) {
            renderNode(builder, child, depth + 1);
        }
    }
}
