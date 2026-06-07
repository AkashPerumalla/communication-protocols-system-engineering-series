package Exercise05;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class USBStorageSimulator {
    private static final Logger logger = Logger.getLogger(USBStorageSimulator.class.getName());
    private static final int PORT = 8005;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            logger.info("USBStorageSimulator listening on " + PORT);
            while (true) {
                try (Socket s = ss.accept(); PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    out.println("STORAGE:VENDOR=AcmeStorage");
                    out.println("STORAGE:PRODUCT=FlashDrive-16GB");
                    out.println("STORAGE:CAPACITY=17179869184");
                    out.println("STORAGE:FILESYSTEM=FAT32");
                    logger.info("Served storage info to " + s.getRemoteSocketAddress());
                } catch (IOException e) {
                    logger.warning("Storage session error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to start USBStorageSimulator: " + e.getMessage());
        }
    }
}
