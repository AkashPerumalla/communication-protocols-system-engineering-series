import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple TCP Echo Server demonstrating socket lifecycle, echo behavior, and proper resource cleanup.
 * Listens on port 9002.
 */
public class EchoServer {
    private static final int PORT = 9002;
    private static final Logger LOGGER = Logger.getLogger(EchoServer.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("Starting EchoServer on port " + PORT);

        // try-with-resources ensures ServerSocket is closed on exit
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("ServerSocket created: " + serverSocket);

            while (true) {
                Socket client = serverSocket.accept();
                String clientInfo = client.getInetAddress().getHostAddress() + ":" + client.getPort();
                LOGGER.info("Client connected: " + clientInfo + " at " + LocalDateTime.now().format(TIME));

                // Handle single client in current thread for echo demo; keep it simple and educational
                try (Socket s = client;
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

                    String line;
                    while ((line = in.readLine()) != null) {
                        String ts = LocalDateTime.now().format(TIME);
                        LOGGER.info("Received from " + clientInfo + " [" + ts + "]: " + line);
                        out.println(line); // echo back
                    }
                } catch (IOException ioe) {
                    LOGGER.log(Level.WARNING, "Client handling error: " + ioe.getMessage(), ioe);
                }

                LOGGER.info("Client disconnected: " + clientInfo + " at " + LocalDateTime.now().format(TIME));
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server error: " + e.getMessage(), e);
        }
    }
}
