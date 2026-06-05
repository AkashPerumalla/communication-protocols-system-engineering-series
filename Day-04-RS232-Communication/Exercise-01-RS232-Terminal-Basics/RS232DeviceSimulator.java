import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple RS232 device simulator over TCP. Responds to STATUS, HELP, VERSION, REBOOT.
 * Designed for educational labs (no real serial port required).
 */
public class RS232DeviceSimulator {
    private static final Logger LOGGER = Logger.getLogger(RS232DeviceSimulator.class.getName());
    private static final int DEFAULT_PORT = 7001;
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException e) { /* keep default */ }
        }

        LOGGER.info("Starting RS232DeviceSimulator on port " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket client = server.accept();
                LOGGER.info("Client connected: " + client.getRemoteSocketAddress());
                new Thread(() -> handleClient(client)).start();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server error", e);
        }
    }

    private static void handleClient(Socket socket) {
        try (socket; BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim().toUpperCase();
                LOGGER.info("[" + LocalDateTime.now().format(TF) + "] Received: " + cmd);
                switch (cmd) {
                    case "STATUS":
                        out.println("DEVICE READY");
                        break;
                    case "HELP":
                        out.println("AVAILABLE: STATUS, HELP, VERSION, REBOOT");
                        break;
                    case "VERSION":
                        out.println("RS232-DEVICE v1.0");
                        break;
                    case "REBOOT":
                        out.println("REBOOTING...");
                        // simulate reboot delay
                        Thread.sleep(1000);
                        out.println("DEVICE READY");
                        break;
                    default:
                        out.println("ERROR: UNKNOWN COMMAND");
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Client disconnected: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
