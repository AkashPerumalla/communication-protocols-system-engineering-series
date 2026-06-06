import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class SlaveSimulator {
    private static final int PORT = 8001;
    private static final String ADDR = "01";
    private static final Logger LOGGER = Logger.getLogger(SlaveSimulator.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("Starting SlaveSimulator on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String ts = LocalDateTime.now().format(TF);
                        LOGGER.info("[" + ADDR + "] RECV " + ts + " : " + line);
                        if (line.contains("READ_STATUS")) {
                            String resp = "ADDR=" + ADDR + ";STATUS=OK";
                            out.println(resp);
                            LOGGER.info("[" + ADDR + "] SENT " + ts + " : " + resp);
                        } else {
                            out.println("ADDR=" + ADDR + ";STATUS=UNKNOWN");
                        }
                    }
                } catch (IOException e) {
                    LOGGER.warning("Connection error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to start server: " + e.getMessage());
        }
    }
}
