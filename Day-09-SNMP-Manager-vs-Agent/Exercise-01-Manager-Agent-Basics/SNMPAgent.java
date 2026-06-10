public class SNMPAgent extends SnmpAgentCore {
    public SNMPAgent() {
        super(SnmpFixtures.routerAgent().deviceName(), SnmpFixtures.routerAgent().browse());
    }
}
