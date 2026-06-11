public final class OidHierarchyExplorer {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibBrowserService browser = new MibBrowserService(repository.tree());
        MibTree tree = repository.tree();
        MibConsole.printBanner("DAY 10 - EXERCISE 02 - OID HIERARCHY EXPLORER", "OID Hierarchy Explorer");
        System.out.println("OID hierarchy:");
        System.out.println(browser.describeBranch("1"));
        printBranchTable(browser.walkBranch("1.3.6.1.2.1"));
    }

    private static void printBranchTable(java.util.List<MibNode> nodes) {
        System.out.println("OID               | Name               | Parent            | Children");
        System.out.println("-------------------+--------------------+-------------------+-----------------------------");
        for (MibNode node : nodes) {
            String children = node.children().isEmpty() ? "-" : MibBrowserService.formatChildNames(node.children());
            System.out.printf("%-18s | %-18s | %-17s | %s%n",
                    MibConsole.displayOid(node),
                    node.name(),
                    node.parentOid().isEmpty() ? "<root>" : node.parentOid(),
                    children);
        }
    }
}
