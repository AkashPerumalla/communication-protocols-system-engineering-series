public final class OidHierarchyExplorer {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        OidConsole.printBanner("DAY 11 - EXERCISE 02 - OID HIERARCHY", "OID Hierarchy Explorer");
        System.out.println("Hierarchy");
        System.out.println(repository.tree().printTree());
    }
}
