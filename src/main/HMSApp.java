package main;

import java.util.Scanner;

public class HMSApp {
    public static void main(String[] args) {

        //initialise the system by importing all data from any CSV files
        SystemInitialisation.start(); 
        Scanner sc = new Scanner(System.in);
        int choice;
        String format = "|%-30s|\n";

        //after importing Data prompt user for Login or Create New Patient Account
        do {
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
