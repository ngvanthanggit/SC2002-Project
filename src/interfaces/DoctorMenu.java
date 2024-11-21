package interfaces;

import java.util.Scanner;

public interface DoctorMenu extends CommonMenu{

    public void medicalRecordMenu(Scanner sc);
    public void scheduleMenu(Scanner sc);
    public void appointmentMenu(Scanner sc);
    /*public void viewPatientRecords();

    public void updatePatientRecords();

    public void viewSchedule();

    public void setSchedule();

    public void viewAppointments();

    public void updateAppointmentRequest();

    public void recordAppointmentOutcome();*/
}
