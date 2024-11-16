package main;

import java.util.Scanner;
import java.util.List;

import accounts.*;
import user.*;

public class HMSApp {
    private static String format = "|%-30s|\n";

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
            //List<User> admins = AdminsAcc.getAdmins();
            List<User> patients = PatientsAcc.getPatients();
            //List<User> pharmacists = PharmacistsAcc.getPharmacists();
            //List<User> doctors = DoctorsAcc.getDoctors();

            System.out.println("\nWelcome to the HMS App");
            System.out.printf("%s\n", "-".repeat(32));
            System.out.printf(format, "1. Login");
            System.out.printf(format, "2. Create new Patient account");
            System.out.printf(format, "3. Exit");
            System.out.printf("%s\n", "-".repeat(32));
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    MainLogin.login(sc);// login
                    break;
                case 2:
                    System.out.println("Creating new patient account!");
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
}
