public final class GetOperationEngine {
    private final MibRepository repository;

    public GetOperationEngine(MibRepository repository) {
        this.repository = repository;
    }

    public SnmpResponse execute(String oid) {
        long start = System.nanoTime();
        return repository.findByOid(oid)
            .map(object -> new SnmpResponse(
                ValidationUtils.normalizeOid(oid),
                object.oid(),
                object.displayName(),
                object.dataType(),
                object.currentValue() == null ? "-" : object.currentValue(),
                "OK",
                "GET Response",
                System.nanoTime() - start))
                .orElseGet(() -> new SnmpResponse(
                        ValidationUtils.normalizeOid(oid),
                        ValidationUtils.normalizeOid(oid),
                        "<unknown>",
                        "OBJECT IDENTIFIER",
                        "NO SUCH OBJECT",
                        "ERROR",
                "No matching OID was found",
                System.nanoTime() - start));
    }

    public SnmpResponse execute(SnmpRequest request) {
        return execute(request.oid());
    }
}
