public class AgentFailureDetectionDemo {
    public static void main(String[] args) {
        for (ManagedDeviceView device : SnmpFixtures.failureDevices()) {
            System.out.println("Agent: " + device.deviceName());
            System.out.println("Status: " + (device.online() ? "ONLINE" : "OFFLINE"));
            System.out.println("Last Seen: " + device.lastSeenSecondsAgo() + " Seconds Ago");
            if (!device.online()) {
                System.out.println("Alarm: DEVICE_UNREACHABLE");
            }
            System.out.println();
        }

        System.out.println("Teach:");
        System.out.println("- Availability Monitoring");
        System.out.println("- Health Checks");
        System.out.println("- Heartbeat Monitoring");
    }
}
