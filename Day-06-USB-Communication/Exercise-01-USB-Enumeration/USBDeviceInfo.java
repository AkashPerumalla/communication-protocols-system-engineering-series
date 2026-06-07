package Exercise01;

public class USBDeviceInfo {
    private final int vid;
    private final int pid;
    private final String manufacturer;
    private final String product;

    public USBDeviceInfo(int vid, int pid, String manufacturer, String product) {
        this.vid = vid;
        this.pid = pid;
        this.manufacturer = manufacturer;
        this.product = product;
    }

    public int getVid() { return vid; }
    public int getPid() { return pid; }
    public String getManufacturer() { return manufacturer; }
    public String getProduct() { return product; }

    @Override
    public String toString() {
        return String.format("VID: 0x%04X, PID: 0x%04X, Manufacturer: %s, Product: %s",
                vid, pid, manufacturer, product);
    }
}
