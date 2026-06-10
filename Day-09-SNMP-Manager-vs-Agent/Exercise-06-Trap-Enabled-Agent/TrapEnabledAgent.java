public class TrapEnabledAgent extends SnmpAgentCore {
    private final TrapReceiver receiver;

    public TrapEnabledAgent(TrapReceiver receiver) {
        super(SnmpFixtures.routerAgent().deviceName(), SnmpFixtures.routerAgent().browse());
        this.receiver = receiver;
    }

    public void emit(TrapEvent event) {
        receiver.printReceipt(event);
    }
}
