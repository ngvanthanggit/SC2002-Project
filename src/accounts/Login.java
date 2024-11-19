package accounts;

import java.util.List;
import java.util.Scanner;
import user.*;

public class Login {

    private List<User> users; // Accepts a list of users
    private static String defaultPW = "password1234";

    public Login(List<User> users) {
        this.users = users;
    }

    public User authenticate(Scanner sc) {

        System.out.print("Enter your hospital ID: ");
        String enteredId = sc.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = sc.nextLine();

        for (User user : users) {
            if (user.getHospitalID().equals(enteredId) && user.getPassword().equals(enteredPassword)) {
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

    // check if user is using default pw "password1234"
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
