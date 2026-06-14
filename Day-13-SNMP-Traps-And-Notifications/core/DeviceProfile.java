public record DeviceProfile(String name, String sourceIp, String site, String role) {
    public DeviceProfile {
        ValidationUtils.requireNonBlank(name, "deviceName");
        ValidationUtils.requireNonBlank(sourceIp, "sourceIp");
        ValidationUtils.requireNonBlank(site, "site");
        ValidationUtils.requireNonBlank(role, "role");
    }
}
