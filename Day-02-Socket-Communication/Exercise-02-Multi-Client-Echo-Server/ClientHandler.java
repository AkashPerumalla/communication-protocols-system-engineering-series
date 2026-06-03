import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a single client connection. Designed to be submitted to an ExecutorService.
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final int clientId;
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ClientHandler(Socket socket, int clientId) {
        this.socket = socket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        Instant start = Instant.now();
        String clientTag = "Client-" + clientId;

        try (Socket s = socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                LOGGER.info("[" + clientTag + "] Message Received at " + LocalDateTime.now().format(TIME) + ": " + line);
                out.println(line); // echo back to sender
            }

        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "[" + clientTag + "] Connection error: " + e.getMessage(), e);
        } finally {
            Instant end = Instant.now();
            long durationMs = Duration.between(start, end).toMillis();
            LOGGER.info("[" + clientTag + "] Disconnected at " + LocalDateTime.now().format(TIME) + ", duration=" + durationMs + "ms");
        }
    }
}
