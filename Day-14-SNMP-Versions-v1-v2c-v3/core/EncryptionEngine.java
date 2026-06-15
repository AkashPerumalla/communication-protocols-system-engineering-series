import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class EncryptionEngine {
    public String encrypt(String plaintext, PrivacyProtocol protocol, String secret) {
        ValidationUtils.requireNonNull(protocol, "protocol");
        ValidationUtils.requireNonBlank(plaintext, "plaintext");
        if (protocol == PrivacyProtocol.NONE) {
            return plaintext;
        }
        byte[] bytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] key = deriveKey(protocol, secret);
        byte[] encrypted = new byte[bytes.length];
        for (int index = 0; index < bytes.length; index++) {
            encrypted[index] = (byte) (bytes[index] ^ key[index % key.length]);
        }
        return protocol.name() + ':' + Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String ciphertext, PrivacyProtocol protocol, String secret) {
        ValidationUtils.requireNonNull(protocol, "protocol");
        ValidationUtils.requireNonBlank(ciphertext, "ciphertext");
        if (protocol == PrivacyProtocol.NONE) {
            return ciphertext;
        }
        String encoded = ciphertext;
        int separator = ciphertext.indexOf(':');
        if (separator >= 0) {
            encoded = ciphertext.substring(separator + 1);
        }
        byte[] encrypted = Base64.getDecoder().decode(encoded);
        byte[] key = deriveKey(protocol, secret);
        byte[] decrypted = new byte[encrypted.length];
        for (int index = 0; index < encrypted.length; index++) {
            decrypted[index] = (byte) (encrypted[index] ^ key[index % key.length]);
        }
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    public String describeWorkflow(String plaintext, PrivacyProtocol protocol, String secret) {
        String encrypted = encrypt(plaintext, protocol, secret);
        String decrypted = decrypt(encrypted, protocol, secret);
        return "Plaintext=" + plaintext + System.lineSeparator()
                + "Encrypted=" + encrypted + System.lineSeparator()
                + "Decrypted=" + decrypted;
    }

    private byte[] deriveKey(PrivacyProtocol protocol, String secret) {
        String material = protocol.name() + '|' + (secret == null ? "" : secret);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(material.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Unable to derive encryption key", exception);
        }
    }
}
