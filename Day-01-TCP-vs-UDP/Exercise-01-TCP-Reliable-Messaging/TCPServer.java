import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    private static final int PORT = 9000;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        ExecutorService clientPool = Executors.newCachedThreadPool();

        System.out.println("TCP Reliable Messaging Server started on port " + PORT);
        System.out.println("Waiting for client connections...\n");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientPool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException exception) {
            System.err.println("Server error: " + exception.getMessage());
        } finally {
            clientPool.shutdown();
        }
    }

    private static String currentTime() {
        return LocalTime.now().format(TIME_FORMAT);
    }

    private static class ClientHandler implements Runnable {

        private final Socket socket;

        private ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String username = "Unknown";

            try (Socket client = socket;
                 BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter output = new PrintWriter(client.getOutputStream(), true)) {

                output.println("Enter username:");
                username = input.readLine();

                if (username == null || username.isBlank()) {
                    username = "Unknown";
                }

                System.out.println("[" + currentTime() + "] " + username + " connected");
                output.println("SERVER ACK: Username accepted");

                String message;
                while ((message = input.readLine()) != null) {
                    if ("/exit".equalsIgnoreCase(message.trim())) {
                        output.println("SERVER ACK: Disconnecting");
                        break;
                    }

                    System.out.println("[" + currentTime() + "] " + username + ":");
                    System.out.println(message);
                    System.out.println("ACK sent\n");

                    output.println("SERVER ACK: Message Delivered Successfully");
                }
            } catch (IOException exception) {
                System.err.println("[" + currentTime() + "] Connection error for " + username + ": " + exception.getMessage());
            } finally {
                System.out.println("[" + currentTime() + "] " + username + " disconnected");
            }
        }
    }
}
