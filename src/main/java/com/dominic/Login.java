package com.dominic;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/bank.db";

    /**
     * The main login screen. <br><br>
     * This method provides the fields for inputting username and password upon login.
     */
    public static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
        =================================================
                        PROTOTYPE MINI BANK
                               LOGIN
        =================================================
        """);

        while (true) {
            System.out.println("Leave either field blank and press enter to return to the Welcome screen.\n");

            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            if (username.isEmpty()) {
                Helpers.transitionDelay();
                AuthenticationPortal.authenticationPortal();
                break;
            }

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                Helpers.transitionDelay();
                AuthenticationPortal.authenticationPortal();
                break;
            }

            int user_id = checkLoginDetails(username, password);

            if (user_id != 0) {
                System.out.println("main menu");
                System.out.println("user id: " + user_id);
                Helpers.transitionDelay();
                break;
            }

            else {
                System.out.println("Incorrect username or password.\n");
            }
        }
    }
    
    /**
     * Authenticates and verifies login details given by the user against details from the account database.
     * @param username the username the user types in
     * @param password the plaintext password the user types in
     * @return the user id of the account with these login details, where 0 indicates invalid login details
     */
    private static int checkLoginDetails(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        String databaseUsername = null;
        String hashedPassword = null;

        int user_id = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL);
              PreparedStatement pStatement = conn.prepareStatement(sql)) {
                pStatement.setString(1, username);

                ResultSet rs = pStatement.executeQuery();
                databaseUsername = rs.getString("username");
                hashedPassword = rs.getString("password");

                if (Helpers.verifyPassword(password, hashedPassword)) {
                    user_id = rs.getInt("user_id");
                }
        }

        catch (SQLException e) {
            System.out.println("An error occurred while logging in.");
            e.printStackTrace();
        }

        return user_id;
    }
}
