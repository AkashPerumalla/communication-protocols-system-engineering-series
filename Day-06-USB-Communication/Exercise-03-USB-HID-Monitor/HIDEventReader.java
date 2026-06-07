package Exercise03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class HIDEventReader {
    private static final Logger logger = Logger.getLogger(HIDEventReader.class.getName());
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8003;

    public static void main(String[] args) {
        try (Socket s = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            System.out.println("--- HID Event Reader ---");
            String line;
            while ((line = in.readLine()) != null) {
                // Basic parsing and display
                System.out.println(line);
            }
        } catch (IOException e) {
            logger.severe("Failed to connect to HID simulator: " + e.getMessage());
        }
    }
}
