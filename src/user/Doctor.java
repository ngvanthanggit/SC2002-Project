package user;

import interfaces.DocApptInterface;
import interfaces.MedicalRecInterface;
import interfaces.ScheduleInterface;
import userInterface.DocApptUI;
import userInterface.DoctorUI;
import userInterface.InvenManageUI;
import userInterface.MedicalRecordUI;
import userInterface.ScheduleUI;

public class Doctor extends User {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

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

        DoctorUI doctorUI = new DoctorUI(this, medicalRecInterface,
                scheduleInterface, docApptInterface);
        doctorUI.displayMenu();
    }
}
