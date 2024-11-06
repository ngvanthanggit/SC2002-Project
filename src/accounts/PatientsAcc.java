package accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.*;
import io.*;

public class PatientsAcc {

    // store the list of patients
    private static List<Patient> patients = new ArrayList<>();

    // static method to load patients from CSV file and return as a list
    public static void loadPatients(boolean isFirstRun) {

        String filePath;
        if (isFirstRun) {
            filePath = "Data//Original/Patient_List.csv";
            CSVclear.clearFile("Data//Updated/Patient_List(Updated).csv");
        } else {
            filePath = "Data//Updated/Patient_List(Updated).csv";
        }

        // clear the list to avoid having duplicate data
        patients.clear();

        Map<String, Integer> patientMapping = new HashMap<>();
        patientMapping.put("hospitalID", 0);
        patientMapping.put("name", 1);
        patientMapping.put("role", 2);
        patientMapping.put("gender", 3);
        patientMapping.put("age", 4);
        patientMapping.put("password", 5);
        patientMapping.put("dateOB", 6);
        patientMapping.put("bloodType", 7);
        patientMapping.put("contactInfo", 8);

        List<Object> patientMapList = CSVread.readCSV(filePath, patientMapping, "Patient");

        // add the data from CSV into patientsList
        for (Object user : patientMapList) {
            if (user instanceof Patient) {
                patients.add((Patient) user);
            }
        }

        if (patients.isEmpty()) {
            System.out.println("No patients were loaded.");
        } else {
            System.out.println("Patients successfully loaded: " + patients.size());
        }
    }

    // getter & display methods
    public static List<User> getPatients() {
        return new ArrayList<>(patients);
    }

    public static void displayPatients() {
        System.out.println("The Patient in the CSV file are: ");
        for (User patient : patients) {
            System.out.println(patient.userInfo());
        }
    }

    public static void duplicatePatient() {
        CSVwrite.writeCSVList("Data//Updated/Patient_List(Updated).csv", patients);
    }

    // find patient by hospitalID
    private static User findPatientById(String hospitalID) {
        for (User patient : patients) {
            if (patient.getHospitalID().equals(hospitalID)) {
                return patient;
            }
        }
        return null;
    }

    // updating methods
    public static void addPatient(Patient patient) {
        // add patient to patient List
        patients.add(patient);
        CSVwrite.writeCSV("Data//Updated/Patient_List(Updated).csv", patient);
    }

    // this method might or might not need, delete if neccessary
    public static void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public static void updatePassword(String hospitalID, String newPassword) {
        // find the staff ID to update
        User patientPWToUpdate = findPatientById(hospitalID);

        if (patientPWToUpdate != null) {
            patientPWToUpdate.setPassword(newPassword);
            duplicatePatient();
            System.out.println("Your password has been changed");
            return;
        }

    }

}
