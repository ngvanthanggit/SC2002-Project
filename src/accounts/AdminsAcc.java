package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.MainUI;
import user.User;
import utility.*;
import user.Administrator;
import user.Role;

/**
 * This class is responsible for managing administrator accounts.
 * <p>
 * This includes loading data from CSV files, displaying, adding, updating, and removing administrators, 
 * as well as managing password updates. The class interacts with utility classes like {@code CSVread}, 
 * {@code CSVwrite}, and {@code CSVclear} to handle file operations.
 */
public class AdminsAcc {

    /** A list to store all {@link Administrator} objects. */
    private static List<Administrator> admins = new ArrayList<>();

    /** The file path to the original administrator CSV file. */
    private static String originalPath = "Data//Original/Admin_List.csv";

    /** The file path to the updated administrator CSV file. */
    private static String updatedPath = "Data//Updated/Admin_List(Updated).csv";

    /**
     * Loads administrator accounts from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
    public static void loadAdmins(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

        // clear the list to avoid having duplicate data
        admins.clear();

        Map<String, Integer> adminMapping = new HashMap<>();
        adminMapping.put("hospitalID", 0);
        adminMapping.put("name", 1);
        adminMapping.put("role", 2);
        adminMapping.put("gender", 3);
        adminMapping.put("age", 4);
        adminMapping.put("password", 5);

        List<Object> adminsMapList = CSVread.readCSV(filePath, adminMapping, "Administrator");

        // add the data from CSV into staffsList
        for (Object user : adminsMapList) {
            if (user instanceof Administrator) {
                admins.add((Administrator) user);
            }
        }

        if (admins.isEmpty()) {
            System.out.println("No admins were loaded.");
        } else {
            System.out.println("Admins successfully loaded: " + admins.size());
        }
    }

    /**
     * Returns a copy of the list of all administrators.
     * 
     * @return A list of {@link User} objects representing administrators.
     */
    public static List<User> getAdmins() {
        return new ArrayList<>(admins);
    }

    /** Displays all administrators currently in the list. */
    public static void displayAdmins() {
        System.out.println("\nThe Admins in the CSV file are: ");
        for (User admin : admins) {
            System.out.println(admin.userInfo());
        }
    }

    /** Duplicates the current administrator list to the updated CSV file. */
    public static void duplicateAdmin() {
        CSVwrite.writeCSVList(updatedPath, admins);
    }

    /**
     * Finds an administrator by their hospital ID.
     * 
     * @param hospitalID The hospital ID of the administrator to find.
     * @return The {@link Administrator} object if found; {@code null} otherwise.
     */
    private static Administrator findAdminById(String hospitalID) {
        for (Administrator admin : admins) {
            if (admin.getHospitalID().equals(hospitalID)) {
                return admin;
            }
        }
        return null;
    }

    /** 
     * Adds a new administrator to the list and saves the updated list to the CSV file. 
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public static void addAdmin(Scanner sc) {
        Administrator newCreatedUser = NewAccount.createNewAccount(sc, admins, Role.Administrator);

        if(newCreatedUser!=null){
            admins.add(newCreatedUser);
            CSVwrite.writeCSV(updatedPath, newCreatedUser);
            System.out.println("Admin " + newCreatedUser.getName() + " created!");
        } else {
            System.out.println("Account creation failed!");
        }
    }

    /**
     * Updates an administrator's details based on their hospital ID.
     * <p>
     * Prompts the user to enter updated details for the administrator.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public static void updateAdmin(Scanner sc) {
        displayAdmins();
        System.out.print("\nEnter the Admin ID to update: ");
        String hospitalID = sc.nextLine();
        Administrator adminToUpdate = findAdminById(hospitalID);

        if(adminToUpdate != null){
            System.out.print("Enter your Name: ");
            String name = sc.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            adminToUpdate.setName(name);

            System.out.print("Enter your Gender: ");
            String gender = sc.nextLine();
            gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
            adminToUpdate.setGender(gender);

            System.out.print("Enter your Age: ");
            int age;
            try {
                age = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Invalid input type. Please enter an Integer.");
                sc.nextLine(); // Consume the invalid input to prevent an infinite loop
                return;
            }
            adminToUpdate.setAge(age);

            System.out.print("Enter your Password: ");
            adminToUpdate.setPassword(sc.nextLine());
            System.out.println("Administrator with Hospital ID " + hospitalID + " has been updated.");
            duplicateAdmin(); // rewrite the CSV file with updated version
        
        } else {
            System.out.println("Administrator with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Removes an administrator from the list based on their hospital ID.
     * <p>
     * Prevents the currently logged-in administrator from removing themselves.
     * 
     * @param sc A {@link Scanner} object for user input.
     */
    public static void removeAdmin(Scanner sc) {
        displayAdmins();
        System.out.print("\nEnter the Admin ID to remove: ");
        String hospitalID = sc.nextLine();

        //admin shouldn't be able to remove themself while logged in
        Administrator currentAdmin = (Administrator) MainUI.getLoggedInUser();
        if(currentAdmin.getHospitalID().equalsIgnoreCase(hospitalID)){
            System.out.println("Error! You can't remove yourself while logged in!");
            return;
        }

        User adminToRemove = findAdminById(hospitalID);
        if (adminToRemove != null) {
            admins.remove(adminToRemove); // remove Data from admin List
            System.out.println("Administrator with Hospital ID " + hospitalID + " has been removed.");
            duplicateAdmin(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Administrator with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Updates the password of an administrator based on their hospital ID.
     * 
     * @param hospitalID The hospital ID of the administrator whose password is to be updated.
     * @param newPassword The new password to set for the administrator.
     */
    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        Administrator adminPWToUpdate = findAdminById(hospitalID);

        if (adminPWToUpdate != null) {
            adminPWToUpdate.setPassword(newPassword);
            duplicateAdmin();
            return;
        }
    }
}
