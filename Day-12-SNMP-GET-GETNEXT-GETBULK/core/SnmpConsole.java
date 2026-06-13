import java.util.List;

public final class SnmpConsole {
    private SnmpConsole() {
    }

    public static String header(String title, String subtitle) {
        return title + "\n" + subtitle + "\n";
    }

    public static String formatResponse(String label, SnmpResponse response) {
        return label + "\n"
                + "Request OID: " + response.requestOid() + "\n"
                + "Resolved OID: " + response.resolvedOid() + "\n"
                + "Object Name: " + response.objectName() + "\n"
                + "Data Type: " + response.dataType() + "\n"
                + "Value: " + response.value() + "\n"
                + "Status: " + response.status() + "\n"
                + "Response Time: " + response.elapsedMillis() + " ms\n"
                + "Message: " + response.message() + "\n";
    }

    public static String formatResponses(String label, List<SnmpResponse> responses) {
        StringBuilder builder = new StringBuilder();
        builder.append(label).append('\n');
        for (SnmpResponse response : responses) {
            builder.append("- ")
                    .append(response.requestOid())
                    .append(" -> ")
                    .append(response.resolvedOid())
                    .append(" | ")
                    .append(response.objectName())
                    .append(" | ")
                    .append(response.value())
                    .append('\n');
        }
        return builder.toString();
    }
}
