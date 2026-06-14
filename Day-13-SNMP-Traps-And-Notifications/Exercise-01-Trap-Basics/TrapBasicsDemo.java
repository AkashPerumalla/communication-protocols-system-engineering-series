import java.util.List;

public final class TrapBasicsDemo {
    private TrapBasicsDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 01 - TRAP BASICS", "Agent-generated asynchronous notifications"));
        System.out.print(ConsoleFormatter.section("Agent-generated traps"));
        List<TrapEvent> traps = SampleData.trapBasics();
        for (TrapEvent trap : traps) {
            System.out.print(ConsoleFormatter.formatTrap(trap));
        }
        System.out.println("TRAP GENERATED");
    }
}
