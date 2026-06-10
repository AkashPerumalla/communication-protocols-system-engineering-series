public class TelecomAgentMonitoringDemo {
    public static void main(String[] args) {
        for (TelecomTelemetry telemetry : SnmpFixtures.telecomDevices()) {
            System.out.println(telemetry.deviceName());
            System.out.println("Tx Power: " + (telemetry.txPowerDbm() == 0 ? "N/A" : telemetry.txPowerDbm() + " dBm"));
            System.out.println("Rx Power: " + telemetry.rxPowerDbm() + " dBm");
            System.out.println("Signal Strength: " + telemetry.signalStrengthPercent() + "%");
            System.out.println("BER: " + telemetry.ber());
            System.out.println("Lock Status: " + telemetry.lockStatus());
            System.out.println("Temperature: " + telemetry.temperatureC() + "°C");
            System.out.println("Status: ONLINE");
            System.out.println();
        }

        System.out.println("Teach:");
        System.out.println("- Telecom Monitoring");
        System.out.println("- SatCom Monitoring");
        System.out.println("- Real NMS Use Cases");
    }
}
