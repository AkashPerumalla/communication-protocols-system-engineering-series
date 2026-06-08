import java.util.HashMap;
import java.util.Map;

public class HubController {
    private final Map<String, RemoteTerminal> terminals = new HashMap<>();

    public void register(RemoteTerminal rt) { terminals.put(rt.getId(), rt); }

    public String sendCommand(String id, String cmd) {
        RemoteTerminal rt = terminals.get(id);
        if (rt == null) return "NO SUCH TERMINAL";
        // simulate latency
        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return rt.handleCommand(cmd);
    }

    public static void main(String[] args) {
        HubController hub = new HubController();
        RemoteTerminal rt1 = new RemoteTerminal("RT-1","192.0.2.1");
        hub.register(rt1);

        System.out.println(hub.sendCommand("RT-1","GET_STATUS"));
        System.out.println(hub.sendCommand("RT-1","GET_TEMP"));
        System.out.println(hub.sendCommand("RT-1","GET_POWER"));
        System.out.println(hub.sendCommand("RT-1","GET_ALARMS"));
    }
}
