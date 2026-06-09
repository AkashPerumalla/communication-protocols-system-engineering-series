import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class SnmpMibAgent {
    private final NavigableMap<SnmpOid, SnmpVariable> variables = new TreeMap<>();

    public SnmpMibAgent register(SnmpVariable variable) {
        variables.put(variable.oid(), variable);
        return this;
    }

    public SnmpResponse get(String oidText) {
        SnmpOid oid = SnmpOid.of(oidText);
        SnmpVariable variable = variables.get(oid);
        if (variable == null) {
            return SnmpResponse.failure("GET", oid, "NO SUCH OBJECT: " + oid);
        }
        return SnmpResponse.success("GET", oid, variable.value(), variable.name() + " = " + variable.value());
    }

    public SnmpResponse getNext(String oidText) {
        SnmpOid oid = SnmpOid.of(oidText);
        Map.Entry<SnmpOid, SnmpVariable> next = variables.higherEntry(oid);
        if (next == null) {
            return SnmpResponse.failure("GETNEXT", oid, "END OF MIB VIEW");
        }
        SnmpVariable variable = next.getValue();
        return SnmpResponse.success("GETNEXT", next.getKey(), variable.value(), next.getKey() + " = " + variable.value());
    }

    public SnmpResponse set(String oidText, String value) {
        SnmpOid oid = SnmpOid.of(oidText);
        SnmpVariable variable = variables.get(oid);
        if (variable == null) {
            return SnmpResponse.failure("SET", oid, "NO SUCH OBJECT: " + oid);
        }
        if (!variable.isWritable()) {
            return SnmpResponse.failure("SET", oid, "READ-ONLY OID REJECTED: " + variable.name());
        }
        if (!variable.validate(value)) {
            return SnmpResponse.failure("SET", oid, "INVALID VALUE REJECTED: " + value);
        }
        variable.setValue(value);
        return SnmpResponse.success("SET", oid, value, variable.name() + " updated to " + value);
    }

    public List<SnmpVariable> walkFrom(String startOid) {
        SnmpOid start = SnmpOid.of(startOid);
        List<SnmpVariable> discovered = new ArrayList<>();
        Map.Entry<SnmpOid, SnmpVariable> first = variables.ceilingEntry(start);
        if (first == null) {
            return discovered;
        }
        for (Map.Entry<SnmpOid, SnmpVariable> entry : variables.tailMap(first.getKey(), true).entrySet()) {
            discovered.add(entry.getValue());
        }
        return discovered;
    }

    public Map<SnmpOid, SnmpVariable> snapshot() {
        return new LinkedHashMap<>(variables);
    }
}
