package Exercise06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class USBPacketAnalyzer {
    private static final Logger logger = Logger.getLogger(USBPacketAnalyzer.class.getName());
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8006;

    public static void main(String[] args) {
        List<String> timeline = new ArrayList<>();
        try (Socket s = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                timeline.add(line);
            }
            System.out.println("USB Packet Timeline:");
            timeline.forEach(System.out::println);
        } catch (IOException e) {
            logger.severe("Packet analyzer failed: " + e.getMessage());
        }
    }
}
