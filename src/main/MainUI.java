package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import accounts.*;
import user.*;
import utility.TerminalColors;

public class MainUI {
    private static User loggedInUser; // store logged-in user. static becuz only 1 at a time
    private static final String format = "|%-25s|\n";

    public static void createPatientAccount(Scanner sc){
        PatientsAcc.addPatient(sc);
    }

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

    public static void logout() {
        System.out.println("Logging out...");
        HMSApp.resetSessionColor(); // Reset terminal color
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
