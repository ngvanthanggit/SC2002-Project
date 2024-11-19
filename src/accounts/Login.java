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

    public User authenticate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your hospital ID: ");
        String enteredId = scanner.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();

        for (User user : users) {
            if (user.getHospitalID().equals(enteredId) && user.getPassword().equals(enteredPassword)) {
                System.out.printf("%s\n", "-".repeat(27));
                System.out.println("Login successful! Welcome, " + user.getRole() + " " + user.getName());

                // prompt user to change password if it is default password
                checkDefPw(user, scanner);

                switch (user.getRole()) {
                    case Patient:
                        // create new patient class
                        Patient patient = (Patient) user;
                        return patient;

                    case Pharmacist:
                        // create new pharmacist class
                        return new Pharmacist(user.getHospitalID(), user.getName(), user.getRole(), user.getGender(),
                                user.getAge(), user.getPassword());

                    case Doctor:
                        // create new Doctor Class
                        return new Doctor(user.getHospitalID(), user.getName(), user.getRole(), user.getGender(),
                                user.getAge(), user.getPassword());

                    case Administrator:
                        return new Administrator(user.getHospitalID(), user.getName(), user.getRole(), user.getGender(),
                                user.getAge(), user.getPassword());
                    default:
                        return null;
                }
            }
        }
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

        }
    }
}
