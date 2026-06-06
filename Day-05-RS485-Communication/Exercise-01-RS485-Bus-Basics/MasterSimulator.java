import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class MasterSimulator {
    private static final String HOST = "localhost";
    private static final int PORT = 8001;
    private static final String ADDR = "00"; // master address = 0
    private static final Logger LOGGER = Logger.getLogger(MasterSimulator.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        LOGGER.info("Starting MasterSimulator connecting to " + HOST + ":" + PORT);
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String cmd = "ADDR=01;CMD=READ_STATUS";
            String ts = LocalDateTime.now().format(TF);
            LOGGER.info("[" + ADDR + "] SENT " + ts + " : " + cmd);
            out.println(cmd);

            String resp = in.readLine();
            String rts = LocalDateTime.now().format(TF);
            LOGGER.info("[" + ADDR + "] RECV " + rts + " : " + resp);

        } catch (IOException e) {
            LOGGER.severe("Communication error: " + e.getMessage());
        }
    }
}
