public final class Snmpv3AuthenticationDemo {
    private Snmpv3AuthenticationDemo() {
    }

    public static void main(String[] args) {
        AuthenticationEngine authenticationEngine = new AuthenticationEngine();
        SnmpUser shaUser = SampleData.shaUser();
        SnmpUser md5User = SampleData.md5User();

        AuthenticationEngine.AuthenticationResult shaResult = authenticationEngine.authenticate(shaUser);
        AuthenticationEngine.AuthenticationResult md5Result = authenticationEngine.authenticate(md5User);

        System.out.println(ConsoleFormatter.title("SNMPv3 Authentication Simulation"));
        System.out.println(ConsoleFormatter.keyValue("Username", shaUser.username()));
        System.out.println(ConsoleFormatter.keyValue("Authentication", shaUser.authenticationProtocol().name()));
        System.out.println(ConsoleFormatter.keyValue("Security Level", shaUser.securityLevel().displayName()));
        System.out.println(ConsoleFormatter.keyValue("Authentication Result", shaResult.summary()));
        System.out.println(ConsoleFormatter.keyValue("Digest Token", shaResult.token()));
        System.out.println(ConsoleFormatter.keyValue("MD5 User", md5User.username()));
        System.out.println(ConsoleFormatter.keyValue("MD5 Result", md5Result.summary()));
        System.out.println(ConsoleFormatter.keyValue("MD5 Digest Token", md5Result.token()));
        System.out.println("AUTH SUCCESS");
    }
}
