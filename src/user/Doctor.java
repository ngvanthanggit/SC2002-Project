package user;

import interfaces.DocApptInterface;
import interfaces.LeaveInterface;
import interfaces.MedicalRecInterface;
import interfaces.ScheduleInterface;
import userInterface.DocApptUI;
import userInterface.DoctorUI;
import userInterface.LeaveUI;
import userInterface.MedicalRecordUI;
import userInterface.ScheduleUI;

/**
 * represents a Doctor in HMS
 * extends {@link User} class
 */
public class Doctor extends User {

    /** default constructor for creating a Doctor with no attributes */
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    /**
     * constructs a Doctor object
     * @param hospitalID The hospital ID of the doctor
     * @param name The name of the doctor
     * @param role The role of the doctor (must be {@link Role#Doctor}).
     * @param gender The gender of the doctor
     * @param age The age of the doctor
     * @param password The password of the doctor
     */
    public Doctor(String hospitalID, String name, Role role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    /**
     * Formatted string containing Doctor information
     * Overrides the {@link User#userInfo()} method
     * 
     * @return A formatted string with Administrator details
     */
    @Override
    public String userInfo() {
        return String.format("[DoctorID = %s, Name = %s, Role = %s, Gender = %s, Age = %d, Password = %s]",
                getHospitalID(), getName(), getRole(), getGender(), getAge(), getPassword());
    }

    /**
     * Displays the user interface for the Doctor
     * Overrides the {@link User#displayUI()} method
     */
    @Override
    public void displayUI() {
        MedicalRecInterface medicalRecInterface = new MedicalRecordUI();
        ScheduleInterface scheduleInterface = new ScheduleUI();
        DocApptInterface docApptInterface = new DocApptUI(scheduleInterface);
        LeaveInterface leaveInterface = new LeaveUI();

        DoctorUI doctorUI = new DoctorUI(this, medicalRecInterface,
                scheduleInterface, docApptInterface, leaveInterface);
        doctorUI.displayMenu();
    }
}
