package medicalrecord;

import java.util.*;

import io.*;

public class MedicalRecordManager {
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static String originalPath = "../Data//Original/MedicalRecord_List.csv";
    private static String updatedPath = "../Data//Updated/MedicalRecord_List(Updated).csv";

    public static void loadMedicalRecords(boolean isFirstRun) {
        String filePath;
        if (isFirstRun) {
            filePath = originalPath;
            CSVclear.clearFile(updatedPath);
        } else {
            filePath = updatedPath;
        }
        medicalRecords.clear();

        Map<String, Integer> medicalRecordsColumnMapping = new HashMap<>();
        medicalRecordsColumnMapping.put("Doctor ID", 0);
        medicalRecordsColumnMapping.put("Patient ID", 1);
        medicalRecordsColumnMapping.put("Diagnoses", 2);
        medicalRecordsColumnMapping.put("Prescriptions", 3);
        medicalRecordsColumnMapping.put("Treatment Plan", 4);

        List<MedicalRecord> medicalRecordsMapList = CSVread.readMedicalRecordCSV(filePath, medicalRecordsColumnMapping);

        for (MedicalRecord record : medicalRecordsMapList) {
            if (record instanceof MedicalRecord) {
                medicalRecords.add(record);
            }
        }

        if (medicalRecords.isEmpty()) {
            System.out.println("No medical records were loaded.");
        } else {
            System.out.println("Medical records successfully loaded: " + medicalRecords.size());
        }
    }

    // getting medical records by patient
    public static List<MedicalRecord> getMedicalRecordsByPatient(String patientID) {
        List<MedicalRecord> patientRecords = new ArrayList<>();
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equalsIgnoreCase(patientID)) {
                patientRecords.add(record);
            }
        }
        return patientRecords;
    }

    public static MedicalRecord getMedicalRecord(String doctorID, String patientID) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getDoctorID().equalsIgnoreCase(doctorID) && record.getPatientID().equalsIgnoreCase(patientID)) {
                return record;
            }
        }
        return null;
    }

    public static List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public static void displayMedicalRecords() {
        if (medicalRecords.isEmpty()) {
            System.out.println("The medical records is currently empty.");
        } else {
            System.out.println("The Medical Records in the CSV file are:");
            for (MedicalRecord record : medicalRecords) {
                System.out.println(record.getRecordDetails());
            }
        }
    }

    public static void duplicateMedicalRecord() {
        CSVwrite.writeCSVList(updatedPath, medicalRecords);
    }

    public static void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    public static void removeMedicalRecord(String doctorID, String patientID) {
        MedicalRecord record = getMedicalRecord(doctorID, patientID);
        if (record != null) {
            System.out.println("Deleted medical record");
            medicalRecords.remove(record);
        } else {
            System.out.println("Medical record not found.");
        }
    }
}
