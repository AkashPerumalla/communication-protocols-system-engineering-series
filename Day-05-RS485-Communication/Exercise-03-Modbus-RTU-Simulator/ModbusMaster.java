import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ModbusMaster {
    private static final String HOST = "localhost";
    private static final int PORT = 8005;
    private static final Logger LOGGER = Logger.getLogger(ModbusMaster.class.getName());

    public static void main(String[] args) throws Exception {
        // Build request: 01 03 00 00 00 02 + CRC
        byte[] req = new byte[] {0x01, 0x03, 0x00, 0x00, 0x00, 0x02};
        byte[] crc = CRC16.modbusCRC(req, 0, req.length);
        byte[] frame = new byte[req.length + crc.length];
        System.arraycopy(req, 0, frame, 0, req.length);
        System.arraycopy(crc, 0, frame, req.length, crc.length);

        LOGGER.info("Sending request (hex): " + bytesToHex(frame));

        try (Socket s = new Socket(HOST, PORT);
             OutputStream out = s.getOutputStream();
             InputStream in = s.getInputStream()) {
            out.write(frame);
            out.flush();
            byte[] header = in.readNBytes(3); // addr, func, bytecount
            int byteCount = header[2] & 0xFF;
            byte[] data = in.readNBytes(byteCount + 2); // data + CRC
            byte[] resp = new byte[3 + data.length];
            System.arraycopy(header, 0, resp, 0, 3);
            System.arraycopy(data, 0, resp, 3, data.length);
            LOGGER.info("Received response (hex): " + bytesToHex(resp));
            // decode registers
            if (resp.length >= 7) {
                int reg1 = ((resp[3] & 0xFF) << 8) | (resp[4] & 0xFF);
                int reg2 = ((resp[5] & 0xFF) << 8) | (resp[6] & 0xFF);
                LOGGER.info("Decoded registers: R0=" + reg1 + ", R1=" + reg2);
            }
        }
    }

    private static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte x : b) sb.append(String.format("%02X ", x));
        return sb.toString().trim();
    }
}
