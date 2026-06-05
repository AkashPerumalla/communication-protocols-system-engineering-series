import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Simple terminal client to talk to RS232DeviceSimulator. Prints timestamp, port, baud rate and command.
 */
public class RS232TerminalClient {
    private static final Logger LOGGER = Logger.getLogger(RS232TerminalClient.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7001;
    private static final String DEFAULT_BAUD = "9600 8N1";

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        String baud = DEFAULT_BAUD;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);
        if (args.length > 2) baud = args[2];

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to " + host + ":" + port + " (baud=" + baud + ")");
            System.out.println("Type commands (STATUS, HELP, VERSION, REBOOT). Ctrl+C to exit.");
            while (true) {
                System.out.print("> ");
                String cmd = scanner.nextLine();
                String ts = LocalDateTime.now().format(TF);
                System.out.println("[" + ts + "] Port=" + port + " Baud=" + baud + " Sent: " + cmd);
                out.println(cmd);
                String resp = in.readLine();
                if (resp != null) {
                    System.out.println("[" + LocalDateTime.now().format(TF) + "] Received: " + resp);
                } else {
                    System.out.println("[" + LocalDateTime.now().format(TF) + "] No response (connection closed)");
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Connection error: " + e.getMessage());
        }
    }
}
