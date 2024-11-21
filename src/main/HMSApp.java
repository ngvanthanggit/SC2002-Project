package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import utility.ColoredPrintStream;

/**
 * This class is the main entry point of the Hospital Management System (HMS) application.
 * It handles user interactions such as logging in, creating a new patient account, or exiting the system.
 * The application displays a menu and processes the user's choice to either log in, create an account, or exit.
 * The system data is initialized from CSV files during startup.
 */
public class HMSApp {

    /** ColoredPrintStream object for printing colored output to the console. */
    private static ColoredPrintStream coloredOut;

    /**
     * The main method is the entry point for the HMSApp.
     * It initializes the system by loading data from CSV files and prompts the user for input.
     * The user can choose to log in, create a new patient account, or exit the system.
     * 
     * @param args Command-line arguments (not used in this case)
     */
    public static void main(String[] args) {
        
        // Redirect System.out to ColoredPrintStream
        coloredOut = new ColoredPrintStream(System.out);
        System.setOut(coloredOut);

        //initialise the system by importing all data from any CSV files
        SystemInitialisation.start(); 
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        String format = "|%-30s|\n";

        //after importing Data prompt user for Login or Create New Patient Account
        do {
            System.out.println("\n|--- Welcome to the HMS App ---|");
            System.out.printf("%s\n", "-".repeat(32));
            System.out.printf(format, "1. Login");
            System.out.printf(format, "2. Create new Patient account");
            System.out.printf(format, "3. Reset Password");
            System.out.printf(format, "4. Exit");
            System.out.printf("%s\n", "-".repeat(32));
            System.out.print("Choice: ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                continue; // Restart the loop to prompt the user again
            }

            switch (choice) {
                case 1:
                    MainUI.login(sc);// login
                    break;
                case 2:
                    MainUI.createPatientAccount(sc);
                    break;
                case 3:
                    MainUI.resetPassword(sc);
                    break;
                case 4:
                    System.out.println("Exiting App, System Terminating!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice != 4);

        sc.close();
    }

    /**
     * Sets the color for the current session based on the user's role.
     * This method changes the color of the output text for the session.
     *
     * @param roleColor The color to be set for the session.
     */
    public static void setSessionColor(String roleColor) {
        coloredOut.setColor(roleColor);
    }

    /**
     * Resets the output color to the default color after a session.
     * This method should be called at the end of the session to restore the default color.
     */
    public static void resetSessionColor() {
        coloredOut.resetColor();
    }
}
