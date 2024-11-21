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

public class MedicalRecord {
    private String medicalRID;
    private String doctorID; // Unique identifier for the doctor
    private String patientID; // Unique identifier for the patient
    private List<String> diagnoses; // List of diagnoses for the patient
    private Map<String, Integer> prescriptions; // Medication and its quantity
    private List<String> treatmentPlans; // Treatment plan for the patient
    private PrescriptionStatus status; 

    // Constructor
    public MedicalRecord() {
        this.medicalRID = null;
        this.doctorID = null;
        this.patientID = null;
        this.diagnoses = new ArrayList<>();
        this.prescriptions = new HashMap<>();
        this.treatmentPlans = new ArrayList<>();
        this.status = null;
    }

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

    public String getMedicalRID(){
        return medicalRID;
    }

    public void setMedicalRID(String medicalRID){
        this.medicalRID = medicalRID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Map<String, Integer> getPrescriptions() {
        return prescriptions;
    }

    public List<String> getTreatmentPlans() {
        return treatmentPlans;
    }

    public void setTreatmentPlans(List<String> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    // Method to display record details
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

    public String getRecordDetailsWithPersonalInfo() {
        Patient patient = PatientsAcc.findPatientById(patientID);
        return "Medical Record for " + patient.getName() + " (" + patient.getHospitalID() + "):\n" +
                "- Contact: " + patient.getEmail() + "\n" +
                "- Blood Type: " + patient.getBloodType() + "\n" +
                "- Diagnoses: " + diagnoses + "\n" +
                "- Prescriptions: " + prescriptions + "\n" +
                "- Treatment Plans: " + treatmentPlans + "\n";
    }

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
    
    

    // method for update diagnoses
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
    

    public void clearDiagnoses() {
        this.diagnoses.clear();
    }

    // method for update prescriptions
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


    public void clearPrescriptions() {
        prescriptions.clear();
    }

    // method for update treatment plans
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
    
    public void clearTreatmentPlans() {
        this.treatmentPlans.clear();
    }
}
