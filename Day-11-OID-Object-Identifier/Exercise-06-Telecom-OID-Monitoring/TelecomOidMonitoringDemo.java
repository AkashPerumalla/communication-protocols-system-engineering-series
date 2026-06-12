public final class TelecomOidMonitoringDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        SnmpAgentSimulator agent = new SnmpAgentSimulator(repository);

        OidConsole.printBanner("DAY 11 - EXERCISE 06 - TELECOM OID MONITORING", "Telecom OID Monitoring");
        OidConsole.printNameValue("RF Power", agent.get("1.3.6.1.4.1.55555.1.1.0").value());
        OidConsole.printNameValue("BER", agent.get("1.3.6.1.4.1.55555.1.2.0").value());
        OidConsole.printNameValue("Carrier Lock", agent.get("1.3.6.1.4.1.55555.1.3.0").value());
        OidConsole.printNameValue("Frequency", agent.get("1.3.6.1.4.1.55555.1.4.0").value());
        OidConsole.printNameValue("Symbol Rate", agent.get("1.3.6.1.4.1.55555.1.5.0").value());
    }
}
