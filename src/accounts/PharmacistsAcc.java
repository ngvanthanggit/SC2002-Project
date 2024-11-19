package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import user.User;
import utility.*;
import user.Pharmacist;
import user.Role;

public class PharmacistsAcc {
    // store all Administrator objects
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static String originalPath = "../../Data//Original/Pharm_List.csv";
    private static String updatedPath = "../../Data//Updated/Pharm_List(Updated).csv";

    // read in csv file of staffs
    public static void loadPharmacists(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

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

    // getter & display methods
    public static List<User> getPharmacists() {
        return new ArrayList<>(pharmacists);
    }

    public static void displayPharmacists() {
        System.out.println("\nThe Pharmacists in the CSV file are: ");
        for (User pharmacist : pharmacists) {
            System.out.println(pharmacist.userInfo());
        }
    }

    public static void duplicatePharmacist() {
        CSVwrite.writeCSVList(updatedPath, pharmacists);
    }

    // find Pharmacist by hospitalID
    private static Pharmacist findPharmById(String hospitalID) {
        for (Pharmacist pharmacist : pharmacists) {
            if (pharmacist.getHospitalID().equals(hospitalID)) {
                return pharmacist;
            }
        }
        return null;
    }

    // updating methods
    public static void addPharmacist(Scanner sc) {
        Pharmacist newCreatedUser = NewAccount.createNewAccount(sc, pharmacists, Role.Pharmacist);

        if (newCreatedUser != null) {
            pharmacists.add(newCreatedUser);
            CSVwrite.writeCSV(updatedPath, newCreatedUser);
            System.out.println("Pharmacist " + newCreatedUser.getName() + " created!");
        } else {
            System.out.println("Account creation failed!");
        }
    }

    public static void updatePharmacist(Scanner sc) {
        displayPharmacists();
        System.out.print("\nEnter the Pharmacist ID to update: ");
        String hospitalID = sc.nextLine();
        Pharmacist pharmToUpdate = findPharmById(hospitalID);

        if (pharmToUpdate != null) {
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
            } catch (InputMismatchException e) {
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