import java.util.ArrayList;
import java.util.List;

public final class GetBulkOperationEngine {
    private final MibRepository repository;

    public GetBulkOperationEngine(MibRepository repository) {
        this.repository = repository;
    }

    public List<SnmpResponse> execute(String oid, int nonRepeaters, int maxRepetitions) {
        return execute(new SnmpRequest("GETBULK", oid, nonRepeaters, maxRepetitions));
    }

    public List<SnmpResponse> execute(SnmpRequest request) {
        List<SnmpResponse> responses = new ArrayList<>();
        String baseOid = request.oid();
        long start = System.nanoTime();

        int exactCount = Math.max(0, request.nonRepeaters());
        for (int index = 0; index < exactCount; index++) {
            String exactOid = index == 0 ? baseOid : baseOid + "." + (index + 1);
            repository.findByOid(exactOid).ifPresent(object -> responses.add(new SnmpResponse(
                    exactOid,
                    object.oid(),
                    object.displayName(),
                    object.dataType(),
                    object.currentValue() == null ? "-" : object.currentValue(),
                    "OK",
                    "GETBULK Result",
                    System.nanoTime() - start)));
        }

        String current = baseOid;
        int repetitions = Math.max(0, request.maxRepetitions());
        while (repetitions-- > 0) {
            Oid next = repository.nextAfter(current).orElse(null);
            if (next == null) {
                break;
            }
            responses.add(new SnmpResponse(
                    baseOid,
                    next.oid(),
                    next.displayName(),
                    next.dataType(),
                    next.currentValue() == null ? "-" : next.currentValue(),
                    "OK",
                    "GETBULK Result",
                    System.nanoTime() - start));
            current = next.oid();
        }

        return responses;
    }
}
