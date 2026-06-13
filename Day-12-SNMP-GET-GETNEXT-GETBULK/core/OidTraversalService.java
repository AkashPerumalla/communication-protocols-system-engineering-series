import java.util.ArrayList;
import java.util.List;

public final class OidTraversalService {
    private final MibRepository repository;

    public OidTraversalService(MibRepository repository) {
        this.repository = repository;
    }

    public List<Oid> walk(String oid) {
        return repository.subtree(oid);
    }

    public List<Oid> discoveryTrail(String oid, int steps) {
        List<Oid> trail = new ArrayList<>();
        String current = oid;
        for (int index = 0; index < steps; index++) {
            Oid next = repository.nextAfter(current).orElse(null);
            if (next == null) {
                break;
            }
            trail.add(next);
            current = next.oid();
        }
        return trail;
    }
}
