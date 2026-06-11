import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MibBrowserService {
    private final MibTree tree;

    public MibBrowserService(MibTree tree) {
        this.tree = tree;
    }

    public Optional<MibNode> searchByOid(String oid) {
        return tree.findByOid(oid);
    }

    public Optional<MibNode> searchByName(String name) {
        return tree.findByName(name);
    }

    public List<MibNode> listChildren(String oid) {
        return tree.childrenOf(oid);
    }

    public Optional<MibNode> showParent(String oid) {
        return tree.parentOf(oid);
    }

    public List<MibNode> walkBranch(String oid) {
        return tree.walkBranch(oid);
    }

    public String describeBranch(String oid) {
        return tree.renderHierarchy(oid);
    }

    public String formatChildren(List<MibNode> nodes) {
        return nodes.stream()
                .map(node -> node.name() + "=" + MibConsole.displayOid(node))
                .collect(Collectors.joining(", "));
    }

    public static String formatChildNames(List<MibNode> nodes) {
        return nodes.stream()
                .map(MibNode::name)
                .collect(Collectors.joining(", "));
    }
}
