package ntnu.idatt.boco.security;

/**
 * Contains constant values used by other classes in .security
 */
public final class SecurityConstants {
    
    private SecurityConstants() {
        // Restrict instantiation
    }

    // Tokens
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // How long tokens are valid in seconds. The first int represents hours.

    // Encryption
    public static final int ITERATIONS = 10000;
    public static final int KEY_LENGTH = 128;

}
