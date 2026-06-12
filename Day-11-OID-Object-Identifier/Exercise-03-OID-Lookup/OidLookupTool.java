import java.util.List;

public final class OidLookupTool {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        OidLookupService lookup = new SnmpAgentSimulator(repository).lookupService();

        OidConsole.printBanner("DAY 11 - EXERCISE 03 - OID LOOKUP", "OID Lookup Tool");
        printSearch("sysName", lookup.search("sysName"));
        printSearch("sysLocation", lookup.search("sysLocation"));
        printSearch("sysUpTime", lookup.search("sysUpTime"));
    }

    private static void printSearch(String term, List<OidNode> nodes) {
        System.out.println("Search: " + term);
        for (OidNode node : nodes) {
            OidConsole.printNameValue("OID", node.oid());
            OidConsole.printNameValue("Name", node.oidObject().name());
            OidConsole.printNameValue("Hierarchy", node.canonicalPath());
            OidConsole.printNameValue("Value", node.currentValue());
            System.out.println();
        }
    }
}
