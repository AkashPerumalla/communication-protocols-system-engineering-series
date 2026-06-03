import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Multi-client echo server. Uses an ExecutorService to handle each client in its own thread.
 */
public class MultiClientEchoServer {
    private static final int PORT = 9003;
    private static final Logger LOGGER = Logger.getLogger(MultiClientEchoServer.class.getName());
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final AtomicInteger CLIENT_ID = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        LOGGER.info("Starting MultiClientEchoServer on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = serverSocket.accept();
                int id = CLIENT_ID.incrementAndGet();
                LOGGER.info("[Client-" + id + "] Connected from " + client.getInetAddress().getHostAddress() + ":" + client.getPort() + " at " + LocalDateTime.now().format(TIME));
                pool.submit(new ClientHandler(client, id));
            }
        } catch (IOException e) {
            LOGGER.severe("Server error: " + e.getMessage());
        } finally {
            pool.shutdown();
            LOGGER.info("Server shutdown, thread pool terminated");
        }
    }
}
