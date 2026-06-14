public final class EventCorrelationDemo {
    private EventCorrelationDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 07 - EVENT CORRELATION", "Root-cause identification across related traps"));
        EventCorrelationEngine engine = new EventCorrelationEngine();
        System.out.print(engine.correlate(SampleData.correlationTraps()));
    }
}
