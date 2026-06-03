import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Simple TCP client that connects to the EchoServer, sends lines from stdin, and prints responses.
 */
public class EchoClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9002;
    private static final Logger LOGGER = Logger.getLogger(EchoClient.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("EchoClient connecting to " + HOST + ":" + PORT);

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String input;
            System.out.println("Connected. Type messages and press Enter. Ctrl+C to exit.");
            while ((input = console.readLine()) != null) {
                out.println(input);
                String resp = in.readLine();
                String ts = LocalDateTime.now().format(TIME);
                System.out.println("[" + ts + "] Echoed: " + resp);
            }
        } catch (IOException e) {
            LOGGER.severe("Client error: " + e.getMessage());
        }
    }
}
