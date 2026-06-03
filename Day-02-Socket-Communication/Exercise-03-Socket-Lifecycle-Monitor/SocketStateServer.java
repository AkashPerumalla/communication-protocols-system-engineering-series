import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class SocketStateServer {
    private static final int PORT = 9004;
    private static final Logger LOGGER = Logger.getLogger(SocketStateServer.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("[CREATE] ServerSocket will be created on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOGGER.info("[CREATE] ServerSocket created at " + LocalDateTime.now().format(TIME));

            while (true) {
                LOGGER.info("[CONNECT] Waiting for client...");
                try (Socket client = serverSocket.accept()) {
                    LOGGER.info("[CONNECT] Client connected from " + client.getRemoteSocketAddress() + " at " + LocalDateTime.now().format(TIME));

                    Instant start = Instant.now();
                    LOGGER.info("[COMMUNICATE] Ready to receive messages");

                    try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                        String line;
                        while ((line = in.readLine()) != null) {
                            LOGGER.info("[COMMUNICATE] Message received: " + line + " at " + LocalDateTime.now().format(TIME));
                        }
                    }

                    Instant end = Instant.now();
                    long durationMs = Duration.between(start, end).toMillis();
                    LOGGER.info("[CLOSE] Connection closed at " + LocalDateTime.now().format(TIME) + ", duration=" + durationMs + "ms");
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Server error: " + e.getMessage());
        }
    }
}
