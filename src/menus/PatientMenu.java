package menus;

import java.util.Scanner;

public interface PatientMenu {
    public void displayMenu();

    public void scheduleAppointment();

    public void scheduleAppointment(Scanner scanner);

    public void rescheduleAppointment();

    public void cancelAppointment();

    public void viewScheduledAppointments();

    public void viewAppointmentStatus();

    public void viewAppointmentOutcomes();

}
