import java.util.List;

public final class OidBasicsDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        SnmpAgentSimulator agent = new SnmpAgentSimulator(repository);
        OidLookupService lookup = agent.lookupService();
        OidNode node = lookup.findByOid("1.3.6.1.2.1.1.5.0").orElseThrow();

        OidConsole.printBanner("DAY 11 - EXERCISE 01 - OID BASICS", "OID Basics");
        OidConsole.printNameValue("OID", node.oid());
        OidConsole.printNameValue("OID Name", node.oidObject().name());
        OidConsole.printNameValue("Device", repository.devices().get(0).hostname());
        OidConsole.printNameValue("Components", String.join(".", List.of("1", "3", "6", "1", "2", "1", "1", "5", "0")));
        OidConsole.printNameValue("Hierarchy Depth", String.valueOf(node.depth()));
        OidConsole.printNameValue("Parent Node", node.parentOid());
        OidConsole.printNameValue("Resolved Path", agent.resolve(node.oid()));
    }
}
