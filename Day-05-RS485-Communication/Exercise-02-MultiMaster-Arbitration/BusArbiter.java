import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class BusArbiter {
    private static final int PORT = 8020;
    private static final Logger LOGGER = Logger.getLogger(BusArbiter.class.getName());
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        new BusArbiter().start();
    }

    public void start() throws Exception {
        LOGGER.info("Starting BusArbiter on port " + PORT);
        try (ServerSocket server = new ServerSocket(PORT)) {
            ExecutorService es = Executors.newCachedThreadPool();
            es.submit(this::tokenLoop);
            while (true) {
                Socket s = server.accept();
                ClientHandler ch = new ClientHandler(s);
                clients.add(ch);
                es.submit(ch);
            }
        }
    }

    private void tokenLoop() {
        int idx = 0;
        while (true) {
            if (clients.isEmpty()) {
                sleep(200);
                continue;
            }
            ClientHandler ch = clients.get(idx % clients.size());
            try {
                ch.grantToken();
                // wait for DONE or timeout
                String resp = ch.readLineWithTimeout(3000);
                LOGGER.info("Arbiter received: " + resp + " from " + ch.id);
            } catch (IOException e) {
                LOGGER.warning("Error communicating with client: " + e.getMessage());
                clients.remove(ch);
            }
            idx++;
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms);} catch(InterruptedException ignored){}
    }

    static class ClientHandler implements Runnable {
        private static int NEXT = 1;
        private final Socket s;
        private final BufferedReader in;
        private final PrintWriter out;
        final String id;

        ClientHandler(Socket s) throws IOException {
            this.s = s;
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new PrintWriter(s.getOutputStream(), true);
            this.id = "M" + (NEXT++);
        }

        @Override
        public void run() {
            try {
                out.println("WELCOME " + id);
                String line;
                while ((line = in.readLine()) != null) {
                    // log registration or heartbeat
                }
            } catch (IOException e) {
                try { s.close(); } catch(IOException ignored){}
            }
        }

        void grantToken() {
            out.println("TOKEN");
        }

        String readLineWithTimeout(long timeoutMs) throws IOException {
            try {
                s.setSoTimeout((int) timeoutMs);
                String l = in.readLine();
                s.setSoTimeout(0);
                return l;
            } catch (SocketTimeoutException e) {
                return "TIMEOUT";
            }
        }
    }
}
