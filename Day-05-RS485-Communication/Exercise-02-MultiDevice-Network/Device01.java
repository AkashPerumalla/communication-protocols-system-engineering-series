import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Device01 {
    private static final int PORT = 8002;
    private static final String ADDR = "01";
    private static final Logger LOGGER = Logger.getLogger(Device01.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting Device01 on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        LOGGER.info("[" + ADDR + "] RECV: " + line);
                        if (line.contains("READ_TEMP")) {
                            out.println("ADDR=" + ADDR + ";TEMP=26");
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
