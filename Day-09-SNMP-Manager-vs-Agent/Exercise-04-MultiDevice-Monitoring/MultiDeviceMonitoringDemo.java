public class MultiDeviceMonitoringDemo {
    public static void main(String[] args) {
        for (SnmpAgentCore agent : SnmpFixtures.multiDeviceAgents()) {
            System.out.println(agent.deviceName());
            System.out.println("ONLINE");
            System.out.println();
        }

        System.out.println("Teach:");
        System.out.println("- Centralized Monitoring");
        System.out.println("- Multi-Agent Communication");
        System.out.println("- Network Visibility");
    }
}
