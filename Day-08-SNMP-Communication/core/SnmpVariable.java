import java.util.Objects;
import java.util.function.Predicate;

public final class SnmpVariable {
    private final SnmpOid oid;
    private final String name;
    private final SnmpAccessMode accessMode;
    private final Predicate<String> validator;
    private String value;

    public SnmpVariable(SnmpOid oid, String name, String value, SnmpAccessMode accessMode) {
        this(oid, name, value, accessMode, candidate -> true);
    }

    public SnmpVariable(SnmpOid oid, String name, String value, SnmpAccessMode accessMode, Predicate<String> validator) {
        this.oid = Objects.requireNonNull(oid, "oid");
        this.name = Objects.requireNonNull(name, "name");
        this.value = Objects.requireNonNull(value, "value");
        this.accessMode = Objects.requireNonNull(accessMode, "accessMode");
        this.validator = Objects.requireNonNull(validator, "validator");
    }

    public SnmpOid oid() {
        return oid;
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    public boolean isWritable() {
        return accessMode == SnmpAccessMode.READ_WRITE;
    }

    public boolean validate(String candidate) {
        return validator.test(candidate);
    }

    public void setValue(String candidate) {
        this.value = candidate;
    }
}
