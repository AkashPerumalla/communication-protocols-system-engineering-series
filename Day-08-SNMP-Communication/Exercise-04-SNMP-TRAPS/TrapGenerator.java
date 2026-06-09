import java.util.List;

public final class TrapGenerator {
    public List<SnmpTrap> traps() {
        return DemoDataFactory.trapSequence();
    }
}
