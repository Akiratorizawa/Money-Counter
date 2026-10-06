package com.dominic;

import org.mindrot.jbcrypt.BCrypt;

public class Helpers {
    /**
     * Acts as the delay between the transition to different screens/parts.
     */
    public static void transitionDelay() {
        try {
            Thread.sleep(500);
        }

        catch (InterruptedException e) {
            System.out.println("Error occurred during loading.");
            e.printStackTrace();
        }
    }

    /**
     * Hashes a password using BCrypt, with 12 log rounds.
     * @param password the password the user types in
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        try {
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }

        catch (RuntimeException e) {
            System.out.println("An error occurred while hashing the password.");
            System.out.println("Error Message:");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Compares a plaintext password against a stored hash, acting as password authentication.
     * @param password the password the user types in
     * @param storedHash the password hash stored in the account database
     * @return true if the password is correct, false if the password is wrong
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            return BCrypt.checkpw(password, storedHash);
        }

        catch (IllegalArgumentException e) {
            return false;
        }
    }
}
