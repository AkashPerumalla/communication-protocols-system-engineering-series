import java.util.ArrayList;
import java.util.List;

public final class BulkRetrievalService {
    private final GetBulkOperationEngine bulkEngine;
    private final GetNextOperationEngine nextEngine;

    public BulkRetrievalService(MibRepository repository) {
        this.bulkEngine = new GetBulkOperationEngine(repository);
        this.nextEngine = new GetNextOperationEngine(repository);
    }

    public List<SnmpResponse> retrieve(String oid, int maxRepetitions) {
        return bulkEngine.execute(oid, 0, maxRepetitions);
    }

    public List<SnmpResponse> walkViaGetNext(String oid, int steps) {
        List<SnmpResponse> responses = new ArrayList<>();
        String current = oid;
        for (int index = 0; index < steps; index++) {
            SnmpResponse response = nextEngine.execute(current);
            responses.add(response);
            if (!"OK".equals(response.status())) {
                break;
            }
            current = response.resolvedOid();
        }
        return responses;
    }
}
