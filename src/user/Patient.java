package user;

import interfaces.PatientApptInterface;
import userInterface.MedicalRecordUI;
import userInterface.PatientApptUI;
import userInterface.PatientUI;

public class Patient extends User {
    private String dateOB; // date of birth
    private String bloodType;
    private String contactInfo; // changeable

    // constructors
    public Patient() {
        super();
        this.dateOB = null;
        this.bloodType = null;
        this.contactInfo = null;
    }

    public Patient(String hospitalID, String name, Role role,
            String gender, int age, String password, String dateOB,
            String bloodType, String contactInfo) {
        super(hospitalID, name, role, gender, age, password);
        this.dateOB = dateOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    // get Methods()
    public String getDateOB() {
        return dateOB;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    // set Methods()
    public void setDateOB(String dateOB){
        this.dateOB = dateOB;
    }

    public void setBloodType(String bloodType){
        this.bloodType = bloodType;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Formatted string containing Patient information
     * Overrides the {@link User#userInfo()} method
     * @return A formatted string with Patient details
     */
    @Override
    public String userInfo() {
        return String.format(
                "[PatientID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s, DateOB = %s, BloodType = %s, ContactInfo = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword(), dateOB, bloodType,
                contactInfo);
    }

    /**
     * Displays the user interface for the Patient
     * Overrides the {@link User#displayUI()} method
     */
    @Override 
    public void displayUI(){

        PatientApptInterface patientApptInterface = new PatientApptUI();
        MedicalRecordUI medicalRecordUI = new MedicalRecordUI();

        PatientUI patientUI = new PatientUI(this, patientApptInterface, medicalRecordUI);
        patientUI.displayMenu();
    }
}
