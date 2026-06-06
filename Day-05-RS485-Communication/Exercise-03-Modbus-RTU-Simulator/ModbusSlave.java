import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class ModbusSlave {
    private static final int PORT = 8005;
    private static final Logger LOGGER = Logger.getLogger(ModbusSlave.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting ModbusSlave on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = server.accept();
                     InputStream in = s.getInputStream();
                     OutputStream out = s.getOutputStream()) {
                    byte[] req = in.readNBytes(8); // basic request length
                    if (req.length >= 6) {
                        LOGGER.info("Received request (hex): " + bytesToHex(req));
                        // Simple check: assume function 03 and read 2 registers
                        byte[] payload = new byte[] {0x01, 0x03, 0x04, 0x00, 0x64, 0x00, 0x32};
                        byte[] crc = CRC16.modbusCRC(payload, 0, payload.length);
                        out.write(payload);
                        out.write(crc);
                        out.flush();
                        LOGGER.info("Sent response (hex): " + bytesToHex(concat(payload, crc)));
                    }
                } catch (IOException e) {
                    LOGGER.warning("Conn error: " + e.getMessage());
                }
            }
        }
    }

    private static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02X ", x));
        return sb.toString().trim();
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
