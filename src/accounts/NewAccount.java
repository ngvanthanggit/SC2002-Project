package accounts;
import java.util.Scanner;
import java.util.List;

import user.*;

public class NewAccount {

    private static String format = "|%-25s|\n";

    public static User createNewAccount(List<User> admins, List<User> patients){
        Scanner sc = new Scanner(System.in);
        String name, role, gender, password, DOB, bloodType, email;
        int age;

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

        //automatically set ID based on Role
        System.out.print("Enter your Name: ");
        name = sc.nextLine();
        System.out.print("Enter your Gender: ");
        gender = sc.nextLine();
        System.out.print("Enter your Age: ");
        age = sc.nextInt();
        sc.nextLine(); //consume
        System.out.print("Enter your Password: ");
        password = sc.nextLine();

        //generate a ID automatically without duplicates based on role
        String hospitalID = generateID(choice, admins, patients);

        switch (choice){
            case 1:
                //patient, role specific additional information
                role = "Patient";
                System.out.print("Enter you Date of Birth (DD/MM/YYYY): ");
                DOB = sc.nextLine();
                System.out.print("Enter your Blood Type (Ex. O+): ");
                bloodType = sc.nextLine();
                System.out.print("Enter your email: ");
                email = sc.nextLine();
                user = new Patient(hospitalID, name, role, gender, age, password, DOB, bloodType, email);
                break;
                
            case 2:
                //pharmacist
                role = "Pharmacist";
                //user = new User(hospitalID, name, role, gender, age, password);
                break;

            case 3:
                //doctor
                role = "Doctor";
                //user = new User(hospitalID, name, role, gender, age, password);
                break;

            case 4:
                //administrator 
                role = "Administrator";
                user = new Administrator(hospitalID, name, role, gender, age, password);
                break;
            default: System.out.println("Invalid Choice!");
        }
        return user;
    }

    public static String generateID(int choice, List<User> staffs, List<User> patients){
        String prefix = null;
        int numDigits, maxID = 0;

        switch(choice){
            case 1:
                prefix = "P1"; //patient
                numDigits = 3;
                break;

            case 2:
                prefix = "P"; //pharmacist
                numDigits = 3;
                break;

            case 3:
                prefix = "D"; //doctor
                numDigits = 3;
                break;

            case 4:
                prefix = "A"; //administrator
                numDigits = 3;
                break;
            default: throw new IllegalArgumentException("Invalid role choice");
        }

        if (prefix.equals(null)){
            System.out.println("ID Generation failed!");
        } else if(prefix.equals("P1")){
            maxID = findMaxID(prefix, patients, numDigits);
        } else {
            maxID = findMaxID(prefix, staffs, numDigits);
        }

        return prefix + String.format("%0" + numDigits + "d",  maxID + 1);
    }

    public static int findMaxID(String prefix, List<User> users, int numDigits){
        int maxID = 0;
        //loop through the whole database
        for(User user: users){
            if(user.getHospitalID().startsWith(prefix)){
                try {
                    int id = Integer.parseInt(user.getHospitalID().substring(prefix.length()));
                    if(id>maxID){
                    maxID = id;
                    }
                } catch (NumberFormatException e){
                    //skip IDs that dont match with prefix
                }
            }
        }

        return maxID;
    }
}
