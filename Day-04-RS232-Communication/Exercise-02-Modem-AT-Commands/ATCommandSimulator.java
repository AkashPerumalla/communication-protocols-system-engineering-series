import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Minimal Hayes-style AT command simulator (over TCP).
 */
public class ATCommandSimulator {
    private static final Logger LOGGER = Logger.getLogger(ATCommandSimulator.class.getName());
    private static final int DEFAULT_PORT = 7002;
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException e) { }
        }
        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("ATCommandSimulator listening on port " + port);
            while (true) {
                Socket s = server.accept();
                LOGGER.info("Client: " + s.getRemoteSocketAddress());
                new Thread(() -> handle(s)).start();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error", e);
        }
    }

    private static void handle(Socket s) {
        try (s; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim();
                LOGGER.info("[" + LocalDateTime.now().format(TF) + "] RX: " + cmd);
                if (cmd.equalsIgnoreCase("AT")) {
                    out.println("OK");
                } else if (cmd.equalsIgnoreCase("ATI")) {
                    out.println("MODEM v1.0");
                } else if (cmd.equalsIgnoreCase("AT+CSQ")) {
                    out.println("SIGNAL=78");
                } else if (cmd.equalsIgnoreCase("AT+RST")) {
                    out.println("REBOOTING...");
                    Thread.sleep(800);
                    out.println("OK");
                } else {
                    out.println("ERROR");
                }
            }
        } catch (IOException e) {
            LOGGER.info("Client disconnected");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
