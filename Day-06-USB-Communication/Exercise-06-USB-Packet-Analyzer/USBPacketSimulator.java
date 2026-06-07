package Exercise06;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class USBPacketSimulator {
    private static final Logger logger = Logger.getLogger(USBPacketSimulator.class.getName());
    private static final int PORT = 8006;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            logger.info("USBPacketSimulator listening on " + PORT);
            while (true) {
                try (Socket s = ss.accept(); PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    out.println("PKT: SOF t=0");
                    out.println("PKT: SETUP t=1 PID=0x2A BmRequest=0x00");
                    out.println("PKT: IN t=2 EP=0x81 len=64");
                    out.println("PKT: OUT t=3 EP=0x01 len=32");
                    out.println("PKT: SOF t=4");
                    logger.info("Sent packet timeline to " + s.getRemoteSocketAddress());
                } catch (IOException e) {
                    logger.warning("Packet session error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to start USBPacketSimulator: " + e.getMessage());
        }
    }
}
