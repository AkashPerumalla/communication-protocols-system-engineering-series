import java.util.List;
import java.util.Optional;

public final class OidResolver {
    private final OidTree tree;

    public OidResolver(OidTree tree) {
        this.tree = tree;
    }

    public String resolve(String oid) {
        String normalized = ValidationUtils.normalizeOid(oid);
        Optional<OidNode> exact = tree.lookup(normalized);
        if (exact.isPresent()) {
            return exact.get().canonicalPath();
        }

        List<Integer> parts = ValidationUtils.parseOid(normalized);
        for (int prefixLength = parts.size() - 1; prefixLength > 0; prefixLength--) {
            String prefix = join(parts, prefixLength);
            Optional<OidNode> node = tree.lookup(prefix);
            if (node.isPresent()) {
                String remainder = join(parts.subList(prefixLength, parts.size()));
                return node.get().canonicalPath() + "." + remainder;
            }
        }
        return normalized;
    }

    public Optional<OidNode> resolveNode(String oid) {
        return tree.lookup(oid);
    }

    private String join(List<Integer> parts, int endExclusive) {
        return join(parts.subList(0, endExclusive));
    }

    private String join(List<Integer> parts) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < parts.size(); index++) {
            if (index > 0) {
                builder.append('.');
            }
            builder.append(parts.get(index));
        }
        return builder.toString();
    }
}
