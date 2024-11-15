package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.*;
import user.User;
import user.Administrator;

public class AdminsAcc {
    // store all Administrator objects
    private static List<Administrator> admins = new ArrayList<>();
    private static String originalPath = "Data//Original/Admin_List.csv";
    private static String updatedPath = "Data//Updated/Admin_List(Updated).csv";

    // read in csv file of staffs
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

    // getter & display methods
    public static List<User> getAdmins() {
        return new ArrayList<>(admins);
    }

    public static void displayAdmins() {
        System.out.println("The Admins in the CSV file are: ");
        for (User admin : admins) {
            System.out.println(admin.userInfo());
        }
    }

    public static void duplicateAdmin() {
        CSVwrite.writeCSVList(updatedPath, admins);
    }

    // find admin by hospitalID
    private static User findStaffById(String hospitalID) {
        for (User admin : admins) {
            if (admin.getHospitalID().equals(hospitalID)) {
                return admin;
            }
        }
        return null;
    }

    // updating methods
    public static void addAdmin(Administrator administrator) {
        // add new users to the staff List
        admins.add(administrator);
        CSVwrite.writeCSV(updatedPath, administrator);
    }

    public static void removeAdmin(String hospitalID) {
        User adminToRemove = findStaffById(hospitalID);

        if (adminToRemove != null) {
            admins.remove(adminToRemove); // remove Data from admin List
            System.out.println("Staff member with Hospital ID " + hospitalID + " has been removed.");
            duplicateAdmin(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Staff member with Hospital ID " + hospitalID + " not found.");
        }
    }

    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        User adminPWToUpdate = findStaffById(hospitalID);

        if (adminPWToUpdate != null) {
            adminPWToUpdate.setPassword(newPassword);
            duplicateAdmin();
            System.out.println("Your password has been changed");
            return;
        }

    }

    public static void updateStaff() {

    }

}
