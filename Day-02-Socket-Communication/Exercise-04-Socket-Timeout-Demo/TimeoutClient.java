import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class TimeoutClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9005;
    private static final Logger LOGGER = Logger.getLogger(TimeoutClient.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            LOGGER.info("Connected to server at " + LocalDateTime.now().format(TIME));
            System.out.println("Optionally press Enter to send a message before the server times out.");
            System.in.read(); // wait for Enter; if the user does nothing, server will timeout
            out.println("Hello after wait at " + LocalDateTime.now().format(TIME));
            LOGGER.info("Sent message to server");

        } catch (Exception e) {
            LOGGER.severe("Client error: " + e.getMessage());
        }
    }
}
