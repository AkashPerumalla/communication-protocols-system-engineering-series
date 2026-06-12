import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public final class OidLookupService {
    private final OidTree tree;
    private final OidResolver resolver;

    public OidLookupService(OidTree tree, OidResolver resolver) {
        this.tree = tree;
        this.resolver = resolver;
    }

    public Optional<OidNode> findByOid(String oid) {
        return tree.lookup(oid);
    }

    public List<OidNode> findByName(String name) {
        String needle = name.toLowerCase(Locale.ROOT);
        return tree.allNodes().stream()
                .filter(node -> node.oidObject().name().toLowerCase(Locale.ROOT).equals(needle)
                        || node.name().toLowerCase(Locale.ROOT).equals(needle)
                        || node.oidObject().displayName().toLowerCase(Locale.ROOT).equals(needle))
                .collect(Collectors.toList());
    }

    public List<OidNode> findByPartialPath(String keyword) {
        String needle = keyword.toLowerCase(Locale.ROOT);
        return tree.allNodes().stream()
                .filter(node -> resolver.resolve(node.oid()).toLowerCase(Locale.ROOT).contains(needle)
                        || node.oidObject().name().toLowerCase(Locale.ROOT).contains(needle)
                        || node.oidObject().description().toLowerCase(Locale.ROOT).contains(needle))
                .collect(Collectors.toList());
    }

    public List<OidNode> search(String keyword) {
        return findByName(keyword).isEmpty() ? findByPartialPath(keyword) : findByName(keyword);
    }

    public List<OidNode> walk(String oid) {
        return tree.walk(oid);
    }
}
