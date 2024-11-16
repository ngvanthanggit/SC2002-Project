package menus;

import java.util.Scanner;

public interface DoctorMenu {
    public void viewPatientRecords();

    public void updatePatientRecords();

    public void viewSchedule();

    public void setSchedule();

    public void viewAppointments();

    public void updateAppointmentRequest();

    public void recordAppointmentOutcome();
}
