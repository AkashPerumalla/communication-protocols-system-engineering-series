import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class TelemetryNode {
    private final int port;
    private final String addr;
    private final String payload;
    private static final Logger LOGGER = Logger.getLogger(TelemetryNode.class.getName());

    public TelemetryNode(int port, String addr, String payload) {
        this.port = port;
        this.addr = addr;
        this.payload = payload;
    }

    public void start() throws Exception {
        LOGGER.info("Starting TelemetryNode " + addr + " on port " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line = in.readLine();
                    LOGGER.info("[" + addr + "] RECV: " + line);
                    // Ignore command, always send telemetry
                    out.println("ADDR=" + addr + ";" + payload);
                } catch (IOException e) {
                    LOGGER.warning("Conn error: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Default node for testing if started standalone
        TelemetryNode n = new TelemetryNode(8006, "01", "TEMP=35;POWER=ON;STATUS=NORMAL;ALARM=NONE");
        n.start();
    }
}
