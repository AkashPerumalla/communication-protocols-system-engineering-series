public final class TrapProcessingDemo {
    private TrapProcessingDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 03 - TRAP PROCESSING", "Receive, validate, decode, store, forward"));
        EventRepository repository = new EventRepository();
        TrapProcessor processor = new TrapProcessor(new TrapReceiver(), repository, new NotificationService());
        System.out.print(processor.process(SampleData.processingTrap()));
    }
}
