package Exercise04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class CDCConsole {
    private static final Logger logger = Logger.getLogger(CDCConsole.class.getName());
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8004;

    public static void main(String[] args) {
        try (Socket s = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true);
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in))) {
            // Print welcome then allow typed commands
            String line;
            // Reader thread to print device responses
            Thread reader = new Thread(() -> {
                try {
                    String resp;
                    while ((resp = in.readLine()) != null) {
                        System.out.println(resp);
                    }
                } catch (IOException e) {
                    logger.warning("Console read error: " + e.getMessage());
                }
            });
            reader.setDaemon(true);
            reader.start();

            System.out.println("Connected to CDC simulator. Type commands (STATUS, VERSION, HELP, RESET). Ctrl-C to exit.");
            while ((line = stdin.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException e) {
            logger.severe("Failed to connect to CDC simulator: " + e.getMessage());
        }
    }
}
