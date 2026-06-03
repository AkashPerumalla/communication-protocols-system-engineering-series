import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class TimeoutServer {
    private static final int PORT = 9005;
    private static final int SOCKET_TIMEOUT_MS = 5000;
    private static final Logger LOGGER = Logger.getLogger(TimeoutServer.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("Starting TimeoutServer on port " + PORT + " with read timeout=" + SOCKET_TIMEOUT_MS + "ms");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    LOGGER.info("Client connected: " + client.getRemoteSocketAddress() + " at " + LocalDateTime.now().format(TIME));
                    client.setSoTimeout(SOCKET_TIMEOUT_MS);

                    try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                        String line = in.readLine(); // blocking read with timeout
                        if (line != null) {
                            LOGGER.info("Received: " + line + " at " + LocalDateTime.now().format(TIME));
                        } else {
                            LOGGER.info("Client closed connection without sending data");
                        }
                    } catch (SocketTimeoutException ste) {
                        LOGGER.warning("Socket Timeout Occurred: no data received within " + SOCKET_TIMEOUT_MS + "ms");
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Server error: " + e.getMessage());
        }
    }
}
