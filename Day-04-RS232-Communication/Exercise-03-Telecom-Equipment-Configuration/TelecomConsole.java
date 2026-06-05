import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class TelecomConsole {
    private static final Logger LOGGER = Logger.getLogger(TelecomConsole.class.getName());
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7003;

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if (args.length > 0) host = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to Telecom Device at " + host + ":" + port);
            System.out.println("Commands: SHOW CONFIG, SET FREQ <MHz>, SET POWER <LOW|HIGH>, SHOW STATUS, SAVE, QUIT");
            while (true) {
                System.out.print("cmd> ");
                String cmd = scanner.nextLine();
                if (cmd == null) break;
                if (cmd.equalsIgnoreCase("QUIT")) break;
                out.println(cmd);
                // read responses until socket has no immediate more lines (we'll read 1-3 lines)
                for (int i = 0; i < 5; i++) {
                    if (!in.ready()) break;
                    String resp = in.readLine();
                    if (resp == null) break;
                    System.out.println(resp);
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Connection error: " + e.getMessage());
        }
    }
}
