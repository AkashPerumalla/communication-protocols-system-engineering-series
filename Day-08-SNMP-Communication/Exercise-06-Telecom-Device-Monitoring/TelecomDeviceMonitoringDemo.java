import java.util.List;
import java.util.Map;

public final class TelecomDeviceMonitoringDemo {
    public static void main(String[] args) {
        List<MonitoredDevice> devices = DemoDataFactory.telecomDevices();
        System.out.println("SATCOM HUB MONITORING");
        System.out.println();
        for (MonitoredDevice device : devices) {
            System.out.println(device.name() + " [" + device.type() + "]");
            for (Map.Entry<String, String> entry : device.metrics().entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
            System.out.println();
        }
    }
}
