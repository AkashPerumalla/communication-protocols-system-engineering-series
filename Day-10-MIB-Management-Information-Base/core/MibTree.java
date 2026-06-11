import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MibTree {
    private static final Map<String, String> STANDARD_LABELS = Map.ofEntries(
            Map.entry("1", "iso"),
            Map.entry("1.3", "org"),
            Map.entry("1.3.6", "dod"),
            Map.entry("1.3.6.1", "internet"),
            Map.entry("1.3.6.1.2", "mgmt"),
            Map.entry("1.3.6.1.2.1", "mib-2"),
            Map.entry("1.3.6.1.2.1.1", "system"),
            Map.entry("1.3.6.1.2.1.2", "interfaces"),
            Map.entry("1.3.6.1.4.1", "enterprises"),
            Map.entry("1.3.6.1.4.1.99999", "avantel")
    );

    private final MibNode root = new MibNode("", "root", null, null);
    private final Map<String, MibNode> index = new LinkedHashMap<>();

    public MibTree() {
        index.put("", root);
    }

    public void addObject(MibObject object) {
        String normalizedOid = OidParser.normalize(object.oid());
        List<Integer> values = OidParser.parse(normalizedOid);
        StringBuilder prefix = new StringBuilder();
        MibNode parent = root;
        for (int position = 0; position < values.size(); position++) {
            if (position > 0) {
                prefix.append('.');
            }
            prefix.append(values.get(position));
            String prefixOid = prefix.toString();
            MibNode current = index.get(prefixOid);
            if (current == null) {
                String label = labelFor(prefixOid, position == values.size() - 1 ? object.name() : null);
                MibObject currentObject = position == values.size() - 1 ? object : null;
                current = new MibNode(prefixOid, label, currentObject, parent);
                index.put(prefixOid, current);
                parent.addChild(current);
            } else if (position == values.size() - 1 && current.object() == null) {
                current = new MibNode(prefixOid, current.name(), object, parent);
                index.put(prefixOid, current);
                parent.addChild(current);
            }
            parent = current;
        }
    }

    public Optional<MibNode> findByOid(String oid) {
        return Optional.ofNullable(index.get(OidParser.normalize(oid)));
    }

    public Optional<MibNode> findByName(String name) {
        String candidate = name.toLowerCase();
        return index.values().stream()
                .filter(node -> node.name().equalsIgnoreCase(candidate) || (node.object() != null && node.object().name().equalsIgnoreCase(candidate)))
                .findFirst();
    }

    public List<MibNode> childrenOf(String oid) {
        return findByOid(oid).map(MibNode::children).orElseGet(Collections::emptyList);
    }

    public Optional<MibNode> parentOf(String oid) {
        return findByOid(oid).map(MibNode::parent);
    }

    public List<MibNode> walkBranch(String oid) {
        Optional<MibNode> start = findByOid(oid);
        if (start.isEmpty()) {
            return List.of();
        }
        List<MibNode> results = new ArrayList<>();
        walkDepthFirst(start.get(), results);
        return results;
    }

    public String renderHierarchy(String oid) {
        Optional<MibNode> start = findByOid(oid);
        if (start.isEmpty()) {
            return "<not found>";
        }
        StringBuilder builder = new StringBuilder();
        renderNode(start.get(), "", builder, true);
        return builder.toString();
    }

    public Map<String, MibNode> snapshot() {
        return Collections.unmodifiableMap(index);
    }

    private void walkDepthFirst(MibNode node, List<MibNode> results) {
        results.add(node);
        for (MibNode child : node.children()) {
            walkDepthFirst(child, results);
        }
    }

    private void renderNode(MibNode node, String indent, StringBuilder builder, boolean last) {
        builder.append(indent);
        if (!indent.isEmpty()) {
            builder.append(last ? "\\- " : "|- ");
        }
        builder.append(node.oid().isEmpty() ? "<root>" : node.oid())
                .append(" ")
                .append(node.name())
                .append(" [")
                .append(node.displayType())
                .append("]")
                .append('\n');
        List<MibNode> children = node.children();
        for (int index = 0; index < children.size(); index++) {
            MibNode child = children.get(index);
            renderNode(child, indent + (indent.isEmpty() ? "" : last ? "   " : "|  "), builder, index == children.size() - 1);
        }
    }

    private String labelFor(String prefixOid, String fallback) {
        return STANDARD_LABELS.getOrDefault(prefixOid, fallback != null ? fallback : prefixOid.substring(prefixOid.lastIndexOf('.') + 1));
    }
}
