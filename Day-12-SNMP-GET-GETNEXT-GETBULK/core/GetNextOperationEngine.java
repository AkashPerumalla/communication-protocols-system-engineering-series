public final class GetNextOperationEngine {
    private final MibRepository repository;

    public GetNextOperationEngine(MibRepository repository) {
        this.repository = repository;
    }

    public SnmpResponse execute(String oid) {
        long start = System.nanoTime();
        String normalized = ValidationUtils.normalizeOid(oid);
        return repository.nextAfter(normalized)
                .map(object -> new SnmpResponse(
                        normalized,
                object.oid(),
                object.displayName(),
                    object.dataType(),
                object.currentValue() == null ? "-" : object.currentValue(),
                        "OK",
                "GETNEXT Result",
                System.nanoTime() - start))
                .orElseGet(() -> new SnmpResponse(
                        normalized,
                        normalized,
                        "<end-of-mib>",
                        "OBJECT IDENTIFIER",
                        "NO NEXT OBJECT",
                        "END",
                "No lexicographically greater OID exists",
                0L));
    }

    public SnmpResponse execute(SnmpRequest request) {
        return execute(request.oid());
    }
}
