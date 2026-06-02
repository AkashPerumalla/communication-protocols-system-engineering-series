import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    public static void main(String[] args) {
        System.out.println("TCP Reliable Messaging Client");
        System.out.println("Type /exit to disconnect.\n");

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            String prompt = serverInput.readLine();
            if (prompt != null) {
                System.out.println(prompt);
            }

            System.out.print("Enter username: ");
            String username = consoleInput.readLine();
            if (username == null) {
                return;
            }

            serverOutput.println(username);

            String acknowledgement = serverInput.readLine();
            if (acknowledgement != null) {
                System.out.println(acknowledgement);
            }

            while (true) {
                System.out.print(username + ": ");
                String message = consoleInput.readLine();

                if (message == null) {
                    break;
                }

                serverOutput.println(message);

                String response = serverInput.readLine();
                if (response != null) {
                    System.out.println(response);
                }

                if ("/exit".equalsIgnoreCase(message.trim())) {
                    break;
                }
            }
        } catch (IOException exception) {
            System.err.println("TCP client error: " + exception.getMessage());
        }
    }
}
