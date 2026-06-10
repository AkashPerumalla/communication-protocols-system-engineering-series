public class MultiOidPollingDemo {
    public static void main(String[] args) {
        SnmpAgentCore agent = SnmpFixtures.multiOidAgent();
        DeviceTelemetry telemetry = SnmpFixtures.networkDevices().get(0);

        System.out.println("Device: " + agent.deviceName());
        System.out.println("CPU: " + telemetry.cpuPercent() + "%");
        System.out.println("Memory: " + telemetry.memoryPercent() + "%");
        System.out.println("Temperature: " + telemetry.temperatureC() + "°C");
        System.out.println("Interface Status: " + telemetry.interfaceStatus());
        System.out.println("Bandwidth: " + telemetry.bandwidthMbps() + " Mbps");
        System.out.println();
        System.out.println("Teach:");
        System.out.println("- Periodic Polling");
        System.out.println("- Device Monitoring");
        System.out.println("- Statistics Collection");
    }
}
