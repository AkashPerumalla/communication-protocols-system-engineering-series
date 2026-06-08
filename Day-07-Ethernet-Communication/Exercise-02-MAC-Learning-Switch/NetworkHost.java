public class NetworkHost {
    private final String name;
    private final String mac;

    public NetworkHost(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public String getMac() { return mac; }

    public void receive(byte[] frame) {
        System.out.println(name + " received frame (len=" + frame.length + ")");
    }
}
