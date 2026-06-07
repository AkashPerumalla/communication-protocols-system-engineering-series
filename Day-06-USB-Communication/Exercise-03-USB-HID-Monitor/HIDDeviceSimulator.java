package Exercise03;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class HIDDeviceSimulator {
    private static final Logger logger = Logger.getLogger(HIDDeviceSimulator.class.getName());
    private static final int PORT = 8003;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            logger.info("HIDDeviceSimulator listening on " + PORT);
            while (true) {
                try (Socket s = ss.accept(); PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    // Send a sequence of HID events
                    String ts = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
                    out.println(ts + " HID:KEY_A_DOWN");
                    Thread.sleep(200);
                    out.println(ts + " HID:KEY_A_UP");
                    Thread.sleep(200);
                    out.println(ts + " HID:MOVE X=12 Y=3");
                    Thread.sleep(200);
                    out.println(ts + " HID:LEFT_CLICK");
                    logger.info("Sent HID event sequence to " + s.getRemoteSocketAddress());
                } catch (IOException | InterruptedException ex) {
                    logger.warning("HID session error: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to start HID simulator: " + e.getMessage());
        }
    }
}
