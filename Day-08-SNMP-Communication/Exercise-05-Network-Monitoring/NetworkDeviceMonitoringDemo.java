import java.util.List;
import java.util.Map;

public final class NetworkDeviceMonitoringDemo {
    public static void main(String[] args) throws InterruptedException {
        List<MonitoredDevice> devices = DemoDataFactory.networkDevices();
        System.out.println("POLLING INTERVAL: 5 SECONDS");
        for (int cycle = 1; cycle <= 2; cycle++) {
            System.out.println();
            System.out.println("POLL CYCLE " + cycle);
            for (MonitoredDevice device : devices) {
                printDevice(device);
            }
            if (cycle < 2) {
                Thread.sleep(50L);
            }
        }
    }

    private static void printDevice(MonitoredDevice device) {
        System.out.println(device.name());
        for (Map.Entry<String, String> entry : device.metrics().entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println();
    }
}
