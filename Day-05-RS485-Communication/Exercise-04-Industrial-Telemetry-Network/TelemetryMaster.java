import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class TelemetryMaster {
    private static final Logger LOGGER = Logger.getLogger(TelemetryMaster.class.getName());
    private static final String HOST = "localhost";
    private static final int[] PORTS = {8006, 8007, 8008};
    private static final String[] ADDRS = {"01", "02", "03"};

    public static void main(String[] args) {
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < PORTS.length; i++) {
            String resp = query(PORTS[i]);
            rows.add(resp != null ? resp : ("ADDR=" + ADDRS[i] + ";STATUS=UNREACHABLE"));
        }
        System.out.println("Aggregated Telemetry:");
        for (String r : rows) System.out.println(r);
    }

    private static String query(int port) {
        try (Socket s = new Socket(HOST, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            out.println("GET_TELEMETRY");
            String resp = in.readLine();
            LOGGER.info("Got: " + resp);
            return resp;
        } catch (IOException e) {
            LOGGER.warning("Failed query port " + port + ": " + e.getMessage());
            return null;
        }
    }
}
