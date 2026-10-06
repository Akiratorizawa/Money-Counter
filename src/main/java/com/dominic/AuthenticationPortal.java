package com.dominic;

import java.util.Scanner;

public class AuthenticationPortal {
    /**
     * The welcome screen users are first greeted with. <br><br>
     * Contains two options for Login (1), and Sign Up (2).
     */
    public static void authenticationPortal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                =================================================
                               PROTOTYPE MINI BANK
                                   SIGN IN
                =================================================

                """);
        
        boolean awaitingInput = true;

        while (awaitingInput) {
            System.out.println("""
                    [1] Login
                    [2] Sign Up
                    """);
            
            System.out.print("Enter option: ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> {
                    Helpers.transitionDelay();
                    Login.login();
                    awaitingInput = false;
                }

                case "2" -> {
                    Helpers.transitionDelay();
                    SignUp.signUp();
                    awaitingInput = false;
                }

                default -> {
                    System.out.println("Invalid input.\n");
                    Helpers.transitionDelay();
                }
            }
        }
    }
}
