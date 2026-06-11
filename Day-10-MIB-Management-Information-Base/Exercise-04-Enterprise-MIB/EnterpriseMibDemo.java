import java.util.List;

public final class EnterpriseMibDemo {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 04 - ENTERPRISE MIB", "Avantel Enterprise MIB Branch: " + EnterpriseMib.AVANTEL_ENTERPRISE_OID);
        printEnterpriseBranch(repository.enterpriseObjects());
    }

    private static void printEnterpriseBranch(List<MibObject> objects) {
        for (MibObject object : objects) {
            MibConsole.printNameValue("Enterprise OID", object.oid());
            MibConsole.printNameValue("Name", object.name());
            MibConsole.printNameValue("Type", object.type());
            MibConsole.printNameValue("Value", object.value());
            System.out.println();
        }
    }
}
