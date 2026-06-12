import java.util.ArrayList;
import java.util.List;

public final class OidWalkEngine {
    private final OidTree tree;

    public OidWalkEngine(OidTree tree) {
        this.tree = tree;
    }

    public List<OidResponse> walk(String branchOid) {
        List<OidResponse> responses = new ArrayList<>();
        for (OidNode node : tree.walk(branchOid)) {
            Oid oid = node.oidObject();
            responses.add(new OidResponse(branchOid, node.oid(), oid.name(), oid.dataType(), ValidationUtils.safe(oid.currentValue()), "OK", "WALK Result"));
        }
        return responses;
    }
}
