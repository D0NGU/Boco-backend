package ntnu.idatt.boco.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** 
 * This class contains methods relating to encryption and decryption of user passwords.
 */
public class Encryption {
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 128;

    /**
     * Returns a random salt to be used to hash a password.
     * @return a 16 byte long random salt
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash
     * @param password the password to be hashed
     * @param salt     a 16 bytes salt, should be generated using {@link ntnu.idatt.boco.security.Encryption#getNextSalt() getNextSalt()}
     * @return the hashed password
     */
    public static byte[] hash(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise
     * @param password     the password to check
     * @param salt         the salt used to hash the password
     * @param expectedHash the expected hashed value of the password
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    public static boolean isExpectedPassword(String password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        return Arrays.equals(pwdHash, expectedHash);
    }

    /**
     * Converts a string representation of a hex to a byte array
     * @param string representation of a hex
     * @return an array of bytes
     */
    public static byte[] hexStringToByteArray(String string) {
        int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                                 + Character.digit(string.charAt(i+1), 16));
        }
        return data;
    }

}
