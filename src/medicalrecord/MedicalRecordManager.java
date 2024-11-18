package medicalrecord;

import java.util.*;

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

    public static void updateMedicalRecord() {

    }

    // Methods for Diagnoses
    public void addDiagnose(String doctorID, String patientID, String newDiagnose) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> diagnoses = medicalRecord.getDiagnoses();
                diagnoses.add(newDiagnose);
                medicalRecord.setDiagnoses(diagnoses);
                System.out.println("Diagnoses added successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }

    public void clearDiagnoses(String doctorID, String patientID) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> diagnoses = medicalRecord.getDiagnoses();
                diagnoses.clear();
                medicalRecord.setDiagnoses(diagnoses);
                System.out.println("Diagnoses cleared successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }

    // Methods for Prescriptions
    public void addPrescription(String doctorID, String patientID, String newPrescription) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> prescriptions = medicalRecord.getPrescriptions();
                prescriptions.add(newPrescription);
                medicalRecord.setPrescriptions(prescriptions);
                System.out.println("Prescription added successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }

    public void clearPrescriptions(String doctorID, String patientID) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> prescriptions = new ArrayList<String>();
                medicalRecord.setPrescriptions(prescriptions);
                System.out.println("Prescriptions cleared successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }

    // Methods for treatment plans
    public void addTreatmentPlan(String doctorID, String patientID, String newTreatmentPlan) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> treatmenPlans = medicalRecord.getTreatmentPlans();
                treatmenPlans.add(newTreatmentPlan);
                medicalRecord.setTreatmentPlans(treatmenPlans);
                System.out.println("Treatment Plan added successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }

    public void clearTreatmentPlans(String doctorID, String patientID) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getDoctorID().equalsIgnoreCase(doctorID)
                    && medicalRecord.getPatientID().equalsIgnoreCase(patientID)) {
                List<String> TreatmentPlans = new ArrayList<String>();
                medicalRecord.setTreatmentPlans(TreatmentPlans);
                System.out.println("TreatmentPlans cleared successfully.");
                return;
            }
        }
        System.out.println("No medical record found for this patient.");
    }
}
