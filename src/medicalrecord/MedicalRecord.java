package medicalrecord;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String doctorID; // Unique identifier for the doctor
    private String patientID; // Unique identifier for the patient
    private List<String> diagnoses; // List of diagnoses for the patient
    private List<String> prescriptions; // List of prescribed medications
    private List<String> treatmentPlans; // Treatment plan for the patient

    // Constructor
    public MedicalRecord() {
        this.doctorID = null;
        this.patientID = null;
        this.diagnoses = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.treatmentPlans = new ArrayList<>();
    }

    public MedicalRecord(String doctorID, String patientID, List<String> diagnoses,
            List<String> prescriptions, List<String> treatmentPlans) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnoses = new ArrayList<>(diagnoses);
        this.prescriptions = new ArrayList<>(prescriptions);
        this.treatmentPlans = new ArrayList<>(treatmentPlans);
    }

    // Getters and Setters
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

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<String> getTreatmentPlans() {
        return treatmentPlans;
    }

    public void setTreatmentPlans(List<String> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
    }

    // Additional Methods
    public String getRecordDetails() {
        return "MedicalRecord{" +
                "doctorID='" + doctorID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", diagnoses=" + diagnoses +
                ", prescriptions=" + prescriptions +
                ", treatmentPlans='" + treatmentPlans + '\'' +
                "}";
    }

    public String toCSVRow() {
        String diagnosesStr = String.join(";", diagnoses);
        String prescriptionsStr = String.join(";", prescriptions);
        String treatmentPlansStr = String.join(";", this.treatmentPlans);
        return String.format("%s,%s,\"%s\",\"%s\",\"%s\"",
                doctorID,
                patientID,
                diagnosesStr,
                prescriptionsStr,
                treatmentPlansStr);
    }

    // method for update diagnoses
    public void addDiagnose(String newDiagnose) {
        this.diagnoses.add(newDiagnose);
    }

    public void clearDiagnoses() {
        this.diagnoses.clear();
    }

    // method for update prescriptions
    public void addPrescription(String newPrescription) {
        this.prescriptions.add(newPrescription);
    }

    public void clearPrescriptions() {
        this.prescriptions.clear();
    }

    // method for update treatment plans
    public void addTreatmentPlan(String newTreatmentPlan) {
        this.treatmentPlans.add(newTreatmentPlan);
    }

    public void clearTreatmentPlans() {
        this.treatmentPlans.clear();
    }
}
