package Exercise05;

import java.util.ArrayList;
import java.util.List;

public class USBStorageScanner {
    public static List<DeviceInfoCollector> scan() {
        List<DeviceInfoCollector> list = new ArrayList<>();
        list.add(new DeviceInfoCollector("AcmeStorage", "FlashDrive-16GB", 16L * 1024 * 1024 * 1024, "FAT32"));
        list.add(new DeviceInfoCollector("AcmeStorage", "SSD-USB-128GB", 128L * 1024 * 1024 * 1024, "exFAT"));
        return list;
    }

    public static void main(String[] args) {
        System.out.println("USB Mass Storage Inspector — simulated devices:");
        for (DeviceInfoCollector d : scan()) {
            System.out.println(" - " + d);
        }
    }
}
