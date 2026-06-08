import java.util.HashMap;
import java.util.Map;

public class VLANSwitch {
    // MAC -> (port,vlan)
    private static class Entry { int port; int vlan; Entry(int p,int v){port=p;vlan=v;} }
    private final Map<String, Entry> table = new HashMap<>();
    private final int ports;

    public VLANSwitch(int ports) { this.ports = ports; }

    public void receive(String srcMac, String dstMac, int inPort, int vlan) {
        table.put(srcMac, new Entry(inPort, vlan));
        System.out.println("Learned " + srcMac + " -> port " + inPort + ", VLAN " + vlan);

        Entry dst = table.get(dstMac);
        if (dst != null) {
            if (dst.vlan != vlan) {
                System.out.println("Destination in different VLAN (" + dst.vlan + ") - cannot forward");
            } else if (dst.port == inPort) {
                System.out.println("Destination on same port - drop");
            } else {
                System.out.println("Forwarding to port " + dst.port + " within VLAN " + vlan);
            }
        } else {
            System.out.println("Unknown dest - flooding within VLAN " + vlan);
            for (int p=1;p<=ports;p++) if (p!=inPort) System.out.println(" - flood to port " + p);
        }
    }

    public void printTable() {
        System.out.println("--- VLAN MAC Table ---");
        table.forEach((mac,e) -> System.out.println(mac + " -> port " + e.port + " vlan " + e.vlan));
    }

    public static void main(String[] args) {
        VLANSwitch sw = new VLANSwitch(4);
        String a = "AA:00:00:00:00:01"; // VLAN 10, port1
        String b = "BB:00:00:00:00:02"; // VLAN 10, port2
        String c = "CC:00:00:00:00:03"; // VLAN 20, port3

        System.out.println("A->B (same VLAN)");
        sw.receive(a,b,1,10);
        sw.receive(b,a,2,10);

        System.out.println("A->C (different VLAN)");
        sw.receive(a,c,1,10);
        sw.receive(c,a,3,20);

        sw.printTable();
    }
}
