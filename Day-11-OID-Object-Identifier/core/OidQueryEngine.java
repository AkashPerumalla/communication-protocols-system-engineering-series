import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class OidQueryEngine {
    private final OidTree tree;
    private final OidWalkEngine walkEngine;

    public OidQueryEngine(OidTree tree) {
        this.tree = tree;
        this.walkEngine = new OidWalkEngine(tree);
    }

    public OidResponse get(String oid) {
        Optional<OidNode> node = tree.lookup(oid);
        if (node.isEmpty()) {
            return new OidResponse(oid, oid, "<unknown>", "OBJECT IDENTIFIER", "NO SUCH OBJECT", "ERROR", "No matching OID was found");
        }
        Oid object = node.get().oidObject();
        return new OidResponse(oid, object.oid(), object.name(), object.dataType(), ValidationUtils.safe(object.currentValue()), "OK", "GET Response");
    }

    public OidResponse getNext(String oid) {
        List<OidNode> nodes = new ArrayList<>(tree.allNodes());
        nodes.sort(Comparator.comparing(OidNode::oid, ValidationUtils::compareOids));
        for (OidNode node : nodes) {
            if (ValidationUtils.compareOids(node.oid(), oid) > 0) {
                Oid object = node.oidObject();
                return new OidResponse(oid, object.oid(), object.name(), object.dataType(), ValidationUtils.safe(object.currentValue()), "OK", "GETNEXT Response");
            }
        }
        return new OidResponse(oid, oid, "<end-of-mib>", "OBJECT IDENTIFIER", "NO NEXT OBJECT", "END", "No lexicographically greater OID exists");
    }

    public List<OidResponse> walk(String oid) {
        return walkEngine.walk(oid);
    }
}
