package com.dominic;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/bank.db";
    /**
     * The main sign up screen. <nr><br>
     * Allows users to sign up for a new account.
     */
    public static void signUp() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                =================================================
                               PROTOTYPE MINI BANK
                                     SIGN UP
                =================================================
                """);
        
        String username;

        while (true) {

            System.out.print("Username: ");
            username = scanner.nextLine();
            
            if (checkUsername(username)) {
                break;
            }

            else {
                System.out.println("\nUsername taken.\n");
            }
        }

        while (true) {
            System.out.print("Password: ");
            String password = scanner.nextLine();

            Helpers.transitionDelay();

            System.out.print("Verify Password: ");
            String hashedPassword = Helpers.hashPassword(scanner.nextLine());

            if (Helpers.verifyPassword(password, hashedPassword)) {
                createAccount(username, hashedPassword);

                Helpers.transitionDelay();

                System.out.println("\nAccount created successfully.\n");

                Helpers.transitionDelay();

                break;
            }

            else {
                System.out.println("The passwords do not match.");
                Helpers.transitionDelay();
            }
        }

        AuthenticationPortal.authenticationPortal();
    }

    /**
     * Checks to see if the provided username is unique, by comparing it against the account database.
     * @param username the username the user types in
     * @return true if the username is unique, false otherwise
     */
    private static boolean checkUsername(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";
        int user_id = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pStatement = conn.prepareStatement(sql)) {

                pStatement.setString(1, username);
                ResultSet rs = pStatement.executeQuery();
                user_id = rs.getInt("user_id");

        } catch (SQLException e) {
            System.out.println("An error occurred while creating an account. Please try again.");
            e.printStackTrace();
        }

        if (user_id == 0) {
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Creates an account with the given username and password, adding a new entry to the account database.
     * @param username the username typed in by the user
     * @param hashedPassword the password typed in by the user after undergoing hashing from Helpers.hashPassword()
     * @return true if the account was made successfully, false otherwise
     */
    private static boolean createAccount(String username, String hashedPassword) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pStatement = conn.prepareStatement(sql)) {
                pStatement.setString(1, username);
                pStatement.setString(2, hashedPassword);
                pStatement.executeUpdate();
                return true;
        }

        catch (SQLException e) {
            System.out.println("An error occurred while creating your account.");
            e.printStackTrace();
            return false;
        }
    }
}
