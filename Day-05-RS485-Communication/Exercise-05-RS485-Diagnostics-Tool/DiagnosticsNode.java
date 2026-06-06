import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class DiagnosticsNode {
    private static final Logger LOGGER = Logger.getLogger(DiagnosticsNode.class.getName());
    private final int port;
    private final String addr;

    public DiagnosticsNode(int port, String addr) {
        this.port = port;
        this.addr = addr;
    }

    public void start() throws Exception {
        LOGGER.info("Starting DiagnosticsNode " + addr + " on port " + port);
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                try (Socket s = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                     PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    String line = in.readLine();
                    if (line == null) continue;
                    LOGGER.info("[" + addr + "] RECV: " + line);
                    if (line.contains("PING")) out.println("ADDR=" + addr + ";PONG");
                    else if (line.contains("ECHO")) out.println("ADDR=" + addr + ";ECHO:" + line);
                    else out.println("ADDR=" + addr + ";OK");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DiagnosticsNode n = new DiagnosticsNode(8009, "99");
        n.start();
    }
}
