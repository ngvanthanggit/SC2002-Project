package user;

import medicalrecord.MedicalRecord;

public class Doctor extends User {
    public Doctor() {
        this(null, null, null, null, 0, null);
    }

    public Doctor(String hospitalID, String name, String role,
            String gender, int age, String password) {
        super(hospitalID, name, role, gender, age, password);
    }

    public void viewMedicalRecord(String patientID) {

    }

    public void updateMedicalRecord(String patientID, MedicalRecord updatedMedicalRecord) {

    }

    public void viewSchedule() {

    }

    public void setSchedule() {

    }

    public void acceptAppointmentRequest() {

    }

    public void declineAppointmentRequest() {

    }

    public void viewUpcomingAppointment() {

    }

    public void recordAppointmentOutcome() {

    }
}
