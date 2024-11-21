package medicalrecord;

import java.util.*;

import inventory.InventoryItem;
import inventory.InventoryManager;
import utility.*;

public class MedicalRecordManager {
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();
    private static String originalPath = "Data//Original/MedicalRecord_List.csv";
    private static String updatedPath = "Data//Updated/MedicalRecord_List(Updated).csv";

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
        medicalRecordsColumnMapping.put("MedicalR ID", 0);
        medicalRecordsColumnMapping.put("Doctor ID", 1);
        medicalRecordsColumnMapping.put("Patient ID", 2);
        medicalRecordsColumnMapping.put("Diagnoses", 3);
        medicalRecordsColumnMapping.put("Prescriptions", 4);
        medicalRecordsColumnMapping.put("Treatment Plan", 5);
        medicalRecordsColumnMapping.put("Prescription Status", 6);

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

    public static MedicalRecord findMedicalRecordbyID(String medicalRID) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getMedicalRID().equals(medicalRID)) {
                return record;
            }
        }
        return null;
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
            System.out.println("\nThe medical records is currently empty.");
        } else {
            System.out.println("\nThe Medical Records in the CSV file are:");
            for (MedicalRecord record : medicalRecords) {
                System.out.println(record.getRecordDetails());
            }
        }
    }

    public static void displayPendingMR() {
        boolean found = false;
        for (MedicalRecord record : medicalRecords) {
            if (record.getStatus() == PrescriptionStatus.PENDING) {
                System.out.println(record.getRecordDetails());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No pending medical records found.");
        }
    }

    public static void displayDispensedMR() {
        boolean found = false;
        for (MedicalRecord record : medicalRecords) {
            if (record.getStatus() == PrescriptionStatus.DISPENSED) {
                System.out.println(record.getRecordDetails());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No dispensed medical records found.");
        }
    }

    public static void updateMRStatus(MedicalRecord record) {
        if (record == null) {
            return;
        }

        if (record.getStatus().equals(PrescriptionStatus.PENDING)) {

            // Update the inventory
            Map<String, Integer> prescriptions = record.getPrescriptions();
            for (Map.Entry<String, Integer> entry : prescriptions.entrySet()) {
                String medicineName = entry.getKey();
                int deduct = entry.getValue();

                InventoryItem item = InventoryManager.findItemByName(medicineName);
                if (item != null) {
                    // Deduct the quantity from the inventory
                    int newAmt = item.getQuantity() - deduct;
                    InventoryManager.deductItemStock(item, newAmt);
                    record.setStatus(PrescriptionStatus.DISPENSED);
                    System.out.println(
                            "The medicine for Meidical Record " + record.getMedicalRID() + " has been dispensed.");
                    System.out.println("Deducted " + deduct + " of " + medicineName + " from inventory.");
                    duplicateMedicalRecord();
                } else {
                    System.out.println("Failed to deduct " + deduct + " of " + medicineName + " from inventory." +
                            medicineName + " does not exist in our inventory");
                }
            }
        }
    }

    public static void duplicateMedicalRecord() {
        CSVwrite.writeCSVList(updatedPath, medicalRecords);
    }

    public static void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
        CSVwrite.writeCSV(updatedPath, record);
    }

    public static void removeMedicalRecord(String doctorID, String patientID) {
        MedicalRecord record = getMedicalRecord(doctorID, patientID);
        if (record != null) {
            System.out.println("Deleted medical record");
            medicalRecords.remove(record);
        } else {
            System.out.println("Medical record not found.");
        }
        duplicateMedicalRecord();
    }
}
