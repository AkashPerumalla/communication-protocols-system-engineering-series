public record TelecomAlarmModel(double ber, double rfPowerDbm, String carrierState, double frequencyGhz, double symbolRateMsps) {
    public TelecomAlarmModel {
        ValidationUtils.requireNonBlank(carrierState, "carrierState");
    }

    public String describe() {
        StringBuilder builder = new StringBuilder();
        builder.append(ConsoleFormatter.section("Telecom Monitoring Values"));
        builder.append(ConsoleFormatter.kv("BER", Double.toString(ber)));
        builder.append(ConsoleFormatter.kv("RF Power", rfPowerDbm + " dBm"));
        builder.append(ConsoleFormatter.kv("Carrier State", carrierState));
        builder.append(ConsoleFormatter.kv("Frequency", frequencyGhz + " GHz"));
        builder.append(ConsoleFormatter.kv("Symbol Rate", symbolRateMsps + " Msps"));
        return builder.toString();
    }
}
