import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Device02 {
    private static final int PORT = 8003;
    private static final String ADDR = "02";
    private static final Logger LOGGER = Logger.getLogger(Device02.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting Device02 on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        LOGGER.info("[" + ADDR + "] RECV: " + line);
                        if (line.contains("READ_TEMP")) {
                            out.println("ADDR=" + ADDR + ";TEMP=28");
                        } else {
                            out.println("ADDR=" + ADDR + ";UNKNOWN");
                        }
                    }
                } catch (IOException e) {
                    LOGGER.warning("Connection error: " + e.getMessage());
                }
            }
        }
    }
}
