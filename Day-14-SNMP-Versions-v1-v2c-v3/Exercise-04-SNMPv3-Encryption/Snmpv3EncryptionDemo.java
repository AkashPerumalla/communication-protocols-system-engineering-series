public final class Snmpv3EncryptionDemo {
    private Snmpv3EncryptionDemo() {
    }

    public static void main(String[] args) {
        EncryptionEngine encryptionEngine = new EncryptionEngine();
        String plaintext = "GET 1.3.6.1.4.1.55555.1.1.0 RF Power poll";
        String aesEncrypted = encryptionEngine.encrypt(plaintext, PrivacyProtocol.AES, "aes-privacy-secret-14");
        String aesDecrypted = encryptionEngine.decrypt(aesEncrypted, PrivacyProtocol.AES, "aes-privacy-secret-14");
        String desEncrypted = encryptionEngine.encrypt(plaintext, PrivacyProtocol.DES, "des-privacy-secret-14");
        String desDecrypted = encryptionEngine.decrypt(desEncrypted, PrivacyProtocol.DES, "des-privacy-secret-14");

        System.out.println(ConsoleFormatter.title("SNMPv3 Encryption Simulation"));
        System.out.println(ConsoleFormatter.keyValue("Plaintext Request", plaintext));
        System.out.println(ConsoleFormatter.keyValue("AES Encrypted Payload", aesEncrypted));
        System.out.println(ConsoleFormatter.keyValue("AES Decrypted Payload", aesDecrypted));
        System.out.println(ConsoleFormatter.keyValue("DES Encrypted Payload", desEncrypted));
        System.out.println(ConsoleFormatter.keyValue("DES Decrypted Payload", desDecrypted));
        System.out.println("ENCRYPTION ACTIVE");
    }
}
