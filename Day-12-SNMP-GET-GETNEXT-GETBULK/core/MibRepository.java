import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class MibRepository {
    private final List<Oid> allOids;
    private final Map<String, Oid> byOid;
    private final Map<String, Oid> byName;

    public MibRepository(List<Oid> allOids) {
        this.allOids = new ArrayList<>(allOids);
        this.allOids.sort(Comparator.naturalOrder());
        this.byOid = new LinkedHashMap<>();
        this.byName = new LinkedHashMap<>();
        for (Oid oid : this.allOids) {
            byOid.put(oid.oid(), oid);
            byName.put(oid.name(), oid);
        }
    }

    public static MibRepository createDefault() {
        return new MibRepository(SampleData.allOids());
    }

    public List<Oid> allOids() {
        return List.copyOf(allOids);
    }

    public Optional<Oid> findByOid(String oid) {
        return Optional.ofNullable(byOid.get(ValidationUtils.normalizeOid(oid)));
    }

    public Optional<Oid> findByName(String name) {
        return Optional.ofNullable(byName.get(name));
    }

    public List<Oid> subtree(String rootOid) {
        String normalized = ValidationUtils.normalizeOid(rootOid);
        List<Oid> results = new ArrayList<>();
        for (Oid oid : allOids) {
            if (oid.oid().equals(normalized) || ValidationUtils.isPrefixOf(normalized, oid.oid())) {
                results.add(oid);
            }
        }
        results.sort(Comparator.naturalOrder());
        return results;
    }

    public Optional<Oid> nextAfter(String oid) {
        String normalized = ValidationUtils.normalizeOid(oid);
        for (Oid candidate : allOids) {
            if (ValidationUtils.compareOids(candidate.oid(), normalized) > 0) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }
}
