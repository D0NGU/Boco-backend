package ntnu.idatt.boco.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EncryptionTest {
    private final byte[] SALT = Encryption.getNextSalt();
    private final String PASSWORD = "passord";
    private final String WRONG_PASSWORD = "passord1";
    private final byte[] EXPECTED_HASH = Encryption.hash(PASSWORD.toCharArray(), SALT);

    @Test
    void hash_sameValuesGiveSameResult_true() throws Exception {
        assertEquals(new String(EXPECTED_HASH), new String(Encryption.hash(PASSWORD.toCharArray(), SALT)));
    }

    @Test
    void isExpectedPassword_correctPassword_true() throws Exception {
        assertTrue(Encryption.isExpectedPassword(PASSWORD.toCharArray(), SALT, EXPECTED_HASH));
    }

    @Test
    void isExpectedPassword_wrongPassword_false() {
        assertFalse(Encryption.isExpectedPassword(WRONG_PASSWORD.toCharArray(), SALT, EXPECTED_HASH));
    }
}
