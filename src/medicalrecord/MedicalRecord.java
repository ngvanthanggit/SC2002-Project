package medicalrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inventory.Medicine;

import accounts.PatientsAcc;
import user.Patient;
import user.Doctor;

/**
 * This class represents a patient's medical record, including diagnoses,
 * prescriptions, treatment plans, and prescription status. It provides functionality
 * for managing and displaying patient medical data, as well as handling updates to diagnoses,
 * prescriptions, and treatment plans.
 * <p>
 * This class is used to track the medical history of a patient, including details
 * about the doctor who manages the case, the patient's condition, and prescribed treatments.
 * </p>
 */
public class MedicalRecord {
    private String medicalRID;
    private String doctorID; // Unique identifier for the doctor
    private String patientID; // Unique identifier for the patient
    private List<String> diagnoses; // List of diagnoses for the patient
    private Map<String, Integer> prescriptions; // Medication and its quantity
    private List<String> treatmentPlans; // Treatment plan for the patient
    private PrescriptionStatus status; 

    /** Default constructor that initializes a new MedicalRecord object with empty values. */
    public MedicalRecord() {
        this.medicalRID = null;
        this.doctorID = null;
        this.patientID = null;
        this.diagnoses = new ArrayList<>();
        this.prescriptions = new HashMap<>();
        this.treatmentPlans = new ArrayList<>();
        this.status = null;
    }

    /**
     * Constructor that initializes a new MedicalRecord with specified values, without the prescription status.
     * 
     * @param medicalRID     The unique medical record ID.
     * @param doctorID       The unique identifier for the doctor.
     * @param patientID      The unique identifier for the patient.
     * @param diagnoses      A list of diagnoses for the patient.
     * @param prescriptions  A map of prescribed medications and their quantities.
     * @param treatmentPlans A list of treatment plans for the patient.
     */
    public MedicalRecord(String medicalRID, String doctorID, String patientID, List<String> diagnoses,
    Map<String, Integer> prescriptions, List<String> treatmentPlans) {
        this.medicalRID = medicalRID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnoses = new ArrayList<>(diagnoses);
        this.prescriptions = prescriptions;
        this.treatmentPlans = new ArrayList<>(treatmentPlans);
        this.status = PrescriptionStatus.PENDING;
    }

    /**
     * Constructor that initializes a new MedicalRecord with all specified values, including the prescription status.
     * 
     * @param medicalRID     The unique medical record ID.
     * @param doctorID       The unique identifier for the doctor.
     * @param patientID      The unique identifier for the patient.
     * @param diagnoses      A list of diagnoses for the patient.
     * @param prescriptions  A map of prescribed medications and their quantities.
     * @param treatmentPlans A list of treatment plans for the patient.
     * @param status         The prescription status of the medical record.
     */
    public MedicalRecord(String medicalRID, String doctorID, String patientID, List<String> diagnoses,
    Map<String, Integer> prescriptions, List<String> treatmentPlans, PrescriptionStatus status) {
        this.medicalRID = medicalRID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnoses = new ArrayList<>(diagnoses);
        this.prescriptions = prescriptions;
        this.treatmentPlans = new ArrayList<>(treatmentPlans);
        this.status = (status != null) ? status : PrescriptionStatus.PENDING; // Default to PENDING if null
    }

    // Getters and Setters

    /**
     * Gets the medical record ID.
     * 
     * @return the medical record ID.
     */
    public String getMedicalRID(){
        return medicalRID;
    }

    /**
     * Sets the medical record ID.
     * 
     * @param medicalRID the medical record ID to set.
     */
    public void setMedicalRID(String medicalRID){
        this.medicalRID = medicalRID;
    }

    /**
     * Gets the doctor ID.
     * 
     * @return the doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the doctor ID.
     * 
     * @param doctorID the doctor ID to set.
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the patient ID.
     * 
     * @return the patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID.
     * 
     * @param patientID the patient ID to set.
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the list of diagnoses.
     * 
     * @return the list of diagnoses.
     */
    public List<String> getDiagnoses() {
        return diagnoses;
    }

    /**
     * Sets the list of diagnoses.
     * 
     * @param diagnoses the list of diagnoses to set.
     */
    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    /**
     * Gets the prescriptions.
     * 
     * @return a map of medications and their quantities.
     */
    public Map<String, Integer> getPrescriptions() {
        return prescriptions;
    }

    /**
     * Gets the list of treatment plans.
     * 
     * @return the list of treatment plans.
     */
    public List<String> getTreatmentPlans() {
        return treatmentPlans;
    }

    /**
     * Sets the list of treatment plans.
     * 
     * @param treatmentPlans the list of treatment plans to set.
     */
    public void setTreatmentPlans(List<String> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
    }

    /**
     * Gets the prescription status.
     * 
     * @return the prescription status.
     */
    public PrescriptionStatus getStatus() {
        return status;
    }

    /**
     * Sets the prescription status.
     * 
     * @param status the prescription status to set.
     */
    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    // Method to display record details

    /**
     * Returns a string representing the details of the medical record.
     * 
     * @return A {@code string} containing the medical record details.
     */
    public String getRecordDetails() {
        StringBuilder prescriptionDetails = new StringBuilder();
        if (prescriptions != null) {
            for (Map.Entry<String, Integer> entry : prescriptions.entrySet()) {
                if (prescriptionDetails.length() > 0) {
                    prescriptionDetails.append("; ");
                }
                prescriptionDetails.append(entry.getKey()).append(": ").append(entry.getValue());
            }
        }
        return String.format("MedicalR ID: %s, Doctor ID: %s, Patient ID: %s, Diagnoses: %s, Prescriptions: %s, Treatment Plans: %s, Prescription Status: %s",
                medicalRID, doctorID, patientID, String.join(";", diagnoses), prescriptionDetails, String.join(";", treatmentPlans), status);
    }

