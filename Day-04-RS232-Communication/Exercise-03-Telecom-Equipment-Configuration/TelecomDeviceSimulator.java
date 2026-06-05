import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Telecom device simulator with simple configuration state machine.
 */
public class TelecomDeviceSimulator {
    private static final Logger LOGGER = Logger.getLogger(TelecomDeviceSimulator.class.getName());
    private static final int DEFAULT_PORT = 7003;
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Map<String, String> config = new HashMap<>();

    public TelecomDeviceSimulator() {
        // defaults
        config.put("FREQ", "1400"); // MHz
        config.put("POWER", "LOW");
        config.put("STATUS", "INACTIVE");
    }

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException e) { }
        }
        TelecomDeviceSimulator sim = new TelecomDeviceSimulator();
        sim.run(port);
    }

    public void run(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("TelecomDeviceSimulator listening on " + port);
            while (true) {
                Socket s = server.accept();
                LOGGER.info("Connection from " + s.getRemoteSocketAddress());
                new Thread(() -> handle(s)).start();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error", e);
        }
    }

    private void handle(Socket s) {
        try (s; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim();
                LOGGER.info("RX: " + cmd);
                if (cmd.equalsIgnoreCase("SHOW CONFIG")) {
                    out.println("FREQ=" + config.get("FREQ") + " MHz");
                    out.println("POWER=" + config.get("POWER"));
                    out.println("STATUS=" + config.get("STATUS"));
                } else if (cmd.toUpperCase().startsWith("SET FREQ ")) {
                    String[] parts = cmd.split("\\s+");
                    if (parts.length >= 3) {
                        String val = parts[2];
                        config.put("FREQ", val);
                        out.println("OK");
                    } else {
                        out.println("ERROR");
                    }
                } else if (cmd.toUpperCase().startsWith("SET POWER ")) {
                    String[] parts = cmd.split("\\s+");
                    if (parts.length >= 3) {
                        String val = parts[2].toUpperCase();
                        if (val.equals("HIGH") || val.equals("LOW")) {
                            config.put("POWER", val);
                            if (val.equals("HIGH")) config.put("STATUS", "ACTIVE");
                            out.println("OK");
                        } else {
                            out.println("ERROR: INVALID POWER");
                        }
                    } else {
                        out.println("ERROR");
                    }
                } else if (cmd.equalsIgnoreCase("SHOW STATUS")) {
                    out.println("FREQ=" + config.get("FREQ") + " MHz");
                    out.println("POWER=" + config.get("POWER"));
                    out.println("STATUS=" + config.get("STATUS"));
                } else if (cmd.equalsIgnoreCase("SAVE")) {
                    out.println("SAVED");
                } else {
                    out.println("UNKNOWN COMMAND");
                }
            }
        } catch (IOException e) {
            LOGGER.info("Client disconnected");
        }
    }
}
