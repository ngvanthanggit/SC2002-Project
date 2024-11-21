package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.SystemInitialisation;
import user.User;
import utility.*;
import user.Pharmacist;
import user.Role;

/**
 * This class is responsible for managing pharmacist accounts.
 * <p>
 * This includes loading data from CSV files, displaying, updating, finding, and removing pharmacists, 
 * as well as managing password updates. The class interacts with utility classes like {@code CSVread}, 
 * {@code CSVwrite}, and {@code CSVclear} to handle file operations.
 */
public class PharmacistsAcc {
    // store all Pharmacist objects
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    /** The file path to the original pharmacist CSV file. */
    private static String originalPath;
    /** The file path to the updated pharmacist CSV file. */
    private static String updatedPath;

    /**
     * Updates the file paths for loading and saving pharmacist data by retrieving them from 
     * the {@link SystemInitialisation} class. 
     * <p>
     * This method centralizes the file path management, ensuring that the file paths 
     * are dynamically retrieved rather than hardcoded, improving maintainability and flexibility.
     * <p>
     * File paths updated:
     * <ul>
     *   <li><b>originalPath</b>: Path to the original CSV file containing admin data.</li>
     *   <li><b>updatedPath</b>: Path to the updated CSV file for saving admin data.</li>
     * </ul>
     * 
     * @see SystemInitialisation#getFilePath(String)
     */
    public static void setFilePaths() {
        originalPath = SystemInitialisation.getFilePath("PharmacistOriginal");
        updatedPath = SystemInitialisation.getFilePath("PharmacistUpdated");
    }

    /**
     * Loads pharmacist accounts from a CSV file.
     * <p>
     * If it is the first run, it loads from the original file path and clears the updated file.
     * Otherwise, it loads from the updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first time; 
     *                   {@code false} otherwise.
     */
    public static void loadPharmacists(boolean isFirstRun) {
        // Load data from the file
        String filePath = isFirstRun ? originalPath : updatedPath;

        // clear the list to avoid having duplicate data
        pharmacists.clear();

        Map<String, Integer> pharmacistMapping = new HashMap<>();
        pharmacistMapping.put("hospitalID", 0);
        pharmacistMapping.put("name", 1);
        pharmacistMapping.put("role", 2);
        pharmacistMapping.put("gender", 3);
        pharmacistMapping.put("age", 4);
        pharmacistMapping.put("password", 5);

        List<Object> pharmacistsMapList = CSVread.readCSV(filePath, pharmacistMapping, "Pharmacist");

        // add the data from CSV into staffsList
        for (Object user : pharmacistsMapList) {
            if (user instanceof Pharmacist) {
                pharmacists.add((Pharmacist) user);
            }
        }

        if (pharmacists.isEmpty()) {
            System.out.println("No pharmacists were loaded.");
        } else {
            System.out.println("Pharmacists successfully loaded: " + pharmacists.size());
        }
    }

    /**
     * Returns a copy of the list of all pharmacists.
     * @return A list of {@link User} objects representing pharmacists.
     */
    public static List<User> getPharmacists() {
        return new ArrayList<>(pharmacists);
    }

    /** Displays all pharmacists currently in the list. */
    public static void displayPharmacists() {
        System.out.println("\nThe Pharmacists in the CSV file are: ");
        for (User pharmacist : pharmacists) {
            System.out.println(pharmacist.userInfo());
        }
    }

    /** Duplicates the current pharmacist list to the updated CSV file. */
    public static void duplicatePharmacist() {
        CSVwrite.writeCSVList(updatedPath, pharmacists);
    }

    /**
     * Finds a pharmacist by their hospital ID.
     * @param hospitalID The hospital ID of the pharmacist to find.
     * @return The {@link Pharmacist} object if found; {@code null} otherwise.
     */
    public static Pharmacist findPharmById(String hospitalID) {
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getHospitalID().equals(hospitalID)) {
                return pharmacist;
            }
        }
        return null;
    }

    /** Adds a new pharmacist to the list and saves the updated list to the CSV file. */
    public static void addPharmacist(Scanner sc) {
        Pharmacist newCreatedUser = NewAccount.createNewAccount(sc, pharmacists, Role.Pharmacist);

        if(newCreatedUser!=null){
            pharmacists.add(newCreatedUser);
            CSVwrite.writeCSV(updatedPath, newCreatedUser);
            System.out.println("Pharmacist " + newCreatedUser.getName() + " created!");
        } else {
            System.out.println("Account creation failed!");
        }
    }

    /**
     * Updates a pharmacist's details based on their hospital ID.
     * <p>
     * Prompts the user to enter updated details for the pharmacist.
     * @param sc A {@link Scanner} object for user input.
     */
    public static void updatePharmacist(Scanner sc) {
        displayPharmacists();
        System.out.print("\nEnter the Pharmacist ID to update: ");
        String hospitalID = sc.nextLine();
        Pharmacist pharmToUpdate = findPharmById(hospitalID);

        if(pharmToUpdate != null){
            System.out.print("Enter your Name: ");
            String name = sc.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            pharmToUpdate.setName(name);

            System.out.print("Enter your Gender: ");
            String gender = sc.nextLine();
            gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
            pharmToUpdate.setGender(gender);

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
            pharmToUpdate.setAge(age);

            System.out.print("Enter your Password: ");
            pharmToUpdate.setPassword(sc.nextLine());
            System.out.println("Pharmacist with Hospital ID " + hospitalID + " has been updated.");
            duplicatePharmacist(); // rewrite the CSV file with updated version
        
        } else {
            System.out.println("Pharmacist with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Removes a pharmacist from the list based on their hospital ID.
     * <p>
     * @param sc A {@link Scanner} object for user input.
     */
    public static void removePharmacist(Scanner sc) {
        displayPharmacists();
        System.out.print("\nEnter the Pharmacist ID to remove: ");
        String hospitalID = sc.nextLine();
        Pharmacist pharmacistToRemove = findPharmById(hospitalID);

        if (pharmacistToRemove != null) {
            pharmacists.remove(pharmacistToRemove); // remove Data from pharmacist List
            System.out.println("Pharmacist with Hospital ID " + hospitalID + " has been removed.");
            duplicatePharmacist(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Pharmacist with Hospital ID " + hospitalID + " not found.");
        }
    }

    /**
     * Updates the password of a pharmacist based on their hospital ID.
     * 
     * @param hospitalID The hospital ID of the pharmacist whose password is to be updated.
     * @param newPassword The new password to set for the pharmacist.
     */
    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        Pharmacist pharmacistPWToUpdate = findPharmById(hospitalID);

        if (pharmacistPWToUpdate != null) {
            pharmacistPWToUpdate.setPassword(newPassword);
            duplicatePharmacist();
            return;
        }
    }
}