package main;

import java.util.Scanner;
import java.util.List;

import accounts.*;
import user.*;
import menus.*;

public class HMSApp {
    private static String format = "|%-25s|\n";
    private static User loggedInUser; // To store the logged-in user

    public static void main(String[] args) {

        /*
         * Initialise the system by importing all data from any CSV files
         * Print out all information imported in a List/Database format
         * Create new Account/Login method
         * Depending on the role of the person, we will call the corresponding
         * classes/methods
         */

        SystemInitialisation.start();
        Scanner sc = new Scanner(System.in);
        int choice;

        // After importing Data prompt user for Login or Create New Account

        do {
            // create and retrieve the list instances holding all staff & patients
            // refresh the List every run
            List<User> admins = AdminsAcc.getAdmins();
            List<User> patients = PatientsAcc.getPatients();
            List<User> pharmacists = PharmacistsAcc.getPharmacists();
            List<User> doctors = DoctorsAcc.getDoctors();

            System.out.println("\nWelcome to the HMS App");
            System.out.printf("%s\n", "-".repeat(27));
            System.out.printf(format, "1. Login");
            System.out.printf(format, "2. Create new account");
            System.out.printf(format, "3. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    mainLogin(sc);// login
                    break;
                case 2:
                    //ignore
                    mainCreateAcc(admins, patients); // create newAcc
                    break;
                case 3:
                    System.out.println("Exiting App, System Terminating!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;

            }

        } while (choice != 3);
    }

    public static void mainLogin(Scanner sc) {

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

            // check return type of user,
            // add pharmacist and doctor here
            if (loggedIn instanceof Patient) {
                Patient patient = (Patient) loggedIn;
            } else if (loggedIn instanceof Doctor) {
                Doctor doctor = (Doctor) loggedIn;
                DoctorMenu doctorMenu = new DoctorMenu(doctor);
                doctorMenu.displayMenu();
            } else if (loggedIn instanceof Administrator) {
                Administrator admin = (Administrator) loggedIn;
                admin.displayMenu();
            } else if (loggedIn instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) loggedIn;
                pharmacist.displayMenu();
                //pharmacist.PharmacistMenu(); //need to check again
            } else {
            System.out.println("Login failed! Incorrect ID or password.");
            }
        }
    }

    public static void mainCreateAcc(List<User> admins, List<User> patients) {
        User newCreatedUser = NewAccount.createNewAccount(admins, patients);
        // add new user into database
        if (newCreatedUser != null && newCreatedUser instanceof Patient) {
            System.out.println("New account created!");
            PatientsAcc.addPatient((Patient) newCreatedUser); // cast to Patient, add to patient List & write into CSV
                                                              // database
        } else if (newCreatedUser != null && newCreatedUser instanceof Administrator) {
            System.out.println("New account created!");
            AdminsAcc.addAdmin((Administrator) newCreatedUser); // cast to Admin add to staff List & write into CSV
                                                                // database
        } else {
            System.out.println("Account creation failed!");
        } // add doctor and pharmacist in this manner
    }

    public static int checkRole(Scanner sc) {
        System.out.println("\nAre you a Staff or a Patient?");
        System.out.printf("%s\n", "-".repeat(25));
        System.out.printf(format, "1. Patient");
        System.out.printf(format, "2. Doctor");
        System.out.printf(format, "3. Pharmacist");
        System.out.printf(format, "4. Administrator");
        System.out.print("Role: ");
        return sc.nextInt();
    }

    public static User getLoggedInUser() {
    return loggedInUser;
    }
}
