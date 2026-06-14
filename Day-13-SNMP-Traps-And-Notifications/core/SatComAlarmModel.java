public record SatComAlarmModel(double ebNoDb, String bucStatus, String lnbStatus, String modemStatus, String terminalState) {
    public SatComAlarmModel {
        ValidationUtils.requireNonBlank(bucStatus, "bucStatus");
        ValidationUtils.requireNonBlank(lnbStatus, "lnbStatus");
        ValidationUtils.requireNonBlank(modemStatus, "modemStatus");
        ValidationUtils.requireNonBlank(terminalState, "terminalState");
    }

    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.section("SatCom Monitoring Values"));
        builder.append(ConsoleFormatter.kv("Eb/No", ebNoDb + " dB"));
        builder.append(ConsoleFormatter.kv("BUC Status", bucStatus));
        builder.append(ConsoleFormatter.kv("LNB Status", lnbStatus));
        builder.append(ConsoleFormatter.kv("Modem Status", modemStatus));
        builder.append(ConsoleFormatter.kv("Terminal State", terminalState));
        return builder.toString();
    }
}
