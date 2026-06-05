import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Connects to SatComTerminalSimulator and prints telemetry packets with simple parsing.
 */
public class MonitoringConsole {
    private static final Logger LOGGER = Logger.getLogger(MonitoringConsole.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7004;

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            System.out.println("Connected to SatCom terminal monitor " + host + ":" + port);
            String line;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;
                System.out.println("[" + LocalDateTime.now().format(TF) + "] Packet#" + count + " -> " + line);
            }
        } catch (IOException e) {
            LOGGER.warning("Connection error: " + e.getMessage());
        }
    }
}
