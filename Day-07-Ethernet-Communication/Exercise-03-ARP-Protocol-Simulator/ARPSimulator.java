import java.util.HashMap;
import java.util.Map;

public class ARPSimulator {
    private final Map<String, String> arpTable = new HashMap<>();

    public void addStaticEntry(String ip, String mac) {
        arpTable.put(ip, mac);
    }

    public ARPReply handleRequest(ARPRequest req) {
        System.out.println(req.toString());
        String mac = arpTable.get(req.targetIp);
        if (mac != null) {
            ARPReply reply = new ARPReply(req.targetIp, mac, req.senderIp, req.senderMac);
            System.out.println("Generating reply: " + reply.toString());
            // learn sender in table
            arpTable.put(req.senderIp, req.senderMac);
            return reply;
        } else {
            System.out.println("No entry for " + req.targetIp + "; request will be broadcast/flooded in real network.");
            // learn sender
            arpTable.put(req.senderIp, req.senderMac);
            return null;
        }
    }

    public void handleReply(ARPReply rep) {
        System.out.println(rep.toString());
        arpTable.put(rep.senderIp, rep.senderMac);
    }

    public void printArpTable() {
        System.out.println("--- ARP Table ---");
        arpTable.forEach((ip, mac) -> System.out.println(ip + " -> " + mac));
    }

    public static void main(String[] args) throws Exception {
        ARPSimulator sim = new ARPSimulator();
        // Static host at 192.168.1.1
        sim.addStaticEntry("192.168.1.1", "AA:BB:CC:DD:EE:FF");

        // Host 192.168.1.100 wants to know who has 192.168.1.1
        ARPRequest req = new ARPRequest("192.168.1.100", "11:22:33:44:55:66", "192.168.1.1");
        ARPReply rep = sim.handleRequest(req);
        if (rep != null) sim.handleReply(rep);

        sim.printArpTable();
    }
}
