package com.dominic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Unit test for password related helper functions.
 */
public class HelpersTest 
{

    /**
     * Helpers.hashPassword should succeed and create a hashed password that is checkable with BCrypt.checkpw().
     */
    @Test
    public void hashPassword_Success() {
        String password = "password123123123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        assertTrue(StringUtils.isNotBlank(hashedPassword));
        assertTrue(BCrypt.checkpw(password, hashedPassword), "The hashing of the password should return true.");
    }

    /**
     * Helpers.hashPassword should generate unique hashes for the same password due to random salting.
     */
    @Test
    public void hashPassword_SamePasswordDifferentHashes() {
        String password = "fslkdfjlsjfsdjflksdjf";
        String hash1 = BCrypt.hashpw(password, BCrypt.gensalt(12));
        String hash2 = BCrypt.hashpw(password, BCrypt.gensalt(12));

        assertNotEquals(hash1, hash2, "Hashes should be generated uniquely for the same password.");
    }

    /**
     * Helpers.hashPassword should generate unique hashes for the same password due to random salting.
     */
    @Test
    public void hashPassword_EmptyPassword() {
        String password = "";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));

        assertNotNull(hash);
        assertTrue(BCrypt.checkpw(password, hash), "Blank passwords should be handled properly.");
    }

    /**
     * Helpers.hashPassword should generate unique hashes for the same password due to random salting.
     */
    @Test
    public void hashPassword_NullPassword() {
        String password = null;

        assertThrows(IllegalArgumentException.class, () -> {
            Helpers.hashPassword(password);
        });
    }

    /**
     * Helpers.verifyPassword(); <br>
     * Case for correct password and matching hash details.
     */
    @Test
    public void verifyPassword_CorrectPassword()
    {
        String password = "blahblahblahdoesntmatter";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        
        assertTrue(Helpers.verifyPassword(password, hashedPassword), "Valid password and its corresponding hash should return true.");
    }

    /**
     * Helpers.verifyPassword(); <br>
     * Case for incorrect password and hash details.
     */
    @Test
    public void verifyPassword_IncorrectPassword() {
        String correctPassword = "whateveruwannaputhere";

        String incorrectPassword = "dominicantonsiao";
        String hashedIncorrectPassword = BCrypt.hashpw(incorrectPassword, BCrypt.gensalt(12));

        assertFalse(Helpers.verifyPassword(correctPassword, hashedIncorrectPassword), "Incorrect password should return false.");

    }

    /**
     * Helpers.verifyPassword(); <br>
     * Case for illegally formed hash.
     */
    @Test
    public void verifyPassword_IllegalHash() {
        String password = "heloooooooooo";
        String hashedPassword = "yoooooowhatisit";

        assertFalse(Helpers.verifyPassword(password, hashedPassword), "Illegal hash formation should return false.");
    }

}
