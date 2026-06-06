import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class ScadaMaster {
    private static final Logger LOGGER = Logger.getLogger(ScadaMaster.class.getName());
    private static final String HOST = "localhost";
    private static final int[] PORTS = {8010, 8011, 8012};

    public static void main(String[] args) {
        for (int p : PORTS) {
            try (Socket s = new Socket(HOST, p);
                 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                out.println("POLL");
                String resp = in.readLine();
                String id = resp != null ? resp.split(";")[0].replace("ID=", "") : "UNKNOWN";
                String status = resp != null && resp.contains("ALARM=NONE") ? "OK" : "ALARM";
                System.out.println(id + " " + status + " -> " + resp);
            } catch (IOException e) {
                LOGGER.warning("Failed to poll port " + p + ": " + e.getMessage());
            }
        }
    }
}
