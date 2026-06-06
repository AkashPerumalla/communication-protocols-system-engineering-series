import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class TokenMasterA {
    private static final String ARBITER_HOST = "localhost";
    private static final int ARBITER_PORT = 8020;
    private static final Logger LOGGER = Logger.getLogger(TokenMasterA.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        try (Socket s = new Socket(ARBITER_HOST, ARBITER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String welcome = in.readLine();
            LOGGER.info("Arbiter says: " + welcome);
            String line;
            int deviceIdx = 0;
            int[] devicePorts = {8002, 8003, 8004};
            while ((line = in.readLine()) != null) {
                if (line.equals("TOKEN")) {
                    String ts = LocalDateTime.now().format(TF);
                    int port = devicePorts[deviceIdx % devicePorts.length];
                    LOGGER.info("[A] Got TOKEN at " + ts + ", polling device on port " + port);
                    // Poll device
                    try (Socket ds = new Socket("localhost", port);
                         BufferedReader din = new BufferedReader(new InputStreamReader(ds.getInputStream()));
                         PrintWriter dout = new PrintWriter(ds.getOutputStream(), true)) {
                        dout.println("ADDR=??;CMD=READ_TEMP");
                        String resp = din.readLine();
                        LOGGER.info("[A] Device resp: " + resp);
                    } catch (IOException e) {
                        LOGGER.warning("[A] Poll failed: " + e.getMessage());
                    }
                    out.println("DONE_A");
                    deviceIdx++;
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Arbiter connection failed: " + e.getMessage());
        }
    }
}
