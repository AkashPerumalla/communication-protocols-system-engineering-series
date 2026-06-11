public final class MibBrowserDemo {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibBrowserService browser = new MibBrowserService(repository.tree());
        MibConsole.printBanner("DAY 10 - EXERCISE 03 - MIB BROWSER", "Professional MIB Browser Demo");
        searchByName(browser, "sysName");
        searchByOid(browser, "1.3.6.1.2.1.1.5.0");
        listChildren(browser, "1.3.6.1.2.1.1");
        showParent(browser, "1.3.6.1.2.1.1.5.0");
        walkBranch(browser, "1.3.6.1.2.1.1");
    }

    private static void searchByName(MibBrowserService browser, String name) {
        System.out.println("Search by Name: " + name);
        browser.searchByName(name).ifPresentOrElse(MibConsole::printNode, () -> System.out.println("Not found"));
        System.out.println();
    }

    private static void searchByOid(MibBrowserService browser, String oid) {
        System.out.println("Search by OID: " + oid);
        browser.searchByOid(oid).ifPresentOrElse(MibConsole::printNode, () -> System.out.println("Not found"));
        System.out.println();
    }

    private static void listChildren(MibBrowserService browser, String oid) {
        System.out.println("List Children of: " + oid);
        browser.searchByOid(oid).ifPresent(node -> {
            for (MibNode child : node.children()) {
                MibConsole.printNode(child);
            }
        });
        System.out.println();
    }

    private static void showParent(MibBrowserService browser, String oid) {
        System.out.println("Show Parent of: " + oid);
        browser.showParent(oid).ifPresentOrElse(MibConsole::printNode, () -> System.out.println("<root>"));
        System.out.println();
    }

    private static void walkBranch(MibBrowserService browser, String oid) {
        System.out.println("Walk Branch: " + oid);
        for (MibNode node : browser.walkBranch(oid)) {
            String children = browser.formatChildren(node.children());
            System.out.printf("%s -> %s | parent=%s | children=%s%n",
                    MibConsole.displayOid(node),
                    node.name(),
                    node.parentOid().isEmpty() ? "<root>" : node.parentOid(),
                    children.isEmpty() ? "-" : children);
        }
        System.out.println();
    }
}
