public final class SatComOidMonitoringDemo {
    public static void main(String[] args) {
        OidRepository repository = OidRepository.createDefault();
        SnmpAgentSimulator agent = new SnmpAgentSimulator(repository);

        OidConsole.printBanner("DAY 11 - EXERCISE 07 - SATCOM OID MONITORING", "SatCom OID Monitoring");
        OidConsole.printNameValue("Eb/No", agent.get("1.3.6.1.4.1.66666.1.2.0").value());
        OidConsole.printNameValue("Terminal State", agent.get("1.3.6.1.4.1.66666.1.1.0").value());
        OidConsole.printNameValue("Modem Status", agent.get("1.3.6.1.4.1.66666.1.5.0").value());
        OidConsole.printNameValue("BUC Status", agent.get("1.3.6.1.4.1.66666.1.6.0").value());
        OidConsole.printNameValue("LNB Status", agent.get("1.3.6.1.4.1.66666.1.7.0").value());
    }
}
