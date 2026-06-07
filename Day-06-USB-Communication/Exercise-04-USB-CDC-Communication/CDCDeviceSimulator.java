package Exercise04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class CDCDeviceSimulator {
    private static final Logger logger = Logger.getLogger(CDCDeviceSimulator.class.getName());
    private static final int PORT = 8004;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)) {
            logger.info("CDCDeviceSimulator listening on " + PORT);
            while (true) {
                try (Socket s = ss.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    out.println("CDC> Welcome to the virtual CDC device. Type HELP for commands.");
                    String cmd;
                    while ((cmd = in.readLine()) != null) {
                        String upper = cmd.trim().toUpperCase();
                        switch (upper) {
                            case "STATUS":
                                out.println("STATUS: OK");
                                break;
                            case "VERSION":
                                out.println("VERSION: USB-CDC-SIM-1.0");
                                break;
                            case "HELP":
                                out.println("Commands: STATUS, VERSION, HELP, RESET");
                                break;
                            case "RESET":
                                out.println("RESETTING...");
                                Thread.sleep(200);
                                out.println("RESET: DONE");
                                break;
                            default:
                                out.println("UNKNOWN COMMAND");
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    logger.warning("CDC session error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to start CDC simulator: " + e.getMessage());
        }
    }
}
