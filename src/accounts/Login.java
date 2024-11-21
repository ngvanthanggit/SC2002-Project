package accounts;

import java.util.List;
import java.util.Scanner;
import user.*;

/**
 * This class is responsible for authenticating users based on their hospital ID and password.
 * <p>
 * It checks the hospitalID and password against a list of users and provides access based on user roles (Patient, Pharmacist, Doctor, Administrator).
 * If a user logs in with the default password, they are prompted to change it.
 */
public class Login {

    private List<User> users; // Accepts a list of users
    private static String defaultPW = "password1234";

    /**
     * Constructor for the Login class.
     * 
     * @param users The list of users to authenticate against.
     */
    public Login(List<User> users) {
        this.users = users;
    }

    /**
     * Authenticates a user by their hospital ID and password.
     * <p>
     * Prompts the user to enter their hospital ID and password.
     * If the details match, the corresponding user object is returned, 
     * otherwise null is returned indicating a failed login.
     * 
     * @param sc A {@link Scanner} object for user input.
     * @return The authenticated {@link user}, or {@code null} if authentication fails.
     */
    public User authenticate(Scanner sc) {

        System.out.println("Enter your hospitalID for Staff or Email for Patient: ");
        System.out.print("ID/Email: ");
        String enteredIdOrEmail = sc.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = sc.nextLine();

        for (User user : users) {
            // Allow Patients to log in with either hospital ID or email
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                if ((patient.getHospitalID().equals(enteredIdOrEmail) || patient.getEmail().equalsIgnoreCase(enteredIdOrEmail)) 
                    && patient.getPassword().equals(enteredPassword)) {

                    System.out.printf("%s\n", "-".repeat(27));
                    System.out.println("Login successful! Welcome, " + patient.getRole() + " " + patient.getName());

                    // Prompt to change the default password if applicable
                    checkDefPw(patient, sc);
                    return patient; // Return the authenticated Patient
                }
            } 
            // Other roles log in with hospital ID
            else if (user.getHospitalID().equals(enteredIdOrEmail) && user.getPassword().equals(enteredPassword)) {
                System.out.printf("%s\n", "-".repeat(27));
                System.out.println("Login successful! Welcome, " + user.getRole() + " " + user.getName());

                // prompt user to change password if it is default password
                checkDefPw(user, sc);

                switch (user.getRole()) {
                    case Patient:
                        // create new patient class
                        Patient patient = (Patient) user;
                        return patient;

                    case Pharmacist:
                        // create new pharmacist class
                        Pharmacist pharmacist = (Pharmacist) user;
                        return pharmacist;

                    case Doctor:
                        // create new Doctor Class
                        Doctor doctor = (Doctor) user;
                        return doctor;

                    case Administrator:
                        // create new Administrator Class
                        Administrator administrator = (Administrator) user;
                        return administrator;
                    default:
                        break;
                }
            }
        }
        System.out.println("Login failed! Incorrect ID or password.");
        return null; // Return null if login fails
    }

    /**
     * Checks if a user is logging in with the default password "password1234".
     * <p>
     * If they are, they are prompted to change their password.
     * 
     * @param user The user who is attempting to log in.
     * @param scanner A {@link scanner} object to capture the new password input.
     */
    public static void checkDefPw(User user, Scanner scanner) {
        if (user.getPassword().equals(defaultPW)) {
            System.out.println("You are logging in with a default password, please change your password. ");
            System.out.print("Enter your password: ");
            String newPassword = scanner.nextLine(); // set new Password

            // update password based on the role
            switch (user.getRole()) {
                case Patient:
                    PatientsAcc.updatePassword(user.getHospitalID(), newPassword);
                    break;
                case Administrator:
                    AdminsAcc.updatePassword(user.getHospitalID(), newPassword);
                    break;
                case Pharmacist:
                    PharmacistsAcc.updatePassword(user.getHospitalID(), newPassword);
                    break;
                case Doctor:
                    DoctorsAcc.updatePassword(user.getHospitalID(), newPassword);
                    break;
                default:
                    System.out.println("Invalid role. Password update failed.");
                    break;
            }
            System.out.println("Your password has been changed");
        }
    }
}
