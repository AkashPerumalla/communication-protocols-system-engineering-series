import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class SocketStateClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9004;
    private static final Logger LOGGER = Logger.getLogger(SocketStateClient.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("[CREATE] Preparing to create client socket");
        Instant created = Instant.now();

        try (Socket socket = new Socket(HOST, PORT)) {
            Instant connected = Instant.now();
            LOGGER.info("[CONNECT] Connected to server at " + LocalDateTime.now().format(TIME));

            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

                LOGGER.info("[COMMUNICATE] You may type messages to send to the server (Ctrl+C to exit)");
                String line;
                Instant commStart = Instant.now();
                while ((line = console.readLine()) != null) {
                    out.println(line);
                }
                Instant commEnd = Instant.now();

                LOGGER.info("[CLOSE] Closing connection at " + LocalDateTime.now().format(TIME));
                long totalMs = Duration.between(created, Instant.now()).toMillis();
                LOGGER.info("Lifecycle summary: created->now=" + totalMs + "ms, connectedAt=" + Duration.between(created, connected).toMillis() + "ms, communication=" + Duration.between(commStart, commEnd).toMillis() + "ms");
            }
        } catch (IOException e) {
            LOGGER.severe("Client error: " + e.getMessage());
        }
    }
}
