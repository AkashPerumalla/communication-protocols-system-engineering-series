public class VLANHost {
    private final String name;
    private final String mac;
    private final int vlan;

    public VLANHost(String name, String mac, int vlan) {
        this.name = name; this.mac = mac; this.vlan = vlan;
    }

    public String getMac() { return mac; }
    public int getVlan() { return vlan; }
    public void recv(byte[] frame) { System.out.println(name + " received frame (len="+frame.length+")"); }
}
