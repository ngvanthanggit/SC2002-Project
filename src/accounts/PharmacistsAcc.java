package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.*;
import user.User;
import user.Pharmacist;

public class PharmacistsAcc {
    // store all Administrator objects
    private static List<Pharmacist> pharmacists = new ArrayList<>();
    private static String originalPath = "../Data//Original/Pharm_List.csv";
    private static String updatedPath = "../Data//Updated/Pharm_List(Updated).csv";

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
        System.out.println("The Pharmacists in the CSV file are: ");
        for (User pharmacist : pharmacists) {
            System.out.println(pharmacist.userInfo());
        }
    }

    public static void duplicatePharmacist() {
        CSVwrite.writeCSVList(updatedPath, pharmacists);
    }

    // find admin by hospitalID
    private static User findStaffById(String hospitalID) {
        for (User pharmacist : pharmacists) {
            if (pharmacist.getHospitalID().equals(hospitalID)) {
                return pharmacist;
            }
        }
        return null;
    }

    // updating methods
    public static void addPharmacist(Pharmacist pharmacist) {
        // add new users to the staff List
        pharmacists.add(pharmacist);
        CSVwrite.writeCSV(updatedPath, pharmacist);
    }

    public static void removePharmacist(String hospitalID) {
        User pharmacistToRemove = findStaffById(hospitalID);

        if (pharmacistToRemove != null) {
            pharmacists.remove(pharmacistToRemove); // remove Data from pharmacist List
            System.out.println("Staff member with Hospital ID " + hospitalID + " has been removed.");
            duplicatePharmacist(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Staff member with Hospital ID " + hospitalID + " not found.");
        }
    }

    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        User pharmacistPWToUpdate = findStaffById(hospitalID);

        if (pharmacistPWToUpdate != null) {
            pharmacistPWToUpdate.setPassword(newPassword);
            duplicatePharmacist();
            System.out.println("Your password has been changed");
            return;
        }

    }

    public static void updateStaff() {

    }

}