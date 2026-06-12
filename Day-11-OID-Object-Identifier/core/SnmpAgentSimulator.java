import java.util.List;

public final class SnmpAgentSimulator {
    private final OidRepository repository;
    private final OidQueryEngine queryEngine;
    private final OidResolver resolver;
    private final OidLookupService lookupService;

    public SnmpAgentSimulator(OidRepository repository) {
        this.repository = repository;
        this.queryEngine = new OidQueryEngine(repository.tree());
        this.resolver = new OidResolver(repository.tree());
        this.lookupService = new OidLookupService(repository.tree(), resolver);
    }

    public OidResponse get(String oid) {
        return queryEngine.get(oid);
    }

    public OidResponse getNext(String oid) {
        return queryEngine.getNext(oid);
    }

    public List<OidResponse> walk(String oid) {
        return queryEngine.walk(oid);
    }

    public String resolve(String oid) {
        return resolver.resolve(oid);
    }

    public OidLookupService lookupService() {
        return lookupService;
    }

    public OidRepository repository() {
        return repository;
    }
}
