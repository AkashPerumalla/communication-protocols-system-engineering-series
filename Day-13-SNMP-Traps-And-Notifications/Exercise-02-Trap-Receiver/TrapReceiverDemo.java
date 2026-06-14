public final class TrapReceiverDemo {
    private TrapReceiverDemo() {
    }

    public static void main(String[] args) {
        System.out.print(ConsoleFormatter.header("DAY 13 - EXERCISE 02 - TRAP RECEIVER", "Agent to receiver simulation"));
        TrapReceiver receiver = new TrapReceiver();
        TrapEvent trap = SampleData.receiverTrap();
        System.out.print(receiver.receipt(trap));
        System.out.println("TRAP RECEIVED");
    }
}
