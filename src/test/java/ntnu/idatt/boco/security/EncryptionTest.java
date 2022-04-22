package ntnu.idatt.boco.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EncryptionTest {
    // Test data consist of the password 'test123', a premade salt and the hashed value
    private final byte[] SALT = DatatypeConverter.parseHexBinary("9eb8eb1886c3184fa3f3d963c1578f40");
    private final String PASSWORD = "test123";
    private final String WRONG_PASSWORD = "3tset21";
    private final byte[] EXPECTED_HASH = DatatypeConverter.parseHexBinary("08dab1fea88143614b4d449ba5ec067d");

    @Test
    @DisplayName("Salt is desired length")
    void getByte_generatesDesiredSaltLength_true() {
        byte[] generatedSalt = Encryption.getNextSalt();
        assertTrue(byte[].class.isInstance(generatedSalt) && generatedSalt.length == 16);
    }

    @Test
    @DisplayName("Password hashes to same value every time")
    void hash_sameValuesGiveSameResult_true() throws Exception {
        assertEquals(new String(EXPECTED_HASH), new String(Encryption.hash(PASSWORD, SALT)));
    }

    @Test
    @DisplayName("Correct password matches expected hashed value")
    void isExpectedPassword_correctPassword_true() throws Exception {
        assertTrue(Encryption.isExpectedPassword(PASSWORD, SALT, EXPECTED_HASH));
    }

    @Test
    @DisplayName("Wrong passwords dont match expected hash")
    void isExpectedPassword_wrongPassword_false() {
        assertFalse(Encryption.isExpectedPassword(WRONG_PASSWORD, SALT, EXPECTED_HASH));
    }
}
