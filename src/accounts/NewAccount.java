package accounts;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;

import user.*;
import utility.IDGenerator;

/**
 * This class handles the creation of new user accounts for different roles (Patient, Doctor, Pharmacist, Administrator).
 * <p>
 * It collects user information such as name, gender, age, password, and additional role-specific details like date of birth and blood type.
 * Also generates a unique hospital ID for each user.
 */
public class NewAccount {

    /**
     * Creates a new account based on the user's role. 
     * <p>
     * Prompts the user for their personal details, and generates a unique hospital ID 
     * before creating and returning an appropriate user object (Patient, Doctor, Pharmacist, or Administrator).
     * 
     * @param <T> The type of user (Patient, Doctor, Pharmacist, Administrator) that is being created.
     * @param sc A {@link Scanner} object to capture user input.
     * @param usersList The list of existing users used to generate a unique hospital ID for the new user.
     * @param role The role of the new user (Patient, Doctor, Pharmacist, Administrator).
     * @return The newly created {@link User} object, or {@code null} if there is an issue with the input or role.
     */
    @SuppressWarnings("unchecked")
    public static <T extends User> T createNewAccount(Scanner sc, List<T> usersList, Role role){
        String prefix, name, gender, password; //common variables
        int age, numDigits = 3;

        //common details, might want to capatalise first letter
        System.out.print("Enter your Name: ");
        name = sc.nextLine().trim();
        System.out.print("Enter your Gender: ");
        gender = sc.nextLine().trim();
        System.out.print("Enter your Age: ");
        try {
            age = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e){
            System.out.println("Invalid input type. Please enter an Integer.");
            sc.nextLine(); // Consume the invalid input to prevent an infinite loop
            return null; // Restart the loop to prompt the user again
        }
        System.out.print("Enter your Password: ");
        password = sc.nextLine().trim();

        //changing 1st letter to uppercase
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);

        //determine prefix based on role passed
        switch (role){
            case Patient:
                prefix = "P1";
                String id = IDGenerator.generateID(prefix, usersList, User::getHospitalID, numDigits);
                
                // Validate Date of Birth
                String DOB = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false); // Strict validation
                while (DOB == null) {
                    System.out.print("Enter your Date of Birth (DD/MM/YYYY): ");
                    String inputDOB = sc.nextLine().trim();
                    try {
                        dateFormat.parse(inputDOB);
                        DOB = inputDOB; // Valid date
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use DD/MM/YYYY.\n");
                    }
                }

                System.out.print("Enter your Blood Type (Ex. O+): ");
                String bloodType = sc.nextLine();
                System.out.print("Enter your Email: ");
                String email = sc.nextLine();
                return (T) new Patient(id, name, role, gender, age, password, DOB, bloodType, email);

            case Doctor:
                prefix = "D";
                id = IDGenerator.generateID(prefix, usersList, User::getHospitalID, numDigits);
                return (T) new Doctor(id, name, role, gender, age, password);
                
            case Pharmacist:
                prefix = "P";
                id = IDGenerator.generateID(prefix, usersList, User::getHospitalID, numDigits);
                return (T) new Pharmacist(id, name, role, gender, age, password);
                
            case Administrator:
                prefix = "A";
                id = IDGenerator.generateID(prefix, usersList, User::getHospitalID, numDigits);
                return (T) new Administrator(id, name, role, gender, age, password);
            default:
                return null;
        }
    }
}
