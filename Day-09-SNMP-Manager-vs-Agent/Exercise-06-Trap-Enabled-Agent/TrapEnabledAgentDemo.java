public class TrapEnabledAgentDemo {
    public static void main(String[] args) {
        TrapReceiver receiver = new TrapReceiver();
        TrapEnabledAgent agent = new TrapEnabledAgent(receiver);

        for (TrapEvent event : SnmpFixtures.trapEvents()) {
            agent.emit(event);
        }

        System.out.println("Teach:");
        System.out.println("- Traps");
        System.out.println("- Event Notifications");
        System.out.println("- Fault Management");
    }
}
