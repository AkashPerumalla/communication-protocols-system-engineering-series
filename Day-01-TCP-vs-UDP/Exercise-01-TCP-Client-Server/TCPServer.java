import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Server listening on port 5000");

        Socket clientSocket = serverSocket.accept();

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

        PrintWriter out =
                new PrintWriter(
                        clientSocket.getOutputStream(),
                        true);

        String message = in.readLine();

        System.out.println("Client: " + message);

        out.println("Hello from TCP Server");

        clientSocket.close();
        serverSocket.close();
    }
}
