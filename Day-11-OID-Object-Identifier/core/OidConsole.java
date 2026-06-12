import java.util.List;
import java.util.StringJoiner;

public final class OidConsole {
    private OidConsole() {
    }

    public static void printBanner(String title, String subtitle) {
        System.out.println("============================================================");
        System.out.println(title);
        System.out.println("============================================================");
        System.out.println(subtitle);
        System.out.println();
    }

    public static void printNameValue(String label, String value) {
        System.out.println(label + ": " + ValidationUtils.safe(value));
    }

    public static void printNode(OidNode node) {
        printNameValue("OID", node.oid());
        printNameValue("Name", node.oidObject().name());
        printNameValue("Hierarchy", node.canonicalPath());
        printNameValue("Type", node.dataType());
        printNameValue("Value", node.currentValue());
        printNameValue("Parent", node.parentOid().isEmpty() ? "<root>" : node.parentOid());
        System.out.println();
    }

    public static void printOid(Oid oid) {
        printNameValue("OID", oid.oid());
        printNameValue("Name", oid.name());
        printNameValue("Hierarchy", oid.canonicalPath());
        printNameValue("Type", oid.dataType());
        printNameValue("Value", oid.currentValue());
        printNameValue("Depth", String.valueOf(oid.depth()));
        printNameValue("Parent", oid.getParent() == null ? "<root>" : oid.getParent().oid());
        System.out.println();
    }

    public static void printResponse(OidResponse response) {
        System.out.print(response.format());
        System.out.println();
    }

    public static void printTree(OidTree tree) {
        System.out.println("Hierarchy");
        System.out.println(tree.printTree());
    }

    public static void printDevice(DeviceProfile device) {
        System.out.println(device.hostName());
        printNameValue("Status", device.status());
        printNameValue("CPU", device.cpuPercent() + "%");
        printNameValue("Memory", device.memoryPercent() + "%");
        printNameValue("Uptime", device.uptime());
        printNameValue("Critical OIDs", join(device.criticalOids()));
        System.out.println();
    }

    public static void printTable(String header, List<String[]> rows) {
        System.out.println(header);
        for (String[] row : rows) {
            System.out.printf("%-16s | %-8s | %-4s | %-6s | %-10s | %s%n", row[0], row[1], row[2], row[3], row[4], row[5]);
        }
        System.out.println();
    }

    private static String join(List<String> values) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String value : values) {
            joiner.add(value);
        }
        return joiner.toString();
    }
}
