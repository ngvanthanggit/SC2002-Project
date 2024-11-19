package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HMSApp {
    public static void main(String[] args) {

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
            System.out.printf(format, "3. Exit");
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
                    System.out.println("Exiting App, System Terminating!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
                    continue;
            }
        } while (choice != 3);
    }
}
