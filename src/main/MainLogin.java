package main;

import java.util.Scanner;

import accounts.*;
import menus.DoctorMenu;
import user.*;

public class MainLogin {
    private static User loggedInUser; //store logged-in user. static becuz only 1 at a time
    private static final String format = "|%-25s|\n";

    public static void login(Scanner sc){
        int userRole = checkRole(sc); // return class of user
        Login login = null;

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
        User loggedIn = login.authenticate(); // returns the user that has logged in
        if (loggedIn != null) {
            loggedInUser = loggedIn; // Store the logged-in user

            if (loggedIn instanceof Patient) {
                Patient patient = (Patient) loggedIn;
                patient.displayMenu();
            } else if (loggedIn instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) loggedIn;
                pharmacist.displayMenu();
            } else if (loggedIn instanceof Doctor) {
                Doctor doctor = (Doctor) loggedIn;
                DoctorMenu doctorMenu = new DoctorMenu(doctor);
                doctorMenu.displayMenu();
            } else if (loggedIn instanceof Administrator) {
                Administrator admin = (Administrator) loggedIn;
                admin.displayMenu();
            }  
        } else {
            System.out.println("Login failed! Incorrect ID or password.");
        }
    }

    public static int checkRole(Scanner sc) {
        System.out.println("\nAre you a Staff or a Patient?");
        System.out.printf("%s\n", "-".repeat(27));
        System.out.printf(format, "1. Patient");
        System.out.printf(format, "2. Doctor");
        System.out.printf(format, "3. Pharmacist");
        System.out.printf(format, "4. Administrator");
        System.out.printf("%s\n", "-".repeat(27));
        System.out.print("Role: ");
        return sc.nextInt();
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
