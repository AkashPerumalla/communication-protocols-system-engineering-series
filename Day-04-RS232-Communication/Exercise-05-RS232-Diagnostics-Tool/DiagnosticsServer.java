import java.io.*;
import java.net.*;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * Diagnostics responder: supports PING, ECHO <text>, DELAY <ms>.
 */
public class DiagnosticsServer {
    private static final Logger LOGGER = Logger.getLogger(DiagnosticsServer.class.getName());
    private static final int DEFAULT_PORT = 7005;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException e) { }
        }
        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("DiagnosticsServer listening on " + port);
            while (true) {
                Socket s = server.accept();
                new Thread(() -> handle(s)).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Server error: " + e.getMessage());
        }
    }

    private static void handle(Socket s) {
        try (s; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim();
                if (cmd.equalsIgnoreCase("PING")) {
                    out.println("PONG");
                } else if (cmd.toUpperCase().startsWith("ECHO ")) {
                    out.println(cmd.substring(5));
                } else if (cmd.toUpperCase().startsWith("DELAY ")) {
                    try {
                        int ms = Integer.parseInt(cmd.split("\\s+")[1]);
                        Thread.sleep(ms);
                        out.println("DELAYED " + ms);
                    } catch (Exception e) {
                        out.println("ERROR");
                    }
                } else {
                    out.println("UNKNOWN");
                }
            }
        } catch (IOException e) {
            LOGGER.info("Client disconnected");
        }
    }
}
