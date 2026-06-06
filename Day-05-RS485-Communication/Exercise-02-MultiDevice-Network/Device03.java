import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Device03 {
    private static final int PORT = 8004;
    private static final String ADDR = "03";
    private static final Logger LOGGER = Logger.getLogger(Device03.class.getName());

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting Device03 on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        LOGGER.info("[" + ADDR + "] RECV: " + line);
                        if (line.contains("READ_TEMP")) {
                            out.println("ADDR=" + ADDR + ";TEMP=30");
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
