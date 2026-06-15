import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class SnmpAgent {
    private final String name;
    private final Map<String, String> managedObjects;
    private final SecurityValidator securityValidator;
    private final AuthenticationEngine authenticationEngine;
    private final EncryptionEngine encryptionEngine;

    public SnmpAgent(String name, Map<String, String> managedObjects) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.managedObjects = new LinkedHashMap<>(Objects.requireNonNull(managedObjects, "managedObjects must not be null"));
        this.securityValidator = new SecurityValidator();
        this.authenticationEngine = new AuthenticationEngine();
        this.encryptionEngine = new EncryptionEngine();
    }

    public String name() {
        return name;
    }

    public SnmpResponse handleRequest(SnmpRequest request) {
        ValidationUtils.requireNonNull(request, "request");
        long latency = simulatedLatencyMicros(request.version(), request.operation());
        boolean authenticated;
        SecurityLevel securityLevel;
        String payload;

        if (request.version() == SnmpVersion.SNMPv3) {
            authenticated = securityValidator.validateUser(request.user());
            AuthenticationEngine.AuthenticationResult authenticationResult = authenticationEngine.authenticate(request.user());
            securityLevel = request.user().securityLevel();
            String rawValue = resolveValue(request);
            payload = request.user().privacyProtocol() == PrivacyProtocol.NONE
                    ? rawValue
                    : encryptionEngine.encrypt(rawValue, request.user().privacyProtocol(), request.user().privacySecret());
            String message = authenticationResult.summary();
            if (!authenticated) {
                return new SnmpResponse(request.version(), false, "AUTH FAILED", request.oid(), rawValue, latency, securityLevel, securityLevel.encrypted(), payload);
            }
            return new SnmpResponse(request.version(), true, message, request.oid(), rawValue, latency, securityLevel, securityLevel.encrypted(), payload);
        }

        authenticated = securityValidator.validateCommunity(request.communityString(), request.version());
        securityLevel = SecurityLevel.NO_AUTH_NO_PRIV;
        String rawValue = resolveValue(request);
        payload = rawValue;
        if (!authenticated) {
            return new SnmpResponse(request.version(), false, "COMMUNITY REJECTED", request.oid(), rawValue, latency, securityLevel, false, payload);
        }
        return new SnmpResponse(request.version(), true, request.operation().name() + " completed for " + request.oid(), request.oid(), rawValue, latency, securityLevel, false, payload);
    }

    private String resolveValue(SnmpRequest request) {
        return switch (request.operation()) {
            case GET -> managedObjects.getOrDefault(request.oid(), "NO SUCH OBJECT");
            case GETNEXT -> resolveNext(request.oid());
            case GETBULK -> resolveBulk(request.oid());
        };
    }

    private String resolveNext(String oid) {
        List<String> orderedKeys = new ArrayList<>(managedObjects.keySet());
        for (String key : orderedKeys) {
            if (key.compareTo(oid) > 0) {
                return key + " = " + managedObjects.get(key);
            }
        }
        return "END OF MIB VIEW";
    }

    private String resolveBulk(String oid) {
        List<String> orderedKeys = new ArrayList<>(managedObjects.keySet());
        List<String> lines = new ArrayList<>();
        boolean collect = false;
        int collected = 0;
        for (String key : orderedKeys) {
            if (collect || key.equals(oid) || key.compareTo(oid) > 0) {
                collect = true;
                lines.add(key + " = " + managedObjects.get(key));
                collected++;
            }
            if (collected == 3) {
                break;
            }
        }
        if (lines.isEmpty()) {
            return "NO BULK DATA";
        }
        return String.join(" | ", lines);
    }

    private long simulatedLatencyMicros(SnmpRequest.Operation operation, SnmpVersion version) {
        return simulatedLatencyMicros(version, operation);
    }

    private long simulatedLatencyMicros(SnmpVersion version, SnmpRequest.Operation operation) {
        long base = switch (version) {
            case SNMPv1 -> 820L;
            case SNMPv2c -> 540L;
            case SNMPv3 -> 980L;
        };
        long operationPenalty = switch (operation) {
            case GET -> 40L;
            case GETNEXT -> 95L;
            case GETBULK -> 140L;
        };
        long securityPenalty = version == SnmpVersion.SNMPv3 ? 160L : 0L;
        return base + operationPenalty + securityPenalty;
    }
}
