import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class RemoteUnit {
    private final int port;
    private final String id;
    private final String statusLine;
    private static final Logger LOGGER = Logger.getLogger(RemoteUnit.class.getName());

    public RemoteUnit(int port, String id, String statusLine) {
        this.port = port;
        this.id = id;
        this.statusLine = statusLine;
    }

    public void start() throws Exception {
        LOGGER.info("Starting RemoteUnit " + id + " on port " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String cmd = in.readLine();
                    LOGGER.info("[" + id + "] CMD: " + cmd);
                    out.println("ID=" + id + ";" + statusLine);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        RemoteUnit r = new RemoteUnit(8010, "RTU-01", "STATUS=OK;ALARM=NONE;TEMP=25;POWER=ON");
        r.start();
    }
}
