package accounts;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import io.CSVclear;
import io.CSVread;
import io.CSVwrite;
import user.*;

public class DoctorAcc {
    // List of all doctors
    private static List<Doctor> doctors = new ArrayList<>();

    public static void loadDoctors(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = "Data//Original/Doctor_List.csv";
            CSVclear.clearFile("Data//Updated/Doctor_List(Updated).csv");
        } else {
            filePath = "Data//Updated/Doctor_List(Updated).csv";
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
        System.out.println("The Doctor in the CSV file are: ");
        for (User doctor : doctors) {
            System.out.println(doctor.userInfo());
        }
    }

    public static void duplicateDoctor() {
        CSVwrite.writeCSVList("Data//Updated/Doctor_List(Updated).csv", doctors);
    }

    private static User findDoctorById(String hospitalID) {
        for (User doctor : doctors) {
            if (doctor.getHospitalID() == hospitalID) {
                return doctor;
            }
        }
        return null;
    }

    private static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        CSVwrite.writeCSV("Data//Updated/Doctor_List(Updated).csv", null);
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
