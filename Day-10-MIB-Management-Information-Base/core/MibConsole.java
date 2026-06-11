import java.util.List;

public final class MibConsole {
    private MibConsole() {
    }

    public static void printBanner(String title, String subtitle) {
        System.out.println("============================================================");
        System.out.println(title);
        System.out.println("============================================================");
        System.out.println(subtitle);
        System.out.println();
    }

    public static void printObject(MibObject object) {
        System.out.println("OID:   " + object.oid());
        System.out.println("Name:  " + object.name());
        System.out.println("Type:  " + object.type());
        System.out.println("Value: " + object.value());
        System.out.println("Access: " + object.access());
        System.out.println();
    }

    public static void printNode(MibNode node) {
        MibObject object = node.object();
        System.out.println("OID:   " + displayOid(node));
        System.out.println("Name:  " + node.name());
        System.out.println("Type:  " + (object == null ? "NODE" : object.type()));
        System.out.println("Value: " + (object == null ? "<branch>" : object.value()));
        System.out.println("Access: " + (object == null ? "n/a" : object.access()));
    }

    public static void printNameValue(String name, String value) {
        System.out.println(name + ": " + value);
    }

    public static void printRows(String header, String divider, List<String[]> rows) {
        System.out.println(header);
        System.out.println(divider);
        for (String[] row : rows) {
            System.out.printf("%-23s | %-6s | %-3s | %-6s | %-11s | %s%n",
                    row[0], row[1], row[2], row[3], row[4], row[5]);
        }
    }

    public static String displayOid(MibNode node) {
        return node.oid().isEmpty() ? "<root>" : node.oid();
    }
}
