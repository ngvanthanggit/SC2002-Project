package medicalrecord;

import java.util.*;

import inventory.InventoryItem;
import inventory.InventoryManager;
import main.SystemInitialisation;
import utility.*;

/**
 * This class is responsible for managing the medical records of patients.
 * It handles loading, updating, retrieving, and displaying medical records.
 * Additionally,
 * it manages the inventory updates when a prescription is dispensed.
 * <p>
 * This class interacts with CSV files to persist medical records and inventory
 * updates.
 * </p>
 */
public class MedicalRecordManager {

    /** A list to store all {@link MedicalRecord} objects. */
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();
    
    /** The file path to the original medical record CSV file. */
    private static String originalPath;
    
    /** The file path to the updated medical record CSV file. */
    private static String updatedPath;

    /**
     * Updates the file paths for loading and saving medical record data by retrieving them from 
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
        originalPath = SystemInitialisation.getFilePath("MedicalRecordOriginal");
        updatedPath = SystemInitialisation.getFilePath("MedicalRecordUpdated");
    }

    /**
     * Loads the medical records from a CSV file. Depending on whether it is the
     * first run or not,
     * it loads either from the original or updated file.
     * 
     * @param isFirstRun {@code true} if the application is running for the first
     *                   time;
     *                   {@code false} otherwise.
     */
    public static void loadMedicalRecords(boolean isFirstRun) {
        // Load data from the file
        String filePath = isFirstRun ? originalPath : updatedPath;

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

    /**
     * Gets all medical records associated with a specific patient.
     * 
     * @param patientID the ID of the patient whose medical records are to be
     *                  retrieved.
     * @return a list of medical records associated with the patient.
     */
    public static List<MedicalRecord> getMedicalRecordsByPatient(String patientID) {
        List<MedicalRecord> patientRecords = new ArrayList<>();
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equalsIgnoreCase(patientID)) {
                patientRecords.add(record);
            }
        }
        return patientRecords;
    }

    /**
     * Finds a medical record by its unique medical record ID.
     * 
     * @param medicalRID the unique medical record ID.
     * @return the medical record associated with the ID, or null if not found.
     */
    public static MedicalRecord findMedicalRecordbyID(String medicalRID) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getMedicalRID().equals(medicalRID)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Gets the medical record associated with a specific doctor and patient.
     * 
     * @param doctorID  the ID of the doctor.
     * @param patientID the ID of the patient.
     * @return the medical record for the specified doctor and patient, or null if
     *         not found.
     */
    public static MedicalRecord getMedicalRecord(String doctorID, String patientID) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getDoctorID().equalsIgnoreCase(doctorID) && record.getPatientID().equalsIgnoreCase(patientID)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Gets all medical records associated with a specific doctor and patient.
     * 
     * @param doctorID  the ID of the doctor.
     * @param patientID the ID of the patient.
     * @return a list of medical records associated with the doctor and patient.
     */
    public static List<MedicalRecord> getMedicalRecords(String doctorID, String patientID) {
        List<MedicalRecord> records = new ArrayList<>();
        for (MedicalRecord record : medicalRecords) {
            if (record.getDoctorID().equalsIgnoreCase(doctorID) && record.getPatientID().equalsIgnoreCase(patientID)) {
                records.add(record);
            }
        }
        return records;
    }

    /**
     * Gets the list of all medical records.
     * 
     * @return a list of all medical records.
     */
    public static List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    /** Displays all medical records currently loaded. */
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

    /**
     * Displays all pending medical records (those with a prescription status of
     * {@code PENDING}).
     */
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

    /**
     * Displays all dispensed medical records (those with a prescription status of
     * {@code DISPENSED}).
     */
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

    /**
     * Updates the prescription status of a medical record to DISPENSED and adjusts
     * the inventory.
     * If the prescription status is already PENDING, the method will attempt to
     * deduct the prescribed
     * medicines from the inventory and update the record's status.
     * 
     * @param record the medical record to update.
     */
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

    /** Duplicates the current medical record list into the updated CSV file. */
    public static void duplicateMedicalRecord() {
        CSVwrite.writeCSVList(updatedPath, medicalRecords);
    }

    /**
     * Adds a new medical record to the list and updates the CSV file.
     * 
     * @param record the medical record to add.
     */
    public static void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
        duplicateMedicalRecord();
    }

    /**
     * Removes a medical record from the list based on the doctor ID and patient ID.
     * If the record is found, it will be deleted and the updated list will be
     * saved.
     * 
     * @param doctorID  the ID of the doctor.
     * @param patientID the ID of the patient.
     */
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
