package Exercise08;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class USBHealthMonitor {
    private static final Logger logger = Logger.getLogger(USBHealthMonitor.class.getName());

    public static boolean checkPort(String host, int port, int timeoutMs) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            return true;
        } catch (Exception e) {
            logger.fine("Port check failed: " + host + ":" + port + " -> " + e.getMessage());
            return false;
        }
    }

    public static String timeStamp() { return LocalDateTime.now().toString(); }
}
