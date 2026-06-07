package Exercise05;

public class DeviceInfoCollector {
    private final String vendor;
    private final String product;
    private final long capacityBytes;
    private final String filesystem;

    public DeviceInfoCollector(String vendor, String product, long capacityBytes, String filesystem) {
        this.vendor = vendor;
        this.product = product;
        this.capacityBytes = capacityBytes;
        this.filesystem = filesystem;
    }

    public String getVendor() { return vendor; }
    public String getProduct() { return product; }
    public long getCapacityBytes() { return capacityBytes; }
    public String getFilesystem() { return filesystem; }

    @Override
    public String toString() {
        return String.format("Vendor=%s Product=%s Capacity=%d bytes FS=%s",
                vendor, product, capacityBytes, filesystem);
    }
}
