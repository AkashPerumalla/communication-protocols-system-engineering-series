package Exercise01;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

// Simple TCP-based simulator that models a USB device being enumerated by a host.
public class USBEnumerator {
    private static final Logger logger = Logger.getLogger(USBEnumerator.class.getName());
    private static final int PORT = 8001;

    public static void main(String[] args) {
        logger.info("Starting USBEnumerator simulator on port " + PORT);
        try (ServerSocket ss = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = ss.accept();
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    LocalDateTime now = LocalDateTime.now();
                    String ts = now.format(DateTimeFormatter.ISO_LOCAL_TIME);
                    // Simulate enumeration lifecycle events
                    USBDeviceInfo dev = new USBDeviceInfo(0x1234, 0xABCD, "Acme Corp", "USB Virtual Device");
                    out.println("EVENT:DEVICE_CONNECTED " + ts);
                    out.println("STEP:HOST_DETECTS_DEVICE");
                    out.println("VID:" + String.format("0x%04X", dev.getVid()));
                    out.println("PID:" + String.format("0x%04X", dev.getPid()));
                    out.println("MANUFACTURER:" + dev.getManufacturer());
                    out.println("PRODUCT:" + dev.getProduct());
                    out.println("STEP:ASSIGN_ADDRESS");
                    out.println("STEP:LOAD_DRIVER");
                    out.println("STEP:READY");
                    logger.info("Served enumeration sequence to " + s.getRemoteSocketAddress());
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Connection error", e);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start simulator", e);
        }
    }
}
