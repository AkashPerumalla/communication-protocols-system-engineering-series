import java.util.Random;

public class RemoteTerminal {
    private final String id;
    private final String ip;
    private final Random rnd = new Random();

    public RemoteTerminal(String id, String ip) { this.id = id; this.ip = ip; }

    public String getId() { return id; }

    public String handleCommand(String cmd) {
        switch (cmd) {
            case "GET_STATUS": return id + " STATUS OK";
            case "GET_TEMP": return id + " TEMP " + (20 + rnd.nextInt(15)) + " C";
            case "GET_POWER": return id + " POWER " + (10 + rnd.nextInt(90)) + "%";
            case "GET_ALARMS": return id + " ALARMS " + (rnd.nextBoolean() ? "NONE" : "HPA_FAULT");
            default: return "UNKNOWN_CMD";
        }
    }
}
