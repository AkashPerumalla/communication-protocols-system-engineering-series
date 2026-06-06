import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class NetworkMaster {
    private static final String HOST = "localhost";
    private static final int[] PORTS = {8002, 8003, 8004};
    private static final String[] ADDRS = {"01", "02", "03"};
    private static final Logger LOGGER = Logger.getLogger(NetworkMaster.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        for (int i = 0; i < PORTS.length; i++) {
            pollDevice(ADDRS[i], PORTS[i]);
        }
    }

    private static void pollDevice(String addr, int port) {
        try (Socket s = new Socket(HOST, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            String cmd = "ADDR=" + addr + ";CMD=READ_TEMP";
            String ts = LocalDateTime.now().format(TF);
            LOGGER.info("POLL -> [" + addr + "] " + ts + " : " + cmd);
            out.println(cmd);
            String resp = in.readLine();
            String rts = LocalDateTime.now().format(TF);
            LOGGER.info("RECV <- [" + addr + "] " + rts + " : " + resp);
        } catch (IOException e) {
            LOGGER.warning("Failed to poll [" + addr + "] on port " + port + ": " + e.getMessage());
        }
    }
}
