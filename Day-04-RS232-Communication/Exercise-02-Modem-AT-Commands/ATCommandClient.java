import java.io.*;
import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.Scanner;

public class ATCommandClient {
    private static final Logger LOGGER = Logger.getLogger(ATCommandClient.class.getName());
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7002;

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to modem simulator at " + host + ":" + port);
            System.out.println("Type AT commands (AT, ATI, AT+CSQ, AT+RST). Type QUIT to exit.");
            while (true) {
                System.out.print("AT> ");
                String cmd = scanner.nextLine();
                if (cmd == null) break;
                if (cmd.equalsIgnoreCase("QUIT")) break;
                Instant send = Instant.now();
                out.println(cmd);
                String resp = in.readLine();
                Instant recv = Instant.now();
                long rttMs = Duration.between(send, recv).toMillis();
                System.out.println("[" + LocalDateTime.now().format(TF) + "] Sent: " + cmd + " | RT T(ms)=" + rttMs);
                System.out.println("Response: " + resp);
            }
        } catch (IOException e) {
            LOGGER.warning("Connection error: " + e.getMessage());
        }
    }
}
