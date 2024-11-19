package accounts;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;

import user.*;
import utility.CSVclear;
import utility.CSVread;
import utility.CSVwrite;

public class DoctorsAcc {
    // List of all doctors
    private static List<Doctor> doctors = new ArrayList<>();
    private static String originalPath = "../../Data//Original/Doctor_List.csv";
    private static String updatedPath = "../../Data//Updated/Doctor_List(Updated).csv";

    public static void loadDoctors(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }

        doctors.clear();

        Map<String, Integer> doctorMapping = new HashMap<>();
        doctorMapping.put("hospitalID", 0);
        doctorMapping.put("name", 1);
        doctorMapping.put("role", 2);
        doctorMapping.put("gender", 3);
        doctorMapping.put("age", 4);
        doctorMapping.put("password", 5);

        List<Object> doctorMapList = CSVread.readCSV(filePath, doctorMapping, "Doctor");

        for (Object user : doctorMapList) {
            if (user instanceof Doctor) {
                doctors.add((Doctor) user);
            }
        }

        if (doctors.isEmpty()) {
            System.out.println("No doctors were loaded.");
        } else {
            System.out.println("Doctors successfully loaded: " + doctors.size());
        }
    }

    public static List<User> getDoctors() {
        return new ArrayList<>(doctors);
    }

    public static void displayDoctors() {
        System.out.println("\nThe Doctor in the CSV file are: ");
        for (Doctor doctor : doctors) {
            System.out.println(doctor.userInfo());
        }
    }

    public static void duplicateDoctor() {
        CSVwrite.writeCSVList(updatedPath, doctors);
    }

    public static Doctor findDoctorById(String hospitalID) {
        for (Doctor doctor : doctors) {
            if (doctor.getHospitalID().equals(hospitalID)) {
                return doctor;
            }
        }
        return null;
    }

    // get doctor name using hospital ID
    public String getDoctorName(String hospitalID) {
        for (User doctor : doctors) {
            if (doctor.getHospitalID().equals(hospitalID)) {
                return (doctor != null) ? doctor.getName() : "Doctor not found";
            }
        }
        return null;
    }

    // updating methods
    public static void addDoctor(Scanner sc) {
        Doctor newCreatedUser = NewAccount.createNewAccount(sc, doctors, Role.Doctor);

        if (newCreatedUser != null) {
            doctors.add(newCreatedUser);
            CSVwrite.writeCSV(updatedPath, newCreatedUser);
            System.out.println("Doctor " + newCreatedUser.getName() + " created!");
        } else {
            System.out.println("Account creation failed!");
        }
    }

    public static void updateDoctor(Scanner sc) {
        displayDoctors();
        System.out.print("\nEnter the Doctor ID to update: ");
        String hospitalID = sc.nextLine();
        Doctor doctorToUpdate = findDoctorById(hospitalID);

        if (doctorToUpdate != null) {
            System.out.print("Enter your Name: ");
            String name = sc.nextLine();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            doctorToUpdate.setName(name);

            System.out.print("Enter your Gender: ");
            String gender = sc.nextLine();
            gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
            doctorToUpdate.setGender(gender);

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
            doctorToUpdate.setAge(age);

            System.out.print("Enter your Password: ");
            doctorToUpdate.setPassword(sc.nextLine());
            System.out.println("Doctor with Hospital ID " + hospitalID + " has been updated.");
            duplicateDoctor(); // rewrite the CSV file with updated version

        } else {
            System.out.println("Doctor with Hospital ID " + hospitalID + " not found.");
        }
    }

    public static void removeDoctor(Scanner sc) {
        displayDoctors();
        System.out.print("\nEnter the Doctor ID to remove: ");
        String hospitalID = sc.nextLine();
        Doctor pharmacistToRemove = findDoctorById(hospitalID);

        if (pharmacistToRemove != null) {
            doctors.remove(pharmacistToRemove); // remove Data from pharmacist List
            System.out.println("Doctor with Hospital ID " + hospitalID + " has been removed.");
            duplicateDoctor(); // rewrite the CSV file without the row removed
        } else {
            System.out.println("Doctor with Hospital ID " + hospitalID + " not found.");
        }
    }

    public static void updatePassword(String hospitalID, String newPassword) {
        User doctorPWToUpdate = findDoctorById(hospitalID);

        if (doctorPWToUpdate != null) {
            doctorPWToUpdate.setPassword(newPassword);
            duplicateDoctor();
            return;
        }
    }
}
