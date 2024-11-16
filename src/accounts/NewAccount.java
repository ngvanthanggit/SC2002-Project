package accounts;
import java.util.Scanner;

import io.IDGenerator;

import java.util.List;

import user.*;

public class NewAccount {

    private static String format = "|%-25s|\n";

    public static User createNewAccount(List<User> patients, List<User> pharmacists, List<User> doctors, List<User> admins){
        Scanner sc = new Scanner(System.in);
        String name, gender, password, DOB, bloodType, email, prefix;
        Role role;
        int age, numDeigits = 3;

        User user = null;
        System.out.println("Select the Account Type");
        System.out.printf("%s\n", "-".repeat(27));
        System.out.printf(format, "1. Patient");
        System.out.printf(format, "2. Pharmacist");            
        System.out.printf(format, "3. Doctor");
        System.out.printf(format, "4. Administrator");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); //consume

        //common details
        System.out.print("Enter your Name: ");
        name = sc.nextLine();
        System.out.print("Enter your Gender: ");
        gender = sc.nextLine();
        System.out.print("Enter your Age: ");
        age = sc.nextInt();
        sc.nextLine(); //consume
        System.out.print("Enter your Password: ");
        password = sc.nextLine();

        switch (choice){
            case 1:
                prefix = "P1";
                role = Role.Patient; //patient
                //role specific additional information
                System.out.print("Enter you Date of Birth (DD/MM/YYYY): ");
                DOB = sc.nextLine();
                System.out.print("Enter your Blood Type (Ex. O+): ");
                bloodType = sc.nextLine();
                System.out.print("Enter your email: ");
                email = sc.nextLine();

                //generate a ID automatically without duplicates based on role
                String patientID = IDGenerator.generateID(prefix, patients, User::getHospitalID, numDeigits);
                user = new Patient(patientID, name, role, gender, age, password, DOB, bloodType, email);
                break;
                
            case 2:
                prefix = "P";
                role = Role.Pharmacist; //pharamacist

                //generate a ID automatically without duplicates based on role
                String pharmID = IDGenerator.generateID(prefix, pharmacists, User::getHospitalID, numDeigits);
                user = new Pharmacist(pharmID, name, role, gender, age, password);
                break;

            case 3:
                prefix = "D";
                role = Role.Doctor; //doctor
                String doctorID = IDGenerator.generateID(prefix, doctors, User::getHospitalID, numDeigits);
                user = new Doctor(doctorID, name, role, gender, age, password);
                break;

            case 4:
                prefix = "A";
                role = Role.Administrator; //administrator

                //generate a ID automatically without duplicates based on role
                String adminID = IDGenerator.generateID(prefix, admins, User::getHospitalID, numDeigits);
                user = new Administrator(adminID, name, role, gender, age, password);
                break;
            default: System.out.println("Invalid Choice!");
        }
        return user;
    }
}