    /**
     * Returns a string with the medical record details along with the patient's personal information.
     * 
     * @return A {@code string} containing the medical record with personal info.
     */
    public String getRecordDetailsWithPersonalInfo() {
        Patient patient = PatientsAcc.findPatientById(patientID);
        return "Medical Record for " + patient.getName() + " (" + patient.getHospitalID() + "):\n" +
                "- Contact: " + patient.getContactInfo() + "\n" +
                "- Blood Type: " + patient.getBloodType() + "\n" +
                "- Diagnoses: " + diagnoses + "\n" +
                "- Prescriptions: " + prescriptions + "\n" +
                "- Treatment Plans: " + treatmentPlans + "\n";
    }

    /**
     * Converts the medical record details into a CSV row format.
     * 
     * @return A {@code string} representing the medical record in CSV format.
     */
    public String toCSVRow() {
        // Join diagnoses with semicolon
        String diagnosesStr = (diagnoses != null && !diagnoses.isEmpty())
                ? "\"" + String.join(";", diagnoses) + "\"" // Wrap in quotes
                : "\"\"";
    
        // Format prescriptions as "Medication: Quantity; Medication: Quantity"
        String prescriptionsStr = (prescriptions != null && !prescriptions.isEmpty())
                ? "\"" + prescriptions.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .reduce((a, b) -> a + "; " + b)
                    .orElse("") + "\"" // Wrap in quotes
                : "\"\"";
    
        // Join treatment plans with semicolon
        String treatmentPlansStr = (treatmentPlans != null && !treatmentPlans.isEmpty())
                ? "\"" + String.join(";", treatmentPlans) + "\"" // Wrap in quotes
                : "\"\"";
    
        // Format as CSV row
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                medicalRID != null ? medicalRID : "",
                doctorID != null ? doctorID : "",
                patientID != null ? patientID : "",
                diagnosesStr,
                prescriptionsStr,
                treatmentPlansStr,
                (status != null) ? status.name() : "");
    }
    
    

    /**
     * Adds new diagnoses to the medical record.
     * 
     * @param newDiagnose A semicolon-separated list of diagnoses to be added.
     */
    public void addDiagnose(String newDiagnose) {
        if (newDiagnose == null || newDiagnose.trim().isEmpty()) {
            System.out.println("Invalid diagnose input. Cannot be empty.");
            return;
        }
    
        // Split the input by semicolon to allow multiple entries
        String[] diagnosesArray = newDiagnose.split(";");
        for (String diagnose : diagnosesArray) {
            String trimmedDiagnose = diagnose.trim(); // Remove extra spaces
            if (!trimmedDiagnose.isEmpty()) {
                this.diagnoses.add(trimmedDiagnose);
            }
        }
        System.out.println("Diagnoses added successfully: " + String.join(", ", this.diagnoses));
    }
    
    /** Clears all diagnoses from the medical record. */
    public void clearDiagnoses() {
        this.diagnoses.clear();
    }

    /**
     * Adds a new prescription to the medical record.
     * 
     * @param prescription A {@code string} in the format "Medication: Quantity".
     */
    public void addPrescription(String prescription) {
        try {
            String[] parts = prescription.split(": ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid prescription format. Use 'Medication: Quantity'.");
            }

            String medication = parts[0];
            int quantity;

            // Validate if the medication exists in the Medicine enum
            try {
                Medicine.valueOf(medication); // Throws IllegalArgumentException if not found
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid medication: " + medication + ". Must be one of: " + Arrays.toString(Medicine.values()));
            }

            // Validate and parse quantity
            try {
                quantity = Integer.parseInt(parts[1]);
                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid quantity format. Must be an integer.");
            }

            // Add the prescription to the map
            prescriptions.put(medication, prescriptions.getOrDefault(medication, 0) + quantity);
            System.out.println("Prescription added successfully: " + medication + ", " + quantity);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Print the error message
        }
    }

    /** Clears all prescriptions from the medical record. */
    public void clearPrescriptions() {
        prescriptions.clear();
    }

    /**
     * Adds a new treatment plan to the medical record.
     * 
     * @param newTreatmentPlan A semicolon-separated list of treatment plans to be added.
     */
    public void addTreatmentPlan(String newTreatmentPlan) {
        if (newTreatmentPlan == null || newTreatmentPlan.trim().isEmpty()) {
            System.out.println("Invalid treatment plan input. Cannot be empty.");
            return;
        }
    
        // Split the input by semicolon to allow multiple entries
        String[] plansArray = newTreatmentPlan.split(";");
        for (String plan : plansArray) {
            String trimmedPlan = plan.trim(); // Remove extra spaces
            if (!trimmedPlan.isEmpty()) {
                this.treatmentPlans.add(trimmedPlan);
            }
        }
        System.out.println("Treatment plans added successfully: " + String.join(", ", this.treatmentPlans));
    }
    
    /** Clears all treatment plans from the medical record. */
    public void clearTreatmentPlans() {
        this.treatmentPlans.clear();
    }
}
