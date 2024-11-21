package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import accounts.*;
import user.*;
import utility.PasswordResetManager;
import utility.PasswordResetRequest;
import utility.TerminalColors;

/**
 * This class handles the user interface for the Hospital Management System (HMS).
 * It allows users to log in, create a new patient account, check their role, and manage session states.
 * The class facilitates user authentication and displays appropriate user-specific UI based on the role.
 */
public class MainUI {

    /** The currently logged-in user in the system. There can be only one logged-in user at a time. */
    private static User loggedInUser; 

    /** Format string for displaying the menu options with a fixed width. */
    private static final String format = "|%-25s|\n";

    /**
     * Creates a new patient account by calling the `addPatient` method from the `PatientsAcc` class.
     *
     * @param sc A {@link Scanner} object for user input.
     */
    public static void createPatientAccount(Scanner sc){
        PatientsAcc.addPatient(sc);
    }

    /**
     * Prompts the user to log in based on their role (Patient, Doctor, Pharmacist, Administrator).
     * The method authenticates the user and displays the appropriate user interface based on the role.
     *
     * @param sc A {@link Scanner} object for user input.
     */
    public static void login(Scanner sc) {
        int userRole = checkRole(sc); // return class of user
        Login login = null;
        User loggedIn = null;

        switch (userRole) {
            case 1: // Patient
                System.out.println("Patient Selected!");
                login = new Login(PatientsAcc.getPatients());
                break;
            case 2: // Doctor
                System.out.println("Doctor Selected!");
                login = new Login(DoctorsAcc.getDoctors());
                break;
            case 3: // Pharmacist
                System.out.println("Pharmacist Selected!");
                login = new Login(PharmacistsAcc.getPharmacists());
                break;
            case 4: // Administrator
                System.out.println("Administrator Selected!");
                login = new Login(AdminsAcc.getAdmins());
                break;
            default:
                System.out.println("Invalid Choice!");
        }

        // authenticate user
        if (login!=null){
            loggedIn = login.authenticate(sc); // returns the user that has logged in
        }
        
        if (loggedIn != null) {
            loggedInUser = loggedIn;

            // Set the terminal color for the logged-in user
            HMSApp.setSessionColor(TerminalColors.getColorByRole(loggedIn.getRole().name()));

            System.out.println("Welcome, " + loggedIn.getName() + "! You are logged in as " + loggedIn.getRole() + ".");
            loggedIn.displayUI(); // Display user-specific UI
        }
    }

    /**
     * Prompts the user to select their role (Patient, Doctor, Pharmacist, Administrator).
     * Returns the corresponding integer value representing the selected role.
     *
     * @param sc A {@link Scanner} object for user input.
     * @return The selected role as an integer (1 for Patient, 2 for Doctor, 3 for Pharmacist, 4 for Administrator).
     */
    public static int checkRole(Scanner sc) {
        int choice = -1;
        System.out.println("\nAre you a Staff or a Patient?");
        System.out.printf("%s\n", "-".repeat(27));
        System.out.printf(format, "1. Patient");
        System.out.printf(format, "2. Doctor");
        System.out.printf(format, "3. Pharmacist");
        System.out.printf(format, "4. Administrator");
        System.out.printf("%s\n", "-".repeat(27));
        System.out.print("Choice: ");
        
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Invalid input type. Please enter an Integer.");
            sc.nextLine(); // Consume the invalid input to prevent an infinite loop
            return -1; //returns -1 indicate invalid
        }
        return choice;
    }

    /** Logs out the current user and resets the session color for the terminal. */
    public static void logout() {
        System.out.println("Logging out...");
        HMSApp.resetSessionColor(); // Reset terminal color
    }

    public static void resetPassword(Scanner sc){
        System.out.println("\n|--- Password Reset Request ---|");
        System.out.print("Enter your User ID: ");
        String userId = sc.nextLine().trim();
        System.out.print("Enter your Name: ");
        String userName = sc.nextLine().trim();

        PasswordResetRequest request = new PasswordResetRequest(userId, userName);
        boolean success = PasswordResetManager.addRequest(request);
        if (success) {
            System.out.println("Your password reset request has been submitted.");
        }
        return;
    }

    /**
     * Returns the currently logged-in user.
     * 
     * @return The {@link User} object representing the logged-in user.
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
