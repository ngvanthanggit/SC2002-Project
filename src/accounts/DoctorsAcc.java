package accounts;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import io.CSVclear;
import io.CSVread;
import io.CSVwrite;
import user.*;

public class DoctorsAcc {
    // List of all doctors
    private static List<Doctor> doctors = new ArrayList<>();
    private static String originalPath = "Data//Original/Doctor_List.csv";
    private static String updatedPath = "Data//Updated/Doctor_List(Updated).csv";

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
        for (User doctor : doctors) {
            System.out.println(doctor.userInfo());
        }
    }

    public static void duplicateDoctor() {
        CSVwrite.writeCSVList(updatedPath, doctors);
    }

    public static User findDoctorById(String hospitalID) {
        for (User doctor : doctors) {
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

    private static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        CSVwrite.writeCSV(updatedPath, null);
    }

    public static void removeDoctor(Doctor doctor) {
        doctors.remove(doctor);
    }

    public static void updatePassword(String hospitalID, String newPassword) {
        User doctorPWToUpdate = findDoctorById(hospitalID);

        if (doctorPWToUpdate != null) {
            doctorPWToUpdate.setPassword(newPassword);
            duplicateDoctor();
            System.out.println("Your password has been changed");
            return;
        }
    }
}
