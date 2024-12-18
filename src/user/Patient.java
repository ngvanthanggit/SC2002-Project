package user;

import interfaces.PatientApptInterface;
import userInterface.MedicalRecordUI;
import userInterface.PatientApptUI;
import userInterface.PatientUI;

/**
 * represents a Patient in HMS
 * extends {@link User} class
 */
public class Patient extends User {
    private String dateOB; // date of birth
    private String bloodType;
    private String email; // changeable

    /** default constructor for creating a Patient with no attributes */
    public Patient() {
        super();
        this.dateOB = null;
        this.bloodType = null;
        this.email = null;
    }

    /**
     * constructs a Patient object
     * 
     * @param hospitalID The hospital ID of the patient
     * @param name       The name of the patient
     * @param role       The role of the patient (must be {@link Role#Patient}).
     * @param gender     The gender of the patient
     * @param age        The age of the patient
     * @param password   The password of the patient
     * @param dateOB     The date of birth of the patient
     * @param bloodType  The blood type of the patient
     * @param email      The contact information of the patient
     */
    public Patient(String hospitalID, String name, Role role,
            String gender, int age, String password, String dateOB,
            String bloodType, String email) {
        super(hospitalID, name, role, gender, age, password);
        this.dateOB = dateOB;
        this.bloodType = bloodType;
        this.email = email;
    }

    // getter Methods()

    /**
     * Returns the date of birth of the patient
     * 
     * @return the date of birth
     */
    public String getDateOB() {
        return dateOB;
    }

    /**
     * Returns the blood type of the patient
     * 
     * @return the blood type
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Returns the contact information of the patient
     * 
     * @return the contact information
     */
    public String getEmail() {
        return email;
    }

    // setter Methods()

    /**
     * Updates date of birth of the patient
     * 
     * @param dateOB
     */
    public void setDateOB(String dateOB) {
        this.dateOB = dateOB;
    }

    /**
     * Updates blood type of the patient
     * 
     * @param bloodType
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Updates contact information of the patient
     * 
     * @param contactInfo
     */
    public void setEmail(String contactInfo) {
        this.email = contactInfo;
    }

    /**
     * Formatted string containing Patient information
     * Overrides the {@link User#userInfo()} method
     * 
     * @return A formatted string with Patient details
     */
    @Override
    public String userInfo() {
        return String.format(
                "[PatientID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s, DateOB = %s, BloodType = %s, ContactInfo = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword(), dateOB, bloodType,
                email);
    }

    /**
     * Returns a formatted string with Patient information for Medical Record
     * 
     * @return A formatted string with Patient details
     */
    public String getInfoForMedicalRecord() {
        String patientInfo = "Patient ID: " + this.getHospitalID() + "\n" +
                "Name: " + this.getName() + "\n" +
                "Date of Birth: " + this.getDateOB() + "\n" +
                "Gender: " + this.getGender() + "\n" +
                "Contact: " + this.email + "\n" +
                "Blood Type: " + this.getBloodType();
        return patientInfo;
    }

    /**
     * Displays the user interface for the Patient
     * Overrides the {@link User#displayUI()} method
     */
    @Override
    public void displayUI() {

        PatientApptInterface patientApptInterface = new PatientApptUI();
        MedicalRecordUI medicalRecordUI = new MedicalRecordUI();

        PatientUI patientUI = new PatientUI(this, patientApptInterface, medicalRecordUI);
        patientUI.displayMenu();
    }
}
