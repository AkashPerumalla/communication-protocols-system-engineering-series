import java.util.List;

public final class SnmpAgentSimulator {
    private final MibRepository repository;
    private final GetOperationEngine getEngine;
    private final GetNextOperationEngine nextEngine;
    private final GetBulkOperationEngine bulkEngine;
    private final OidTraversalService traversalService;

    public SnmpAgentSimulator(MibRepository repository) {
        this.repository = repository;
        this.getEngine = new GetOperationEngine(repository);
        this.nextEngine = new GetNextOperationEngine(repository);
        this.bulkEngine = new GetBulkOperationEngine(repository);
        this.traversalService = new OidTraversalService(repository);
    }

    public SnmpResponse get(String oid) {
        return getEngine.execute(oid);
    }

    public SnmpResponse getNext(String oid) {
        return nextEngine.execute(oid);
    }

    public List<SnmpResponse> getBulk(String oid, int nonRepeaters, int maxRepetitions) {
        return bulkEngine.execute(oid, nonRepeaters, maxRepetitions);
    }

    public List<Oid> walk(String oid) {
        return traversalService.walk(oid);
    }

    public List<Oid> discover(String oid, int steps) {
        return traversalService.discoveryTrail(oid, steps);
    }

    public MibRepository repository() {
        return repository;
    }
}
