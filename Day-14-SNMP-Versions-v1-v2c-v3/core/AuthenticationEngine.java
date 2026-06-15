import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class AuthenticationEngine {
    public record AuthenticationResult(boolean success, String summary, String token) {
    }

    public AuthenticationResult authenticate(SnmpUser user) {
        ValidationUtils.requireNonNull(user, "user");
        if (user.authenticationProtocol() == AuthenticationProtocol.NONE) {
            return new AuthenticationResult(true, "No authentication required", "NONE");
        }
        String seed = user.username() + '|' + user.authenticationSecret() + '|' + user.authenticationProtocol().name();
        String token = digest(seed, user.authenticationProtocol());
        return new AuthenticationResult(true, user.authenticationProtocol().name() + " authentication validated for " + user.username(), token);
    }

    private String digest(String seed, AuthenticationProtocol protocol) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(protocol == AuthenticationProtocol.MD5 ? "MD5" : "SHA-256");
            byte[] digest = messageDigest.digest(seed.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest).substring(0, 16);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Unable to compute authentication digest", exception);
        }
    }
}
