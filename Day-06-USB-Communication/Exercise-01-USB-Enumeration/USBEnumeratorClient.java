package Exercise01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class USBEnumeratorClient {
    private static final Logger logger = Logger.getLogger(USBEnumeratorClient.class.getName());
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8001;

    public static void main(String[] args) {
        try (Socket s = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String line;
            System.out.println("--- USB Enumeration Client ---");
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to connect to USBEnumerator", e);
        }
    }
}
